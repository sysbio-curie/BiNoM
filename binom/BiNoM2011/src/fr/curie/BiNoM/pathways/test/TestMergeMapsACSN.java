package fr.curie.BiNoM.pathways.test;

import java.util.ArrayList;

import fr.curie.BiNoM.pathways.utils.MergingMapsProcessor;

public class TestMergeMapsACSN {

	private class MapData {
		public String fileName;
		public int deltaX;
		public int deltaY;
		
		// constructor
		MapData(String fn, int dx, int dy) {
			fileName = fn;
			deltaX = dx;
			deltaY = dy;
		}
	}
	
	private ArrayList<MapData> list = new ArrayList<MapData>();
	
	public static void main(String[] args) {
		TestMergeMapsACSN m = new TestMergeMapsACSN();
		m.run();
	}
	
	public void run() {
		
		list.add(new MapData("/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-3.xml",10,10));
		list.add(new MapData("/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-4.xml",600, 0));
		
//		list.add(new MapData("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/AKT/AKT_cytosol.xml",186,80));
//		
//		list.add(new MapData("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/sonic_hedgehog/SHH_cytosol.xml",5706,80));
//		list.add(new MapData("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/sonic_hedgehog/SHH_non_canon.xml",5706,2590));
//		list.add(new MapData("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/sonic_hedgehog/SHH_Nucleus.xml",5706,5100));
//
//		list.add(new MapData("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/WNT/Wnt_cytosol.xml",2696,80));
//		list.add(new MapData("/bioinfo/projects_prod/acsn/1_Work/cellsurvival/MAPS/AKT/AKT_nucleus.xml",1,2590));		
		
		mergeAll("1300","700");
	}

	public void mergeAll(String sizeX, String sizeY) {
		int nbFiles = list.size();
		
		MergingMapsProcessor mp = new MergingMapsProcessor();
		
		mp.setAndLoadFileName1(list.get(0).fileName);
		
		
		mp.setCd1MapSizeX(sizeX);
		mp.setCd1MapSizeY(sizeY);
		mp.shiftCoordinatesCD1(list.get(0).deltaX, list.get(0).deltaY);
		
		
		mp.setAndLoadFileName2(list.get(1).fileName);
		mp.shiftCoordinatesCD2(list.get(1).deltaX, list.get(1).deltaY);
		mp.setMergeLists();
		mp.mergeTwoMaps();
		
		for (int i=2;i<nbFiles;i++) {
			// set new file 2 name
			mp.setAndLoadFileName2(list.get(i).fileName);
			mp.shiftCoordinatesCD2(list.get(i).deltaX, list.get(i).deltaY);
			// create new common species names
			mp.setMergeLists();
			// merge maps
			mp.mergeTwoMaps();
		}
		
		System.out.println("saving file...");
		mp.saveCd1File("/bioinfo/users/ebonnet/merged_maps.xml");
		System.out.println("done.");

	}

}
