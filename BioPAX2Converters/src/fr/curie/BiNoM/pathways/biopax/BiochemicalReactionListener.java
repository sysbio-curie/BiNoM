

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface BiochemicalReactionListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of dataSource has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void dataSourceAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.Provenance newValue);

	/**
	 * Called when a value of dataSource has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void dataSourceRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.Provenance oldValue);
		
	/**
	 * Called when a value of availability has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void availabilityAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, java.lang.String newValue);

	/**
	 * Called when a value of availability has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param oldValue the object representing the removed value
	 */	
	public void availabilityRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, java.lang.String oldValue);

	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, java.lang.String oldValue);

	/**
	 * Called when a value of name has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void nameAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, java.lang.String newValue);

	/**
	 * Called when a value of name has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param oldValue the object representing the removed value
	 */	
	public void nameRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, java.lang.String oldValue);

	/**
	 * Called when a value of evidence has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void evidenceAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.Evidence newValue);

	/**
	 * Called when a value of evidence has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void evidenceRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.Evidence oldValue);
		
	/**
	 * Called when a value of xref has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void xrefAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.Xref newValue);

	/**
	 * Called when a value of xref has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void xrefRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.Xref oldValue);
		
	/**
	 * Called when interactionType has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 */
	public void interactionTypeChanged(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source);

	/**
	 * Called when a value of participant has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void participantAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.Entity newValue);

	/**
	 * Called when a value of participant has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void participantRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.Entity oldValue);
		
	/**
	 * Called when a value of participant has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void participantAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.PhysicalEntity newValue);

	/**
	 * Called when a value of participant has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void participantRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.PhysicalEntity oldValue);
		
	/**
	 * Called when a value of participantStoichiometry has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void participantStoichiometryAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.Stoichiometry newValue);

	/**
	 * Called when a value of participantStoichiometry has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void participantStoichiometryRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.Stoichiometry oldValue);
		
	/**
	 * Called when spontaneous has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 */
	public void spontaneousChanged(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source);

	/**
	 * Called when a value of left has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void leftAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.PhysicalEntity newValue);

	/**
	 * Called when a value of left has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void leftRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.PhysicalEntity oldValue);
		
	/**
	 * Called when conversionDirection has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 */
	public void conversionDirectionChanged(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source);

	/**
	 * Called when a value of right has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void rightAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.PhysicalEntity newValue);

	/**
	 * Called when a value of right has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void rightRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.PhysicalEntity oldValue);
		
	/**
	 * Called when a value of deltaG has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void deltaGAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.DeltaG newValue);

	/**
	 * Called when a value of deltaG has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void deltaGRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.DeltaG oldValue);
		
	/**
	 * Called when a value of deltaH has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void deltaHAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, java.lang.Float newValue);

	/**
	 * Called when a value of deltaH has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param oldValue the object representing the removed value
	 */	
	public void deltaHRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, java.lang.Float oldValue);

	/**
	 * Called when a value of kEQ has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void kEQAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.KPrime newValue);

	/**
	 * Called when a value of kEQ has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param oldValue the object representing the removed value
	 */
	public void kEQRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, fr.curie.BiNoM.pathways.biopax.KPrime oldValue);
		
	/**
	 * Called when a value of eCNumber has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void eCNumberAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, java.lang.String newValue);

	/**
	 * Called when a value of eCNumber has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param oldValue the object representing the removed value
	 */	
	public void eCNumberRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, java.lang.String oldValue);

	/**
	 * Called when a value of deltaS has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param newValue the object representing the new value
	 */	
	public void deltaSAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, java.lang.Float newValue);

	/**
	 * Called when a value of deltaS has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.BiochemicalReaction
	 * @param oldValue the object representing the removed value
	 */	
	public void deltaSRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction source, java.lang.Float oldValue);

}