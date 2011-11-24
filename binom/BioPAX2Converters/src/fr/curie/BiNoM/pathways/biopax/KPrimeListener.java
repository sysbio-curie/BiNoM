

package fr.curie.BiNoM.pathways.biopax;

/*
import com.hp.hpl.jena.datatypes.xsd.*;
import com.hp.hpl.jena.datatypes.xsd.impl.*;
import com.hp.hpl.jena.rdf.model.*;
import com.ibm.adtech.jastor.*;
import java.util.*;
import java.math.*;
*/


/**
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.KPrime to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface KPrimeListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.KPrime
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.KPrime source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.KPrime
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.KPrime source, java.lang.String oldValue);

	/**
	 * Called when kPrime has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.KPrime
	 */
	public void kPrimeChanged(fr.curie.BiNoM.pathways.biopax.KPrime source);

	/**
	 * Called when ionicStrength has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.KPrime
	 */
	public void ionicStrengthChanged(fr.curie.BiNoM.pathways.biopax.KPrime source);

	/**
	 * Called when temperature has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.KPrime
	 */
	public void temperatureChanged(fr.curie.BiNoM.pathways.biopax.KPrime source);

	/**
	 * Called when pMg has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.KPrime
	 */
	public void pMgChanged(fr.curie.BiNoM.pathways.biopax.KPrime source);

	/**
	 * Called when ph has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.KPrime
	 */
	public void phChanged(fr.curie.BiNoM.pathways.biopax.KPrime source);

}