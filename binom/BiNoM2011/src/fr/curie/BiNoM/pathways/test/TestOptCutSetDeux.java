package fr.curie.BiNoM.pathways.test;

import java.util.ArrayList;

import javax.swing.JFrame;

import fr.curie.BiNoM.cytoscape.analysis.OptimalCutSetGenesDialog;
import fr.curie.BiNoM.cytoscape.analysis.PathConsistencyAnalyzerReportDialog;
import fr.curie.BiNoM.pathways.analysis.structure.DataPathConsistencyAnalyzer;
import fr.curie.BiNoM.pathways.analysis.structure.Node;

public class TestOptCutSetDeux {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			/*
			 * 
			 * Graphical interface test
			 */
			//OptimalCutSetGenesDialog dialog = new OptimalCutSetGenesDialog(new JFrame(), "Title", true);
			//dialog.setVisible(true);
			
			
			PathConsistencyAnalyzerReportDialog dialog = new PathConsistencyAnalyzerReportDialog("test bla bla bla bla........////->");
			dialog.pop();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
