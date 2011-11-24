

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for sequenceParticipant ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#sequenceParticipant)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A DNA, RNA or protein participant in an interaction.
Comment: See physicalEntityParticipant for more documentation.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface sequenceParticipant extends fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#sequenceParticipant");
	

	/**
	 * The Jena Property for SEQUENCE_DASH_FEATURE_DASH_LIST 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#SEQUENCE-FEATURE-LIST)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Sequence features relevant for the interaction, for example binding domains or modification sites. Warning: this property may be moved into a state class in Level 3.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property SEQUENCE_DASH_FEATURE_DASH_LISTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SEQUENCE-FEATURE-LIST");






	/**
	 * Get an Iterator the 'SEQUENCE_DASH_FEATURE_DASH_LIST' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.sequenceFeature}
	 * @see			#SEQUENCE_DASH_FEATURE_DASH_LISTProperty
	 */
	public java.util.Iterator getSEQUENCE_DASH_FEATURE_DASH_LIST() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'SEQUENCE_DASH_FEATURE_DASH_LIST' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.sequenceFeature} to add
	 * @see			#SEQUENCE_DASH_FEATURE_DASH_LISTProperty
	 */
	public void addSEQUENCE_DASH_FEATURE_DASH_LIST(fr.curie.BiNoM.pathways.biopax.sequenceFeature SEQUENCE_DASH_FEATURE_DASH_LIST) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'SEQUENCE_DASH_FEATURE_DASH_LIST' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.sequenceFeature} created
	 * @see			#SEQUENCE_DASH_FEATURE_DASH_LISTProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.sequenceFeature addSEQUENCE_DASH_FEATURE_DASH_LIST() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'SEQUENCE_DASH_FEATURE_DASH_LIST' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.sequenceFeature} with the factory
	 * and calling addSEQUENCE_DASH_FEATURE_DASH_LIST(fr.curie.BiNoM.pathways.biopax.sequenceFeature SEQUENCE_DASH_FEATURE_DASH_LIST)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#sequenceFeature.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#SEQUENCE_DASH_FEATURE_DASH_LISTProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.sequenceFeature addSEQUENCE_DASH_FEATURE_DASH_LIST(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'SEQUENCE_DASH_FEATURE_DASH_LIST' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.sequenceFeature} to remove
	 * @see			#SEQUENCE_DASH_FEATURE_DASH_LISTProperty
	 */
	public void removeSEQUENCE_DASH_FEATURE_DASH_LIST(fr.curie.BiNoM.pathways.biopax.sequenceFeature SEQUENCE_DASH_FEATURE_DASH_LIST) throws com.ibm.adtech.jastor.JastorException;
		
}