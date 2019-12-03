

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for experimentalForm ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#experimentalForm)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: The form of a physical entity in a particular experiment, as it may be modified for purposes of experimental design.
Examples: A His-tagged protein in a binding assay. A protein can be tagged by multiple tags, so can have more than 1 experimental form type terms^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface experimentalForm extends fr.curie.BiNoM.pathways.biopax.utilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#experimentalForm");
	

	/**
	 * The Jena Property for PARTICIPANT 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#PARTICIPANT)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The participant that has the experimental form being described.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property PARTICIPANTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#PARTICIPANT");


	/**
	 * The Jena Property for EXPERIMENTAL_DASH_FORM_DASH_TYPE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#EXPERIMENTAL-FORM-TYPE)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Descriptor of this experimental form from a controlled vocabulary.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#EXPERIMENTAL-FORM-TYPE");






	/**
	 * Gets the 'PARTICIPANT' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant}
	 * @see			#PARTICIPANTProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant getPARTICIPANT() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'PARTICIPANT' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant}
	 * @see			#PARTICIPANTProperty
	 */
	public void setPARTICIPANT(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANT) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'PARTICIPANT' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant}, the created value
	 * @see			#PARTICIPANTProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant setPARTICIPANT() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'PARTICIPANT' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} with the factory.
	 * and calling setPARTICIPANT(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANT)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#physicalEntityParticipant.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant}, the newly created value
	 * @see			#PARTICIPANTProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant setPARTICIPANT(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Get an Iterator the 'EXPERIMENTAL_DASH_FORM_DASH_TYPE' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}
	 * @see			#EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty
	 */
	public java.util.Iterator getEXPERIMENTAL_DASH_FORM_DASH_TYPE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'EXPERIMENTAL_DASH_FORM_DASH_TYPE' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary} to add
	 * @see			#EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty
	 */
	public void addEXPERIMENTAL_DASH_FORM_DASH_TYPE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary EXPERIMENTAL_DASH_FORM_DASH_TYPE) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'EXPERIMENTAL_DASH_FORM_DASH_TYPE' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary} created
	 * @see			#EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary addEXPERIMENTAL_DASH_FORM_DASH_TYPE() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'EXPERIMENTAL_DASH_FORM_DASH_TYPE' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary} with the factory
	 * and calling addEXPERIMENTAL_DASH_FORM_DASH_TYPE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary EXPERIMENTAL_DASH_FORM_DASH_TYPE)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#openControlledVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary addEXPERIMENTAL_DASH_FORM_DASH_TYPE(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'EXPERIMENTAL_DASH_FORM_DASH_TYPE' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary} to remove
	 * @see			#EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty
	 */
	public void removeEXPERIMENTAL_DASH_FORM_DASH_TYPE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary EXPERIMENTAL_DASH_FORM_DASH_TYPE) throws com.ibm.adtech.jastor.JastorException;
		
}