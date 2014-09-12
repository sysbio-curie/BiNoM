# This program is free software; you can redistribute it and/or modify
# it under the terms of the (LGPL) GNU Lesser General Public License as
# published by the Free Software Foundation; either version 3 of the 
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Library Lesser General Public License for more details at
# ( http://www.gnu.org/licenses/lgpl.html ).
#
# You should have received a copy of the GNU Lesser General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
# written by: Eric Viara (eric.viara@curie.fr)

import subprocess, sys, os
import json
import http.client, urllib.request, urllib.parse, urllib.error
import datetime
from time import sleep

# NV_MAP_URL=http://localhost/~eviara/navicell/nv2.2/maps/acsn_light/master/index.php python -i navicell.py

print("")
print("nv = NaviCell(NVChromeLauncher())")
print("")
#print("nv.isReady('')")
#print("")
#print("nv.waitForReady('')")
#print("")
#print("nv.isReady('')")
#print("")
print('nv.importDatatables("http://localhost/~eviara/data/cancer_cell_line_broad/datatable_list_localhost.txt", "", "Datatable list", {"open_drawing_editor": True, "import_display_markers": "checked", "import_display_heatmap": True})')
print("")

print('nv.importDatatables(nv.makeDataFromFile("/bioinfo/users/eviara/projects/navicell/data_examples/cancer_cell_line_broad/CCL_Expression_neg.txt"), "MyExpr", "Protein expression data", {"open_drawing_editor": True, "import_display_markers": "checked", "import_display_heatmap": True})')
print("")

print('nv.sampleAnnotationImport("http://localhost/~eviara/data/cancer_cell_line_broad/SampleAnnotations.txt")')
print("")

print('nv.findEntities("", "A*", {"in": "annot", "token": "word"}, False)')
print("")
print('nv.openModule("../../survival_light/master/index.html")')
print("")

PACKSIZE = 500000

class NVProtocol:

    def __init__(self, host='', url=''):
        if not host and 'NV_PROTOCOL_HOST' in os.environ:
            host = os.environ['NV_PROTOCOL_HOST']
        if not url and 'NV_PROTOCOL_URL' in os.environ:
            url = os.environ['NV_PROTOCOL_URL']

        self.host = host
        self.url = url

class NVLauncher:

    def __init__(self, browser='', url=''):
        if not browser and 'NV_BROWSER' in os.environ:
            browser = os.environ['NV_BROWSER']
        if not url and 'NV_MAP_URL' in os.environ:
            url = os.environ['NV_MAP_URL']

        self.browser = browser
        self.url = url

    def launch(self, session_id):
        raise Exception('NVLauncher is an abstract class, must be inherited')

class NVChromeLauncher(NVLauncher):

    def launch(self, session_id):
        if self.browser == 'chrome':
            cmd = []
            cmd.append("/usr/bin/google-chrome")
            cmd.append("--allow-file-access-from-files")
            cmd.append(self.url + "?id=" + session_id);
            subprocess.check_call(cmd);

class NaviCell:

    def _make_session_id(self):
        d = datetime.datetime.now() 
        pid = str(os.getpid())
        for x in range(6-len(pid)):
            pid = "0" + pid
        return (d.strftime('%s%%06d') % d.microsecond) + pid;

    def __init__(self, nv_launcher='', session_id='', nv_protocol=NVProtocol()):
        if not session_id:
            session_id = self._make_session_id()

        self._msg_id = 1000
        self._hugo_list = []
        self._hugo_map = {}
        self.session_id = session_id
        self.nv_protocol = nv_protocol
        self._init_session()

        if nv_launcher:
            nv_launcher.launch(self.session_id)

        self._waitForReady('')

    def _message_id(self):
        self._msg_id += 1
        return self._msg_id

    def _send(self, msg_id, params):
        if not self.session_id:
            raise Exception('navicell session is not active')

        packcount = 0;
        params['id'] = self.session_id;
        params['msg_id'] = msg_id

        if 'data' in params:
            datalen = len(params['data'])
#            print("datalen", datalen, PACKSIZE)
            if datalen > PACKSIZE:
                packcount = int(datalen/PACKSIZE)+1
                params['packcount'] = packcount
                data = params['data']
                params['data'] = "@@"

        encoded_params = urllib.parse.urlencode(params)
        headers = {"Content-type": "application/x-www-form-urlencoded", "Accept": "text/plain"}
        conn = http.client.HTTPConnection(self.nv_protocol.host)
        conn.request("POST", self.nv_protocol.url, encoded_params, headers);

        if packcount > 0:
