

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Modulation ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Modulation)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A control interaction in which a physical entity modulates a catalysis interaction. 

Rationale: Biologically, most modulation interactions describe an interaction in which a small molecule alters the ability of an enzyme to catalyze a specific reaction. Instances of this class describe a pairing between a modulating entity and a catalysis interaction.

Usage:  A typical modulation instance has a small molecule as the controller entity and a catalysis instance as the controlled entity. A separate modulation instance should be created for each different catalysis instance that a physical entity may modulate, and for each different physical entity that may modulate a catalysis instance.
Examples: Allosteric activation and competitive inhibition of an enzyme's ability to catalyze a specific reaction.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface Modulation extends fr.curie.BiNoM.pathways.biopax.Control, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#Modulation");
	





	/**
	 * Gets the 'controlled' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Catalysis}
	 * @see			#controlledProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Catalysis getControlled_asCatalysis() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'controlled' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.Catalysis}
	 * @see			#controlledProperty
	 */
	public void setControlled(fr.curie.BiNoM.pathways.biopax.Catalysis controlled) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'controlled' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Catalysis}, the created value
	 * @see			#controlledProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.Catalysis setControlled_asCatalysis() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'controlled' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Catalysis} with the factory.
	 * and calling setControlled(fr.curie.BiNoM.pathways.biopax.Catalysis controlled)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Catalysis.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Catalysis}, the newly created value
	 * @see			#controlledProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Catalysis setControlled_asCatalysis(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
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