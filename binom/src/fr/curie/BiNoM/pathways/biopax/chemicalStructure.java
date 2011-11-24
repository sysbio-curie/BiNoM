

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for chemicalStructure ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#chemicalStructure)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: Describes a small molecule structure. Structure information is stored in the property STRUCTURE-DATA, in one of three formats: the CML format (see URL www.xml-cml.org), the SMILES format (see URL www.daylight.com/dayhtml/smiles/) or the InChI format (http://www.iupac.org/inchi/). The STRUCTURE-FORMAT property specifies which format is used.
Comment: By virtue of the expressivity of CML, an instance of this class can also provide additional information about a small molecule, such as its chemical formula, names, and synonyms, if CML is used as the structure format.
Examples: The following SMILES string, which describes the structure of glucose-6-phosphate:
'C(OP(=O)(O)O)[CH]1([CH](O)[CH](O)[CH](O)[CH](O)O1)'.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface chemicalStructure extends fr.curie.BiNoM.pathways.biopax.utilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#chemicalStructure");
	

	/**
	 * The Jena Property for STRUCTURE_DASH_DATA 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#STRUCTURE-DATA)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : This property holds a string of data defining chemical structure or other information, in either the CML or SMILES format, as specified in property Structure-Format. If, for example, the CML format is used, then the value of this property is a string containing the XML encoding of the CML data.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property STRUCTURE_DASH_DATAProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#STRUCTURE-DATA");


	/**
	 * The Jena Property for STRUCTURE_DASH_FORMAT 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#STRUCTURE-FORMAT)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : This property specifies which format is used to define chemical structure data.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property STRUCTURE_DASH_FORMATProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#STRUCTURE-FORMAT");






	/**
	 * Gets the 'STRUCTURE_DASH_DATA' property value
	 * @return		{@link java.lang.String}
	 * @see			#STRUCTURE_DASH_DATAProperty
	 */
	public java.lang.String getSTRUCTURE_DASH_DATA() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'STRUCTURE_DASH_DATA' property value
	 * @param		{@link java.lang.String}
	 * @see			#STRUCTURE_DASH_DATAProperty
	 */
	public void setSTRUCTURE_DASH_DATA(java.lang.String STRUCTURE_DASH_DATA) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'STRUCTURE_DASH_FORMAT' property value
	 * @return		{@link java.lang.String}
	 * @see			#STRUCTURE_DASH_FORMATProperty
	 */
	public java.lang.String getSTRUCTURE_DASH_FORMAT() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'STRUCTURE_DASH_FORMAT' property value
	 * @param		{@link java.lang.String}
	 * @see			#STRUCTURE_DASH_FORMATProperty
	 */
	public void setSTRUCTURE_DASH_FORMAT(java.lang.String STRUCTURE_DASH_FORMAT) throws com.ibm.adtech.jastor.JastorException;

}