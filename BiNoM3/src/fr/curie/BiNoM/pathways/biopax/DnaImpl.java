

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.Dna}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Dna)</p>
 * <br>
 */
public class DnaImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.Dna {
	

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
	private static com.hp.hpl.jena.rdf.model.Property featureProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#feature");
	private java.util.ArrayList feature;
	private static com.hp.hpl.jena.rdf.model.Property notFeatureProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#notFeature");
	private java.util.ArrayList notFeature;
	private static com.hp.hpl.jena.rdf.model.Property memberPhysicalEntityProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#memberPhysicalEntity");
	private java.util.ArrayList memberPhysicalEntity;
	private java.util.ArrayList memberPhysicalEntity_asDna;
	private static com.hp.hpl.jena.rdf.model.Property cellularLocationProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#cellularLocation");
	private fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary cellularLocation;
	private static com.hp.hpl.jena.rdf.model.Property entityReferenceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#entityReference");
	private fr.curie.BiNoM.pathways.biopax.EntityReference entityReference;
	private fr.curie.BiNoM.pathways.biopax.DnaReference entityReference_asDnaReference;

	DnaImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static DnaImpl getDna(Resource resource, Model model) throws JastorException {
		return new DnaImpl(resource, model);
	}
	    
	static DnaImpl createDna(Resource resource, Model model) throws JastorException {
		DnaImpl impl = new DnaImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, Dna.TYPE)))
			impl._model.add(impl._resource, RDF.type, Dna.TYPE);
		impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Entity.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Entity.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.PhysicalEntity.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.PhysicalEntity.TYPE));     
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
		it = _model.listStatements(_resource,featureProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,notFeatureProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,memberPhysicalEntityProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,cellularLocationProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,entityReferenceProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.Dna.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.Entity.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.PhysicalEntity.TYPE);
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
		feature = null;
		notFeature = null;
		memberPhysicalEntity = null;
		memberPhysicalEntity_asDna = null;
		cellularLocation = null;
		entityReference = null;
		entityReference_asDnaReference = null;
	}


	private void initDataSource() throws JastorException {
		this.dataSource = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, dataSourceProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#dataSource properties in Dna model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#availability properties in Dna model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#comment properties in Dna model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#name properties in Dna model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#evidence properties in Dna model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#xref properties in Dna model not a Resource", stmt.getObject());
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
		 

	private void initFeature() throws JastorException {
		this.feature = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, featureProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#feature properties in Dna model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.EntityFeature feature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
				this.feature.add(feature);
			}
		}
	}

	public java.util.Iterator getFeature() throws JastorException {
		if (feature == null)
			initFeature();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(feature,_resource,featureProperty,true);
	}

	public void addFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature feature) throws JastorException {
		if (this.feature == null)
			initFeature();
		if (this.feature.contains(feature)) {
			this.feature.remove(feature);
			this.feature.add(feature);
			return;
		}
		this.feature.add(feature);
		_model.add(_model.createStatement(_resource,featureProperty,feature.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addFeature() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.EntityFeature feature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createEntityFeature(_model.createResource(),_model);
		if (this.feature == null)
			initFeature();
		this.feature.add(feature);
		_model.add(_model.createStatement(_resource,featureProperty,feature.resource()));
		return feature;
	}
	
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addFeature(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.EntityFeature feature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
		if (this.feature == null)
			initFeature();
		if (this.feature.contains(feature))
			return feature;
		this.feature.add(feature);
		_model.add(_model.createStatement(_resource,featureProperty,feature.resource()));
		return feature;
	}
	
	public void removeFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature feature) throws JastorException {
		if (this.feature == null)
			initFeature();
		if (!this.feature.contains(feature))
			return;
		if (!_model.contains(_resource, featureProperty, feature.resource()))
			return;
		this.feature.remove(feature);
		_model.removeAll(_resource, featureProperty, feature.resource());
	}
		 

	private void initNotFeature() throws JastorException {
		this.notFeature = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, notFeatureProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#notFeature properties in Dna model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.EntityFeature notFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
				this.notFeature.add(notFeature);
			}
		}
	}

	public java.util.Iterator getNotFeature() throws JastorException {
		if (notFeature == null)
			initNotFeature();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(notFeature,_resource,notFeatureProperty,true);
	}

	public void addNotFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature notFeature) throws JastorException {
		if (this.notFeature == null)
			initNotFeature();
		if (this.notFeature.contains(notFeature)) {
			this.notFeature.remove(notFeature);
			this.notFeature.add(notFeature);
			return;
		}
		this.notFeature.add(notFeature);
		_model.add(_model.createStatement(_resource,notFeatureProperty,notFeature.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addNotFeature() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.EntityFeature notFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createEntityFeature(_model.createResource(),_model);
		if (this.notFeature == null)
			initNotFeature();
		this.notFeature.add(notFeature);
		_model.add(_model.createStatement(_resource,notFeatureProperty,notFeature.resource()));
		return notFeature;
	}
	
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addNotFeature(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.EntityFeature notFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
		if (this.notFeature == null)
			initNotFeature();
		if (this.notFeature.contains(notFeature))
			return notFeature;
		this.notFeature.add(notFeature);
		_model.add(_model.createStatement(_resource,notFeatureProperty,notFeature.resource()));
		return notFeature;
	}
	
	public void removeNotFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature notFeature) throws JastorException {
		if (this.notFeature == null)
			initNotFeature();
		if (!this.notFeature.contains(notFeature))
			return;
		if (!_model.contains(_resource, notFeatureProperty, notFeature.resource()))
			return;
		this.notFeature.remove(notFeature);
		_model.removeAll(_resource, notFeatureProperty, notFeature.resource());
	}
		 

	private void initMemberPhysicalEntity() throws JastorException {
		this.memberPhysicalEntity = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, memberPhysicalEntityProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#memberPhysicalEntity properties in Dna model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.PhysicalEntity memberPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
				this.memberPhysicalEntity.add(memberPhysicalEntity);
			}
		}
	}

	public java.util.Iterator getMemberPhysicalEntity() throws JastorException {
		if (memberPhysicalEntity == null)
			initMemberPhysicalEntity();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(memberPhysicalEntity,_resource,memberPhysicalEntityProperty,true);
	}

	public void addMemberPhysicalEntity(fr.curie.BiNoM.pathways.biopax.PhysicalEntity memberPhysicalEntity) throws JastorException {
		if (this.memberPhysicalEntity == null)
			initMemberPhysicalEntity();
		if (this.memberPhysicalEntity.contains(memberPhysicalEntity)) {
			this.memberPhysicalEntity.remove(memberPhysicalEntity);
			this.memberPhysicalEntity.add(memberPhysicalEntity);
			return;
		}
		this.memberPhysicalEntity.add(memberPhysicalEntity);
		_model.add(_model.createStatement(_resource,memberPhysicalEntityProperty,memberPhysicalEntity.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addMemberPhysicalEntity() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PhysicalEntity memberPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createPhysicalEntity(_model.createResource(),_model);
		if (this.memberPhysicalEntity == null)
			initMemberPhysicalEntity();
		this.memberPhysicalEntity.add(memberPhysicalEntity);
		_model.add(_model.createStatement(_resource,memberPhysicalEntityProperty,memberPhysicalEntity.resource()));
		return memberPhysicalEntity;
	}
	
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addMemberPhysicalEntity(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PhysicalEntity memberPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
		if (this.memberPhysicalEntity == null)
			initMemberPhysicalEntity();
		if (this.memberPhysicalEntity.contains(memberPhysicalEntity))
			return memberPhysicalEntity;
		this.memberPhysicalEntity.add(memberPhysicalEntity);
		_model.add(_model.createStatement(_resource,memberPhysicalEntityProperty,memberPhysicalEntity.resource()));
		return memberPhysicalEntity;
	}
	
	public void removeMemberPhysicalEntity(fr.curie.BiNoM.pathways.biopax.PhysicalEntity memberPhysicalEntity) throws JastorException {
		if (this.memberPhysicalEntity == null)
			initMemberPhysicalEntity();
		if (!this.memberPhysicalEntity.contains(memberPhysicalEntity))
			return;
		if (!_model.contains(_resource, memberPhysicalEntityProperty, memberPhysicalEntity.resource()))
			return;
		this.memberPhysicalEntity.remove(memberPhysicalEntity);
		_model.removeAll(_resource, memberPhysicalEntityProperty, memberPhysicalEntity.resource());
	}
		
	private void initMemberPhysicalEntity_asDna() throws JastorException {
		this.memberPhysicalEntity_asDna = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, memberPhysicalEntityProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#memberPhysicalEntity properties in Dna model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Dna.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.Dna memberPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getDna(resource,_model);
				this.memberPhysicalEntity_asDna.add(memberPhysicalEntity);
			}
		}
	}

	public java.util.Iterator getMemberPhysicalEntity_asDna() throws JastorException {
		if (memberPhysicalEntity_asDna == null)
			initMemberPhysicalEntity_asDna();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(memberPhysicalEntity_asDna,_resource,memberPhysicalEntityProperty,true);
	}

	public void addMemberPhysicalEntity(fr.curie.BiNoM.pathways.biopax.Dna memberPhysicalEntity) throws JastorException {
		if (this.memberPhysicalEntity_asDna == null)
			initMemberPhysicalEntity_asDna();
		if (this.memberPhysicalEntity_asDna.contains(memberPhysicalEntity)) {
			this.memberPhysicalEntity_asDna.remove(memberPhysicalEntity);
			this.memberPhysicalEntity_asDna.add(memberPhysicalEntity);
			return;
		}
		this.memberPhysicalEntity_asDna.add(memberPhysicalEntity);
		_model.add(_model.createStatement(_resource,memberPhysicalEntityProperty,memberPhysicalEntity.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Dna addMemberPhysicalEntity_asDna() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Dna memberPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createDna(_model.createResource(),_model);
		if (this.memberPhysicalEntity_asDna == null)
			initMemberPhysicalEntity_asDna();
		this.memberPhysicalEntity_asDna.add(memberPhysicalEntity);
		_model.add(_model.createStatement(_resource,memberPhysicalEntityProperty,memberPhysicalEntity.resource()));
		return memberPhysicalEntity;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Dna addMemberPhysicalEntity_asDna(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Dna memberPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getDna(resource,_model);
		if (this.memberPhysicalEntity_asDna == null)
			initMemberPhysicalEntity_asDna();
		if (this.memberPhysicalEntity_asDna.contains(memberPhysicalEntity))
			return memberPhysicalEntity;
		this.memberPhysicalEntity_asDna.add(memberPhysicalEntity);
		_model.add(_model.createStatement(_resource,memberPhysicalEntityProperty,memberPhysicalEntity.resource()));
		return memberPhysicalEntity;
	}
	
	public void removeMemberPhysicalEntity(fr.curie.BiNoM.pathways.biopax.Dna memberPhysicalEntity) throws JastorException {
		if (this.memberPhysicalEntity_asDna == null)
			initMemberPhysicalEntity_asDna();
		if (!this.memberPhysicalEntity_asDna.contains(memberPhysicalEntity))
			return;
		if (!_model.contains(_resource, memberPhysicalEntityProperty, memberPhysicalEntity.resource()))
			return;
		this.memberPhysicalEntity_asDna.remove(memberPhysicalEntity);
		_model.removeAll(_resource, memberPhysicalEntityProperty, memberPhysicalEntity.resource());
	}
		 

	public fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary getCellularLocation() throws JastorException {
		if (cellularLocation != null)
			return cellularLocation;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, cellularLocationProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": cellularLocation getProperty() in fr.curie.BiNoM.pathways.biopax.Dna model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		cellularLocation = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getCellularLocationVocabulary(resource,_model);
		return cellularLocation;
	}

	public void setCellularLocation(fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary cellularLocation) throws JastorException {
		if (_model.contains(_resource,cellularLocationProperty)) {
			_model.removeAll(_resource,cellularLocationProperty,null);
		}
		this.cellularLocation = cellularLocation;
		if (cellularLocation != null) {
			_model.add(_model.createStatement(_resource,cellularLocationProperty, cellularLocation.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary setCellularLocation() throws JastorException {
		if (_model.contains(_resource,cellularLocationProperty)) {
			_model.removeAll(_resource,cellularLocationProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary cellularLocation = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createCellularLocationVocabulary(_model.createResource(),_model);
		this.cellularLocation = cellularLocation;
		_model.add(_model.createStatement(_resource,cellularLocationProperty, cellularLocation.resource()));
		return cellularLocation;
	}
	
	public fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary setCellularLocation(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,cellularLocationProperty)) {
			_model.removeAll(_resource,cellularLocationProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.CellularLocationVocabulary cellularLocation = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getCellularLocationVocabulary(resource,_model);
		this.cellularLocation = cellularLocation;
		_model.add(_model.createStatement(_resource,cellularLocationProperty, cellularLocation.resource()));
		return cellularLocation;
	}
	

	public fr.curie.BiNoM.pathways.biopax.EntityReference getEntityReference() throws JastorException {
		if (entityReference != null)
			return entityReference;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, entityReferenceProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": entityReference getProperty() in fr.curie.BiNoM.pathways.biopax.Dna model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		entityReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityReference(resource,_model);
		return entityReference;
	}

	public void setEntityReference(fr.curie.BiNoM.pathways.biopax.EntityReference entityReference) throws JastorException {
		if (_model.contains(_resource,entityReferenceProperty)) {
			_model.removeAll(_resource,entityReferenceProperty,null);
		}
		this.entityReference = entityReference;
		if (entityReference != null) {
			_model.add(_model.createStatement(_resource,entityReferenceProperty, entityReference.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.EntityReference setEntityReference() throws JastorException {
		if (_model.contains(_resource,entityReferenceProperty)) {
			_model.removeAll(_resource,entityReferenceProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.EntityReference entityReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createEntityReference(_model.createResource(),_model);
		this.entityReference = entityReference;
		_model.add(_model.createStatement(_resource,entityReferenceProperty, entityReference.resource()));
		return entityReference;
	}
	
	public fr.curie.BiNoM.pathways.biopax.EntityReference setEntityReference(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,entityReferenceProperty)) {
			_model.removeAll(_resource,entityReferenceProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.EntityReference entityReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityReference(resource,_model);
		this.entityReference = entityReference;
		_model.add(_model.createStatement(_resource,entityReferenceProperty, entityReference.resource()));
		return entityReference;
	}
	
	public fr.curie.BiNoM.pathways.biopax.DnaReference getEntityReference_asDnaReference() throws JastorException {
		if (entityReference_asDnaReference != null)
			return entityReference_asDnaReference;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, entityReferenceProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": entityReference_asDnaReference getProperty() in fr.curie.BiNoM.pathways.biopax.Dna model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		if (!_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.DnaReference.TYPE))
			return null;
		entityReference_asDnaReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getDnaReference(resource,_model);
		return entityReference_asDnaReference;
	}

	public void setEntityReference(fr.curie.BiNoM.pathways.biopax.DnaReference entityReference) throws JastorException {
		if (_model.contains(_resource,entityReferenceProperty)) {
			_model.removeAll(_resource,entityReferenceProperty,null);
		}
		this.entityReference_asDnaReference = entityReference;
		if (entityReference != null) {
			_model.add(_model.createStatement(_resource,entityReferenceProperty, entityReference.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.DnaReference setEntityReference_asDnaReference() throws JastorException {
		if (_model.contains(_resource,entityReferenceProperty)) {
			_model.removeAll(_resource,entityReferenceProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.DnaReference entityReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createDnaReference(_model.createResource(),_model);
		this.entityReference_asDnaReference = entityReference;
		_model.add(_model.createStatement(_resource,entityReferenceProperty, entityReference.resource()));
		return entityReference;
	}
	
	public fr.curie.BiNoM.pathways.biopax.DnaReference setEntityReference_asDnaReference(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,entityReferenceProperty)) {
			_model.removeAll(_resource,entityReferenceProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.DnaReference entityReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getDnaReference(resource,_model);
		this.entityReference_asDnaReference = entityReference;
		_model.add(_model.createStatement(_resource,entityReferenceProperty, entityReference.resource()));
		return entityReference;
	}
	 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof DnaListener))
			throw new IllegalArgumentException("ThingListener must be instance of DnaListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((DnaListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof DnaListener))
			throw new IllegalArgumentException("ThingListener must be instance of DnaListener"); 
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
							DnaListener listener=(DnaListener)iter.next();
							listener.dataSourceAdded(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,_dataSource);
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
						DnaListener listener=(DnaListener)iter.next();
						listener.availabilityAdded(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,(java.lang.String)obj);
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
						DnaListener listener=(DnaListener)iter.next();
						listener.commentAdded(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,(java.lang.String)obj);
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
						DnaListener listener=(DnaListener)iter.next();
						listener.nameAdded(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,(java.lang.String)obj);
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
							DnaListener listener=(DnaListener)iter.next();
							listener.evidenceAdded(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,_evidence);
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
							DnaListener listener=(DnaListener)iter.next();
							listener.xrefAdded(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,_xref);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(featureProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.EntityFeature _feature = null;
					try {
						_feature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (feature == null) {
						try {
							initFeature();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!feature.contains(_feature))
						feature.add(_feature);
					if (listeners != null) {
						java.util.ArrayList consumersForFeature;
						synchronized (listeners) {
							consumersForFeature = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForFeature.iterator();iter.hasNext();){
							DnaListener listener=(DnaListener)iter.next();
							listener.featureAdded(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,_feature);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(notFeatureProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.EntityFeature _notFeature = null;
					try {
						_notFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (notFeature == null) {
						try {
							initNotFeature();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!notFeature.contains(_notFeature))
						notFeature.add(_notFeature);
					if (listeners != null) {
						java.util.ArrayList consumersForNotFeature;
						synchronized (listeners) {
							consumersForNotFeature = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForNotFeature.iterator();iter.hasNext();){
							DnaListener listener=(DnaListener)iter.next();
							listener.notFeatureAdded(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,_notFeature);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(memberPhysicalEntityProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.PhysicalEntity _memberPhysicalEntity = null;
					try {
						_memberPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (memberPhysicalEntity == null) {
						try {
							initMemberPhysicalEntity();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!memberPhysicalEntity.contains(_memberPhysicalEntity))
						memberPhysicalEntity.add(_memberPhysicalEntity);
					if (listeners != null) {
						java.util.ArrayList consumersForMemberPhysicalEntity;
						synchronized (listeners) {
							consumersForMemberPhysicalEntity = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMemberPhysicalEntity.iterator();iter.hasNext();){
							DnaListener listener=(DnaListener)iter.next();
							listener.memberPhysicalEntityAdded(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,_memberPhysicalEntity);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Dna.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Dna _memberPhysicalEntity_asDna = null;
					try {
						_memberPhysicalEntity_asDna = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getDna(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (memberPhysicalEntity_asDna == null) {
						try {
							initMemberPhysicalEntity_asDna();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!memberPhysicalEntity_asDna.contains(_memberPhysicalEntity_asDna))
						memberPhysicalEntity_asDna.add(_memberPhysicalEntity_asDna);
					if (listeners != null) {
						java.util.ArrayList consumersForMemberPhysicalEntity_asDna;
						synchronized (listeners) {
							consumersForMemberPhysicalEntity_asDna = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMemberPhysicalEntity_asDna.iterator();iter.hasNext();){
							DnaListener listener=(DnaListener)iter.next();
							listener.memberPhysicalEntityAdded(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,_memberPhysicalEntity_asDna);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(cellularLocationProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				cellularLocation = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						cellularLocation = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getCellularLocationVocabulary(resource,_model);
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
						DnaListener listener=(DnaListener)iter.next();
						listener.cellularLocationChanged(fr.curie.BiNoM.pathways.biopax.DnaImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(entityReferenceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				entityReference = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						entityReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityReference(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
				}
				entityReference_asDnaReference = null;
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.DnaReference.TYPE)) {
					try {
						entityReference_asDnaReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getDnaReference(resource,_model);
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
						DnaListener listener=(DnaListener)iter.next();
						listener.entityReferenceChanged(fr.curie.BiNoM.pathways.biopax.DnaImpl.this);
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
							DnaListener listener=(DnaListener)iter.next();
							listener.dataSourceRemoved(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,_dataSource);
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
						DnaListener listener=(DnaListener)iter.next();
						listener.availabilityRemoved(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,(java.lang.String)obj);
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
						DnaListener listener=(DnaListener)iter.next();
						listener.commentRemoved(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,(java.lang.String)obj);
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
						DnaListener listener=(DnaListener)iter.next();
						listener.nameRemoved(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,(java.lang.String)obj);
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
							DnaListener listener=(DnaListener)iter.next();
							listener.evidenceRemoved(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,_evidence);
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
							DnaListener listener=(DnaListener)iter.next();
							listener.xrefRemoved(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,_xref);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(featureProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.EntityFeature _feature = null;
					if (feature != null) {
						boolean found = false;
						for (int i=0;i<feature.size();i++) {
							fr.curie.BiNoM.pathways.biopax.EntityFeature __item = (fr.curie.BiNoM.pathways.biopax.EntityFeature) feature.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_feature = __item;
								break;
							}
						}
						if (found)
							feature.remove(_feature);
						else {
							try {
								_feature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_feature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForFeature;
						synchronized (listeners) {
							consumersForFeature = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForFeature.iterator();iter.hasNext();){
							DnaListener listener=(DnaListener)iter.next();
							listener.featureRemoved(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,_feature);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(notFeatureProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.EntityFeature _notFeature = null;
					if (notFeature != null) {
						boolean found = false;
						for (int i=0;i<notFeature.size();i++) {
							fr.curie.BiNoM.pathways.biopax.EntityFeature __item = (fr.curie.BiNoM.pathways.biopax.EntityFeature) notFeature.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_notFeature = __item;
								break;
							}
						}
						if (found)
							notFeature.remove(_notFeature);
						else {
							try {
								_notFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_notFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForNotFeature;
						synchronized (listeners) {
							consumersForNotFeature = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForNotFeature.iterator();iter.hasNext();){
							DnaListener listener=(DnaListener)iter.next();
							listener.notFeatureRemoved(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,_notFeature);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(memberPhysicalEntityProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.PhysicalEntity _memberPhysicalEntity = null;
					if (memberPhysicalEntity != null) {
						boolean found = false;
						for (int i=0;i<memberPhysicalEntity.size();i++) {
							fr.curie.BiNoM.pathways.biopax.PhysicalEntity __item = (fr.curie.BiNoM.pathways.biopax.PhysicalEntity) memberPhysicalEntity.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_memberPhysicalEntity = __item;
								break;
							}
						}
						if (found)
							memberPhysicalEntity.remove(_memberPhysicalEntity);
						else {
							try {
								_memberPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_memberPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForMemberPhysicalEntity;
						synchronized (listeners) {
							consumersForMemberPhysicalEntity = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMemberPhysicalEntity.iterator();iter.hasNext();){
							DnaListener listener=(DnaListener)iter.next();
							listener.memberPhysicalEntityRemoved(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,_memberPhysicalEntity);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Dna.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Dna _memberPhysicalEntity_asDna = null;
					if (memberPhysicalEntity_asDna != null) {
						boolean found = false;
						for (int i=0;i<memberPhysicalEntity_asDna.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Dna __item = (fr.curie.BiNoM.pathways.biopax.Dna) memberPhysicalEntity_asDna.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_memberPhysicalEntity_asDna = __item;
								break;
							}
						}
						if (found)
							memberPhysicalEntity_asDna.remove(_memberPhysicalEntity_asDna);
						else {
							try {
								_memberPhysicalEntity_asDna = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getDna(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_memberPhysicalEntity_asDna = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getDna(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForMemberPhysicalEntity_asDna;
						synchronized (listeners) {
							consumersForMemberPhysicalEntity_asDna = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMemberPhysicalEntity_asDna.iterator();iter.hasNext();){
							DnaListener listener=(DnaListener)iter.next();
							listener.memberPhysicalEntityRemoved(fr.curie.BiNoM.pathways.biopax.DnaImpl.this,_memberPhysicalEntity_asDna);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(cellularLocationProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (cellularLocation != null && cellularLocation.resource().equals(resource))
						cellularLocation = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						DnaListener listener=(DnaListener)iter.next();
						listener.cellularLocationChanged(fr.curie.BiNoM.pathways.biopax.DnaImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(entityReferenceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (entityReference != null && entityReference.resource().equals(resource))
						entityReference = null;				
					if (entityReference_asDnaReference != null && entityReference_asDnaReference.resource().equals(resource))
						entityReference_asDnaReference = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						DnaListener listener=(DnaListener)iter.next();
						listener.entityReferenceChanged(fr.curie.BiNoM.pathways.biopax.DnaImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}