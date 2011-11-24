

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.complexAssembly}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#complexAssembly)</p>
 * <br>
 */
public class complexAssemblyImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.complexAssembly {
	

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
	private static com.hp.hpl.jena.rdf.model.Property PARTICIPANTSProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#PARTICIPANTS");
	private java.util.ArrayList PARTICIPANTS_asphysicalEntityParticipant;
	private java.util.ArrayList PARTICIPANTS_asentity;
	private static com.hp.hpl.jena.rdf.model.Property EVIDENCEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#EVIDENCE");
	private java.util.ArrayList EVIDENCE;
	private static com.hp.hpl.jena.rdf.model.Property INTERACTION_DASH_TYPEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#INTERACTION-TYPE");
	private java.util.ArrayList INTERACTION_DASH_TYPE;
	private static com.hp.hpl.jena.rdf.model.Property LEFTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#LEFT");
	private java.util.ArrayList LEFT;
	private static com.hp.hpl.jena.rdf.model.Property SPONTANEOUSProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#SPONTANEOUS");
	private java.lang.String SPONTANEOUS;
	private static com.hp.hpl.jena.rdf.model.Property RIGHTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#RIGHT");
	private java.util.ArrayList RIGHT;

	complexAssemblyImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static complexAssemblyImpl getcomplexAssembly(Resource resource, Model model) throws JastorException {
		return new complexAssemblyImpl(resource, model);
	}
	    
