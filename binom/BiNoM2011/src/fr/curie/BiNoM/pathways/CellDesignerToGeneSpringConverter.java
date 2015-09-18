package fr.curie.BiNoM.pathways;

import java.util.HashMap;

import org.sbml.x2001.ns.celldesigner.CelldesignerAntisenseRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerGeneDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument;
import org.sbml.x2001.ns.celldesigner.ReactionDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;

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
		
		System.out.println("INFO: starting CellDesigner to BioPAX conversion...");
		getSpecies();
		getIncludedSpecies();
		getReactions();
		getComplexes();
		getProteins();
		getGenes();
		getRNAs();
		getAntisenseRNAs();
		
		// print some stats about the CD map
		System.out.println("Species: "+this.species.size());
		System.out.println("Included species: "+this.includedSpecies.size());
		System.out.println("Reactions: "+this.reactions.size());
		System.out.println("Complexes: "+this.complexes.size());
		System.out.println("Proteins: "+this.proteins.size());
		System.out.println("Genes: "+this.genes.size());
		System.out.println("RNA: "+this.rnas.size());
		System.out.println("AS RNA: "+this.asrnas.size());
		
		int numberOfAliases = this.sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;
		System.out.println("Number of aliases: "+numberOfAliases);
	
		int numberOfComplexAliases = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;		
		System.out.println("Number of complex aliases: "+numberOfComplexAliases);
		
		for(int i=0;i<numberOfAliases;i++){
			
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias spa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			final String species_id = spa.getSpecies();
			
			//System.out.println(spa);
			System.out.println(species_id);
			
			String spa_id = spa.getId();
			int pos_x = (int) Math.round(Float.parseFloat(spa.getCelldesignerBounds().getX()));
			int pos_y = (int) Math.round(Float.parseFloat(spa.getCelldesignerBounds().getY()));
			int width = (int) Math.round(Float.parseFloat(spa.getCelldesignerBounds().getW()));
			int height = (int) Math.round(Float.parseFloat(spa.getCelldesignerBounds().getH()));
			
			
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

	
	
	




}
