package fr.curie.BiNoM.pathways.test;

import java.util.ArrayList;

import fr.curie.BiNoM.pathways.utils.MergingMapsProcessor;

public class TestMergeMapsACSN {
	
	public static void main(String[] args) {

		MergingMapsProcessor mm = new MergingMapsProcessor();
		mm.verbose=false;
		try {
			//mm.loadConfigFile("/Users/eric/wk/merge_maps_bug2014-10/survival_config.txt");
			//mm.loadConfigFile("/Users/eric/wk/test_merge/wnt/module_wnt_canonical2.txt");
			//mm.loadConfigFile("/Users/eric/wk/test_merge/simple_example/config.txt");
			//mm.loadConfigFile("/Users/eric/wk/test_merge/toy_config.txt");
			//mm.loadConfigFile("/Users/eric/wk/test_merge/survival/config2.txt");
			//mm.loadConfigFile("/Users/eric/wk/test_merge/survival/survival_configEB.txt");
			mm.loadConfigFile("/Users/eric/test/merge_config");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("config loaded.");
		mm.verbose = false;
		mm.doMergeSpecies = true;
		String outputFileName = "/Users/eric/res.xml";
		mm.mergeAll(outputFileName);
		mm.postProcessAnnotations(outputFileName);
	}

}
