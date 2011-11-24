

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Gene ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Gene)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A continuant that encodes information that can be inherited through replication. 
Rationale: Gene is an abstract continuant that can be best described as a "schema", a common conception commonly used by biologists to demark a component within genome. In BioPAX, Gene is considered a generalization over eukaryotic and prokaryotic genes and is used only in genetic interactions.  Gene is often confused with DNA and RNA fragments, however, these are considered the physical encoding of a gene.  N.B. Gene expression regulation makes use of DNA and RNA physical entities and not this class.
Usage: Gene should only be used for describing GeneticInteractions.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface Gene extends fr.curie.BiNoM.pathways.biopax.Entity, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#Gene");
	

	/**
	 * The Jena Property for organism 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#organism)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : An organism, e.g. 'Homo sapiens'. This is the organism that the entity is found in. Pathways may not have an organism associated with them, for instance, reference pathways from KEGG. Sequence-based entities (DNA, protein, RNA) may contain an xref to a sequence database that contains organism information, in which case the information should be consistent with the value for ORGANISM.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property organismProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#organism");






	/**
	 * Gets the 'organism' property value
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.BioSource}
	 * @see			#organismProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.BioSource getOrganism() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'organism' property value
	 * @param		{@link fr.curie.BiNoM.pathways.biopax.BioSource}
	 * @see			#organismProperty
	 */
	public void setOrganism(fr.curie.BiNoM.pathways.biopax.BioSource organism) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'organism' property value to an anonymous node
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.BioSource}, the created value
	 * @see			#organismProperty
	 */	
	public fr.curie.BiNoM.pathways.biopax.BioSource setOrganism() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Sets the 'organism' property value to the given resource, and add's rdf:type properties.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.BioSource} with the factory.
	 * and calling setOrganism(fr.curie.BiNoM.pathways.biopax.BioSource organism)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#BioSource.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Resource} must not be be null.
	 * @return		{@link fr.curie.BiNoM.pathways.biopax.BioSource}, the newly created value
	 * @see			#organismProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.BioSource setOrganism(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
}