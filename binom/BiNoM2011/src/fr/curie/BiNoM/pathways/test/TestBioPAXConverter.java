package fr.curie.BiNoM.pathways.test;

import java.io.File;
import java.util.Vector;

import org.sbml.x2001.ns.celldesigner.SbmlDocument;

import cytoscape.CyNetwork;
import cytoscape.task.TaskMonitor;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerExportTask;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerSourceDB;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerVisualStyleDefinition;
import fr.curie.BiNoM.cytoscape.lib.NetworkFactory;
import fr.curie.BiNoM.pathways.CellDesignerToBioPAXConverter;
import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;

public class TestBioPAXConverter {

	public static void main(String[] args) {
		
		/*
		 *  Test conversion from SBML to BioPAX Level 3
		 *  Started 5.5.2014
		 */
		
	    
		 // Load celldesigner file 
		File cellDesignerFile = new File("/Users/eric/wk/agilent_pathways/cc_maps/cellcycle_APC.xml");
		CellDesignerToCytoscapeConverter.Graph graph = CellDesignerToCytoscapeConverter.convert(cellDesignerFile.getAbsolutePath());

		// extract SbmlDocument for conversion
		SbmlDocument sbml = graph.sbml;
		
		/*
		 * Convert to BioPAX, code extracted from BioPAXExportTask class (cytoscape.biopax)
		 */
		
		Vector species = new Vector();
		Vector speciesAliases = new Vector();
		Vector reactions = new Vector();
		Vector degraded = new Vector();

		CellDesignerExportTask.findSBMLSpeciesAndReactions
		(sbml, species, speciesAliases,
				reactions, degraded);
		CellDesignerToBioPAXConverter cd2bp = new CellDesignerToBioPAXConverter();

		String outputFile = "/Users/eric/toto.owl";
		
		cd2bp.sbml = sbml;
		cd2bp.biopax = new BioPAX();
		System.out.println(cd2bp.biopax.biopaxmodel);
		BioPAX bp = cd2bp.convert();
		//cd2bp.biopax.saveToFile(outputFile, cd2bp.biopax.biopaxmodel);
		System.out.println("Done.");
		

	}

}
