package fr.curie.BiNoM.pathways.test;

import java.util.Vector;

import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.utils.BioPAXGraphQuery;
import fr.curie.BiNoM.pathways.utils.BioPAXGraphQueryEngine;
import fr.curie.BiNoM.pathways.utils.GraphXGMMLParser;

public class TestBioPAXQueryEngineEB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			BioPAXGraphQueryEngine beng = new BioPAXGraphQueryEngine();
			GraphXGMMLParser gp2 = new GraphXGMMLParser();
			gp2.parse("/bioinfo/users/ebonnet/Binom/biopax/Apoptosis3.xgmml");
			beng.setDatabase(gp2.graph);
			
//			for (Node n : beng.database.Nodes)
//				System.out.println(n.Id);
//			System.exit(1);
			
		  	Vector names = new Vector();
        	Vector xrefs = new Vector();
        	names.add("SMAC@cytosol");
        	names.add("Oligomerization_of_BAK_at_the_mitochondrial_membrane");
        	xrefs.add(new Vector());
        	xrefs.add(new Vector());
        	
        	// Oligomerization_of_BAK_at_the_mitochondrial_membrane
        	BioPAXGraphQuery query = BioPAXGraphQuery.convertListOfNamesToQuery(names, xrefs);
        	beng.doQuery(query, BioPAXGraphQuery.ADD_COMPLEXES_EXPAND);
        	beng.query.input = beng.query.result;
        	beng.doQuery(query, BioPAXGraphQuery.ADD_ALL_REACTIONS);
        	beng.query.input = beng.query.result;
        	beng.doQuery(query, BioPAXGraphQuery.ADD_SPECIES);
        	beng.query.input = beng.query.result;
        	beng.doQuery(query,BioPAXGraphQuery.ADD_PUBLICATIONS);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
