

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.evidence to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface evidenceListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of COMMENT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.evidence
	 * @param newValue the object representing the new value
	 */	
	public void COMMENTAdded(fr.curie.BiNoM.pathways.biopax.evidence source, java.lang.String newValue);

	/**
	 * Called when a value of COMMENT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.evidence
	 * @param oldValue the object representing the removed value
	 */	
	public void COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.evidence source, java.lang.String oldValue);

	/**
	 * Called when a value of EXPERIMENTAL_DASH_FORM has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.evidence
	 * @param newValue the object representing the new value
	 */	
	public void EXPERIMENTAL_DASH_FORMAdded(fr.curie.BiNoM.pathways.biopax.evidence source, fr.curie.BiNoM.pathways.biopax.experimentalForm newValue);

	/**
	 * Called when a value of EXPERIMENTAL_DASH_FORM has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.evidence
	 * @param oldValue the object representing the removed value
	 */
	public void EXPERIMENTAL_DASH_FORMRemoved(fr.curie.BiNoM.pathways.biopax.evidence source, fr.curie.BiNoM.pathways.biopax.experimentalForm oldValue);
		
	/**
	 * Called when a value of EVIDENCE_DASH_CODE has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.evidence
	 * @param newValue the object representing the new value
	 */	
	public void EVIDENCE_DASH_CODEAdded(fr.curie.BiNoM.pathways.biopax.evidence source, fr.curie.BiNoM.pathways.biopax.openControlledVocabulary newValue);

	/**
	 * Called when a value of EVIDENCE_DASH_CODE has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.evidence
	 * @param oldValue the object representing the removed value
	 */
	public void EVIDENCE_DASH_CODERemoved(fr.curie.BiNoM.pathways.biopax.evidence source, fr.curie.BiNoM.pathways.biopax.openControlledVocabulary oldValue);
		
	/**
	 * Called when a value of CONFIDENCE has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.evidence
	 * @param newValue the object representing the new value
	 */	
	public void CONFIDENCEAdded(fr.curie.BiNoM.pathways.biopax.evidence source, fr.curie.BiNoM.pathways.biopax.confidence newValue);

	/**
	 * Called when a value of CONFIDENCE has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.evidence
	 * @param oldValue the object representing the removed value
	 */
	public void CONFIDENCERemoved(fr.curie.BiNoM.pathways.biopax.evidence source, fr.curie.BiNoM.pathways.biopax.confidence oldValue);
		
	/**
	 * Called when a value of XREF has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.evidence
	 * @param newValue the object representing the new value
	 */	
	public void XREFAdded(fr.curie.BiNoM.pathways.biopax.evidence source, fr.curie.BiNoM.pathways.biopax.xref newValue);

	/**
	 * Called when a value of XREF has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.evidence
	 * @param oldValue the object representing the removed value
	 */
	public void XREFRemoved(fr.curie.BiNoM.pathways.biopax.evidence source, fr.curie.BiNoM.pathways.biopax.xref oldValue);
		
}