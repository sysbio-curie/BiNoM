

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Pathway ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Pathway)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A set or series of interactions, often forming a network, which biologists have found useful to group together for organizational, historic, biophysical or other reasons.

Usage: Pathways can be used for demarcating any subnetwork of a BioPAX model. It is also possible to define a pathway without specifying the interactions within the pathway. In this case, the pathway instance could consist simply of a name and could be treated as a 'black box'.  Pathways can also soverlap, i.e. a single interaction might belong to multiple pathways. Pathways can also contain sub-pathways. Pathways are continuants.

Synonyms: network, module, cascade,  
Examples: glycolysis, valine biosynthesis, EGFR signaling^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface Pathway extends fr.curie.BiNoM.pathways.biopax.Entity, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#Pathway");
	

	/**
	 * The Jena Property for pathwayOrder 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#pathwayOrder)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The ordering of components (interactions and pathways) in the context of this pathway. This is useful to specific circular or branched pathways or orderings when component biochemical reactions are normally reversible, but are directed in the context of this pathway.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property pathwayOrderProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#pathwayOrder");


	/**
	 * The Jena Property for pathwayComponent 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#pathwayComponent)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The set of interactions and/or pathwaySteps in this pathway/network. Each instance of the pathwayStep class defines: 1) a set of interactions that together define a particular step in the pathway, for example a catalysis instance and the conversion that it catalyzes; 2) an order relationship to one or more other pathway steps (via the NEXT-STEP property). Note: This ordering is not necessarily temporal - the order described may simply represent connectivity between adjacent steps. Temporal ordering information should only be inferred from the direction of each interaction.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property pathwayComponentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#pathwayComponent");


	/**
	 * The Jena Property for organism 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#organism)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : An organism, e.g. 'Homo sapiens'. This is the organism that the entity is found in. Pathways may not have an organism associated with them, for instance, reference pathways from KEGG. Sequence-based entities (DNA, protein, RNA) may contain an xref to a sequence database that contains organism information, in which case the information should be consistent with the value for ORGANISM.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property organismProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#organism");






	/**
	 * Get an Iterator the 'pathwayOrder' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.PathwayStep}
	 * @see			#pathwayOrderProperty
	 */
	public java.util.Iterator getPathwayOrder() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'pathwayOrder' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PathwayStep} to add
	 * @see			#pathwayOrderProperty
	 */
	public void addPathwayOrder(fr.curie.BiNoM.pathways.biopax.PathwayStep pathwayOrder) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'pathwayOrder' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.PathwayStep} created
	 * @see			#pathwayOrderProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PathwayStep addPathwayOrder() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'pathwayOrder' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.PathwayStep} with the factory
	 * and calling addPathwayOrder(fr.curie.BiNoM.pathways.biopax.PathwayStep pathwayOrder)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#PathwayStep.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#pathwayOrderProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PathwayStep addPathwayOrder(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'pathwayOrder' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PathwayStep} to remove
	 * @see			#pathwayOrderProperty
	 */
	public void removePathwayOrder(fr.curie.BiNoM.pathways.biopax.PathwayStep pathwayOrder) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'pathwayComponent' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Interaction}
	 * @see			#pathwayComponentProperty
	 */
	public java.util.Iterator getPathwayComponent_asInteraction() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'pathwayComponent' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Interaction} to add
	 * @see			#pathwayComponentProperty
	 */
	public void addPathwayComponent(fr.curie.BiNoM.pathways.biopax.Interaction pathwayComponent) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'pathwayComponent' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Interaction} created
	 * @see			#pathwayComponentProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Interaction addPathwayComponent_asInteraction() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'pathwayComponent' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Interaction} with the factory
	 * and calling addPathwayComponent(fr.curie.BiNoM.pathways.biopax.Interaction pathwayComponent)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Interaction.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#pathwayComponentProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Interaction addPathwayComponent_asInteraction(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'pathwayComponent' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Interaction} to remove
	 * @see			#pathwayComponentProperty
	 */
	public void removePathwayComponent(fr.curie.BiNoM.pathways.biopax.Interaction pathwayComponent) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'pathwayComponent' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Pathway}
	 * @see			#pathwayComponentProperty
	 */
	public java.util.Iterator getPathwayComponent_asPathway() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'pathwayComponent' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Pathway} to add
	 * @see			#pathwayComponentProperty
	 */
	public void addPathwayComponent(fr.curie.BiNoM.pathways.biopax.Pathway pathwayComponent) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'pathwayComponent' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Pathway} created
	 * @see			#pathwayComponentProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Pathway addPathwayComponent_asPathway() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'pathwayComponent' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Pathway} with the factory
	 * and calling addPathwayComponent(fr.curie.BiNoM.pathways.biopax.Pathway pathwayComponent)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Pathway.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#pathwayComponentProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Pathway addPathwayComponent_asPathway(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'pathwayComponent' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Pathway} to remove
	 * @see			#pathwayComponentProperty
	 */
	public void removePathwayComponent(fr.curie.BiNoM.pathways.biopax.Pathway pathwayComponent) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Gets the 'organism' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.BioSource}
	 * @see			#organismProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.BioSource getOrganism() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'organism' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.BioSource}
	 * @see			#organismProperty
	 */
	public void setOrganism(fr.curie.BiNoM.pathways.biopax.BioSource organism) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'organism' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.BioSource}, the created value
	 * @see			#organismProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.BioSource setOrganism() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'organism' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.BioSource} with the factory.
	 * and calling setOrganism(fr.curie.BiNoM.pathways.biopax.BioSource organism)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#BioSource.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.BioSource}, the newly created value
	 * @see			#organismProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.BioSource setOrganism(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}