

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Score ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Score)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A score associated with a publication reference describing how the score was determined, the name of the method and a comment briefly describing the method.
Usage:  The xref must contain at least one publication that describes the method used to determine the score value. There is currently no standard way of describing  values, so any string is valid.
Examples: The statistical significance of a result, e.g. "p<0.05".^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface Score extends fr.curie.BiNoM.pathways.biopax.UtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#Score");
	

	/**
	 * The Jena Property for value 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#value)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The value of the score.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property valueProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#value");


	/**
	 * The Jena Property for scoreSource 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#scoreSource)</p>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property scoreSourceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#scoreSource");






	/**
	 * Gets the 'value' property value
	 * @return		{@link java.lang.String}
	 * @see			#valueProperty
	 */
	public java.lang.String getValue() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'value' property value
	 * @param		{@link java.lang.String}
	 * @see			#valueProperty
	 */
	public void setValue(java.lang.String value) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'scoreSource' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Provenance}
	 * @see			#scoreSourceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Provenance getScoreSource() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'scoreSource' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.Provenance}
	 * @see			#scoreSourceProperty
	 */
	public void setScoreSource(fr.curie.BiNoM.pathways.biopax.Provenance scoreSource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'scoreSource' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Provenance}, the created value
	 * @see			#scoreSourceProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.Provenance setScoreSource() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'scoreSource' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Provenance} with the factory.
	 * and calling setScoreSource(fr.curie.BiNoM.pathways.biopax.Provenance scoreSource)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Provenance.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Provenance}, the newly created value
	 * @see			#scoreSourceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Provenance setScoreSource(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}