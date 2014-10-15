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

import subprocess, sys, os, optparse
import json
import http.client, urllib.request, urllib.parse, urllib.error
import datetime
from time import sleep

# NV_MAP_URL=http://localhost/~eviara/navicell/nv2.2/maps/acsn_light/master/index.php python -i navicell.py

PACKSIZE = 500000

class Cli2NV:

    def __init_attrs__(self, cli2nv_url, is_https, str = ''):
        self._is_https = is_https
        if not str:
            url = cli2nv_url
        else:
            url = cli2nv_url[len(str):]
        idx = url.find("/")
        if idx != -1:
            self._host = url[0:idx]
            self._url = url[idx:]
        else:
            raise Exception("invalid format " + cli2nv_url)

    def __init__(self, cli2nv_url = ''):
        if not cli2nv_url:
            raise Exception("empty cli2nv URL")

        idx = cli2nv_url.find("http://")
        if idx != -1:
            self.__init_attrs__(cli2nv_url, False, "http://")
        else:
            idx = cli2nv_url.find("https://")
            if idx != -1:
                self.__init_attrs__(cli2nv_url, True, "https://")
            else:                
                self.__init_attrs__(cli2nv_url, False)

    def getURL(self):
        return self._url

    def getHost(self):
        return self._host

    def isHttps(self):
        return self._is_https

    def getProtocol(self):
        if self._is_https:
            return "https";
        return "http";

    def newConnection(self):
        if self._is_https:
            return http.client.HTTPSConnection(self._host)
        return http.client.HTTPConnection(self._host)

class BrowserLauncher:

    def __init__(self, browser_command, map_url):
        if not browser_command:
            raise Exception("browser command is not set")
        if not map_url:
            raise Exception("map url is not set")
        self.map_url = map_url
        self.cmd = []
        for item in browser_command.split(" "):
            self.cmd.append(item)

    def launch(self, session_id):
        self.cmd.append(self.map_url + "?id=" + session_id)
        subprocess.check_call(self.cmd)

class NaviCell:

    def __init__(self, options):

        self.cli2nv = Cli2NV(options.cli2nv_url)

        if options.map_url and options.browser_command:
            self._browser_launcher = BrowserLauncher(options.browser_command, options.map_url)

        self._msg_id = 1000
        self.session_id = "1"

        self._hugo_list = []
        self._hugo_map = {}

    #
    # private methods
    #

    def _message_id(self):
        self._msg_id += 1
        return self._msg_id

    def _send(self, msg_id, params, straight=False):
        if not self.session_id:
            raise Exception('navicell session is not active')

        packcount = 0
        params['id'] = self.session_id
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
        conn = self.cli2nv.newConnection()

        conn.request("POST", self.cli2nv.getURL(), encoded_params, headers)

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
                fillconn = self.cli2nv.newConnection()
                fillconn.request("POST", self.cli2nv.getURL(), encoded_params, headers);
                fillconn.close()

        response = conn.getresponse()
        data = response.read()
#        print (response.status, response.reason, data)
        conn.close()

        if data:
            if straight:
                return data.decode('utf-8')
            decoded_data = json.loads(data.decode('utf-8'))
            if not decoded_data['status']:
                return decoded_data['data']
            raise Exception('navicell error', decoded_data[data])
        return data

### session management
    def _gen_session_id(self):
        return self._send(self._message_id(), {'mode': 'session', 'perform': 'genid'}, True)

    def _reset_current_session(self):
        self._send(self._message_id(), {'mode': 'session', 'perform': 'reset'});

    def _check_session(self, session_id):
        checked = self._send(self._message_id(), {'mode': 'session', 'perform': 'check'}, True)
        return checked == "ok"

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

    def _notice_perform(self, module, action, arg1='', arg2='', arg3='', arg4='', arg5=''):
        self._cli2srv('nv_notice_perform', module, [action, arg1, arg2, arg3, arg4, arg5])

    def _drawing_config_perform(self, module, action, arg1='', arg2='', arg3=''):
        self._cli2srv('nv_drawing_config_perform', module, [action, arg1, arg2, arg3])

    def _mydata_perform(self, module, action, arg1='', arg2='', arg3=''):
        self._cli2srv('nv_mydata_perform', module, [action, arg1, arg2, arg3])

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

    def get_session_id(self):
        return self.session_id

    def _is_https(self):
        return self.protocol == 'https'

    #
    # public API
    #

