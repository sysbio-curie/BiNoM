

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.Catalysis to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface CatalysisListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of dataSource has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param newValue the object representing the new value
	 */	
	public void dataSourceAdded(fr.curie.BiNoM.pathways.biopax.Catalysis source, fr.curie.BiNoM.pathways.biopax.Provenance newValue);

	/**
	 * Called when a value of dataSource has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param oldValue the object representing the removed value
	 */
	public void dataSourceRemoved(fr.curie.BiNoM.pathways.biopax.Catalysis source, fr.curie.BiNoM.pathways.biopax.Provenance oldValue);
		
	/**
	 * Called when a value of availability has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param newValue the object representing the new value
	 */	
	public void availabilityAdded(fr.curie.BiNoM.pathways.biopax.Catalysis source, java.lang.String newValue);

	/**
	 * Called when a value of availability has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param oldValue the object representing the removed value
	 */	
	public void availabilityRemoved(fr.curie.BiNoM.pathways.biopax.Catalysis source, java.lang.String oldValue);

	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.Catalysis source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.Catalysis source, java.lang.String oldValue);

	/**
	 * Called when a value of name has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param newValue the object representing the new value
	 */	
	public void nameAdded(fr.curie.BiNoM.pathways.biopax.Catalysis source, java.lang.String newValue);

	/**
	 * Called when a value of name has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param oldValue the object representing the removed value
	 */	
	public void nameRemoved(fr.curie.BiNoM.pathways.biopax.Catalysis source, java.lang.String oldValue);

	/**
	 * Called when a value of evidence has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param newValue the object representing the new value
	 */	
	public void evidenceAdded(fr.curie.BiNoM.pathways.biopax.Catalysis source, fr.curie.BiNoM.pathways.biopax.Evidence newValue);

	/**
	 * Called when a value of evidence has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param oldValue the object representing the removed value
	 */
	public void evidenceRemoved(fr.curie.BiNoM.pathways.biopax.Catalysis source, fr.curie.BiNoM.pathways.biopax.Evidence oldValue);
		
	/**
	 * Called when a value of xref has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param newValue the object representing the new value
	 */	
	public void xrefAdded(fr.curie.BiNoM.pathways.biopax.Catalysis source, fr.curie.BiNoM.pathways.biopax.Xref newValue);

	/**
	 * Called when a value of xref has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param oldValue the object representing the removed value
	 */
	public void xrefRemoved(fr.curie.BiNoM.pathways.biopax.Catalysis source, fr.curie.BiNoM.pathways.biopax.Xref oldValue);
		
	/**
	 * Called when interactionType has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 */
	public void interactionTypeChanged(fr.curie.BiNoM.pathways.biopax.Catalysis source);

	/**
	 * Called when a value of participant has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param newValue the object representing the new value
	 */	
	public void participantAdded(fr.curie.BiNoM.pathways.biopax.Catalysis source, fr.curie.BiNoM.pathways.biopax.Entity newValue);

	/**
	 * Called when a value of participant has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param oldValue the object representing the removed value
	 */
	public void participantRemoved(fr.curie.BiNoM.pathways.biopax.Catalysis source, fr.curie.BiNoM.pathways.biopax.Entity oldValue);
		
	/**
	 * Called when controlled has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 */
	public void controlledChanged(fr.curie.BiNoM.pathways.biopax.Catalysis source);

	/**
	 * Called when a value of controller has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param newValue the object representing the new value
	 */	
	public void controllerAdded(fr.curie.BiNoM.pathways.biopax.Catalysis source, fr.curie.BiNoM.pathways.biopax.Pathway newValue);

	/**
	 * Called when a value of controller has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param oldValue the object representing the removed value
	 */
	public void controllerRemoved(fr.curie.BiNoM.pathways.biopax.Catalysis source, fr.curie.BiNoM.pathways.biopax.Pathway oldValue);
		
	/**
	 * Called when a value of controller has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param newValue the object representing the new value
	 */	
	public void controllerAdded(fr.curie.BiNoM.pathways.biopax.Catalysis source, fr.curie.BiNoM.pathways.biopax.PhysicalEntity newValue);

	/**
	 * Called when a value of controller has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param oldValue the object representing the removed value
	 */
	public void controllerRemoved(fr.curie.BiNoM.pathways.biopax.Catalysis source, fr.curie.BiNoM.pathways.biopax.PhysicalEntity oldValue);
		
	/**
	 * Called when controlType has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 */
	public void controlTypeChanged(fr.curie.BiNoM.pathways.biopax.Catalysis source);

	/**
	 * Called when catalysisDirection has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 */
	public void catalysisDirectionChanged(fr.curie.BiNoM.pathways.biopax.Catalysis source);

	/**
	 * Called when a value of cofactor has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param newValue the object representing the new value
	 */	
	public void cofactorAdded(fr.curie.BiNoM.pathways.biopax.Catalysis source, fr.curie.BiNoM.pathways.biopax.PhysicalEntity newValue);

	/**
	 * Called when a value of cofactor has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Catalysis
	 * @param oldValue the object representing the removed value
	 */
	public void cofactorRemoved(fr.curie.BiNoM.pathways.biopax.Catalysis source, fr.curie.BiNoM.pathways.biopax.PhysicalEntity oldValue);
		
}