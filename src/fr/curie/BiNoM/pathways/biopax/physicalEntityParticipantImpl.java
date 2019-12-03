

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#physicalEntityParticipant)</p>
 * <br>
 */
public class physicalEntityParticipantImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant {
	

	private static com.hp.hpl.jena.rdf.model.Property COMMENTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#COMMENT");
	private java.util.ArrayList COMMENT;
	private static com.hp.hpl.jena.rdf.model.Property PHYSICAL_DASH_ENTITYProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#PHYSICAL-ENTITY");
	private fr.curie.BiNoM.pathways.biopax.physicalEntity PHYSICAL_DASH_ENTITY;
	private static com.hp.hpl.jena.rdf.model.Property STOICHIOMETRIC_DASH_COEFFICIENTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#STOICHIOMETRIC-COEFFICIENT");
	private java.lang.Double STOICHIOMETRIC_DASH_COEFFICIENT;
	private static com.hp.hpl.jena.rdf.model.Property CELLULAR_DASH_LOCATIONProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#CELLULAR-LOCATION");
	private fr.curie.BiNoM.pathways.biopax.openControlledVocabulary CELLULAR_DASH_LOCATION;

	physicalEntityParticipantImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static physicalEntityParticipantImpl getphysicalEntityParticipant(Resource resource, Model model) throws JastorException {
		return new physicalEntityParticipantImpl(resource, model);
	}
	    
