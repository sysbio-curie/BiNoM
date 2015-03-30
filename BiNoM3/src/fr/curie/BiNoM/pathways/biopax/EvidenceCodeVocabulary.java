

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for EvidenceCodeVocabulary ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#EvidenceCodeVocabulary)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A reference to the PSI Molecular Interaction ontology (MI) experimental method types, including "interaction detection method", "participant identification method", "feature detection method". Homepage at http://www.psidev.info/.  Browse at http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI

Terms from the Pathway Tools Evidence Ontology may also be used. Homepage http://brg.ai.sri.com/evidence-ontology/^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface EvidenceCodeVocabulary extends fr.curie.BiNoM.pathways.biopax.ControlledVocabulary, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#EvidenceCodeVocabulary");
	





}