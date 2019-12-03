

package fr.curie.BiNoM.pathways.biopax;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for physicalEntity ontology class<br>
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this interface.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#physicalEntity)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : Definition: An entity with a physical structure. A pool of entities, not a specific molecular instance of an entity in a cell.
Comment: This class serves as the super-class for all physical entities, although its current set of subclasses is limited to molecules. As a highly abstract class in the ontology, instances of the physicalEntity class should never be created. Instead, more specific classes should be used.
Synonyms: part, interactor, object
Naming rationale: It's difficult to find a name that encompasses all of the subclasses of this class without being too general. E.g. PSI-MI uses 'interactor', BIND uses 'object', BioCyc uses 'chemicals'. physicalEntity seems to be a good name for this specialization of entity.
Examples: protein, small molecule, RNA^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public interface physicalEntity extends fr.curie.BiNoM.pathways.biopax.entity, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://www.biopax.org/release/biopax-level2.owl#physicalEntity");
	





}