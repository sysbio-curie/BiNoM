

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for conversion ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#conversion)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: An interaction in which one or more entities is physically transformed into one or more other entities.
Comment: This class is designed to represent a simple, single-step transformation. Multi-step transformations, such as the conversion of glucose to pyruvate in the glycolysis pathway, should be represented as pathways, if known. Since it is a highly abstract class in the ontology, instances of the conversion class should never be created.
Examples: A biochemical reaction converts substrates to products, the process of complex assembly converts single molecules to a complex, transport converts entities in one compartment to the same entities in another compartment.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface conversion extends fr.curie.BiNoM.pathways.biopax.physicalInteraction, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#conversion");
	

	/**
	 * The Jena Property for LEFT 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#LEFT)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The participants on the left side of the conversion interaction. Since conversion interactions may proceed in either the left-to-right or right-to-left direction, occupants of the LEFT property may be either reactants or products. LEFT is a sub-property of PARTICIPANTS.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property LEFTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#LEFT");


	/**
	 * The Jena Property for SPONTANEOUS 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#SPONTANEOUS)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Specifies whether a conversion occurs spontaneously (i.e. uncatalyzed, under biological conditions) left-to-right, right-to-left, or not at all. If the spontaneity is not known, the SPONTANEOUS property should be left empty.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property SPONTANEOUSProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SPONTANEOUS");


	/**
	 * The Jena Property for RIGHT 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#RIGHT)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The participants on the right side of the conversion interaction. Since conversion interactions may proceed in either the left-to-right or right-to-left direction, occupants of the RIGHT property may be either reactants or products. RIGHT is a sub-property of PARTICIPANTS.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property RIGHTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#RIGHT");






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
	 * Get an Iterator the 'LEFT' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant}
	 * @see			#LEFTProperty
	 */
	public java.util.Iterator getLEFT() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'LEFT' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} to add
	 * @see			#LEFTProperty
	 */
	public void addLEFT(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant LEFT) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'LEFT' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} created
	 * @see			#LEFTProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addLEFT() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'LEFT' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} with the factory
	 * and calling addLEFT(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant LEFT)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#physicalEntityParticipant.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#LEFTProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addLEFT(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'LEFT' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} to remove
	 * @see			#LEFTProperty
	 */
	public void removeLEFT(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant LEFT) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Gets the 'SPONTANEOUS' property value
	 * @return		{@link java.lang.String}
	 * @see			#SPONTANEOUSProperty
	 */
	public java.lang.String getSPONTANEOUS() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'SPONTANEOUS' property value
	 * @param		{@link java.lang.String}
	 * @see			#SPONTANEOUSProperty
	 */
	public void setSPONTANEOUS(java.lang.String SPONTANEOUS) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'RIGHT' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant}
	 * @see			#RIGHTProperty
	 */
	public java.util.Iterator getRIGHT() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'RIGHT' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} to add
	 * @see			#RIGHTProperty
	 */
	public void addRIGHT(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant RIGHT) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'RIGHT' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} created
	 * @see			#RIGHTProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addRIGHT() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'RIGHT' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} with the factory
	 * and calling addRIGHT(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant RIGHT)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#physicalEntityParticipant.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#RIGHTProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addRIGHT(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'RIGHT' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} to remove
	 * @see			#RIGHTProperty
	 */
	public void removeRIGHT(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant RIGHT) throws com.ibm.adtech.jastor.JastorException;
		
}