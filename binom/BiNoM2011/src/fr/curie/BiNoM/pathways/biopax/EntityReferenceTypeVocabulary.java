

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for EntityReferenceTypeVocabulary ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#EntityReferenceTypeVocabulary)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definiiton: A reference to a term from an entity reference group ontology. As of the writing of this documentation, there is no standard ontology of these terms, though a common type is ‘homology’.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface EntityReferenceTypeVocabulary extends fr.curie.BiNoM.pathways.biopax.ControlledVocabulary, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#EntityReferenceTypeVocabulary");
	





}