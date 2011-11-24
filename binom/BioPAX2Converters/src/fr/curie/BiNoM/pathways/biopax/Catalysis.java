

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Catalysis ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Catalysis)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A control interaction in which a physical entity (a catalyst) increases the rate of a conversion interaction by lowering its activation energy. Instances of this class describe a pairing between a catalyzing entity and a catalyzed conversion.
Rationale: Catalysis, theoretically, is always bidirectional since it acts by lowering the activation energy. Physiologically, however, it can have a direction because of the concentration of the participants. For example, the oxidative decarboxylation catalyzed by Isocitrate dehydrogenase always happens in one direction under physiological conditions since the produced carbon dioxide is constantly removed from the system.
   
Usage: A separate catalysis instance should be created for each different conversion that a physicalEntity may catalyze and for each different physicalEntity that may catalyze a conversion. For example, a bifunctional enzyme that catalyzes two different biochemical reactions would be linked to each of those biochemical reactions by two separate instances of the catalysis class. Also, catalysis reactions from multiple different organisms could be linked to the same generic biochemical reaction (a biochemical reaction is generic if it only includes small molecules). Generally, the enzyme catalyzing a conversion is known and the use of this class is obvious, however, in the cases where a catalyzed reaction is known to occur but the enzyme is not known, a catalysis instance can be created without a controller specified.
Synonyms: facilitation, acceleration.
Examples: The catalysis of a biochemical reaction by an enzyme, the enabling of a transport interaction by a membrane pore complex, and the facilitation of a complex assembly by a scaffold protein. Hexokinase -> (The "Glucose + ATP -> Glucose-6-phosphate +ADP" reaction). A plasma membrane Na+/K+ ATPase is an active transporter (antiport pump) using the energy of ATP to pump Na+ out of the cell and K+ in. Na+ from cytoplasm to extracellular space would be described in a transport instance. K+ from extracellular space to cytoplasm would be described in a transport instance. The ATPase pump would be stored in a catalysis instance controlling each of the above transport instances. A biochemical reaction that does not occur by itself under physiological conditions, but has been observed to occur in the presence of cell extract, likely via one or more unknown enzymes present in the extract, would be stored in the CONTROLLED property, with the CONTROLLER property empty.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface Catalysis extends fr.curie.BiNoM.pathways.biopax.Control, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#Catalysis");
	

	/**
	 * The Jena Property for catalysisDirection 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#catalysisDirection)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : This property represents the direction of this catalysis under all
physiological conditions if there is one.

Note that chemically a catalyst will increase the rate of the reaction
in both directions. In biology, however, there are cases where the
enzyme is expressed only when the controlled bidirectional conversion is
on one side of the chemical equilibrium [todo : example]. If that is the
case and the controller, under biological conditions, is always
catalyzing the conversion in one direction then this fact can be
captured using this property. If the enzyme is active for both
directions, or the conversion is not bidirectional, this property should
be left empty.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property catalysisDirectionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#catalysisDirection");


	/**
	 * The Jena Property for cofactor 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#cofactor)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Any cofactor(s) or coenzyme(s) required for catalysis of the conversion by the enzyme. COFACTOR is a sub-property of PARTICIPANTS.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property cofactorProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#cofactor");






	/**
	 * Gets the 'controlled' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Conversion}
	 * @see			#controlledProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Conversion getControlled_asConversion() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'controlled' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.Conversion}
	 * @see			#controlledProperty
	 */
	public void setControlled(fr.curie.BiNoM.pathways.biopax.Conversion controlled) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'controlled' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Conversion}, the created value
	 * @see			#controlledProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.Conversion setControlled_asConversion() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'controlled' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Conversion} with the factory.
	 * and calling setControlled(fr.curie.BiNoM.pathways.biopax.Conversion controlled)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Conversion.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Conversion}, the newly created value
	 * @see			#controlledProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Conversion setControlled_asConversion(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'catalysisDirection' property value
	 * @return		{@link java.lang.String}
	 * @see			#catalysisDirectionProperty
	 */
	public java.lang.String getCatalysisDirection() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'catalysisDirection' property value
	 * @param		{@link java.lang.String}
	 * @see			#catalysisDirectionProperty
	 */
	public void setCatalysisDirection(java.lang.String catalysisDirection) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'cofactor' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity}
	 * @see			#cofactorProperty
	 */
	public java.util.Iterator getCofactor() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'cofactor' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to add
	 * @see			#cofactorProperty
	 */
	public void addCofactor(fr.curie.BiNoM.pathways.biopax.PhysicalEntity cofactor) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'cofactor' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} created
	 * @see			#cofactorProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addCofactor() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'cofactor' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} with the factory
	 * and calling addCofactor(fr.curie.BiNoM.pathways.biopax.PhysicalEntity cofactor)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#PhysicalEntity.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#cofactorProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addCofactor(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'cofactor' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to remove
	 * @see			#cofactorProperty
	 */
	public void removeCofactor(fr.curie.BiNoM.pathways.biopax.PhysicalEntity cofactor) throws com.ibm.adtech.jastor.JastorException;
		
}