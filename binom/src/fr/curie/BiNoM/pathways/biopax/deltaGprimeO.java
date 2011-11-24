

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for deltaGprimeO ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#deltaGprimeO)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: For biochemical reactions, this property refers to the standard transformed Gibbs energy change for a reaction written in terms of biochemical reactants (sums of species), delta-G'<sup>o</sup>.

  delta-G'<sup>o</sup> = -RT lnK'
and
  delta-G'<sup>o</sup> = delta-H'<sup>o</sup> - T delta-S'<sup>o</sup>

delta-G'<sup>o</sup> has units of kJ/mol.  Like K', it is a function of temperature (T), ionic strength (I), pH, and pMg (pMg = -log<sub>10</sub>[Mg<sup>2+</sup>]). Therefore, these quantities must be specified, and values for DELTA-G for biochemical reactions are represented as 5-tuples of the form (delta-G'<sup>o</sup> T I pH pMg).  This property may have multiple values, representing different measurements for delta-G'<sup>o</sup> obtained under the different experimental conditions listed in the 5-tuple.

(This definition from EcoCyc)^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface deltaGprimeO extends fr.curie.BiNoM.pathways.biopax.utilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#deltaGprimeO");
	

	/**
	 * The Jena Property for DELTA_DASH_G_DASH_PRIME_DASH_O 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#DELTA-G-PRIME-O)</p>
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
	public static com.hp.hpl.jena.rdf.model.Property DELTA_DASH_G_DASH_PRIME_DASH_OProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#DELTA-G-PRIME-O");


	/**
	 * The Jena Property for TEMPERATURE 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#TEMPERATURE)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : Temperature in Celsius^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property TEMPERATUREProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#TEMPERATURE");


	/**
	 * The Jena Property for PH 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#PH)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : A measure of acidity and alkalinity of a solution that is a number on a scale on which a value of 7 represents neutrality and lower numbers indicate increasing acidity and higher numbers increasing alkalinity and on which each unit of change represents a tenfold change in acidity or alkalinity and that is the negative logarithm of the effective hydrogen-ion concentration or hydrogen-ion activity in gram equivalents per liter of the solution. (Definition from Merriam-Webster Dictionary)^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property PHProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#PH");


	/**
	 * The Jena Property for IONIC_DASH_STRENGTH 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#IONIC-STRENGTH)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : The ionic strength is defined as half of the total sum of the concentration (ci) of every ionic species (i) in the solution times the square of its charge (zi). For example, the ionic strength of a 0.1 M solution of CaCl2 is 0.5 x (0.1 x 22 + 0.2 x 12) = 0.3 M
(Definition from http://www.lsbu.ac.uk/biology/enztech/ph.html)^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property IONIC_DASH_STRENGTHProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#IONIC-STRENGTH");


	/**
	 * The Jena Property for PMG 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#PMG)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	comment : A measure of the concentration of magnesium (Mg) in solution. (pMg = -log<sub>10</sub>[Mg<sup>2+</sup>])^^http://www.w3.org/2001/XMLSchema#string <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property PMGProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#PMG");






	/**
	 * Gets the 'DELTA_DASH_G_DASH_PRIME_DASH_O' property value
	 * @return		{@link java.lang.Float}
	 * @see			#DELTA_DASH_G_DASH_PRIME_DASH_OProperty
	 */
	public java.lang.Float getDELTA_DASH_G_DASH_PRIME_DASH_O() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'DELTA_DASH_G_DASH_PRIME_DASH_O' property value
	 * @param		{@link java.lang.Float}
	 * @see			#DELTA_DASH_G_DASH_PRIME_DASH_OProperty
	 */
	public void setDELTA_DASH_G_DASH_PRIME_DASH_O(java.lang.Float DELTA_DASH_G_DASH_PRIME_DASH_O) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'TEMPERATURE' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.Float}
	 * @see			#TEMPERATUREProperty
	 */
	public java.util.Iterator getTEMPERATURE() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'TEMPERATURE' property value
	 * @param		{@link java.lang.Float}, the value to add
	 * @see			#TEMPERATUREProperty
	 */
	public void addTEMPERATURE(java.lang.Float TEMPERATURE) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'TEMPERATURE' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.Float}, the value to remove
	 * @see			#TEMPERATUREProperty
	 */
	public void removeTEMPERATURE(java.lang.Float TEMPERATURE) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'PH' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.Float}
	 * @see			#PHProperty
	 */
	public java.util.Iterator getPH() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'PH' property value
	 * @param		{@link java.lang.Float}, the value to add
	 * @see			#PHProperty
	 */
	public void addPH(java.lang.Float PH) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'PH' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.Float}, the value to remove
	 * @see			#PHProperty
	 */
	public void removePH(java.lang.Float PH) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'IONIC_DASH_STRENGTH' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.Float}
	 * @see			#IONIC_DASH_STRENGTHProperty
	 */
	public java.util.Iterator getIONIC_DASH_STRENGTH() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'IONIC_DASH_STRENGTH' property value
	 * @param		{@link java.lang.Float}, the value to add
	 * @see			#IONIC_DASH_STRENGTHProperty
	 */
	public void addIONIC_DASH_STRENGTH(java.lang.Float IONIC_DASH_STRENGTH) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'IONIC_DASH_STRENGTH' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.Float}, the value to remove
	 * @see			#IONIC_DASH_STRENGTHProperty
	 */
	public void removeIONIC_DASH_STRENGTH(java.lang.Float IONIC_DASH_STRENGTH) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'PMG' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link java.lang.Float}
	 * @see			#PMGProperty
	 */
	public java.util.Iterator getPMG() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'PMG' property value
	 * @param		{@link java.lang.Float}, the value to add
	 * @see			#PMGProperty
	 */
	public void addPMG(java.lang.Float PMG) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'PMG' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link java.lang.Float}, the value to remove
	 * @see			#PMGProperty
	 */
	public void removePMG(java.lang.Float PMG) throws com.ibm.adtech.jastor.JastorException;

}