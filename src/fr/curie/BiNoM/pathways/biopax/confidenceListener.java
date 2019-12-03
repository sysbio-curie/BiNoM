

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.confidence to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface confidenceListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of COMMENT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.confidence
	 * @param newValue the object representing the new value
	 */	
	public void COMMENTAdded(fr.curie.BiNoM.pathways.biopax.confidence source, java.lang.String newValue);

	/**
	 * Called when a value of COMMENT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.confidence
	 * @param oldValue the object representing the removed value
	 */	
	public void COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.confidence source, java.lang.String oldValue);

	/**
	 * Called when CONFIDENCE_DASH_VALUE has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.confidence
	 */
	public void CONFIDENCE_DASH_VALUEChanged(fr.curie.BiNoM.pathways.biopax.confidence source);

	/**
	 * Called when a value of XREF has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.confidence
	 * @param newValue the object representing the new value
	 */	
	public void XREFAdded(fr.curie.BiNoM.pathways.biopax.confidence source, fr.curie.BiNoM.pathways.biopax.xref newValue);

	/**
	 * Called when a value of XREF has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.confidence
	 * @param oldValue the object representing the removed value
	 */
	public void XREFRemoved(fr.curie.BiNoM.pathways.biopax.confidence source, fr.curie.BiNoM.pathways.biopax.xref oldValue);
		
	/**
	 * Called when a value of XREF has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.confidence
	 * @param newValue the object representing the new value
	 */	
	public void XREFAdded(fr.curie.BiNoM.pathways.biopax.confidence source, fr.curie.BiNoM.pathways.biopax.publicationXref newValue);

	/**
	 * Called when a value of XREF has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.confidence
	 * @param oldValue the object representing the removed value
	 */
	public void XREFRemoved(fr.curie.BiNoM.pathways.biopax.confidence source, fr.curie.BiNoM.pathways.biopax.publicationXref oldValue);
		
}