

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for RelationshipXref ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#RelationshipXref)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: An xref that defines a reference to an entity in an external resource that does not have the same biological identity as the referring entity.
Usage: There is currently no controlled vocabulary of relationship types for BioPAX, although one will be created in the future if a need develops.
Examples: A link between a gene G in a BioPAX data collection, and the protein product P of that gene in an external database. This is not a unification xref because G and P are different biological entities (one is a gene and one is a protein). Another example is a relationship xref for a protein that refers to the Gene Ontology biological process, e.g. 'immune response,' that the protein is involved in.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface RelationshipXref extends fr.curie.BiNoM.pathways.biopax.Xref, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#RelationshipXref");
	

	/**
	 * The Jena Property for relationshipType 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#relationshipType)</p>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property relationshipTypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#relationshipType");






	/**
	 * Get an Iterator the 'relationshipType' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary}
	 * @see			#relationshipTypeProperty
	 */
	public java.util.Iterator getRelationshipType() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'relationshipType' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary} to add
	 * @see			#relationshipTypeProperty
	 */
	public void addRelationshipType(fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary relationshipType) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'relationshipType' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary} created
	 * @see			#relationshipTypeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary addRelationshipType() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'relationshipType' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary} with the factory
	 * and calling addRelationshipType(fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary relationshipType)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#RelationshipTypeVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#relationshipTypeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary addRelationshipType(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'relationshipType' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary} to remove
	 * @see			#relationshipTypeProperty
	 */
	public void removeRelationshipType(fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary relationshipType) throws com.ibm.adtech.jastor.JastorException;
		
}