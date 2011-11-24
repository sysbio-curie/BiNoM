

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface BiochemicalPathwayStepListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep source, java.lang.String oldValue);

	/**
	 * Called when a value of nextStep has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep
	 * @param newValue the object representing the new value
	 */	
	public void nextStepAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep source, fr.curie.BiNoM.pathways.biopax.PathwayStep newValue);

	/**
	 * Called when a value of nextStep has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep
	 * @param oldValue the object representing the removed value
	 */
	public void nextStepRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep source, fr.curie.BiNoM.pathways.biopax.PathwayStep oldValue);
		
	/**
	 * Called when a value of stepProcess has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep
	 * @param newValue the object representing the new value
	 */	
	public void stepProcessAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep source, fr.curie.BiNoM.pathways.biopax.Control newValue);

	/**
	 * Called when a value of stepProcess has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep
	 * @param oldValue the object representing the removed value
	 */
	public void stepProcessRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep source, fr.curie.BiNoM.pathways.biopax.Control oldValue);
		
	/**
	 * Called when a value of stepProcess has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep
	 * @param newValue the object representing the new value
	 */	
	public void stepProcessAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep source, fr.curie.BiNoM.pathways.biopax.Interaction newValue);

	/**
	 * Called when a value of stepProcess has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep
	 * @param oldValue the object representing the removed value
	 */
	public void stepProcessRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep source, fr.curie.BiNoM.pathways.biopax.Interaction oldValue);
		
	/**
	 * Called when a value of stepProcess has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep
	 * @param newValue the object representing the new value
	 */	
	public void stepProcessAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep source, fr.curie.BiNoM.pathways.biopax.Pathway newValue);

	/**
	 * Called when a value of stepProcess has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep
	 * @param oldValue the object representing the removed value
	 */
	public void stepProcessRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep source, fr.curie.BiNoM.pathways.biopax.Pathway oldValue);
		
	/**
	 * Called when a value of evidence has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep
	 * @param newValue the object representing the new value
	 */	
	public void evidenceAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep source, fr.curie.BiNoM.pathways.biopax.Evidence newValue);

	/**
	 * Called when a value of evidence has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep
	 * @param oldValue the object representing the removed value
	 */
	public void evidenceRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep source, fr.curie.BiNoM.pathways.biopax.Evidence oldValue);
		
	/**
	 * Called when stepConversion has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep
	 */
	public void stepConversionChanged(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep source);

	/**
	 * Called when stepDirection has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep
	 */
	public void stepDirectionChanged(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep source);

}