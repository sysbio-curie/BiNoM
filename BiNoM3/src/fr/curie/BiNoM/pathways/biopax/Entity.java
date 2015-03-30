

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Entity ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Entity)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A discrete biological unit used when describing pathways. 

Rationale: Entity is the most abstract class for representing interacting 
    elements in a pathway. It includes both occurents (interactions and 
    pathways) and continuants (physical entities and genes). Loosely speaking, 
    BioPAX Entity is an atomic scientific statement with an associated source, 
    evidence and references. 
Synonyms: element, thing, object, bioentity, statement.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface Entity extends com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#Entity");
	

	/**
	 * The Jena Property for dataSource 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#dataSource)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : A free text description of the source of this data, e.g. a database or person name. This property should be used to describe the source of the data. This is meant to be used by databases that export their data to the BioPAX format or by systems that are integrating data from multiple sources. The granularity of use (specifying the data source in many or few instances) is up to the user. It is intended that this property report the last data source, not all data sources that the data has passed through from creation.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property dataSourceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#dataSource");


	/**
	 * The Jena Property for availability 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#availability)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Describes the availability of this data (e.g. a copyright statement).@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property availabilityProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#availability");


	/**
	 * The Jena Property for comment 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#comment)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Comment on the data in the container class. This property should be used instead of the OWL documentation elements (rdfs:comment) for instances because information in 'comment' is data to be exchanged, whereas the rdfs:comment field is used for metadata about the structure of the BioPAX ontology.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property commentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#comment");


	/**
	 * The Jena Property for name 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#name)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : One or more synonyms for the name of this individual. This should include the values of the standardName and displayName property so that it is easy to find all known names in one place.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property nameProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#name");


	/**
	 * The Jena Property for evidence 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#evidence)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Scientific evidence supporting the existence of the entity as described.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property evidenceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#evidence");


	/**
	 * The Jena Property for xref 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#xref)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Values of this property define external cross-references from this entity to entities in external databases.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property xrefProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#xref");






	/**
	 * Get an Iterator the 'dataSource' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Provenance}
	 * @see			#dataSourceProperty
	 */
	public java.util.Iterator getDataSource() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'dataSource' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Provenance} to add
	 * @see			#dataSourceProperty
	 */
	public void addDataSource(fr.curie.BiNoM.pathways.biopax.Provenance dataSource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'dataSource' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Provenance} created
	 * @see			#dataSourceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Provenance addDataSource() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'dataSource' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Provenance} with the factory
	 * and calling addDataSource(fr.curie.BiNoM.pathways.biopax.Provenance dataSource)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Provenance.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#dataSourceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Provenance addDataSource(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'dataSource' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Provenance} to remove
	 * @see			#dataSourceProperty
	 */
	public void removeDataSource(fr.curie.BiNoM.pathways.biopax.Provenance dataSource) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Iterates through the 'availability' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#availabilityProperty
	 */
	public java.util.Iterator getAvailability() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'availability' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#availabilityProperty
	 */
	public void addAvailability(java.lang.String availability) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'availability' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#availabilityProperty
	 */
	public void removeAvailability(java.lang.String availability) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'comment' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#commentProperty
	 */
	public java.util.Iterator getComment() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'comment' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#commentProperty
	 */
	public void addComment(java.lang.String comment) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'comment' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#commentProperty
	 */
	public void removeComment(java.lang.String comment) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'name' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#nameProperty
	 */
	public java.util.Iterator getName() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'name' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#nameProperty
	 */
	public void addName(java.lang.String name) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'name' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#nameProperty
	 */
	public void removeName(java.lang.String name) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'evidence' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Evidence}
	 * @see			#evidenceProperty
	 */
	public java.util.Iterator getEvidence() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'evidence' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Evidence} to add
	 * @see			#evidenceProperty
	 */
	public void addEvidence(fr.curie.BiNoM.pathways.biopax.Evidence evidence) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'evidence' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Evidence} created
	 * @see			#evidenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Evidence addEvidence() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'evidence' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Evidence} with the factory
	 * and calling addEvidence(fr.curie.BiNoM.pathways.biopax.Evidence evidence)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Evidence.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#evidenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Evidence addEvidence(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'evidence' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Evidence} to remove
	 * @see			#evidenceProperty
	 */
	public void removeEvidence(fr.curie.BiNoM.pathways.biopax.Evidence evidence) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'xref' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Xref}
	 * @see			#xrefProperty
	 */
	public java.util.Iterator getXref() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'xref' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Xref} to add
	 * @see			#xrefProperty
	 */
	public void addXref(fr.curie.BiNoM.pathways.biopax.Xref xref) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'xref' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Xref} created
	 * @see			#xrefProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Xref addXref() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'xref' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Xref} with the factory
	 * and calling addXref(fr.curie.BiNoM.pathways.biopax.Xref xref)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Xref.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#xrefProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Xref addXref(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'xref' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Xref} to remove
	 * @see			#xrefProperty
	 */
	public void removeXref(fr.curie.BiNoM.pathways.biopax.Xref xref) throws com.ibm.adtech.jastor.JastorException;
		
}