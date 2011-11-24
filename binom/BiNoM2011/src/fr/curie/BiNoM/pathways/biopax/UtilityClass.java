

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for UtilityClass ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#UtilityClass)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: This is a placeholder for classes, used for annotating the "Entity" and its subclasses. Mostly, these are not  an "Entity" themselves. Examples include references to external databases, controlled vocabularies, evidence and provenance.

Rationale: Utility classes are created when simple slots are insufficient to describe an aspect of an entity or to increase compatibility of this ontology with other standards.  

Usage: The utilityClass class is actually a metaclass and is only present to organize the other helper classes under one class hierarchy; instances of utilityClass should never be created.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface UtilityClass extends com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#UtilityClass");
	

	/**
	 * The Jena Property for comment 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#comment)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Comment on the data in the container class. This property should be used instead of the OWL documentation elements (rdfs:comment) for instances because information in 'comment' is data to be exchanged, whereas the rdfs:comment field is used for metadata about the structure of the BioPAX ontology.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property commentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#comment");






	/**
	 * Iterates through the 'comment' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#commentProperty
	 */
	public java.util.Iterator getComment() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'comment' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#commentProperty
	 */
	public void addComment(java.lang.String comment) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'comment' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#commentProperty
	 */
	public void removeComment(java.lang.String comment) throws com.ibm.adtech.jastor.JastorException;

}