package fr.curie.BiNoM.pathways.test;

import java.util.ArrayList;

import fr.curie.BiNoM.pathways.utils.MergingMapsProcessor;

public class TestMergeMapsACSN {
	
	public static void main(String[] args) {
		
		MergingMapsProcessor mm = new MergingMapsProcessor();
		mm.runScript();
		
//		try {
//			mm.loadConfigFile("/bioinfo/users/ebonnet/test/test.txt");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		mm.mergeAll("/bioinfo/users/ebonnet/out.xml");
		
//		mm.addMap("/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-3.xml",50,15);
//		mm.addMap("/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-4.xml",650, 15);
//		mm.setMapSize(1300, 500);
//		mm.mergeAll();
//		mm.saveMap("/bioinfo/users/ebonnet/merged_maps.xml");

//		mm.addMap("/bioinfo/users/ebonnet/test2.xml",50,15);
//		mm.addMap("/bioinfo/users/ebonnet/test1.xml",650, 15);
//		mm.setMapSize(1300, 500);
//		mm.mergeAll();
//		mm.saveMap("/bioinfo/users/ebonnet/merged_maps.xml");
		
		
//		mm.addMap("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/AKT/AKT_cytosol.xml",140,80);
//		mm.addMap("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/sonic_hedgehog/SHH_cytosol.xml",5706,80);
//		mm.addMap("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/sonic_hedgehog/SHH_non_canon.xml",5706,2590);
//		mm.addMap("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/sonic_hedgehog/SHH_Nucleus.xml",5706,5100);
//		mm.addMap("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/WNT/Wnt_cytosol.xml",2696,80);
//		mm.addMap("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/AKT/AKT_nucleus.xml",140,2610);
//		mm.setMapSize(8192, 7720);
//		mm.mergeAll();
//		mm.saveMap("/bioinfo/users/ebonnet/merged_maps.xml");

		
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/emtcellmotility_v1_names.xml",50,50);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/cellcycle_v1_names.xml",6500,50);
//		mm.setMapSize(14000, 7000);
//		mm.mergeAll();
//		mm.saveMap("/bioinfo/users/ebonnet/test.xml");

//		mm.addMap("/bioinfo/users/ebonnet/layers.xml",10,10);
//		mm.addMap("/bioinfo/users/ebonnet/layers.xml",650,10);
//		mm.setMapSize(1300, 500);
//		mm.mergeAll();
//		mm.saveMap("/bioinfo/users/ebonnet/test.xml");
		
		// all ACSN maps
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/dnarepair_v2_names.xml",50,50);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_PI3K_AKT_MTOR02.xml",10290,50);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_HEDGEHOG03.xml",50,9316);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_WNT_CANONICAL02.xml",10290,9316);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_WNT_NON_CANONICAL02.xml",50,18582);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_WNT_NON_CANONICAL04.xml",10290,18582);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_WNT_NON_CANONICAL03.xml",50,27848);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_HEDGEHOG02.xml",10290,27848);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_WNT_CANONICAL03.xml",50,37114);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_WNT_CANONICAL04.xml",10290,37114);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_HEDGEHOG01.xml",50,46380);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_WNT_CANONICAL01.xml",10290,46380);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_PI3K_AKT_MTOR01.xml",50,55646);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_WNT_NON_CANONICAL01.xml",10290,55646);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/cellcycle_v1_names.xml",50,64912);
////		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/emtcellmotility_v1_names.xml",10290,64912);
//		mm.setMapSize(21000, 75000);
//		mm.mergeAll();
//		mm.saveMap("/bioinfo/users/ebonnet/big.xml");

		// Survival maps only
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_HEDGEHOG01.xml",9254,3026);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_HEDGEHOG02.xml",9254,5526);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_HEDGEHOG03.xml",9254,8026);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_MAPK01.xml",22,3222);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_PI3K_AKT_MTOR01.xml",1554,5526);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_PI3K_AKT_MTOR02.xml",1554,8026);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_WNT_CANONICAL01.xml",3754,26);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_WNT_CANONICAL02.xml",6754,526);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_WNT_CANONICAL03.xml",4254,3026);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_WNT_CANONICAL04.xml",6754,3026);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_WNT_NON_CANONICAL01.xml",4054,8026);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_WNT_NON_CANONICAL02.xml",6754,8026);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_WNT_NON_CANONICAL03.xml",6754,5526);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v2_names/survival_WNT_NON_CANONICAL04.xml",4254,5526);
//		mm.setMapSize(11776,10752);
//		mm.mergeAll();
//		mm.saveMap("/bioinfo/users/ebonnet/merged_surv.xml");
		
//		mm.addMap("/bioinfo/users/ebonnet/test/survival_HEDGEHOG01.xml",9254,3026);
//		mm.addMap("/bioinfo/users/ebonnet/test/survival_HEDGEHOG02.xml",9254,5526);
//		mm.addMap("/bioinfo/users/ebonnet/test/survival_HEDGEHOG03.xml",9254,8026);
//		mm.setMapSize(11776,10752);
//		mm.mergeAll();
//		mm.saveMap("/bioinfo/users/ebonnet/merged_surv.xml");
		
//		mm.addMap("/bioinfo/users/ebonnet/test_gate/MAP1_AND.xml", 10, 10);
//		mm.addMap("/bioinfo/users/ebonnet/test_gate/MAP2_AND.xml",650,10);
//		mm.setMapSize(1500,1500);
//		mm.mergeAll();
//		mm.saveMap("/bioinfo/users/ebonnet/test.xml");

	}

}
