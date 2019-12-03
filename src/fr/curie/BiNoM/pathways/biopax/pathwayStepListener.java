

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.pathwayStep to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface pathwayStepListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of COMMENT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.pathwayStep
	 * @param newValue the object representing the new value
	 */	
	public void COMMENTAdded(fr.curie.BiNoM.pathways.biopax.pathwayStep source, java.lang.String newValue);

	/**
	 * Called when a value of COMMENT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.pathwayStep
	 * @param oldValue the object representing the removed value
	 */	
	public void COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.pathwayStep source, java.lang.String oldValue);

	/**
	 * Called when a value of NEXT_DASH_STEP has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.pathwayStep
	 * @param newValue the object representing the new value
	 */	
	public void NEXT_DASH_STEPAdded(fr.curie.BiNoM.pathways.biopax.pathwayStep source, fr.curie.BiNoM.pathways.biopax.pathwayStep newValue);

	/**
	 * Called when a value of NEXT_DASH_STEP has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.pathwayStep
	 * @param oldValue the object representing the removed value
	 */
	public void NEXT_DASH_STEPRemoved(fr.curie.BiNoM.pathways.biopax.pathwayStep source, fr.curie.BiNoM.pathways.biopax.pathwayStep oldValue);
		
	/**
	 * Called when a value of STEP_DASH_INTERACTIONS has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.pathwayStep
	 * @param newValue the object representing the new value
	 */	
	public void STEP_DASH_INTERACTIONSAdded(fr.curie.BiNoM.pathways.biopax.pathwayStep source, fr.curie.BiNoM.pathways.biopax.interaction newValue);

	/**
	 * Called when a value of STEP_DASH_INTERACTIONS has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.pathwayStep
	 * @param oldValue the object representing the removed value
	 */
	public void STEP_DASH_INTERACTIONSRemoved(fr.curie.BiNoM.pathways.biopax.pathwayStep source, fr.curie.BiNoM.pathways.biopax.interaction oldValue);
		
	/**
	 * Called when a value of STEP_DASH_INTERACTIONS has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.pathwayStep
	 * @param newValue the object representing the new value
	 */	
	public void STEP_DASH_INTERACTIONSAdded(fr.curie.BiNoM.pathways.biopax.pathwayStep source, fr.curie.BiNoM.pathways.biopax.pathway newValue);

	/**
	 * Called when a value of STEP_DASH_INTERACTIONS has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.pathwayStep
	 * @param oldValue the object representing the removed value
	 */
	public void STEP_DASH_INTERACTIONSRemoved(fr.curie.BiNoM.pathways.biopax.pathwayStep source, fr.curie.BiNoM.pathways.biopax.pathway oldValue);
		
}