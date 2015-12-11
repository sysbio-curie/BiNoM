from curie.navicell import NaviCell, Options

# set the minimal options (url of the web server and map used) and create a NaviCell object
options = Options()

# NaviCell production server
#options.proxy_url = 'https://navicell.curie.fr/cgi-bin/nv_proxy.php'
#options.map_url = 'https://navicell.curie.fr/navicell/maps/cellcycle/master/index.php'

# Local NaviCell server
options.proxy_url = 'http://localhost/nv_proxy.php'
options.map_url = 'http://localhost/navicell/maps/cellcycle/master/index.php'

nv = NaviCell(options)

nv.launchBrowser()

# load expression data and send it to the map
filename = "DU145_data.txt"
dat = nv.makeDataFromFile(filename)
nv.importDatatables(dat, "DU145", "mRNA expression data")

# select a datatable, select a sample and produce a heatmap
nv.heatmapEditorSelectDatatable('','0','DU145')
nv.heatmapEditorSelectSample('','0','data')
nv.heatmapEditorApply('')
