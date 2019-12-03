

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for RnaRegionReference ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#RnaRegionReference)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A RNARegion reference is a grouping of several RNARegion entities that are common in sequence and genomic position.  Members can differ in celular location, sequence features, mutations and bound partners.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface RnaRegionReference extends fr.curie.BiNoM.pathways.biopax.EntityReference, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#RnaRegionReference");
	

	/**
	 * The Jena Property for sequence 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#sequence)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Polymer sequence in uppercase letters. For DNA, usually A,C,G,T letters representing the nucleosides of adenine, cytosine, guanine and thymine, respectively; for RNA, usually A, C, U, G; for protein, usually the letters corresponding to the 20 letter IUPAC amino acid code.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property sequenceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#sequence");


	/**
	 * The Jena Property for subRegion 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#subRegion)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The sub region of a region or nucleic acid molecule. The sub region must be wholly part of the region, not outside of it.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property subRegionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#subRegion");


	/**
	 * The Jena Property for regionType 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#regionType)</p>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property regionTypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#regionType");


	/**
	 * The Jena Property for organism 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#organism)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : An organism, e.g. 'Homo sapiens'. This is the organism that the entity is found in. Pathways may not have an organism associated with them, for instance, reference pathways from KEGG. Sequence-based entities (DNA, protein, RNA) may contain an xref to a sequence database that contains organism information, in which case the information should be consistent with the value for ORGANISM.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property organismProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#organism");


	/**
	 * The Jena Property for absoluteRegion 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#absoluteRegion)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Absolute location as defined by the referenced sequence database record. E.g. an operon has a absolute region on the DNA molecule referenced by the UnificationXref.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property absoluteRegionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#absoluteRegion");






	/**
	 * Gets the 'sequence' property value
	 * @return		{@link java.lang.String}
	 * @see			#sequenceProperty
	 */
	public java.lang.String getSequence() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'sequence' property value
	 * @param		{@link java.lang.String}
	 * @see			#sequenceProperty
	 */
	public void setSequence(java.lang.String sequence) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'subRegion' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.RnaRegionReference}
	 * @see			#subRegionProperty
	 */
	public java.util.Iterator getSubRegion_asRnaRegionReference() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'subRegion' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.RnaRegionReference} to add
	 * @see			#subRegionProperty
	 */
	public void addSubRegion(fr.curie.BiNoM.pathways.biopax.RnaRegionReference subRegion) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'subRegion' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.RnaRegionReference} created
	 * @see			#subRegionProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.RnaRegionReference addSubRegion_asRnaRegionReference() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'subRegion' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.RnaRegionReference} with the factory
	 * and calling addSubRegion(fr.curie.BiNoM.pathways.biopax.RnaRegionReference subRegion)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#RnaRegionReference.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#subRegionProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.RnaRegionReference addSubRegion_asRnaRegionReference(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'subRegion' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.RnaRegionReference} to remove
	 * @see			#subRegionProperty
	 */
	public void removeSubRegion(fr.curie.BiNoM.pathways.biopax.RnaRegionReference subRegion) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'subRegion' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.DnaRegionReference}
	 * @see			#subRegionProperty
	 */
	public java.util.Iterator getSubRegion_asDnaRegionReference() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'subRegion' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.DnaRegionReference} to add
	 * @see			#subRegionProperty
	 */
	public void addSubRegion(fr.curie.BiNoM.pathways.biopax.DnaRegionReference subRegion) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'subRegion' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.DnaRegionReference} created
	 * @see			#subRegionProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.DnaRegionReference addSubRegion_asDnaRegionReference() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'subRegion' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.DnaRegionReference} with the factory
	 * and calling addSubRegion(fr.curie.BiNoM.pathways.biopax.DnaRegionReference subRegion)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#DnaRegionReference.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#subRegionProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.DnaRegionReference addSubRegion_asDnaRegionReference(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'subRegion' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.DnaRegionReference} to remove
	 * @see			#subRegionProperty
	 */
	public void removeSubRegion(fr.curie.BiNoM.pathways.biopax.DnaRegionReference subRegion) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Gets the 'regionType' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary}
	 * @see			#regionTypeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary getRegionType() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'regionType' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary}
	 * @see			#regionTypeProperty
	 */
	public void setRegionType(fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary regionType) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'regionType' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary}, the created value
	 * @see			#regionTypeProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary setRegionType() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'regionType' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary} with the factory.
	 * and calling setRegionType(fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary regionType)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#SequenceRegionVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary}, the newly created value
	 * @see			#regionTypeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary setRegionType(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'organism' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.BioSource}
	 * @see			#organismProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.BioSource getOrganism() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'organism' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.BioSource}
	 * @see			#organismProperty
	 */
	public void setOrganism(fr.curie.BiNoM.pathways.biopax.BioSource organism) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'organism' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.BioSource}, the created value
	 * @see			#organismProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.BioSource setOrganism() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'organism' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.BioSource} with the factory.
	 * and calling setOrganism(fr.curie.BiNoM.pathways.biopax.BioSource organism)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#BioSource.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.BioSource}, the newly created value
	 * @see			#organismProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.BioSource setOrganism(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'absoluteRegion' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SequenceLocation}
	 * @see			#absoluteRegionProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SequenceLocation getAbsoluteRegion() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'absoluteRegion' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.SequenceLocation}
	 * @see			#absoluteRegionProperty
	 */
	public void setAbsoluteRegion(fr.curie.BiNoM.pathways.biopax.SequenceLocation absoluteRegion) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'absoluteRegion' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SequenceLocation}, the created value
	 * @see			#absoluteRegionProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.SequenceLocation setAbsoluteRegion() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'absoluteRegion' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.SequenceLocation} with the factory.
	 * and calling setAbsoluteRegion(fr.curie.BiNoM.pathways.biopax.SequenceLocation absoluteRegion)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#SequenceLocation.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SequenceLocation}, the newly created value
	 * @see			#absoluteRegionProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SequenceLocation setAbsoluteRegion(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}