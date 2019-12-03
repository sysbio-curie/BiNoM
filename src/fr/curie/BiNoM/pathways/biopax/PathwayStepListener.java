

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.PathwayStep to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface PathwayStepListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PathwayStep
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.PathwayStep source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PathwayStep
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.PathwayStep source, java.lang.String oldValue);

	/**
	 * Called when a value of nextStep has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PathwayStep
	 * @param newValue the object representing the new value
	 */	
	public void nextStepAdded(fr.curie.BiNoM.pathways.biopax.PathwayStep source, fr.curie.BiNoM.pathways.biopax.PathwayStep newValue);

	/**
	 * Called when a value of nextStep has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PathwayStep
	 * @param oldValue the object representing the removed value
	 */
	public void nextStepRemoved(fr.curie.BiNoM.pathways.biopax.PathwayStep source, fr.curie.BiNoM.pathways.biopax.PathwayStep oldValue);
		
	/**
	 * Called when a value of stepProcess has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PathwayStep
	 * @param newValue the object representing the new value
	 */	
	public void stepProcessAdded(fr.curie.BiNoM.pathways.biopax.PathwayStep source, fr.curie.BiNoM.pathways.biopax.Interaction newValue);

	/**
	 * Called when a value of stepProcess has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PathwayStep
	 * @param oldValue the object representing the removed value
	 */
	public void stepProcessRemoved(fr.curie.BiNoM.pathways.biopax.PathwayStep source, fr.curie.BiNoM.pathways.biopax.Interaction oldValue);
		
	/**
	 * Called when a value of stepProcess has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PathwayStep
	 * @param newValue the object representing the new value
	 */	
	public void stepProcessAdded(fr.curie.BiNoM.pathways.biopax.PathwayStep source, fr.curie.BiNoM.pathways.biopax.Pathway newValue);

	/**
	 * Called when a value of stepProcess has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PathwayStep
	 * @param oldValue the object representing the removed value
	 */
	public void stepProcessRemoved(fr.curie.BiNoM.pathways.biopax.PathwayStep source, fr.curie.BiNoM.pathways.biopax.Pathway oldValue);
		
	/**
	 * Called when a value of evidence has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PathwayStep
	 * @param newValue the object representing the new value
	 */	
	public void evidenceAdded(fr.curie.BiNoM.pathways.biopax.PathwayStep source, fr.curie.BiNoM.pathways.biopax.Evidence newValue);

	/**
	 * Called when a value of evidence has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.PathwayStep
	 * @param oldValue the object representing the removed value
	 */
	public void evidenceRemoved(fr.curie.BiNoM.pathways.biopax.PathwayStep source, fr.curie.BiNoM.pathways.biopax.Evidence oldValue);
		
}