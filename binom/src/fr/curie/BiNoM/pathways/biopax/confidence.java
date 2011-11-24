

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for confidence ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#confidence)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: Confidence that the containing instance actually occurs or exists in vivo, usually a statistical measure. The xref must contain at least on publication that describes the method used to determine the confidence. There is currently no standard way of describing confidence values, so any string is valid for the confidence value. In the future, a controlled vocabulary of accepted confidence values could become available, in which case it will likely be adopted for use here to describe the value.
Examples: The statistical significance of a result, e.g. "p<0.05".^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface confidence extends fr.curie.BiNoM.pathways.biopax.utilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#confidence");
	

	/**
	 * The Jena Property for CONFIDENCE_DASH_VALUE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#CONFIDENCE-VALUE)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The value of the confidence measure.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property CONFIDENCE_DASH_VALUEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#CONFIDENCE-VALUE");


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
	 * Gets the 'CONFIDENCE_DASH_VALUE' property value
	 * @return		{@link java.lang.String}
	 * @see			#CONFIDENCE_DASH_VALUEProperty
	 */
	public java.lang.String getCONFIDENCE_DASH_VALUE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'CONFIDENCE_DASH_VALUE' property value
	 * @param		{@link java.lang.String}
	 * @see			#CONFIDENCE_DASH_VALUEProperty
	 */
	public void setCONFIDENCE_DASH_VALUE(java.lang.String CONFIDENCE_DASH_VALUE) throws com.ibm.adtech.jastor.JastorException;

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
		
	/**
	 * Get an Iterator the 'XREF' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.publicationXref}
	 * @see			#XREFProperty
	 */
	public java.util.Iterator getXREF_aspublicationXref() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'XREF' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.publicationXref} to add
	 * @see			#XREFProperty
	 */
	public void addXREF(fr.curie.BiNoM.pathways.biopax.publicationXref XREF) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'XREF' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.publicationXref} created
	 * @see			#XREFProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.publicationXref addXREF_aspublicationXref() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'XREF' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.publicationXref} with the factory
	 * and calling addXREF(fr.curie.BiNoM.pathways.biopax.publicationXref XREF)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#publicationXref.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#XREFProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.publicationXref addXREF_aspublicationXref(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'XREF' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.publicationXref} to remove
	 * @see			#XREFProperty
	 */
	public void removeXREF(fr.curie.BiNoM.pathways.biopax.publicationXref XREF) throws com.ibm.adtech.jastor.JastorException;
		
}