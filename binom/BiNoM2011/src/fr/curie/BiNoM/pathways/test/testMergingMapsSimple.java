package fr.curie.BiNoM.pathways.test;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import fr.curie.BiNoM.pathways.utils.MergingMapsDialog;
import fr.curie.BiNoM.pathways.utils.MergingMapsProcessor;

public class testMergingMapsSimple {

	public static void main(String[] args) {
		
		//String file1 = "/bioinfo/users/ebonnet/Binom/mergeMaps/a1.xml";
		//String file2 = "/bioinfo/users/ebonnet/Binom/mergeMaps/a2.xml";
		
		String file1 = "/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-1.xml";
		String file2 = "/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-2.xml";

		//String file1 = "/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-3.xml";
		//String file2 = "/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-4.xml";
		
		// build a list of maps to merge
		ArrayList<File> fl = new ArrayList<File>();
		fl.add(new File(file1));
		fl.add(new File(file2));
	
		int nbFiles = fl.size();
		
		MergingMapsProcessor mp = new MergingMapsProcessor();
		
		mp.setAndLoadFileName1(fl.get(0).getAbsolutePath());
		mp.setAndLoadFileName2(fl.get(1).getAbsolutePath());
		mp.testShiftCoord();
		
		// create common species maps
		mp.setMergeLists();
		//mp.printSpeciesMap();
		
		// merge 2 maps
		mp.mergeTwoMaps();
		
		for (int i=2;i<nbFiles;i++) {
			// set new file 2 name
			mp.setAndLoadFileName2(fl.get(i).getAbsolutePath());
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
