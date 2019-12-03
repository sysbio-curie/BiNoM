

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.kPrime}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#kPrime)</p>
 * <br>
 */
public class kPrimeImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.kPrime {
	

	private static com.hp.hpl.jena.rdf.model.Property COMMENTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#COMMENT");
	private java.util.ArrayList COMMENT;
	private static com.hp.hpl.jena.rdf.model.Property K_DASH_PRIMEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#K-PRIME");
	private java.lang.Float K_DASH_PRIME;
	private static com.hp.hpl.jena.rdf.model.Property TEMPERATUREProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#TEMPERATURE");
	private java.util.ArrayList TEMPERATURE;
	private static com.hp.hpl.jena.rdf.model.Property PHProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#PH");
	private java.util.ArrayList PH;
	private static com.hp.hpl.jena.rdf.model.Property IONIC_DASH_STRENGTHProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#IONIC-STRENGTH");
	private java.util.ArrayList IONIC_DASH_STRENGTH;
	private static com.hp.hpl.jena.rdf.model.Property PMGProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#PMG");
	private java.util.ArrayList PMG;

	kPrimeImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static kPrimeImpl getkPrime(Resource resource, Model model) throws JastorException {
		return new kPrimeImpl(resource, model);
	}
	    
	static kPrimeImpl createkPrime(Resource resource, Model model) throws JastorException {
		kPrimeImpl impl = new kPrimeImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, kPrime.TYPE)))
			impl._model.add(impl._resource, RDF.type, kPrime.TYPE);
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
		it = _model.listStatements(_resource,K_DASH_PRIMEProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,TEMPERATUREProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,PHProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,IONIC_DASH_STRENGTHProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,PMGProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.kPrime.TYPE);
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
		K_DASH_PRIME = null;
		TEMPERATURE = null;
		PH = null;
		IONIC_DASH_STRENGTH = null;
		PMG = null;
	}


	private void initCOMMENT() throws JastorException {
		COMMENT = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, COMMENTProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#COMMENT properties in kPrime model not a Literal", stmt.getObject());
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

	public java.lang.Float getK_DASH_PRIME() throws JastorException {
		if (K_DASH_PRIME != null)
			return K_DASH_PRIME;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, K_DASH_PRIMEProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": K_DASH_PRIME getProperty() in fr.curie.BiNoM.pathways.biopax.kPrime model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
		K_DASH_PRIME = (java.lang.Float)obj;
		return K_DASH_PRIME;
	}
	
	public void setK_DASH_PRIME(java.lang.Float K_DASH_PRIME) throws JastorException {
		if (_model.contains(_resource,K_DASH_PRIMEProperty)) {
			_model.removeAll(_resource,K_DASH_PRIMEProperty,null);
		}
		this.K_DASH_PRIME = K_DASH_PRIME;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (K_DASH_PRIME != null) {
			_model.add(_model.createStatement(_resource,K_DASH_PRIMEProperty, _model.createTypedLiteral(K_DASH_PRIME)));
		}	
	}


	private void initTEMPERATURE() throws JastorException {
		TEMPERATURE = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, TEMPERATUREProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#TEMPERATURE properties in kPrime model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			TEMPERATURE.add(Util.fixLiteral(literal.getValue(),"java.lang.Float"));
		}
	}

	public java.util.Iterator getTEMPERATURE() throws JastorException {
		if (TEMPERATURE == null)
			initTEMPERATURE();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(TEMPERATURE,_resource,TEMPERATUREProperty,false);
	}

	public void addTEMPERATURE(java.lang.Float TEMPERATURE) throws JastorException {
		if (this.TEMPERATURE == null)
			initTEMPERATURE();
		if (this.TEMPERATURE.contains(TEMPERATURE))
			return;
		if (_model.contains(_resource, TEMPERATUREProperty, _model.createTypedLiteral(TEMPERATURE)))
			return;
		this.TEMPERATURE.add(TEMPERATURE);
		_model.add(_resource, TEMPERATUREProperty, _model.createTypedLiteral(TEMPERATURE));
	}
	
	public void removeTEMPERATURE(java.lang.Float TEMPERATURE) throws JastorException {
		if (this.TEMPERATURE == null)
			initTEMPERATURE();
		if (!this.TEMPERATURE.contains(TEMPERATURE))
			return;
		if (!_model.contains(_resource, TEMPERATUREProperty, _model.createTypedLiteral(TEMPERATURE)))
			return;
		this.TEMPERATURE.remove(TEMPERATURE);
		_model.removeAll(_resource, TEMPERATUREProperty, _model.createTypedLiteral(TEMPERATURE));
	}


	private void initPH() throws JastorException {
		PH = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, PHProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#PH properties in kPrime model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			PH.add(Util.fixLiteral(literal.getValue(),"java.lang.Float"));
		}
	}

	public java.util.Iterator getPH() throws JastorException {
		if (PH == null)
			initPH();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(PH,_resource,PHProperty,false);
	}

	public void addPH(java.lang.Float PH) throws JastorException {
		if (this.PH == null)
			initPH();
		if (this.PH.contains(PH))
			return;
		if (_model.contains(_resource, PHProperty, _model.createTypedLiteral(PH)))
			return;
		this.PH.add(PH);
		_model.add(_resource, PHProperty, _model.createTypedLiteral(PH));
	}
	
	public void removePH(java.lang.Float PH) throws JastorException {
		if (this.PH == null)
			initPH();
		if (!this.PH.contains(PH))
			return;
		if (!_model.contains(_resource, PHProperty, _model.createTypedLiteral(PH)))
			return;
		this.PH.remove(PH);
		_model.removeAll(_resource, PHProperty, _model.createTypedLiteral(PH));
	}


	private void initIONIC_DASH_STRENGTH() throws JastorException {
		IONIC_DASH_STRENGTH = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, IONIC_DASH_STRENGTHProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#IONIC-STRENGTH properties in kPrime model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			IONIC_DASH_STRENGTH.add(Util.fixLiteral(literal.getValue(),"java.lang.Float"));
		}
	}

	public java.util.Iterator getIONIC_DASH_STRENGTH() throws JastorException {
		if (IONIC_DASH_STRENGTH == null)
			initIONIC_DASH_STRENGTH();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(IONIC_DASH_STRENGTH,_resource,IONIC_DASH_STRENGTHProperty,false);
	}

	public void addIONIC_DASH_STRENGTH(java.lang.Float IONIC_DASH_STRENGTH) throws JastorException {
		if (this.IONIC_DASH_STRENGTH == null)
			initIONIC_DASH_STRENGTH();
		if (this.IONIC_DASH_STRENGTH.contains(IONIC_DASH_STRENGTH))
			return;
		if (_model.contains(_resource, IONIC_DASH_STRENGTHProperty, _model.createTypedLiteral(IONIC_DASH_STRENGTH)))
			return;
		this.IONIC_DASH_STRENGTH.add(IONIC_DASH_STRENGTH);
		_model.add(_resource, IONIC_DASH_STRENGTHProperty, _model.createTypedLiteral(IONIC_DASH_STRENGTH));
	}
	
	public void removeIONIC_DASH_STRENGTH(java.lang.Float IONIC_DASH_STRENGTH) throws JastorException {
		if (this.IONIC_DASH_STRENGTH == null)
			initIONIC_DASH_STRENGTH();
		if (!this.IONIC_DASH_STRENGTH.contains(IONIC_DASH_STRENGTH))
			return;
		if (!_model.contains(_resource, IONIC_DASH_STRENGTHProperty, _model.createTypedLiteral(IONIC_DASH_STRENGTH)))
			return;
		this.IONIC_DASH_STRENGTH.remove(IONIC_DASH_STRENGTH);
		_model.removeAll(_resource, IONIC_DASH_STRENGTHProperty, _model.createTypedLiteral(IONIC_DASH_STRENGTH));
	}


	private void initPMG() throws JastorException {
		PMG = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, PMGProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#PMG properties in kPrime model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			PMG.add(Util.fixLiteral(literal.getValue(),"java.lang.Float"));
		}
	}

	public java.util.Iterator getPMG() throws JastorException {
		if (PMG == null)
			initPMG();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(PMG,_resource,PMGProperty,false);
	}

	public void addPMG(java.lang.Float PMG) throws JastorException {
		if (this.PMG == null)
			initPMG();
		if (this.PMG.contains(PMG))
			return;
		if (_model.contains(_resource, PMGProperty, _model.createTypedLiteral(PMG)))
			return;
		this.PMG.add(PMG);
		_model.add(_resource, PMGProperty, _model.createTypedLiteral(PMG));
	}
	
	public void removePMG(java.lang.Float PMG) throws JastorException {
		if (this.PMG == null)
			initPMG();
		if (!this.PMG.contains(PMG))
			return;
		if (!_model.contains(_resource, PMGProperty, _model.createTypedLiteral(PMG)))
			return;
		this.PMG.remove(PMG);
		_model.removeAll(_resource, PMGProperty, _model.createTypedLiteral(PMG));
	}
 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof kPrimeListener))
			throw new IllegalArgumentException("ThingListener must be instance of kPrimeListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((kPrimeListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof kPrimeListener))
			throw new IllegalArgumentException("ThingListener must be instance of kPrimeListener"); 
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
						kPrimeListener listener=(kPrimeListener)iter.next();
						listener.COMMENTAdded(fr.curie.BiNoM.pathways.biopax.kPrimeImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(K_DASH_PRIMEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				K_DASH_PRIME = (java.lang.Float)Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						kPrimeListener listener=(kPrimeListener)iter.next();
						listener.K_DASH_PRIMEChanged(fr.curie.BiNoM.pathways.biopax.kPrimeImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(TEMPERATUREProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (TEMPERATURE == null) {
					try {
						initTEMPERATURE();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (!TEMPERATURE.contains(obj))
					TEMPERATURE.add(obj);
				java.util.ArrayList consumersForTEMPERATURE;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForTEMPERATURE = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForTEMPERATURE.iterator();iter.hasNext();){
						kPrimeListener listener=(kPrimeListener)iter.next();
						listener.TEMPERATUREAdded(fr.curie.BiNoM.pathways.biopax.kPrimeImpl.this,(java.lang.Float)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(PHProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (PH == null) {
					try {
						initPH();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (!PH.contains(obj))
					PH.add(obj);
				java.util.ArrayList consumersForPH;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForPH = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForPH.iterator();iter.hasNext();){
						kPrimeListener listener=(kPrimeListener)iter.next();
						listener.PHAdded(fr.curie.BiNoM.pathways.biopax.kPrimeImpl.this,(java.lang.Float)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(IONIC_DASH_STRENGTHProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (IONIC_DASH_STRENGTH == null) {
					try {
						initIONIC_DASH_STRENGTH();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (!IONIC_DASH_STRENGTH.contains(obj))
					IONIC_DASH_STRENGTH.add(obj);
				java.util.ArrayList consumersForIONIC_DASH_STRENGTH;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForIONIC_DASH_STRENGTH = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForIONIC_DASH_STRENGTH.iterator();iter.hasNext();){
						kPrimeListener listener=(kPrimeListener)iter.next();
						listener.IONIC_DASH_STRENGTHAdded(fr.curie.BiNoM.pathways.biopax.kPrimeImpl.this,(java.lang.Float)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(PMGProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (PMG == null) {
					try {
						initPMG();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (!PMG.contains(obj))
					PMG.add(obj);
				java.util.ArrayList consumersForPMG;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForPMG = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForPMG.iterator();iter.hasNext();){
						kPrimeListener listener=(kPrimeListener)iter.next();
						listener.PMGAdded(fr.curie.BiNoM.pathways.biopax.kPrimeImpl.this,(java.lang.Float)obj);
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
						kPrimeListener listener=(kPrimeListener)iter.next();
						listener.COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.kPrimeImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(K_DASH_PRIMEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (K_DASH_PRIME != null && K_DASH_PRIME.equals(obj))
					K_DASH_PRIME = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						kPrimeListener listener=(kPrimeListener)iter.next();
						listener.K_DASH_PRIMEChanged(fr.curie.BiNoM.pathways.biopax.kPrimeImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(TEMPERATUREProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (TEMPERATURE != null) {
					if (TEMPERATURE.contains(obj))
						TEMPERATURE.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						kPrimeListener listener=(kPrimeListener)iter.next();
						listener.TEMPERATURERemoved(fr.curie.BiNoM.pathways.biopax.kPrimeImpl.this,(java.lang.Float)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(PHProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (PH != null) {
					if (PH.contains(obj))
						PH.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						kPrimeListener listener=(kPrimeListener)iter.next();
						listener.PHRemoved(fr.curie.BiNoM.pathways.biopax.kPrimeImpl.this,(java.lang.Float)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(IONIC_DASH_STRENGTHProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (IONIC_DASH_STRENGTH != null) {
					if (IONIC_DASH_STRENGTH.contains(obj))
						IONIC_DASH_STRENGTH.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						kPrimeListener listener=(kPrimeListener)iter.next();
						listener.IONIC_DASH_STRENGTHRemoved(fr.curie.BiNoM.pathways.biopax.kPrimeImpl.this,(java.lang.Float)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(PMGProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.Float");
				if (PMG != null) {
					if (PMG.contains(obj))
						PMG.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						kPrimeListener listener=(kPrimeListener)iter.next();
						listener.PMGRemoved(fr.curie.BiNoM.pathways.biopax.kPrimeImpl.this,(java.lang.Float)obj);
					}
				}
				return;
			}
		}

	//}
	


}