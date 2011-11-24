

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for SequenceModificationVocabulary ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#SequenceModificationVocabulary)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definiiton: A reference to the PSI Molecular Interaction ontology (MI) of covalent sequence modifications. Homepage at http://www.psidev.info/.  Browse at http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0252&termName=biological%20feature. Only children that are covelent modifications at specific positions can be used.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface SequenceModificationVocabulary extends fr.curie.BiNoM.pathways.biopax.ControlledVocabulary, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#SequenceModificationVocabulary");
	





}