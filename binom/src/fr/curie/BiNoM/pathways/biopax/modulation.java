

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for modulation ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#modulation)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A control interaction in which a physical entity modulates a catalysis interaction. Biologically, most modulation interactions describe an interaction in which a small molecule alters the ability of an enzyme to catalyze a specific reaction. Instances of this class describe a pairing between a modulating entity and a catalysis interaction.
Comment: A separate modulation instance should be created for each different catalysis instance that a physical entity may modulate and for each different physical entity that may modulate a catalysis instance. A typical modulation instance has a small molecule as the controller entity and a catalysis instance as the controlled entity.
Examples: Allosteric activation and competitive inhibition of an enzyme's ability to catalyze a specific reaction.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface modulation extends fr.curie.BiNoM.pathways.biopax.control, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#modulation");
	





	/**
	 * Gets the 'CONTROLLED' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.catalysis}
	 * @see			#CONTROLLEDProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.catalysis getCONTROLLED_ascatalysis() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'CONTROLLED' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.catalysis}
	 * @see			#CONTROLLEDProperty
	 */
	public void setCONTROLLED(fr.curie.BiNoM.pathways.biopax.catalysis CONTROLLED) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'CONTROLLED' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.catalysis}, the created value
	 * @see			#CONTROLLEDProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.catalysis setCONTROLLED_ascatalysis() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'CONTROLLED' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.catalysis} with the factory.
	 * and calling setCONTROLLED(fr.curie.BiNoM.pathways.biopax.catalysis CONTROLLED)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#catalysis.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.catalysis}, the newly created value
	 * @see			#CONTROLLEDProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.catalysis setCONTROLLED_ascatalysis(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}