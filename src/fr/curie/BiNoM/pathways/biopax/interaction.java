

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for interaction ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#interaction)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A single biological relationship between two or more entities. An interaction cannot be defined without the entities it relates.
Comment: Since it is a highly abstract class in the ontology, instances of the interaction class should never be created. Instead, more specific classes should be used. Currently this class only has subclasses that define physical interactions; later levels of BioPAX may define other types of interactions, such as genetic (e.g. synthetic lethal).
Naming rationale: A number of names were considered for this concept, including "process", "synthesis" and "relationship"; Interaction was chosen as it is understood by biologists in a biological context and is compatible with PSI-MI.
Examples: protein-protein interaction, biochemical reaction, enzyme catalysis^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface interaction extends fr.curie.BiNoM.pathways.biopax.entity, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#interaction");
	

	/**
	 * The Jena Property for PARTICIPANTS 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#PARTICIPANTS)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : This property lists the entities that participate in this interaction. For example, in a biochemical reaction, the participants are the union of the reactants and the products of the reaction. This property has a number of sub-properties, such as LEFT and RIGHT in the biochemicalInteraction class. Any participant listed in a sub-property will automatically be assumed to also be in PARTICIPANTS by a number of software systems, including Protégé, so this property should not contain any instances if there are instances contained in a sub-property.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property PARTICIPANTSProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#PARTICIPANTS");


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
	 * Get an Iterator the 'PARTICIPANTS' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.entity}
	 * @see			#PARTICIPANTSProperty
	 */
	public java.util.Iterator getPARTICIPANTS_asentity() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'PARTICIPANTS' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.entity} to add
	 * @see			#PARTICIPANTSProperty
	 */
	public void addPARTICIPANTS(fr.curie.BiNoM.pathways.biopax.entity PARTICIPANTS) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'PARTICIPANTS' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.entity} created
	 * @see			#PARTICIPANTSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.entity addPARTICIPANTS_asentity() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'PARTICIPANTS' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.entity} with the factory
	 * and calling addPARTICIPANTS(fr.curie.BiNoM.pathways.biopax.entity PARTICIPANTS)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#entity.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#PARTICIPANTSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.entity addPARTICIPANTS_asentity(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'PARTICIPANTS' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.entity} to remove
	 * @see			#PARTICIPANTSProperty
	 */
	public void removePARTICIPANTS(fr.curie.BiNoM.pathways.biopax.entity PARTICIPANTS) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'PARTICIPANTS' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant}
	 * @see			#PARTICIPANTSProperty
	 */
	public java.util.Iterator getPARTICIPANTS_asphysicalEntityParticipant() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'PARTICIPANTS' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} to add
	 * @see			#PARTICIPANTSProperty
	 */
	public void addPARTICIPANTS(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANTS) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'PARTICIPANTS' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} created
	 * @see			#PARTICIPANTSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addPARTICIPANTS_asphysicalEntityParticipant() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'PARTICIPANTS' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} with the factory
	 * and calling addPARTICIPANTS(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANTS)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#physicalEntityParticipant.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#PARTICIPANTSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addPARTICIPANTS_asphysicalEntityParticipant(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'PARTICIPANTS' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} to remove
	 * @see			#PARTICIPANTSProperty
	 */
	public void removePARTICIPANTS(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANTS) throws com.ibm.adtech.jastor.JastorException;
		
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
		
}