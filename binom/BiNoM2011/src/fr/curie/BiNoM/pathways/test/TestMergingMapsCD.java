package fr.curie.BiNoM.pathways.test;

import java.util.Vector;

import org.sbml.x2001.ns.celldesigner.SbmlDocument;

import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;

public class TestMergingMapsCD {

	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//String fileName = "/bioinfo/users/ebonnet/Binom/mergeMaps/a1.xml";
		String fileName = "/bioinfo/users/ebonnet/test.xml";
		//String fileName = "/bioinfo/users/ebonnet/Binom/mergeMaps/mTOR_scratch_v31.xml";
		SbmlDocument cd = CellDesigner.loadCellDesigner(fileName);
		String text = cd.toString();
		String prefix  = "rz_";
		
		Vector<String> ids = Utils.extractAllStringBetween(text, "id=\"", "\"");
		for(int i=0;i<ids.size();i++)
			if (!ids.get(i).equals("default") && !ids.get(i).startsWith("rb_")) {
				System.out.println(i+" => "+ids.get(i));
				text = Utils.replaceString(text, "\""+ids.get(i)+"\"", "\""+prefix+""+ids.get(i)+"\"");
				text = Utils.replaceString(text, ">"+ids.get(i)+"<", ">"+prefix+""+ids.get(i)+"<");
			}
		
		SbmlDocument res = CellDesigner.loadCellDesignerFromText(text);
		
		CellDesigner.saveCellDesigner(res, "/bioinfo/users/ebonnet/res.xml");
		
	}

}
