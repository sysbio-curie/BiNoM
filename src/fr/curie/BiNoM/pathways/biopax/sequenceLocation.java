

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for sequenceLocation ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#sequenceLocation)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A location on a nucleotide or amino acid sequence.
Comment: For organizational purposes only; direct instances of this class should not be created.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface sequenceLocation extends fr.curie.BiNoM.pathways.biopax.utilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#sequenceLocation");
	





}