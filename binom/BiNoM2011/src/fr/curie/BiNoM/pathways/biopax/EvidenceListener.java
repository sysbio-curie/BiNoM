

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.Evidence to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface EvidenceListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Evidence
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.Evidence source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Evidence
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.Evidence source, java.lang.String oldValue);

	/**
	 * Called when a value of confidence has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Evidence
	 * @param newValue the object representing the new value
	 */	
	public void confidenceAdded(fr.curie.BiNoM.pathways.biopax.Evidence source, fr.curie.BiNoM.pathways.biopax.Score newValue);

	/**
	 * Called when a value of confidence has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Evidence
	 * @param oldValue the object representing the removed value
	 */
	public void confidenceRemoved(fr.curie.BiNoM.pathways.biopax.Evidence source, fr.curie.BiNoM.pathways.biopax.Score oldValue);
		
	/**
	 * Called when a value of evidenceCode has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Evidence
	 * @param newValue the object representing the new value
	 */	
	public void evidenceCodeAdded(fr.curie.BiNoM.pathways.biopax.Evidence source, fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary newValue);

	/**
	 * Called when a value of evidenceCode has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Evidence
	 * @param oldValue the object representing the removed value
	 */
	public void evidenceCodeRemoved(fr.curie.BiNoM.pathways.biopax.Evidence source, fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary oldValue);
		
	/**
	 * Called when a value of experimentalForm has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Evidence
	 * @param newValue the object representing the new value
	 */	
	public void experimentalFormAdded(fr.curie.BiNoM.pathways.biopax.Evidence source, fr.curie.BiNoM.pathways.biopax.ExperimentalForm newValue);

	/**
	 * Called when a value of experimentalForm has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Evidence
	 * @param oldValue the object representing the removed value
	 */
	public void experimentalFormRemoved(fr.curie.BiNoM.pathways.biopax.Evidence source, fr.curie.BiNoM.pathways.biopax.ExperimentalForm oldValue);
		
	/**
	 * Called when a value of xref has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Evidence
	 * @param newValue the object representing the new value
	 */	
	public void xrefAdded(fr.curie.BiNoM.pathways.biopax.Evidence source, fr.curie.BiNoM.pathways.biopax.Xref newValue);

	/**
	 * Called when a value of xref has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Evidence
	 * @param oldValue the object representing the removed value
	 */
	public void xrefRemoved(fr.curie.BiNoM.pathways.biopax.Evidence source, fr.curie.BiNoM.pathways.biopax.Xref oldValue);
		
}