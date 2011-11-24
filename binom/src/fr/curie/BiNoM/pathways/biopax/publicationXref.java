

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for publicationXref ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#publicationXref)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: An xref that defines a reference to a publication such as a book, journal article, web page, or software manual. The reference may or may not be in a database, although references to PubMed are preferred when possible. The publication should make a direct reference to the instance it is attached to.
Comment: Publication xrefs should make use of PubMed IDs wherever possible. The DB property of an xref to an entry in PubMed should use the string "PubMed" and not "MEDLINE".
Examples: PubMed:10234245^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface publicationXref extends fr.curie.BiNoM.pathways.biopax.xref, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#publicationXref");
	

	/**
	 * The Jena Property for TITLE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#TITLE)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The title of the publication.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property TITLEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#TITLE");


	/**
	 * The Jena Property for YEAR 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#YEAR)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The year in which this publication was published.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property YEARProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#YEAR");


	/**
	 * The Jena Property for URL 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#URL)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The URL at which the publication can be found, if it is available through the Web.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property URLProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#URL");


	/**
	 * The Jena Property for SOURCE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#SOURCE)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The source  in which the reference was published, such as: a book title, or a journal title and volume and pages.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property SOURCEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SOURCE");


	/**
	 * The Jena Property for AUTHORS 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#AUTHORS)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The authors of this publication, one per property value.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property AUTHORSProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#AUTHORS");






	/**
	 * Gets the 'TITLE' property value
	 * @return		{@link java.lang.String}
	 * @see			#TITLEProperty
	 */
	public java.lang.String getTITLE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'TITLE' property value
	 * @param		{@link java.lang.String}
	 * @see			#TITLEProperty
	 */
	public void setTITLE(java.lang.String TITLE) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'YEAR' property value
	 * @return		{@link java.lang.Integer}
	 * @see			#YEARProperty
	 */
	public java.lang.Integer getYEAR() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'YEAR' property value
	 * @param		{@link java.lang.Integer}
	 * @see			#YEARProperty
	 */
	public void setYEAR(java.lang.Integer YEAR) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'URL' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#URLProperty
	 */
	public java.util.Iterator getURL() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'URL' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#URLProperty
	 */
	public void addURL(java.lang.String URL) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'URL' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#URLProperty
	 */
	public void removeURL(java.lang.String URL) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'SOURCE' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#SOURCEProperty
	 */
	public java.util.Iterator getSOURCE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'SOURCE' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#SOURCEProperty
	 */
	public void addSOURCE(java.lang.String SOURCE) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'SOURCE' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#SOURCEProperty
	 */
	public void removeSOURCE(java.lang.String SOURCE) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'AUTHORS' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#AUTHORSProperty
	 */
	public java.util.Iterator getAUTHORS() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'AUTHORS' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#AUTHORSProperty
	 */
	public void addAUTHORS(java.lang.String AUTHORS) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'AUTHORS' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#AUTHORSProperty
	 */
	public void removeAUTHORS(java.lang.String AUTHORS) throws com.ibm.adtech.jastor.JastorException;

}