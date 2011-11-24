

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
 * Implementations of this listener may be registered with instances of fr.curie.BiNoM.pathways.biopax.deltaGprimeO to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface deltaGprimeOListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of COMMENT has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.deltaGprimeO
	 * @param newValue the object representing the new value
	 */	
	public void COMMENTAdded(fr.curie.BiNoM.pathways.biopax.deltaGprimeO source, java.lang.String newValue);

	/**
	 * Called when a value of COMMENT has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.deltaGprimeO
	 * @param oldValue the object representing the removed value
	 */	
	public void COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.deltaGprimeO source, java.lang.String oldValue);

	/**
	 * Called when DELTA_DASH_G_DASH_PRIME_DASH_O has changed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.deltaGprimeO
	 */
	public void DELTA_DASH_G_DASH_PRIME_DASH_OChanged(fr.curie.BiNoM.pathways.biopax.deltaGprimeO source);

	/**
	 * Called when a value of TEMPERATURE has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.deltaGprimeO
	 * @param newValue the object representing the new value
	 */	
	public void TEMPERATUREAdded(fr.curie.BiNoM.pathways.biopax.deltaGprimeO source, java.lang.Float newValue);

	/**
	 * Called when a value of TEMPERATURE has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.deltaGprimeO
	 * @param oldValue the object representing the removed value
	 */	
	public void TEMPERATURERemoved(fr.curie.BiNoM.pathways.biopax.deltaGprimeO source, java.lang.Float oldValue);

	/**
	 * Called when a value of PH has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.deltaGprimeO
	 * @param newValue the object representing the new value
	 */	
	public void PHAdded(fr.curie.BiNoM.pathways.biopax.deltaGprimeO source, java.lang.Float newValue);

	/**
	 * Called when a value of PH has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.deltaGprimeO
	 * @param oldValue the object representing the removed value
	 */	
	public void PHRemoved(fr.curie.BiNoM.pathways.biopax.deltaGprimeO source, java.lang.Float oldValue);

	/**
	 * Called when a value of IONIC_DASH_STRENGTH has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.deltaGprimeO
	 * @param newValue the object representing the new value
	 */	
	public void IONIC_DASH_STRENGTHAdded(fr.curie.BiNoM.pathways.biopax.deltaGprimeO source, java.lang.Float newValue);

	/**
	 * Called when a value of IONIC_DASH_STRENGTH has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.deltaGprimeO
	 * @param oldValue the object representing the removed value
	 */	
	public void IONIC_DASH_STRENGTHRemoved(fr.curie.BiNoM.pathways.biopax.deltaGprimeO source, java.lang.Float oldValue);

	/**
	 * Called when a value of PMG has been added
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.deltaGprimeO
	 * @param newValue the object representing the new value
	 */	
	public void PMGAdded(fr.curie.BiNoM.pathways.biopax.deltaGprimeO source, java.lang.Float newValue);

	/**
	 * Called when a value of PMG has been removed
	 * @param source the affected instance of fr.curie.BiNoM.pathways.biopax.deltaGprimeO
	 * @param oldValue the object representing the removed value
	 */	
	public void PMGRemoved(fr.curie.BiNoM.pathways.biopax.deltaGprimeO source, java.lang.Float oldValue);

}