#            print("performing filling", packcount)
            fillparams = params
            fillparams['perform'] = 'filling'
            for packnum in reversed(range(packcount)): # testing packing order
#            for packnum in range(packcount):
                fillparams['packnum'] = packnum+1
                beg = packnum*PACKSIZE
                end = (packnum+1)*PACKSIZE
                if end > datalen:
                    end = datalen
                fillparams['data'] = data[beg:end]
                encoded_params = urllib.parse.urlencode(fillparams)
                fillconn = http.client.HTTPConnection(self.nv_protocol.host)
                fillconn.request("POST", self.nv_protocol.url, encoded_params, headers);
                fillconn.close()

        response = conn.getresponse()
        data = response.read()
#        print (response.status, response.reason, data)
        conn.close()

        if data:
            decoded_data = json.loads(data.decode('utf-8'))
            if not decoded_data['status']:
                return decoded_data['data']
            raise Exception('navicell error', decoded_data[data])
        return data

    def _init_session(self):
        self._send(self._message_id(), {'mode': 'none', 'perform': 'init'});

    def _reset_session(self):
        self._send(self._message_id(), {'mode': 'none', 'perform': 'reset'});

    def _waitForReady(self, module):
        while not self._isReady(module):
            sleep(0.05)
        return True

    def _isReady(self, module):
        return self._cli2srv('nv_is_ready', module, [], 'send_and_rcv')

    def _make_data(self, data):
        return "@COMMAND " + json.dumps(data)

    def _cli2srv(self, action, module, args, perform='send_and_rcv'):
        msg_id = self._message_id()
        return self._send(msg_id, {'mode': 'cli2srv', 'perform': perform, 'data': self._make_data({'action': action, 'module': module, 'args' : args, 'msg_id': msg_id})})

    def _drawing_config_perform(self, module, action, arg1='', arg2='', arg3=''):
        self._cli2srv('nv_drawing_config_perform', module, [action, arg1, arg2, arg3])

    def _heatmap_editor_perform(self, module, action, arg1='', arg2='', arg3=''):
        self._cli2srv('nv_heatmap_editor_perform', module, [action, arg1, arg2, arg3])

    def _barplot_editor_perform(self, module, action, arg1='', arg2='', arg3=''):
        self._cli2srv('nv_barplot_editor_perform', module, [action, arg1, arg2, arg3])

    def _glyph_editor_perform(self, module, action, arg1='', arg2='', arg3=''):
        self._cli2srv('nv_glyph_editor_perform', module, [action, arg1, arg2, arg3])

    def _map_staining_editor_perform(self, module, action, arg1='', arg2='', arg3=''):
        self._cli2srv('nv_map_staining_editor_perform', module, [action, arg1, arg2, arg3])

    def _datatable_config_perform(self, module, action, datatable, what, arg1='', arg2=''):
        self._cli2srv('nv_datatable_config_perform', module, [action, datatable, what, arg1, arg2])

    # public API
    def makeData(self, data):
            self.getHugoList()
            hugo_map = self._hugo_map

            lines = data.split('\n')
            ret = lines[0]

            for line in lines:
                arr = line.split('\t')
                if arr[0] in hugo_map:
                    ret += line + "\n"
            return "@DATA\n" + ret


    def makeDataFromFile(self, filename):
        with open(filename) as f:
            self.getHugoList()
            hugo_map = self._hugo_map

            ret = ''
            for line in f:
                ret = line + "\n"
                break

            for line in f:
                arr = line.split('\t')
                if arr[0] in hugo_map:
                    ret += line + "\n"
            return "@DATA\n" + ret

### datatable data: methods do not depend on module
    def importDatatables(self, datatable_url_list, datatable_name, datatable_type, params={}):
        self._cli2srv('nv_import_datatables', '', [datatable_type, datatable_name, '', datatable_url_list, params])

    def sampleAnnotationImport(self, sample_annotation_url):
        self._cli2srv('nv_sample_annotation_perform', '', ['import', sample_annotation_url])

    def sampleAnnotationOpen(self):
        self._cli2srv('nv_sample_annotation_perform', '', ['open'])

    def sampleAnnotationClose(self):
        self._cli2srv('nv_sample_annotation_perform', '', ['close'])

    def sampleAnnotationApply(self):
        self._cli2srv('nv_sample_annotation_perform', '', ['apply'])

    def sampleAnnotationCancel(self):
        self._cli2srv('nv_sample_annotation_perform', '', ['cancel'])

    def sampleAnnotationSelectAnnotation(self, annot, select=True):
        self._cli2srv('nv_sample_annotation_perform', '', ['select_annotation', annot, select])

    def getDatatableList(self):
        return self._cli2srv('nv_get_datatable_list', '', [], 'send_and_rcv')

    def getDatatableGeneList(self):
        return self._cli2srv('nv_get_datatable_gene_list', '', [], 'send_and_rcv')

    def getDatatableSampleList(self):
        return self._cli2srv('nv_get_datatable_sample_list', '', [], 'send_and_rcv')

