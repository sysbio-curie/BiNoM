

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for sequenceInterval ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#sequenceInterval)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: Describes an interval on a sequence. All of the sequence from the begin site to the end site (inclusive) is described, not any subset.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface sequenceInterval extends fr.curie.BiNoM.pathways.biopax.sequenceLocation, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#sequenceInterval");
	

	/**
	 * The Jena Property for SEQUENCE_DASH_INTERVAL_DASH_BEGIN 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#SEQUENCE-INTERVAL-BEGIN)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The begin position of a sequence interval.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SEQUENCE-INTERVAL-BEGIN");


	/**
	 * The Jena Property for SEQUENCE_DASH_INTERVAL_DASH_END 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#SEQUENCE-INTERVAL-END)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The end position of a sequence interval.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property SEQUENCE_DASH_INTERVAL_DASH_ENDProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SEQUENCE-INTERVAL-END");






	/**
	 * Gets the 'SEQUENCE_DASH_INTERVAL_DASH_BEGIN' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.sequenceSite}
	 * @see			#SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.sequenceSite getSEQUENCE_DASH_INTERVAL_DASH_BEGIN() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'SEQUENCE_DASH_INTERVAL_DASH_BEGIN' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.sequenceSite}
	 * @see			#SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty
	 */
	public void setSEQUENCE_DASH_INTERVAL_DASH_BEGIN(fr.curie.BiNoM.pathways.biopax.sequenceSite SEQUENCE_DASH_INTERVAL_DASH_BEGIN) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'SEQUENCE_DASH_INTERVAL_DASH_BEGIN' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.sequenceSite}, the created value
	 * @see			#SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.sequenceSite setSEQUENCE_DASH_INTERVAL_DASH_BEGIN() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'SEQUENCE_DASH_INTERVAL_DASH_BEGIN' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.sequenceSite} with the factory.
	 * and calling setSEQUENCE_DASH_INTERVAL_DASH_BEGIN(fr.curie.BiNoM.pathways.biopax.sequenceSite SEQUENCE_DASH_INTERVAL_DASH_BEGIN)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#sequenceSite.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.sequenceSite}, the newly created value
	 * @see			#SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.sequenceSite setSEQUENCE_DASH_INTERVAL_DASH_BEGIN(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'SEQUENCE_DASH_INTERVAL_DASH_END' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.sequenceSite}
	 * @see			#SEQUENCE_DASH_INTERVAL_DASH_ENDProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.sequenceSite getSEQUENCE_DASH_INTERVAL_DASH_END() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'SEQUENCE_DASH_INTERVAL_DASH_END' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.sequenceSite}
	 * @see			#SEQUENCE_DASH_INTERVAL_DASH_ENDProperty
	 */
	public void setSEQUENCE_DASH_INTERVAL_DASH_END(fr.curie.BiNoM.pathways.biopax.sequenceSite SEQUENCE_DASH_INTERVAL_DASH_END) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'SEQUENCE_DASH_INTERVAL_DASH_END' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.sequenceSite}, the created value
	 * @see			#SEQUENCE_DASH_INTERVAL_DASH_ENDProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.sequenceSite setSEQUENCE_DASH_INTERVAL_DASH_END() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'SEQUENCE_DASH_INTERVAL_DASH_END' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.sequenceSite} with the factory.
	 * and calling setSEQUENCE_DASH_INTERVAL_DASH_END(fr.curie.BiNoM.pathways.biopax.sequenceSite SEQUENCE_DASH_INTERVAL_DASH_END)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#sequenceSite.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.sequenceSite}, the newly created value
	 * @see			#SEQUENCE_DASH_INTERVAL_DASH_ENDProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.sequenceSite setSEQUENCE_DASH_INTERVAL_DASH_END(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}