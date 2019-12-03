

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for control ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#control)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: An interaction in which one entity regulates, modifies, or otherwise influences another. Two types of control interactions are defined: activation and inhibition.
Comment: In general, the targets of control processes (i.e. occupants of the CONTROLLED property) should be interactions. Conceptually, physical entities are involved in interactions (or events) and the events should be controlled or modified, not the physical entities themselves. For example, a kinase activating a protein is a frequent event in signaling pathways and is usually represented as an 'activation' arrow from the kinase to the substrate in signaling diagrams. This is an abstraction that can be ambiguous out of context. In BioPAX, this information should be captured as the kinase catalyzing (via an instance of the catalysis class) a reaction in which the substrate is phosphorylated, instead of as a control interaction in which the kinase activates the substrate. Since this class is a superclass for specific types of control, instances of the control class should only be created when none of its subclasses are applicable.
Synonyms: regulation, mediation
Examples: A small molecule that inhibits a pathway by an unknown mechanism controls the pathway.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface control extends fr.curie.BiNoM.pathways.biopax.physicalInteraction, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#control");
	

	/**
	 * The Jena Property for CONTROLLER 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#CONTROLLER)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The controlling entity, e.g., in a biochemical reaction, an enzyme is the controlling entity of the reaction. CONTROLLER is a sub-property of PARTICIPANTS.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property CONTROLLERProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#CONTROLLER");


	/**
	 * The Jena Property for CONTROLLED 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#CONTROLLED)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The entity that is controlled, e.g., in a biochemical reaction, the reaction is controlled by an enzyme. CONTROLLED is a sub-property of PARTICIPANTS.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property CONTROLLEDProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#CONTROLLED");


	/**
	 * The Jena Property for CONTROL_DASH_TYPE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#CONTROL-TYPE)</p>
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
	public static com.hp.hpl.jena.rdf.model.Property CONTROL_DASH_TYPEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#CONTROL-TYPE");






	/**
	 * Gets the 'CONTROLLER' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant}
	 * @see			#CONTROLLERProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant getCONTROLLER() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'CONTROLLER' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant}
	 * @see			#CONTROLLERProperty
	 */
	public void setCONTROLLER(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant CONTROLLER) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'CONTROLLER' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant}, the created value
	 * @see			#CONTROLLERProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant setCONTROLLER() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'CONTROLLER' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} with the factory.
	 * and calling setCONTROLLER(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant CONTROLLER)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#physicalEntityParticipant.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant}, the newly created value
	 * @see			#CONTROLLERProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant setCONTROLLER(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'CONTROLLED' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.interaction}
	 * @see			#CONTROLLEDProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.interaction getCONTROLLED_asinteraction() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'CONTROLLED' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.interaction}
	 * @see			#CONTROLLEDProperty
	 */
	public void setCONTROLLED(fr.curie.BiNoM.pathways.biopax.interaction CONTROLLED) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'CONTROLLED' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.interaction}, the created value
	 * @see			#CONTROLLEDProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.interaction setCONTROLLED_asinteraction() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'CONTROLLED' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.interaction} with the factory.
	 * and calling setCONTROLLED(fr.curie.BiNoM.pathways.biopax.interaction CONTROLLED)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#interaction.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.interaction}, the newly created value
	 * @see			#CONTROLLEDProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.interaction setCONTROLLED_asinteraction(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'CONTROLLED' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.pathway}
	 * @see			#CONTROLLEDProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.pathway getCONTROLLED_aspathway() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'CONTROLLED' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.pathway}
	 * @see			#CONTROLLEDProperty
	 */
	public void setCONTROLLED(fr.curie.BiNoM.pathways.biopax.pathway CONTROLLED) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'CONTROLLED' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.pathway}, the created value
	 * @see			#CONTROLLEDProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.pathway setCONTROLLED_aspathway() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'CONTROLLED' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.pathway} with the factory.
	 * and calling setCONTROLLED(fr.curie.BiNoM.pathways.biopax.pathway CONTROLLED)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#pathway.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.pathway}, the newly created value
	 * @see			#CONTROLLEDProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.pathway setCONTROLLED_aspathway(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'CONTROL_DASH_TYPE' property value
	 * @return		{@link java.lang.String}
	 * @see			#CONTROL_DASH_TYPEProperty
	 */
	public java.lang.String getCONTROL_DASH_TYPE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'CONTROL_DASH_TYPE' property value
	 * @param		{@link java.lang.String}
	 * @see			#CONTROL_DASH_TYPEProperty
	 */
	public void setCONTROL_DASH_TYPE(java.lang.String CONTROL_DASH_TYPE) throws com.ibm.adtech.jastor.JastorException;

}