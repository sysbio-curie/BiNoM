

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.GeneticInteraction to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface GeneticInteractionListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of dataSource has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param newValue the object representing the new value
	 */	
	public void dataSourceAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, fr.curie.BiNoM.pathways.biopax.Provenance newValue);

	/**
	 * Called when a value of dataSource has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param oldValue the object representing the removed value
	 */
	public void dataSourceRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, fr.curie.BiNoM.pathways.biopax.Provenance oldValue);
		
	/**
	 * Called when a value of availability has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param newValue the object representing the new value
	 */	
	public void availabilityAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, java.lang.String newValue);

	/**
	 * Called when a value of availability has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param oldValue the object representing the removed value
	 */	
	public void availabilityRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, java.lang.String oldValue);

	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, java.lang.String oldValue);

	/**
	 * Called when a value of name has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param newValue the object representing the new value
	 */	
	public void nameAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, java.lang.String newValue);

	/**
	 * Called when a value of name has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param oldValue the object representing the removed value
	 */	
	public void nameRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, java.lang.String oldValue);

	/**
	 * Called when a value of evidence has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param newValue the object representing the new value
	 */	
	public void evidenceAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, fr.curie.BiNoM.pathways.biopax.Evidence newValue);

	/**
	 * Called when a value of evidence has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param oldValue the object representing the removed value
	 */
	public void evidenceRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, fr.curie.BiNoM.pathways.biopax.Evidence oldValue);
		
	/**
	 * Called when a value of xref has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param newValue the object representing the new value
	 */	
	public void xrefAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, fr.curie.BiNoM.pathways.biopax.Xref newValue);

	/**
	 * Called when a value of xref has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param oldValue the object representing the removed value
	 */
	public void xrefRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, fr.curie.BiNoM.pathways.biopax.Xref oldValue);
		
	/**
	 * Called when interactionType has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 */
	public void interactionTypeChanged(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source);

	/**
	 * Called when a value of participant has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param newValue the object representing the new value
	 */	
	public void participantAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, fr.curie.BiNoM.pathways.biopax.Entity newValue);

	/**
	 * Called when a value of participant has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param oldValue the object representing the removed value
	 */
	public void participantRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, fr.curie.BiNoM.pathways.biopax.Entity oldValue);
		
	/**
	 * Called when a value of participant has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param newValue the object representing the new value
	 */	
	public void participantAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, fr.curie.BiNoM.pathways.biopax.Gene newValue);

	/**
	 * Called when a value of participant has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param oldValue the object representing the removed value
	 */
	public void participantRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, fr.curie.BiNoM.pathways.biopax.Gene oldValue);
		
	/**
	 * Called when a value of interactionScore has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param newValue the object representing the new value
	 */	
	public void interactionScoreAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, fr.curie.BiNoM.pathways.biopax.Score newValue);

	/**
	 * Called when a value of interactionScore has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 * @param oldValue the object representing the removed value
	 */
	public void interactionScoreRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source, fr.curie.BiNoM.pathways.biopax.Score oldValue);
		
	/**
	 * Called when phenotype has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.GeneticInteraction
	 */
	public void phenotypeChanged(fr.curie.BiNoM.pathways.biopax.GeneticInteraction source);

}