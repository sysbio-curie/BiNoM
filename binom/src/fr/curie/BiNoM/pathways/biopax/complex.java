

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for complex ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#complex)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A physical entity whose structure is comprised of other physical entities bound to each other non-covalently, at least one of which is a macromolecule (e.g. protein, DNA, or RNA). Complexes must be stable enough to function as a biological unit; in general, the temporary association of an enzyme with its substrate(s) should not be considered or represented as a complex. A complex is the physical product of an interaction (complexAssembly) and is not itself considered an interaction.
Comment: In general, complexes should not be defined recursively so that smaller complexes exist within larger complexes, i.e. a complex should not be a COMPONENT of another complex (see comments on the COMPONENT property). The boundaries on the size of complexes described by this class are not defined here, although elements of the cell as large and dynamic as, e.g., a mitochondrion would typically not be described using this class (later versions of this ontology may include a cellularComponent class to represent these). The strength of binding and the topology of the components cannot be described currently, but may be included in future versions of the ontology, depending on community need.
Examples: Ribosome, RNA polymerase II. Other examples of this class include complexes of multiple protein monomers and complexes of proteins and small molecules.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface complex extends fr.curie.BiNoM.pathways.biopax.physicalEntity, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#complex");
	

	/**
	 * The Jena Property for COMPONENTS 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#COMPONENTS)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Defines the physicalEntity subunits of this complex. This property should not contain other complexes, i.e. it should always be a flat representation of the complex. For example, if two protein complexes join to form a single larger complex via a complex assembly interaction, the COMPONENTS of the new complex should be the individual proteins of the smaller complexes, not the two smaller complexes themselves. Exceptions are black-box complexes (i.e. complexes in which the COMPONENTS property is empty), which may be used as COMPONENTS of other complexes because their constituent parts are unknown / unspecified. The reason for keeping complexes flat is to signify that there is no information stored in the way complexes are nested, such as assembly order.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property COMPONENTSProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#COMPONENTS");


	/**
	 * The Jena Property for ORGANISM 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#ORGANISM)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : An organism, e.g. 'Homo sapiens'. This is the organism that the entity is found in. Pathways may not have an organism associated with them, for instance, reference pathways from KEGG. Sequence-based entities (DNA, protein, RNA) may contain an xref to a sequence database that contains organism information, in which case the information should be consistent with the value for ORGANISM.@en <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property ORGANISMProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#ORGANISM");






	/**
	 * Get an Iterator the 'COMPONENTS' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant}
	 * @see			#COMPONENTSProperty
	 */
	public java.util.Iterator getCOMPONENTS() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'COMPONENTS' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} to add
	 * @see			#COMPONENTSProperty
	 */
	public void addCOMPONENTS(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant COMPONENTS) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'COMPONENTS' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} created
	 * @see			#COMPONENTSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addCOMPONENTS() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'COMPONENTS' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} with the factory
	 * and calling addCOMPONENTS(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant COMPONENTS)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#physicalEntityParticipant.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#COMPONENTSProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addCOMPONENTS(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'COMPONENTS' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant} to remove
	 * @see			#COMPONENTSProperty
	 */
	public void removeCOMPONENTS(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant COMPONENTS) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'ORGANISM' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.bioSource}
	 * @see			#ORGANISMProperty
	 */
	public java.util.Iterator getORGANISM() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'ORGANISM' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.bioSource} to add
	 * @see			#ORGANISMProperty
	 */
	public void addORGANISM(fr.curie.BiNoM.pathways.biopax.bioSource ORGANISM) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'ORGANISM' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.bioSource} created
	 * @see			#ORGANISMProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.bioSource addORGANISM() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'ORGANISM' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.bioSource} with the factory
	 * and calling addORGANISM(fr.curie.BiNoM.pathways.biopax.bioSource ORGANISM)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#bioSource.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#ORGANISMProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.bioSource addORGANISM(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'ORGANISM' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.bioSource} to remove
	 * @see			#ORGANISMProperty
	 */
	public void removeORGANISM(fr.curie.BiNoM.pathways.biopax.bioSource ORGANISM) throws com.ibm.adtech.jastor.JastorException;
		
}