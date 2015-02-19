/*
   BiNoM Cytoscape Plugin
   Copyright (C) 2006-2007 Curie Institute, 26 rue d'Ulm, 75005 Paris - FRANCE

   BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

/*
  BiNoM authors:
	Andrei Zinovyev : http://www.ihes.fr/~zinovyev
	Eric Viara : http://www.sysra.com/viara
	Laurence Calzone :	http://leibniz.biol.vt.edu/people/laurence/laurence.html
 */
package fr.curie.BiNoM.pathways;


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

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

/**
 * Implementation of converter from CellDesigner 3.* file
 * to BioPAX
 */
@SuppressWarnings("unchecked")
public class CellDesignerToBioPAXConverter {

	/**
	 * Check to include compartment label in the name
	 */
	public static boolean alwaysMentionCompartment = false;
	/**
	 * not used
	 */
	public static boolean useBiopaxModelOntology = false;

	/**
	 * Map of CellDesigner SpeciesDocument.Species objects
	 */
	public HashMap<String, SpeciesDocument.Species> species = null;
	public HashMap<String, CelldesignerSpeciesDocument.CelldesignerSpecies> includedSpecies = null;
	/**
	 * Map of CellDesigner ReactionDocument.Reaction objects
	 */  
	public HashMap<String, ReactionDocument.Reaction> reactions = null;
	/**
	 * Map of CellDesigner CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias objects
	 */  
	public HashMap<String, CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias> complexes = null;
	/**
	 * Map of CellDesigner Celldesigner entity objects
	 */  
	public HashMap<String, CelldesignerProteinDocument.CelldesignerProtein> proteins = null;
	public HashMap<String, CelldesignerGeneDocument.CelldesignerGene> genes = null;
	public HashMap<String, CelldesignerRNADocument.CelldesignerRNA> rnas = null;	
	public HashMap<String, CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA> asrnas = null;
	
	public HashMap<String, EntityReference> entityReferences = null;
	public HashMap<String, PhysicalEntity> entities = null;
	

	/**
	 * Name of CellDesigner file
	 */  
	public String filename = "";
	/**
	 * Java xml-beans mapping of CellDesigner file
	 */  
	public org.sbml.x2001.ns.celldesigner.SbmlDocument sbml = null;
	/**
	 * The result of the conversion
	 */  
	public BioPAX biopax = null;
	/**
	 * Map from compartment ids to CompartmentDocument.Compartment objects
	 */  
	public HashMap<String, CompartmentDocument.Compartment> compartmentHash = null;
	public HashMap<String, CellularLocationVocabulary> cellularLocationVocabularyHash = null;
	/**
	 * Map from Pubmed ids to publicationXref objects
	 */  
	public HashMap pubmedrefsHash = new HashMap();
	/**
	 * Map from CellDesigner terms (strings) to OpenControlledVocabulary objects
	 */  
	public HashMap cellDesignerTerms = new HashMap();

	public CellDesignerToBioPAXConverter  () {
	}

	/**
	 * The main conversion function to be used from outside 
	 */
	public BioPAX convert(){
		if(biopax==null) biopax = new BioPAX();
		CellDesignerToBioPAXConverter.useBiopaxModelOntology = false;
		CellDesignerToCytoscapeConverter.alwaysMentionCompartment = true;
		CellDesignerToCytoscapeConverter.createSpeciesMap(sbml.getSbml());
		alwaysMentionCompartment = true;
		biopax.makeCompartments();
		createCellDesignerTerms();
		compartmentHash = getCompartmentHash(sbml.getSbml());
		CellDesigner.entities = CellDesigner.getEntities(sbml);
		//return populateModel();
		populateModelnew();
		return biopax;
	}

	/**
	 * The converter itself 
	 */
	
	public void populateModelnew(){
		try{
			
			CellDesignerToCytoscapeConverter.createSpeciesMap(sbml.getSbml());			
			Pathway path = biopax_DASH_level3_DOT_owlFactory.createPathway(biopax.namespaceString+sbml.getSbml().getModel().getId(),biopax.biopaxmodel);
			path.addName(sbml.getSbml().getModel().getId());
			path.addComment("This pathway is generated automatically from the CellDesigner XML file "+filename+" using BiNoM software (http://binom.curie.fr) developed by the Computational Systems Biology Group at Institut Curie (http://sysbio.curie.fr)");			
			// We have to create modules as pathways
			
			entityReferences = new HashMap<String, EntityReference>();
			entities = new HashMap<String, PhysicalEntity>();
			species = getSpecies(sbml.getSbml());
			includedSpecies = getIncludedSpecies(sbml.getSbml());			
			reactions = getReactions(sbml.getSbml());
			complexes = getComplexes(sbml.getSbml());
			proteins = getProteins(sbml.getSbml());
			genes = getGenes(sbml.getSbml());			
			rnas = getRNAs(sbml.getSbml());
			asrnas = getAntisenseRNAs(sbml.getSbml());			

			//Phenotypes and Unknowns are treated as PhysicalEntityReferences
			Iterator<String> itspecies = species.keySet().iterator();
			while(itspecies.hasNext()){
				SpeciesDocument.Species sp = species.get(itspecies.next());
				if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null)
					if(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("PHENOTYPE")){
						System.out.println("Phenotype: "+sp.getId()+" - "+sp.getName().getStringValue());
						EntityReference pheno = biopax_DASH_level3_DOT_owlFactory.createEntityReference(biopax.namespaceString+sp.getId()+"_ref",biopax.biopaxmodel);
						pheno.addName(sp.getName().getStringValue());
						//pheno.setSHORT_DASH_NAME(sp.getName().getStringValue());
						String cleanname = correctName(sp.getName().getStringValue());
						pheno.addName(cleanname);
						pheno.addComment("This is a CellDesigner PHENOTYPE entity. It is not a real protein but rather an abstract representation of a process.");
						if(sp.getNotes()!=null){
							if(sp.getNotes().getHtml()!=null)if(sp.getNotes()!=null)
								pheno.addComment(Utils.cutFirstLastNonVisibleSymbols(Utils.getValue(sp.getNotes())));
								Vector v = extractPubMedReferenceFromComment(Utils.getValue(sp.getNotes()));
								addPublicationXrefsToEntityReference(pheno,v);

						}
						entityReferences.put(sp.getId()+"_ref",pheno);
					}
			}

			itspecies = species.keySet().iterator();
			while(itspecies.hasNext()){
				SpeciesDocument.Species sp = species.get(itspecies.next());
				if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null)
					if(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("UNKNOWN")){
						System.out.println("Unknown: "+sp.getId()+" - "+sp.getName().getStringValue());
						EntityReference pheno = biopax_DASH_level3_DOT_owlFactory.createEntityReference(biopax.namespaceString+sp.getId()+"_ref",biopax.biopaxmodel);
						pheno.addName(sp.getName().getStringValue());
						//pheno.setSHORT_DASH_NAME(sp.getName().getStringValue());
						String cleanname = correctName(sp.getName().getStringValue());
						pheno.addName(cleanname);
						pheno.addComment("This is a CellDesigner UNKNOWN entity.");
						if(sp.getNotes()!=null){
							if(sp.getNotes().getHtml()!=null)if(sp.getNotes()!=null)
								pheno.addComment(Utils.cutFirstLastNonVisibleSymbols(Utils.getValue(sp.getNotes())));
								Vector v = extractPubMedReferenceFromComment(Utils.getValue(sp.getNotes()));
								addPublicationXrefsToEntityReference(pheno,v);
						}
						entityReferences.put(sp.getId()+"_ref",pheno);
					}
			}

