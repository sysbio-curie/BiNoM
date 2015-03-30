

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Complex ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Complex)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A physical entity whose structure is comprised of other physical entities bound to each other covalently or non-covalently, at least one of which is a macromolecule (e.g. protein, DNA, or RNA) and the Stoichiometry of the components are known. 

Comment: Complexes must be stable enough to function as a biological unit; in general, the temporary association of an enzyme with its substrate(s) should not be considered or represented as a complex. A complex is the physical product of an interaction (complexAssembly) and is not itself considered an interaction.
The boundaries on the size of complexes described by this class are not defined here, although possible, elements of the cell  such a mitochondria would typically not be described using this class (later versions of this ontology may include a cellularComponent class to represent these). The strength of binding cannot be described currently, but may be included in future versions of the ontology, depending on community need.
Examples: Ribosome, RNA polymerase II. Other examples of this class include complexes of multiple protein monomers and complexes of proteins and small molecules.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface Complex extends fr.curie.BiNoM.pathways.biopax.PhysicalEntity, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#Complex");
	

	/**
	 * The Jena Property for component 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#component)</p>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property componentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#component");


	/**
	 * The Jena Property for componentStoichiometry 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#componentStoichiometry)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The stoichiometry of components in a complex^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property componentStoichiometryProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#componentStoichiometry");






	/**
	 * Get an Iterator the 'memberPhysicalEntity' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Complex}
	 * @see			#memberPhysicalEntityProperty
	 */
	public java.util.Iterator getMemberPhysicalEntity_asComplex() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'memberPhysicalEntity' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Complex} to add
	 * @see			#memberPhysicalEntityProperty
	 */
	public void addMemberPhysicalEntity(fr.curie.BiNoM.pathways.biopax.Complex memberPhysicalEntity) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'memberPhysicalEntity' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Complex} created
	 * @see			#memberPhysicalEntityProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Complex addMemberPhysicalEntity_asComplex() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'memberPhysicalEntity' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Complex} with the factory
	 * and calling addMemberPhysicalEntity(fr.curie.BiNoM.pathways.biopax.Complex memberPhysicalEntity)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Complex.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#memberPhysicalEntityProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Complex addMemberPhysicalEntity_asComplex(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'memberPhysicalEntity' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Complex} to remove
	 * @see			#memberPhysicalEntityProperty
	 */
	public void removeMemberPhysicalEntity(fr.curie.BiNoM.pathways.biopax.Complex memberPhysicalEntity) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'component' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity}
	 * @see			#componentProperty
	 */
	public java.util.Iterator getComponent() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'component' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to add
	 * @see			#componentProperty
	 */
	public void addComponent(fr.curie.BiNoM.pathways.biopax.PhysicalEntity component) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'component' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} created
	 * @see			#componentProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addComponent() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'component' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} with the factory
	 * and calling addComponent(fr.curie.BiNoM.pathways.biopax.PhysicalEntity component)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#PhysicalEntity.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#componentProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addComponent(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'component' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to remove
	 * @see			#componentProperty
	 */
	public void removeComponent(fr.curie.BiNoM.pathways.biopax.PhysicalEntity component) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'componentStoichiometry' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Stoichiometry}
	 * @see			#componentStoichiometryProperty
	 */
	public java.util.Iterator getComponentStoichiometry() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'componentStoichiometry' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Stoichiometry} to add
	 * @see			#componentStoichiometryProperty
	 */
	public void addComponentStoichiometry(fr.curie.BiNoM.pathways.biopax.Stoichiometry componentStoichiometry) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'componentStoichiometry' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Stoichiometry} created
	 * @see			#componentStoichiometryProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Stoichiometry addComponentStoichiometry() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'componentStoichiometry' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Stoichiometry} with the factory
	 * and calling addComponentStoichiometry(fr.curie.BiNoM.pathways.biopax.Stoichiometry componentStoichiometry)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Stoichiometry.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#componentStoichiometryProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Stoichiometry addComponentStoichiometry(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'componentStoichiometry' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Stoichiometry} to remove
	 * @see			#componentStoichiometryProperty
	 */
	public void removeComponentStoichiometry(fr.curie.BiNoM.pathways.biopax.Stoichiometry componentStoichiometry) throws com.ibm.adtech.jastor.JastorException;
		
}