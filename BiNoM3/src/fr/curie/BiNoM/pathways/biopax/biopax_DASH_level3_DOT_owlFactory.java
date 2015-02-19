

package fr.curie.BiNoM.pathways.biopax;

import com.ibm.adtech.jastor.*;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * Factory for instantiating objects for ontology classes in the biopax-level3.owl ontology.  The
 * get methods leave the model unchanged and return a Java view of the object in the model.  The create methods
 * may add certain baseline properties to the model such as rdf:type and any properties with hasValue restrictions.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : This is version 1.0 of the BioPAX Level 3 ontology.  The goal of the BioPAX group is to develop a common exchange format for biological pathway data.  More information is available at http://www.biopax.org.  This ontology is freely available under the LGPL (http://www.gnu.org/copyleft/lesser.html).^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public class biopax_DASH_level3_DOT_owlFactory extends com.ibm.adtech.jastor.ThingFactory { 



	/**
	 * Create a new instance of Complex.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Complex
	 * @param model the Jena Model.
	 */
	public static Complex createComplex(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.ComplexImpl.createComplex(resource,model);
	}
	
	/**
	 * Create a new instance of Complex.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Complex
	 * @param model the Jena Model.
	 */
	public static Complex createComplex(String uri, Model model) throws JastorException {
		Complex obj = fr.curie.BiNoM.pathways.biopax.ComplexImpl.createComplex(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Complex.  Leaves the model unchanged.
	 * @param uri The uri of the Complex
	 * @param model the Jena Model.
	 */
	public static Complex getComplex(String uri, Model model) throws JastorException {
		return getComplex(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Complex.  Leaves the model unchanged.
	 * @param resource The resource of the Complex
	 * @param model the Jena Model.
	 */
	public static Complex getComplex(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Complex.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.ComplexImpl obj = (fr.curie.BiNoM.pathways.biopax.ComplexImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.ComplexImpl.getComplex(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Complex for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Complex
	 * @param model the Jena Model
	 * @return a List of Complex
	 */
	public static java.util.List getAllComplex(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Complex.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getComplex(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of CellularLocationVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the CellularLocationVocabulary
	 * @param model the Jena Model.
	 */
	public static CellularLocationVocabulary createCellularLocationVocabulary(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.CellularLocationVocabularyImpl.createCellularLocationVocabulary(resource,model);
	}
	
	/**
	 * Create a new instance of CellularLocationVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the CellularLocationVocabulary
	 * @param model the Jena Model.
	 */
	public static CellularLocationVocabulary createCellularLocationVocabulary(String uri, Model model) throws JastorException {
		CellularLocationVocabulary obj = fr.curie.BiNoM.pathways.biopax.CellularLocationVocabularyImpl.createCellularLocationVocabulary(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of CellularLocationVocabulary.  Leaves the model unchanged.
	 * @param uri The uri of the CellularLocationVocabulary
	 * @param model the Jena Model.
	 */
	public static CellularLocationVocabulary getCellularLocationVocabulary(String uri, Model model) throws JastorException {
		return getCellularLocationVocabulary(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of CellularLocationVocabulary.  Leaves the model unchanged.
	 * @param resource The resource of the CellularLocationVocabulary
	 * @param model the Jena Model.
	 */
	public static CellularLocationVocabulary getCellularLocationVocabulary(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.CellularLocationVocabularyImpl obj = (fr.curie.BiNoM.pathways.biopax.CellularLocationVocabularyImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.CellularLocationVocabularyImpl.getCellularLocationVocabulary(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of CellularLocationVocabulary for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#CellularLocationVocabulary
	 * @param model the Jena Model
	 * @return a List of CellularLocationVocabulary
	 */
	public static java.util.List getAllCellularLocationVocabulary(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,CellularLocationVocabulary.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getCellularLocationVocabulary(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of EntityReferenceTypeVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the EntityReferenceTypeVocabulary
	 * @param model the Jena Model.
	 */
	public static EntityReferenceTypeVocabulary createEntityReferenceTypeVocabulary(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabularyImpl.createEntityReferenceTypeVocabulary(resource,model);
	}
	
	/**
	 * Create a new instance of EntityReferenceTypeVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the EntityReferenceTypeVocabulary
	 * @param model the Jena Model.
	 */
	public static EntityReferenceTypeVocabulary createEntityReferenceTypeVocabulary(String uri, Model model) throws JastorException {
		EntityReferenceTypeVocabulary obj = fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabularyImpl.createEntityReferenceTypeVocabulary(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of EntityReferenceTypeVocabulary.  Leaves the model unchanged.
	 * @param uri The uri of the EntityReferenceTypeVocabulary
	 * @param model the Jena Model.
	 */
	public static EntityReferenceTypeVocabulary getEntityReferenceTypeVocabulary(String uri, Model model) throws JastorException {
		return getEntityReferenceTypeVocabulary(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of EntityReferenceTypeVocabulary.  Leaves the model unchanged.
	 * @param resource The resource of the EntityReferenceTypeVocabulary
	 * @param model the Jena Model.
	 */
	public static EntityReferenceTypeVocabulary getEntityReferenceTypeVocabulary(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabularyImpl obj = (fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabularyImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabularyImpl.getEntityReferenceTypeVocabulary(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of EntityReferenceTypeVocabulary for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#EntityReferenceTypeVocabulary
	 * @param model the Jena Model
	 * @return a List of EntityReferenceTypeVocabulary
	 */
	public static java.util.List getAllEntityReferenceTypeVocabulary(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,EntityReferenceTypeVocabulary.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getEntityReferenceTypeVocabulary(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Modulation.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Modulation
	 * @param model the Jena Model.
	 */
	public static Modulation createModulation(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.ModulationImpl.createModulation(resource,model);
	}
	
	/**
	 * Create a new instance of Modulation.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Modulation
	 * @param model the Jena Model.
	 */
	public static Modulation createModulation(String uri, Model model) throws JastorException {
		Modulation obj = fr.curie.BiNoM.pathways.biopax.ModulationImpl.createModulation(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Modulation.  Leaves the model unchanged.
	 * @param uri The uri of the Modulation
	 * @param model the Jena Model.
	 */
	public static Modulation getModulation(String uri, Model model) throws JastorException {
		return getModulation(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Modulation.  Leaves the model unchanged.
	 * @param resource The resource of the Modulation
	 * @param model the Jena Model.
	 */
	public static Modulation getModulation(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Modulation.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.ModulationImpl obj = (fr.curie.BiNoM.pathways.biopax.ModulationImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.ModulationImpl.getModulation(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Modulation for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Modulation
	 * @param model the Jena Model
	 * @return a List of Modulation
	 */
	public static java.util.List getAllModulation(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Modulation.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getModulation(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of DeltaG.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the DeltaG
	 * @param model the Jena Model.
	 */
	public static DeltaG createDeltaG(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.DeltaGImpl.createDeltaG(resource,model);
	}
	
	/**
	 * Create a new instance of DeltaG.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the DeltaG
	 * @param model the Jena Model.
	 */
	public static DeltaG createDeltaG(String uri, Model model) throws JastorException {
		DeltaG obj = fr.curie.BiNoM.pathways.biopax.DeltaGImpl.createDeltaG(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of DeltaG.  Leaves the model unchanged.
	 * @param uri The uri of the DeltaG
	 * @param model the Jena Model.
	 */
	public static DeltaG getDeltaG(String uri, Model model) throws JastorException {
		return getDeltaG(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of DeltaG.  Leaves the model unchanged.
	 * @param resource The resource of the DeltaG
	 * @param model the Jena Model.
	 */
	public static DeltaG getDeltaG(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.DeltaG.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.DeltaGImpl obj = (fr.curie.BiNoM.pathways.biopax.DeltaGImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.DeltaGImpl.getDeltaG(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of DeltaG for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#DeltaG
	 * @param model the Jena Model
	 * @return a List of DeltaG
	 */
	public static java.util.List getAllDeltaG(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,DeltaG.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getDeltaG(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of RelationshipXref.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the RelationshipXref
	 * @param model the Jena Model.
	 */
	public static RelationshipXref createRelationshipXref(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl.createRelationshipXref(resource,model);
	}
	
	/**
	 * Create a new instance of RelationshipXref.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the RelationshipXref
	 * @param model the Jena Model.
	 */
	public static RelationshipXref createRelationshipXref(String uri, Model model) throws JastorException {
		RelationshipXref obj = fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl.createRelationshipXref(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of RelationshipXref.  Leaves the model unchanged.
	 * @param uri The uri of the RelationshipXref
	 * @param model the Jena Model.
	 */
	public static RelationshipXref getRelationshipXref(String uri, Model model) throws JastorException {
		return getRelationshipXref(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of RelationshipXref.  Leaves the model unchanged.
	 * @param resource The resource of the RelationshipXref
	 * @param model the Jena Model.
	 */
	public static RelationshipXref getRelationshipXref(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.RelationshipXref.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl obj = (fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl.getRelationshipXref(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of RelationshipXref for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#RelationshipXref
	 * @param model the Jena Model
	 * @return a List of RelationshipXref
	 */
	public static java.util.List getAllRelationshipXref(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,RelationshipXref.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getRelationshipXref(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Protein.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Protein
	 * @param model the Jena Model.
	 */
	public static Protein createProtein(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.ProteinImpl.createProtein(resource,model);
	}
	
	/**
	 * Create a new instance of Protein.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Protein
	 * @param model the Jena Model.
	 */
	public static Protein createProtein(String uri, Model model) throws JastorException {
		Protein obj = fr.curie.BiNoM.pathways.biopax.ProteinImpl.createProtein(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Protein.  Leaves the model unchanged.
	 * @param uri The uri of the Protein
	 * @param model the Jena Model.
	 */
	public static Protein getProtein(String uri, Model model) throws JastorException {
		return getProtein(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Protein.  Leaves the model unchanged.
	 * @param resource The resource of the Protein
	 * @param model the Jena Model.
	 */
	public static Protein getProtein(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Protein.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.ProteinImpl obj = (fr.curie.BiNoM.pathways.biopax.ProteinImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.ProteinImpl.getProtein(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Protein for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Protein
	 * @param model the Jena Model
	 * @return a List of Protein
	 */
	public static java.util.List getAllProtein(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Protein.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getProtein(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of ControlledVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the ControlledVocabulary
	 * @param model the Jena Model.
	 */
	public static ControlledVocabulary createControlledVocabulary(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.ControlledVocabularyImpl.createControlledVocabulary(resource,model);
	}
	
	/**
	 * Create a new instance of ControlledVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the ControlledVocabulary
	 * @param model the Jena Model.
	 */
	public static ControlledVocabulary createControlledVocabulary(String uri, Model model) throws JastorException {
		ControlledVocabulary obj = fr.curie.BiNoM.pathways.biopax.ControlledVocabularyImpl.createControlledVocabulary(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of ControlledVocabulary.  Leaves the model unchanged.
	 * @param uri The uri of the ControlledVocabulary
	 * @param model the Jena Model.
	 */
	public static ControlledVocabulary getControlledVocabulary(String uri, Model model) throws JastorException {
		return getControlledVocabulary(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of ControlledVocabulary.  Leaves the model unchanged.
	 * @param resource The resource of the ControlledVocabulary
	 * @param model the Jena Model.
	 */
	public static ControlledVocabulary getControlledVocabulary(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.ControlledVocabulary.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.ControlledVocabularyImpl obj = (fr.curie.BiNoM.pathways.biopax.ControlledVocabularyImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.ControlledVocabularyImpl.getControlledVocabulary(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of ControlledVocabulary for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#ControlledVocabulary
	 * @param model the Jena Model
	 * @return a List of ControlledVocabulary
	 */
	public static java.util.List getAllControlledVocabulary(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,ControlledVocabulary.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getControlledVocabulary(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Entity.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Entity
	 * @param model the Jena Model.
	 */
	public static Entity createEntity(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.EntityImpl.createEntity(resource,model);
	}
	
	/**
	 * Create a new instance of Entity.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Entity
	 * @param model the Jena Model.
	 */
	public static Entity createEntity(String uri, Model model) throws JastorException {
		Entity obj = fr.curie.BiNoM.pathways.biopax.EntityImpl.createEntity(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Entity.  Leaves the model unchanged.
	 * @param uri The uri of the Entity
	 * @param model the Jena Model.
	 */
	public static Entity getEntity(String uri, Model model) throws JastorException {
		return getEntity(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Entity.  Leaves the model unchanged.
	 * @param resource The resource of the Entity
	 * @param model the Jena Model.
	 */
	public static Entity getEntity(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Entity.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.EntityImpl obj = (fr.curie.BiNoM.pathways.biopax.EntityImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.EntityImpl.getEntity(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Entity for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Entity
	 * @param model the Jena Model
	 * @return a List of Entity
	 */
	public static java.util.List getAllEntity(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Entity.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getEntity(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of UtilityClass.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the UtilityClass
	 * @param model the Jena Model.
	 */
	public static UtilityClass createUtilityClass(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.UtilityClassImpl.createUtilityClass(resource,model);
	}
	
	/**
	 * Create a new instance of UtilityClass.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the UtilityClass
	 * @param model the Jena Model.
	 */
	public static UtilityClass createUtilityClass(String uri, Model model) throws JastorException {
		UtilityClass obj = fr.curie.BiNoM.pathways.biopax.UtilityClassImpl.createUtilityClass(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of UtilityClass.  Leaves the model unchanged.
	 * @param uri The uri of the UtilityClass
	 * @param model the Jena Model.
	 */
	public static UtilityClass getUtilityClass(String uri, Model model) throws JastorException {
		return getUtilityClass(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of UtilityClass.  Leaves the model unchanged.
	 * @param resource The resource of the UtilityClass
	 * @param model the Jena Model.
	 */
	public static UtilityClass getUtilityClass(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.UtilityClass.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.UtilityClassImpl obj = (fr.curie.BiNoM.pathways.biopax.UtilityClassImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.UtilityClassImpl.getUtilityClass(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of UtilityClass for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#UtilityClass
	 * @param model the Jena Model
	 * @return a List of UtilityClass
	 */
	public static java.util.List getAllUtilityClass(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,UtilityClass.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getUtilityClass(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of PhysicalEntity.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the PhysicalEntity
	 * @param model the Jena Model.
	 */
	public static PhysicalEntity createPhysicalEntity(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.PhysicalEntityImpl.createPhysicalEntity(resource,model);
	}
	
	/**
	 * Create a new instance of PhysicalEntity.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the PhysicalEntity
	 * @param model the Jena Model.
	 */
	public static PhysicalEntity createPhysicalEntity(String uri, Model model) throws JastorException {
		PhysicalEntity obj = fr.curie.BiNoM.pathways.biopax.PhysicalEntityImpl.createPhysicalEntity(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of PhysicalEntity.  Leaves the model unchanged.
	 * @param uri The uri of the PhysicalEntity
	 * @param model the Jena Model.
	 */
	public static PhysicalEntity getPhysicalEntity(String uri, Model model) throws JastorException {
		return getPhysicalEntity(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of PhysicalEntity.  Leaves the model unchanged.
	 * @param resource The resource of the PhysicalEntity
	 * @param model the Jena Model.
	 */
	public static PhysicalEntity getPhysicalEntity(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.PhysicalEntity.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.PhysicalEntityImpl obj = (fr.curie.BiNoM.pathways.biopax.PhysicalEntityImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.PhysicalEntityImpl.getPhysicalEntity(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of PhysicalEntity for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#PhysicalEntity
	 * @param model the Jena Model
	 * @return a List of PhysicalEntity
	 */
	public static java.util.List getAllPhysicalEntity(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,PhysicalEntity.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getPhysicalEntity(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Xref.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Xref
	 * @param model the Jena Model.
	 */
	public static Xref createXref(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.XrefImpl.createXref(resource,model);
	}
	
	/**
	 * Create a new instance of Xref.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Xref
	 * @param model the Jena Model.
	 */
	public static Xref createXref(String uri, Model model) throws JastorException {
		Xref obj = fr.curie.BiNoM.pathways.biopax.XrefImpl.createXref(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Xref.  Leaves the model unchanged.
	 * @param uri The uri of the Xref
	 * @param model the Jena Model.
	 */
	public static Xref getXref(String uri, Model model) throws JastorException {
		return getXref(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Xref.  Leaves the model unchanged.
	 * @param resource The resource of the Xref
	 * @param model the Jena Model.
	 */
	public static Xref getXref(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Xref.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.XrefImpl obj = (fr.curie.BiNoM.pathways.biopax.XrefImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.XrefImpl.getXref(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Xref for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Xref
	 * @param model the Jena Model
	 * @return a List of Xref
	 */
	public static java.util.List getAllXref(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Xref.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getXref(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Interaction.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Interaction
	 * @param model the Jena Model.
	 */
	public static Interaction createInteraction(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.InteractionImpl.createInteraction(resource,model);
	}
	
	/**
	 * Create a new instance of Interaction.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Interaction
	 * @param model the Jena Model.
	 */
	public static Interaction createInteraction(String uri, Model model) throws JastorException {
		Interaction obj = fr.curie.BiNoM.pathways.biopax.InteractionImpl.createInteraction(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Interaction.  Leaves the model unchanged.
	 * @param uri The uri of the Interaction
	 * @param model the Jena Model.
	 */
	public static Interaction getInteraction(String uri, Model model) throws JastorException {
		return getInteraction(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Interaction.  Leaves the model unchanged.
	 * @param resource The resource of the Interaction
	 * @param model the Jena Model.
	 */
	public static Interaction getInteraction(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Interaction.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.InteractionImpl obj = (fr.curie.BiNoM.pathways.biopax.InteractionImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.InteractionImpl.getInteraction(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Interaction for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Interaction
	 * @param model the Jena Model
	 * @return a List of Interaction
	 */
	public static java.util.List getAllInteraction(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Interaction.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getInteraction(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Provenance.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Provenance
	 * @param model the Jena Model.
	 */
	public static Provenance createProvenance(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.ProvenanceImpl.createProvenance(resource,model);
	}
	
	/**
	 * Create a new instance of Provenance.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Provenance
	 * @param model the Jena Model.
	 */
	public static Provenance createProvenance(String uri, Model model) throws JastorException {
		Provenance obj = fr.curie.BiNoM.pathways.biopax.ProvenanceImpl.createProvenance(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Provenance.  Leaves the model unchanged.
	 * @param uri The uri of the Provenance
	 * @param model the Jena Model.
	 */
	public static Provenance getProvenance(String uri, Model model) throws JastorException {
		return getProvenance(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Provenance.  Leaves the model unchanged.
	 * @param resource The resource of the Provenance
	 * @param model the Jena Model.
	 */
	public static Provenance getProvenance(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Provenance.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.ProvenanceImpl obj = (fr.curie.BiNoM.pathways.biopax.ProvenanceImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.ProvenanceImpl.getProvenance(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Provenance for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Provenance
	 * @param model the Jena Model
	 * @return a List of Provenance
	 */
	public static java.util.List getAllProvenance(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Provenance.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getProvenance(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of SequenceModificationVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the SequenceModificationVocabulary
	 * @param model the Jena Model.
	 */
	public static SequenceModificationVocabulary createSequenceModificationVocabulary(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabularyImpl.createSequenceModificationVocabulary(resource,model);
	}
	
	/**
	 * Create a new instance of SequenceModificationVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the SequenceModificationVocabulary
	 * @param model the Jena Model.
	 */
	public static SequenceModificationVocabulary createSequenceModificationVocabulary(String uri, Model model) throws JastorException {
		SequenceModificationVocabulary obj = fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabularyImpl.createSequenceModificationVocabulary(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of SequenceModificationVocabulary.  Leaves the model unchanged.
	 * @param uri The uri of the SequenceModificationVocabulary
	 * @param model the Jena Model.
	 */
	public static SequenceModificationVocabulary getSequenceModificationVocabulary(String uri, Model model) throws JastorException {
		return getSequenceModificationVocabulary(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of SequenceModificationVocabulary.  Leaves the model unchanged.
	 * @param resource The resource of the SequenceModificationVocabulary
	 * @param model the Jena Model.
	 */
	public static SequenceModificationVocabulary getSequenceModificationVocabulary(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabularyImpl obj = (fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabularyImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabularyImpl.getSequenceModificationVocabulary(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of SequenceModificationVocabulary for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#SequenceModificationVocabulary
	 * @param model the Jena Model
	 * @return a List of SequenceModificationVocabulary
	 */
	public static java.util.List getAllSequenceModificationVocabulary(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,SequenceModificationVocabulary.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getSequenceModificationVocabulary(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of ExperimentalForm.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the ExperimentalForm
	 * @param model the Jena Model.
	 */
	public static ExperimentalForm createExperimentalForm(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.ExperimentalFormImpl.createExperimentalForm(resource,model);
	}
	
	/**
	 * Create a new instance of ExperimentalForm.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the ExperimentalForm
	 * @param model the Jena Model.
	 */
	public static ExperimentalForm createExperimentalForm(String uri, Model model) throws JastorException {
		ExperimentalForm obj = fr.curie.BiNoM.pathways.biopax.ExperimentalFormImpl.createExperimentalForm(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of ExperimentalForm.  Leaves the model unchanged.
	 * @param uri The uri of the ExperimentalForm
	 * @param model the Jena Model.
	 */
	public static ExperimentalForm getExperimentalForm(String uri, Model model) throws JastorException {
		return getExperimentalForm(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of ExperimentalForm.  Leaves the model unchanged.
	 * @param resource The resource of the ExperimentalForm
	 * @param model the Jena Model.
	 */
	public static ExperimentalForm getExperimentalForm(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.ExperimentalForm.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.ExperimentalFormImpl obj = (fr.curie.BiNoM.pathways.biopax.ExperimentalFormImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.ExperimentalFormImpl.getExperimentalForm(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of ExperimentalForm for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#ExperimentalForm
	 * @param model the Jena Model
	 * @return a List of ExperimentalForm
	 */
	public static java.util.List getAllExperimentalForm(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,ExperimentalForm.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getExperimentalForm(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of RnaRegionReference.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the RnaRegionReference
	 * @param model the Jena Model.
	 */
	public static RnaRegionReference createRnaRegionReference(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.RnaRegionReferenceImpl.createRnaRegionReference(resource,model);
	}
	
	/**
	 * Create a new instance of RnaRegionReference.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the RnaRegionReference
	 * @param model the Jena Model.
	 */
	public static RnaRegionReference createRnaRegionReference(String uri, Model model) throws JastorException {
		RnaRegionReference obj = fr.curie.BiNoM.pathways.biopax.RnaRegionReferenceImpl.createRnaRegionReference(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of RnaRegionReference.  Leaves the model unchanged.
	 * @param uri The uri of the RnaRegionReference
	 * @param model the Jena Model.
	 */
	public static RnaRegionReference getRnaRegionReference(String uri, Model model) throws JastorException {
		return getRnaRegionReference(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of RnaRegionReference.  Leaves the model unchanged.
	 * @param resource The resource of the RnaRegionReference
	 * @param model the Jena Model.
	 */
	public static RnaRegionReference getRnaRegionReference(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.RnaRegionReference.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.RnaRegionReferenceImpl obj = (fr.curie.BiNoM.pathways.biopax.RnaRegionReferenceImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.RnaRegionReferenceImpl.getRnaRegionReference(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of RnaRegionReference for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#RnaRegionReference
	 * @param model the Jena Model
	 * @return a List of RnaRegionReference
	 */
	public static java.util.List getAllRnaRegionReference(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,RnaRegionReference.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getRnaRegionReference(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of TemplateReactionRegulation.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the TemplateReactionRegulation
	 * @param model the Jena Model.
	 */
	public static TemplateReactionRegulation createTemplateReactionRegulation(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.createTemplateReactionRegulation(resource,model);
	}
	
	/**
	 * Create a new instance of TemplateReactionRegulation.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the TemplateReactionRegulation
	 * @param model the Jena Model.
	 */
	public static TemplateReactionRegulation createTemplateReactionRegulation(String uri, Model model) throws JastorException {
		TemplateReactionRegulation obj = fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.createTemplateReactionRegulation(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of TemplateReactionRegulation.  Leaves the model unchanged.
	 * @param uri The uri of the TemplateReactionRegulation
	 * @param model the Jena Model.
	 */
	public static TemplateReactionRegulation getTemplateReactionRegulation(String uri, Model model) throws JastorException {
		return getTemplateReactionRegulation(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of TemplateReactionRegulation.  Leaves the model unchanged.
	 * @param resource The resource of the TemplateReactionRegulation
	 * @param model the Jena Model.
	 */
	public static TemplateReactionRegulation getTemplateReactionRegulation(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulation.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl obj = (fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.getTemplateReactionRegulation(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of TemplateReactionRegulation for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#TemplateReactionRegulation
	 * @param model the Jena Model
	 * @return a List of TemplateReactionRegulation
	 */
	public static java.util.List getAllTemplateReactionRegulation(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,TemplateReactionRegulation.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getTemplateReactionRegulation(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of BiochemicalReaction.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the BiochemicalReaction
	 * @param model the Jena Model.
	 */
	public static BiochemicalReaction createBiochemicalReaction(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.BiochemicalReactionImpl.createBiochemicalReaction(resource,model);
	}
	
	/**
	 * Create a new instance of BiochemicalReaction.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the BiochemicalReaction
	 * @param model the Jena Model.
	 */
	public static BiochemicalReaction createBiochemicalReaction(String uri, Model model) throws JastorException {
		BiochemicalReaction obj = fr.curie.BiNoM.pathways.biopax.BiochemicalReactionImpl.createBiochemicalReaction(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of BiochemicalReaction.  Leaves the model unchanged.
	 * @param uri The uri of the BiochemicalReaction
	 * @param model the Jena Model.
	 */
	public static BiochemicalReaction getBiochemicalReaction(String uri, Model model) throws JastorException {
		return getBiochemicalReaction(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of BiochemicalReaction.  Leaves the model unchanged.
	 * @param resource The resource of the BiochemicalReaction
	 * @param model the Jena Model.
	 */
	public static BiochemicalReaction getBiochemicalReaction(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.BiochemicalReaction.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.BiochemicalReactionImpl obj = (fr.curie.BiNoM.pathways.biopax.BiochemicalReactionImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.BiochemicalReactionImpl.getBiochemicalReaction(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of BiochemicalReaction for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#BiochemicalReaction
	 * @param model the Jena Model
	 * @return a List of BiochemicalReaction
	 */
	public static java.util.List getAllBiochemicalReaction(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,BiochemicalReaction.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getBiochemicalReaction(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Conversion.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Conversion
	 * @param model the Jena Model.
	 */
	public static Conversion createConversion(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.ConversionImpl.createConversion(resource,model);
	}
	
	/**
	 * Create a new instance of Conversion.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Conversion
	 * @param model the Jena Model.
	 */
	public static Conversion createConversion(String uri, Model model) throws JastorException {
		Conversion obj = fr.curie.BiNoM.pathways.biopax.ConversionImpl.createConversion(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Conversion.  Leaves the model unchanged.
	 * @param uri The uri of the Conversion
	 * @param model the Jena Model.
	 */
	public static Conversion getConversion(String uri, Model model) throws JastorException {
		return getConversion(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Conversion.  Leaves the model unchanged.
	 * @param resource The resource of the Conversion
	 * @param model the Jena Model.
	 */
	public static Conversion getConversion(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Conversion.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.ConversionImpl obj = (fr.curie.BiNoM.pathways.biopax.ConversionImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.ConversionImpl.getConversion(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Conversion for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Conversion
	 * @param model the Jena Model
	 * @return a List of Conversion
	 */
	public static java.util.List getAllConversion(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Conversion.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getConversion(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of DnaRegionReference.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the DnaRegionReference
	 * @param model the Jena Model.
	 */
	public static DnaRegionReference createDnaRegionReference(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.DnaRegionReferenceImpl.createDnaRegionReference(resource,model);
	}
	
	/**
	 * Create a new instance of DnaRegionReference.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the DnaRegionReference
	 * @param model the Jena Model.
	 */
	public static DnaRegionReference createDnaRegionReference(String uri, Model model) throws JastorException {
		DnaRegionReference obj = fr.curie.BiNoM.pathways.biopax.DnaRegionReferenceImpl.createDnaRegionReference(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of DnaRegionReference.  Leaves the model unchanged.
	 * @param uri The uri of the DnaRegionReference
	 * @param model the Jena Model.
	 */
	public static DnaRegionReference getDnaRegionReference(String uri, Model model) throws JastorException {
		return getDnaRegionReference(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of DnaRegionReference.  Leaves the model unchanged.
	 * @param resource The resource of the DnaRegionReference
	 * @param model the Jena Model.
	 */
	public static DnaRegionReference getDnaRegionReference(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.DnaRegionReference.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.DnaRegionReferenceImpl obj = (fr.curie.BiNoM.pathways.biopax.DnaRegionReferenceImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.DnaRegionReferenceImpl.getDnaRegionReference(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of DnaRegionReference for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#DnaRegionReference
	 * @param model the Jena Model
	 * @return a List of DnaRegionReference
	 */
	public static java.util.List getAllDnaRegionReference(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,DnaRegionReference.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getDnaRegionReference(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of ModificationFeature.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the ModificationFeature
	 * @param model the Jena Model.
	 */
	public static ModificationFeature createModificationFeature(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl.createModificationFeature(resource,model);
	}
	
	/**
	 * Create a new instance of ModificationFeature.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the ModificationFeature
	 * @param model the Jena Model.
	 */
	public static ModificationFeature createModificationFeature(String uri, Model model) throws JastorException {
		ModificationFeature obj = fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl.createModificationFeature(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of ModificationFeature.  Leaves the model unchanged.
	 * @param uri The uri of the ModificationFeature
	 * @param model the Jena Model.
	 */
	public static ModificationFeature getModificationFeature(String uri, Model model) throws JastorException {
		return getModificationFeature(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of ModificationFeature.  Leaves the model unchanged.
	 * @param resource The resource of the ModificationFeature
	 * @param model the Jena Model.
	 */
	public static ModificationFeature getModificationFeature(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.ModificationFeature.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl obj = (fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl.getModificationFeature(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of ModificationFeature for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#ModificationFeature
	 * @param model the Jena Model
	 * @return a List of ModificationFeature
	 */
	public static java.util.List getAllModificationFeature(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,ModificationFeature.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getModificationFeature(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of TemplateReaction.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the TemplateReaction
	 * @param model the Jena Model.
	 */
	public static TemplateReaction createTemplateReaction(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.TemplateReactionImpl.createTemplateReaction(resource,model);
	}
	
	/**
	 * Create a new instance of TemplateReaction.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the TemplateReaction
	 * @param model the Jena Model.
	 */
	public static TemplateReaction createTemplateReaction(String uri, Model model) throws JastorException {
		TemplateReaction obj = fr.curie.BiNoM.pathways.biopax.TemplateReactionImpl.createTemplateReaction(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of TemplateReaction.  Leaves the model unchanged.
	 * @param uri The uri of the TemplateReaction
	 * @param model the Jena Model.
	 */
	public static TemplateReaction getTemplateReaction(String uri, Model model) throws JastorException {
		return getTemplateReaction(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of TemplateReaction.  Leaves the model unchanged.
	 * @param resource The resource of the TemplateReaction
	 * @param model the Jena Model.
	 */
	public static TemplateReaction getTemplateReaction(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.TemplateReaction.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.TemplateReactionImpl obj = (fr.curie.BiNoM.pathways.biopax.TemplateReactionImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.TemplateReactionImpl.getTemplateReaction(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of TemplateReaction for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#TemplateReaction
	 * @param model the Jena Model
	 * @return a List of TemplateReaction
	 */
	public static java.util.List getAllTemplateReaction(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,TemplateReaction.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getTemplateReaction(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Transport.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Transport
	 * @param model the Jena Model.
	 */
	public static Transport createTransport(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.TransportImpl.createTransport(resource,model);
	}
	
	/**
	 * Create a new instance of Transport.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Transport
	 * @param model the Jena Model.
	 */
	public static Transport createTransport(String uri, Model model) throws JastorException {
		Transport obj = fr.curie.BiNoM.pathways.biopax.TransportImpl.createTransport(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Transport.  Leaves the model unchanged.
	 * @param uri The uri of the Transport
	 * @param model the Jena Model.
	 */
	public static Transport getTransport(String uri, Model model) throws JastorException {
		return getTransport(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Transport.  Leaves the model unchanged.
	 * @param resource The resource of the Transport
	 * @param model the Jena Model.
	 */
	public static Transport getTransport(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Transport.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.TransportImpl obj = (fr.curie.BiNoM.pathways.biopax.TransportImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.TransportImpl.getTransport(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Transport for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Transport
	 * @param model the Jena Model
	 * @return a List of Transport
	 */
	public static java.util.List getAllTransport(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Transport.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getTransport(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of ComplexAssembly.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the ComplexAssembly
	 * @param model the Jena Model.
	 */
	public static ComplexAssembly createComplexAssembly(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.ComplexAssemblyImpl.createComplexAssembly(resource,model);
	}
	
	/**
	 * Create a new instance of ComplexAssembly.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the ComplexAssembly
	 * @param model the Jena Model.
	 */
	public static ComplexAssembly createComplexAssembly(String uri, Model model) throws JastorException {
		ComplexAssembly obj = fr.curie.BiNoM.pathways.biopax.ComplexAssemblyImpl.createComplexAssembly(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of ComplexAssembly.  Leaves the model unchanged.
	 * @param uri The uri of the ComplexAssembly
	 * @param model the Jena Model.
	 */
	public static ComplexAssembly getComplexAssembly(String uri, Model model) throws JastorException {
		return getComplexAssembly(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of ComplexAssembly.  Leaves the model unchanged.
	 * @param resource The resource of the ComplexAssembly
	 * @param model the Jena Model.
	 */
	public static ComplexAssembly getComplexAssembly(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.ComplexAssembly.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.ComplexAssemblyImpl obj = (fr.curie.BiNoM.pathways.biopax.ComplexAssemblyImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.ComplexAssemblyImpl.getComplexAssembly(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of ComplexAssembly for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#ComplexAssembly
	 * @param model the Jena Model
	 * @return a List of ComplexAssembly
	 */
	public static java.util.List getAllComplexAssembly(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,ComplexAssembly.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getComplexAssembly(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of SmallMolecule.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the SmallMolecule
	 * @param model the Jena Model.
	 */
	public static SmallMolecule createSmallMolecule(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.SmallMoleculeImpl.createSmallMolecule(resource,model);
	}
	
	/**
	 * Create a new instance of SmallMolecule.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the SmallMolecule
	 * @param model the Jena Model.
	 */
	public static SmallMolecule createSmallMolecule(String uri, Model model) throws JastorException {
		SmallMolecule obj = fr.curie.BiNoM.pathways.biopax.SmallMoleculeImpl.createSmallMolecule(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of SmallMolecule.  Leaves the model unchanged.
	 * @param uri The uri of the SmallMolecule
	 * @param model the Jena Model.
	 */
	public static SmallMolecule getSmallMolecule(String uri, Model model) throws JastorException {
		return getSmallMolecule(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of SmallMolecule.  Leaves the model unchanged.
	 * @param resource The resource of the SmallMolecule
	 * @param model the Jena Model.
	 */
	public static SmallMolecule getSmallMolecule(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.SmallMolecule.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.SmallMoleculeImpl obj = (fr.curie.BiNoM.pathways.biopax.SmallMoleculeImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.SmallMoleculeImpl.getSmallMolecule(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of SmallMolecule for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#SmallMolecule
	 * @param model the Jena Model
	 * @return a List of SmallMolecule
	 */
	public static java.util.List getAllSmallMolecule(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,SmallMolecule.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getSmallMolecule(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of DnaRegion.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the DnaRegion
	 * @param model the Jena Model.
	 */
	public static DnaRegion createDnaRegion(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.DnaRegionImpl.createDnaRegion(resource,model);
	}
	
	/**
	 * Create a new instance of DnaRegion.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the DnaRegion
	 * @param model the Jena Model.
	 */
	public static DnaRegion createDnaRegion(String uri, Model model) throws JastorException {
		DnaRegion obj = fr.curie.BiNoM.pathways.biopax.DnaRegionImpl.createDnaRegion(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of DnaRegion.  Leaves the model unchanged.
	 * @param uri The uri of the DnaRegion
	 * @param model the Jena Model.
	 */
	public static DnaRegion getDnaRegion(String uri, Model model) throws JastorException {
		return getDnaRegion(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of DnaRegion.  Leaves the model unchanged.
	 * @param resource The resource of the DnaRegion
	 * @param model the Jena Model.
	 */
	public static DnaRegion getDnaRegion(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.DnaRegion.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.DnaRegionImpl obj = (fr.curie.BiNoM.pathways.biopax.DnaRegionImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.DnaRegionImpl.getDnaRegion(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of DnaRegion for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#DnaRegion
	 * @param model the Jena Model
	 * @return a List of DnaRegion
	 */
	public static java.util.List getAllDnaRegion(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,DnaRegion.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getDnaRegion(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of PathwayStep.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the PathwayStep
	 * @param model the Jena Model.
	 */
	public static PathwayStep createPathwayStep(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.PathwayStepImpl.createPathwayStep(resource,model);
	}
	
	/**
	 * Create a new instance of PathwayStep.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the PathwayStep
	 * @param model the Jena Model.
	 */
	public static PathwayStep createPathwayStep(String uri, Model model) throws JastorException {
		PathwayStep obj = fr.curie.BiNoM.pathways.biopax.PathwayStepImpl.createPathwayStep(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of PathwayStep.  Leaves the model unchanged.
	 * @param uri The uri of the PathwayStep
	 * @param model the Jena Model.
	 */
	public static PathwayStep getPathwayStep(String uri, Model model) throws JastorException {
		return getPathwayStep(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of PathwayStep.  Leaves the model unchanged.
	 * @param resource The resource of the PathwayStep
	 * @param model the Jena Model.
	 */
	public static PathwayStep getPathwayStep(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.PathwayStep.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.PathwayStepImpl obj = (fr.curie.BiNoM.pathways.biopax.PathwayStepImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.PathwayStepImpl.getPathwayStep(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of PathwayStep for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#PathwayStep
	 * @param model the Jena Model
	 * @return a List of PathwayStep
	 */
	public static java.util.List getAllPathwayStep(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,PathwayStep.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getPathwayStep(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Gene.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Gene
	 * @param model the Jena Model.
	 */
	public static Gene createGene(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.GeneImpl.createGene(resource,model);
	}
	
	/**
	 * Create a new instance of Gene.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Gene
	 * @param model the Jena Model.
	 */
	public static Gene createGene(String uri, Model model) throws JastorException {
		Gene obj = fr.curie.BiNoM.pathways.biopax.GeneImpl.createGene(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Gene.  Leaves the model unchanged.
	 * @param uri The uri of the Gene
	 * @param model the Jena Model.
	 */
	public static Gene getGene(String uri, Model model) throws JastorException {
		return getGene(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Gene.  Leaves the model unchanged.
	 * @param resource The resource of the Gene
	 * @param model the Jena Model.
	 */
	public static Gene getGene(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Gene.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.GeneImpl obj = (fr.curie.BiNoM.pathways.biopax.GeneImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.GeneImpl.getGene(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Gene for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Gene
	 * @param model the Jena Model
	 * @return a List of Gene
	 */
	public static java.util.List getAllGene(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Gene.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getGene(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Score.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Score
	 * @param model the Jena Model.
	 */
	public static Score createScore(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.ScoreImpl.createScore(resource,model);
	}
	
	/**
	 * Create a new instance of Score.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Score
	 * @param model the Jena Model.
	 */
	public static Score createScore(String uri, Model model) throws JastorException {
		Score obj = fr.curie.BiNoM.pathways.biopax.ScoreImpl.createScore(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Score.  Leaves the model unchanged.
	 * @param uri The uri of the Score
	 * @param model the Jena Model.
	 */
	public static Score getScore(String uri, Model model) throws JastorException {
		return getScore(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Score.  Leaves the model unchanged.
	 * @param resource The resource of the Score
	 * @param model the Jena Model.
	 */
	public static Score getScore(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Score.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.ScoreImpl obj = (fr.curie.BiNoM.pathways.biopax.ScoreImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.ScoreImpl.getScore(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Score for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Score
	 * @param model the Jena Model
	 * @return a List of Score
	 */
	public static java.util.List getAllScore(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Score.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getScore(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of SequenceSite.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the SequenceSite
	 * @param model the Jena Model.
	 */
	public static SequenceSite createSequenceSite(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.SequenceSiteImpl.createSequenceSite(resource,model);
	}
	
	/**
	 * Create a new instance of SequenceSite.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the SequenceSite
	 * @param model the Jena Model.
	 */
	public static SequenceSite createSequenceSite(String uri, Model model) throws JastorException {
		SequenceSite obj = fr.curie.BiNoM.pathways.biopax.SequenceSiteImpl.createSequenceSite(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of SequenceSite.  Leaves the model unchanged.
	 * @param uri The uri of the SequenceSite
	 * @param model the Jena Model.
	 */
	public static SequenceSite getSequenceSite(String uri, Model model) throws JastorException {
		return getSequenceSite(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of SequenceSite.  Leaves the model unchanged.
	 * @param resource The resource of the SequenceSite
	 * @param model the Jena Model.
	 */
	public static SequenceSite getSequenceSite(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.SequenceSite.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.SequenceSiteImpl obj = (fr.curie.BiNoM.pathways.biopax.SequenceSiteImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.SequenceSiteImpl.getSequenceSite(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of SequenceSite for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#SequenceSite
	 * @param model the Jena Model
	 * @return a List of SequenceSite
	 */
	public static java.util.List getAllSequenceSite(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,SequenceSite.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getSequenceSite(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of SmallMoleculeReference.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the SmallMoleculeReference
	 * @param model the Jena Model.
	 */
	public static SmallMoleculeReference createSmallMoleculeReference(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.SmallMoleculeReferenceImpl.createSmallMoleculeReference(resource,model);
	}
	
	/**
	 * Create a new instance of SmallMoleculeReference.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the SmallMoleculeReference
	 * @param model the Jena Model.
	 */
	public static SmallMoleculeReference createSmallMoleculeReference(String uri, Model model) throws JastorException {
		SmallMoleculeReference obj = fr.curie.BiNoM.pathways.biopax.SmallMoleculeReferenceImpl.createSmallMoleculeReference(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of SmallMoleculeReference.  Leaves the model unchanged.
	 * @param uri The uri of the SmallMoleculeReference
	 * @param model the Jena Model.
	 */
	public static SmallMoleculeReference getSmallMoleculeReference(String uri, Model model) throws JastorException {
		return getSmallMoleculeReference(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of SmallMoleculeReference.  Leaves the model unchanged.
	 * @param resource The resource of the SmallMoleculeReference
	 * @param model the Jena Model.
	 */
	public static SmallMoleculeReference getSmallMoleculeReference(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.SmallMoleculeReferenceImpl obj = (fr.curie.BiNoM.pathways.biopax.SmallMoleculeReferenceImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.SmallMoleculeReferenceImpl.getSmallMoleculeReference(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of SmallMoleculeReference for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#SmallMoleculeReference
	 * @param model the Jena Model
	 * @return a List of SmallMoleculeReference
	 */
	public static java.util.List getAllSmallMoleculeReference(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,SmallMoleculeReference.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getSmallMoleculeReference(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Pathway.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Pathway
	 * @param model the Jena Model.
	 */
	public static Pathway createPathway(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.PathwayImpl.createPathway(resource,model);
	}
	
	/**
	 * Create a new instance of Pathway.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Pathway
	 * @param model the Jena Model.
	 */
	public static Pathway createPathway(String uri, Model model) throws JastorException {
		Pathway obj = fr.curie.BiNoM.pathways.biopax.PathwayImpl.createPathway(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Pathway.  Leaves the model unchanged.
	 * @param uri The uri of the Pathway
	 * @param model the Jena Model.
	 */
	public static Pathway getPathway(String uri, Model model) throws JastorException {
		return getPathway(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Pathway.  Leaves the model unchanged.
	 * @param resource The resource of the Pathway
	 * @param model the Jena Model.
	 */
	public static Pathway getPathway(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Pathway.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.PathwayImpl obj = (fr.curie.BiNoM.pathways.biopax.PathwayImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.PathwayImpl.getPathway(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Pathway for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Pathway
	 * @param model the Jena Model
	 * @return a List of Pathway
	 */
	public static java.util.List getAllPathway(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Pathway.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getPathway(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Rna.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Rna
	 * @param model the Jena Model.
	 */
	public static Rna createRna(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.RnaImpl.createRna(resource,model);
	}
	
	/**
	 * Create a new instance of Rna.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Rna
	 * @param model the Jena Model.
	 */
	public static Rna createRna(String uri, Model model) throws JastorException {
		Rna obj = fr.curie.BiNoM.pathways.biopax.RnaImpl.createRna(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Rna.  Leaves the model unchanged.
	 * @param uri The uri of the Rna
	 * @param model the Jena Model.
	 */
	public static Rna getRna(String uri, Model model) throws JastorException {
		return getRna(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Rna.  Leaves the model unchanged.
	 * @param resource The resource of the Rna
	 * @param model the Jena Model.
	 */
	public static Rna getRna(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Rna.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.RnaImpl obj = (fr.curie.BiNoM.pathways.biopax.RnaImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.RnaImpl.getRna(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Rna for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Rna
	 * @param model the Jena Model
	 * @return a List of Rna
	 */
	public static java.util.List getAllRna(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Rna.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getRna(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of GeneticInteraction.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the GeneticInteraction
	 * @param model the Jena Model.
	 */
	public static GeneticInteraction createGeneticInteraction(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.createGeneticInteraction(resource,model);
	}
	
	/**
	 * Create a new instance of GeneticInteraction.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the GeneticInteraction
	 * @param model the Jena Model.
	 */
	public static GeneticInteraction createGeneticInteraction(String uri, Model model) throws JastorException {
		GeneticInteraction obj = fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.createGeneticInteraction(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of GeneticInteraction.  Leaves the model unchanged.
	 * @param uri The uri of the GeneticInteraction
	 * @param model the Jena Model.
	 */
	public static GeneticInteraction getGeneticInteraction(String uri, Model model) throws JastorException {
		return getGeneticInteraction(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of GeneticInteraction.  Leaves the model unchanged.
	 * @param resource The resource of the GeneticInteraction
	 * @param model the Jena Model.
	 */
	public static GeneticInteraction getGeneticInteraction(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.GeneticInteraction.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl obj = (fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.getGeneticInteraction(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of GeneticInteraction for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#GeneticInteraction
	 * @param model the Jena Model
	 * @return a List of GeneticInteraction
	 */
	public static java.util.List getAllGeneticInteraction(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,GeneticInteraction.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getGeneticInteraction(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of KPrime.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the KPrime
	 * @param model the Jena Model.
	 */
	public static KPrime createKPrime(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.KPrimeImpl.createKPrime(resource,model);
	}
	
	/**
	 * Create a new instance of KPrime.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the KPrime
	 * @param model the Jena Model.
	 */
	public static KPrime createKPrime(String uri, Model model) throws JastorException {
		KPrime obj = fr.curie.BiNoM.pathways.biopax.KPrimeImpl.createKPrime(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of KPrime.  Leaves the model unchanged.
	 * @param uri The uri of the KPrime
	 * @param model the Jena Model.
	 */
	public static KPrime getKPrime(String uri, Model model) throws JastorException {
		return getKPrime(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of KPrime.  Leaves the model unchanged.
	 * @param resource The resource of the KPrime
	 * @param model the Jena Model.
	 */
	public static KPrime getKPrime(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.KPrime.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.KPrimeImpl obj = (fr.curie.BiNoM.pathways.biopax.KPrimeImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.KPrimeImpl.getKPrime(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of KPrime for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#KPrime
	 * @param model the Jena Model
	 * @return a List of KPrime
	 */
	public static java.util.List getAllKPrime(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,KPrime.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getKPrime(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of TissueVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the TissueVocabulary
	 * @param model the Jena Model.
	 */
	public static TissueVocabulary createTissueVocabulary(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.TissueVocabularyImpl.createTissueVocabulary(resource,model);
	}
	
	/**
	 * Create a new instance of TissueVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the TissueVocabulary
	 * @param model the Jena Model.
	 */
	public static TissueVocabulary createTissueVocabulary(String uri, Model model) throws JastorException {
		TissueVocabulary obj = fr.curie.BiNoM.pathways.biopax.TissueVocabularyImpl.createTissueVocabulary(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of TissueVocabulary.  Leaves the model unchanged.
	 * @param uri The uri of the TissueVocabulary
	 * @param model the Jena Model.
	 */
	public static TissueVocabulary getTissueVocabulary(String uri, Model model) throws JastorException {
		return getTissueVocabulary(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of TissueVocabulary.  Leaves the model unchanged.
	 * @param resource The resource of the TissueVocabulary
	 * @param model the Jena Model.
	 */
	public static TissueVocabulary getTissueVocabulary(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.TissueVocabulary.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.TissueVocabularyImpl obj = (fr.curie.BiNoM.pathways.biopax.TissueVocabularyImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.TissueVocabularyImpl.getTissueVocabulary(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of TissueVocabulary for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#TissueVocabulary
	 * @param model the Jena Model
	 * @return a List of TissueVocabulary
	 */
	public static java.util.List getAllTissueVocabulary(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,TissueVocabulary.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getTissueVocabulary(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of RnaRegion.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the RnaRegion
	 * @param model the Jena Model.
	 */
	public static RnaRegion createRnaRegion(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.RnaRegionImpl.createRnaRegion(resource,model);
	}
	
	/**
	 * Create a new instance of RnaRegion.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the RnaRegion
	 * @param model the Jena Model.
	 */
	public static RnaRegion createRnaRegion(String uri, Model model) throws JastorException {
		RnaRegion obj = fr.curie.BiNoM.pathways.biopax.RnaRegionImpl.createRnaRegion(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of RnaRegion.  Leaves the model unchanged.
	 * @param uri The uri of the RnaRegion
	 * @param model the Jena Model.
	 */
	public static RnaRegion getRnaRegion(String uri, Model model) throws JastorException {
		return getRnaRegion(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of RnaRegion.  Leaves the model unchanged.
	 * @param resource The resource of the RnaRegion
	 * @param model the Jena Model.
	 */
	public static RnaRegion getRnaRegion(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.RnaRegion.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.RnaRegionImpl obj = (fr.curie.BiNoM.pathways.biopax.RnaRegionImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.RnaRegionImpl.getRnaRegion(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of RnaRegion for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#RnaRegion
	 * @param model the Jena Model
	 * @return a List of RnaRegion
	 */
	public static java.util.List getAllRnaRegion(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,RnaRegion.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getRnaRegion(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of MolecularInteraction.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the MolecularInteraction
	 * @param model the Jena Model.
	 */
	public static MolecularInteraction createMolecularInteraction(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.MolecularInteractionImpl.createMolecularInteraction(resource,model);
	}
	
	/**
	 * Create a new instance of MolecularInteraction.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the MolecularInteraction
	 * @param model the Jena Model.
	 */
	public static MolecularInteraction createMolecularInteraction(String uri, Model model) throws JastorException {
		MolecularInteraction obj = fr.curie.BiNoM.pathways.biopax.MolecularInteractionImpl.createMolecularInteraction(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of MolecularInteraction.  Leaves the model unchanged.
	 * @param uri The uri of the MolecularInteraction
	 * @param model the Jena Model.
	 */
	public static MolecularInteraction getMolecularInteraction(String uri, Model model) throws JastorException {
		return getMolecularInteraction(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of MolecularInteraction.  Leaves the model unchanged.
	 * @param resource The resource of the MolecularInteraction
	 * @param model the Jena Model.
	 */
	public static MolecularInteraction getMolecularInteraction(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.MolecularInteraction.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.MolecularInteractionImpl obj = (fr.curie.BiNoM.pathways.biopax.MolecularInteractionImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.MolecularInteractionImpl.getMolecularInteraction(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of MolecularInteraction for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#MolecularInteraction
	 * @param model the Jena Model
	 * @return a List of MolecularInteraction
	 */
	public static java.util.List getAllMolecularInteraction(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,MolecularInteraction.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getMolecularInteraction(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of ProteinReference.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the ProteinReference
	 * @param model the Jena Model.
	 */
	public static ProteinReference createProteinReference(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.createProteinReference(resource,model);
	}
	
	/**
	 * Create a new instance of ProteinReference.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the ProteinReference
	 * @param model the Jena Model.
	 */
	public static ProteinReference createProteinReference(String uri, Model model) throws JastorException {
		ProteinReference obj = fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.createProteinReference(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of ProteinReference.  Leaves the model unchanged.
	 * @param uri The uri of the ProteinReference
	 * @param model the Jena Model.
	 */
	public static ProteinReference getProteinReference(String uri, Model model) throws JastorException {
		return getProteinReference(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of ProteinReference.  Leaves the model unchanged.
	 * @param resource The resource of the ProteinReference
	 * @param model the Jena Model.
	 */
	public static ProteinReference getProteinReference(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.ProteinReference.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl obj = (fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.getProteinReference(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of ProteinReference for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#ProteinReference
	 * @param model the Jena Model
	 * @return a List of ProteinReference
	 */
	public static java.util.List getAllProteinReference(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,ProteinReference.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getProteinReference(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of CellVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the CellVocabulary
	 * @param model the Jena Model.
	 */
	public static CellVocabulary createCellVocabulary(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.CellVocabularyImpl.createCellVocabulary(resource,model);
	}
	
	/**
	 * Create a new instance of CellVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the CellVocabulary
	 * @param model the Jena Model.
	 */
	public static CellVocabulary createCellVocabulary(String uri, Model model) throws JastorException {
		CellVocabulary obj = fr.curie.BiNoM.pathways.biopax.CellVocabularyImpl.createCellVocabulary(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of CellVocabulary.  Leaves the model unchanged.
	 * @param uri The uri of the CellVocabulary
	 * @param model the Jena Model.
	 */
	public static CellVocabulary getCellVocabulary(String uri, Model model) throws JastorException {
		return getCellVocabulary(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of CellVocabulary.  Leaves the model unchanged.
	 * @param resource The resource of the CellVocabulary
	 * @param model the Jena Model.
	 */
	public static CellVocabulary getCellVocabulary(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.CellVocabulary.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.CellVocabularyImpl obj = (fr.curie.BiNoM.pathways.biopax.CellVocabularyImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.CellVocabularyImpl.getCellVocabulary(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of CellVocabulary for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#CellVocabulary
	 * @param model the Jena Model
	 * @return a List of CellVocabulary
	 */
	public static java.util.List getAllCellVocabulary(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,CellVocabulary.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getCellVocabulary(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of PhenotypeVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the PhenotypeVocabulary
	 * @param model the Jena Model.
	 */
	public static PhenotypeVocabulary createPhenotypeVocabulary(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.PhenotypeVocabularyImpl.createPhenotypeVocabulary(resource,model);
	}
	
	/**
	 * Create a new instance of PhenotypeVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the PhenotypeVocabulary
	 * @param model the Jena Model.
	 */
	public static PhenotypeVocabulary createPhenotypeVocabulary(String uri, Model model) throws JastorException {
		PhenotypeVocabulary obj = fr.curie.BiNoM.pathways.biopax.PhenotypeVocabularyImpl.createPhenotypeVocabulary(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of PhenotypeVocabulary.  Leaves the model unchanged.
	 * @param uri The uri of the PhenotypeVocabulary
	 * @param model the Jena Model.
	 */
	public static PhenotypeVocabulary getPhenotypeVocabulary(String uri, Model model) throws JastorException {
		return getPhenotypeVocabulary(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of PhenotypeVocabulary.  Leaves the model unchanged.
	 * @param resource The resource of the PhenotypeVocabulary
	 * @param model the Jena Model.
	 */
	public static PhenotypeVocabulary getPhenotypeVocabulary(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.PhenotypeVocabularyImpl obj = (fr.curie.BiNoM.pathways.biopax.PhenotypeVocabularyImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.PhenotypeVocabularyImpl.getPhenotypeVocabulary(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of PhenotypeVocabulary for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#PhenotypeVocabulary
	 * @param model the Jena Model
	 * @return a List of PhenotypeVocabulary
	 */
	public static java.util.List getAllPhenotypeVocabulary(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,PhenotypeVocabulary.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getPhenotypeVocabulary(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of UnificationXref.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the UnificationXref
	 * @param model the Jena Model.
	 */
	public static UnificationXref createUnificationXref(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.UnificationXrefImpl.createUnificationXref(resource,model);
	}
	
	/**
	 * Create a new instance of UnificationXref.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the UnificationXref
	 * @param model the Jena Model.
	 */
	public static UnificationXref createUnificationXref(String uri, Model model) throws JastorException {
		UnificationXref obj = fr.curie.BiNoM.pathways.biopax.UnificationXrefImpl.createUnificationXref(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of UnificationXref.  Leaves the model unchanged.
	 * @param uri The uri of the UnificationXref
	 * @param model the Jena Model.
	 */
	public static UnificationXref getUnificationXref(String uri, Model model) throws JastorException {
		return getUnificationXref(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of UnificationXref.  Leaves the model unchanged.
	 * @param resource The resource of the UnificationXref
	 * @param model the Jena Model.
	 */
	public static UnificationXref getUnificationXref(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.UnificationXref.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.UnificationXrefImpl obj = (fr.curie.BiNoM.pathways.biopax.UnificationXrefImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.UnificationXrefImpl.getUnificationXref(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of UnificationXref for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#UnificationXref
	 * @param model the Jena Model
	 * @return a List of UnificationXref
	 */
	public static java.util.List getAllUnificationXref(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,UnificationXref.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getUnificationXref(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of DnaReference.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the DnaReference
	 * @param model the Jena Model.
	 */
	public static DnaReference createDnaReference(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.DnaReferenceImpl.createDnaReference(resource,model);
	}
	
	/**
	 * Create a new instance of DnaReference.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the DnaReference
	 * @param model the Jena Model.
	 */
	public static DnaReference createDnaReference(String uri, Model model) throws JastorException {
		DnaReference obj = fr.curie.BiNoM.pathways.biopax.DnaReferenceImpl.createDnaReference(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of DnaReference.  Leaves the model unchanged.
	 * @param uri The uri of the DnaReference
	 * @param model the Jena Model.
	 */
	public static DnaReference getDnaReference(String uri, Model model) throws JastorException {
		return getDnaReference(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of DnaReference.  Leaves the model unchanged.
	 * @param resource The resource of the DnaReference
	 * @param model the Jena Model.
	 */
	public static DnaReference getDnaReference(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.DnaReference.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.DnaReferenceImpl obj = (fr.curie.BiNoM.pathways.biopax.DnaReferenceImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.DnaReferenceImpl.getDnaReference(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of DnaReference for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#DnaReference
	 * @param model the Jena Model
	 * @return a List of DnaReference
	 */
	public static java.util.List getAllDnaReference(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,DnaReference.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getDnaReference(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Evidence.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Evidence
	 * @param model the Jena Model.
	 */
	public static Evidence createEvidence(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.EvidenceImpl.createEvidence(resource,model);
	}
	
	/**
	 * Create a new instance of Evidence.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Evidence
	 * @param model the Jena Model.
	 */
	public static Evidence createEvidence(String uri, Model model) throws JastorException {
		Evidence obj = fr.curie.BiNoM.pathways.biopax.EvidenceImpl.createEvidence(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Evidence.  Leaves the model unchanged.
	 * @param uri The uri of the Evidence
	 * @param model the Jena Model.
	 */
	public static Evidence getEvidence(String uri, Model model) throws JastorException {
		return getEvidence(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Evidence.  Leaves the model unchanged.
	 * @param resource The resource of the Evidence
	 * @param model the Jena Model.
	 */
	public static Evidence getEvidence(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Evidence.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.EvidenceImpl obj = (fr.curie.BiNoM.pathways.biopax.EvidenceImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.EvidenceImpl.getEvidence(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Evidence for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Evidence
	 * @param model the Jena Model
	 * @return a List of Evidence
	 */
	public static java.util.List getAllEvidence(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Evidence.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getEvidence(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of RelationshipTypeVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the RelationshipTypeVocabulary
	 * @param model the Jena Model.
	 */
	public static RelationshipTypeVocabulary createRelationshipTypeVocabulary(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabularyImpl.createRelationshipTypeVocabulary(resource,model);
	}
	
	/**
	 * Create a new instance of RelationshipTypeVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the RelationshipTypeVocabulary
	 * @param model the Jena Model.
	 */
	public static RelationshipTypeVocabulary createRelationshipTypeVocabulary(String uri, Model model) throws JastorException {
		RelationshipTypeVocabulary obj = fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabularyImpl.createRelationshipTypeVocabulary(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of RelationshipTypeVocabulary.  Leaves the model unchanged.
	 * @param uri The uri of the RelationshipTypeVocabulary
	 * @param model the Jena Model.
	 */
	public static RelationshipTypeVocabulary getRelationshipTypeVocabulary(String uri, Model model) throws JastorException {
		return getRelationshipTypeVocabulary(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of RelationshipTypeVocabulary.  Leaves the model unchanged.
	 * @param resource The resource of the RelationshipTypeVocabulary
	 * @param model the Jena Model.
	 */
	public static RelationshipTypeVocabulary getRelationshipTypeVocabulary(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabularyImpl obj = (fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabularyImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabularyImpl.getRelationshipTypeVocabulary(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of RelationshipTypeVocabulary for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#RelationshipTypeVocabulary
	 * @param model the Jena Model
	 * @return a List of RelationshipTypeVocabulary
	 */
	public static java.util.List getAllRelationshipTypeVocabulary(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,RelationshipTypeVocabulary.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getRelationshipTypeVocabulary(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of InteractionVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the InteractionVocabulary
	 * @param model the Jena Model.
	 */
	public static InteractionVocabulary createInteractionVocabulary(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.InteractionVocabularyImpl.createInteractionVocabulary(resource,model);
	}
	
	/**
	 * Create a new instance of InteractionVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the InteractionVocabulary
	 * @param model the Jena Model.
	 */
	public static InteractionVocabulary createInteractionVocabulary(String uri, Model model) throws JastorException {
		InteractionVocabulary obj = fr.curie.BiNoM.pathways.biopax.InteractionVocabularyImpl.createInteractionVocabulary(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of InteractionVocabulary.  Leaves the model unchanged.
	 * @param uri The uri of the InteractionVocabulary
	 * @param model the Jena Model.
	 */
	public static InteractionVocabulary getInteractionVocabulary(String uri, Model model) throws JastorException {
		return getInteractionVocabulary(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of InteractionVocabulary.  Leaves the model unchanged.
	 * @param resource The resource of the InteractionVocabulary
	 * @param model the Jena Model.
	 */
	public static InteractionVocabulary getInteractionVocabulary(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.InteractionVocabulary.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.InteractionVocabularyImpl obj = (fr.curie.BiNoM.pathways.biopax.InteractionVocabularyImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.InteractionVocabularyImpl.getInteractionVocabulary(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of InteractionVocabulary for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#InteractionVocabulary
	 * @param model the Jena Model
	 * @return a List of InteractionVocabulary
	 */
	public static java.util.List getAllInteractionVocabulary(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,InteractionVocabulary.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getInteractionVocabulary(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of SequenceLocation.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the SequenceLocation
	 * @param model the Jena Model.
	 */
	public static SequenceLocation createSequenceLocation(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.SequenceLocationImpl.createSequenceLocation(resource,model);
	}
	
	/**
	 * Create a new instance of SequenceLocation.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the SequenceLocation
	 * @param model the Jena Model.
	 */
	public static SequenceLocation createSequenceLocation(String uri, Model model) throws JastorException {
		SequenceLocation obj = fr.curie.BiNoM.pathways.biopax.SequenceLocationImpl.createSequenceLocation(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of SequenceLocation.  Leaves the model unchanged.
	 * @param uri The uri of the SequenceLocation
	 * @param model the Jena Model.
	 */
	public static SequenceLocation getSequenceLocation(String uri, Model model) throws JastorException {
		return getSequenceLocation(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of SequenceLocation.  Leaves the model unchanged.
	 * @param resource The resource of the SequenceLocation
	 * @param model the Jena Model.
	 */
	public static SequenceLocation getSequenceLocation(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.SequenceLocation.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.SequenceLocationImpl obj = (fr.curie.BiNoM.pathways.biopax.SequenceLocationImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.SequenceLocationImpl.getSequenceLocation(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of SequenceLocation for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#SequenceLocation
	 * @param model the Jena Model
	 * @return a List of SequenceLocation
	 */
	public static java.util.List getAllSequenceLocation(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,SequenceLocation.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getSequenceLocation(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Control.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Control
	 * @param model the Jena Model.
	 */
	public static Control createControl(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.ControlImpl.createControl(resource,model);
	}
	
	/**
	 * Create a new instance of Control.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Control
	 * @param model the Jena Model.
	 */
	public static Control createControl(String uri, Model model) throws JastorException {
		Control obj = fr.curie.BiNoM.pathways.biopax.ControlImpl.createControl(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Control.  Leaves the model unchanged.
	 * @param uri The uri of the Control
	 * @param model the Jena Model.
	 */
	public static Control getControl(String uri, Model model) throws JastorException {
		return getControl(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Control.  Leaves the model unchanged.
	 * @param resource The resource of the Control
	 * @param model the Jena Model.
	 */
	public static Control getControl(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Control.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.ControlImpl obj = (fr.curie.BiNoM.pathways.biopax.ControlImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.ControlImpl.getControl(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Control for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Control
	 * @param model the Jena Model
	 * @return a List of Control
	 */
	public static java.util.List getAllControl(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Control.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getControl(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of FragmentFeature.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the FragmentFeature
	 * @param model the Jena Model.
	 */
	public static FragmentFeature createFragmentFeature(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.FragmentFeatureImpl.createFragmentFeature(resource,model);
	}
	
	/**
	 * Create a new instance of FragmentFeature.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the FragmentFeature
	 * @param model the Jena Model.
	 */
	public static FragmentFeature createFragmentFeature(String uri, Model model) throws JastorException {
		FragmentFeature obj = fr.curie.BiNoM.pathways.biopax.FragmentFeatureImpl.createFragmentFeature(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of FragmentFeature.  Leaves the model unchanged.
	 * @param uri The uri of the FragmentFeature
	 * @param model the Jena Model.
	 */
	public static FragmentFeature getFragmentFeature(String uri, Model model) throws JastorException {
		return getFragmentFeature(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of FragmentFeature.  Leaves the model unchanged.
	 * @param resource The resource of the FragmentFeature
	 * @param model the Jena Model.
	 */
	public static FragmentFeature getFragmentFeature(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.FragmentFeature.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.FragmentFeatureImpl obj = (fr.curie.BiNoM.pathways.biopax.FragmentFeatureImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.FragmentFeatureImpl.getFragmentFeature(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of FragmentFeature for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#FragmentFeature
	 * @param model the Jena Model
	 * @return a List of FragmentFeature
	 */
	public static java.util.List getAllFragmentFeature(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,FragmentFeature.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getFragmentFeature(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of RnaReference.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the RnaReference
	 * @param model the Jena Model.
	 */
	public static RnaReference createRnaReference(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.RnaReferenceImpl.createRnaReference(resource,model);
	}
	
	/**
	 * Create a new instance of RnaReference.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the RnaReference
	 * @param model the Jena Model.
	 */
	public static RnaReference createRnaReference(String uri, Model model) throws JastorException {
		RnaReference obj = fr.curie.BiNoM.pathways.biopax.RnaReferenceImpl.createRnaReference(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of RnaReference.  Leaves the model unchanged.
	 * @param uri The uri of the RnaReference
	 * @param model the Jena Model.
	 */
	public static RnaReference getRnaReference(String uri, Model model) throws JastorException {
		return getRnaReference(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of RnaReference.  Leaves the model unchanged.
	 * @param resource The resource of the RnaReference
	 * @param model the Jena Model.
	 */
	public static RnaReference getRnaReference(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.RnaReference.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.RnaReferenceImpl obj = (fr.curie.BiNoM.pathways.biopax.RnaReferenceImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.RnaReferenceImpl.getRnaReference(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of RnaReference for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#RnaReference
	 * @param model the Jena Model
	 * @return a List of RnaReference
	 */
	public static java.util.List getAllRnaReference(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,RnaReference.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getRnaReference(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of CovalentBindingFeature.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the CovalentBindingFeature
	 * @param model the Jena Model.
	 */
	public static CovalentBindingFeature createCovalentBindingFeature(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.CovalentBindingFeatureImpl.createCovalentBindingFeature(resource,model);
	}
	
	/**
	 * Create a new instance of CovalentBindingFeature.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the CovalentBindingFeature
	 * @param model the Jena Model.
	 */
	public static CovalentBindingFeature createCovalentBindingFeature(String uri, Model model) throws JastorException {
		CovalentBindingFeature obj = fr.curie.BiNoM.pathways.biopax.CovalentBindingFeatureImpl.createCovalentBindingFeature(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of CovalentBindingFeature.  Leaves the model unchanged.
	 * @param uri The uri of the CovalentBindingFeature
	 * @param model the Jena Model.
	 */
	public static CovalentBindingFeature getCovalentBindingFeature(String uri, Model model) throws JastorException {
		return getCovalentBindingFeature(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of CovalentBindingFeature.  Leaves the model unchanged.
	 * @param resource The resource of the CovalentBindingFeature
	 * @param model the Jena Model.
	 */
	public static CovalentBindingFeature getCovalentBindingFeature(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.CovalentBindingFeatureImpl obj = (fr.curie.BiNoM.pathways.biopax.CovalentBindingFeatureImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.CovalentBindingFeatureImpl.getCovalentBindingFeature(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of CovalentBindingFeature for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#CovalentBindingFeature
	 * @param model the Jena Model
	 * @return a List of CovalentBindingFeature
	 */
	public static java.util.List getAllCovalentBindingFeature(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,CovalentBindingFeature.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getCovalentBindingFeature(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Stoichiometry.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Stoichiometry
	 * @param model the Jena Model.
	 */
	public static Stoichiometry createStoichiometry(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.StoichiometryImpl.createStoichiometry(resource,model);
	}
	
	/**
	 * Create a new instance of Stoichiometry.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Stoichiometry
	 * @param model the Jena Model.
	 */
	public static Stoichiometry createStoichiometry(String uri, Model model) throws JastorException {
		Stoichiometry obj = fr.curie.BiNoM.pathways.biopax.StoichiometryImpl.createStoichiometry(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Stoichiometry.  Leaves the model unchanged.
	 * @param uri The uri of the Stoichiometry
	 * @param model the Jena Model.
	 */
	public static Stoichiometry getStoichiometry(String uri, Model model) throws JastorException {
		return getStoichiometry(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Stoichiometry.  Leaves the model unchanged.
	 * @param resource The resource of the Stoichiometry
	 * @param model the Jena Model.
	 */
	public static Stoichiometry getStoichiometry(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Stoichiometry.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.StoichiometryImpl obj = (fr.curie.BiNoM.pathways.biopax.StoichiometryImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.StoichiometryImpl.getStoichiometry(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Stoichiometry for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Stoichiometry
	 * @param model the Jena Model
	 * @return a List of Stoichiometry
	 */
	public static java.util.List getAllStoichiometry(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Stoichiometry.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getStoichiometry(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of BioSource.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the BioSource
	 * @param model the Jena Model.
	 */
	public static BioSource createBioSource(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.BioSourceImpl.createBioSource(resource,model);
	}
	
	/**
	 * Create a new instance of BioSource.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the BioSource
	 * @param model the Jena Model.
	 */
	public static BioSource createBioSource(String uri, Model model) throws JastorException {
		BioSource obj = fr.curie.BiNoM.pathways.biopax.BioSourceImpl.createBioSource(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of BioSource.  Leaves the model unchanged.
	 * @param uri The uri of the BioSource
	 * @param model the Jena Model.
	 */
	public static BioSource getBioSource(String uri, Model model) throws JastorException {
		return getBioSource(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of BioSource.  Leaves the model unchanged.
	 * @param resource The resource of the BioSource
	 * @param model the Jena Model.
	 */
	public static BioSource getBioSource(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.BioSource.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.BioSourceImpl obj = (fr.curie.BiNoM.pathways.biopax.BioSourceImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.BioSourceImpl.getBioSource(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of BioSource for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#BioSource
	 * @param model the Jena Model
	 * @return a List of BioSource
	 */
	public static java.util.List getAllBioSource(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,BioSource.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getBioSource(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of EntityReference.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the EntityReference
	 * @param model the Jena Model.
	 */
	public static EntityReference createEntityReference(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.EntityReferenceImpl.createEntityReference(resource,model);
	}
	
	/**
	 * Create a new instance of EntityReference.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the EntityReference
	 * @param model the Jena Model.
	 */
	public static EntityReference createEntityReference(String uri, Model model) throws JastorException {
		EntityReference obj = fr.curie.BiNoM.pathways.biopax.EntityReferenceImpl.createEntityReference(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of EntityReference.  Leaves the model unchanged.
	 * @param uri The uri of the EntityReference
	 * @param model the Jena Model.
	 */
	public static EntityReference getEntityReference(String uri, Model model) throws JastorException {
		return getEntityReference(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of EntityReference.  Leaves the model unchanged.
	 * @param resource The resource of the EntityReference
	 * @param model the Jena Model.
	 */
	public static EntityReference getEntityReference(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.EntityReference.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.EntityReferenceImpl obj = (fr.curie.BiNoM.pathways.biopax.EntityReferenceImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.EntityReferenceImpl.getEntityReference(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of EntityReference for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#EntityReference
	 * @param model the Jena Model
	 * @return a List of EntityReference
	 */
	public static java.util.List getAllEntityReference(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,EntityReference.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getEntityReference(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Dna.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Dna
	 * @param model the Jena Model.
	 */
	public static Dna createDna(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.DnaImpl.createDna(resource,model);
	}
	
	/**
	 * Create a new instance of Dna.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Dna
	 * @param model the Jena Model.
	 */
	public static Dna createDna(String uri, Model model) throws JastorException {
		Dna obj = fr.curie.BiNoM.pathways.biopax.DnaImpl.createDna(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Dna.  Leaves the model unchanged.
	 * @param uri The uri of the Dna
	 * @param model the Jena Model.
	 */
	public static Dna getDna(String uri, Model model) throws JastorException {
		return getDna(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Dna.  Leaves the model unchanged.
	 * @param resource The resource of the Dna
	 * @param model the Jena Model.
	 */
	public static Dna getDna(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Dna.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.DnaImpl obj = (fr.curie.BiNoM.pathways.biopax.DnaImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.DnaImpl.getDna(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Dna for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Dna
	 * @param model the Jena Model
	 * @return a List of Dna
	 */
	public static java.util.List getAllDna(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Dna.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getDna(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of ChemicalStructure.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the ChemicalStructure
	 * @param model the Jena Model.
	 */
	public static ChemicalStructure createChemicalStructure(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.ChemicalStructureImpl.createChemicalStructure(resource,model);
	}
	
	/**
	 * Create a new instance of ChemicalStructure.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the ChemicalStructure
	 * @param model the Jena Model.
	 */
	public static ChemicalStructure createChemicalStructure(String uri, Model model) throws JastorException {
		ChemicalStructure obj = fr.curie.BiNoM.pathways.biopax.ChemicalStructureImpl.createChemicalStructure(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of ChemicalStructure.  Leaves the model unchanged.
	 * @param uri The uri of the ChemicalStructure
	 * @param model the Jena Model.
	 */
	public static ChemicalStructure getChemicalStructure(String uri, Model model) throws JastorException {
		return getChemicalStructure(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of ChemicalStructure.  Leaves the model unchanged.
	 * @param resource The resource of the ChemicalStructure
	 * @param model the Jena Model.
	 */
	public static ChemicalStructure getChemicalStructure(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.ChemicalStructure.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.ChemicalStructureImpl obj = (fr.curie.BiNoM.pathways.biopax.ChemicalStructureImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.ChemicalStructureImpl.getChemicalStructure(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of ChemicalStructure for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#ChemicalStructure
	 * @param model the Jena Model
	 * @return a List of ChemicalStructure
	 */
	public static java.util.List getAllChemicalStructure(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,ChemicalStructure.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getChemicalStructure(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of SequenceRegionVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the SequenceRegionVocabulary
	 * @param model the Jena Model.
	 */
	public static SequenceRegionVocabulary createSequenceRegionVocabulary(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabularyImpl.createSequenceRegionVocabulary(resource,model);
	}
	
	/**
	 * Create a new instance of SequenceRegionVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the SequenceRegionVocabulary
	 * @param model the Jena Model.
	 */
	public static SequenceRegionVocabulary createSequenceRegionVocabulary(String uri, Model model) throws JastorException {
		SequenceRegionVocabulary obj = fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabularyImpl.createSequenceRegionVocabulary(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of SequenceRegionVocabulary.  Leaves the model unchanged.
	 * @param uri The uri of the SequenceRegionVocabulary
	 * @param model the Jena Model.
	 */
	public static SequenceRegionVocabulary getSequenceRegionVocabulary(String uri, Model model) throws JastorException {
		return getSequenceRegionVocabulary(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of SequenceRegionVocabulary.  Leaves the model unchanged.
	 * @param resource The resource of the SequenceRegionVocabulary
	 * @param model the Jena Model.
	 */
	public static SequenceRegionVocabulary getSequenceRegionVocabulary(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabularyImpl obj = (fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabularyImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabularyImpl.getSequenceRegionVocabulary(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of SequenceRegionVocabulary for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#SequenceRegionVocabulary
	 * @param model the Jena Model
	 * @return a List of SequenceRegionVocabulary
	 */
	public static java.util.List getAllSequenceRegionVocabulary(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,SequenceRegionVocabulary.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getSequenceRegionVocabulary(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of SequenceInterval.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the SequenceInterval
	 * @param model the Jena Model.
	 */
	public static SequenceInterval createSequenceInterval(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.SequenceIntervalImpl.createSequenceInterval(resource,model);
	}
	
	/**
	 * Create a new instance of SequenceInterval.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the SequenceInterval
	 * @param model the Jena Model.
	 */
	public static SequenceInterval createSequenceInterval(String uri, Model model) throws JastorException {
		SequenceInterval obj = fr.curie.BiNoM.pathways.biopax.SequenceIntervalImpl.createSequenceInterval(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of SequenceInterval.  Leaves the model unchanged.
	 * @param uri The uri of the SequenceInterval
	 * @param model the Jena Model.
	 */
	public static SequenceInterval getSequenceInterval(String uri, Model model) throws JastorException {
		return getSequenceInterval(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of SequenceInterval.  Leaves the model unchanged.
	 * @param resource The resource of the SequenceInterval
	 * @param model the Jena Model.
	 */
	public static SequenceInterval getSequenceInterval(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.SequenceInterval.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.SequenceIntervalImpl obj = (fr.curie.BiNoM.pathways.biopax.SequenceIntervalImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.SequenceIntervalImpl.getSequenceInterval(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of SequenceInterval for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#SequenceInterval
	 * @param model the Jena Model
	 * @return a List of SequenceInterval
	 */
	public static java.util.List getAllSequenceInterval(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,SequenceInterval.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getSequenceInterval(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of ExperimentalFormVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the ExperimentalFormVocabulary
	 * @param model the Jena Model.
	 */
	public static ExperimentalFormVocabulary createExperimentalFormVocabulary(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabularyImpl.createExperimentalFormVocabulary(resource,model);
	}
	
	/**
	 * Create a new instance of ExperimentalFormVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the ExperimentalFormVocabulary
	 * @param model the Jena Model.
	 */
	public static ExperimentalFormVocabulary createExperimentalFormVocabulary(String uri, Model model) throws JastorException {
		ExperimentalFormVocabulary obj = fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabularyImpl.createExperimentalFormVocabulary(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of ExperimentalFormVocabulary.  Leaves the model unchanged.
	 * @param uri The uri of the ExperimentalFormVocabulary
	 * @param model the Jena Model.
	 */
	public static ExperimentalFormVocabulary getExperimentalFormVocabulary(String uri, Model model) throws JastorException {
		return getExperimentalFormVocabulary(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of ExperimentalFormVocabulary.  Leaves the model unchanged.
	 * @param resource The resource of the ExperimentalFormVocabulary
	 * @param model the Jena Model.
	 */
	public static ExperimentalFormVocabulary getExperimentalFormVocabulary(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabularyImpl obj = (fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabularyImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabularyImpl.getExperimentalFormVocabulary(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of ExperimentalFormVocabulary for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#ExperimentalFormVocabulary
	 * @param model the Jena Model
	 * @return a List of ExperimentalFormVocabulary
	 */
	public static java.util.List getAllExperimentalFormVocabulary(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,ExperimentalFormVocabulary.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getExperimentalFormVocabulary(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of PublicationXref.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the PublicationXref
	 * @param model the Jena Model.
	 */
	public static PublicationXref createPublicationXref(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.createPublicationXref(resource,model);
	}
	
	/**
	 * Create a new instance of PublicationXref.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the PublicationXref
	 * @param model the Jena Model.
	 */
	public static PublicationXref createPublicationXref(String uri, Model model) throws JastorException {
		PublicationXref obj = fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.createPublicationXref(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of PublicationXref.  Leaves the model unchanged.
	 * @param uri The uri of the PublicationXref
	 * @param model the Jena Model.
	 */
	public static PublicationXref getPublicationXref(String uri, Model model) throws JastorException {
		return getPublicationXref(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of PublicationXref.  Leaves the model unchanged.
	 * @param resource The resource of the PublicationXref
	 * @param model the Jena Model.
	 */
	public static PublicationXref getPublicationXref(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.PublicationXref.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl obj = (fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.getPublicationXref(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of PublicationXref for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#PublicationXref
	 * @param model the Jena Model
	 * @return a List of PublicationXref
	 */
	public static java.util.List getAllPublicationXref(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,PublicationXref.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getPublicationXref(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of BindingFeature.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the BindingFeature
	 * @param model the Jena Model.
	 */
	public static BindingFeature createBindingFeature(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.BindingFeatureImpl.createBindingFeature(resource,model);
	}
	
	/**
	 * Create a new instance of BindingFeature.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the BindingFeature
	 * @param model the Jena Model.
	 */
	public static BindingFeature createBindingFeature(String uri, Model model) throws JastorException {
		BindingFeature obj = fr.curie.BiNoM.pathways.biopax.BindingFeatureImpl.createBindingFeature(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of BindingFeature.  Leaves the model unchanged.
	 * @param uri The uri of the BindingFeature
	 * @param model the Jena Model.
	 */
	public static BindingFeature getBindingFeature(String uri, Model model) throws JastorException {
		return getBindingFeature(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of BindingFeature.  Leaves the model unchanged.
	 * @param resource The resource of the BindingFeature
	 * @param model the Jena Model.
	 */
	public static BindingFeature getBindingFeature(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.BindingFeature.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.BindingFeatureImpl obj = (fr.curie.BiNoM.pathways.biopax.BindingFeatureImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.BindingFeatureImpl.getBindingFeature(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of BindingFeature for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#BindingFeature
	 * @param model the Jena Model
	 * @return a List of BindingFeature
	 */
	public static java.util.List getAllBindingFeature(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,BindingFeature.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getBindingFeature(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Degradation.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Degradation
	 * @param model the Jena Model.
	 */
	public static Degradation createDegradation(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.DegradationImpl.createDegradation(resource,model);
	}
	
	/**
	 * Create a new instance of Degradation.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Degradation
	 * @param model the Jena Model.
	 */
	public static Degradation createDegradation(String uri, Model model) throws JastorException {
		Degradation obj = fr.curie.BiNoM.pathways.biopax.DegradationImpl.createDegradation(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Degradation.  Leaves the model unchanged.
	 * @param uri The uri of the Degradation
	 * @param model the Jena Model.
	 */
	public static Degradation getDegradation(String uri, Model model) throws JastorException {
		return getDegradation(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Degradation.  Leaves the model unchanged.
	 * @param resource The resource of the Degradation
	 * @param model the Jena Model.
	 */
	public static Degradation getDegradation(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Degradation.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.DegradationImpl obj = (fr.curie.BiNoM.pathways.biopax.DegradationImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.DegradationImpl.getDegradation(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Degradation for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Degradation
	 * @param model the Jena Model
	 * @return a List of Degradation
	 */
	public static java.util.List getAllDegradation(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Degradation.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getDegradation(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of BiochemicalPathwayStep.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the BiochemicalPathwayStep
	 * @param model the Jena Model.
	 */
	public static BiochemicalPathwayStep createBiochemicalPathwayStep(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.createBiochemicalPathwayStep(resource,model);
	}
	
	/**
	 * Create a new instance of BiochemicalPathwayStep.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the BiochemicalPathwayStep
	 * @param model the Jena Model.
	 */
	public static BiochemicalPathwayStep createBiochemicalPathwayStep(String uri, Model model) throws JastorException {
		BiochemicalPathwayStep obj = fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.createBiochemicalPathwayStep(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of BiochemicalPathwayStep.  Leaves the model unchanged.
	 * @param uri The uri of the BiochemicalPathwayStep
	 * @param model the Jena Model.
	 */
	public static BiochemicalPathwayStep getBiochemicalPathwayStep(String uri, Model model) throws JastorException {
		return getBiochemicalPathwayStep(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of BiochemicalPathwayStep.  Leaves the model unchanged.
	 * @param resource The resource of the BiochemicalPathwayStep
	 * @param model the Jena Model.
	 */
	public static BiochemicalPathwayStep getBiochemicalPathwayStep(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl obj = (fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.getBiochemicalPathwayStep(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of BiochemicalPathwayStep for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#BiochemicalPathwayStep
	 * @param model the Jena Model
	 * @return a List of BiochemicalPathwayStep
	 */
	public static java.util.List getAllBiochemicalPathwayStep(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,BiochemicalPathwayStep.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getBiochemicalPathwayStep(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of EvidenceCodeVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the EvidenceCodeVocabulary
	 * @param model the Jena Model.
	 */
	public static EvidenceCodeVocabulary createEvidenceCodeVocabulary(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabularyImpl.createEvidenceCodeVocabulary(resource,model);
	}
	
	/**
	 * Create a new instance of EvidenceCodeVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the EvidenceCodeVocabulary
	 * @param model the Jena Model.
	 */
	public static EvidenceCodeVocabulary createEvidenceCodeVocabulary(String uri, Model model) throws JastorException {
		EvidenceCodeVocabulary obj = fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabularyImpl.createEvidenceCodeVocabulary(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of EvidenceCodeVocabulary.  Leaves the model unchanged.
	 * @param uri The uri of the EvidenceCodeVocabulary
	 * @param model the Jena Model.
	 */
	public static EvidenceCodeVocabulary getEvidenceCodeVocabulary(String uri, Model model) throws JastorException {
		return getEvidenceCodeVocabulary(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of EvidenceCodeVocabulary.  Leaves the model unchanged.
	 * @param resource The resource of the EvidenceCodeVocabulary
	 * @param model the Jena Model.
	 */
	public static EvidenceCodeVocabulary getEvidenceCodeVocabulary(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabularyImpl obj = (fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabularyImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabularyImpl.getEvidenceCodeVocabulary(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of EvidenceCodeVocabulary for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#EvidenceCodeVocabulary
	 * @param model the Jena Model
	 * @return a List of EvidenceCodeVocabulary
	 */
	public static java.util.List getAllEvidenceCodeVocabulary(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,EvidenceCodeVocabulary.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getEvidenceCodeVocabulary(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of TransportWithBiochemicalReaction.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the TransportWithBiochemicalReaction
	 * @param model the Jena Model.
	 */
	public static TransportWithBiochemicalReaction createTransportWithBiochemicalReaction(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.TransportWithBiochemicalReactionImpl.createTransportWithBiochemicalReaction(resource,model);
	}
	
	/**
	 * Create a new instance of TransportWithBiochemicalReaction.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the TransportWithBiochemicalReaction
	 * @param model the Jena Model.
	 */
	public static TransportWithBiochemicalReaction createTransportWithBiochemicalReaction(String uri, Model model) throws JastorException {
		TransportWithBiochemicalReaction obj = fr.curie.BiNoM.pathways.biopax.TransportWithBiochemicalReactionImpl.createTransportWithBiochemicalReaction(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of TransportWithBiochemicalReaction.  Leaves the model unchanged.
	 * @param uri The uri of the TransportWithBiochemicalReaction
	 * @param model the Jena Model.
	 */
	public static TransportWithBiochemicalReaction getTransportWithBiochemicalReaction(String uri, Model model) throws JastorException {
		return getTransportWithBiochemicalReaction(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of TransportWithBiochemicalReaction.  Leaves the model unchanged.
	 * @param resource The resource of the TransportWithBiochemicalReaction
	 * @param model the Jena Model.
	 */
	public static TransportWithBiochemicalReaction getTransportWithBiochemicalReaction(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.TransportWithBiochemicalReaction.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.TransportWithBiochemicalReactionImpl obj = (fr.curie.BiNoM.pathways.biopax.TransportWithBiochemicalReactionImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.TransportWithBiochemicalReactionImpl.getTransportWithBiochemicalReaction(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of TransportWithBiochemicalReaction for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#TransportWithBiochemicalReaction
	 * @param model the Jena Model
	 * @return a List of TransportWithBiochemicalReaction
	 */
	public static java.util.List getAllTransportWithBiochemicalReaction(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,TransportWithBiochemicalReaction.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getTransportWithBiochemicalReaction(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Catalysis.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Catalysis
	 * @param model the Jena Model.
	 */
	public static Catalysis createCatalysis(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.CatalysisImpl.createCatalysis(resource,model);
	}
	
	/**
	 * Create a new instance of Catalysis.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Catalysis
	 * @param model the Jena Model.
	 */
	public static Catalysis createCatalysis(String uri, Model model) throws JastorException {
		Catalysis obj = fr.curie.BiNoM.pathways.biopax.CatalysisImpl.createCatalysis(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Catalysis.  Leaves the model unchanged.
	 * @param uri The uri of the Catalysis
	 * @param model the Jena Model.
	 */
	public static Catalysis getCatalysis(String uri, Model model) throws JastorException {
		return getCatalysis(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Catalysis.  Leaves the model unchanged.
	 * @param resource The resource of the Catalysis
	 * @param model the Jena Model.
	 */
	public static Catalysis getCatalysis(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.Catalysis.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.CatalysisImpl obj = (fr.curie.BiNoM.pathways.biopax.CatalysisImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.CatalysisImpl.getCatalysis(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Catalysis for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#Catalysis
	 * @param model the Jena Model
	 * @return a List of Catalysis
	 */
	public static java.util.List getAllCatalysis(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Catalysis.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getCatalysis(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of EntityFeature.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the EntityFeature
	 * @param model the Jena Model.
	 */
	public static EntityFeature createEntityFeature(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.EntityFeatureImpl.createEntityFeature(resource,model);
	}
	
	/**
	 * Create a new instance of EntityFeature.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the EntityFeature
	 * @param model the Jena Model.
	 */
	public static EntityFeature createEntityFeature(String uri, Model model) throws JastorException {
		EntityFeature obj = fr.curie.BiNoM.pathways.biopax.EntityFeatureImpl.createEntityFeature(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of EntityFeature.  Leaves the model unchanged.
	 * @param uri The uri of the EntityFeature
	 * @param model the Jena Model.
	 */
	public static EntityFeature getEntityFeature(String uri, Model model) throws JastorException {
		return getEntityFeature(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of EntityFeature.  Leaves the model unchanged.
	 * @param resource The resource of the EntityFeature
	 * @param model the Jena Model.
	 */
	public static EntityFeature getEntityFeature(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.EntityFeature.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.EntityFeatureImpl obj = (fr.curie.BiNoM.pathways.biopax.EntityFeatureImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.EntityFeatureImpl.getEntityFeature(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of EntityFeature for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level3.owl#EntityFeature
	 * @param model the Jena Model
	 * @return a List of EntityFeature
	 */
	public static java.util.List getAllEntityFeature(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,EntityFeature.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getEntityFeature(stmt.getSubject(),model));
		}
		return list;
	}
	
	
	/**
	 * Returns an instance of an interface for the given Resource.  The return instance is guaranteed to 
	 * implement the most specific interface in *some* hierarchy in which the Resource participates.  The behavior
	 * is unspecified for resources with RDF types from different hierarchies.
	 * @return an instance of Thing
	 */
	public static Thing getThing(com.hp.hpl.jena.rdf.model.Resource res, com.hp.hpl.jena.rdf.model.Model model) throws JastorException {
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Catalysis"))) {
			return getCatalysis(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#TransportWithBiochemicalReaction"))) {
			return getTransportWithBiochemicalReaction(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Transport"))) {
			return getTransport(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#BiochemicalReaction"))) {
			return getBiochemicalReaction(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#EvidenceCodeVocabulary"))) {
			return getEvidenceCodeVocabulary(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#BiochemicalPathwayStep"))) {
			return getBiochemicalPathwayStep(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#PathwayStep"))) {
			return getPathwayStep(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Degradation"))) {
			return getDegradation(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#PublicationXref"))) {
			return getPublicationXref(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#ExperimentalFormVocabulary"))) {
			return getExperimentalFormVocabulary(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#SequenceInterval"))) {
			return getSequenceInterval(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#SequenceRegionVocabulary"))) {
			return getSequenceRegionVocabulary(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#ChemicalStructure"))) {
			return getChemicalStructure(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Dna"))) {
			return getDna(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#BioSource"))) {
			return getBioSource(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Stoichiometry"))) {
			return getStoichiometry(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#CovalentBindingFeature"))) {
			return getCovalentBindingFeature(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#ModificationFeature"))) {
			return getModificationFeature(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#BindingFeature"))) {
			return getBindingFeature(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#RnaReference"))) {
			return getRnaReference(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#FragmentFeature"))) {
			return getFragmentFeature(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#EntityFeature"))) {
			return getEntityFeature(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#InteractionVocabulary"))) {
			return getInteractionVocabulary(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#RelationshipTypeVocabulary"))) {
			return getRelationshipTypeVocabulary(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Evidence"))) {
			return getEvidence(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#DnaReference"))) {
			return getDnaReference(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#UnificationXref"))) {
			return getUnificationXref(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#PhenotypeVocabulary"))) {
			return getPhenotypeVocabulary(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#CellVocabulary"))) {
			return getCellVocabulary(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#ProteinReference"))) {
			return getProteinReference(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#MolecularInteraction"))) {
			return getMolecularInteraction(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#RnaRegion"))) {
			return getRnaRegion(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#TissueVocabulary"))) {
			return getTissueVocabulary(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#KPrime"))) {
			return getKPrime(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#GeneticInteraction"))) {
			return getGeneticInteraction(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Rna"))) {
			return getRna(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Pathway"))) {
			return getPathway(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#SmallMoleculeReference"))) {
			return getSmallMoleculeReference(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#SequenceSite"))) {
			return getSequenceSite(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#SequenceLocation"))) {
			return getSequenceLocation(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Score"))) {
			return getScore(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Gene"))) {
			return getGene(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#DnaRegion"))) {
			return getDnaRegion(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#SmallMolecule"))) {
			return getSmallMolecule(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#ComplexAssembly"))) {
			return getComplexAssembly(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Conversion"))) {
			return getConversion(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#TemplateReaction"))) {
			return getTemplateReaction(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#DnaRegionReference"))) {
			return getDnaRegionReference(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#TemplateReactionRegulation"))) {
			return getTemplateReactionRegulation(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#RnaRegionReference"))) {
			return getRnaRegionReference(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#EntityReference"))) {
			return getEntityReference(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#ExperimentalForm"))) {
			return getExperimentalForm(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#SequenceModificationVocabulary"))) {
			return getSequenceModificationVocabulary(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Provenance"))) {
			return getProvenance(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Protein"))) {
			return getProtein(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#RelationshipXref"))) {
			return getRelationshipXref(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Xref"))) {
			return getXref(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#DeltaG"))) {
			return getDeltaG(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Modulation"))) {
			return getModulation(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Control"))) {
			return getControl(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Interaction"))) {
			return getInteraction(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#EntityReferenceTypeVocabulary"))) {
			return getEntityReferenceTypeVocabulary(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#CellularLocationVocabulary"))) {
			return getCellularLocationVocabulary(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#ControlledVocabulary"))) {
			return getControlledVocabulary(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#UtilityClass"))) {
			return getUtilityClass(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Complex"))) {
			return getComplex(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#PhysicalEntity"))) {
			return getPhysicalEntity(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level3.owl#Entity"))) {
			return getEntity(res,model);
		}
		return new ThingImpl(res,model);
	}
	
	/**
	 * Returns an instance of an interface for the given Resource URI.  The return instance is guaranteed to 
	 * implement the most specific interface in *some* hierarchy in which the Resource participates.  The behavior
	 * is unspecified for resources with RDF types from different hierarchies.
	 * @return an instance of Thing
	 */
	public static Thing getThing(String uri, com.hp.hpl.jena.rdf.model.Model model) throws JastorException {
		return getThing(model.getResource(uri),model);
	}

	/**
	 * Return a list of compatible interfaces for the given type.  Searches through all ontology classes
	 * in the biopax-level3.owl ontology.  The list is sorted according to the topological sort
	 * of the class hierarchy
	 * @return a List of type java.lang.Class
	 */
	public static java.util.List listCompatibleInterfaces (com.hp.hpl.jena.rdf.model.Resource type) {
		java.util.List types = new java.util.ArrayList();
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Catalysis.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Catalysis.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.TransportWithBiochemicalReaction.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.TransportWithBiochemicalReaction.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Transport.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Transport.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.BiochemicalReaction.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.PathwayStep.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.PathwayStep.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Degradation.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Degradation.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.PublicationXref.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.PublicationXref.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.SequenceInterval.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.SequenceInterval.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.ChemicalStructure.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.ChemicalStructure.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Dna.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Dna.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.BioSource.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.BioSource.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Stoichiometry.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Stoichiometry.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.CovalentBindingFeature.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.ModificationFeature.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.ModificationFeature.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.BindingFeature.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.BindingFeature.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.RnaReference.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.RnaReference.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.FragmentFeature.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.FragmentFeature.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.EntityFeature.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.EntityFeature.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.InteractionVocabulary.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.InteractionVocabulary.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Evidence.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Evidence.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.DnaReference.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.DnaReference.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.UnificationXref.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.UnificationXref.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.CellVocabulary.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.CellVocabulary.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.ProteinReference.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.ProteinReference.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.MolecularInteraction.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.MolecularInteraction.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.RnaRegion.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.RnaRegion.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.TissueVocabulary.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.TissueVocabulary.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.KPrime.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.KPrime.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.GeneticInteraction.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.GeneticInteraction.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Rna.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Rna.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Pathway.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Pathway.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.SmallMoleculeReference.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.SequenceSite.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.SequenceSite.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.SequenceLocation.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.SequenceLocation.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Score.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Score.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Gene.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Gene.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.DnaRegion.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.DnaRegion.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.SmallMolecule.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.SmallMolecule.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.ComplexAssembly.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.ComplexAssembly.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Conversion.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Conversion.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.TemplateReaction.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.TemplateReaction.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.DnaRegionReference.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.DnaRegionReference.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulation.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulation.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.RnaRegionReference.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.RnaRegionReference.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.EntityReference.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.EntityReference.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.ExperimentalForm.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.ExperimentalForm.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Provenance.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Provenance.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Protein.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Protein.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.RelationshipXref.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.RelationshipXref.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Xref.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Xref.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.DeltaG.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.DeltaG.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Modulation.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Modulation.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Control.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Control.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Interaction.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Interaction.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.ControlledVocabulary.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.ControlledVocabulary.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.UtilityClass.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Complex.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Complex.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.PhysicalEntity.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.PhysicalEntity.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.Entity.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.Entity.class);
		}
		return types;
	}
}