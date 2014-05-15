package fr.curie.BiNoM.pathways.test;

import org.sbml.x2001.ns.celldesigner.SbmlDocument;

import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;

public class TestEB2 {

	public static void main(String[] args) {

		String fileName = "/Users/eric/wk/test_merge/wnc3.xml";
		String text = Utils.loadString(fileName);
		//generateRandomPrefix();
		//makePrefix(text);
		//text = addPrefixToIds(text);
		//this.cdFileString = text;
		SbmlDocument cd2 = CellDesigner.loadCellDesignerFromText(text);
		System.out.println("done.");
		
		
	}

}
