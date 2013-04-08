package fr.curie.BiNoM.pathways.test;

import java.util.ArrayList;

import fr.curie.BiNoM.pathways.utils.MergingMapsProcessor;

public class TestMergeMapsACSN {
	
	public static void main(String[] args) {
		
		MergingMapsProcessor mm = new MergingMapsProcessor();
		
//		mm.addMap("/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-3.xml",50,15);
//		mm.addMap("/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-4.xml",650, 15);
//		mm.mergeAll("1300","500");
		
		mm.addMap("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/AKT/AKT_cytosol.xml",140,80);
		mm.addMap("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/sonic_hedgehog/SHH_cytosol.xml",5706,80);
		mm.addMap("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/sonic_hedgehog/SHH_non_canon.xml",5706,2590);
		mm.addMap("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/sonic_hedgehog/SHH_Nucleus.xml",5706,5100);
		mm.addMap("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/WNT/Wnt_cytosol.xml",2696,80);
		mm.addMap("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/AKT/AKT_nucleus.xml",140,2610);
		mm.mergeAll("8192", "7720");

	}

}
