

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for BiochemicalReaction ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#BiochemicalReaction)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: A conversion in which molecules of one or more physicalEntity pools, undergo covalent modifications and become a member of one or more other physicalEntity pools. The substrates of biochemical reactions are defined in terms of sums of species. This is a convention in biochemistry, and, in principle, all EC reactions should be biochemical reactions.

Examples: ATP + H2O = ADP + Pi

Comment: In the example reaction above, ATP is considered to be an equilibrium mixture of several species, namely ATP4-, HATP3-, H2ATP2-, MgATP2-, MgHATP-, and Mg2ATP. Additional species may also need to be considered if other ions (e.g. Ca2+) that bind ATP are present. Similar considerations apply to ADP and to inorganic phosphate (Pi). When writing biochemical reactions, it is not necessary to attach charges to the biochemical reactants or to include ions such as H+ and Mg2+ in the equation. The reaction is written in the direction specified by the EC nomenclature system, if applicable, regardless of the physiological direction(s) in which the reaction proceeds. Polymerization reactions involving large polymers whose structure is not explicitly captured should generally be represented as unbalanced reactions in which the monomer is consumed but the polymer remains unchanged, e.g. glycogen + glucose = glycogen. A better coverage for polymerization will be developed.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface BiochemicalReaction extends fr.curie.BiNoM.pathways.biopax.Conversion, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#BiochemicalReaction");
	

	/**
	 * The Jena Property for deltaG 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#deltaG)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : For biochemical reactions, this property refers to the standard transformed Gibbs energy change for a reaction written in terms of biochemical reactants (sums of species), delta-G

Since Delta-G can change based on multiple factors including ionic strength and temperature a reaction can have multiple DeltaG values.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property deltaGProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#deltaG");


	/**
	 * The Jena Property for deltaH 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#deltaH)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : For biochemical reactions, this property refers to the standard transformed enthalpy change for a reaction written in terms of biochemical reactants (sums of species), delta-H'<sup>o</sup>.

  delta-G'<sup>o</sup> = delta-H'<sup>o</sup> - T delta-S'<sup>o</sup>

Units: kJ/mole

(This definition from EcoCyc)^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property deltaHProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#deltaH");


	/**
	 * The Jena Property for kEQ 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#kEQ)</p>
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
	public static com.hp.hpl.jena.rdf.model.Property kEQProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#kEQ");


	/**
	 * The Jena Property for eCNumber 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#eCNumber)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The unique number assigned to a reaction by the Enzyme Commission of the International Union of Biochemistry and Molecular Biology.

Note that not all biochemical reactions currently have EC numbers assigned to them.^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property eCNumberProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#eCNumber");


	/**
	 * The Jena Property for deltaS 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#deltaS)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : For biochemical reactions, this property refers to the standard transformed entropy change for a reaction written in terms of biochemical reactants (sums of species), delta-S'<sup>o</sup>.

  delta-G'<sup>o</sup> = delta-H'<sup>o</sup> - T delta-S'<sup>o</sup>

(This definition from EcoCyc)^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property deltaSProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#deltaS");






	/**
	 * Get an Iterator the 'deltaG' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.DeltaG}
	 * @see			#deltaGProperty
	 */
	public java.util.Iterator getDeltaG() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'deltaG' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.DeltaG} to add
	 * @see			#deltaGProperty
	 */
	public void addDeltaG(fr.curie.BiNoM.pathways.biopax.DeltaG deltaG) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'deltaG' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.DeltaG} created
	 * @see			#deltaGProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.DeltaG addDeltaG() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'deltaG' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.DeltaG} with the factory
	 * and calling addDeltaG(fr.curie.BiNoM.pathways.biopax.DeltaG deltaG)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#DeltaG.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#deltaGProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.DeltaG addDeltaG(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'deltaG' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.DeltaG} to remove
	 * @see			#deltaGProperty
	 */
	public void removeDeltaG(fr.curie.BiNoM.pathways.biopax.DeltaG deltaG) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Iterates through the 'deltaH' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.Float}
	 * @see			#deltaHProperty
	 */
	public java.util.Iterator getDeltaH() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'deltaH' property value
	 * @param		{@link java.lang.Float}, the value to add
	 * @see			#deltaHProperty
	 */
	public void addDeltaH(java.lang.Float deltaH) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'deltaH' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.Float}, the value to remove
	 * @see			#deltaHProperty
	 */
	public void removeDeltaH(java.lang.Float deltaH) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'kEQ' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link fr.curie.BiNoM.pathways.biopax.KPrime}
	 * @see			#kEQProperty
	 */
	public java.util.Iterator getKEQ() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'kEQ' property
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.KPrime} to add
	 * @see			#kEQProperty
	 */
	public void addKEQ(fr.curie.BiNoM.pathways.biopax.KPrime kEQ) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'kEQ' property
	 * @return		The anoymous {@link fr.curie.BiNoM.pathways.biopax.KPrime} created
	 * @see			#kEQProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.KPrime addKEQ() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds a value for the 'kEQ' property.  This
	 * method is equivalent constructing a new instance of {@link fr.curie.BiNoM.pathways.biopax.KPrime} with the factory
	 * and calling addKEQ(fr.curie.BiNoM.pathways.biopax.KPrime kEQ)
	 * The resource argument have rdf:type http://www.biopax.org/release/biopax-level3.owl#KPrime.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#kEQProperty
	 */
	public fr.curie.BiNoM.pathways.biopax.KPrime addKEQ(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'kEQ' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link fr.curie.BiNoM.pathways.biopax.KPrime} to remove
	 * @see			#kEQProperty
	 */
	public void removeKEQ(fr.curie.BiNoM.pathways.biopax.KPrime kEQ) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Iterates through the 'eCNumber' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.String}
	 * @see			#eCNumberProperty
	 */
	public java.util.Iterator getECNumber() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'eCNumber' property value
	 * @param		{@link java.lang.String}, the value to add
	 * @see			#eCNumberProperty
	 */
	public void addECNumber(java.lang.String eCNumber) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'eCNumber' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.String}, the value to remove
	 * @see			#eCNumberProperty
	 */
	public void removeECNumber(java.lang.String eCNumber) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'deltaS' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.Float}
	 * @see			#deltaSProperty
	 */
	public java.util.Iterator getDeltaS() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'deltaS' property value
	 * @param		{@link java.lang.Float}, the value to add
	 * @see			#deltaSProperty
	 */
	public void addDeltaS(java.lang.Float deltaS) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'deltaS' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.Float}, the value to remove
	 * @see			#deltaSProperty
	 */
	public void removeDeltaS(java.lang.Float deltaS) throws com.ibm.adtech.jastor.JastorException;

}