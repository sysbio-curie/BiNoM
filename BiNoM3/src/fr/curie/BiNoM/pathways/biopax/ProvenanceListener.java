

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.Provenance to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface ProvenanceListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Provenance
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.Provenance source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Provenance
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.Provenance source, java.lang.String oldValue);

	/**
	 * Called when a value of name has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Provenance
	 * @param newValue the object representing the new value
	 */	
	public void nameAdded(fr.curie.BiNoM.pathways.biopax.Provenance source, java.lang.String newValue);

	/**
	 * Called when a value of name has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Provenance
	 * @param oldValue the object representing the removed value
	 */	
	public void nameRemoved(fr.curie.BiNoM.pathways.biopax.Provenance source, java.lang.String oldValue);

	/**
	 * Called when a value of xref has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Provenance
	 * @param newValue the object representing the new value
	 */	
	public void xrefAdded(fr.curie.BiNoM.pathways.biopax.Provenance source, fr.curie.BiNoM.pathways.biopax.Xref newValue);

	/**
	 * Called when a value of xref has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Provenance
	 * @param oldValue the object representing the removed value
	 */
	public void xrefRemoved(fr.curie.BiNoM.pathways.biopax.Provenance source, fr.curie.BiNoM.pathways.biopax.Xref oldValue);
		
	/**
	 * Called when a value of xref has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Provenance
	 * @param newValue the object representing the new value
	 */	
	public void xrefAdded(fr.curie.BiNoM.pathways.biopax.Provenance source, fr.curie.BiNoM.pathways.biopax.PublicationXref newValue);

	/**
	 * Called when a value of xref has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Provenance
	 * @param oldValue the object representing the removed value
	 */
	public void xrefRemoved(fr.curie.BiNoM.pathways.biopax.Provenance source, fr.curie.BiNoM.pathways.biopax.PublicationXref oldValue);
		
	/**
	 * Called when a value of xref has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Provenance
	 * @param newValue the object representing the new value
	 */	
	public void xrefAdded(fr.curie.BiNoM.pathways.biopax.Provenance source, fr.curie.BiNoM.pathways.biopax.UnificationXref newValue);

	/**
	 * Called when a value of xref has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.Provenance
	 * @param oldValue the object representing the removed value
	 */
	public void xrefRemoved(fr.curie.BiNoM.pathways.biopax.Provenance source, fr.curie.BiNoM.pathways.biopax.UnificationXref oldValue);
		
}