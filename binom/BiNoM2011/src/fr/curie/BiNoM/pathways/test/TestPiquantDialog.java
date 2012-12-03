package fr.curie.BiNoM.pathways.test;

import javax.swing.JFrame;

import cytoscape.Cytoscape;

import fr.curie.BiNoM.cytoscape.analysis.OptimalCutSetAnalyzerDialog;
import fr.curie.BiNoM.cytoscape.analysis.PathConsistencyAnalyzerDialog;
import fr.curie.BiNoM.pathways.analysis.structure.DataPathConsistencyAnalyzer;

public class TestPiquantDialog {

		/**
		 * @param args
		 */
		public static void main(String[] args) {
			try {
				
				DataPathConsistencyAnalyzer dpc = new DataPathConsistencyAnalyzer();
				dpc.loadGraph("/bioinfo/users/ebonnet/Binom/tmp.xgmml");
				
				/*
				 * Graphical interface test
				 */

				JFrame window = new JFrame();
				PathConsistencyAnalyzerDialog dialog = new PathConsistencyAnalyzerDialog(window,"Path Influence Quantification analyzer",true);
				dialog.analyzer = dpc;
				dialog.fillTheData();
				dialog.setVisible(true);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
}
