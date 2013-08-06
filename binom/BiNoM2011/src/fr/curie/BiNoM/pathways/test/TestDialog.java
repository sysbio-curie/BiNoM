package fr.curie.BiNoM.pathways.test;

import java.util.ArrayList;

import javax.swing.JFrame;

import fr.curie.BiNoM.cytoscape.analysis.OptimalCutSetGenesDialog;
import fr.curie.BiNoM.cytoscape.analysis.OptimalCutSetVisuDialog;
import fr.curie.BiNoM.cytoscape.analysis.PathConsistencyAnalyzerReportDialog;
import fr.curie.BiNoM.cytoscape.celldesigner.ProduceNaviCellMapFilesDialog;
import fr.curie.BiNoM.pathways.analysis.structure.DataPathConsistencyAnalyzer;
import fr.curie.BiNoM.pathways.analysis.structure.Node;

public class TestDialog {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			//OptimalCutSetGenesDialog dialog = new OptimalCutSetGenesDialog(new JFrame(), "Title", true);
			//dialog.setVisible(true);
			
//			PathConsistencyAnalyzerReportDialog dialog = new PathConsistencyAnalyzerReportDialog("test bla bla bla bla........////->");
//			dialog.pop();
			
//			OptimalCutSetVisuDialog dialog = new OptimalCutSetVisuDialog(new JFrame(), "test", true);
//			dialog.setVisible(true);
			
			ProduceNaviCellMapFilesDialog dialog = new ProduceNaviCellMapFilesDialog();
			dialog.setVisible(true);
						
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
