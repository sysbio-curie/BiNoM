

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for TemplateReaction ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#TemplateReaction)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definiton: An interaction where a macromolecule is polymerized from a 
    template macromolecule. 

Rationale: This is an abstraction over multiple (not explicitly stated) biochemical 
    reactions. The ubiquitous molecules (NTP and amino acids) consumed are also usually
    omitted. Template reaction is non-stoichiometric, does not obey law of 
    mass conservation and temporally non-atomic. It, however, provides a 
    mechanism to capture processes that are central to all living organisms.  

Usage: Regulation of TemplateReaction, e.g. via a transcription factor can be 
    captured using TemplateReactionRegulation. TemplateReaction can also be 
    indirect  for example, it is not necessary to represent intermediary mRNA 
    for describing expression of a protein. It was decided to not subclass 
    TemplateReaction to subtypes such as transcription of translation for the 
    sake of  simplicity. If needed these subclasses can be added in the 
    future. 

Examples: Transcription, translation, replication, reverse transcription. E.g. 
    DNA to RNA is transcription, RNA to protein is translation and DNA to 
    protein is protein expression from DNA.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface TemplateReaction extends fr.curie.BiNoM.pathways.biopax.Interaction, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#TemplateReaction");
	

	/**
	 * The Jena Property for templateDirection 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#templateDirection)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The direction of the template reaction on the template.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property templateDirectionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#templateDirection");


	/**
	 * The Jena Property for product 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#product)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The product of a template reaction.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property productProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#product");


	/**
	 * The Jena Property for template 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#template)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The template molecule that is used in this template reaction.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property templateProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#template");






	/**
	 * Get an Iterator the 'participant' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity}
	 * @see			#participantProperty
	 */
	public java.util.Iterator getParticipant_asPhysicalEntity() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'participant' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to add
	 * @see			#participantProperty
	 */
	public void addParticipant(fr.curie.BiNoM.pathways.biopax.PhysicalEntity participant) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'participant' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} created
	 * @see			#participantProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addParticipant_asPhysicalEntity() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'participant' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} with the factory
	 * and calling addParticipant(fr.curie.BiNoM.pathways.biopax.PhysicalEntity participant)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#PhysicalEntity.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#participantProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addParticipant_asPhysicalEntity(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'participant' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.PhysicalEntity} to remove
	 * @see			#participantProperty
	 */
	public void removeParticipant(fr.curie.BiNoM.pathways.biopax.PhysicalEntity participant) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Gets the 'templateDirection' property value
	 * @return		{@link java.lang.String}
	 * @see			#templateDirectionProperty
	 */
	public java.lang.String getTemplateDirection() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'templateDirection' property value
	 * @param		{@link java.lang.String}
	 * @see			#templateDirectionProperty
	 */
	public void setTemplateDirection(java.lang.String templateDirection) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'product' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Dna}
	 * @see			#productProperty
	 */
	public java.util.Iterator getProduct_asDna() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'product' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Dna} to add
	 * @see			#productProperty
	 */
	public void addProduct(fr.curie.BiNoM.pathways.biopax.Dna product) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'product' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Dna} created
	 * @see			#productProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Dna addProduct_asDna() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'product' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Dna} with the factory
	 * and calling addProduct(fr.curie.BiNoM.pathways.biopax.Dna product)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Dna.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#productProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Dna addProduct_asDna(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'product' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Dna} to remove
	 * @see			#productProperty
	 */
	public void removeProduct(fr.curie.BiNoM.pathways.biopax.Dna product) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'product' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Protein}
	 * @see			#productProperty
	 */
	public java.util.Iterator getProduct_asProtein() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'product' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Protein} to add
	 * @see			#productProperty
	 */
	public void addProduct(fr.curie.BiNoM.pathways.biopax.Protein product) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'product' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Protein} created
	 * @see			#productProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Protein addProduct_asProtein() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'product' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Protein} with the factory
	 * and calling addProduct(fr.curie.BiNoM.pathways.biopax.Protein product)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Protein.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#productProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Protein addProduct_asProtein(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'product' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Protein} to remove
	 * @see			#productProperty
	 */
	public void removeProduct(fr.curie.BiNoM.pathways.biopax.Protein product) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'product' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.Rna}
	 * @see			#productProperty
	 */
	public java.util.Iterator getProduct_asRna() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'product' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Rna} to add
	 * @see			#productProperty
	 */
	public void addProduct(fr.curie.BiNoM.pathways.biopax.Rna product) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'product' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.Rna} created
	 * @see			#productProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Rna addProduct_asRna() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'product' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Rna} with the factory
	 * and calling addProduct(fr.curie.BiNoM.pathways.biopax.Rna product)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Rna.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#productProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Rna addProduct_asRna(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'product' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.Rna} to remove
	 * @see			#productProperty
	 */
	public void removeProduct(fr.curie.BiNoM.pathways.biopax.Rna product) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Gets the 'template' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Dna}
	 * @see			#templateProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Dna getTemplate_asDna() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'template' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.Dna}
	 * @see			#templateProperty
	 */
	public void setTemplate(fr.curie.BiNoM.pathways.biopax.Dna template) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'template' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Dna}, the created value
	 * @see			#templateProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.Dna setTemplate_asDna() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'template' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Dna} with the factory.
	 * and calling setTemplate(fr.curie.BiNoM.pathways.biopax.Dna template)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Dna.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Dna}, the newly created value
	 * @see			#templateProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Dna setTemplate_asDna(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'template' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.DnaRegion}
	 * @see			#templateProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.DnaRegion getTemplate_asDnaRegion() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'template' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.DnaRegion}
	 * @see			#templateProperty
	 */
	public void setTemplate(fr.curie.BiNoM.pathways.biopax.DnaRegion template) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'template' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.DnaRegion}, the created value
	 * @see			#templateProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.DnaRegion setTemplate_asDnaRegion() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'template' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.DnaRegion} with the factory.
	 * and calling setTemplate(fr.curie.BiNoM.pathways.biopax.DnaRegion template)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#DnaRegion.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.DnaRegion}, the newly created value
	 * @see			#templateProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.DnaRegion setTemplate_asDnaRegion(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'template' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Rna}
	 * @see			#templateProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Rna getTemplate_asRna() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'template' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.Rna}
	 * @see			#templateProperty
	 */
	public void setTemplate(fr.curie.BiNoM.pathways.biopax.Rna template) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'template' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Rna}, the created value
	 * @see			#templateProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.Rna setTemplate_asRna() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'template' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.Rna} with the factory.
	 * and calling setTemplate(fr.curie.BiNoM.pathways.biopax.Rna template)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#Rna.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.Rna}, the newly created value
	 * @see			#templateProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.Rna setTemplate_asRna(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Gets the 'template' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.RnaRegion}
	 * @see			#templateProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.RnaRegion getTemplate_asRnaRegion() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'template' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.RnaRegion}
	 * @see			#templateProperty
	 */
	public void setTemplate(fr.curie.BiNoM.pathways.biopax.RnaRegion template) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'template' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.RnaRegion}, the created value
	 * @see			#templateProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.RnaRegion setTemplate_asRnaRegion() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'template' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.RnaRegion} with the factory.
	 * and calling setTemplate(fr.curie.BiNoM.pathways.biopax.RnaRegion template)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#RnaRegion.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.RnaRegion}, the newly created value
	 * @see			#templateProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.RnaRegion setTemplate_asRnaRegion(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}