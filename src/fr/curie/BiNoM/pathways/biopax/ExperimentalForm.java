

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for ExperimentalForm ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#ExperimentalForm)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: The form of a physical entity in a particular experiment, as it may be modified for purposes of experimental design.
Examples: A His-tagged protein in a binding assay. A protein can be tagged by multiple tags, so can have more than 1 experimental form type terms^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface ExperimentalForm extends fr.curie.BiNoM.pathways.biopax.UtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#ExperimentalForm");
	

	/**
	 * The Jena Property for experimentalFeature 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#experimentalFeature)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : A feature of the experimental form of the participant of the interaction, such as a protein tag. It is not expected to occur in vivo or be necessary for the interaction.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property experimentalFeatureProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#experimentalFeature");


	/**
	 * The Jena Property for experimentalFormDescription 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#experimentalFormDescription)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Descriptor of this experimental form from a controlled vocabulary.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property experimentalFormDescriptionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#experimentalFormDescription");


	/**
	 * The Jena Property for experimentalFormEntity 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#experimentalFormEntity)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The gene or physical entity that this experimental form describes.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property experimentalFormEntityProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#experimentalFormEntity");






	/**
	 * Get an Iterator the 'experimentalFeature' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.EntityFeature}
	 * @see			#experimentalFeatureProperty
	 */
	public java.util.Iterator getExperimentalFeature() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'experimentalFeature' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} to add
	 * @see			#experimentalFeatureProperty
	 */
	public void addExperimentalFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature experimentalFeature) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'experimentalFeature' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} created
	 * @see			#experimentalFeatureProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addExperimentalFeature() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'experimentalFeature' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} with the factory
	 * and calling addExperimentalFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature experimentalFeature)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#EntityFeature.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#experimentalFeatureProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addExperimentalFeature(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'experimentalFeature' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} to remove
	 * @see			#experimentalFeatureProperty
	 */
	public void removeExperimentalFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature experimentalFeature) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'experimentalFormDescription' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary}
	 * @see			#experimentalFormDescriptionProperty
	 */
	public java.util.Iterator getExperimentalFormDescription() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'experimentalFormDescription' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary} to add
	 * @see			#experimentalFormDescriptionProperty
	 */
	public void addExperimentalFormDescription(fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary experimentalFormDescription) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'experimentalFormDescription' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary} created
	 * @see			#experimentalFormDescriptionProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary addExperimentalFormDescription() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'experimentalFormDescription' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary} with the factory
	 * and calling addExperimentalFormDescription(fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary experimentalFormDescription)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#ExperimentalFormVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#experimentalFormDescriptionProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary addExperimentalFormDescription(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'experimentalFormDescription' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary} to remove
	 * @see			#experimentalFormDescriptionProperty
	 */
	public void removeExperimentalFormDescription(fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary experimentalFormDescription) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'experimentalFormEntity' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Gene}
	 * @see			#experimentalFormEntityProperty
	 */
	public java.util.Iterator getExperimentalFormEntity_asGene() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'experimentalFormEntity' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Gene} to add
	 * @see			#experimentalFormEntityProperty
	 */
	public void addExperimentalFormEntity(fr.curie.BiNoM.pathways.biopax.Gene experimentalFormEntity) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'experimentalFormEntity' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Gene} created
	 * @see			#experimentalFormEntityProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Gene addExperimentalFormEntity_asGene() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'experimentalFormEntity' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Gene} with the factory
	 * and calling addExperimentalFormEntity(fr.curie.BiNoM.pathways.biopax.Gene experimentalFormEntity)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Gene.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#experimentalFormEntityProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Gene addExperimentalFormEntity_asGene(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'experimentalFormEntity' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Gene} to remove
	 * @see			#experimentalFormEntityProperty
	 */
	public void removeExperimentalFormEntity(fr.curie.BiNoM.pathways.biopax.Gene experimentalFormEntity) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'experimentalFormEntity' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity}
	 * @see			#experimentalFormEntityProperty
	 */
	public java.util.Iterator getExperimentalFormEntity_asPhysicalEntity() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'experimentalFormEntity' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to add
	 * @see			#experimentalFormEntityProperty
	 */
	public void addExperimentalFormEntity(fr.curie.BiNoM.pathways.biopax.PhysicalEntity experimentalFormEntity) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'experimentalFormEntity' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} created
	 * @see			#experimentalFormEntityProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addExperimentalFormEntity_asPhysicalEntity() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'experimentalFormEntity' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} with the factory
	 * and calling addExperimentalFormEntity(fr.curie.BiNoM.pathways.biopax.PhysicalEntity experimentalFormEntity)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#PhysicalEntity.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#experimentalFormEntityProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addExperimentalFormEntity_asPhysicalEntity(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'experimentalFormEntity' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to remove
	 * @see			#experimentalFormEntityProperty
	 */
	public void removeExperimentalFormEntity(fr.curie.BiNoM.pathways.biopax.PhysicalEntity experimentalFormEntity) throws com.ibm.adtech.jastor.JastorException;
		
}