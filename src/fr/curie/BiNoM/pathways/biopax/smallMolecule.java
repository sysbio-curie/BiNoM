

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for smallMolecule ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#smallMolecule)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: Any bioactive molecule that is not a peptide, DNA, or RNA. Generally these are non-polymeric, but complex carbohydrates are not explicitly modeled as classes in this version of the ontology, thus are forced into this class.
Comment: Recently, a number of small molecule databases have become available to cross-reference from this class.
Examples: glucose, penicillin, phosphatidylinositol^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface smallMolecule extends fr.curie.BiNoM.pathways.biopax.physicalEntity, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#smallMolecule");
	

	/**
	 * The Jena Property for MOLECULAR_DASH_WEIGHT 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#MOLECULAR-WEIGHT)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Defines the molecular weight of the molecule, in daltons.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property MOLECULAR_DASH_WEIGHTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#MOLECULAR-WEIGHT");


	/**
	 * The Jena Property for STRUCTURE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#STRUCTURE)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Defines the chemical structure and other information about this molecule, using an instance of class chemicalStructure.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property STRUCTUREProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#STRUCTURE");


	/**
	 * The Jena Property for CHEMICAL_DASH_FORMULA 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#CHEMICAL-FORMULA)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The chemical formula of the small molecule. Note: chemical formula can also be stored in the STRUCTURE property (in CML). In case of disagreement between the value of this property and that in the CML file, the CML value takes precedence.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property CHEMICAL_DASH_FORMULAProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#CHEMICAL-FORMULA");






	/**
	 * Gets the 'MOLECULAR_DASH_WEIGHT' property value
	 * @return		{@link java.lang.Double}
	 * @see			#MOLECULAR_DASH_WEIGHTProperty
	 */
	public java.lang.Double getMOLECULAR_DASH_WEIGHT() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'MOLECULAR_DASH_WEIGHT' property value
	 * @param		{@link java.lang.Double}
	 * @see			#MOLECULAR_DASH_WEIGHTProperty
	 */
	public void setMOLECULAR_DASH_WEIGHT(java.lang.Double MOLECULAR_DASH_WEIGHT) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'STRUCTURE' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.chemicalStructure}
	 * @see			#STRUCTUREProperty
	 */
	public java.util.Iterator getSTRUCTURE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'STRUCTURE' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.chemicalStructure} to add
	 * @see			#STRUCTUREProperty
	 */
	public void addSTRUCTURE(fr.curie.BiNoM.pathways.biopax.chemicalStructure STRUCTURE) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'STRUCTURE' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.chemicalStructure} created
	 * @see			#STRUCTUREProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.chemicalStructure addSTRUCTURE() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'STRUCTURE' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.chemicalStructure} with the factory
	 * and calling addSTRUCTURE(fr.curie.BiNoM.pathways.biopax.chemicalStructure STRUCTURE)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#chemicalStructure.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#STRUCTUREProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.chemicalStructure addSTRUCTURE(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'STRUCTURE' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.chemicalStructure} to remove
	 * @see			#STRUCTUREProperty
	 */
	public void removeSTRUCTURE(fr.curie.BiNoM.pathways.biopax.chemicalStructure STRUCTURE) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Gets the 'CHEMICAL_DASH_FORMULA' property value
	 * @return		{@link java.lang.String}
	 * @see			#CHEMICAL_DASH_FORMULAProperty
	 */
	public java.lang.String getCHEMICAL_DASH_FORMULA() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'CHEMICAL_DASH_FORMULA' property value
	 * @param		{@link java.lang.String}
	 * @see			#CHEMICAL_DASH_FORMULAProperty
	 */
	public void setCHEMICAL_DASH_FORMULA(java.lang.String CHEMICAL_DASH_FORMULA) throws com.ibm.adtech.jastor.JastorException;

}