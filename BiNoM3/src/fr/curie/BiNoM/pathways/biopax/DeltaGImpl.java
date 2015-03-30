

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.DeltaG}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#DeltaG)</p>
 * <br>
 */
public class DeltaGImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.DeltaG {
	

	private static com.hp.hpl.jena.rdf.model.Property commentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#comment");
	private java.util.ArrayList comment;
	private static com.hp.hpl.jena.rdf.model.Property deltaGPrime0Property = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#deltaGPrime0");
	private java.lang.Float deltaGPrime0;
	private static com.hp.hpl.jena.rdf.model.Property ionicStrengthProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#ionicStrength");
	private java.lang.Float ionicStrength;
	private static com.hp.hpl.jena.rdf.model.Property temperatureProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#temperature");
	private java.lang.Float temperature;
	private static com.hp.hpl.jena.rdf.model.Property pMgProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#pMg");
	private java.lang.Float pMg;
	private static com.hp.hpl.jena.rdf.model.Property phProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#ph");
	private java.lang.Float ph;

	DeltaGImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static DeltaGImpl getDeltaG(Resource resource, Model model) throws JastorException {
		return new DeltaGImpl(resource, model);
	}
	    
	static DeltaGImpl createDeltaG(Resource resource, Model model) throws JastorException {
		DeltaGImpl impl = new DeltaGImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, DeltaG.TYPE)))
			impl._model.add(impl._resource, RDF.type, DeltaG.TYPE);
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
		it = _model.listStatements(_resource,deltaGPrime0Property,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,ionicStrengthProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,temperatureProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,pMgProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,phProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.DeltaG.TYPE);
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
		deltaGPrime0 = null;
		ionicStrength = null;
		temperature = null;
		pMg = null;
		ph = null;
	}


	private void initComment() throws JastorException {
		comment = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, commentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#comment properties in DeltaG model not a Literal", stmt.getObject());
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

	public java.lang.Float getDeltaGPrime0() throws JastorException {
		if (deltaGPrime0 != null)
			return deltaGPrime0;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, deltaGPrime0Property);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": deltaGPrime0 getProperty() in fr.curie.BiNoM.pathways.biopax.DeltaG model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
		deltaGPrime0 = (java.lang.Float)obj;
		return deltaGPrime0;
	}
	
	public void setDeltaGPrime0(java.lang.Float deltaGPrime0) throws JastorException {
		if (_model.contains(_resource,deltaGPrime0Property)) {
			_model.removeAll(_resource,deltaGPrime0Property,null);
		}
		this.deltaGPrime0 = deltaGPrime0;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (deltaGPrime0 != null) {
			_model.add(_model.createStatement(_resource,deltaGPrime0Property, _model.createTypedLiteral(deltaGPrime0)));
		}	
	}

	public java.lang.Float getIonicStrength() throws JastorException {
		if (ionicStrength != null)
			return ionicStrength;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, ionicStrengthProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": ionicStrength getProperty() in fr.curie.BiNoM.pathways.biopax.DeltaG model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
		ionicStrength = (java.lang.Float)obj;
		return ionicStrength;
	}
	
	public void setIonicStrength(java.lang.Float ionicStrength) throws JastorException {
		if (_model.contains(_resource,ionicStrengthProperty)) {
			_model.removeAll(_resource,ionicStrengthProperty,null);
		}
		this.ionicStrength = ionicStrength;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (ionicStrength != null) {
			_model.add(_model.createStatement(_resource,ionicStrengthProperty, _model.createTypedLiteral(ionicStrength)));
		}	
	}

	public java.lang.Float getTemperature() throws JastorException {
		if (temperature != null)
			return temperature;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, temperatureProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": temperature getProperty() in fr.curie.BiNoM.pathways.biopax.DeltaG model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
		temperature = (java.lang.Float)obj;
		return temperature;
	}
	
	public void setTemperature(java.lang.Float temperature) throws JastorException {
		if (_model.contains(_resource,temperatureProperty)) {
			_model.removeAll(_resource,temperatureProperty,null);
		}
		this.temperature = temperature;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (temperature != null) {
			_model.add(_model.createStatement(_resource,temperatureProperty, _model.createTypedLiteral(temperature)));
		}	
	}

	public java.lang.Float getPMg() throws JastorException {
		if (pMg != null)
			return pMg;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, pMgProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": pMg getProperty() in fr.curie.BiNoM.pathways.biopax.DeltaG model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
		pMg = (java.lang.Float)obj;
		return pMg;
	}
	
	public void setPMg(java.lang.Float pMg) throws JastorException {
		if (_model.contains(_resource,pMgProperty)) {
			_model.removeAll(_resource,pMgProperty,null);
		}
		this.pMg = pMg;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (pMg != null) {
			_model.add(_model.createStatement(_resource,pMgProperty, _model.createTypedLiteral(pMg)));
		}	
	}

	public java.lang.Float getPh() throws JastorException {
		if (ph != null)
			return ph;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, phProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": ph getProperty() in fr.curie.BiNoM.pathways.biopax.DeltaG model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
		ph = (java.lang.Float)obj;
		return ph;
	}
	
	public void setPh(java.lang.Float ph) throws JastorException {
		if (_model.contains(_resource,phProperty)) {
			_model.removeAll(_resource,phProperty,null);
		}
		this.ph = ph;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (ph != null) {
			_model.add(_model.createStatement(_resource,phProperty, _model.createTypedLiteral(ph)));
		}	
	}
 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof DeltaGListener))
			throw new IllegalArgumentException("ThingListener must be instance of DeltaGListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((DeltaGListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof DeltaGListener))
			throw new IllegalArgumentException("ThingListener must be instance of DeltaGListener"); 
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
						DeltaGListener listener=(DeltaGListener)iter.next();
						listener.commentAdded(fr.curie.BiNoM.pathways.biopax.DeltaGImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(deltaGPrime0Property)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				deltaGPrime0 = (java.lang.Float)Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						DeltaGListener listener=(DeltaGListener)iter.next();
						listener.deltaGPrime0Changed(fr.curie.BiNoM.pathways.biopax.DeltaGImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ionicStrengthProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				ionicStrength = (java.lang.Float)Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						DeltaGListener listener=(DeltaGListener)iter.next();
						listener.ionicStrengthChanged(fr.curie.BiNoM.pathways.biopax.DeltaGImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(temperatureProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				temperature = (java.lang.Float)Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						DeltaGListener listener=(DeltaGListener)iter.next();
						listener.temperatureChanged(fr.curie.BiNoM.pathways.biopax.DeltaGImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(pMgProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				pMg = (java.lang.Float)Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						DeltaGListener listener=(DeltaGListener)iter.next();
						listener.pMgChanged(fr.curie.BiNoM.pathways.biopax.DeltaGImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(phProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				ph = (java.lang.Float)Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						DeltaGListener listener=(DeltaGListener)iter.next();
						listener.phChanged(fr.curie.BiNoM.pathways.biopax.DeltaGImpl.this);
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
						DeltaGListener listener=(DeltaGListener)iter.next();
						listener.commentRemoved(fr.curie.BiNoM.pathways.biopax.DeltaGImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(deltaGPrime0Property)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (deltaGPrime0 != null && deltaGPrime0.equals(obj))
					deltaGPrime0 = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						DeltaGListener listener=(DeltaGListener)iter.next();
						listener.deltaGPrime0Changed(fr.curie.BiNoM.pathways.biopax.DeltaGImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ionicStrengthProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (ionicStrength != null && ionicStrength.equals(obj))
					ionicStrength = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						DeltaGListener listener=(DeltaGListener)iter.next();
						listener.ionicStrengthChanged(fr.curie.BiNoM.pathways.biopax.DeltaGImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(temperatureProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (temperature != null && temperature.equals(obj))
					temperature = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						DeltaGListener listener=(DeltaGListener)iter.next();
						listener.temperatureChanged(fr.curie.BiNoM.pathways.biopax.DeltaGImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(pMgProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (pMg != null && pMg.equals(obj))
					pMg = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						DeltaGListener listener=(DeltaGListener)iter.next();
						listener.pMgChanged(fr.curie.BiNoM.pathways.biopax.DeltaGImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(phProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (ph != null && ph.equals(obj))
					ph = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						DeltaGListener listener=(DeltaGListener)iter.next();
						listener.phChanged(fr.curie.BiNoM.pathways.biopax.DeltaGImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}