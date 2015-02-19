

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for ModificationFeature ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#ModificationFeature)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: An entity feature that represents  the covalently modified state of a dna, rna or a protein. 

Rationale: In Biology, identity of DNA, RNA and Protein entities are defined around a wildtype sequence. Covalent modifications to this basal sequence are represented using modificaton features. Since small molecules are identified based on their chemical structure, not sequence, a covalent modification to a small molecule would result in a different molecule. 

Usage: The added groups should be simple and stateless, such as phosphate or methyl groups and are captured by the modificationType controlled vocabulary. In other cases, such as covalently linked proteins, use CovalentBindingFeature instead. 

Instances: A phosphorylation on a protein, a methylation on a DNA.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface ModificationFeature extends fr.curie.BiNoM.pathways.biopax.EntityFeature, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#ModificationFeature");
	

	/**
	 * The Jena Property for modificationType 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#modificationType)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Description and classification of the feature.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property modificationTypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#modificationType");






	/**
	 * Gets the 'modificationType' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary}
	 * @see			#modificationTypeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary getModificationType() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'modificationType' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary}
	 * @see			#modificationTypeProperty
	 */
	public void setModificationType(fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary modificationType) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'modificationType' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary}, the created value
	 * @see			#modificationTypeProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary setModificationType() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'modificationType' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary} with the factory.
	 * and calling setModificationType(fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary modificationType)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#SequenceModificationVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary}, the newly created value
	 * @see			#modificationTypeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary setModificationType(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}