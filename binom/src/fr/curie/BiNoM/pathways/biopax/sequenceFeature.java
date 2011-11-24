

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for sequenceFeature ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#sequenceFeature)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A feature on a sequence relevant to an interaction, such as a binding site or post-translational modification.
Examples: A phosphorylation on a protein.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface sequenceFeature extends fr.curie.BiNoM.pathways.biopax.utilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#sequenceFeature");
	

	/**
	 * The Jena Property for FEATURE_DASH_TYPE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#FEATURE-TYPE)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Description and classification of the feature.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property FEATURE_DASH_TYPEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#FEATURE-TYPE");


	/**
	 * The Jena Property for FEATURE_DASH_LOCATION 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#FEATURE-LOCATION)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Location of the feature on the sequence of the interactor. One feature may have more than one location, used e.g. for features which involve sequence positions close in the folded, three-dimensional state of a protein, but non-continuous along the sequence.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property FEATURE_DASH_LOCATIONProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#FEATURE-LOCATION");


	/**
	 * The Jena Property for SHORT_DASH_NAME 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#SHORT-NAME)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : An abbreviated name for this entity, preferably a name that is short enough to be used in a visualization application to label a graphical element that represents this entity. If no short name is available, an xref may be used for this purpose by the visualization application.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property SHORT_DASH_NAMEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SHORT-NAME");


	/**
	 * The Jena Property for SYNONYMS 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#SYNONYMS)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : One or more synonyms for the name of this entity. This should include the values of the NAME and SHORT-NAME property so that it is easy to find all known names in one place.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property SYNONYMSProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SYNONYMS");


	/**
	 * The Jena Property for NAME 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#NAME)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The preferred full name for this entity.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property NAMEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#NAME");


	/**
	 * The Jena Property for XREF 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#XREF)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Values of this property define external cross-references from this entity to entities in external databases.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property XREFProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#XREF");






	/**
	 * Gets the 'FEATURE_DASH_TYPE' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}
	 * @see			#FEATURE_DASH_TYPEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary getFEATURE_DASH_TYPE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'FEATURE_DASH_TYPE' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}
	 * @see			#FEATURE_DASH_TYPEProperty
	 */
	public void setFEATURE_DASH_TYPE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary FEATURE_DASH_TYPE) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'FEATURE_DASH_TYPE' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}, the created value
	 * @see			#FEATURE_DASH_TYPEProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary setFEATURE_DASH_TYPE() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'FEATURE_DASH_TYPE' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary} with the factory.
	 * and calling setFEATURE_DASH_TYPE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary FEATURE_DASH_TYPE)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#openControlledVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}, the newly created value
	 * @see			#FEATURE_DASH_TYPEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary setFEATURE_DASH_TYPE(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Get an Iterator the 'FEATURE_DASH_LOCATION' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.sequenceLocation}
	 * @see			#FEATURE_DASH_LOCATIONProperty
	 */
	public java.util.Iterator getFEATURE_DASH_LOCATION() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'FEATURE_DASH_LOCATION' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.sequenceLocation} to add
	 * @see			#FEATURE_DASH_LOCATIONProperty
	 */
	public void addFEATURE_DASH_LOCATION(fr.curie.BiNoM.pathways.biopax.sequenceLocation FEATURE_DASH_LOCATION) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'FEATURE_DASH_LOCATION' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.sequenceLocation} created
	 * @see			#FEATURE_DASH_LOCATIONProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.sequenceLocation addFEATURE_DASH_LOCATION() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'FEATURE_DASH_LOCATION' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.sequenceLocation} with the factory
	 * and calling addFEATURE_DASH_LOCATION(fr.curie.BiNoM.pathways.biopax.sequenceLocation FEATURE_DASH_LOCATION)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#sequenceLocation.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#FEATURE_DASH_LOCATIONProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.sequenceLocation addFEATURE_DASH_LOCATION(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'FEATURE_DASH_LOCATION' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.sequenceLocation} to remove
	 * @see			#FEATURE_DASH_LOCATIONProperty
	 */
	public void removeFEATURE_DASH_LOCATION(fr.curie.BiNoM.pathways.biopax.sequenceLocation FEATURE_DASH_LOCATION) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Gets the 'SHORT_DASH_NAME' property value
	 * @return		{@link java.lang.String}
	 * @see			#SHORT_DASH_NAMEProperty
	 */
	public java.lang.String getSHORT_DASH_NAME() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'SHORT_DASH_NAME' property value
	 * @param		{@link java.lang.String}
	 * @see			#SHORT_DASH_NAMEProperty
	 */
	public void setSHORT_DASH_NAME(java.lang.String SHORT_DASH_NAME) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'SYNONYMS' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#SYNONYMSProperty
	 */
	public java.util.Iterator getSYNONYMS() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'SYNONYMS' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#SYNONYMSProperty
	 */
	public void addSYNONYMS(java.lang.String SYNONYMS) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'SYNONYMS' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#SYNONYMSProperty
	 */
	public void removeSYNONYMS(java.lang.String SYNONYMS) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'NAME' property value
	 * @return		{@link java.lang.String}
	 * @see			#NAMEProperty
	 */
	public java.lang.String getNAME() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'NAME' property value
	 * @param		{@link java.lang.String}
	 * @see			#NAMEProperty
	 */
	public void setNAME(java.lang.String NAME) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'XREF' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.xref}
	 * @see			#XREFProperty
	 */
	public java.util.Iterator getXREF() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'XREF' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.xref} to add
	 * @see			#XREFProperty
	 */
	public void addXREF(fr.curie.BiNoM.pathways.biopax.xref XREF) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'XREF' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.xref} created
	 * @see			#XREFProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.xref addXREF() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'XREF' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.xref} with the factory
	 * and calling addXREF(fr.curie.BiNoM.pathways.biopax.xref XREF)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#xref.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#XREFProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.xref addXREF(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'XREF' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.xref} to remove
	 * @see			#XREFProperty
	 */
	public void removeXREF(fr.curie.BiNoM.pathways.biopax.xref XREF) throws com.ibm.adtech.jastor.JastorException;
		
}