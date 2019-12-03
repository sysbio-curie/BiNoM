

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Control ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Control)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: An interaction in which one entity regulates, modifies, or otherwise influences a continuant entity, i.e. pathway or interaction. 

Usage: Conceptually, physical entities are involved in interactions (or events) and the events are controlled or modified, not the physical entities themselves. For example, a kinase activating a protein is a frequent event in signaling pathways and is usually represented as an 'activation' arrow from the kinase to the substrate in signaling diagrams. This is an abstraction, called "Activity Flow" representation,  that can be ambiguous without context. In BioPAX, this information should be captured as the kinase catalyzing (via an instance of the catalysis class) a Biochemical Reaction in which the substrate is phosphorylated. 
Subclasses of control define types specific to the biological process that is being controlled and should be used instead of the generic "control" class when applicable. 

A control can potentially have multiple controllers. This acts as a logical AND, i.e. both controllers are needed to regulate the  controlled event. Alternatively multiple controllers can control the same event and this acts as a logical OR, i.e. any one of them is sufficient to regulate the controlled event. Using this structure it is possible to describe arbitrary control logic using BioPAX.

Rationale: Control can be temporally non-atomic, for example a pathway can control another pathway in BioPAX.  
Synonyms: regulation, mediation

Examples: A small molecule that inhibits a pathway by an unknown mechanism.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface Control extends fr.curie.BiNoM.pathways.biopax.Interaction, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#Control");
	

	/**
	 * The Jena Property for controlled 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#controlled)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The entity that is controlled, e.g., in a biochemical reaction, the reaction is controlled by an enzyme. CONTROLLED is a sub-property of PARTICIPANTS.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property controlledProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#controlled");


	/**
	 * The Jena Property for controller 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#controller)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The controlling entity, e.g., in a biochemical reaction, an enzyme is the controlling entity of the reaction. CONTROLLER is a sub-property of PARTICIPANTS.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property controllerProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#controller");


	/**
	 * The Jena Property for controlType 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#controlType)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Defines the nature of the control relationship between the CONTROLLER and the CONTROLLED entities.

The following terms are possible values:

ACTIVATION: General activation. Compounds that activate the specified enzyme activity by an unknown mechanism. The mechanism is defined as unknown, because either the mechanism has yet to be elucidated in the experimental literature, or the paper(s) curated thus far do not define the mechanism, and a full literature search has yet to be performed.

The following term can not be used in the catalysis class:
INHIBITION: General inhibition. Compounds that inhibit the specified enzyme activity by an unknown mechanism. The mechanism is defined as unknown, because either the mechanism has yet to be elucidated in the experimental literature, or the paper(s) curated thus far do not define the mechanism, and a full literature search has yet to be performed.

The following terms can only be used in the modulation class (these definitions from EcoCyc):
INHIBITION-ALLOSTERIC
Allosteric inhibitors decrease the specified enzyme activity by binding reversibly to the enzyme and inducing a conformational change that decreases the affinity of the enzyme to its substrates without affecting its VMAX. Allosteric inhibitors can be competitive or noncompetitive inhibitors, therefore, those inhibition categories can be used in conjunction with this category.

INHIBITION-COMPETITIVE
Competitive inhibitors are compounds that competitively inhibit the specified enzyme activity by binding reversibly to the enzyme and preventing the substrate from binding. Binding of the inhibitor and substrate are mutually exclusive because it is assumed that the inhibitor and substrate can both bind only to the free enzyme. A competitive inhibitor can either bind to the active site of the enzyme, directly excluding the substrate from binding there, or it can bind to another site on the enzyme, altering the conformation of the enzyme such that the substrate can not bind to the active site.

INHIBITION-IRREVERSIBLE
Irreversible inhibitors are compounds that irreversibly inhibit the specified enzyme activity by binding to the enzyme and dissociating so slowly that it is considered irreversible. For example, alkylating agents, such as iodoacetamide, irreversibly inhibit the catalytic activity of some enzymes by modifying cysteine side chains.

INHIBITION-NONCOMPETITIVE
Noncompetitive inhibitors are compounds that noncompetitively inhibit the specified enzyme by binding reversibly to both the free enzyme and to the enzyme-substrate complex. The inhibitor and substrate may be bound to the enzyme simultaneously and do not exclude each other. However, only the enzyme-substrate complex (not the enzyme-substrate-inhibitor complex) is catalytically active.

INHIBITION-OTHER
Compounds that inhibit the specified enzyme activity by a mechanism that has been characterized, but that cannot be clearly classified as irreversible, competitive, noncompetitive, uncompetitive, or allosteric.

INHIBITION-UNCOMPETITIVE
Uncompetitive inhibitors are compounds that uncompetitively inhibit the specified enzyme activity by binding reversibly to the enzyme-substrate complex but not to the enzyme alone.

ACTIVATION-NONALLOSTERIC
Nonallosteric activators increase the specified enzyme activity by means other than allosteric.

ACTIVATION-ALLOSTERIC
Allosteric activators increase the specified enzyme activity by binding reversibly to the enzyme and inducing a conformational change that increases the affinity of the enzyme to its substrates without affecting its VMAX.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property controlTypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#controlType");






	/**
	 * Gets the 'controlled' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Interaction}
	 * @see			#controlledProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Interaction getControlled_asInteraction() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'controlled' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.Interaction}
	 * @see			#controlledProperty
	 */
	public void setControlled(fr.curie.BiNoM.pathways.biopax.Interaction controlled) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'controlled' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Interaction}, the created value
	 * @see			#controlledProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.Interaction setControlled_asInteraction() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'controlled' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Interaction} with the factory.
	 * and calling setControlled(fr.curie.BiNoM.pathways.biopax.Interaction controlled)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Interaction.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Interaction}, the newly created value
	 * @see			#controlledProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Interaction setControlled_asInteraction(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'controlled' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Pathway}
	 * @see			#controlledProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Pathway getControlled_asPathway() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'controlled' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.Pathway}
	 * @see			#controlledProperty
	 */
	public void setControlled(fr.curie.BiNoM.pathways.biopax.Pathway controlled) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'controlled' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Pathway}, the created value
	 * @see			#controlledProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.Pathway setControlled_asPathway() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'controlled' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Pathway} with the factory.
	 * and calling setControlled(fr.curie.BiNoM.pathways.biopax.Pathway controlled)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Pathway.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Pathway}, the newly created value
	 * @see			#controlledProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Pathway setControlled_asPathway(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Get an Iterator the 'controller' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Pathway}
	 * @see			#controllerProperty
	 */
	public java.util.Iterator getController_asPathway() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'controller' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Pathway} to add
	 * @see			#controllerProperty
	 */
	public void addController(fr.curie.BiNoM.pathways.biopax.Pathway controller) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'controller' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Pathway} created
	 * @see			#controllerProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Pathway addController_asPathway() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'controller' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Pathway} with the factory
	 * and calling addController(fr.curie.BiNoM.pathways.biopax.Pathway controller)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Pathway.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#controllerProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Pathway addController_asPathway(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'controller' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Pathway} to remove
	 * @see			#controllerProperty
	 */
	public void removeController(fr.curie.BiNoM.pathways.biopax.Pathway controller) throws com.ibm.adtech.jastor.JastorException;
		
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
		
	/**
	 * Gets the 'controlType' property value
	 * @return		{@link java.lang.String}
	 * @see			#controlTypeProperty
	 */
	public java.lang.String getControlType() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'controlType' property value
	 * @param		{@link java.lang.String}
	 * @see			#controlTypeProperty
	 */
	public void setControlType(java.lang.String controlType) throws com.ibm.adtech.jastor.JastorException;

}