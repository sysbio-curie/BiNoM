

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for CellularLocationVocabulary ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#CellularLocationVocabulary)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A reference to the Gene Ontology Cellular Component (GO CC) ontology. Homepage at http://www.geneontology.org.  Browse at http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=GO^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface CellularLocationVocabulary extends fr.curie.BiNoM.pathways.biopax.ControlledVocabulary, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#CellularLocationVocabulary");
	





}