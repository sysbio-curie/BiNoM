

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for BioSource ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#BioSource)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: The biological source (organism, tissue or cell type) of an Entity. 

Usage: Some entities are considered source-neutral (e.g. small molecules), and the biological source of others can be deduced from their constituentss (e.g. complex, pathway).

Instances: HeLa cells, Homo sapiens, and mouse liver tissue.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface BioSource extends fr.curie.BiNoM.pathways.biopax.UtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#BioSource");
	

	/**
	 * The Jena Property for tissue 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#tissue)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : An external controlled vocabulary of tissue types.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property tissueProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#tissue");


	/**
	 * The Jena Property for cellType 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#cellType)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : A cell type, e.g. 'HeLa'. This should reference a term in a controlled vocabulary of cell types. Best practice is to refer to OBO Cell Ontology. http://www.obofoundry.org/cgi-bin/detail.cgi?id=cell@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property cellTypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#cellType");


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
	 * Gets the 'tissue' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.TissueVocabulary}
	 * @see			#tissueProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.TissueVocabulary getTissue() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'tissue' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.TissueVocabulary}
	 * @see			#tissueProperty
	 */
	public void setTissue(fr.curie.BiNoM.pathways.biopax.TissueVocabulary tissue) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'tissue' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.TissueVocabulary}, the created value
	 * @see			#tissueProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.TissueVocabulary setTissue() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'tissue' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.TissueVocabulary} with the factory.
	 * and calling setTissue(fr.curie.BiNoM.pathways.biopax.TissueVocabulary tissue)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#TissueVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.TissueVocabulary}, the newly created value
	 * @see			#tissueProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.TissueVocabulary setTissue(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'cellType' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.CellVocabulary}
	 * @see			#cellTypeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.CellVocabulary getCellType() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'cellType' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.CellVocabulary}
	 * @see			#cellTypeProperty
	 */
	public void setCellType(fr.curie.BiNoM.pathways.biopax.CellVocabulary cellType) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'cellType' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.CellVocabulary}, the created value
	 * @see			#cellTypeProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.CellVocabulary setCellType() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'cellType' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.CellVocabulary} with the factory.
	 * and calling setCellType(fr.curie.BiNoM.pathways.biopax.CellVocabulary cellType)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#CellVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.CellVocabulary}, the newly created value
	 * @see			#cellTypeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.CellVocabulary setCellType(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
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