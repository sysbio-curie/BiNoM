

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for TissueVocabulary ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#TissueVocabulary)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A reference to the BRENDA (BTO). Homepage at http://www.brenda-enzymes.info/.  Browse at http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=BTO^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface TissueVocabulary extends fr.curie.BiNoM.pathways.biopax.ControlledVocabulary, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#TissueVocabulary");
	





}