

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for unificationXref ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#unificationXref)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A unification xref defines a reference to an entity in an external resource that has the same biological identity as the referring entity. For example, if one wished to link from a database record, C, describing a chemical compound in a BioPAX data collection to a record, C', describing the same chemical compound in an external database, one would use a unification xref since records C and C' describe the same biological identity. Generally, unification xrefs should be used whenever possible, although there are cases where they might not be useful, such as application to application data exchange.
Comment: Unification xrefs in physical entities are essential for data integration, but are less important in interactions. This is because unification xrefs on the physical entities in an interaction can be used to compute the equivalence of two interactions of the same type. An xref in a protein pointing to a gene, e.g. in the LocusLink database17, would not be a unification xref since the two entities do not have the same biological identity (one is a protein, the other is a gene). Instead, this link should be a captured as a relationship xref. References to an external controlled vocabulary term within the OpenControlledVocabulary class should use a unification xref where possible (e.g. GO:0005737).
Examples: An xref in a protein instance pointing to an entry in the Swiss-Prot database, and an xref in an RNA instance pointing to the corresponding RNA sequence in the RefSeq database..^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface unificationXref extends fr.curie.BiNoM.pathways.biopax.xref, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#unificationXref");
	





}