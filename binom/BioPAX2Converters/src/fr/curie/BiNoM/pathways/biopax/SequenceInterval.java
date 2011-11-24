

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for SequenceInterval ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#SequenceInterval)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: An interval on a sequence. 
Usage: Interval is defined as an ordered pair of SequenceSites. All of the sequence from the begin site to the end site (inclusive) is described, not any subset.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface SequenceInterval extends fr.curie.BiNoM.pathways.biopax.SequenceLocation, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#SequenceInterval");
	

	/**
	 * The Jena Property for sequenceIntervalEnd 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#sequenceIntervalEnd)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The end position of a sequence interval.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property sequenceIntervalEndProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#sequenceIntervalEnd");


	/**
	 * The Jena Property for sequenceIntervalBegin 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#sequenceIntervalBegin)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The begin position of a sequence interval.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property sequenceIntervalBeginProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#sequenceIntervalBegin");






	/**
	 * Gets the 'sequenceIntervalEnd' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SequenceSite}
	 * @see			#sequenceIntervalEndProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SequenceSite getSequenceIntervalEnd() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'sequenceIntervalEnd' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.SequenceSite}
	 * @see			#sequenceIntervalEndProperty
	 */
	public void setSequenceIntervalEnd(fr.curie.BiNoM.pathways.biopax.SequenceSite sequenceIntervalEnd) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'sequenceIntervalEnd' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SequenceSite}, the created value
	 * @see			#sequenceIntervalEndProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.SequenceSite setSequenceIntervalEnd() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'sequenceIntervalEnd' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.SequenceSite} with the factory.
	 * and calling setSequenceIntervalEnd(fr.curie.BiNoM.pathways.biopax.SequenceSite sequenceIntervalEnd)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#SequenceSite.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SequenceSite}, the newly created value
	 * @see			#sequenceIntervalEndProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SequenceSite setSequenceIntervalEnd(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'sequenceIntervalBegin' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SequenceSite}
	 * @see			#sequenceIntervalBeginProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SequenceSite getSequenceIntervalBegin() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'sequenceIntervalBegin' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.SequenceSite}
	 * @see			#sequenceIntervalBeginProperty
	 */
	public void setSequenceIntervalBegin(fr.curie.BiNoM.pathways.biopax.SequenceSite sequenceIntervalBegin) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'sequenceIntervalBegin' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SequenceSite}, the created value
	 * @see			#sequenceIntervalBeginProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.SequenceSite setSequenceIntervalBegin() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'sequenceIntervalBegin' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.SequenceSite} with the factory.
	 * and calling setSequenceIntervalBegin(fr.curie.BiNoM.pathways.biopax.SequenceSite sequenceIntervalBegin)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#SequenceSite.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.SequenceSite}, the newly created value
	 * @see			#sequenceIntervalBeginProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SequenceSite setSequenceIntervalBegin(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}