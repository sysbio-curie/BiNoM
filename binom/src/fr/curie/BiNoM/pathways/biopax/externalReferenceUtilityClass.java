

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for externalReferenceUtilityClass ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#externalReferenceUtilityClass)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A pointer to an external object, such as an entry in a database or a term in a controlled vocabulary.
Comment: This class is for organizational purposes only; direct instances of this class should not be created.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface externalReferenceUtilityClass extends fr.curie.BiNoM.pathways.biopax.utilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#externalReferenceUtilityClass");
	





}