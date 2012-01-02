package fr.curie.BiNoM.pathways.test;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;

import fr.curie.BiNoM.cytoscape.analysis.OptimalCutSetAnalyzer;
import fr.curie.BiNoM.cytoscape.analysis.OptimalCutSetAnalyzerDialog;
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
		// TODO Auto-generated method stub
		try {
			
			DataPathConsistencyAnalyzer dpc = new DataPathConsistencyAnalyzer();
			
			dpc.loadGraph("/bioinfo/users/ebonnet/Binom/signal.xgmml");
			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/signal_with_exception.xgmml");
			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/signal_node0.xgmml");
			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/egfr_linearized.xgmml");
			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/test_laurence/24112011.xgmml");
			
			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/merged_net_0pc.xgmml");
			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/merged_net_30p.xgmml");
			
			/*
			 * 
			 * Graphical interface test
			 */
			
//			JFrame window = new JFrame();
//			OptimalCutSetAnalyzerDialog dialog = new OptimalCutSetAnalyzerDialog(window,"Optimal Combinations of Intervention Strategies for Network Analysis",true);
//			dialog.analyzer = dpc;
//			dialog.fillTheData();
//			dialog.setVisible(true);
//			
//			if (dialog.result>0) {
//				String report = dpc.optCutSetReport.toString();
//				PathConsistencyAnalyzerReportDialog reportForm = new PathConsistencyAnalyzerReportDialog(report);
//				reportForm.pop();
//			}

			
			/*
			 * 
			 * Non graphical test
			 */
			ArrayList<Node> testSource = new ArrayList<Node>();
			ArrayList<Node> testTarget = new ArrayList <Node>();
			ArrayList<Node> testSide = new ArrayList<Node>();
			
			// toynet
			testSource.add(dpc.graph.getNode("I1"));
			testSource.add(dpc.graph.getNode("I2"));
			
			testTarget.add(dpc.graph.getNode("O1"));
			//testTarget.add(dpc.graph.getNode("O2"));
			
			//testSide.add(dpc.graph.getNode("O2"));
			
			// merged 30pc no selection 
//			testSource.add(dpc.graph.getNode("XN000017011"));
//			testSource.add(dpc.graph.getNode("MO000057270"));
//			testSource.add(dpc.graph.getNode("MO000017272"));
//			testSource.add(dpc.graph.getNode("MO000082277"));
//			testSource.add(dpc.graph.getNode("MO000056897"));
//			
//			testTarget.add(dpc.graph.getNode("MO000079060"));
//			testTarget.add(dpc.graph.getNode("MO000082708"));
//			testTarget.add(dpc.graph.getNode("MO000083592"));
//			testTarget.add(dpc.graph.getNode("MO000058770"));
//			testTarget.add(dpc.graph.getNode("MO000079681"));
			
			
			//merged 0 pc test
//			testSource.add(dpc.graph.getNode("XN000017011"));
//			testSource.add(dpc.graph.getNode("MO000057270"));
//			testSource.add(dpc.graph.getNode("MO000017272"));
//			testSource.add(dpc.graph.getNode("MO000082277"));
//			testSource.add(dpc.graph.getNode("MO000056897"));
//			testSource.add(dpc.graph.getNode("MO000063085"));
//			
//			testTarget.add(dpc.graph.getNode("MO000079060"));
//			testTarget.add(dpc.graph.getNode("MO000082708"));
//			testTarget.add(dpc.graph.getNode("MO000083592"));
//			testTarget.add(dpc.graph.getNode("MO000058770"));
//			testTarget.add(dpc.graph.getNode("MO000079681"));
//			
			for (Node n : testSource)
				dpc.sourceNodes.add(n);
			
			for (Node n : testTarget)
				dpc.targetNodes.add(n);
			
			for (Node n : testSide)
				dpc.sideNodes.add(n);
			
			dpc.searchPathMode = dpc.SHORTEST_PATHS;
			dpc.maxSetSize = 20;
			dpc.maxSetNb = (long) 1e+6;
			
			dpc.ocsanaScore();
			//dpc.ocsanaOptimalCutSet();
			//System.out.println(dpc.optCutSetReport);
			
			OptimalCombinationAnalyzer oca = new OptimalCombinationAnalyzer();
			oca.pathMatrix = dpc.pathMatrix;
			oca.pathMatrixNbCol = dpc.pathMatrixNbCol;
			oca.pathMatrixNbRow = dpc.pathMatrixNbRow;
			oca.pathMatrixNodeList = dpc.pathMatrixNodeList;
			//oca.printPathMatrix();
			oca.checkRows();
			//oca.printPathMatrix();
			oca.searchHitSetSizeOne();
			//oca.printPathMatrix();
			
			for (int i=0;i<oca.hitSetSizeOne.size();i++)
				System.out.print(oca.hitSetSizeOne.get(i)+" ");
			System.out.println();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
