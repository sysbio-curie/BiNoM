

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for xref ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#xref)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A reference from an instance of a class in this ontology to an object in an external resource.
Comment: Instances of the xref class should never be created and more specific classes should be used instead.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface xref extends fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#xref");
	

	/**
	 * The Jena Property for ID_DASH_VERSION 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#ID-VERSION)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The version number of the identifier (ID). E.g. The RefSeq accession number NM_005228.3 should be split into NM_005228 as the ID and 3 as the ID-VERSION.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property ID_DASH_VERSIONProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#ID-VERSION");


	/**
	 * The Jena Property for DB_DASH_VERSION 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#DB-VERSION)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The version of the external database in which this xref was last known to be valid. Resources may have recommendations for referencing dataset versions. For instance, the Gene Ontology recommends listing the date the GO terms were downloaded.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property DB_DASH_VERSIONProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#DB-VERSION");


	/**
	 * The Jena Property for ID 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#ID)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The primary identifier in the external database of the object to which this xref refers.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property IDProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#ID");


	/**
	 * The Jena Property for DB 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#DB)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The name of the external database to which this xref refers.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property DBProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#DB");






	/**
	 * Gets the 'ID_DASH_VERSION' property value
	 * @return		{@link java.lang.String}
	 * @see			#ID_DASH_VERSIONProperty
	 */
	public java.lang.String getID_DASH_VERSION() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'ID_DASH_VERSION' property value
	 * @param		{@link java.lang.String}
	 * @see			#ID_DASH_VERSIONProperty
	 */
	public void setID_DASH_VERSION(java.lang.String ID_DASH_VERSION) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'DB_DASH_VERSION' property value
	 * @return		{@link java.lang.String}
	 * @see			#DB_DASH_VERSIONProperty
	 */
	public java.lang.String getDB_DASH_VERSION() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'DB_DASH_VERSION' property value
	 * @param		{@link java.lang.String}
	 * @see			#DB_DASH_VERSIONProperty
	 */
	public void setDB_DASH_VERSION(java.lang.String DB_DASH_VERSION) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'ID' property value
	 * @return		{@link java.lang.String}
	 * @see			#IDProperty
	 */
	public java.lang.String getID() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'ID' property value
	 * @param		{@link java.lang.String}
	 * @see			#IDProperty
	 */
	public void setID(java.lang.String ID) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'DB' property value
	 * @return		{@link java.lang.String}
	 * @see			#DBProperty
	 */
	public java.lang.String getDB() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'DB' property value
	 * @param		{@link java.lang.String}
	 * @see			#DBProperty
	 */
	public void setDB(java.lang.String DB) throws com.ibm.adtech.jastor.JastorException;

}