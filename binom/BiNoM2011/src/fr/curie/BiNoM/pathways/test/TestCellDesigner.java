package fr.curie.BiNoM.pathways.test;

import org.sbml.x2001.ns.celldesigner.SbmlDocument;

import fr.curie.BiNoM.pathways.wrappers.CellDesigner;

public class TestCellDesigner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SbmlDocument cd = CellDesigner.loadCellDesigner("/bioinfo/users/ebonnet/testAND.xml");
		
		//System.out.println(cd.getSbml().getModel().getAnnotation().get;
		
	}

}
