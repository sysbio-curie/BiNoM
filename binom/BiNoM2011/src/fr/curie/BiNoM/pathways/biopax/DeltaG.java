

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for DeltaG ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#DeltaG)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: Standard transformed Gibbs energy change for a reaction written in terms of biochemical reactants.  
Usage: Delta-G is represented as a 5-tuple of delta-G'<sup>0</sup>, temperature, ionic strength , pH, and pMg . A conversion in BioPAX may have multiple Delta-G values, representing different measurements for delta-G'<sup>0</sup> obtained under the different experimental conditions.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface DeltaG extends fr.curie.BiNoM.pathways.biopax.UtilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level3.owl#DeltaG");
	

	/**
	 * The Jena Property for deltaGPrime0 
	 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#deltaGPrime0)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : For biochemical reactions, this property refers to the standard transformed Gibbs energy change for a reaction written in terms of biochemical reactants (sums of species), delta-G'<sup>o</sup>.

  delta-G'<sup>o</sup> = -RT lnK'
and
  delta-G'<sup>o</sup> = delta-H'<sup>o</sup> - T delta-S'<sup>o</sup>

delta-G'<sup>o</sup> has units of kJ/mol.  Like K', it is a function of temperature (T), ionic strength (I), pH, and pMg (pMg = -log<sub>10</sub>[Mg<sup>2+</sup>]). Therefore, these quantities must be specified, and values for DELTA-G for biochemical reactions are represented as 5-tuples of the form (delta-G'<sup>o</sup> T I pH pMg).

(This definition from EcoCyc)^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property deltaGPrime0Property = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#deltaGPrime0");


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
	 * Gets the 'deltaGPrime0' property value
	 * @return		{@link java.lang.Float}
	 * @see			#deltaGPrime0Property
	 */
	public java.lang.Float getDeltaGPrime0() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'deltaGPrime0' property value
	 * @param		{@link java.lang.Float}
	 * @see			#deltaGPrime0Property
	 */
	public void setDeltaGPrime0(java.lang.Float deltaGPrime0) throws com.ibm.adtech.jastor.JastorException;

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