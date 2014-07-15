package fr.curie.BiNoM.pathways;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import org.biopax.paxtools.io.BioPAXIOHandler;
import org.biopax.paxtools.io.SimpleIOHandler;
import org.biopax.paxtools.model.BioPAXFactory;
import org.biopax.paxtools.model.BioPAXLevel;
import org.biopax.paxtools.model.Model;
import org.biopax.paxtools.model.level3.Complex;
import org.biopax.paxtools.model.level3.Control;
import org.biopax.paxtools.model.level3.Conversion;
import org.biopax.paxtools.model.level3.DnaRegionReference;
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
import org.sbml.x2001.ns.celldesigner.AnnotationDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerAntisenseRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerGeneDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerHomodimerDocument.CelldesignerHomodimer;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesIdentityDocument;
import org.sbml.x2001.ns.celldesigner.ReactionDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;

import fr.curie.BiNoM.pathways.utils.Utils;

public class CellDesignerToBioPAXConverterPaxtools {
	
	
	/**
	 * Java xml-beans mapping of CellDesigner file
	 */  
	private SbmlDocument sbml;
	
	/**
	 * The BioPAX object
	 */
	private Model model;
	
	/**
	 * Map of CellDesigner SpeciesDocument.Species objects
	 */
	private HashMap<String, SpeciesDocument.Species> species;
	private HashMap<String, CelldesignerSpeciesDocument.CelldesignerSpecies> includedSpecies;
	
	
	/**
	 * Map of CellDesigner ReactionDocument.Reaction objects
	 */  
	private HashMap<String, ReactionDocument.Reaction> reactions;
	
	/**
	 * Map of CellDesigner CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias objects
	 */  
	private HashMap<String, CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias> complexes;
	
	/**
	 * Map of CellDesigner Celldesigner entity objects
	 */  
	private HashMap<String, CelldesignerProteinDocument.CelldesignerProtein> proteins;
	private HashMap<String, CelldesignerGeneDocument.CelldesignerGene> genes;
	private HashMap<String, CelldesignerRNADocument.CelldesignerRNA> rnas;	
	private HashMap<String, CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA> asrnas;
	
	/**
	 * Map to BioPAX EntityReference objects
	 */
	private HashMap<String, EntityReference> bpEntityReferences;
	/**
	 * Map to BioPAX PhysicalEntity objects
	 */
	private HashMap<String, PhysicalEntity> bpPhysicalEntities;
	
	/**
	 * BioPAX name space prefix
	 */
	private String biopaxNameSpacePrefix = "http://www.biopax.org/release/biopax-level3.owl#";
	/**
	 * Map to BioPAX PublicationXref objects
	 */
	private HashMap<String, PublicationXref> bpPublicationXref;
	private HashMap<String, UnificationXref> bpUnificationXref;
	
	/**
	 * Constructor.
	 */
	public CellDesignerToBioPAXConverterPaxtools() {
		// initialize map containers objects
		species = new HashMap<String, SpeciesDocument.Species>();
		includedSpecies = new HashMap<String, CelldesignerSpeciesDocument.CelldesignerSpecies>();
		reactions = new HashMap<String, ReactionDocument.Reaction>();
		complexes = new HashMap<String, CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias>();
		proteins = new HashMap<String, CelldesignerProteinDocument.CelldesignerProtein>();
		genes = new HashMap<String, CelldesignerGeneDocument.CelldesignerGene>();
		rnas = new HashMap<String, CelldesignerRNADocument.CelldesignerRNA>();
		asrnas = new HashMap<String, CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA>();
		// initialize BioPAX objects containers
		bpEntityReferences = new HashMap<String, EntityReference>();
		bpPhysicalEntities = new HashMap<String, PhysicalEntity>();
		bpPublicationXref = new  HashMap<String, PublicationXref>();
		bpUnificationXref = new  HashMap<String, UnificationXref>();
	}
	
	// test 
	public static void main(String[] args) {
		
		 // Load celldesigner file 
		//File cellDesignerFile = new File("/Users/eric/wk/agilent_pathways/cc_maps/cellcycle_APC.xml");
		//File cellDesignerFile = new File("/Users/eric/wk/agilent_pathways/cc_maps/cellcycle_master.xml");
		File cellDesignerFile = new File("/Users/eric/wk/acsn_maps/survival_master.xml");
		//File cellDesignerFile = new File("/Users/eric/wk/acsn_maps/acsn_master.xml");
		//File cellDesignerFile = new File("/Users/eric/wk/acsn_maps/emtcellmotility_ECM.xml");
		CellDesignerToCytoscapeConverter.Graph graph = CellDesignerToCytoscapeConverter.convert(cellDesignerFile.getAbsolutePath());

		CellDesignerToBioPAXConverterPaxtools c2b = new CellDesignerToBioPAXConverterPaxtools();
		c2b.setCellDesigner(graph.sbml);
		c2b.convert();
	
		//c2b.dumpModel();
		System.out.println("EOF.");
	}
	
