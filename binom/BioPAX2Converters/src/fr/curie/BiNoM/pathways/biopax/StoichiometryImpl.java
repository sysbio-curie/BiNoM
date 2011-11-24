

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.Stoichiometry}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#Stoichiometry)</p>
 * <br>
 */
public class StoichiometryImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.Stoichiometry {
	

	private static com.hp.hpl.jena.rdf.model.Property commentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#comment");
	private java.util.ArrayList comment;
	private static com.hp.hpl.jena.rdf.model.Property stoichiometricCoefficientProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#stoichiometricCoefficient");
	private java.lang.Float stoichiometricCoefficient;
	private static com.hp.hpl.jena.rdf.model.Property physicalEntityProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#physicalEntity");
	private fr.curie.BiNoM.pathways.biopax.PhysicalEntity physicalEntity;

	StoichiometryImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static StoichiometryImpl getStoichiometry(Resource resource, Model model) throws JastorException {
		return new StoichiometryImpl(resource, model);
	}
	    
	static StoichiometryImpl createStoichiometry(Resource resource, Model model) throws JastorException {
		StoichiometryImpl impl = new StoichiometryImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, Stoichiometry.TYPE)))
			impl._model.add(impl._resource, RDF.type, Stoichiometry.TYPE);
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
		it = _model.listStatements(_resource,stoichiometricCoefficientProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,physicalEntityProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.Stoichiometry.TYPE);
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
		stoichiometricCoefficient = null;
		physicalEntity = null;
	}


	private void initComment() throws JastorException {
		comment = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, commentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#comment properties in Stoichiometry model not a Literal", stmt.getObject());
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

	public java.lang.Float getStoichiometricCoefficient() throws JastorException {
		if (stoichiometricCoefficient != null)
			return stoichiometricCoefficient;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, stoichiometricCoefficientProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": stoichiometricCoefficient getProperty() in fr.curie.BiNoM.pathways.biopax.Stoichiometry model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
		stoichiometricCoefficient = (java.lang.Float)obj;
		return stoichiometricCoefficient;
	}
	
	public void setStoichiometricCoefficient(java.lang.Float stoichiometricCoefficient) throws JastorException {
		if (_model.contains(_resource,stoichiometricCoefficientProperty)) {
			_model.removeAll(_resource,stoichiometricCoefficientProperty,null);
		}
		this.stoichiometricCoefficient = stoichiometricCoefficient;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (stoichiometricCoefficient != null) {
			_model.add(_model.createStatement(_resource,stoichiometricCoefficientProperty, _model.createTypedLiteral(stoichiometricCoefficient)));
		}	
	}


	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity getPhysicalEntity() throws JastorException {
		if (physicalEntity != null)
			return physicalEntity;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, physicalEntityProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": physicalEntity getProperty() in fr.curie.BiNoM.pathways.biopax.Stoichiometry model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		physicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
		return physicalEntity;
	}

	public void setPhysicalEntity(fr.curie.BiNoM.pathways.biopax.PhysicalEntity physicalEntity) throws JastorException {
		if (_model.contains(_resource,physicalEntityProperty)) {
			_model.removeAll(_resource,physicalEntityProperty,null);
		}
		this.physicalEntity = physicalEntity;
		if (physicalEntity != null) {
			_model.add(_model.createStatement(_resource,physicalEntityProperty, physicalEntity.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity setPhysicalEntity() throws JastorException {
		if (_model.contains(_resource,physicalEntityProperty)) {
			_model.removeAll(_resource,physicalEntityProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.PhysicalEntity physicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createPhysicalEntity(_model.createResource(),_model);
		this.physicalEntity = physicalEntity;
		_model.add(_model.createStatement(_resource,physicalEntityProperty, physicalEntity.resource()));
		return physicalEntity;
	}
	
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity setPhysicalEntity(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,physicalEntityProperty)) {
			_model.removeAll(_resource,physicalEntityProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.PhysicalEntity physicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
		this.physicalEntity = physicalEntity;
		_model.add(_model.createStatement(_resource,physicalEntityProperty, physicalEntity.resource()));
		return physicalEntity;
	}
	 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof StoichiometryListener))
			throw new IllegalArgumentException("ThingListener must be instance of StoichiometryListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((StoichiometryListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof StoichiometryListener))
			throw new IllegalArgumentException("ThingListener must be instance of StoichiometryListener"); 
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
						StoichiometryListener listener=(StoichiometryListener)iter.next();
						listener.commentAdded(fr.curie.BiNoM.pathways.biopax.StoichiometryImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(stoichiometricCoefficientProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				stoichiometricCoefficient = (java.lang.Float)Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						StoichiometryListener listener=(StoichiometryListener)iter.next();
						listener.stoichiometricCoefficientChanged(fr.curie.BiNoM.pathways.biopax.StoichiometryImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(physicalEntityProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				physicalEntity = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						physicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
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
						StoichiometryListener listener=(StoichiometryListener)iter.next();
						listener.physicalEntityChanged(fr.curie.BiNoM.pathways.biopax.StoichiometryImpl.this);
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
						StoichiometryListener listener=(StoichiometryListener)iter.next();
						listener.commentRemoved(fr.curie.BiNoM.pathways.biopax.StoichiometryImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(stoichiometricCoefficientProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (stoichiometricCoefficient != null && stoichiometricCoefficient.equals(obj))
					stoichiometricCoefficient = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						StoichiometryListener listener=(StoichiometryListener)iter.next();
						listener.stoichiometricCoefficientChanged(fr.curie.BiNoM.pathways.biopax.StoichiometryImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(physicalEntityProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (physicalEntity != null && physicalEntity.resource().equals(resource))
						physicalEntity = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						StoichiometryListener listener=(StoichiometryListener)iter.next();
						listener.physicalEntityChanged(fr.curie.BiNoM.pathways.biopax.StoichiometryImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}