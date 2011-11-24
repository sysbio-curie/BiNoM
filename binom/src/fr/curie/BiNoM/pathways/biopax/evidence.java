

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for evidence ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#evidence)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: The support for a particular assertion, such as the existence of an interaction or pathway. At least one of CONFIDENCE, EVIDENCE-CODE, or EXPERIMENTAL-FORM must be instantiated when creating an evidence instance. XREF may reference a publication describing the experimental evidence using a publicationXref or may store a description of the experiment in an experimental description database using a unificationXref (if the referenced experiment is the same) or relationshipXref (if it is not identical, but similar in some way e.g. similar in protocol). Evidence is meant to provide more information than just an xref to the source paper.
Examples: A description of a molecular binding assay that was used to detect a protein-protein interaction.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface evidence extends fr.curie.BiNoM.pathways.biopax.utilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#evidence");
	

	/**
	 * The Jena Property for EXPERIMENTAL_DASH_FORM 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#EXPERIMENTAL-FORM)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The experimental forms associated with an evidence instance.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property EXPERIMENTAL_DASH_FORMProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#EXPERIMENTAL-FORM");


	/**
	 * The Jena Property for EVIDENCE_DASH_CODE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#EVIDENCE-CODE)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : A pointer to a term in an external controlled vocabulary, such as the GO, PSI-MI or BioCyc evidence codes, that describes the nature of the support, such as 'traceable author statement' or 'yeast two-hybrid'.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property EVIDENCE_DASH_CODEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#EVIDENCE-CODE");


	/**
	 * The Jena Property for CONFIDENCE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#CONFIDENCE)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Confidence in the containing instance.  Usually a statistical measure.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property CONFIDENCEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#CONFIDENCE");


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
	 * Get an Iterator the 'EXPERIMENTAL_DASH_FORM' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.experimentalForm}
	 * @see			#EXPERIMENTAL_DASH_FORMProperty
	 */
	public java.util.Iterator getEXPERIMENTAL_DASH_FORM() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'EXPERIMENTAL_DASH_FORM' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.experimentalForm} to add
	 * @see			#EXPERIMENTAL_DASH_FORMProperty
	 */
	public void addEXPERIMENTAL_DASH_FORM(fr.curie.BiNoM.pathways.biopax.experimentalForm EXPERIMENTAL_DASH_FORM) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'EXPERIMENTAL_DASH_FORM' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.experimentalForm} created
	 * @see			#EXPERIMENTAL_DASH_FORMProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.experimentalForm addEXPERIMENTAL_DASH_FORM() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'EXPERIMENTAL_DASH_FORM' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.experimentalForm} with the factory
	 * and calling addEXPERIMENTAL_DASH_FORM(fr.curie.BiNoM.pathways.biopax.experimentalForm EXPERIMENTAL_DASH_FORM)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#experimentalForm.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#EXPERIMENTAL_DASH_FORMProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.experimentalForm addEXPERIMENTAL_DASH_FORM(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'EXPERIMENTAL_DASH_FORM' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.experimentalForm} to remove
	 * @see			#EXPERIMENTAL_DASH_FORMProperty
	 */
	public void removeEXPERIMENTAL_DASH_FORM(fr.curie.BiNoM.pathways.biopax.experimentalForm EXPERIMENTAL_DASH_FORM) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'EVIDENCE_DASH_CODE' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary}
	 * @see			#EVIDENCE_DASH_CODEProperty
	 */
	public java.util.Iterator getEVIDENCE_DASH_CODE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'EVIDENCE_DASH_CODE' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary} to add
	 * @see			#EVIDENCE_DASH_CODEProperty
	 */
	public void addEVIDENCE_DASH_CODE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary EVIDENCE_DASH_CODE) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'EVIDENCE_DASH_CODE' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary} created
	 * @see			#EVIDENCE_DASH_CODEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary addEVIDENCE_DASH_CODE() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'EVIDENCE_DASH_CODE' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary} with the factory
	 * and calling addEVIDENCE_DASH_CODE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary EVIDENCE_DASH_CODE)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#openControlledVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#EVIDENCE_DASH_CODEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary addEVIDENCE_DASH_CODE(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'EVIDENCE_DASH_CODE' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.openControlledVocabulary} to remove
	 * @see			#EVIDENCE_DASH_CODEProperty
	 */
	public void removeEVIDENCE_DASH_CODE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary EVIDENCE_DASH_CODE) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'CONFIDENCE' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.confidence}
	 * @see			#CONFIDENCEProperty
	 */
	public java.util.Iterator getCONFIDENCE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'CONFIDENCE' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.confidence} to add
	 * @see			#CONFIDENCEProperty
	 */
	public void addCONFIDENCE(fr.curie.BiNoM.pathways.biopax.confidence CONFIDENCE) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'CONFIDENCE' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.confidence} created
	 * @see			#CONFIDENCEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.confidence addCONFIDENCE() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'CONFIDENCE' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.confidence} with the factory
	 * and calling addCONFIDENCE(fr.curie.BiNoM.pathways.biopax.confidence CONFIDENCE)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#confidence.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#CONFIDENCEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.confidence addCONFIDENCE(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'CONFIDENCE' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.confidence} to remove
	 * @see			#CONFIDENCEProperty
	 */
	public void removeCONFIDENCE(fr.curie.BiNoM.pathways.biopax.confidence CONFIDENCE) throws com.ibm.adtech.jastor.JastorException;
		
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
		
}