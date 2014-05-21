package fr.curie.BiNoM.pathways;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.xmlbeans.XmlString;
import org.biopax.paxtools.io.BioPAXIOHandler;
import org.biopax.paxtools.io.SimpleIOHandler;
import org.biopax.paxtools.model.BioPAXFactory;
import org.biopax.paxtools.model.BioPAXLevel;
import org.biopax.paxtools.model.Model;
import org.biopax.paxtools.model.level3.Entity;
import org.biopax.paxtools.model.level3.EntityReference;
import org.biopax.paxtools.model.level3.PhysicalEntity;
import org.biopax.paxtools.model.level3.Protein;
import org.biopax.paxtools.model.level3.ProteinReference;
import org.biopax.paxtools.model.level3.PublicationXref;
import org.biopax.paxtools.model.level3.UnificationXref;
import org.biopax.paxtools.model.level3.Xref;
import org.sbml.x2001.ns.celldesigner.CelldesignerAntisenseRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerGeneDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument;
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
		File cellDesignerFile = new File("/Users/eric/wk/agilent_pathways/cc_maps/cellcycle_master.xml");
		//File cellDesignerFile = new File("/Users/eric/wk/acsn_maps/survival_master.xml");
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
	 * Encode CellDesigner Phenotypes as BioPAX PhysicalEntities
	 */
	private void setBioPAXPhenotypes() {
		for (String id : species.keySet()) {
			SpeciesDocument.Species sp = species.get(id);
			if (sp.getAnnotation() != null) {
				if (sp.getAnnotation().getCelldesignerSpeciesIdentity() != null) {
					if (Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("PHENOTYPE")) {
						
						String uri = biopaxNameSpacePrefix + sp.getId() + "_ref";
						String name = cleanString(sp.getName().getStringValue());
						
						PhysicalEntity pe = model.addNew(PhysicalEntity.class, uri);
						pe.addName(name);
						pe.setDisplayName(name);
						pe.setStandardName(name);
						pe.addComment("This is a CellDesigner PHENOTYPE entity. It is not a real protein but rather an abstract representation of a process.");
						//System.out.println("Phenotype name:" + pe.getStandardName());
						bpPhysicalEntities.put(sp.getId()+"_ref", pe);
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

						String uri = biopaxNameSpacePrefix + sp.getId() + "_ref";
						String name = cleanString(sp.getName().getStringValue());
						
						PhysicalEntity pe = model.addNew(PhysicalEntity.class, uri);
						pe.addName(name);
						pe.setDisplayName(name);
						pe.setStandardName(name);
						pe.addComment("This is a CellDesigner UNKNOWN entity.");
						//System.out.println("Unknown name:" + pe.getStandardName());
						bpPhysicalEntities.put(sp.getId()+"_ref", pe);
						
					}
				}
			}
		}
	}
	
	/**
	 * encode CellDesigner Proteins as BioPAX ProteinReference
	 */
	private void setBioPAXProteins() {
		for (String id : proteins.keySet()) {
			CelldesignerProteinDocument.CelldesignerProtein prot = proteins.get(id);
			String protName = cleanString(prot.getName().getStringValue());
			//System.out.println("protein name:" + prot.getName().getStringValue() + " " + cleanString(protName));
			String uri = biopaxNameSpacePrefix + prot.getId() + "_ref";
			
			ProteinReference pr = model.addNew(ProteinReference.class, uri);
			pr.addName(protName);
			pr.setDisplayName(protName);
			pr.setStandardName(protName);
			// add publications if any
			if (prot.getCelldesignerNotes() != null) {
				String notes = Utils.getValue(prot.getCelldesignerNotes());
				ArrayList<PublicationXref> xref = extractPubMedReferenceFromComment(notes.trim());
				for (PublicationXref pref : xref) {
					pr.addXref(pref);
				}
				extractHUGOReferences(notes.trim());
			}
		}
	}
	
	private void setBioPAXGenes() {
		for (String id : genes.keySet()) {
			CelldesignerGeneDocument.CelldesignerGene gene = genes.get(id);
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
        s = new String(c);
        if((c[0]>='0')&&(c[0]<='9'))
        	s = "id_"+s;
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
	private ArrayList<PublicationXref> extractPubMedReferenceFromComment(String comment){
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
