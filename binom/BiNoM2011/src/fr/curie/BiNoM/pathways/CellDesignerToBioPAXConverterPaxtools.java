package fr.curie.BiNoM.pathways;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Vector;

import org.biopax.paxtools.io.BioPAXIOHandler;
import org.biopax.paxtools.io.SimpleIOHandler;
import org.biopax.paxtools.model.BioPAXFactory;
import org.biopax.paxtools.model.BioPAXLevel;
import org.biopax.paxtools.model.Model;
import org.biopax.paxtools.model.level3.BioSource;
import org.biopax.paxtools.model.level3.BiochemicalReaction;
import org.biopax.paxtools.model.level3.Catalysis;
import org.biopax.paxtools.model.level3.Complex;
import org.biopax.paxtools.model.level3.ComplexAssembly;
import org.biopax.paxtools.model.level3.Control;
import org.biopax.paxtools.model.level3.ControlType;
import org.biopax.paxtools.model.level3.Conversion;
import org.biopax.paxtools.model.level3.Degradation;
import org.biopax.paxtools.model.level3.DnaRegionReference;
import org.biopax.paxtools.model.level3.Entity;
import org.biopax.paxtools.model.level3.EntityReference;
import org.biopax.paxtools.model.level3.Interaction;
import org.biopax.paxtools.model.level3.Pathway;
import org.biopax.paxtools.model.level3.PhysicalEntity;
import org.biopax.paxtools.model.level3.Protein;
import org.biopax.paxtools.model.level3.ProteinReference;
import org.biopax.paxtools.model.level3.PublicationXref;
import org.biopax.paxtools.model.level3.RnaRegionReference;
import org.biopax.paxtools.model.level3.SmallMolecule;
import org.biopax.paxtools.model.level3.SmallMoleculeReference;
import org.biopax.paxtools.model.level3.UnificationXref;
import org.biopax.paxtools.model.level3.Xref;
import org.biopax.paxtools.model.level3.DnaRegion;
import org.biopax.paxtools.model.level3.RnaRegion;
import org.biopax.paxtools.model.level3.Transport;
import org.biopax.paxtools.model.level3.Process;
import org.sbml.x2001.ns.celldesigner.AnnotationDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerAntisenseRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerGeneDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerModificationDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesIdentityDocument;
import org.sbml.x2001.ns.celldesigner.ReactionDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;

import fr.curie.BiNoM.pathways.utils.Utils;

/**
 * CellDesigner to BioPAX converter using the Paxtools library.
 * 
 * @author eric
 *
 */
public class CellDesignerToBioPAXConverterPaxtools {
	
	
	/**
	 * Java xml-beans mapping of CellDesigner file.
	 */  
	private SbmlDocument sbml;
	
	/**
	 * The BioPAX model object.
	 */
	private Model model;
	
	/**
	 * Map of CellDesigner species IDs to species objects.
	 */
	private HashMap<String, SpeciesDocument.Species> species;
	
	/**
	 * Map of CellDesigner included species IDs to species objects. 
	 */
	private HashMap<String, CelldesignerSpeciesDocument.CelldesignerSpecies> includedSpecies;
	
	
	/**
	 * Map of CellDesigner reaction IDs to reaction objects.
	 */  
	private HashMap<String, ReactionDocument.Reaction> reactions;
	
	/**
	 * Map of CellDesigner complex IDs to complex objects.
	 */  
	private HashMap<String, CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias> complexes;
	
	/**
	 * Map of CellDesigner protein IDs to protein objects.
	 */  
	private HashMap<String, CelldesignerProteinDocument.CelldesignerProtein> proteins;
	
	/**
	 * Map of CellDesigner gene IDs to gene objects.
	 */
	private HashMap<String, CelldesignerGeneDocument.CelldesignerGene> genes;
	
	/**
	 * Map of CellDesigner rna IDs to rna objects.
	 */
	private HashMap<String, CelldesignerRNADocument.CelldesignerRNA> rnas;	
	
	/**
	 * Map of CellDesigner antisense rna IDs to antisense rna objects.
	 */
	private HashMap<String, CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA> asrnas;
	
	/**
	 * Map to BioPAX EntityReference objects.
	 */
	private HashMap<String, EntityReference> bpEntityReferences;
	
	/**
	 * Map to BioPAX PhysicalEntity objects.
	 */
	private HashMap<String, PhysicalEntity> bpPhysicalEntities;
	
	/**
	 * BioPAX name space prefix (legacy code, not used).
	 */
	private String biopaxNameSpacePrefix = "";
	
	/**
	 * Map of Pubmed IDs to BioPAX PublicationXref objects.
	 */
	private HashMap<String, PublicationXref> bpPublicationXref;
	
	/**
	 * Map of HUGO ID codes to BioPAX UnificationXref objects.
	 */
	private HashMap<String, UnificationXref> bpUnificationXref;
	
	/**
	 * Map of reaction ID to pathway name(s), corresponding to annotations as maps or modules.
	 */
	private HashMap<String, HashSet<String>> reactionToPathwayMap;
	
