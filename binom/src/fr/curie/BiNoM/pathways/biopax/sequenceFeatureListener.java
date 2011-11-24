

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.sequenceFeature to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface sequenceFeatureListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of COMMENT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceFeature
	 * @param newValue the object representing the new value
	 */	
	public void COMMENTAdded(fr.curie.BiNoM.pathways.biopax.sequenceFeature source, java.lang.String newValue);

	/**
	 * Called when a value of COMMENT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceFeature
	 * @param oldValue the object representing the removed value
	 */	
	public void COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.sequenceFeature source, java.lang.String oldValue);

	/**
	 * Called when FEATURE_DASH_TYPE has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceFeature
	 */
	public void FEATURE_DASH_TYPEChanged(fr.curie.BiNoM.pathways.biopax.sequenceFeature source);

	/**
	 * Called when a value of FEATURE_DASH_LOCATION has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceFeature
	 * @param newValue the object representing the new value
	 */	
	public void FEATURE_DASH_LOCATIONAdded(fr.curie.BiNoM.pathways.biopax.sequenceFeature source, fr.curie.BiNoM.pathways.biopax.sequenceLocation newValue);

	/**
	 * Called when a value of FEATURE_DASH_LOCATION has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceFeature
	 * @param oldValue the object representing the removed value
	 */
	public void FEATURE_DASH_LOCATIONRemoved(fr.curie.BiNoM.pathways.biopax.sequenceFeature source, fr.curie.BiNoM.pathways.biopax.sequenceLocation oldValue);
		
	/**
	 * Called when SHORT_DASH_NAME has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceFeature
	 */
	public void SHORT_DASH_NAMEChanged(fr.curie.BiNoM.pathways.biopax.sequenceFeature source);

	/**
	 * Called when a value of SYNONYMS has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceFeature
	 * @param newValue the object representing the new value
	 */	
	public void SYNONYMSAdded(fr.curie.BiNoM.pathways.biopax.sequenceFeature source, java.lang.String newValue);

	/**
	 * Called when a value of SYNONYMS has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceFeature
	 * @param oldValue the object representing the removed value
	 */	
	public void SYNONYMSRemoved(fr.curie.BiNoM.pathways.biopax.sequenceFeature source, java.lang.String oldValue);

	/**
	 * Called when NAME has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceFeature
	 */
	public void NAMEChanged(fr.curie.BiNoM.pathways.biopax.sequenceFeature source);

	/**
	 * Called when a value of XREF has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceFeature
	 * @param newValue the object representing the new value
	 */	
	public void XREFAdded(fr.curie.BiNoM.pathways.biopax.sequenceFeature source, fr.curie.BiNoM.pathways.biopax.xref newValue);

	/**
	 * Called when a value of XREF has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceFeature
	 * @param oldValue the object representing the removed value
	 */
	public void XREFRemoved(fr.curie.BiNoM.pathways.biopax.sequenceFeature source, fr.curie.BiNoM.pathways.biopax.xref oldValue);
		
}