

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for transport ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#transport)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A conversion interaction in which an entity (or set of entities) changes location within or with respect to the cell. A transport interaction does not include the transporter entity, even if one is required in order for the transport to occur. Instead, transporters are linked to transport interactions via the catalysis class.
Comment: Transport interactions do not involve chemical changes of the participant(s). These cases are handled by the transportWithBiochemicalReaction class.
Synonyms: translocation.
Examples: The movement of Na+ into the cell through an open voltage-gated channel.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface transport extends fr.curie.BiNoM.pathways.biopax.conversion, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#transport");
	





}