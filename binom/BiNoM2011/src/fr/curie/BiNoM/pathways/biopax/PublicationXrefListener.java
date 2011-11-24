

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.PublicationXref to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface PublicationXrefListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PublicationXref
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.PublicationXref source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PublicationXref
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.PublicationXref source, java.lang.String oldValue);

	/**
	 * Called when id has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PublicationXref
	 */
	public void idChanged(fr.curie.BiNoM.pathways.biopax.PublicationXref source);

	/**
	 * Called when db has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PublicationXref
	 */
	public void dbChanged(fr.curie.BiNoM.pathways.biopax.PublicationXref source);

	/**
	 * Called when idVersion has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PublicationXref
	 */
	public void idVersionChanged(fr.curie.BiNoM.pathways.biopax.PublicationXref source);

	/**
	 * Called when dbVersion has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PublicationXref
	 */
	public void dbVersionChanged(fr.curie.BiNoM.pathways.biopax.PublicationXref source);

	/**
	 * Called when title has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PublicationXref
	 */
	public void titleChanged(fr.curie.BiNoM.pathways.biopax.PublicationXref source);

	/**
	 * Called when year has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PublicationXref
	 */
	public void yearChanged(fr.curie.BiNoM.pathways.biopax.PublicationXref source);

	/**
	 * Called when a value of url has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PublicationXref
	 * @param newValue the object representing the new value
	 */	
	public void urlAdded(fr.curie.BiNoM.pathways.biopax.PublicationXref source, java.lang.String newValue);

	/**
	 * Called when a value of url has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PublicationXref
	 * @param oldValue the object representing the removed value
	 */	
	public void urlRemoved(fr.curie.BiNoM.pathways.biopax.PublicationXref source, java.lang.String oldValue);

	/**
	 * Called when a value of author has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PublicationXref
	 * @param newValue the object representing the new value
	 */	
	public void authorAdded(fr.curie.BiNoM.pathways.biopax.PublicationXref source, java.lang.String newValue);

	/**
	 * Called when a value of author has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PublicationXref
	 * @param oldValue the object representing the removed value
	 */	
	public void authorRemoved(fr.curie.BiNoM.pathways.biopax.PublicationXref source, java.lang.String oldValue);

	/**
	 * Called when a value of source has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PublicationXref
	 * @param newValue the object representing the new value
	 */	
	public void sourceAdded(fr.curie.BiNoM.pathways.biopax.PublicationXref source, java.lang.String newValue);

	/**
	 * Called when a value of source has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PublicationXref
	 * @param oldValue the object representing the removed value
	 */	
	public void sourceRemoved(fr.curie.BiNoM.pathways.biopax.PublicationXref source, java.lang.String oldValue);

}