

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.unificationXref to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface unificationXrefListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of COMMENT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.unificationXref
	 * @param newValue the object representing the new value
	 */	
	public void COMMENTAdded(fr.curie.BiNoM.pathways.biopax.unificationXref source, java.lang.String newValue);

	/**
	 * Called when a value of COMMENT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.unificationXref
	 * @param oldValue the object representing the removed value
	 */	
	public void COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.unificationXref source, java.lang.String oldValue);

	/**
	 * Called when ID_DASH_VERSION has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.unificationXref
	 */
	public void ID_DASH_VERSIONChanged(fr.curie.BiNoM.pathways.biopax.unificationXref source);

	/**
	 * Called when DB_DASH_VERSION has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.unificationXref
	 */
	public void DB_DASH_VERSIONChanged(fr.curie.BiNoM.pathways.biopax.unificationXref source);

	/**
	 * Called when ID has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.unificationXref
	 */
	public void IDChanged(fr.curie.BiNoM.pathways.biopax.unificationXref source);

	/**
	 * Called when DB has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.unificationXref
	 */
	public void DBChanged(fr.curie.BiNoM.pathways.biopax.unificationXref source);

}