

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.ExperimentalForm}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#ExperimentalForm)</p>
 * <br>
 */
public class ExperimentalFormImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.ExperimentalForm {
	

	private static com.hp.hpl.jena.rdf.model.Property commentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#comment");
	private java.util.ArrayList comment;
	private static com.hp.hpl.jena.rdf.model.Property experimentalFeatureProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#experimentalFeature");
	private java.util.ArrayList experimentalFeature;
	private static com.hp.hpl.jena.rdf.model.Property experimentalFormDescriptionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#experimentalFormDescription");
	private java.util.ArrayList experimentalFormDescription;
	private static com.hp.hpl.jena.rdf.model.Property experimentalFormEntityProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#experimentalFormEntity");
	private java.util.ArrayList experimentalFormEntity_asGene;
	private java.util.ArrayList experimentalFormEntity_asPhysicalEntity;

	ExperimentalFormImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static ExperimentalFormImpl getExperimentalForm(Resource resource, Model model) throws JastorException {
		return new ExperimentalFormImpl(resource, model);
	}
	    
	static ExperimentalFormImpl createExperimentalForm(Resource resource, Model model) throws JastorException {
		ExperimentalFormImpl impl = new ExperimentalFormImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, ExperimentalForm.TYPE)))
			impl._model.add(impl._resource, RDF.type, ExperimentalForm.TYPE);
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
		it = _model.listStatements(_resource,experimentalFeatureProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,experimentalFormDescriptionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,experimentalFormEntityProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.ExperimentalForm.TYPE);
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
		experimentalFeature = null;
		experimentalFormDescription = null;
		experimentalFormEntity_asGene = null;
		experimentalFormEntity_asPhysicalEntity = null;
	}


	private void initComment() throws JastorException {
		comment = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, commentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#comment properties in ExperimentalForm model not a Literal", stmt.getObject());
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


	private void initExperimentalFeature() throws JastorException {
		this.experimentalFeature = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, experimentalFeatureProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#experimentalFeature properties in ExperimentalForm model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.EntityFeature experimentalFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
				this.experimentalFeature.add(experimentalFeature);
			}
		}
	}

	public java.util.Iterator getExperimentalFeature() throws JastorException {
		if (experimentalFeature == null)
			initExperimentalFeature();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(experimentalFeature,_resource,experimentalFeatureProperty,true);
	}

	public void addExperimentalFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature experimentalFeature) throws JastorException {
		if (this.experimentalFeature == null)
			initExperimentalFeature();
		if (this.experimentalFeature.contains(experimentalFeature)) {
			this.experimentalFeature.remove(experimentalFeature);
			this.experimentalFeature.add(experimentalFeature);
			return;
		}
		this.experimentalFeature.add(experimentalFeature);
		_model.add(_model.createStatement(_resource,experimentalFeatureProperty,experimentalFeature.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addExperimentalFeature() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.EntityFeature experimentalFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createEntityFeature(_model.createResource(),_model);
		if (this.experimentalFeature == null)
			initExperimentalFeature();
		this.experimentalFeature.add(experimentalFeature);
		_model.add(_model.createStatement(_resource,experimentalFeatureProperty,experimentalFeature.resource()));
		return experimentalFeature;
	}
	
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addExperimentalFeature(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.EntityFeature experimentalFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
		if (this.experimentalFeature == null)
			initExperimentalFeature();
		if (this.experimentalFeature.contains(experimentalFeature))
			return experimentalFeature;
		this.experimentalFeature.add(experimentalFeature);
		_model.add(_model.createStatement(_resource,experimentalFeatureProperty,experimentalFeature.resource()));
		return experimentalFeature;
	}
	
	public void removeExperimentalFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature experimentalFeature) throws JastorException {
		if (this.experimentalFeature == null)
			initExperimentalFeature();
		if (!this.experimentalFeature.contains(experimentalFeature))
			return;
		if (!_model.contains(_resource, experimentalFeatureProperty, experimentalFeature.resource()))
			return;
		this.experimentalFeature.remove(experimentalFeature);
		_model.removeAll(_resource, experimentalFeatureProperty, experimentalFeature.resource());
	}
		 

	private void initExperimentalFormDescription() throws JastorException {
		this.experimentalFormDescription = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, experimentalFormDescriptionProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#experimentalFormDescription properties in ExperimentalForm model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary experimentalFormDescription = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getExperimentalFormVocabulary(resource,_model);
				this.experimentalFormDescription.add(experimentalFormDescription);
			}
		}
	}

	public java.util.Iterator getExperimentalFormDescription() throws JastorException {
		if (experimentalFormDescription == null)
			initExperimentalFormDescription();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(experimentalFormDescription,_resource,experimentalFormDescriptionProperty,true);
	}

	public void addExperimentalFormDescription(fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary experimentalFormDescription) throws JastorException {
		if (this.experimentalFormDescription == null)
			initExperimentalFormDescription();
		if (this.experimentalFormDescription.contains(experimentalFormDescription)) {
			this.experimentalFormDescription.remove(experimentalFormDescription);
			this.experimentalFormDescription.add(experimentalFormDescription);
			return;
		}
		this.experimentalFormDescription.add(experimentalFormDescription);
		_model.add(_model.createStatement(_resource,experimentalFormDescriptionProperty,experimentalFormDescription.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary addExperimentalFormDescription() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary experimentalFormDescription = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createExperimentalFormVocabulary(_model.createResource(),_model);
		if (this.experimentalFormDescription == null)
			initExperimentalFormDescription();
		this.experimentalFormDescription.add(experimentalFormDescription);
		_model.add(_model.createStatement(_resource,experimentalFormDescriptionProperty,experimentalFormDescription.resource()));
		return experimentalFormDescription;
	}
	
	public fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary addExperimentalFormDescription(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary experimentalFormDescription = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getExperimentalFormVocabulary(resource,_model);
		if (this.experimentalFormDescription == null)
			initExperimentalFormDescription();
		if (this.experimentalFormDescription.contains(experimentalFormDescription))
			return experimentalFormDescription;
		this.experimentalFormDescription.add(experimentalFormDescription);
		_model.add(_model.createStatement(_resource,experimentalFormDescriptionProperty,experimentalFormDescription.resource()));
		return experimentalFormDescription;
	}
	
	public void removeExperimentalFormDescription(fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary experimentalFormDescription) throws JastorException {
		if (this.experimentalFormDescription == null)
			initExperimentalFormDescription();
		if (!this.experimentalFormDescription.contains(experimentalFormDescription))
			return;
		if (!_model.contains(_resource, experimentalFormDescriptionProperty, experimentalFormDescription.resource()))
			return;
		this.experimentalFormDescription.remove(experimentalFormDescription);
		_model.removeAll(_resource, experimentalFormDescriptionProperty, experimentalFormDescription.resource());
	}
		 

	private void initExperimentalFormEntity_asGene() throws JastorException {
		this.experimentalFormEntity_asGene = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, experimentalFormEntityProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#experimentalFormEntity properties in ExperimentalForm model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Gene.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.Gene experimentalFormEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getGene(resource,_model);
				this.experimentalFormEntity_asGene.add(experimentalFormEntity);
			}
		}
	}

	public java.util.Iterator getExperimentalFormEntity_asGene() throws JastorException {
		if (experimentalFormEntity_asGene == null)
			initExperimentalFormEntity_asGene();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(experimentalFormEntity_asGene,_resource,experimentalFormEntityProperty,true);
	}

	public void addExperimentalFormEntity(fr.curie.BiNoM.pathways.biopax.Gene experimentalFormEntity) throws JastorException {
		if (this.experimentalFormEntity_asGene == null)
			initExperimentalFormEntity_asGene();
		if (this.experimentalFormEntity_asGene.contains(experimentalFormEntity)) {
			this.experimentalFormEntity_asGene.remove(experimentalFormEntity);
			this.experimentalFormEntity_asGene.add(experimentalFormEntity);
			return;
		}
		this.experimentalFormEntity_asGene.add(experimentalFormEntity);
		_model.add(_model.createStatement(_resource,experimentalFormEntityProperty,experimentalFormEntity.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Gene addExperimentalFormEntity_asGene() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Gene experimentalFormEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createGene(_model.createResource(),_model);
		if (this.experimentalFormEntity_asGene == null)
			initExperimentalFormEntity_asGene();
		this.experimentalFormEntity_asGene.add(experimentalFormEntity);
		_model.add(_model.createStatement(_resource,experimentalFormEntityProperty,experimentalFormEntity.resource()));
		return experimentalFormEntity;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Gene addExperimentalFormEntity_asGene(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Gene experimentalFormEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getGene(resource,_model);
		if (this.experimentalFormEntity_asGene == null)
			initExperimentalFormEntity_asGene();
		if (this.experimentalFormEntity_asGene.contains(experimentalFormEntity))
			return experimentalFormEntity;
		this.experimentalFormEntity_asGene.add(experimentalFormEntity);
		_model.add(_model.createStatement(_resource,experimentalFormEntityProperty,experimentalFormEntity.resource()));
		return experimentalFormEntity;
	}
	
	public void removeExperimentalFormEntity(fr.curie.BiNoM.pathways.biopax.Gene experimentalFormEntity) throws JastorException {
		if (this.experimentalFormEntity_asGene == null)
			initExperimentalFormEntity_asGene();
		if (!this.experimentalFormEntity_asGene.contains(experimentalFormEntity))
			return;
		if (!_model.contains(_resource, experimentalFormEntityProperty, experimentalFormEntity.resource()))
			return;
		this.experimentalFormEntity_asGene.remove(experimentalFormEntity);
		_model.removeAll(_resource, experimentalFormEntityProperty, experimentalFormEntity.resource());
	}
		
	private void initExperimentalFormEntity_asPhysicalEntity() throws JastorException {
		this.experimentalFormEntity_asPhysicalEntity = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, experimentalFormEntityProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#experimentalFormEntity properties in ExperimentalForm model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.PhysicalEntity.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.PhysicalEntity experimentalFormEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
				this.experimentalFormEntity_asPhysicalEntity.add(experimentalFormEntity);
			}
		}
	}

	public java.util.Iterator getExperimentalFormEntity_asPhysicalEntity() throws JastorException {
		if (experimentalFormEntity_asPhysicalEntity == null)
			initExperimentalFormEntity_asPhysicalEntity();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(experimentalFormEntity_asPhysicalEntity,_resource,experimentalFormEntityProperty,true);
	}

	public void addExperimentalFormEntity(fr.curie.BiNoM.pathways.biopax.PhysicalEntity experimentalFormEntity) throws JastorException {
		if (this.experimentalFormEntity_asPhysicalEntity == null)
			initExperimentalFormEntity_asPhysicalEntity();
		if (this.experimentalFormEntity_asPhysicalEntity.contains(experimentalFormEntity)) {
			this.experimentalFormEntity_asPhysicalEntity.remove(experimentalFormEntity);
			this.experimentalFormEntity_asPhysicalEntity.add(experimentalFormEntity);
			return;
		}
		this.experimentalFormEntity_asPhysicalEntity.add(experimentalFormEntity);
		_model.add(_model.createStatement(_resource,experimentalFormEntityProperty,experimentalFormEntity.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addExperimentalFormEntity_asPhysicalEntity() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PhysicalEntity experimentalFormEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createPhysicalEntity(_model.createResource(),_model);
		if (this.experimentalFormEntity_asPhysicalEntity == null)
			initExperimentalFormEntity_asPhysicalEntity();
		this.experimentalFormEntity_asPhysicalEntity.add(experimentalFormEntity);
		_model.add(_model.createStatement(_resource,experimentalFormEntityProperty,experimentalFormEntity.resource()));
		return experimentalFormEntity;
	}
	
	public fr.curie.BiNoM.pathways.biopax.PhysicalEntity addExperimentalFormEntity_asPhysicalEntity(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PhysicalEntity experimentalFormEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
		if (this.experimentalFormEntity_asPhysicalEntity == null)
			initExperimentalFormEntity_asPhysicalEntity();
		if (this.experimentalFormEntity_asPhysicalEntity.contains(experimentalFormEntity))
			return experimentalFormEntity;
		this.experimentalFormEntity_asPhysicalEntity.add(experimentalFormEntity);
		_model.add(_model.createStatement(_resource,experimentalFormEntityProperty,experimentalFormEntity.resource()));
		return experimentalFormEntity;
	}
	
	public void removeExperimentalFormEntity(fr.curie.BiNoM.pathways.biopax.PhysicalEntity experimentalFormEntity) throws JastorException {
		if (this.experimentalFormEntity_asPhysicalEntity == null)
			initExperimentalFormEntity_asPhysicalEntity();
		if (!this.experimentalFormEntity_asPhysicalEntity.contains(experimentalFormEntity))
			return;
		if (!_model.contains(_resource, experimentalFormEntityProperty, experimentalFormEntity.resource()))
			return;
		this.experimentalFormEntity_asPhysicalEntity.remove(experimentalFormEntity);
		_model.removeAll(_resource, experimentalFormEntityProperty, experimentalFormEntity.resource());
	}
		  


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof ExperimentalFormListener))
			throw new IllegalArgumentException("ThingListener must be instance of ExperimentalFormListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((ExperimentalFormListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof ExperimentalFormListener))
			throw new IllegalArgumentException("ThingListener must be instance of ExperimentalFormListener"); 
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
						ExperimentalFormListener listener=(ExperimentalFormListener)iter.next();
						listener.commentAdded(fr.curie.BiNoM.pathways.biopax.ExperimentalFormImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(experimentalFeatureProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.EntityFeature _experimentalFeature = null;
					try {
						_experimentalFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (experimentalFeature == null) {
						try {
							initExperimentalFeature();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!experimentalFeature.contains(_experimentalFeature))
						experimentalFeature.add(_experimentalFeature);
					if (listeners != null) {
						java.util.ArrayList consumersForExperimentalFeature;
						synchronized (listeners) {
							consumersForExperimentalFeature = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForExperimentalFeature.iterator();iter.hasNext();){
							ExperimentalFormListener listener=(ExperimentalFormListener)iter.next();
							listener.experimentalFeatureAdded(fr.curie.BiNoM.pathways.biopax.ExperimentalFormImpl.this,_experimentalFeature);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(experimentalFormDescriptionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary _experimentalFormDescription = null;
					try {
						_experimentalFormDescription = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getExperimentalFormVocabulary(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (experimentalFormDescription == null) {
						try {
							initExperimentalFormDescription();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!experimentalFormDescription.contains(_experimentalFormDescription))
						experimentalFormDescription.add(_experimentalFormDescription);
					if (listeners != null) {
						java.util.ArrayList consumersForExperimentalFormDescription;
						synchronized (listeners) {
							consumersForExperimentalFormDescription = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForExperimentalFormDescription.iterator();iter.hasNext();){
							ExperimentalFormListener listener=(ExperimentalFormListener)iter.next();
							listener.experimentalFormDescriptionAdded(fr.curie.BiNoM.pathways.biopax.ExperimentalFormImpl.this,_experimentalFormDescription);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(experimentalFormEntityProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Gene.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Gene _experimentalFormEntity_asGene = null;
					try {
						_experimentalFormEntity_asGene = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getGene(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (experimentalFormEntity_asGene == null) {
						try {
							initExperimentalFormEntity_asGene();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!experimentalFormEntity_asGene.contains(_experimentalFormEntity_asGene))
						experimentalFormEntity_asGene.add(_experimentalFormEntity_asGene);
					if (listeners != null) {
						java.util.ArrayList consumersForExperimentalFormEntity_asGene;
						synchronized (listeners) {
							consumersForExperimentalFormEntity_asGene = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForExperimentalFormEntity_asGene.iterator();iter.hasNext();){
							ExperimentalFormListener listener=(ExperimentalFormListener)iter.next();
							listener.experimentalFormEntityAdded(fr.curie.BiNoM.pathways.biopax.ExperimentalFormImpl.this,_experimentalFormEntity_asGene);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.PhysicalEntity.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.PhysicalEntity _experimentalFormEntity_asPhysicalEntity = null;
					try {
						_experimentalFormEntity_asPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (experimentalFormEntity_asPhysicalEntity == null) {
						try {
							initExperimentalFormEntity_asPhysicalEntity();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!experimentalFormEntity_asPhysicalEntity.contains(_experimentalFormEntity_asPhysicalEntity))
						experimentalFormEntity_asPhysicalEntity.add(_experimentalFormEntity_asPhysicalEntity);
					if (listeners != null) {
						java.util.ArrayList consumersForExperimentalFormEntity_asPhysicalEntity;
						synchronized (listeners) {
							consumersForExperimentalFormEntity_asPhysicalEntity = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForExperimentalFormEntity_asPhysicalEntity.iterator();iter.hasNext();){
							ExperimentalFormListener listener=(ExperimentalFormListener)iter.next();
							listener.experimentalFormEntityAdded(fr.curie.BiNoM.pathways.biopax.ExperimentalFormImpl.this,_experimentalFormEntity_asPhysicalEntity);
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
						ExperimentalFormListener listener=(ExperimentalFormListener)iter.next();
						listener.commentRemoved(fr.curie.BiNoM.pathways.biopax.ExperimentalFormImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(experimentalFeatureProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.EntityFeature _experimentalFeature = null;
					if (experimentalFeature != null) {
						boolean found = false;
						for (int i=0;i<experimentalFeature.size();i++) {
							fr.curie.BiNoM.pathways.biopax.EntityFeature __item = (fr.curie.BiNoM.pathways.biopax.EntityFeature) experimentalFeature.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_experimentalFeature = __item;
								break;
							}
						}
						if (found)
							experimentalFeature.remove(_experimentalFeature);
						else {
							try {
								_experimentalFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_experimentalFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForExperimentalFeature;
						synchronized (listeners) {
							consumersForExperimentalFeature = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForExperimentalFeature.iterator();iter.hasNext();){
							ExperimentalFormListener listener=(ExperimentalFormListener)iter.next();
							listener.experimentalFeatureRemoved(fr.curie.BiNoM.pathways.biopax.ExperimentalFormImpl.this,_experimentalFeature);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(experimentalFormDescriptionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary _experimentalFormDescription = null;
					if (experimentalFormDescription != null) {
						boolean found = false;
						for (int i=0;i<experimentalFormDescription.size();i++) {
							fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary __item = (fr.curie.BiNoM.pathways.biopax.ExperimentalFormVocabulary) experimentalFormDescription.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_experimentalFormDescription = __item;
								break;
							}
						}
						if (found)
							experimentalFormDescription.remove(_experimentalFormDescription);
						else {
							try {
								_experimentalFormDescription = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getExperimentalFormVocabulary(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_experimentalFormDescription = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getExperimentalFormVocabulary(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForExperimentalFormDescription;
						synchronized (listeners) {
							consumersForExperimentalFormDescription = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForExperimentalFormDescription.iterator();iter.hasNext();){
							ExperimentalFormListener listener=(ExperimentalFormListener)iter.next();
							listener.experimentalFormDescriptionRemoved(fr.curie.BiNoM.pathways.biopax.ExperimentalFormImpl.this,_experimentalFormDescription);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(experimentalFormEntityProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Gene.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Gene _experimentalFormEntity_asGene = null;
					if (experimentalFormEntity_asGene != null) {
						boolean found = false;
						for (int i=0;i<experimentalFormEntity_asGene.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Gene __item = (fr.curie.BiNoM.pathways.biopax.Gene) experimentalFormEntity_asGene.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_experimentalFormEntity_asGene = __item;
								break;
							}
						}
						if (found)
							experimentalFormEntity_asGene.remove(_experimentalFormEntity_asGene);
						else {
							try {
								_experimentalFormEntity_asGene = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getGene(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_experimentalFormEntity_asGene = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getGene(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForExperimentalFormEntity_asGene;
						synchronized (listeners) {
							consumersForExperimentalFormEntity_asGene = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForExperimentalFormEntity_asGene.iterator();iter.hasNext();){
							ExperimentalFormListener listener=(ExperimentalFormListener)iter.next();
							listener.experimentalFormEntityRemoved(fr.curie.BiNoM.pathways.biopax.ExperimentalFormImpl.this,_experimentalFormEntity_asGene);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.PhysicalEntity.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.PhysicalEntity _experimentalFormEntity_asPhysicalEntity = null;
					if (experimentalFormEntity_asPhysicalEntity != null) {
						boolean found = false;
						for (int i=0;i<experimentalFormEntity_asPhysicalEntity.size();i++) {
							fr.curie.BiNoM.pathways.biopax.PhysicalEntity __item = (fr.curie.BiNoM.pathways.biopax.PhysicalEntity) experimentalFormEntity_asPhysicalEntity.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_experimentalFormEntity_asPhysicalEntity = __item;
								break;
							}
						}
						if (found)
							experimentalFormEntity_asPhysicalEntity.remove(_experimentalFormEntity_asPhysicalEntity);
						else {
							try {
								_experimentalFormEntity_asPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_experimentalFormEntity_asPhysicalEntity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhysicalEntity(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForExperimentalFormEntity_asPhysicalEntity;
						synchronized (listeners) {
							consumersForExperimentalFormEntity_asPhysicalEntity = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForExperimentalFormEntity_asPhysicalEntity.iterator();iter.hasNext();){
							ExperimentalFormListener listener=(ExperimentalFormListener)iter.next();
							listener.experimentalFormEntityRemoved(fr.curie.BiNoM.pathways.biopax.ExperimentalFormImpl.this,_experimentalFormEntity_asPhysicalEntity);
						}
					}
				}
				return;
			}
		}

	//}
	


}