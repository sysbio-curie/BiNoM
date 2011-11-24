

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for PublicationXref ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#PublicationXref)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: An xref that defines a reference to a publication such as a book, journal article, web page, or software manual.
Usage:  The reference may or may not be in a database, although references to PubMed are preferred when possible. The publication should make a direct reference to the instance it is attached to. Publication xrefs should make use of PubMed IDs wherever possible. The DB property of an xref to an entry in PubMed should use the string "PubMed" and not "MEDLINE".
Examples: PubMed:10234245^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface PublicationXref extends fr.curie.BiNoM.pathways.biopax.Xref, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#PublicationXref");
	

	/**
	 * The Jena Property for title 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#title)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The title of the publication.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property titleProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#title");


	/**
	 * The Jena Property for year 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#year)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The year in which this publication was published.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property yearProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#year");


	/**
	 * The Jena Property for url 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#url)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The URL at which the publication can be found, if it is available through the Web.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property urlProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#url");


	/**
	 * The Jena Property for author 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#author)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The authors of this publication, one per property value.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property authorProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#author");


	/**
	 * The Jena Property for source 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#source)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The source  in which the reference was published, such as: a book title, or a journal title and volume and pages.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property sourceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#source");






	/**
	 * Gets the 'title' property value
	 * @return		{@link java.lang.String}
	 * @see			#titleProperty
	 */
	public java.lang.String getTitle() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'title' property value
	 * @param		{@link java.lang.String}
	 * @see			#titleProperty
	 */
	public void setTitle(java.lang.String title) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'year' property value
	 * @return		{@link java.lang.Integer}
	 * @see			#yearProperty
	 */
	public java.lang.Integer getYear() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'year' property value
	 * @param		{@link java.lang.Integer}
	 * @see			#yearProperty
	 */
	public void setYear(java.lang.Integer year) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'url' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#urlProperty
	 */
	public java.util.Iterator getUrl() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'url' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#urlProperty
	 */
	public void addUrl(java.lang.String url) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'url' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#urlProperty
	 */
	public void removeUrl(java.lang.String url) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'author' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#authorProperty
	 */
	public java.util.Iterator getAuthor() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'author' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#authorProperty
	 */
	public void addAuthor(java.lang.String author) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'author' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#authorProperty
	 */
	public void removeAuthor(java.lang.String author) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'source' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#sourceProperty
	 */
	public java.util.Iterator getSource() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'source' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#sourceProperty
	 */
	public void addSource(java.lang.String source) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'source' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#sourceProperty
	 */
	public void removeSource(java.lang.String source) throws com.ibm.adtech.jastor.JastorException;

}