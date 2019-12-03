

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.experimentalForm}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#experimentalForm)</p>
 * <br>
 */
public class experimentalFormImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.experimentalForm {
	

	private static com.hp.hpl.jena.rdf.model.Property COMMENTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#COMMENT");
	private java.util.ArrayList COMMENT;
	private static com.hp.hpl.jena.rdf.model.Property PARTICIPANTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#PARTICIPANT");
	private fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANT;
	private static com.hp.hpl.jena.rdf.model.Property EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#EXPERIMENTAL-FORM-TYPE");
	private java.util.ArrayList EXPERIMENTAL_DASH_FORM_DASH_TYPE;

	experimentalFormImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static experimentalFormImpl getexperimentalForm(Resource resource, Model model) throws JastorException {
		return new experimentalFormImpl(resource, model);
	}
	    
	static experimentalFormImpl createexperimentalForm(Resource resource, Model model) throws JastorException {
		experimentalFormImpl impl = new experimentalFormImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, experimentalForm.TYPE)))
			impl._model.add(impl._resource, RDF.type, experimentalForm.TYPE);
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
		it = _model.listStatements(_resource,PARTICIPANTProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.experimentalForm.TYPE);
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
		PARTICIPANT = null;
		EXPERIMENTAL_DASH_FORM_DASH_TYPE = null;
	}


	private void initCOMMENT() throws JastorException {
		COMMENT = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, COMMENTProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#COMMENT properties in experimentalForm model not a Literal", stmt.getObject());
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


	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant getPARTICIPANT() throws JastorException {
		if (PARTICIPANT != null)
			return PARTICIPANT;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, PARTICIPANTProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": PARTICIPANT getProperty() in fr.curie.BiNoM.pathways.biopax.experimentalForm model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		PARTICIPANT = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
		return PARTICIPANT;
	}

	public void setPARTICIPANT(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANT) throws JastorException {
		if (_model.contains(_resource,PARTICIPANTProperty)) {
			_model.removeAll(_resource,PARTICIPANTProperty,null);
		}
		this.PARTICIPANT = PARTICIPANT;
		if (PARTICIPANT != null) {
			_model.add(_model.createStatement(_resource,PARTICIPANTProperty, PARTICIPANT.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant setPARTICIPANT() throws JastorException {
		if (_model.contains(_resource,PARTICIPANTProperty)) {
			_model.removeAll(_resource,PARTICIPANTProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANT = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createphysicalEntityParticipant(_model.createResource(),_model);
		this.PARTICIPANT = PARTICIPANT;
		_model.add(_model.createStatement(_resource,PARTICIPANTProperty, PARTICIPANT.resource()));
		return PARTICIPANT;
	}
	
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant setPARTICIPANT(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,PARTICIPANTProperty)) {
			_model.removeAll(_resource,PARTICIPANTProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANT = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
		this.PARTICIPANT = PARTICIPANT;
		_model.add(_model.createStatement(_resource,PARTICIPANTProperty, PARTICIPANT.resource()));
		return PARTICIPANT;
	}
	

	private void initEXPERIMENTAL_DASH_FORM_DASH_TYPE() throws JastorException {
		this.EXPERIMENTAL_DASH_FORM_DASH_TYPE = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#EXPERIMENTAL-FORM-TYPE properties in experimentalForm model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.openControlledVocabulary EXPERIMENTAL_DASH_FORM_DASH_TYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
				this.EXPERIMENTAL_DASH_FORM_DASH_TYPE.add(EXPERIMENTAL_DASH_FORM_DASH_TYPE);
			}
		}
	}

	public java.util.Iterator getEXPERIMENTAL_DASH_FORM_DASH_TYPE() throws JastorException {
		if (EXPERIMENTAL_DASH_FORM_DASH_TYPE == null)
			initEXPERIMENTAL_DASH_FORM_DASH_TYPE();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(EXPERIMENTAL_DASH_FORM_DASH_TYPE,_resource,EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty,true);
	}

	public void addEXPERIMENTAL_DASH_FORM_DASH_TYPE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary EXPERIMENTAL_DASH_FORM_DASH_TYPE) throws JastorException {
		if (this.EXPERIMENTAL_DASH_FORM_DASH_TYPE == null)
			initEXPERIMENTAL_DASH_FORM_DASH_TYPE();
		if (this.EXPERIMENTAL_DASH_FORM_DASH_TYPE.contains(EXPERIMENTAL_DASH_FORM_DASH_TYPE)) {
			this.EXPERIMENTAL_DASH_FORM_DASH_TYPE.remove(EXPERIMENTAL_DASH_FORM_DASH_TYPE);
			this.EXPERIMENTAL_DASH_FORM_DASH_TYPE.add(EXPERIMENTAL_DASH_FORM_DASH_TYPE);
			return;
		}
		this.EXPERIMENTAL_DASH_FORM_DASH_TYPE.add(EXPERIMENTAL_DASH_FORM_DASH_TYPE);
		_model.add(_model.createStatement(_resource,EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty,EXPERIMENTAL_DASH_FORM_DASH_TYPE.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary addEXPERIMENTAL_DASH_FORM_DASH_TYPE() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.openControlledVocabulary EXPERIMENTAL_DASH_FORM_DASH_TYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createopenControlledVocabulary(_model.createResource(),_model);
		if (this.EXPERIMENTAL_DASH_FORM_DASH_TYPE == null)
			initEXPERIMENTAL_DASH_FORM_DASH_TYPE();
		this.EXPERIMENTAL_DASH_FORM_DASH_TYPE.add(EXPERIMENTAL_DASH_FORM_DASH_TYPE);
		_model.add(_model.createStatement(_resource,EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty,EXPERIMENTAL_DASH_FORM_DASH_TYPE.resource()));
		return EXPERIMENTAL_DASH_FORM_DASH_TYPE;
	}
	
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary addEXPERIMENTAL_DASH_FORM_DASH_TYPE(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.openControlledVocabulary EXPERIMENTAL_DASH_FORM_DASH_TYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
		if (this.EXPERIMENTAL_DASH_FORM_DASH_TYPE == null)
			initEXPERIMENTAL_DASH_FORM_DASH_TYPE();
		if (this.EXPERIMENTAL_DASH_FORM_DASH_TYPE.contains(EXPERIMENTAL_DASH_FORM_DASH_TYPE))
			return EXPERIMENTAL_DASH_FORM_DASH_TYPE;
		this.EXPERIMENTAL_DASH_FORM_DASH_TYPE.add(EXPERIMENTAL_DASH_FORM_DASH_TYPE);
		_model.add(_model.createStatement(_resource,EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty,EXPERIMENTAL_DASH_FORM_DASH_TYPE.resource()));
		return EXPERIMENTAL_DASH_FORM_DASH_TYPE;
	}
	
	public void removeEXPERIMENTAL_DASH_FORM_DASH_TYPE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary EXPERIMENTAL_DASH_FORM_DASH_TYPE) throws JastorException {
		if (this.EXPERIMENTAL_DASH_FORM_DASH_TYPE == null)
			initEXPERIMENTAL_DASH_FORM_DASH_TYPE();
		if (!this.EXPERIMENTAL_DASH_FORM_DASH_TYPE.contains(EXPERIMENTAL_DASH_FORM_DASH_TYPE))
			return;
		if (!_model.contains(_resource, EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty, EXPERIMENTAL_DASH_FORM_DASH_TYPE.resource()))
			return;
		this.EXPERIMENTAL_DASH_FORM_DASH_TYPE.remove(EXPERIMENTAL_DASH_FORM_DASH_TYPE);
		_model.removeAll(_resource, EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty, EXPERIMENTAL_DASH_FORM_DASH_TYPE.resource());
	}
		  


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof experimentalFormListener))
			throw new IllegalArgumentException("ThingListener must be instance of experimentalFormListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((experimentalFormListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof experimentalFormListener))
			throw new IllegalArgumentException("ThingListener must be instance of experimentalFormListener"); 
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
						experimentalFormListener listener=(experimentalFormListener)iter.next();
						listener.COMMENTAdded(fr.curie.BiNoM.pathways.biopax.experimentalFormImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(PARTICIPANTProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				PARTICIPANT = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						PARTICIPANT = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
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
						experimentalFormListener listener=(experimentalFormListener)iter.next();
						listener.PARTICIPANTChanged(fr.curie.BiNoM.pathways.biopax.experimentalFormImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.openControlledVocabulary _EXPERIMENTAL_DASH_FORM_DASH_TYPE = null;
					try {
						_EXPERIMENTAL_DASH_FORM_DASH_TYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (EXPERIMENTAL_DASH_FORM_DASH_TYPE == null) {
						try {
							initEXPERIMENTAL_DASH_FORM_DASH_TYPE();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!EXPERIMENTAL_DASH_FORM_DASH_TYPE.contains(_EXPERIMENTAL_DASH_FORM_DASH_TYPE))
						EXPERIMENTAL_DASH_FORM_DASH_TYPE.add(_EXPERIMENTAL_DASH_FORM_DASH_TYPE);
					if (listeners != null) {
						java.util.ArrayList consumersForEXPERIMENTAL_DASH_FORM_DASH_TYPE;
						synchronized (listeners) {
							consumersForEXPERIMENTAL_DASH_FORM_DASH_TYPE = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEXPERIMENTAL_DASH_FORM_DASH_TYPE.iterator();iter.hasNext();){
							experimentalFormListener listener=(experimentalFormListener)iter.next();
							listener.EXPERIMENTAL_DASH_FORM_DASH_TYPEAdded(fr.curie.BiNoM.pathways.biopax.experimentalFormImpl.this,_EXPERIMENTAL_DASH_FORM_DASH_TYPE);
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
						experimentalFormListener listener=(experimentalFormListener)iter.next();
						listener.COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.experimentalFormImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(PARTICIPANTProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (PARTICIPANT != null && PARTICIPANT.resource().equals(resource))
						PARTICIPANT = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						experimentalFormListener listener=(experimentalFormListener)iter.next();
						listener.PARTICIPANTChanged(fr.curie.BiNoM.pathways.biopax.experimentalFormImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(EXPERIMENTAL_DASH_FORM_DASH_TYPEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.openControlledVocabulary _EXPERIMENTAL_DASH_FORM_DASH_TYPE = null;
					if (EXPERIMENTAL_DASH_FORM_DASH_TYPE != null) {
						boolean found = false;
						for (int i=0;i<EXPERIMENTAL_DASH_FORM_DASH_TYPE.size();i++) {
							fr.curie.BiNoM.pathways.biopax.openControlledVocabulary __item = (fr.curie.BiNoM.pathways.biopax.openControlledVocabulary) EXPERIMENTAL_DASH_FORM_DASH_TYPE.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_EXPERIMENTAL_DASH_FORM_DASH_TYPE = __item;
								break;
							}
						}
						if (found)
							EXPERIMENTAL_DASH_FORM_DASH_TYPE.remove(_EXPERIMENTAL_DASH_FORM_DASH_TYPE);
						else {
							try {
								_EXPERIMENTAL_DASH_FORM_DASH_TYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_EXPERIMENTAL_DASH_FORM_DASH_TYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForEXPERIMENTAL_DASH_FORM_DASH_TYPE;
						synchronized (listeners) {
							consumersForEXPERIMENTAL_DASH_FORM_DASH_TYPE = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEXPERIMENTAL_DASH_FORM_DASH_TYPE.iterator();iter.hasNext();){
							experimentalFormListener listener=(experimentalFormListener)iter.next();
							listener.EXPERIMENTAL_DASH_FORM_DASH_TYPERemoved(fr.curie.BiNoM.pathways.biopax.experimentalFormImpl.this,_EXPERIMENTAL_DASH_FORM_DASH_TYPE);
						}
					}
				}
				return;
			}
		}

	//}
	


}