

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for PhysicalEntity ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#PhysicalEntity)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A pool of molecules or molecular complexes. 

Comments: Each PhysicalEntity is defined by a  sequence or structure based on an EntityReference AND any set of Features that are given. For example,  ser46 phosphorylated p53 is a physical entity in BioPAX defined by the p53 sequence and the phosphorylation feature on the serine at position 46 in the sequence.  Features are any combination of cellular location, covalent and non-covalent bonds with other molecules and covalent modifications.  

For a specific molecule to be a member of the pool it has to satisfy all of the specified features. Unspecified features are treated as unknowns or unneccesary. Features that are known to not be on the molecules should be explicitly stated with the "not feature" property. 
A physical entity in BioPAX  never represents a specific molecular instance. 

Physical Entity can be heterogenous and potentially overlap, i.e. a single molecule can be counted as a member of multiple pools. This makes BioPAX semantics different than regular chemical notation but is necessary for dealing with combinatorial complexity. 

Synonyms: part, interactor, object, species

Examples: extracellular calcium, ser 64 phosphorylated p53^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface PhysicalEntity extends fr.curie.BiNoM.pathways.biopax.Entity, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#PhysicalEntity");
	

	/**
	 * The Jena Property for feature 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#feature)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Sequence features of the owner physical entity.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property featureProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#feature");


	/**
	 * The Jena Property for notFeature 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#notFeature)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Sequence features where the owner physical entity has a feature. If not specified, other potential features are not known.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property notFeatureProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#notFeature");


	/**
	 * The Jena Property for memberPhysicalEntity 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#memberPhysicalEntity)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : This property stores the members of a generic physical entity. 

For representing homology generics a better way is to use generic entity references and generic features. However not all generic logic can be captured by this, such as complex generics or rare cases where feature cardinality is variable. Usages of this property should be limited to such cases.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property memberPhysicalEntityProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#memberPhysicalEntity");


	/**
	 * The Jena Property for cellularLocation 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#cellularLocation)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : A cellular location, e.g. 'cytoplasm'. This should reference a term in the Gene Ontology Cellular Component ontology. The location referred to by this property should be as specific as is known. If an interaction is known to occur in multiple locations, separate interactions (and physicalEntities) must be created for each different location.  If the location of a participant in a complex is unspecified, it may be assumed to be the same location as that of the complex. 

 A molecule in two different cellular locations are considered two different physical entities.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property cellularLocationProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#cellularLocation");






	/**
	 * Get an Iterator the 'feature' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.EntityFeature}
	 * @see			#featureProperty
	 */
	public java.util.Iterator getFeature() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'feature' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} to add
	 * @see			#featureProperty
	 */
	public void addFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature feature) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'feature' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} created
	 * @see			#featureProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addFeature() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'feature' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} with the factory
	 * and calling addFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature feature)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#EntityFeature.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#featureProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addFeature(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'feature' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} to remove
	 * @see			#featureProperty
	 */
	public void removeFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature feature) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'notFeature' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.EntityFeature}
	 * @see			#notFeatureProperty
	 */
	public java.util.Iterator getNotFeature() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'notFeature' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} to add
	 * @see			#notFeatureProperty
	 */
	public void addNotFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature notFeature) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'notFeature' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} created
	 * @see			#notFeatureProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addNotFeature() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'notFeature' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} with the factory
	 * and calling addNotFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature notFeature)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#EntityFeature.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#notFeatureProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addNotFeature(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'notFeature' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} to remove
	 * @see			#notFeatureProperty
	 */
	public void removeNotFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature notFeature) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'memberPhysicalEntity' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity}
	 * @see			#memberPhysicalEntityProperty
	 */
	public java.util.Iterator getMemberPhysicalEntity() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'memberPhysicalEntity' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to add
	 * @see			#memberPhysicalEntityProperty
	 */
	public void addMemberPhysicalEntity(fr.curie.BiNoM.pathways.biopax.PhysicalEntity memberPhysicalEntity) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'memberPhysicalEntity' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} created
	 * @see			#memberPhysicalEntityProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addMemberPhysicalEntity() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'memberPhysicalEntity' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} with the factory
	 * and calling addMemberPhysicalEntity(fr.curie.BiNoM.pathways.biopax.PhysicalEntity memberPhysicalEntity)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#PhysicalEntity.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#memberPhysicalEntityProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addMemberPhysicalEntity(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'memberPhysicalEntity' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to remove
	 * @see			#memberPhysicalEntityProperty
	 */
	public void removeMemberPhysicalEntity(fr.curie.BiNoM.pathways.biopax.PhysicalEntity memberPhysicalEntity) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Gets the 'cellularLocation' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary}
	 * @see			#cellularLocationProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary getCellularLocation() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'cellularLocation' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary}
	 * @see			#cellularLocationProperty
	 */
	public void setCellularLocation(fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary cellularLocation) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'cellularLocation' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary}, the created value
	 * @see			#cellularLocationProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary setCellularLocation() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'cellularLocation' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary} with the factory.
	 * and calling setCellularLocation(fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary cellularLocation)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#CellularLocationVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary}, the newly created value
	 * @see			#cellularLocationProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary setCellularLocation(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}