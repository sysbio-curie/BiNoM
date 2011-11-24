

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for ControlledVocabulary ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#ControlledVocabulary)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Used to reference terms from external controlled vocabularies (CVs) from the ontology. To support consistency and compatibility, open, freely available CVs should be used whenever possible, such as the Gene Ontology (GO)15 or other open biological CVs listed on the OBO website (http://obo.sourceforge.net/). See the section on controlled vocabularies in Section 4 for more information.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface ControlledVocabulary extends fr.curie.BiNoM.pathways.biopax.UtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#ControlledVocabulary");
	

	/**
	 * The Jena Property for term 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#term)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The external controlled vocabulary term.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property termProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#term");


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
	 * Iterates through the 'term' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#termProperty
	 */
	public java.util.Iterator getTerm() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'term' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#termProperty
	 */
	public void addTerm(java.lang.String term) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'term' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#termProperty
	 */
	public void removeTerm(java.lang.String term) throws com.ibm.adtech.jastor.JastorException;

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
		
	/**
	 * Get an Iterator the 'xref' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.UnificationXref}
	 * @see			#xrefProperty
	 */
	public java.util.Iterator getXref_asUnificationXref() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'xref' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.UnificationXref} to add
	 * @see			#xrefProperty
	 */
	public void addXref(fr.curie.BiNoM.pathways.biopax.UnificationXref xref) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'xref' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.UnificationXref} created
	 * @see			#xrefProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.UnificationXref addXref_asUnificationXref() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'xref' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.UnificationXref} with the factory
	 * and calling addXref(fr.curie.BiNoM.pathways.biopax.UnificationXref xref)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#UnificationXref.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#xrefProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.UnificationXref addXref_asUnificationXref(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'xref' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.UnificationXref} to remove
	 * @see			#xrefProperty
	 */
	public void removeXref(fr.curie.BiNoM.pathways.biopax.UnificationXref xref) throws com.ibm.adtech.jastor.JastorException;
		
}