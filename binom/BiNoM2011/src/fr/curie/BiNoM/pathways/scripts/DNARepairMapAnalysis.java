package fr.curie.BiNoM.pathways.scripts;

import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.utils.SetOverlapAnalysis;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class DNARepairMapAnalysis {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			SetOverlapAnalysis so = new SetOverlapAnalysis();
			String folder = "C:/Datas/DNARepairAnalysis/ver2/";
			String prefix = "paths_ber";
			String mapFileName = "dnarepair_master_08112013_nophenotypes.xgmml";
			int order = 3;
			
			// Process the paths file (extracted from the OCSANA report)
			//so.createGMTFromOCSANAOutput(folder+"ocsana_report");

			// Extract regulators along the paths as gmt file with "reg" suffix
			so.LoadSetsFromGMT(folder+prefix+".gmt");
			String typesOfRegulations[] = new String[]{"CATALYSIS","TRIGGER","MODULATION","PHYSICAL_STIMULATION","UNKNOWN_CATALYSIS"};
			Graph graph = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(folder+mapFileName));
			so.makeGMTOfReactionRegulators(folder+prefix+"_reg"+order, graph, typesOfRegulations, order);
			
			// Now find all hit sets
			so.LoadSetsFromGMT(folder+prefix+"_reg"+order+".gmt");
			so.findMinimalHittingSet(3, folder+prefix+"_reg"+	order);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
