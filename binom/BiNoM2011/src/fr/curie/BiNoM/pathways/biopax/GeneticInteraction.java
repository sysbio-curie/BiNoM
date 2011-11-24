

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for GeneticInteraction ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#GeneticInteraction)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition : Genetic interactions between genes occur when two genetic perturbations (e.g. mutations) have a combined phenotypic effect not caused by either perturbation alone. A gene participant in a genetic interaction represents the gene that is perturbed. Genetic interactions are not physical interactions but logical (AND) relationships. Their physical manifestations can be complex and span an arbitarily long duration. 

Rationale: Currently,  BioPAX provides a simple definition that can capture most genetic interactions described in the literature. In the future, if required, the definition can be extended to capture other logical relationships and different, participant specific phenotypes. 

Example: A synthetic lethal interaction occurs when cell growth is possible without either gene A OR B, but not without both gene A AND B. If you knock out A and B together, the cell will die.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface GeneticInteraction extends fr.curie.BiNoM.pathways.biopax.Interaction, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#GeneticInteraction");
	

	/**
	 * The Jena Property for interactionScore 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#interactionScore)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The score of an interaction e.g. a genetic interaction score.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property interactionScoreProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#interactionScore");


	/**
	 * The Jena Property for phenotype 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#phenotype)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The phenotype quality used to define this genetic interaction e.g. viability.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property phenotypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#phenotype");






	/**
	 * Get an Iterator the 'participant' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Gene}
	 * @see			#participantProperty
	 */
	public java.util.Iterator getParticipant_asGene() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'participant' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Gene} to add
	 * @see			#participantProperty
	 */
	public void addParticipant(fr.curie.BiNoM.pathways.biopax.Gene participant) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'participant' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Gene} created
	 * @see			#participantProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Gene addParticipant_asGene() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'participant' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Gene} with the factory
	 * and calling addParticipant(fr.curie.BiNoM.pathways.biopax.Gene participant)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Gene.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#participantProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Gene addParticipant_asGene(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'participant' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Gene} to remove
	 * @see			#participantProperty
	 */
	public void removeParticipant(fr.curie.BiNoM.pathways.biopax.Gene participant) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'interactionScore' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Score}
	 * @see			#interactionScoreProperty
	 */
	public java.util.Iterator getInteractionScore() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'interactionScore' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Score} to add
	 * @see			#interactionScoreProperty
	 */
	public void addInteractionScore(fr.curie.BiNoM.pathways.biopax.Score interactionScore) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'interactionScore' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Score} created
	 * @see			#interactionScoreProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Score addInteractionScore() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'interactionScore' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Score} with the factory
	 * and calling addInteractionScore(fr.curie.BiNoM.pathways.biopax.Score interactionScore)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Score.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#interactionScoreProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Score addInteractionScore(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'interactionScore' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Score} to remove
	 * @see			#interactionScoreProperty
	 */
	public void removeInteractionScore(fr.curie.BiNoM.pathways.biopax.Score interactionScore) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Gets the 'phenotype' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary}
	 * @see			#phenotypeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary getPhenotype() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'phenotype' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary}
	 * @see			#phenotypeProperty
	 */
	public void setPhenotype(fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary phenotype) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'phenotype' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary}, the created value
	 * @see			#phenotypeProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary setPhenotype() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'phenotype' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary} with the factory.
	 * and calling setPhenotype(fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary phenotype)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#PhenotypeVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary}, the newly created value
	 * @see			#phenotypeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary setPhenotype(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}