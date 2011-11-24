

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.publicationXref to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface publicationXrefListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of COMMENT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.publicationXref
	 * @param newValue the object representing the new value
	 */	
	public void COMMENTAdded(fr.curie.BiNoM.pathways.biopax.publicationXref source, java.lang.String newValue);

	/**
	 * Called when a value of COMMENT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.publicationXref
	 * @param oldValue the object representing the removed value
	 */	
	public void COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.publicationXref source, java.lang.String oldValue);

	/**
	 * Called when ID_DASH_VERSION has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.publicationXref
	 */
	public void ID_DASH_VERSIONChanged(fr.curie.BiNoM.pathways.biopax.publicationXref source);

	/**
	 * Called when DB_DASH_VERSION has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.publicationXref
	 */
	public void DB_DASH_VERSIONChanged(fr.curie.BiNoM.pathways.biopax.publicationXref source);

	/**
	 * Called when ID has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.publicationXref
	 */
	public void IDChanged(fr.curie.BiNoM.pathways.biopax.publicationXref source);

	/**
	 * Called when DB has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.publicationXref
	 */
	public void DBChanged(fr.curie.BiNoM.pathways.biopax.publicationXref source);

	/**
	 * Called when TITLE has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.publicationXref
	 */
	public void TITLEChanged(fr.curie.BiNoM.pathways.biopax.publicationXref source);

	/**
	 * Called when YEAR has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.publicationXref
	 */
	public void YEARChanged(fr.curie.BiNoM.pathways.biopax.publicationXref source);

	/**
	 * Called when a value of URL has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.publicationXref
	 * @param newValue the object representing the new value
	 */	
	public void URLAdded(fr.curie.BiNoM.pathways.biopax.publicationXref source, java.lang.String newValue);

	/**
	 * Called when a value of URL has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.publicationXref
	 * @param oldValue the object representing the removed value
	 */	
	public void URLRemoved(fr.curie.BiNoM.pathways.biopax.publicationXref source, java.lang.String oldValue);

	/**
	 * Called when a value of SOURCE has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.publicationXref
	 * @param newValue the object representing the new value
	 */	
	public void SOURCEAdded(fr.curie.BiNoM.pathways.biopax.publicationXref source, java.lang.String newValue);

	/**
	 * Called when a value of SOURCE has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.publicationXref
	 * @param oldValue the object representing the removed value
	 */	
	public void SOURCERemoved(fr.curie.BiNoM.pathways.biopax.publicationXref source, java.lang.String oldValue);

	/**
	 * Called when a value of AUTHORS has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.publicationXref
	 * @param newValue the object representing the new value
	 */	
	public void AUTHORSAdded(fr.curie.BiNoM.pathways.biopax.publicationXref source, java.lang.String newValue);

	/**
	 * Called when a value of AUTHORS has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.publicationXref
	 * @param oldValue the object representing the removed value
	 */	
	public void AUTHORSRemoved(fr.curie.BiNoM.pathways.biopax.publicationXref source, java.lang.String oldValue);

}