###
    def findEntities(self, module, search, hints = {}, open_bubble = False):
        if hints:
            mod_list = []
            for hint in ["in", "class", "op", "token"]:
                if hint in hints:
                    mod_list.append(hint + "=" + hints[hint])
            if len(mod_list) > 0:
                mod = '/'
                for ind in range(len(mod_list)):
                    if ind > 0:
                        mod += ";"
                    mod += mod_list[ind]
                search += " " + mod
        return self._cli2srv('nv_find_entities', module, [search, open_bubble])

    def uncheckAllEntities(self, module):
        self._cli2srv('nv_uncheck_all_entities', module, [])

    def selectEntity(self, module, entity_name):
        return self._cli2srv('nv_find_entities', module, [entity_name])

### navigation
    def setZoom(self, module, zoom):
        self._cli2srv('nv_set_zoom', module, [zoom])

    def scroll(self, module, xscroll, yscroll=0):
        self._cli2srv('nv_scroll', module, [xscroll, yscroll])

### heatmap editor
    def heatmapEditorOpen(self, module):
        self._heatmap_editor_perform(module, 'open')

    def heatmapEditorClose(self, module):
        self._heatmap_editor_perform(module, 'close')

    def heatmapEditorApply(self, module):
        self._heatmap_editor_perform(module, 'apply')

    def heatmapEditorApplyAndClose(self, module):
        self._heatmap_editor_perform(module, 'apply_and_close')

    def heatmapEditorCancel(self, module):
        self._heatmap_editor_perform(module, 'cancel')

    def heatmapEditorClearSamples(self, module):
        self._heatmap_editor_perform(module, 'clear_samples')

    def heatmapEditorAllSamples(self, module):
        self._heatmap_editor_perform(module, 'all_samples')

    def heatmapEditorAllGroups(self, module):
        self._heatmap_editor_perform(module, 'all_groups')

    def heatmapEditorFromBarplot(self, module):
        self._heatmap_editor_perform(module, 'from_barplot')

    def heatmapEditorSetTransparency(self, module, value):
        self._heatmap_editor_perform(module, 'set_transparency', value)

    def heatmapEditorSelectSample(self, module, where, sample):
        self._heatmap_editor_perform(module, 'select_sample', where, sample)

    def heatmapEditorSelectDatatable(self, module, where, datatable):
        self._heatmap_editor_perform(module, 'select_datatable', where, datatable)

### barplot editor
    def barplotEditorOpen(self, module):
        self._barplot_editor_perform(module, 'open')

    def barplotEditorClose(self, module):
        self._barplot_editor_perform(module, 'close')

    def barplotEditorApply(self, module):
        self._barplot_editor_perform(module, 'apply')

    def barplotEditorApplyAndClose(self, module):
        self._barplot_editor_perform(module, 'apply_and_close')

    def barplotEditorCancel(self, module):
        self._barplot_editor_perform(module, 'cancel')

    def barplotEditorClearSamples(self, module):
        self._barplot_editor_perform(module, 'clear_samples')

    def barplotEditorAllSamples(self, module):
        self._barplot_editor_perform(module, 'all_samples')

    def barplotEditorAllGroups(self, module):
        self._barplot_editor_perform(module, 'all_groups')

    def barplotEditorFromHeatmap(self, module):
        self._barplot_editor_perform(module, 'from_heatmap')

    def barplotEditorSetTransparency(self, module, value):
        self._barplot_editor_perform(module, 'set_transparency', value)

    def barplotEditorSelectSample(self, module, where, sample):
        self._barplot_editor_perform(module, 'select_sample', where, sample)

    def barplotEditorSelectDatatable(self, module, datatable):
        self._barplot_editor_perform(module, 'select_datatable', datatable)

