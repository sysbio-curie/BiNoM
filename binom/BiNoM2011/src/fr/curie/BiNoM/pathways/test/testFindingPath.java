package fr.curie.BiNoM.pathways.test;

import java.util.*;

import cytoscape.Cytoscape;

import fr.curie.BiNoM.cytoscape.analysis.OptimalCutSetAnalyzer;
import fr.curie.BiNoM.cytoscape.analysis.OptimalCutSetAnalyzerDialog;
import fr.curie.BiNoM.cytoscape.analysis.OptimalCutSetReportDialog;
import fr.curie.BiNoM.cytoscape.analysis.PathConsistencyAnalyzerReportDialog;
import fr.curie.BiNoM.cytoscape.analysis.PathConsistencyAnalyzerPathDialog;
import fr.curie.BiNoM.cytoscape.analysis.PathConsistencyAnalyzerDialog;
import fr.curie.BiNoM.pathways.analysis.structure.Attribute;
import fr.curie.BiNoM.pathways.analysis.structure.DataPathConsistencyAnalyzer;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.analysis.structure.OptimalCombinationAnalyzer;
import fr.curie.BiNoM.pathways.utils.GraphUtils;


public class testFindingPath {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{

			DataPathConsistencyAnalyzer dpc = new DataPathConsistencyAnalyzer();
			dpc.loadGraph("c:/datas/binomtest/emt2-piquant.xgmml");
			//dpc.loadGraph("c:/datas/binomtest/snai2_zeb1.xgmml");

			Vector<Node> testSource = new Vector<Node>();
			Vector<Node> testTarget = new Vector<Node>();
			
			testSource.add(dpc.graph.getNode("Snai2"));
			testTarget.add(dpc.graph.getNode("Zeb1"));
			
			dpc.searchPathMode = dpc.SUBOPTIMAL_SHORTEST_PATHS;
			dpc.pathwayNodes = testTarget;
			dpc.EnrichedNodes = testSource;
	
			
			dpc.findPaths();

			/*PathConsistencyAnalyzerDialog dialog = new PathConsistencyAnalyzerDialog(Cytoscape.getDesktop(), "", false);
			dialog.analyzer = dpc;
			dialog.fillTheData();
			
    		if(dialog.result>0){
    			PathConsistencyAnalyzerPathDialog dialogPath = new PathConsistencyAnalyzerPathDialog(Cytoscape.getDesktop(),"Path Browser",false);
        		dialogPath.analyzer = dialog.analyzer;
        		dialogPath.fillTheData();
        		dialogPath.setVisible(true);
    		}
			
		
			//System.out.println("Finished");
			
			//PathConsistencyAnalyzerPathDialog dialog1 = new PathConsistencyAnalyzerPathDialog(null, "test bla bla bla bla........////->", false);/
			*/
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
