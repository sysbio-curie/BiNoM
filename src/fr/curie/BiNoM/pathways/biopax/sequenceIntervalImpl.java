

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.sequenceInterval}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#sequenceInterval)</p>
 * <br>
 */
public class sequenceIntervalImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.sequenceInterval {
	

	private static com.hp.hpl.jena.rdf.model.Property COMMENTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#COMMENT");
	private java.util.ArrayList COMMENT;
	private static com.hp.hpl.jena.rdf.model.Property SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SEQUENCE-INTERVAL-BEGIN");
	private fr.curie.BiNoM.pathways.biopax.sequenceSite SEQUENCE_DASH_INTERVAL_DASH_BEGIN;
	private static com.hp.hpl.jena.rdf.model.Property SEQUENCE_DASH_INTERVAL_DASH_ENDProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SEQUENCE-INTERVAL-END");
	private fr.curie.BiNoM.pathways.biopax.sequenceSite SEQUENCE_DASH_INTERVAL_DASH_END;

	sequenceIntervalImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static sequenceIntervalImpl getsequenceInterval(Resource resource, Model model) throws JastorException {
		return new sequenceIntervalImpl(resource, model);
	}
	    
	static sequenceIntervalImpl createsequenceInterval(Resource resource, Model model) throws JastorException {
		sequenceIntervalImpl impl = new sequenceIntervalImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, sequenceInterval.TYPE)))
			impl._model.add(impl._resource, RDF.type, sequenceInterval.TYPE);
		//impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.utilityClass.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.utilityClass.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.sequenceLocation.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.sequenceLocation.TYPE));     
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
		it = _model.listStatements(_resource,SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,SEQUENCE_DASH_INTERVAL_DASH_ENDProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.sequenceInterval.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.utilityClass.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.sequenceLocation.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
	
	public void clearCache() {
		COMMENT = null;
		SEQUENCE_DASH_INTERVAL_DASH_BEGIN = null;
		SEQUENCE_DASH_INTERVAL_DASH_END = null;
	}


	private void initCOMMENT() throws JastorException {
		COMMENT = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, COMMENTProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#COMMENT properties in sequenceInterval model not a Literal", stmt.getObject());
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


	public fr.curie.BiNoM.pathways.biopax.sequenceSite getSEQUENCE_DASH_INTERVAL_DASH_BEGIN() throws JastorException {
		if (SEQUENCE_DASH_INTERVAL_DASH_BEGIN != null)
			return SEQUENCE_DASH_INTERVAL_DASH_BEGIN;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": SEQUENCE_DASH_INTERVAL_DASH_BEGIN getProperty() in fr.curie.BiNoM.pathways.biopax.sequenceInterval model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		SEQUENCE_DASH_INTERVAL_DASH_BEGIN = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getsequenceSite(resource,_model);
		return SEQUENCE_DASH_INTERVAL_DASH_BEGIN;
	}

	public void setSEQUENCE_DASH_INTERVAL_DASH_BEGIN(fr.curie.BiNoM.pathways.biopax.sequenceSite SEQUENCE_DASH_INTERVAL_DASH_BEGIN) throws JastorException {
		if (_model.contains(_resource,SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty)) {
			_model.removeAll(_resource,SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty,null);
		}
		this.SEQUENCE_DASH_INTERVAL_DASH_BEGIN = SEQUENCE_DASH_INTERVAL_DASH_BEGIN;
		if (SEQUENCE_DASH_INTERVAL_DASH_BEGIN != null) {
			_model.add(_model.createStatement(_resource,SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty, SEQUENCE_DASH_INTERVAL_DASH_BEGIN.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.sequenceSite setSEQUENCE_DASH_INTERVAL_DASH_BEGIN() throws JastorException {
		if (_model.contains(_resource,SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty)) {
			_model.removeAll(_resource,SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.sequenceSite SEQUENCE_DASH_INTERVAL_DASH_BEGIN = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createsequenceSite(_model.createResource(),_model);
		this.SEQUENCE_DASH_INTERVAL_DASH_BEGIN = SEQUENCE_DASH_INTERVAL_DASH_BEGIN;
		_model.add(_model.createStatement(_resource,SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty, SEQUENCE_DASH_INTERVAL_DASH_BEGIN.resource()));
		return SEQUENCE_DASH_INTERVAL_DASH_BEGIN;
	}
	
	public fr.curie.BiNoM.pathways.biopax.sequenceSite setSEQUENCE_DASH_INTERVAL_DASH_BEGIN(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty)) {
			_model.removeAll(_resource,SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.sequenceSite SEQUENCE_DASH_INTERVAL_DASH_BEGIN = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getsequenceSite(resource,_model);
		this.SEQUENCE_DASH_INTERVAL_DASH_BEGIN = SEQUENCE_DASH_INTERVAL_DASH_BEGIN;
		_model.add(_model.createStatement(_resource,SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty, SEQUENCE_DASH_INTERVAL_DASH_BEGIN.resource()));
		return SEQUENCE_DASH_INTERVAL_DASH_BEGIN;
	}
	

	public fr.curie.BiNoM.pathways.biopax.sequenceSite getSEQUENCE_DASH_INTERVAL_DASH_END() throws JastorException {
		if (SEQUENCE_DASH_INTERVAL_DASH_END != null)
			return SEQUENCE_DASH_INTERVAL_DASH_END;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, SEQUENCE_DASH_INTERVAL_DASH_ENDProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": SEQUENCE_DASH_INTERVAL_DASH_END getProperty() in fr.curie.BiNoM.pathways.biopax.sequenceInterval model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		SEQUENCE_DASH_INTERVAL_DASH_END = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getsequenceSite(resource,_model);
		return SEQUENCE_DASH_INTERVAL_DASH_END;
	}

	public void setSEQUENCE_DASH_INTERVAL_DASH_END(fr.curie.BiNoM.pathways.biopax.sequenceSite SEQUENCE_DASH_INTERVAL_DASH_END) throws JastorException {
		if (_model.contains(_resource,SEQUENCE_DASH_INTERVAL_DASH_ENDProperty)) {
			_model.removeAll(_resource,SEQUENCE_DASH_INTERVAL_DASH_ENDProperty,null);
		}
		this.SEQUENCE_DASH_INTERVAL_DASH_END = SEQUENCE_DASH_INTERVAL_DASH_END;
		if (SEQUENCE_DASH_INTERVAL_DASH_END != null) {
			_model.add(_model.createStatement(_resource,SEQUENCE_DASH_INTERVAL_DASH_ENDProperty, SEQUENCE_DASH_INTERVAL_DASH_END.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.sequenceSite setSEQUENCE_DASH_INTERVAL_DASH_END() throws JastorException {
		if (_model.contains(_resource,SEQUENCE_DASH_INTERVAL_DASH_ENDProperty)) {
			_model.removeAll(_resource,SEQUENCE_DASH_INTERVAL_DASH_ENDProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.sequenceSite SEQUENCE_DASH_INTERVAL_DASH_END = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createsequenceSite(_model.createResource(),_model);
		this.SEQUENCE_DASH_INTERVAL_DASH_END = SEQUENCE_DASH_INTERVAL_DASH_END;
		_model.add(_model.createStatement(_resource,SEQUENCE_DASH_INTERVAL_DASH_ENDProperty, SEQUENCE_DASH_INTERVAL_DASH_END.resource()));
		return SEQUENCE_DASH_INTERVAL_DASH_END;
	}
	
	public fr.curie.BiNoM.pathways.biopax.sequenceSite setSEQUENCE_DASH_INTERVAL_DASH_END(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,SEQUENCE_DASH_INTERVAL_DASH_ENDProperty)) {
			_model.removeAll(_resource,SEQUENCE_DASH_INTERVAL_DASH_ENDProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.sequenceSite SEQUENCE_DASH_INTERVAL_DASH_END = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getsequenceSite(resource,_model);
		this.SEQUENCE_DASH_INTERVAL_DASH_END = SEQUENCE_DASH_INTERVAL_DASH_END;
		_model.add(_model.createStatement(_resource,SEQUENCE_DASH_INTERVAL_DASH_ENDProperty, SEQUENCE_DASH_INTERVAL_DASH_END.resource()));
		return SEQUENCE_DASH_INTERVAL_DASH_END;
	}
	 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof sequenceIntervalListener))
			throw new IllegalArgumentException("ThingListener must be instance of sequenceIntervalListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((sequenceIntervalListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof sequenceIntervalListener))
			throw new IllegalArgumentException("ThingListener must be instance of sequenceIntervalListener"); 
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
						sequenceIntervalListener listener=(sequenceIntervalListener)iter.next();
						listener.COMMENTAdded(fr.curie.BiNoM.pathways.biopax.sequenceIntervalImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				SEQUENCE_DASH_INTERVAL_DASH_BEGIN = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						SEQUENCE_DASH_INTERVAL_DASH_BEGIN = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getsequenceSite(resource,_model);
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
						sequenceIntervalListener listener=(sequenceIntervalListener)iter.next();
						listener.SEQUENCE_DASH_INTERVAL_DASH_BEGINChanged(fr.curie.BiNoM.pathways.biopax.sequenceIntervalImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(SEQUENCE_DASH_INTERVAL_DASH_ENDProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				SEQUENCE_DASH_INTERVAL_DASH_END = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						SEQUENCE_DASH_INTERVAL_DASH_END = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getsequenceSite(resource,_model);
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
						sequenceIntervalListener listener=(sequenceIntervalListener)iter.next();
						listener.SEQUENCE_DASH_INTERVAL_DASH_ENDChanged(fr.curie.BiNoM.pathways.biopax.sequenceIntervalImpl.this);
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
						sequenceIntervalListener listener=(sequenceIntervalListener)iter.next();
						listener.COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.sequenceIntervalImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(SEQUENCE_DASH_INTERVAL_DASH_BEGINProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (SEQUENCE_DASH_INTERVAL_DASH_BEGIN != null && SEQUENCE_DASH_INTERVAL_DASH_BEGIN.resource().equals(resource))
						SEQUENCE_DASH_INTERVAL_DASH_BEGIN = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						sequenceIntervalListener listener=(sequenceIntervalListener)iter.next();
						listener.SEQUENCE_DASH_INTERVAL_DASH_BEGINChanged(fr.curie.BiNoM.pathways.biopax.sequenceIntervalImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(SEQUENCE_DASH_INTERVAL_DASH_ENDProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (SEQUENCE_DASH_INTERVAL_DASH_END != null && SEQUENCE_DASH_INTERVAL_DASH_END.resource().equals(resource))
						SEQUENCE_DASH_INTERVAL_DASH_END = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						sequenceIntervalListener listener=(sequenceIntervalListener)iter.next();
						listener.SEQUENCE_DASH_INTERVAL_DASH_ENDChanged(fr.curie.BiNoM.pathways.biopax.sequenceIntervalImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}