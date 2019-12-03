

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for pathwayStep ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#pathwayStep)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A step in a pathway.
Comment: Multiple interactions may occur in a pathway step, each should be listed in the STEP-INTERACTIONS property. Order relationships between pathway steps may be established with the NEXT-STEP slot. This order may not be temporally meaningful for specific steps, such as for a pathway loop or a reversible reaction, but represents a directed graph of step relationships that can be useful for describing the overall flow of a pathway, as may be useful in a pathway diagram.
Example: A metabolic pathway may contain a pathway step composed of one biochemical reaction (BR1) and one catalysis (CAT1) instance, where CAT1 describes the catalysis of BR1.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface pathwayStep extends fr.curie.BiNoM.pathways.biopax.utilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#pathwayStep");
	

	/**
	 * The Jena Property for NEXT_DASH_STEP 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#NEXT-STEP)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The next step(s) of the pathway.  Contains zero or more pathwayStep instances.  If there is no next step, this property is empty.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property NEXT_DASH_STEPProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#NEXT-STEP");


	/**
	 * The Jena Property for STEP_DASH_INTERACTIONS 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#STEP-INTERACTIONS)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The interactions that take place at this step of the pathway.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property STEP_DASH_INTERACTIONSProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#STEP-INTERACTIONS");






	/**
	 * Get an Iterator the 'NEXT_DASH_STEP' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.pathwayStep}
	 * @see			#NEXT_DASH_STEPProperty
	 */
	public java.util.Iterator getNEXT_DASH_STEP() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'NEXT_DASH_STEP' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.pathwayStep} to add
	 * @see			#NEXT_DASH_STEPProperty
	 */
	public void addNEXT_DASH_STEP(fr.curie.BiNoM.pathways.biopax.pathwayStep NEXT_DASH_STEP) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'NEXT_DASH_STEP' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.pathwayStep} created
	 * @see			#NEXT_DASH_STEPProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.pathwayStep addNEXT_DASH_STEP() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'NEXT_DASH_STEP' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.pathwayStep} with the factory
	 * and calling addNEXT_DASH_STEP(fr.curie.BiNoM.pathways.biopax.pathwayStep NEXT_DASH_STEP)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#pathwayStep.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#NEXT_DASH_STEPProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.pathwayStep addNEXT_DASH_STEP(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'NEXT_DASH_STEP' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.pathwayStep} to remove
	 * @see			#NEXT_DASH_STEPProperty
	 */
	public void removeNEXT_DASH_STEP(fr.curie.BiNoM.pathways.biopax.pathwayStep NEXT_DASH_STEP) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'STEP_DASH_INTERACTIONS' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.interaction}
	 * @see			#STEP_DASH_INTERACTIONSProperty
	 */
	public java.util.Iterator getSTEP_DASH_INTERACTIONS_asinteraction() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'STEP_DASH_INTERACTIONS' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.interaction} to add
	 * @see			#STEP_DASH_INTERACTIONSProperty
	 */
	public void addSTEP_DASH_INTERACTIONS(fr.curie.BiNoM.pathways.biopax.interaction STEP_DASH_INTERACTIONS) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'STEP_DASH_INTERACTIONS' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.interaction} created
	 * @see			#STEP_DASH_INTERACTIONSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.interaction addSTEP_DASH_INTERACTIONS_asinteraction() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'STEP_DASH_INTERACTIONS' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.interaction} with the factory
	 * and calling addSTEP_DASH_INTERACTIONS(fr.curie.BiNoM.pathways.biopax.interaction STEP_DASH_INTERACTIONS)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#interaction.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#STEP_DASH_INTERACTIONSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.interaction addSTEP_DASH_INTERACTIONS_asinteraction(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'STEP_DASH_INTERACTIONS' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.interaction} to remove
	 * @see			#STEP_DASH_INTERACTIONSProperty
	 */
	public void removeSTEP_DASH_INTERACTIONS(fr.curie.BiNoM.pathways.biopax.interaction STEP_DASH_INTERACTIONS) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'STEP_DASH_INTERACTIONS' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.pathway}
	 * @see			#STEP_DASH_INTERACTIONSProperty
	 */
	public java.util.Iterator getSTEP_DASH_INTERACTIONS_aspathway() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'STEP_DASH_INTERACTIONS' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.pathway} to add
	 * @see			#STEP_DASH_INTERACTIONSProperty
	 */
	public void addSTEP_DASH_INTERACTIONS(fr.curie.BiNoM.pathways.biopax.pathway STEP_DASH_INTERACTIONS) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'STEP_DASH_INTERACTIONS' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.pathway} created
	 * @see			#STEP_DASH_INTERACTIONSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.pathway addSTEP_DASH_INTERACTIONS_aspathway() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'STEP_DASH_INTERACTIONS' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.pathway} with the factory
	 * and calling addSTEP_DASH_INTERACTIONS(fr.curie.BiNoM.pathways.biopax.pathway STEP_DASH_INTERACTIONS)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#pathway.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#STEP_DASH_INTERACTIONSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.pathway addSTEP_DASH_INTERACTIONS_aspathway(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'STEP_DASH_INTERACTIONS' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.pathway} to remove
	 * @see			#STEP_DASH_INTERACTIONSProperty
	 */
	public void removeSTEP_DASH_INTERACTIONS(fr.curie.BiNoM.pathways.biopax.pathway STEP_DASH_INTERACTIONS) throws com.ibm.adtech.jastor.JastorException;
		
}