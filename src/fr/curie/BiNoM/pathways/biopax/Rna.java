

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Rna ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Rna)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A physical entity consisting of a sequence of ribonucleotide monophosphates; a ribonucleic acid.
Usage: RNA should be used for pools of individual RNA molecules. For describing subregions on those molecules use RNARegion.
Examples: messengerRNA, microRNA, ribosomalRNA. A specific example is the let-7 microRNA.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface Rna extends fr.curie.BiNoM.pathways.biopax.PhysicalEntity, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#Rna");
	

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
	 * Get an Iterator the 'memberPhysicalEntity' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Rna}
	 * @see			#memberPhysicalEntityProperty
	 */
	public java.util.Iterator getMemberPhysicalEntity_asRna() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'memberPhysicalEntity' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Rna} to add
	 * @see			#memberPhysicalEntityProperty
	 */
	public void addMemberPhysicalEntity(fr.curie.BiNoM.pathways.biopax.Rna memberPhysicalEntity) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'memberPhysicalEntity' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Rna} created
	 * @see			#memberPhysicalEntityProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Rna addMemberPhysicalEntity_asRna() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'memberPhysicalEntity' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Rna} with the factory
	 * and calling addMemberPhysicalEntity(fr.curie.BiNoM.pathways.biopax.Rna memberPhysicalEntity)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Rna.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#memberPhysicalEntityProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Rna addMemberPhysicalEntity_asRna(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'memberPhysicalEntity' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Rna} to remove
	 * @see			#memberPhysicalEntityProperty
	 */
	public void removeMemberPhysicalEntity(fr.curie.BiNoM.pathways.biopax.Rna memberPhysicalEntity) throws com.ibm.adtech.jastor.JastorException;
		
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
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.RnaReference}
	 * @see			#entityReferenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.RnaReference getEntityReference_asRnaReference() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'entityReference' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.RnaReference}
	 * @see			#entityReferenceProperty
	 */
	public void setEntityReference(fr.curie.BiNoM.pathways.biopax.RnaReference entityReference) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'entityReference' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.RnaReference}, the created value
	 * @see			#entityReferenceProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.RnaReference setEntityReference_asRnaReference() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'entityReference' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.RnaReference} with the factory.
	 * and calling setEntityReference(fr.curie.BiNoM.pathways.biopax.RnaReference entityReference)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#RnaReference.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.RnaReference}, the newly created value
	 * @see			#entityReferenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.RnaReference setEntityReference_asRnaReference(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}