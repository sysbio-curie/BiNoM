

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for complexAssembly ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#complexAssembly)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A conversion interaction in which a set of physical entities, at least one being a macromolecule (e.g. protein, RNA, DNA), aggregate via non-covalent interactions. One of the participants of a complexAssembly must be an instance of the class complex.
Comment: This class is also used to represent complex disassembly. The assembly or disassembly of a complex is often a spontaneous process, in which case the direction of the complexAssembly (toward either assembly or disassembly) should be specified via the SPONTANEOUS property.
Synonyms: aggregation, complex formation
Examples: Assembly of the TFB2 and TFB3 proteins into the TFIIH complex, and assembly of the ribosome through aggregation of its subunits.
Note: The following are not examples of complex assembly: Covalent phosphorylation of a protein (this is a biochemicalReaction); the TFIIH complex itself (this is an instance of the complex class, not the complexAssembly class).^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface complexAssembly extends fr.curie.BiNoM.pathways.biopax.conversion, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#complexAssembly");
	





}