

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface CovalentBindingFeatureListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature source, java.lang.String oldValue);

	/**
	 * Called when a value of memberFeature has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature
	 * @param newValue the object representing the new value
	 */	
	public void memberFeatureAdded(fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature source, fr.curie.BiNoM.pathways.biopax.EntityFeature newValue);

	/**
	 * Called when a value of memberFeature has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature
	 * @param oldValue the object representing the removed value
	 */
	public void memberFeatureRemoved(fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature source, fr.curie.BiNoM.pathways.biopax.EntityFeature oldValue);
		
	/**
	 * Called when a value of featureLocation has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature
	 * @param newValue the object representing the new value
	 */	
	public void featureLocationAdded(fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature source, fr.curie.BiNoM.pathways.biopax.SequenceLocation newValue);

	/**
	 * Called when a value of featureLocation has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature
	 * @param oldValue the object representing the removed value
	 */
	public void featureLocationRemoved(fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature source, fr.curie.BiNoM.pathways.biopax.SequenceLocation oldValue);
		
	/**
	 * Called when a value of featureLocationType has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature
	 * @param newValue the object representing the new value
	 */	
	public void featureLocationTypeAdded(fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature source, fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary newValue);

	/**
	 * Called when a value of featureLocationType has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature
	 * @param oldValue the object representing the removed value
	 */
	public void featureLocationTypeRemoved(fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature source, fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary oldValue);
		
	/**
	 * Called when a value of evidence has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature
	 * @param newValue the object representing the new value
	 */	
	public void evidenceAdded(fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature source, fr.curie.BiNoM.pathways.biopax.Evidence newValue);

	/**
	 * Called when a value of evidence has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature
	 * @param oldValue the object representing the removed value
	 */
	public void evidenceRemoved(fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature source, fr.curie.BiNoM.pathways.biopax.Evidence oldValue);
		
	/**
	 * Called when intraMolecular has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature
	 */
	public void intraMolecularChanged(fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature source);

	/**
	 * Called when bindsTo has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature
	 */
	public void bindsToChanged(fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature source);

	/**
	 * Called when modificationType has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature
	 */
	public void modificationTypeChanged(fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature source);

}