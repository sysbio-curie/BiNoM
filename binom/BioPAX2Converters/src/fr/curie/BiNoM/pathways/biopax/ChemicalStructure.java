

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for ChemicalStructure ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#ChemicalStructure)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: The chemical structure of a small molecule. 

Usage: Structure information is stored in the property structureData, in one of three formats: the CML format (see URL www.xml-cml.org), the SMILES format (see URL www.daylight.com/dayhtml/smiles/) or the InChI format (http://www.iupac.org/inchi/). The structureFormat property specifies which format is used.

Examples: The following SMILES string describes the structure of glucose-6-phosphate:
'C(OP(=O)(O)O)[CH]1([CH](O)[CH](O)[CH](O)[CH](O)O1)'.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface ChemicalStructure extends fr.curie.BiNoM.pathways.biopax.UtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#ChemicalStructure");
	

	/**
	 * The Jena Property for structureFormat 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#structureFormat)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : This property specifies which format is used to define chemical structure data.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property structureFormatProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#structureFormat");


	/**
	 * The Jena Property for structureData 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#structureData)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : This property holds a string of data defining chemical structure or other information, in either the CML or SMILES format, as specified in property Structure-Format. If, for example, the CML format is used, then the value of this property is a string containing the XML encoding of the CML data.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property structureDataProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#structureData");






	/**
	 * Gets the 'structureFormat' property value
	 * @return		{@link java.lang.String}
	 * @see			#structureFormatProperty
	 */
	public java.lang.String getStructureFormat() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'structureFormat' property value
	 * @param		{@link java.lang.String}
	 * @see			#structureFormatProperty
	 */
	public void setStructureFormat(java.lang.String structureFormat) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'structureData' property value
	 * @return		{@link java.lang.String}
	 * @see			#structureDataProperty
	 */
	public java.lang.String getStructureData() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'structureData' property value
	 * @param		{@link java.lang.String}
	 * @see			#structureDataProperty
	 */
	public void setStructureData(java.lang.String structureData) throws com.ibm.adtech.jastor.JastorException;

}