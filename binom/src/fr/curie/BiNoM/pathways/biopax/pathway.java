

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for pathway ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#pathway)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A set or series of interactions, often forming a network, which biologists have found useful to group together for organizational, historic, biophysical or other reasons.
Comment: It is possible to define a pathway without specifying the interactions within the pathway. In this case, the pathway instance could consist simply of a name and could be treated as a 'black box'.
Synonyms: network
Examples: glycolysis, valine biosynthesis^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface pathway extends fr.curie.BiNoM.pathways.biopax.entity, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#pathway");
	

	/**
	 * The Jena Property for PATHWAY_DASH_COMPONENTS 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#PATHWAY-COMPONENTS)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The set of interactions and/or pathwaySteps in this pathway/network. Each instance of the pathwayStep class defines: 1) a set of interactions that together define a particular step in the pathway, for example a catalysis instance and the conversion that it catalyzes; 2) an order relationship to one or more other pathway steps (via the NEXT-STEP property). Note: This ordering is not necessarily temporal - the order described may simply represent connectivity between adjacent steps. Temporal ordering information should only be inferred from the direction of each interaction.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property PATHWAY_DASH_COMPONENTSProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#PATHWAY-COMPONENTS");


	/**
	 * The Jena Property for EVIDENCE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#EVIDENCE)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Scientific evidence supporting the existence of the entity as described.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property EVIDENCEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#EVIDENCE");


	/**
	 * The Jena Property for ORGANISM 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#ORGANISM)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : An organism, e.g. 'Homo sapiens'. This is the organism that the entity is found in. Pathways may not have an organism associated with them, for instance, reference pathways from KEGG. Sequence-based entities (DNA, protein, RNA) may contain an xref to a sequence database that contains organism information, in which case the information should be consistent with the value for ORGANISM.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property ORGANISMProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#ORGANISM");






	/**
	 * Get an Iterator the 'PATHWAY_DASH_COMPONENTS' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.interaction}
	 * @see			#PATHWAY_DASH_COMPONENTSProperty
	 */
	public java.util.Iterator getPATHWAY_DASH_COMPONENTS_asinteraction() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'PATHWAY_DASH_COMPONENTS' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.interaction} to add
	 * @see			#PATHWAY_DASH_COMPONENTSProperty
	 */
	public void addPATHWAY_DASH_COMPONENTS(fr.curie.BiNoM.pathways.biopax.interaction PATHWAY_DASH_COMPONENTS) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'PATHWAY_DASH_COMPONENTS' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.interaction} created
	 * @see			#PATHWAY_DASH_COMPONENTSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.interaction addPATHWAY_DASH_COMPONENTS_asinteraction() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'PATHWAY_DASH_COMPONENTS' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.interaction} with the factory
	 * and calling addPATHWAY_DASH_COMPONENTS(fr.curie.BiNoM.pathways.biopax.interaction PATHWAY_DASH_COMPONENTS)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#interaction.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#PATHWAY_DASH_COMPONENTSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.interaction addPATHWAY_DASH_COMPONENTS_asinteraction(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'PATHWAY_DASH_COMPONENTS' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.interaction} to remove
	 * @see			#PATHWAY_DASH_COMPONENTSProperty
	 */
	public void removePATHWAY_DASH_COMPONENTS(fr.curie.BiNoM.pathways.biopax.interaction PATHWAY_DASH_COMPONENTS) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'PATHWAY_DASH_COMPONENTS' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.pathwayStep}
	 * @see			#PATHWAY_DASH_COMPONENTSProperty
	 */
	public java.util.Iterator getPATHWAY_DASH_COMPONENTS_aspathwayStep() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'PATHWAY_DASH_COMPONENTS' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.pathwayStep} to add
	 * @see			#PATHWAY_DASH_COMPONENTSProperty
	 */
	public void addPATHWAY_DASH_COMPONENTS(fr.curie.BiNoM.pathways.biopax.pathwayStep PATHWAY_DASH_COMPONENTS) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'PATHWAY_DASH_COMPONENTS' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.pathwayStep} created
	 * @see			#PATHWAY_DASH_COMPONENTSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.pathwayStep addPATHWAY_DASH_COMPONENTS_aspathwayStep() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'PATHWAY_DASH_COMPONENTS' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.pathwayStep} with the factory
	 * and calling addPATHWAY_DASH_COMPONENTS(fr.curie.BiNoM.pathways.biopax.pathwayStep PATHWAY_DASH_COMPONENTS)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#pathwayStep.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#PATHWAY_DASH_COMPONENTSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.pathwayStep addPATHWAY_DASH_COMPONENTS_aspathwayStep(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'PATHWAY_DASH_COMPONENTS' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.pathwayStep} to remove
	 * @see			#PATHWAY_DASH_COMPONENTSProperty
	 */
	public void removePATHWAY_DASH_COMPONENTS(fr.curie.BiNoM.pathways.biopax.pathwayStep PATHWAY_DASH_COMPONENTS) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'PATHWAY_DASH_COMPONENTS' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.pathway}
	 * @see			#PATHWAY_DASH_COMPONENTSProperty
	 */
	public java.util.Iterator getPATHWAY_DASH_COMPONENTS_aspathway() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'PATHWAY_DASH_COMPONENTS' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.pathway} to add
	 * @see			#PATHWAY_DASH_COMPONENTSProperty
	 */
	public void addPATHWAY_DASH_COMPONENTS(fr.curie.BiNoM.pathways.biopax.pathway PATHWAY_DASH_COMPONENTS) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'PATHWAY_DASH_COMPONENTS' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.pathway} created
	 * @see			#PATHWAY_DASH_COMPONENTSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.pathway addPATHWAY_DASH_COMPONENTS_aspathway() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'PATHWAY_DASH_COMPONENTS' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.pathway} with the factory
	 * and calling addPATHWAY_DASH_COMPONENTS(fr.curie.BiNoM.pathways.biopax.pathway PATHWAY_DASH_COMPONENTS)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#pathway.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#PATHWAY_DASH_COMPONENTSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.pathway addPATHWAY_DASH_COMPONENTS_aspathway(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'PATHWAY_DASH_COMPONENTS' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.pathway} to remove
	 * @see			#PATHWAY_DASH_COMPONENTSProperty
	 */
	public void removePATHWAY_DASH_COMPONENTS(fr.curie.BiNoM.pathways.biopax.pathway PATHWAY_DASH_COMPONENTS) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'EVIDENCE' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.evidence}
	 * @see			#EVIDENCEProperty
	 */
	public java.util.Iterator getEVIDENCE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'EVIDENCE' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.evidence} to add
	 * @see			#EVIDENCEProperty
	 */
	public void addEVIDENCE(fr.curie.BiNoM.pathways.biopax.evidence EVIDENCE) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'EVIDENCE' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.evidence} created
	 * @see			#EVIDENCEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.evidence addEVIDENCE() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'EVIDENCE' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.evidence} with the factory
	 * and calling addEVIDENCE(fr.curie.BiNoM.pathways.biopax.evidence EVIDENCE)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#evidence.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#EVIDENCEProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.evidence addEVIDENCE(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'EVIDENCE' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.evidence} to remove
	 * @see			#EVIDENCEProperty
	 */
	public void removeEVIDENCE(fr.curie.BiNoM.pathways.biopax.evidence EVIDENCE) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Gets the 'ORGANISM' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.bioSource}
	 * @see			#ORGANISMProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.bioSource getORGANISM() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'ORGANISM' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.bioSource}
	 * @see			#ORGANISMProperty
	 */
	public void setORGANISM(fr.curie.BiNoM.pathways.biopax.bioSource ORGANISM) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'ORGANISM' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.bioSource}, the created value
	 * @see			#ORGANISMProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.bioSource setORGANISM() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'ORGANISM' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.bioSource} with the factory.
	 * and calling setORGANISM(fr.curie.BiNoM.pathways.biopax.bioSource ORGANISM)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#bioSource.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.bioSource}, the newly created value
	 * @see			#ORGANISMProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.bioSource setORGANISM(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}