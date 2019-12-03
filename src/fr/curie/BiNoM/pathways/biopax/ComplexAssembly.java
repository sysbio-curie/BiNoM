

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for ComplexAssembly ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#ComplexAssembly)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A conversion interaction in which a set of physical entities, at least one being a macromolecule (e.g. protein, RNA, DNA), aggregate to from a complex physicalEntity. One of the participants of a complexAssembly must be an instance of the class Complex. The modification of the physicalentities involved in the ComplexAssembly is captured via BindingFeature class.

Usage: This class is also used to represent complex disassembly. The assembly or disassembly of a complex is often a spontaneous process, in which case the direction of the complexAssembly (toward either assembly or disassembly) should be specified via the SPONTANEOUS property. Conversions in which participants obtain or lose CovalentBindingFeatures ( e.g. glycolysation of proteins) should be modeled with BiochemicalReaction.

Synonyms: aggregation, complex formation

Examples: Assembly of the TFB2 and TFB3 proteins into the TFIIH complex, and assembly of the ribosome through aggregation of its subunits.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface ComplexAssembly extends fr.curie.BiNoM.pathways.biopax.Conversion, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#ComplexAssembly");
	





}