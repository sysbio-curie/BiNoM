package fr.curie.BiNoM.pathways.test;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.sbml.x2001.ns.celldesigner.SbmlDocument;

import fr.curie.BiNoM.pathways.utils.MergingMapsDialog;
import fr.curie.BiNoM.pathways.utils.MergingMapsProcessor;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;

public class testMergingMapsSimple {

	public static void main(String[] args) {
		
		//String file1 = "/bioinfo/users/ebonnet/Binom/mergeMaps/a1.xml";
		//String file2 = "/bioinfo/users/ebonnet/Binom/mergeMaps/a2.xml";
		
		//String file1 = "/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-1.xml";
		//String file2 = "/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-2.xml";

		//String file1 = "/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-3.xml";
		//String file2 = "/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-4.xml";
		//String file3 = "/bioinfo/users/ebonnet/Binom/mergeMaps/inna_toy_models/ToyModel-4.xml";
		
		//String file1 = "/bioinfo/users/ebonnet/testAND.xml";
		//String file2 = "/bioinfo/users/ebonnet/testAND.xml";
		
		/*
		 * sizeX = 2500 sizeY = 2500
		 */
		String file1 = "/bioinfo/users/ebonnet/Binom/mergeMaps/david_toy_models/AKT_nucleus/mTOR_nucleus_zoom_3.xml";
		String file2 = "/bioinfo/users/ebonnet/Binom/mergeMaps/david_toy_models/AKT_cytosol/mTOR_cytosol_zoom_3.xml";
		
		// build a list of maps to merge
		ArrayList<File> fl = new ArrayList<File>();
		fl.add(new File(file1));
		fl.add(new File(file2));
		//fl.add(new File(file3));
		
		int nbFiles = fl.size();
		
		MergingMapsProcessor mp = new MergingMapsProcessor();
		
		mp.setAndLoadFileName1(fl.get(0).getAbsolutePath());
		mp.setAndLoadFileName2(fl.get(1).getAbsolutePath());
		//mp.setCd1MapSizeX("2000");
		//mp.shiftCoordinatesCd2(600, 0);
		mp.setCd1MapSizeX("6000");
		mp.shiftCoordinatesCd2(2600, 0);
		//mp.setCd1MapSizeX("1500");
		//mp.shiftCoordinatesCd2(700, 0);
		
		// create common species maps
		mp.setMergeLists();
		//mp.printSpeciesMap();
		
		// merge 2 maps
		mp.mergeTwoMaps();
		
		for (int i=2;i<nbFiles;i++) {
			// set new file 2 name
			mp.setAndLoadFileName2(fl.get(i).getAbsolutePath());
			mp.shiftCoordinatesCd2(1200, 0);
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
