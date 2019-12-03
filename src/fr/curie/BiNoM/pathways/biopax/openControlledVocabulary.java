

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for openControlledVocabulary ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#openControlledVocabulary)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: Used to import terms from external controlled vocabularies (CVs) into the ontology. To support consistency and compatibility, open, freely available CVs should be used whenever possible, such as the Gene Ontology (GO) or other open biological CVs listed on the OBO website (http://obo.sourceforge.net/).
Comment: The ID property in unification xrefs to GO and other OBO ontologies should include the ontology name in the ID property (e.g. ID="GO:0005634" instead of ID="0005634").^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface openControlledVocabulary extends fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#openControlledVocabulary");
	

	/**
	 * The Jena Property for TERM 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#TERM)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The external controlled vocabulary term.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property TERMProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#TERM");


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
	 * Iterates through the 'TERM' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#TERMProperty
	 */
	public java.util.Iterator getTERM() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'TERM' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#TERMProperty
	 */
	public void addTERM(java.lang.String TERM) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'TERM' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#TERMProperty
	 */
	public void removeTERM(java.lang.String TERM) throws com.ibm.adtech.jastor.JastorException;

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
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.unificationXref}
	 * @see			#XREFProperty
	 */
	public java.util.Iterator getXREF_asunificationXref() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'XREF' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.unificationXref} to add
	 * @see			#XREFProperty
	 */
	public void addXREF(fr.curie.BiNoM.pathways.biopax.unificationXref XREF) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'XREF' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.unificationXref} created
	 * @see			#XREFProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.unificationXref addXREF_asunificationXref() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'XREF' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.unificationXref} with the factory
	 * and calling addXREF(fr.curie.BiNoM.pathways.biopax.unificationXref XREF)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#unificationXref.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#XREFProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.unificationXref addXREF_asunificationXref(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'XREF' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.unificationXref} to remove
	 * @see			#XREFProperty
	 */
	public void removeXREF(fr.curie.BiNoM.pathways.biopax.unificationXref XREF) throws com.ibm.adtech.jastor.JastorException;
		
}