

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for sequenceSite ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#sequenceSite)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: Describes a site on a sequence, i.e. the position of a single nucleotide or amino acid.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface sequenceSite extends fr.curie.BiNoM.pathways.biopax.sequenceLocation, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#sequenceSite");
	

	/**
	 * The Jena Property for POSITION_DASH_STATUS 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#POSITION-STATUS)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The confidence status of the sequence position. This could be:
EQUAL: The SEQUENCE-POSITION is known to be at the SEQUENCE-POSITION.
GREATER-THAN: The site is greater than the SEQUENCE-POSITION.
LESS-THAN: The site is less than the SEQUENCE-POSITION.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property POSITION_DASH_STATUSProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#POSITION-STATUS");


	/**
	 * The Jena Property for SEQUENCE_DASH_POSITION 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#SEQUENCE-POSITION)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The integer listed gives the position. The first base or amino acid is position 1. In combination with the numeric value, the property 'POSITION-STATUS' allows to express fuzzy positions, e.g. 'less than 4'.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property SEQUENCE_DASH_POSITIONProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SEQUENCE-POSITION");






	/**
	 * Gets the 'POSITION_DASH_STATUS' property value
	 * @return		{@link java.lang.String}
	 * @see			#POSITION_DASH_STATUSProperty
	 */
	public java.lang.String getPOSITION_DASH_STATUS() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'POSITION_DASH_STATUS' property value
	 * @param		{@link java.lang.String}
	 * @see			#POSITION_DASH_STATUSProperty
	 */
	public void setPOSITION_DASH_STATUS(java.lang.String POSITION_DASH_STATUS) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'SEQUENCE_DASH_POSITION' property value
	 * @return		{@link com.hp.hpl.jena.datatypes.xsd.impl.XSDBaseNumericType}
	 * @see			#SEQUENCE_DASH_POSITIONProperty
	 */
	public com.hp.hpl.jena.datatypes.xsd.impl.XSDBaseNumericType getSEQUENCE_DASH_POSITION() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'SEQUENCE_DASH_POSITION' property value
	 * @param		{@link com.hp.hpl.jena.datatypes.xsd.impl.XSDBaseNumericType}
	 * @see			#SEQUENCE_DASH_POSITIONProperty
	 */
	public void setSEQUENCE_DASH_POSITION(com.hp.hpl.jena.datatypes.xsd.impl.XSDBaseNumericType SEQUENCE_DASH_POSITION) throws com.ibm.adtech.jastor.JastorException;

}