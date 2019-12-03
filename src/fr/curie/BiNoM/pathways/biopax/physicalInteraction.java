

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for physicalInteraction ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#physicalInteraction)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: An interaction in which at least one participant is a physical entity, e.g. a binding event.
Comment: This class should be used by default for representing molecular interactions, such as those defined by PSI-MI level 2. The participants in a molecular interaction should be listed in the PARTICIPANTS slot. Note that this is one of the few cases in which the PARTICPANT slot should be directly populated with instances (see comments on the PARTICPANTS property in the interaction class description). If sufficient information on the nature of a molecular interaction is available, a more specific BioPAX interaction class should be used.
Example: Two proteins observed to interact in a yeast-two-hybrid experiment where there is not enough experimental evidence to suggest that the proteins are forming a complex by themselves without any indirect involvement of other proteins. This is the case for most large-scale yeast two-hybrid screens.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface physicalInteraction extends fr.curie.BiNoM.pathways.biopax.interaction, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#physicalInteraction");
	

	/**
	 * The Jena Property for INTERACTION_DASH_TYPE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#INTERACTION-TYPE)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : External controlled vocabulary characterizing the interaction type, for example "phosphorylation".^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property INTERACTION_DASH_TYPEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#INTERACTION-TYPE");






	/**
	 * Get an Iterator the 'PARTICIPANTS' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant}
	 * @see			#PARTICIPANTSProperty
	 */
	public java.util.Iterator getPARTICIPANTS_asphysicalEntityParticipant() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'PARTICIPANTS' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} to add
	 * @see			#PARTICIPANTSProperty
	 */
	public void addPARTICIPANTS(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANTS) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'PARTICIPANTS' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} created
	 * @see			#PARTICIPANTSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addPARTICIPANTS_asphysicalEntityParticipant() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'PARTICIPANTS' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} with the factory
	 * and calling addPARTICIPANTS(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANTS)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#physicalEntityParticipant.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#PARTICIPANTSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addPARTICIPANTS_asphysicalEntityParticipant(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'PARTICIPANTS' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} to remove
	 * @see			#PARTICIPANTSProperty
	 */
	public void removePARTICIPANTS(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANTS) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'INTERACTION_DASH_TYPE' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}
	 * @see			#INTERACTION_DASH_TYPEProperty
	 */
	public java.util.Iterator getINTERACTION_DASH_TYPE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'INTERACTION_DASH_TYPE' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary} to add
	 * @see			#INTERACTION_DASH_TYPEProperty
	 */
	public void addINTERACTION_DASH_TYPE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary INTERACTION_DASH_TYPE) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'INTERACTION_DASH_TYPE' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary} created
	 * @see			#INTERACTION_DASH_TYPEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary addINTERACTION_DASH_TYPE() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'INTERACTION_DASH_TYPE' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary} with the factory
	 * and calling addINTERACTION_DASH_TYPE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary INTERACTION_DASH_TYPE)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#openControlledVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#INTERACTION_DASH_TYPEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary addINTERACTION_DASH_TYPE(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'INTERACTION_DASH_TYPE' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary} to remove
	 * @see			#INTERACTION_DASH_TYPEProperty
	 */
	public void removeINTERACTION_DASH_TYPE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary INTERACTION_DASH_TYPE) throws com.ibm.adtech.jastor.JastorException;
		
}