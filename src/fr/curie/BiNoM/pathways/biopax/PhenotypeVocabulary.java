

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for PhenotypeVocabulary ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#PhenotypeVocabulary)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: The phenotype measured in the experiment e.g. growth rate or viability of a cell. This is only the type, not the value e.g. for a synthetic lethal interaction, the phenotype is viability, specified by ID: PATO:0000169, "viability", not the value (specified by ID: PATO:0000718, "lethal (sensu genetics)". A single term in a phenotype controlled vocabulary can be referenced using the xref, or the PhenoXML describing the PATO EQ model phenotype description can be stored as a string in PATO-DATA.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface PhenotypeVocabulary extends fr.curie.BiNoM.pathways.biopax.ControlledVocabulary, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#PhenotypeVocabulary");
	

	/**
	 * The Jena Property for patoData 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#patoData)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The phenotype data from PATO, formatted as PhenoXML (defined at http://www.fruitfly.org/~cjm/obd/formats.html)^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property patoDataProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#patoData");






	/**
	 * Gets the 'patoData' property value
	 * @return		{@link java.lang.String}
	 * @see			#patoDataProperty
	 */
	public java.lang.String getPatoData() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'patoData' property value
	 * @param		{@link java.lang.String}
	 * @see			#patoDataProperty
	 */
	public void setPatoData(java.lang.String patoData) throws com.ibm.adtech.jastor.JastorException;

}