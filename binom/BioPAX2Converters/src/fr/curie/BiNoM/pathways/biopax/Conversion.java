

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Conversion ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Conversion)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: An interaction in which molecules of one or more physicalEntity pools are physically transformed and become a member of one or more other physicalEntity pools.

Comments: Conversions in BioPAX are stoichiometric and closed world, i.e. it is assumed that all of the participants are listed. Both properties are due to the law of mass conservation. 

Usage: Subclasses of conversion represent different types of transformation reflected by the properties of different physicalEntity. BiochemicalReactions will change the ModificationFeatures on a PhysicalEntity , Transport will change the CellularLocation and ComplexAssembly will change BindingFeatures. Generic Conversion class should only be used when the modification does not fit into one of these classes. 

Example: Opening of a voltage gated channel.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface Conversion extends fr.curie.BiNoM.pathways.biopax.Interaction, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#Conversion");
	

	/**
	 * The Jena Property for participantStoichiometry 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#participantStoichiometry)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Stoichiometry of the left and right participants.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property participantStoichiometryProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#participantStoichiometry");


	/**
	 * The Jena Property for spontaneous 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#spontaneous)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Specifies whether a conversion occurs spontaneously or not. If the spontaneity is not known, the SPONTANEOUS property should be left empty.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property spontaneousProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#spontaneous");


	/**
	 * The Jena Property for left 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#left)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The participants on the left side of the conversion interaction. Since conversion interactions may proceed in either the left-to-right or right-to-left direction, occupants of the LEFT property may be either reactants or products. LEFT is a sub-property of PARTICIPANTS.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property leftProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#left");


	/**
	 * The Jena Property for conversionDirection 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#conversionDirection)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : This property represents the direction of the reaction. If a reaction is fundamentally irreversible, then it will run in a single direction under all contexts. Otherwise it is reversible.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property conversionDirectionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#conversionDirection");


	/**
	 * The Jena Property for right 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#right)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The participants on the right side of the conversion interaction. Since conversion interactions may proceed in either the left-to-right or right-to-left direction, occupants of the RIGHT property may be either reactants or products. RIGHT is a sub-property of PARTICIPANTS.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property rightProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#right");






	/**
	 * Get an Iterator the 'participant' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity}
	 * @see			#participantProperty
	 */
	public java.util.Iterator getParticipant_asPhysicalEntity() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'participant' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to add
	 * @see			#participantProperty
	 */
	public void addParticipant(fr.curie.BiNoM.pathways.biopax.PhysicalEntity participant) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'participant' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} created
	 * @see			#participantProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addParticipant_asPhysicalEntity() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'participant' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} with the factory
	 * and calling addParticipant(fr.curie.BiNoM.pathways.biopax.PhysicalEntity participant)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#PhysicalEntity.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#participantProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addParticipant_asPhysicalEntity(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'participant' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to remove
	 * @see			#participantProperty
	 */
	public void removeParticipant(fr.curie.BiNoM.pathways.biopax.PhysicalEntity participant) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'participantStoichiometry' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Stoichiometry}
	 * @see			#participantStoichiometryProperty
	 */
	public java.util.Iterator getParticipantStoichiometry() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'participantStoichiometry' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Stoichiometry} to add
	 * @see			#participantStoichiometryProperty
	 */
	public void addParticipantStoichiometry(fr.curie.BiNoM.pathways.biopax.Stoichiometry participantStoichiometry) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'participantStoichiometry' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Stoichiometry} created
	 * @see			#participantStoichiometryProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Stoichiometry addParticipantStoichiometry() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'participantStoichiometry' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Stoichiometry} with the factory
	 * and calling addParticipantStoichiometry(fr.curie.BiNoM.pathways.biopax.Stoichiometry participantStoichiometry)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Stoichiometry.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#participantStoichiometryProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Stoichiometry addParticipantStoichiometry(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'participantStoichiometry' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Stoichiometry} to remove
	 * @see			#participantStoichiometryProperty
	 */
	public void removeParticipantStoichiometry(fr.curie.BiNoM.pathways.biopax.Stoichiometry participantStoichiometry) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Gets the 'spontaneous' property value
	 * @return		{@link java.lang.Boolean}
	 * @see			#spontaneousProperty
	 */
	public java.lang.Boolean getSpontaneous() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'spontaneous' property value
	 * @param		{@link java.lang.Boolean}
	 * @see			#spontaneousProperty
	 */
	public void setSpontaneous(java.lang.Boolean spontaneous) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'left' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity}
	 * @see			#leftProperty
	 */
	public java.util.Iterator getLeft() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'left' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to add
	 * @see			#leftProperty
	 */
	public void addLeft(fr.curie.BiNoM.pathways.biopax.PhysicalEntity left) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'left' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} created
	 * @see			#leftProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addLeft() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'left' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} with the factory
	 * and calling addLeft(fr.curie.BiNoM.pathways.biopax.PhysicalEntity left)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#PhysicalEntity.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#leftProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addLeft(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'left' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to remove
	 * @see			#leftProperty
	 */
	public void removeLeft(fr.curie.BiNoM.pathways.biopax.PhysicalEntity left) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Gets the 'conversionDirection' property value
	 * @return		{@link java.lang.String}
	 * @see			#conversionDirectionProperty
	 */
	public java.lang.String getConversionDirection() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'conversionDirection' property value
	 * @param		{@link java.lang.String}
	 * @see			#conversionDirectionProperty
	 */
	public void setConversionDirection(java.lang.String conversionDirection) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'right' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity}
	 * @see			#rightProperty
	 */
	public java.util.Iterator getRight() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'right' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to add
	 * @see			#rightProperty
	 */
	public void addRight(fr.curie.BiNoM.pathways.biopax.PhysicalEntity right) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'right' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} created
	 * @see			#rightProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addRight() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'right' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} with the factory
	 * and calling addRight(fr.curie.BiNoM.pathways.biopax.PhysicalEntity right)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#PhysicalEntity.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#rightProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addRight(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'right' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to remove
	 * @see			#rightProperty
	 */
	public void removeRight(fr.curie.BiNoM.pathways.biopax.PhysicalEntity right) throws com.ibm.adtech.jastor.JastorException;
		
}