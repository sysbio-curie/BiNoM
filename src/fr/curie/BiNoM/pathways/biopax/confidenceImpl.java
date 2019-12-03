

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.confidence}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#confidence)</p>
 * <br>
 */
public class confidenceImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.confidence {
	

	private static com.hp.hpl.jena.rdf.model.Property COMMENTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#COMMENT");
	private java.util.ArrayList COMMENT;
	private static com.hp.hpl.jena.rdf.model.Property CONFIDENCE_DASH_VALUEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#CONFIDENCE-VALUE");
	private java.lang.String CONFIDENCE_DASH_VALUE;
	private static com.hp.hpl.jena.rdf.model.Property XREFProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#XREF");
	private java.util.ArrayList XREF;
	private java.util.ArrayList XREF_aspublicationXref;

	confidenceImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static confidenceImpl getconfidence(Resource resource, Model model) throws JastorException {
		return new confidenceImpl(resource, model);
	}
	    
	static confidenceImpl createconfidence(Resource resource, Model model) throws JastorException {
		confidenceImpl impl = new confidenceImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, confidence.TYPE)))
			impl._model.add(impl._resource, RDF.type, confidence.TYPE);
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
		it = _model.listStatements(_resource,CONFIDENCE_DASH_VALUEProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,XREFProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.confidence.TYPE);
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
		CONFIDENCE_DASH_VALUE = null;
		XREF = null;
		XREF_aspublicationXref = null;
	}


	private void initCOMMENT() throws JastorException {
		COMMENT = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, COMMENTProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#COMMENT properties in confidence model not a Literal", stmt.getObject());
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

	public java.lang.String getCONFIDENCE_DASH_VALUE() throws JastorException {
		if (CONFIDENCE_DASH_VALUE != null)
			return CONFIDENCE_DASH_VALUE;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, CONFIDENCE_DASH_VALUEProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": CONFIDENCE_DASH_VALUE getProperty() in fr.curie.BiNoM.pathways.biopax.confidence model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		CONFIDENCE_DASH_VALUE = (java.lang.String)obj;
		return CONFIDENCE_DASH_VALUE;
	}
	
	public void setCONFIDENCE_DASH_VALUE(java.lang.String CONFIDENCE_DASH_VALUE) throws JastorException {
		if (_model.contains(_resource,CONFIDENCE_DASH_VALUEProperty)) {
			_model.removeAll(_resource,CONFIDENCE_DASH_VALUEProperty,null);
		}
		this.CONFIDENCE_DASH_VALUE = CONFIDENCE_DASH_VALUE;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (CONFIDENCE_DASH_VALUE != null) {
			_model.add(_model.createStatement(_resource,CONFIDENCE_DASH_VALUEProperty, _model.createTypedLiteral(CONFIDENCE_DASH_VALUE)));
		}	
	}


	private void initXREF() throws JastorException {
		this.XREF = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, XREFProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#XREF properties in confidence model not a Resource", stmt.getObject());
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
		
	private void initXREF_aspublicationXref() throws JastorException {
		this.XREF_aspublicationXref = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, XREFProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#XREF properties in confidence model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.publicationXref.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.publicationXref XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpublicationXref(resource,_model);
				this.XREF_aspublicationXref.add(XREF);
			}
		}
	}

	public java.util.Iterator getXREF_aspublicationXref() throws JastorException {
		if (XREF_aspublicationXref == null)
			initXREF_aspublicationXref();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(XREF_aspublicationXref,_resource,XREFProperty,true);
	}

	public void addXREF(fr.curie.BiNoM.pathways.biopax.publicationXref XREF) throws JastorException {
		if (this.XREF_aspublicationXref == null)
			initXREF_aspublicationXref();
		if (this.XREF_aspublicationXref.contains(XREF)) {
			this.XREF_aspublicationXref.remove(XREF);
			this.XREF_aspublicationXref.add(XREF);
			return;
		}
		this.XREF_aspublicationXref.add(XREF);
		_model.add(_model.createStatement(_resource,XREFProperty,XREF.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.publicationXref addXREF_aspublicationXref() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.publicationXref XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createpublicationXref(_model.createResource(),_model);
		if (this.XREF_aspublicationXref == null)
			initXREF_aspublicationXref();
		this.XREF_aspublicationXref.add(XREF);
		_model.add(_model.createStatement(_resource,XREFProperty,XREF.resource()));
		return XREF;
	}
	
	public fr.curie.BiNoM.pathways.biopax.publicationXref addXREF_aspublicationXref(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.publicationXref XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpublicationXref(resource,_model);
		if (this.XREF_aspublicationXref == null)
			initXREF_aspublicationXref();
		if (this.XREF_aspublicationXref.contains(XREF))
			return XREF;
		this.XREF_aspublicationXref.add(XREF);
		_model.add(_model.createStatement(_resource,XREFProperty,XREF.resource()));
		return XREF;
	}
	
	public void removeXREF(fr.curie.BiNoM.pathways.biopax.publicationXref XREF) throws JastorException {
		if (this.XREF_aspublicationXref == null)
			initXREF_aspublicationXref();
		if (!this.XREF_aspublicationXref.contains(XREF))
			return;
		if (!_model.contains(_resource, XREFProperty, XREF.resource()))
			return;
		this.XREF_aspublicationXref.remove(XREF);
		_model.removeAll(_resource, XREFProperty, XREF.resource());
	}
		  


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof confidenceListener))
			throw new IllegalArgumentException("ThingListener must be instance of confidenceListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((confidenceListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof confidenceListener))
			throw new IllegalArgumentException("ThingListener must be instance of confidenceListener"); 
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
						confidenceListener listener=(confidenceListener)iter.next();
						listener.COMMENTAdded(fr.curie.BiNoM.pathways.biopax.confidenceImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(CONFIDENCE_DASH_VALUEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				CONFIDENCE_DASH_VALUE = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						confidenceListener listener=(confidenceListener)iter.next();
						listener.CONFIDENCE_DASH_VALUEChanged(fr.curie.BiNoM.pathways.biopax.confidenceImpl.this);
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
							confidenceListener listener=(confidenceListener)iter.next();
							listener.XREFAdded(fr.curie.BiNoM.pathways.biopax.confidenceImpl.this,_XREF);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.publicationXref.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.publicationXref _XREF_aspublicationXref = null;
					try {
						_XREF_aspublicationXref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpublicationXref(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (XREF_aspublicationXref == null) {
						try {
							initXREF_aspublicationXref();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!XREF_aspublicationXref.contains(_XREF_aspublicationXref))
						XREF_aspublicationXref.add(_XREF_aspublicationXref);
					if (listeners != null) {
						java.util.ArrayList consumersForXREF_aspublicationXref;
						synchronized (listeners) {
							consumersForXREF_aspublicationXref = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForXREF_aspublicationXref.iterator();iter.hasNext();){
							confidenceListener listener=(confidenceListener)iter.next();
							listener.XREFAdded(fr.curie.BiNoM.pathways.biopax.confidenceImpl.this,_XREF_aspublicationXref);
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
						confidenceListener listener=(confidenceListener)iter.next();
						listener.COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.confidenceImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(CONFIDENCE_DASH_VALUEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (CONFIDENCE_DASH_VALUE != null && CONFIDENCE_DASH_VALUE.equals(obj))
					CONFIDENCE_DASH_VALUE = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						confidenceListener listener=(confidenceListener)iter.next();
						listener.CONFIDENCE_DASH_VALUEChanged(fr.curie.BiNoM.pathways.biopax.confidenceImpl.this);
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
							confidenceListener listener=(confidenceListener)iter.next();
							listener.XREFRemoved(fr.curie.BiNoM.pathways.biopax.confidenceImpl.this,_XREF);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.publicationXref.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.publicationXref _XREF_aspublicationXref = null;
					if (XREF_aspublicationXref != null) {
						boolean found = false;
						for (int i=0;i<XREF_aspublicationXref.size();i++) {
							fr.curie.BiNoM.pathways.biopax.publicationXref __item = (fr.curie.BiNoM.pathways.biopax.publicationXref) XREF_aspublicationXref.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_XREF_aspublicationXref = __item;
								break;
							}
						}
						if (found)
							XREF_aspublicationXref.remove(_XREF_aspublicationXref);
						else {
							try {
								_XREF_aspublicationXref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpublicationXref(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_XREF_aspublicationXref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpublicationXref(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForXREF_aspublicationXref;
						synchronized (listeners) {
							consumersForXREF_aspublicationXref = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForXREF_aspublicationXref.iterator();iter.hasNext();){
							confidenceListener listener=(confidenceListener)iter.next();
							listener.XREFRemoved(fr.curie.BiNoM.pathways.biopax.confidenceImpl.this,_XREF_aspublicationXref);
						}
					}
				}
				return;
			}
		}

	//}
	


}