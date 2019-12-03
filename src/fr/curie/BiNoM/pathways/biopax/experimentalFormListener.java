

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.experimentalForm to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface experimentalFormListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of COMMENT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.experimentalForm
	 * @param newValue the object representing the new value
	 */	
	public void COMMENTAdded(fr.curie.BiNoM.pathways.biopax.experimentalForm source, java.lang.String newValue);

	/**
	 * Called when a value of COMMENT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.experimentalForm
	 * @param oldValue the object representing the removed value
	 */	
	public void COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.experimentalForm source, java.lang.String oldValue);

	/**
	 * Called when PARTICIPANT has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.experimentalForm
	 */
	public void PARTICIPANTChanged(fr.curie.BiNoM.pathways.biopax.experimentalForm source);

	/**
	 * Called when a value of EXPERIMENTAL_DASH_FORM_DASH_TYPE has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.experimentalForm
	 * @param newValue the object representing the new value
	 */	
	public void EXPERIMENTAL_DASH_FORM_DASH_TYPEAdded(fr.curie.BiNoM.pathways.biopax.experimentalForm source, fr.curie.BiNoM.pathways.biopax.openControlledVocabulary newValue);

	/**
	 * Called when a value of EXPERIMENTAL_DASH_FORM_DASH_TYPE has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.experimentalForm
	 * @param oldValue the object representing the removed value
	 */
	public void EXPERIMENTAL_DASH_FORM_DASH_TYPERemoved(fr.curie.BiNoM.pathways.biopax.experimentalForm source, fr.curie.BiNoM.pathways.biopax.openControlledVocabulary oldValue);
		
}