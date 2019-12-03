

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.pathway}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#pathway)</p>
 * <br>
 */
public class pathwayImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.pathway {
	

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
	private static com.hp.hpl.jena.rdf.model.Property PATHWAY_DASH_COMPONENTSProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#PATHWAY-COMPONENTS");
	private java.util.ArrayList PATHWAY_DASH_COMPONENTS_asinteraction;
	private java.util.ArrayList PATHWAY_DASH_COMPONENTS_aspathwayStep;
	private java.util.ArrayList PATHWAY_DASH_COMPONENTS_aspathway;
	private static com.hp.hpl.jena.rdf.model.Property EVIDENCEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#EVIDENCE");
	private java.util.ArrayList EVIDENCE;
	private static com.hp.hpl.jena.rdf.model.Property ORGANISMProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#ORGANISM");
	private fr.curie.BiNoM.pathways.biopax.bioSource ORGANISM;

	pathwayImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static pathwayImpl getpathway(Resource resource, Model model) throws JastorException {
		return new pathwayImpl(resource, model);
	}
	    
	static pathwayImpl createpathway(Resource resource, Model model) throws JastorException {
		pathwayImpl impl = new pathwayImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, pathway.TYPE)))
			impl._model.add(impl._resource, RDF.type, pathway.TYPE);
		//impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.entity.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.entity.TYPE));     
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
		it = _model.listStatements(_resource,PATHWAY_DASH_COMPONENTSProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,EVIDENCEProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,ORGANISMProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.pathway.TYPE);
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
		PATHWAY_DASH_COMPONENTS_asinteraction = null;
		PATHWAY_DASH_COMPONENTS_aspathwayStep = null;
		PATHWAY_DASH_COMPONENTS_aspathway = null;
		EVIDENCE = null;
		ORGANISM = null;
	}


	private void initDATA_DASH_SOURCE() throws JastorException {
		this.DATA_DASH_SOURCE = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, DATA_DASH_SOURCEProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#DATA-SOURCE properties in pathway model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#AVAILABILITY properties in pathway model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#COMMENT properties in pathway model not a Literal", stmt.getObject());
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
			throw new JastorInvalidRDFNodeException(uri() + ": SHORT_DASH_NAME getProperty() in fr.curie.BiNoM.pathways.biopax.pathway model not Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#SYNONYMS properties in pathway model not a Literal", stmt.getObject());
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
			throw new JastorInvalidRDFNodeException(uri() + ": NAME getProperty() in fr.curie.BiNoM.pathways.biopax.pathway model not Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#XREF properties in pathway model not a Resource", stmt.getObject());
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
		 

	private void initPATHWAY_DASH_COMPONENTS_asinteraction() throws JastorException {
		this.PATHWAY_DASH_COMPONENTS_asinteraction = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, PATHWAY_DASH_COMPONENTSProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#PATHWAY-COMPONENTS properties in pathway model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.interaction.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.interaction PATHWAY_DASH_COMPONENTS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getinteraction(resource,_model);
				this.PATHWAY_DASH_COMPONENTS_asinteraction.add(PATHWAY_DASH_COMPONENTS);
			}
		}
	}

	public java.util.Iterator getPATHWAY_DASH_COMPONENTS_asinteraction() throws JastorException {
		if (PATHWAY_DASH_COMPONENTS_asinteraction == null)
			initPATHWAY_DASH_COMPONENTS_asinteraction();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(PATHWAY_DASH_COMPONENTS_asinteraction,_resource,PATHWAY_DASH_COMPONENTSProperty,true);
	}

	public void addPATHWAY_DASH_COMPONENTS(fr.curie.BiNoM.pathways.biopax.interaction PATHWAY_DASH_COMPONENTS) throws JastorException {
		if (this.PATHWAY_DASH_COMPONENTS_asinteraction == null)
			initPATHWAY_DASH_COMPONENTS_asinteraction();
		if (this.PATHWAY_DASH_COMPONENTS_asinteraction.contains(PATHWAY_DASH_COMPONENTS)) {
			this.PATHWAY_DASH_COMPONENTS_asinteraction.remove(PATHWAY_DASH_COMPONENTS);
			this.PATHWAY_DASH_COMPONENTS_asinteraction.add(PATHWAY_DASH_COMPONENTS);
			return;
		}
		this.PATHWAY_DASH_COMPONENTS_asinteraction.add(PATHWAY_DASH_COMPONENTS);
		_model.add(_model.createStatement(_resource,PATHWAY_DASH_COMPONENTSProperty,PATHWAY_DASH_COMPONENTS.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.interaction addPATHWAY_DASH_COMPONENTS_asinteraction() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.interaction PATHWAY_DASH_COMPONENTS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createinteraction(_model.createResource(),_model);
		if (this.PATHWAY_DASH_COMPONENTS_asinteraction == null)
			initPATHWAY_DASH_COMPONENTS_asinteraction();
		this.PATHWAY_DASH_COMPONENTS_asinteraction.add(PATHWAY_DASH_COMPONENTS);
		_model.add(_model.createStatement(_resource,PATHWAY_DASH_COMPONENTSProperty,PATHWAY_DASH_COMPONENTS.resource()));
		return PATHWAY_DASH_COMPONENTS;
	}
	
	public fr.curie.BiNoM.pathways.biopax.interaction addPATHWAY_DASH_COMPONENTS_asinteraction(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.interaction PATHWAY_DASH_COMPONENTS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getinteraction(resource,_model);
		if (this.PATHWAY_DASH_COMPONENTS_asinteraction == null)
			initPATHWAY_DASH_COMPONENTS_asinteraction();
		if (this.PATHWAY_DASH_COMPONENTS_asinteraction.contains(PATHWAY_DASH_COMPONENTS))
			return PATHWAY_DASH_COMPONENTS;
		this.PATHWAY_DASH_COMPONENTS_asinteraction.add(PATHWAY_DASH_COMPONENTS);
		_model.add(_model.createStatement(_resource,PATHWAY_DASH_COMPONENTSProperty,PATHWAY_DASH_COMPONENTS.resource()));
		return PATHWAY_DASH_COMPONENTS;
	}
	
	public void removePATHWAY_DASH_COMPONENTS(fr.curie.BiNoM.pathways.biopax.interaction PATHWAY_DASH_COMPONENTS) throws JastorException {
		if (this.PATHWAY_DASH_COMPONENTS_asinteraction == null)
			initPATHWAY_DASH_COMPONENTS_asinteraction();
		if (!this.PATHWAY_DASH_COMPONENTS_asinteraction.contains(PATHWAY_DASH_COMPONENTS))
			return;
		if (!_model.contains(_resource, PATHWAY_DASH_COMPONENTSProperty, PATHWAY_DASH_COMPONENTS.resource()))
			return;
		this.PATHWAY_DASH_COMPONENTS_asinteraction.remove(PATHWAY_DASH_COMPONENTS);
		_model.removeAll(_resource, PATHWAY_DASH_COMPONENTSProperty, PATHWAY_DASH_COMPONENTS.resource());
	}
		
	private void initPATHWAY_DASH_COMPONENTS_aspathwayStep() throws JastorException {
		this.PATHWAY_DASH_COMPONENTS_aspathwayStep = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, PATHWAY_DASH_COMPONENTSProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#PATHWAY-COMPONENTS properties in pathway model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.pathwayStep.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.pathwayStep PATHWAY_DASH_COMPONENTS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathwayStep(resource,_model);
				this.PATHWAY_DASH_COMPONENTS_aspathwayStep.add(PATHWAY_DASH_COMPONENTS);
			}
		}
	}

	public java.util.Iterator getPATHWAY_DASH_COMPONENTS_aspathwayStep() throws JastorException {
		if (PATHWAY_DASH_COMPONENTS_aspathwayStep == null)
			initPATHWAY_DASH_COMPONENTS_aspathwayStep();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(PATHWAY_DASH_COMPONENTS_aspathwayStep,_resource,PATHWAY_DASH_COMPONENTSProperty,true);
	}

	public void addPATHWAY_DASH_COMPONENTS(fr.curie.BiNoM.pathways.biopax.pathwayStep PATHWAY_DASH_COMPONENTS) throws JastorException {
		if (this.PATHWAY_DASH_COMPONENTS_aspathwayStep == null)
			initPATHWAY_DASH_COMPONENTS_aspathwayStep();
		if (this.PATHWAY_DASH_COMPONENTS_aspathwayStep.contains(PATHWAY_DASH_COMPONENTS)) {
			this.PATHWAY_DASH_COMPONENTS_aspathwayStep.remove(PATHWAY_DASH_COMPONENTS);
			this.PATHWAY_DASH_COMPONENTS_aspathwayStep.add(PATHWAY_DASH_COMPONENTS);
			return;
		}
		this.PATHWAY_DASH_COMPONENTS_aspathwayStep.add(PATHWAY_DASH_COMPONENTS);
		_model.add(_model.createStatement(_resource,PATHWAY_DASH_COMPONENTSProperty,PATHWAY_DASH_COMPONENTS.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.pathwayStep addPATHWAY_DASH_COMPONENTS_aspathwayStep() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.pathwayStep PATHWAY_DASH_COMPONENTS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createpathwayStep(_model.createResource(),_model);
		if (this.PATHWAY_DASH_COMPONENTS_aspathwayStep == null)
			initPATHWAY_DASH_COMPONENTS_aspathwayStep();
		this.PATHWAY_DASH_COMPONENTS_aspathwayStep.add(PATHWAY_DASH_COMPONENTS);
		_model.add(_model.createStatement(_resource,PATHWAY_DASH_COMPONENTSProperty,PATHWAY_DASH_COMPONENTS.resource()));
		return PATHWAY_DASH_COMPONENTS;
	}
	
	public fr.curie.BiNoM.pathways.biopax.pathwayStep addPATHWAY_DASH_COMPONENTS_aspathwayStep(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.pathwayStep PATHWAY_DASH_COMPONENTS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathwayStep(resource,_model);
		if (this.PATHWAY_DASH_COMPONENTS_aspathwayStep == null)
			initPATHWAY_DASH_COMPONENTS_aspathwayStep();
		if (this.PATHWAY_DASH_COMPONENTS_aspathwayStep.contains(PATHWAY_DASH_COMPONENTS))
			return PATHWAY_DASH_COMPONENTS;
		this.PATHWAY_DASH_COMPONENTS_aspathwayStep.add(PATHWAY_DASH_COMPONENTS);
		_model.add(_model.createStatement(_resource,PATHWAY_DASH_COMPONENTSProperty,PATHWAY_DASH_COMPONENTS.resource()));
		return PATHWAY_DASH_COMPONENTS;
	}
	
	public void removePATHWAY_DASH_COMPONENTS(fr.curie.BiNoM.pathways.biopax.pathwayStep PATHWAY_DASH_COMPONENTS) throws JastorException {
		if (this.PATHWAY_DASH_COMPONENTS_aspathwayStep == null)
			initPATHWAY_DASH_COMPONENTS_aspathwayStep();
		if (!this.PATHWAY_DASH_COMPONENTS_aspathwayStep.contains(PATHWAY_DASH_COMPONENTS))
			return;
		if (!_model.contains(_resource, PATHWAY_DASH_COMPONENTSProperty, PATHWAY_DASH_COMPONENTS.resource()))
			return;
		this.PATHWAY_DASH_COMPONENTS_aspathwayStep.remove(PATHWAY_DASH_COMPONENTS);
		_model.removeAll(_resource, PATHWAY_DASH_COMPONENTSProperty, PATHWAY_DASH_COMPONENTS.resource());
	}
		
	private void initPATHWAY_DASH_COMPONENTS_aspathway() throws JastorException {
		this.PATHWAY_DASH_COMPONENTS_aspathway = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, PATHWAY_DASH_COMPONENTSProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#PATHWAY-COMPONENTS properties in pathway model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.pathway.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.pathway PATHWAY_DASH_COMPONENTS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathway(resource,_model);
				this.PATHWAY_DASH_COMPONENTS_aspathway.add(PATHWAY_DASH_COMPONENTS);
			}
		}
	}

	public java.util.Iterator getPATHWAY_DASH_COMPONENTS_aspathway() throws JastorException {
		if (PATHWAY_DASH_COMPONENTS_aspathway == null)
			initPATHWAY_DASH_COMPONENTS_aspathway();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(PATHWAY_DASH_COMPONENTS_aspathway,_resource,PATHWAY_DASH_COMPONENTSProperty,true);
	}

	public void addPATHWAY_DASH_COMPONENTS(fr.curie.BiNoM.pathways.biopax.pathway PATHWAY_DASH_COMPONENTS) throws JastorException {
		if (this.PATHWAY_DASH_COMPONENTS_aspathway == null)
			initPATHWAY_DASH_COMPONENTS_aspathway();
		if (this.PATHWAY_DASH_COMPONENTS_aspathway.contains(PATHWAY_DASH_COMPONENTS)) {
			this.PATHWAY_DASH_COMPONENTS_aspathway.remove(PATHWAY_DASH_COMPONENTS);
			this.PATHWAY_DASH_COMPONENTS_aspathway.add(PATHWAY_DASH_COMPONENTS);
			return;
		}
		this.PATHWAY_DASH_COMPONENTS_aspathway.add(PATHWAY_DASH_COMPONENTS);
		_model.add(_model.createStatement(_resource,PATHWAY_DASH_COMPONENTSProperty,PATHWAY_DASH_COMPONENTS.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.pathway addPATHWAY_DASH_COMPONENTS_aspathway() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.pathway PATHWAY_DASH_COMPONENTS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createpathway(_model.createResource(),_model);
		if (this.PATHWAY_DASH_COMPONENTS_aspathway == null)
			initPATHWAY_DASH_COMPONENTS_aspathway();
		this.PATHWAY_DASH_COMPONENTS_aspathway.add(PATHWAY_DASH_COMPONENTS);
		_model.add(_model.createStatement(_resource,PATHWAY_DASH_COMPONENTSProperty,PATHWAY_DASH_COMPONENTS.resource()));
		return PATHWAY_DASH_COMPONENTS;
	}
	
	public fr.curie.BiNoM.pathways.biopax.pathway addPATHWAY_DASH_COMPONENTS_aspathway(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.pathway PATHWAY_DASH_COMPONENTS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathway(resource,_model);
		if (this.PATHWAY_DASH_COMPONENTS_aspathway == null)
			initPATHWAY_DASH_COMPONENTS_aspathway();
		if (this.PATHWAY_DASH_COMPONENTS_aspathway.contains(PATHWAY_DASH_COMPONENTS))
			return PATHWAY_DASH_COMPONENTS;
		this.PATHWAY_DASH_COMPONENTS_aspathway.add(PATHWAY_DASH_COMPONENTS);
		_model.add(_model.createStatement(_resource,PATHWAY_DASH_COMPONENTSProperty,PATHWAY_DASH_COMPONENTS.resource()));
		return PATHWAY_DASH_COMPONENTS;
	}
	
	public void removePATHWAY_DASH_COMPONENTS(fr.curie.BiNoM.pathways.biopax.pathway PATHWAY_DASH_COMPONENTS) throws JastorException {
		if (this.PATHWAY_DASH_COMPONENTS_aspathway == null)
			initPATHWAY_DASH_COMPONENTS_aspathway();
		if (!this.PATHWAY_DASH_COMPONENTS_aspathway.contains(PATHWAY_DASH_COMPONENTS))
			return;
		if (!_model.contains(_resource, PATHWAY_DASH_COMPONENTSProperty, PATHWAY_DASH_COMPONENTS.resource()))
			return;
		this.PATHWAY_DASH_COMPONENTS_aspathway.remove(PATHWAY_DASH_COMPONENTS);
		_model.removeAll(_resource, PATHWAY_DASH_COMPONENTSProperty, PATHWAY_DASH_COMPONENTS.resource());
	}
		 

	private void initEVIDENCE() throws JastorException {
		this.EVIDENCE = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, EVIDENCEProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#EVIDENCE properties in pathway model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.evidence EVIDENCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getevidence(resource,_model);
				this.EVIDENCE.add(EVIDENCE);
			}
		}
	}

	public java.util.Iterator getEVIDENCE() throws JastorException {
		if (EVIDENCE == null)
			initEVIDENCE();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(EVIDENCE,_resource,EVIDENCEProperty,true);
	}

	public void addEVIDENCE(fr.curie.BiNoM.pathways.biopax.evidence EVIDENCE) throws JastorException {
		if (this.EVIDENCE == null)
			initEVIDENCE();
		if (this.EVIDENCE.contains(EVIDENCE)) {
			this.EVIDENCE.remove(EVIDENCE);
			this.EVIDENCE.add(EVIDENCE);
			return;
		}
		this.EVIDENCE.add(EVIDENCE);
		_model.add(_model.createStatement(_resource,EVIDENCEProperty,EVIDENCE.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.evidence addEVIDENCE() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.evidence EVIDENCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createevidence(_model.createResource(),_model);
		if (this.EVIDENCE == null)
			initEVIDENCE();
		this.EVIDENCE.add(EVIDENCE);
		_model.add(_model.createStatement(_resource,EVIDENCEProperty,EVIDENCE.resource()));
		return EVIDENCE;
	}
	
	public fr.curie.BiNoM.pathways.biopax.evidence addEVIDENCE(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.evidence EVIDENCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getevidence(resource,_model);
		if (this.EVIDENCE == null)
			initEVIDENCE();
		if (this.EVIDENCE.contains(EVIDENCE))
			return EVIDENCE;
		this.EVIDENCE.add(EVIDENCE);
		_model.add(_model.createStatement(_resource,EVIDENCEProperty,EVIDENCE.resource()));
		return EVIDENCE;
	}
	
	public void removeEVIDENCE(fr.curie.BiNoM.pathways.biopax.evidence EVIDENCE) throws JastorException {
		if (this.EVIDENCE == null)
			initEVIDENCE();
		if (!this.EVIDENCE.contains(EVIDENCE))
			return;
		if (!_model.contains(_resource, EVIDENCEProperty, EVIDENCE.resource()))
			return;
		this.EVIDENCE.remove(EVIDENCE);
		_model.removeAll(_resource, EVIDENCEProperty, EVIDENCE.resource());
	}
		 

	public fr.curie.BiNoM.pathways.biopax.bioSource getORGANISM() throws JastorException {
		if (ORGANISM != null)
			return ORGANISM;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, ORGANISMProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": ORGANISM getProperty() in fr.curie.BiNoM.pathways.biopax.pathway model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		ORGANISM = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getbioSource(resource,_model);
		return ORGANISM;
	}

	public void setORGANISM(fr.curie.BiNoM.pathways.biopax.bioSource ORGANISM) throws JastorException {
		if (_model.contains(_resource,ORGANISMProperty)) {
			_model.removeAll(_resource,ORGANISMProperty,null);
		}
		this.ORGANISM = ORGANISM;
		if (ORGANISM != null) {
			_model.add(_model.createStatement(_resource,ORGANISMProperty, ORGANISM.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.bioSource setORGANISM() throws JastorException {
		if (_model.contains(_resource,ORGANISMProperty)) {
			_model.removeAll(_resource,ORGANISMProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.bioSource ORGANISM = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createbioSource(_model.createResource(),_model);
		this.ORGANISM = ORGANISM;
		_model.add(_model.createStatement(_resource,ORGANISMProperty, ORGANISM.resource()));
		return ORGANISM;
	}
	
	public fr.curie.BiNoM.pathways.biopax.bioSource setORGANISM(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,ORGANISMProperty)) {
			_model.removeAll(_resource,ORGANISMProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.bioSource ORGANISM = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getbioSource(resource,_model);
		this.ORGANISM = ORGANISM;
		_model.add(_model.createStatement(_resource,ORGANISMProperty, ORGANISM.resource()));
		return ORGANISM;
	}
	 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof pathwayListener))
			throw new IllegalArgumentException("ThingListener must be instance of pathwayListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((pathwayListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof pathwayListener))
			throw new IllegalArgumentException("ThingListener must be instance of pathwayListener"); 
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
							pathwayListener listener=(pathwayListener)iter.next();
							listener.DATA_DASH_SOURCEAdded(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,_DATA_DASH_SOURCE);
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
						pathwayListener listener=(pathwayListener)iter.next();
						listener.AVAILABILITYAdded(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,(java.lang.String)obj);
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
						pathwayListener listener=(pathwayListener)iter.next();
						listener.COMMENTAdded(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,(java.lang.String)obj);
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
						pathwayListener listener=(pathwayListener)iter.next();
						listener.SHORT_DASH_NAMEChanged(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this);
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
						pathwayListener listener=(pathwayListener)iter.next();
						listener.SYNONYMSAdded(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,(java.lang.String)obj);
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
						pathwayListener listener=(pathwayListener)iter.next();
						listener.NAMEChanged(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this);
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
							pathwayListener listener=(pathwayListener)iter.next();
							listener.XREFAdded(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,_XREF);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(PATHWAY_DASH_COMPONENTSProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.interaction.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.interaction _PATHWAY_DASH_COMPONENTS_asinteraction = null;
					try {
						_PATHWAY_DASH_COMPONENTS_asinteraction = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getinteraction(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (PATHWAY_DASH_COMPONENTS_asinteraction == null) {
						try {
							initPATHWAY_DASH_COMPONENTS_asinteraction();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!PATHWAY_DASH_COMPONENTS_asinteraction.contains(_PATHWAY_DASH_COMPONENTS_asinteraction))
						PATHWAY_DASH_COMPONENTS_asinteraction.add(_PATHWAY_DASH_COMPONENTS_asinteraction);
					if (listeners != null) {
						java.util.ArrayList consumersForPATHWAY_DASH_COMPONENTS_asinteraction;
						synchronized (listeners) {
							consumersForPATHWAY_DASH_COMPONENTS_asinteraction = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPATHWAY_DASH_COMPONENTS_asinteraction.iterator();iter.hasNext();){
							pathwayListener listener=(pathwayListener)iter.next();
							listener.PATHWAY_DASH_COMPONENTSAdded(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,_PATHWAY_DASH_COMPONENTS_asinteraction);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.pathwayStep.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.pathwayStep _PATHWAY_DASH_COMPONENTS_aspathwayStep = null;
					try {
						_PATHWAY_DASH_COMPONENTS_aspathwayStep = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathwayStep(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (PATHWAY_DASH_COMPONENTS_aspathwayStep == null) {
						try {
							initPATHWAY_DASH_COMPONENTS_aspathwayStep();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!PATHWAY_DASH_COMPONENTS_aspathwayStep.contains(_PATHWAY_DASH_COMPONENTS_aspathwayStep))
						PATHWAY_DASH_COMPONENTS_aspathwayStep.add(_PATHWAY_DASH_COMPONENTS_aspathwayStep);
					if (listeners != null) {
						java.util.ArrayList consumersForPATHWAY_DASH_COMPONENTS_aspathwayStep;
						synchronized (listeners) {
							consumersForPATHWAY_DASH_COMPONENTS_aspathwayStep = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPATHWAY_DASH_COMPONENTS_aspathwayStep.iterator();iter.hasNext();){
							pathwayListener listener=(pathwayListener)iter.next();
							listener.PATHWAY_DASH_COMPONENTSAdded(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,_PATHWAY_DASH_COMPONENTS_aspathwayStep);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.pathway.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.pathway _PATHWAY_DASH_COMPONENTS_aspathway = null;
					try {
						_PATHWAY_DASH_COMPONENTS_aspathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathway(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (PATHWAY_DASH_COMPONENTS_aspathway == null) {
						try {
							initPATHWAY_DASH_COMPONENTS_aspathway();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!PATHWAY_DASH_COMPONENTS_aspathway.contains(_PATHWAY_DASH_COMPONENTS_aspathway))
						PATHWAY_DASH_COMPONENTS_aspathway.add(_PATHWAY_DASH_COMPONENTS_aspathway);
					if (listeners != null) {
						java.util.ArrayList consumersForPATHWAY_DASH_COMPONENTS_aspathway;
						synchronized (listeners) {
							consumersForPATHWAY_DASH_COMPONENTS_aspathway = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPATHWAY_DASH_COMPONENTS_aspathway.iterator();iter.hasNext();){
							pathwayListener listener=(pathwayListener)iter.next();
							listener.PATHWAY_DASH_COMPONENTSAdded(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,_PATHWAY_DASH_COMPONENTS_aspathway);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(EVIDENCEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.evidence _EVIDENCE = null;
					try {
						_EVIDENCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getevidence(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (EVIDENCE == null) {
						try {
							initEVIDENCE();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!EVIDENCE.contains(_EVIDENCE))
						EVIDENCE.add(_EVIDENCE);
					if (listeners != null) {
						java.util.ArrayList consumersForEVIDENCE;
						synchronized (listeners) {
							consumersForEVIDENCE = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEVIDENCE.iterator();iter.hasNext();){
							pathwayListener listener=(pathwayListener)iter.next();
							listener.EVIDENCEAdded(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,_EVIDENCE);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ORGANISMProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				ORGANISM = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						ORGANISM = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getbioSource(resource,_model);
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
						pathwayListener listener=(pathwayListener)iter.next();
						listener.ORGANISMChanged(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this);
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
							pathwayListener listener=(pathwayListener)iter.next();
							listener.DATA_DASH_SOURCERemoved(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,_DATA_DASH_SOURCE);
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
						pathwayListener listener=(pathwayListener)iter.next();
						listener.AVAILABILITYRemoved(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,(java.lang.String)obj);
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
						pathwayListener listener=(pathwayListener)iter.next();
						listener.COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,(java.lang.String)obj);
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
						pathwayListener listener=(pathwayListener)iter.next();
						listener.SHORT_DASH_NAMEChanged(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this);
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
						pathwayListener listener=(pathwayListener)iter.next();
						listener.SYNONYMSRemoved(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,(java.lang.String)obj);
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
						pathwayListener listener=(pathwayListener)iter.next();
						listener.NAMEChanged(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this);
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
							pathwayListener listener=(pathwayListener)iter.next();
							listener.XREFRemoved(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,_XREF);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(PATHWAY_DASH_COMPONENTSProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.interaction.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.interaction _PATHWAY_DASH_COMPONENTS_asinteraction = null;
					if (PATHWAY_DASH_COMPONENTS_asinteraction != null) {
						boolean found = false;
						for (int i=0;i<PATHWAY_DASH_COMPONENTS_asinteraction.size();i++) {
							fr.curie.BiNoM.pathways.biopax.interaction __item = (fr.curie.BiNoM.pathways.biopax.interaction) PATHWAY_DASH_COMPONENTS_asinteraction.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_PATHWAY_DASH_COMPONENTS_asinteraction = __item;
								break;
							}
						}
						if (found)
							PATHWAY_DASH_COMPONENTS_asinteraction.remove(_PATHWAY_DASH_COMPONENTS_asinteraction);
						else {
							try {
								_PATHWAY_DASH_COMPONENTS_asinteraction = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getinteraction(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_PATHWAY_DASH_COMPONENTS_asinteraction = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getinteraction(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForPATHWAY_DASH_COMPONENTS_asinteraction;
						synchronized (listeners) {
							consumersForPATHWAY_DASH_COMPONENTS_asinteraction = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPATHWAY_DASH_COMPONENTS_asinteraction.iterator();iter.hasNext();){
							pathwayListener listener=(pathwayListener)iter.next();
							listener.PATHWAY_DASH_COMPONENTSRemoved(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,_PATHWAY_DASH_COMPONENTS_asinteraction);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.pathwayStep.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.pathwayStep _PATHWAY_DASH_COMPONENTS_aspathwayStep = null;
					if (PATHWAY_DASH_COMPONENTS_aspathwayStep != null) {
						boolean found = false;
						for (int i=0;i<PATHWAY_DASH_COMPONENTS_aspathwayStep.size();i++) {
							fr.curie.BiNoM.pathways.biopax.pathwayStep __item = (fr.curie.BiNoM.pathways.biopax.pathwayStep) PATHWAY_DASH_COMPONENTS_aspathwayStep.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_PATHWAY_DASH_COMPONENTS_aspathwayStep = __item;
								break;
							}
						}
						if (found)
							PATHWAY_DASH_COMPONENTS_aspathwayStep.remove(_PATHWAY_DASH_COMPONENTS_aspathwayStep);
						else {
							try {
								_PATHWAY_DASH_COMPONENTS_aspathwayStep = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathwayStep(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_PATHWAY_DASH_COMPONENTS_aspathwayStep = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathwayStep(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForPATHWAY_DASH_COMPONENTS_aspathwayStep;
						synchronized (listeners) {
							consumersForPATHWAY_DASH_COMPONENTS_aspathwayStep = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPATHWAY_DASH_COMPONENTS_aspathwayStep.iterator();iter.hasNext();){
							pathwayListener listener=(pathwayListener)iter.next();
							listener.PATHWAY_DASH_COMPONENTSRemoved(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,_PATHWAY_DASH_COMPONENTS_aspathwayStep);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.pathway.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.pathway _PATHWAY_DASH_COMPONENTS_aspathway = null;
					if (PATHWAY_DASH_COMPONENTS_aspathway != null) {
						boolean found = false;
						for (int i=0;i<PATHWAY_DASH_COMPONENTS_aspathway.size();i++) {
							fr.curie.BiNoM.pathways.biopax.pathway __item = (fr.curie.BiNoM.pathways.biopax.pathway) PATHWAY_DASH_COMPONENTS_aspathway.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_PATHWAY_DASH_COMPONENTS_aspathway = __item;
								break;
							}
						}
						if (found)
							PATHWAY_DASH_COMPONENTS_aspathway.remove(_PATHWAY_DASH_COMPONENTS_aspathway);
						else {
							try {
								_PATHWAY_DASH_COMPONENTS_aspathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathway(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_PATHWAY_DASH_COMPONENTS_aspathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getpathway(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForPATHWAY_DASH_COMPONENTS_aspathway;
						synchronized (listeners) {
							consumersForPATHWAY_DASH_COMPONENTS_aspathway = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPATHWAY_DASH_COMPONENTS_aspathway.iterator();iter.hasNext();){
							pathwayListener listener=(pathwayListener)iter.next();
							listener.PATHWAY_DASH_COMPONENTSRemoved(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,_PATHWAY_DASH_COMPONENTS_aspathway);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(EVIDENCEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.evidence _EVIDENCE = null;
					if (EVIDENCE != null) {
						boolean found = false;
						for (int i=0;i<EVIDENCE.size();i++) {
							fr.curie.BiNoM.pathways.biopax.evidence __item = (fr.curie.BiNoM.pathways.biopax.evidence) EVIDENCE.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_EVIDENCE = __item;
								break;
							}
						}
						if (found)
							EVIDENCE.remove(_EVIDENCE);
						else {
							try {
								_EVIDENCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getevidence(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_EVIDENCE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getevidence(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForEVIDENCE;
						synchronized (listeners) {
							consumersForEVIDENCE = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEVIDENCE.iterator();iter.hasNext();){
							pathwayListener listener=(pathwayListener)iter.next();
							listener.EVIDENCERemoved(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this,_EVIDENCE);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ORGANISMProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (ORGANISM != null && ORGANISM.resource().equals(resource))
						ORGANISM = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						pathwayListener listener=(pathwayListener)iter.next();
						listener.ORGANISMChanged(fr.curie.BiNoM.pathways.biopax.pathwayImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}