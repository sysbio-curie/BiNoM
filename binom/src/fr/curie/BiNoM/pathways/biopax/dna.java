

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for dna ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#dna)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A physical entity consisting of a sequence of deoxyribonucleotide monophosphates; a deoxyribonucleic acid.
Comment: This is not a 'gene', since gene is a genetic concept, not a physical entity. The concept of a gene may be added later in BioPAX.
Examples: a chromosome, a plasmid. A specific example is chromosome 7 of Homo sapiens.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface dna extends fr.curie.BiNoM.pathways.biopax.physicalEntity, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#dna");
	

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
	 * Iterates through the 'SEQUENCE' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#SEQUENCEProperty
	 */
	public java.util.Iterator getSEQUENCE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'SEQUENCE' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#SEQUENCEProperty
	 */
	public void addSEQUENCE(java.lang.String SEQUENCE) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'SEQUENCE' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#SEQUENCEProperty
	 */
	public void removeSEQUENCE(java.lang.String SEQUENCE) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'ORGANISM' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.bioSource}
	 * @see			#ORGANISMProperty
	 */
	public java.util.Iterator getORGANISM() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'ORGANISM' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.bioSource} to add
	 * @see			#ORGANISMProperty
	 */
	public void addORGANISM(fr.curie.BiNoM.pathways.biopax.bioSource ORGANISM) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'ORGANISM' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.bioSource} created
	 * @see			#ORGANISMProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.bioSource addORGANISM() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'ORGANISM' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.bioSource} with the factory
	 * and calling addORGANISM(fr.curie.BiNoM.pathways.biopax.bioSource ORGANISM)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#bioSource.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#ORGANISMProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.bioSource addORGANISM(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'ORGANISM' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.bioSource} to remove
	 * @see			#ORGANISMProperty
	 */
	public void removeORGANISM(fr.curie.BiNoM.pathways.biopax.bioSource ORGANISM) throws com.ibm.adtech.jastor.JastorException;
		
}