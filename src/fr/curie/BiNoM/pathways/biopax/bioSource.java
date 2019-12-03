

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for bioSource ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#bioSource)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: The biological source of an entity (e.g. protein, RNA or DNA). Some entities are considered source-neutral (e.g. small molecules), and the biological source of others can be deduced from their constituentss (e.g. complex, pathway).
Examples: HeLa cells, human, and mouse liver tissue.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface bioSource extends fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#bioSource");
	

	/**
	 * The Jena Property for CELLTYPE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#CELLTYPE)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : A cell type, e.g. 'HeLa'. This should reference a term in a controlled vocabulary of cell types.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property CELLTYPEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#CELLTYPE");


	/**
	 * The Jena Property for TISSUE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#TISSUE)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : An external controlled vocabulary of tissue types.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property TISSUEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#TISSUE");


	/**
	 * The Jena Property for TAXON_DASH_XREF 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#TAXON-XREF)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : An xref to an organism taxonomy database, preferably NCBI taxon. This should be an instance of unificationXref, unless the organism is not in an existing database.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property TAXON_DASH_XREFProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#TAXON-XREF");


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
	 * Gets the 'CELLTYPE' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}
	 * @see			#CELLTYPEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary getCELLTYPE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'CELLTYPE' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}
	 * @see			#CELLTYPEProperty
	 */
	public void setCELLTYPE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary CELLTYPE) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'CELLTYPE' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}, the created value
	 * @see			#CELLTYPEProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary setCELLTYPE() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'CELLTYPE' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary} with the factory.
	 * and calling setCELLTYPE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary CELLTYPE)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#openControlledVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}, the newly created value
	 * @see			#CELLTYPEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary setCELLTYPE(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'TISSUE' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}
	 * @see			#TISSUEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary getTISSUE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'TISSUE' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}
	 * @see			#TISSUEProperty
	 */
	public void setTISSUE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary TISSUE) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'TISSUE' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}, the created value
	 * @see			#TISSUEProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary setTISSUE() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'TISSUE' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary} with the factory.
	 * and calling setTISSUE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary TISSUE)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#openControlledVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}, the newly created value
	 * @see			#TISSUEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary setTISSUE(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'TAXON_DASH_XREF' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.unificationXref}
	 * @see			#TAXON_DASH_XREFProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.unificationXref getTAXON_DASH_XREF() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'TAXON_DASH_XREF' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.unificationXref}
	 * @see			#TAXON_DASH_XREFProperty
	 */
	public void setTAXON_DASH_XREF(fr.curie.BiNoM.pathways.biopax.unificationXref TAXON_DASH_XREF) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'TAXON_DASH_XREF' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.unificationXref}, the created value
	 * @see			#TAXON_DASH_XREFProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.unificationXref setTAXON_DASH_XREF() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'TAXON_DASH_XREF' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.unificationXref} with the factory.
	 * and calling setTAXON_DASH_XREF(fr.curie.BiNoM.pathways.biopax.unificationXref TAXON_DASH_XREF)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#unificationXref.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.unificationXref}, the newly created value
	 * @see			#TAXON_DASH_XREFProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.unificationXref setTAXON_DASH_XREF(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
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

}