	/**
	 * map of pathway names to BioPAX pathway objects.
	 */
	private HashMap<String, Pathway> pathwayMap;
	
	/**
	 * set of pathway names, derived from map(s) and modules names.
	 */
	private HashSet<String> pathwayNames;
	
	
	/**
	 * Constructor.
	 */
	public CellDesignerToBioPAXConverterPaxtools() {
		
		// initialize CellDesigner objects maps and pathway maps
		species = new HashMap<String, SpeciesDocument.Species>();
		includedSpecies = new HashMap<String, CelldesignerSpeciesDocument.CelldesignerSpecies>();
		reactions = new HashMap<String, ReactionDocument.Reaction>();
		complexes = new HashMap<String, CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias>();
		proteins = new HashMap<String, CelldesignerProteinDocument.CelldesignerProtein>();
		genes = new HashMap<String, CelldesignerGeneDocument.CelldesignerGene>();
		rnas = new HashMap<String, CelldesignerRNADocument.CelldesignerRNA>();
		asrnas = new HashMap<String, CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA>();
		reactionToPathwayMap = new HashMap<String, HashSet<String>>();
		pathwayMap = new HashMap<String, Pathway>();
		pathwayNames = new HashSet<String>();
		
		// initialize BioPAX objects containers
		bpEntityReferences = new HashMap<String, EntityReference>();
		bpPhysicalEntities = new HashMap<String, PhysicalEntity>();
		bpPublicationXref = new  HashMap<String, PublicationXref>();
		bpUnificationXref = new  HashMap<String, UnificationXref>();
	}
	
	/**
	 * Do the conversion from CellDesigner file to BioPAX.
	 */
	public void convert() {
		createBioPAXModel();
		fillBioPAXModel();
		//saveModel();
		//printHUGO();
	}
	
	/**
	 * save BioPAX model to file.
	 */
	public void saveBioPAXModel(String fileName) {
		try {
			FileOutputStream fo = new FileOutputStream(fileName);
			BioPAXIOHandler handler = new SimpleIOHandler();
			handler.convertToOWL(model, fo);
			fo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * set CellDesigner model
	 * @param sb CellDesigner SbmlDocument object.
	 */
	public void setCellDesigner(SbmlDocument sb) {
		sbml = sb;
	}
	
	/**
	 * Initialize the BioPAX model object.
	 */
	private void createBioPAXModel() {
		BioPAXFactory factory = BioPAXLevel.L3.getDefaultFactory();
		model = factory.createModel();
		model.setXmlBase("http://sysbio.curie.fr/biopax/");
	}
	
	/**
	 * Extract all CellDesigner objects and convert them to BioPAX objects.
	 */
	private void fillBioPAXModel() {
		
		System.out.println("INFO: starting CellDesigner to BioPAX conversion...");
		getSpecies();
		getIncludedSpecies();
		getReactions();
		getComplexes();
		getProteins();
		getGenes();
		getRNAs();
		getAntisenseRNAs();
		
		setBioPAXPhenotypes();
		setBioPAXUnknowns();
		setBioPAXProteins();
		setBioPAXGenes();
		setBioPAXRNAs();
		setBioPAXAntisenseRNAs();
		setBioPAXSmallMolecules();
		setBioPAXSpecies();
		setBioPAXReactions();
		System.out.println("INFO: conversion done.");
	}
	
	/**
	 * Fill the map of CellDesigner species.
	 */
	private void getSpecies(){
		for (int i=0;i<sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray().length;i++) {
			SpeciesDocument.Species s = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			species.put(s.getId(), s);
		}
	}
	
	/**
	 * Fill the map of CellDesigner included species.
	 */
	private void getIncludedSpecies(){
		if (sbml.getSbml().getModel().getAnnotation() != null) {
			if (sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies() != null) {
				for (int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
					CelldesignerSpeciesDocument.CelldesignerSpecies s = 
							sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
					includedSpecies.put(s.getId(), s);
				}
			}
		}
	}
	
	/**
	 * Fill the map of CellDesigner reactions and create pathway objects.
	 */
	private void getReactions(){
		if (sbml.getSbml().getModel().getListOfReactions() != null) {
			for (int i=0; i<sbml.getSbml().getModel().getListOfReactions().getReactionArray().length; i++) {
				ReactionDocument.Reaction r = sbml.getSbml().getModel().getListOfReactions().getReactionArray(i);
				reactions.put(r.getId(),r);
				buildPathwayReactionMap(r);
			}
			buildPathwayMap();
		}
	}
	
	/**
	 * Fill the map of CellDesigner complexes.
	 */
	private void getComplexes() {
		if (sbml.getSbml().getModel().getAnnotation()!=null)if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null) {
			for (int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++) {
				CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias c = 
						sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().
						getCelldesignerComplexSpeciesAliasArray(i);
				complexes.put(c.getId(), c);
			}
		}
	}
	
	/**
	 * Fill the map of CellDesigner proteins. 
	 */
	private void getProteins() {
		if (sbml.getSbml().getModel().getAnnotation()!=null) {
			if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins() != null) {
				for (int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray().length;i++) {
					CelldesignerProteinDocument.CelldesignerProtein prot = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
					proteins.put(prot.getId(), prot);
				}
			}
		}
	}
	
