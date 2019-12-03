

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Degradation ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Degradation)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A conversion in which a pool of macromolecules are degraded into their elementary units.

Usage: This conversion always has a direction of left-to-right and is irreversible. Degraded molecules are always represented on the left, degradation products on the right. 

Comments: Degradation is a complex abstraction over multiple reactions. Although it obeys law of mass conservation and stoichiometric, the products are rarely specified since they are ubiquitous.

Example:  Degradation of a protein to amino acids.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface Degradation extends fr.curie.BiNoM.pathways.biopax.Conversion, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#Degradation");
	





}