			// Proteins
			for(String id: proteins.keySet()){
				CelldesignerProteinDocument.CelldesignerProtein prot = proteins.get(id);
				System.out.println("Protein: "+prot.getName().getStringValue());
				ProteinReference protein_ = biopax_DASH_level3_DOT_owlFactory.createProteinReference(biopax.namespaceString+prot.getId(),biopax.biopaxmodel);
				protein_.addName(prot.getName().getStringValue());
				//protein_.setSHORT_DASH_NAME(prot.getName().getStringValue());
				String cleanname = correctName(prot.getName().getStringValue());
				protein_.addName(cleanname);
				if(prot.getCelldesignerNotes()!=null){
					protein_.addComment((Utils.cutFirstLastNonVisibleSymbols(Utils.getValue(prot.getCelldesignerNotes()))));
					Vector v = extractPubMedReferenceFromComment(Utils.getValue(prot.getCelldesignerNotes()));
					addPublicationXrefsToEntityReference(protein_,v);
				}
				entityReferences.put(prot.getId(),protein_);
			}
			// Genes
			if(sbml.getSbml().getModel().getAnnotation()!=null)if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes()!=null)
				for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray().length;i++){
					CelldesignerGeneDocument.CelldesignerGene gene = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
					System.out.println("Gene: "+gene.getName());
					DnaReference dna_ = biopax_DASH_level3_DOT_owlFactory.createDnaReference(biopax.namespaceString+gene.getId(),biopax.biopaxmodel);
					dna_.addName(gene.getName());
					dna_.addName(gene.getName());
					entityReferences.put(gene.getId(),dna_);
					if(gene.getCelldesignerNotes()!=null){
						dna_.addComment((Utils.cutFirstLastNonVisibleSymbols(Utils.getValue(gene.getCelldesignerNotes()))));
						Vector v = extractPubMedReferenceFromComment(Utils.getValue(gene.getCelldesignerNotes()));
						addPublicationXrefsToEntityReference(dna_,v);
					}
				}
			// RNA
			if(sbml.getSbml().getModel().getAnnotation()!=null)if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs()!=null)
				for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray().length;i++){
					CelldesignerRNADocument.CelldesignerRNA RNA = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
					System.out.println("RNA: "+RNA.getName());
					RnaReference rna_ = biopax_DASH_level3_DOT_owlFactory.createRnaReference(biopax.namespaceString+RNA.getId(),biopax.biopaxmodel);
					rna_.addName(RNA.getName());
					rna_.addName(RNA.getName());
					entityReferences.put(RNA.getId(),rna_);
					if(RNA.getCelldesignerNotes()!=null){
						rna_.addComment((Utils.cutFirstLastNonVisibleSymbols(Utils.getValue(RNA.getCelldesignerNotes()))));
						Vector v = extractPubMedReferenceFromComment(Utils.getValue(RNA.getCelldesignerNotes()));
						addPublicationXrefsToEntityReference(rna_,v);
					}
				}
			// Antisense RNA
			if(sbml.getSbml().getModel().getAnnotation()!=null)if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs()!=null)    
				for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray().length;i++){
					CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA aRNA = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
					System.out.println("aRNA: "+aRNA.getName());
					RnaReference rna_ = biopax_DASH_level3_DOT_owlFactory.createRnaReference(biopax.namespaceString+aRNA.getId(),biopax.biopaxmodel);
					rna_.addName(aRNA.getName());
					rna_.addName(aRNA.getName());
					entityReferences.put(aRNA.getId(),rna_);
					if(aRNA.getCelldesignerNotes()!=null){
						rna_.addComment((Utils.cutFirstLastNonVisibleSymbols(Utils.getValue(aRNA.getCelldesignerNotes()))));
						Vector v = extractPubMedReferenceFromComment(Utils.getValue(aRNA.getCelldesignerNotes()));
						addPublicationXrefsToEntityReference(rna_,v);
					}
			}
			// Small molecules
			for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray().length;i++){
				SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
				AnnotationDocument.Annotation an = sp.getAnnotation();
				if(an!=null){
					String cl = Utils.getValue(an.getCelldesignerSpeciesIdentity().getCelldesignerClass());
					if(cl.equals("ION")){
						System.out.println("Small molecule: "+sp.getName().getStringValue());
						SmallMoleculeReference sm = biopax_DASH_level3_DOT_owlFactory.createSmallMoleculeReference(biopax.namespaceString+sp.getId()+"_ref",biopax.biopaxmodel);
						sm.addName(CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,sp.getId(),false,true));
						sm.addName(sp.getName().getStringValue());
						String cleanname = correctName(sp.getName().getStringValue());
						sm.addName(cleanname);
						if(sp.getNotes()!=null){
							sm.addComment(Utils.cutFirstLastNonVisibleSymbols(Utils.getValue(sp.getNotes())));
							Vector v = extractPubMedReferenceFromComment(Utils.getValue(sp.getNotes()));
							addPublicationXrefsToEntityReference(sm,v);
						}
						entityReferences.put(sp.getId()+"_ref",sm);
					}
					if(cl.equals("SIMPLE_MOLECULE")){
						System.out.println("Small molecule: "+sp.getName().getStringValue());
						SmallMoleculeReference sm = biopax_DASH_level3_DOT_owlFactory.createSmallMoleculeReference(biopax.namespaceString+sp.getId()+"_ref",biopax.biopaxmodel);
						sm.addName(CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,sp.getId(),false,true));
						sm.addName(sp.getName().getStringValue());
						if(sp.getNotes()!=null){
							sm.addComment(Utils.cutFirstLastNonVisibleSymbols(Utils.getValue(sp.getNotes())));
							Vector v = extractPubMedReferenceFromComment(Utils.getValue(sp.getNotes()));
							addPublicationXrefsToEntityReference(sm,v);
						}
						entityReferences.put(sp.getId()+"_ref",sm);
					}
					if(cl.equals("DRUG")){
						System.out.println("Small molecule: "+sp.getName().getStringValue());
						SmallMoleculeReference sm = biopax_DASH_level3_DOT_owlFactory.createSmallMoleculeReference(biopax.namespaceString+sp.getId()+"_ref",biopax.biopaxmodel);
						sm.addName(CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,sp.getId(),false,true));
						sm.addName(sp.getName().getStringValue());
						if(sp.getNotes()!=null){
							sm.addComment(Utils.cutFirstLastNonVisibleSymbols(Utils.getValue(sp.getNotes())));
							Vector v = extractPubMedReferenceFromComment(Utils.getValue(sp.getNotes()));
							addPublicationXrefsToEntityReference(sm,v);
						}
						entityReferences.put(sp.getId()+"_ref",sm);
					}					
				}
			}
			

			for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray().length;i++){
				System.out.print((i+1)+"/"+sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray().length+"\t");
				SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
				String spcl = "";
				if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null){
					spcl = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
				if(!spcl.equals("DEGRADED")){
					PhysicalEntity ent = createEntityForSpecies(sp.getId(),sbml.getSbml(),biopax);
				}
				}
			}
			
			
			// Reactions
			int k=0;
			for(String rid: reactions.keySet()){
				ReactionDocument.Reaction r = reactions.get(rid);
				String rtype = "UNKNOWN_TRANSITION";
				if(r.getAnnotation()!=null)if(r.getAnnotation().getCelldesignerReactionType()!=null)
					rtype = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());

				k++;
				System.out.println("Reaction ("+k+"/"+reactions.size()+"): "+r.getId()+" "+rtype);

				Control ctrl = null;
				Conversion conv = null;
				//physicalInteraction inter = null;
				Interaction inter = null;

				if(rtype.equals("TRANSPORT")){
					Transport tr = biopax_DASH_level3_DOT_owlFactory.createTransport(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = tr;
					inter = tr;
				}

				if(rtype.equals("CATALYSIS")||rtype.equals("UNKNOWN_CATALYSIS")||rtype.equals("TRANSCRIPTIONAL_ACTIVATION")||rtype.equals("TRANSLATIONAL_ACTIVATION")){
					Catalysis cat = biopax_DASH_level3_DOT_owlFactory.createCatalysis(biopax.namespaceString+"c"+r.getId(),biopax.biopaxmodel);
					cat.setControlType("ACTIVATION");
					//PhysicalEntity pepr = createEntityParticipantForSpecies(r.getListOfReactants().getSpeciesReferenceArray(0).getSpecies(),species,complexes_list,physEntities,sbml.getSbml(),biopax,"c"+r.getId());
					PhysicalEntity pepr = (PhysicalEntity)entities.get(r.getListOfReactants().getSpeciesReferenceArray(0).getSpecies());
					cat.addController(pepr);
					//PhysicalEntity pepp = createEntityParticipantForSpecies(r.getListOfProducts().getSpeciesReferenceArray(0).getSpecies(),species,complexes_list,physEntities,sbml.getSbml(),biopax,"c"+r.getId());
					PhysicalEntity pepp = (PhysicalEntity)entities.get(r.getListOfProducts().getSpeciesReferenceArray(0).getSpecies());
					//cat.addPARTICIPANTS(pepp);
					cat.addParticipant(pepp);
					ctrl = cat;
					inter = cat;

					BiochemicalReaction bre = biopax_DASH_level3_DOT_owlFactory.createBiochemicalReaction(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = bre;
					//cat.setCONTROLLED(bre);
					cat.setControlled(bre);
				}

				if(rtype.equals("INHIBITION")||rtype.equals("UNKNOWN_INHIBITION")||rtype.equals("TRANSCRIPTIONAL_INHIBITION")||rtype.equals("TRANSLATIONAL_INHIBITION")){
					Catalysis cat = biopax_DASH_level3_DOT_owlFactory.createCatalysis(biopax.namespaceString+"c"+r.getId(),biopax.biopaxmodel);
					cat.setControlType("INHIBITION");
					PhysicalEntity pepr = createEntityForSpecies(r.getListOfReactants().getSpeciesReferenceArray(0).getSpecies(),sbml.getSbml(),biopax);
					//cat.setCONTROLLER(pepr);
					PhysicalEntity pepp = createEntityForSpecies(r.getListOfProducts().getSpeciesReferenceArray(0).getSpecies(),sbml.getSbml(),biopax);
					cat.addParticipant(pepp);
					ctrl = cat;
					inter = cat;

					BiochemicalReaction bre = biopax_DASH_level3_DOT_owlFactory.createBiochemicalReaction(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = bre;
					cat.setControlled(bre);
				}


				if(rtype.equals("HETERODIMER_ASSOCIATION")){
					ComplexAssembly cas = biopax_DASH_level3_DOT_owlFactory.createComplexAssembly(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = cas;
					inter = cas;
				}

				if(rtype.equals("DISSOCIATION")){
					ComplexAssembly cas = biopax_DASH_level3_DOT_owlFactory.createComplexAssembly(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = cas;
					inter = cas;
				}

				if(rtype.equals("STATE_TRANSITION")){
					BiochemicalReaction bre = biopax_DASH_level3_DOT_owlFactory.createBiochemicalReaction(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = bre;
					inter = bre;
				}

				if(rtype.equals("TRUNCATION")){
					BiochemicalReaction bre = biopax_DASH_level3_DOT_owlFactory.createBiochemicalReaction(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = bre;
					inter = bre;
				}


				if(rtype.equals("KNOWN_TRANSITION_OMITTED")){
					BiochemicalReaction bre = biopax_DASH_level3_DOT_owlFactory.createBiochemicalReaction(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = bre;
					inter = bre;
				}

				if(rtype.equals("UNKNOWN_TRANSITION")){
					BiochemicalReaction bre = biopax_DASH_level3_DOT_owlFactory.createBiochemicalReaction(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = bre;
					inter = bre;
				}
				
				if(rtype.equals("DEGRADATION")){
					Degradation deg = biopax_DASH_level3_DOT_owlFactory.createDegradation(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = deg;
					inter = deg;
				}
				
				
				if(rtype.equals("TRANSLATION")||rtype.equals("TRANSCRIPTION")){
					BiochemicalReaction trans = biopax_DASH_level3_DOT_owlFactory.createBiochemicalReaction(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = trans;
					inter = trans;
					// In the future, make it template reaction, if possible
				}
				
				if(rtype.contains("POSITIVE_INFLUENCE")||rtype.contains("NEGATIVE_INFLUENCE")){
					BiochemicalReaction trans = biopax_DASH_level3_DOT_owlFactory.createBiochemicalReaction(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = trans;
					inter = trans;
					// In the future, make it template reaction, if possible
				}
				

				if(rtype.equals("TRANSCRIPTIONAL_ACTIVATION")){

				}

				if(rtype.equals("TRANSCRIPTIONAL_INHIBITION")){

				}

				if(rtype.equals("TRANSLATIONAL_ACTIVATION")){

				}

				if(rtype.equals("TRANSLATIONAL_INHIBITION")){

				}

				if(rtype.equals("TRUNCATION")){

				}

				// Here we produce catalysis reactions
				if(r.getListOfModifiers()!=null)
					for(int j=0;j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length;j++){
						String id = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
						//PhysicalEntity pep = createEntityParticipantForSpecies(id,species,complexes_list,physEntities,sbml.getSbml(),biopax,r.getId());
						PhysicalEntity pep = (PhysicalEntity)entities.get(id);
						if(pep==null)
							pep = createEntityForSpecies(id,sbml.getSbml(),biopax);
						//pep.setSTOICHIOMETRIC_DASH_COEFFICIENT(new Double(1));
						Catalysis cat = biopax_DASH_level3_DOT_owlFactory.createCatalysis(biopax.namespaceString+r.getId()+"_c_"+id,biopax.biopaxmodel);
						path.addPathwayComponent(cat);
						cat.addController(pep);
						cat.setControlled(inter);
						if(r.getAnnotation()!=null)
							if(r.getAnnotation().getCelldesignerListOfModification()!=null){
								//for(int k=0;k<r.getReactionAnnotation().getCelldesigner_listOfModification().getCellDesignerModifications().size();k++){
								CelldesignerModificationDocument.CelldesignerModification modif = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
								String mtyp = "";
								try{
									mtyp =  modif.getType().toString();
								}catch(Exception e){
									mtyp = "UNKNOWN_CATALYSIS";
									System.out.println("WARNING!!! Catalysis type not well defined for "+r.getId()+" (modifier "+id+")");
								}
								if(mtyp.equals("CATALYSIS")||mtyp.equals("UNKNOWN_CATALYSIS")){
									cat.setControlType("ACTIVATION");
								}
								if(mtyp.equals("INHIBITION")||mtyp.equals("UNKNOWN_INHIBITION")){
									cat.setControlType("INHIBITION");
								}
								//if(mtyp.equals("UNKNOWN"))
								//  cat.setCONTROL_DASH_TYPE("");
								cat.addComment("CDTYPE:"+mtyp);
								//cat.addInteractionType((ControlledVocabulary)cellDesignerTerms.get(mtyp));
							}
					}

				if(inter==null){
					System.out.println("INTER = NULL! "+r.getId());
				}else{
					path.addPathwayComponent(inter);
					inter.addComment("CDTYPE:"+rtype);
					//inter.addINTERACTION_DASH_TYPE((openControlledVocabulary)cellDesignerTerms.get(rtype));
				}
				if(inter!=null)if(r.getNotes()!=null){
					//System.out.println("COMMENT: "+r.getNotes().getHtml().getBody());
					inter.addComment(Utils.cutFirstLastNonVisibleSymbols(Utils.getValue(r.getNotes())));
					Vector v = extractPubMedReferenceFromComment(Utils.getValue(r.getNotes()));
					addPublicationXrefsToEntity(inter,v);
				}
				if(conv!=null){
					boolean iscontrol = (ctrl!=null);
						for(int j=0;j<r.getListOfReactants().getSpeciesReferenceArray().length;j++){
							String id = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
							PhysicalEntity pep = createEntityForSpecies(id,sbml.getSbml(),biopax);
							SpeciesReferenceDocument.SpeciesReference sr = r.getListOfReactants().getSpeciesReferenceArray(j);
							double st = 1;
							if((sr.getStoichiometry()!=null)&&(!sr.getStoichiometry().equals("")))
								st = Double.parseDouble(sr.getStoichiometry());
							//pep.setSTOICHIOMETRIC_DASH_COEFFICIENT(new Double(st));
							if(pep!=null){
								conv.addLeft(pep);
								//pep.setSTOICHIOMETRIC_DASH_COEFFICIENT(new Double(st));
							}
						}
					for(int j=0;j<r.getListOfProducts().getSpeciesReferenceArray().length;j++){
						String id = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
						PhysicalEntity pep = createEntityForSpecies(id,sbml.getSbml(),biopax);
						SpeciesReferenceDocument.SpeciesReference sr = r.getListOfProducts().getSpeciesReferenceArray(j);
						double st = 1;
						if((sr.getStoichiometry()!=null)&&(!sr.getStoichiometry().equals("")))
								st = Double.parseDouble(sr.getStoichiometry());
						if(pep!=null){
							conv.addRight(pep);
							//pep.setSTOICHIOMETRIC_DASH_COEFFICIENT(new Double(st));
						}
					}
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void populateModel(){
		try{
		
			/*
			// Now let us create all species and included species but not complexes, homodimers and degraded
			for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray().length;i++){
				SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
				String spcl = "";
				if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null){
					spcl = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
				boolean isHomodimer = false;
				if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerState()!=null)
				if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerState().getCelldesignerHomodimer()!=null)
						isHomodimer = true;
				if(!spcl.equals("COMPLEX"))if(!spcl.equals("DEGRADED"))if(!isHomodimer){
					PhysicalEntity ent = createEntityForSpecies(sp.getId(),species,complexes_list,entities,entityReferences,sbml.getSbml(),biopax,sp.getId());
					entities.put(sp.getId(), ent);
				}
				}
			}			
			if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
			for(int j=0;j<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray().length;j++){
				CelldesignerSpeciesDocument.CelldesignerSpecies isp = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(j);
				PhysicalEntity ent = createEntityForSpecies(isp.getId(),species,complexes_list,entities,entityReferences,sbml.getSbml(),biopax,isp.getId());
				entities.put(isp.getId(), ent);
			}			
			
			// Complexes
			Vector complexes = new Vector();
			if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
				for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
					CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias csa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);

					String csa_id = csa.getId();
					String csa_sp_id = csa.getSpecies();
					SpeciesDocument.Species sp = species.get(csa_sp_id);
					String complex_name = "";
					String complex_id = "";
					complex_name = Utils.getValue(sp.getName()); //CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,csa_sp_id,false,true);
					complex_id = csa_sp_id;
					System.out.println("Complex: ("+i+"/"+sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length+"): "+csa.getSpecies()+"\t"+complex_name);

					if(complexes.indexOf(complex_id)<0){
						Complex compl = biopax_DASH_level3_DOT_owlFactory.createComplex(biopax.namespaceString+complex_id,biopax.biopaxmodel);
						compl.addName(complex_name);
						//compl.setSHORT_DASH_NAME(complex_name);
						//compl.addName(CellDesignerToCytoscapeConverter.getCelldesignerSpeciesName(sbml.getSbml(),csa.getSpecies()));
						complexes.add(complex_id);
						entities.put(complex_id,compl);
						for(int j=0;j<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray().length;j++){
							CelldesignerSpeciesDocument.CelldesignerSpecies isp = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(j);
							CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity ispi = isp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
							String cs = Utils.getValue(isp.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
							if(cs.equals(csa_sp_id)){
								PhysicalEntity pep = createEntityForSpecies(isp.getId(),species,complexes_list,entities,entityReferences,sbml.getSbml(),biopax,csa_id);
								//if(pep==null)
								//	System.out.println("ERROR: no entity for a complex "+complex_id+" component "+isp.getId());
								compl.addComponent(pep);
							}
						}
					}
					entities.put(csa_sp_id,entities.get(complex_id));
				}
			// Homodimers are treated as complexes
			for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray().length;i++){
				SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
				if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null)
					if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerState()!=null){
						String hm = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerState().getCelldesignerHomodimer());
						if(hm!=null){
							if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null){
								CelldesignerProteinDocument.CelldesignerProtein pp = (CelldesignerProteinDocument.CelldesignerProtein)getProteinById(proteins,Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()));
								System.out.println("Homodimer: "+pp.getName().getStringValue()+" ("+hm+")");
							}
							if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()!=null){
								CelldesignerRNADocument.CelldesignerRNA pp = (CelldesignerRNADocument.CelldesignerRNA)getProteinById(proteins,Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()));
								System.out.println("Homodimer: "+pp.getName()+" ("+hm+")");
							}
							String csa_sp_id = sp.getId();
							String complex_name = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,csa_sp_id,false,true);
							String complex_id = csa_sp_id;
							int ihm = Integer.parseInt(hm);
							if(complexes.indexOf(complex_id)<0){
								Complex compl = biopax_DASH_level3_DOT_owlFactory.createComplex(biopax.namespaceString+complex_name,biopax.biopaxmodel);
								System.out.println("Created complex "+compl.uri());
								compl.addName(complex_name);
								//compl.setSHORT_DASH_NAME(complex_name);
								complexes.add(complex_id);
								entities.put(complex_id,compl);
								entities.put(biopax.namespaceString+Utils.correctName(complex_name),compl);
								Stoichiometry stoich = biopax_DASH_level3_DOT_owlFactory.createStoichiometry(biopax.namespaceString+complex_id+"_stoich", biopax.biopaxmodel);
								stoich.setStoichiometricCoefficient((float)ihm);
								PhysicalEntity pep = createEntityForHomodimer(sp.getId(),species,complexes_list,entities,entityReferences,sbml.getSbml(),biopax,csa_sp_id);
								stoich.setPhysicalEntity(pep);
								compl.addComponent(pep);
								compl.addComponentStoichiometry(stoich);
							}
						}
					}
			}
			
			// Interactions
			for(int i=0;i<reactions.size();i++){
				ReactionDocument.Reaction r = (ReactionDocument.Reaction)reactions.elementAt(i);
				String rtype = "UNKNOWN_TRANSITION";
				if(r.getAnnotation()!=null)if(r.getAnnotation().getCelldesignerReactionType()!=null)
					rtype = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());


				System.out.println("Reaction ("+i+"/"+reactions.size()+"): "+r.getId()+" "+rtype);

				Control ctrl = null;
				Conversion conv = null;
				//physicalInteraction inter = null;
				Interaction inter = null;

				if(rtype.equals("TRANSPORT")){
					Transport tr = biopax_DASH_level3_DOT_owlFactory.createTransport(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = tr;
					inter = tr;
				}

				if(rtype.equals("CATALYSIS")||rtype.equals("UNKNOWN_CATALYSIS")||rtype.equals("TRANSCRIPTIONAL_ACTIVATION")||rtype.equals("TRANSLATIONAL_ACTIVATION")){
					Catalysis cat = biopax_DASH_level3_DOT_owlFactory.createCatalysis(biopax.namespaceString+"c"+r.getId(),biopax.biopaxmodel);
					cat.setControlType("ACTIVATION");
					//PhysicalEntity pepr = createEntityParticipantForSpecies(r.getListOfReactants().getSpeciesReferenceArray(0).getSpecies(),species,complexes_list,physEntities,sbml.getSbml(),biopax,"c"+r.getId());
					PhysicalEntity pepr = (PhysicalEntity)entities.get(r.getListOfReactants().getSpeciesReferenceArray(0).getSpecies());
					cat.addController(pepr);
					//PhysicalEntity pepp = createEntityParticipantForSpecies(r.getListOfProducts().getSpeciesReferenceArray(0).getSpecies(),species,complexes_list,physEntities,sbml.getSbml(),biopax,"c"+r.getId());
					PhysicalEntity pepp = (PhysicalEntity)entities.get(r.getListOfProducts().getSpeciesReferenceArray(0).getSpecies());
					//cat.addPARTICIPANTS(pepp);
					cat.addParticipant(pepp);
					ctrl = cat;
					inter = cat;

					BiochemicalReaction bre = biopax_DASH_level3_DOT_owlFactory.createBiochemicalReaction(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = bre;
					//cat.setCONTROLLED(bre);
					cat.setControlled(bre);
				}

				if(rtype.equals("INHIBITION")||rtype.equals("UNKNOWN_INHIBITION")||rtype.equals("TRANSCRIPTIONAL_INHIBITION")||rtype.equals("TRANSLATIONAL_INHIBITION")){
					Catalysis cat = biopax_DASH_level3_DOT_owlFactory.createCatalysis(biopax.namespaceString+"c"+r.getId(),biopax.biopaxmodel);
					cat.setControlType("INHIBITION");
					PhysicalEntity pepr = createEntityForSpecies(r.getListOfReactants().getSpeciesReferenceArray(0).getSpecies(),species,complexes_list,entities,entityReferences,sbml.getSbml(),biopax,"c"+r.getId());
					//cat.setCONTROLLER(pepr);
					PhysicalEntity pepp = createEntityForSpecies(r.getListOfProducts().getSpeciesReferenceArray(0).getSpecies(),species,complexes_list,entities,entityReferences,sbml.getSbml(),biopax,"c"+r.getId());
					cat.addParticipant(pepp);
					ctrl = cat;
					inter = cat;

					BiochemicalReaction bre = biopax_DASH_level3_DOT_owlFactory.createBiochemicalReaction(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = bre;
					cat.setControlled(bre);
				}


				if(rtype.equals("HETERODIMER_ASSOCIATION")){
					ComplexAssembly cas = biopax_DASH_level3_DOT_owlFactory.createComplexAssembly(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = cas;
					inter = cas;
				}

				if(rtype.equals("DISSOCIATION")){
					ComplexAssembly cas = biopax_DASH_level3_DOT_owlFactory.createComplexAssembly(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = cas;
					inter = cas;
				}

				if(rtype.equals("STATE_TRANSITION")){
					BiochemicalReaction bre = biopax_DASH_level3_DOT_owlFactory.createBiochemicalReaction(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = bre;
					inter = bre;
				}

				if(rtype.equals("TRUNCATION")){
					BiochemicalReaction bre = biopax_DASH_level3_DOT_owlFactory.createBiochemicalReaction(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = bre;
					inter = bre;
				}


				if(rtype.equals("KNOWN_TRANSITION_OMITTED")){
					BiochemicalReaction bre = biopax_DASH_level3_DOT_owlFactory.createBiochemicalReaction(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = bre;
					inter = bre;
				}

				if(rtype.equals("UNKNOWN_TRANSITION")){
					BiochemicalReaction bre = biopax_DASH_level3_DOT_owlFactory.createBiochemicalReaction(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
					conv = bre;
					inter = bre;
				}

				if(rtype.equals("TRANSCRIPTIONAL_ACTIVATION")){

				}

				if(rtype.equals("TRANSCRIPTIONAL_INHIBITION")){

				}

				if(rtype.equals("TRANSLATIONAL_ACTIVATION")){

				}

				if(rtype.equals("TRANSLATIONAL_INHIBITION")){

				}

				if(rtype.equals("TRUNCATION")){

				}

				// Here we produce catalysis reactions
				if(r.getListOfModifiers()!=null)
					for(int j=0;j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length;j++){
						String id = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
						//PhysicalEntity pep = createEntityParticipantForSpecies(id,species,complexes_list,physEntities,sbml.getSbml(),biopax,r.getId());
						PhysicalEntity pep = (PhysicalEntity)entities.get(id);
						if(pep==null)
							pep = createEntityForSpecies(id,species,complexes_list,entities,entityReferences,sbml.getSbml(),biopax,r.getId());
						//pep.setSTOICHIOMETRIC_DASH_COEFFICIENT(new Double(1));
						Catalysis cat = biopax_DASH_level3_DOT_owlFactory.createCatalysis(biopax.namespaceString+r.getId()+"_c_"+id,biopax.biopaxmodel);
						path.addPathwayComponent(cat);
						cat.addController(pep);
						cat.setControlled(inter);
						if(r.getAnnotation()!=null)
							if(r.getAnnotation().getCelldesignerListOfModification()!=null){
								//for(int k=0;k<r.getReactionAnnotation().getCelldesigner_listOfModification().getCellDesignerModifications().size();k++){
								CelldesignerModificationDocument.CelldesignerModification modif = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
								String mtyp = "";
								try{
									mtyp =  modif.getType().toString();
								}catch(Exception e){
									mtyp = "UNKNOWN_CATALYSIS";
									System.out.println("WARNING!!! Catalysis type not well defined for "+r.getId()+" (modifier "+id+")");
								}
								if(mtyp.equals("CATALYSIS")||mtyp.equals("UNKNOWN_CATALYSIS")){
									cat.setControlType("ACTIVATION");
								}
								if(mtyp.equals("INHIBITION")||mtyp.equals("UNKNOWN_INHIBITION")){
									cat.setControlType("INHIBITION");
								}
								//if(mtyp.equals("UNKNOWN"))
								//  cat.setCONTROL_DASH_TYPE("");
								cat.addComment("CDTYPE:"+mtyp);
								//cat.addInteractionType((ControlledVocabulary)cellDesignerTerms.get(mtyp));
							}
					}

				if(inter==null){
					System.out.println("INTER = NULL! "+r.getId());
				}else{
					path.addPathwayComponent(inter);
					inter.addComment("CDTYPE:"+rtype);
					//inter.addINTERACTION_DASH_TYPE((openControlledVocabulary)cellDesignerTerms.get(rtype));
				}
				if(inter!=null)if(r.getNotes()!=null){
					//System.out.println("COMMENT: "+r.getNotes().getHtml().getBody());
					inter.addComment(Utils.getValue(r.getNotes()));
					Vector v = extractPubMedReferenceFromComment(Utils.getValue(r.getNotes()));
					addPublicationXrefsToEntity(inter,v);
				}
				if(conv!=null){
					boolean iscontrol = (ctrl!=null);
						for(int j=0;j<r.getListOfReactants().getSpeciesReferenceArray().length;j++){
							String id = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
							PhysicalEntity pep = createEntityForSpecies(id,species,complexes_list,entities,entityReferences,sbml.getSbml(),biopax,r.getId());
							SpeciesReferenceDocument.SpeciesReference sr = r.getListOfReactants().getSpeciesReferenceArray(j);
							double st = 1;
							if((sr.getStoichiometry()!=null)&&(!sr.getStoichiometry().equals("")))
								st = Double.parseDouble(sr.getStoichiometry());
							//pep.setSTOICHIOMETRIC_DASH_COEFFICIENT(new Double(st));
							if(pep!=null){
								conv.addLeft(pep);
								//pep.setSTOICHIOMETRIC_DASH_COEFFICIENT(new Double(st));
							}
						}
					for(int j=0;j<r.getListOfProducts().getSpeciesReferenceArray().length;j++){
						String id = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
						PhysicalEntity pep = createEntityForSpecies(id,species,complexes_list,entities,entityReferences,sbml.getSbml(),biopax,r.getId());
						SpeciesReferenceDocument.SpeciesReference sr = r.getListOfProducts().getSpeciesReferenceArray(j);
						double st = 1;
						if((sr.getStoichiometry()!=null)&&(!sr.getStoichiometry().equals("")))
								st = Double.parseDouble(sr.getStoichiometry());
						if(pep!=null){
							conv.addRight(pep);
							//pep.setSTOICHIOMETRIC_DASH_COEFFICIENT(new Double(st));
						}
					}
				}
			}

			// Now we store SBML-specific and layout information
			if(CellDesignerToBioPAXConverter.useBiopaxModelOntology){
				// Parameters
				// Kinetic Laws
				// Rules
				// Events
				// Layout
			}

		*/
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * Creates the list of proteins 
	 */
	public static HashMap<String, CelldesignerProteinDocument.CelldesignerProtein> getProteins(SbmlDocument.Sbml sbml){
		HashMap<String, CelldesignerProteinDocument.CelldesignerProtein> v = new HashMap<String, CelldesignerProteinDocument.CelldesignerProtein>();
		if(sbml.getModel().getAnnotation()!=null)if(sbml.getModel().getAnnotation().getCelldesignerListOfProteins()!=null){
			for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray().length;i++){
				CelldesignerProteinDocument.CelldesignerProtein prot = sbml.getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
				v.put(prot.getId(),prot);
			}}else{
				// if the model is not annotated at all, we are going to create pseudo-entitites (proteins)
					for(int i=0;i<sbml.getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
						SpeciesDocument.Species sp = sbml.getModel().getListOfSpecies().getSpeciesArray(i);
						CelldesignerProteinDocument.CelldesignerProtein prot = CelldesignerProteinDocument.Factory.newInstance().addNewCelldesignerProtein();
						if(sp.getName()!=null){
							XmlString xs = XmlString.Factory.newInstance();
							String name = Utils.getValue(sp.getName());
							xs.setStringValue(name);    				
							prot.setName(xs);
						}
						else{
							XmlString xs = XmlString.Factory.newInstance();
							xs.setStringValue(sp.getId());
							prot.setName(xs);
						}
						prot.setId(sp.getId());
						v.put(prot.getId(),prot);
					}
			}
		return v;
	}
	/**
	 * Creates the list of genes 
	 */
	public static HashMap<String, CelldesignerGeneDocument.CelldesignerGene> getGenes(SbmlDocument.Sbml sbml){
		HashMap<String, CelldesignerGeneDocument.CelldesignerGene> v = new HashMap<String, CelldesignerGeneDocument.CelldesignerGene>();
		if(sbml.getModel().getAnnotation()!=null)if(sbml.getModel().getAnnotation().getCelldesignerListOfGenes()!=null){
		for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
		  CelldesignerGeneDocument.CelldesignerGene prot = sbml.getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
		  v.put(prot.getId(),prot);
		}}
		return v;
	}
	
	public static HashMap<String, CelldesignerRNADocument.CelldesignerRNA> getRNAs(SbmlDocument.Sbml sbml){
		HashMap<String, CelldesignerRNADocument.CelldesignerRNA> v = new HashMap<String, CelldesignerRNADocument.CelldesignerRNA>();
		if(sbml.getModel().getAnnotation()!=null)if(sbml.getModel().getAnnotation().getCelldesignerListOfRNAs()!=null){
		for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
		  CelldesignerRNADocument.CelldesignerRNA prot = sbml.getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
		  v.put(prot.getId(),prot);
		}}
		return v;
	}

	public static HashMap<String, CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA> getAntisenseRNAs(SbmlDocument.Sbml sbml){
		HashMap<String, CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA> v = new HashMap<String, CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA>();
		if(sbml.getModel().getAnnotation()!=null)if(sbml.getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs()!=null){
		for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().sizeOfCelldesignerAntisenseRNAArray();i++){
		  CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA prot = sbml.getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
		  v.put(prot.getId(),prot);
		}}
		return v;
	}
	

	public CelldesignerProteinDocument.CelldesignerProtein getProteinById(String pid){
		return proteins.get(pid);
	}
	public CelldesignerRNADocument.CelldesignerRNA getRNAById(String pid){
		return rnas.get(pid);
	}
	public CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA getAntisenseRNAById(String pid){
		return asrnas.get(pid);
	}
	

	/**
	 * Creates the list of reactions 
	 */
	public static HashMap<String, ReactionDocument.Reaction> getReactions(SbmlDocument.Sbml sbml){
		HashMap<String, ReactionDocument.Reaction> v = new HashMap<String, ReactionDocument.Reaction>(); 
		if(sbml.getModel().getListOfReactions()!=null)
			for(int i=0;i<sbml.getModel().getListOfReactions().getReactionArray().length;i++){
				ReactionDocument.Reaction r = sbml.getModel().getListOfReactions().getReactionArray(i);
				v.put(r.getId(),r);
			}
		return v;
	}

	public ReactionDocument.Reaction getReactionById(String id){
		return reactions.get(id);
	}

	/**
	 * Creates the list of species 
	 */
	public static HashMap<String, SpeciesDocument.Species> getSpecies(SbmlDocument.Sbml sbml){
		HashMap<String, SpeciesDocument.Species> v = new HashMap<String, SpeciesDocument.Species>();
		for(int i=0;i<sbml.getModel().getListOfSpecies().getSpeciesArray().length;i++){
			SpeciesDocument.Species s = sbml.getModel().getListOfSpecies().getSpeciesArray(i);
			v.put(s.getId(),s);
		}
		return v;
	}
	
	public static HashMap<String, CelldesignerSpeciesDocument.CelldesignerSpecies> getIncludedSpecies(SbmlDocument.Sbml sbml){
		HashMap<String, CelldesignerSpeciesDocument.CelldesignerSpecies> v = new HashMap<String, CelldesignerSpeciesDocument.CelldesignerSpecies>();
		if(sbml.getModel().getAnnotation()!=null)
		if(sbml.getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
		for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
			CelldesignerSpeciesDocument.CelldesignerSpecies s = sbml.getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
			v.put(s.getId(),s);
		}
		return v;
	}
	

	public SpeciesDocument.Species getSpeciesById(String id){
		return species.get(id);
	}

	/**
	 * Creates the list of complexes 
	 */
	public static HashMap<String, CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias> getComplexes(SbmlDocument.Sbml sbml){
		HashMap<String, CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias> v = new HashMap<String, CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias>();
		if(sbml.getModel().getAnnotation()!=null)if(sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
			for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
				CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias c = sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
				v.put(c.getId(),c);
			}
		return v;
	}

	public CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias getComplexById(String id){
		return complexes.get(id);
	}

	/**
	 * Creates the map of compartments 
	 */
	public static HashMap getCompartmentHash(SbmlDocument.Sbml sbml){
		HashMap hm = new HashMap();
		for(int i=0;i<sbml.getModel().getListOfCompartments().getCompartmentArray().length;i++){
			CompartmentDocument.Compartment cp = sbml.getModel().getListOfCompartments().getCompartmentArray(i);
			hm.put(cp.getId(),cp);
		}
		return hm;
	}

	/**
	 * Makes a physical entity from CellDesigner species   
	 */
	
	public PhysicalEntity createEntityForSpecies(String id, SbmlDocument.Sbml sbml, BioPAX biopax) throws Exception{
		return createEntityForSpecies(id, sbml, biopax, false);
	}
	
	public PhysicalEntity createEntityForSpecies(String id, SbmlDocument.Sbml sbml, BioPAX biopax, boolean complexComponent) throws Exception{
		PhysicalEntity pep = null;
		SpeciesDocument.Species sp = species.get(id);
		CelldesignerSpeciesDocument.CelldesignerSpecies isp = null;		
		String spname = "";
		String spname_native = "";
		String sp_id = "";
		CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity si = null;		
		if(sp!=null){
			spname_native = Utils.getValue(sp.getName());
			spname = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,sp.getId(),true,true);
			if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null)
				si = sp.getAnnotation().getCelldesignerSpeciesIdentity();
			sp_id = sp.getId();
		}else{
			isp = includedSpecies.get(id);
			spname_native = Utils.getValue(isp.getName());
			spname = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,isp.getId(),true,true);
			si = isp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
			sp_id = isp.getId();
		}
		
		if(spname==null) return null;
				
		if(complexComponent){
			for(int i=2;i<10;i++){
				String nm = spname.split("@")[0];
				//String comp = spname.split("@")[1];				
				String s = "|hm"+i;
				if(nm.endsWith(s))
					spname = nm.substring(0,nm.length()-s.length());
			}
		}
		
		String entityUri = biopax.namespaceString+correctName(spname);
		String cl = "UNKNOWN";

		if(entities.get(entityUri)==null){
			if((spname!=null)&&(!spname.toLowerCase().startsWith("null"))){
				String eid = "";
				if(si!=null)
					cl = Utils.getValue(si.getCelldesignerClass());
				if(cl.equals("PROTEIN")){
					
					boolean homodimer = false;
					if(si.getCelldesignerState()!=null)
						if(si.getCelldesignerState().getCelldesignerHomodimer()!=null)
							homodimer = true;
					if(homodimer&&!complexComponent){
						Complex compl = biopax_DASH_level3_DOT_owlFactory.createComplex(entityUri,biopax.biopaxmodel);
						System.out.println("Created homodimer as complex "+compl.uri());
						compl.addName(spname);
						int ihm = Integer.parseInt(Utils.getValue(si.getCelldesignerState().getCelldesignerHomodimer()));
						Stoichiometry stoich = biopax_DASH_level3_DOT_owlFactory.createStoichiometry(entityUri+"_stoich", biopax.biopaxmodel);
						stoich.setStoichiometricCoefficient((float)ihm);
						PhysicalEntity pepcomp = createEntityForSpecies(id, sbml, biopax, true);
						stoich.setPhysicalEntity(pepcomp);
						compl.addComponent(pepcomp);
						compl.addComponentStoichiometry(stoich);
						pep = compl;
					}else{
						Protein protein = biopax_DASH_level3_DOT_owlFactory.createProtein(entityUri,biopax.biopaxmodel);
						System.out.println("Created protein "+protein.uri());
						protein.addName(spname);
						protein.setEntityReference(entityReferences.get(Utils.getValue(si.getCelldesignerProteinReference())));
						entities.put(entityUri, protein);
						pep = protein;
					}
				}else
				if(cl.equals("COMPLEX")){
					Complex compl = biopax_DASH_level3_DOT_owlFactory.createComplex(entityUri,biopax.biopaxmodel);
					System.out.println("Created complex "+compl.uri());
					compl.addName(spname);
					Vector<CelldesignerSpeciesDocument.CelldesignerSpecies> vis = CellDesignerToCytoscapeConverter.complexSpeciesMap.get(id);
					if(vis==null){
						System.out.println("ERROR: vis = null for id = "+id+", entityUri="+entityUri);
					}else{
					for(CelldesignerSpeciesDocument.CelldesignerSpecies ispc: vis){
						CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity ispi = ispc.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
						PhysicalEntity pepi = createEntityForSpecies(ispc.getId(),sbml,biopax, true);
						if(pepi==null)
							System.out.println("ERROR: no entity for a complex "+entityUri+" component "+ispc.getId());
						else
							compl.addComponent(pepi);
					}}
					entities.put(entityUri, compl);	
					pep = compl;
				}else
				if(cl.equals("GENE")){
					Dna dna = biopax_DASH_level3_DOT_owlFactory.createDna(entityUri,biopax.biopaxmodel);
					System.out.println("Created dna "+dna.uri());
					dna.addName(spname);
					dna.setEntityReference(entityReferences.get(Utils.getValue(si.getCelldesignerGeneReference())));
					entities.put(entityUri, dna);
					pep = dna;
				}else
				if(cl.equals("RNA")){
					Rna rna = biopax_DASH_level3_DOT_owlFactory.createRna(entityUri,biopax.biopaxmodel);
					System.out.println("Created rna "+rna.uri());					
					rna.addName(spname);
					rna.setEntityReference(entityReferences.get(Utils.getValue(si.getCelldesignerRnaReference())));
					entities.put(entityUri, rna);
					pep = rna;
				}else
				if(cl.equals("UNKNOWN")){
					Protein p = biopax_DASH_level3_DOT_owlFactory.createProtein(entityUri,biopax.biopaxmodel);
					System.out.println("Created unknown as protein "+p.uri());					
					p.addName(spname_native);
					p.setEntityReference(entityReferences.get(id+"_ref"));
					pep = p;
					entities.put(entityUri, pep);
				}else
				if(cl.equals("ANTISENSE_RNA")){
					Rna rna = biopax_DASH_level3_DOT_owlFactory.createRna(entityUri,biopax.biopaxmodel);
					System.out.println("Created rna "+rna.uri());					
					rna.addName(spname);
					rna.setEntityReference(entityReferences.get(Utils.getValue(si.getCelldesignerAntisensernaReference())));
					entities.put(entityUri, rna);
					pep = rna;
				}else
				if(cl.equals("PHENOTYPE")){
					Protein p = biopax_DASH_level3_DOT_owlFactory.createProtein(entityUri,biopax.biopaxmodel);
					System.out.println("Created phenotype as protein "+p.uri());					
					p.addName(spname_native);
					p.setEntityReference(entityReferences.get(id+"_ref"));
					pep = p;
					entities.put(entityUri, pep);
				}else
				if(cl.equals("SIMPLE_MOLECULE")){
					SmallMolecule sm = biopax_DASH_level3_DOT_owlFactory.createSmallMolecule(entityUri,biopax.biopaxmodel);
					System.out.println("Created smallMolecule "+sm.uri());					
					sm.addName(spname_native);
					sm.setEntityReference(entityReferences.get(id+"_ref"));
					pep = sm;
					entities.put(entityUri, sm);
				}else
				if(cl.equals("ION")){
					SmallMolecule sm = biopax_DASH_level3_DOT_owlFactory.createSmallMolecule(entityUri,biopax.biopaxmodel);
					System.out.println("Created smallMolecule "+sm.uri());
					sm.addName(spname_native);
					sm.setEntityReference(entityReferences.get(id+"_ref"));
					pep = sm;
					entities.put(entityUri, sm);
				}else
				if(cl.equals("DRUG")){
					SmallMolecule sm = biopax_DASH_level3_DOT_owlFactory.createSmallMolecule(entityUri,biopax.biopaxmodel);
					System.out.println("Created smallMolecule "+sm.uri());
					sm.addName(spname_native);
					sm.setEntityReference(entityReferences.get(id+"_ref"));	
					pep = sm;
					entities.put(entityUri, pep);
				}
				
				
			}
		
		if(pep==null){
			//System.out.println("No species found for "+id+" "+cl);
			System.out.println("Unknown species type! "+id+" "+cl);
		}else{
			
			// Set location
			if(!complexComponent){
			CellularLocationVocabulary comp = null;
			if(sp!=null){
					String scomp = sp.getCompartment();
					CompartmentDocument.Compartment bcomp = (CompartmentDocument.Compartment)compartmentHash.get(scomp);
					if(bcomp.getName()!=null)
								comp = biopax.getCompartment(bcomp.getName().getStringValue());
					else
								comp = biopax.getCompartment("0008372");
			}
			else{
								String compl_id = Utils.getValue(isp.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
								SpeciesDocument.Species spt = species.get(compl_id);
								String compartment = spt.getCompartment();
								CompartmentDocument.Compartment compo = (CompartmentDocument.Compartment)compartmentHash.get(compartment);
								String compname = compartment;
								if(compo.getName()!=null)
										compname = compo.getName().getStringValue();
								comp = biopax.getCompartment(compname);
			}

			pep.setCellularLocation((CellularLocationVocabulary)comp);
			}
			
			// Add comments and publications to Entity (not Reference)
			if(sp!=null)
				if(sp.getNotes()!=null){
					pep.addComment(Utils.cutFirstLastNonVisibleSymbols(Utils.getValue(sp.getNotes())));
					Vector v = extractPubMedReferenceFromComment(Utils.getValue(sp.getNotes()));
					addPublicationXrefsToEntity(pep,v);
				}

			// Set modifications
			addModification(sp_id, pep, si);
		}
		
		}else{
			pep = entities.get(entityUri);
		}
		
		
		return pep;
	}
	
	
	public PhysicalEntity createEntityForSpecies(String id, HashMap<String, SpeciesDocument.Species> species, Vector complexes_list, HashMap<String, PhysicalEntity> entities, HashMap<String, EntityReference> entityReferences, SbmlDocument.Sbml sbml, BioPAX biopax, String rId){
		
		PhysicalEntity pep = null;
		/*
		try{
			
			
			SpeciesDocument.Species sp = species.get(id);
			CelldesignerSpeciesDocument.CelldesignerSpecies isp = null;
			String sp_id = "";
			String spname = "";
			CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity si = null;
			if(sp!=null){
				spname = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,sp.getId(),true,true);
				if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null)
					si = sp.getAnnotation().getCelldesignerSpeciesIdentity();
				sp_id = sp.getId();
			}else{
				for(int j=0;j<sbml.getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray().length;j++){
					isp = sbml.getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(j);
					if(isp.getId().equals(id)){
						//spname = "in_"+Utils.getValue(isp.getCelldesignerAnnotation().getCelldesignerComplexSpecies())+"_"+CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,isp.getId(),true,true);
						spname = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,isp.getId(),true,true);
						si = isp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
						sp_id = isp.getId();
					}
				}
			}
			
			
			if(spname==null) return null;
			//System.out.println("ERROR: spname is null for species "+id);
			String entityUri = biopax.namespaceString+Utils.correctName(spname);

			if(entities.get(entityUri)==null){
			
			//physicalEntityParticipant pep = biopax_DASH_level3_DOT_owlFactory.createphysicalEntityParticipant(biopax.namespaceString+sp.getId(),biopax.biopaxmodel);
			if((spname!=null)&&(!spname.toLowerCase().startsWith("null"))){
				String eid = "";

				String cl = "UNKNOWN";
				if(si!=null)
					cl = Utils.getValue(si.getCelldesignerClass());

				//if((si!=null)&&(si.getCelldesignerState()!=null)&&(si.getCelldesignerState().getCelldesignerHomodimer()!=null)){
				if((si!=null)&&(si.getCelldesignerState()!=null)){
					boolean reacfound = false;
					for(int kk=0;kk<reactions.size();kk++){
						ReactionDocument.Reaction r = (ReactionDocument.Reaction)reactions.elementAt(kk);
						if(r.getId().equals(rId))
						{ reacfound = true; break; }
					}
					if(reacfound)
						eid = sp.getId();
					else
						eid = Utils.getValue(si.getCelldesignerProteinReference());
				}{
					
					if(cl.equals("PROTEIN")){
						//pep = biopax_DASH_level3_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+rId+"_"+Utils.correctName(spname),biopax.biopaxmodel);
						if(entities.get(entityUri)==null){
							Protein protein = biopax_DASH_level3_DOT_owlFactory.createProtein(entityUri,biopax.biopaxmodel);
							System.out.println("Created protein "+protein.uri());
							protein.addName(spname);
							protein.setEntityReference(entityReferences.get(Utils.getValue(si.getCelldesignerProteinReference())));
							entities.put(entityUri, protein);
							pep = protein;
						}else{
							pep = entities.get(entityUri);
						}
						eid = Utils.getValue(si.getCelldesignerProteinReference());
						
					}else
					if(cl.equals("COMPLEX")){
							//pep = biopax_DASH_level3_DOT_owlFactory.createphysicalEntityParticipant(biopax.namespaceString+rId+"_"+Utils.correctName(spname),biopax.biopaxmodel);
							//pep = biopax_DASH_level3_DOT_owlFactory.createComplex(biopax.namespaceString+entityUri,biopax.biopaxmodel);
							pep = (PhysicalEntity)entities.get(sp.getId());
							eid = getComplexById(complexes_list,sp.getId()).getSpecies();
					}else
					if(cl.equals("GENE")){
								//pep = biopax_DASH_level3_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+rId+"_"+Utils.correctName(spname),biopax.biopaxmodel);
								//TODO check this Gene is not a PE
							if(entities.get(entityUri)==null){
								Dna dna = biopax_DASH_level3_DOT_owlFactory.createDna(entityUri,biopax.biopaxmodel);
								dna.addName(spname);
								dna.setEntityReference(entityReferences.get(Utils.getValue(si.getCelldesignerGeneReference())));
								entities.put(entityUri, dna);
								pep = dna;
								}else{
									pep = entities.get(entityUri);
								}								
								eid = Utils.getValue(si.getCelldesignerGeneReference());
					}else
					if(cl.equals("RNA")){
							if(entities.get(entityUri)==null){
								Rna rna = biopax_DASH_level3_DOT_owlFactory.createRna(entityUri,biopax.biopaxmodel);
								rna.addName(spname);
								rna.setEntityReference(entityReferences.get(Utils.getValue(si.getCelldesignerRnaReference())));
								entities.put(entityUri, rna);
								pep = rna;
								}else{
										pep = entities.get(entityUri);
								}	
								eid = Utils.getValue(si.getCelldesignerRnaReference());
					}else
					if(cl.equals("UNKNOWN")){
							if(entities.get(entityUri)==null){
								pep = biopax_DASH_level3_DOT_owlFactory.createPhysicalEntity(entityUri,biopax.biopaxmodel);
								pep.addName(spname);
								entities.put(entityUri, pep);
								}else{
										pep = entities.get(entityUri);
								}											
								eid = sp_id;
					}else
					if(cl.equals("PHENOTYPE")){
							if(entities.get(entityUri)==null){
								pep = biopax_DASH_level3_DOT_owlFactory.createPhysicalEntity(entityUri,biopax.biopaxmodel);
								pep.addName(spname);
								entities.put(entityUri, pep);
								}else{
										pep = entities.get(entityUri);
								}											
								eid = sp_id;
					}else
					if(cl.equals("ANTISENSE_RNA")){
							if(entities.get(entityUri)==null){
								Rna rna = biopax_DASH_level3_DOT_owlFactory.createRna(entityUri,biopax.biopaxmodel);
								rna.addName(spname);
								rna.setEntityReference(entityReferences.get(Utils.getValue(si.getCelldesignerAntisensernaReference())));
								entities.put(entityUri, rna);
								pep = rna;
								}else{
										pep = entities.get(entityUri);
								}												
								eid = Utils.getValue(si.getCelldesignerAntisensernaReference());
					}else
					if(cl.equals("ION")){
							if(entities.get(entityUri)==null){
								pep = biopax_DASH_level3_DOT_owlFactory.createSmallMolecule(entityUri,biopax.biopaxmodel);
								pep.addName(spname);
								entities.put(entityUri, pep);
								}else{
										pep = entities.get(entityUri);
								}													
								eid = sp_id;
					}else
					if(cl.equals("SIMPLE_MOLECULE")){
							if(entities.get(entityUri)==null){
								pep = biopax_DASH_level3_DOT_owlFactory.createSmallMolecule(entityUri,biopax.biopaxmodel);
								pep.addName(spname);
								entities.put(entityUri, pep);
								}else{
										pep = entities.get(entityUri);
								}	
								eid = sp_id;
					}
					

					if(pep==null){
							System.out.println("Unknown species type! "+sp.getId()+" "+cl);
					}else{

							ControlledVocabulary comp = null;
							if(sp!=null){
									String scomp = sp.getCompartment();
									CompartmentDocument.Compartment bcomp = (CompartmentDocument.Compartment)compartmentHash.get(scomp);
									if(bcomp.getName()!=null)
												comp = biopax.getCompartment(bcomp.getName().getStringValue());
									else
												comp = biopax.getCompartment("0008372");
							}
							else{
												String compartment = "";
												String compl_id = Utils.getValue(isp.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
												for(int k=0;k<sbml.getModel().getListOfSpecies().getSpeciesArray().length;k++){
														SpeciesDocument.Species spt = sbml.getModel().getListOfSpecies().getSpeciesArray(k);
														if(spt.getId().equals(compl_id)){
																		compartment = spt.getCompartment();
														}
												}
												CompartmentDocument.Compartment compo = (CompartmentDocument.Compartment)compartmentHash.get(compartment);
												String compname = compartment;
												if(compo.getName()!=null){
														compname = compo.getName().getStringValue();
												}
												comp = biopax.getCompartment(compname);
							}

							pep.setCellularLocation((CellularLocationVocabulary)comp);


							if(sp!=null)
								if(sp.getNotes()!=null){
									pep.addComment(Utils.getValue(sp.getNotes()));
									Vector v = extractPubMedReferenceFromComment(Utils.getValue(sp.getNotes()));
									addPublicationXrefsToEntity(pep,v);
								}


							addModification(sp_id, pep, si);

						} // end pep is not null
				} // end si is not null
			} // end spname is not null
			}else{ pep = entities.get(entityUri);} // if the entity already generated
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(pep==null)
			System.out.println("ERROR: no entity for the species "+id+" is generated");
		*/
		return pep;
	}
	
	public void addModification(String sp_id, PhysicalEntity pep, CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity si) throws Exception{
		// Now we add modifications
		if((si!=null)&&(si.getCelldesignerState()!=null)){
			CelldesignerListOfModificationsDocument.CelldesignerListOfModifications lmodifs = si.getCelldesignerState().getCelldesignerListOfModifications();
			if(lmodifs!=null){
					CelldesignerModificationDocument.CelldesignerModification modifs[] = lmodifs.getCelldesignerModificationArray();
					for(int j=0;j<modifs.length;j++){
							CelldesignerModificationDocument.CelldesignerModification cm = (CelldesignerModificationDocument.CelldesignerModification)modifs[j];
							//System.out.print("\t"+cm.getResidue()+"_"+cm.getState());
							String resname = CellDesignerToCytoscapeConverter.getNameOfModificationResidue(sbml,si,cm.getResidue());
							String stt = cm.getState().getStringValue();
							stt = stt.substring(0,3);
							stt = Utils.replaceString(stt," ","_");
							String s = "";
							if(resname==null)
								s+="s"+(j+1)+"_"+stt;
							else
								s+=""+resname+"_"+stt;
							
							
							ModificationFeature sf = biopax_DASH_level3_DOT_owlFactory.createModificationFeature(biopax.namespaceString+sp_id+"_"+s,biopax.biopaxmodel);
							String state = cm.getState().getStringValue();
							if(state.equals("unknown")) state+="_modification";
							
							
							SequenceModificationVocabulary modif = biopax_DASH_level3_DOT_owlFactory.createSequenceModificationVocabulary(biopax.namespaceString+s, biopax.biopaxmodel);
							modif.addTerm(s);
							//System.out.println("MODIFICATION: "+s+" resname"+resname);
							sf.setModificationType(modif);
							
							//sf.setModificationType((SequenceModificationVocabulary)cellDesignerTerms.get(state));
							
							//SequenceSite site = biopax_DASH_level3_DOT_owlFactory.createSequenceSite(biopax.namespaceString+sp_id+""+s+"_site",biopax.biopaxmodel);;
							//sf.addFeatureLocation(site);
							//sf.setModificationType(modificationType);
							
							
							pep.addFeature(sf);
					}
			}
		} // --------- adding modifications ------------
	}
	
	public PhysicalEntity createEntityForHomodimer(String id, HashMap<String, SpeciesDocument.Species> species, Vector complexes_list, HashMap<String, PhysicalEntity> entities, HashMap<String, EntityReference> entityReferences, SbmlDocument.Sbml sbml, BioPAX biopax, String rId){
		PhysicalEntity pep = null;
		try{
		SpeciesDocument.Species sp = species.get(id);
		EntityReference proteinRef = entityReferences.get(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()));
		String spname = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,sp.getId(),true,true);
		
		StringTokenizer st = new StringTokenizer(spname,"|");
		String lastModif = null;
		while(st.hasMoreTokens())
			lastModif = st.nextToken();
		if(lastModif.startsWith("hm")){
			spname = spname.substring(0, spname.length()-lastModif.length()-1);
		}
		
		String entityUri = biopax.namespaceString+Utils.correctName(spname);
		if(entities.get(entityUri)!=null)
			pep = entities.get(entityUri);
		else{
			Protein protein = biopax_DASH_level3_DOT_owlFactory.createProtein(entityUri,biopax.biopaxmodel);
			System.out.println("Created protein "+protein.uri());
			protein.addName(spname);
			protein.setEntityReference(proteinRef);
			entities.put(entityUri, protein);
			pep = protein;
		}
		
		addModification(id, pep, sp.getAnnotation().getCelldesignerSpeciesIdentity());
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return pep;
	}

	/**
	 * Extracts pubmed ids from a string in the form PMID: xxxxxx  and creates publicationXref list
	 */
	public Vector extractPubMedReferenceFromComment(String comment){
		Vector refs = new Vector();
		try{
			String pubmedid = "";
			StringTokenizer st = new StringTokenizer(comment," :\r\n\t;.,");
			String s = "";
			while(st.hasMoreTokens()){
				String ss = st.nextToken();
				//System.out.println(ss);
				if(ss.toLowerCase().equals("pmid")){
					if(st.hasMoreTokens()){
						pubmedid = st.nextToken();
						PublicationXref xref = (PublicationXref)pubmedrefsHash.get(pubmedid);
						if(xref==null){
							xref = biopax_DASH_level3_DOT_owlFactory.createPublicationXref(biopax.namespaceString+"Pubmed_"+pubmedid,biopax.biopaxmodel);
							xref.setDb("Pubmed");
							xref.setId(pubmedid);
							pubmedrefsHash.put(pubmedid,xref);
						}
						refs.add(xref);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return refs;
	}

	public void addPublicationXrefsToEntity(Entity ent, Vector v){
		try{
			Vector present = new Vector();
			for(int i=0;i<v.size();i++){
				PublicationXref xref = (PublicationXref)v.elementAt(i);
				if(present.indexOf(xref)<0)
					ent.addXref((Xref)xref);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void addPublicationXrefsToEntityReference(EntityReference ent, Vector v){
		try{
			Vector present = new Vector();
			for(int i=0;i<v.size();i++){
				PublicationXref xref = (PublicationXref)v.elementAt(i);
				if(present.indexOf(xref)<0)
					ent.addXref((Xref)xref);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	/**
	 * Eliminates spaces, stars, dashes from the string 
	 */
	public static String correctName(String s){
    	s = Utils.replaceString(s, "@", "_at_");
    	s = Utils.replaceString(s, ":", "_");
    	s = Utils.replaceString(s, "|", "_");
        char c[] = s.toCharArray();
        for(int i=0;i<c.length;i++){
        	if((c[i]>='a')&&(c[i]<='z')) continue;
        	if((c[i]>='A')&&(c[i]<='Z')) continue;
        	if((c[i]>='0')&&(c[i]<='9')) continue;
        	if(c[i]=='_') continue;
        	c[i] = '_';
        }
        s = new String(c);
        if((c[0]>='0')&&(c[0]<='9'))
        	s = "id_"+s;
        //System.out.println("AFTER : "+s);
    	return s;
	}

	/**
	 * Encodes a list of standard terms (protein modification types (GOs) and
	 * influence types) by controlledVocabulary terms
	 */
	public void createCellDesignerTerms(){
		try{
			ControlledVocabulary voc = null;
			String s = null;

			s = "phosphorylation;phosphorylated"; voc = biopax.addGOTerm("0016310",s); cellDesignerTerms.put("phosphorylated",voc);
			s = "acetylation;acetylated"; voc = biopax.addGOTerm("0006473",s); cellDesignerTerms.put("acetylated",voc);
			s = "protein_ubiquitination;ubiquitinated"; voc = biopax.addGOTerm("0016567",s); cellDesignerTerms.put("ubiquitinated",voc);
			s = "methylation;methylated"; voc = biopax.addGOTerm("0032259",s); cellDesignerTerms.put("methylated",voc);
			s = "protein_amino_acid_hydroxylation;hydroxylated"; voc = biopax.addGOTerm("0018126",s); cellDesignerTerms.put("hydroxylated",voc);
			s = "post_translational_protein_modification;don't_care"; voc = biopax.addGOTerm("0043687",s); cellDesignerTerms.put("don't care",voc);
			s = "biopolymer_modification;unknown_modification"; voc = biopax.addGOTerm("0043412",s); cellDesignerTerms.put("unknown_modification",voc);

			s = "CATALYSIS";  voc = biopax_DASH_level3_DOT_owlFactory.createControlledVocabulary(biopax.namespaceString+"cd3_"+s,biopax.biopaxmodel); voc.addTerm(s); cellDesignerTerms.put(s,voc);
			s = "UNKNOWN_CATALYSIS";  voc = biopax_DASH_level3_DOT_owlFactory.createControlledVocabulary(biopax.namespaceString+"cd3_"+s,biopax.biopaxmodel); voc.addTerm(s); cellDesignerTerms.put(s,voc);
			s = "INHIBITION";  voc = biopax_DASH_level3_DOT_owlFactory.createControlledVocabulary(biopax.namespaceString+"cd3_"+s,biopax.biopaxmodel); voc.addTerm(s); cellDesignerTerms.put(s,voc);
			s = "UNKNOWN_INHIBITION";  voc = biopax_DASH_level3_DOT_owlFactory.createControlledVocabulary(biopax.namespaceString+"cd3_"+s,biopax.biopaxmodel); voc.addTerm(s); cellDesignerTerms.put(s,voc);
			s = "TRANSCRIPTIONAL_ACTIVATION";  voc = biopax_DASH_level3_DOT_owlFactory.createControlledVocabulary(biopax.namespaceString+"cd3_"+s,biopax.biopaxmodel); voc.addTerm(s); cellDesignerTerms.put(s,voc);
			s = "TRANSCRIPTIONAL_INHIBITION";  voc = biopax_DASH_level3_DOT_owlFactory.createControlledVocabulary(biopax.namespaceString+"cd3_"+s,biopax.biopaxmodel); voc.addTerm(s); cellDesignerTerms.put(s,voc);
			s = "TRANSLATIONAL_ACTIVATION";  voc = biopax_DASH_level3_DOT_owlFactory.createControlledVocabulary(biopax.namespaceString+"cd3_"+s,biopax.biopaxmodel); voc.addTerm(s); cellDesignerTerms.put(s,voc);
			s = "TRANSLATIONAL_INHIBITION";  voc = biopax_DASH_level3_DOT_owlFactory.createControlledVocabulary(biopax.namespaceString+"cd3_"+s,biopax.biopaxmodel); voc.addTerm(s); cellDesignerTerms.put(s,voc);
			s = "STATE_TRANSITION";  voc = biopax_DASH_level3_DOT_owlFactory.createControlledVocabulary(biopax.namespaceString+"cd3_"+s,biopax.biopaxmodel); voc.addTerm(s); cellDesignerTerms.put(s,voc);
			s = "UNKNOWN_TRANSITION";  voc = biopax_DASH_level3_DOT_owlFactory.createControlledVocabulary(biopax.namespaceString+"cd3_"+s,biopax.biopaxmodel); voc.addTerm(s); cellDesignerTerms.put(s,voc);
			s = "HETERODIMER_ASSOCIATION";  voc = biopax_DASH_level3_DOT_owlFactory.createControlledVocabulary(biopax.namespaceString+"cd3_"+s,biopax.biopaxmodel); voc.addTerm(s); cellDesignerTerms.put(s,voc);
			s = "DISSOCIATION";  voc = biopax_DASH_level3_DOT_owlFactory.createControlledVocabulary(biopax.namespaceString+"cd3_"+s,biopax.biopaxmodel); voc.addTerm(s); cellDesignerTerms.put(s,voc);
			s = "TRANSPORT";  voc = biopax_DASH_level3_DOT_owlFactory.createControlledVocabulary(biopax.namespaceString+"cd3_"+s,biopax.biopaxmodel); voc.addTerm(s); cellDesignerTerms.put(s,voc);
			s = "KNOWN_TRANSITION_OMITTED";  voc = biopax_DASH_level3_DOT_owlFactory.createControlledVocabulary(biopax.namespaceString+"cd3_"+s,biopax.biopaxmodel); voc.addTerm(s); cellDesignerTerms.put(s,voc);
			s = "TRUNCATION";  voc = biopax_DASH_level3_DOT_owlFactory.createControlledVocabulary(biopax.namespaceString+"cd3_"+s,biopax.biopaxmodel); voc.addTerm(s); cellDesignerTerms.put(s,voc);

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Returns true if the reaction r describes real interaction (not influence on a phenotype, for example)  
	 */
	public static boolean isRealReaction(ReactionDocument.Reaction r, SbmlDocument.Sbml sbml){
		boolean res = true;
		boolean foundrealreact = false;
		for(int i=0;i<r.getListOfReactants().getSpeciesReferenceArray().length;i++){
			SpeciesReferenceDocument.SpeciesReference sr = r.getListOfReactants().getSpeciesReferenceArray(i);
			String id = sr.getSpecies();
			String cl = "";
			for(int j=0;j<sbml.getModel().getListOfSpecies().getSpeciesArray().length;j++){
				SpeciesDocument.Species sp = sbml.getModel().getListOfSpecies().getSpeciesArray(j);
				if(sp.getId().equals(id)){
					if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null){
						cl = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
					}
					break;
				}
			}
			if(!cl.equals("PHENOTYPE")){
				foundrealreact = true; break;
			}
		}

		boolean foundrealprod = false;
		for(int i=0;i<r.getListOfProducts().getSpeciesReferenceArray().length;i++){
			SpeciesReferenceDocument.SpeciesReference sr = r.getListOfProducts().getSpeciesReferenceArray(i);
			String id = sr.getSpecies();
			String cl = "";
			for(int j=0;j<sbml.getModel().getListOfSpecies().getSpeciesArray().length;j++){
				SpeciesDocument.Species sp = sbml.getModel().getListOfSpecies().getSpeciesArray(j);
				if(sp.getId().equals(id)){
					if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null){
						cl = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
					}
					break;
				}
			}
			if(!cl.equals("PHENOTYPE")){
				foundrealprod = true; break;
			}
		}
		return foundrealprod&&foundrealreact;
	}



}
