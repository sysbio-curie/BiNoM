

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.Pathway}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Pathway)</p>
 * <br>
 */
public class PathwayImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.Pathway {
	

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
	private static com.hp.hpl.jena.rdf.model.Property pathwayOrderProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#pathwayOrder");
	private java.util.ArrayList pathwayOrder;
	private static com.hp.hpl.jena.rdf.model.Property pathwayComponentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#pathwayComponent");
	private java.util.ArrayList pathwayComponent_asInteraction;
	private java.util.ArrayList pathwayComponent_asPathway;
	private static com.hp.hpl.jena.rdf.model.Property organismProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#organism");
	private fr.curie.BiNoM.pathways.biopax.BioSource organism;

	PathwayImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static PathwayImpl getPathway(Resource resource, Model model) throws JastorException {
		return new PathwayImpl(resource, model);
	}
	    
	static PathwayImpl createPathway(Resource resource, Model model) throws JastorException {
		PathwayImpl impl = new PathwayImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, Pathway.TYPE)))
			impl._model.add(impl._resource, RDF.type, Pathway.TYPE);
		impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Entity.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Entity.TYPE));     
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
		it = _model.listStatements(_resource,pathwayOrderProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,pathwayComponentProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,organismProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.Pathway.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.Entity.TYPE);
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
		pathwayOrder = null;
		pathwayComponent_asInteraction = null;
		pathwayComponent_asPathway = null;
		organism = null;
	}


	private void initDataSource() throws JastorException {
		this.dataSource = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, dataSourceProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#dataSource properties in Pathway model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#availability properties in Pathway model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#comment properties in Pathway model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#name properties in Pathway model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#evidence properties in Pathway model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#xref properties in Pathway model not a Resource", stmt.getObject());
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
		 

	private void initPathwayOrder() throws JastorException {
		this.pathwayOrder = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, pathwayOrderProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#pathwayOrder properties in Pathway model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.PathwayStep pathwayOrder = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathwayStep(resource,_model);
				this.pathwayOrder.add(pathwayOrder);
			}
		}
	}

	public java.util.Iterator getPathwayOrder() throws JastorException {
		if (pathwayOrder == null)
			initPathwayOrder();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(pathwayOrder,_resource,pathwayOrderProperty,true);
	}

	public void addPathwayOrder(fr.curie.BiNoM.pathways.biopax.PathwayStep pathwayOrder) throws JastorException {
		if (this.pathwayOrder == null)
			initPathwayOrder();
		if (this.pathwayOrder.contains(pathwayOrder)) {
			this.pathwayOrder.remove(pathwayOrder);
			this.pathwayOrder.add(pathwayOrder);
			return;
		}
		this.pathwayOrder.add(pathwayOrder);
		_model.add(_model.createStatement(_resource,pathwayOrderProperty,pathwayOrder.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.PathwayStep addPathwayOrder() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PathwayStep pathwayOrder = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createPathwayStep(_model.createResource(),_model);
		if (this.pathwayOrder == null)
			initPathwayOrder();
		this.pathwayOrder.add(pathwayOrder);
		_model.add(_model.createStatement(_resource,pathwayOrderProperty,pathwayOrder.resource()));
		return pathwayOrder;
	}
	
	public fr.curie.BiNoM.pathways.biopax.PathwayStep addPathwayOrder(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PathwayStep pathwayOrder = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathwayStep(resource,_model);
		if (this.pathwayOrder == null)
			initPathwayOrder();
		if (this.pathwayOrder.contains(pathwayOrder))
			return pathwayOrder;
		this.pathwayOrder.add(pathwayOrder);
		_model.add(_model.createStatement(_resource,pathwayOrderProperty,pathwayOrder.resource()));
		return pathwayOrder;
	}
	
	public void removePathwayOrder(fr.curie.BiNoM.pathways.biopax.PathwayStep pathwayOrder) throws JastorException {
		if (this.pathwayOrder == null)
			initPathwayOrder();
		if (!this.pathwayOrder.contains(pathwayOrder))
			return;
		if (!_model.contains(_resource, pathwayOrderProperty, pathwayOrder.resource()))
			return;
		this.pathwayOrder.remove(pathwayOrder);
		_model.removeAll(_resource, pathwayOrderProperty, pathwayOrder.resource());
	}
		 

	private void initPathwayComponent_asInteraction() throws JastorException {
		this.pathwayComponent_asInteraction = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, pathwayComponentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#pathwayComponent properties in Pathway model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Interaction.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.Interaction pathwayComponent = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteraction(resource,_model);
				this.pathwayComponent_asInteraction.add(pathwayComponent);
			}
		}
	}

	public java.util.Iterator getPathwayComponent_asInteraction() throws JastorException {
		if (pathwayComponent_asInteraction == null)
			initPathwayComponent_asInteraction();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(pathwayComponent_asInteraction,_resource,pathwayComponentProperty,true);
	}

	public void addPathwayComponent(fr.curie.BiNoM.pathways.biopax.Interaction pathwayComponent) throws JastorException {
		if (this.pathwayComponent_asInteraction == null)
			initPathwayComponent_asInteraction();
		if (this.pathwayComponent_asInteraction.contains(pathwayComponent)) {
			this.pathwayComponent_asInteraction.remove(pathwayComponent);
			this.pathwayComponent_asInteraction.add(pathwayComponent);
			return;
		}
		this.pathwayComponent_asInteraction.add(pathwayComponent);
		_model.add(_model.createStatement(_resource,pathwayComponentProperty,pathwayComponent.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Interaction addPathwayComponent_asInteraction() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Interaction pathwayComponent = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createInteraction(_model.createResource(),_model);
		if (this.pathwayComponent_asInteraction == null)
			initPathwayComponent_asInteraction();
		this.pathwayComponent_asInteraction.add(pathwayComponent);
		_model.add(_model.createStatement(_resource,pathwayComponentProperty,pathwayComponent.resource()));
		return pathwayComponent;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Interaction addPathwayComponent_asInteraction(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Interaction pathwayComponent = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteraction(resource,_model);
		if (this.pathwayComponent_asInteraction == null)
			initPathwayComponent_asInteraction();
		if (this.pathwayComponent_asInteraction.contains(pathwayComponent))
			return pathwayComponent;
		this.pathwayComponent_asInteraction.add(pathwayComponent);
		_model.add(_model.createStatement(_resource,pathwayComponentProperty,pathwayComponent.resource()));
		return pathwayComponent;
	}
	
	public void removePathwayComponent(fr.curie.BiNoM.pathways.biopax.Interaction pathwayComponent) throws JastorException {
		if (this.pathwayComponent_asInteraction == null)
			initPathwayComponent_asInteraction();
		if (!this.pathwayComponent_asInteraction.contains(pathwayComponent))
			return;
		if (!_model.contains(_resource, pathwayComponentProperty, pathwayComponent.resource()))
			return;
		this.pathwayComponent_asInteraction.remove(pathwayComponent);
		_model.removeAll(_resource, pathwayComponentProperty, pathwayComponent.resource());
	}
		
	private void initPathwayComponent_asPathway() throws JastorException {
		this.pathwayComponent_asPathway = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, pathwayComponentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#pathwayComponent properties in Pathway model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Pathway.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.Pathway pathwayComponent = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
				this.pathwayComponent_asPathway.add(pathwayComponent);
			}
		}
	}

	public java.util.Iterator getPathwayComponent_asPathway() throws JastorException {
		if (pathwayComponent_asPathway == null)
			initPathwayComponent_asPathway();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(pathwayComponent_asPathway,_resource,pathwayComponentProperty,true);
	}

	public void addPathwayComponent(fr.curie.BiNoM.pathways.biopax.Pathway pathwayComponent) throws JastorException {
		if (this.pathwayComponent_asPathway == null)
			initPathwayComponent_asPathway();
		if (this.pathwayComponent_asPathway.contains(pathwayComponent)) {
			this.pathwayComponent_asPathway.remove(pathwayComponent);
			this.pathwayComponent_asPathway.add(pathwayComponent);
			return;
		}
		this.pathwayComponent_asPathway.add(pathwayComponent);
		_model.add(_model.createStatement(_resource,pathwayComponentProperty,pathwayComponent.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Pathway addPathwayComponent_asPathway() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Pathway pathwayComponent = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createPathway(_model.createResource(),_model);
		if (this.pathwayComponent_asPathway == null)
			initPathwayComponent_asPathway();
		this.pathwayComponent_asPathway.add(pathwayComponent);
		_model.add(_model.createStatement(_resource,pathwayComponentProperty,pathwayComponent.resource()));
		return pathwayComponent;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Pathway addPathwayComponent_asPathway(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Pathway pathwayComponent = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
		if (this.pathwayComponent_asPathway == null)
			initPathwayComponent_asPathway();
		if (this.pathwayComponent_asPathway.contains(pathwayComponent))
			return pathwayComponent;
		this.pathwayComponent_asPathway.add(pathwayComponent);
		_model.add(_model.createStatement(_resource,pathwayComponentProperty,pathwayComponent.resource()));
		return pathwayComponent;
	}
	
	public void removePathwayComponent(fr.curie.BiNoM.pathways.biopax.Pathway pathwayComponent) throws JastorException {
		if (this.pathwayComponent_asPathway == null)
			initPathwayComponent_asPathway();
		if (!this.pathwayComponent_asPathway.contains(pathwayComponent))
			return;
		if (!_model.contains(_resource, pathwayComponentProperty, pathwayComponent.resource()))
			return;
		this.pathwayComponent_asPathway.remove(pathwayComponent);
		_model.removeAll(_resource, pathwayComponentProperty, pathwayComponent.resource());
	}
		 

	public fr.curie.BiNoM.pathways.biopax.BioSource getOrganism() throws JastorException {
		if (organism != null)
			return organism;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, organismProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": organism getProperty() in fr.curie.BiNoM.pathways.biopax.Pathway model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		organism = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getBioSource(resource,_model);
		return organism;
	}

	public void setOrganism(fr.curie.BiNoM.pathways.biopax.BioSource organism) throws JastorException {
		if (_model.contains(_resource,organismProperty)) {
			_model.removeAll(_resource,organismProperty,null);
		}
		this.organism = organism;
		if (organism != null) {
			_model.add(_model.createStatement(_resource,organismProperty, organism.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.BioSource setOrganism() throws JastorException {
		if (_model.contains(_resource,organismProperty)) {
			_model.removeAll(_resource,organismProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.BioSource organism = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createBioSource(_model.createResource(),_model);
		this.organism = organism;
		_model.add(_model.createStatement(_resource,organismProperty, organism.resource()));
		return organism;
	}
	
	public fr.curie.BiNoM.pathways.biopax.BioSource setOrganism(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,organismProperty)) {
			_model.removeAll(_resource,organismProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.BioSource organism = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getBioSource(resource,_model);
		this.organism = organism;
		_model.add(_model.createStatement(_resource,organismProperty, organism.resource()));
		return organism;
	}
	 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof PathwayListener))
			throw new IllegalArgumentException("ThingListener must be instance of PathwayListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((PathwayListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof PathwayListener))
			throw new IllegalArgumentException("ThingListener must be instance of PathwayListener"); 
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
							PathwayListener listener=(PathwayListener)iter.next();
							listener.dataSourceAdded(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,_dataSource);
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
						PathwayListener listener=(PathwayListener)iter.next();
						listener.availabilityAdded(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,(java.lang.String)obj);
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
						PathwayListener listener=(PathwayListener)iter.next();
						listener.commentAdded(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,(java.lang.String)obj);
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
						PathwayListener listener=(PathwayListener)iter.next();
						listener.nameAdded(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,(java.lang.String)obj);
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
							PathwayListener listener=(PathwayListener)iter.next();
							listener.evidenceAdded(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,_evidence);
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
							PathwayListener listener=(PathwayListener)iter.next();
							listener.xrefAdded(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,_xref);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(pathwayOrderProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.PathwayStep _pathwayOrder = null;
					try {
						_pathwayOrder = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathwayStep(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (pathwayOrder == null) {
						try {
							initPathwayOrder();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!pathwayOrder.contains(_pathwayOrder))
						pathwayOrder.add(_pathwayOrder);
					if (listeners != null) {
						java.util.ArrayList consumersForPathwayOrder;
						synchronized (listeners) {
							consumersForPathwayOrder = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPathwayOrder.iterator();iter.hasNext();){
							PathwayListener listener=(PathwayListener)iter.next();
							listener.pathwayOrderAdded(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,_pathwayOrder);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(pathwayComponentProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Interaction.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Interaction _pathwayComponent_asInteraction = null;
					try {
						_pathwayComponent_asInteraction = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteraction(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (pathwayComponent_asInteraction == null) {
						try {
							initPathwayComponent_asInteraction();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!pathwayComponent_asInteraction.contains(_pathwayComponent_asInteraction))
						pathwayComponent_asInteraction.add(_pathwayComponent_asInteraction);
					if (listeners != null) {
						java.util.ArrayList consumersForPathwayComponent_asInteraction;
						synchronized (listeners) {
							consumersForPathwayComponent_asInteraction = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPathwayComponent_asInteraction.iterator();iter.hasNext();){
							PathwayListener listener=(PathwayListener)iter.next();
							listener.pathwayComponentAdded(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,_pathwayComponent_asInteraction);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Pathway.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Pathway _pathwayComponent_asPathway = null;
					try {
						_pathwayComponent_asPathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (pathwayComponent_asPathway == null) {
						try {
							initPathwayComponent_asPathway();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!pathwayComponent_asPathway.contains(_pathwayComponent_asPathway))
						pathwayComponent_asPathway.add(_pathwayComponent_asPathway);
					if (listeners != null) {
						java.util.ArrayList consumersForPathwayComponent_asPathway;
						synchronized (listeners) {
							consumersForPathwayComponent_asPathway = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPathwayComponent_asPathway.iterator();iter.hasNext();){
							PathwayListener listener=(PathwayListener)iter.next();
							listener.pathwayComponentAdded(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,_pathwayComponent_asPathway);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(organismProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				organism = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						organism = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getBioSource(resource,_model);
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
						PathwayListener listener=(PathwayListener)iter.next();
						listener.organismChanged(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this);
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
							PathwayListener listener=(PathwayListener)iter.next();
							listener.dataSourceRemoved(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,_dataSource);
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
						PathwayListener listener=(PathwayListener)iter.next();
						listener.availabilityRemoved(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,(java.lang.String)obj);
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
						PathwayListener listener=(PathwayListener)iter.next();
						listener.commentRemoved(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,(java.lang.String)obj);
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
						PathwayListener listener=(PathwayListener)iter.next();
						listener.nameRemoved(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,(java.lang.String)obj);
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
							PathwayListener listener=(PathwayListener)iter.next();
							listener.evidenceRemoved(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,_evidence);
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
							PathwayListener listener=(PathwayListener)iter.next();
							listener.xrefRemoved(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,_xref);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(pathwayOrderProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.PathwayStep _pathwayOrder = null;
					if (pathwayOrder != null) {
						boolean found = false;
						for (int i=0;i<pathwayOrder.size();i++) {
							fr.curie.BiNoM.pathways.biopax.PathwayStep __item = (fr.curie.BiNoM.pathways.biopax.PathwayStep) pathwayOrder.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_pathwayOrder = __item;
								break;
							}
						}
						if (found)
							pathwayOrder.remove(_pathwayOrder);
						else {
							try {
								_pathwayOrder = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathwayStep(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_pathwayOrder = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathwayStep(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForPathwayOrder;
						synchronized (listeners) {
							consumersForPathwayOrder = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPathwayOrder.iterator();iter.hasNext();){
							PathwayListener listener=(PathwayListener)iter.next();
							listener.pathwayOrderRemoved(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,_pathwayOrder);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(pathwayComponentProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Interaction.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Interaction _pathwayComponent_asInteraction = null;
					if (pathwayComponent_asInteraction != null) {
						boolean found = false;
						for (int i=0;i<pathwayComponent_asInteraction.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Interaction __item = (fr.curie.BiNoM.pathways.biopax.Interaction) pathwayComponent_asInteraction.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_pathwayComponent_asInteraction = __item;
								break;
							}
						}
						if (found)
							pathwayComponent_asInteraction.remove(_pathwayComponent_asInteraction);
						else {
							try {
								_pathwayComponent_asInteraction = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteraction(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_pathwayComponent_asInteraction = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteraction(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForPathwayComponent_asInteraction;
						synchronized (listeners) {
							consumersForPathwayComponent_asInteraction = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPathwayComponent_asInteraction.iterator();iter.hasNext();){
							PathwayListener listener=(PathwayListener)iter.next();
							listener.pathwayComponentRemoved(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,_pathwayComponent_asInteraction);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Pathway.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Pathway _pathwayComponent_asPathway = null;
					if (pathwayComponent_asPathway != null) {
						boolean found = false;
						for (int i=0;i<pathwayComponent_asPathway.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Pathway __item = (fr.curie.BiNoM.pathways.biopax.Pathway) pathwayComponent_asPathway.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_pathwayComponent_asPathway = __item;
								break;
							}
						}
						if (found)
							pathwayComponent_asPathway.remove(_pathwayComponent_asPathway);
						else {
							try {
								_pathwayComponent_asPathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_pathwayComponent_asPathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForPathwayComponent_asPathway;
						synchronized (listeners) {
							consumersForPathwayComponent_asPathway = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPathwayComponent_asPathway.iterator();iter.hasNext();){
							PathwayListener listener=(PathwayListener)iter.next();
							listener.pathwayComponentRemoved(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this,_pathwayComponent_asPathway);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(organismProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (organism != null && organism.resource().equals(resource))
						organism = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						PathwayListener listener=(PathwayListener)iter.next();
						listener.organismChanged(fr.curie.BiNoM.pathways.biopax.PathwayImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}