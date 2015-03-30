

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for RelationshipTypeVocabulary ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#RelationshipTypeVocabulary)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: Vocabulary for defining relationship Xref types. A reference to the PSI Molecular Interaction ontology (MI) Cross Reference type. Homepage at http://www.psidev.info/.  Browse at http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0353&termName=cross-reference%20type^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface RelationshipTypeVocabulary extends fr.curie.BiNoM.pathways.biopax.ControlledVocabulary, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#RelationshipTypeVocabulary");
	





}