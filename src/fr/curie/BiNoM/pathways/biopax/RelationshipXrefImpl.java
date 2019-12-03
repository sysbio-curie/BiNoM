

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.RelationshipXref}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#RelationshipXref)</p>
 * <br>
 */
public class RelationshipXrefImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.RelationshipXref {
	

	private static com.hp.hpl.jena.rdf.model.Property commentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#comment");
	private java.util.ArrayList comment;
	private static com.hp.hpl.jena.rdf.model.Property idProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#id");
	private java.lang.String id;
	private static com.hp.hpl.jena.rdf.model.Property dbProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#db");
	private java.lang.String db;
	private static com.hp.hpl.jena.rdf.model.Property idVersionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#idVersion");
	private java.lang.String idVersion;
	private static com.hp.hpl.jena.rdf.model.Property dbVersionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#dbVersion");
	private java.lang.String dbVersion;
	private static com.hp.hpl.jena.rdf.model.Property relationshipTypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#relationshipType");
	private java.util.ArrayList relationshipType;

	RelationshipXrefImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static RelationshipXrefImpl getRelationshipXref(Resource resource, Model model) throws JastorException {
		return new RelationshipXrefImpl(resource, model);
	}
	    
	static RelationshipXrefImpl createRelationshipXref(Resource resource, Model model) throws JastorException {
		RelationshipXrefImpl impl = new RelationshipXrefImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, RelationshipXref.TYPE)))
			impl._model.add(impl._resource, RDF.type, RelationshipXref.TYPE);
		impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Xref.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Xref.TYPE));     
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
		it = _model.listStatements(_resource,idProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,dbProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,idVersionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,dbVersionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,relationshipTypeProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.RelationshipXref.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.Xref.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
	
	public void clearCache() {
		comment = null;
		id = null;
		db = null;
		idVersion = null;
		dbVersion = null;
		relationshipType = null;
	}


	private void initComment() throws JastorException {
		comment = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, commentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#comment properties in RelationshipXref model not a Literal", stmt.getObject());
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

	public java.lang.String getId() throws JastorException {
		if (id != null)
			return id;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, idProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": id getProperty() in fr.curie.BiNoM.pathways.biopax.RelationshipXref model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		id = (java.lang.String)obj;
		return id;
	}
	
	public void setId(java.lang.String id) throws JastorException {
		if (_model.contains(_resource,idProperty)) {
			_model.removeAll(_resource,idProperty,null);
		}
		this.id = id;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (id != null) {
			_model.add(_model.createStatement(_resource,idProperty, _model.createTypedLiteral(id)));
		}	
	}

	public java.lang.String getDb() throws JastorException {
		if (db != null)
			return db;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, dbProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": db getProperty() in fr.curie.BiNoM.pathways.biopax.RelationshipXref model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		db = (java.lang.String)obj;
		return db;
	}
	
	public void setDb(java.lang.String db) throws JastorException {
		if (_model.contains(_resource,dbProperty)) {
			_model.removeAll(_resource,dbProperty,null);
		}
		this.db = db;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (db != null) {
			_model.add(_model.createStatement(_resource,dbProperty, _model.createTypedLiteral(db)));
		}	
	}

	public java.lang.String getIdVersion() throws JastorException {
		if (idVersion != null)
			return idVersion;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, idVersionProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": idVersion getProperty() in fr.curie.BiNoM.pathways.biopax.RelationshipXref model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		idVersion = (java.lang.String)obj;
		return idVersion;
	}
	
	public void setIdVersion(java.lang.String idVersion) throws JastorException {
		if (_model.contains(_resource,idVersionProperty)) {
			_model.removeAll(_resource,idVersionProperty,null);
		}
		this.idVersion = idVersion;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (idVersion != null) {
			_model.add(_model.createStatement(_resource,idVersionProperty, _model.createTypedLiteral(idVersion)));
		}	
	}

	public java.lang.String getDbVersion() throws JastorException {
		if (dbVersion != null)
			return dbVersion;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, dbVersionProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": dbVersion getProperty() in fr.curie.BiNoM.pathways.biopax.RelationshipXref model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		dbVersion = (java.lang.String)obj;
		return dbVersion;
	}
	
	public void setDbVersion(java.lang.String dbVersion) throws JastorException {
		if (_model.contains(_resource,dbVersionProperty)) {
			_model.removeAll(_resource,dbVersionProperty,null);
		}
		this.dbVersion = dbVersion;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (dbVersion != null) {
			_model.add(_model.createStatement(_resource,dbVersionProperty, _model.createTypedLiteral(dbVersion)));
		}	
	}


	private void initRelationshipType() throws JastorException {
		this.relationshipType = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, relationshipTypeProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#relationshipType properties in RelationshipXref model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary relationshipType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getRelationshipTypeVocabulary(resource,_model);
				this.relationshipType.add(relationshipType);
			}
		}
	}

	public java.util.Iterator getRelationshipType() throws JastorException {
		if (relationshipType == null)
			initRelationshipType();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(relationshipType,_resource,relationshipTypeProperty,true);
	}

	public void addRelationshipType(fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary relationshipType) throws JastorException {
		if (this.relationshipType == null)
			initRelationshipType();
		if (this.relationshipType.contains(relationshipType)) {
			this.relationshipType.remove(relationshipType);
			this.relationshipType.add(relationshipType);
			return;
		}
		this.relationshipType.add(relationshipType);
		_model.add(_model.createStatement(_resource,relationshipTypeProperty,relationshipType.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary addRelationshipType() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary relationshipType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createRelationshipTypeVocabulary(_model.createResource(),_model);
		if (this.relationshipType == null)
			initRelationshipType();
		this.relationshipType.add(relationshipType);
		_model.add(_model.createStatement(_resource,relationshipTypeProperty,relationshipType.resource()));
		return relationshipType;
	}
	
	public fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary addRelationshipType(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary relationshipType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getRelationshipTypeVocabulary(resource,_model);
		if (this.relationshipType == null)
			initRelationshipType();
		if (this.relationshipType.contains(relationshipType))
			return relationshipType;
		this.relationshipType.add(relationshipType);
		_model.add(_model.createStatement(_resource,relationshipTypeProperty,relationshipType.resource()));
		return relationshipType;
	}
	
	public void removeRelationshipType(fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary relationshipType) throws JastorException {
		if (this.relationshipType == null)
			initRelationshipType();
		if (!this.relationshipType.contains(relationshipType))
			return;
		if (!_model.contains(_resource, relationshipTypeProperty, relationshipType.resource()))
			return;
		this.relationshipType.remove(relationshipType);
		_model.removeAll(_resource, relationshipTypeProperty, relationshipType.resource());
	}
		  


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof RelationshipXrefListener))
			throw new IllegalArgumentException("ThingListener must be instance of RelationshipXrefListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((RelationshipXrefListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof RelationshipXrefListener))
			throw new IllegalArgumentException("ThingListener must be instance of RelationshipXrefListener"); 
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
						RelationshipXrefListener listener=(RelationshipXrefListener)iter.next();
						listener.commentAdded(fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(idProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				id = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						RelationshipXrefListener listener=(RelationshipXrefListener)iter.next();
						listener.idChanged(fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(dbProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				db = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						RelationshipXrefListener listener=(RelationshipXrefListener)iter.next();
						listener.dbChanged(fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(idVersionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				idVersion = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						RelationshipXrefListener listener=(RelationshipXrefListener)iter.next();
						listener.idVersionChanged(fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(dbVersionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				dbVersion = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						RelationshipXrefListener listener=(RelationshipXrefListener)iter.next();
						listener.dbVersionChanged(fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(relationshipTypeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary _relationshipType = null;
					try {
						_relationshipType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getRelationshipTypeVocabulary(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (relationshipType == null) {
						try {
							initRelationshipType();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!relationshipType.contains(_relationshipType))
						relationshipType.add(_relationshipType);
					if (listeners != null) {
						java.util.ArrayList consumersForRelationshipType;
						synchronized (listeners) {
							consumersForRelationshipType = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForRelationshipType.iterator();iter.hasNext();){
							RelationshipXrefListener listener=(RelationshipXrefListener)iter.next();
							listener.relationshipTypeAdded(fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl.this,_relationshipType);
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
						RelationshipXrefListener listener=(RelationshipXrefListener)iter.next();
						listener.commentRemoved(fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(idProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (id != null && id.equals(obj))
					id = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						RelationshipXrefListener listener=(RelationshipXrefListener)iter.next();
						listener.idChanged(fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(dbProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (db != null && db.equals(obj))
					db = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						RelationshipXrefListener listener=(RelationshipXrefListener)iter.next();
						listener.dbChanged(fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(idVersionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (idVersion != null && idVersion.equals(obj))
					idVersion = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						RelationshipXrefListener listener=(RelationshipXrefListener)iter.next();
						listener.idVersionChanged(fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(dbVersionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (dbVersion != null && dbVersion.equals(obj))
					dbVersion = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						RelationshipXrefListener listener=(RelationshipXrefListener)iter.next();
						listener.dbVersionChanged(fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(relationshipTypeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary _relationshipType = null;
					if (relationshipType != null) {
						boolean found = false;
						for (int i=0;i<relationshipType.size();i++) {
							fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary __item = (fr.curie.BiNoM.pathways.biopax.RelationshipTypeVocabulary) relationshipType.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_relationshipType = __item;
								break;
							}
						}
						if (found)
							relationshipType.remove(_relationshipType);
						else {
							try {
								_relationshipType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getRelationshipTypeVocabulary(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_relationshipType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getRelationshipTypeVocabulary(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForRelationshipType;
						synchronized (listeners) {
							consumersForRelationshipType = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForRelationshipType.iterator();iter.hasNext();){
							RelationshipXrefListener listener=(RelationshipXrefListener)iter.next();
							listener.relationshipTypeRemoved(fr.curie.BiNoM.pathways.biopax.RelationshipXrefImpl.this,_relationshipType);
						}
					}
				}
				return;
			}
		}

	//}
	


}