### session management
    def launchBrowser(self):
        if not self._browser_launcher:
            raise Exception("no launcher configurated")
        self.session_id = self._gen_session_id()
        self._browser_launcher.launch(self.session_id)
        self._waitForReady('')

    def listSessions(self):
        print(self._send(self._message_id(), {'mode': 'session', 'perform': 'list'}, True), end="")

    def cleanSessions(self):
        print(self._send(self._message_id(), {'mode': 'session', 'perform': 'clean'}, True), end="")

    def attachSession(self, session_id):
        if self.session_id != "1":
            raise Exception("session id already set");
        o_session_id = self.session_id
        self.session_id = session_id
        if not self._check_session(session_id):
            self.session_id = o_session_id
            raise Exception("session id " + session_id + " is invalid");

    def attachLastSession(self):
        session_id = self._send(self._message_id(), {'mode': 'session', 'perform': 'get', 'which' : '@@'}, True);
        self.attachSession(session_id)

    def attachRefererSession(self, referer):
        session_id = self._send(self._message_id(), {'mode': 'session', 'perform': 'get', 'which' : '@' + referer}, True);
        self.attachSession(session_id)

    def reset(self):
        self._reset_current_session()
        self.session_id = 0

### utility data methods
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

### notice dialog
    def noticeMessage(self, module, header, msg, position='left top', width=0, height=0):
        self._notice_perform(module, 'set_message_and_open', header, msg, position, width, height)

    def noticeOpen(self, module):
        self._notice_perform(module, 'open')

    def noticeClose(self, module):
        self._notice_perform(module, 'close')

### mydata dialog
    def myDataOpen(self, module):
        self._mydata_perform(module, 'open')

    def myDataClose(self, module):
        self._mydata_perform(module, 'close')

    def myDataSelectDatatables(self, module):
        self._mydata_perform(module, 'select_datatables')

    def myDataSelectSamples(self, module):
        self._mydata_perform(module, 'select_samples')

    def myDataSelectGenes(self, module):
        self._mydata_perform(module, 'select_genes')

    def myDataSelectGroups(self, module):
        self._mydata_perform(module, 'select_groups')

    def myDataSelectModules(self, module):
        self._mydata_perform(module, 'select_modules')

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

###
    def examples(self):
        protocol = self.cli2nv.getProtocol()
        if self.cli2nv.isHttps():
            datalist_url = "datatable_list_url_secure.txt"
        else:
            datalist_url = "datatable_list_url.txt"

        print('nv.importDatatables("http://localhost/~eviara/data/cancer_cell_line_broad/datatable_list_localhost.txt", "", "Datatable list", {"open_drawing_editor": True, "import_display_markers": "checked", "import_display_heatmap": True})')
        print("")

        print('nv.importDatatables("' + protocol + '://acsn.curie.fr/navicell/demo/data/CCL_CopyNumber.txt", "", "Continuous copy number data", {"open_drawing_editor": True, "import_display_markers": "checked", "import_display_heatmap": True})')
        print("")

        print('nv.importDatatables("' + protocol + '://acsn.curie.fr/navicell/demo/data/' + datalist_url + '", "", "Datatable list", {"open_drawing_editor": True, "import_display_markers": "checked", "import_display_heatmap": True})')
        print("")
        print('nv.importDatatables(nv.makeDataFromFile("/bioinfo/users/eviara/projects/navicell/data_examples/cancer_cell_line_broad/CCL_Expression_neg.txt"), "MyExpr", "Protein expression data", {"open_drawing_editor": True, "import_display_markers": "checked", "import_display_heatmap": True})')
        print("")
        
        print('nv.executeCommands("", "!!' + protocol + '://acsn.curie.fr/navicell/demo/commands/demo1.nvc")')
        print("")
        print('nv.executeCommands("", "!!http://localhost/~eviara/demo/demo1.nvc")')
        print("")

        print('nv.sampleAnnotationImport("http://localhost/~eviara/data/cancer_cell_line_broad/SampleAnnotations.txt")')
        print("")

        print('nv.findEntities("", "A*", {"in": "annot", "token": "word"}, False)')
        print("")
        print('nv.openModule("../../survival_light/master/index.html")')
        print("")
        print('nv.noticeMessage("", "", "<span style=\\"color: darkblue\\">Running NaviCell in demo mode<br/>Please wait...</span>", "left top", 350, 300)')
        print("")
        print('nv.noticeMessage("", "<span style=\\"color: darkred; font-size: 18px\\">Demo</span>", "<span style=\\"color: darkblue; font-size: 14px\\">NaviCell is currently running in demo mode<br/><br/>Please&nbsp;wait...</span>", "left center", 380, 320)')
        print("")

