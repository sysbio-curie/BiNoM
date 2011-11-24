

package fr.curie.BiNoM.pathways.biopax;

/*
import com.hp.hpl.jena.datatypes.xsd.*;
import com.hp.hpl.jena.datatypes.xsd.impl.*;
*/
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.listeners.StatementListener;
import com.hp.hpl.jena.vocabulary.RDF;
import com.ibm.adtech.jastor.*;
import com.ibm.adtech.jastor.util.*;


/**
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulation}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#TemplateReactionRegulation)</p>
 * <br>
 */
public class TemplateReactionRegulationImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulation {
	

	private static com.hp.hpl.jena.rdf.model.Property dataSourceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#dataSource");
	private java.util.ArrayList dataSource;
	private static com.hp.hpl.jena.rdf.model.Property availabilityProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#availability");
	private java.util.ArrayList availability;
	private static com.hp.hpl.jena.rdf.model.Property commentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#comment");
	private java.util.ArrayList comment;
	private static com.hp.hpl.jena.rdf.model.Property nameProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#name");
	private java.util.ArrayList name;
	private static com.hp.hpl.jena.rdf.model.Property evidenceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#evidence");
	private java.util.ArrayList evidence;
	private static com.hp.hpl.jena.rdf.model.Property xrefProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#xref");
	private java.util.ArrayList xref;
	private static com.hp.hpl.jena.rdf.model.Property interactionTypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#interactionType");
	private fr.curie.BiNoM.pathways.biopax.InteractionVocabulary interactionType;
	private static com.hp.hpl.jena.rdf.model.Property participantProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#participant");
	private java.util.ArrayList participant;
	private static com.hp.hpl.jena.rdf.model.Property controlledProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#controlled");
	private fr.curie.BiNoM.pathways.biopax.TemplateReaction controlled_asTemplateReaction;
	private fr.curie.BiNoM.pathways.biopax.Interaction controlled_asInteraction;
	private fr.curie.BiNoM.pathways.biopax.Pathway controlled_asPathway;
	private static com.hp.hpl.jena.rdf.model.Property controllerProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#controller");
	private java.util.ArrayList controller_asPhysicalEntity;
	private java.util.ArrayList controller_asPathway;
	private static com.hp.hpl.jena.rdf.model.Property controlTypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#controlType");
	private java.lang.String controlType;

	TemplateReactionRegulationImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static TemplateReactionRegulationImpl getTemplateReactionRegulation(Resource resource, Model model) throws JastorException {
		return new TemplateReactionRegulationImpl(resource, model);
	}
	    
