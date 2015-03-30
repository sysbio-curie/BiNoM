

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for BiochemicalPathwayStep ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#BiochemicalPathwayStep)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: Imposes ordering on a step in a biochemical pathway. 
Retionale: A biochemical reaction can be reversible by itself, but can be physiologically directed in the context of a pathway, for instance due to flux of reactants and products. 
Usage: Only one conversion interaction can be ordered at a time, but multiple catalysis or modulation instances can be part of one step.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface BiochemicalPathwayStep extends fr.curie.BiNoM.pathways.biopax.PathwayStep, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#BiochemicalPathwayStep");
	

	/**
	 * The Jena Property for stepConversion 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#stepConversion)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The central process that take place at this step of the biochemical pathway.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property stepConversionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#stepConversion");


	/**
	 * The Jena Property for stepDirection 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#stepDirection)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Direction of the conversion in this particular pathway context. 
This property can be used for annotating direction of enzymatic activity. Even if an enzyme catalyzes a reaction reversibly, the flow of matter through the pathway will force the equilibrium in a given direction for that particular pathway.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property stepDirectionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#stepDirection");






	/**
	 * Get an Iterator the 'stepProcess' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Control}
	 * @see			#stepProcessProperty
	 */
	public java.util.Iterator getStepProcess_asControl() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'stepProcess' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Control} to add
	 * @see			#stepProcessProperty
	 */
	public void addStepProcess(fr.curie.BiNoM.pathways.biopax.Control stepProcess) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'stepProcess' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Control} created
	 * @see			#stepProcessProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Control addStepProcess_asControl() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'stepProcess' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Control} with the factory
	 * and calling addStepProcess(fr.curie.BiNoM.pathways.biopax.Control stepProcess)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Control.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#stepProcessProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Control addStepProcess_asControl(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'stepProcess' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Control} to remove
	 * @see			#stepProcessProperty
	 */
	public void removeStepProcess(fr.curie.BiNoM.pathways.biopax.Control stepProcess) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Gets the 'stepConversion' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Conversion}
	 * @see			#stepConversionProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Conversion getStepConversion() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'stepConversion' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.Conversion}
	 * @see			#stepConversionProperty
	 */
	public void setStepConversion(fr.curie.BiNoM.pathways.biopax.Conversion stepConversion) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'stepConversion' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Conversion}, the created value
	 * @see			#stepConversionProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.Conversion setStepConversion() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'stepConversion' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Conversion} with the factory.
	 * and calling setStepConversion(fr.curie.BiNoM.pathways.biopax.Conversion stepConversion)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Conversion.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Conversion}, the newly created value
	 * @see			#stepConversionProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Conversion setStepConversion(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'stepDirection' property value
	 * @return		{@link java.lang.String}
	 * @see			#stepDirectionProperty
	 */
	public java.lang.String getStepDirection() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'stepDirection' property value
	 * @param		{@link java.lang.String}
	 * @see			#stepDirectionProperty
	 */
	public void setStepDirection(java.lang.String stepDirection) throws com.ibm.adtech.jastor.JastorException;

}