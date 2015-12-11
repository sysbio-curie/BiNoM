import fr.curie.jnavicell.NaviCell;

public class Test {
    public static void main(String[] args) {

            NaviCell n = new NaviCell();

            // test localhost
            //n.setMapUrl("http://localhost/navicell/maps/cellcycle/master/index.php");
            //n.setProxyUrl("http://localhost/nv_proxy.php");

            n.launchBrowser();

            n.importData("", "DU145_data.txt",  "mRNA Expression data", "DU145");
            
            // select heatmap for visualization
            n.drawingConfigSelectHeatmap("", true);
            n.drawingConfigApply("");

            // select sample and datatable 
            n.heatmapEditorSelectSample("", 0,"data");
            n.heatmapEditorSelectDatatable("", 0, "DU145");

            // visualize the results
            n.heatmapEditorApply("");
    }
}
