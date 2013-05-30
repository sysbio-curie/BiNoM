package fr.curie.BiNoM.pathways.test;

import java.io.File;

import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.utils.BioPAXGraphMapper;
import fr.curie.BiNoM.pathways.utils.BioPAXGraphMappingService;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

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
			//String BioPAXFileName = "/bioinfo/users/ebonnet/Binom/biopax/Apoptosis3.owl";
			String BioPAXFileName = "c:/datas/binomtest/biopaxtest/test.owl";
			//String BioPAXFileName = "c:/datas/binomtest/biopaxtest/cellcycle_master.owl";
			//String BioPAXFileName = "C:/Datas/acsn/acsn_only/acsn_src/acsn_master.owl";
			//String BioPAXFileName = "C:/Datas/Reactome/reactome_human.owl";
			BioPAXGraphMappingService bgms = new BioPAXGraphMappingService();
			BioPAX biopax = new BioPAX();
			biopax.loadBioPAX(BioPAXFileName);

			Graph graph = bgms.mapBioPAXToGraph(biopax);
			graph.saveAsCytoscapeXGMML(BioPAXFileName+".xgmml");
			//XGMML.saveToXGMML(graph, BioPAXFileName+".xgmml");
			
			/*for(int i=0;i<graph.Nodes.size();i++)for(int j=i+1;j<graph.Nodes.size();j++){
				if(graph.Nodes.get(i).Id.equals(graph.Nodes.get(j).Id))
					System.out.println("CHECKING GRAPH: double name "+graph.Nodes.get(j).Id);
			}*/

			//BioPAXGraphMapper mapper = new BioPAXGraphMapper();
			//mapper.biopax = biopax;
			//mapper.map();
			//mapper.graph.saveAsCytoscapeXGMML(BioPAXFileName+".xgmml");
			//return mapper.graph;

			//Graph gr = graph;
			//gr.saveAsCytoscapeXGMML(IndexFileName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
