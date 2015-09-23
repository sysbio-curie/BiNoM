package fr.curie.BiNoM.pathways;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Vector;

import org.sbml.x2001.ns.celldesigner.CelldesignerAntisenseRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerGeneDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesIdentityDocument;
import org.sbml.x2001.ns.celldesigner.ReactionDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;

import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;

public class CellDesignerToGeneSpringConverter {

	/**
	 * Java xml-beans mapping of CellDesigner file.
	 */  
	private SbmlDocument sbml;

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
	
	private HashMap<String, HashSet<String>> entityReferences;


	public CellDesignerToGeneSpringConverter() {
		
		// initialize CellDesigner objects maps and pathway maps
		species = new HashMap<String, SpeciesDocument.Species>();
		includedSpecies = new HashMap<String, CelldesignerSpeciesDocument.CelldesignerSpecies>();
		reactions = new HashMap<String, ReactionDocument.Reaction>();
		complexes = new HashMap<String, CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias>();
		proteins = new HashMap<String, CelldesignerProteinDocument.CelldesignerProtein>();
		genes = new HashMap<String, CelldesignerGeneDocument.CelldesignerGene>();
		rnas = new HashMap<String, CelldesignerRNADocument.CelldesignerRNA>();
		asrnas = new HashMap<String, CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA>();
		entityReferences = new HashMap<String, HashSet<String>>();
	
	}

	
	public static void main(String[] args) {
		
		String fn = "/Users/eric/wk/genespring_integration/cell_cycle/cellcycle_master.xml";
		
		CellDesignerToCytoscapeConverter.Graph graph = 
				CellDesignerToCytoscapeConverter.convert(fn);
		
		
		CellDesignerToGeneSpringConverter c2b = new CellDesignerToGeneSpringConverter();
		c2b.setCellDesigner(graph.sbml);
		c2b.convert();
		
		
		
	}

	
	/**
	 * set CellDesigner model
	 * @param sb CellDesigner SbmlDocument object.
	 */
	public void setCellDesigner(SbmlDocument sb) {
		sbml = sb;
	}
	
