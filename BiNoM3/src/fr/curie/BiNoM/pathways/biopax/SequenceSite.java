

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for SequenceSite ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#SequenceSite)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: Describes a site on a sequence, i.e. the position of a single nucleotide or amino acid.
Usage: A sequence site is always defined based on the reference sequence of the owning entity. For DNARegion and RNARegion it is relative to the region itself not the genome or full RNA molecule.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface SequenceSite extends fr.curie.BiNoM.pathways.biopax.SequenceLocation, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#SequenceSite");
	

	/**
	 * The Jena Property for sequencePosition 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#sequencePosition)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The integer listed gives the position. The first base or amino acid is position 1. In combination with the numeric value, the property 'POSITION-STATUS' allows to express fuzzy positions, e.g. 'less than 4'.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property sequencePositionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#sequencePosition");


	/**
	 * The Jena Property for positionStatus 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#positionStatus)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The confidence status of the sequence position. This could be:
EQUAL: The SEQUENCE-POSITION is known to be at the SEQUENCE-POSITION.
GREATER-THAN: The site is greater than the SEQUENCE-POSITION.
LESS-THAN: The site is less than the SEQUENCE-POSITION.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property positionStatusProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#positionStatus");






	/**
	 * Gets the 'sequencePosition' property value
	 * @return		{@link java.lang.Integer}
	 * @see			#sequencePositionProperty
	 */
	public java.lang.Integer getSequencePosition() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'sequencePosition' property value
	 * @param		{@link java.lang.Integer}
	 * @see			#sequencePositionProperty
	 */
	public void setSequencePosition(java.lang.Integer sequencePosition) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'positionStatus' property value
	 * @return		{@link java.lang.String}
	 * @see			#positionStatusProperty
	 */
	public java.lang.String getPositionStatus() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'positionStatus' property value
	 * @param		{@link java.lang.String}
	 * @see			#positionStatusProperty
	 */
	public void setPositionStatus(java.lang.String positionStatus) throws com.ibm.adtech.jastor.JastorException;

}