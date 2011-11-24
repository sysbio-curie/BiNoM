

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for FragmentFeature ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#FragmentFeature)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: An entity feature that represents the resulting physical entity subsequent to a cleavage or degradation event. 

Usage: Fragment Feature can be used to cover multiple types of modfications to the sequence of the physical entity: 
1.    A protein with a single cleavage site that converts the protein into two fragments (e.g. pro-insulin converted to insulin and C-peptide). TODO: CV term for sequence fragment?  PSI-MI CV term for cleavage site?
2.    A protein with two cleavage sites that removes an internal sequence e.g. an intein i.e. ABC -> A
3.    Cleavage of a circular sequence e.g. a plasmid.

In the case of removal ( e.g. intron)  the fragment that is *removed* is specified in the feature location property. In the case of a "cut" (e.g. restriction enzyme cut site) the location of the cut is specified instead.
Examples: Insulin Hormone^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface FragmentFeature extends fr.curie.BiNoM.pathways.biopax.EntityFeature, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#FragmentFeature");
	





}