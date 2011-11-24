package fr.curie.BiNoM.pathways.test;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.*;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;

public class testBioPAXManipulation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			String prefix = "c:/datas/binomtest/text/";
			String fname = prefix+"test.owl";
			
		    BioPAXToCytoscapeConverter b2c = new BioPAXToCytoscapeConverter();
            BioPAXToCytoscapeConverter.Graph gr = b2c.convert(BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION, fname, new BioPAXToCytoscapeConverter.Option());
            
            fr.curie.BiNoM.pathways.analysis.structure.Graph graph  = XGMML.convertXGMMLToGraph(gr.graphDocument);
            
            XGMML.saveToXGMML(graph, prefix+"test_before.xgmml");            
            graph = BiographUtils.LinearizeNetwork(graph);
            XGMML.saveToXGMML(graph, prefix+"test_after.xgmml");
            

		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
