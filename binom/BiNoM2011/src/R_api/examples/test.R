library(RNaviCell)

navicell <- NaviCell()

navicell$proxy_url <- "http://localhost/nv_proxy.php"
navicell$map_url <- "http://localhost/navicell/maps/cellcycle/master/index.php"

navicell$launchBrowser()

mat <- navicell$readDatatable('DU145_data.txt')

navicell$importDatatable("mRNA expression data", "DU145", mat)

navicell$heatmapEditorSelectSample('0','data')
navicell$heatmapEditorSelectDatatable('0','DU145')
navicell$heatmapEditorApply()



