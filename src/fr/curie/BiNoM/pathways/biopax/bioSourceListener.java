

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.bioSource to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface bioSourceListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of COMMENT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.bioSource
	 * @param newValue the object representing the new value
	 */	
	public void COMMENTAdded(fr.curie.BiNoM.pathways.biopax.bioSource source, java.lang.String newValue);

	/**
	 * Called when a value of COMMENT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.bioSource
	 * @param oldValue the object representing the removed value
	 */	
	public void COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.bioSource source, java.lang.String oldValue);

	/**
	 * Called when CELLTYPE has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.bioSource
	 */
	public void CELLTYPEChanged(fr.curie.BiNoM.pathways.biopax.bioSource source);

	/**
	 * Called when TISSUE has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.bioSource
	 */
	public void TISSUEChanged(fr.curie.BiNoM.pathways.biopax.bioSource source);

	/**
	 * Called when TAXON_DASH_XREF has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.bioSource
	 */
	public void TAXON_DASH_XREFChanged(fr.curie.BiNoM.pathways.biopax.bioSource source);

	/**
	 * Called when NAME has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.bioSource
	 */
	public void NAMEChanged(fr.curie.BiNoM.pathways.biopax.bioSource source);

}