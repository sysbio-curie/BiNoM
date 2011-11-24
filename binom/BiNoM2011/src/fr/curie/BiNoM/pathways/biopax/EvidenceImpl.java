

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.Evidence}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Evidence)</p>
 * <br>
 */
public class EvidenceImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.Evidence {
	

	private static com.hp.hpl.jena.rdf.model.Property commentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#comment");
	private java.util.ArrayList comment;
	private static com.hp.hpl.jena.rdf.model.Property confidenceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#confidence");
	private java.util.ArrayList confidence;
	private static com.hp.hpl.jena.rdf.model.Property evidenceCodeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#evidenceCode");
	private java.util.ArrayList evidenceCode;
	private static com.hp.hpl.jena.rdf.model.Property experimentalFormProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#experimentalForm");
	private java.util.ArrayList experimentalForm;
	private static com.hp.hpl.jena.rdf.model.Property xrefProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#xref");
	private java.util.ArrayList xref;

	EvidenceImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static EvidenceImpl getEvidence(Resource resource, Model model) throws JastorException {
		return new EvidenceImpl(resource, model);
	}
	    
	static EvidenceImpl createEvidence(Resource resource, Model model) throws JastorException {
		EvidenceImpl impl = new EvidenceImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, Evidence.TYPE)))
			impl._model.add(impl._resource, RDF.type, Evidence.TYPE);
		impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE));     
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
		it = _model.listStatements(_resource,confidenceProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,evidenceCodeProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,experimentalFormProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,xrefProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.Evidence.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
	
	public void clearCache() {
		comment = null;
		confidence = null;
		evidenceCode = null;
		experimentalForm = null;
		xref = null;
	}


	private void initComment() throws JastorException {
		comment = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, commentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#comment properties in Evidence model not a Literal", stmt.getObject());
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


	private void initConfidence() throws JastorException {
		this.confidence = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, confidenceProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#confidence properties in Evidence model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.Score confidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getScore(resource,_model);
				this.confidence.add(confidence);
			}
		}
	}

	public java.util.Iterator getConfidence() throws JastorException {
		if (confidence == null)
			initConfidence();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(confidence,_resource,confidenceProperty,true);
	}

	public void addConfidence(fr.curie.BiNoM.pathways.biopax.Score confidence) throws JastorException {
		if (this.confidence == null)
			initConfidence();
		if (this.confidence.contains(confidence)) {
			this.confidence.remove(confidence);
			this.confidence.add(confidence);
			return;
		}
		this.confidence.add(confidence);
		_model.add(_model.createStatement(_resource,confidenceProperty,confidence.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Score addConfidence() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Score confidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createScore(_model.createResource(),_model);
		if (this.confidence == null)
			initConfidence();
		this.confidence.add(confidence);
		_model.add(_model.createStatement(_resource,confidenceProperty,confidence.resource()));
		return confidence;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Score addConfidence(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Score confidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getScore(resource,_model);
		if (this.confidence == null)
			initConfidence();
		if (this.confidence.contains(confidence))
			return confidence;
		this.confidence.add(confidence);
		_model.add(_model.createStatement(_resource,confidenceProperty,confidence.resource()));
		return confidence;
	}
	
	public void removeConfidence(fr.curie.BiNoM.pathways.biopax.Score confidence) throws JastorException {
		if (this.confidence == null)
			initConfidence();
		if (!this.confidence.contains(confidence))
			return;
		if (!_model.contains(_resource, confidenceProperty, confidence.resource()))
			return;
		this.confidence.remove(confidence);
		_model.removeAll(_resource, confidenceProperty, confidence.resource());
	}
		 

	private void initEvidenceCode() throws JastorException {
		this.evidenceCode = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, evidenceCodeProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#evidenceCode properties in Evidence model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary evidenceCode = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEvidenceCodeVocabulary(resource,_model);
				this.evidenceCode.add(evidenceCode);
			}
		}
	}

	public java.util.Iterator getEvidenceCode() throws JastorException {
		if (evidenceCode == null)
			initEvidenceCode();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(evidenceCode,_resource,evidenceCodeProperty,true);
	}

	public void addEvidenceCode(fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary evidenceCode) throws JastorException {
		if (this.evidenceCode == null)
			initEvidenceCode();
		if (this.evidenceCode.contains(evidenceCode)) {
			this.evidenceCode.remove(evidenceCode);
			this.evidenceCode.add(evidenceCode);
			return;
		}
		this.evidenceCode.add(evidenceCode);
		_model.add(_model.createStatement(_resource,evidenceCodeProperty,evidenceCode.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary addEvidenceCode() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary evidenceCode = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createEvidenceCodeVocabulary(_model.createResource(),_model);
		if (this.evidenceCode == null)
			initEvidenceCode();
		this.evidenceCode.add(evidenceCode);
		_model.add(_model.createStatement(_resource,evidenceCodeProperty,evidenceCode.resource()));
		return evidenceCode;
	}
	
	public fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary addEvidenceCode(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary evidenceCode = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEvidenceCodeVocabulary(resource,_model);
		if (this.evidenceCode == null)
			initEvidenceCode();
		if (this.evidenceCode.contains(evidenceCode))
			return evidenceCode;
		this.evidenceCode.add(evidenceCode);
		_model.add(_model.createStatement(_resource,evidenceCodeProperty,evidenceCode.resource()));
		return evidenceCode;
	}
	
	public void removeEvidenceCode(fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary evidenceCode) throws JastorException {
		if (this.evidenceCode == null)
			initEvidenceCode();
		if (!this.evidenceCode.contains(evidenceCode))
			return;
		if (!_model.contains(_resource, evidenceCodeProperty, evidenceCode.resource()))
			return;
		this.evidenceCode.remove(evidenceCode);
		_model.removeAll(_resource, evidenceCodeProperty, evidenceCode.resource());
	}
		 

	private void initExperimentalForm() throws JastorException {
		this.experimentalForm = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, experimentalFormProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#experimentalForm properties in Evidence model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.ExperimentalForm experimentalForm = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getExperimentalForm(resource,_model);
				this.experimentalForm.add(experimentalForm);
			}
		}
	}

	public java.util.Iterator getExperimentalForm() throws JastorException {
		if (experimentalForm == null)
			initExperimentalForm();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(experimentalForm,_resource,experimentalFormProperty,true);
	}

	public void addExperimentalForm(fr.curie.BiNoM.pathways.biopax.ExperimentalForm experimentalForm) throws JastorException {
		if (this.experimentalForm == null)
			initExperimentalForm();
		if (this.experimentalForm.contains(experimentalForm)) {
			this.experimentalForm.remove(experimentalForm);
			this.experimentalForm.add(experimentalForm);
			return;
		}
		this.experimentalForm.add(experimentalForm);
		_model.add(_model.createStatement(_resource,experimentalFormProperty,experimentalForm.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.ExperimentalForm addExperimentalForm() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.ExperimentalForm experimentalForm = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createExperimentalForm(_model.createResource(),_model);
		if (this.experimentalForm == null)
			initExperimentalForm();
		this.experimentalForm.add(experimentalForm);
		_model.add(_model.createStatement(_resource,experimentalFormProperty,experimentalForm.resource()));
		return experimentalForm;
	}
	
	public fr.curie.BiNoM.pathways.biopax.ExperimentalForm addExperimentalForm(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.ExperimentalForm experimentalForm = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getExperimentalForm(resource,_model);
		if (this.experimentalForm == null)
			initExperimentalForm();
		if (this.experimentalForm.contains(experimentalForm))
			return experimentalForm;
		this.experimentalForm.add(experimentalForm);
		_model.add(_model.createStatement(_resource,experimentalFormProperty,experimentalForm.resource()));
		return experimentalForm;
	}
	
	public void removeExperimentalForm(fr.curie.BiNoM.pathways.biopax.ExperimentalForm experimentalForm) throws JastorException {
		if (this.experimentalForm == null)
			initExperimentalForm();
		if (!this.experimentalForm.contains(experimentalForm))
			return;
		if (!_model.contains(_resource, experimentalFormProperty, experimentalForm.resource()))
			return;
		this.experimentalForm.remove(experimentalForm);
		_model.removeAll(_resource, experimentalFormProperty, experimentalForm.resource());
	}
		 

	private void initXref() throws JastorException {
		this.xref = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, xrefProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#xref properties in Evidence model not a Resource", stmt.getObject());
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
		  


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof EvidenceListener))
			throw new IllegalArgumentException("ThingListener must be instance of EvidenceListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((EvidenceListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof EvidenceListener))
			throw new IllegalArgumentException("ThingListener must be instance of EvidenceListener"); 
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
						EvidenceListener listener=(EvidenceListener)iter.next();
						listener.commentAdded(fr.curie.BiNoM.pathways.biopax.EvidenceImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(confidenceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Score _confidence = null;
					try {
						_confidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getScore(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (confidence == null) {
						try {
							initConfidence();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!confidence.contains(_confidence))
						confidence.add(_confidence);
					if (listeners != null) {
						java.util.ArrayList consumersForConfidence;
						synchronized (listeners) {
							consumersForConfidence = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForConfidence.iterator();iter.hasNext();){
							EvidenceListener listener=(EvidenceListener)iter.next();
							listener.confidenceAdded(fr.curie.BiNoM.pathways.biopax.EvidenceImpl.this,_confidence);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(evidenceCodeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary _evidenceCode = null;
					try {
						_evidenceCode = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEvidenceCodeVocabulary(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (evidenceCode == null) {
						try {
							initEvidenceCode();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!evidenceCode.contains(_evidenceCode))
						evidenceCode.add(_evidenceCode);
					if (listeners != null) {
						java.util.ArrayList consumersForEvidenceCode;
						synchronized (listeners) {
							consumersForEvidenceCode = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEvidenceCode.iterator();iter.hasNext();){
							EvidenceListener listener=(EvidenceListener)iter.next();
							listener.evidenceCodeAdded(fr.curie.BiNoM.pathways.biopax.EvidenceImpl.this,_evidenceCode);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(experimentalFormProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.ExperimentalForm _experimentalForm = null;
					try {
						_experimentalForm = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getExperimentalForm(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (experimentalForm == null) {
						try {
							initExperimentalForm();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!experimentalForm.contains(_experimentalForm))
						experimentalForm.add(_experimentalForm);
					if (listeners != null) {
						java.util.ArrayList consumersForExperimentalForm;
						synchronized (listeners) {
							consumersForExperimentalForm = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForExperimentalForm.iterator();iter.hasNext();){
							EvidenceListener listener=(EvidenceListener)iter.next();
							listener.experimentalFormAdded(fr.curie.BiNoM.pathways.biopax.EvidenceImpl.this,_experimentalForm);
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
							EvidenceListener listener=(EvidenceListener)iter.next();
							listener.xrefAdded(fr.curie.BiNoM.pathways.biopax.EvidenceImpl.this,_xref);
						}
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
						EvidenceListener listener=(EvidenceListener)iter.next();
						listener.commentRemoved(fr.curie.BiNoM.pathways.biopax.EvidenceImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(confidenceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Score _confidence = null;
					if (confidence != null) {
						boolean found = false;
						for (int i=0;i<confidence.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Score __item = (fr.curie.BiNoM.pathways.biopax.Score) confidence.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_confidence = __item;
								break;
							}
						}
						if (found)
							confidence.remove(_confidence);
						else {
							try {
								_confidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getScore(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_confidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getScore(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForConfidence;
						synchronized (listeners) {
							consumersForConfidence = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForConfidence.iterator();iter.hasNext();){
							EvidenceListener listener=(EvidenceListener)iter.next();
							listener.confidenceRemoved(fr.curie.BiNoM.pathways.biopax.EvidenceImpl.this,_confidence);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(evidenceCodeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary _evidenceCode = null;
					if (evidenceCode != null) {
						boolean found = false;
						for (int i=0;i<evidenceCode.size();i++) {
							fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary __item = (fr.curie.BiNoM.pathways.biopax.EvidenceCodeVocabulary) evidenceCode.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_evidenceCode = __item;
								break;
							}
						}
						if (found)
							evidenceCode.remove(_evidenceCode);
						else {
							try {
								_evidenceCode = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEvidenceCodeVocabulary(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_evidenceCode = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEvidenceCodeVocabulary(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForEvidenceCode;
						synchronized (listeners) {
							consumersForEvidenceCode = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEvidenceCode.iterator();iter.hasNext();){
							EvidenceListener listener=(EvidenceListener)iter.next();
							listener.evidenceCodeRemoved(fr.curie.BiNoM.pathways.biopax.EvidenceImpl.this,_evidenceCode);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(experimentalFormProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.ExperimentalForm _experimentalForm = null;
					if (experimentalForm != null) {
						boolean found = false;
						for (int i=0;i<experimentalForm.size();i++) {
							fr.curie.BiNoM.pathways.biopax.ExperimentalForm __item = (fr.curie.BiNoM.pathways.biopax.ExperimentalForm) experimentalForm.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_experimentalForm = __item;
								break;
							}
						}
						if (found)
							experimentalForm.remove(_experimentalForm);
						else {
							try {
								_experimentalForm = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getExperimentalForm(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_experimentalForm = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getExperimentalForm(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForExperimentalForm;
						synchronized (listeners) {
							consumersForExperimentalForm = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForExperimentalForm.iterator();iter.hasNext();){
							EvidenceListener listener=(EvidenceListener)iter.next();
							listener.experimentalFormRemoved(fr.curie.BiNoM.pathways.biopax.EvidenceImpl.this,_experimentalForm);
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
							EvidenceListener listener=(EvidenceListener)iter.next();
							listener.xrefRemoved(fr.curie.BiNoM.pathways.biopax.EvidenceImpl.this,_xref);
						}
					}
				}
				return;
			}
		}

	//}
	


}