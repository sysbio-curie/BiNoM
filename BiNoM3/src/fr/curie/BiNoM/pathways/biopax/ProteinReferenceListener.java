

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.ProteinReference to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface ProteinReferenceListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.ProteinReference source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.ProteinReference source, java.lang.String oldValue);

	/**
	 * Called when a value of memberEntityReference has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 * @param newValue the object representing the new value
	 */	
	public void memberEntityReferenceAdded(fr.curie.BiNoM.pathways.biopax.ProteinReference source, fr.curie.BiNoM.pathways.biopax.EntityReference newValue);

	/**
	 * Called when a value of memberEntityReference has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 * @param oldValue the object representing the removed value
	 */
	public void memberEntityReferenceRemoved(fr.curie.BiNoM.pathways.biopax.ProteinReference source, fr.curie.BiNoM.pathways.biopax.EntityReference oldValue);
		
	/**
	 * Called when a value of memberEntityReference has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 * @param newValue the object representing the new value
	 */	
	public void memberEntityReferenceAdded(fr.curie.BiNoM.pathways.biopax.ProteinReference source, fr.curie.BiNoM.pathways.biopax.ProteinReference newValue);

	/**
	 * Called when a value of memberEntityReference has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 * @param oldValue the object representing the removed value
	 */
	public void memberEntityReferenceRemoved(fr.curie.BiNoM.pathways.biopax.ProteinReference source, fr.curie.BiNoM.pathways.biopax.ProteinReference oldValue);
		
	/**
	 * Called when entityReferenceType has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 */
	public void entityReferenceTypeChanged(fr.curie.BiNoM.pathways.biopax.ProteinReference source);

	/**
	 * Called when a value of entityFeature has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 * @param newValue the object representing the new value
	 */	
	public void entityFeatureAdded(fr.curie.BiNoM.pathways.biopax.ProteinReference source, fr.curie.BiNoM.pathways.biopax.EntityFeature newValue);

	/**
	 * Called when a value of entityFeature has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 * @param oldValue the object representing the removed value
	 */
	public void entityFeatureRemoved(fr.curie.BiNoM.pathways.biopax.ProteinReference source, fr.curie.BiNoM.pathways.biopax.EntityFeature oldValue);
		
	/**
	 * Called when a value of name has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 * @param newValue the object representing the new value
	 */	
	public void nameAdded(fr.curie.BiNoM.pathways.biopax.ProteinReference source, java.lang.String newValue);

	/**
	 * Called when a value of name has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 * @param oldValue the object representing the removed value
	 */	
	public void nameRemoved(fr.curie.BiNoM.pathways.biopax.ProteinReference source, java.lang.String oldValue);

	/**
	 * Called when a value of evidence has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 * @param newValue the object representing the new value
	 */	
	public void evidenceAdded(fr.curie.BiNoM.pathways.biopax.ProteinReference source, fr.curie.BiNoM.pathways.biopax.Evidence newValue);

	/**
	 * Called when a value of evidence has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 * @param oldValue the object representing the removed value
	 */
	public void evidenceRemoved(fr.curie.BiNoM.pathways.biopax.ProteinReference source, fr.curie.BiNoM.pathways.biopax.Evidence oldValue);
		
	/**
	 * Called when a value of xref has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 * @param newValue the object representing the new value
	 */	
	public void xrefAdded(fr.curie.BiNoM.pathways.biopax.ProteinReference source, fr.curie.BiNoM.pathways.biopax.Xref newValue);

	/**
	 * Called when a value of xref has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 * @param oldValue the object representing the removed value
	 */
	public void xrefRemoved(fr.curie.BiNoM.pathways.biopax.ProteinReference source, fr.curie.BiNoM.pathways.biopax.Xref oldValue);
		
	/**
	 * Called when sequence has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 */
	public void sequenceChanged(fr.curie.BiNoM.pathways.biopax.ProteinReference source);

	/**
	 * Called when organism has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.ProteinReference
	 */
	public void organismChanged(fr.curie.BiNoM.pathways.biopax.ProteinReference source);

}