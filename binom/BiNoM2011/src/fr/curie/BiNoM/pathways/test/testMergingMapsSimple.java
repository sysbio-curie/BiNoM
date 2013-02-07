package fr.curie.BiNoM.pathways.test;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import fr.curie.BiNoM.pathways.utils.MergingMapsDialog;
import fr.curie.BiNoM.pathways.utils.MergingMapsProcessor;

public class testMergingMapsSimple {

	public static void main(String[] args) {
		
		// build a list of maps to merge
		ArrayList<File> fl = new ArrayList<File>();
		fl.add(new File("/bioinfo/users/ebonnet/Binom/mergeMaps/a1.xml"));
		fl.add(new File("/bioinfo/users/ebonnet/Binom/mergeMaps/a2.xml"));
	
		int nbFiles = fl.size();
		
		MergingMapsProcessor mp = new MergingMapsProcessor();
		
		mp.setAndLoadFileName1(fl.get(0).getAbsolutePath());
		mp.setAndLoadFileName2(fl.get(1).getAbsolutePath());
		mp.testShiftCoord();
		
		// create common species maps
		mp.setMergeLists();
		
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
		
		mp.saveCd1File("/bioinfo/users/ebonnet/merged_maps.xml");
	}
}
