package fr.curie.BiNoM.pathways.test;

import fr.curie.BiNoM.cytoscape.celldesigner.colorCellDesignerProteinsDialog;
import fr.curie.BiNoM.pathways.CellDesignerColorProteins;

public class TestColorCD {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String prefix = "/Users/eric/wk/color_CD/";
		String CD_file = prefix + "cellcycle_master.xml";
		String features_file = prefix + "LNCAP_data.txt";

		CellDesignerColorProteins.colorProteins(CD_file,  features_file, "-0.7","0.5");
//		colorCellDesignerProteinsDialog.getInstance().raise(null);
	}

}
