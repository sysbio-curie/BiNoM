

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.entity}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#entity)</p>
 * <br>
 */
public class entityImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.entity {
	

	private static com.hp.hpl.jena.rdf.model.Property DATA_DASH_SOURCEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#DATA-SOURCE");
	private java.util.ArrayList DATA_DASH_SOURCE;
	private static com.hp.hpl.jena.rdf.model.Property AVAILABILITYProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#AVAILABILITY");
	private java.util.ArrayList AVAILABILITY;
	private static com.hp.hpl.jena.rdf.model.Property COMMENTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#COMMENT");
	private java.util.ArrayList COMMENT;
	private static com.hp.hpl.jena.rdf.model.Property SHORT_DASH_NAMEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SHORT-NAME");
	private java.lang.String SHORT_DASH_NAME;
	private static com.hp.hpl.jena.rdf.model.Property SYNONYMSProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SYNONYMS");
	private java.util.ArrayList SYNONYMS;
	private static com.hp.hpl.jena.rdf.model.Property NAMEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#NAME");
	private java.lang.String NAME;
	private static com.hp.hpl.jena.rdf.model.Property XREFProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#XREF");
	private java.util.ArrayList XREF;

	entityImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static entityImpl getentity(Resource resource, Model model) throws JastorException {
		return new entityImpl(resource, model);
	}
	    
