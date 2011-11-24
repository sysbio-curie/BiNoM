

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface physicalEntityParticipantListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of COMMENT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant
	 * @param newValue the object representing the new value
	 */	
	public void COMMENTAdded(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant source, java.lang.String newValue);

	/**
	 * Called when a value of COMMENT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant
	 * @param oldValue the object representing the removed value
	 */	
	public void COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant source, java.lang.String oldValue);

	/**
	 * Called when PHYSICAL_DASH_ENTITY has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant
	 */
	public void PHYSICAL_DASH_ENTITYChanged(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant source);

	/**
	 * Called when STOICHIOMETRIC_DASH_COEFFICIENT has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant
	 */
	public void STOICHIOMETRIC_DASH_COEFFICIENTChanged(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant source);

	/**
	 * Called when CELLULAR_DASH_LOCATION has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant
	 */
	public void CELLULAR_DASH_LOCATIONChanged(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant source);

}