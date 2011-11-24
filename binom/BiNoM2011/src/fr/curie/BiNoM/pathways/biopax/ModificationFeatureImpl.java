

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.ModificationFeature}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#ModificationFeature)</p>
 * <br>
 */
public class ModificationFeatureImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.ModificationFeature {
	

	private static com.hp.hpl.jena.rdf.model.Property commentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#comment");
	private java.util.ArrayList comment;
	private static com.hp.hpl.jena.rdf.model.Property memberFeatureProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#memberFeature");
	private java.util.ArrayList memberFeature;
	private static com.hp.hpl.jena.rdf.model.Property featureLocationProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#featureLocation");
	private java.util.ArrayList featureLocation;
	private static com.hp.hpl.jena.rdf.model.Property featureLocationTypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#featureLocationType");
	private java.util.ArrayList featureLocationType;
	private static com.hp.hpl.jena.rdf.model.Property evidenceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#evidence");
	private java.util.ArrayList evidence;
	private static com.hp.hpl.jena.rdf.model.Property modificationTypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#modificationType");
	private fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary modificationType;

	ModificationFeatureImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static ModificationFeatureImpl getModificationFeature(Resource resource, Model model) throws JastorException {
		return new ModificationFeatureImpl(resource, model);
	}
	    
