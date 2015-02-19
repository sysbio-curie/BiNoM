

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.ExperimentalForm to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface ExperimentalFormListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ExperimentalForm
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.ExperimentalForm source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ExperimentalForm
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.ExperimentalForm source, java.lang.String oldValue);

	/**
	 * Called when a value of experimentalFeature has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ExperimentalForm
	 * @param newValue the object representing the new value
	 */	
	public void experimentalFeatureAdded(fr.curie.BiNoM.pathways.biopax.ExperimentalForm source, fr.curie.BiNoM.pathways.biopax.EntityFeature newValue);

	/**
	 * Called when a value of experimentalFeature has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ExperimentalForm
	 * @param oldValue the object representing the removed value
	 */
	public void experimentalFeatureRemoved(fr.curie.BiNoM.pathways.biopax.ExperimentalForm source, fr.curie.BiNoM.pathways.biopax.EntityFeature oldValue);
		
	/**
	 * Called when a value of experimentalFormDescription has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ExperimentalForm
	 * @param newValue the object representing the new value
	 */	
	public void experimentalFormDescriptionAdded(fr.curie.BiNoM.pathways.biopax.ExperimentalForm source, fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary newValue);

	/**
	 * Called when a value of experimentalFormDescription has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ExperimentalForm
	 * @param oldValue the object representing the removed value
	 */
	public void experimentalFormDescriptionRemoved(fr.curie.BiNoM.pathways.biopax.ExperimentalForm source, fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary oldValue);
		
	/**
	 * Called when a value of experimentalFormEntity has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ExperimentalForm
	 * @param newValue the object representing the new value
	 */	
	public void experimentalFormEntityAdded(fr.curie.BiNoM.pathways.biopax.ExperimentalForm source, fr.curie.BiNoM.pathways.biopax.Gene newValue);

	/**
	 * Called when a value of experimentalFormEntity has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ExperimentalForm
	 * @param oldValue the object representing the removed value
	 */
	public void experimentalFormEntityRemoved(fr.curie.BiNoM.pathways.biopax.ExperimentalForm source, fr.curie.BiNoM.pathways.biopax.Gene oldValue);
		
	/**
	 * Called when a value of experimentalFormEntity has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ExperimentalForm
	 * @param newValue the object representing the new value
	 */	
	public void experimentalFormEntityAdded(fr.curie.BiNoM.pathways.biopax.ExperimentalForm source, fr.curie.BiNoM.pathways.biopax.PhysicalEntity newValue);

	/**
	 * Called when a value of experimentalFormEntity has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ExperimentalForm
	 * @param oldValue the object representing the removed value
	 */
	public void experimentalFormEntityRemoved(fr.curie.BiNoM.pathways.biopax.ExperimentalForm source, fr.curie.BiNoM.pathways.biopax.PhysicalEntity oldValue);
		
}