

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Interaction ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Interaction)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A biological relationship between two or more entities. 

Rationale: In BioPAX, interactions are atomic from a database modeling perspective, i.e. interactions can not be decomposed into sub-interactions. When representing non-atomic continuants with explicit subevents the pathway class should be used instead. Interactions are not necessarily  temporally atomic, for example genetic interactions cover a large span of time. Interactions as a formal concept is a continuant, it retains its identitiy regardless of time, or any differences in specific states or properties.

Usage: Interaction is a highly abstract class and in almost all cases it is more appropriate to use one of the subclasses of interaction. 
It is partially possible to define generic reactions by using generic participants. A more comprehensive method is planned for BioPAX L4 for covering all generic cases like oxidization of a generic alcohol.   

Synonyms: Process, relationship, event.

Examples: protein-protein interaction, biochemical reaction, enzyme catalysis^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface Interaction extends fr.curie.BiNoM.pathways.biopax.Entity, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#Interaction");
	

	/**
	 * The Jena Property for interactionType 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#interactionType)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : External controlled vocabulary annotating the interaction type, for example "phosphorylation". This is annotation useful for e.g. display on a web page or database searching, but may not be suitable for other computing tasks, like reasoning.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property interactionTypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#interactionType");


	/**
	 * The Jena Property for participant 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#participant)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : This property lists the entities that participate in this interaction. For example, in a biochemical reaction, the participants are the union of the reactants and the products of the reaction. This property has a number of sub-properties, such as LEFT and RIGHT used in the biochemicalInteraction class. Any participant listed in a sub-property will automatically be assumed to also be in PARTICIPANTS by a number of software systems, including Protege, so this property should not contain any instances if there are instances contained in a sub-property.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property participantProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#participant");






	/**
	 * Gets the 'interactionType' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.InteractionVocabulary}
	 * @see			#interactionTypeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.InteractionVocabulary getInteractionType() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'interactionType' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.InteractionVocabulary}
	 * @see			#interactionTypeProperty
	 */
	public void setInteractionType(fr.curie.BiNoM.pathways.biopax.InteractionVocabulary interactionType) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'interactionType' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.InteractionVocabulary}, the created value
	 * @see			#interactionTypeProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.InteractionVocabulary setInteractionType() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'interactionType' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.InteractionVocabulary} with the factory.
	 * and calling setInteractionType(fr.curie.BiNoM.pathways.biopax.InteractionVocabulary interactionType)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#InteractionVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.InteractionVocabulary}, the newly created value
	 * @see			#interactionTypeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.InteractionVocabulary setInteractionType(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Get an Iterator the 'participant' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Entity}
	 * @see			#participantProperty
	 */
	public java.util.Iterator getParticipant() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'participant' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Entity} to add
	 * @see			#participantProperty
	 */
	public void addParticipant(fr.curie.BiNoM.pathways.biopax.Entity participant) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'participant' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Entity} created
	 * @see			#participantProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Entity addParticipant() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'participant' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Entity} with the factory
	 * and calling addParticipant(fr.curie.BiNoM.pathways.biopax.Entity participant)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Entity.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#participantProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Entity addParticipant(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'participant' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Entity} to remove
	 * @see			#participantProperty
	 */
	public void removeParticipant(fr.curie.BiNoM.pathways.biopax.Entity participant) throws com.ibm.adtech.jastor.JastorException;
		
}