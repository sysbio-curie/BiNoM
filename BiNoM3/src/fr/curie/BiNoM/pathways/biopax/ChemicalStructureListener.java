

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.ChemicalStructure to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface ChemicalStructureListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ChemicalStructure
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.ChemicalStructure source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ChemicalStructure
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.ChemicalStructure source, java.lang.String oldValue);

	/**
	 * Called when structureFormat has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ChemicalStructure
	 */
	public void structureFormatChanged(fr.curie.BiNoM.pathways.biopax.ChemicalStructure source);

	/**
	 * Called when structureData has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ChemicalStructure
	 */
	public void structureDataChanged(fr.curie.BiNoM.pathways.biopax.ChemicalStructure source);

}