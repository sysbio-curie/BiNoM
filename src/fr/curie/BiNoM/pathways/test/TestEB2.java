package fr.curie.BiNoM.pathways.test;

import java.io.BufferedReader;
import java.io.FileReader;

import org.sbml.x2001.ns.celldesigner.SbmlDocument;

import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;

public class TestEB2 {

	public static void main(String[] args) {

		//String fileName = "/Users/eric/wk/test_merge/wnc3.xml";
		String fileName = "/Users/eric/test.xml";
		String text = Utils.loadString(fileName);
		
		//generateRandomPrefix();
		//makePrefix(text);
		//text = addPrefixToIds(text);
		//this.cdFileString = text;
		
		SbmlDocument cd2 = CellDesigner.loadCellDesignerFromText(text);
		System.out.println("done.");
		
		// look for non-ascii characters
//		try {
//			BufferedReader bf = new BufferedReader(new FileReader(fileName));	
//			String line;
//			while ((line=bf.readLine()) != null) {
//				//line = line.trim();
//				for (int i=0; i<line.length(); i++) {
//					if ( (int) line.charAt(i) > 127)
//						System.out.println(line.charAt(i));
//				}
//			}
//			bf.close();
//			System.out.println("done.");
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
