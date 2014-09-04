package fr.curie.BiNoM.pathways.test;

import java.io.File;

import fr.curie.BiNoM.pathways.CellDesignerToBioPAXConverterPaxtools;
import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;

public class TestPaxtoolsConverter {

	public static void main(String[] args) {

		
		 // Load celldesigner file 
			//File cellDesignerFile = new File("/Users/eric/wk/agilent_pathways/cc_maps/cellcycle_APC.xml");
			File cellDesignerFile = new File("/Users/eric/wk/agilent_pathways/cc_maps/cellcycle_master.xml");
			//File cellDesignerFile = new File("/Users/eric/wk/acsn_maps/survival_master.xml");
			//File cellDesignerFile = new File("/Users/eric/wk/acsn_maps/acsn_master.xml");
			//File cellDesignerFile = new File("/Users/eric/wk/acsn_maps/emtcellmotility_ECM.xml");
			CellDesignerToCytoscapeConverter.Graph graph = CellDesignerToCytoscapeConverter.convert(cellDesignerFile.getAbsolutePath());

			CellDesignerToBioPAXConverterPaxtools c2b = new CellDesignerToBioPAXConverterPaxtools();
			c2b.setCellDesigner(graph.sbml);
			c2b.convert();
			c2b.saveBioPAXModel("/Users/eric/out.owl");
		
	}

}
