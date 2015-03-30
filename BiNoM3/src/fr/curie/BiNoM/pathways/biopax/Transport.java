

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Transport ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Transport)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: An conversion in which molecules of one or more physicalEntity pools change their subcellular location and become a member of one or more other physicalEntity pools. A transport interaction does not include the transporter entity, even if one is required in order for the transport to occur. Instead, transporters are linked to transport interactions via the catalysis class.

Usage: If there is a simultaneous chemical modification of the participant(s), use transportWithBiochemicalReaction class.

Synonyms: translocation.

Examples: The movement of Na+ into the cell through an open voltage-gated channel.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface Transport extends fr.curie.BiNoM.pathways.biopax.Conversion, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#Transport");
	





}