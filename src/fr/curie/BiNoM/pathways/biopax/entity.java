

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for entity ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#entity)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A discrete biological unit used when describing pathways.
Comment: This is the root class for all biological concepts in the ontology, which include pathways, interactions and physical entities. As the most abstract class in the ontology, instances of the entity class should never be created. Instead, more specific classes should be used.
Synonyms: thing, object, bioentity.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface entity extends com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#entity");
	

	/**
	 * The Jena Property for DATA_DASH_SOURCE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#DATA-SOURCE)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : A free text description of the source of this data, e.g. a database or person name. This property should be used to describe the source of the data. This is meant to be used by databases that export their data to the BioPAX format or by systems that are integrating data from multiple sources. The granularity of use (specifying the data source in many or few instances) is up to the user. It is intended that this property report the last data source, not all data sources that the data has passed through from creation.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property DATA_DASH_SOURCEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#DATA-SOURCE");


	/**
	 * The Jena Property for AVAILABILITY 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#AVAILABILITY)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Describes the availability of this data (e.g. a copyright statement).@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property AVAILABILITYProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#AVAILABILITY");


	/**
	 * The Jena Property for COMMENT 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#COMMENT)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Comment on the data in the container class. This property should be used instead of the OWL documentation elements (rdfs:comment) for instances because information in COMMENT is data to be exchanged, whereas the rdfs:comment field is used for metadata about the structure of the BioPAX ontology.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property COMMENTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#COMMENT");


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
	 * Get an Iterator the 'DATA_DASH_SOURCE' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.dataSource}
	 * @see			#DATA_DASH_SOURCEProperty
	 */
	public java.util.Iterator getDATA_DASH_SOURCE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'DATA_DASH_SOURCE' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.dataSource} to add
	 * @see			#DATA_DASH_SOURCEProperty
	 */
	public void addDATA_DASH_SOURCE(fr.curie.BiNoM.pathways.biopax.dataSource DATA_DASH_SOURCE) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'DATA_DASH_SOURCE' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.dataSource} created
	 * @see			#DATA_DASH_SOURCEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.dataSource addDATA_DASH_SOURCE() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'DATA_DASH_SOURCE' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.dataSource} with the factory
	 * and calling addDATA_DASH_SOURCE(fr.curie.BiNoM.pathways.biopax.dataSource DATA_DASH_SOURCE)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#dataSource.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#DATA_DASH_SOURCEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.dataSource addDATA_DASH_SOURCE(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'DATA_DASH_SOURCE' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.dataSource} to remove
	 * @see			#DATA_DASH_SOURCEProperty
	 */
	public void removeDATA_DASH_SOURCE(fr.curie.BiNoM.pathways.biopax.dataSource DATA_DASH_SOURCE) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Iterates through the 'AVAILABILITY' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#AVAILABILITYProperty
	 */
	public java.util.Iterator getAVAILABILITY() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'AVAILABILITY' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#AVAILABILITYProperty
	 */
	public void addAVAILABILITY(java.lang.String AVAILABILITY) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'AVAILABILITY' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#AVAILABILITYProperty
	 */
	public void removeAVAILABILITY(java.lang.String AVAILABILITY) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'COMMENT' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#COMMENTProperty
	 */
	public java.util.Iterator getCOMMENT() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'COMMENT' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#COMMENTProperty
	 */
	public void addCOMMENT(java.lang.String COMMENT) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'COMMENT' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#COMMENTProperty
	 */
	public void removeCOMMENT(java.lang.String COMMENT) throws com.ibm.adtech.jastor.JastorException;

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