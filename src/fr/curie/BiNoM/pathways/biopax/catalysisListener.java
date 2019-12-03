

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.catalysis to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface catalysisListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of DATA_DASH_SOURCE has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param newValue the object representing the new value
	 */	
	public void DATA_DASH_SOURCEAdded(fr.curie.BiNoM.pathways.biopax.catalysis source, fr.curie.BiNoM.pathways.biopax.dataSource newValue);

	/**
	 * Called when a value of DATA_DASH_SOURCE has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param oldValue the object representing the removed value
	 */
	public void DATA_DASH_SOURCERemoved(fr.curie.BiNoM.pathways.biopax.catalysis source, fr.curie.BiNoM.pathways.biopax.dataSource oldValue);
		
	/**
	 * Called when a value of AVAILABILITY has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param newValue the object representing the new value
	 */	
	public void AVAILABILITYAdded(fr.curie.BiNoM.pathways.biopax.catalysis source, java.lang.String newValue);

	/**
	 * Called when a value of AVAILABILITY has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param oldValue the object representing the removed value
	 */	
	public void AVAILABILITYRemoved(fr.curie.BiNoM.pathways.biopax.catalysis source, java.lang.String oldValue);

	/**
	 * Called when a value of COMMENT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param newValue the object representing the new value
	 */	
	public void COMMENTAdded(fr.curie.BiNoM.pathways.biopax.catalysis source, java.lang.String newValue);

	/**
	 * Called when a value of COMMENT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param oldValue the object representing the removed value
	 */	
	public void COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.catalysis source, java.lang.String oldValue);

	/**
	 * Called when SHORT_DASH_NAME has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 */
	public void SHORT_DASH_NAMEChanged(fr.curie.BiNoM.pathways.biopax.catalysis source);

	/**
	 * Called when a value of SYNONYMS has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param newValue the object representing the new value
	 */	
	public void SYNONYMSAdded(fr.curie.BiNoM.pathways.biopax.catalysis source, java.lang.String newValue);

	/**
	 * Called when a value of SYNONYMS has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param oldValue the object representing the removed value
	 */	
	public void SYNONYMSRemoved(fr.curie.BiNoM.pathways.biopax.catalysis source, java.lang.String oldValue);

	/**
	 * Called when NAME has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 */
	public void NAMEChanged(fr.curie.BiNoM.pathways.biopax.catalysis source);

	/**
	 * Called when a value of XREF has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param newValue the object representing the new value
	 */	
	public void XREFAdded(fr.curie.BiNoM.pathways.biopax.catalysis source, fr.curie.BiNoM.pathways.biopax.xref newValue);

	/**
	 * Called when a value of XREF has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param oldValue the object representing the removed value
	 */
	public void XREFRemoved(fr.curie.BiNoM.pathways.biopax.catalysis source, fr.curie.BiNoM.pathways.biopax.xref oldValue);
		
	/**
	 * Called when a value of PARTICIPANTS has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param newValue the object representing the new value
	 */	
	public void PARTICIPANTSAdded(fr.curie.BiNoM.pathways.biopax.catalysis source, fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant newValue);

	/**
	 * Called when a value of PARTICIPANTS has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param oldValue the object representing the removed value
	 */
	public void PARTICIPANTSRemoved(fr.curie.BiNoM.pathways.biopax.catalysis source, fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant oldValue);
		
	/**
	 * Called when a value of PARTICIPANTS has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param newValue the object representing the new value
	 */	
	public void PARTICIPANTSAdded(fr.curie.BiNoM.pathways.biopax.catalysis source, fr.curie.BiNoM.pathways.biopax.entity newValue);

	/**
	 * Called when a value of PARTICIPANTS has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param oldValue the object representing the removed value
	 */
	public void PARTICIPANTSRemoved(fr.curie.BiNoM.pathways.biopax.catalysis source, fr.curie.BiNoM.pathways.biopax.entity oldValue);
		
	/**
	 * Called when a value of EVIDENCE has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param newValue the object representing the new value
	 */	
	public void EVIDENCEAdded(fr.curie.BiNoM.pathways.biopax.catalysis source, fr.curie.BiNoM.pathways.biopax.evidence newValue);

	/**
	 * Called when a value of EVIDENCE has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param oldValue the object representing the removed value
	 */
	public void EVIDENCERemoved(fr.curie.BiNoM.pathways.biopax.catalysis source, fr.curie.BiNoM.pathways.biopax.evidence oldValue);
		
	/**
	 * Called when a value of INTERACTION_DASH_TYPE has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param newValue the object representing the new value
	 */	
	public void INTERACTION_DASH_TYPEAdded(fr.curie.BiNoM.pathways.biopax.catalysis source, fr.curie.BiNoM.pathways.biopax.openControlledVocabulary newValue);

	/**
	 * Called when a value of INTERACTION_DASH_TYPE has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param oldValue the object representing the removed value
	 */
	public void INTERACTION_DASH_TYPERemoved(fr.curie.BiNoM.pathways.biopax.catalysis source, fr.curie.BiNoM.pathways.biopax.openControlledVocabulary oldValue);
		
	/**
	 * Called when CONTROLLER has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 */
	public void CONTROLLERChanged(fr.curie.BiNoM.pathways.biopax.catalysis source);

	/**
	 * Called when CONTROLLED has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 */
	public void CONTROLLEDChanged(fr.curie.BiNoM.pathways.biopax.catalysis source);

	/**
	 * Called when CONTROL_DASH_TYPE has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 */
	public void CONTROL_DASH_TYPEChanged(fr.curie.BiNoM.pathways.biopax.catalysis source);

	/**
	 * Called when DIRECTION has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 */
	public void DIRECTIONChanged(fr.curie.BiNoM.pathways.biopax.catalysis source);

	/**
	 * Called when a value of COFACTOR has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param newValue the object representing the new value
	 */	
	public void COFACTORAdded(fr.curie.BiNoM.pathways.biopax.catalysis source, fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant newValue);

	/**
	 * Called when a value of COFACTOR has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.catalysis
	 * @param oldValue the object representing the removed value
	 */
	public void COFACTORRemoved(fr.curie.BiNoM.pathways.biopax.catalysis source, fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant oldValue);
		
}