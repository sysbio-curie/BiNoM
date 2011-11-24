

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for SmallMoleculeReference ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#SmallMoleculeReference)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : A small molecule reference is a grouping of several small molecule entities  that have the same chemical structure.  Members can differ in celular location and bound partners. Covalent modifications of small molecules are not considered as state changes but treated as different molecules.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface SmallMoleculeReference extends fr.curie.BiNoM.pathways.biopax.EntityReference, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#SmallMoleculeReference");
	

	/**
	 * The Jena Property for chemicalFormula 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#chemicalFormula)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The chemical formula of the small molecule. Note: chemical formula can also be stored in the STRUCTURE property (in CML). In case of disagreement between the value of this property and that in the CML file, the CML value takes precedence.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property chemicalFormulaProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#chemicalFormula");


	/**
	 * The Jena Property for molecularWeight 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#molecularWeight)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Defines the molecular weight of the molecule, in daltons.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property molecularWeightProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#molecularWeight");


	/**
	 * The Jena Property for structure 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#structure)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Defines the chemical structure and other information about this molecule, using an instance of class chemicalStructure.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property structureProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#structure");






	/**
	 * Get an Iterator the 'memberEntityReference' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference}
	 * @see			#memberEntityReferenceProperty
	 */
	public java.util.Iterator getMemberEntityReference_asSmallMoleculeReference() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'memberEntityReference' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference} to add
	 * @see			#memberEntityReferenceProperty
	 */
	public void addMemberEntityReference(fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference memberEntityReference) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'memberEntityReference' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference} created
	 * @see			#memberEntityReferenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference addMemberEntityReference_asSmallMoleculeReference() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'memberEntityReference' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference} with the factory
	 * and calling addMemberEntityReference(fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference memberEntityReference)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#SmallMoleculeReference.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#memberEntityReferenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference addMemberEntityReference_asSmallMoleculeReference(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'memberEntityReference' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference} to remove
	 * @see			#memberEntityReferenceProperty
	 */
	public void removeMemberEntityReference(fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference memberEntityReference) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Gets the 'chemicalFormula' property value
	 * @return		{@link java.lang.String}
	 * @see			#chemicalFormulaProperty
	 */
	public java.lang.String getChemicalFormula() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'chemicalFormula' property value
	 * @param		{@link java.lang.String}
	 * @see			#chemicalFormulaProperty
	 */
	public void setChemicalFormula(java.lang.String chemicalFormula) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'molecularWeight' property value
	 * @return		{@link java.lang.Float}
	 * @see			#molecularWeightProperty
	 */
	public java.lang.Float getMolecularWeight() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'molecularWeight' property value
	 * @param		{@link java.lang.Float}
	 * @see			#molecularWeightProperty
	 */
	public void setMolecularWeight(java.lang.Float molecularWeight) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'structure' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.ChemicalStructure}
	 * @see			#structureProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.ChemicalStructure getStructure() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'structure' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.ChemicalStructure}
	 * @see			#structureProperty
	 */
	public void setStructure(fr.curie.BiNoM.pathways.biopax.ChemicalStructure structure) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'structure' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.ChemicalStructure}, the created value
	 * @see			#structureProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.ChemicalStructure setStructure() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'structure' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.ChemicalStructure} with the factory.
	 * and calling setStructure(fr.curie.BiNoM.pathways.biopax.ChemicalStructure structure)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#ChemicalStructure.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.ChemicalStructure}, the newly created value
	 * @see			#structureProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.ChemicalStructure setStructure(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}