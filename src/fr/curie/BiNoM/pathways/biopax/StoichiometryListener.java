

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.Stoichiometry to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface StoichiometryListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Stoichiometry
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.Stoichiometry source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Stoichiometry
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.Stoichiometry source, java.lang.String oldValue);

	/**
	 * Called when stoichiometricCoefficient has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Stoichiometry
	 */
	public void stoichiometricCoefficientChanged(fr.curie.BiNoM.pathways.biopax.Stoichiometry source);

	/**
	 * Called when physicalEntity has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Stoichiometry
	 */
	public void physicalEntityChanged(fr.curie.BiNoM.pathways.biopax.Stoichiometry source);

}