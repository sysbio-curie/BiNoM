

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for CovalentBindingFeature ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#CovalentBindingFeature)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition : An entity feature that represent the covalently bound state of  a physical entity. 

Rationale: Most frequent covalent modifications to proteins and DNA, such as phosphorylation and metylation are covered by the ModificationFeature class. In these cases, the added groups are simple and stateless therefore they can be captured by a controlled vocabulary. In other cases, such as ThiS-Thilacyl-disulfide, the covalently linked molecules are best represented as a molecular complex. CovalentBindingFeature should be used to model such covalently linked complexes.

Usage: Using this construct, it is possible to represent small molecules as a covalent complex of two other small molecules. The demarcation of small molecules is a general problem and is delegated to small molecule databases.The best practice is not to model using covalent complexes unless at least one of the participants is a protein, DNA or RNA.

Examples:
disulfide bond
UhpC + glc-6P -> Uhpc-glc-6p
acetyl-ACP -> decenoyl-ACP
charged tRNA^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface CovalentBindingFeature extends fr.curie.BiNoM.pathways.biopax.BindingFeature, fr.curie.BiNoM.pathways.biopax.ModificationFeature, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#CovalentBindingFeature");
	





}