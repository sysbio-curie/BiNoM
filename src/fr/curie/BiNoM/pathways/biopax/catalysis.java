

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for catalysis ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#catalysis)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A control interaction in which a physical entity (a catalyst) increases the rate of a conversion interaction by lowering its activation energy. Instances of this class describe a pairing between a catalyzing entity and a catalyzed conversion.
Comment: A separate catalysis instance should be created for each different conversion that a physicalEntity may catalyze and for each different physicalEntity that may catalyze a conversion. For example, a bifunctional enzyme that catalyzes two different biochemical reactions would be linked to each of those biochemical reactions by two separate instances of the catalysis class. Also, catalysis reactions from multiple different organisms could be linked to the same generic biochemical reaction (a biochemical reaction is generic if it only includes small molecules). Generally, the enzyme catalyzing a conversion is known and the use of this class is obvious. In the cases where a catalyzed reaction is known to occur but the enzyme is not known, a catalysis instance should be created without a controller specified (i.e. the CONTROLLER property should remain empty).
Synonyms: facilitation, acceleration.
Examples: The catalysis of a biochemical reaction by an enzyme, the enabling of a transport interaction by a membrane pore complex, and the facilitation of a complex assembly by a scaffold protein. Hexokinase -> (The "Glucose + ATP -> Glucose-6-phosphate +ADP" reaction). A plasma membrane Na+/K+ ATPase is an active transporter (antiport pump) using the energy of ATP to pump Na+ out of the cell and K+ in. Na+ from cytoplasm to extracellular space would be described in a transport instance. K+ from extracellular space to cytoplasm would be described in a transport instance. The ATPase pump would be stored in a catalysis instance controlling each of the above transport instances. A biochemical reaction that does not occur by itself under physiological conditions, but has been observed to occur in the presence of cell extract, likely via one or more unknown enzymes present in the extract, would be stored in the CONTROLLED property, with the CONTROLLER property empty.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface catalysis extends fr.curie.BiNoM.pathways.biopax.control, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#catalysis");
	

	/**
	 * The Jena Property for DIRECTION 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#DIRECTION)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Specifies the reaction direction of the interaction catalyzed by this instance of the catalysis class.

Possible values of this slot are:

REVERSIBLE: Interaction occurs in both directions in physiological settings.

PHYSIOL-LEFT-TO-RIGHT
PHYSIOL-RIGHT-TO-LEFT
The interaction occurs in the specified direction in physiological settings,  because of several possible factors including the energetics of the reaction, local concentrations
of reactants and products, and the regulation of the enzyme or its expression.

IRREVERSIBLE-LEFT-TO-RIGHT
IRREVERSIBLE-RIGHT-TO-LEFT
For all practical purposes, the interactions occurs only in the specified direction in physiological settings, because of chemical properties of the reaction.

(This definition from EcoCyc)^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property DIRECTIONProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#DIRECTION");


	/**
	 * The Jena Property for COFACTOR 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#COFACTOR)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Any cofactor(s) or coenzyme(s) required for catalysis of the conversion by the enzyme. COFACTOR is a sub-property of PARTICIPANTS.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property COFACTORProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#COFACTOR");






	/**
	 * Gets the 'CONTROLLED' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.conversion}
	 * @see			#CONTROLLEDProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.conversion getCONTROLLED_asconversion() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'CONTROLLED' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.conversion}
	 * @see			#CONTROLLEDProperty
	 */
	public void setCONTROLLED(fr.curie.BiNoM.pathways.biopax.conversion CONTROLLED) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'CONTROLLED' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.conversion}, the created value
	 * @see			#CONTROLLEDProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.conversion setCONTROLLED_asconversion() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'CONTROLLED' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.conversion} with the factory.
	 * and calling setCONTROLLED(fr.curie.BiNoM.pathways.biopax.conversion CONTROLLED)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#conversion.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.conversion}, the newly created value
	 * @see			#CONTROLLEDProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.conversion setCONTROLLED_asconversion(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'DIRECTION' property value
	 * @return		{@link java.lang.String}
	 * @see			#DIRECTIONProperty
	 */
	public java.lang.String getDIRECTION() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'DIRECTION' property value
	 * @param		{@link java.lang.String}
	 * @see			#DIRECTIONProperty
	 */
	public void setDIRECTION(java.lang.String DIRECTION) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'COFACTOR' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant}
	 * @see			#COFACTORProperty
	 */
	public java.util.Iterator getCOFACTOR() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'COFACTOR' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} to add
	 * @see			#COFACTORProperty
	 */
	public void addCOFACTOR(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant COFACTOR) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'COFACTOR' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} created
	 * @see			#COFACTORProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addCOFACTOR() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'COFACTOR' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} with the factory
	 * and calling addCOFACTOR(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant COFACTOR)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#physicalEntityParticipant.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#COFACTORProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addCOFACTOR(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'COFACTOR' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} to remove
	 * @see			#COFACTORProperty
	 */
	public void removeCOFACTOR(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant COFACTOR) throws com.ibm.adtech.jastor.JastorException;
		
}