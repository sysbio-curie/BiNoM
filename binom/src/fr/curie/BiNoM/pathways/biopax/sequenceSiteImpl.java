

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.sequenceSite}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#sequenceSite)</p>
 * <br>
 */
public class sequenceSiteImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.sequenceSite {
	

	private static com.hp.hpl.jena.rdf.model.Property COMMENTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#COMMENT");
	private java.util.ArrayList COMMENT;
	private static com.hp.hpl.jena.rdf.model.Property POSITION_DASH_STATUSProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#POSITION-STATUS");
	private java.lang.String POSITION_DASH_STATUS;
	private static com.hp.hpl.jena.rdf.model.Property SEQUENCE_DASH_POSITIONProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SEQUENCE-POSITION");
	private com.hp.hpl.jena.datatypes.xsd.impl.XSDBaseNumericType SEQUENCE_DASH_POSITION;

	sequenceSiteImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static sequenceSiteImpl getsequenceSite(Resource resource, Model model) throws JastorException {
		return new sequenceSiteImpl(resource, model);
	}
	    
	static sequenceSiteImpl createsequenceSite(Resource resource, Model model) throws JastorException {
		sequenceSiteImpl impl = new sequenceSiteImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, sequenceSite.TYPE)))
			impl._model.add(impl._resource, RDF.type, sequenceSite.TYPE);
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
		it = _model.listStatements(_resource,POSITION_DASH_STATUSProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,SEQUENCE_DASH_POSITIONProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.sequenceSite.TYPE);
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
		POSITION_DASH_STATUS = null;
		SEQUENCE_DASH_POSITION = null;
	}


	private void initCOMMENT() throws JastorException {
		COMMENT = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, COMMENTProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#COMMENT properties in sequenceSite model not a Literal", stmt.getObject());
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

	public java.lang.String getPOSITION_DASH_STATUS() throws JastorException {
		if (POSITION_DASH_STATUS != null)
			return POSITION_DASH_STATUS;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, POSITION_DASH_STATUSProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": POSITION_DASH_STATUS getProperty() in fr.curie.BiNoM.pathways.biopax.sequenceSite model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		POSITION_DASH_STATUS = (java.lang.String)obj;
		return POSITION_DASH_STATUS;
	}
	
	public void setPOSITION_DASH_STATUS(java.lang.String POSITION_DASH_STATUS) throws JastorException {
		if (_model.contains(_resource,POSITION_DASH_STATUSProperty)) {
			_model.removeAll(_resource,POSITION_DASH_STATUSProperty,null);
		}
		this.POSITION_DASH_STATUS = POSITION_DASH_STATUS;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (POSITION_DASH_STATUS != null) {
			_model.add(_model.createStatement(_resource,POSITION_DASH_STATUSProperty, _model.createTypedLiteral(POSITION_DASH_STATUS)));
		}	
	}

	public com.hp.hpl.jena.datatypes.xsd.impl.XSDBaseNumericType getSEQUENCE_DASH_POSITION() throws JastorException {
		if (SEQUENCE_DASH_POSITION != null)
			return SEQUENCE_DASH_POSITION;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, SEQUENCE_DASH_POSITIONProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": SEQUENCE_DASH_POSITION getProperty() in fr.curie.BiNoM.pathways.biopax.sequenceSite model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"com.hp.hpl.jena.datatypes.xsd.impl.XSDBaseNumericType");
		SEQUENCE_DASH_POSITION = (com.hp.hpl.jena.datatypes.xsd.impl.XSDBaseNumericType)obj;
		return SEQUENCE_DASH_POSITION;
	}
	
	public void setSEQUENCE_DASH_POSITION(com.hp.hpl.jena.datatypes.xsd.impl.XSDBaseNumericType SEQUENCE_DASH_POSITION) throws JastorException {
		if (_model.contains(_resource,SEQUENCE_DASH_POSITIONProperty)) {
			_model.removeAll(_resource,SEQUENCE_DASH_POSITIONProperty,null);
		}
		this.SEQUENCE_DASH_POSITION = SEQUENCE_DASH_POSITION;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (SEQUENCE_DASH_POSITION != null) {
			_model.add(_model.createStatement(_resource,SEQUENCE_DASH_POSITIONProperty, _model.createTypedLiteral(SEQUENCE_DASH_POSITION)));
		}	
	}
 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof sequenceSiteListener))
			throw new IllegalArgumentException("ThingListener must be instance of sequenceSiteListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((sequenceSiteListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof sequenceSiteListener))
			throw new IllegalArgumentException("ThingListener must be instance of sequenceSiteListener"); 
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
						sequenceSiteListener listener=(sequenceSiteListener)iter.next();
						listener.COMMENTAdded(fr.curie.BiNoM.pathways.biopax.sequenceSiteImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(POSITION_DASH_STATUSProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				POSITION_DASH_STATUS = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						sequenceSiteListener listener=(sequenceSiteListener)iter.next();
						listener.POSITION_DASH_STATUSChanged(fr.curie.BiNoM.pathways.biopax.sequenceSiteImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(SEQUENCE_DASH_POSITIONProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				SEQUENCE_DASH_POSITION = (com.hp.hpl.jena.datatypes.xsd.impl.XSDBaseNumericType)Util.fixLiteral(literal.getValue(),"com.hp.hpl.jena.datatypes.xsd.impl.XSDBaseNumericType");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						sequenceSiteListener listener=(sequenceSiteListener)iter.next();
						listener.SEQUENCE_DASH_POSITIONChanged(fr.curie.BiNoM.pathways.biopax.sequenceSiteImpl.this);
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
						sequenceSiteListener listener=(sequenceSiteListener)iter.next();
						listener.COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.sequenceSiteImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(POSITION_DASH_STATUSProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (POSITION_DASH_STATUS != null && POSITION_DASH_STATUS.equals(obj))
					POSITION_DASH_STATUS = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						sequenceSiteListener listener=(sequenceSiteListener)iter.next();
						listener.POSITION_DASH_STATUSChanged(fr.curie.BiNoM.pathways.biopax.sequenceSiteImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(SEQUENCE_DASH_POSITIONProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"com.hp.hpl.jena.datatypes.xsd.impl.XSDBaseNumericType");
				if (SEQUENCE_DASH_POSITION != null && SEQUENCE_DASH_POSITION.equals(obj))
					SEQUENCE_DASH_POSITION = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						sequenceSiteListener listener=(sequenceSiteListener)iter.next();
						listener.SEQUENCE_DASH_POSITIONChanged(fr.curie.BiNoM.pathways.biopax.sequenceSiteImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}