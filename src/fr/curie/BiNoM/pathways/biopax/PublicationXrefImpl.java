

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.PublicationXref}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#PublicationXref)</p>
 * <br>
 */
public class PublicationXrefImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.PublicationXref {
	

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
	private static com.hp.hpl.jena.rdf.model.Property titleProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#title");
	private java.lang.String title;
	private static com.hp.hpl.jena.rdf.model.Property yearProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#year");
	private java.lang.Integer year;
	private static com.hp.hpl.jena.rdf.model.Property urlProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#url");
	private java.util.ArrayList url;
	private static com.hp.hpl.jena.rdf.model.Property authorProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#author");
	private java.util.ArrayList author;
	private static com.hp.hpl.jena.rdf.model.Property sourceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#source");
	private java.util.ArrayList source;

	PublicationXrefImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static PublicationXrefImpl getPublicationXref(Resource resource, Model model) throws JastorException {
		return new PublicationXrefImpl(resource, model);
	}
	    
	static PublicationXrefImpl createPublicationXref(Resource resource, Model model) throws JastorException {
		PublicationXrefImpl impl = new PublicationXrefImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, PublicationXref.TYPE)))
			impl._model.add(impl._resource, RDF.type, PublicationXref.TYPE);
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
		it = _model.listStatements(_resource,titleProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,yearProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,urlProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,authorProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,sourceProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.PublicationXref.TYPE);
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
		title = null;
		year = null;
		url = null;
		author = null;
		source = null;
	}


	private void initComment() throws JastorException {
		comment = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, commentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#comment properties in PublicationXref model not a Literal", stmt.getObject());
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
			throw new JastorInvalidRDFNodeException(uri() + ": id getProperty() in fr.curie.BiNoM.pathways.biopax.PublicationXref model not Literal", stmt.getObject());
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
			throw new JastorInvalidRDFNodeException(uri() + ": db getProperty() in fr.curie.BiNoM.pathways.biopax.PublicationXref model not Literal", stmt.getObject());
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
			throw new JastorInvalidRDFNodeException(uri() + ": idVersion getProperty() in fr.curie.BiNoM.pathways.biopax.PublicationXref model not Literal", stmt.getObject());
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
			throw new JastorInvalidRDFNodeException(uri() + ": dbVersion getProperty() in fr.curie.BiNoM.pathways.biopax.PublicationXref model not Literal", stmt.getObject());
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

	public java.lang.String getTitle() throws JastorException {
		if (title != null)
			return title;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, titleProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": title getProperty() in fr.curie.BiNoM.pathways.biopax.PublicationXref model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		title = (java.lang.String)obj;
		return title;
	}
	
	public void setTitle(java.lang.String title) throws JastorException {
		if (_model.contains(_resource,titleProperty)) {
			_model.removeAll(_resource,titleProperty,null);
		}
		this.title = title;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (title != null) {
			_model.add(_model.createStatement(_resource,titleProperty, _model.createTypedLiteral(title)));
		}	
	}

	public java.lang.Integer getYear() throws JastorException {
		if (year != null)
			return year;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, yearProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": year getProperty() in fr.curie.BiNoM.pathways.biopax.PublicationXref model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Integer");
		year = (java.lang.Integer)obj;
		return year;
	}
	
	public void setYear(java.lang.Integer year) throws JastorException {
		if (_model.contains(_resource,yearProperty)) {
			_model.removeAll(_resource,yearProperty,null);
		}
		this.year = year;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (year != null) {
			_model.add(_model.createStatement(_resource,yearProperty, _model.createTypedLiteral(year)));
		}	
	}


	private void initUrl() throws JastorException {
		url = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, urlProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#url properties in PublicationXref model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			url.add(Util.fixLiteral(literal.getValue(),"java.lang.String"));
		}
	}

	public java.util.Iterator getUrl() throws JastorException {
		if (url == null)
			initUrl();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(url,_resource,urlProperty,false);
	}

	public void addUrl(java.lang.String url) throws JastorException {
		if (this.url == null)
			initUrl();
		if (this.url.contains(url))
			return;
		if (_model.contains(_resource, urlProperty, _model.createTypedLiteral(url)))
			return;
		this.url.add(url);
		_model.add(_resource, urlProperty, _model.createTypedLiteral(url));
	}
	
	public void removeUrl(java.lang.String url) throws JastorException {
		if (this.url == null)
			initUrl();
		if (!this.url.contains(url))
			return;
		if (!_model.contains(_resource, urlProperty, _model.createTypedLiteral(url)))
			return;
		this.url.remove(url);
		_model.removeAll(_resource, urlProperty, _model.createTypedLiteral(url));
	}


	private void initAuthor() throws JastorException {
		author = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, authorProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#author properties in PublicationXref model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			author.add(Util.fixLiteral(literal.getValue(),"java.lang.String"));
		}
	}

	public java.util.Iterator getAuthor() throws JastorException {
		if (author == null)
			initAuthor();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(author,_resource,authorProperty,false);
	}

	public void addAuthor(java.lang.String author) throws JastorException {
		if (this.author == null)
			initAuthor();
		if (this.author.contains(author))
			return;
		if (_model.contains(_resource, authorProperty, _model.createTypedLiteral(author)))
			return;
		this.author.add(author);
		_model.add(_resource, authorProperty, _model.createTypedLiteral(author));
	}
	
	public void removeAuthor(java.lang.String author) throws JastorException {
		if (this.author == null)
			initAuthor();
		if (!this.author.contains(author))
			return;
		if (!_model.contains(_resource, authorProperty, _model.createTypedLiteral(author)))
			return;
		this.author.remove(author);
		_model.removeAll(_resource, authorProperty, _model.createTypedLiteral(author));
	}


	private void initSource() throws JastorException {
		source = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, sourceProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#source properties in PublicationXref model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			source.add(Util.fixLiteral(literal.getValue(),"java.lang.String"));
		}
	}

	public java.util.Iterator getSource() throws JastorException {
		if (source == null)
			initSource();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(source,_resource,sourceProperty,false);
	}

	public void addSource(java.lang.String source) throws JastorException {
		if (this.source == null)
			initSource();
		if (this.source.contains(source))
			return;
		if (_model.contains(_resource, sourceProperty, _model.createTypedLiteral(source)))
			return;
		this.source.add(source);
		_model.add(_resource, sourceProperty, _model.createTypedLiteral(source));
	}
	
	public void removeSource(java.lang.String source) throws JastorException {
		if (this.source == null)
			initSource();
		if (!this.source.contains(source))
			return;
		if (!_model.contains(_resource, sourceProperty, _model.createTypedLiteral(source)))
			return;
		this.source.remove(source);
		_model.removeAll(_resource, sourceProperty, _model.createTypedLiteral(source));
	}
 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof PublicationXrefListener))
			throw new IllegalArgumentException("ThingListener must be instance of PublicationXrefListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((PublicationXrefListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof PublicationXrefListener))
			throw new IllegalArgumentException("ThingListener must be instance of PublicationXrefListener"); 
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
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.commentAdded(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this,(java.lang.String)obj);
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
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.idChanged(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this);
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
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.dbChanged(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this);
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
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.idVersionChanged(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this);
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
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.dbVersionChanged(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(titleProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				title = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.titleChanged(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(yearProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				year = (java.lang.Integer)Util.fixLiteral(literal.getValue(),"java.lang.Integer");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.yearChanged(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(urlProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (url == null) {
					try {
						initUrl();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (!url.contains(obj))
					url.add(obj);
				java.util.ArrayList consumersForUrl;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForUrl = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForUrl.iterator();iter.hasNext();){
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.urlAdded(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(authorProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (author == null) {
					try {
						initAuthor();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (!author.contains(obj))
					author.add(obj);
				java.util.ArrayList consumersForAuthor;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForAuthor = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForAuthor.iterator();iter.hasNext();){
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.authorAdded(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(sourceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (source == null) {
					try {
						initSource();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (!source.contains(obj))
					source.add(obj);
				java.util.ArrayList consumersForSource;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForSource = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForSource.iterator();iter.hasNext();){
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.sourceAdded(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this,(java.lang.String)obj);
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
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.commentRemoved(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this,(java.lang.String)obj);
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
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.idChanged(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this);
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
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.dbChanged(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this);
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
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.idVersionChanged(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this);
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
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.dbVersionChanged(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(titleProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (title != null && title.equals(obj))
					title = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.titleChanged(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(yearProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Integer");
				if (year != null && year.equals(obj))
					year = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.yearChanged(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(urlProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (url != null) {
					if (url.contains(obj))
						url.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.urlRemoved(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(authorProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (author != null) {
					if (author.contains(obj))
						author.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.authorRemoved(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(sourceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (source != null) {
					if (source.contains(obj))
						source.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						PublicationXrefListener listener=(PublicationXrefListener)iter.next();
						listener.sourceRemoved(fr.curie.BiNoM.pathways.biopax.PublicationXrefImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
		}

	//}
	


}