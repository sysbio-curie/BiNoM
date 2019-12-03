

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for EntityReference ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#EntityReference)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: An entity reference is a grouping of several physical entities across different contexts and molecular states, that share common physical properties and often named and treated as a single entity with multiple states by biologists. 

Rationale:   Many protein, small molecule and gene databases share this point of view, and such a grouping is an important prerequisite for interoperability with those databases. Biologists would often group different pools of molecules in different contexts under the same name. For example cytoplasmic and extracellular calcium have different effects on the cell's behavior, but they are still called calcium. For DNA, RNA and Proteins the grouping is defined based on a wildtype sequence, for small molecules it is defined by the chemical structure.

Usage: Entity references store the information common to a set of molecules in various states described in the BioPAX document, including database cross-references. For instance, the P53 protein can be phosphorylated in multiple different ways. Each separate P53 protein (pool) in a phosphorylation state would be represented as a different protein (child of physicalEntity) and all things common to all P53 proteins, including all possible phosphorylation sites, the sequence common to all of them and common references to protein databases containing more information about P53 would be stored in a Entity Reference.  

Comments: This grouping has three semantic implications:

1.  Members of different pools share many physical and biochemical properties. This includes their chemical structure, sequence, organism and set of molecules they react with. They will also share a lot of secondary information such as their names, functional groupings, annotation terms and database identifiers.

2. A small number of transitions seperates these pools. In other words it is relatively easy and frequent for a molecule to transform from one physical entity to another that belong to the same reference entity. For example an extracellular calcium can become cytoplasmic, and p53 can become phosphorylated. However no calcium virtually becomes sodium, or no p53 becomes mdm2. In the former it is the sheer energy barrier of a nuclear reaction, in the latter sheer statistical improbability of synthesizing the same sequence without a template. If one thinks about the biochemical network as molecules transforming into each other, and remove edges that respond to transcription, translation, degradation and covalent modification of small molecules, each remaining component is a reference entity.

3. Some of the pools in the same group can overlap. p53-p@ser15 can overlap with p53-p@thr18. Most of the experiments in molecular biology will only check for one state variable, rarely multiple, and never for the all possible combinations. So almost all statements that refer to the state of the molecule talk about a pool that can overlap with other pools. However no overlaps is possible between molecules of different groups.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface EntityReference extends fr.curie.BiNoM.pathways.biopax.UtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#EntityReference");
	

	/**
	 * The Jena Property for memberEntityReference 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#memberEntityReference)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : An entity reference that qualifies for the definition of this group. For example a member of a PFAM protein family.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property memberEntityReferenceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#memberEntityReference");


	/**
	 * The Jena Property for entityReferenceType 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#entityReferenceType)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : A controlled vocabulary term that is used to describe the type of grouping such as homology or functional group.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property entityReferenceTypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#entityReferenceType");


	/**
	 * The Jena Property for entityFeature 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#entityFeature)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Variable features that are observed for this entity - such as known PTM or methylation sites and non-covalent bonds.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property entityFeatureProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#entityFeature");


	/**
	 * The Jena Property for name 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#name)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : One or more synonyms for the name of this individual. This should include the values of the standardName and displayName property so that it is easy to find all known names in one place.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property nameProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#name");


	/**
	 * The Jena Property for evidence 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#evidence)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Scientific evidence supporting the existence of the entity as described.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property evidenceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#evidence");


	/**
	 * The Jena Property for xref 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#xref)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Values of this property define external cross-references from this entity to entities in external databases.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property xrefProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#xref");






	/**
	 * Get an Iterator the 'memberEntityReference' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.EntityReference}
	 * @see			#memberEntityReferenceProperty
	 */
	public java.util.Iterator getMemberEntityReference() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'memberEntityReference' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.EntityReference} to add
	 * @see			#memberEntityReferenceProperty
	 */
	public void addMemberEntityReference(fr.curie.BiNoM.pathways.biopax.EntityReference memberEntityReference) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'memberEntityReference' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.EntityReference} created
	 * @see			#memberEntityReferenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EntityReference addMemberEntityReference() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'memberEntityReference' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.EntityReference} with the factory
	 * and calling addMemberEntityReference(fr.curie.BiNoM.pathways.biopax.EntityReference memberEntityReference)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#EntityReference.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#memberEntityReferenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EntityReference addMemberEntityReference(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'memberEntityReference' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.EntityReference} to remove
	 * @see			#memberEntityReferenceProperty
	 */
	public void removeMemberEntityReference(fr.curie.BiNoM.pathways.biopax.EntityReference memberEntityReference) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Gets the 'entityReferenceType' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary}
	 * @see			#entityReferenceTypeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary getEntityReferenceType() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'entityReferenceType' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary}
	 * @see			#entityReferenceTypeProperty
	 */
	public void setEntityReferenceType(fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary entityReferenceType) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'entityReferenceType' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary}, the created value
	 * @see			#entityReferenceTypeProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary setEntityReferenceType() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'entityReferenceType' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary} with the factory.
	 * and calling setEntityReferenceType(fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary entityReferenceType)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#EntityReferenceTypeVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary}, the newly created value
	 * @see			#entityReferenceTypeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary setEntityReferenceType(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Get an Iterator the 'entityFeature' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.EntityFeature}
	 * @see			#entityFeatureProperty
	 */
	public java.util.Iterator getEntityFeature() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'entityFeature' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} to add
	 * @see			#entityFeatureProperty
	 */
	public void addEntityFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature entityFeature) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'entityFeature' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} created
	 * @see			#entityFeatureProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addEntityFeature() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'entityFeature' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} with the factory
	 * and calling addEntityFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature entityFeature)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#EntityFeature.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#entityFeatureProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addEntityFeature(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'entityFeature' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} to remove
	 * @see			#entityFeatureProperty
	 */
	public void removeEntityFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature entityFeature) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Iterates through the 'name' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#nameProperty
	 */
	public java.util.Iterator getName() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'name' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#nameProperty
	 */
	public void addName(java.lang.String name) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'name' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#nameProperty
	 */
	public void removeName(java.lang.String name) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'evidence' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Evidence}
	 * @see			#evidenceProperty
	 */
	public java.util.Iterator getEvidence() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'evidence' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Evidence} to add
	 * @see			#evidenceProperty
	 */
	public void addEvidence(fr.curie.BiNoM.pathways.biopax.Evidence evidence) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'evidence' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Evidence} created
	 * @see			#evidenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Evidence addEvidence() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'evidence' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Evidence} with the factory
	 * and calling addEvidence(fr.curie.BiNoM.pathways.biopax.Evidence evidence)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Evidence.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#evidenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Evidence addEvidence(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'evidence' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Evidence} to remove
	 * @see			#evidenceProperty
	 */
	public void removeEvidence(fr.curie.BiNoM.pathways.biopax.Evidence evidence) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'xref' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Xref}
	 * @see			#xrefProperty
	 */
	public java.util.Iterator getXref() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'xref' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Xref} to add
	 * @see			#xrefProperty
	 */
	public void addXref(fr.curie.BiNoM.pathways.biopax.Xref xref) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'xref' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Xref} created
	 * @see			#xrefProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Xref addXref() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'xref' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Xref} with the factory
	 * and calling addXref(fr.curie.BiNoM.pathways.biopax.Xref xref)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Xref.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#xrefProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Xref addXref(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'xref' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Xref} to remove
	 * @see			#xrefProperty
	 */
	public void removeXref(fr.curie.BiNoM.pathways.biopax.Xref xref) throws com.ibm.adtech.jastor.JastorException;
		
}