	static entityImpl createentity(Resource resource, Model model) throws JastorException {
		entityImpl impl = new entityImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, entity.TYPE)))
			impl._model.add(impl._resource, RDF.type, entity.TYPE);
		//impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
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
		it = _model.listStatements(_resource,DATA_DASH_SOURCEProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,AVAILABILITYProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,COMMENTProperty,(RDFNode)null);
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
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.entity.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
	
	public void clearCache() {
		DATA_DASH_SOURCE = null;
		AVAILABILITY = null;
		COMMENT = null;
		SHORT_DASH_NAME = null;
		SYNONYMS = null;
		NAME = null;
		XREF = null;
	}


	private void initDATA_DASH_SOURCE() throws JastorException {
		this.DATA_DASH_SOURCE = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, DATA_DASH_SOURCEProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#DATA-SOURCE properties in entity model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.dataSource DATA_DASH_SOURCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getdataSource(resource,_model);
				this.DATA_DASH_SOURCE.add(DATA_DASH_SOURCE);
			}
		}
	}

	public java.util.Iterator getDATA_DASH_SOURCE() throws JastorException {
		if (DATA_DASH_SOURCE == null)
			initDATA_DASH_SOURCE();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(DATA_DASH_SOURCE,_resource,DATA_DASH_SOURCEProperty,true);
	}

	public void addDATA_DASH_SOURCE(fr.curie.BiNoM.pathways.biopax.dataSource DATA_DASH_SOURCE) throws JastorException {
		if (this.DATA_DASH_SOURCE == null)
			initDATA_DASH_SOURCE();
		if (this.DATA_DASH_SOURCE.contains(DATA_DASH_SOURCE)) {
			this.DATA_DASH_SOURCE.remove(DATA_DASH_SOURCE);
			this.DATA_DASH_SOURCE.add(DATA_DASH_SOURCE);
			return;
		}
		this.DATA_DASH_SOURCE.add(DATA_DASH_SOURCE);
		_model.add(_model.createStatement(_resource,DATA_DASH_SOURCEProperty,DATA_DASH_SOURCE.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.dataSource addDATA_DASH_SOURCE() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.dataSource DATA_DASH_SOURCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createdataSource(_model.createResource(),_model);
		if (this.DATA_DASH_SOURCE == null)
			initDATA_DASH_SOURCE();
		this.DATA_DASH_SOURCE.add(DATA_DASH_SOURCE);
		_model.add(_model.createStatement(_resource,DATA_DASH_SOURCEProperty,DATA_DASH_SOURCE.resource()));
		return DATA_DASH_SOURCE;
	}
	
	public fr.curie.BiNoM.pathways.biopax.dataSource addDATA_DASH_SOURCE(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.dataSource DATA_DASH_SOURCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getdataSource(resource,_model);
		if (this.DATA_DASH_SOURCE == null)
			initDATA_DASH_SOURCE();
		if (this.DATA_DASH_SOURCE.contains(DATA_DASH_SOURCE))
			return DATA_DASH_SOURCE;
		this.DATA_DASH_SOURCE.add(DATA_DASH_SOURCE);
		_model.add(_model.createStatement(_resource,DATA_DASH_SOURCEProperty,DATA_DASH_SOURCE.resource()));
		return DATA_DASH_SOURCE;
	}
	
	public void removeDATA_DASH_SOURCE(fr.curie.BiNoM.pathways.biopax.dataSource DATA_DASH_SOURCE) throws JastorException {
		if (this.DATA_DASH_SOURCE == null)
			initDATA_DASH_SOURCE();
		if (!this.DATA_DASH_SOURCE.contains(DATA_DASH_SOURCE))
			return;
		if (!_model.contains(_resource, DATA_DASH_SOURCEProperty, DATA_DASH_SOURCE.resource()))
			return;
		this.DATA_DASH_SOURCE.remove(DATA_DASH_SOURCE);
		_model.removeAll(_resource, DATA_DASH_SOURCEProperty, DATA_DASH_SOURCE.resource());
	}
		 

	private void initAVAILABILITY() throws JastorException {
		AVAILABILITY = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, AVAILABILITYProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#AVAILABILITY properties in entity model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			AVAILABILITY.add(Util.fixLiteral(literal.getValue(),"java.lang.String"));
		}
	}

	public java.util.Iterator getAVAILABILITY() throws JastorException {
		if (AVAILABILITY == null)
			initAVAILABILITY();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(AVAILABILITY,_resource,AVAILABILITYProperty,false);
	}

	public void addAVAILABILITY(java.lang.String AVAILABILITY) throws JastorException {
		if (this.AVAILABILITY == null)
			initAVAILABILITY();
		if (this.AVAILABILITY.contains(AVAILABILITY))
			return;
		if (_model.contains(_resource, AVAILABILITYProperty, _model.createTypedLiteral(AVAILABILITY)))
			return;
		this.AVAILABILITY.add(AVAILABILITY);
		_model.add(_resource, AVAILABILITYProperty, _model.createTypedLiteral(AVAILABILITY));
	}
	
	public void removeAVAILABILITY(java.lang.String AVAILABILITY) throws JastorException {
		if (this.AVAILABILITY == null)
			initAVAILABILITY();
		if (!this.AVAILABILITY.contains(AVAILABILITY))
			return;
		if (!_model.contains(_resource, AVAILABILITYProperty, _model.createTypedLiteral(AVAILABILITY)))
			return;
		this.AVAILABILITY.remove(AVAILABILITY);
		_model.removeAll(_resource, AVAILABILITYProperty, _model.createTypedLiteral(AVAILABILITY));
	}


	private void initCOMMENT() throws JastorException {
		COMMENT = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, COMMENTProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#COMMENT properties in entity model not a Literal", stmt.getObject());
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

	public java.lang.String getSHORT_DASH_NAME() throws JastorException {
		if (SHORT_DASH_NAME != null)
			return SHORT_DASH_NAME;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, SHORT_DASH_NAMEProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": SHORT_DASH_NAME getProperty() in fr.curie.BiNoM.pathways.biopax.entity model not Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#SYNONYMS properties in entity model not a Literal", stmt.getObject());
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
			throw new JastorInvalidRDFNodeException(uri() + ": NAME getProperty() in fr.curie.BiNoM.pathways.biopax.entity model not Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#XREF properties in entity model not a Resource", stmt.getObject());
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
		if (!(listener instanceof entityListener))
			throw new IllegalArgumentException("ThingListener must be instance of entityListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((entityListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof entityListener))
			throw new IllegalArgumentException("ThingListener must be instance of entityListener"); 
		if (listeners == null)
			return;
		if (this.listeners.contains(listener)){
			listeners.remove(listener);
		}
	}



	
		public void addedStatement(com.hp.hpl.jena.rdf.model.Statement stmt) {

			if (stmt.getPredicate().equals(DATA_DASH_SOURCEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.dataSource _DATA_DASH_SOURCE = null;
					try {
						_DATA_DASH_SOURCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getdataSource(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (DATA_DASH_SOURCE == null) {
						try {
							initDATA_DASH_SOURCE();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!DATA_DASH_SOURCE.contains(_DATA_DASH_SOURCE))
						DATA_DASH_SOURCE.add(_DATA_DASH_SOURCE);
					if (listeners != null) {
						java.util.ArrayList consumersForDATA_DASH_SOURCE;
						synchronized (listeners) {
							consumersForDATA_DASH_SOURCE = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForDATA_DASH_SOURCE.iterator();iter.hasNext();){
							entityListener listener=(entityListener)iter.next();
							listener.DATA_DASH_SOURCEAdded(fr.curie.BiNoM.pathways.biopax.entityImpl.this,_DATA_DASH_SOURCE);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(AVAILABILITYProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (AVAILABILITY == null) {
					try {
						initAVAILABILITY();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (!AVAILABILITY.contains(obj))
					AVAILABILITY.add(obj);
				java.util.ArrayList consumersForAVAILABILITY;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForAVAILABILITY = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForAVAILABILITY.iterator();iter.hasNext();){
						entityListener listener=(entityListener)iter.next();
						listener.AVAILABILITYAdded(fr.curie.BiNoM.pathways.biopax.entityImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
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
						entityListener listener=(entityListener)iter.next();
						listener.COMMENTAdded(fr.curie.BiNoM.pathways.biopax.entityImpl.this,(java.lang.String)obj);
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
						entityListener listener=(entityListener)iter.next();
						listener.SHORT_DASH_NAMEChanged(fr.curie.BiNoM.pathways.biopax.entityImpl.this);
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
						entityListener listener=(entityListener)iter.next();
						listener.SYNONYMSAdded(fr.curie.BiNoM.pathways.biopax.entityImpl.this,(java.lang.String)obj);
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
						entityListener listener=(entityListener)iter.next();
						listener.NAMEChanged(fr.curie.BiNoM.pathways.biopax.entityImpl.this);
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
							entityListener listener=(entityListener)iter.next();
							listener.XREFAdded(fr.curie.BiNoM.pathways.biopax.entityImpl.this,_XREF);
						}
					}
				}
				return;
			}
		}
		
		public void removedStatement(com.hp.hpl.jena.rdf.model.Statement stmt) {
//			if (!stmt.getSubject().equals(_resource))
//				return;
			if (stmt.getPredicate().equals(DATA_DASH_SOURCEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.dataSource _DATA_DASH_SOURCE = null;
					if (DATA_DASH_SOURCE != null) {
						boolean found = false;
						for (int i=0;i<DATA_DASH_SOURCE.size();i++) {
							fr.curie.BiNoM.pathways.biopax.dataSource __item = (fr.curie.BiNoM.pathways.biopax.dataSource) DATA_DASH_SOURCE.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_DATA_DASH_SOURCE = __item;
								break;
							}
						}
						if (found)
							DATA_DASH_SOURCE.remove(_DATA_DASH_SOURCE);
						else {
							try {
								_DATA_DASH_SOURCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getdataSource(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_DATA_DASH_SOURCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getdataSource(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForDATA_DASH_SOURCE;
						synchronized (listeners) {
							consumersForDATA_DASH_SOURCE = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForDATA_DASH_SOURCE.iterator();iter.hasNext();){
							entityListener listener=(entityListener)iter.next();
							listener.DATA_DASH_SOURCERemoved(fr.curie.BiNoM.pathways.biopax.entityImpl.this,_DATA_DASH_SOURCE);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(AVAILABILITYProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (AVAILABILITY != null) {
					if (AVAILABILITY.contains(obj))
						AVAILABILITY.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						entityListener listener=(entityListener)iter.next();
						listener.AVAILABILITYRemoved(fr.curie.BiNoM.pathways.biopax.entityImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
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
						entityListener listener=(entityListener)iter.next();
						listener.COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.entityImpl.this,(java.lang.String)obj);
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
						entityListener listener=(entityListener)iter.next();
						listener.SHORT_DASH_NAMEChanged(fr.curie.BiNoM.pathways.biopax.entityImpl.this);
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
						entityListener listener=(entityListener)iter.next();
						listener.SYNONYMSRemoved(fr.curie.BiNoM.pathways.biopax.entityImpl.this,(java.lang.String)obj);
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
						entityListener listener=(entityListener)iter.next();
						listener.NAMEChanged(fr.curie.BiNoM.pathways.biopax.entityImpl.this);
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
							entityListener listener=(entityListener)iter.next();
							listener.XREFRemoved(fr.curie.BiNoM.pathways.biopax.entityImpl.this,_XREF);
						}
					}
				}
				return;
			}
		}

	//}
	


}