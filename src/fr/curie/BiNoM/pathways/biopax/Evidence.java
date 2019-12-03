

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Evidence ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Evidence)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: The support for a particular assertion, such as the existence of an interaction or pathway. 
Usage: At least one of confidence, evidenceCode, or experimentalForm must be instantiated when creating an evidence instance. XREF may reference a publication describing the experimental evidence using a publicationXref or may store a description of the experiment in an experimental description database using a unificationXref (if the referenced experiment is the same) or relationshipXref (if it is not identical, but similar in some way e.g. similar in protocol). Evidence is meant to provide more information than just an xref to the source paper.
Examples: A description of a molecular binding assay that was used to detect a protein-protein interaction.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface Evidence extends fr.curie.BiNoM.pathways.biopax.UtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#Evidence");
	

	/**
	 * The Jena Property for confidence 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#confidence)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Confidence in the containing instance.  Usually a statistical measure.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property confidenceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#confidence");


	/**
	 * The Jena Property for evidenceCode 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#evidenceCode)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : A pointer to a term in an external controlled vocabulary, such as the GO, PSI-MI or BioCyc evidence codes, that describes the nature of the support, such as 'traceable author statement' or 'yeast two-hybrid'.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property evidenceCodeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#evidenceCode");


	/**
	 * The Jena Property for experimentalForm 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#experimentalForm)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The experimental forms associated with an evidence instance.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property experimentalFormProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#experimentalForm");


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
	 * Get an Iterator the 'confidence' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Score}
	 * @see			#confidenceProperty
	 */
	public java.util.Iterator getConfidence() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'confidence' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Score} to add
	 * @see			#confidenceProperty
	 */
	public void addConfidence(fr.curie.BiNoM.pathways.biopax.Score confidence) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'confidence' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Score} created
	 * @see			#confidenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Score addConfidence() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'confidence' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Score} with the factory
	 * and calling addConfidence(fr.curie.BiNoM.pathways.biopax.Score confidence)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Score.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#confidenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Score addConfidence(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'confidence' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Score} to remove
	 * @see			#confidenceProperty
	 */
	public void removeConfidence(fr.curie.BiNoM.pathways.biopax.Score confidence) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'evidenceCode' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary}
	 * @see			#evidenceCodeProperty
	 */
	public java.util.Iterator getEvidenceCode() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'evidenceCode' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary} to add
	 * @see			#evidenceCodeProperty
	 */
	public void addEvidenceCode(fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary evidenceCode) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'evidenceCode' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary} created
	 * @see			#evidenceCodeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary addEvidenceCode() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'evidenceCode' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary} with the factory
	 * and calling addEvidenceCode(fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary evidenceCode)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#EvidenceCodeVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#evidenceCodeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary addEvidenceCode(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'evidenceCode' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary} to remove
	 * @see			#evidenceCodeProperty
	 */
	public void removeEvidenceCode(fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary evidenceCode) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'experimentalForm' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.ExperimentalForm}
	 * @see			#experimentalFormProperty
	 */
	public java.util.Iterator getExperimentalForm() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'experimentalForm' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.ExperimentalForm} to add
	 * @see			#experimentalFormProperty
	 */
	public void addExperimentalForm(fr.curie.BiNoM.pathways.biopax.ExperimentalForm experimentalForm) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'experimentalForm' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.ExperimentalForm} created
	 * @see			#experimentalFormProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.ExperimentalForm addExperimentalForm() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'experimentalForm' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.ExperimentalForm} with the factory
	 * and calling addExperimentalForm(fr.curie.BiNoM.pathways.biopax.ExperimentalForm experimentalForm)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#ExperimentalForm.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#experimentalFormProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.ExperimentalForm addExperimentalForm(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'experimentalForm' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.ExperimentalForm} to remove
	 * @see			#experimentalFormProperty
	 */
	public void removeExperimentalForm(fr.curie.BiNoM.pathways.biopax.ExperimentalForm experimentalForm) throws com.ibm.adtech.jastor.JastorException;
		
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