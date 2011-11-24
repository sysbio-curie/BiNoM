

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Stoichiometry ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Stoichiometry)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: Stoichiometric coefficient of a physical entity in the context of a conversion or complex.
Usage: For each participating element there must be 0 or 1 stoichiometry element. A non-existing stoichiometric element is treated as unknown.
This is an n-ary bridge for left, right and component properties. Relative stoichiometries ( e.g n, n+1) often used for describing polymerization is not supported.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface Stoichiometry extends fr.curie.BiNoM.pathways.biopax.UtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#Stoichiometry");
	

	/**
	 * The Jena Property for stoichiometricCoefficient 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#stoichiometricCoefficient)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Stoichiometric coefficient for one of the entities in an interaction or complex. This value can be any rational number. Generic values such as "n" or "n+1" should not be used - polymers are currently not covered.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property stoichiometricCoefficientProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#stoichiometricCoefficient");


	/**
	 * The Jena Property for physicalEntity 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#physicalEntity)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The physical entity to be annotated with stoichiometry.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property physicalEntityProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#physicalEntity");






	/**
	 * Gets the 'stoichiometricCoefficient' property value
	 * @return		{@link java.lang.Float}
	 * @see			#stoichiometricCoefficientProperty
	 */
	public java.lang.Float getStoichiometricCoefficient() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'stoichiometricCoefficient' property value
	 * @param		{@link java.lang.Float}
	 * @see			#stoichiometricCoefficientProperty
	 */
	public void setStoichiometricCoefficient(java.lang.Float stoichiometricCoefficient) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'physicalEntity' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity}
	 * @see			#physicalEntityProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity getPhysicalEntity() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'physicalEntity' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity}
	 * @see			#physicalEntityProperty
	 */
	public void setPhysicalEntity(fr.curie.BiNoM.pathways.biopax.PhysicalEntity physicalEntity) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'physicalEntity' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity}, the created value
	 * @see			#physicalEntityProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity setPhysicalEntity() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'physicalEntity' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} with the factory.
	 * and calling setPhysicalEntity(fr.curie.BiNoM.pathways.biopax.PhysicalEntity physicalEntity)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#PhysicalEntity.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity}, the newly created value
	 * @see			#physicalEntityProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity setPhysicalEntity(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}