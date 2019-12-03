

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.FragmentFeature to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface FragmentFeatureListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.FragmentFeature
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.FragmentFeature source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.FragmentFeature
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.FragmentFeature source, java.lang.String oldValue);

	/**
	 * Called when a value of memberFeature has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.FragmentFeature
	 * @param newValue the object representing the new value
	 */	
	public void memberFeatureAdded(fr.curie.BiNoM.pathways.biopax.FragmentFeature source, fr.curie.BiNoM.pathways.biopax.EntityFeature newValue);

	/**
	 * Called when a value of memberFeature has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.FragmentFeature
	 * @param oldValue the object representing the removed value
	 */
	public void memberFeatureRemoved(fr.curie.BiNoM.pathways.biopax.FragmentFeature source, fr.curie.BiNoM.pathways.biopax.EntityFeature oldValue);
		
	/**
	 * Called when a value of featureLocation has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.FragmentFeature
	 * @param newValue the object representing the new value
	 */	
	public void featureLocationAdded(fr.curie.BiNoM.pathways.biopax.FragmentFeature source, fr.curie.BiNoM.pathways.biopax.SequenceLocation newValue);

	/**
	 * Called when a value of featureLocation has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.FragmentFeature
	 * @param oldValue the object representing the removed value
	 */
	public void featureLocationRemoved(fr.curie.BiNoM.pathways.biopax.FragmentFeature source, fr.curie.BiNoM.pathways.biopax.SequenceLocation oldValue);
		
	/**
	 * Called when a value of featureLocationType has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.FragmentFeature
	 * @param newValue the object representing the new value
	 */	
	public void featureLocationTypeAdded(fr.curie.BiNoM.pathways.biopax.FragmentFeature source, fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary newValue);

	/**
	 * Called when a value of featureLocationType has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.FragmentFeature
	 * @param oldValue the object representing the removed value
	 */
	public void featureLocationTypeRemoved(fr.curie.BiNoM.pathways.biopax.FragmentFeature source, fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary oldValue);
		
	/**
	 * Called when a value of evidence has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.FragmentFeature
	 * @param newValue the object representing the new value
	 */	
	public void evidenceAdded(fr.curie.BiNoM.pathways.biopax.FragmentFeature source, fr.curie.BiNoM.pathways.biopax.Evidence newValue);

	/**
	 * Called when a value of evidence has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.FragmentFeature
	 * @param oldValue the object representing the removed value
	 */
	public void evidenceRemoved(fr.curie.BiNoM.pathways.biopax.FragmentFeature source, fr.curie.BiNoM.pathways.biopax.Evidence oldValue);
		
}