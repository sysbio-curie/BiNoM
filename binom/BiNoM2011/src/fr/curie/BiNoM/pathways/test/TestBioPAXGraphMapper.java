package fr.curie.BiNoM.pathways.test;

import java.io.File;

import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.utils.BioPAXGraphMapper;
import fr.curie.BiNoM.pathways.utils.BioPAXGraphMappingService;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;

public class TestBioPAXGraphMapper {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		//String fn = "/bioinfo/users/ebonnet/Binom/biopax/M-Phase-L3.owl";
		//String fn = "/bioinfo/users/ebonnet/Binom/biopax/Apoptosis3.owl";
		
		
		try {
			
			//String BioPAXFileName = "/bioinfo/users/ebonnet/Binom/biopax/M-Phase-L3.owl";
			String BioPAXFileName = "/bioinfo/users/ebonnet/Binom/biopax/Apoptosis3.owl";
			BioPAXGraphMappingService bgms = new BioPAXGraphMappingService();
			BioPAX biopax = new BioPAX();
			biopax.loadBioPAX(BioPAXFileName);

			Graph graph = bgms.mapBioPAXToGraph(biopax);

			BioPAXGraphMapper mapper = new BioPAXGraphMapper();
			mapper.biopax = biopax;
			mapper.map();
			//mapper.graph.saveAsCytoscapeXGMML("/bioinfo/users/ebonnet/test.xgmml");
			//return mapper.graph;

			//Graph gr = graph;
			//gr.saveAsCytoscapeXGMML(IndexFileName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
