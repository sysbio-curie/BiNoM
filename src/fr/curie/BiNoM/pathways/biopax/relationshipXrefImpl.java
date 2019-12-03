

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.relationshipXref}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#relationshipXref)</p>
 * <br>
 */
public class relationshipXrefImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.relationshipXref {
	

	private static com.hp.hpl.jena.rdf.model.Property COMMENTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#COMMENT");
	private java.util.ArrayList COMMENT;
	private static com.hp.hpl.jena.rdf.model.Property ID_DASH_VERSIONProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#ID-VERSION");
	private java.lang.String ID_DASH_VERSION;
	private static com.hp.hpl.jena.rdf.model.Property DB_DASH_VERSIONProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#DB-VERSION");
	private java.lang.String DB_DASH_VERSION;
	private static com.hp.hpl.jena.rdf.model.Property IDProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#ID");
	private java.lang.String ID;
	private static com.hp.hpl.jena.rdf.model.Property DBProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#DB");
	private java.lang.String DB;
	private static com.hp.hpl.jena.rdf.model.Property RELATIONSHIP_DASH_TYPEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#RELATIONSHIP-TYPE");
	private java.lang.String RELATIONSHIP_DASH_TYPE;

	relationshipXrefImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static relationshipXrefImpl getrelationshipXref(Resource resource, Model model) throws JastorException {
		return new relationshipXrefImpl(resource, model);
	}
	    
