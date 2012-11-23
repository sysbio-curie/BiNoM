package fr.curie.BiNoM.pathways.test;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;

import fr.curie.BiNoM.cytoscape.analysis.OptimalCutSetAnalyzer;
import fr.curie.BiNoM.cytoscape.analysis.OptimalCutSetAnalyzerDialog;
import fr.curie.BiNoM.cytoscape.analysis.OptimalCutSetReportDialog;
import fr.curie.BiNoM.cytoscape.analysis.PathConsistencyAnalyzerReportDialog;
import fr.curie.BiNoM.pathways.analysis.structure.Attribute;
import fr.curie.BiNoM.pathways.analysis.structure.DataPathConsistencyAnalyzer;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.analysis.structure.OptimalCombinationAnalyzer;
import fr.curie.BiNoM.pathways.utils.GraphUtils;



public class TestOPtCutSet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//noGraphTest();
		graphTest();
		//OptimalCombinationAnalyzer oca = new OptimalCombinationAnalyzer();
		//oca.testSeedSet();
	}
	

	public static void graphTest() {
		try {
			
			DataPathConsistencyAnalyzer dpc = new DataPathConsistencyAnalyzer();

			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/signal.xgmml");
			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/toynet2.xgmml");
			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/signal_with_exception.xgmml");
			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/signal_node0.xgmml");
			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/egfr_linearized.xgmml");
			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/test_laurence/24112011.xgmml");

			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/merged_net_0pc.xgmml");
			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/merged_net_30p.xgmml");
			dpc.loadGraph("/bioinfo/users/ebonnet/Binom/signal4.xgmml");
			
			/*
			 * Graphical interface test
			 */

			JFrame window = new JFrame();
			OptimalCutSetAnalyzerDialog dialog = new OptimalCutSetAnalyzerDialog(window,"Optimal Combinations of Intervention Strategies for Network Analysis",true);
			dialog.analyzer = dpc;
			dialog.fillTheData();
			dialog.setVisible(true);

			if (dialog.result>0) {
				OptimalCutSetReportDialog reportForm = new OptimalCutSetReportDialog(dpc);
				reportForm.pop();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void noGraphTest() {
		try {
			DataPathConsistencyAnalyzer dpc = new DataPathConsistencyAnalyzer();

			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/toynet2.xgmml");
			dpc.loadGraph("/bioinfo/users/ebonnet/Binom/egfr_linearized.xgmml");

			/*
			 * Non graphical test
			 */
			ArrayList<Node> testSource = new ArrayList<Node>();
			ArrayList<Node> testTarget = new ArrayList <Node>();
			ArrayList<Node> testSide = new ArrayList<Node>();

			/* toynet2 test 
			 */
//			testSource.add(dpc.graph.getNode("I1"));
//			testSource.add(dpc.graph.getNode("I2"));
//			testTarget.add(dpc.graph.getNode("O1"));
//			testTarget.add(dpc.graph.getNode("O2"));

			/* egfr linearized network test settings
			 * Source nodes: ar bir btc csrc egf epr erbb1 erbb2 erbb3 erbb4 hbegf mkp nrg1a nrg1b nrg2a nrg2b nrg3 nrg4 pdk1 pp2a pp2b pten ship2 tgfa 
			 * Target nodes: elk1
			 */

			testSource.add(dpc.graph.getNode("ar"));
			testSource.add(dpc.graph.getNode("bir"));
			testSource.add(dpc.graph.getNode("btc"));
			testSource.add(dpc.graph.getNode("csrc"));
			testSource.add(dpc.graph.getNode("egf"));
			testSource.add(dpc.graph.getNode("epr"));
			testSource.add(dpc.graph.getNode("erbb1"));
			testSource.add(dpc.graph.getNode("erbb2"));
			testSource.add(dpc.graph.getNode("erbb3"));
			testSource.add(dpc.graph.getNode("erbb4"));
			testSource.add(dpc.graph.getNode("hbegf"));
			testSource.add(dpc.graph.getNode("mkp"));
			testSource.add(dpc.graph.getNode("nrg1a"));
			testSource.add(dpc.graph.getNode("nrg1b"));
			testSource.add(dpc.graph.getNode("nrg2a"));
			testSource.add(dpc.graph.getNode("nrg2b"));
			testSource.add(dpc.graph.getNode("nrg3"));
			testSource.add(dpc.graph.getNode("nrg4"));
			testSource.add(dpc.graph.getNode("pdk1"));
			testSource.add(dpc.graph.getNode("pp2a"));
			testSource.add(dpc.graph.getNode("pp2b"));
			testSource.add(dpc.graph.getNode("pten"));
			testSource.add(dpc.graph.getNode("ship2"));
			testSource.add(dpc.graph.getNode("tgfa"));
			testTarget.add(dpc.graph.getNode("elk1"));
			//testTarget.add(dpc.graph.getNode("erbb11"));
			//testTarget.add(dpc.graph.getNode("pak1"));

			for (Node n : testSource)
				dpc.sourceNodes.add(n);

			for (Node n : testTarget)
				dpc.targetNodes.add(n);

			for (Node n : testSide)
				dpc.sideNodes.add(n);

			//	dpc.searchPathMode = dpc.ALL_PATHS;
			dpc.searchPathMode = dpc.SHORTEST_PATHS;
			//dpc.ocsSearch = dpc.OCS_PARTIAL;
			//dpc.ocsSearch = dpc.OCS_BERGE;
			//dpc.ocsanaSearch();
			//System.out.println(dpc.optCutSetReport);

			/*
			 * data preprocessing
			 * create path matrix
			 */
			dpc.ocsanaScore();
			OptimalCombinationAnalyzer oca = new OptimalCombinationAnalyzer();
			oca.pathMatrix = dpc.pathMatrix;
			oca.pathMatrixNbCol = dpc.pathMatrixNbCol;
			oca.pathMatrixNbRow = dpc.pathMatrixNbRow;
			oca.pathMatrixNodeList = dpc.pathMatrixNodeList;
			oca.omegaScoreList = dpc.omegaScores;
			
			oca.initOrderedNodesList();
			
			oca.checkRows();
			oca.searchHitSetSizeOne();

			// convert path matrix to BitSet objects; both rows and columns
			oca.convertPathMatrixColToBinary();
			oca.convertPathMatrixRowToBinary();

			oca.maxNbHitSet = (long)50e6;
			oca.maxHitSetSize = 10;
			
			long tic = System.currentTimeMillis();
			oca.searchHitSetSeed();
			//oca.searchHitSetPartial();
			//oca.mainBerge(false);
			//long toc = System.currentTimeMillis() - tic;
			//System.out.println("global timing: "+toc);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
