package fr.curie.BiNoM.pathways.test;

import java.util.ArrayList;

import fr.curie.BiNoM.pathways.utils.MergingMapsProcessor;

public class TestMergeMapsACSN {
	
	public static void main(String[] args) {
		
		MergingMapsProcessor mm = new MergingMapsProcessor();
		
//		mm.addMap("/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-3.xml",50,15);
//		mm.addMap("/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-4.xml",650, 15);
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
		
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/dnarepair_v2.xml",10,10);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/dnarepair_v2.xml",11000,10);
//		mm.setMapSize(22000, 12000);
//		mm.mergeAll();
//		mm.saveMap("/bioinfo/users/ebonnet/test.xml");
		
		// all ACSN maps
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_canonical/Wnt_Nucleus_version_26.xml",50,50);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_canonical/wnt_cytosol_canonical_version_33.xml",50,2600);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_canonical/wnt_continued_version_13.xml",50,5650);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_canonical/wnt_nucleus_continued_version_04.xml",50,8200);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_non-canonical/wnt_cytosol_non_canonical_version_27.xml",50,10750);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_non-canonical/wnt_crosstalk_version_07.xml",50,13500);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_non-canonical/wnt_cytosol_non_canonical_continued_version_14.xml",50,16050);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_non-canonical/wnt_nucleus_non_canonical_version_06.xml",50,18600);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/sonic_hedgehog/hedgehog_nucleus_version_01.xml",50,21150);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/sonic_hedgehog/hedgehog_cytosol_non_canonical_version_04.xml",50,23700);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/sonic_hedgehog/hedgehog_cytosol_version_01.xml",50,26250);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/AKT/pi3k_akt_mtor_nucleus_version_02.xml",50,28800);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/AKT/pi3K_akt_mtor_cytosol_version_33.xml",50,31350);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/rbe2f_v1.xml",50,33900);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/dnarepair_v2.xml",50,39070);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/mapk_v1.xml",50,48624);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/apoptosis_v1.xml",50,50978);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/emt_v1.xml",50,64068);
		mm.setMapSize(19000, 71000);
		mm.mergeAll();
		mm.saveMap("/bioinfo/users/ebonnet/big.xml");

		// Survival maps only
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_canonical/Wnt_Nucleus_version_26.xml",50,50);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_canonical/wnt_cytosol_canonical_version_33.xml",50,2600);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_canonical/wnt_continued_version_13.xml",50,5650);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_canonical/wnt_nucleus_continued_version_04.xml",50,8200);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_non-canonical/wnt_cytosol_non_canonical_version_27.xml",50,10750);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_non-canonical/wnt_crosstalk_version_07.xml",50,13500);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_non-canonical/wnt_cytosol_non_canonical_continued_version_14.xml",50,16050);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_non-canonical/wnt_nucleus_non_canonical_version_06.xml",50,18600);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/sonic_hedgehog/hedgehog_nucleus_version_01.xml",50,21150);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/sonic_hedgehog/hedgehog_cytosol_non_canonical_version_04.xml",50,23700);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/sonic_hedgehog/hedgehog_cytosol_version_01.xml",50,26250);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/AKT/pi3k_akt_mtor_nucleus_version_02.xml",50,28800);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/AKT/pi3K_akt_mtor_cytosol_version_33.xml",50,31350);
//		mm.setMapSize(3100, 35000);
//		mm.mergeAll();
//		mm.saveMap("/bioinfo/users/ebonnet/merged_surv.xml");

	}

}