### glyph editor
    def glyphEditorOpen(self, module, num):
        self._glyph_editor_perform(module, 'open', num)

    def glyphEditorClose(self, module, num):
        self._glyph_editor_perform(module, 'close', num)

    def glyphEditorApply(self, module, num):
        self._glyph_editor_perform(module, 'apply', num)

    def glyphEditorApplyAndClose(self, module, num):
        self._glyph_editor_perform(module, 'apply_and_close', num)

    def glyphEditorCancel(self, module, num):
        self._glyph_editor_perform(module, 'cancel', num)

    def glyphEditorSetTransparency(self, module, num, value):
        self._glyph_editor_perform(module, 'set_transparency', num, value)

    def glyphEditorSelectSample(self, module, num, sample):
        self._glyph_editor_perform(module, 'select_sample', num, sample)

    def glyphEditorSelectShapeDatatable(self, module, num, datatable):
        self._glyph_editor_perform(module, 'select_datatable_shape', num, datatable)

    def glyphEditorSelectColorDatatable(self, module, num, datatable):
        self._glyph_editor_perform(module, 'select_datatable_color', num, datatable)

    def glyphEditorSelectSizeDatatable(self, module, num, datatable):
        self._glyph_editor_perform(module, 'select_datatable_size', num, datatable)

### map staining editor
    def mapStainingEditorOpen(self, module):
        self._map_staining_editor_perform(module, 'open')

    def mapStainingEditorClose(self, module):
        self._map_staining_editor_perform(module, 'close')

    def mapStainingEditorApply(self, module):
        self._map_staining_editor_perform(module, 'apply')

    def mapStainingEditorApplyAndClose(self, module):
        self._map_staining_editor_perform(module, 'apply_and_close')

    def mapStainingEditorCancel(self, module):
        self._map_staining_editor_perform(module, 'cancel')

    def mapStainingEditorSetTransparency(self, module, value):
        self._map_staining_editor_perform(module, 'set_transparency', value)

    def mapStainingEditorSelectSample(self, module, sample):
        self._map_staining_editor_perform(module, 'select_sample', sample)

    def mapStainingEditorSelectDatatable(self, module, datatable):
        self._map_staining_editor_perform(module, 'select_datatable', datatable)

### drawing configuration
    def drawingConfigOpen(self, module):
        self._drawing_config_perform(module, 'open')

    def drawingConfigClose(self, module):
        self._drawing_config_perform(module, 'close')

    def drawingConfigApply(self, module):
        self._drawing_config_perform(module, 'apply')

    def drawingConfigApplyAndClose(self, module):
        self._drawing_config_perform(module, 'apply_and_close')

    def drawingConfigCancel(self, module):
        self._drawing_config_perform(module, 'cancel')

    def drawingConfigSelectHeatmap(self, module, select=True):
        self._drawing_config_perform(module, 'select_heatmap', select)

    def drawingConfigSelectBarplot(self, module, select=True):
        self._drawing_config_perform(module, 'select_barplot', select)

    def drawingConfigSelectGlyph(self, module, num, select=True):
        self._drawing_config_perform(module, 'select_glyph', num, select)

    def drawingConfigSelectMapStaining(self, module, select=True):
        self._drawing_config_perform(module, 'select_map_staining', select)

    def drawingConfigDisplayAllGenes(self, module):
        self._drawing_config_perform(module, 'display_all_genes')

    def drawingConfigDisplaySelectedGenes(self, module):
        self._drawing_config_perform(module, 'display_selected_genes')

### datatable config
    def datatableConfigOpen(self, module, datatable, what):
        self._datatable_config_perform(module, 'open', datatable, what)

    def datatableConfigClose(self, module, datatable, what):
        self._datatable_config_perform(module, 'close', datatable, what)

    def datatableConfigApply(self, module, datatable, what):
        self._datatable_config_perform(module, 'apply', datatable, what)

    def datatableConfigCancel(self, module, datatable, what):
        self._datatable_config_perform(module, 'cancel', datatable, what)

###

    def prepareImportDialog(self, module, filename, name, filetype):
        self._cli2srv('nv_prepare_import_dialog', module, [filename, name, filetype])

    def getCommandHistory(self, module):
        return self._cli2srv('nv_get_command_history', module, [], 'send_and_rcv')

    def executeCommands(self, module, commands):
        return self._send(self._message_id(), {'mode': 'cli2srv', 'perform': 'send_and_rcv', 'data': commands});

### get methods
    def getModuleList(self):
        return self._cli2srv('nv_get_module_list', '', [], 'send_and_rcv')

    def getBiotypeList(self):
        return self._cli2srv('nv_get_biotype_list', '', [], 'send_and_rcv')

    def getHugoList(self):
        module = ''
        if not self._hugo_list:
            self._hugo_list = self._cli2srv('nv_get_hugo_list', '', [], 'send_and_rcv')
            hugo_map = {}
            for hugo_name in self._hugo_list:
                hugo_map[hugo_name] = True
            self._hugo_map = hugo_map

        return self._hugo_list

    def openModule(self, module2open):
        self._cli2srv('nv_open_module', '', [module2open, []])

    def reset(self):
        self._reset_session()
        self.session_id = 0
