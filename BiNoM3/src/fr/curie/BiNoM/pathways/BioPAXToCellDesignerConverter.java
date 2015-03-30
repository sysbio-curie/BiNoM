package fr.curie.BiNoM.pathways;

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;
import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.utils.*;
import java.io.*;
import java.util.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.shared.*;



public class BioPAXToCellDesignerConverter extends BioPAXToSBMLConverter {

	public void convertToCellDesigner() throws Exception{
		generateTags();
		createCompartments();
		changeClassesOfSpecies();
	}

	public void generateTags(){
	}

	public void createCompartments(){
	}

	public void changeClassesOfSpecies() throws Exception{
		for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfSpecies().getSpeciesArray().length;i++){
			SpeciesDocument.Species sp = sbmlDoc.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			String name = Utils.getValue(sbmlDoc.getSbml().getModel().getListOfSpecies().getSpeciesArray(i).getName());
			String uri = bpnm.getUriByName(name);
			Resource res  = biopax.model.getResource(uri);
			String type = Utils.cutUri(BioPAXUtilities.getResourceType(res));
//			if(type.equals("physicalEntityParticipant")||type.equals("sequenceParticipant")){
//				physicalEntityParticipant pep = null;
//				if(type.equals("physicalEntityParticipant"))
//					pep = biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(res, biopax.model);
//				if(type.equals("sequenceParticipant"))
//					pep = biopax_DASH_level2_DOT_owlFactory.getsequenceParticipant(res, biopax.model);
//				physicalEntity pent = pep.getPHYSICAL_DASH_ENTITY();
//				String urie = pent.uri();
//				String entityType = Utils.cutUri(BioPAXUtilities.getResourceType(biopax.model.getResource(urie)));
//				System.out.println(name+"\t"+uri+"\t"+pent.getNAME()+"\t"+entityType);
//				if(entityType.equals("smallMolecule")){
//
//				}
//				if(entityType.equals("complex")){				
//
//				}
//				if(entityType.equals("protein")){
//
//				}
//			}
		}
	}

	public static String removeCompartmentSuffix(String name){
		String ret = name;
		if(ret.indexOf("@")>=0){
			ret = ret.substring(0, ret.indexOf("@"));
		}
		return ret;
	}

}
