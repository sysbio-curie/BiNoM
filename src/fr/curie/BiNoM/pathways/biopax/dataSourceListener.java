

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.dataSource to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface dataSourceListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of COMMENT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.dataSource
	 * @param newValue the object representing the new value
	 */	
	public void COMMENTAdded(fr.curie.BiNoM.pathways.biopax.dataSource source, java.lang.String newValue);

	/**
	 * Called when a value of COMMENT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.dataSource
	 * @param oldValue the object representing the removed value
	 */	
	public void COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.dataSource source, java.lang.String oldValue);

	/**
	 * Called when a value of NAME has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.dataSource
	 * @param newValue the object representing the new value
	 */	
	public void NAMEAdded(fr.curie.BiNoM.pathways.biopax.dataSource source, java.lang.String newValue);

	/**
	 * Called when a value of NAME has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.dataSource
	 * @param oldValue the object representing the removed value
	 */	
	public void NAMERemoved(fr.curie.BiNoM.pathways.biopax.dataSource source, java.lang.String oldValue);

	/**
	 * Called when a value of XREF has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.dataSource
	 * @param newValue the object representing the new value
	 */	
	public void XREFAdded(fr.curie.BiNoM.pathways.biopax.dataSource source, fr.curie.BiNoM.pathways.biopax.xref newValue);

	/**
	 * Called when a value of XREF has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.dataSource
	 * @param oldValue the object representing the removed value
	 */
	public void XREFRemoved(fr.curie.BiNoM.pathways.biopax.dataSource source, fr.curie.BiNoM.pathways.biopax.xref oldValue);
		
	/**
	 * Called when a value of XREF has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.dataSource
	 * @param newValue the object representing the new value
	 */	
	public void XREFAdded(fr.curie.BiNoM.pathways.biopax.dataSource source, fr.curie.BiNoM.pathways.biopax.publicationXref newValue);

	/**
	 * Called when a value of XREF has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.dataSource
	 * @param oldValue the object representing the removed value
	 */
	public void XREFRemoved(fr.curie.BiNoM.pathways.biopax.dataSource source, fr.curie.BiNoM.pathways.biopax.publicationXref oldValue);
		
	/**
	 * Called when a value of XREF has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.dataSource
	 * @param newValue the object representing the new value
	 */	
	public void XREFAdded(fr.curie.BiNoM.pathways.biopax.dataSource source, fr.curie.BiNoM.pathways.biopax.unificationXref newValue);

	/**
	 * Called when a value of XREF has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.dataSource
	 * @param oldValue the object representing the removed value
	 */
	public void XREFRemoved(fr.curie.BiNoM.pathways.biopax.dataSource source, fr.curie.BiNoM.pathways.biopax.unificationXref oldValue);
		
}