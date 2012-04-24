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
	 * List of CellDesigner SpeciesDocument.Species objects
	 */
	public Vector species = null;
	/**
	 * List of CellDesigner ReactionDocument.Reaction objects
	 */  
	public Vector reactions = null;
	/**
	 * List of CellDesigner CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias objects
	 */  
	public Vector complexes_list = null;
	/**
	 * List of CellDesigner CelldesignerProteinDocument.CelldesignerProtein objects
	 */  
	public Vector proteins = null;

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
	public HashMap compartmentHash = null;
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
		BioPAX.addBiopaxModelOntology = CellDesignerToBioPAXConverter.useBiopaxModelOntology;
		alwaysMentionCompartment = true;
		biopax.makeCompartments();
		createCellDesignerTerms();
		compartmentHash = getCompartmentHash(sbml.getSbml());
		CellDesigner.entities = CellDesigner.getEntities(sbml);
		return populateModel();
	}

	/**
	 * The converter itself 
	 */
	public BioPAX populateModel(){
		try{
			
			CellDesignerToCytoscapeConverter.createSpeciesMap(sbml.getSbml());
			
			// First, create a pathway (one for everything)
			Pathway path = biopax_DASH_level3_DOT_owlFactory.createPathway(biopax.namespaceString+sbml.getSbml().getModel().getId(),biopax.biopaxmodel);
			path.addName(sbml.getSbml().getModel().getId());
			//path.setSHORT_DASH_NAME(filename);
			path.addComment("This pathway is generated automatically from the CellDesigner 3.0 XML file "+filename+" using CellDesigner2BioPAX converter developed by Andrei Zinovyev at the Bioinformatics Service of Institut Curie (andrei.zinovyev@curie.fr)");

			// Second, add all single species
			/*for(int i=0;i<sbml.getModel().getListOfSpecies().getSpeciess().size();i++){
      Species sp = sbml.getModel().getListOfSpecies().getSpecies(i);
      SpeciesAnnotation an = sp.getSpeciesAnnotation();
      if(an!=null){
        String cl = an.getCelldesigner_speciesIdentity().getCelldesigner_class();
        if(cl.equals("PROTEIN")){
          protein prot = biopax_DASH_level3_DOT_owlFactory.createprotein(biopax.namespaceString+sp.getId(),biopax.biopaxmodel);
          prot.setNAME(convertSpeciesToName(sbml,sp.getId(),true,true));
          prot.setSHORT_DASH_NAME(sp.getName());
        }
        if(cl.equals("ION")){
          smallMolecule sm = biopax_DASH_level3_DOT_owlFactory.createsmallMolecule(biopax.namespaceString+sp.getId(),biopax.biopaxmodel);
          sm.setNAME(convertSpeciesToName(sbml,sp.getId(),true,true));
          sm.setSHORT_DASH_NAME(sp.getName());
        }
      }
    }*/

			HashMap physEntities = new HashMap();
			species = getSpecies(sbml.getSbml());
			reactions = getReactions(sbml.getSbml());
			complexes_list = getComplexes(sbml.getSbml());
			proteins = getProteins(sbml.getSbml());

			//Phenotypes are treated as proteins
			for(int i=0;i<species.size();i++){
				SpeciesDocument.Species sp = (SpeciesDocument.Species)species.elementAt(i);
				if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null)
					if(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("PHENOTYPE")){
						System.out.println("Phenotype: "+sp.getId()+" - "+sp.getName().getStringValue());
						ProteinReference pheno = biopax_DASH_level3_DOT_owlFactory.createProteinReference(biopax.namespaceString+sp.getId(),biopax.biopaxmodel);
						pheno.addName(sp.getName().getStringValue());
						//pheno.setSHORT_DASH_NAME(sp.getName().getStringValue());
						String cleanname = correctName(sp.getName().getStringValue());
						pheno.addName(cleanname);
						pheno.addComment("This is a CellDesigner PHENOTYPE entity. It is not a real protein but rather an abstract representation of a process.");
						if(sp.getNotes()!=null){
							if(sp.getNotes().getHtml()!=null)if(sp.getNotes().getHtml().getBody()!=null)
								pheno.addComment(Utils.getValue(sp.getNotes().getHtml().getBody()));
						}
						physEntities.put(sp.getId(),pheno);
					}
			}

			for(int i=0;i<species.size();i++){
				SpeciesDocument.Species sp = (SpeciesDocument.Species)species.elementAt(i);
				if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null)
					if(Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("UNKNOWN")){
						System.out.println("Unknown: "+sp.getId()+" - "+sp.getName().getStringValue());
						EntityReference pheno = biopax_DASH_level3_DOT_owlFactory.createEntityReference(biopax.namespaceString+sp.getId(),biopax.biopaxmodel);
						pheno.addName(sp.getName().getStringValue());
						//pheno.setSHORT_DASH_NAME(sp.getName().getStringValue());
						String cleanname = correctName(sp.getName().getStringValue());
						pheno.addName(cleanname);
						pheno.addComment("This is a CellDesigner UNKNOWN entity.");
						if(sp.getNotes()!=null){
							if(sp.getNotes().getHtml()!=null)if(sp.getNotes().getHtml().getBody()!=null)
								pheno.addComment(Utils.getValue(sp.getNotes().getHtml().getBody()));
						}
						physEntities.put(sp.getId(),pheno);
					}
			}

			// Unique proteins
			for(int i=0;i<proteins.size();i++){
				CelldesignerProteinDocument.CelldesignerProtein prot = (CelldesignerProteinDocument.CelldesignerProtein)proteins.elementAt(i);
				System.out.println("Protein: "+prot.getName().getStringValue());
				ProteinReference protein_ = biopax_DASH_level3_DOT_owlFactory.createProteinReference(biopax.namespaceString+prot.getId(),biopax.biopaxmodel);
				protein_.addName(prot.getName().getStringValue());
				//protein_.setSHORT_DASH_NAME(prot.getName().getStringValue());
				String cleanname = correctName(prot.getName().getStringValue());
				protein_.addName(cleanname);
				if(prot.getCelldesignerNotes()!=null)if(prot.getCelldesignerNotes().getHtml()!=null)if(prot.getCelldesignerNotes().getHtml().getBody()!=null)
					protein_.addComment(Utils.getValue(prot.getCelldesignerNotes().getHtml().getBody()));
				physEntities.put(prot.getId(),protein_);
			}
			// Genes
			if(sbml.getSbml().getModel().getAnnotation()!=null)if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes()!=null)
				for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray().length;i++){
					CelldesignerGeneDocument.CelldesignerGene gene = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
					System.out.println("Gene: "+gene.getName());
					Dna dna_ = biopax_DASH_level3_DOT_owlFactory.createDna(biopax.namespaceString+gene.getId(),biopax.biopaxmodel);
					dna_.addName(gene.getName());
					dna_.addName(gene.getName());
					physEntities.put(gene.getId(),dna_);
				}
			// RNA
			if(sbml.getSbml().getModel().getAnnotation()!=null)if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs()!=null)
				for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray().length;i++){
					CelldesignerRNADocument.CelldesignerRNA RNA = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
					System.out.println("RNA: "+RNA.getName());
					Rna rna_ = biopax_DASH_level3_DOT_owlFactory.createRna(biopax.namespaceString+RNA.getId(),biopax.biopaxmodel);
					rna_.addName(RNA.getName());
					rna_.addName(RNA.getName());
					physEntities.put(RNA.getId(),rna_);
				}
			// Antisense RNA
			if(sbml.getSbml().getModel().getAnnotation()!=null)if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs()!=null)    
				for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray().length;i++){
					CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA aRNA = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
					System.out.println("aRNA: "+aRNA.getName());
					Rna rna_ = biopax_DASH_level3_DOT_owlFactory.createRna(biopax.namespaceString+aRNA.getId(),biopax.biopaxmodel);
					rna_.addName(aRNA.getName());
					rna_.addName(aRNA.getName());
					physEntities.put(aRNA.getId(),rna_);
				}
			// Small molecules
			for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray().length;i++){
				SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
				AnnotationDocument.Annotation an = sp.getAnnotation();
				if(an!=null){
					String cl = Utils.getValue(an.getCelldesignerSpeciesIdentity().getCelldesignerClass());
					if(cl.equals("ION")){
						System.out.println("Small molecule: "+sp.getName().getStringValue());
						SmallMolecule sm = biopax_DASH_level3_DOT_owlFactory.createSmallMolecule(biopax.namespaceString+sp.getId(),biopax.biopaxmodel);
						sm.addName(CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,sp.getId(),false,true));
						sm.addName(sp.getName().getStringValue());
						String cleanname = correctName(sp.getName().getStringValue());
						sm.addName(cleanname);
						if(sp.getNotes()!=null)if(sp.getNotes().getHtml()!=null)if(sp.getNotes().getHtml().getBody()!=null)
							sm.addComment(Utils.getValue(sp.getNotes().getHtml().getBody()));
						physEntities.put(sp.getId(),sm);
					}
					if(cl.equals("SIMPLE_MOLECULE")){
						System.out.println("Small molecule: "+sp.getName().getStringValue());
						SmallMolecule sm = biopax_DASH_level3_DOT_owlFactory.createSmallMolecule(biopax.namespaceString+sp.getId(),biopax.biopaxmodel);
						sm.addName(CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,sp.getId(),false,true));
						sm.addName(sp.getName().getStringValue());
						if(sp.getNotes()!=null)if(sp.getNotes().getHtml()!=null)if(sp.getNotes().getHtml().getBody()!=null)
							sm.addComment(Utils.getValue(sp.getNotes().getHtml().getBody()));
						physEntities.put(sp.getId(),sm);
					}
				}
			}
			// Complexes
			Vector complexes = new Vector();
			if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
				for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
					CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias csa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);

					String csa_id = csa.getId();
					String csa_sp_id = csa.getSpecies();
					String complex_name = "";
					String complex_id = "";
					complex_name = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,csa_sp_id,false,true);
					complex_id = csa_sp_id;
					System.out.println("Complex: ("+i+"/"+sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length+"): "+csa.getSpecies()+"\t"+complex_name);

					if(complexes.indexOf(complex_id)<0){
						Complex compl = biopax_DASH_level3_DOT_owlFactory.createComplex(biopax.namespaceString+complex_id,biopax.biopaxmodel);
						compl.addName(complex_name);
						//compl.setSHORT_DASH_NAME(complex_name);
						compl.addName(CellDesignerToCytoscapeConverter.getCelldesignerSpeciesName(sbml.getSbml(),csa.getSpecies()));
						complexes.add(complex_id);
						physEntities.put(complex_id,compl);
						for(int j=0;j<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray().length;j++){
							CelldesignerSpeciesDocument.CelldesignerSpecies isp = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(j);
							CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity ispi = isp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
							String cs = Utils.getValue(isp.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
							if(cs.equals(csa_sp_id)){
								PhysicalEntity pep = createEntityParticipantForSpecies(isp.getId(),species,complexes_list,physEntities,sbml.getSbml(),biopax,csa_id);
								compl.addComponent(pep);
							}
						}
					}
					physEntities.put(csa_sp_id,physEntities.get(complex_id));
				}
			// Homodimers are treated as complexes
			if(false)for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray().length;i++){
				SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
				if(sp.getAnnotation()!=null)if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null)
					if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerState()!=null){
						String hm = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerState().getCelldesignerHomodimer());
						if(hm!=null){
							CelldesignerProteinDocument.CelldesignerProtein pp = (CelldesignerProteinDocument.CelldesignerProtein)getProteinById(proteins,Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()));
							System.out.println("Homodimer: "+pp.getName().getStringValue()+" ("+hm+")");
							String csa_sp_id = sp.getId();
							String complex_name = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,csa_sp_id,false,true);
							String complex_id = csa_sp_id;
							int ihm = Integer.parseInt(hm);
							if(complexes.indexOf(complex_id)<0){
								Complex compl = biopax_DASH_level3_DOT_owlFactory.createComplex(biopax.namespaceString+complex_id,biopax.biopaxmodel);
								compl.addName(complex_name);
								//compl.setSHORT_DASH_NAME(complex_name);
								complexes.add(complex_id);
								physEntities.put(complex_id,compl);
								for(int j=0;j<ihm;j++){
									PhysicalEntity pep = createEntityParticipantForSpecies(sp.getId(),species,complexes_list,physEntities,sbml.getSbml(),biopax,csa_sp_id+"_"+(j+1));
									//physicalEntityParticipant pep = createEntityParticipantForSpecies(sp.getId(),species,complexes_list,physEntities,sbml,biopax,csa_sp_id);
									//System.out.println("Homodimer pep : "+Utils.cutUri(pep.uri()));
									compl.addComponent(pep);
								}
							}
						}
					}
			}

			// Interactions
			for(int i=0;i<reactions.size();i++)/*if(isRealReaction((Reaction)reactions.elementAt(i),sbml))*/{
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
					PhysicalEntity pepr = (PhysicalEntity)physEntities.get(r.getListOfReactants().getSpeciesReferenceArray(0).getSpecies());
					cat.addController(pepr);
					//PhysicalEntity pepp = createEntityParticipantForSpecies(r.getListOfProducts().getSpeciesReferenceArray(0).getSpecies(),species,complexes_list,physEntities,sbml.getSbml(),biopax,"c"+r.getId());
					PhysicalEntity pepp = (PhysicalEntity)physEntities.get(r.getListOfProducts().getSpeciesReferenceArray(0).getSpecies());
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
					PhysicalEntity pepr = createEntityParticipantForSpecies(r.getListOfReactants().getSpeciesReferenceArray(0).getSpecies(),species,complexes_list,physEntities,sbml.getSbml(),biopax,"c"+r.getId());
					//cat.setCONTROLLER(pepr);
					PhysicalEntity pepp = createEntityParticipantForSpecies(r.getListOfProducts().getSpeciesReferenceArray(0).getSpecies(),species,complexes_list,physEntities,sbml.getSbml(),biopax,"c"+r.getId());
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
						PhysicalEntity pep = (PhysicalEntity)physEntities.get(id);
						if(pep==null)
							pep = createEntityParticipantForSpecies(id,species,complexes_list,physEntities,sbml.getSbml(),biopax,r.getId());
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
							PhysicalEntity pep = createEntityParticipantForSpecies(id,species,complexes_list,physEntities,sbml.getSbml(),biopax,r.getId());
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
						PhysicalEntity pep = createEntityParticipantForSpecies(id,species,complexes_list,physEntities,sbml.getSbml(),biopax,r.getId());
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


		}catch(Exception e){
			e.printStackTrace();
		}

		return biopax;
	}

	/**
	 * Creates the list of proteins 
	 */
	public static Vector getProteins(SbmlDocument.Sbml sbml){
		Vector v = new Vector();
		if(sbml.getModel().getAnnotation()!=null)if(sbml.getModel().getAnnotation().getCelldesignerListOfProteins()!=null){
			for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray().length;i++){
				CelldesignerProteinDocument.CelldesignerProtein prot = sbml.getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
				v.add(prot);
			}}else{
				// if the model is not annotated at all, we are going to create pseudo-entitites (proteins)
				if(getGenes(sbml).size()==0){
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
						v.add(prot);
					}
				}
			}
		return v;
	}
	/**
	 * Creates the list of genes 
	 */
	public static Vector getGenes(SbmlDocument.Sbml sbml){
		Vector v = new Vector();
		//for(int i=0;i<sbml.getModel().getAnnotation().get;i++){
		//  Celldesigner_gene prot = sbml.getModel().getAnnotation().getCelldesigner_listOfProteins().getCelldesigner_protein(i);
		//  v.add(prot);
		//}
		return v;
	}

	public static CelldesignerProteinDocument.CelldesignerProtein getProteinById(Vector proteins, String pid){
		CelldesignerProteinDocument.CelldesignerProtein pr = null;
		for(int i=0;i<proteins.size();i++){
			CelldesignerProteinDocument.CelldesignerProtein prot = (CelldesignerProteinDocument.CelldesignerProtein)proteins.elementAt(i);
			if(prot.getId().equals(pid))
				pr = prot;
		}
		return pr;
	}

	/**
	 * Creates the list of reactions 
	 */
	public static Vector getReactions(SbmlDocument.Sbml sbml){
		Vector v = new Vector();
		if(sbml.getModel().getListOfReactions()!=null)
			for(int i=0;i<sbml.getModel().getListOfReactions().getReactionArray().length;i++){
				ReactionDocument.Reaction r = sbml.getModel().getListOfReactions().getReactionArray(i);
				v.add(r);
			}
		return v;
	}

	public static ReactionDocument.Reaction getReactionById(Vector reactions, String id){
		ReactionDocument.Reaction r = null;
		for(int i=0;i<reactions.size();i++){
			ReactionDocument.Reaction re = (ReactionDocument.Reaction)reactions.elementAt(i);
			if(r.getId().equals(id))
				r = re;
		}
		return r;
	}

	/**
	 * Creates the list of species 
	 */
	public static Vector getSpecies(SbmlDocument.Sbml sbml){
		Vector v = new Vector();
		for(int i=0;i<sbml.getModel().getListOfSpecies().getSpeciesArray().length;i++){
			SpeciesDocument.Species s = sbml.getModel().getListOfSpecies().getSpeciesArray(i);
			v.add(s);
		}
		return v;
	}

	public static SpeciesDocument.Species getSpeciesById(Vector species, String id){
		SpeciesDocument.Species s = null;
		for(int i=0;i<species.size();i++){
			SpeciesDocument.Species sp = (SpeciesDocument.Species)species.elementAt(i);
			if(sp.getId().equals(id))
				s = sp;
		}
		return s;
	}

	/**
	 * Creates the list of complexes 
	 */
	public static Vector getComplexes(SbmlDocument.Sbml sbml){
		Vector v = new Vector();
		if(sbml.getModel().getAnnotation()!=null)if(sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
			for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
				CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias c = sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
				v.add(c);
			}
		return v;
	}

	public static CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias getComplexById(Vector complexes, String id){
		CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias s = null;
		for(int i=0;i<complexes.size();i++){
			CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias sp = (CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias)complexes.elementAt(i);
			if(sp.getSpecies().equals(id))
				s = sp;
		}
		return s;
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
	 * Converts physicalEntity participant for a reaction rId  
	 */
	public PhysicalEntity createEntityParticipantForSpecies(String id, Vector species, Vector complexes_list, HashMap physEntities, SbmlDocument.Sbml sbml, BioPAX biopax, String rId){
		//physicalEntityParticipant pep = null;
		PhysicalEntity pep = null;
		try{
			SpeciesDocument.Species sp = getSpeciesById(species,id);
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
						spname = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,isp.getId(),true,true);
						si = isp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
						sp_id = isp.getId();
					}
				}
			}
			//physicalEntityParticipant pep = biopax_DASH_level3_DOT_owlFactory.createphysicalEntityParticipant(biopax.namespaceString+sp.getId(),biopax.biopaxmodel);
			if((spname!=null)&&(!spname.toLowerCase().startsWith("null"))){

				String eid = "";

				String cl = "UNKNOWN";
				if(si!=null)
					cl = Utils.getValue(si.getCelldesignerClass());

				if((si!=null)&&(si.getCelldesignerState()!=null)&&(si.getCelldesignerState().getCelldesignerHomodimer()!=null)){
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
				}else
					if(cl.equals("PROTEIN")){
						//pep = biopax_DASH_level3_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+rId+"_"+Utils.correctName(spname),biopax.biopaxmodel);
						pep = biopax_DASH_level3_DOT_owlFactory.createProtein(biopax.namespaceString+Utils.correctName(spname),biopax.biopaxmodel);
						pep.addName(spname);
						eid = Utils.getValue(si.getCelldesignerProteinReference());
						
					}else
						if(cl.equals("COMPLEX")){
							//pep = biopax_DASH_level3_DOT_owlFactory.createphysicalEntityParticipant(biopax.namespaceString+rId+"_"+Utils.correctName(spname),biopax.biopaxmodel);
							//pep = biopax_DASH_level3_DOT_owlFactory.createComplex(biopax.namespaceString+rId+"_"+Utils.correctName(spname),biopax.biopaxmodel);
							pep = (PhysicalEntity)physEntities.get(sp.getId());
							eid = getComplexById(complexes_list,sp.getId()).getSpecies();
						}else
							if(cl.equals("GENE")){
								//pep = biopax_DASH_level3_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+rId+"_"+Utils.correctName(spname),biopax.biopaxmodel);
								//TODO check this Gene is not a PE
								pep = (PhysicalEntity) biopax_DASH_level3_DOT_owlFactory.createGene(biopax.namespaceString+rId+"_"+Utils.correctName(spname),biopax.biopaxmodel);
								eid = Utils.getValue(si.getCelldesignerGeneReference());
							}else
								if(cl.equals("RNA")){
									pep = biopax_DASH_level3_DOT_owlFactory.createRna(biopax.namespaceString+rId+"_"+Utils.correctName(spname),biopax.biopaxmodel);
									eid = Utils.getValue(si.getCelldesignerRnaReference());
								}else
									if(cl.equals("UNKNOWN")){
										pep = biopax_DASH_level3_DOT_owlFactory.createPhysicalEntity(biopax.namespaceString+rId+"_"+Utils.correctName(spname),biopax.biopaxmodel);
										eid = sp_id;
									}else
										if(cl.equals("PHENOTYPE")){
											pep = biopax_DASH_level3_DOT_owlFactory.createPhysicalEntity(biopax.namespaceString+rId+"_"+Utils.correctName(spname),biopax.biopaxmodel);
											eid = sp_id;
										}else
											if(cl.equals("ANTISENSE_RNA")){
												pep = biopax_DASH_level3_DOT_owlFactory.createRna(biopax.namespaceString+rId+"_"+Utils.correctName(spname),biopax.biopaxmodel);
												eid = Utils.getValue(si.getCelldesignerAntisensernaReference());
											}else
												if(cl.equals("ION")){
													pep = biopax_DASH_level3_DOT_owlFactory.createSmallMolecule(biopax.namespaceString+rId+"_"+Utils.correctName(spname),biopax.biopaxmodel);
													eid = sp_id;
												}else
													if(cl.equals("SIMPLE_MOLECULE")){
														pep = biopax_DASH_level3_DOT_owlFactory.createSmallMolecule(biopax.namespaceString+rId+"_"+Utils.correctName(spname),biopax.biopaxmodel);
														eid = sp_id;

														if(pep==null){
															System.out.println("Unknown species type! "+sp.getId()+" "+cl);
														}else{

															//physicalEntity pe = (physicalEntity)physEntities.get(eid);
															//pep.setPHYSICAL_DASH_ENTITY(pe);
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
																//pep.setSTOICHIOMETRIC_DASH_COEFFICIENT(new Double(1));
															}

															pep.setCellularLocation((CellularLocationVocabulary)comp);


															if(sp!=null)

																if(sp.getNotes()!=null){
																	pep.addComment(Utils.getValue(sp.getNotes()));
																	Vector v = extractPubMedReferenceFromComment(Utils.getValue(sp.getNotes()));
																	//if(pep.getPHYSICAL_DASH_ENTITY()!=null)
																	//TODO this should be tested
																	addPublicationXrefsToEntity(pep,v);
																}

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
																		String s = "";
																		if(resname==null)
																			s+="_s"+(j+1)+"_"+stt;
																		else
																			s+="_"+resname+"_"+stt;
																		stt = Utils.replaceString(stt," ","_");
																		//System.out.println("State "+stt+" "+cm.getState().getStringValue());
																		//sequenceFeature sf = biopax_DASH_level3_DOT_owlFactory.createsequenceFeature(biopax.namespaceString+sp_id+""+s,biopax.biopaxmodel);
																		ModificationFeature sf = biopax_DASH_level3_DOT_owlFactory.createModificationFeature(biopax.namespaceString+sp_id+""+s,biopax.biopaxmodel);
																		String state = cm.getState().getStringValue();
																		if(state.equals("unknown")) state+="_modification";
																		sf.setModificationType((SequenceModificationVocabulary)cellDesignerTerms.get(state));
																		//sf.addName(resname+"_"+stt);
																		//sf.setSHORT_DASH_NAME(resname+"_"+stt);

																		//sequenceParticipant spep = (sequenceParticipant)pep;
																		//spep.addSEQUENCE_DASH_FEATURE_DASH_LIST(sf);
																	}
																}
																//if(si.getCelldesigner_state().getCelldesigner_homodimer()!=null){
																//  pep.setSTOICHIOMETRIC_DASH_COEFFICIENT(new Double(si.getCelldesigner_state().getCelldesigner_homodimer()));
																//}
															}
															// --------- adding modifications ------------

														}

													}
			}
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
			StringTokenizer st = new StringTokenizer(comment," :\r\n");
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
				publicationXref xref = (publicationXref)v.elementAt(i);
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
	public static String correctName(String name){
		name = Utils.replaceString(name," ","");
		name = Utils.replaceString(name,"*","");
		name = Utils.replaceString(name,"-","");
		return name;
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