	public void convert() {
		
		getSpecies();
		getIncludedSpecies();
		getReactions();
		getComplexes();
		getProteins();
		getGenes();
		getRNAs();
		getAntisenseRNAs();
		
		// print some stats about the CD map
//		System.out.println("Species: "+this.species.size());
//		System.out.println("Included species: "+this.includedSpecies.size());
//		System.out.println("Reactions: "+this.reactions.size());
//		System.out.println("Complexes: "+this.complexes.size());
//		System.out.println("Proteins: "+this.proteins.size());
//		System.out.println("Genes: "+this.genes.size());
//		System.out.println("RNA: "+this.rnas.size());
//		System.out.println("AS RNA: "+this.asrnas.size());
		
		this.buildEntityReferences();
		
		/*
		 * Process aliases
		 */
		int numberOfAliases = this.sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;
		System.out.println("Number of aliases: "+numberOfAliases);
	
		int numberOfComplexAliases = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;		
		System.out.println("Number of complex aliases: "+numberOfComplexAliases);
		
		BufferedWriter coord_file = null;
		BufferedWriter hugo_file = null;
		try {
			coord_file = new BufferedWriter(new FileWriter("/Users/eric/wk/genespring_integration/cell_cycle/coord.txt"));
			hugo_file = new BufferedWriter(new FileWriter("/Users/eric/wk/genespring_integration/cell_cycle/hugo.txt"));
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		// process simple aliases
		for (int i=0;i<numberOfAliases;i++) {
			
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias spa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			String species_id = spa.getSpecies();
			
			//System.out.println(spa);
			
			String spa_id = spa.getId();
			int x1 = (int) Math.round(Float.parseFloat(spa.getCelldesignerBounds().getX()));
			int y1 = (int) Math.round(Float.parseFloat(spa.getCelldesignerBounds().getY()));
			
			int width = (int) Math.round(Float.parseFloat(spa.getCelldesignerBounds().getW()));
			int height = (int) Math.round(Float.parseFloat(spa.getCelldesignerBounds().getH()));
			
			int x2 = x1 + width;
			int y2 = y1 + height;
			
			SpeciesDocument.Species spe = species.get(species_id);
			CelldesignerSpeciesDocument.CelldesignerSpecies inc_spe = this.includedSpecies.get(species_id);
			
			String speciesName = "";
			String cdClass = "";
			CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity si = null;
			
			if (spe != null) {
				// regular species
				cdClass = Utils.getValue(spe.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
				speciesName = cleanString(Utils.getValue(spe.getName()));
				si = spe.getAnnotation().getCelldesignerSpeciesIdentity();
			}
			else if (inc_spe != null){
				// included species
				speciesName = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,inc_spe.getId(),true,true);
				speciesName = cleanString(speciesName);
				si = inc_spe.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
				cdClass = Utils.getValue(si.getCelldesignerClass());
			}
			else {
				//speciesName = "WARNING";
			}
			
			String referenceId = null;
			if (cdClass.equals("PROTEIN")) {
				referenceId = Utils.getValue(si.getCelldesignerProteinReference());
				if (entityReferences.get(referenceId) == null)
					System.out.println("null :"+ referenceId);
			}
			else if (cdClass.equals("GENE")) {
				referenceId = Utils.getValue(si.getCelldesignerGeneReference());
			}
			else if (cdClass.equals("RNA")) {
				referenceId = Utils.getValue(si.getCelldesignerRnaReference());
			}
			else if (cdClass.equals("ANTISENSE_RNA")) {
				referenceId = Utils.getValue(si.getCelldesignerAntisensernaReference());
			}
			
			String hugo_string = "";
			if (referenceId != null) {
				HashSet<String> hugoList = entityReferences.get(referenceId);
				if (hugoList != null)
					for (String hg : hugoList) {
						try {
							hugo_file.write(spa_id + "\t" + hg + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
			}
			
			// rect (118,161) (121,163)	-	c_s418 (AKT1@Nucleus)
			String out = "rect("+x1+","+y1+") ("+x2+","+y2+")"+"\t"+"-\t"+spa_id + " (" + speciesName + ")";
			//System.out.println(out);
			try {
				coord_file.write(out + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// process complex aliases
		for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
			CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias cspa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
			String cspa_id = cspa.getId();
			int x1 = (int) Math.round(Float.parseFloat(cspa.getCelldesignerBounds().getX()));
			int y1 = (int) Math.round(Float.parseFloat(cspa.getCelldesignerBounds().getY()));
			int width = (int) Math.round(Float.parseFloat(cspa.getCelldesignerBounds().getW()));
			int height = (int) Math.round(Float.parseFloat(cspa.getCelldesignerBounds().getH()));
			String cspa_species_id = cspa.getSpecies();
			
			SpeciesDocument.Species sp = (SpeciesDocument.Species)CellDesigner.entities.get(cspa.getSpecies());
			String entname = CellDesignerToCytoscapeConverter.getEntityName(sp.getId(),sp.getAnnotation().getCelldesignerSpeciesIdentity(),sbml);
			String label =  CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), sp.getId(), entname, sp.getCompartment(), true, true, "", sbml); //spa.getSpecies();
			Vector<CelldesignerSpeciesDocument.CelldesignerSpecies> vis = CellDesignerToCytoscapeConverter.complexSpeciesMap.get(cspa_species_id);
			
			//System.out.println(cspa_id+" "+ vis);
			
			
		}
		
		// close output files
		try {
			coord_file.close();
			hugo_file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
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
			}
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
	

	private void buildEntityReferences() {
		
		for (String id : proteins.keySet()) {
			CelldesignerProteinDocument.CelldesignerProtein prot = proteins.get(id);
			//String protName = cleanString(prot.getName().getStringValue());

			if (prot.getCelldesignerNotes() != null) {
				String notes = Utils.getValue(prot.getCelldesignerNotes()).trim();
				extractHUGOReferences(notes, id);
			}
		}
		
		for (String id : genes.keySet()) {
			CelldesignerGeneDocument.CelldesignerGene gene = genes.get(id);
			//String geneName = cleanString(gene.getName());
			
			if (gene.getCelldesignerNotes() != null) {
				String notes = Utils.getValue(gene.getCelldesignerNotes()).trim();
				extractHUGOReferences(notes, id);
			}
		}
		
		for (String id : rnas.keySet()) {
			CelldesignerRNADocument.CelldesignerRNA rna = rnas.get(id);
			//String rnaName = cleanString(rna.getName());
			
			if (rna.getCelldesignerNotes() != null) {
				String notes = Utils.getValue(rna.getCelldesignerNotes()).trim();
				extractHUGOReferences(notes, id);
			}
		}
		
		for (String id : asrnas.keySet()) {
			CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA arna = asrnas.get(id);
			//String arnaName = cleanString(arna.getName());
			
			// add publications and HUGO gene names
			if (arna.getCelldesignerNotes() != null) {
				String notes = Utils.getValue(arna.getCelldesignerNotes()).trim();
				extractHUGOReferences(notes, id);
			}
		}
	}
	
	private void extractHUGOReferences(String comment, String id) {
		
		StringTokenizer st = new StringTokenizer(comment," :\r\n\t;.,");
		HashSet<String> hugo_list = new HashSet<String>();
		while (st.hasMoreTokens()){
			String ss = st.nextToken();
			if (ss.toLowerCase().equals("hugo")) {
				if(st.hasMoreTokens()) {
					String hugo_id = st.nextToken();
					hugo_list.add(hugo_id);
				}
			}
		}
		
		for (String hugo_id : hugo_list) {
			if (entityReferences.get(id) == null)
				entityReferences.put(id, new HashSet<String>());
			
			entityReferences.get(id).add(hugo_id);
		}
	}


}
