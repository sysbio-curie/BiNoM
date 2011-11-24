

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.sequenceSite to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface sequenceSiteListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of COMMENT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceSite
	 * @param newValue the object representing the new value
	 */	
	public void COMMENTAdded(fr.curie.BiNoM.pathways.biopax.sequenceSite source, java.lang.String newValue);

	/**
	 * Called when a value of COMMENT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceSite
	 * @param oldValue the object representing the removed value
	 */	
	public void COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.sequenceSite source, java.lang.String oldValue);

	/**
	 * Called when POSITION_DASH_STATUS has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceSite
	 */
	public void POSITION_DASH_STATUSChanged(fr.curie.BiNoM.pathways.biopax.sequenceSite source);

	/**
	 * Called when SEQUENCE_DASH_POSITION has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceSite
	 */
	public void SEQUENCE_DASH_POSITIONChanged(fr.curie.BiNoM.pathways.biopax.sequenceSite source);

}