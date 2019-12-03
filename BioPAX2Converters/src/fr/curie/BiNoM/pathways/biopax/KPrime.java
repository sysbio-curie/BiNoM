

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for KPrime ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#KPrime)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: The apparent equilibrium constant, K', and associated values. 
Usage: Concentrations in the equilibrium constant equation refer to the total concentrations of  all forms of particular biochemical reactants. For example, in the equilibrium constant equation for the biochemical reaction in which ATP is hydrolyzed to ADP and inorganic phosphate:

K' = [ADP][P<sub>i</sub>]/[ATP],

The concentration of ATP refers to the total concentration of all of the following species:

[ATP] = [ATP<sup>4-</sup>] + [HATP<sup>3-</sup>] + [H<sub>2</sub>ATP<sup>2-</sup>] + [MgATP<sup>2-</sup>] + [MgHATP<sup>-</sup>] + [Mg<sub>2</sub>ATP].

The apparent equilibrium constant is formally dimensionless, and can be kept so by inclusion of as many of the terms (1 mol/dm<sup>3</sup>) in the numerator or denominator as necessary.  It is a function of temperature (T), ionic strength (I), pH, and pMg (pMg = -log<sub>10</sub>[Mg<sup>2+</sup>]). Therefore, these quantities must be specified to be precise, and values for KEQ for biochemical reactions may be represented as 5-tuples of the form (K' T I pH pMg).  This property may have multiple values, representing different measurements for K' obtained under the different experimental conditions listed in the 5-tuple. (This definition adapted from EcoCyc)

See http://www.chem.qmul.ac.uk/iubmb/thermod/ for a thermodynamics tutorial.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface KPrime extends fr.curie.BiNoM.pathways.biopax.UtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#KPrime");
	

	/**
	 * The Jena Property for kPrime 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#kPrime)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The apparent equilibrium constant K'. Concentrations in the equilibrium constant equation refer to the total concentrations of  all forms of particular biochemical reactants. For example, in the equilibrium constant equation for the biochemical reaction in which ATP is hydrolyzed to ADP and inorganic phosphate:

K' = [ADP][P<sub>i</sub>]/[ATP],

The concentration of ATP refers to the total concentration of all of the following species:

[ATP] = [ATP<sup>4-</sup>] + [HATP<sup>3-</sup>] + [H<sub>2</sub>ATP<sup>2-</sup>] + [MgATP<sup>2-</sup>] + [MgHATP<sup>-</sup>] + [Mg<sub>2</sub>ATP].

The apparent equilibrium constant is formally dimensionless, and can be kept so by inclusion of as many of the terms (1 mol/dm<sup>3</sup>) in the numerator or denominator as necessary.  It is a function of temperature (T), ionic strength (I), pH, and pMg (pMg = -log<sub>10</sub>[Mg<sup>2+</sup>]).
(Definition from EcoCyc)^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property kPrimeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#kPrime");


	/**
	 * The Jena Property for ionicStrength 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#ionicStrength)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The ionic strength is defined as half of the total sum of the concentration (ci) of every ionic species (i) in the solution times the square of its charge (zi). For example, the ionic strength of a 0.1 M solution of CaCl2 is 0.5 x (0.1 x 22 + 0.2 x 12) = 0.3 M
(Definition from http://www.lsbu.ac.uk/biology/enztech/ph.html)^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property ionicStrengthProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#ionicStrength");


	/**
	 * The Jena Property for temperature 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#temperature)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Temperature in Celsius^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property temperatureProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#temperature");


	/**
	 * The Jena Property for pMg 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#pMg)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : A measure of the concentration of magnesium (Mg) in solution. (pMg = -log<sub>10</sub>[Mg<sup>2+</sup>])^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property pMgProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#pMg");


	/**
	 * The Jena Property for ph 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#ph)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : A measure of acidity and alkalinity of a solution that is a number on a scale on which a value of 7 represents neutrality and lower numbers indicate increasing acidity and higher numbers increasing alkalinity and on which each unit of change represents a tenfold change in acidity or alkalinity and that is the negative logarithm of the effective hydrogen-ion concentration or hydrogen-ion activity in gram equivalents per liter of the solution. (Definition from Merriam-Webster Dictionary)^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property phProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#ph");






	/**
	 * Gets the 'kPrime' property value
	 * @return		{@link java.lang.Float}
	 * @see			#kPrimeProperty
	 */
	public java.lang.Float getKPrime() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'kPrime' property value
	 * @param		{@link java.lang.Float}
	 * @see			#kPrimeProperty
	 */
	public void setKPrime(java.lang.Float kPrime) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'ionicStrength' property value
	 * @return		{@link java.lang.Float}
	 * @see			#ionicStrengthProperty
	 */
	public java.lang.Float getIonicStrength() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'ionicStrength' property value
	 * @param		{@link java.lang.Float}
	 * @see			#ionicStrengthProperty
	 */
	public void setIonicStrength(java.lang.Float ionicStrength) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'temperature' property value
	 * @return		{@link java.lang.Float}
	 * @see			#temperatureProperty
	 */
	public java.lang.Float getTemperature() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'temperature' property value
	 * @param		{@link java.lang.Float}
	 * @see			#temperatureProperty
	 */
	public void setTemperature(java.lang.Float temperature) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'pMg' property value
	 * @return		{@link java.lang.Float}
	 * @see			#pMgProperty
	 */
	public java.lang.Float getPMg() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'pMg' property value
	 * @param		{@link java.lang.Float}
	 * @see			#pMgProperty
	 */
	public void setPMg(java.lang.Float pMg) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Gets the 'ph' property value
	 * @return		{@link java.lang.Float}
	 * @see			#phProperty
	 */
	public java.lang.Float getPh() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'ph' property value
	 * @param		{@link java.lang.Float}
	 * @see			#phProperty
	 */
	public void setPh(java.lang.Float ph) throws com.ibm.adtech.jastor.JastorException;

}