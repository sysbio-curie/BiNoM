

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for biochemicalReaction ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#biochemicalReaction)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A conversion interaction in which one or more entities (substrates) undergo covalent changes to become one or more other entities (products). The substrates of biochemical reactions are defined in terms of sums of species. This is convention in biochemistry, and, in principle, all of the EC reactions should be biochemical reactions.
Examples: ATP + H2O = ADP + Pi
Comment: In the example reaction above, ATP is considered to be an equilibrium mixture of several species, namely ATP4-, HATP3-, H2ATP2-, MgATP2-, MgHATP-, and Mg2ATP. Additional species may also need to be considered if other ions (e.g. Ca2+) that bind ATP are present. Similar considerations apply to ADP and to inorganic phosphate (Pi). When writing biochemical reactions, it is not necessary to attach charges to the biochemical reactants or to include ions such as H+ and Mg2+ in the equation. The reaction is written in the direction specified by the EC nomenclature system, if applicable, regardless of the physiological direction(s) in which the reaction proceeds. Polymerization reactions involving large polymers whose structure is not explicitly captured should generally be represented as unbalanced reactions in which the monomer is consumed but the polymer remains unchanged, e.g. glycogen + glucose = glycogen.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface biochemicalReaction extends fr.curie.BiNoM.pathways.biopax.conversion, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#biochemicalReaction");
	

	/**
	 * The Jena Property for DELTA_DASH_H 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#DELTA-H)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : For biochemical reactions, this property refers to the standard transformed enthalpy change for a reaction written in terms of biochemical reactants (sums of species), delta-H'<sup>o</sup>.

  delta-G'<sup>o</sup> = delta-H'<sup>o</sup> - T delta-S'<sup>o</sup>

Units: kJ/mole

(This definition from EcoCyc)^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property DELTA_DASH_HProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#DELTA-H");


	/**
	 * The Jena Property for EC_DASH_NUMBER 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#EC-NUMBER)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The unique number assigned to a reaction by the Enzyme Commission of the International Union of Biochemistry and Molecular Biology.

Note that not all biochemical reactions currently have EC numbers assigned to them.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property EC_DASH_NUMBERProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#EC-NUMBER");


	/**
	 * The Jena Property for DELTA_DASH_S 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#DELTA-S)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : For biochemical reactions, this property refers to the standard transformed entropy change for a reaction written in terms of biochemical reactants (sums of species), delta-S'<sup>o</sup>.

  delta-G'<sup>o</sup> = delta-H'<sup>o</sup> - T delta-S'<sup>o</sup>

(This definition from EcoCyc)^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property DELTA_DASH_SProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#DELTA-S");


	/**
	 * The Jena Property for DELTA_DASH_G 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#DELTA-G)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : For biochemical reactions, this property refers to the standard transformed Gibbs energy change for a reaction written in terms of biochemical reactants (sums of species), delta-G'<sup>o</sup>.

  delta-G'<sup>o</sup> = -RT lnK'
and
  delta-G'<sup>o</sup> = delta-H'<sup>o</sup> - T delta-S'<sup>o</sup>

delta-G'<sup>o</sup> has units of kJ/mol.  Like K', it is a function of temperature (T), ionic strength (I), pH, and pMg (pMg = -log<sub>10</sub>[Mg<sup>2+</sup>]). Therefore, these quantities must be specified, and values for DELTA-G for biochemical reactions are represented as 5-tuples of the form (delta-G'<sup>o</sup> T I pH pMg).  This property may have multiple values, representing different measurements for delta-G'<sup>o</sup> obtained under the different experimental conditions listed in the 5-tuple.

(This definition from EcoCyc)^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property DELTA_DASH_GProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#DELTA-G");


	/**
	 * The Jena Property for KEQ 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#KEQ)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : This quantity is dimensionless and is usually a single number. The measured equilibrium constant for a biochemical reaction, encoded by the slot KEQ, is actually the apparent equilibrium constant, K'.  Concentrations in the equilibrium constant equation refer to the total concentrations of  all forms of particular biochemical reactants. For example, in the equilibrium constant equation for the biochemical reaction in which ATP is hydrolyzed to ADP and inorganic phosphate:

K' = [ADP][P<sub>i</sub>]/[ATP],

The concentration of ATP refers to the total concentration of all of the following species:

[ATP] = [ATP<sup>4-</sup>] + [HATP<sup>3-</sup>] + [H<sub>2</sub>ATP<sup>2-</sup>] + [MgATP<sup>2-</sup>] + [MgHATP<sup>-</sup>] + [Mg<sub>2</sub>ATP].

The apparent equilibrium constant is formally dimensionless, and can be kept so by inclusion of as many of the terms (1 mol/dm<sup>3</sup>) in the numerator or denominator as necessary.  It is a function of temperature (T), ionic strength (I), pH, and pMg (pMg = -log<sub>10</sub>[Mg<sup>2+</sup>]). Therefore, these quantities must be specified to be precise, and values for KEQ for biochemical reactions may be represented as 5-tuples of the form (K' T I pH pMg).  This property may have multiple values, representing different measurements for K' obtained under the different experimental conditions listed in the 5-tuple. (This definition adapted from EcoCyc)^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property KEQProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#KEQ");






	/**
	 * Iterates through the 'DELTA_DASH_H' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.Double}
	 * @see			#DELTA_DASH_HProperty
	 */
	public java.util.Iterator getDELTA_DASH_H() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'DELTA_DASH_H' property value
	 * @param		{@link java.lang.Double}, the value to add
	 * @see			#DELTA_DASH_HProperty
	 */
	public void addDELTA_DASH_H(java.lang.Double DELTA_DASH_H) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'DELTA_DASH_H' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.Double}, the value to remove
	 * @see			#DELTA_DASH_HProperty
	 */
	public void removeDELTA_DASH_H(java.lang.Double DELTA_DASH_H) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'EC_DASH_NUMBER' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#EC_DASH_NUMBERProperty
	 */
	public java.util.Iterator getEC_DASH_NUMBER() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'EC_DASH_NUMBER' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#EC_DASH_NUMBERProperty
	 */
	public void addEC_DASH_NUMBER(java.lang.String EC_DASH_NUMBER) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'EC_DASH_NUMBER' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#EC_DASH_NUMBERProperty
	 */
	public void removeEC_DASH_NUMBER(java.lang.String EC_DASH_NUMBER) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'DELTA_DASH_S' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.Double}
	 * @see			#DELTA_DASH_SProperty
	 */
	public java.util.Iterator getDELTA_DASH_S() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'DELTA_DASH_S' property value
	 * @param		{@link java.lang.Double}, the value to add
	 * @see			#DELTA_DASH_SProperty
	 */
	public void addDELTA_DASH_S(java.lang.Double DELTA_DASH_S) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'DELTA_DASH_S' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.Double}, the value to remove
	 * @see			#DELTA_DASH_SProperty
	 */
	public void removeDELTA_DASH_S(java.lang.Double DELTA_DASH_S) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'DELTA_DASH_G' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.deltaGprimeO}
	 * @see			#DELTA_DASH_GProperty
	 */
	public java.util.Iterator getDELTA_DASH_G() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'DELTA_DASH_G' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.deltaGprimeO} to add
	 * @see			#DELTA_DASH_GProperty
	 */
	public void addDELTA_DASH_G(fr.curie.BiNoM.pathways.biopax.deltaGprimeO DELTA_DASH_G) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'DELTA_DASH_G' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.deltaGprimeO} created
	 * @see			#DELTA_DASH_GProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.deltaGprimeO addDELTA_DASH_G() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'DELTA_DASH_G' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.deltaGprimeO} with the factory
	 * and calling addDELTA_DASH_G(fr.curie.BiNoM.pathways.biopax.deltaGprimeO DELTA_DASH_G)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#deltaGprimeO.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#DELTA_DASH_GProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.deltaGprimeO addDELTA_DASH_G(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'DELTA_DASH_G' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.deltaGprimeO} to remove
	 * @see			#DELTA_DASH_GProperty
	 */
	public void removeDELTA_DASH_G(fr.curie.BiNoM.pathways.biopax.deltaGprimeO DELTA_DASH_G) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'KEQ' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.kPrime}
	 * @see			#KEQProperty
	 */
	public java.util.Iterator getKEQ() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'KEQ' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.kPrime} to add
	 * @see			#KEQProperty
	 */
	public void addKEQ(fr.curie.BiNoM.pathways.biopax.kPrime KEQ) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'KEQ' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.kPrime} created
	 * @see			#KEQProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.kPrime addKEQ() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'KEQ' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.kPrime} with the factory
	 * and calling addKEQ(fr.curie.BiNoM.pathways.biopax.kPrime KEQ)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level2.owl#kPrime.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#KEQProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.kPrime addKEQ(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'KEQ' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.kPrime} to remove
	 * @see			#KEQProperty
	 */
	public void removeKEQ(fr.curie.BiNoM.pathways.biopax.kPrime KEQ) throws com.ibm.adtech.jastor.JastorException;
		
}