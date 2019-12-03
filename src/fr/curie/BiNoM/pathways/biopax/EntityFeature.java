

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for EntityFeature ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#EntityFeature)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Description: A characteristic of a physical entity that can change while the entity still retains its biological identity. 

Rationale: Two phosphorylated forms of a protein are strictly speaking different chemical  molecules. It is, however, standard in biology to treat them as different states of the same entity, where the entity is loosely defined based on sequence. Entity Feature class and its subclassses captures these variable characteristics. A Physical Entity in BioPAX represents a pool of  molecules rather than an individual molecule. This is a notion imported from chemistry( See PhysicalEntity). Pools are defined by a set of Entity Features in the sense that a single molecule must have all of the features in the set in order to be considered a member of the pool. Since it is impossible to list and experimentally test all potential features for an  entity, features that are not listed in the selection criteria is neglected Pools can also be defined by the converse by specifying features  that are known to NOT exist in a specific context. As DNA, RNA and Proteins can be hierarchicaly organized into families based on sequence homology so can entity features. The memberFeature property allows capturing such hierarchical classifications among entity features.


Usage: Subclasses of entity feature describe most common biological instances and should be preferred whenever possible. One common usecase for instantiating  entity feature is, for describing active/inactive states of proteins where more specific feature information is not available.  

Examples: Open/close conformational state of channel proteins, "active"/"inactive" states, excited states of photoreactive groups.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface EntityFeature extends fr.curie.BiNoM.pathways.biopax.UtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#EntityFeature");
	

	/**
	 * The Jena Property for memberFeature 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#memberFeature)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : An entity feature  that belongs to this homology grouping. Example: a homologous phosphorylation site across a protein family.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property memberFeatureProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#memberFeature");


	/**
	 * The Jena Property for featureLocation 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#featureLocation)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Location of the feature on the sequence of the interactor. One feature may have more than one location, used e.g. for features which involve sequence positions close in the folded, three-dimensional state of a protein, but non-continuous along the sequence.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property featureLocationProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#featureLocation");


	/**
	 * The Jena Property for featureLocationType 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#featureLocationType)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : A controlled vocabulary term describing the type of the sequence location such as C-Terminal or SH2 Domain.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property featureLocationTypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#featureLocationType");


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
	 * Get an Iterator the 'memberFeature' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.EntityFeature}
	 * @see			#memberFeatureProperty
	 */
	public java.util.Iterator getMemberFeature() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'memberFeature' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} to add
	 * @see			#memberFeatureProperty
	 */
	public void addMemberFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature memberFeature) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'memberFeature' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} created
	 * @see			#memberFeatureProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addMemberFeature() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'memberFeature' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} with the factory
	 * and calling addMemberFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature memberFeature)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#EntityFeature.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#memberFeatureProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addMemberFeature(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'memberFeature' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.EntityFeature} to remove
	 * @see			#memberFeatureProperty
	 */
	public void removeMemberFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature memberFeature) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'featureLocation' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.SequenceLocation}
	 * @see			#featureLocationProperty
	 */
	public java.util.Iterator getFeatureLocation() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'featureLocation' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.SequenceLocation} to add
	 * @see			#featureLocationProperty
	 */
	public void addFeatureLocation(fr.curie.BiNoM.pathways.biopax.SequenceLocation featureLocation) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'featureLocation' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.SequenceLocation} created
	 * @see			#featureLocationProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SequenceLocation addFeatureLocation() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'featureLocation' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.SequenceLocation} with the factory
	 * and calling addFeatureLocation(fr.curie.BiNoM.pathways.biopax.SequenceLocation featureLocation)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#SequenceLocation.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#featureLocationProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SequenceLocation addFeatureLocation(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'featureLocation' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.SequenceLocation} to remove
	 * @see			#featureLocationProperty
	 */
	public void removeFeatureLocation(fr.curie.BiNoM.pathways.biopax.SequenceLocation featureLocation) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'featureLocationType' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary}
	 * @see			#featureLocationTypeProperty
	 */
	public java.util.Iterator getFeatureLocationType() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'featureLocationType' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary} to add
	 * @see			#featureLocationTypeProperty
	 */
	public void addFeatureLocationType(fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary featureLocationType) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'featureLocationType' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary} created
	 * @see			#featureLocationTypeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary addFeatureLocationType() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'featureLocationType' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary} with the factory
	 * and calling addFeatureLocationType(fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary featureLocationType)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#SequenceRegionVocabulary.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#featureLocationTypeProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary addFeatureLocationType(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'featureLocationType' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary} to remove
	 * @see			#featureLocationTypeProperty
	 */
	public void removeFeatureLocationType(fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary featureLocationType) throws com.ibm.adtech.jastor.JastorException;
		
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