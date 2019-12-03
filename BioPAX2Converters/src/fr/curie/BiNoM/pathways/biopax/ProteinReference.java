

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for ProteinReference ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#ProteinReference)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : A protein reference is a grouping of several protein entities that are encoded by the same gene.  Members can differ in celular location, sequence features and bound partners. Currently conformational states (such as open and closed) are not covered.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface ProteinReference extends fr.curie.BiNoM.pathways.biopax.EntityReference, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#ProteinReference");
	

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
	 * Get an Iterator the 'memberEntityReference' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.ProteinReference}
	 * @see			#memberEntityReferenceProperty
	 */
	public java.util.Iterator getMemberEntityReference_asProteinReference() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'memberEntityReference' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.ProteinReference} to add
	 * @see			#memberEntityReferenceProperty
	 */
	public void addMemberEntityReference(fr.curie.BiNoM.pathways.biopax.ProteinReference memberEntityReference) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'memberEntityReference' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.ProteinReference} created
	 * @see			#memberEntityReferenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.ProteinReference addMemberEntityReference_asProteinReference() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'memberEntityReference' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.ProteinReference} with the factory
	 * and calling addMemberEntityReference(fr.curie.BiNoM.pathways.biopax.ProteinReference memberEntityReference)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#ProteinReference.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#memberEntityReferenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.ProteinReference addMemberEntityReference_asProteinReference(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'memberEntityReference' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.ProteinReference} to remove
	 * @see			#memberEntityReferenceProperty
	 */
	public void removeMemberEntityReference(fr.curie.BiNoM.pathways.biopax.ProteinReference memberEntityReference) throws com.ibm.adtech.jastor.JastorException;
		
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
	
}