

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.CellVocabulary to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface CellVocabularyListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CellVocabulary
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.CellVocabulary source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CellVocabulary
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.CellVocabulary source, java.lang.String oldValue);

	/**
	 * Called when a value of term has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CellVocabulary
	 * @param newValue the object representing the new value
	 */	
	public void termAdded(fr.curie.BiNoM.pathways.biopax.CellVocabulary source, java.lang.String newValue);

	/**
	 * Called when a value of term has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CellVocabulary
	 * @param oldValue the object representing the removed value
	 */	
	public void termRemoved(fr.curie.BiNoM.pathways.biopax.CellVocabulary source, java.lang.String oldValue);

	/**
	 * Called when a value of xref has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CellVocabulary
	 * @param newValue the object representing the new value
	 */	
	public void xrefAdded(fr.curie.BiNoM.pathways.biopax.CellVocabulary source, fr.curie.BiNoM.pathways.biopax.Xref newValue);

	/**
	 * Called when a value of xref has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CellVocabulary
	 * @param oldValue the object representing the removed value
	 */
	public void xrefRemoved(fr.curie.BiNoM.pathways.biopax.CellVocabulary source, fr.curie.BiNoM.pathways.biopax.Xref oldValue);
		
	/**
	 * Called when a value of xref has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CellVocabulary
	 * @param newValue the object representing the new value
	 */	
	public void xrefAdded(fr.curie.BiNoM.pathways.biopax.CellVocabulary source, fr.curie.BiNoM.pathways.biopax.UnificationXref newValue);

	/**
	 * Called when a value of xref has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.CellVocabulary
	 * @param oldValue the object representing the removed value
	 */
	public void xrefRemoved(fr.curie.BiNoM.pathways.biopax.CellVocabulary source, fr.curie.BiNoM.pathways.biopax.UnificationXref oldValue);
		
}