	static physicalEntityParticipantImpl createphysicalEntityParticipant(Resource resource, Model model) throws JastorException {
		physicalEntityParticipantImpl impl = new physicalEntityParticipantImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, physicalEntityParticipant.TYPE)))
			impl._model.add(impl._resource, RDF.type, physicalEntityParticipant.TYPE);
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
		it = _model.listStatements(_resource,PHYSICAL_DASH_ENTITYProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,STOICHIOMETRIC_DASH_COEFFICIENTProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,CELLULAR_DASH_LOCATIONProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant.TYPE);
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
		PHYSICAL_DASH_ENTITY = null;
		STOICHIOMETRIC_DASH_COEFFICIENT = null;
		CELLULAR_DASH_LOCATION = null;
	}


	private void initCOMMENT() throws JastorException {
		COMMENT = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, COMMENTProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#COMMENT properties in physicalEntityParticipant model not a Literal", stmt.getObject());
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


	public fr.curie.BiNoM.pathways.biopax.physicalEntity getPHYSICAL_DASH_ENTITY() throws JastorException {
		if (PHYSICAL_DASH_ENTITY != null)
			return PHYSICAL_DASH_ENTITY;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, PHYSICAL_DASH_ENTITYProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": PHYSICAL_DASH_ENTITY getProperty() in fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		PHYSICAL_DASH_ENTITY = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntity(resource,_model);
		return PHYSICAL_DASH_ENTITY;
	}

	public void setPHYSICAL_DASH_ENTITY(fr.curie.BiNoM.pathways.biopax.physicalEntity PHYSICAL_DASH_ENTITY) throws JastorException {
		if (_model.contains(_resource,PHYSICAL_DASH_ENTITYProperty)) {
			_model.removeAll(_resource,PHYSICAL_DASH_ENTITYProperty,null);
		}
		this.PHYSICAL_DASH_ENTITY = PHYSICAL_DASH_ENTITY;
		if (PHYSICAL_DASH_ENTITY != null) {
			_model.add(_model.createStatement(_resource,PHYSICAL_DASH_ENTITYProperty, PHYSICAL_DASH_ENTITY.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.physicalEntity setPHYSICAL_DASH_ENTITY() throws JastorException {
		if (_model.contains(_resource,PHYSICAL_DASH_ENTITYProperty)) {
			_model.removeAll(_resource,PHYSICAL_DASH_ENTITYProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.physicalEntity PHYSICAL_DASH_ENTITY = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createphysicalEntity(_model.createResource(),_model);
		this.PHYSICAL_DASH_ENTITY = PHYSICAL_DASH_ENTITY;
		_model.add(_model.createStatement(_resource,PHYSICAL_DASH_ENTITYProperty, PHYSICAL_DASH_ENTITY.resource()));
		return PHYSICAL_DASH_ENTITY;
	}
	
	public fr.curie.BiNoM.pathways.biopax.physicalEntity setPHYSICAL_DASH_ENTITY(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,PHYSICAL_DASH_ENTITYProperty)) {
			_model.removeAll(_resource,PHYSICAL_DASH_ENTITYProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.physicalEntity PHYSICAL_DASH_ENTITY = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntity(resource,_model);
		this.PHYSICAL_DASH_ENTITY = PHYSICAL_DASH_ENTITY;
		_model.add(_model.createStatement(_resource,PHYSICAL_DASH_ENTITYProperty, PHYSICAL_DASH_ENTITY.resource()));
		return PHYSICAL_DASH_ENTITY;
	}
	
	public java.lang.Double getSTOICHIOMETRIC_DASH_COEFFICIENT() throws JastorException {
		if (STOICHIOMETRIC_DASH_COEFFICIENT != null)
			return STOICHIOMETRIC_DASH_COEFFICIENT;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, STOICHIOMETRIC_DASH_COEFFICIENTProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": STOICHIOMETRIC_DASH_COEFFICIENT getProperty() in fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Double");
		STOICHIOMETRIC_DASH_COEFFICIENT = (java.lang.Double)obj;
		return STOICHIOMETRIC_DASH_COEFFICIENT;
	}
	
	public void setSTOICHIOMETRIC_DASH_COEFFICIENT(java.lang.Double STOICHIOMETRIC_DASH_COEFFICIENT) throws JastorException {
		if (_model.contains(_resource,STOICHIOMETRIC_DASH_COEFFICIENTProperty)) {
			_model.removeAll(_resource,STOICHIOMETRIC_DASH_COEFFICIENTProperty,null);
		}
		this.STOICHIOMETRIC_DASH_COEFFICIENT = STOICHIOMETRIC_DASH_COEFFICIENT;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (STOICHIOMETRIC_DASH_COEFFICIENT != null) {
			_model.add(_model.createStatement(_resource,STOICHIOMETRIC_DASH_COEFFICIENTProperty, _model.createTypedLiteral(STOICHIOMETRIC_DASH_COEFFICIENT)));
		}	
	}


	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary getCELLULAR_DASH_LOCATION() throws JastorException {
		if (CELLULAR_DASH_LOCATION != null)
			return CELLULAR_DASH_LOCATION;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, CELLULAR_DASH_LOCATIONProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": CELLULAR_DASH_LOCATION getProperty() in fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		CELLULAR_DASH_LOCATION = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
		return CELLULAR_DASH_LOCATION;
	}

	public void setCELLULAR_DASH_LOCATION(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary CELLULAR_DASH_LOCATION) throws JastorException {
		if (_model.contains(_resource,CELLULAR_DASH_LOCATIONProperty)) {
			_model.removeAll(_resource,CELLULAR_DASH_LOCATIONProperty,null);
		}
		this.CELLULAR_DASH_LOCATION = CELLULAR_DASH_LOCATION;
		if (CELLULAR_DASH_LOCATION != null) {
			_model.add(_model.createStatement(_resource,CELLULAR_DASH_LOCATIONProperty, CELLULAR_DASH_LOCATION.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary setCELLULAR_DASH_LOCATION() throws JastorException {
		if (_model.contains(_resource,CELLULAR_DASH_LOCATIONProperty)) {
			_model.removeAll(_resource,CELLULAR_DASH_LOCATIONProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.openControlledVocabulary CELLULAR_DASH_LOCATION = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createopenControlledVocabulary(_model.createResource(),_model);
		this.CELLULAR_DASH_LOCATION = CELLULAR_DASH_LOCATION;
		_model.add(_model.createStatement(_resource,CELLULAR_DASH_LOCATIONProperty, CELLULAR_DASH_LOCATION.resource()));
		return CELLULAR_DASH_LOCATION;
	}
	
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary setCELLULAR_DASH_LOCATION(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,CELLULAR_DASH_LOCATIONProperty)) {
			_model.removeAll(_resource,CELLULAR_DASH_LOCATIONProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.openControlledVocabulary CELLULAR_DASH_LOCATION = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
		this.CELLULAR_DASH_LOCATION = CELLULAR_DASH_LOCATION;
		_model.add(_model.createStatement(_resource,CELLULAR_DASH_LOCATIONProperty, CELLULAR_DASH_LOCATION.resource()));
		return CELLULAR_DASH_LOCATION;
	}
	 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof physicalEntityParticipantListener))
			throw new IllegalArgumentException("ThingListener must be instance of physicalEntityParticipantListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((physicalEntityParticipantListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof physicalEntityParticipantListener))
			throw new IllegalArgumentException("ThingListener must be instance of physicalEntityParticipantListener"); 
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
						physicalEntityParticipantListener listener=(physicalEntityParticipantListener)iter.next();
						listener.COMMENTAdded(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipantImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(PHYSICAL_DASH_ENTITYProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				PHYSICAL_DASH_ENTITY = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						PHYSICAL_DASH_ENTITY = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntity(resource,_model);
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
						physicalEntityParticipantListener listener=(physicalEntityParticipantListener)iter.next();
						listener.PHYSICAL_DASH_ENTITYChanged(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipantImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(STOICHIOMETRIC_DASH_COEFFICIENTProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				STOICHIOMETRIC_DASH_COEFFICIENT = (java.lang.Double)Util.fixLiteral(literal.getValue(),"java.lang.Double");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						physicalEntityParticipantListener listener=(physicalEntityParticipantListener)iter.next();
						listener.STOICHIOMETRIC_DASH_COEFFICIENTChanged(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipantImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(CELLULAR_DASH_LOCATIONProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				CELLULAR_DASH_LOCATION = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						CELLULAR_DASH_LOCATION = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
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
						physicalEntityParticipantListener listener=(physicalEntityParticipantListener)iter.next();
						listener.CELLULAR_DASH_LOCATIONChanged(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipantImpl.this);
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
						physicalEntityParticipantListener listener=(physicalEntityParticipantListener)iter.next();
						listener.COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipantImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(PHYSICAL_DASH_ENTITYProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (PHYSICAL_DASH_ENTITY != null && PHYSICAL_DASH_ENTITY.resource().equals(resource))
						PHYSICAL_DASH_ENTITY = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						physicalEntityParticipantListener listener=(physicalEntityParticipantListener)iter.next();
						listener.PHYSICAL_DASH_ENTITYChanged(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipantImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(STOICHIOMETRIC_DASH_COEFFICIENTProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Double");
				if (STOICHIOMETRIC_DASH_COEFFICIENT != null && STOICHIOMETRIC_DASH_COEFFICIENT.equals(obj))
					STOICHIOMETRIC_DASH_COEFFICIENT = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						physicalEntityParticipantListener listener=(physicalEntityParticipantListener)iter.next();
						listener.STOICHIOMETRIC_DASH_COEFFICIENTChanged(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipantImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(CELLULAR_DASH_LOCATIONProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (CELLULAR_DASH_LOCATION != null && CELLULAR_DASH_LOCATION.resource().equals(resource))
						CELLULAR_DASH_LOCATION = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						physicalEntityParticipantListener listener=(physicalEntityParticipantListener)iter.next();
						listener.CELLULAR_DASH_LOCATIONChanged(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipantImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}