	static TemplateReactionRegulationImpl createTemplateReactionRegulation(Resource resource, Model model) throws JastorException {
		TemplateReactionRegulationImpl impl = new TemplateReactionRegulationImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, TemplateReactionRegulation.TYPE)))
			impl._model.add(impl._resource, RDF.type, TemplateReactionRegulation.TYPE);
		impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Entity.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Entity.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Interaction.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Interaction.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Control.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Control.TYPE));     
	}
   
	void addHasValueValues() {
	}
    
    private void setupModelListener() {
    	listeners = new java.util.ArrayList();
    	fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.registerThing(this);
    }

	public java.util.List listStatements() {
		java.util.List list = new java.util.ArrayList();
		StmtIterator it = null;
		it = _model.listStatements(_resource,dataSourceProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,availabilityProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,commentProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,nameProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,evidenceProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,xrefProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,interactionTypeProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,participantProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,controlledProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,controllerProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,controlTypeProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulation.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.Entity.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.Interaction.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.Control.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
	
	public void clearCache() {
		dataSource = null;
		availability = null;
		comment = null;
		name = null;
		evidence = null;
		xref = null;
		interactionType = null;
		participant = null;
		controlled_asTemplateReaction = null;
		controlled_asInteraction = null;
		controlled_asPathway = null;
		controller_asPhysicalEntity = null;
		controller_asPathway = null;
		controlType = null;
	}


	private void initDataSource() throws JastorException {
		this.dataSource = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, dataSourceProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#dataSource properties in TemplateReactionRegulation model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.Provenance dataSource = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getProvenance(resource,_model);
				this.dataSource.add(dataSource);
			}
		}
	}

	public java.util.Iterator getDataSource() throws JastorException {
		if (dataSource == null)
			initDataSource();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(dataSource,_resource,dataSourceProperty,true);
	}

	public void addDataSource(fr.curie.BiNoM.pathways.biopax.Provenance dataSource) throws JastorException {
		if (this.dataSource == null)
			initDataSource();
		if (this.dataSource.contains(dataSource)) {
			this.dataSource.remove(dataSource);
			this.dataSource.add(dataSource);
			return;
		}
		this.dataSource.add(dataSource);
		_model.add(_model.createStatement(_resource,dataSourceProperty,dataSource.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Provenance addDataSource() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Provenance dataSource = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createProvenance(_model.createResource(),_model);
		if (this.dataSource == null)
			initDataSource();
		this.dataSource.add(dataSource);
		_model.add(_model.createStatement(_resource,dataSourceProperty,dataSource.resource()));
		return dataSource;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Provenance addDataSource(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Provenance dataSource = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getProvenance(resource,_model);
		if (this.dataSource == null)
			initDataSource();
		if (this.dataSource.contains(dataSource))
			return dataSource;
		this.dataSource.add(dataSource);
		_model.add(_model.createStatement(_resource,dataSourceProperty,dataSource.resource()));
		return dataSource;
	}
	
	public void removeDataSource(fr.curie.BiNoM.pathways.biopax.Provenance dataSource) throws JastorException {
		if (this.dataSource == null)
			initDataSource();
		if (!this.dataSource.contains(dataSource))
			return;
		if (!_model.contains(_resource, dataSourceProperty, dataSource.resource()))
			return;
		this.dataSource.remove(dataSource);
		_model.removeAll(_resource, dataSourceProperty, dataSource.resource());
	}
		 

	private void initAvailability() throws JastorException {
		availability = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, availabilityProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#availability properties in TemplateReactionRegulation model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			availability.add(Util.fixLiteral(literal.getValue(),"java.lang.String"));
		}
	}

	public java.util.Iterator getAvailability() throws JastorException {
		if (availability == null)
			initAvailability();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(availability,_resource,availabilityProperty,false);
	}

	public void addAvailability(java.lang.String availability) throws JastorException {
		if (this.availability == null)
			initAvailability();
		if (this.availability.contains(availability))
			return;
		if (_model.contains(_resource, availabilityProperty, _model.createTypedLiteral(availability)))
			return;
		this.availability.add(availability);
		_model.add(_resource, availabilityProperty, _model.createTypedLiteral(availability));
	}
	
	public void removeAvailability(java.lang.String availability) throws JastorException {
		if (this.availability == null)
			initAvailability();
		if (!this.availability.contains(availability))
			return;
		if (!_model.contains(_resource, availabilityProperty, _model.createTypedLiteral(availability)))
			return;
		this.availability.remove(availability);
		_model.removeAll(_resource, availabilityProperty, _model.createTypedLiteral(availability));
	}


	private void initComment() throws JastorException {
		comment = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, commentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#comment properties in TemplateReactionRegulation model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			comment.add(Util.fixLiteral(literal.getValue(),"java.lang.String"));
		}
	}

	public java.util.Iterator getComment() throws JastorException {
		if (comment == null)
			initComment();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(comment,_resource,commentProperty,false);
	}

	public void addComment(java.lang.String comment) throws JastorException {
		if (this.comment == null)
			initComment();
		if (this.comment.contains(comment))
			return;
		if (_model.contains(_resource, commentProperty, _model.createTypedLiteral(comment)))
			return;
		this.comment.add(comment);
		_model.add(_resource, commentProperty, _model.createTypedLiteral(comment));
	}
	
	public void removeComment(java.lang.String comment) throws JastorException {
		if (this.comment == null)
			initComment();
		if (!this.comment.contains(comment))
			return;
		if (!_model.contains(_resource, commentProperty, _model.createTypedLiteral(comment)))
			return;
		this.comment.remove(comment);
		_model.removeAll(_resource, commentProperty, _model.createTypedLiteral(comment));
	}


	private void initName() throws JastorException {
		name = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, nameProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#name properties in TemplateReactionRegulation model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			name.add(Util.fixLiteral(literal.getValue(),"java.lang.String"));
		}
	}

	public java.util.Iterator getName() throws JastorException {
		if (name == null)
			initName();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(name,_resource,nameProperty,false);
	}

	public void addName(java.lang.String name) throws JastorException {
		if (this.name == null)
			initName();
		if (this.name.contains(name))
			return;
		if (_model.contains(_resource, nameProperty, _model.createTypedLiteral(name)))
			return;
		this.name.add(name);
		_model.add(_resource, nameProperty, _model.createTypedLiteral(name));
	}
	
	public void removeName(java.lang.String name) throws JastorException {
		if (this.name == null)
			initName();
		if (!this.name.contains(name))
			return;
		if (!_model.contains(_resource, nameProperty, _model.createTypedLiteral(name)))
			return;
		this.name.remove(name);
		_model.removeAll(_resource, nameProperty, _model.createTypedLiteral(name));
	}


	private void initEvidence() throws JastorException {
		this.evidence = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, evidenceProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#evidence properties in TemplateReactionRegulation model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.Evidence evidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEvidence(resource,_model);
				this.evidence.add(evidence);
			}
		}
	}

	public java.util.Iterator getEvidence() throws JastorException {
		if (evidence == null)
			initEvidence();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(evidence,_resource,evidenceProperty,true);
	}

	public void addEvidence(fr.curie.BiNoM.pathways.biopax.Evidence evidence) throws JastorException {
		if (this.evidence == null)
			initEvidence();
		if (this.evidence.contains(evidence)) {
			this.evidence.remove(evidence);
			this.evidence.add(evidence);
			return;
		}
		this.evidence.add(evidence);
		_model.add(_model.createStatement(_resource,evidenceProperty,evidence.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Evidence addEvidence() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Evidence evidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createEvidence(_model.createResource(),_model);
		if (this.evidence == null)
			initEvidence();
		this.evidence.add(evidence);
		_model.add(_model.createStatement(_resource,evidenceProperty,evidence.resource()));
		return evidence;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Evidence addEvidence(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Evidence evidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEvidence(resource,_model);
		if (this.evidence == null)
			initEvidence();
		if (this.evidence.contains(evidence))
			return evidence;
		this.evidence.add(evidence);
		_model.add(_model.createStatement(_resource,evidenceProperty,evidence.resource()));
		return evidence;
	}
	
	public void removeEvidence(fr.curie.BiNoM.pathways.biopax.Evidence evidence) throws JastorException {
		if (this.evidence == null)
			initEvidence();
		if (!this.evidence.contains(evidence))
			return;
		if (!_model.contains(_resource, evidenceProperty, evidence.resource()))
			return;
		this.evidence.remove(evidence);
		_model.removeAll(_resource, evidenceProperty, evidence.resource());
	}
		 

	private void initXref() throws JastorException {
		this.xref = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, xrefProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#xref properties in TemplateReactionRegulation model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.Xref xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getXref(resource,_model);
				this.xref.add(xref);
			}
		}
	}

	public java.util.Iterator getXref() throws JastorException {
		if (xref == null)
			initXref();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(xref,_resource,xrefProperty,true);
	}

	public void addXref(fr.curie.BiNoM.pathways.biopax.Xref xref) throws JastorException {
		if (this.xref == null)
			initXref();
		if (this.xref.contains(xref)) {
			this.xref.remove(xref);
			this.xref.add(xref);
			return;
		}
		this.xref.add(xref);
		_model.add(_model.createStatement(_resource,xrefProperty,xref.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Xref addXref() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Xref xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createXref(_model.createResource(),_model);
		if (this.xref == null)
			initXref();
		this.xref.add(xref);
		_model.add(_model.createStatement(_resource,xrefProperty,xref.resource()));
		return xref;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Xref addXref(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Xref xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getXref(resource,_model);
		if (this.xref == null)
			initXref();
		if (this.xref.contains(xref))
			return xref;
		this.xref.add(xref);
		_model.add(_model.createStatement(_resource,xrefProperty,xref.resource()));
		return xref;
	}
	
	public void removeXref(fr.curie.BiNoM.pathways.biopax.Xref xref) throws JastorException {
		if (this.xref == null)
			initXref();
		if (!this.xref.contains(xref))
			return;
		if (!_model.contains(_resource, xrefProperty, xref.resource()))
			return;
		this.xref.remove(xref);
		_model.removeAll(_resource, xrefProperty, xref.resource());
	}
		 

	public fr.curie.BiNoM.pathways.biopax.InteractionVocabulary getInteractionType() throws JastorException {
		if (interactionType != null)
			return interactionType;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, interactionTypeProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": interactionType getProperty() in fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulation model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		interactionType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteractionVocabulary(resource,_model);
		return interactionType;
	}

	public void setInteractionType(fr.curie.BiNoM.pathways.biopax.InteractionVocabulary interactionType) throws JastorException {
		if (_model.contains(_resource,interactionTypeProperty)) {
			_model.removeAll(_resource,interactionTypeProperty,null);
		}
		this.interactionType = interactionType;
		if (interactionType != null) {
			_model.add(_model.createStatement(_resource,interactionTypeProperty, interactionType.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.InteractionVocabulary setInteractionType() throws JastorException {
		if (_model.contains(_resource,interactionTypeProperty)) {
			_model.removeAll(_resource,interactionTypeProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.InteractionVocabulary interactionType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createInteractionVocabulary(_model.createResource(),_model);
		this.interactionType = interactionType;
		_model.add(_model.createStatement(_resource,interactionTypeProperty, interactionType.resource()));
		return interactionType;
	}
	
	public fr.curie.BiNoM.pathways.biopax.InteractionVocabulary setInteractionType(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,interactionTypeProperty)) {
			_model.removeAll(_resource,interactionTypeProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.InteractionVocabulary interactionType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteractionVocabulary(resource,_model);
		this.interactionType = interactionType;
		_model.add(_model.createStatement(_resource,interactionTypeProperty, interactionType.resource()));
		return interactionType;
	}
	

	private void initParticipant() throws JastorException {
		this.participant = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, participantProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#participant properties in TemplateReactionRegulation model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.Entity participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntity(resource,_model);
				this.participant.add(participant);
			}
		}
	}

	public java.util.Iterator getParticipant() throws JastorException {
		if (participant == null)
			initParticipant();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(participant,_resource,participantProperty,true);
	}

	public void addParticipant(fr.curie.BiNoM.pathways.biopax.Entity participant) throws JastorException {
		if (this.participant == null)
			initParticipant();
		if (this.participant.contains(participant)) {
			this.participant.remove(participant);
			this.participant.add(participant);
			return;
		}
		this.participant.add(participant);
		_model.add(_model.createStatement(_resource,participantProperty,participant.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Entity addParticipant() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Entity participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createEntity(_model.createResource(),_model);
		if (this.participant == null)
			initParticipant();
		this.participant.add(participant);
		_model.add(_model.createStatement(_resource,participantProperty,participant.resource()));
		return participant;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Entity addParticipant(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Entity participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntity(resource,_model);
		if (this.participant == null)
			initParticipant();
		if (this.participant.contains(participant))
			return participant;
		this.participant.add(participant);
		_model.add(_model.createStatement(_resource,participantProperty,participant.resource()));
		return participant;
	}
	
	public void removeParticipant(fr.curie.BiNoM.pathways.biopax.Entity participant) throws JastorException {
		if (this.participant == null)
			initParticipant();
		if (!this.participant.contains(participant))
			return;
		if (!_model.contains(_resource, participantProperty, participant.resource()))
			return;
		this.participant.remove(participant);
		_model.removeAll(_resource, participantProperty, participant.resource());
	}
		 

	public fr.curie.BiNoM.pathways.biopax.TemplateReaction getControlled_asTemplateReaction() throws JastorException {
		if (controlled_asTemplateReaction != null)
			return controlled_asTemplateReaction;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, controlledProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": controlled_asTemplateReaction getProperty() in fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulation model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		if (!_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.TemplateReaction.TYPE))
			return null;
		controlled_asTemplateReaction = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getTemplateReaction(resource,_model);
		return controlled_asTemplateReaction;
	}

	public void setControlled(fr.curie.BiNoM.pathways.biopax.TemplateReaction controlled) throws JastorException {
		if (_model.contains(_resource,controlledProperty)) {
			_model.removeAll(_resource,controlledProperty,null);
		}
		this.controlled_asTemplateReaction = controlled;
		if (controlled != null) {
			_model.add(_model.createStatement(_resource,controlledProperty, controlled.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.TemplateReaction setControlled_asTemplateReaction() throws JastorException {
		if (_model.contains(_resource,controlledProperty)) {
			_model.removeAll(_resource,controlledProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.TemplateReaction controlled = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createTemplateReaction(_model.createResource(),_model);
		this.controlled_asTemplateReaction = controlled;
		_model.add(_model.createStatement(_resource,controlledProperty, controlled.resource()));
		return controlled;
	}
	
	public fr.curie.BiNoM.pathways.biopax.TemplateReaction setControlled_asTemplateReaction(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,controlledProperty)) {
			_model.removeAll(_resource,controlledProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.TemplateReaction controlled = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getTemplateReaction(resource,_model);
		this.controlled_asTemplateReaction = controlled;
		_model.add(_model.createStatement(_resource,controlledProperty, controlled.resource()));
		return controlled;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Interaction getControlled_asInteraction() throws JastorException {
		if (controlled_asInteraction != null)
			return controlled_asInteraction;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, controlledProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": controlled_asInteraction getProperty() in fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulation model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		if (!_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Interaction.TYPE))
			return null;
		controlled_asInteraction = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteraction(resource,_model);
		return controlled_asInteraction;
	}

	public void setControlled(fr.curie.BiNoM.pathways.biopax.Interaction controlled) throws JastorException {
		if (_model.contains(_resource,controlledProperty)) {
			_model.removeAll(_resource,controlledProperty,null);
		}
		this.controlled_asInteraction = controlled;
		if (controlled != null) {
			_model.add(_model.createStatement(_resource,controlledProperty, controlled.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.Interaction setControlled_asInteraction() throws JastorException {
		if (_model.contains(_resource,controlledProperty)) {
			_model.removeAll(_resource,controlledProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.Interaction controlled = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createInteraction(_model.createResource(),_model);
		this.controlled_asInteraction = controlled;
		_model.add(_model.createStatement(_resource,controlledProperty, controlled.resource()));
		return controlled;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Interaction setControlled_asInteraction(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,controlledProperty)) {
			_model.removeAll(_resource,controlledProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.Interaction controlled = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteraction(resource,_model);
		this.controlled_asInteraction = controlled;
		_model.add(_model.createStatement(_resource,controlledProperty, controlled.resource()));
		return controlled;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Pathway getControlled_asPathway() throws JastorException {
		if (controlled_asPathway != null)
			return controlled_asPathway;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, controlledProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": controlled_asPathway getProperty() in fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulation model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		if (!_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Pathway.TYPE))
			return null;
		controlled_asPathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
		return controlled_asPathway;
	}

	public void setControlled(fr.curie.BiNoM.pathways.biopax.Pathway controlled) throws JastorException {
		if (_model.contains(_resource,controlledProperty)) {
			_model.removeAll(_resource,controlledProperty,null);
		}
		this.controlled_asPathway = controlled;
		if (controlled != null) {
			_model.add(_model.createStatement(_resource,controlledProperty, controlled.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.Pathway setControlled_asPathway() throws JastorException {
		if (_model.contains(_resource,controlledProperty)) {
			_model.removeAll(_resource,controlledProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.Pathway controlled = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createPathway(_model.createResource(),_model);
		this.controlled_asPathway = controlled;
		_model.add(_model.createStatement(_resource,controlledProperty, controlled.resource()));
		return controlled;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Pathway setControlled_asPathway(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,controlledProperty)) {
			_model.removeAll(_resource,controlledProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.Pathway controlled = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
		this.controlled_asPathway = controlled;
		_model.add(_model.createStatement(_resource,controlledProperty, controlled.resource()));
		return controlled;
	}
	

	private void initController_asPhysicalEntity() throws JastorException {
		this.controller_asPhysicalEntity = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, controllerProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#controller properties in TemplateReactionRegulation model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.PhysicalEntity.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.PhysicalEntity controller = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
				this.controller_asPhysicalEntity.add(controller);
			}
		}
	}

	public java.util.Iterator getController_asPhysicalEntity() throws JastorException {
		if (controller_asPhysicalEntity == null)
			initController_asPhysicalEntity();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(controller_asPhysicalEntity,_resource,controllerProperty,true);
	}

	public void addController(fr.curie.BiNoM.pathways.biopax.PhysicalEntity controller) throws JastorException {
		if (this.controller_asPhysicalEntity == null)
			initController_asPhysicalEntity();
		if (this.controller_asPhysicalEntity.contains(controller)) {
			this.controller_asPhysicalEntity.remove(controller);
			this.controller_asPhysicalEntity.add(controller);
			return;
		}
		this.controller_asPhysicalEntity.add(controller);
		_model.add(_model.createStatement(_resource,controllerProperty,controller.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addController_asPhysicalEntity() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PhysicalEntity controller = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createPhysicalEntity(_model.createResource(),_model);
		if (this.controller_asPhysicalEntity == null)
			initController_asPhysicalEntity();
		this.controller_asPhysicalEntity.add(controller);
		_model.add(_model.createStatement(_resource,controllerProperty,controller.resource()));
		return controller;
	}
	
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addController_asPhysicalEntity(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PhysicalEntity controller = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
		if (this.controller_asPhysicalEntity == null)
			initController_asPhysicalEntity();
		if (this.controller_asPhysicalEntity.contains(controller))
			return controller;
		this.controller_asPhysicalEntity.add(controller);
		_model.add(_model.createStatement(_resource,controllerProperty,controller.resource()));
		return controller;
	}
	
	public void removeController(fr.curie.BiNoM.pathways.biopax.PhysicalEntity controller) throws JastorException {
		if (this.controller_asPhysicalEntity == null)
			initController_asPhysicalEntity();
		if (!this.controller_asPhysicalEntity.contains(controller))
			return;
		if (!_model.contains(_resource, controllerProperty, controller.resource()))
			return;
		this.controller_asPhysicalEntity.remove(controller);
		_model.removeAll(_resource, controllerProperty, controller.resource());
	}
		
	private void initController_asPathway() throws JastorException {
		this.controller_asPathway = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, controllerProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#controller properties in TemplateReactionRegulation model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Pathway.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.Pathway controller = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
				this.controller_asPathway.add(controller);
			}
		}
	}

	public java.util.Iterator getController_asPathway() throws JastorException {
		if (controller_asPathway == null)
			initController_asPathway();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(controller_asPathway,_resource,controllerProperty,true);
	}

	public void addController(fr.curie.BiNoM.pathways.biopax.Pathway controller) throws JastorException {
		if (this.controller_asPathway == null)
			initController_asPathway();
		if (this.controller_asPathway.contains(controller)) {
			this.controller_asPathway.remove(controller);
			this.controller_asPathway.add(controller);
			return;
		}
		this.controller_asPathway.add(controller);
		_model.add(_model.createStatement(_resource,controllerProperty,controller.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Pathway addController_asPathway() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Pathway controller = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createPathway(_model.createResource(),_model);
		if (this.controller_asPathway == null)
			initController_asPathway();
		this.controller_asPathway.add(controller);
		_model.add(_model.createStatement(_resource,controllerProperty,controller.resource()));
		return controller;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Pathway addController_asPathway(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Pathway controller = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
		if (this.controller_asPathway == null)
			initController_asPathway();
		if (this.controller_asPathway.contains(controller))
			return controller;
		this.controller_asPathway.add(controller);
		_model.add(_model.createStatement(_resource,controllerProperty,controller.resource()));
		return controller;
	}
	
	public void removeController(fr.curie.BiNoM.pathways.biopax.Pathway controller) throws JastorException {
		if (this.controller_asPathway == null)
			initController_asPathway();
		if (!this.controller_asPathway.contains(controller))
			return;
		if (!_model.contains(_resource, controllerProperty, controller.resource()))
			return;
		this.controller_asPathway.remove(controller);
		_model.removeAll(_resource, controllerProperty, controller.resource());
	}
		 
	public java.lang.String getControlType() throws JastorException {
		if (controlType != null)
			return controlType;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, controlTypeProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": controlType getProperty() in fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulation model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		controlType = (java.lang.String)obj;
		return controlType;
	}
	
	public void setControlType(java.lang.String controlType) throws JastorException {
		if (_model.contains(_resource,controlTypeProperty)) {
			_model.removeAll(_resource,controlTypeProperty,null);
		}
		this.controlType = controlType;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (controlType != null) {
			_model.add(_model.createStatement(_resource,controlTypeProperty, _model.createTypedLiteral(controlType)));
		}	
	}
 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof TemplateReactionRegulationListener))
			throw new IllegalArgumentException("ThingListener must be instance of TemplateReactionRegulationListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((TemplateReactionRegulationListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof TemplateReactionRegulationListener))
			throw new IllegalArgumentException("ThingListener must be instance of TemplateReactionRegulationListener"); 
		if (listeners == null)
			return;
		if (this.listeners.contains(listener)){
			listeners.remove(listener);
		}
	}



	
		public void addedStatement(com.hp.hpl.jena.rdf.model.Statement stmt) {

			if (stmt.getPredicate().equals(dataSourceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Provenance _dataSource = null;
					try {
						_dataSource = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getProvenance(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (dataSource == null) {
						try {
							initDataSource();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!dataSource.contains(_dataSource))
						dataSource.add(_dataSource);
					if (listeners != null) {
						java.util.ArrayList consumersForDataSource;
						synchronized (listeners) {
							consumersForDataSource = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForDataSource.iterator();iter.hasNext();){
							TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
							listener.dataSourceAdded(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,_dataSource);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(availabilityProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (availability == null) {
					try {
						initAvailability();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (!availability.contains(obj))
					availability.add(obj);
				java.util.ArrayList consumersForAvailability;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForAvailability = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForAvailability.iterator();iter.hasNext();){
						TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
						listener.availabilityAdded(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(commentProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (comment == null) {
					try {
						initComment();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (!comment.contains(obj))
					comment.add(obj);
				java.util.ArrayList consumersForComment;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForComment = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForComment.iterator();iter.hasNext();){
						TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
						listener.commentAdded(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(nameProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (name == null) {
					try {
						initName();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (!name.contains(obj))
					name.add(obj);
				java.util.ArrayList consumersForName;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForName = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForName.iterator();iter.hasNext();){
						TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
						listener.nameAdded(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(evidenceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Evidence _evidence = null;
					try {
						_evidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEvidence(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (evidence == null) {
						try {
							initEvidence();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!evidence.contains(_evidence))
						evidence.add(_evidence);
					if (listeners != null) {
						java.util.ArrayList consumersForEvidence;
						synchronized (listeners) {
							consumersForEvidence = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEvidence.iterator();iter.hasNext();){
							TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
							listener.evidenceAdded(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,_evidence);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(xrefProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Xref _xref = null;
					try {
						_xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getXref(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (xref == null) {
						try {
							initXref();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!xref.contains(_xref))
						xref.add(_xref);
					if (listeners != null) {
						java.util.ArrayList consumersForXref;
						synchronized (listeners) {
							consumersForXref = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForXref.iterator();iter.hasNext();){
							TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
							listener.xrefAdded(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,_xref);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(interactionTypeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				interactionType = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						interactionType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteractionVocabulary(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
						listener.interactionTypeChanged(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(participantProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Entity _participant = null;
					try {
						_participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntity(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (participant == null) {
						try {
							initParticipant();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!participant.contains(_participant))
						participant.add(_participant);
					if (listeners != null) {
						java.util.ArrayList consumersForParticipant;
						synchronized (listeners) {
							consumersForParticipant = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForParticipant.iterator();iter.hasNext();){
							TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
							listener.participantAdded(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,_participant);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(controlledProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				controlled_asTemplateReaction = null;
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.TemplateReaction.TYPE)) {
					try {
						controlled_asTemplateReaction = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getTemplateReaction(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
				}
				controlled_asInteraction = null;
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Interaction.TYPE)) {
					try {
						controlled_asInteraction = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteraction(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
				}
				controlled_asPathway = null;
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Pathway.TYPE)) {
					try {
						controlled_asPathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
						listener.controlledChanged(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(controllerProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.PhysicalEntity.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.PhysicalEntity _controller_asPhysicalEntity = null;
					try {
						_controller_asPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (controller_asPhysicalEntity == null) {
						try {
							initController_asPhysicalEntity();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!controller_asPhysicalEntity.contains(_controller_asPhysicalEntity))
						controller_asPhysicalEntity.add(_controller_asPhysicalEntity);
					if (listeners != null) {
						java.util.ArrayList consumersForController_asPhysicalEntity;
						synchronized (listeners) {
							consumersForController_asPhysicalEntity = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForController_asPhysicalEntity.iterator();iter.hasNext();){
							TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
							listener.controllerAdded(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,_controller_asPhysicalEntity);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Pathway.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Pathway _controller_asPathway = null;
					try {
						_controller_asPathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (controller_asPathway == null) {
						try {
							initController_asPathway();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!controller_asPathway.contains(_controller_asPathway))
						controller_asPathway.add(_controller_asPathway);
					if (listeners != null) {
						java.util.ArrayList consumersForController_asPathway;
						synchronized (listeners) {
							consumersForController_asPathway = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForController_asPathway.iterator();iter.hasNext();){
							TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
							listener.controllerAdded(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,_controller_asPathway);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(controlTypeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				controlType = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
						listener.controlTypeChanged(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this);
					}
				}
				return;
			}
		}
		
		public void removedStatement(com.hp.hpl.jena.rdf.model.Statement stmt) {
//			if (!stmt.getSubject().equals(_resource))
//				return;
			if (stmt.getPredicate().equals(dataSourceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Provenance _dataSource = null;
					if (dataSource != null) {
						boolean found = false;
						for (int i=0;i<dataSource.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Provenance __item = (fr.curie.BiNoM.pathways.biopax.Provenance) dataSource.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_dataSource = __item;
								break;
							}
						}
						if (found)
							dataSource.remove(_dataSource);
						else {
							try {
								_dataSource = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getProvenance(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_dataSource = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getProvenance(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForDataSource;
						synchronized (listeners) {
							consumersForDataSource = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForDataSource.iterator();iter.hasNext();){
							TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
							listener.dataSourceRemoved(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,_dataSource);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(availabilityProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (availability != null) {
					if (availability.contains(obj))
						availability.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
						listener.availabilityRemoved(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(commentProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (comment != null) {
					if (comment.contains(obj))
						comment.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
						listener.commentRemoved(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(nameProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (name != null) {
					if (name.contains(obj))
						name.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
						listener.nameRemoved(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(evidenceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Evidence _evidence = null;
					if (evidence != null) {
						boolean found = false;
						for (int i=0;i<evidence.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Evidence __item = (fr.curie.BiNoM.pathways.biopax.Evidence) evidence.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_evidence = __item;
								break;
							}
						}
						if (found)
							evidence.remove(_evidence);
						else {
							try {
								_evidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEvidence(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_evidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEvidence(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForEvidence;
						synchronized (listeners) {
							consumersForEvidence = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEvidence.iterator();iter.hasNext();){
							TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
							listener.evidenceRemoved(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,_evidence);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(xrefProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Xref _xref = null;
					if (xref != null) {
						boolean found = false;
						for (int i=0;i<xref.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Xref __item = (fr.curie.BiNoM.pathways.biopax.Xref) xref.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_xref = __item;
								break;
							}
						}
						if (found)
							xref.remove(_xref);
						else {
							try {
								_xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getXref(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getXref(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForXref;
						synchronized (listeners) {
							consumersForXref = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForXref.iterator();iter.hasNext();){
							TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
							listener.xrefRemoved(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,_xref);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(interactionTypeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (interactionType != null && interactionType.resource().equals(resource))
						interactionType = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
						listener.interactionTypeChanged(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(participantProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Entity _participant = null;
					if (participant != null) {
						boolean found = false;
						for (int i=0;i<participant.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Entity __item = (fr.curie.BiNoM.pathways.biopax.Entity) participant.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_participant = __item;
								break;
							}
						}
						if (found)
							participant.remove(_participant);
						else {
							try {
								_participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntity(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntity(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForParticipant;
						synchronized (listeners) {
							consumersForParticipant = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForParticipant.iterator();iter.hasNext();){
							TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
							listener.participantRemoved(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,_participant);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(controlledProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (controlled_asTemplateReaction != null && controlled_asTemplateReaction.resource().equals(resource))
						controlled_asTemplateReaction = null;				
					if (controlled_asInteraction != null && controlled_asInteraction.resource().equals(resource))
						controlled_asInteraction = null;				
					if (controlled_asPathway != null && controlled_asPathway.resource().equals(resource))
						controlled_asPathway = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
						listener.controlledChanged(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(controllerProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.PhysicalEntity.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.PhysicalEntity _controller_asPhysicalEntity = null;
					if (controller_asPhysicalEntity != null) {
						boolean found = false;
						for (int i=0;i<controller_asPhysicalEntity.size();i++) {
							fr.curie.BiNoM.pathways.biopax.PhysicalEntity __item = (fr.curie.BiNoM.pathways.biopax.PhysicalEntity) controller_asPhysicalEntity.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_controller_asPhysicalEntity = __item;
								break;
							}
						}
						if (found)
							controller_asPhysicalEntity.remove(_controller_asPhysicalEntity);
						else {
							try {
								_controller_asPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_controller_asPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForController_asPhysicalEntity;
						synchronized (listeners) {
							consumersForController_asPhysicalEntity = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForController_asPhysicalEntity.iterator();iter.hasNext();){
							TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
							listener.controllerRemoved(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,_controller_asPhysicalEntity);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Pathway.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Pathway _controller_asPathway = null;
					if (controller_asPathway != null) {
						boolean found = false;
						for (int i=0;i<controller_asPathway.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Pathway __item = (fr.curie.BiNoM.pathways.biopax.Pathway) controller_asPathway.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_controller_asPathway = __item;
								break;
							}
						}
						if (found)
							controller_asPathway.remove(_controller_asPathway);
						else {
							try {
								_controller_asPathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_controller_asPathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForController_asPathway;
						synchronized (listeners) {
							consumersForController_asPathway = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForController_asPathway.iterator();iter.hasNext();){
							TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
							listener.controllerRemoved(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this,_controller_asPathway);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(controlTypeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (controlType != null && controlType.equals(obj))
					controlType = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						TemplateReactionRegulationListener listener=(TemplateReactionRegulationListener)iter.next();
						listener.controlTypeChanged(fr.curie.BiNoM.pathways.biopax.TemplateReactionRegulationImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}