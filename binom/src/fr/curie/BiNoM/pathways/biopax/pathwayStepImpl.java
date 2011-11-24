

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.pathwayStep}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#pathwayStep)</p>
 * <br>
 */
public class pathwayStepImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.pathwayStep {
	

	private static com.hp.hpl.jena.rdf.model.Property COMMENTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#COMMENT");
	private java.util.ArrayList COMMENT;
	private static com.hp.hpl.jena.rdf.model.Property NEXT_DASH_STEPProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#NEXT-STEP");
	private java.util.ArrayList NEXT_DASH_STEP;
	private static com.hp.hpl.jena.rdf.model.Property STEP_DASH_INTERACTIONSProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#STEP-INTERACTIONS");
	private java.util.ArrayList STEP_DASH_INTERACTIONS_asinteraction;
	private java.util.ArrayList STEP_DASH_INTERACTIONS_aspathway;

	pathwayStepImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static pathwayStepImpl getpathwayStep(Resource resource, Model model) throws JastorException {
		return new pathwayStepImpl(resource, model);
	}
	    
	static pathwayStepImpl createpathwayStep(Resource resource, Model model) throws JastorException {
		pathwayStepImpl impl = new pathwayStepImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, pathwayStep.TYPE)))
			impl._model.add(impl._resource, RDF.type, pathwayStep.TYPE);
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
		it = _model.listStatements(_resource,NEXT_DASH_STEPProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,STEP_DASH_INTERACTIONSProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.pathwayStep.TYPE);
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
		NEXT_DASH_STEP = null;
		STEP_DASH_INTERACTIONS_asinteraction = null;
		STEP_DASH_INTERACTIONS_aspathway = null;
	}


	private void initCOMMENT() throws JastorException {
		COMMENT = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, COMMENTProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#COMMENT properties in pathwayStep model not a Literal", stmt.getObject());
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


	private void initNEXT_DASH_STEP() throws JastorException {
		this.NEXT_DASH_STEP = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, NEXT_DASH_STEPProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#NEXT-STEP properties in pathwayStep model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.pathwayStep NEXT_DASH_STEP = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathwayStep(resource,_model);
				this.NEXT_DASH_STEP.add(NEXT_DASH_STEP);
			}
		}
	}

	public java.util.Iterator getNEXT_DASH_STEP() throws JastorException {
		if (NEXT_DASH_STEP == null)
			initNEXT_DASH_STEP();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(NEXT_DASH_STEP,_resource,NEXT_DASH_STEPProperty,true);
	}

	public void addNEXT_DASH_STEP(fr.curie.BiNoM.pathways.biopax.pathwayStep NEXT_DASH_STEP) throws JastorException {
		if (this.NEXT_DASH_STEP == null)
			initNEXT_DASH_STEP();
		if (this.NEXT_DASH_STEP.contains(NEXT_DASH_STEP)) {
			this.NEXT_DASH_STEP.remove(NEXT_DASH_STEP);
			this.NEXT_DASH_STEP.add(NEXT_DASH_STEP);
			return;
		}
		this.NEXT_DASH_STEP.add(NEXT_DASH_STEP);
		_model.add(_model.createStatement(_resource,NEXT_DASH_STEPProperty,NEXT_DASH_STEP.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.pathwayStep addNEXT_DASH_STEP() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.pathwayStep NEXT_DASH_STEP = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createpathwayStep(_model.createResource(),_model);
		if (this.NEXT_DASH_STEP == null)
			initNEXT_DASH_STEP();
		this.NEXT_DASH_STEP.add(NEXT_DASH_STEP);
		_model.add(_model.createStatement(_resource,NEXT_DASH_STEPProperty,NEXT_DASH_STEP.resource()));
		return NEXT_DASH_STEP;
	}
	
	public fr.curie.BiNoM.pathways.biopax.pathwayStep addNEXT_DASH_STEP(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.pathwayStep NEXT_DASH_STEP = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathwayStep(resource,_model);
		if (this.NEXT_DASH_STEP == null)
			initNEXT_DASH_STEP();
		if (this.NEXT_DASH_STEP.contains(NEXT_DASH_STEP))
			return NEXT_DASH_STEP;
		this.NEXT_DASH_STEP.add(NEXT_DASH_STEP);
		_model.add(_model.createStatement(_resource,NEXT_DASH_STEPProperty,NEXT_DASH_STEP.resource()));
		return NEXT_DASH_STEP;
	}
	
	public void removeNEXT_DASH_STEP(fr.curie.BiNoM.pathways.biopax.pathwayStep NEXT_DASH_STEP) throws JastorException {
		if (this.NEXT_DASH_STEP == null)
			initNEXT_DASH_STEP();
		if (!this.NEXT_DASH_STEP.contains(NEXT_DASH_STEP))
			return;
		if (!_model.contains(_resource, NEXT_DASH_STEPProperty, NEXT_DASH_STEP.resource()))
			return;
		this.NEXT_DASH_STEP.remove(NEXT_DASH_STEP);
		_model.removeAll(_resource, NEXT_DASH_STEPProperty, NEXT_DASH_STEP.resource());
	}
		 

	private void initSTEP_DASH_INTERACTIONS_asinteraction() throws JastorException {
		this.STEP_DASH_INTERACTIONS_asinteraction = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, STEP_DASH_INTERACTIONSProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#STEP-INTERACTIONS properties in pathwayStep model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.interaction.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.interaction STEP_DASH_INTERACTIONS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getinteraction(resource,_model);
				this.STEP_DASH_INTERACTIONS_asinteraction.add(STEP_DASH_INTERACTIONS);
			}
		}
	}

	public java.util.Iterator getSTEP_DASH_INTERACTIONS_asinteraction() throws JastorException {
		if (STEP_DASH_INTERACTIONS_asinteraction == null)
			initSTEP_DASH_INTERACTIONS_asinteraction();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(STEP_DASH_INTERACTIONS_asinteraction,_resource,STEP_DASH_INTERACTIONSProperty,true);
	}

	public void addSTEP_DASH_INTERACTIONS(fr.curie.BiNoM.pathways.biopax.interaction STEP_DASH_INTERACTIONS) throws JastorException {
		if (this.STEP_DASH_INTERACTIONS_asinteraction == null)
			initSTEP_DASH_INTERACTIONS_asinteraction();
		if (this.STEP_DASH_INTERACTIONS_asinteraction.contains(STEP_DASH_INTERACTIONS)) {
			this.STEP_DASH_INTERACTIONS_asinteraction.remove(STEP_DASH_INTERACTIONS);
			this.STEP_DASH_INTERACTIONS_asinteraction.add(STEP_DASH_INTERACTIONS);
			return;
		}
		this.STEP_DASH_INTERACTIONS_asinteraction.add(STEP_DASH_INTERACTIONS);
		_model.add(_model.createStatement(_resource,STEP_DASH_INTERACTIONSProperty,STEP_DASH_INTERACTIONS.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.interaction addSTEP_DASH_INTERACTIONS_asinteraction() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.interaction STEP_DASH_INTERACTIONS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createinteraction(_model.createResource(),_model);
		if (this.STEP_DASH_INTERACTIONS_asinteraction == null)
			initSTEP_DASH_INTERACTIONS_asinteraction();
		this.STEP_DASH_INTERACTIONS_asinteraction.add(STEP_DASH_INTERACTIONS);
		_model.add(_model.createStatement(_resource,STEP_DASH_INTERACTIONSProperty,STEP_DASH_INTERACTIONS.resource()));
		return STEP_DASH_INTERACTIONS;
	}
	
	public fr.curie.BiNoM.pathways.biopax.interaction addSTEP_DASH_INTERACTIONS_asinteraction(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.interaction STEP_DASH_INTERACTIONS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getinteraction(resource,_model);
		if (this.STEP_DASH_INTERACTIONS_asinteraction == null)
			initSTEP_DASH_INTERACTIONS_asinteraction();
		if (this.STEP_DASH_INTERACTIONS_asinteraction.contains(STEP_DASH_INTERACTIONS))
			return STEP_DASH_INTERACTIONS;
		this.STEP_DASH_INTERACTIONS_asinteraction.add(STEP_DASH_INTERACTIONS);
		_model.add(_model.createStatement(_resource,STEP_DASH_INTERACTIONSProperty,STEP_DASH_INTERACTIONS.resource()));
		return STEP_DASH_INTERACTIONS;
	}
	
	public void removeSTEP_DASH_INTERACTIONS(fr.curie.BiNoM.pathways.biopax.interaction STEP_DASH_INTERACTIONS) throws JastorException {
		if (this.STEP_DASH_INTERACTIONS_asinteraction == null)
			initSTEP_DASH_INTERACTIONS_asinteraction();
		if (!this.STEP_DASH_INTERACTIONS_asinteraction.contains(STEP_DASH_INTERACTIONS))
			return;
		if (!_model.contains(_resource, STEP_DASH_INTERACTIONSProperty, STEP_DASH_INTERACTIONS.resource()))
			return;
		this.STEP_DASH_INTERACTIONS_asinteraction.remove(STEP_DASH_INTERACTIONS);
		_model.removeAll(_resource, STEP_DASH_INTERACTIONSProperty, STEP_DASH_INTERACTIONS.resource());
	}
		
	private void initSTEP_DASH_INTERACTIONS_aspathway() throws JastorException {
		this.STEP_DASH_INTERACTIONS_aspathway = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, STEP_DASH_INTERACTIONSProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#STEP-INTERACTIONS properties in pathwayStep model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.pathway.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.pathway STEP_DASH_INTERACTIONS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathway(resource,_model);
				this.STEP_DASH_INTERACTIONS_aspathway.add(STEP_DASH_INTERACTIONS);
			}
		}
	}

	public java.util.Iterator getSTEP_DASH_INTERACTIONS_aspathway() throws JastorException {
		if (STEP_DASH_INTERACTIONS_aspathway == null)
			initSTEP_DASH_INTERACTIONS_aspathway();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(STEP_DASH_INTERACTIONS_aspathway,_resource,STEP_DASH_INTERACTIONSProperty,true);
	}

	public void addSTEP_DASH_INTERACTIONS(fr.curie.BiNoM.pathways.biopax.pathway STEP_DASH_INTERACTIONS) throws JastorException {
		if (this.STEP_DASH_INTERACTIONS_aspathway == null)
			initSTEP_DASH_INTERACTIONS_aspathway();
		if (this.STEP_DASH_INTERACTIONS_aspathway.contains(STEP_DASH_INTERACTIONS)) {
			this.STEP_DASH_INTERACTIONS_aspathway.remove(STEP_DASH_INTERACTIONS);
			this.STEP_DASH_INTERACTIONS_aspathway.add(STEP_DASH_INTERACTIONS);
			return;
		}
		this.STEP_DASH_INTERACTIONS_aspathway.add(STEP_DASH_INTERACTIONS);
		_model.add(_model.createStatement(_resource,STEP_DASH_INTERACTIONSProperty,STEP_DASH_INTERACTIONS.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.pathway addSTEP_DASH_INTERACTIONS_aspathway() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.pathway STEP_DASH_INTERACTIONS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createpathway(_model.createResource(),_model);
		if (this.STEP_DASH_INTERACTIONS_aspathway == null)
			initSTEP_DASH_INTERACTIONS_aspathway();
		this.STEP_DASH_INTERACTIONS_aspathway.add(STEP_DASH_INTERACTIONS);
		_model.add(_model.createStatement(_resource,STEP_DASH_INTERACTIONSProperty,STEP_DASH_INTERACTIONS.resource()));
		return STEP_DASH_INTERACTIONS;
	}
	
	public fr.curie.BiNoM.pathways.biopax.pathway addSTEP_DASH_INTERACTIONS_aspathway(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.pathway STEP_DASH_INTERACTIONS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathway(resource,_model);
		if (this.STEP_DASH_INTERACTIONS_aspathway == null)
			initSTEP_DASH_INTERACTIONS_aspathway();
		if (this.STEP_DASH_INTERACTIONS_aspathway.contains(STEP_DASH_INTERACTIONS))
			return STEP_DASH_INTERACTIONS;
		this.STEP_DASH_INTERACTIONS_aspathway.add(STEP_DASH_INTERACTIONS);
		_model.add(_model.createStatement(_resource,STEP_DASH_INTERACTIONSProperty,STEP_DASH_INTERACTIONS.resource()));
		return STEP_DASH_INTERACTIONS;
	}
	
	public void removeSTEP_DASH_INTERACTIONS(fr.curie.BiNoM.pathways.biopax.pathway STEP_DASH_INTERACTIONS) throws JastorException {
		if (this.STEP_DASH_INTERACTIONS_aspathway == null)
			initSTEP_DASH_INTERACTIONS_aspathway();
		if (!this.STEP_DASH_INTERACTIONS_aspathway.contains(STEP_DASH_INTERACTIONS))
			return;
		if (!_model.contains(_resource, STEP_DASH_INTERACTIONSProperty, STEP_DASH_INTERACTIONS.resource()))
			return;
		this.STEP_DASH_INTERACTIONS_aspathway.remove(STEP_DASH_INTERACTIONS);
		_model.removeAll(_resource, STEP_DASH_INTERACTIONSProperty, STEP_DASH_INTERACTIONS.resource());
	}
		  


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof pathwayStepListener))
			throw new IllegalArgumentException("ThingListener must be instance of pathwayStepListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((pathwayStepListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof pathwayStepListener))
			throw new IllegalArgumentException("ThingListener must be instance of pathwayStepListener"); 
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
						pathwayStepListener listener=(pathwayStepListener)iter.next();
						listener.COMMENTAdded(fr.curie.BiNoM.pathways.biopax.pathwayStepImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(NEXT_DASH_STEPProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.pathwayStep _NEXT_DASH_STEP = null;
					try {
						_NEXT_DASH_STEP = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathwayStep(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (NEXT_DASH_STEP == null) {
						try {
							initNEXT_DASH_STEP();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!NEXT_DASH_STEP.contains(_NEXT_DASH_STEP))
						NEXT_DASH_STEP.add(_NEXT_DASH_STEP);
					if (listeners != null) {
						java.util.ArrayList consumersForNEXT_DASH_STEP;
						synchronized (listeners) {
							consumersForNEXT_DASH_STEP = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForNEXT_DASH_STEP.iterator();iter.hasNext();){
							pathwayStepListener listener=(pathwayStepListener)iter.next();
							listener.NEXT_DASH_STEPAdded(fr.curie.BiNoM.pathways.biopax.pathwayStepImpl.this,_NEXT_DASH_STEP);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(STEP_DASH_INTERACTIONSProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.interaction.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.interaction _STEP_DASH_INTERACTIONS_asinteraction = null;
					try {
						_STEP_DASH_INTERACTIONS_asinteraction = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getinteraction(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (STEP_DASH_INTERACTIONS_asinteraction == null) {
						try {
							initSTEP_DASH_INTERACTIONS_asinteraction();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!STEP_DASH_INTERACTIONS_asinteraction.contains(_STEP_DASH_INTERACTIONS_asinteraction))
						STEP_DASH_INTERACTIONS_asinteraction.add(_STEP_DASH_INTERACTIONS_asinteraction);
					if (listeners != null) {
						java.util.ArrayList consumersForSTEP_DASH_INTERACTIONS_asinteraction;
						synchronized (listeners) {
							consumersForSTEP_DASH_INTERACTIONS_asinteraction = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForSTEP_DASH_INTERACTIONS_asinteraction.iterator();iter.hasNext();){
							pathwayStepListener listener=(pathwayStepListener)iter.next();
							listener.STEP_DASH_INTERACTIONSAdded(fr.curie.BiNoM.pathways.biopax.pathwayStepImpl.this,_STEP_DASH_INTERACTIONS_asinteraction);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.pathway.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.pathway _STEP_DASH_INTERACTIONS_aspathway = null;
					try {
						_STEP_DASH_INTERACTIONS_aspathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathway(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (STEP_DASH_INTERACTIONS_aspathway == null) {
						try {
							initSTEP_DASH_INTERACTIONS_aspathway();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!STEP_DASH_INTERACTIONS_aspathway.contains(_STEP_DASH_INTERACTIONS_aspathway))
						STEP_DASH_INTERACTIONS_aspathway.add(_STEP_DASH_INTERACTIONS_aspathway);
					if (listeners != null) {
						java.util.ArrayList consumersForSTEP_DASH_INTERACTIONS_aspathway;
						synchronized (listeners) {
							consumersForSTEP_DASH_INTERACTIONS_aspathway = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForSTEP_DASH_INTERACTIONS_aspathway.iterator();iter.hasNext();){
							pathwayStepListener listener=(pathwayStepListener)iter.next();
							listener.STEP_DASH_INTERACTIONSAdded(fr.curie.BiNoM.pathways.biopax.pathwayStepImpl.this,_STEP_DASH_INTERACTIONS_aspathway);
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
						pathwayStepListener listener=(pathwayStepListener)iter.next();
						listener.COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.pathwayStepImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(NEXT_DASH_STEPProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.pathwayStep _NEXT_DASH_STEP = null;
					if (NEXT_DASH_STEP != null) {
						boolean found = false;
						for (int i=0;i<NEXT_DASH_STEP.size();i++) {
							fr.curie.BiNoM.pathways.biopax.pathwayStep __item = (fr.curie.BiNoM.pathways.biopax.pathwayStep) NEXT_DASH_STEP.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_NEXT_DASH_STEP = __item;
								break;
							}
						}
						if (found)
							NEXT_DASH_STEP.remove(_NEXT_DASH_STEP);
						else {
							try {
								_NEXT_DASH_STEP = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathwayStep(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_NEXT_DASH_STEP = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathwayStep(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForNEXT_DASH_STEP;
						synchronized (listeners) {
							consumersForNEXT_DASH_STEP = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForNEXT_DASH_STEP.iterator();iter.hasNext();){
							pathwayStepListener listener=(pathwayStepListener)iter.next();
							listener.NEXT_DASH_STEPRemoved(fr.curie.BiNoM.pathways.biopax.pathwayStepImpl.this,_NEXT_DASH_STEP);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(STEP_DASH_INTERACTIONSProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.interaction.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.interaction _STEP_DASH_INTERACTIONS_asinteraction = null;
					if (STEP_DASH_INTERACTIONS_asinteraction != null) {
						boolean found = false;
						for (int i=0;i<STEP_DASH_INTERACTIONS_asinteraction.size();i++) {
							fr.curie.BiNoM.pathways.biopax.interaction __item = (fr.curie.BiNoM.pathways.biopax.interaction) STEP_DASH_INTERACTIONS_asinteraction.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_STEP_DASH_INTERACTIONS_asinteraction = __item;
								break;
							}
						}
						if (found)
							STEP_DASH_INTERACTIONS_asinteraction.remove(_STEP_DASH_INTERACTIONS_asinteraction);
						else {
							try {
								_STEP_DASH_INTERACTIONS_asinteraction = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getinteraction(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_STEP_DASH_INTERACTIONS_asinteraction = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getinteraction(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForSTEP_DASH_INTERACTIONS_asinteraction;
						synchronized (listeners) {
							consumersForSTEP_DASH_INTERACTIONS_asinteraction = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForSTEP_DASH_INTERACTIONS_asinteraction.iterator();iter.hasNext();){
							pathwayStepListener listener=(pathwayStepListener)iter.next();
							listener.STEP_DASH_INTERACTIONSRemoved(fr.curie.BiNoM.pathways.biopax.pathwayStepImpl.this,_STEP_DASH_INTERACTIONS_asinteraction);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.pathway.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.pathway _STEP_DASH_INTERACTIONS_aspathway = null;
					if (STEP_DASH_INTERACTIONS_aspathway != null) {
						boolean found = false;
						for (int i=0;i<STEP_DASH_INTERACTIONS_aspathway.size();i++) {
							fr.curie.BiNoM.pathways.biopax.pathway __item = (fr.curie.BiNoM.pathways.biopax.pathway) STEP_DASH_INTERACTIONS_aspathway.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_STEP_DASH_INTERACTIONS_aspathway = __item;
								break;
							}
						}
						if (found)
							STEP_DASH_INTERACTIONS_aspathway.remove(_STEP_DASH_INTERACTIONS_aspathway);
						else {
							try {
								_STEP_DASH_INTERACTIONS_aspathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathway(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_STEP_DASH_INTERACTIONS_aspathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathway(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForSTEP_DASH_INTERACTIONS_aspathway;
						synchronized (listeners) {
							consumersForSTEP_DASH_INTERACTIONS_aspathway = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForSTEP_DASH_INTERACTIONS_aspathway.iterator();iter.hasNext();){
							pathwayStepListener listener=(pathwayStepListener)iter.next();
							listener.STEP_DASH_INTERACTIONSRemoved(fr.curie.BiNoM.pathways.biopax.pathwayStepImpl.this,_STEP_DASH_INTERACTIONS_aspathway);
						}
					}
				}
				return;
			}
		}

	//}
	


}