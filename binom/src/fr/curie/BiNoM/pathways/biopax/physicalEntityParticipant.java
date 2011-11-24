

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for physicalEntityParticipant ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#physicalEntityParticipant)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: Any additional special characteristics of a physical entity in the context of an interaction. These currently include stoichiometric coefficient and cellular location, but this list may be expanded in later levels.
Comment: PhysicalEntityParticipants should not be used in multiple interaction or complex instances. Instead, each interaction and complex should reference its own unique set of physicalEntityParticipants. The reason for this is that a user may add new information about a physicalEntityParticipant for one interaction or complex, such as the presence of a previously unknown post-translational modification, and unwittingly invalidate the physicalEntityParticipant for the other interactions or complexes that make use of it.
Example: In the interaction describing the transport of L-arginine into the cytoplasm in E. coli, the LEFT property in the interaction would be filled with an instance of physicalEntityParticipant that specified the location of L-arginine as periplasm and the stoichiometric coefficient as one.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface physicalEntityParticipant extends fr.curie.BiNoM.pathways.biopax.utilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#physicalEntityParticipant");
	

	/**
	 * The Jena Property for PHYSICAL_DASH_ENTITY 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#PHYSICAL-ENTITY)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The physical entity annotated with stoichiometry and cellular location attributes from the physicalEntityParticipant instance.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property PHYSICAL_DASH_ENTITYProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#PHYSICAL-ENTITY");


	/**
	 * The Jena Property for STOICHIOMETRIC_DASH_COEFFICIENT 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#STOICHIOMETRIC-COEFFICIENT)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Each value of this property represents the stoichiometric coefficient for one of the physical entities in an interaction or complex. For a given interaction, the stoichiometry should always be used where possible instead of representing the number of participants with separate instances of each participant. If there are three ATP molecules, one ATP molecule should be represented as a participant and the stoichiometry should be set to 3.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property STOICHIOMETRIC_DASH_COEFFICIENTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#STOICHIOMETRIC-COEFFICIENT");


	/**
	 * The Jena Property for CELLULAR_DASH_LOCATION 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#CELLULAR-LOCATION)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : A cellular location, e.g. 'cytoplasm'. This should reference a term in the Gene Ontology Cellular Component ontology. The location referred to by this property should be as specific as is known. If an interaction is known to occur in multiple locations, separate interactions (and physicalEntityParticipants) must be created for each different location. Note: If a location is unknown then the GO term for 'cellular component unknown' (GO:0008372) should be used in the LOCATION property. If the location of a participant in a complex is unspecified, it may be assumed to be the same location as that of the complex. In case of conflicting information, the location of the most outer layer of any nesting should be considered correct. Note: Cellular location describes a specific location of a physical entity as it would be used in e.g. a transport reaction. It does not describe all of the possible locations that the physical entity could be in the cell.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property CELLULAR_DASH_LOCATIONProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#CELLULAR-LOCATION");






	/**
	 * Gets the 'PHYSICAL_DASH_ENTITY' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.physicalEntity}
	 * @see			#PHYSICAL_DASH_ENTITYProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntity getPHYSICAL_DASH_ENTITY() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'PHYSICAL_DASH_ENTITY' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.physicalEntity}
	 * @see			#PHYSICAL_DASH_ENTITYProperty
	 */
	public void setPHYSICAL_DASH_ENTITY(fr.curie.BiNoM.pathways.biopax.physicalEntity PHYSICAL_DASH_ENTITY) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'PHYSICAL_DASH_ENTITY' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.physicalEntity}, the created value
	 * @see			#PHYSICAL_DASH_ENTITYProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.physicalEntity setPHYSICAL_DASH_ENTITY() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'PHYSICAL_DASH_ENTITY' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.physicalEntity} with the factory.
	 * and calling setPHYSICAL_DASH_ENTITY(fr.curie.BiNoM.pathways.biopax.physicalEntity PHYSICAL_DASH_ENTITY)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#physicalEntity.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.physicalEntity}, the newly created value
	 * @see			#PHYSICAL_DASH_ENTITYProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntity setPHYSICAL_DASH_ENTITY(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'STOICHIOMETRIC_DASH_COEFFICIENT' property value
	 * @return		{@link java.lang.Double}
	 * @see			#STOICHIOMETRIC_DASH_COEFFICIENTProperty
	 */
	public java.lang.Double getSTOICHIOMETRIC_DASH_COEFFICIENT() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'STOICHIOMETRIC_DASH_COEFFICIENT' property value
	 * @param		{@link java.lang.Double}
	 * @see			#STOICHIOMETRIC_DASH_COEFFICIENTProperty
	 */
	public void setSTOICHIOMETRIC_DASH_COEFFICIENT(java.lang.Double STOICHIOMETRIC_DASH_COEFFICIENT) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'CELLULAR_DASH_LOCATION' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}
	 * @see			#CELLULAR_DASH_LOCATIONProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary getCELLULAR_DASH_LOCATION() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'CELLULAR_DASH_LOCATION' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}
	 * @see			#CELLULAR_DASH_LOCATIONProperty
	 */
	public void setCELLULAR_DASH_LOCATION(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary CELLULAR_DASH_LOCATION) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'CELLULAR_DASH_LOCATION' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}, the created value
	 * @see			#CELLULAR_DASH_LOCATIONProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary setCELLULAR_DASH_LOCATION() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'CELLULAR_DASH_LOCATION' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary} with the factory.
	 * and calling setCELLULAR_DASH_LOCATION(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary CELLULAR_DASH_LOCATION)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#openControlledVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}, the newly created value
	 * @see			#CELLULAR_DASH_LOCATIONProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary setCELLULAR_DASH_LOCATION(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}