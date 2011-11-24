

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.biochemicalReaction to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface biochemicalReactionListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of DATA_DASH_SOURCE has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void DATA_DASH_SOURCEAdded(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.dataSource newValue);

	/**
	 * Called when a value of DATA_DASH_SOURCE has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void DATA_DASH_SOURCERemoved(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.dataSource oldValue);
		
	/**
	 * Called when a value of AVAILABILITY has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void AVAILABILITYAdded(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, java.lang.String newValue);

	/**
	 * Called when a value of AVAILABILITY has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param oldValue the object representing the removed value
	 */	
	public void AVAILABILITYRemoved(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, java.lang.String oldValue);

	/**
	 * Called when a value of COMMENT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void COMMENTAdded(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, java.lang.String newValue);

	/**
	 * Called when a value of COMMENT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param oldValue the object representing the removed value
	 */	
	public void COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, java.lang.String oldValue);

	/**
	 * Called when SHORT_DASH_NAME has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 */
	public void SHORT_DASH_NAMEChanged(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source);

	/**
	 * Called when a value of SYNONYMS has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void SYNONYMSAdded(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, java.lang.String newValue);

	/**
	 * Called when a value of SYNONYMS has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param oldValue the object representing the removed value
	 */	
	public void SYNONYMSRemoved(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, java.lang.String oldValue);

	/**
	 * Called when NAME has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 */
	public void NAMEChanged(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source);

	/**
	 * Called when a value of XREF has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void XREFAdded(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.xref newValue);

	/**
	 * Called when a value of XREF has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void XREFRemoved(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.xref oldValue);
		
	/**
	 * Called when a value of PARTICIPANTS has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void PARTICIPANTSAdded(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant newValue);

	/**
	 * Called when a value of PARTICIPANTS has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void PARTICIPANTSRemoved(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant oldValue);
		
	/**
	 * Called when a value of PARTICIPANTS has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void PARTICIPANTSAdded(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.entity newValue);

	/**
	 * Called when a value of PARTICIPANTS has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void PARTICIPANTSRemoved(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.entity oldValue);
		
	/**
	 * Called when a value of EVIDENCE has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void EVIDENCEAdded(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.evidence newValue);

	/**
	 * Called when a value of EVIDENCE has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void EVIDENCERemoved(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.evidence oldValue);
		
	/**
	 * Called when a value of INTERACTION_DASH_TYPE has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void INTERACTION_DASH_TYPEAdded(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.openControlledVocabulary newValue);

	/**
	 * Called when a value of INTERACTION_DASH_TYPE has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void INTERACTION_DASH_TYPERemoved(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.openControlledVocabulary oldValue);
		
	/**
	 * Called when a value of LEFT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void LEFTAdded(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant newValue);

	/**
	 * Called when a value of LEFT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void LEFTRemoved(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant oldValue);
		
	/**
	 * Called when SPONTANEOUS has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 */
	public void SPONTANEOUSChanged(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source);

	/**
	 * Called when a value of RIGHT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void RIGHTAdded(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant newValue);

	/**
	 * Called when a value of RIGHT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void RIGHTRemoved(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant oldValue);
		
	/**
	 * Called when a value of DELTA_DASH_H has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void DELTA_DASH_HAdded(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, java.lang.Double newValue);

	/**
	 * Called when a value of DELTA_DASH_H has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param oldValue the object representing the removed value
	 */	
	public void DELTA_DASH_HRemoved(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, java.lang.Double oldValue);

	/**
	 * Called when a value of EC_DASH_NUMBER has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void EC_DASH_NUMBERAdded(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, java.lang.String newValue);

	/**
	 * Called when a value of EC_DASH_NUMBER has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param oldValue the object representing the removed value
	 */	
	public void EC_DASH_NUMBERRemoved(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, java.lang.String oldValue);

	/**
	 * Called when a value of DELTA_DASH_S has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void DELTA_DASH_SAdded(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, java.lang.Double newValue);

	/**
	 * Called when a value of DELTA_DASH_S has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param oldValue the object representing the removed value
	 */	
	public void DELTA_DASH_SRemoved(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, java.lang.Double oldValue);

	/**
	 * Called when a value of DELTA_DASH_G has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void DELTA_DASH_GAdded(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.deltaGprimeO newValue);

	/**
	 * Called when a value of DELTA_DASH_G has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void DELTA_DASH_GRemoved(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.deltaGprimeO oldValue);
		
	/**
	 * Called when a value of KEQ has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void KEQAdded(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.kPrime newValue);

	/**
	 * Called when a value of KEQ has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.biochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void KEQRemoved(fr.curie.BiNoM.pathways.biopax.biochemicalReaction source, fr.curie.BiNoM.pathways.biopax.kPrime oldValue);
		
}