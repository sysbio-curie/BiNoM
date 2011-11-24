

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for TemplateReactionRegulation ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#TemplateReactionRegulation)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: Regulation of an expression reaction by a controlling element such as a transcription factor or microRNA. 

Usage: To represent the binding of the transcription factor to a regulatory element in the TemplateReaction, create a complex of the transcription factor and the regulatory element and set that as the controller.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface TemplateReactionRegulation extends fr.curie.BiNoM.pathways.biopax.Control, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#TemplateReactionRegulation");
	





	/**
	 * Gets the 'controlled' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.TemplateReaction}
	 * @see			#controlledProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.TemplateReaction getControlled_asTemplateReaction() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'controlled' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.TemplateReaction}
	 * @see			#controlledProperty
	 */
	public void setControlled(fr.curie.BiNoM.pathways.biopax.TemplateReaction controlled) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'controlled' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.TemplateReaction}, the created value
	 * @see			#controlledProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.TemplateReaction setControlled_asTemplateReaction() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'controlled' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.TemplateReaction} with the factory.
	 * and calling setControlled(fr.curie.BiNoM.pathways.biopax.TemplateReaction controlled)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#TemplateReaction.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.TemplateReaction}, the newly created value
	 * @see			#controlledProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.TemplateReaction setControlled_asTemplateReaction(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Get an Iterator the 'controller' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity}
	 * @see			#controllerProperty
	 */
	public java.util.Iterator getController_asPhysicalEntity() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'controller' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to add
	 * @see			#controllerProperty
	 */
	public void addController(fr.curie.BiNoM.pathways.biopax.PhysicalEntity controller) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'controller' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} created
	 * @see			#controllerProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addController_asPhysicalEntity() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'controller' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} with the factory
	 * and calling addController(fr.curie.BiNoM.pathways.biopax.PhysicalEntity controller)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#PhysicalEntity.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#controllerProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addController_asPhysicalEntity(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'controller' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to remove
	 * @see			#controllerProperty
	 */
	public void removeController(fr.curie.BiNoM.pathways.biopax.PhysicalEntity controller) throws com.ibm.adtech.jastor.JastorException;
		
}