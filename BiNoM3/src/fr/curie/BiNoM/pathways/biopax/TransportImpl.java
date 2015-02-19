

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.Transport}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Transport)</p>
 * <br>
 */
public class TransportImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.Transport {
	

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
	private java.util.ArrayList participant_asPhysicalEntity;
	private static com.hp.hpl.jena.rdf.model.Property participantStoichiometryProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#participantStoichiometry");
	private java.util.ArrayList participantStoichiometry;
	private static com.hp.hpl.jena.rdf.model.Property spontaneousProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#spontaneous");
	private java.lang.Boolean spontaneous;
	private static com.hp.hpl.jena.rdf.model.Property leftProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#left");
	private java.util.ArrayList left;
	private static com.hp.hpl.jena.rdf.model.Property conversionDirectionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#conversionDirection");
	private java.lang.String conversionDirection;
	private static com.hp.hpl.jena.rdf.model.Property rightProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#right");
	private java.util.ArrayList right;

	TransportImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static TransportImpl getTransport(Resource resource, Model model) throws JastorException {
		return new TransportImpl(resource, model);
	}
	    
	static TransportImpl createTransport(Resource resource, Model model) throws JastorException {
		TransportImpl impl = new TransportImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, Transport.TYPE)))
			impl._model.add(impl._resource, RDF.type, Transport.TYPE);
		impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Entity.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Entity.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Interaction.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Interaction.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Conversion.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Conversion.TYPE));     
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
		it = _model.listStatements(_resource,participantStoichiometryProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,spontaneousProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,leftProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,conversionDirectionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,rightProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.Transport.TYPE);
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
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.Conversion.TYPE);
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
		participant_asPhysicalEntity = null;
		participantStoichiometry = null;
		spontaneous = null;
		left = null;
		conversionDirection = null;
		right = null;
	}


	private void initDataSource() throws JastorException {
		this.dataSource = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, dataSourceProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#dataSource properties in Transport model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#availability properties in Transport model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#comment properties in Transport model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#name properties in Transport model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#evidence properties in Transport model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#xref properties in Transport model not a Resource", stmt.getObject());
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
			throw new JastorInvalidRDFNodeException(uri() + ": interactionType getProperty() in fr.curie.BiNoM.pathways.biopax.Transport model not Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#participant properties in Transport model not a Resource", stmt.getObject());
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
		
	private void initParticipant_asPhysicalEntity() throws JastorException {
		this.participant_asPhysicalEntity = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, participantProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#participant properties in Transport model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.PhysicalEntity.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.PhysicalEntity participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
				this.participant_asPhysicalEntity.add(participant);
			}
		}
	}

	public java.util.Iterator getParticipant_asPhysicalEntity() throws JastorException {
		if (participant_asPhysicalEntity == null)
			initParticipant_asPhysicalEntity();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(participant_asPhysicalEntity,_resource,participantProperty,true);
	}

	public void addParticipant(fr.curie.BiNoM.pathways.biopax.PhysicalEntity participant) throws JastorException {
		if (this.participant_asPhysicalEntity == null)
			initParticipant_asPhysicalEntity();
		if (this.participant_asPhysicalEntity.contains(participant)) {
			this.participant_asPhysicalEntity.remove(participant);
			this.participant_asPhysicalEntity.add(participant);
			return;
		}
		this.participant_asPhysicalEntity.add(participant);
		_model.add(_model.createStatement(_resource,participantProperty,participant.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addParticipant_asPhysicalEntity() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PhysicalEntity participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createPhysicalEntity(_model.createResource(),_model);
		if (this.participant_asPhysicalEntity == null)
			initParticipant_asPhysicalEntity();
		this.participant_asPhysicalEntity.add(participant);
		_model.add(_model.createStatement(_resource,participantProperty,participant.resource()));
		return participant;
	}
	
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addParticipant_asPhysicalEntity(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PhysicalEntity participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
		if (this.participant_asPhysicalEntity == null)
			initParticipant_asPhysicalEntity();
		if (this.participant_asPhysicalEntity.contains(participant))
			return participant;
		this.participant_asPhysicalEntity.add(participant);
		_model.add(_model.createStatement(_resource,participantProperty,participant.resource()));
		return participant;
	}
	
	public void removeParticipant(fr.curie.BiNoM.pathways.biopax.PhysicalEntity participant) throws JastorException {
		if (this.participant_asPhysicalEntity == null)
			initParticipant_asPhysicalEntity();
		if (!this.participant_asPhysicalEntity.contains(participant))
			return;
		if (!_model.contains(_resource, participantProperty, participant.resource()))
			return;
		this.participant_asPhysicalEntity.remove(participant);
		_model.removeAll(_resource, participantProperty, participant.resource());
	}
		 

	private void initParticipantStoichiometry() throws JastorException {
		this.participantStoichiometry = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, participantStoichiometryProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#participantStoichiometry properties in Transport model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.Stoichiometry participantStoichiometry = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getStoichiometry(resource,_model);
				this.participantStoichiometry.add(participantStoichiometry);
			}
		}
	}

	public java.util.Iterator getParticipantStoichiometry() throws JastorException {
		if (participantStoichiometry == null)
			initParticipantStoichiometry();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(participantStoichiometry,_resource,participantStoichiometryProperty,true);
	}

	public void addParticipantStoichiometry(fr.curie.BiNoM.pathways.biopax.Stoichiometry participantStoichiometry) throws JastorException {
		if (this.participantStoichiometry == null)
			initParticipantStoichiometry();
		if (this.participantStoichiometry.contains(participantStoichiometry)) {
			this.participantStoichiometry.remove(participantStoichiometry);
			this.participantStoichiometry.add(participantStoichiometry);
			return;
		}
		this.participantStoichiometry.add(participantStoichiometry);
		_model.add(_model.createStatement(_resource,participantStoichiometryProperty,participantStoichiometry.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Stoichiometry addParticipantStoichiometry() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Stoichiometry participantStoichiometry = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createStoichiometry(_model.createResource(),_model);
		if (this.participantStoichiometry == null)
			initParticipantStoichiometry();
		this.participantStoichiometry.add(participantStoichiometry);
		_model.add(_model.createStatement(_resource,participantStoichiometryProperty,participantStoichiometry.resource()));
		return participantStoichiometry;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Stoichiometry addParticipantStoichiometry(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Stoichiometry participantStoichiometry = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getStoichiometry(resource,_model);
		if (this.participantStoichiometry == null)
			initParticipantStoichiometry();
		if (this.participantStoichiometry.contains(participantStoichiometry))
			return participantStoichiometry;
		this.participantStoichiometry.add(participantStoichiometry);
		_model.add(_model.createStatement(_resource,participantStoichiometryProperty,participantStoichiometry.resource()));
		return participantStoichiometry;
	}
	
	public void removeParticipantStoichiometry(fr.curie.BiNoM.pathways.biopax.Stoichiometry participantStoichiometry) throws JastorException {
		if (this.participantStoichiometry == null)
			initParticipantStoichiometry();
		if (!this.participantStoichiometry.contains(participantStoichiometry))
			return;
		if (!_model.contains(_resource, participantStoichiometryProperty, participantStoichiometry.resource()))
			return;
		this.participantStoichiometry.remove(participantStoichiometry);
		_model.removeAll(_resource, participantStoichiometryProperty, participantStoichiometry.resource());
	}
		 
	public java.lang.Boolean getSpontaneous() throws JastorException {
		if (spontaneous != null)
			return spontaneous;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, spontaneousProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": spontaneous getProperty() in fr.curie.BiNoM.pathways.biopax.Transport model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Boolean");
		spontaneous = (java.lang.Boolean)obj;
		return spontaneous;
	}
	
	public void setSpontaneous(java.lang.Boolean spontaneous) throws JastorException {
		if (_model.contains(_resource,spontaneousProperty)) {
			_model.removeAll(_resource,spontaneousProperty,null);
		}
		this.spontaneous = spontaneous;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (spontaneous != null) {
			_model.add(_model.createStatement(_resource,spontaneousProperty, _model.createTypedLiteral(spontaneous)));
		}	
	}


	private void initLeft() throws JastorException {
		this.left = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, leftProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#left properties in Transport model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.PhysicalEntity left = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
				this.left.add(left);
			}
		}
	}

	public java.util.Iterator getLeft() throws JastorException {
		if (left == null)
			initLeft();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(left,_resource,leftProperty,true);
	}

	public void addLeft(fr.curie.BiNoM.pathways.biopax.PhysicalEntity left) throws JastorException {
		if (this.left == null)
			initLeft();
		if (this.left.contains(left)) {
			this.left.remove(left);
			this.left.add(left);
			return;
		}
		this.left.add(left);
		_model.add(_model.createStatement(_resource,leftProperty,left.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addLeft() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PhysicalEntity left = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createPhysicalEntity(_model.createResource(),_model);
		if (this.left == null)
			initLeft();
		this.left.add(left);
		_model.add(_model.createStatement(_resource,leftProperty,left.resource()));
		return left;
	}
	
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addLeft(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PhysicalEntity left = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
		if (this.left == null)
			initLeft();
		if (this.left.contains(left))
			return left;
		this.left.add(left);
		_model.add(_model.createStatement(_resource,leftProperty,left.resource()));
		return left;
	}
	
	public void removeLeft(fr.curie.BiNoM.pathways.biopax.PhysicalEntity left) throws JastorException {
		if (this.left == null)
			initLeft();
		if (!this.left.contains(left))
			return;
		if (!_model.contains(_resource, leftProperty, left.resource()))
			return;
		this.left.remove(left);
		_model.removeAll(_resource, leftProperty, left.resource());
	}
		 
	public java.lang.String getConversionDirection() throws JastorException {
		if (conversionDirection != null)
			return conversionDirection;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, conversionDirectionProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": conversionDirection getProperty() in fr.curie.BiNoM.pathways.biopax.Transport model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		conversionDirection = (java.lang.String)obj;
		return conversionDirection;
	}
	
	public void setConversionDirection(java.lang.String conversionDirection) throws JastorException {
		if (_model.contains(_resource,conversionDirectionProperty)) {
			_model.removeAll(_resource,conversionDirectionProperty,null);
		}
		this.conversionDirection = conversionDirection;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (conversionDirection != null) {
			_model.add(_model.createStatement(_resource,conversionDirectionProperty, _model.createTypedLiteral(conversionDirection)));
		}	
	}


	private void initRight() throws JastorException {
		this.right = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, rightProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#right properties in Transport model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.PhysicalEntity right = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
				this.right.add(right);
			}
		}
	}

	public java.util.Iterator getRight() throws JastorException {
		if (right == null)
			initRight();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(right,_resource,rightProperty,true);
	}

	public void addRight(fr.curie.BiNoM.pathways.biopax.PhysicalEntity right) throws JastorException {
		if (this.right == null)
			initRight();
		if (this.right.contains(right)) {
			this.right.remove(right);
			this.right.add(right);
			return;
		}
		this.right.add(right);
		_model.add(_model.createStatement(_resource,rightProperty,right.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addRight() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PhysicalEntity right = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createPhysicalEntity(_model.createResource(),_model);
		if (this.right == null)
			initRight();
		this.right.add(right);
		_model.add(_model.createStatement(_resource,rightProperty,right.resource()));
		return right;
	}
	
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addRight(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PhysicalEntity right = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
		if (this.right == null)
			initRight();
		if (this.right.contains(right))
			return right;
		this.right.add(right);
		_model.add(_model.createStatement(_resource,rightProperty,right.resource()));
		return right;
	}
	
	public void removeRight(fr.curie.BiNoM.pathways.biopax.PhysicalEntity right) throws JastorException {
		if (this.right == null)
			initRight();
		if (!this.right.contains(right))
			return;
		if (!_model.contains(_resource, rightProperty, right.resource()))
			return;
		this.right.remove(right);
		_model.removeAll(_resource, rightProperty, right.resource());
	}
		  


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof TransportListener))
			throw new IllegalArgumentException("ThingListener must be instance of TransportListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((TransportListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof TransportListener))
			throw new IllegalArgumentException("ThingListener must be instance of TransportListener"); 
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
							TransportListener listener=(TransportListener)iter.next();
							listener.dataSourceAdded(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,_dataSource);
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
						TransportListener listener=(TransportListener)iter.next();
						listener.availabilityAdded(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,(java.lang.String)obj);
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
						TransportListener listener=(TransportListener)iter.next();
						listener.commentAdded(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,(java.lang.String)obj);
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
						TransportListener listener=(TransportListener)iter.next();
						listener.nameAdded(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,(java.lang.String)obj);
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
							TransportListener listener=(TransportListener)iter.next();
							listener.evidenceAdded(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,_evidence);
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
							TransportListener listener=(TransportListener)iter.next();
							listener.xrefAdded(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,_xref);
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
						TransportListener listener=(TransportListener)iter.next();
						listener.interactionTypeChanged(fr.curie.BiNoM.pathways.biopax.TransportImpl.this);
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
							TransportListener listener=(TransportListener)iter.next();
							listener.participantAdded(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,_participant);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.PhysicalEntity.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.PhysicalEntity _participant_asPhysicalEntity = null;
					try {
						_participant_asPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (participant_asPhysicalEntity == null) {
						try {
							initParticipant_asPhysicalEntity();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!participant_asPhysicalEntity.contains(_participant_asPhysicalEntity))
						participant_asPhysicalEntity.add(_participant_asPhysicalEntity);
					if (listeners != null) {
						java.util.ArrayList consumersForParticipant_asPhysicalEntity;
						synchronized (listeners) {
							consumersForParticipant_asPhysicalEntity = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForParticipant_asPhysicalEntity.iterator();iter.hasNext();){
							TransportListener listener=(TransportListener)iter.next();
							listener.participantAdded(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,_participant_asPhysicalEntity);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(participantStoichiometryProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Stoichiometry _participantStoichiometry = null;
					try {
						_participantStoichiometry = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getStoichiometry(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (participantStoichiometry == null) {
						try {
							initParticipantStoichiometry();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!participantStoichiometry.contains(_participantStoichiometry))
						participantStoichiometry.add(_participantStoichiometry);
					if (listeners != null) {
						java.util.ArrayList consumersForParticipantStoichiometry;
						synchronized (listeners) {
							consumersForParticipantStoichiometry = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForParticipantStoichiometry.iterator();iter.hasNext();){
							TransportListener listener=(TransportListener)iter.next();
							listener.participantStoichiometryAdded(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,_participantStoichiometry);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(spontaneousProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				spontaneous = (java.lang.Boolean)Util.fixLiteral(literal.getValue(),"java.lang.Boolean");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						TransportListener listener=(TransportListener)iter.next();
						listener.spontaneousChanged(fr.curie.BiNoM.pathways.biopax.TransportImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(leftProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.PhysicalEntity _left = null;
					try {
						_left = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (left == null) {
						try {
							initLeft();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!left.contains(_left))
						left.add(_left);
					if (listeners != null) {
						java.util.ArrayList consumersForLeft;
						synchronized (listeners) {
							consumersForLeft = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForLeft.iterator();iter.hasNext();){
							TransportListener listener=(TransportListener)iter.next();
							listener.leftAdded(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,_left);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(conversionDirectionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				conversionDirection = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						TransportListener listener=(TransportListener)iter.next();
						listener.conversionDirectionChanged(fr.curie.BiNoM.pathways.biopax.TransportImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(rightProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.PhysicalEntity _right = null;
					try {
						_right = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (right == null) {
						try {
							initRight();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!right.contains(_right))
						right.add(_right);
					if (listeners != null) {
						java.util.ArrayList consumersForRight;
						synchronized (listeners) {
							consumersForRight = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForRight.iterator();iter.hasNext();){
							TransportListener listener=(TransportListener)iter.next();
							listener.rightAdded(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,_right);
						}
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
							TransportListener listener=(TransportListener)iter.next();
							listener.dataSourceRemoved(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,_dataSource);
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
						TransportListener listener=(TransportListener)iter.next();
						listener.availabilityRemoved(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,(java.lang.String)obj);
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
						TransportListener listener=(TransportListener)iter.next();
						listener.commentRemoved(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,(java.lang.String)obj);
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
						TransportListener listener=(TransportListener)iter.next();
						listener.nameRemoved(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,(java.lang.String)obj);
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
							TransportListener listener=(TransportListener)iter.next();
							listener.evidenceRemoved(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,_evidence);
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
							TransportListener listener=(TransportListener)iter.next();
							listener.xrefRemoved(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,_xref);
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
						TransportListener listener=(TransportListener)iter.next();
						listener.interactionTypeChanged(fr.curie.BiNoM.pathways.biopax.TransportImpl.this);
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
							TransportListener listener=(TransportListener)iter.next();
							listener.participantRemoved(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,_participant);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.PhysicalEntity.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.PhysicalEntity _participant_asPhysicalEntity = null;
					if (participant_asPhysicalEntity != null) {
						boolean found = false;
						for (int i=0;i<participant_asPhysicalEntity.size();i++) {
							fr.curie.BiNoM.pathways.biopax.PhysicalEntity __item = (fr.curie.BiNoM.pathways.biopax.PhysicalEntity) participant_asPhysicalEntity.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_participant_asPhysicalEntity = __item;
								break;
							}
						}
						if (found)
							participant_asPhysicalEntity.remove(_participant_asPhysicalEntity);
						else {
							try {
								_participant_asPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_participant_asPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForParticipant_asPhysicalEntity;
						synchronized (listeners) {
							consumersForParticipant_asPhysicalEntity = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForParticipant_asPhysicalEntity.iterator();iter.hasNext();){
							TransportListener listener=(TransportListener)iter.next();
							listener.participantRemoved(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,_participant_asPhysicalEntity);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(participantStoichiometryProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Stoichiometry _participantStoichiometry = null;
					if (participantStoichiometry != null) {
						boolean found = false;
						for (int i=0;i<participantStoichiometry.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Stoichiometry __item = (fr.curie.BiNoM.pathways.biopax.Stoichiometry) participantStoichiometry.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_participantStoichiometry = __item;
								break;
							}
						}
						if (found)
							participantStoichiometry.remove(_participantStoichiometry);
						else {
							try {
								_participantStoichiometry = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getStoichiometry(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_participantStoichiometry = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getStoichiometry(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForParticipantStoichiometry;
						synchronized (listeners) {
							consumersForParticipantStoichiometry = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForParticipantStoichiometry.iterator();iter.hasNext();){
							TransportListener listener=(TransportListener)iter.next();
							listener.participantStoichiometryRemoved(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,_participantStoichiometry);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(spontaneousProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Boolean");
				if (spontaneous != null && spontaneous.equals(obj))
					spontaneous = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						TransportListener listener=(TransportListener)iter.next();
						listener.spontaneousChanged(fr.curie.BiNoM.pathways.biopax.TransportImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(leftProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.PhysicalEntity _left = null;
					if (left != null) {
						boolean found = false;
						for (int i=0;i<left.size();i++) {
							fr.curie.BiNoM.pathways.biopax.PhysicalEntity __item = (fr.curie.BiNoM.pathways.biopax.PhysicalEntity) left.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_left = __item;
								break;
							}
						}
						if (found)
							left.remove(_left);
						else {
							try {
								_left = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_left = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForLeft;
						synchronized (listeners) {
							consumersForLeft = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForLeft.iterator();iter.hasNext();){
							TransportListener listener=(TransportListener)iter.next();
							listener.leftRemoved(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,_left);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(conversionDirectionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (conversionDirection != null && conversionDirection.equals(obj))
					conversionDirection = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						TransportListener listener=(TransportListener)iter.next();
						listener.conversionDirectionChanged(fr.curie.BiNoM.pathways.biopax.TransportImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(rightProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.PhysicalEntity _right = null;
					if (right != null) {
						boolean found = false;
						for (int i=0;i<right.size();i++) {
							fr.curie.BiNoM.pathways.biopax.PhysicalEntity __item = (fr.curie.BiNoM.pathways.biopax.PhysicalEntity) right.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_right = __item;
								break;
							}
						}
						if (found)
							right.remove(_right);
						else {
							try {
								_right = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_right = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForRight;
						synchronized (listeners) {
							consumersForRight = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForRight.iterator();iter.hasNext();){
							TransportListener listener=(TransportListener)iter.next();
							listener.rightRemoved(fr.curie.BiNoM.pathways.biopax.TransportImpl.this,_right);
						}
					}
				}
				return;
			}
		}

	//}
	


}