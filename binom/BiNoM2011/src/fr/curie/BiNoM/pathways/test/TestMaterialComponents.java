package fr.curie.BiNoM.pathways.test;

import java.util.Vector;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.analysis.structure.StructureAnalysisUtils;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class TestMaterialComponents {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String file = "/bioinfo/users/ebonnet/Binom/rb_pathway.xgmml";
		
		try {
			GraphDocument gr = XGMML.loadFromXMGML(file);
			
			Vector v = StructureAnalysisUtils.getMaterialComponents(gr);
			
			System.out.println(v.size());
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
