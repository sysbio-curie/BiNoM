

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.Provenance}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Provenance)</p>
 * <br>
 */
public class ProvenanceImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.Provenance {
	

	private static com.hp.hpl.jena.rdf.model.Property commentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#comment");
	private java.util.ArrayList comment;
	private static com.hp.hpl.jena.rdf.model.Property nameProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#name");
	private java.util.ArrayList name;
	private static com.hp.hpl.jena.rdf.model.Property xrefProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#xref");
	private java.util.ArrayList xref;
	private java.util.ArrayList xref_asPublicationXref;
	private java.util.ArrayList xref_asUnificationXref;

	ProvenanceImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static ProvenanceImpl getProvenance(Resource resource, Model model) throws JastorException {
		return new ProvenanceImpl(resource, model);
	}
	    
	static ProvenanceImpl createProvenance(Resource resource, Model model) throws JastorException {
		ProvenanceImpl impl = new ProvenanceImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, Provenance.TYPE)))
			impl._model.add(impl._resource, RDF.type, Provenance.TYPE);
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
		it = _model.listStatements(_resource,nameProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,xrefProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.Provenance.TYPE);
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
		name = null;
		xref = null;
		xref_asPublicationXref = null;
		xref_asUnificationXref = null;
	}


	private void initComment() throws JastorException {
		comment = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, commentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#comment properties in Provenance model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#name properties in Provenance model not a Literal", stmt.getObject());
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


	private void initXref() throws JastorException {
		this.xref = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, xrefProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#xref properties in Provenance model not a Resource", stmt.getObject());
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
		
	private void initXref_asPublicationXref() throws JastorException {
		this.xref_asPublicationXref = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, xrefProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#xref properties in Provenance model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.PublicationXref.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.PublicationXref xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPublicationXref(resource,_model);
				this.xref_asPublicationXref.add(xref);
			}
		}
	}

	public java.util.Iterator getXref_asPublicationXref() throws JastorException {
		if (xref_asPublicationXref == null)
			initXref_asPublicationXref();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(xref_asPublicationXref,_resource,xrefProperty,true);
	}

	public void addXref(fr.curie.BiNoM.pathways.biopax.PublicationXref xref) throws JastorException {
		if (this.xref_asPublicationXref == null)
			initXref_asPublicationXref();
		if (this.xref_asPublicationXref.contains(xref)) {
			this.xref_asPublicationXref.remove(xref);
			this.xref_asPublicationXref.add(xref);
			return;
		}
		this.xref_asPublicationXref.add(xref);
		_model.add(_model.createStatement(_resource,xrefProperty,xref.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.PublicationXref addXref_asPublicationXref() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PublicationXref xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createPublicationXref(_model.createResource(),_model);
		if (this.xref_asPublicationXref == null)
			initXref_asPublicationXref();
		this.xref_asPublicationXref.add(xref);
		_model.add(_model.createStatement(_resource,xrefProperty,xref.resource()));
		return xref;
	}
	
	public fr.curie.BiNoM.pathways.biopax.PublicationXref addXref_asPublicationXref(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PublicationXref xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPublicationXref(resource,_model);
		if (this.xref_asPublicationXref == null)
			initXref_asPublicationXref();
		if (this.xref_asPublicationXref.contains(xref))
			return xref;
		this.xref_asPublicationXref.add(xref);
		_model.add(_model.createStatement(_resource,xrefProperty,xref.resource()));
		return xref;
	}
	
	public void removeXref(fr.curie.BiNoM.pathways.biopax.PublicationXref xref) throws JastorException {
		if (this.xref_asPublicationXref == null)
			initXref_asPublicationXref();
		if (!this.xref_asPublicationXref.contains(xref))
			return;
		if (!_model.contains(_resource, xrefProperty, xref.resource()))
			return;
		this.xref_asPublicationXref.remove(xref);
		_model.removeAll(_resource, xrefProperty, xref.resource());
	}
		
	private void initXref_asUnificationXref() throws JastorException {
		this.xref_asUnificationXref = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, xrefProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#xref properties in Provenance model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.UnificationXref.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.UnificationXref xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getUnificationXref(resource,_model);
				this.xref_asUnificationXref.add(xref);
			}
		}
	}

	public java.util.Iterator getXref_asUnificationXref() throws JastorException {
		if (xref_asUnificationXref == null)
			initXref_asUnificationXref();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(xref_asUnificationXref,_resource,xrefProperty,true);
	}

	public void addXref(fr.curie.BiNoM.pathways.biopax.UnificationXref xref) throws JastorException {
		if (this.xref_asUnificationXref == null)
			initXref_asUnificationXref();
		if (this.xref_asUnificationXref.contains(xref)) {
			this.xref_asUnificationXref.remove(xref);
			this.xref_asUnificationXref.add(xref);
			return;
		}
		this.xref_asUnificationXref.add(xref);
		_model.add(_model.createStatement(_resource,xrefProperty,xref.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.UnificationXref addXref_asUnificationXref() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.UnificationXref xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createUnificationXref(_model.createResource(),_model);
		if (this.xref_asUnificationXref == null)
			initXref_asUnificationXref();
		this.xref_asUnificationXref.add(xref);
		_model.add(_model.createStatement(_resource,xrefProperty,xref.resource()));
		return xref;
	}
	
	public fr.curie.BiNoM.pathways.biopax.UnificationXref addXref_asUnificationXref(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.UnificationXref xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getUnificationXref(resource,_model);
		if (this.xref_asUnificationXref == null)
			initXref_asUnificationXref();
		if (this.xref_asUnificationXref.contains(xref))
			return xref;
		this.xref_asUnificationXref.add(xref);
		_model.add(_model.createStatement(_resource,xrefProperty,xref.resource()));
		return xref;
	}
	
	public void removeXref(fr.curie.BiNoM.pathways.biopax.UnificationXref xref) throws JastorException {
		if (this.xref_asUnificationXref == null)
			initXref_asUnificationXref();
		if (!this.xref_asUnificationXref.contains(xref))
			return;
		if (!_model.contains(_resource, xrefProperty, xref.resource()))
			return;
		this.xref_asUnificationXref.remove(xref);
		_model.removeAll(_resource, xrefProperty, xref.resource());
	}
		  


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof ProvenanceListener))
			throw new IllegalArgumentException("ThingListener must be instance of ProvenanceListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((ProvenanceListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof ProvenanceListener))
			throw new IllegalArgumentException("ThingListener must be instance of ProvenanceListener"); 
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
						ProvenanceListener listener=(ProvenanceListener)iter.next();
						listener.commentAdded(fr.curie.BiNoM.pathways.biopax.ProvenanceImpl.this,(java.lang.String)obj);
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
						ProvenanceListener listener=(ProvenanceListener)iter.next();
						listener.nameAdded(fr.curie.BiNoM.pathways.biopax.ProvenanceImpl.this,(java.lang.String)obj);
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
							ProvenanceListener listener=(ProvenanceListener)iter.next();
							listener.xrefAdded(fr.curie.BiNoM.pathways.biopax.ProvenanceImpl.this,_xref);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.PublicationXref.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.PublicationXref _xref_asPublicationXref = null;
					try {
						_xref_asPublicationXref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPublicationXref(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (xref_asPublicationXref == null) {
						try {
							initXref_asPublicationXref();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!xref_asPublicationXref.contains(_xref_asPublicationXref))
						xref_asPublicationXref.add(_xref_asPublicationXref);
					if (listeners != null) {
						java.util.ArrayList consumersForXref_asPublicationXref;
						synchronized (listeners) {
							consumersForXref_asPublicationXref = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForXref_asPublicationXref.iterator();iter.hasNext();){
							ProvenanceListener listener=(ProvenanceListener)iter.next();
							listener.xrefAdded(fr.curie.BiNoM.pathways.biopax.ProvenanceImpl.this,_xref_asPublicationXref);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.UnificationXref.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.UnificationXref _xref_asUnificationXref = null;
					try {
						_xref_asUnificationXref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getUnificationXref(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (xref_asUnificationXref == null) {
						try {
							initXref_asUnificationXref();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!xref_asUnificationXref.contains(_xref_asUnificationXref))
						xref_asUnificationXref.add(_xref_asUnificationXref);
					if (listeners != null) {
						java.util.ArrayList consumersForXref_asUnificationXref;
						synchronized (listeners) {
							consumersForXref_asUnificationXref = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForXref_asUnificationXref.iterator();iter.hasNext();){
							ProvenanceListener listener=(ProvenanceListener)iter.next();
							listener.xrefAdded(fr.curie.BiNoM.pathways.biopax.ProvenanceImpl.this,_xref_asUnificationXref);
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
						ProvenanceListener listener=(ProvenanceListener)iter.next();
						listener.commentRemoved(fr.curie.BiNoM.pathways.biopax.ProvenanceImpl.this,(java.lang.String)obj);
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
						ProvenanceListener listener=(ProvenanceListener)iter.next();
						listener.nameRemoved(fr.curie.BiNoM.pathways.biopax.ProvenanceImpl.this,(java.lang.String)obj);
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
							ProvenanceListener listener=(ProvenanceListener)iter.next();
							listener.xrefRemoved(fr.curie.BiNoM.pathways.biopax.ProvenanceImpl.this,_xref);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.PublicationXref.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.PublicationXref _xref_asPublicationXref = null;
					if (xref_asPublicationXref != null) {
						boolean found = false;
						for (int i=0;i<xref_asPublicationXref.size();i++) {
							fr.curie.BiNoM.pathways.biopax.PublicationXref __item = (fr.curie.BiNoM.pathways.biopax.PublicationXref) xref_asPublicationXref.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_xref_asPublicationXref = __item;
								break;
							}
						}
						if (found)
							xref_asPublicationXref.remove(_xref_asPublicationXref);
						else {
							try {
								_xref_asPublicationXref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPublicationXref(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_xref_asPublicationXref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPublicationXref(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForXref_asPublicationXref;
						synchronized (listeners) {
							consumersForXref_asPublicationXref = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForXref_asPublicationXref.iterator();iter.hasNext();){
							ProvenanceListener listener=(ProvenanceListener)iter.next();
							listener.xrefRemoved(fr.curie.BiNoM.pathways.biopax.ProvenanceImpl.this,_xref_asPublicationXref);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.UnificationXref.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.UnificationXref _xref_asUnificationXref = null;
					if (xref_asUnificationXref != null) {
						boolean found = false;
						for (int i=0;i<xref_asUnificationXref.size();i++) {
							fr.curie.BiNoM.pathways.biopax.UnificationXref __item = (fr.curie.BiNoM.pathways.biopax.UnificationXref) xref_asUnificationXref.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_xref_asUnificationXref = __item;
								break;
							}
						}
						if (found)
							xref_asUnificationXref.remove(_xref_asUnificationXref);
						else {
							try {
								_xref_asUnificationXref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getUnificationXref(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_xref_asUnificationXref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getUnificationXref(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForXref_asUnificationXref;
						synchronized (listeners) {
							consumersForXref_asUnificationXref = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForXref_asUnificationXref.iterator();iter.hasNext();){
							ProvenanceListener listener=(ProvenanceListener)iter.next();
							listener.xrefRemoved(fr.curie.BiNoM.pathways.biopax.ProvenanceImpl.this,_xref_asUnificationXref);
						}
					}
				}
				return;
			}
		}

	//}
	


}