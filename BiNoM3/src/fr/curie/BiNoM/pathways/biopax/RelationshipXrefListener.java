

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.RelationshipXref to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface RelationshipXrefListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of comment has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.RelationshipXref
	 * @param newValue the object representing the new value
	 */	
	public void commentAdded(fr.curie.BiNoM.pathways.biopax.RelationshipXref source, java.lang.String newValue);

	/**
	 * Called when a value of comment has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.RelationshipXref
	 * @param oldValue the object representing the removed value
	 */	
	public void commentRemoved(fr.curie.BiNoM.pathways.biopax.RelationshipXref source, java.lang.String oldValue);

	/**
	 * Called when id has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.RelationshipXref
	 */
	public void idChanged(fr.curie.BiNoM.pathways.biopax.RelationshipXref source);

	/**
	 * Called when db has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.RelationshipXref
	 */
	public void dbChanged(fr.curie.BiNoM.pathways.biopax.RelationshipXref source);

	/**
	 * Called when idVersion has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.RelationshipXref
	 */
	public void idVersionChanged(fr.curie.BiNoM.pathways.biopax.RelationshipXref source);

	/**
	 * Called when dbVersion has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.RelationshipXref
	 */
	public void dbVersionChanged(fr.curie.BiNoM.pathways.biopax.RelationshipXref source);

	/**
	 * Called when a value of relationshipType has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.RelationshipXref
	 * @param newValue the object representing the new value
	 */	
	public void relationshipTypeAdded(fr.curie.BiNoM.pathways.biopax.RelationshipXref source, fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary newValue);

	/**
	 * Called when a value of relationshipType has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.RelationshipXref
	 * @param oldValue the object representing the removed value
	 */
	public void relationshipTypeRemoved(fr.curie.BiNoM.pathways.biopax.RelationshipXref source, fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary oldValue);
		
}