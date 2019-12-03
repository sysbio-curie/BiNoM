package fr.curie.BiNoM.pathways.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.sbml.x2001.ns.celldesigner.AnnotationDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerListOfModificationsDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerModificationDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesIdentityDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerStateDocument;
import org.sbml.x2001.ns.celldesigner.CompartmentDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;

import fr.curie.BiNoM.pathways.CellDesignerToBioPAXConverterPaxtools;
import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.utils.Utils;

public class TestCellDesignerToBioPAXConverterPaxtools {

	public static void main(String[] args) {

		//File cellDesignerFile = new File("/Users/eric/wk/agilent_pathways/cc_maps/cellcycle_master.xml");
		//File cellDesignerFile = new File("/Users/eric/wk/acsn_maps/survival_master.xml");
		//File cellDesignerFile = new File("/Users/eric/wk/acsn_maps/acsn_master.xml");
		//File cellDesignerFile = new File("/Users/eric/wk/acsn_maps/emtcellmotility_ECM.xml");
		//CellDesignerToCytoscapeConverter.Graph graph = CellDesignerToCytoscapeConverter.convert(cellDesignerFile.getAbsolutePath());

		
//		CellDesignerToBioPAXConverterPaxtools c2b = new CellDesignerToBioPAXConverterPaxtools();
//		c2b.setCellDesigner(graph.sbml);
//		c2b.convert();
//		c2b.saveBioPAXModel("/Users/eric/test.owl");
		
		
		/*
		 * Convert a bunch of files
		 */
		ArrayList<String> l = new ArrayList<String>();
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader("/Users/eric/biopax/files.txt"));
	        //String line = br.readLine();
	    	String line;
	        while ((line = br.readLine()) != null) {
	        	l.add(line.trim());
	        }
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
		
		
//		l.add("/Users/eric/wk/acsn_maps/acsn_master.xml");
//		l.add("/Users/eric/wk/acsn_maps/apoptosis_master.xml");
//		l.add("/Users/eric/wk/acsn_maps/cellcycle_master.xml");
//		l.add("/Users/eric/wk/acsn_maps/dnarepair_master.xml");
//		l.add("/Users/eric/wk/acsn_maps/emtcellmotility_master.xml");
//		l.add("/Users/eric/wk/acsn_maps/survival_master.xml");
		
		for (String fn : l) {
			String output_fn = fn;
			output_fn = output_fn.replace("master.xml", "v1.0.owl");
			System.out.println("processing "+fn+" "+output_fn);
			CellDesignerToCytoscapeConverter.Graph graph = 
					CellDesignerToCytoscapeConverter.convert(fn);
			CellDesignerToBioPAXConverterPaxtools c2b = new CellDesignerToBioPAXConverterPaxtools();
			c2b.setCellDesigner(graph.sbml);
			c2b.convert();
			c2b.saveBioPAXModel(output_fn);
		}
	}

	
	
	 public static void printSpeciesList(SbmlDocument.Sbml sbml) {
		 for(int i=0;i<sbml.getModel().getListOfSpecies().getSpeciesArray().length;i++){
			 SpeciesDocument.Species sp = sbml.getModel().getListOfSpecies().getSpeciesArray(i);
			 //System.out.println("Species "+(i+1)+":");
			 AnnotationDocument.Annotation an = sp.getAnnotation();
			 
			 /*
			 if(an!=null)
				 System.out.print("\t"+Utils.getValue(an.getCelldesignerSpeciesIdentity().getCelldesignerClass()));
			 else
				 System.out.print("\t_");
			 */
			 
			 //System.out.println("\t"+sp.getId()+"\t"+sp.getName().getStringValue());
			 
			 //String cn = ((CompartmentDocument.Compartment)hm.get(sp.getCompartment())).getName().getStringValue();
			 //if(!sp.getCompartment().equals("default"))
			 //	 System.out.print("\t"+cn);
			 //else
			 //	 System.out.print("\t_");
			 
			 String ss = null;
			 CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity ident = sp.getAnnotation().getCelldesignerSpeciesIdentity();
			 if(ident!=null){
				 CelldesignerStateDocument.CelldesignerState state = ident.getCelldesignerState();
				 if(state!=null){
					 CelldesignerListOfModificationsDocument.CelldesignerListOfModifications lmodifs = ident.getCelldesignerState().getCelldesignerListOfModifications();
					 if(lmodifs!=null){
						 CelldesignerModificationDocument.CelldesignerModification modifs[] = lmodifs.getCelldesignerModificationArray();
						 for (int j=0;j<modifs.length;j++) {
							 CelldesignerModificationDocument.CelldesignerModification cm = modifs[j];
							 //System.out.println(">>> modif:"+cm.getResidue()+"_"+cm.getState().getStringValue());
							 System.out.println(">>> modif:"+cm.getResidue());
						 }
					 }
				 }
			 }
		 }
	 }

	
}
