

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.chemicalStructure to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface chemicalStructureListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of COMMENT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.chemicalStructure
	 * @param newValue the object representing the new value
	 */	
	public void COMMENTAdded(fr.curie.BiNoM.pathways.biopax.chemicalStructure source, java.lang.String newValue);

	/**
	 * Called when a value of COMMENT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.chemicalStructure
	 * @param oldValue the object representing the removed value
	 */	
	public void COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.chemicalStructure source, java.lang.String oldValue);

	/**
	 * Called when STRUCTURE_DASH_DATA has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.chemicalStructure
	 */
	public void STRUCTURE_DASH_DATAChanged(fr.curie.BiNoM.pathways.biopax.chemicalStructure source);

	/**
	 * Called when STRUCTURE_DASH_FORMAT has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.chemicalStructure
	 */
	public void STRUCTURE_DASH_FORMATChanged(fr.curie.BiNoM.pathways.biopax.chemicalStructure source);

}