parser = optparse.OptionParser()
parser.disable_interspersed_args()

if 'NV_CLI2NV_URL' in os.environ:
    parser.set_defaults(cli2nv_url = os.environ['NV_CLI2NV_URL'])
if 'NV_BROWSER_COMMAND' in os.environ:
    parser.set_defaults(browser_command = os.environ['NV_BROWSER_COMMAND'])
if 'NV_MAP_URL' in os.environ:
    parser.set_defaults(map_url = os.environ['NV_MAP_URL'])

parser.set_defaults(navicell_varname = 'nv')

parser.add_option("--map-url", help="URL of map [%default]", metavar="URL")
parser.add_option("--launch-browser", action="store_true", help="launch browser using the URL given by --map-url URL")
parser.add_option("--cli2nv_url", help="client/NaviCell communication URL [%default]", metavar="URL")
parser.add_option("--browser-command", help="browser command to be used [%default]", metavar="COMMAND")
parser.add_option("--attach-session", help="attach the given session", metavar="SESSION_ID")
parser.add_option("--attach-last-session", action="store_true", help="attach the last session")
parser.add_option("--attach-referer-session", help="attach the last session from this referer", metavar="REFERER_IP")
parser.add_option("--list-sessions", action="store_true", help="list active sessions and exit")
parser.add_option("--clean-sessions", action="store_true", help="clean all active sessions and exit")
parser.add_option("--navicell-varname", help="name of python variable for navicell [%default]", metavar="VARNAME")
    
try:
    (options, args) = parser.parse_args()
except:
    os._exit(0)

if options.list_sessions and options.clean_sessions:
    print("--list-sessions and --clean-sessions are exclusive options")
    os._exit(1)

if options.list_sessions and options.attach_session:
    print("--list-sessions and --attach-session are exclusive options")
    os._exit(1)

if options.clean_sessions and options.attach_session:
    print("--clean-sessions and --attach-session are exclusive options")
    os._exit(1)

try:
    _nv_varname = options.navicell_varname
    globals()[_nv_varname] = NaviCell(options)
    if options.list_sessions:
        globals()[_nv_varname].listSessions()
        os._exit(0)
    elif options.clean_sessions:
        globals()[_nv_varname].cleanSessions()
        os._exit(0)
    elif options.attach_session:
        globals()[_nv_varname].attachSession(options.attach_session)
    elif options.attach_last_session:
        globals()[_nv_varname].attachLastSession()
    elif options.attach_referer_session:
        globals()[_nv_varname].attachRefererSession(options.attach_referer_session)
    elif options.launch_browser:
        globals()[_nv_varname].launchBrowser()

except Exception as e:
    print("NaviCell Python Client:", str(e))
    os._exit(1)

print("")
print("===================================")
print(" Welcome in NaviCell python client")
print("===================================")
print("")

print("Use python variable '" + _nv_varname + "' as the NaviCell object")
print("Type '" + _nv_varname + ".examples()' to get examples")
print("")
#if options.attach_session or options.attach_last_session or options.attach_referer_session:
#    print(_nv_varname + " object attached to session", globals()[_nv_varname].get_session_id())

