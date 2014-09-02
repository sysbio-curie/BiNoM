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
import httplib, urllib
import datetime

print ""
print "nv = NaviCell(NVChromeLauncher())"
print ""
print 'nv.import_datatables("20111118modelc:master", "http://localhost/~eviara/data/cancer_cell_line_broad/datatable_list_localhost.txt", "", "Datatable list", {"open_drawing_editor": True, "async": False, "import_display_markers": "checked", "import_display_heatmap": True})'
print ""
print 'nv.import_datatables("20111118modelc:master", nv.make_data_from_file("20111118modelc:master", "/bioinfo/users/eviara/projects/navicell/data_examples/cancer_cell_line_broad/CCL_Expression_baby.txt"), "MyExpr_baby", "Protein expression data", {"open_drawing_editor": True, "async": False, "import_display_markers": "checked", "import_display_heatmap": True})'
print ""
print 'nv.import_datatables("20111118modelc:master", nv.make_data_from_file("20111118modelc:master", "/bioinfo/users/eviara/projects/navicell/data_examples/cancer_cell_line_broad/CCL_Expression_neg.txt"), "MyExpr", "Protein expression data", {"open_drawing_editor": True, "async": False, "import_display_markers": "checked", "import_display_heatmap": True})'
print ""

class NVProtocol:

    def __init__(self, host='', url=''):
        if not host and os.environ.has_key('NV_PROTOCOL_HOST'):
            host = os.environ['NV_PROTOCOL_HOST']
        if not url and os.environ.has_key('NV_PROTOCOL_URL'):
            url = os.environ['NV_PROTOCOL_URL']

        self.host = host
        self.url = url

class NVLauncher:

    def __init__(self, browser='', url=''):
        if not browser and os.environ.has_key('NV_BROWSER'):
            browser = os.environ['NV_BROWSER']
        if not url and os.environ.has_key('NV_MAP_URL'):
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
        return (d.strftime('%s%%06d') % d.microsecond) + '.' + str(os.getpid())

    def __init__(self, nv_launcher='', nv_protocol=NVProtocol(), session_id=''):
        if not session_id:
            session_id = self._make_session_id()

        self.session_id = session_id
        self.nv_protocol = nv_protocol
        self._init_session()
        if nv_launcher:
            nv_launcher.launch(self.session_id)

    def _send(self, params):
        if not self.session_id:
            raise Exception('navicell session is not active')

        params['id'] = self.session_id;
        params = urllib.urlencode(params)
        headers = {"Content-type": "application/x-www-form-urlencoded", "Accept": "text/plain"}
        conn = httplib.HTTPConnection(self.nv_protocol.host)
        conn.request("POST", self.nv_protocol.url, params, headers);
        response = conn.getresponse()
        data = response.read()
#        print response.status, response.reason, data
        conn.close()
        if data:
            return json.loads(data)
        return data

    def _init_session(self):
        self._send({'mode': 'none', 'perform': 'init'});

    def _reset_session(self):
        self._send({'mode': 'none', 'perform': 'reset'});

    def _make_data(self, data):
        return "@COMMAND " + json.dumps(data)

    def _cli2srv(self, action, module, args, perform='send'):
        return self._send({'mode': 'cli2srv', 'perform': perform, 'data': self._make_data({'action': action, 'module': module, 'args' : args})})

    def _heatmap_editor_perform(self, module, action, arg1='', arg2='', arg3=''):
        self._cli2srv('nv_heatmap_editor_perform', module, [action, arg1, arg2, arg3])

    # public API
    def make_data_from_file(self, module, filename):
        with open(filename) as f:
            hugo_map = {}
            for hugo_name in self.get_hugo_list(module):
                hugo_map[hugo_name] = True

            ret = ''
            for line in f:
                ret = line + "\n"
                break

            for line in f:
                arr = line.split('\t')
                if hugo_map.has_key(arr[0]):
                    ret += line + "\n"
            return "@DATA\n" + ret

    def import_datatables(self, module, datatable_url_list, datatable_name, datatable_type, params={}):
        self._cli2srv('nv_import_datatables', module, [datatable_type, datatable_name, '', datatable_url_list, params])

    def find_entities(self, module, search, open_bubble = False):
        self._cli2srv('nv_find_entities', module, [search, open_bubble])

    def uncheck_all_entities(self, module):
        self._cli2srv('nv_uncheck_all_entities', module, [])

    def get_module_list(self, module):
        return self._cli2srv('nv_get_module_list', module, [], 'send_and_rcv')

    def get_hugo_list(self, module):
        return self._cli2srv('nv_get_hugo_list', module, [], 'send_and_rcv')

    def get_datatable_list(self, module):
        return self._cli2srv('nv_get_datatable_list', module, [], 'send_and_rcv')

    def get_datatable_gene_list(self, module):
        return self._cli2srv('nv_get_datatable_gene_list', module, [], 'send_and_rcv')

    def select_entity(self, module, entity_name):
        self._cli2srv('nv_find_entities', module, [entity_name])

    def set_zoom(self, module, zoom):
        self._cli2srv('nv_set_zoom', module, [zoom])

    def scroll(self, module, xscroll, yscroll=0):
        self._cli2srv('nv_scroll', module, [xscroll, yscroll])

    def heatmap_editor_open(self, module):
        self._heatmap_editor_perform(module, 'open')

    def heatmap_editor_close(self, module):
        self._heatmap_editor_perform(module, 'close')

    def heatmap_editor_apply(self, module):
        self._heatmap_editor_perform(module, 'apply')

    def heatmap_editor_cancel(self, module):
        self._heatmap_editor_perform(module, 'cancel')

    def heatmap_editor_clear_samples(self, module):
        self._heatmap_editor_perform(module, 'clear_samples')

    def heatmap_editor_all_samples(self, module):
        self._heatmap_editor_perform(module, 'all_samples')

    def heatmap_editor_all_groups(self, module):
        self._heatmap_editor_perform(module, 'all_groups')

    def heatmap_editor_from_barplot(self, module):
        self._heatmap_editor_perform(module, 'from_barplot')

    def heatmap_editor_set_slider_value(self, module, value):
        self._heatmap_editor_perform(module, 'set_slider_value', value)

    def heatmap_editor_select_sample(self, module, where, sample):
        self._heatmap_editor_perform(module, 'select_sample', where, sample)

    def heatmap_editor_select_sample(self, module, where, sample):
        self._heatmap_editor_perform(module, 'select_sample', where, sample)

    def prepare_import_dialog(self, module, filename, name, filetype):
        self._cli2srv('nv_prepare_import_dialog', module, [filename, name, filetype])

    def open_module(self, module, module2open):
        self._cli2srv('nv_open_module', module, [module2open, []])

    def reset(self):
        self._reset_session()
        self.session_id = 0