	static ModificationFeatureImpl createModificationFeature(Resource resource, Model model) throws JastorException {
		ModificationFeatureImpl impl = new ModificationFeatureImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, ModificationFeature.TYPE)))
			impl._model.add(impl._resource, RDF.type, ModificationFeature.TYPE);
		impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.EntityFeature.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.EntityFeature.TYPE));     
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
		it = _model.listStatements(_resource,commentProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,memberFeatureProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,featureLocationProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,featureLocationTypeProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,evidenceProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,modificationTypeProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.ModificationFeature.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.EntityFeature.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
	
	public void clearCache() {
		comment = null;
		memberFeature = null;
		featureLocation = null;
		featureLocationType = null;
		evidence = null;
		modificationType = null;
	}


	private void initComment() throws JastorException {
		comment = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, commentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#comment properties in ModificationFeature model not a Literal", stmt.getObject());
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


	private void initMemberFeature() throws JastorException {
		this.memberFeature = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, memberFeatureProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#memberFeature properties in ModificationFeature model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.EntityFeature memberFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
				this.memberFeature.add(memberFeature);
			}
		}
	}

	public java.util.Iterator getMemberFeature() throws JastorException {
		if (memberFeature == null)
			initMemberFeature();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(memberFeature,_resource,memberFeatureProperty,true);
	}

	public void addMemberFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature memberFeature) throws JastorException {
		if (this.memberFeature == null)
			initMemberFeature();
		if (this.memberFeature.contains(memberFeature)) {
			this.memberFeature.remove(memberFeature);
			this.memberFeature.add(memberFeature);
			return;
		}
		this.memberFeature.add(memberFeature);
		_model.add(_model.createStatement(_resource,memberFeatureProperty,memberFeature.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addMemberFeature() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.EntityFeature memberFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createEntityFeature(_model.createResource(),_model);
		if (this.memberFeature == null)
			initMemberFeature();
		this.memberFeature.add(memberFeature);
		_model.add(_model.createStatement(_resource,memberFeatureProperty,memberFeature.resource()));
		return memberFeature;
	}
	
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addMemberFeature(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.EntityFeature memberFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
		if (this.memberFeature == null)
			initMemberFeature();
		if (this.memberFeature.contains(memberFeature))
			return memberFeature;
		this.memberFeature.add(memberFeature);
		_model.add(_model.createStatement(_resource,memberFeatureProperty,memberFeature.resource()));
		return memberFeature;
	}
	
	public void removeMemberFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature memberFeature) throws JastorException {
		if (this.memberFeature == null)
			initMemberFeature();
		if (!this.memberFeature.contains(memberFeature))
			return;
		if (!_model.contains(_resource, memberFeatureProperty, memberFeature.resource()))
			return;
		this.memberFeature.remove(memberFeature);
		_model.removeAll(_resource, memberFeatureProperty, memberFeature.resource());
	}
		 

	private void initFeatureLocation() throws JastorException {
		this.featureLocation = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, featureLocationProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#featureLocation properties in ModificationFeature model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.SequenceLocation featureLocation = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceLocation(resource,_model);
				this.featureLocation.add(featureLocation);
			}
		}
	}

	public java.util.Iterator getFeatureLocation() throws JastorException {
		if (featureLocation == null)
			initFeatureLocation();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(featureLocation,_resource,featureLocationProperty,true);
	}

	public void addFeatureLocation(fr.curie.BiNoM.pathways.biopax.SequenceLocation featureLocation) throws JastorException {
		if (this.featureLocation == null)
			initFeatureLocation();
		if (this.featureLocation.contains(featureLocation)) {
			this.featureLocation.remove(featureLocation);
			this.featureLocation.add(featureLocation);
			return;
		}
		this.featureLocation.add(featureLocation);
		_model.add(_model.createStatement(_resource,featureLocationProperty,featureLocation.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.SequenceLocation addFeatureLocation() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.SequenceLocation featureLocation = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createSequenceLocation(_model.createResource(),_model);
		if (this.featureLocation == null)
			initFeatureLocation();
		this.featureLocation.add(featureLocation);
		_model.add(_model.createStatement(_resource,featureLocationProperty,featureLocation.resource()));
		return featureLocation;
	}
	
	public fr.curie.BiNoM.pathways.biopax.SequenceLocation addFeatureLocation(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.SequenceLocation featureLocation = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceLocation(resource,_model);
		if (this.featureLocation == null)
			initFeatureLocation();
		if (this.featureLocation.contains(featureLocation))
			return featureLocation;
		this.featureLocation.add(featureLocation);
		_model.add(_model.createStatement(_resource,featureLocationProperty,featureLocation.resource()));
		return featureLocation;
	}
	
	public void removeFeatureLocation(fr.curie.BiNoM.pathways.biopax.SequenceLocation featureLocation) throws JastorException {
		if (this.featureLocation == null)
			initFeatureLocation();
		if (!this.featureLocation.contains(featureLocation))
			return;
		if (!_model.contains(_resource, featureLocationProperty, featureLocation.resource()))
			return;
		this.featureLocation.remove(featureLocation);
		_model.removeAll(_resource, featureLocationProperty, featureLocation.resource());
	}
		 

	private void initFeatureLocationType() throws JastorException {
		this.featureLocationType = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, featureLocationTypeProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#featureLocationType properties in ModificationFeature model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary featureLocationType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceRegionVocabulary(resource,_model);
				this.featureLocationType.add(featureLocationType);
			}
		}
	}

	public java.util.Iterator getFeatureLocationType() throws JastorException {
		if (featureLocationType == null)
			initFeatureLocationType();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(featureLocationType,_resource,featureLocationTypeProperty,true);
	}

	public void addFeatureLocationType(fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary featureLocationType) throws JastorException {
		if (this.featureLocationType == null)
			initFeatureLocationType();
		if (this.featureLocationType.contains(featureLocationType)) {
			this.featureLocationType.remove(featureLocationType);
			this.featureLocationType.add(featureLocationType);
			return;
		}
		this.featureLocationType.add(featureLocationType);
		_model.add(_model.createStatement(_resource,featureLocationTypeProperty,featureLocationType.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary addFeatureLocationType() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary featureLocationType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createSequenceRegionVocabulary(_model.createResource(),_model);
		if (this.featureLocationType == null)
			initFeatureLocationType();
		this.featureLocationType.add(featureLocationType);
		_model.add(_model.createStatement(_resource,featureLocationTypeProperty,featureLocationType.resource()));
		return featureLocationType;
	}
	
	public fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary addFeatureLocationType(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary featureLocationType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceRegionVocabulary(resource,_model);
		if (this.featureLocationType == null)
			initFeatureLocationType();
		if (this.featureLocationType.contains(featureLocationType))
			return featureLocationType;
		this.featureLocationType.add(featureLocationType);
		_model.add(_model.createStatement(_resource,featureLocationTypeProperty,featureLocationType.resource()));
		return featureLocationType;
	}
	
	public void removeFeatureLocationType(fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary featureLocationType) throws JastorException {
		if (this.featureLocationType == null)
			initFeatureLocationType();
		if (!this.featureLocationType.contains(featureLocationType))
			return;
		if (!_model.contains(_resource, featureLocationTypeProperty, featureLocationType.resource()))
			return;
		this.featureLocationType.remove(featureLocationType);
		_model.removeAll(_resource, featureLocationTypeProperty, featureLocationType.resource());
	}
		 

	private void initEvidence() throws JastorException {
		this.evidence = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, evidenceProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#evidence properties in ModificationFeature model not a Resource", stmt.getObject());
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
		 

	public fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary getModificationType() throws JastorException {
		if (modificationType != null)
			return modificationType;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, modificationTypeProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": modificationType getProperty() in fr.curie.BiNoM.pathways.biopax.ModificationFeature model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		modificationType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceModificationVocabulary(resource,_model);
		return modificationType;
	}

	public void setModificationType(fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary modificationType) throws JastorException {
		if (_model.contains(_resource,modificationTypeProperty)) {
			_model.removeAll(_resource,modificationTypeProperty,null);
		}
		this.modificationType = modificationType;
		if (modificationType != null) {
			_model.add(_model.createStatement(_resource,modificationTypeProperty, modificationType.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary setModificationType() throws JastorException {
		if (_model.contains(_resource,modificationTypeProperty)) {
			_model.removeAll(_resource,modificationTypeProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary modificationType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createSequenceModificationVocabulary(_model.createResource(),_model);
		this.modificationType = modificationType;
		_model.add(_model.createStatement(_resource,modificationTypeProperty, modificationType.resource()));
		return modificationType;
	}
	
	public fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary setModificationType(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,modificationTypeProperty)) {
			_model.removeAll(_resource,modificationTypeProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary modificationType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceModificationVocabulary(resource,_model);
		this.modificationType = modificationType;
		_model.add(_model.createStatement(_resource,modificationTypeProperty, modificationType.resource()));
		return modificationType;
	}
	 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof ModificationFeatureListener))
			throw new IllegalArgumentException("ThingListener must be instance of ModificationFeatureListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((ModificationFeatureListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof ModificationFeatureListener))
			throw new IllegalArgumentException("ThingListener must be instance of ModificationFeatureListener"); 
		if (listeners == null)
			return;
		if (this.listeners.contains(listener)){
			listeners.remove(listener);
		}
	}



	
		public void addedStatement(com.hp.hpl.jena.rdf.model.Statement stmt) {

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
						ModificationFeatureListener listener=(ModificationFeatureListener)iter.next();
						listener.commentAdded(fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(memberFeatureProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.EntityFeature _memberFeature = null;
					try {
						_memberFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (memberFeature == null) {
						try {
							initMemberFeature();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!memberFeature.contains(_memberFeature))
						memberFeature.add(_memberFeature);
					if (listeners != null) {
						java.util.ArrayList consumersForMemberFeature;
						synchronized (listeners) {
							consumersForMemberFeature = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMemberFeature.iterator();iter.hasNext();){
							ModificationFeatureListener listener=(ModificationFeatureListener)iter.next();
							listener.memberFeatureAdded(fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl.this,_memberFeature);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(featureLocationProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.SequenceLocation _featureLocation = null;
					try {
						_featureLocation = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceLocation(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (featureLocation == null) {
						try {
							initFeatureLocation();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!featureLocation.contains(_featureLocation))
						featureLocation.add(_featureLocation);
					if (listeners != null) {
						java.util.ArrayList consumersForFeatureLocation;
						synchronized (listeners) {
							consumersForFeatureLocation = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForFeatureLocation.iterator();iter.hasNext();){
							ModificationFeatureListener listener=(ModificationFeatureListener)iter.next();
							listener.featureLocationAdded(fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl.this,_featureLocation);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(featureLocationTypeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary _featureLocationType = null;
					try {
						_featureLocationType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceRegionVocabulary(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (featureLocationType == null) {
						try {
							initFeatureLocationType();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!featureLocationType.contains(_featureLocationType))
						featureLocationType.add(_featureLocationType);
					if (listeners != null) {
						java.util.ArrayList consumersForFeatureLocationType;
						synchronized (listeners) {
							consumersForFeatureLocationType = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForFeatureLocationType.iterator();iter.hasNext();){
							ModificationFeatureListener listener=(ModificationFeatureListener)iter.next();
							listener.featureLocationTypeAdded(fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl.this,_featureLocationType);
						}
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
							ModificationFeatureListener listener=(ModificationFeatureListener)iter.next();
							listener.evidenceAdded(fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl.this,_evidence);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(modificationTypeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				modificationType = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						modificationType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceModificationVocabulary(resource,_model);
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
						ModificationFeatureListener listener=(ModificationFeatureListener)iter.next();
						listener.modificationTypeChanged(fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl.this);
					}
				}
				return;
			}
		}
		
		public void removedStatement(com.hp.hpl.jena.rdf.model.Statement stmt) {
//			if (!stmt.getSubject().equals(_resource))
//				return;
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
						ModificationFeatureListener listener=(ModificationFeatureListener)iter.next();
						listener.commentRemoved(fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(memberFeatureProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.EntityFeature _memberFeature = null;
					if (memberFeature != null) {
						boolean found = false;
						for (int i=0;i<memberFeature.size();i++) {
							fr.curie.BiNoM.pathways.biopax.EntityFeature __item = (fr.curie.BiNoM.pathways.biopax.EntityFeature) memberFeature.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_memberFeature = __item;
								break;
							}
						}
						if (found)
							memberFeature.remove(_memberFeature);
						else {
							try {
								_memberFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_memberFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForMemberFeature;
						synchronized (listeners) {
							consumersForMemberFeature = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMemberFeature.iterator();iter.hasNext();){
							ModificationFeatureListener listener=(ModificationFeatureListener)iter.next();
							listener.memberFeatureRemoved(fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl.this,_memberFeature);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(featureLocationProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.SequenceLocation _featureLocation = null;
					if (featureLocation != null) {
						boolean found = false;
						for (int i=0;i<featureLocation.size();i++) {
							fr.curie.BiNoM.pathways.biopax.SequenceLocation __item = (fr.curie.BiNoM.pathways.biopax.SequenceLocation) featureLocation.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_featureLocation = __item;
								break;
							}
						}
						if (found)
							featureLocation.remove(_featureLocation);
						else {
							try {
								_featureLocation = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceLocation(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_featureLocation = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceLocation(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForFeatureLocation;
						synchronized (listeners) {
							consumersForFeatureLocation = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForFeatureLocation.iterator();iter.hasNext();){
							ModificationFeatureListener listener=(ModificationFeatureListener)iter.next();
							listener.featureLocationRemoved(fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl.this,_featureLocation);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(featureLocationTypeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary _featureLocationType = null;
					if (featureLocationType != null) {
						boolean found = false;
						for (int i=0;i<featureLocationType.size();i++) {
							fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary __item = (fr.curie.BiNoM.pathways.biopax.SequenceRegionVocabulary) featureLocationType.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_featureLocationType = __item;
								break;
							}
						}
						if (found)
							featureLocationType.remove(_featureLocationType);
						else {
							try {
								_featureLocationType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceRegionVocabulary(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_featureLocationType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceRegionVocabulary(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForFeatureLocationType;
						synchronized (listeners) {
							consumersForFeatureLocationType = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForFeatureLocationType.iterator();iter.hasNext();){
							ModificationFeatureListener listener=(ModificationFeatureListener)iter.next();
							listener.featureLocationTypeRemoved(fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl.this,_featureLocationType);
						}
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
							ModificationFeatureListener listener=(ModificationFeatureListener)iter.next();
							listener.evidenceRemoved(fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl.this,_evidence);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(modificationTypeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (modificationType != null && modificationType.resource().equals(resource))
						modificationType = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						ModificationFeatureListener listener=(ModificationFeatureListener)iter.next();
						listener.modificationTypeChanged(fr.curie.BiNoM.pathways.biopax.ModificationFeatureImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}