	static relationshipXrefImpl createrelationshipXref(Resource resource, Model model) throws JastorException {
		relationshipXrefImpl impl = new relationshipXrefImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, relationshipXref.TYPE)))
			impl._model.add(impl._resource, RDF.type, relationshipXref.TYPE);
		//impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.utilityClass.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.utilityClass.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClass.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClass.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.xref.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.xref.TYPE));     
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
		it = _model.listStatements(_resource,ID_DASH_VERSIONProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,DB_DASH_VERSIONProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,IDProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,DBProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RELATIONSHIP_DASH_TYPEProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.relationshipXref.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.utilityClass.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClass.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.xref.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
	
	public void clearCache() {
		COMMENT = null;
		ID_DASH_VERSION = null;
		DB_DASH_VERSION = null;
		ID = null;
		DB = null;
		RELATIONSHIP_DASH_TYPE = null;
	}


	private void initCOMMENT() throws JastorException {
		COMMENT = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, COMMENTProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#COMMENT properties in relationshipXref model not a Literal", stmt.getObject());
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

	public java.lang.String getID_DASH_VERSION() throws JastorException {
		if (ID_DASH_VERSION != null)
			return ID_DASH_VERSION;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, ID_DASH_VERSIONProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": ID_DASH_VERSION getProperty() in fr.curie.BiNoM.pathways.biopax.relationshipXref model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		ID_DASH_VERSION = (java.lang.String)obj;
		return ID_DASH_VERSION;
	}
	
	public void setID_DASH_VERSION(java.lang.String ID_DASH_VERSION) throws JastorException {
		if (_model.contains(_resource,ID_DASH_VERSIONProperty)) {
			_model.removeAll(_resource,ID_DASH_VERSIONProperty,null);
		}
		this.ID_DASH_VERSION = ID_DASH_VERSION;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (ID_DASH_VERSION != null) {
			_model.add(_model.createStatement(_resource,ID_DASH_VERSIONProperty, _model.createTypedLiteral(ID_DASH_VERSION)));
		}	
	}

	public java.lang.String getDB_DASH_VERSION() throws JastorException {
		if (DB_DASH_VERSION != null)
			return DB_DASH_VERSION;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, DB_DASH_VERSIONProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": DB_DASH_VERSION getProperty() in fr.curie.BiNoM.pathways.biopax.relationshipXref model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		DB_DASH_VERSION = (java.lang.String)obj;
		return DB_DASH_VERSION;
	}
	
	public void setDB_DASH_VERSION(java.lang.String DB_DASH_VERSION) throws JastorException {
		if (_model.contains(_resource,DB_DASH_VERSIONProperty)) {
			_model.removeAll(_resource,DB_DASH_VERSIONProperty,null);
		}
		this.DB_DASH_VERSION = DB_DASH_VERSION;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (DB_DASH_VERSION != null) {
			_model.add(_model.createStatement(_resource,DB_DASH_VERSIONProperty, _model.createTypedLiteral(DB_DASH_VERSION)));
		}	
	}

	public java.lang.String getID() throws JastorException {
		if (ID != null)
			return ID;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, IDProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": ID getProperty() in fr.curie.BiNoM.pathways.biopax.relationshipXref model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		ID = (java.lang.String)obj;
		return ID;
	}
	
	public void setID(java.lang.String ID) throws JastorException {
		if (_model.contains(_resource,IDProperty)) {
			_model.removeAll(_resource,IDProperty,null);
		}
		this.ID = ID;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (ID != null) {
			_model.add(_model.createStatement(_resource,IDProperty, _model.createTypedLiteral(ID)));
		}	
	}

	public java.lang.String getDB() throws JastorException {
		if (DB != null)
			return DB;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, DBProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": DB getProperty() in fr.curie.BiNoM.pathways.biopax.relationshipXref model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		DB = (java.lang.String)obj;
		return DB;
	}
	
	public void setDB(java.lang.String DB) throws JastorException {
		if (_model.contains(_resource,DBProperty)) {
			_model.removeAll(_resource,DBProperty,null);
		}
		this.DB = DB;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (DB != null) {
			_model.add(_model.createStatement(_resource,DBProperty, _model.createTypedLiteral(DB)));
		}	
	}

	public java.lang.String getRELATIONSHIP_DASH_TYPE() throws JastorException {
		if (RELATIONSHIP_DASH_TYPE != null)
			return RELATIONSHIP_DASH_TYPE;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, RELATIONSHIP_DASH_TYPEProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": RELATIONSHIP_DASH_TYPE getProperty() in fr.curie.BiNoM.pathways.biopax.relationshipXref model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		RELATIONSHIP_DASH_TYPE = (java.lang.String)obj;
		return RELATIONSHIP_DASH_TYPE;
	}
	
	public void setRELATIONSHIP_DASH_TYPE(java.lang.String RELATIONSHIP_DASH_TYPE) throws JastorException {
		if (_model.contains(_resource,RELATIONSHIP_DASH_TYPEProperty)) {
			_model.removeAll(_resource,RELATIONSHIP_DASH_TYPEProperty,null);
		}
		this.RELATIONSHIP_DASH_TYPE = RELATIONSHIP_DASH_TYPE;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (RELATIONSHIP_DASH_TYPE != null) {
			_model.add(_model.createStatement(_resource,RELATIONSHIP_DASH_TYPEProperty, _model.createTypedLiteral(RELATIONSHIP_DASH_TYPE)));
		}	
	}
 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof relationshipXrefListener))
			throw new IllegalArgumentException("ThingListener must be instance of relationshipXrefListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((relationshipXrefListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof relationshipXrefListener))
			throw new IllegalArgumentException("ThingListener must be instance of relationshipXrefListener"); 
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
						relationshipXrefListener listener=(relationshipXrefListener)iter.next();
						listener.COMMENTAdded(fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ID_DASH_VERSIONProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				ID_DASH_VERSION = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						relationshipXrefListener listener=(relationshipXrefListener)iter.next();
						listener.ID_DASH_VERSIONChanged(fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(DB_DASH_VERSIONProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				DB_DASH_VERSION = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						relationshipXrefListener listener=(relationshipXrefListener)iter.next();
						listener.DB_DASH_VERSIONChanged(fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(IDProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				ID = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						relationshipXrefListener listener=(relationshipXrefListener)iter.next();
						listener.IDChanged(fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(DBProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				DB = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						relationshipXrefListener listener=(relationshipXrefListener)iter.next();
						listener.DBChanged(fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(RELATIONSHIP_DASH_TYPEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				RELATIONSHIP_DASH_TYPE = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						relationshipXrefListener listener=(relationshipXrefListener)iter.next();
						listener.RELATIONSHIP_DASH_TYPEChanged(fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl.this);
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
						relationshipXrefListener listener=(relationshipXrefListener)iter.next();
						listener.COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ID_DASH_VERSIONProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (ID_DASH_VERSION != null && ID_DASH_VERSION.equals(obj))
					ID_DASH_VERSION = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						relationshipXrefListener listener=(relationshipXrefListener)iter.next();
						listener.ID_DASH_VERSIONChanged(fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(DB_DASH_VERSIONProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (DB_DASH_VERSION != null && DB_DASH_VERSION.equals(obj))
					DB_DASH_VERSION = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						relationshipXrefListener listener=(relationshipXrefListener)iter.next();
						listener.DB_DASH_VERSIONChanged(fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(IDProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (ID != null && ID.equals(obj))
					ID = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						relationshipXrefListener listener=(relationshipXrefListener)iter.next();
						listener.IDChanged(fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(DBProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (DB != null && DB.equals(obj))
					DB = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						relationshipXrefListener listener=(relationshipXrefListener)iter.next();
						listener.DBChanged(fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(RELATIONSHIP_DASH_TYPEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (RELATIONSHIP_DASH_TYPE != null && RELATIONSHIP_DASH_TYPE.equals(obj))
					RELATIONSHIP_DASH_TYPE = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						relationshipXrefListener listener=(relationshipXrefListener)iter.next();
						listener.RELATIONSHIP_DASH_TYPEChanged(fr.curie.BiNoM.pathways.biopax.relationshipXrefImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}