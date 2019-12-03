

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.SequenceInterval}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#SequenceInterval)</p>
 * <br>
 */
public class SequenceIntervalImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.SequenceInterval {
	

	private static com.hp.hpl.jena.rdf.model.Property commentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#comment");
	private java.util.ArrayList comment;
	private static com.hp.hpl.jena.rdf.model.Property sequenceIntervalEndProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#sequenceIntervalEnd");
	private fr.curie.BiNoM.pathways.biopax.SequenceSite sequenceIntervalEnd;
	private static com.hp.hpl.jena.rdf.model.Property sequenceIntervalBeginProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#sequenceIntervalBegin");
	private fr.curie.BiNoM.pathways.biopax.SequenceSite sequenceIntervalBegin;

	SequenceIntervalImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static SequenceIntervalImpl getSequenceInterval(Resource resource, Model model) throws JastorException {
		return new SequenceIntervalImpl(resource, model);
	}
	    
	static SequenceIntervalImpl createSequenceInterval(Resource resource, Model model) throws JastorException {
		SequenceIntervalImpl impl = new SequenceIntervalImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, SequenceInterval.TYPE)))
			impl._model.add(impl._resource, RDF.type, SequenceInterval.TYPE);
		impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.SequenceLocation.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.SequenceLocation.TYPE));     
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
		it = _model.listStatements(_resource,sequenceIntervalEndProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,sequenceIntervalBeginProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.SequenceInterval.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.SequenceLocation.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
	
	public void clearCache() {
		comment = null;
		sequenceIntervalEnd = null;
		sequenceIntervalBegin = null;
	}


	private void initComment() throws JastorException {
		comment = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, commentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#comment properties in SequenceInterval model not a Literal", stmt.getObject());
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


	public fr.curie.BiNoM.pathways.biopax.SequenceSite getSequenceIntervalEnd() throws JastorException {
		if (sequenceIntervalEnd != null)
			return sequenceIntervalEnd;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, sequenceIntervalEndProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": sequenceIntervalEnd getProperty() in fr.curie.BiNoM.pathways.biopax.SequenceInterval model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		sequenceIntervalEnd = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceSite(resource,_model);
		return sequenceIntervalEnd;
	}

	public void setSequenceIntervalEnd(fr.curie.BiNoM.pathways.biopax.SequenceSite sequenceIntervalEnd) throws JastorException {
		if (_model.contains(_resource,sequenceIntervalEndProperty)) {
			_model.removeAll(_resource,sequenceIntervalEndProperty,null);
		}
		this.sequenceIntervalEnd = sequenceIntervalEnd;
		if (sequenceIntervalEnd != null) {
			_model.add(_model.createStatement(_resource,sequenceIntervalEndProperty, sequenceIntervalEnd.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.SequenceSite setSequenceIntervalEnd() throws JastorException {
		if (_model.contains(_resource,sequenceIntervalEndProperty)) {
			_model.removeAll(_resource,sequenceIntervalEndProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.SequenceSite sequenceIntervalEnd = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createSequenceSite(_model.createResource(),_model);
		this.sequenceIntervalEnd = sequenceIntervalEnd;
		_model.add(_model.createStatement(_resource,sequenceIntervalEndProperty, sequenceIntervalEnd.resource()));
		return sequenceIntervalEnd;
	}
	
	public fr.curie.BiNoM.pathways.biopax.SequenceSite setSequenceIntervalEnd(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,sequenceIntervalEndProperty)) {
			_model.removeAll(_resource,sequenceIntervalEndProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.SequenceSite sequenceIntervalEnd = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceSite(resource,_model);
		this.sequenceIntervalEnd = sequenceIntervalEnd;
		_model.add(_model.createStatement(_resource,sequenceIntervalEndProperty, sequenceIntervalEnd.resource()));
		return sequenceIntervalEnd;
	}
	

	public fr.curie.BiNoM.pathways.biopax.SequenceSite getSequenceIntervalBegin() throws JastorException {
		if (sequenceIntervalBegin != null)
			return sequenceIntervalBegin;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, sequenceIntervalBeginProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": sequenceIntervalBegin getProperty() in fr.curie.BiNoM.pathways.biopax.SequenceInterval model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		sequenceIntervalBegin = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceSite(resource,_model);
		return sequenceIntervalBegin;
	}

	public void setSequenceIntervalBegin(fr.curie.BiNoM.pathways.biopax.SequenceSite sequenceIntervalBegin) throws JastorException {
		if (_model.contains(_resource,sequenceIntervalBeginProperty)) {
			_model.removeAll(_resource,sequenceIntervalBeginProperty,null);
		}
		this.sequenceIntervalBegin = sequenceIntervalBegin;
		if (sequenceIntervalBegin != null) {
			_model.add(_model.createStatement(_resource,sequenceIntervalBeginProperty, sequenceIntervalBegin.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.SequenceSite setSequenceIntervalBegin() throws JastorException {
		if (_model.contains(_resource,sequenceIntervalBeginProperty)) {
			_model.removeAll(_resource,sequenceIntervalBeginProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.SequenceSite sequenceIntervalBegin = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createSequenceSite(_model.createResource(),_model);
		this.sequenceIntervalBegin = sequenceIntervalBegin;
		_model.add(_model.createStatement(_resource,sequenceIntervalBeginProperty, sequenceIntervalBegin.resource()));
		return sequenceIntervalBegin;
	}
	
	public fr.curie.BiNoM.pathways.biopax.SequenceSite setSequenceIntervalBegin(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,sequenceIntervalBeginProperty)) {
			_model.removeAll(_resource,sequenceIntervalBeginProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.SequenceSite sequenceIntervalBegin = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceSite(resource,_model);
		this.sequenceIntervalBegin = sequenceIntervalBegin;
		_model.add(_model.createStatement(_resource,sequenceIntervalBeginProperty, sequenceIntervalBegin.resource()));
		return sequenceIntervalBegin;
	}
	 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof SequenceIntervalListener))
			throw new IllegalArgumentException("ThingListener must be instance of SequenceIntervalListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((SequenceIntervalListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof SequenceIntervalListener))
			throw new IllegalArgumentException("ThingListener must be instance of SequenceIntervalListener"); 
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
						SequenceIntervalListener listener=(SequenceIntervalListener)iter.next();
						listener.commentAdded(fr.curie.BiNoM.pathways.biopax.SequenceIntervalImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(sequenceIntervalEndProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				sequenceIntervalEnd = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						sequenceIntervalEnd = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceSite(resource,_model);
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
						SequenceIntervalListener listener=(SequenceIntervalListener)iter.next();
						listener.sequenceIntervalEndChanged(fr.curie.BiNoM.pathways.biopax.SequenceIntervalImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(sequenceIntervalBeginProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				sequenceIntervalBegin = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						sequenceIntervalBegin = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getSequenceSite(resource,_model);
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
						SequenceIntervalListener listener=(SequenceIntervalListener)iter.next();
						listener.sequenceIntervalBeginChanged(fr.curie.BiNoM.pathways.biopax.SequenceIntervalImpl.this);
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
						SequenceIntervalListener listener=(SequenceIntervalListener)iter.next();
						listener.commentRemoved(fr.curie.BiNoM.pathways.biopax.SequenceIntervalImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(sequenceIntervalEndProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (sequenceIntervalEnd != null && sequenceIntervalEnd.resource().equals(resource))
						sequenceIntervalEnd = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						SequenceIntervalListener listener=(SequenceIntervalListener)iter.next();
						listener.sequenceIntervalEndChanged(fr.curie.BiNoM.pathways.biopax.SequenceIntervalImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(sequenceIntervalBeginProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (sequenceIntervalBegin != null && sequenceIntervalBegin.resource().equals(resource))
						sequenceIntervalBegin = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						SequenceIntervalListener listener=(SequenceIntervalListener)iter.next();
						listener.sequenceIntervalBeginChanged(fr.curie.BiNoM.pathways.biopax.SequenceIntervalImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}