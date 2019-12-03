

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.sequenceFeature}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#sequenceFeature)</p>
 * <br>
 */
public class sequenceFeatureImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.sequenceFeature {
	

	private static com.hp.hpl.jena.rdf.model.Property COMMENTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#COMMENT");
	private java.util.ArrayList COMMENT;
	private static com.hp.hpl.jena.rdf.model.Property FEATURE_DASH_TYPEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#FEATURE-TYPE");
	private fr.curie.BiNoM.pathways.biopax.openControlledVocabulary FEATURE_DASH_TYPE;
	private static com.hp.hpl.jena.rdf.model.Property FEATURE_DASH_LOCATIONProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#FEATURE-LOCATION");
	private java.util.ArrayList FEATURE_DASH_LOCATION;
	private static com.hp.hpl.jena.rdf.model.Property SHORT_DASH_NAMEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SHORT-NAME");
	private java.lang.String SHORT_DASH_NAME;
	private static com.hp.hpl.jena.rdf.model.Property SYNONYMSProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SYNONYMS");
	private java.util.ArrayList SYNONYMS;
	private static com.hp.hpl.jena.rdf.model.Property NAMEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#NAME");
	private java.lang.String NAME;
	private static com.hp.hpl.jena.rdf.model.Property XREFProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#XREF");
	private java.util.ArrayList XREF;

	sequenceFeatureImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static sequenceFeatureImpl getsequenceFeature(Resource resource, Model model) throws JastorException {
		return new sequenceFeatureImpl(resource, model);
	}
	    
