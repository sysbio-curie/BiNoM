

package fr.curie.BiNoM.pathways.biopax;

import com.ibm.adtech.jastor.*;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * Factory for instantiating objects for ontology classes in the biopax-level2.owl ontology.  The
 * get methods leave the model unchanged and return a Java view of the object in the model.  The create methods
 * may add certain baseline properties to the model such as rdf:type and any properties with hasValue restrictions.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	comment : This is version 0.94 of the BioPAX Level 2 ontology.  The goal of the BioPAX group is to develop a common exchange format for biological pathway data.  More information is available at http://www.biopax.org.  This ontology is freely available under the LGPL (http://www.gnu.org/copyleft/lesser.html).^^http://www.w3.org/2001/XMLSchema#string <br>
 * <br>
 * <br>
 */
public class biopax_DASH_level2_DOT_owlFactory extends com.ibm.adtech.jastor.ThingFactory { 



	/**
	 * Create a new instance of control.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the control
	 * @param model the Jena Model.
	 */
	public static control createcontrol(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.controlImpl.createcontrol(resource,model);
	}
	
	/**
	 * Create a new instance of control.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the control
	 * @param model the Jena Model.
	 */
	public static control createcontrol(String uri, Model model) throws JastorException {
		control obj = fr.curie.BiNoM.pathways.biopax.controlImpl.createcontrol(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of control.  Leaves the model unchanged.
	 * @param uri The uri of the control
	 * @param model the Jena Model.
	 */
	public static control getcontrol(String uri, Model model) throws JastorException {
		return getcontrol(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of control.  Leaves the model unchanged.
	 * @param resource The resource of the control
	 * @param model the Jena Model.
	 */
	public static control getcontrol(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.control.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.controlImpl obj = (fr.curie.BiNoM.pathways.biopax.controlImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.controlImpl.getcontrol(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of control for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#control
	 * @param model the Jena Model
	 * @return a List of control
	 */
	public static java.util.List getAllcontrol(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,control.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getcontrol(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of confidence.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the confidence
	 * @param model the Jena Model.
	 */
	public static confidence createconfidence(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.confidenceImpl.createconfidence(resource,model);
	}
	
	/**
	 * Create a new instance of confidence.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the confidence
	 * @param model the Jena Model.
	 */
	public static confidence createconfidence(String uri, Model model) throws JastorException {
		confidence obj = fr.curie.BiNoM.pathways.biopax.confidenceImpl.createconfidence(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of confidence.  Leaves the model unchanged.
	 * @param uri The uri of the confidence
	 * @param model the Jena Model.
	 */
	public static confidence getconfidence(String uri, Model model) throws JastorException {
		return getconfidence(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of confidence.  Leaves the model unchanged.
	 * @param resource The resource of the confidence
	 * @param model the Jena Model.
	 */
	public static confidence getconfidence(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.confidence.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.confidenceImpl obj = (fr.curie.BiNoM.pathways.biopax.confidenceImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.confidenceImpl.getconfidence(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of confidence for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#confidence
	 * @param model the Jena Model
	 * @return a List of confidence
	 */
	public static java.util.List getAllconfidence(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,confidence.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getconfidence(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of relationshipXref.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the relationshipXref
	 * @param model the Jena Model.
	 */
	public static relationshipXref createrelationshipXref(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl.createrelationshipXref(resource,model);
	}
	
	/**
	 * Create a new instance of relationshipXref.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the relationshipXref
	 * @param model the Jena Model.
	 */
	public static relationshipXref createrelationshipXref(String uri, Model model) throws JastorException {
		relationshipXref obj = fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl.createrelationshipXref(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of relationshipXref.  Leaves the model unchanged.
	 * @param uri The uri of the relationshipXref
	 * @param model the Jena Model.
	 */
	public static relationshipXref getrelationshipXref(String uri, Model model) throws JastorException {
		return getrelationshipXref(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of relationshipXref.  Leaves the model unchanged.
	 * @param resource The resource of the relationshipXref
	 * @param model the Jena Model.
	 */
	public static relationshipXref getrelationshipXref(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.relationshipXref.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl obj = (fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl.getrelationshipXref(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of relationshipXref for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#relationshipXref
	 * @param model the Jena Model
	 * @return a List of relationshipXref
	 */
	public static java.util.List getAllrelationshipXref(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,relationshipXref.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getrelationshipXref(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of publicationXref.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the publicationXref
	 * @param model the Jena Model.
	 */
	public static publicationXref createpublicationXref(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.publicationXrefImpl.createpublicationXref(resource,model);
	}
	
	/**
	 * Create a new instance of publicationXref.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the publicationXref
	 * @param model the Jena Model.
	 */
	public static publicationXref createpublicationXref(String uri, Model model) throws JastorException {
		publicationXref obj = fr.curie.BiNoM.pathways.biopax.publicationXrefImpl.createpublicationXref(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of publicationXref.  Leaves the model unchanged.
	 * @param uri The uri of the publicationXref
	 * @param model the Jena Model.
	 */
	public static publicationXref getpublicationXref(String uri, Model model) throws JastorException {
		return getpublicationXref(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of publicationXref.  Leaves the model unchanged.
	 * @param resource The resource of the publicationXref
	 * @param model the Jena Model.
	 */
	public static publicationXref getpublicationXref(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.publicationXref.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.publicationXrefImpl obj = (fr.curie.BiNoM.pathways.biopax.publicationXrefImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.publicationXrefImpl.getpublicationXref(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of publicationXref for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#publicationXref
	 * @param model the Jena Model
	 * @return a List of publicationXref
	 */
	public static java.util.List getAllpublicationXref(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,publicationXref.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getpublicationXref(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of physicalEntityParticipant.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the physicalEntityParticipant
	 * @param model the Jena Model.
	 */
	public static physicalEntityParticipant createphysicalEntityParticipant(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.physicalEntityParticipantImpl.createphysicalEntityParticipant(resource,model);
	}
	
	/**
	 * Create a new instance of physicalEntityParticipant.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the physicalEntityParticipant
	 * @param model the Jena Model.
	 */
	public static physicalEntityParticipant createphysicalEntityParticipant(String uri, Model model) throws JastorException {
		physicalEntityParticipant obj = fr.curie.BiNoM.pathways.biopax.physicalEntityParticipantImpl.createphysicalEntityParticipant(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of physicalEntityParticipant.  Leaves the model unchanged.
	 * @param uri The uri of the physicalEntityParticipant
	 * @param model the Jena Model.
	 */
	public static physicalEntityParticipant getphysicalEntityParticipant(String uri, Model model) throws JastorException {
		return getphysicalEntityParticipant(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of physicalEntityParticipant.  Leaves the model unchanged.
	 * @param resource The resource of the physicalEntityParticipant
	 * @param model the Jena Model.
	 */
	public static physicalEntityParticipant getphysicalEntityParticipant(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.physicalEntityParticipantImpl obj = (fr.curie.BiNoM.pathways.biopax.physicalEntityParticipantImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.physicalEntityParticipantImpl.getphysicalEntityParticipant(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of physicalEntityParticipant for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#physicalEntityParticipant
	 * @param model the Jena Model
	 * @return a List of physicalEntityParticipant
	 */
	public static java.util.List getAllphysicalEntityParticipant(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,physicalEntityParticipant.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getphysicalEntityParticipant(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of experimentalForm.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the experimentalForm
	 * @param model the Jena Model.
	 */
	public static experimentalForm createexperimentalForm(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.experimentalFormImpl.createexperimentalForm(resource,model);
	}
	
	/**
	 * Create a new instance of experimentalForm.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the experimentalForm
	 * @param model the Jena Model.
	 */
	public static experimentalForm createexperimentalForm(String uri, Model model) throws JastorException {
		experimentalForm obj = fr.curie.BiNoM.pathways.biopax.experimentalFormImpl.createexperimentalForm(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of experimentalForm.  Leaves the model unchanged.
	 * @param uri The uri of the experimentalForm
	 * @param model the Jena Model.
	 */
	public static experimentalForm getexperimentalForm(String uri, Model model) throws JastorException {
		return getexperimentalForm(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of experimentalForm.  Leaves the model unchanged.
	 * @param resource The resource of the experimentalForm
	 * @param model the Jena Model.
	 */
	public static experimentalForm getexperimentalForm(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.experimentalForm.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.experimentalFormImpl obj = (fr.curie.BiNoM.pathways.biopax.experimentalFormImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.experimentalFormImpl.getexperimentalForm(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of experimentalForm for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#experimentalForm
	 * @param model the Jena Model
	 * @return a List of experimentalForm
	 */
	public static java.util.List getAllexperimentalForm(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,experimentalForm.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getexperimentalForm(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of catalysis.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the catalysis
	 * @param model the Jena Model.
	 */
	public static catalysis createcatalysis(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.catalysisImpl.createcatalysis(resource,model);
	}
	
	/**
	 * Create a new instance of catalysis.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the catalysis
	 * @param model the Jena Model.
	 */
	public static catalysis createcatalysis(String uri, Model model) throws JastorException {
		catalysis obj = fr.curie.BiNoM.pathways.biopax.catalysisImpl.createcatalysis(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of catalysis.  Leaves the model unchanged.
	 * @param uri The uri of the catalysis
	 * @param model the Jena Model.
	 */
	public static catalysis getcatalysis(String uri, Model model) throws JastorException {
		return getcatalysis(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of catalysis.  Leaves the model unchanged.
	 * @param resource The resource of the catalysis
	 * @param model the Jena Model.
	 */
	public static catalysis getcatalysis(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.catalysis.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.catalysisImpl obj = (fr.curie.BiNoM.pathways.biopax.catalysisImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.catalysisImpl.getcatalysis(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of catalysis for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#catalysis
	 * @param model the Jena Model
	 * @return a List of catalysis
	 */
	public static java.util.List getAllcatalysis(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,catalysis.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getcatalysis(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of complexAssembly.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the complexAssembly
	 * @param model the Jena Model.
	 */
	public static complexAssembly createcomplexAssembly(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.createcomplexAssembly(resource,model);
	}
	
	/**
	 * Create a new instance of complexAssembly.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the complexAssembly
	 * @param model the Jena Model.
	 */
	public static complexAssembly createcomplexAssembly(String uri, Model model) throws JastorException {
		complexAssembly obj = fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.createcomplexAssembly(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of complexAssembly.  Leaves the model unchanged.
	 * @param uri The uri of the complexAssembly
	 * @param model the Jena Model.
	 */
	public static complexAssembly getcomplexAssembly(String uri, Model model) throws JastorException {
		return getcomplexAssembly(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of complexAssembly.  Leaves the model unchanged.
	 * @param resource The resource of the complexAssembly
	 * @param model the Jena Model.
	 */
	public static complexAssembly getcomplexAssembly(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.complexAssembly.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl obj = (fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.getcomplexAssembly(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of complexAssembly for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#complexAssembly
	 * @param model the Jena Model
	 * @return a List of complexAssembly
	 */
	public static java.util.List getAllcomplexAssembly(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,complexAssembly.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getcomplexAssembly(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of physicalEntity.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the physicalEntity
	 * @param model the Jena Model.
	 */
	public static physicalEntity createphysicalEntity(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.physicalEntityImpl.createphysicalEntity(resource,model);
	}
	
	/**
	 * Create a new instance of physicalEntity.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the physicalEntity
	 * @param model the Jena Model.
	 */
	public static physicalEntity createphysicalEntity(String uri, Model model) throws JastorException {
		physicalEntity obj = fr.curie.BiNoM.pathways.biopax.physicalEntityImpl.createphysicalEntity(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of physicalEntity.  Leaves the model unchanged.
	 * @param uri The uri of the physicalEntity
	 * @param model the Jena Model.
	 */
	public static physicalEntity getphysicalEntity(String uri, Model model) throws JastorException {
		return getphysicalEntity(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of physicalEntity.  Leaves the model unchanged.
	 * @param resource The resource of the physicalEntity
	 * @param model the Jena Model.
	 */
	public static physicalEntity getphysicalEntity(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.physicalEntity.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.physicalEntityImpl obj = (fr.curie.BiNoM.pathways.biopax.physicalEntityImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.physicalEntityImpl.getphysicalEntity(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of physicalEntity for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#physicalEntity
	 * @param model the Jena Model
	 * @return a List of physicalEntity
	 */
	public static java.util.List getAllphysicalEntity(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,physicalEntity.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getphysicalEntity(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of chemicalStructure.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the chemicalStructure
	 * @param model the Jena Model.
	 */
	public static chemicalStructure createchemicalStructure(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.chemicalStructureImpl.createchemicalStructure(resource,model);
	}
	
	/**
	 * Create a new instance of chemicalStructure.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the chemicalStructure
	 * @param model the Jena Model.
	 */
	public static chemicalStructure createchemicalStructure(String uri, Model model) throws JastorException {
		chemicalStructure obj = fr.curie.BiNoM.pathways.biopax.chemicalStructureImpl.createchemicalStructure(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of chemicalStructure.  Leaves the model unchanged.
	 * @param uri The uri of the chemicalStructure
	 * @param model the Jena Model.
	 */
	public static chemicalStructure getchemicalStructure(String uri, Model model) throws JastorException {
		return getchemicalStructure(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of chemicalStructure.  Leaves the model unchanged.
	 * @param resource The resource of the chemicalStructure
	 * @param model the Jena Model.
	 */
	public static chemicalStructure getchemicalStructure(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.chemicalStructure.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.chemicalStructureImpl obj = (fr.curie.BiNoM.pathways.biopax.chemicalStructureImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.chemicalStructureImpl.getchemicalStructure(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of chemicalStructure for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#chemicalStructure
	 * @param model the Jena Model
	 * @return a List of chemicalStructure
	 */
	public static java.util.List getAllchemicalStructure(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,chemicalStructure.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getchemicalStructure(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of sequenceInterval.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the sequenceInterval
	 * @param model the Jena Model.
	 */
	public static sequenceInterval createsequenceInterval(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.sequenceIntervalImpl.createsequenceInterval(resource,model);
	}
	
	/**
	 * Create a new instance of sequenceInterval.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the sequenceInterval
	 * @param model the Jena Model.
	 */
	public static sequenceInterval createsequenceInterval(String uri, Model model) throws JastorException {
		sequenceInterval obj = fr.curie.BiNoM.pathways.biopax.sequenceIntervalImpl.createsequenceInterval(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of sequenceInterval.  Leaves the model unchanged.
	 * @param uri The uri of the sequenceInterval
	 * @param model the Jena Model.
	 */
	public static sequenceInterval getsequenceInterval(String uri, Model model) throws JastorException {
		return getsequenceInterval(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of sequenceInterval.  Leaves the model unchanged.
	 * @param resource The resource of the sequenceInterval
	 * @param model the Jena Model.
	 */
	public static sequenceInterval getsequenceInterval(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.sequenceInterval.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.sequenceIntervalImpl obj = (fr.curie.BiNoM.pathways.biopax.sequenceIntervalImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.sequenceIntervalImpl.getsequenceInterval(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of sequenceInterval for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#sequenceInterval
	 * @param model the Jena Model
	 * @return a List of sequenceInterval
	 */
	public static java.util.List getAllsequenceInterval(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,sequenceInterval.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getsequenceInterval(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of complex.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the complex
	 * @param model the Jena Model.
	 */
	public static complex createcomplex(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.complexImpl.createcomplex(resource,model);
	}
	
	/**
	 * Create a new instance of complex.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the complex
	 * @param model the Jena Model.
	 */
	public static complex createcomplex(String uri, Model model) throws JastorException {
		complex obj = fr.curie.BiNoM.pathways.biopax.complexImpl.createcomplex(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of complex.  Leaves the model unchanged.
	 * @param uri The uri of the complex
	 * @param model the Jena Model.
	 */
	public static complex getcomplex(String uri, Model model) throws JastorException {
		return getcomplex(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of complex.  Leaves the model unchanged.
	 * @param resource The resource of the complex
	 * @param model the Jena Model.
	 */
	public static complex getcomplex(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.complex.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.complexImpl obj = (fr.curie.BiNoM.pathways.biopax.complexImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.complexImpl.getcomplex(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of complex for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#complex
	 * @param model the Jena Model
	 * @return a List of complex
	 */
	public static java.util.List getAllcomplex(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,complex.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getcomplex(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of dataSource.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the dataSource
	 * @param model the Jena Model.
	 */
	public static dataSource createdataSource(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.dataSourceImpl.createdataSource(resource,model);
	}
	
	/**
	 * Create a new instance of dataSource.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the dataSource
	 * @param model the Jena Model.
	 */
	public static dataSource createdataSource(String uri, Model model) throws JastorException {
		dataSource obj = fr.curie.BiNoM.pathways.biopax.dataSourceImpl.createdataSource(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of dataSource.  Leaves the model unchanged.
	 * @param uri The uri of the dataSource
	 * @param model the Jena Model.
	 */
	public static dataSource getdataSource(String uri, Model model) throws JastorException {
		return getdataSource(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of dataSource.  Leaves the model unchanged.
	 * @param resource The resource of the dataSource
	 * @param model the Jena Model.
	 */
	public static dataSource getdataSource(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.dataSource.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.dataSourceImpl obj = (fr.curie.BiNoM.pathways.biopax.dataSourceImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.dataSourceImpl.getdataSource(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of dataSource for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#dataSource
	 * @param model the Jena Model
	 * @return a List of dataSource
	 */
	public static java.util.List getAlldataSource(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,dataSource.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getdataSource(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of dna.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the dna
	 * @param model the Jena Model.
	 */
	public static dna createdna(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.dnaImpl.createdna(resource,model);
	}
	
	/**
	 * Create a new instance of dna.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the dna
	 * @param model the Jena Model.
	 */
	public static dna createdna(String uri, Model model) throws JastorException {
		dna obj = fr.curie.BiNoM.pathways.biopax.dnaImpl.createdna(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of dna.  Leaves the model unchanged.
	 * @param uri The uri of the dna
	 * @param model the Jena Model.
	 */
	public static dna getdna(String uri, Model model) throws JastorException {
		return getdna(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of dna.  Leaves the model unchanged.
	 * @param resource The resource of the dna
	 * @param model the Jena Model.
	 */
	public static dna getdna(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.dna.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.dnaImpl obj = (fr.curie.BiNoM.pathways.biopax.dnaImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.dnaImpl.getdna(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of dna for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#dna
	 * @param model the Jena Model
	 * @return a List of dna
	 */
	public static java.util.List getAlldna(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,dna.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getdna(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of sequenceParticipant.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the sequenceParticipant
	 * @param model the Jena Model.
	 */
	public static sequenceParticipant createsequenceParticipant(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.sequenceParticipantImpl.createsequenceParticipant(resource,model);
	}
	
	/**
	 * Create a new instance of sequenceParticipant.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the sequenceParticipant
	 * @param model the Jena Model.
	 */
	public static sequenceParticipant createsequenceParticipant(String uri, Model model) throws JastorException {
		sequenceParticipant obj = fr.curie.BiNoM.pathways.biopax.sequenceParticipantImpl.createsequenceParticipant(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of sequenceParticipant.  Leaves the model unchanged.
	 * @param uri The uri of the sequenceParticipant
	 * @param model the Jena Model.
	 */
	public static sequenceParticipant getsequenceParticipant(String uri, Model model) throws JastorException {
		return getsequenceParticipant(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of sequenceParticipant.  Leaves the model unchanged.
	 * @param resource The resource of the sequenceParticipant
	 * @param model the Jena Model.
	 */
	public static sequenceParticipant getsequenceParticipant(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.sequenceParticipant.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.sequenceParticipantImpl obj = (fr.curie.BiNoM.pathways.biopax.sequenceParticipantImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.sequenceParticipantImpl.getsequenceParticipant(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of sequenceParticipant for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#sequenceParticipant
	 * @param model the Jena Model
	 * @return a List of sequenceParticipant
	 */
	public static java.util.List getAllsequenceParticipant(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,sequenceParticipant.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getsequenceParticipant(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of sequenceSite.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the sequenceSite
	 * @param model the Jena Model.
	 */
	public static sequenceSite createsequenceSite(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.sequenceSiteImpl.createsequenceSite(resource,model);
	}
	
	/**
	 * Create a new instance of sequenceSite.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the sequenceSite
	 * @param model the Jena Model.
	 */
	public static sequenceSite createsequenceSite(String uri, Model model) throws JastorException {
		sequenceSite obj = fr.curie.BiNoM.pathways.biopax.sequenceSiteImpl.createsequenceSite(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of sequenceSite.  Leaves the model unchanged.
	 * @param uri The uri of the sequenceSite
	 * @param model the Jena Model.
	 */
	public static sequenceSite getsequenceSite(String uri, Model model) throws JastorException {
		return getsequenceSite(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of sequenceSite.  Leaves the model unchanged.
	 * @param resource The resource of the sequenceSite
	 * @param model the Jena Model.
	 */
	public static sequenceSite getsequenceSite(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.sequenceSite.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.sequenceSiteImpl obj = (fr.curie.BiNoM.pathways.biopax.sequenceSiteImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.sequenceSiteImpl.getsequenceSite(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of sequenceSite for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#sequenceSite
	 * @param model the Jena Model
	 * @return a List of sequenceSite
	 */
	public static java.util.List getAllsequenceSite(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,sequenceSite.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getsequenceSite(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of unificationXref.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the unificationXref
	 * @param model the Jena Model.
	 */
	public static unificationXref createunificationXref(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.unificationXrefImpl.createunificationXref(resource,model);
	}
	
	/**
	 * Create a new instance of unificationXref.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the unificationXref
	 * @param model the Jena Model.
	 */
	public static unificationXref createunificationXref(String uri, Model model) throws JastorException {
		unificationXref obj = fr.curie.BiNoM.pathways.biopax.unificationXrefImpl.createunificationXref(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of unificationXref.  Leaves the model unchanged.
	 * @param uri The uri of the unificationXref
	 * @param model the Jena Model.
	 */
	public static unificationXref getunificationXref(String uri, Model model) throws JastorException {
		return getunificationXref(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of unificationXref.  Leaves the model unchanged.
	 * @param resource The resource of the unificationXref
	 * @param model the Jena Model.
	 */
	public static unificationXref getunificationXref(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.unificationXref.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.unificationXrefImpl obj = (fr.curie.BiNoM.pathways.biopax.unificationXrefImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.unificationXrefImpl.getunificationXref(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of unificationXref for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#unificationXref
	 * @param model the Jena Model
	 * @return a List of unificationXref
	 */
	public static java.util.List getAllunificationXref(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,unificationXref.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getunificationXref(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of transport.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the transport
	 * @param model the Jena Model.
	 */
	public static transport createtransport(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.transportImpl.createtransport(resource,model);
	}
	
	/**
	 * Create a new instance of transport.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the transport
	 * @param model the Jena Model.
	 */
	public static transport createtransport(String uri, Model model) throws JastorException {
		transport obj = fr.curie.BiNoM.pathways.biopax.transportImpl.createtransport(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of transport.  Leaves the model unchanged.
	 * @param uri The uri of the transport
	 * @param model the Jena Model.
	 */
	public static transport gettransport(String uri, Model model) throws JastorException {
		return gettransport(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of transport.  Leaves the model unchanged.
	 * @param resource The resource of the transport
	 * @param model the Jena Model.
	 */
	public static transport gettransport(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.transport.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.transportImpl obj = (fr.curie.BiNoM.pathways.biopax.transportImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.transportImpl.gettransport(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of transport for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#transport
	 * @param model the Jena Model
	 * @return a List of transport
	 */
	public static java.util.List getAlltransport(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,transport.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(gettransport(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of transportWithBiochemicalReaction.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the transportWithBiochemicalReaction
	 * @param model the Jena Model.
	 */
	public static transportWithBiochemicalReaction createtransportWithBiochemicalReaction(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.transportWithBiochemicalReactionImpl.createtransportWithBiochemicalReaction(resource,model);
	}
	
	/**
	 * Create a new instance of transportWithBiochemicalReaction.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the transportWithBiochemicalReaction
	 * @param model the Jena Model.
	 */
	public static transportWithBiochemicalReaction createtransportWithBiochemicalReaction(String uri, Model model) throws JastorException {
		transportWithBiochemicalReaction obj = fr.curie.BiNoM.pathways.biopax.transportWithBiochemicalReactionImpl.createtransportWithBiochemicalReaction(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of transportWithBiochemicalReaction.  Leaves the model unchanged.
	 * @param uri The uri of the transportWithBiochemicalReaction
	 * @param model the Jena Model.
	 */
	public static transportWithBiochemicalReaction gettransportWithBiochemicalReaction(String uri, Model model) throws JastorException {
		return gettransportWithBiochemicalReaction(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of transportWithBiochemicalReaction.  Leaves the model unchanged.
	 * @param resource The resource of the transportWithBiochemicalReaction
	 * @param model the Jena Model.
	 */
	public static transportWithBiochemicalReaction gettransportWithBiochemicalReaction(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.transportWithBiochemicalReaction.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.transportWithBiochemicalReactionImpl obj = (fr.curie.BiNoM.pathways.biopax.transportWithBiochemicalReactionImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.transportWithBiochemicalReactionImpl.gettransportWithBiochemicalReaction(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of transportWithBiochemicalReaction for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#transportWithBiochemicalReaction
	 * @param model the Jena Model
	 * @return a List of transportWithBiochemicalReaction
	 */
	public static java.util.List getAlltransportWithBiochemicalReaction(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,transportWithBiochemicalReaction.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(gettransportWithBiochemicalReaction(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of biochemicalReaction.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the biochemicalReaction
	 * @param model the Jena Model.
	 */
	public static biochemicalReaction createbiochemicalReaction(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.biochemicalReactionImpl.createbiochemicalReaction(resource,model);
	}
	
	/**
	 * Create a new instance of biochemicalReaction.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the biochemicalReaction
	 * @param model the Jena Model.
	 */
	public static biochemicalReaction createbiochemicalReaction(String uri, Model model) throws JastorException {
		biochemicalReaction obj = fr.curie.BiNoM.pathways.biopax.biochemicalReactionImpl.createbiochemicalReaction(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of biochemicalReaction.  Leaves the model unchanged.
	 * @param uri The uri of the biochemicalReaction
	 * @param model the Jena Model.
	 */
	public static biochemicalReaction getbiochemicalReaction(String uri, Model model) throws JastorException {
		return getbiochemicalReaction(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of biochemicalReaction.  Leaves the model unchanged.
	 * @param resource The resource of the biochemicalReaction
	 * @param model the Jena Model.
	 */
	public static biochemicalReaction getbiochemicalReaction(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.biochemicalReaction.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.biochemicalReactionImpl obj = (fr.curie.BiNoM.pathways.biopax.biochemicalReactionImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.biochemicalReactionImpl.getbiochemicalReaction(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of biochemicalReaction for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#biochemicalReaction
	 * @param model the Jena Model
	 * @return a List of biochemicalReaction
	 */
	public static java.util.List getAllbiochemicalReaction(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,biochemicalReaction.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getbiochemicalReaction(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of interaction.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the interaction
	 * @param model the Jena Model.
	 */
	public static interaction createinteraction(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.interactionImpl.createinteraction(resource,model);
	}
	
	/**
	 * Create a new instance of interaction.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the interaction
	 * @param model the Jena Model.
	 */
	public static interaction createinteraction(String uri, Model model) throws JastorException {
		interaction obj = fr.curie.BiNoM.pathways.biopax.interactionImpl.createinteraction(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of interaction.  Leaves the model unchanged.
	 * @param uri The uri of the interaction
	 * @param model the Jena Model.
	 */
	public static interaction getinteraction(String uri, Model model) throws JastorException {
		return getinteraction(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of interaction.  Leaves the model unchanged.
	 * @param resource The resource of the interaction
	 * @param model the Jena Model.
	 */
	public static interaction getinteraction(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.interaction.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.interactionImpl obj = (fr.curie.BiNoM.pathways.biopax.interactionImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.interactionImpl.getinteraction(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of interaction for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#interaction
	 * @param model the Jena Model
	 * @return a List of interaction
	 */
	public static java.util.List getAllinteraction(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,interaction.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getinteraction(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of kPrime.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the kPrime
	 * @param model the Jena Model.
	 */
	public static kPrime createkPrime(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.kPrimeImpl.createkPrime(resource,model);
	}
	
	/**
	 * Create a new instance of kPrime.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the kPrime
	 * @param model the Jena Model.
	 */
	public static kPrime createkPrime(String uri, Model model) throws JastorException {
		kPrime obj = fr.curie.BiNoM.pathways.biopax.kPrimeImpl.createkPrime(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of kPrime.  Leaves the model unchanged.
	 * @param uri The uri of the kPrime
	 * @param model the Jena Model.
	 */
	public static kPrime getkPrime(String uri, Model model) throws JastorException {
		return getkPrime(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of kPrime.  Leaves the model unchanged.
	 * @param resource The resource of the kPrime
	 * @param model the Jena Model.
	 */
	public static kPrime getkPrime(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.kPrime.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.kPrimeImpl obj = (fr.curie.BiNoM.pathways.biopax.kPrimeImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.kPrimeImpl.getkPrime(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of kPrime for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#kPrime
	 * @param model the Jena Model
	 * @return a List of kPrime
	 */
	public static java.util.List getAllkPrime(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,kPrime.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getkPrime(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of bioSource.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the bioSource
	 * @param model the Jena Model.
	 */
	public static bioSource createbioSource(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.bioSourceImpl.createbioSource(resource,model);
	}
	
	/**
	 * Create a new instance of bioSource.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the bioSource
	 * @param model the Jena Model.
	 */
	public static bioSource createbioSource(String uri, Model model) throws JastorException {
		bioSource obj = fr.curie.BiNoM.pathways.biopax.bioSourceImpl.createbioSource(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of bioSource.  Leaves the model unchanged.
	 * @param uri The uri of the bioSource
	 * @param model the Jena Model.
	 */
	public static bioSource getbioSource(String uri, Model model) throws JastorException {
		return getbioSource(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of bioSource.  Leaves the model unchanged.
	 * @param resource The resource of the bioSource
	 * @param model the Jena Model.
	 */
	public static bioSource getbioSource(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.bioSource.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.bioSourceImpl obj = (fr.curie.BiNoM.pathways.biopax.bioSourceImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.bioSourceImpl.getbioSource(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of bioSource for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#bioSource
	 * @param model the Jena Model
	 * @return a List of bioSource
	 */
	public static java.util.List getAllbioSource(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,bioSource.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getbioSource(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of smallMolecule.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the smallMolecule
	 * @param model the Jena Model.
	 */
	public static smallMolecule createsmallMolecule(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.smallMoleculeImpl.createsmallMolecule(resource,model);
	}
	
	/**
	 * Create a new instance of smallMolecule.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the smallMolecule
	 * @param model the Jena Model.
	 */
	public static smallMolecule createsmallMolecule(String uri, Model model) throws JastorException {
		smallMolecule obj = fr.curie.BiNoM.pathways.biopax.smallMoleculeImpl.createsmallMolecule(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of smallMolecule.  Leaves the model unchanged.
	 * @param uri The uri of the smallMolecule
	 * @param model the Jena Model.
	 */
	public static smallMolecule getsmallMolecule(String uri, Model model) throws JastorException {
		return getsmallMolecule(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of smallMolecule.  Leaves the model unchanged.
	 * @param resource The resource of the smallMolecule
	 * @param model the Jena Model.
	 */
	public static smallMolecule getsmallMolecule(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.smallMolecule.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.smallMoleculeImpl obj = (fr.curie.BiNoM.pathways.biopax.smallMoleculeImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.smallMoleculeImpl.getsmallMolecule(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of smallMolecule for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#smallMolecule
	 * @param model the Jena Model
	 * @return a List of smallMolecule
	 */
	public static java.util.List getAllsmallMolecule(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,smallMolecule.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getsmallMolecule(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of externalReferenceUtilityClass.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the externalReferenceUtilityClass
	 * @param model the Jena Model.
	 */
	public static externalReferenceUtilityClass createexternalReferenceUtilityClass(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClassImpl.createexternalReferenceUtilityClass(resource,model);
	}
	
	/**
	 * Create a new instance of externalReferenceUtilityClass.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the externalReferenceUtilityClass
	 * @param model the Jena Model.
	 */
	public static externalReferenceUtilityClass createexternalReferenceUtilityClass(String uri, Model model) throws JastorException {
		externalReferenceUtilityClass obj = fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClassImpl.createexternalReferenceUtilityClass(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of externalReferenceUtilityClass.  Leaves the model unchanged.
	 * @param uri The uri of the externalReferenceUtilityClass
	 * @param model the Jena Model.
	 */
	public static externalReferenceUtilityClass getexternalReferenceUtilityClass(String uri, Model model) throws JastorException {
		return getexternalReferenceUtilityClass(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of externalReferenceUtilityClass.  Leaves the model unchanged.
	 * @param resource The resource of the externalReferenceUtilityClass
	 * @param model the Jena Model.
	 */
	public static externalReferenceUtilityClass getexternalReferenceUtilityClass(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClass.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClassImpl obj = (fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClassImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClassImpl.getexternalReferenceUtilityClass(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of externalReferenceUtilityClass for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#externalReferenceUtilityClass
	 * @param model the Jena Model
	 * @return a List of externalReferenceUtilityClass
	 */
	public static java.util.List getAllexternalReferenceUtilityClass(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,externalReferenceUtilityClass.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getexternalReferenceUtilityClass(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of entity.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the entity
	 * @param model the Jena Model.
	 */
	public static entity createentity(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.entityImpl.createentity(resource,model);
	}
	
	/**
	 * Create a new instance of entity.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the entity
	 * @param model the Jena Model.
	 */
	public static entity createentity(String uri, Model model) throws JastorException {
		entity obj = fr.curie.BiNoM.pathways.biopax.entityImpl.createentity(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of entity.  Leaves the model unchanged.
	 * @param uri The uri of the entity
	 * @param model the Jena Model.
	 */
	public static entity getentity(String uri, Model model) throws JastorException {
		return getentity(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of entity.  Leaves the model unchanged.
	 * @param resource The resource of the entity
	 * @param model the Jena Model.
	 */
	public static entity getentity(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.entity.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.entityImpl obj = (fr.curie.BiNoM.pathways.biopax.entityImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.entityImpl.getentity(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of entity for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#entity
	 * @param model the Jena Model
	 * @return a List of entity
	 */
	public static java.util.List getAllentity(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,entity.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getentity(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of conversion.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the conversion
	 * @param model the Jena Model.
	 */
	public static conversion createconversion(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.conversionImpl.createconversion(resource,model);
	}
	
	/**
	 * Create a new instance of conversion.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the conversion
	 * @param model the Jena Model.
	 */
	public static conversion createconversion(String uri, Model model) throws JastorException {
		conversion obj = fr.curie.BiNoM.pathways.biopax.conversionImpl.createconversion(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of conversion.  Leaves the model unchanged.
	 * @param uri The uri of the conversion
	 * @param model the Jena Model.
	 */
	public static conversion getconversion(String uri, Model model) throws JastorException {
		return getconversion(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of conversion.  Leaves the model unchanged.
	 * @param resource The resource of the conversion
	 * @param model the Jena Model.
	 */
	public static conversion getconversion(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.conversion.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.conversionImpl obj = (fr.curie.BiNoM.pathways.biopax.conversionImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.conversionImpl.getconversion(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of conversion for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#conversion
	 * @param model the Jena Model
	 * @return a List of conversion
	 */
	public static java.util.List getAllconversion(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,conversion.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getconversion(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of physicalInteraction.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the physicalInteraction
	 * @param model the Jena Model.
	 */
	public static physicalInteraction createphysicalInteraction(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.physicalInteractionImpl.createphysicalInteraction(resource,model);
	}
	
	/**
	 * Create a new instance of physicalInteraction.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the physicalInteraction
	 * @param model the Jena Model.
	 */
	public static physicalInteraction createphysicalInteraction(String uri, Model model) throws JastorException {
		physicalInteraction obj = fr.curie.BiNoM.pathways.biopax.physicalInteractionImpl.createphysicalInteraction(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of physicalInteraction.  Leaves the model unchanged.
	 * @param uri The uri of the physicalInteraction
	 * @param model the Jena Model.
	 */
	public static physicalInteraction getphysicalInteraction(String uri, Model model) throws JastorException {
		return getphysicalInteraction(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of physicalInteraction.  Leaves the model unchanged.
	 * @param resource The resource of the physicalInteraction
	 * @param model the Jena Model.
	 */
	public static physicalInteraction getphysicalInteraction(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.physicalInteraction.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.physicalInteractionImpl obj = (fr.curie.BiNoM.pathways.biopax.physicalInteractionImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.physicalInteractionImpl.getphysicalInteraction(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of physicalInteraction for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#physicalInteraction
	 * @param model the Jena Model
	 * @return a List of physicalInteraction
	 */
	public static java.util.List getAllphysicalInteraction(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,physicalInteraction.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getphysicalInteraction(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of pathway.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the pathway
	 * @param model the Jena Model.
	 */
	public static pathway createpathway(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.pathwayImpl.createpathway(resource,model);
	}
	
	/**
	 * Create a new instance of pathway.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the pathway
	 * @param model the Jena Model.
	 */
	public static pathway createpathway(String uri, Model model) throws JastorException {
		pathway obj = fr.curie.BiNoM.pathways.biopax.pathwayImpl.createpathway(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of pathway.  Leaves the model unchanged.
	 * @param uri The uri of the pathway
	 * @param model the Jena Model.
	 */
	public static pathway getpathway(String uri, Model model) throws JastorException {
		return getpathway(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of pathway.  Leaves the model unchanged.
	 * @param resource The resource of the pathway
	 * @param model the Jena Model.
	 */
	public static pathway getpathway(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.pathway.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.pathwayImpl obj = (fr.curie.BiNoM.pathways.biopax.pathwayImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.pathwayImpl.getpathway(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of pathway for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#pathway
	 * @param model the Jena Model
	 * @return a List of pathway
	 */
	public static java.util.List getAllpathway(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,pathway.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getpathway(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of sequenceFeature.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the sequenceFeature
	 * @param model the Jena Model.
	 */
	public static sequenceFeature createsequenceFeature(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.createsequenceFeature(resource,model);
	}
	
	/**
	 * Create a new instance of sequenceFeature.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the sequenceFeature
	 * @param model the Jena Model.
	 */
	public static sequenceFeature createsequenceFeature(String uri, Model model) throws JastorException {
		sequenceFeature obj = fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.createsequenceFeature(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of sequenceFeature.  Leaves the model unchanged.
	 * @param uri The uri of the sequenceFeature
	 * @param model the Jena Model.
	 */
	public static sequenceFeature getsequenceFeature(String uri, Model model) throws JastorException {
		return getsequenceFeature(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of sequenceFeature.  Leaves the model unchanged.
	 * @param resource The resource of the sequenceFeature
	 * @param model the Jena Model.
	 */
	public static sequenceFeature getsequenceFeature(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.sequenceFeature.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl obj = (fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.getsequenceFeature(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of sequenceFeature for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#sequenceFeature
	 * @param model the Jena Model
	 * @return a List of sequenceFeature
	 */
	public static java.util.List getAllsequenceFeature(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,sequenceFeature.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getsequenceFeature(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of sequenceLocation.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the sequenceLocation
	 * @param model the Jena Model.
	 */
	public static sequenceLocation createsequenceLocation(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.sequenceLocationImpl.createsequenceLocation(resource,model);
	}
	
	/**
	 * Create a new instance of sequenceLocation.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the sequenceLocation
	 * @param model the Jena Model.
	 */
	public static sequenceLocation createsequenceLocation(String uri, Model model) throws JastorException {
		sequenceLocation obj = fr.curie.BiNoM.pathways.biopax.sequenceLocationImpl.createsequenceLocation(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of sequenceLocation.  Leaves the model unchanged.
	 * @param uri The uri of the sequenceLocation
	 * @param model the Jena Model.
	 */
	public static sequenceLocation getsequenceLocation(String uri, Model model) throws JastorException {
		return getsequenceLocation(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of sequenceLocation.  Leaves the model unchanged.
	 * @param resource The resource of the sequenceLocation
	 * @param model the Jena Model.
	 */
	public static sequenceLocation getsequenceLocation(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.sequenceLocation.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.sequenceLocationImpl obj = (fr.curie.BiNoM.pathways.biopax.sequenceLocationImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.sequenceLocationImpl.getsequenceLocation(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of sequenceLocation for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#sequenceLocation
	 * @param model the Jena Model
	 * @return a List of sequenceLocation
	 */
	public static java.util.List getAllsequenceLocation(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,sequenceLocation.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getsequenceLocation(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of evidence.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the evidence
	 * @param model the Jena Model.
	 */
	public static evidence createevidence(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.evidenceImpl.createevidence(resource,model);
	}
	
	/**
	 * Create a new instance of evidence.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the evidence
	 * @param model the Jena Model.
	 */
	public static evidence createevidence(String uri, Model model) throws JastorException {
		evidence obj = fr.curie.BiNoM.pathways.biopax.evidenceImpl.createevidence(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of evidence.  Leaves the model unchanged.
	 * @param uri The uri of the evidence
	 * @param model the Jena Model.
	 */
	public static evidence getevidence(String uri, Model model) throws JastorException {
		return getevidence(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of evidence.  Leaves the model unchanged.
	 * @param resource The resource of the evidence
	 * @param model the Jena Model.
	 */
	public static evidence getevidence(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.evidence.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.evidenceImpl obj = (fr.curie.BiNoM.pathways.biopax.evidenceImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.evidenceImpl.getevidence(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of evidence for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#evidence
	 * @param model the Jena Model
	 * @return a List of evidence
	 */
	public static java.util.List getAllevidence(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,evidence.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getevidence(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of rna.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the rna
	 * @param model the Jena Model.
	 */
	public static rna createrna(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.rnaImpl.createrna(resource,model);
	}
	
	/**
	 * Create a new instance of rna.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the rna
	 * @param model the Jena Model.
	 */
	public static rna createrna(String uri, Model model) throws JastorException {
		rna obj = fr.curie.BiNoM.pathways.biopax.rnaImpl.createrna(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of rna.  Leaves the model unchanged.
	 * @param uri The uri of the rna
	 * @param model the Jena Model.
	 */
	public static rna getrna(String uri, Model model) throws JastorException {
		return getrna(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of rna.  Leaves the model unchanged.
	 * @param resource The resource of the rna
	 * @param model the Jena Model.
	 */
	public static rna getrna(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.rna.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.rnaImpl obj = (fr.curie.BiNoM.pathways.biopax.rnaImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.rnaImpl.getrna(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of rna for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#rna
	 * @param model the Jena Model
	 * @return a List of rna
	 */
	public static java.util.List getAllrna(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,rna.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getrna(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of xref.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the xref
	 * @param model the Jena Model.
	 */
	public static xref createxref(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.xrefImpl.createxref(resource,model);
	}
	
	/**
	 * Create a new instance of xref.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the xref
	 * @param model the Jena Model.
	 */
	public static xref createxref(String uri, Model model) throws JastorException {
		xref obj = fr.curie.BiNoM.pathways.biopax.xrefImpl.createxref(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of xref.  Leaves the model unchanged.
	 * @param uri The uri of the xref
	 * @param model the Jena Model.
	 */
	public static xref getxref(String uri, Model model) throws JastorException {
		return getxref(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of xref.  Leaves the model unchanged.
	 * @param resource The resource of the xref
	 * @param model the Jena Model.
	 */
	public static xref getxref(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.xref.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.xrefImpl obj = (fr.curie.BiNoM.pathways.biopax.xrefImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.xrefImpl.getxref(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of xref for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#xref
	 * @param model the Jena Model
	 * @return a List of xref
	 */
	public static java.util.List getAllxref(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,xref.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getxref(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of modulation.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the modulation
	 * @param model the Jena Model.
	 */
	public static modulation createmodulation(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.modulationImpl.createmodulation(resource,model);
	}
	
	/**
	 * Create a new instance of modulation.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the modulation
	 * @param model the Jena Model.
	 */
	public static modulation createmodulation(String uri, Model model) throws JastorException {
		modulation obj = fr.curie.BiNoM.pathways.biopax.modulationImpl.createmodulation(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of modulation.  Leaves the model unchanged.
	 * @param uri The uri of the modulation
	 * @param model the Jena Model.
	 */
	public static modulation getmodulation(String uri, Model model) throws JastorException {
		return getmodulation(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of modulation.  Leaves the model unchanged.
	 * @param resource The resource of the modulation
	 * @param model the Jena Model.
	 */
	public static modulation getmodulation(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.modulation.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.modulationImpl obj = (fr.curie.BiNoM.pathways.biopax.modulationImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.modulationImpl.getmodulation(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of modulation for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#modulation
	 * @param model the Jena Model
	 * @return a List of modulation
	 */
	public static java.util.List getAllmodulation(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,modulation.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getmodulation(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of protein.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the protein
	 * @param model the Jena Model.
	 */
	public static protein createprotein(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.proteinImpl.createprotein(resource,model);
	}
	
	/**
	 * Create a new instance of protein.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the protein
	 * @param model the Jena Model.
	 */
	public static protein createprotein(String uri, Model model) throws JastorException {
		protein obj = fr.curie.BiNoM.pathways.biopax.proteinImpl.createprotein(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of protein.  Leaves the model unchanged.
	 * @param uri The uri of the protein
	 * @param model the Jena Model.
	 */
	public static protein getprotein(String uri, Model model) throws JastorException {
		return getprotein(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of protein.  Leaves the model unchanged.
	 * @param resource The resource of the protein
	 * @param model the Jena Model.
	 */
	public static protein getprotein(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.protein.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.proteinImpl obj = (fr.curie.BiNoM.pathways.biopax.proteinImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.proteinImpl.getprotein(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of protein for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#protein
	 * @param model the Jena Model
	 * @return a List of protein
	 */
	public static java.util.List getAllprotein(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,protein.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getprotein(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of utilityClass.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the utilityClass
	 * @param model the Jena Model.
	 */
	public static utilityClass createutilityClass(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.utilityClassImpl.createutilityClass(resource,model);
	}
	
	/**
	 * Create a new instance of utilityClass.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the utilityClass
	 * @param model the Jena Model.
	 */
	public static utilityClass createutilityClass(String uri, Model model) throws JastorException {
		utilityClass obj = fr.curie.BiNoM.pathways.biopax.utilityClassImpl.createutilityClass(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of utilityClass.  Leaves the model unchanged.
	 * @param uri The uri of the utilityClass
	 * @param model the Jena Model.
	 */
	public static utilityClass getutilityClass(String uri, Model model) throws JastorException {
		return getutilityClass(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of utilityClass.  Leaves the model unchanged.
	 * @param resource The resource of the utilityClass
	 * @param model the Jena Model.
	 */
	public static utilityClass getutilityClass(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.utilityClass.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.utilityClassImpl obj = (fr.curie.BiNoM.pathways.biopax.utilityClassImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.utilityClassImpl.getutilityClass(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of utilityClass for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#utilityClass
	 * @param model the Jena Model
	 * @return a List of utilityClass
	 */
	public static java.util.List getAllutilityClass(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,utilityClass.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getutilityClass(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of openControlledVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the openControlledVocabulary
	 * @param model the Jena Model.
	 */
	public static openControlledVocabulary createopenControlledVocabulary(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.openControlledVocabularyImpl.createopenControlledVocabulary(resource,model);
	}
	
	/**
	 * Create a new instance of openControlledVocabulary.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the openControlledVocabulary
	 * @param model the Jena Model.
	 */
	public static openControlledVocabulary createopenControlledVocabulary(String uri, Model model) throws JastorException {
		openControlledVocabulary obj = fr.curie.BiNoM.pathways.biopax.openControlledVocabularyImpl.createopenControlledVocabulary(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of openControlledVocabulary.  Leaves the model unchanged.
	 * @param uri The uri of the openControlledVocabulary
	 * @param model the Jena Model.
	 */
	public static openControlledVocabulary getopenControlledVocabulary(String uri, Model model) throws JastorException {
		return getopenControlledVocabulary(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of openControlledVocabulary.  Leaves the model unchanged.
	 * @param resource The resource of the openControlledVocabulary
	 * @param model the Jena Model.
	 */
	public static openControlledVocabulary getopenControlledVocabulary(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.openControlledVocabulary.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.openControlledVocabularyImpl obj = (fr.curie.BiNoM.pathways.biopax.openControlledVocabularyImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.openControlledVocabularyImpl.getopenControlledVocabulary(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of openControlledVocabulary for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#openControlledVocabulary
	 * @param model the Jena Model
	 * @return a List of openControlledVocabulary
	 */
	public static java.util.List getAllopenControlledVocabulary(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,openControlledVocabulary.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getopenControlledVocabulary(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of deltaGprimeO.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the deltaGprimeO
	 * @param model the Jena Model.
	 */
	public static deltaGprimeO createdeltaGprimeO(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.deltaGprimeOImpl.createdeltaGprimeO(resource,model);
	}
	
	/**
	 * Create a new instance of deltaGprimeO.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the deltaGprimeO
	 * @param model the Jena Model.
	 */
	public static deltaGprimeO createdeltaGprimeO(String uri, Model model) throws JastorException {
		deltaGprimeO obj = fr.curie.BiNoM.pathways.biopax.deltaGprimeOImpl.createdeltaGprimeO(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of deltaGprimeO.  Leaves the model unchanged.
	 * @param uri The uri of the deltaGprimeO
	 * @param model the Jena Model.
	 */
	public static deltaGprimeO getdeltaGprimeO(String uri, Model model) throws JastorException {
		return getdeltaGprimeO(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of deltaGprimeO.  Leaves the model unchanged.
	 * @param resource The resource of the deltaGprimeO
	 * @param model the Jena Model.
	 */
	public static deltaGprimeO getdeltaGprimeO(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.deltaGprimeO.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.deltaGprimeOImpl obj = (fr.curie.BiNoM.pathways.biopax.deltaGprimeOImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.deltaGprimeOImpl.getdeltaGprimeO(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of deltaGprimeO for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#deltaGprimeO
	 * @param model the Jena Model
	 * @return a List of deltaGprimeO
	 */
	public static java.util.List getAlldeltaGprimeO(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,deltaGprimeO.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getdeltaGprimeO(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of pathwayStep.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the pathwayStep
	 * @param model the Jena Model.
	 */
	public static pathwayStep createpathwayStep(Resource resource, Model model) throws JastorException {
		return fr.curie.BiNoM.pathways.biopax.pathwayStepImpl.createpathwayStep(resource,model);
	}
	
	/**
	 * Create a new instance of pathwayStep.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the pathwayStep
	 * @param model the Jena Model.
	 */
	public static pathwayStep createpathwayStep(String uri, Model model) throws JastorException {
		pathwayStep obj = fr.curie.BiNoM.pathways.biopax.pathwayStepImpl.createpathwayStep(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of pathwayStep.  Leaves the model unchanged.
	 * @param uri The uri of the pathwayStep
	 * @param model the Jena Model.
	 */
	public static pathwayStep getpathwayStep(String uri, Model model) throws JastorException {
		return getpathwayStep(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of pathwayStep.  Leaves the model unchanged.
	 * @param resource The resource of the pathwayStep
	 * @param model the Jena Model.
	 */
	public static pathwayStep getpathwayStep(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + fr.curie.BiNoM.pathways.biopax.pathwayStep.class.hashCode()) + resource.toString();
		fr.curie.BiNoM.pathways.biopax.pathwayStepImpl obj = (fr.curie.BiNoM.pathways.biopax.pathwayStepImpl)objects.get(code);
		if (obj == null) {
			obj = fr.curie.BiNoM.pathways.biopax.pathwayStepImpl.getpathwayStep(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of pathwayStep for every resource in the model with rdf:Type http://www.biopax.org/release/biopax-level2.owl#pathwayStep
	 * @param model the Jena Model
	 * @return a List of pathwayStep
	 */
	public static java.util.List getAllpathwayStep(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,pathwayStep.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getpathwayStep(stmt.getSubject(),model));
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
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#pathwayStep"))) {
			return getpathwayStep(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#deltaGprimeO"))) {
			return getdeltaGprimeO(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#openControlledVocabulary"))) {
			return getopenControlledVocabulary(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#protein"))) {
			return getprotein(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#modulation"))) {
			return getmodulation(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#rna"))) {
			return getrna(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#evidence"))) {
			return getevidence(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#sequenceFeature"))) {
			return getsequenceFeature(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#pathway"))) {
			return getpathway(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#smallMolecule"))) {
			return getsmallMolecule(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#bioSource"))) {
			return getbioSource(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#kPrime"))) {
			return getkPrime(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#transportWithBiochemicalReaction"))) {
			return gettransportWithBiochemicalReaction(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#transport"))) {
			return gettransport(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#biochemicalReaction"))) {
			return getbiochemicalReaction(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#unificationXref"))) {
			return getunificationXref(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#sequenceSite"))) {
			return getsequenceSite(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#sequenceParticipant"))) {
			return getsequenceParticipant(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#physicalEntityParticipant"))) {
			return getphysicalEntityParticipant(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#dna"))) {
			return getdna(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#dataSource"))) {
			return getdataSource(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#complex"))) {
			return getcomplex(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#physicalEntity"))) {
			return getphysicalEntity(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#sequenceInterval"))) {
			return getsequenceInterval(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#sequenceLocation"))) {
			return getsequenceLocation(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#chemicalStructure"))) {
			return getchemicalStructure(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#complexAssembly"))) {
			return getcomplexAssembly(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#conversion"))) {
			return getconversion(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#catalysis"))) {
			return getcatalysis(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#control"))) {
			return getcontrol(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#physicalInteraction"))) {
			return getphysicalInteraction(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#interaction"))) {
			return getinteraction(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#entity"))) {
			return getentity(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#experimentalForm"))) {
			return getexperimentalForm(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#publicationXref"))) {
			return getpublicationXref(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#relationshipXref"))) {
			return getrelationshipXref(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#xref"))) {
			return getxref(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#externalReferenceUtilityClass"))) {
			return getexternalReferenceUtilityClass(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#confidence"))) {
			return getconfidence(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://www.biopax.org/release/biopax-level2.owl#utilityClass"))) {
			return getutilityClass(res,model);
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
	 * in the biopax-level2.owl ontology.  The list is sorted according to the topological sort
	 * of the class hierarchy
	 * @return a List of type java.lang.Class
	 */
	public static java.util.List listCompatibleInterfaces (com.hp.hpl.jena.rdf.model.Resource type) {
		java.util.List types = new java.util.ArrayList();
		if (type.equals(fr.curie.BiNoM.pathways.biopax.pathwayStep.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.pathwayStep.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.deltaGprimeO.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.deltaGprimeO.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.protein.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.protein.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.modulation.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.modulation.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.rna.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.rna.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.evidence.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.evidence.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.sequenceFeature.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.sequenceFeature.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.pathway.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.pathway.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.smallMolecule.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.smallMolecule.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.bioSource.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.bioSource.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.kPrime.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.kPrime.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.transportWithBiochemicalReaction.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.transportWithBiochemicalReaction.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.transport.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.transport.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.biochemicalReaction.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.biochemicalReaction.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.unificationXref.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.unificationXref.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.sequenceSite.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.sequenceSite.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.sequenceParticipant.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.sequenceParticipant.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.dna.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.dna.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.dataSource.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.dataSource.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.complex.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.complex.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.physicalEntity.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.physicalEntity.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.sequenceInterval.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.sequenceInterval.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.sequenceLocation.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.sequenceLocation.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.chemicalStructure.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.chemicalStructure.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.complexAssembly.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.complexAssembly.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.conversion.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.conversion.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.catalysis.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.catalysis.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.control.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.control.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.physicalInteraction.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.physicalInteraction.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.interaction.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.interaction.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.entity.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.entity.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.experimentalForm.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.experimentalForm.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.publicationXref.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.publicationXref.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.relationshipXref.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.relationshipXref.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.xref.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.xref.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClass.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClass.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.confidence.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.confidence.class);
		}
		if (type.equals(fr.curie.BiNoM.pathways.biopax.utilityClass.TYPE)) {
			types.add(fr.curie.BiNoM.pathways.biopax.utilityClass.class);
		}
		return types;
	}
}