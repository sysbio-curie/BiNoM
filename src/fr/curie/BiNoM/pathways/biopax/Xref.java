

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Xref ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Xref)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A reference from an instance of a class in this ontology to an object in an external resource.
Rationale: Xrefs in the future can be removed in the future in favor of explicit miram links. 
Usage: For most cases one of the subclasses of xref should be used.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface Xref extends fr.curie.BiNoM.pathways.biopax.UtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#Xref");
	

	/**
	 * The Jena Property for id 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#id)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The primary identifier in the external database of the object to which this xref refers.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property idProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#id");


	/**
	 * The Jena Property for db 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#db)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The name of the external database to which this xref refers.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property dbProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#db");


	/**
	 * The Jena Property for idVersion 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#idVersion)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The version number of the identifier (ID). E.g. The RefSeq accession number NM_005228.3 should be split into NM_005228 as the ID and 3 as the ID-VERSION.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property idVersionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#idVersion");


	/**
	 * The Jena Property for dbVersion 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#dbVersion)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The version of the external database in which this xref was last known to be valid. Resources may have recommendations for referencing dataset versions. For instance, the Gene Ontology recommends listing the date the GO terms were downloaded.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property dbVersionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#dbVersion");






	/**
	 * Gets the 'id' property value
	 * @return		{@link java.lang.String}
	 * @see			#idProperty
	 */
	public java.lang.String getId() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'id' property value
	 * @param		{@link java.lang.String}
	 * @see			#idProperty
	 */
	public void setId(java.lang.String id) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'db' property value
	 * @return		{@link java.lang.String}
	 * @see			#dbProperty
	 */
	public java.lang.String getDb() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'db' property value
	 * @param		{@link java.lang.String}
	 * @see			#dbProperty
	 */
	public void setDb(java.lang.String db) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'idVersion' property value
	 * @return		{@link java.lang.String}
	 * @see			#idVersionProperty
	 */
	public java.lang.String getIdVersion() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'idVersion' property value
	 * @param		{@link java.lang.String}
	 * @see			#idVersionProperty
	 */
	public void setIdVersion(java.lang.String idVersion) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'dbVersion' property value
	 * @return		{@link java.lang.String}
	 * @see			#dbVersionProperty
	 */
	public java.lang.String getDbVersion() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'dbVersion' property value
	 * @param		{@link java.lang.String}
	 * @see			#dbVersionProperty
	 */
	public void setDbVersion(java.lang.String dbVersion) throws com.ibm.adtech.jastor.JastorException;

}