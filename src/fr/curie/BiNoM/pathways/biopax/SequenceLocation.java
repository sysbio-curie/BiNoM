

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for SequenceLocation ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#SequenceLocation)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A location on a nucleotide or amino acid sequence.
Usage: For most purposes it is more appropriate to use subclasses of this class. Direct instances of SequenceLocation can be used for uknown locations that can not be classified neither as an interval nor a site.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface SequenceLocation extends fr.curie.BiNoM.pathways.biopax.UtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#SequenceLocation");
	





}