	static complexAssemblyImpl createcomplexAssembly(Resource resource, Model model) throws JastorException {
		complexAssemblyImpl impl = new complexAssemblyImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, complexAssembly.TYPE)))
			impl._model.add(impl._resource, RDF.type, complexAssembly.TYPE);
		//impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.entity.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.entity.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.interaction.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.interaction.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.physicalInteraction.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.physicalInteraction.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.conversion.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.conversion.TYPE));     
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
		it = _model.listStatements(_resource,PARTICIPANTSProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,EVIDENCEProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,INTERACTION_DASH_TYPEProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,LEFTProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,SPONTANEOUSProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RIGHTProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.complexAssembly.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.entity.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.interaction.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.physicalInteraction.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.conversion.TYPE);
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
		PARTICIPANTS_asphysicalEntityParticipant = null;
		PARTICIPANTS_asentity = null;
		EVIDENCE = null;
		INTERACTION_DASH_TYPE = null;
		LEFT = null;
		SPONTANEOUS = null;
		RIGHT = null;
	}


	private void initDATA_DASH_SOURCE() throws JastorException {
		this.DATA_DASH_SOURCE = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, DATA_DASH_SOURCEProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#DATA-SOURCE properties in complexAssembly model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#AVAILABILITY properties in complexAssembly model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#COMMENT properties in complexAssembly model not a Literal", stmt.getObject());
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
			throw new JastorInvalidRDFNodeException(uri() + ": SHORT_DASH_NAME getProperty() in fr.curie.BiNoM.pathways.biopax.complexAssembly model not Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#SYNONYMS properties in complexAssembly model not a Literal", stmt.getObject());
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
			throw new JastorInvalidRDFNodeException(uri() + ": NAME getProperty() in fr.curie.BiNoM.pathways.biopax.complexAssembly model not Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#XREF properties in complexAssembly model not a Resource", stmt.getObject());
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
		 

	private void initPARTICIPANTS_asphysicalEntityParticipant() throws JastorException {
		this.PARTICIPANTS_asphysicalEntityParticipant = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, PARTICIPANTSProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#PARTICIPANTS properties in complexAssembly model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANTS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
				this.PARTICIPANTS_asphysicalEntityParticipant.add(PARTICIPANTS);
			}
		}
	}

	public java.util.Iterator getPARTICIPANTS_asphysicalEntityParticipant() throws JastorException {
		if (PARTICIPANTS_asphysicalEntityParticipant == null)
			initPARTICIPANTS_asphysicalEntityParticipant();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(PARTICIPANTS_asphysicalEntityParticipant,_resource,PARTICIPANTSProperty,true);
	}

	public void addPARTICIPANTS(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANTS) throws JastorException {
		if (this.PARTICIPANTS_asphysicalEntityParticipant == null)
			initPARTICIPANTS_asphysicalEntityParticipant();
		if (this.PARTICIPANTS_asphysicalEntityParticipant.contains(PARTICIPANTS)) {
			this.PARTICIPANTS_asphysicalEntityParticipant.remove(PARTICIPANTS);
			this.PARTICIPANTS_asphysicalEntityParticipant.add(PARTICIPANTS);
			return;
		}
		this.PARTICIPANTS_asphysicalEntityParticipant.add(PARTICIPANTS);
		_model.add(_model.createStatement(_resource,PARTICIPANTSProperty,PARTICIPANTS.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addPARTICIPANTS_asphysicalEntityParticipant() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANTS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createphysicalEntityParticipant(_model.createResource(),_model);
		if (this.PARTICIPANTS_asphysicalEntityParticipant == null)
			initPARTICIPANTS_asphysicalEntityParticipant();
		this.PARTICIPANTS_asphysicalEntityParticipant.add(PARTICIPANTS);
		_model.add(_model.createStatement(_resource,PARTICIPANTSProperty,PARTICIPANTS.resource()));
		return PARTICIPANTS;
	}
	
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addPARTICIPANTS_asphysicalEntityParticipant(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANTS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
		if (this.PARTICIPANTS_asphysicalEntityParticipant == null)
			initPARTICIPANTS_asphysicalEntityParticipant();
		if (this.PARTICIPANTS_asphysicalEntityParticipant.contains(PARTICIPANTS))
			return PARTICIPANTS;
		this.PARTICIPANTS_asphysicalEntityParticipant.add(PARTICIPANTS);
		_model.add(_model.createStatement(_resource,PARTICIPANTSProperty,PARTICIPANTS.resource()));
		return PARTICIPANTS;
	}
	
	public void removePARTICIPANTS(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant PARTICIPANTS) throws JastorException {
		if (this.PARTICIPANTS_asphysicalEntityParticipant == null)
			initPARTICIPANTS_asphysicalEntityParticipant();
		if (!this.PARTICIPANTS_asphysicalEntityParticipant.contains(PARTICIPANTS))
			return;
		if (!_model.contains(_resource, PARTICIPANTSProperty, PARTICIPANTS.resource()))
			return;
		this.PARTICIPANTS_asphysicalEntityParticipant.remove(PARTICIPANTS);
		_model.removeAll(_resource, PARTICIPANTSProperty, PARTICIPANTS.resource());
	}
		
	private void initPARTICIPANTS_asentity() throws JastorException {
		this.PARTICIPANTS_asentity = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, PARTICIPANTSProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#PARTICIPANTS properties in complexAssembly model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.entity.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.entity PARTICIPANTS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getentity(resource,_model);
				this.PARTICIPANTS_asentity.add(PARTICIPANTS);
			}
		}
	}

	public java.util.Iterator getPARTICIPANTS_asentity() throws JastorException {
		if (PARTICIPANTS_asentity == null)
			initPARTICIPANTS_asentity();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(PARTICIPANTS_asentity,_resource,PARTICIPANTSProperty,true);
	}

	public void addPARTICIPANTS(fr.curie.BiNoM.pathways.biopax.entity PARTICIPANTS) throws JastorException {
		if (this.PARTICIPANTS_asentity == null)
			initPARTICIPANTS_asentity();
		if (this.PARTICIPANTS_asentity.contains(PARTICIPANTS)) {
			this.PARTICIPANTS_asentity.remove(PARTICIPANTS);
			this.PARTICIPANTS_asentity.add(PARTICIPANTS);
			return;
		}
		this.PARTICIPANTS_asentity.add(PARTICIPANTS);
		_model.add(_model.createStatement(_resource,PARTICIPANTSProperty,PARTICIPANTS.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.entity addPARTICIPANTS_asentity() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.entity PARTICIPANTS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createentity(_model.createResource(),_model);
		if (this.PARTICIPANTS_asentity == null)
			initPARTICIPANTS_asentity();
		this.PARTICIPANTS_asentity.add(PARTICIPANTS);
		_model.add(_model.createStatement(_resource,PARTICIPANTSProperty,PARTICIPANTS.resource()));
		return PARTICIPANTS;
	}
	
	public fr.curie.BiNoM.pathways.biopax.entity addPARTICIPANTS_asentity(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.entity PARTICIPANTS = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getentity(resource,_model);
		if (this.PARTICIPANTS_asentity == null)
			initPARTICIPANTS_asentity();
		if (this.PARTICIPANTS_asentity.contains(PARTICIPANTS))
			return PARTICIPANTS;
		this.PARTICIPANTS_asentity.add(PARTICIPANTS);
		_model.add(_model.createStatement(_resource,PARTICIPANTSProperty,PARTICIPANTS.resource()));
		return PARTICIPANTS;
	}
	
	public void removePARTICIPANTS(fr.curie.BiNoM.pathways.biopax.entity PARTICIPANTS) throws JastorException {
		if (this.PARTICIPANTS_asentity == null)
			initPARTICIPANTS_asentity();
		if (!this.PARTICIPANTS_asentity.contains(PARTICIPANTS))
			return;
		if (!_model.contains(_resource, PARTICIPANTSProperty, PARTICIPANTS.resource()))
			return;
		this.PARTICIPANTS_asentity.remove(PARTICIPANTS);
		_model.removeAll(_resource, PARTICIPANTSProperty, PARTICIPANTS.resource());
	}
		 

	private void initEVIDENCE() throws JastorException {
		this.EVIDENCE = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, EVIDENCEProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#EVIDENCE properties in complexAssembly model not a Resource", stmt.getObject());
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
		 

	private void initINTERACTION_DASH_TYPE() throws JastorException {
		this.INTERACTION_DASH_TYPE = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, INTERACTION_DASH_TYPEProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#INTERACTION-TYPE properties in complexAssembly model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.openControlledVocabulary INTERACTION_DASH_TYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
				this.INTERACTION_DASH_TYPE.add(INTERACTION_DASH_TYPE);
			}
		}
	}

	public java.util.Iterator getINTERACTION_DASH_TYPE() throws JastorException {
		if (INTERACTION_DASH_TYPE == null)
			initINTERACTION_DASH_TYPE();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(INTERACTION_DASH_TYPE,_resource,INTERACTION_DASH_TYPEProperty,true);
	}

	public void addINTERACTION_DASH_TYPE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary INTERACTION_DASH_TYPE) throws JastorException {
		if (this.INTERACTION_DASH_TYPE == null)
			initINTERACTION_DASH_TYPE();
		if (this.INTERACTION_DASH_TYPE.contains(INTERACTION_DASH_TYPE)) {
			this.INTERACTION_DASH_TYPE.remove(INTERACTION_DASH_TYPE);
			this.INTERACTION_DASH_TYPE.add(INTERACTION_DASH_TYPE);
			return;
		}
		this.INTERACTION_DASH_TYPE.add(INTERACTION_DASH_TYPE);
		_model.add(_model.createStatement(_resource,INTERACTION_DASH_TYPEProperty,INTERACTION_DASH_TYPE.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary addINTERACTION_DASH_TYPE() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.openControlledVocabulary INTERACTION_DASH_TYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createopenControlledVocabulary(_model.createResource(),_model);
		if (this.INTERACTION_DASH_TYPE == null)
			initINTERACTION_DASH_TYPE();
		this.INTERACTION_DASH_TYPE.add(INTERACTION_DASH_TYPE);
		_model.add(_model.createStatement(_resource,INTERACTION_DASH_TYPEProperty,INTERACTION_DASH_TYPE.resource()));
		return INTERACTION_DASH_TYPE;
	}
	
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary addINTERACTION_DASH_TYPE(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.openControlledVocabulary INTERACTION_DASH_TYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
		if (this.INTERACTION_DASH_TYPE == null)
			initINTERACTION_DASH_TYPE();
		if (this.INTERACTION_DASH_TYPE.contains(INTERACTION_DASH_TYPE))
			return INTERACTION_DASH_TYPE;
		this.INTERACTION_DASH_TYPE.add(INTERACTION_DASH_TYPE);
		_model.add(_model.createStatement(_resource,INTERACTION_DASH_TYPEProperty,INTERACTION_DASH_TYPE.resource()));
		return INTERACTION_DASH_TYPE;
	}
	
	public void removeINTERACTION_DASH_TYPE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary INTERACTION_DASH_TYPE) throws JastorException {
		if (this.INTERACTION_DASH_TYPE == null)
			initINTERACTION_DASH_TYPE();
		if (!this.INTERACTION_DASH_TYPE.contains(INTERACTION_DASH_TYPE))
			return;
		if (!_model.contains(_resource, INTERACTION_DASH_TYPEProperty, INTERACTION_DASH_TYPE.resource()))
			return;
		this.INTERACTION_DASH_TYPE.remove(INTERACTION_DASH_TYPE);
		_model.removeAll(_resource, INTERACTION_DASH_TYPEProperty, INTERACTION_DASH_TYPE.resource());
	}
		 

	private void initLEFT() throws JastorException {
		this.LEFT = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, LEFTProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#LEFT properties in complexAssembly model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant LEFT = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
				this.LEFT.add(LEFT);
			}
		}
	}

	public java.util.Iterator getLEFT() throws JastorException {
		if (LEFT == null)
			initLEFT();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(LEFT,_resource,LEFTProperty,true);
	}

	public void addLEFT(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant LEFT) throws JastorException {
		if (this.LEFT == null)
			initLEFT();
		if (this.LEFT.contains(LEFT)) {
			this.LEFT.remove(LEFT);
			this.LEFT.add(LEFT);
			return;
		}
		this.LEFT.add(LEFT);
		_model.add(_model.createStatement(_resource,LEFTProperty,LEFT.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addLEFT() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant LEFT = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createphysicalEntityParticipant(_model.createResource(),_model);
		if (this.LEFT == null)
			initLEFT();
		this.LEFT.add(LEFT);
		_model.add(_model.createStatement(_resource,LEFTProperty,LEFT.resource()));
		return LEFT;
	}
	
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addLEFT(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant LEFT = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
		if (this.LEFT == null)
			initLEFT();
		if (this.LEFT.contains(LEFT))
			return LEFT;
		this.LEFT.add(LEFT);
		_model.add(_model.createStatement(_resource,LEFTProperty,LEFT.resource()));
		return LEFT;
	}
	
	public void removeLEFT(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant LEFT) throws JastorException {
		if (this.LEFT == null)
			initLEFT();
		if (!this.LEFT.contains(LEFT))
			return;
		if (!_model.contains(_resource, LEFTProperty, LEFT.resource()))
			return;
		this.LEFT.remove(LEFT);
		_model.removeAll(_resource, LEFTProperty, LEFT.resource());
	}
		 
	public java.lang.String getSPONTANEOUS() throws JastorException {
		if (SPONTANEOUS != null)
			return SPONTANEOUS;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, SPONTANEOUSProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": SPONTANEOUS getProperty() in fr.curie.BiNoM.pathways.biopax.complexAssembly model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		SPONTANEOUS = (java.lang.String)obj;
		return SPONTANEOUS;
	}
	
	public void setSPONTANEOUS(java.lang.String SPONTANEOUS) throws JastorException {
		if (_model.contains(_resource,SPONTANEOUSProperty)) {
			_model.removeAll(_resource,SPONTANEOUSProperty,null);
		}
		this.SPONTANEOUS = SPONTANEOUS;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (SPONTANEOUS != null) {
			_model.add(_model.createStatement(_resource,SPONTANEOUSProperty, _model.createTypedLiteral(SPONTANEOUS)));
		}	
	}


	private void initRIGHT() throws JastorException {
		this.RIGHT = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, RIGHTProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#RIGHT properties in complexAssembly model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant RIGHT = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
				this.RIGHT.add(RIGHT);
			}
		}
	}

	public java.util.Iterator getRIGHT() throws JastorException {
		if (RIGHT == null)
			initRIGHT();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(RIGHT,_resource,RIGHTProperty,true);
	}

	public void addRIGHT(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant RIGHT) throws JastorException {
		if (this.RIGHT == null)
			initRIGHT();
		if (this.RIGHT.contains(RIGHT)) {
			this.RIGHT.remove(RIGHT);
			this.RIGHT.add(RIGHT);
			return;
		}
		this.RIGHT.add(RIGHT);
		_model.add(_model.createStatement(_resource,RIGHTProperty,RIGHT.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addRIGHT() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant RIGHT = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createphysicalEntityParticipant(_model.createResource(),_model);
		if (this.RIGHT == null)
			initRIGHT();
		this.RIGHT.add(RIGHT);
		_model.add(_model.createStatement(_resource,RIGHTProperty,RIGHT.resource()));
		return RIGHT;
	}
	
	public fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant addRIGHT(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant RIGHT = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
		if (this.RIGHT == null)
			initRIGHT();
		if (this.RIGHT.contains(RIGHT))
			return RIGHT;
		this.RIGHT.add(RIGHT);
		_model.add(_model.createStatement(_resource,RIGHTProperty,RIGHT.resource()));
		return RIGHT;
	}
	
	public void removeRIGHT(fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant RIGHT) throws JastorException {
		if (this.RIGHT == null)
			initRIGHT();
		if (!this.RIGHT.contains(RIGHT))
			return;
		if (!_model.contains(_resource, RIGHTProperty, RIGHT.resource()))
			return;
		this.RIGHT.remove(RIGHT);
		_model.removeAll(_resource, RIGHTProperty, RIGHT.resource());
	}
		  


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof complexAssemblyListener))
			throw new IllegalArgumentException("ThingListener must be instance of complexAssemblyListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((complexAssemblyListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof complexAssemblyListener))
			throw new IllegalArgumentException("ThingListener must be instance of complexAssemblyListener"); 
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
							complexAssemblyListener listener=(complexAssemblyListener)iter.next();
							listener.DATA_DASH_SOURCEAdded(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,_DATA_DASH_SOURCE);
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
						complexAssemblyListener listener=(complexAssemblyListener)iter.next();
						listener.AVAILABILITYAdded(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,(java.lang.String)obj);
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
						complexAssemblyListener listener=(complexAssemblyListener)iter.next();
						listener.COMMENTAdded(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,(java.lang.String)obj);
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
						complexAssemblyListener listener=(complexAssemblyListener)iter.next();
						listener.SHORT_DASH_NAMEChanged(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this);
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
						complexAssemblyListener listener=(complexAssemblyListener)iter.next();
						listener.SYNONYMSAdded(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,(java.lang.String)obj);
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
						complexAssemblyListener listener=(complexAssemblyListener)iter.next();
						listener.NAMEChanged(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this);
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
							complexAssemblyListener listener=(complexAssemblyListener)iter.next();
							listener.XREFAdded(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,_XREF);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(PARTICIPANTSProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant _PARTICIPANTS_asphysicalEntityParticipant = null;
					try {
						_PARTICIPANTS_asphysicalEntityParticipant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (PARTICIPANTS_asphysicalEntityParticipant == null) {
						try {
							initPARTICIPANTS_asphysicalEntityParticipant();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!PARTICIPANTS_asphysicalEntityParticipant.contains(_PARTICIPANTS_asphysicalEntityParticipant))
						PARTICIPANTS_asphysicalEntityParticipant.add(_PARTICIPANTS_asphysicalEntityParticipant);
					if (listeners != null) {
						java.util.ArrayList consumersForPARTICIPANTS_asphysicalEntityParticipant;
						synchronized (listeners) {
							consumersForPARTICIPANTS_asphysicalEntityParticipant = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPARTICIPANTS_asphysicalEntityParticipant.iterator();iter.hasNext();){
							complexAssemblyListener listener=(complexAssemblyListener)iter.next();
							listener.PARTICIPANTSAdded(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,_PARTICIPANTS_asphysicalEntityParticipant);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.entity.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.entity _PARTICIPANTS_asentity = null;
					try {
						_PARTICIPANTS_asentity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getentity(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (PARTICIPANTS_asentity == null) {
						try {
							initPARTICIPANTS_asentity();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!PARTICIPANTS_asentity.contains(_PARTICIPANTS_asentity))
						PARTICIPANTS_asentity.add(_PARTICIPANTS_asentity);
					if (listeners != null) {
						java.util.ArrayList consumersForPARTICIPANTS_asentity;
						synchronized (listeners) {
							consumersForPARTICIPANTS_asentity = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPARTICIPANTS_asentity.iterator();iter.hasNext();){
							complexAssemblyListener listener=(complexAssemblyListener)iter.next();
							listener.PARTICIPANTSAdded(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,_PARTICIPANTS_asentity);
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
							complexAssemblyListener listener=(complexAssemblyListener)iter.next();
							listener.EVIDENCEAdded(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,_EVIDENCE);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(INTERACTION_DASH_TYPEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.openControlledVocabulary _INTERACTION_DASH_TYPE = null;
					try {
						_INTERACTION_DASH_TYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (INTERACTION_DASH_TYPE == null) {
						try {
							initINTERACTION_DASH_TYPE();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!INTERACTION_DASH_TYPE.contains(_INTERACTION_DASH_TYPE))
						INTERACTION_DASH_TYPE.add(_INTERACTION_DASH_TYPE);
					if (listeners != null) {
						java.util.ArrayList consumersForINTERACTION_DASH_TYPE;
						synchronized (listeners) {
							consumersForINTERACTION_DASH_TYPE = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForINTERACTION_DASH_TYPE.iterator();iter.hasNext();){
							complexAssemblyListener listener=(complexAssemblyListener)iter.next();
							listener.INTERACTION_DASH_TYPEAdded(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,_INTERACTION_DASH_TYPE);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(LEFTProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant _LEFT = null;
					try {
						_LEFT = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (LEFT == null) {
						try {
							initLEFT();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!LEFT.contains(_LEFT))
						LEFT.add(_LEFT);
					if (listeners != null) {
						java.util.ArrayList consumersForLEFT;
						synchronized (listeners) {
							consumersForLEFT = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForLEFT.iterator();iter.hasNext();){
							complexAssemblyListener listener=(complexAssemblyListener)iter.next();
							listener.LEFTAdded(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,_LEFT);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(SPONTANEOUSProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				SPONTANEOUS = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						complexAssemblyListener listener=(complexAssemblyListener)iter.next();
						listener.SPONTANEOUSChanged(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(RIGHTProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant _RIGHT = null;
					try {
						_RIGHT = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (RIGHT == null) {
						try {
							initRIGHT();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!RIGHT.contains(_RIGHT))
						RIGHT.add(_RIGHT);
					if (listeners != null) {
						java.util.ArrayList consumersForRIGHT;
						synchronized (listeners) {
							consumersForRIGHT = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForRIGHT.iterator();iter.hasNext();){
							complexAssemblyListener listener=(complexAssemblyListener)iter.next();
							listener.RIGHTAdded(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,_RIGHT);
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
							complexAssemblyListener listener=(complexAssemblyListener)iter.next();
							listener.DATA_DASH_SOURCERemoved(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,_DATA_DASH_SOURCE);
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
						complexAssemblyListener listener=(complexAssemblyListener)iter.next();
						listener.AVAILABILITYRemoved(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,(java.lang.String)obj);
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
						complexAssemblyListener listener=(complexAssemblyListener)iter.next();
						listener.COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,(java.lang.String)obj);
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
						complexAssemblyListener listener=(complexAssemblyListener)iter.next();
						listener.SHORT_DASH_NAMEChanged(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this);
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
						complexAssemblyListener listener=(complexAssemblyListener)iter.next();
						listener.SYNONYMSRemoved(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,(java.lang.String)obj);
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
						complexAssemblyListener listener=(complexAssemblyListener)iter.next();
						listener.NAMEChanged(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this);
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
							complexAssemblyListener listener=(complexAssemblyListener)iter.next();
							listener.XREFRemoved(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,_XREF);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(PARTICIPANTSProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant _PARTICIPANTS_asphysicalEntityParticipant = null;
					if (PARTICIPANTS_asphysicalEntityParticipant != null) {
						boolean found = false;
						for (int i=0;i<PARTICIPANTS_asphysicalEntityParticipant.size();i++) {
							fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant __item = (fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant) PARTICIPANTS_asphysicalEntityParticipant.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_PARTICIPANTS_asphysicalEntityParticipant = __item;
								break;
							}
						}
						if (found)
							PARTICIPANTS_asphysicalEntityParticipant.remove(_PARTICIPANTS_asphysicalEntityParticipant);
						else {
							try {
								_PARTICIPANTS_asphysicalEntityParticipant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_PARTICIPANTS_asphysicalEntityParticipant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForPARTICIPANTS_asphysicalEntityParticipant;
						synchronized (listeners) {
							consumersForPARTICIPANTS_asphysicalEntityParticipant = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPARTICIPANTS_asphysicalEntityParticipant.iterator();iter.hasNext();){
							complexAssemblyListener listener=(complexAssemblyListener)iter.next();
							listener.PARTICIPANTSRemoved(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,_PARTICIPANTS_asphysicalEntityParticipant);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.entity.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.entity _PARTICIPANTS_asentity = null;
					if (PARTICIPANTS_asentity != null) {
						boolean found = false;
						for (int i=0;i<PARTICIPANTS_asentity.size();i++) {
							fr.curie.BiNoM.pathways.biopax.entity __item = (fr.curie.BiNoM.pathways.biopax.entity) PARTICIPANTS_asentity.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_PARTICIPANTS_asentity = __item;
								break;
							}
						}
						if (found)
							PARTICIPANTS_asentity.remove(_PARTICIPANTS_asentity);
						else {
							try {
								_PARTICIPANTS_asentity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getentity(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_PARTICIPANTS_asentity = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getentity(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForPARTICIPANTS_asentity;
						synchronized (listeners) {
							consumersForPARTICIPANTS_asentity = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPARTICIPANTS_asentity.iterator();iter.hasNext();){
							complexAssemblyListener listener=(complexAssemblyListener)iter.next();
							listener.PARTICIPANTSRemoved(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,_PARTICIPANTS_asentity);
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
							complexAssemblyListener listener=(complexAssemblyListener)iter.next();
							listener.EVIDENCERemoved(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,_EVIDENCE);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(INTERACTION_DASH_TYPEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.openControlledVocabulary _INTERACTION_DASH_TYPE = null;
					if (INTERACTION_DASH_TYPE != null) {
						boolean found = false;
						for (int i=0;i<INTERACTION_DASH_TYPE.size();i++) {
							fr.curie.BiNoM.pathways.biopax.openControlledVocabulary __item = (fr.curie.BiNoM.pathways.biopax.openControlledVocabulary) INTERACTION_DASH_TYPE.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_INTERACTION_DASH_TYPE = __item;
								break;
							}
						}
						if (found)
							INTERACTION_DASH_TYPE.remove(_INTERACTION_DASH_TYPE);
						else {
							try {
								_INTERACTION_DASH_TYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_INTERACTION_DASH_TYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForINTERACTION_DASH_TYPE;
						synchronized (listeners) {
							consumersForINTERACTION_DASH_TYPE = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForINTERACTION_DASH_TYPE.iterator();iter.hasNext();){
							complexAssemblyListener listener=(complexAssemblyListener)iter.next();
							listener.INTERACTION_DASH_TYPERemoved(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,_INTERACTION_DASH_TYPE);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(LEFTProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant _LEFT = null;
					if (LEFT != null) {
						boolean found = false;
						for (int i=0;i<LEFT.size();i++) {
							fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant __item = (fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant) LEFT.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_LEFT = __item;
								break;
							}
						}
						if (found)
							LEFT.remove(_LEFT);
						else {
							try {
								_LEFT = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_LEFT = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForLEFT;
						synchronized (listeners) {
							consumersForLEFT = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForLEFT.iterator();iter.hasNext();){
							complexAssemblyListener listener=(complexAssemblyListener)iter.next();
							listener.LEFTRemoved(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,_LEFT);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(SPONTANEOUSProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (SPONTANEOUS != null && SPONTANEOUS.equals(obj))
					SPONTANEOUS = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						complexAssemblyListener listener=(complexAssemblyListener)iter.next();
						listener.SPONTANEOUSChanged(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(RIGHTProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant _RIGHT = null;
					if (RIGHT != null) {
						boolean found = false;
						for (int i=0;i<RIGHT.size();i++) {
							fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant __item = (fr.curie.BiNoM.pathways.biopax.physicalEntityParticipant) RIGHT.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_RIGHT = __item;
								break;
							}
						}
						if (found)
							RIGHT.remove(_RIGHT);
						else {
							try {
								_RIGHT = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_RIGHT = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getphysicalEntityParticipant(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForRIGHT;
						synchronized (listeners) {
							consumersForRIGHT = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForRIGHT.iterator();iter.hasNext();){
							complexAssemblyListener listener=(complexAssemblyListener)iter.next();
							listener.RIGHTRemoved(fr.curie.BiNoM.pathways.biopax.complexAssemblyImpl.this,_RIGHT);
						}
					}
				}
				return;
			}
		}

	//}
	


}