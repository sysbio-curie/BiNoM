

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for SmallMolecule ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#SmallMolecule)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A pool of molecules that are neither complexes nor are genetically encoded.

Rationale: Identity of small molecules are based on structure, rather than sequence as in the case of DNA, RNA or Protein. A small molecule reference is a grouping of several small molecule entities  that have the same chemical structure.  

Usage : Smalle Molecules can have a cellular location and binding features. They can't have modification features as covalent modifications of small molecules are not considered as state changes but treated as different molecules.
Some non-genomic macromolecules, such as large complex carbohydrates are currently covered by small molecules despite they lack a static structure. Better coverage for such molecules require representation of generic stoichiometry and polymerization, currently planned for BioPAX level 4.

Examples: glucose, penicillin, phosphatidylinositol^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface SmallMolecule extends fr.curie.BiNoM.pathways.biopax.PhysicalEntity, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#SmallMolecule");
	

	/**
	 * The Jena Property for entityReference 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#entityReference)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Reference entity for this physical entity.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property entityReferenceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#entityReference");






	/**
	 * Get an Iterator the 'feature' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.BindingFeature}
	 * @see			#featureProperty
	 */
	public java.util.Iterator getFeature_asBindingFeature() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'feature' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.BindingFeature} to add
	 * @see			#featureProperty
	 */
	public void addFeature(fr.curie.BiNoM.pathways.biopax.BindingFeature feature) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'feature' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.BindingFeature} created
	 * @see			#featureProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.BindingFeature addFeature_asBindingFeature() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'feature' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.BindingFeature} with the factory
	 * and calling addFeature(fr.curie.BiNoM.pathways.biopax.BindingFeature feature)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#BindingFeature.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#featureProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.BindingFeature addFeature_asBindingFeature(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'feature' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.BindingFeature} to remove
	 * @see			#featureProperty
	 */
	public void removeFeature(fr.curie.BiNoM.pathways.biopax.BindingFeature feature) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'notFeature' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.BindingFeature}
	 * @see			#notFeatureProperty
	 */
	public java.util.Iterator getNotFeature_asBindingFeature() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'notFeature' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.BindingFeature} to add
	 * @see			#notFeatureProperty
	 */
	public void addNotFeature(fr.curie.BiNoM.pathways.biopax.BindingFeature notFeature) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'notFeature' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.BindingFeature} created
	 * @see			#notFeatureProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.BindingFeature addNotFeature_asBindingFeature() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'notFeature' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.BindingFeature} with the factory
	 * and calling addNotFeature(fr.curie.BiNoM.pathways.biopax.BindingFeature notFeature)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#BindingFeature.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#notFeatureProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.BindingFeature addNotFeature_asBindingFeature(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'notFeature' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.BindingFeature} to remove
	 * @see			#notFeatureProperty
	 */
	public void removeNotFeature(fr.curie.BiNoM.pathways.biopax.BindingFeature notFeature) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'memberPhysicalEntity' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.SmallMolecule}
	 * @see			#memberPhysicalEntityProperty
	 */
	public java.util.Iterator getMemberPhysicalEntity_asSmallMolecule() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'memberPhysicalEntity' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.SmallMolecule} to add
	 * @see			#memberPhysicalEntityProperty
	 */
	public void addMemberPhysicalEntity(fr.curie.BiNoM.pathways.biopax.SmallMolecule memberPhysicalEntity) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'memberPhysicalEntity' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.SmallMolecule} created
	 * @see			#memberPhysicalEntityProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SmallMolecule addMemberPhysicalEntity_asSmallMolecule() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'memberPhysicalEntity' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.SmallMolecule} with the factory
	 * and calling addMemberPhysicalEntity(fr.curie.BiNoM.pathways.biopax.SmallMolecule memberPhysicalEntity)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#SmallMolecule.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#memberPhysicalEntityProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SmallMolecule addMemberPhysicalEntity_asSmallMolecule(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'memberPhysicalEntity' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.SmallMolecule} to remove
	 * @see			#memberPhysicalEntityProperty
	 */
	public void removeMemberPhysicalEntity(fr.curie.BiNoM.pathways.biopax.SmallMolecule memberPhysicalEntity) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Gets the 'entityReference' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.EntityReference}
	 * @see			#entityReferenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EntityReference getEntityReference() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'entityReference' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.EntityReference}
	 * @see			#entityReferenceProperty
	 */
	public void setEntityReference(fr.curie.BiNoM.pathways.biopax.EntityReference entityReference) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'entityReference' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.EntityReference}, the created value
	 * @see			#entityReferenceProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.EntityReference setEntityReference() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'entityReference' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.EntityReference} with the factory.
	 * and calling setEntityReference(fr.curie.BiNoM.pathways.biopax.EntityReference entityReference)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#EntityReference.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.EntityReference}, the newly created value
	 * @see			#entityReferenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EntityReference setEntityReference(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'entityReference' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference}
	 * @see			#entityReferenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference getEntityReference_asSmallMoleculeReference() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'entityReference' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference}
	 * @see			#entityReferenceProperty
	 */
	public void setEntityReference(fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference entityReference) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'entityReference' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference}, the created value
	 * @see			#entityReferenceProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference setEntityReference_asSmallMoleculeReference() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'entityReference' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference} with the factory.
	 * and calling setEntityReference(fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference entityReference)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#SmallMoleculeReference.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference}, the newly created value
	 * @see			#entityReferenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference setEntityReference_asSmallMoleculeReference(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}