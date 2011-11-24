

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for relationshipXref ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#relationshipXref)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: An xref that defines a reference to an entity in an external resource that does not have the same biological identity as the referring entity.
Comment: There is currently no controlled vocabulary of relationship types for BioPAX, although one will be created in the future if a need develops.
Examples: A link between a gene G in a BioPAX data collection, and the protein product P of that gene in an external database. This is not a unification xref because G and P are different biological entities (one is a gene and one is a protein). Another example is a relationship xref for a protein that refers to the Gene Ontology biological process, e.g. 'immune response,' that the protein is involved in.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface relationshipXref extends fr.curie.BiNoM.pathways.biopax.xref, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#relationshipXref");
	

	/**
	 * The Jena Property for RELATIONSHIP_DASH_TYPE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#RELATIONSHIP-TYPE)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : This property names the type of relationship between the BioPAX object linked from, and the external object linked to, such as 'gene of this protein', or 'protein with similar sequence'.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property RELATIONSHIP_DASH_TYPEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#RELATIONSHIP-TYPE");






	/**
	 * Gets the 'RELATIONSHIP_DASH_TYPE' property value
	 * @return		{@link java.lang.String}
	 * @see			#RELATIONSHIP_DASH_TYPEProperty
	 */
	public java.lang.String getRELATIONSHIP_DASH_TYPE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'RELATIONSHIP_DASH_TYPE' property value
	 * @param		{@link java.lang.String}
	 * @see			#RELATIONSHIP_DASH_TYPEProperty
	 */
	public void setRELATIONSHIP_DASH_TYPE(java.lang.String RELATIONSHIP_DASH_TYPE) throws com.ibm.adtech.jastor.JastorException;

}