	/**
	 * Do the conversion from CellDesigner file to BioPAX
	 */
	public void convert() {
		createBioPAXModel();
		fillBioPAXModel();
	}
	
	public void setCellDesigner(SbmlDocument sb) {
		sbml = sb;
	}
	
	private void createBioPAXModel() {
		BioPAXFactory factory = BioPAXLevel.L3.getDefaultFactory();
		model = factory.createModel();
		model.setXmlBase("http://sysbio.curie.fr/");
	}
	
	private void fillBioPAXModel() {
		
		System.out.println("start filling the model...");
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
	}
	
	/**
	 * Fill the map of CellDesigner species
	 */
	private void getSpecies(){
		for (int i=0;i<sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray().length;i++) {
			SpeciesDocument.Species s = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			//System.out.println(s);
			species.put(s.getId(), s);
		}
	}
	
	/**
	 * Fill the map of CellDesigner included species
	 */
	private void getIncludedSpecies(){
		if (sbml.getSbml().getModel().getAnnotation() != null) {
			if (sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies() != null) {
				for (int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
					CelldesignerSpeciesDocument.CelldesignerSpecies s = 
							sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
					includedSpecies.put(s.getId(), s);
					//System.out.println("included species: " + s.getId());
				}
			}
		}
	}
	
	/**
	 * Fill the map of CellDesigner reactions
	 */
	private void getReactions(){
		if(sbml.getSbml().getModel().getListOfReactions() != null) {
			for (int i=0; i<sbml.getSbml().getModel().getListOfReactions().getReactionArray().length; i++) {
				ReactionDocument.Reaction r = sbml.getSbml().getModel().getListOfReactions().getReactionArray(i);
				reactions.put(r.getId(),r);
				//System.out.println("reaction: " + r.getId());
			}
		}
	}
	
