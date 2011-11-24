

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for PathwayStep ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#PathwayStep)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A step in an ordered pathway.
Rationale: Some pathways can have a temporal order. For example,  if the pathway boundaries are based on a perturbation phenotype link, the pathway might start with the perturbing agent and end at gene expression leading to the observed changes. Pathway steps can represent directed compound graphs.
Usage: Multiple interactions may occur in a pathway step, each should be listed in the stepProcess property. Order relationships between pathway steps may be established with the nextStep slot. If the reaction contained in the step is a reversible biochemical reaction but physiologically has a direction in the context of this pathway, use the subclass BiochemicalPathwayStep.

Example: A metabolic pathway may contain a pathway step composed of one biochemical reaction (BR1) and one catalysis (CAT1) instance, where CAT1 describes the catalysis of BR1. The M phase of the cell cycle, defined as a pathway, precedes the G1 phase, also defined as a pathway.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface PathwayStep extends fr.curie.BiNoM.pathways.biopax.UtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#PathwayStep");
	

	/**
	 * The Jena Property for nextStep 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#nextStep)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The next step(s) of the pathway.  Contains zero or more pathwayStep instances.  If there is no next step, this property is empty. Multiple pathwayStep instances indicate pathway branching.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property nextStepProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#nextStep");


	/**
	 * The Jena Property for stepProcess 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#stepProcess)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : An interaction or a pathway that are a part of this pathway step.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property stepProcessProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#stepProcess");


	/**
	 * The Jena Property for evidence 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#evidence)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Scientific evidence supporting the existence of the entity as described.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property evidenceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#evidence");






	/**
	 * Get an Iterator the 'nextStep' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.PathwayStep}
	 * @see			#nextStepProperty
	 */
	public java.util.Iterator getNextStep() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'nextStep' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PathwayStep} to add
	 * @see			#nextStepProperty
	 */
	public void addNextStep(fr.curie.BiNoM.pathways.biopax.PathwayStep nextStep) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'nextStep' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.PathwayStep} created
	 * @see			#nextStepProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PathwayStep addNextStep() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'nextStep' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.PathwayStep} with the factory
	 * and calling addNextStep(fr.curie.BiNoM.pathways.biopax.PathwayStep nextStep)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#PathwayStep.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#nextStepProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PathwayStep addNextStep(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'nextStep' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PathwayStep} to remove
	 * @see			#nextStepProperty
	 */
	public void removeNextStep(fr.curie.BiNoM.pathways.biopax.PathwayStep nextStep) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'stepProcess' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Interaction}
	 * @see			#stepProcessProperty
	 */
	public java.util.Iterator getStepProcess_asInteraction() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'stepProcess' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Interaction} to add
	 * @see			#stepProcessProperty
	 */
	public void addStepProcess(fr.curie.BiNoM.pathways.biopax.Interaction stepProcess) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'stepProcess' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Interaction} created
	 * @see			#stepProcessProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Interaction addStepProcess_asInteraction() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'stepProcess' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Interaction} with the factory
	 * and calling addStepProcess(fr.curie.BiNoM.pathways.biopax.Interaction stepProcess)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Interaction.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#stepProcessProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Interaction addStepProcess_asInteraction(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'stepProcess' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Interaction} to remove
	 * @see			#stepProcessProperty
	 */
	public void removeStepProcess(fr.curie.BiNoM.pathways.biopax.Interaction stepProcess) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'stepProcess' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Pathway}
	 * @see			#stepProcessProperty
	 */
	public java.util.Iterator getStepProcess_asPathway() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'stepProcess' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Pathway} to add
	 * @see			#stepProcessProperty
	 */
	public void addStepProcess(fr.curie.BiNoM.pathways.biopax.Pathway stepProcess) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'stepProcess' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Pathway} created
	 * @see			#stepProcessProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Pathway addStepProcess_asPathway() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'stepProcess' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Pathway} with the factory
	 * and calling addStepProcess(fr.curie.BiNoM.pathways.biopax.Pathway stepProcess)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Pathway.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#stepProcessProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Pathway addStepProcess_asPathway(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'stepProcess' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Pathway} to remove
	 * @see			#stepProcessProperty
	 */
	public void removeStepProcess(fr.curie.BiNoM.pathways.biopax.Pathway stepProcess) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'evidence' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Evidence}
	 * @see			#evidenceProperty
	 */
	public java.util.Iterator getEvidence() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'evidence' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Evidence} to add
	 * @see			#evidenceProperty
	 */
	public void addEvidence(fr.curie.BiNoM.pathways.biopax.Evidence evidence) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'evidence' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Evidence} created
	 * @see			#evidenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Evidence addEvidence() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'evidence' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Evidence} with the factory
	 * and calling addEvidence(fr.curie.BiNoM.pathways.biopax.Evidence evidence)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Evidence.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#evidenceProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Evidence addEvidence(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'evidence' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Evidence} to remove
	 * @see			#evidenceProperty
	 */
	public void removeEvidence(fr.curie.BiNoM.pathways.biopax.Evidence evidence) throws com.ibm.adtech.jastor.JastorException;
		
}