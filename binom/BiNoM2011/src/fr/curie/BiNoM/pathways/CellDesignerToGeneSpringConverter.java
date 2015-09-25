package fr.curie.BiNoM.pathways;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

	private String sourceFile;

	class Alias {
		String alias_id;
		String species_name;
		int x1;
		int y1;
		int x2;
		int y2;
		HashSet<String> hugo_list;
	}

	private ArrayList<Alias> aliasList;

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
		aliasList = new ArrayList<Alias>();

	}


	public static void main(String[] args) {

		String fn = "/Users/eric/wk/genespring_integration/cell_cycle/cellcycle_master.xml";

		CellDesignerToGeneSpringConverter c2b = new CellDesignerToGeneSpringConverter();
		c2b.convert(fn);

	}


	public void convert(String fileName) {

		sourceFile = fileName;

		CellDesignerToCytoscapeConverter.Graph graph = 
				CellDesignerToCytoscapeConverter.convert(sourceFile);

		sbml = graph.sbml;

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

		buildEntityReferences();
		processAliases();
		processComplexAliases();
		writeOutputFiles();

	}

	private void writeOutputFiles() {

		String coord_file = sourceFile;
		coord_file = coord_file.replace(".xml", "");
		coord_file = coord_file + ".coord.txt";

		String hugo_file = sourceFile;
		hugo_file = hugo_file.replace(".xml", "");
		hugo_file = hugo_file + ".hugo.txt";
		//System.out.println(sourceFile+" "+coord_file+" "+hugo_file);

		try {
			BufferedWriter cf = new BufferedWriter(new FileWriter(coord_file));
			BufferedWriter hf = new BufferedWriter(new FileWriter(hugo_file));

			for (Alias al : aliasList) {
				// rect (118,161) (121,163)	-	c_s418 (AKT1@Nucleus)
				String out = "rect("+al.x1+","+al.y1+") ("+al.x2+","+al.y2+")"+"\t"+"-\t"+al.alias_id + " (" + al.species_name + ")";
				cf.write(out + "\n");

				for (String hugo : al.hugo_list) {
					hf.write(al.alias_id + "\t" + hugo + "\n");
				}
			}
			cf.close();
			hf.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}


	private void processAliases() {

		int numberOfAliases = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;

		// process simple aliases
		for (int i=0;i<numberOfAliases;i++) {

			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias spa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			String species_id = spa.getSpecies();

			String spa_id = spa.getId();
			int x1 = (int) Math.round(Float.parseFloat(spa.getCelldesignerBounds().getX()));
			int y1 = (int) Math.round(Float.parseFloat(spa.getCelldesignerBounds().getY()));

			int width = (int) Math.round(Float.parseFloat(spa.getCelldesignerBounds().getW()));
			int height = (int) Math.round(Float.parseFloat(spa.getCelldesignerBounds().getH()));

			int x2 = x1 + width;
			int y2 = y1 + height;

			SpeciesDocument.Species spe = species.get(species_id);
			/*
			 * Here we get only regular species, not species included in complexes
			 */
			if (spe != null) {
				// regular species
				String cdClass = Utils.getValue(spe.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
				String speciesName = cleanString(Utils.getValue(spe.getName()));
				CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity si = spe.getAnnotation().getCelldesignerSpeciesIdentity();
				
				//System.out.println("simple_alias::"+cdClass+"::"+spa_id+"::"+speciesName+" "+x1+" "+y1+" "+x2+" "+y2);

				Alias al = new Alias();
				al.alias_id = spa_id;
				al.species_name = speciesName;
				al.x1 = x1;
				al.y1 = y1;
				al.x2 = x2;
				al.y2 = y2;

				String referenceId = null;
				if (cdClass.equals("PROTEIN")) {
					referenceId = Utils.getValue(si.getCelldesignerProteinReference());
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

				al.hugo_list = new HashSet<String>();
				if (referenceId != null && entityReferences.get(referenceId) != null) {
					al.hugo_list = entityReferences.get(referenceId);
				}

				aliasList.add(al);
			}
	
		}
	}

	private void processComplexAliases() {
		// process complex aliases
		for (int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++) {

			CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias cspa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
			String cspa_id = cspa.getId();
			int x1 = (int) Math.round(Float.parseFloat(cspa.getCelldesignerBounds().getX()));
			int y1 = (int) Math.round(Float.parseFloat(cspa.getCelldesignerBounds().getY()));
			int width = (int) Math.round(Float.parseFloat(cspa.getCelldesignerBounds().getW()));
			int height = (int) Math.round(Float.parseFloat(cspa.getCelldesignerBounds().getH()));
			int x2  = x1 + width;
			int y2 = y1 + height;
			
			String cspa_species_id = cspa.getSpecies();

			SpeciesDocument.Species sp = (SpeciesDocument.Species)CellDesigner.entities.get(cspa.getSpecies());
			String entname = CellDesignerToCytoscapeConverter.getEntityName(sp.getId(),sp.getAnnotation().getCelldesignerSpeciesIdentity(),sbml);
			String label =  CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), sp.getId(), entname, sp.getCompartment(), true, true, "", sbml); //spa.getSpecies();
			
			Alias al = new Alias();
			al.alias_id = cspa_id;
			al.species_name = label;
			al.x1 = x1;
			al.y1 = y1;
			al.x2 = x2;
			al.y2 = y2;
			
			// get list of species included in the complex and build hugo list from species
			HashSet<String> hg = new HashSet<String>(); 
			Vector<CelldesignerSpeciesDocument.CelldesignerSpecies> vis = CellDesignerToCytoscapeConverter.complexSpeciesMap.get(cspa_species_id);
			for (CelldesignerSpeciesDocument.CelldesignerSpecies ispc: vis) {
				//System.out.println("complex_aliases::"+cspa_id + "::" + label + "::" + entname + "::"+ispc.getId() + " " + Utils.getValue(ispc.getName())+" "+x1+" "+y1+" "+x2+" "+y2);
				CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity si = ispc.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();	
				String speciesName = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,ispc.getId(),true,true);
				speciesName = cleanString(speciesName);
				String cdClass = Utils.getValue(si.getCelldesignerClass());
				
				String referenceId = null;
				if (cdClass.equals("PROTEIN")) {
					referenceId = Utils.getValue(si.getCelldesignerProteinReference());
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

				if (referenceId != null && entityReferences.get(referenceId) != null) {
					for (String h : entityReferences.get(referenceId)) {
						hg.add(h);
					}
				}
			}
			al.hugo_list = hg;
			aliasList.add(al);
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
