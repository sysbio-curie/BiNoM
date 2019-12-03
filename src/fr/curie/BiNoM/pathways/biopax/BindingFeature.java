

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for BindingFeature ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#BindingFeature)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition : An entity feature that represent the bound state of a physical entity. A pair of binding features represents a bond. 

Rationale: A physical entity in a molecular complex is considered as a new state of an entity as it is structurally and functionally different. Binding features provide facilities for describing these states. Similar to other features, a molecule can have bound and not-bound states. 

Usage: Typically, binding features are present in pairs, each describing the binding characteristic for one of the interacting physical entities. One exception is using a binding feature with no paired feature to describe any potential binding. For example, an unbound receptor can be described by using a "not-feature" property with an unpaired binding feature as its value.  BindingSiteType and featureLocation allows annotating the binding location.

IntraMolecular property should be set to "true" if the bond links two parts of the same molecule. A pair of binding features are still used where they are owned by the same physical entity. 

If the binding is due to the covalent interactions, for example in the case of lipoproteins, CovalentBindingFeature subclass should be used instead of this class.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface BindingFeature extends fr.curie.BiNoM.pathways.biopax.EntityFeature, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#BindingFeature");
	

	/**
	 * The Jena Property for intraMolecular 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#intraMolecular)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : This flag represents whether the binding feature is within the same molecule or not.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property intraMolecularProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#intraMolecular");


	/**
	 * The Jena Property for bindsTo 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#bindsTo)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : A binding feature represents a "half" of the bond between two entities. This property points to another binding feature which represents the other half. The bond can be covalent or non-covalent.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property bindsToProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#bindsTo");






	/**
	 * Gets the 'intraMolecular' property value
	 * @return		{@link java.lang.Boolean}
	 * @see			#intraMolecularProperty
	 */
	public java.lang.Boolean getIntraMolecular() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'intraMolecular' property value
	 * @param		{@link java.lang.Boolean}
	 * @see			#intraMolecularProperty
	 */
	public void setIntraMolecular(java.lang.Boolean intraMolecular) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'bindsTo' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.BindingFeature}
	 * @see			#bindsToProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.BindingFeature getBindsTo() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'bindsTo' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.BindingFeature}
	 * @see			#bindsToProperty
	 */
	public void setBindsTo(fr.curie.BiNoM.pathways.biopax.BindingFeature bindsTo) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'bindsTo' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.BindingFeature}, the created value
	 * @see			#bindsToProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.BindingFeature setBindsTo() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'bindsTo' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.BindingFeature} with the factory.
	 * and calling setBindsTo(fr.curie.BiNoM.pathways.biopax.BindingFeature bindsTo)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#BindingFeature.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.BindingFeature}, the newly created value
	 * @see			#bindsToProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.BindingFeature setBindsTo(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}