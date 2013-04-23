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
		
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_canonical/Wnt_Nucleus_version_26.xml",30,30);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_canonical/wnt_cytosol_canonical_version_33.xml",30,2530);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_canonical/wnt_continued_version_13.xml",30,5530);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_canonical/wnt_nucleus_continued_version_04.xml",30,8030);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_non-canonical/wnt_cytosol_non_canonical_version_27.xml",30,10530);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_non-canonical/wnt_crosstalk_version_07.xml",30,13230);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_non-canonical/wnt_cytosol_non_canonical_continued_version_14.xml",30,15730);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_non-canonical/wnt_nucleus_non_canonical_version_06.xml",30,18230);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/sonic_hedgehog/hedgehog_nucleus_version_01.xml",30,20730);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/sonic_hedgehog/hedgehog_cytosol_non_canonical_version_04.xml",30,23230);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/sonic_hedgehog/hedgehog_cytosol_version_01.xml",30,25730);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/AKT/pi3k_akt_mtor_nucleus_version_02.xml",30,28230);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/AKT/pi3K_akt_mtor_cytosol_version_33.xml",30,30730);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/rbe2f_v1.xml",30,33230);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/dnarepair_v2.xml",30,38350);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/mapk_v1.xml",30,47854);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/apoptosis_v1.xml",30,50158);
//		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/emt_v1.xml",30,63198);
//		mm.setMapSize(20000, 70000);
//		mm.mergeAll();
//		mm.saveMap("/bioinfo/users/ebonnet/test.xml");

		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_canonical/Wnt_Nucleus_version_26.xml",30,30);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_canonical/wnt_cytosol_canonical_version_33.xml",30,2530);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_canonical/wnt_continued_version_13.xml",30,5530);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_canonical/wnt_nucleus_continued_version_04.xml",30,8030);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_non-canonical/wnt_cytosol_non_canonical_version_27.xml",30,10530);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_non-canonical/wnt_crosstalk_version_07.xml",30,13230);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_non-canonical/wnt_cytosol_non_canonical_continued_version_14.xml",30,15730);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/WNT/Wnt_non-canonical/wnt_nucleus_non_canonical_version_06.xml",30,18230);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/sonic_hedgehog/hedgehog_nucleus_version_01.xml",30,20730);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/sonic_hedgehog/hedgehog_cytosol_non_canonical_version_04.xml",30,23230);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/sonic_hedgehog/hedgehog_cytosol_version_01.xml",30,25730);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/AKT/pi3k_akt_mtor_nucleus_version_02.xml",30,28230);
		mm.addMap("/bioinfo/projects_prod/acsn/5_Release_xmls/survival_v1/AKT/pi3K_akt_mtor_cytosol_version_33.xml",30,30730);
		mm.setMapSize(3100, 35000);
		mm.mergeAll();
		mm.saveMap("/bioinfo/users/ebonnet/merged_surv.xml");

	}

}
