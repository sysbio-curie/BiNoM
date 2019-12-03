

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for kPrime ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#kPrime)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: The apparent equilibrium constant, K', and associated values.  Concentrations in the equilibrium constant equation refer to the total concentrations of  all forms of particular biochemical reactants. For example, in the equilibrium constant equation for the biochemical reaction in which ATP is hydrolyzed to ADP and inorganic phosphate:

K' = [ADP][P<sub>i</sub>]/[ATP],

The concentration of ATP refers to the total concentration of all of the following species:

[ATP] = [ATP<sup>4-</sup>] + [HATP<sup>3-</sup>] + [H<sub>2</sub>ATP<sup>2-</sup>] + [MgATP<sup>2-</sup>] + [MgHATP<sup>-</sup>] + [Mg<sub>2</sub>ATP].

The apparent equilibrium constant is formally dimensionless, and can be kept so by inclusion of as many of the terms (1 mol/dm<sup>3</sup>) in the numerator or denominator as necessary.  It is a function of temperature (T), ionic strength (I), pH, and pMg (pMg = -log<sub>10</sub>[Mg<sup>2+</sup>]). Therefore, these quantities must be specified to be precise, and values for KEQ for biochemical reactions may be represented as 5-tuples of the form (K' T I pH pMg).  This property may have multiple values, representing different measurements for K' obtained under the different experimental conditions listed in the 5-tuple. (This definition adapted from EcoCyc)

See http://www.chem.qmul.ac.uk/iubmb/thermod/ for a thermodynamics tutorial.^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface kPrime extends fr.curie.BiNoM.pathways.biopax.utilityClass, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#kPrime");
	

	/**
	 * The Jena Property for K_DASH_PRIME 
	 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#K-PRIME)</p>
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
	public static com.hp.hpl.jena.rdf.model.Property K_DASH_PRIMEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#K-PRIME");


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
	 * Gets the 'K_DASH_PRIME' property value
	 * @return		{@link java.lang.Float}
	 * @see			#K_DASH_PRIMEProperty
	 */
	public java.lang.Float getK_DASH_PRIME() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Sets the 'K_DASH_PRIME' property value
	 * @param		{@link java.lang.Float}
	 * @see			#K_DASH_PRIMEProperty
	 */
	public void setK_DASH_PRIME(java.lang.Float K_DASH_PRIME) throws com.ibm.adtech.jastor.JastorException;

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