	static sequenceFeatureImpl createsequenceFeature(Resource resource, Model model) throws JastorException {
		sequenceFeatureImpl impl = new sequenceFeatureImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, sequenceFeature.TYPE)))
			impl._model.add(impl._resource, RDF.type, sequenceFeature.TYPE);
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
		it = _model.listStatements(_resource,FEATURE_DASH_TYPEProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,FEATURE_DASH_LOCATIONProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,SHORT_DASH_NAMEProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,SYNONYMSProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,NAMEProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,XREFProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.sequenceFeature.TYPE);
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
		FEATURE_DASH_TYPE = null;
		FEATURE_DASH_LOCATION = null;
		SHORT_DASH_NAME = null;
		SYNONYMS = null;
		NAME = null;
		XREF = null;
	}


	private void initCOMMENT() throws JastorException {
		COMMENT = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, COMMENTProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#COMMENT properties in sequenceFeature model not a Literal", stmt.getObject());
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


	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary getFEATURE_DASH_TYPE() throws JastorException {
		if (FEATURE_DASH_TYPE != null)
			return FEATURE_DASH_TYPE;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, FEATURE_DASH_TYPEProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": FEATURE_DASH_TYPE getProperty() in fr.curie.BiNoM.pathways.biopax.sequenceFeature model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		FEATURE_DASH_TYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
		return FEATURE_DASH_TYPE;
	}

	public void setFEATURE_DASH_TYPE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary FEATURE_DASH_TYPE) throws JastorException {
		if (_model.contains(_resource,FEATURE_DASH_TYPEProperty)) {
			_model.removeAll(_resource,FEATURE_DASH_TYPEProperty,null);
		}
		this.FEATURE_DASH_TYPE = FEATURE_DASH_TYPE;
		if (FEATURE_DASH_TYPE != null) {
			_model.add(_model.createStatement(_resource,FEATURE_DASH_TYPEProperty, FEATURE_DASH_TYPE.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary setFEATURE_DASH_TYPE() throws JastorException {
		if (_model.contains(_resource,FEATURE_DASH_TYPEProperty)) {
			_model.removeAll(_resource,FEATURE_DASH_TYPEProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.openControlledVocabulary FEATURE_DASH_TYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createopenControlledVocabulary(_model.createResource(),_model);
		this.FEATURE_DASH_TYPE = FEATURE_DASH_TYPE;
		_model.add(_model.createStatement(_resource,FEATURE_DASH_TYPEProperty, FEATURE_DASH_TYPE.resource()));
		return FEATURE_DASH_TYPE;
	}
	
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary setFEATURE_DASH_TYPE(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,FEATURE_DASH_TYPEProperty)) {
			_model.removeAll(_resource,FEATURE_DASH_TYPEProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.openControlledVocabulary FEATURE_DASH_TYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
		this.FEATURE_DASH_TYPE = FEATURE_DASH_TYPE;
		_model.add(_model.createStatement(_resource,FEATURE_DASH_TYPEProperty, FEATURE_DASH_TYPE.resource()));
		return FEATURE_DASH_TYPE;
	}
	

	private void initFEATURE_DASH_LOCATION() throws JastorException {
		this.FEATURE_DASH_LOCATION = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, FEATURE_DASH_LOCATIONProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#FEATURE-LOCATION properties in sequenceFeature model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.sequenceLocation FEATURE_DASH_LOCATION = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getsequenceLocation(resource,_model);
				this.FEATURE_DASH_LOCATION.add(FEATURE_DASH_LOCATION);
			}
		}
	}

	public java.util.Iterator getFEATURE_DASH_LOCATION() throws JastorException {
		if (FEATURE_DASH_LOCATION == null)
			initFEATURE_DASH_LOCATION();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(FEATURE_DASH_LOCATION,_resource,FEATURE_DASH_LOCATIONProperty,true);
	}

	public void addFEATURE_DASH_LOCATION(fr.curie.BiNoM.pathways.biopax.sequenceLocation FEATURE_DASH_LOCATION) throws JastorException {
		if (this.FEATURE_DASH_LOCATION == null)
			initFEATURE_DASH_LOCATION();
		if (this.FEATURE_DASH_LOCATION.contains(FEATURE_DASH_LOCATION)) {
			this.FEATURE_DASH_LOCATION.remove(FEATURE_DASH_LOCATION);
			this.FEATURE_DASH_LOCATION.add(FEATURE_DASH_LOCATION);
			return;
		}
		this.FEATURE_DASH_LOCATION.add(FEATURE_DASH_LOCATION);
		_model.add(_model.createStatement(_resource,FEATURE_DASH_LOCATIONProperty,FEATURE_DASH_LOCATION.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.sequenceLocation addFEATURE_DASH_LOCATION() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.sequenceLocation FEATURE_DASH_LOCATION = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createsequenceLocation(_model.createResource(),_model);
		if (this.FEATURE_DASH_LOCATION == null)
			initFEATURE_DASH_LOCATION();
		this.FEATURE_DASH_LOCATION.add(FEATURE_DASH_LOCATION);
		_model.add(_model.createStatement(_resource,FEATURE_DASH_LOCATIONProperty,FEATURE_DASH_LOCATION.resource()));
		return FEATURE_DASH_LOCATION;
	}
	
	public fr.curie.BiNoM.pathways.biopax.sequenceLocation addFEATURE_DASH_LOCATION(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.sequenceLocation FEATURE_DASH_LOCATION = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getsequenceLocation(resource,_model);
		if (this.FEATURE_DASH_LOCATION == null)
			initFEATURE_DASH_LOCATION();
		if (this.FEATURE_DASH_LOCATION.contains(FEATURE_DASH_LOCATION))
			return FEATURE_DASH_LOCATION;
		this.FEATURE_DASH_LOCATION.add(FEATURE_DASH_LOCATION);
		_model.add(_model.createStatement(_resource,FEATURE_DASH_LOCATIONProperty,FEATURE_DASH_LOCATION.resource()));
		return FEATURE_DASH_LOCATION;
	}
	
	public void removeFEATURE_DASH_LOCATION(fr.curie.BiNoM.pathways.biopax.sequenceLocation FEATURE_DASH_LOCATION) throws JastorException {
		if (this.FEATURE_DASH_LOCATION == null)
			initFEATURE_DASH_LOCATION();
		if (!this.FEATURE_DASH_LOCATION.contains(FEATURE_DASH_LOCATION))
			return;
		if (!_model.contains(_resource, FEATURE_DASH_LOCATIONProperty, FEATURE_DASH_LOCATION.resource()))
			return;
		this.FEATURE_DASH_LOCATION.remove(FEATURE_DASH_LOCATION);
		_model.removeAll(_resource, FEATURE_DASH_LOCATIONProperty, FEATURE_DASH_LOCATION.resource());
	}
		 
	public java.lang.String getSHORT_DASH_NAME() throws JastorException {
		if (SHORT_DASH_NAME != null)
			return SHORT_DASH_NAME;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, SHORT_DASH_NAMEProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": SHORT_DASH_NAME getProperty() in fr.curie.BiNoM.pathways.biopax.sequenceFeature model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		SHORT_DASH_NAME = (java.lang.String)obj;
		return SHORT_DASH_NAME;
	}
	
	public void setSHORT_DASH_NAME(java.lang.String SHORT_DASH_NAME) throws JastorException {
		if (_model.contains(_resource,SHORT_DASH_NAMEProperty)) {
			_model.removeAll(_resource,SHORT_DASH_NAMEProperty,null);
		}
		this.SHORT_DASH_NAME = SHORT_DASH_NAME;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (SHORT_DASH_NAME != null) {
			_model.add(_model.createStatement(_resource,SHORT_DASH_NAMEProperty, _model.createTypedLiteral(SHORT_DASH_NAME)));
		}	
	}


	private void initSYNONYMS() throws JastorException {
		SYNONYMS = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, SYNONYMSProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#SYNONYMS properties in sequenceFeature model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			SYNONYMS.add(Util.fixLiteral(literal.getValue(),"java.lang.String"));
		}
	}

	public java.util.Iterator getSYNONYMS() throws JastorException {
		if (SYNONYMS == null)
			initSYNONYMS();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(SYNONYMS,_resource,SYNONYMSProperty,false);
	}

	public void addSYNONYMS(java.lang.String SYNONYMS) throws JastorException {
		if (this.SYNONYMS == null)
			initSYNONYMS();
		if (this.SYNONYMS.contains(SYNONYMS))
			return;
		if (_model.contains(_resource, SYNONYMSProperty, _model.createTypedLiteral(SYNONYMS)))
			return;
		this.SYNONYMS.add(SYNONYMS);
		_model.add(_resource, SYNONYMSProperty, _model.createTypedLiteral(SYNONYMS));
	}
	
	public void removeSYNONYMS(java.lang.String SYNONYMS) throws JastorException {
		if (this.SYNONYMS == null)
			initSYNONYMS();
		if (!this.SYNONYMS.contains(SYNONYMS))
			return;
		if (!_model.contains(_resource, SYNONYMSProperty, _model.createTypedLiteral(SYNONYMS)))
			return;
		this.SYNONYMS.remove(SYNONYMS);
		_model.removeAll(_resource, SYNONYMSProperty, _model.createTypedLiteral(SYNONYMS));
	}

	public java.lang.String getNAME() throws JastorException {
		if (NAME != null)
			return NAME;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, NAMEProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": NAME getProperty() in fr.curie.BiNoM.pathways.biopax.sequenceFeature model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		NAME = (java.lang.String)obj;
		return NAME;
	}
	
	public void setNAME(java.lang.String NAME) throws JastorException {
		if (_model.contains(_resource,NAMEProperty)) {
			_model.removeAll(_resource,NAMEProperty,null);
		}
		this.NAME = NAME;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (NAME != null) {
			_model.add(_model.createStatement(_resource,NAMEProperty, _model.createTypedLiteral(NAME)));
		}	
	}


	private void initXREF() throws JastorException {
		this.XREF = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, XREFProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#XREF properties in sequenceFeature model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.xref XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getxref(resource,_model);
				this.XREF.add(XREF);
			}
		}
	}

	public java.util.Iterator getXREF() throws JastorException {
		if (XREF == null)
			initXREF();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(XREF,_resource,XREFProperty,true);
	}

	public void addXREF(fr.curie.BiNoM.pathways.biopax.xref XREF) throws JastorException {
		if (this.XREF == null)
			initXREF();
		if (this.XREF.contains(XREF)) {
			this.XREF.remove(XREF);
			this.XREF.add(XREF);
			return;
		}
		this.XREF.add(XREF);
		_model.add(_model.createStatement(_resource,XREFProperty,XREF.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.xref addXREF() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.xref XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createxref(_model.createResource(),_model);
		if (this.XREF == null)
			initXREF();
		this.XREF.add(XREF);
		_model.add(_model.createStatement(_resource,XREFProperty,XREF.resource()));
		return XREF;
	}
	
	public fr.curie.BiNoM.pathways.biopax.xref addXREF(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.xref XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getxref(resource,_model);
		if (this.XREF == null)
			initXREF();
		if (this.XREF.contains(XREF))
			return XREF;
		this.XREF.add(XREF);
		_model.add(_model.createStatement(_resource,XREFProperty,XREF.resource()));
		return XREF;
	}
	
	public void removeXREF(fr.curie.BiNoM.pathways.biopax.xref XREF) throws JastorException {
		if (this.XREF == null)
			initXREF();
		if (!this.XREF.contains(XREF))
			return;
		if (!_model.contains(_resource, XREFProperty, XREF.resource()))
			return;
		this.XREF.remove(XREF);
		_model.removeAll(_resource, XREFProperty, XREF.resource());
	}
		  


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof sequenceFeatureListener))
			throw new IllegalArgumentException("ThingListener must be instance of sequenceFeatureListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((sequenceFeatureListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof sequenceFeatureListener))
			throw new IllegalArgumentException("ThingListener must be instance of sequenceFeatureListener"); 
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
						sequenceFeatureListener listener=(sequenceFeatureListener)iter.next();
						listener.COMMENTAdded(fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(FEATURE_DASH_TYPEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				FEATURE_DASH_TYPE = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						FEATURE_DASH_TYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
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
						sequenceFeatureListener listener=(sequenceFeatureListener)iter.next();
						listener.FEATURE_DASH_TYPEChanged(fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(FEATURE_DASH_LOCATIONProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.sequenceLocation _FEATURE_DASH_LOCATION = null;
					try {
						_FEATURE_DASH_LOCATION = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getsequenceLocation(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (FEATURE_DASH_LOCATION == null) {
						try {
							initFEATURE_DASH_LOCATION();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!FEATURE_DASH_LOCATION.contains(_FEATURE_DASH_LOCATION))
						FEATURE_DASH_LOCATION.add(_FEATURE_DASH_LOCATION);
					if (listeners != null) {
						java.util.ArrayList consumersForFEATURE_DASH_LOCATION;
						synchronized (listeners) {
							consumersForFEATURE_DASH_LOCATION = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForFEATURE_DASH_LOCATION.iterator();iter.hasNext();){
							sequenceFeatureListener listener=(sequenceFeatureListener)iter.next();
							listener.FEATURE_DASH_LOCATIONAdded(fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.this,_FEATURE_DASH_LOCATION);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(SHORT_DASH_NAMEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				SHORT_DASH_NAME = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						sequenceFeatureListener listener=(sequenceFeatureListener)iter.next();
						listener.SHORT_DASH_NAMEChanged(fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(SYNONYMSProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (SYNONYMS == null) {
					try {
						initSYNONYMS();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (!SYNONYMS.contains(obj))
					SYNONYMS.add(obj);
				java.util.ArrayList consumersForSYNONYMS;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForSYNONYMS = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForSYNONYMS.iterator();iter.hasNext();){
						sequenceFeatureListener listener=(sequenceFeatureListener)iter.next();
						listener.SYNONYMSAdded(fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(NAMEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				NAME = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						sequenceFeatureListener listener=(sequenceFeatureListener)iter.next();
						listener.NAMEChanged(fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(XREFProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.xref _XREF = null;
					try {
						_XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getxref(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (XREF == null) {
						try {
							initXREF();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!XREF.contains(_XREF))
						XREF.add(_XREF);
					if (listeners != null) {
						java.util.ArrayList consumersForXREF;
						synchronized (listeners) {
							consumersForXREF = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForXREF.iterator();iter.hasNext();){
							sequenceFeatureListener listener=(sequenceFeatureListener)iter.next();
							listener.XREFAdded(fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.this,_XREF);
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
						sequenceFeatureListener listener=(sequenceFeatureListener)iter.next();
						listener.COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(FEATURE_DASH_TYPEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (FEATURE_DASH_TYPE != null && FEATURE_DASH_TYPE.resource().equals(resource))
						FEATURE_DASH_TYPE = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						sequenceFeatureListener listener=(sequenceFeatureListener)iter.next();
						listener.FEATURE_DASH_TYPEChanged(fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(FEATURE_DASH_LOCATIONProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.sequenceLocation _FEATURE_DASH_LOCATION = null;
					if (FEATURE_DASH_LOCATION != null) {
						boolean found = false;
						for (int i=0;i<FEATURE_DASH_LOCATION.size();i++) {
							fr.curie.BiNoM.pathways.biopax.sequenceLocation __item = (fr.curie.BiNoM.pathways.biopax.sequenceLocation) FEATURE_DASH_LOCATION.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_FEATURE_DASH_LOCATION = __item;
								break;
							}
						}
						if (found)
							FEATURE_DASH_LOCATION.remove(_FEATURE_DASH_LOCATION);
						else {
							try {
								_FEATURE_DASH_LOCATION = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getsequenceLocation(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_FEATURE_DASH_LOCATION = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getsequenceLocation(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForFEATURE_DASH_LOCATION;
						synchronized (listeners) {
							consumersForFEATURE_DASH_LOCATION = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForFEATURE_DASH_LOCATION.iterator();iter.hasNext();){
							sequenceFeatureListener listener=(sequenceFeatureListener)iter.next();
							listener.FEATURE_DASH_LOCATIONRemoved(fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.this,_FEATURE_DASH_LOCATION);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(SHORT_DASH_NAMEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (SHORT_DASH_NAME != null && SHORT_DASH_NAME.equals(obj))
					SHORT_DASH_NAME = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						sequenceFeatureListener listener=(sequenceFeatureListener)iter.next();
						listener.SHORT_DASH_NAMEChanged(fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(SYNONYMSProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (SYNONYMS != null) {
					if (SYNONYMS.contains(obj))
						SYNONYMS.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						sequenceFeatureListener listener=(sequenceFeatureListener)iter.next();
						listener.SYNONYMSRemoved(fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(NAMEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (NAME != null && NAME.equals(obj))
					NAME = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						sequenceFeatureListener listener=(sequenceFeatureListener)iter.next();
						listener.NAMEChanged(fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(XREFProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.xref _XREF = null;
					if (XREF != null) {
						boolean found = false;
						for (int i=0;i<XREF.size();i++) {
							fr.curie.BiNoM.pathways.biopax.xref __item = (fr.curie.BiNoM.pathways.biopax.xref) XREF.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_XREF = __item;
								break;
							}
						}
						if (found)
							XREF.remove(_XREF);
						else {
							try {
								_XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getxref(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getxref(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForXREF;
						synchronized (listeners) {
							consumersForXREF = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForXREF.iterator();iter.hasNext();){
							sequenceFeatureListener listener=(sequenceFeatureListener)iter.next();
							listener.XREFRemoved(fr.curie.BiNoM.pathways.biopax.sequenceFeatureImpl.this,_XREF);
						}
					}
				}
				return;
			}
		}

	//}
	


}