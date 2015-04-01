from curie.navicell import NaviCell, Options

# set the minimal options (url of the web server and map used) and create a NaviCell object
options = Options()
options.proxy_url = 'https://navicell.curie.fr/cgi-bin/nv_proxy.php'
options.map_url = 'https://navicell.curie.fr/navicell/maps/cellcycle/master/index.php'

nv = NaviCell(options)

#-----------------------------------------------------------------
# Here is the set of commands described in the Python API tutorial
#------------------------------------------------------------------

# open the default browser and points it to the default map
nv.launchBrowser()

# set zoom to level 4 and back to level 1
nv.setZoom('', 4)
nv.setZoom('', 1)

# load expression data and send it to the map
filename = "data/ovca_expression.txt"
dat = nv.makeDataFromFile(filename)
nv.importDatatables(dat, "ovca-exp", "mRNA expression data")

# load annotation file from a file located on a remote server
nv.sampleAnnotationImport("https://navicell.curie.fr/data/ovca_sampleinfo.txt")
nv.sampleAnnotationSelectAnnotation("", "IntrinsicExprClassJCI")
nv.sampleAnnotationApply("")

# select a datatable, select a group of samples and produce a heatmap
nv.heatmapEditorSelectDatatable('', 0, 'ovca-exp')
nv.heatmapEditorSelectSample('', 0, 'IntrinsicExprClassJCI: Proliferative')
nv.heatmapEditorApply('')

# set the color gradient parameters and apply the settings
nv.datatableConfigSwitchGroupTab('', 'ovca-exp', 'color')
nv.datatableConfigSetStepCount('', 'ovca-exp', 'color', NaviCell.TABNAME_GROUPS, 2)
nv.datatableConfigSetColorAt('', 'ovca-exp', 'color', NaviCell.TABNAME_GROUPS, 1, 'FFFFFF')
nv.datatableConfigSetValueAt('', 'ovca-exp', 'color', NaviCell.TABNAME_GROUPS, 0, -0.2)
nv.datatableConfigSetValueAt('', 'ovca-exp', 'color', NaviCell.TABNAME_GROUPS, 1, 0.0)
nv.datatableConfigSetValueAt('', 'ovca-exp', 'color', NaviCell.TABNAME_GROUPS, 2, 0.2)
nv.datatableConfigApply('', 'ovca-exp', NaviCell.CONFIG_COLOR)