	/**
	 * Fill the map of CellDesigner complexes 
	 */
	private void getComplexes() {
		if (sbml.getSbml().getModel().getAnnotation()!=null)if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null) {
			for (int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++) {
				CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias c = 
						sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().
						getCelldesignerComplexSpeciesAliasArray(i);
				complexes.put(c.getId(), c);
				//System.out.println("complexes: " + c.getId());
			}
		}
	}
	
	/**
	 * Fill the map of CellDesigner proteins 
	 */
	private void getProteins() {
		if (sbml.getSbml().getModel().getAnnotation()!=null) {
			if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins() != null) {
				for (int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray().length;i++) {
					CelldesignerProteinDocument.CelldesignerProtein prot = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
					proteins.put(prot.getId(),prot);
					//System.out.println("protein: " + prot.getId());
				}
			}
//TODO tested on big acsn map, this seem never to be used, to be removed??
//			else{
//					// if the model is not annotated at all, we are going to create pseudo-entitites (proteins)
//					for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
//						SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
//						CelldesignerProteinDocument.CelldesignerProtein prot = CelldesignerProteinDocument.Factory.newInstance().addNewCelldesignerProtein();
//						if (sp.getName() != null) {
//							XmlString xs = XmlString.Factory.newInstance();
//							String name = Utils.getValue(sp.getName());
//							xs.setStringValue(name);    				
//							prot.setName(xs);
//						}
//						else {
//							XmlString xs = XmlString.Factory.newInstance();
//							xs.setStringValue(sp.getId());
//							prot.setName(xs);
//						}
//						prot.setId(sp.getId());
//						System.out.println("protein: " + prot.getId());
//						proteins.put(prot.getId(),prot);
//					}
//				}
		}
	}
	
	/**
	 * Fill the map of CellDesigner genes
	 */
	private void getGenes() {
		if (sbml.getSbml().getModel().getAnnotation() != null) {
			if (sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes() != null) {
				for (int i=0;i<sbml.getSbml().getModel().getAnnotation().
						getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++) {
					CelldesignerGeneDocument.CelldesignerGene gene = sbml.getSbml().getModel().
							getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
					genes.put(gene.getId(), gene);
					//System.out.println("gene: " + gene.getId());
				}
			}
		}
	}
	
	/**
	 * Fill the map of CellDesigner rnas
	 */
	private void getRNAs() {
		if (sbml.getSbml().getModel().getAnnotation() != null) {
			if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs() != null) {
				for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().
						sizeOfCelldesignerRNAArray();i++) {
					CelldesignerRNADocument.CelldesignerRNA rna = 
							sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
					rnas.put(rna.getId(), rna);
					//System.out.println("rna: " + rna.getId());
				}
			}
		}
	}
	
	/**
	 * Fill the map of CellDesigner antisense RNAs
	 */
	private void getAntisenseRNAs() {
		if (sbml.getSbml().getModel().getAnnotation() != null) {
			if (sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs() != null) {
				for (int i=0;i<sbml.getSbml().getModel().getAnnotation().
						getCelldesignerListOfAntisenseRNAs().sizeOfCelldesignerAntisenseRNAArray();i++) {
					CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA asrna = 
							sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
					asrnas.put(asrna.getId(), asrna);
					//System.out.println("antisense rna: " + asrna.getId());
				}
			}
		}
	}
	
	/**
	 * Encode CellDesigner Phenotypes as BioPAX "black box" Pathway object.
	 */
	private void setBioPAXPhenotypes() {
		for (String id : species.keySet()) {
			SpeciesDocument.Species sp = species.get(id);
			if (sp.getAnnotation() != null) {
				if (sp.getAnnotation().getCelldesignerSpeciesIdentity() != null) {
					if (Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("PHENOTYPE")) {
						
						String uri = biopaxNameSpacePrefix + sp.getId() + "_phenotype";
						String name = cleanString(sp.getName().getStringValue());
						Pathway pa = model.addNew(Pathway.class, uri);
						pa.setDisplayName(name);
						pa.addComment("This is a CellDesigner PHENOTYPE entity, representing an abstract biological process.");
					}
				}
			}
		}
	}

	/**
	 * Encode CellDesigner Unknowns as BioPAX PhysicalEntities
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
	 * Encode CellDesigner Proteins as BioPAX ProteinReference
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
	 * Encode CellDesigner Genes as BioPAX DNARegionReference
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
	 * Encode CellDesigner Species as BioPAX PhysicalEntities, also species included in complexes.
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
	 * return an existing CellDesigner species as PhysicalEntity or create a new one
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
				System.out.println("Warning: vis null for "+speciesId);
			}
			else {
				for (CelldesignerSpeciesDocument.CelldesignerSpecies ispc: vis) {
					PhysicalEntity ipe = getOrCreateBioPAXSpecies(ispc.getId());
					if (ipe != null)
						co.addComponent(ipe);
					else
						System.out.println("Warning: null PhysicalEntity for complex component "+ispc.getId());
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
	
	private void setBioPAXReactions() {
		
		int k=0;
		for (String reactionId : reactions.keySet()){
			ReactionDocument.Reaction re = reactions.get(reactionId);
			k++;
			//System.out.println("_Reaction ("+k+"/"+reactions.size()+"):\t"+reaction.getId()+"\t"+reactionType);
			createBioPAXReaction(re);
		}
	}
	
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
		
		// create catalysis reactions
		if(reaction.getListOfModifiers() != null) {
			for (int j=0; j < reaction.getListOfModifiers().getModifierSpeciesReferenceArray().length; j++) {
				String speciesId = reaction.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
				PhysicalEntity pe = bpPhysicalEntities.get(speciesId);
				if (pe == null)
					System.out.println("_error "+ speciesId);
			}
		}
	
	
	
	}
	
	
	private void testSetBioPAXSpecies() {
		//System.out.println(">>> mem:: "+Utils.getUsedMemoryMb());
		//long toto = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		//System.out.println(">>> mem:: "+ toto);
		
		for (String id : species.keySet()) {
			SpeciesDocument.Species sp = species.get(id);
			String sp_name = Utils.getValue(sp.getName());
			String cd_class = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
			
			CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity si = sp.getAnnotation().getCelldesignerSpeciesIdentity();
			if (si != null) {
				if (si.getCelldesignerState() != null) {
				CelldesignerHomodimer h = si.getCelldesignerState().getCelldesignerHomodimer();
				if (h != null)
					System.out.println(">>>" + sp.getId() + ":"+ Utils.getValue(sp.getName())	 + "----\n" + h);
				}
			}
			
			//System.out.println("species raw name:" + sp_name);
			//System.out.println(CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,sp.getId(),true,true));
			//System.out.println("-------");
			if (cd_class.equals("COMPLEX")) {
				//System.out.println(cleanString(sp_name) + " " + sp.getId());
				Vector<CelldesignerSpeciesDocument.CelldesignerSpecies> vis = CellDesignerToCytoscapeConverter.complexSpeciesMap.get(id);
				if (vis != null) {
//					System.out.print("species id list:");
//					for (CelldesignerSpeciesDocument.CelldesignerSpecies ispc: vis){
//						System.out.print(ispc.getId() + ":");
//					}
//					System.out.println();
				}
				else {
					//System.out.println("no species list!!");
				}
			}
			
		}
	}
	
	
	private void test() {
		for (String id : species.keySet()) {
			SpeciesDocument.Species sp = species.get(id);
			if (sp.getAnnotation() != null) {
				if (sp.getAnnotation().getCelldesignerSpeciesIdentity() != null) {
					if (Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("DEGRADED")) {
						System.out.println("test: " + sp.getName().getStringValue() + " " + sp.getId());
					}
				}
			}
		}
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
	 * Print BioPAX model to screen
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
						xref.setDb("Pubmed");
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
			if(ss.toLowerCase().equals("hugo")){
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