	/**
	 * Fill the map of CellDesigner genes.
	 */
	private void getGenes() {
		if (sbml.getSbml().getModel().getAnnotation() != null) {
			if (sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes() != null) {
				for (int i=0;i<sbml.getSbml().getModel().getAnnotation().
						getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++) {
					CelldesignerGeneDocument.CelldesignerGene gene = sbml.getSbml().getModel().
							getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
					genes.put(gene.getId(), gene);
				}
			}
		}
	}
	
	/**
	 * Fill the map of CellDesigner rnas.
	 */
	private void getRNAs() {
		if (sbml.getSbml().getModel().getAnnotation() != null) {
			if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs() != null) {
				for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().
						sizeOfCelldesignerRNAArray();i++) {
					CelldesignerRNADocument.CelldesignerRNA rna = 
							sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
					rnas.put(rna.getId(), rna);
				}
			}
		}
	}
	
	/**
	 * Fill the map of CellDesigner antisense RNAs.
	 */
	private void getAntisenseRNAs() {
		if (sbml.getSbml().getModel().getAnnotation() != null) {
			if (sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs() != null) {
				for (int i=0;i<sbml.getSbml().getModel().getAnnotation().
						getCelldesignerListOfAntisenseRNAs().sizeOfCelldesignerAntisenseRNAArray();i++) {
					CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA asrna = 
							sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
					asrnas.put(asrna.getId(), asrna);
				}
			}
		}
	}
	

	/**
	 * Encode CellDesigner Phenotypes as BioPAX PhysicalEntities.
	 */
	private void setBioPAXPhenotypes() {
		for (String id : species.keySet()) {
			SpeciesDocument.Species sp = species.get(id);
			if (sp.getAnnotation() != null) {
				if (sp.getAnnotation().getCelldesignerSpeciesIdentity() != null) {
					if (Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("PHENOTYPE")) {
						String uri = biopaxNameSpacePrefix + sp.getId();
						String name = cleanString(sp.getName().getStringValue());
						PhysicalEntity pe = model.addNew(PhysicalEntity.class, uri);
						pe.setDisplayName(name);
						pe.addName(name);
						pe.setStandardName(name);
						pe.addComment("This is a CellDesigner PHENOTYPE entity, representing an abstract biological process.");
						bpPhysicalEntities.put(sp.getId(), pe);
					}
				}
			}
		}
	}
	
	
	/**
	 * Encode CellDesigner UNKNOWNS as BioPAX PhysicalEntities.
	 */
	private void setBioPAXUnknowns() {
		for (String id : species.keySet()) {
			SpeciesDocument.Species sp = species.get(id);
			if (sp.getAnnotation() != null) {
				if (sp.getAnnotation().getCelldesignerSpeciesIdentity() != null) {
					if (Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("UNKNOWN")) {

						String uri = biopaxNameSpacePrefix + sp.getId();
						String name = cleanString(sp.getName().getStringValue());
						
						PhysicalEntity pe = model.addNew(PhysicalEntity.class, uri);
						pe.addName(name);
						pe.setDisplayName(name);
						pe.setStandardName(name);
						pe.addComment("This is a CellDesigner UNKNOWN entity.");
						bpPhysicalEntities.put(sp.getId(), pe);
					}
				}
			}
		}
	}
	
	/**
	 * Encode CellDesigner Proteins as BioPAX ProteinReference objects.
	 */
	private void setBioPAXProteins() {
		for (String id : proteins.keySet()) {
			CelldesignerProteinDocument.CelldesignerProtein prot = proteins.get(id);
			String protName = cleanString(prot.getName().getStringValue());
			String uri = biopaxNameSpacePrefix + prot.getId() + "_ref";
			
			ProteinReference pr = model.addNew(ProteinReference.class, uri);
			pr.addName(protName);
			pr.setDisplayName(protName);
			pr.setStandardName(protName);
			
			// add publications and HUGO gene names
			if (prot.getCelldesignerNotes() != null) {
				String notes = Utils.getValue(prot.getCelldesignerNotes()).trim();
				
				for (PublicationXref pref : extractPubMedReferences(notes))
					pr.addXref(pref);
				
				for (UnificationXref u_xref : extractHUGOReferences(notes))
					pr.addXref(u_xref);
			}
			bpEntityReferences.put(prot.getId(), pr);
		}
	}
	
	/**
	 * Encode CellDesigner Genes as BioPAX DNARegionReference objects.
	 */
	private void setBioPAXGenes() {
		for (String id : genes.keySet()) {
			CelldesignerGeneDocument.CelldesignerGene gene = genes.get(id);
			String geneName = cleanString(gene.getName());
			String uri = biopaxNameSpacePrefix + gene.getId() + "_ref";
			
			DnaRegionReference gn = model.addNew(DnaRegionReference.class, uri);
			gn.addName(geneName);
			gn.setDisplayName(geneName);
			gn.setStandardName(geneName);
			
			// add publications and HUGO gene names
			if (gene.getCelldesignerNotes() != null) {
				String notes = Utils.getValue(gene.getCelldesignerNotes()).trim();
				
				for (PublicationXref pref : extractPubMedReferences(notes))
					gn.addXref(pref);
				
				for (UnificationXref u_xref : extractHUGOReferences(notes))
					gn.addXref(u_xref);
			}
			bpEntityReferences.put(gene.getId(), gn);
		}
	}
	
	/**
	 * Encode CellDesigner RNAs as BioPAX RNARegionReference objects.
	 */
	private void setBioPAXRNAs() {
		for (String id : rnas.keySet()) {
			CelldesignerRNADocument.CelldesignerRNA rna = rnas.get(id);
			String rnaName = cleanString(rna.getName());
			String uri = biopaxNameSpacePrefix + rna.getId() + "_ref";
			
			RnaRegionReference rn = model.addNew(RnaRegionReference.class, uri);
			rn.addName(rnaName);
			rn.setDisplayName(rnaName);
			rn.setStandardName(rnaName);
			
			// add publications and HUGO gene names
			if (rna.getCelldesignerNotes() != null) {
				String notes = Utils.getValue(rna.getCelldesignerNotes()).trim();
				
				for (PublicationXref pref : extractPubMedReferences(notes))
					rn.addXref(pref);
				
				for (UnificationXref u_xref : extractHUGOReferences(notes))
					rn.addXref(u_xref);
			}
			bpEntityReferences.put(rna.getId(), rn);
		}
	}
	
	/**
	 * Encode CellDesigner antisenseRNAs as BioPAX RNARegionReference objects.
	 */
	private void setBioPAXAntisenseRNAs() {
		for (String id : asrnas.keySet()) {
			CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA arna = asrnas.get(id);
			String uri = biopaxNameSpacePrefix + arna.getId() + "_ref";
			String arnaName = cleanString(arna.getName());
			
			RnaRegionReference rn = model.addNew(RnaRegionReference.class, uri);
			rn.addName(arnaName);
			rn.setDisplayName(arnaName);
			rn.setStandardName(arnaName);
			
			// add publications and HUGO gene names
			if (arna.getCelldesignerNotes() != null) {
				String notes = Utils.getValue(arna.getCelldesignerNotes()).trim();
				
				for (PublicationXref pref : extractPubMedReferences(notes))
					rn.addXref(pref);
				
				for (UnificationXref u_xref : extractHUGOReferences(notes))
					rn.addXref(u_xref);
			}
			bpEntityReferences.put(arna.getId(), rn);
		}
	}
	
	/**
	 * Encode CellDesigner small molecules (ion, drug and simple molecule) as BioPAX SmallMoleculeReference objects.
	 */
	private void setBioPAXSmallMolecules() {
		for (String id : species.keySet()) {
			SpeciesDocument.Species sp = species.get(id);
			AnnotationDocument.Annotation annot = sp.getAnnotation();
			if (annot != null) {
				String cd_class = Utils.getValue(annot.getCelldesignerSpeciesIdentity().getCelldesignerClass());
				if (cd_class.equals("ION") || cd_class.equals("DRUG") || cd_class.equals("SIMPLE_MOLECULE")) {
					String sm_name = cleanString(sp.getName().getStringValue());
					String uri = biopaxNameSpacePrefix + sp.getId() + "_ref";
					
					SmallMoleculeReference sm = model.addNew(SmallMoleculeReference.class, uri);
					sm.addName(sm_name);
					sm.setDisplayName(sm_name);
					sm.setStandardName(sm_name);
					
					// add publications
					if (sp.getNotes() != null) {
						String notes = Utils.getValue(sp.getNotes()).trim();
						
						for (PublicationXref pref : extractPubMedReferences(notes))
							sm.addXref(pref);
					}
					bpEntityReferences.put(sp.getId()+"_ref", sm);
				}
			}
		}
	}
	
	/**
	 * Encode CellDesigner Species (and included species in complexes) as BioPAX PhysicalEntity objects.
	 */
	private void setBioPAXSpecies() {
		for (String id : species.keySet()) {
			SpeciesDocument.Species sp = species.get(id);
			if (sp != null && sp.getAnnotation() != null) {
				if (sp.getAnnotation().getCelldesignerSpeciesIdentity() != null) {
					PhysicalEntity pe = getOrCreateBioPAXSpecies(sp.getId());
				}
			}
		}
	}
	
	/**
	 * Return an existing CellDesigner species as PhysicalEntity or create a new one
	 * @param speciesId
	 * @return BioPAX PhysicalEntity
	 */
	private PhysicalEntity getOrCreateBioPAXSpecies(String speciesId) {
		
		// return PhysicalEntity if it's already in the current list
		if (bpPhysicalEntities.get(speciesId) != null)
			return bpPhysicalEntities.get(speciesId);
					
		PhysicalEntity retPe = null;
		
		SpeciesDocument.Species sp = species.get(speciesId);
		
		// set variables for species name, class and identity
		String speciesName = "";
		String cdClass = "";
		CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity si = null;
		boolean complexComponent = false;
		
		if (sp != null) {
			// regular species
			cdClass = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
			speciesName = cleanString(Utils.getValue(sp.getName()));
			si = sp.getAnnotation().getCelldesignerSpeciesIdentity();
		}
		else {
			// included species
			complexComponent = true;
			CelldesignerSpeciesDocument.CelldesignerSpecies isp = includedSpecies.get(speciesId);
			speciesName = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,isp.getId(),true,true);
			speciesName = cleanString(speciesName);
			si = isp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
			cdClass = Utils.getValue(si.getCelldesignerClass());
		}
		
		// build uri using BioPAX prefix and species ID
		String uri =  biopaxNameSpacePrefix + speciesId;
		
		if (cdClass.equals("PROTEIN")) {
			boolean homodimer = false;
			if (si.getCelldesignerState() != null && si.getCelldesignerState().getCelldesignerHomodimer() != null)
				homodimer = true;
			// if the protein is an homodimer and is not included in a complex, create it as a complex
			if (homodimer && !complexComponent) {
				Complex co = model.addNew(Complex.class, uri);
				co.addName(speciesName);
				co.setStandardName(speciesName);
				co.setDisplayName(speciesName);
				//TODO: add stoichiometry as included component, see old code
				bpPhysicalEntities.put(speciesId, co);
				retPe = co;
			}
			else {
				Protein pr = model.addNew(Protein.class, uri);
				pr.addName(speciesName);
				pr.setStandardName(speciesName);
				pr.setDisplayName(speciesName);
				String referenceId = Utils.getValue(si.getCelldesignerProteinReference());
				pr.setEntityReference(bpEntityReferences.get(referenceId));
				bpPhysicalEntities.put(speciesId, pr);
				retPe = pr;
			}
		} else if (cdClass.equals("GENE")) {
			DnaRegion dr = model.addNew(DnaRegion.class, uri);
			dr.addName(speciesName);
			dr.setDisplayName(speciesName);
			dr.setStandardName(speciesName);
			String referenceId = Utils.getValue(si.getCelldesignerGeneReference());
			dr.setEntityReference(bpEntityReferences.get(referenceId));
			bpPhysicalEntities.put(speciesId, dr);
			retPe = dr;
		} else if (cdClass.equals("RNA") || cdClass.equals("ANTISENSE_RNA")) {
			RnaRegion rr = model.addNew(RnaRegion.class, uri);
			rr.addName(speciesName);
			rr.setDisplayName(speciesName);
			rr.setStandardName(speciesName);
			String referenceId = "";
			if (cdClass.equals("RNA"))
				referenceId = Utils.getValue(si.getCelldesignerRnaReference());
			else
				referenceId = Utils.getValue(si.getCelldesignerAntisensernaReference());
			rr.setEntityReference(bpEntityReferences.get(referenceId));
			bpPhysicalEntities.put(speciesId, rr);
			retPe = rr;
		} else if (cdClass.equals("ION") || cdClass.equals("DRUG") || cdClass.equals("SIMPLE_MOLECULE")) {
			SmallMolecule sm = model.addNew(SmallMolecule.class, uri);
			sm.addName(speciesName);
			sm.setDisplayName(speciesName);
			sm.setStandardName(speciesName);
			sm.setEntityReference(bpEntityReferences.get(speciesId+"_ref"));
			bpPhysicalEntities.put(speciesId, sm);
			retPe = sm;
		} else if (cdClass.equals("COMPLEX")) {
			Complex co = model.addNew(Complex.class, uri);
			co.addName(speciesName);
			co.setDisplayName(speciesName);
			co.setStandardName(speciesName);
			// get complex components
			Vector<CelldesignerSpeciesDocument.CelldesignerSpecies> vis = CellDesignerToCytoscapeConverter.complexSpeciesMap.get(speciesId);
			if (vis == null) {
				System.out.println("WARNING: no component found (vis null) for complex "+speciesId);
			}
			else {
				for (CelldesignerSpeciesDocument.CelldesignerSpecies ispc: vis) {
					PhysicalEntity ipe = getOrCreateBioPAXSpecies(ispc.getId());
					if (ipe != null)
						co.addComponent(ipe);
					else
						System.out.println("WARNING: no PhysicalEntity could be created for complex component "+ispc.getId());
				}
			}
			bpPhysicalEntities.put(speciesId, co);
			retPe = co;
		} else if (cdClass.equals("UNKNOWN")) {
			PhysicalEntity pe = model.addNew(PhysicalEntity.class, uri);
			pe.addName(speciesName);
			pe.setDisplayName(speciesName);
			pe.setStandardName(speciesName);
			pe.addComment("This is a CellDesigner UNKNOWN entity.");
			bpPhysicalEntities.put(sp.getId(), pe);
			retPe = pe;
		}
		
		return retPe;
	}
	
	/**
	 * Set BioPAX reaction objects.
	 */
	private void setBioPAXReactions() {
		for (String reactionId : reactions.keySet()) {
			ReactionDocument.Reaction re = reactions.get(reactionId);
			createBioPAXReaction(re);
		}
	}
	
	/**
	 * Create BioPAX reaction objects depending on the CellDesigner type of the reaction.
	 * @param reaction reaction object
	 */
	private void createBioPAXReaction(ReactionDocument.Reaction reaction) {
		
		// default reaction type
		String reactionType = "UNKNOWN_TRANSITION";
		if (reaction.getAnnotation() != null && 
				reaction.getAnnotation().getCelldesignerReactionType() != null) {
			reactionType = Utils.getValue(reaction.getAnnotation().getCelldesignerReactionType());
		}
		
		Control ctrl = null;
		Conversion conv = null;
		Interaction inter = null;
		
		if (reactionType.equals("TRANSPORT")) {
			String uri = biopaxNameSpacePrefix + reaction.getId();
			Transport tr = model.addNew(Transport.class, uri);
			inter = tr;
			conv = tr;
		}
		
		// !!! these cases never seems to be used, even on the master, legacy code, deprecate?
		if (reactionType.equals("CATALYSIS")||
				reactionType.equals("UNKNOWN_CATALYSIS")||
				reactionType.equals("TRANSCRIPTIONAL_ACTIVATION")||
				reactionType.equals("TRANSLATIONAL_ACTIVATION")) {
			
			// create catalysis reaction and set controller and participant
			String cat_uri = biopaxNameSpacePrefix + "c" + reaction.getId();
			Catalysis cat = model.addNew(Catalysis.class, cat_uri);
			cat.setControlType(ControlType.ACTIVATION);
			String reactantId = reaction.getListOfReactants().getSpeciesReferenceArray(0).getSpecies();
			PhysicalEntity per = bpPhysicalEntities.get(reactantId);
			cat.addController(per);
			String productId = reaction.getListOfProducts().getSpeciesReferenceArray(0).getSpecies();
			PhysicalEntity pep = bpPhysicalEntities.get(productId);
			cat.addParticipant(pep);
			
			ctrl = cat;
			inter = cat;
			
			// create biochemical reaction and add catalysis as controller
			String br_uri = biopaxNameSpacePrefix + reaction.getId();
			BiochemicalReaction bre = model.addNew(BiochemicalReaction.class, br_uri);
			cat.addControlled(bre);
			conv = bre;
			//System.out.println("_test!");
			// not used, even with acsn_master !?!
		}
		
		// !!! this code is never executed, even on master, same as above.
		if (reactionType.equals("INHIBITION")||
				reactionType.equals("UNKNOWN_INHIBITION")||
				reactionType.equals("TRANSCRIPTIONAL_INHIBITION")||
				reactionType.equals("TRANSLATIONAL_INHIBITION")) {
			
			String cat_uri = biopaxNameSpacePrefix + "c" + reaction.getId();
			Catalysis cat = model.addNew(Catalysis.class, cat_uri);
			cat.setControlType(ControlType.INHIBITION);
			String reactantId = reaction.getListOfReactants().getSpeciesReferenceArray(0).getSpecies();
			PhysicalEntity per = bpPhysicalEntities.get(reactantId);
			cat.addController(per);
			String productId = reaction.getListOfProducts().getSpeciesReferenceArray(0).getSpecies();
			PhysicalEntity pep = bpPhysicalEntities.get(productId);
			cat.addParticipant(pep);
			
			ctrl = cat;
			inter = cat;
			
			// create biochemical reaction and add catalysis as controller
			String br_uri = biopaxNameSpacePrefix + reaction.getId();
			BiochemicalReaction bre = model.addNew(BiochemicalReaction.class, br_uri);
			cat.addControlled(bre);
			conv = bre;
		}
		
		
		if(reactionType.equals("HETERODIMER_ASSOCIATION") || 
				reactionType.equals("DISSOCIATION")) {
			String uri = biopaxNameSpacePrefix + reaction.getId();
			ComplexAssembly cas = model.addNew(ComplexAssembly.class, uri);
			inter = cas;
			conv = cas;
		}
		
		if(reactionType.equals("STATE_TRANSITION") || 
				reactionType.equals("TRUNCATION") || 
				reactionType.equals("KNOWN_TRANSITION_OMITTED") || 
				reactionType.equals("UNKNOWN_TRANSITION") ||
				reactionType.equals("TRANSLATION") ||
				reactionType.equals("TRANSCRIPTION") ||
				reactionType.equals("POSITIVE_INFLUENCE") ||
				reactionType.equals("UNKNOWN_POSITIVE_INFLUENCE") ||
				reactionType.equals("UNKNOWN_NEGATIVE_INFLUENCE") ||
				reactionType.equals("MODULATION") ||
				reactionType.equals("REDUCED_MODULATION") ||
				reactionType.equals("NEGATIVE_INFLUENCE")
				) {
			String uri = biopaxNameSpacePrefix + reaction.getId();
			BiochemicalReaction bre = model.addNew(BiochemicalReaction.class, uri);
			inter = bre;
			conv = bre;
		}
	
		if(reactionType.equals("DEGRADATION")) {
			String uri = biopaxNameSpacePrefix + reaction.getId();
			Degradation deg = model.addNew(Degradation.class, uri);
			conv = deg;
			inter = deg;
		}
		
		// if reaction is catalysed, create catalysis reaction here
		if (reaction.getListOfModifiers() != null) {
			for (int j=0; j < reaction.getListOfModifiers().getModifierSpeciesReferenceArray().length; j++) {
				String speciesId = reaction.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
				PhysicalEntity pe = bpPhysicalEntities.get(speciesId);
				// create uri unique for each modifiers related to the main reaction
				String uri = biopaxNameSpacePrefix + "_c" + Integer.toString(j) + "_"+ reaction.getId();
				Catalysis cat = model.addNew(Catalysis.class, uri);
				//pathway.addPathwayComponent(cat);
				addReactionToPathway(reaction.getId(), cat);
				cat.addController(pe);
				cat.addControlled(inter);
				if (reaction.getAnnotation() != null && reaction.getAnnotation().getCelldesignerListOfModification() != null) {
					CelldesignerModificationDocument.CelldesignerModification modif = 
							reaction.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
					String modifType = "";
					try {
						modifType =  modif.getType().toString();
					}
					catch (Exception e) {
						modifType = "UNKNOWN_CATALYSIS";
					}
					if(modifType.equals("CATALYSIS") || modifType.equals("UNKNOWN_CATALYSIS")){
						cat.setControlType(ControlType.ACTIVATION);
					}
					if(modifType.equals("INHIBITION") || modifType.equals("UNKNOWN_INHIBITION")){
						cat.setControlType(ControlType.INHIBITION);
					}
					cat.addComment("CDTYPE:"+modifType);
				}
			}
		}

		// add publications cross-references
		if (inter != null && reaction.getNotes() != null) {
			String notes = Utils.getValue(reaction.getNotes()).trim();
			for (PublicationXref pref : extractPubMedReferences(notes))
				inter.addXref(pref);
		}
		
		if (inter == null) {
			System.out.println("WARNING: no reaction (inter) created for reaction: " + reaction.getId() + " type: " + reactionType);
		}
		else {
			addReactionToPathway(reaction.getId(), inter);
		}
		
		// add physical entities to conversion reactions
		if (conv != null) {
			
			for (int j=0; j < reaction.getListOfReactants().getSpeciesReferenceArray().length; j++) {
				String id = reaction.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
				PhysicalEntity pe = getOrCreateBioPAXSpecies(id);
				//  stochiometry coefficients might be added here, see old code
				if (pe != null)
					conv.addLeft(pe);
			}
			
			for (int j=0; j < reaction.getListOfProducts().getSpeciesReferenceArray().length; j++) {
				String id = reaction.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
				//PhysicalEntity pe = bpPhysicalEntities.get(id);
				PhysicalEntity pe = getOrCreateBioPAXSpecies(id);
			//  stochiometry coefficients might be added here, see old code
				if (pe != null)
					conv.addRight(pe);
			}
		}
		
	}
	
	/**
	 * create map of reaction to pathway names.
	 * @param reaction
	 */
	private void buildPathwayReactionMap(ReactionDocument.Reaction reaction) {
		String reactionId  = reaction.getId();
		String comment  = Utils.getValue(reaction.getNotes()).trim();
		StringTokenizer st = new StringTokenizer(comment," :\r\n\t;.,");
		while (st.hasMoreTokens()) {
			String ss = st.nextToken();
			if (ss.equals("MAP") || ss.equals("MODULE")) {
				String str = st.nextToken();
				String pathwayName = ss.toLowerCase() + "_" + str;
				pathwayNames.add(pathwayName);
				if (reactionToPathwayMap.get(reactionId) == null)
					reactionToPathwayMap.put(reactionId, new HashSet<String>());
				reactionToPathwayMap.get(reactionId).add(pathwayName);
			}
		}
	}
	
	/**
	 * build map of pathway names to BioPAX pathway objects.
	 */
	private void buildPathwayMap() {
		// set taxon for pathways
		BioSource bs = model.addNew(BioSource.class, biopaxNameSpacePrefix + "_organism");
		HashSet<String> taxon = new HashSet<String>();
		taxon.add("Homo sapiens");
		bs.setName(taxon);
		for (String pathwayName : pathwayNames) {
			String uri = biopaxNameSpacePrefix + pathwayName;
			Pathway p = model.addNew(Pathway.class, uri);
			p.addComment("Pathway generated automatically from CellDesigner xml file using BiNoM software http://binom.curie.fr");
			p.setOrganism(bs);
			p.setDisplayName(pathwayName);
			p.setStandardName(pathwayName);
			pathwayMap.put(pathwayName, p);
		}
	}
	
	/**
	 * add a reaction to 0 or more pathways, according to pathway maps.
	 * @param reactionId reaction ID
	 * @param inter BioPAX reaction object (Process)
	 */
	private void addReactionToPathway(String reactionId, Interaction inter) {
		
		HashSet<String> pathways  = reactionToPathwayMap.get(reactionId);
		if (pathways != null) {
			for (String name : pathways) {
				pathwayMap.get(name).addPathwayComponent(inter);
			}
		}
	
	}
	
	/**
	 * print a list of HUGO codes for each pathway.
	 */
	private void printHUGO() {
		for (Pathway p : model.getObjects(Pathway.class)) {
			HashSet<String> hugoSet = new HashSet<String>();
			for (Process pro : p.getPathwayComponent()) {
				for (Entity part : ((Interaction)pro).getParticipant()) {
					
					HashSet<EntityReference> entityReferenceSet = new HashSet<EntityReference>();
					if (part instanceof Complex)
						extractEntityReference(part, entityReferenceSet);
					
					for (EntityReference er : entityReferenceSet) {
						for (Xref xr : er.getXref()) {
							if (xr instanceof UnificationXref) {
								hugoSet.add(xr.getId());
							}
						}
					}
				}
			
			}
			
			for (String h : hugoSet)
			  System.out.println(p.getDisplayName()+ "\t"+ h);
		}
	}
	
	private void printProt() {
		for (Protein p : model.getObjects(Protein.class))
			System.out.println(">>> " + p.getRDFId());
		
		Protein p = (Protein)model.getByID("c_s800");
		System.out.println(p.getDisplayName()+" "+p.getRDFId());
	}
	
	private void extractEntityReference(Entity part, HashSet<EntityReference> erSet) {
		EntityReference pr = null;
		if (part instanceof Protein)
			pr = ((Protein)part).getEntityReference();
		else if (part instanceof DnaRegion)
			pr = ((DnaRegion)part).getEntityReference();
		else if (part instanceof RnaRegion)
			pr = ((RnaRegion)part).getEntityReference();
		else if (part instanceof Complex) {
			Complex co = ((Complex)part);
			for (PhysicalEntity pe : co.getComponent())
				extractEntityReference(pe, erSet);
		}
		if (pr != null)
			erSet.add(pr);
	}
	
	
	/**
	 * Eliminates spaces, stars, dashes from the string 
	 */
	private String cleanString(String s){
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
        // string starting with numbers not allowed
        s = new String(c);
        if((c[0]>='0')&&(c[0]<='9'))
        	s = "id_"+s;
        // remove any trailing underscore character(s)
        while (s.endsWith("_") == true)
        	s = s.substring(0, s.length()-1); 
    	return s;
	}
	
	/**
	 * Print BioPAX model to screen.
	 */
	private void dumpModel() {
		OutputStream out = new ByteArrayOutputStream();
		BioPAXIOHandler handler = new SimpleIOHandler();
		handler.convertToOWL(model, out);
		System.out.println(out);
	}
	


	/**
	 * Extracts Pubmed IDs from CellDesigner comment string (PMID:xxxxxx) in Celldesigner object notes 
	 * and creates a BioPAX publicationXref list.
	 */
	private ArrayList<PublicationXref> extractPubMedReferences(String comment){
		ArrayList<PublicationXref> refs = new ArrayList<PublicationXref>();
		String pubmed_id = "";
		StringTokenizer st = new StringTokenizer(comment," :\r\n\t;.,");
		while (st.hasMoreTokens()){
			String ss = st.nextToken();
			if(ss.toLowerCase().equals("pmid")){
				if(st.hasMoreTokens()) {
					pubmed_id = st.nextToken();
					//System.out.println("pubmed_id=" + pubmed_id);
					PublicationXref xref = bpPublicationXref.get(pubmed_id);
					if (xref == null) {
						String uri = biopaxNameSpacePrefix + "Pubmed_" + pubmed_id;
						xref = model.addNew(PublicationXref.class, uri);
						xref.setDb("pubmed");
						xref.setId(pubmed_id);
						bpPublicationXref.put(pubmed_id, xref);
					}
					refs.add(xref);
				}
			}
		}
		return refs;
	}
	
	/** 
	 * Extract HUGO IDs from comment string and build a list of BioPAX
	 * UnificationXref objects.
	 * 
	 * @param CellDesigner comment string
	 * @return list of UnificationXref objects
	 */
	private ArrayList<UnificationXref> extractHUGOReferences(String comment) {
		ArrayList<UnificationXref> refs = new ArrayList<UnificationXref>();
		String hugo_id = "";
		StringTokenizer st = new StringTokenizer(comment," :\r\n\t;.,");
		while (st.hasMoreTokens()){
			String ss = st.nextToken();
			if (ss.toLowerCase().equals("hugo")) {
				if(st.hasMoreTokens()) {
					hugo_id = st.nextToken();
					UnificationXref xref = bpUnificationXref.get(hugo_id);
					if (xref == null) {
						String uri = biopaxNameSpacePrefix + "HUGO_" + hugo_id;
						xref = model.addNew(UnificationXref.class, uri);
						xref.setDb("HUGO");
						xref.setId(hugo_id);
						bpUnificationXref.put(hugo_id, xref);
					}
					refs.add(xref);
				}
			}
		}
		return refs;
	}

}
