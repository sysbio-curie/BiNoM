

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for protein ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#protein)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A physical entity consisting of a sequence of amino acids; a protein monomer; a single polypeptide chain.
Examples: The epidermal growth factor receptor (EGFR) protein.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface protein extends fr.curie.BiNoM.pathways.biopax.physicalEntity, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#protein");
	

	/**
	 * The Jena Property for SEQUENCE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#SEQUENCE)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Polymer sequence in uppercase letters. For DNA, usually A,C,G,T letters representing the nucleosides of adenine, cytosine, guanine and thymine, respectively; for RNA, usually A, C, U, G; for protein, usually the letters corresponding to the 20 letter IUPAC amino acid code.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property SEQUENCEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SEQUENCE");


	/**
	 * The Jena Property for ORGANISM 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#ORGANISM)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : An organism, e.g. 'Homo sapiens'. This is the organism that the entity is found in. Pathways may not have an organism associated with them, for instance, reference pathways from KEGG. Sequence-based entities (DNA, protein, RNA) may contain an xref to a sequence database that contains organism information, in which case the information should be consistent with the value for ORGANISM.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property ORGANISMProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#ORGANISM");






	/**
	 * Gets the 'SEQUENCE' property value
	 * @return		{@link java.lang.String}
	 * @see			#SEQUENCEProperty
	 */
	public java.lang.String getSEQUENCE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'SEQUENCE' property value
	 * @param		{@link java.lang.String}
	 * @see			#SEQUENCEProperty
	 */
	public void setSEQUENCE(java.lang.String SEQUENCE) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'ORGANISM' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.bioSource}
	 * @see			#ORGANISMProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.bioSource getORGANISM() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'ORGANISM' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.bioSource}
	 * @see			#ORGANISMProperty
	 */
	public void setORGANISM(fr.curie.BiNoM.pathways.biopax.bioSource ORGANISM) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'ORGANISM' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.bioSource}, the created value
	 * @see			#ORGANISMProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.bioSource setORGANISM() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'ORGANISM' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.bioSource} with the factory.
	 * and calling setORGANISM(fr.curie.BiNoM.pathways.biopax.bioSource ORGANISM)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#bioSource.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.bioSource}, the newly created value
	 * @see			#ORGANISMProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.bioSource setORGANISM(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}