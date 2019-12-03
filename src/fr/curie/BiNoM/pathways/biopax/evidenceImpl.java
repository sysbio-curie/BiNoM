

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.evidence}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#evidence)</p>
 * <br>
 */
public class evidenceImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.evidence {
	

	private static com.hp.hpl.jena.rdf.model.Property COMMENTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#COMMENT");
	private java.util.ArrayList COMMENT;
	private static com.hp.hpl.jena.rdf.model.Property EXPERIMENTAL_DASH_FORMProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#EXPERIMENTAL-FORM");
	private java.util.ArrayList EXPERIMENTAL_DASH_FORM;
	private static com.hp.hpl.jena.rdf.model.Property EVIDENCE_DASH_CODEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#EVIDENCE-CODE");
	private java.util.ArrayList EVIDENCE_DASH_CODE;
	private static com.hp.hpl.jena.rdf.model.Property CONFIDENCEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#CONFIDENCE");
	private java.util.ArrayList CONFIDENCE;
	private static com.hp.hpl.jena.rdf.model.Property XREFProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#XREF");
	private java.util.ArrayList XREF;

	evidenceImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static evidenceImpl getevidence(Resource resource, Model model) throws JastorException {
		return new evidenceImpl(resource, model);
	}
	    
	static evidenceImpl createevidence(Resource resource, Model model) throws JastorException {
		evidenceImpl impl = new evidenceImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, evidence.TYPE)))
			impl._model.add(impl._resource, RDF.type, evidence.TYPE);
		//impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.utilityClass.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.utilityClass.TYPE));     
	}
   
	void addHasValueValues() {
	}
    
    private void setupModelListener() {
    	listeners = new java.util.ArrayList();
    	fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.registerThing(this);
    }

	public java.util.List listStatements() {
		java.util.List list = new java.util.ArrayList();
		StmtIterator it = null;
		it = _model.listStatements(_resource,COMMENTProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,EXPERIMENTAL_DASH_FORMProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,EVIDENCE_DASH_CODEProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,CONFIDENCEProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,XREFProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.evidence.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.utilityClass.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
	
	public void clearCache() {
		COMMENT = null;
		EXPERIMENTAL_DASH_FORM = null;
		EVIDENCE_DASH_CODE = null;
		CONFIDENCE = null;
		XREF = null;
	}


	private void initCOMMENT() throws JastorException {
		COMMENT = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, COMMENTProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#COMMENT properties in evidence model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			COMMENT.add(Util.fixLiteral(literal.getValue(),"java.lang.String"));
		}
	}

	public java.util.Iterator getCOMMENT() throws JastorException {
		if (COMMENT == null)
			initCOMMENT();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(COMMENT,_resource,COMMENTProperty,false);
	}

	public void addCOMMENT(java.lang.String COMMENT) throws JastorException {
		if (this.COMMENT == null)
			initCOMMENT();
		if (this.COMMENT.contains(COMMENT))
			return;
		if (_model.contains(_resource, COMMENTProperty, _model.createTypedLiteral(COMMENT)))
			return;
		this.COMMENT.add(COMMENT);
		_model.add(_resource, COMMENTProperty, _model.createTypedLiteral(COMMENT));
	}
	
	public void removeCOMMENT(java.lang.String COMMENT) throws JastorException {
		if (this.COMMENT == null)
			initCOMMENT();
		if (!this.COMMENT.contains(COMMENT))
			return;
		if (!_model.contains(_resource, COMMENTProperty, _model.createTypedLiteral(COMMENT)))
			return;
		this.COMMENT.remove(COMMENT);
		_model.removeAll(_resource, COMMENTProperty, _model.createTypedLiteral(COMMENT));
	}


	private void initEXPERIMENTAL_DASH_FORM() throws JastorException {
		this.EXPERIMENTAL_DASH_FORM = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, EXPERIMENTAL_DASH_FORMProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#EXPERIMENTAL-FORM properties in evidence model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.experimentalForm EXPERIMENTAL_DASH_FORM = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getexperimentalForm(resource,_model);
				this.EXPERIMENTAL_DASH_FORM.add(EXPERIMENTAL_DASH_FORM);
			}
		}
	}

	public java.util.Iterator getEXPERIMENTAL_DASH_FORM() throws JastorException {
		if (EXPERIMENTAL_DASH_FORM == null)
			initEXPERIMENTAL_DASH_FORM();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(EXPERIMENTAL_DASH_FORM,_resource,EXPERIMENTAL_DASH_FORMProperty,true);
	}

	public void addEXPERIMENTAL_DASH_FORM(fr.curie.BiNoM.pathways.biopax.experimentalForm EXPERIMENTAL_DASH_FORM) throws JastorException {
		if (this.EXPERIMENTAL_DASH_FORM == null)
			initEXPERIMENTAL_DASH_FORM();
		if (this.EXPERIMENTAL_DASH_FORM.contains(EXPERIMENTAL_DASH_FORM)) {
			this.EXPERIMENTAL_DASH_FORM.remove(EXPERIMENTAL_DASH_FORM);
			this.EXPERIMENTAL_DASH_FORM.add(EXPERIMENTAL_DASH_FORM);
			return;
		}
		this.EXPERIMENTAL_DASH_FORM.add(EXPERIMENTAL_DASH_FORM);
		_model.add(_model.createStatement(_resource,EXPERIMENTAL_DASH_FORMProperty,EXPERIMENTAL_DASH_FORM.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.experimentalForm addEXPERIMENTAL_DASH_FORM() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.experimentalForm EXPERIMENTAL_DASH_FORM = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createexperimentalForm(_model.createResource(),_model);
		if (this.EXPERIMENTAL_DASH_FORM == null)
			initEXPERIMENTAL_DASH_FORM();
		this.EXPERIMENTAL_DASH_FORM.add(EXPERIMENTAL_DASH_FORM);
		_model.add(_model.createStatement(_resource,EXPERIMENTAL_DASH_FORMProperty,EXPERIMENTAL_DASH_FORM.resource()));
		return EXPERIMENTAL_DASH_FORM;
	}
	
	public fr.curie.BiNoM.pathways.biopax.experimentalForm addEXPERIMENTAL_DASH_FORM(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.experimentalForm EXPERIMENTAL_DASH_FORM = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getexperimentalForm(resource,_model);
		if (this.EXPERIMENTAL_DASH_FORM == null)
			initEXPERIMENTAL_DASH_FORM();
		if (this.EXPERIMENTAL_DASH_FORM.contains(EXPERIMENTAL_DASH_FORM))
			return EXPERIMENTAL_DASH_FORM;
		this.EXPERIMENTAL_DASH_FORM.add(EXPERIMENTAL_DASH_FORM);
		_model.add(_model.createStatement(_resource,EXPERIMENTAL_DASH_FORMProperty,EXPERIMENTAL_DASH_FORM.resource()));
		return EXPERIMENTAL_DASH_FORM;
	}
	
	public void removeEXPERIMENTAL_DASH_FORM(fr.curie.BiNoM.pathways.biopax.experimentalForm EXPERIMENTAL_DASH_FORM) throws JastorException {
		if (this.EXPERIMENTAL_DASH_FORM == null)
			initEXPERIMENTAL_DASH_FORM();
		if (!this.EXPERIMENTAL_DASH_FORM.contains(EXPERIMENTAL_DASH_FORM))
			return;
		if (!_model.contains(_resource, EXPERIMENTAL_DASH_FORMProperty, EXPERIMENTAL_DASH_FORM.resource()))
			return;
		this.EXPERIMENTAL_DASH_FORM.remove(EXPERIMENTAL_DASH_FORM);
		_model.removeAll(_resource, EXPERIMENTAL_DASH_FORMProperty, EXPERIMENTAL_DASH_FORM.resource());
	}
		 

	private void initEVIDENCE_DASH_CODE() throws JastorException {
		this.EVIDENCE_DASH_CODE = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, EVIDENCE_DASH_CODEProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#EVIDENCE-CODE properties in evidence model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.openControlledVocabulary EVIDENCE_DASH_CODE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
				this.EVIDENCE_DASH_CODE.add(EVIDENCE_DASH_CODE);
			}
		}
	}

	public java.util.Iterator getEVIDENCE_DASH_CODE() throws JastorException {
		if (EVIDENCE_DASH_CODE == null)
			initEVIDENCE_DASH_CODE();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(EVIDENCE_DASH_CODE,_resource,EVIDENCE_DASH_CODEProperty,true);
	}

	public void addEVIDENCE_DASH_CODE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary EVIDENCE_DASH_CODE) throws JastorException {
		if (this.EVIDENCE_DASH_CODE == null)
			initEVIDENCE_DASH_CODE();
		if (this.EVIDENCE_DASH_CODE.contains(EVIDENCE_DASH_CODE)) {
			this.EVIDENCE_DASH_CODE.remove(EVIDENCE_DASH_CODE);
			this.EVIDENCE_DASH_CODE.add(EVIDENCE_DASH_CODE);
			return;
		}
		this.EVIDENCE_DASH_CODE.add(EVIDENCE_DASH_CODE);
		_model.add(_model.createStatement(_resource,EVIDENCE_DASH_CODEProperty,EVIDENCE_DASH_CODE.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary addEVIDENCE_DASH_CODE() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.openControlledVocabulary EVIDENCE_DASH_CODE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createopenControlledVocabulary(_model.createResource(),_model);
		if (this.EVIDENCE_DASH_CODE == null)
			initEVIDENCE_DASH_CODE();
		this.EVIDENCE_DASH_CODE.add(EVIDENCE_DASH_CODE);
		_model.add(_model.createStatement(_resource,EVIDENCE_DASH_CODEProperty,EVIDENCE_DASH_CODE.resource()));
		return EVIDENCE_DASH_CODE;
	}
	
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary addEVIDENCE_DASH_CODE(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.openControlledVocabulary EVIDENCE_DASH_CODE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
		if (this.EVIDENCE_DASH_CODE == null)
			initEVIDENCE_DASH_CODE();
		if (this.EVIDENCE_DASH_CODE.contains(EVIDENCE_DASH_CODE))
			return EVIDENCE_DASH_CODE;
		this.EVIDENCE_DASH_CODE.add(EVIDENCE_DASH_CODE);
		_model.add(_model.createStatement(_resource,EVIDENCE_DASH_CODEProperty,EVIDENCE_DASH_CODE.resource()));
		return EVIDENCE_DASH_CODE;
	}
	
	public void removeEVIDENCE_DASH_CODE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary EVIDENCE_DASH_CODE) throws JastorException {
		if (this.EVIDENCE_DASH_CODE == null)
			initEVIDENCE_DASH_CODE();
		if (!this.EVIDENCE_DASH_CODE.contains(EVIDENCE_DASH_CODE))
			return;
		if (!_model.contains(_resource, EVIDENCE_DASH_CODEProperty, EVIDENCE_DASH_CODE.resource()))
			return;
		this.EVIDENCE_DASH_CODE.remove(EVIDENCE_DASH_CODE);
		_model.removeAll(_resource, EVIDENCE_DASH_CODEProperty, EVIDENCE_DASH_CODE.resource());
	}
		 

	private void initCONFIDENCE() throws JastorException {
		this.CONFIDENCE = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, CONFIDENCEProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#CONFIDENCE properties in evidence model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.confidence CONFIDENCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getconfidence(resource,_model);
				this.CONFIDENCE.add(CONFIDENCE);
			}
		}
	}

	public java.util.Iterator getCONFIDENCE() throws JastorException {
		if (CONFIDENCE == null)
			initCONFIDENCE();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(CONFIDENCE,_resource,CONFIDENCEProperty,true);
	}

	public void addCONFIDENCE(fr.curie.BiNoM.pathways.biopax.confidence CONFIDENCE) throws JastorException {
		if (this.CONFIDENCE == null)
			initCONFIDENCE();
		if (this.CONFIDENCE.contains(CONFIDENCE)) {
			this.CONFIDENCE.remove(CONFIDENCE);
			this.CONFIDENCE.add(CONFIDENCE);
			return;
		}
		this.CONFIDENCE.add(CONFIDENCE);
		_model.add(_model.createStatement(_resource,CONFIDENCEProperty,CONFIDENCE.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.confidence addCONFIDENCE() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.confidence CONFIDENCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createconfidence(_model.createResource(),_model);
		if (this.CONFIDENCE == null)
			initCONFIDENCE();
		this.CONFIDENCE.add(CONFIDENCE);
		_model.add(_model.createStatement(_resource,CONFIDENCEProperty,CONFIDENCE.resource()));
		return CONFIDENCE;
	}
	
	public fr.curie.BiNoM.pathways.biopax.confidence addCONFIDENCE(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.confidence CONFIDENCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getconfidence(resource,_model);
		if (this.CONFIDENCE == null)
			initCONFIDENCE();
		if (this.CONFIDENCE.contains(CONFIDENCE))
			return CONFIDENCE;
		this.CONFIDENCE.add(CONFIDENCE);
		_model.add(_model.createStatement(_resource,CONFIDENCEProperty,CONFIDENCE.resource()));
		return CONFIDENCE;
	}
	
	public void removeCONFIDENCE(fr.curie.BiNoM.pathways.biopax.confidence CONFIDENCE) throws JastorException {
		if (this.CONFIDENCE == null)
			initCONFIDENCE();
		if (!this.CONFIDENCE.contains(CONFIDENCE))
			return;
		if (!_model.contains(_resource, CONFIDENCEProperty, CONFIDENCE.resource()))
			return;
		this.CONFIDENCE.remove(CONFIDENCE);
		_model.removeAll(_resource, CONFIDENCEProperty, CONFIDENCE.resource());
	}
		 

	private void initXREF() throws JastorException {
		this.XREF = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, XREFProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#XREF properties in evidence model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.xref XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getxref(resource,_model);
				this.XREF.add(XREF);
			}
		}
	}

	public java.util.Iterator getXREF() throws JastorException {
		if (XREF == null)
			initXREF();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(XREF,_resource,XREFProperty,true);
	}

	public void addXREF(fr.curie.BiNoM.pathways.biopax.xref XREF) throws JastorException {
		if (this.XREF == null)
			initXREF();
		if (this.XREF.contains(XREF)) {
			this.XREF.remove(XREF);
			this.XREF.add(XREF);
			return;
		}
		this.XREF.add(XREF);
		_model.add(_model.createStatement(_resource,XREFProperty,XREF.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.xref addXREF() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.xref XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createxref(_model.createResource(),_model);
		if (this.XREF == null)
			initXREF();
		this.XREF.add(XREF);
		_model.add(_model.createStatement(_resource,XREFProperty,XREF.resource()));
		return XREF;
	}
	
	public fr.curie.BiNoM.pathways.biopax.xref addXREF(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.xref XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getxref(resource,_model);
		if (this.XREF == null)
			initXREF();
		if (this.XREF.contains(XREF))
			return XREF;
		this.XREF.add(XREF);
		_model.add(_model.createStatement(_resource,XREFProperty,XREF.resource()));
		return XREF;
	}
	
	public void removeXREF(fr.curie.BiNoM.pathways.biopax.xref XREF) throws JastorException {
		if (this.XREF == null)
			initXREF();
		if (!this.XREF.contains(XREF))
			return;
		if (!_model.contains(_resource, XREFProperty, XREF.resource()))
			return;
		this.XREF.remove(XREF);
		_model.removeAll(_resource, XREFProperty, XREF.resource());
	}
		  


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof evidenceListener))
			throw new IllegalArgumentException("ThingListener must be instance of evidenceListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((evidenceListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof evidenceListener))
			throw new IllegalArgumentException("ThingListener must be instance of evidenceListener"); 
		if (listeners == null)
			return;
		if (this.listeners.contains(listener)){
			listeners.remove(listener);
		}
	}



	
		public void addedStatement(com.hp.hpl.jena.rdf.model.Statement stmt) {

			if (stmt.getPredicate().equals(COMMENTProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (COMMENT == null) {
					try {
						initCOMMENT();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (!COMMENT.contains(obj))
					COMMENT.add(obj);
				java.util.ArrayList consumersForCOMMENT;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForCOMMENT = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForCOMMENT.iterator();iter.hasNext();){
						evidenceListener listener=(evidenceListener)iter.next();
						listener.COMMENTAdded(fr.curie.BiNoM.pathways.biopax.evidenceImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(EXPERIMENTAL_DASH_FORMProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.experimentalForm _EXPERIMENTAL_DASH_FORM = null;
					try {
						_EXPERIMENTAL_DASH_FORM = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getexperimentalForm(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (EXPERIMENTAL_DASH_FORM == null) {
						try {
							initEXPERIMENTAL_DASH_FORM();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!EXPERIMENTAL_DASH_FORM.contains(_EXPERIMENTAL_DASH_FORM))
						EXPERIMENTAL_DASH_FORM.add(_EXPERIMENTAL_DASH_FORM);
					if (listeners != null) {
						java.util.ArrayList consumersForEXPERIMENTAL_DASH_FORM;
						synchronized (listeners) {
							consumersForEXPERIMENTAL_DASH_FORM = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEXPERIMENTAL_DASH_FORM.iterator();iter.hasNext();){
							evidenceListener listener=(evidenceListener)iter.next();
							listener.EXPERIMENTAL_DASH_FORMAdded(fr.curie.BiNoM.pathways.biopax.evidenceImpl.this,_EXPERIMENTAL_DASH_FORM);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(EVIDENCE_DASH_CODEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.openControlledVocabulary _EVIDENCE_DASH_CODE = null;
					try {
						_EVIDENCE_DASH_CODE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (EVIDENCE_DASH_CODE == null) {
						try {
							initEVIDENCE_DASH_CODE();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!EVIDENCE_DASH_CODE.contains(_EVIDENCE_DASH_CODE))
						EVIDENCE_DASH_CODE.add(_EVIDENCE_DASH_CODE);
					if (listeners != null) {
						java.util.ArrayList consumersForEVIDENCE_DASH_CODE;
						synchronized (listeners) {
							consumersForEVIDENCE_DASH_CODE = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEVIDENCE_DASH_CODE.iterator();iter.hasNext();){
							evidenceListener listener=(evidenceListener)iter.next();
							listener.EVIDENCE_DASH_CODEAdded(fr.curie.BiNoM.pathways.biopax.evidenceImpl.this,_EVIDENCE_DASH_CODE);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(CONFIDENCEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.confidence _CONFIDENCE = null;
					try {
						_CONFIDENCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getconfidence(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (CONFIDENCE == null) {
						try {
							initCONFIDENCE();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!CONFIDENCE.contains(_CONFIDENCE))
						CONFIDENCE.add(_CONFIDENCE);
					if (listeners != null) {
						java.util.ArrayList consumersForCONFIDENCE;
						synchronized (listeners) {
							consumersForCONFIDENCE = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForCONFIDENCE.iterator();iter.hasNext();){
							evidenceListener listener=(evidenceListener)iter.next();
							listener.CONFIDENCEAdded(fr.curie.BiNoM.pathways.biopax.evidenceImpl.this,_CONFIDENCE);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(XREFProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.xref _XREF = null;
					try {
						_XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getxref(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (XREF == null) {
						try {
							initXREF();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!XREF.contains(_XREF))
						XREF.add(_XREF);
					if (listeners != null) {
						java.util.ArrayList consumersForXREF;
						synchronized (listeners) {
							consumersForXREF = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForXREF.iterator();iter.hasNext();){
							evidenceListener listener=(evidenceListener)iter.next();
							listener.XREFAdded(fr.curie.BiNoM.pathways.biopax.evidenceImpl.this,_XREF);
						}
					}
				}
				return;
			}
		}
		
		public void removedStatement(com.hp.hpl.jena.rdf.model.Statement stmt) {
//			if (!stmt.getSubject().equals(_resource))
//				return;
			if (stmt.getPredicate().equals(COMMENTProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (COMMENT != null) {
					if (COMMENT.contains(obj))
						COMMENT.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						evidenceListener listener=(evidenceListener)iter.next();
						listener.COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.evidenceImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(EXPERIMENTAL_DASH_FORMProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.experimentalForm _EXPERIMENTAL_DASH_FORM = null;
					if (EXPERIMENTAL_DASH_FORM != null) {
						boolean found = false;
						for (int i=0;i<EXPERIMENTAL_DASH_FORM.size();i++) {
							fr.curie.BiNoM.pathways.biopax.experimentalForm __item = (fr.curie.BiNoM.pathways.biopax.experimentalForm) EXPERIMENTAL_DASH_FORM.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_EXPERIMENTAL_DASH_FORM = __item;
								break;
							}
						}
						if (found)
							EXPERIMENTAL_DASH_FORM.remove(_EXPERIMENTAL_DASH_FORM);
						else {
							try {
								_EXPERIMENTAL_DASH_FORM = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getexperimentalForm(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_EXPERIMENTAL_DASH_FORM = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getexperimentalForm(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForEXPERIMENTAL_DASH_FORM;
						synchronized (listeners) {
							consumersForEXPERIMENTAL_DASH_FORM = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEXPERIMENTAL_DASH_FORM.iterator();iter.hasNext();){
							evidenceListener listener=(evidenceListener)iter.next();
							listener.EXPERIMENTAL_DASH_FORMRemoved(fr.curie.BiNoM.pathways.biopax.evidenceImpl.this,_EXPERIMENTAL_DASH_FORM);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(EVIDENCE_DASH_CODEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.openControlledVocabulary _EVIDENCE_DASH_CODE = null;
					if (EVIDENCE_DASH_CODE != null) {
						boolean found = false;
						for (int i=0;i<EVIDENCE_DASH_CODE.size();i++) {
							fr.curie.BiNoM.pathways.biopax.openControlledVocabulary __item = (fr.curie.BiNoM.pathways.biopax.openControlledVocabulary) EVIDENCE_DASH_CODE.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_EVIDENCE_DASH_CODE = __item;
								break;
							}
						}
						if (found)
							EVIDENCE_DASH_CODE.remove(_EVIDENCE_DASH_CODE);
						else {
							try {
								_EVIDENCE_DASH_CODE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_EVIDENCE_DASH_CODE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForEVIDENCE_DASH_CODE;
						synchronized (listeners) {
							consumersForEVIDENCE_DASH_CODE = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEVIDENCE_DASH_CODE.iterator();iter.hasNext();){
							evidenceListener listener=(evidenceListener)iter.next();
							listener.EVIDENCE_DASH_CODERemoved(fr.curie.BiNoM.pathways.biopax.evidenceImpl.this,_EVIDENCE_DASH_CODE);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(CONFIDENCEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.confidence _CONFIDENCE = null;
					if (CONFIDENCE != null) {
						boolean found = false;
						for (int i=0;i<CONFIDENCE.size();i++) {
							fr.curie.BiNoM.pathways.biopax.confidence __item = (fr.curie.BiNoM.pathways.biopax.confidence) CONFIDENCE.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_CONFIDENCE = __item;
								break;
							}
						}
						if (found)
							CONFIDENCE.remove(_CONFIDENCE);
						else {
							try {
								_CONFIDENCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getconfidence(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_CONFIDENCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getconfidence(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForCONFIDENCE;
						synchronized (listeners) {
							consumersForCONFIDENCE = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForCONFIDENCE.iterator();iter.hasNext();){
							evidenceListener listener=(evidenceListener)iter.next();
							listener.CONFIDENCERemoved(fr.curie.BiNoM.pathways.biopax.evidenceImpl.this,_CONFIDENCE);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(XREFProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.xref _XREF = null;
					if (XREF != null) {
						boolean found = false;
						for (int i=0;i<XREF.size();i++) {
							fr.curie.BiNoM.pathways.biopax.xref __item = (fr.curie.BiNoM.pathways.biopax.xref) XREF.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_XREF = __item;
								break;
							}
						}
						if (found)
							XREF.remove(_XREF);
						else {
							try {
								_XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getxref(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getxref(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForXREF;
						synchronized (listeners) {
							consumersForXREF = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForXREF.iterator();iter.hasNext();){
							evidenceListener listener=(evidenceListener)iter.next();
							listener.XREFRemoved(fr.curie.BiNoM.pathways.biopax.evidenceImpl.this,_XREF);
						}
					}
				}
				return;
			}
		}

	//}
	


}