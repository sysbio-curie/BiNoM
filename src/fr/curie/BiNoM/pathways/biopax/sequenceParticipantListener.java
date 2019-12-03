

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.sequenceParticipant to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface sequenceParticipantListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of COMMENT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceParticipant
	 * @param newValue the object representing the new value
	 */	
	public void COMMENTAdded(fr.curie.BiNoM.pathways.biopax.sequenceParticipant source, java.lang.String newValue);

	/**
	 * Called when a value of COMMENT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceParticipant
	 * @param oldValue the object representing the removed value
	 */	
	public void COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.sequenceParticipant source, java.lang.String oldValue);

	/**
	 * Called when PHYSICAL_DASH_ENTITY has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceParticipant
	 */
	public void PHYSICAL_DASH_ENTITYChanged(fr.curie.BiNoM.pathways.biopax.sequenceParticipant source);

	/**
	 * Called when STOICHIOMETRIC_DASH_COEFFICIENT has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceParticipant
	 */
	public void STOICHIOMETRIC_DASH_COEFFICIENTChanged(fr.curie.BiNoM.pathways.biopax.sequenceParticipant source);

	/**
	 * Called when CELLULAR_DASH_LOCATION has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceParticipant
	 */
	public void CELLULAR_DASH_LOCATIONChanged(fr.curie.BiNoM.pathways.biopax.sequenceParticipant source);

	/**
	 * Called when a value of SEQUENCE_DASH_FEATURE_DASH_LIST has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceParticipant
	 * @param newValue the object representing the new value
	 */	
	public void SEQUENCE_DASH_FEATURE_DASH_LISTAdded(fr.curie.BiNoM.pathways.biopax.sequenceParticipant source, fr.curie.BiNoM.pathways.biopax.sequenceFeature newValue);

	/**
	 * Called when a value of SEQUENCE_DASH_FEATURE_DASH_LIST has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.sequenceParticipant
	 * @param oldValue the object representing the removed value
	 */
	public void SEQUENCE_DASH_FEATURE_DASH_LISTRemoved(fr.curie.BiNoM.pathways.biopax.sequenceParticipant source, fr.curie.BiNoM.pathways.biopax.sequenceFeature oldValue);
		
}