

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.bioSource}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#bioSource)</p>
 * <br>
 */
public class bioSourceImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.bioSource {
	

	private static com.hp.hpl.jena.rdf.model.Property COMMENTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#COMMENT");
	private java.util.ArrayList COMMENT;
	private static com.hp.hpl.jena.rdf.model.Property CELLTYPEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#CELLTYPE");
	private fr.curie.BiNoM.pathways.biopax.openControlledVocabulary CELLTYPE;
	private static com.hp.hpl.jena.rdf.model.Property TISSUEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#TISSUE");
	private fr.curie.BiNoM.pathways.biopax.openControlledVocabulary TISSUE;
	private static com.hp.hpl.jena.rdf.model.Property TAXON_DASH_XREFProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#TAXON-XREF");
	private fr.curie.BiNoM.pathways.biopax.unificationXref TAXON_DASH_XREF;
	private static com.hp.hpl.jena.rdf.model.Property NAMEProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#NAME");
	private java.lang.String NAME;

	bioSourceImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static bioSourceImpl getbioSource(Resource resource, Model model) throws JastorException {
		return new bioSourceImpl(resource, model);
	}
	    
	static bioSourceImpl createbioSource(Resource resource, Model model) throws JastorException {
		bioSourceImpl impl = new bioSourceImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, bioSource.TYPE)))
			impl._model.add(impl._resource, RDF.type, bioSource.TYPE);
		//impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.utilityClass.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.utilityClass.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClass.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClass.TYPE));     
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
		it = _model.listStatements(_resource,CELLTYPEProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,TISSUEProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,TAXON_DASH_XREFProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,NAMEProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.bioSource.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.utilityClass.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.externalReferenceUtilityClass.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
	
	public void clearCache() {
		COMMENT = null;
		CELLTYPE = null;
		TISSUE = null;
		TAXON_DASH_XREF = null;
		NAME = null;
	}


	private void initCOMMENT() throws JastorException {
		COMMENT = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, COMMENTProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#COMMENT properties in bioSource model not a Literal", stmt.getObject());
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


	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary getCELLTYPE() throws JastorException {
		if (CELLTYPE != null)
			return CELLTYPE;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, CELLTYPEProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": CELLTYPE getProperty() in fr.curie.BiNoM.pathways.biopax.bioSource model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		CELLTYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
		return CELLTYPE;
	}

	public void setCELLTYPE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary CELLTYPE) throws JastorException {
		if (_model.contains(_resource,CELLTYPEProperty)) {
			_model.removeAll(_resource,CELLTYPEProperty,null);
		}
		this.CELLTYPE = CELLTYPE;
		if (CELLTYPE != null) {
			_model.add(_model.createStatement(_resource,CELLTYPEProperty, CELLTYPE.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary setCELLTYPE() throws JastorException {
		if (_model.contains(_resource,CELLTYPEProperty)) {
			_model.removeAll(_resource,CELLTYPEProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.openControlledVocabulary CELLTYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createopenControlledVocabulary(_model.createResource(),_model);
		this.CELLTYPE = CELLTYPE;
		_model.add(_model.createStatement(_resource,CELLTYPEProperty, CELLTYPE.resource()));
		return CELLTYPE;
	}
	
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary setCELLTYPE(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,CELLTYPEProperty)) {
			_model.removeAll(_resource,CELLTYPEProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.openControlledVocabulary CELLTYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
		this.CELLTYPE = CELLTYPE;
		_model.add(_model.createStatement(_resource,CELLTYPEProperty, CELLTYPE.resource()));
		return CELLTYPE;
	}
	

	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary getTISSUE() throws JastorException {
		if (TISSUE != null)
			return TISSUE;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, TISSUEProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": TISSUE getProperty() in fr.curie.BiNoM.pathways.biopax.bioSource model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		TISSUE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
		return TISSUE;
	}

	public void setTISSUE(fr.curie.BiNoM.pathways.biopax.openControlledVocabulary TISSUE) throws JastorException {
		if (_model.contains(_resource,TISSUEProperty)) {
			_model.removeAll(_resource,TISSUEProperty,null);
		}
		this.TISSUE = TISSUE;
		if (TISSUE != null) {
			_model.add(_model.createStatement(_resource,TISSUEProperty, TISSUE.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary setTISSUE() throws JastorException {
		if (_model.contains(_resource,TISSUEProperty)) {
			_model.removeAll(_resource,TISSUEProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.openControlledVocabulary TISSUE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createopenControlledVocabulary(_model.createResource(),_model);
		this.TISSUE = TISSUE;
		_model.add(_model.createStatement(_resource,TISSUEProperty, TISSUE.resource()));
		return TISSUE;
	}
	
	public fr.curie.BiNoM.pathways.biopax.openControlledVocabulary setTISSUE(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,TISSUEProperty)) {
			_model.removeAll(_resource,TISSUEProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.openControlledVocabulary TISSUE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
		this.TISSUE = TISSUE;
		_model.add(_model.createStatement(_resource,TISSUEProperty, TISSUE.resource()));
		return TISSUE;
	}
	

	public fr.curie.BiNoM.pathways.biopax.unificationXref getTAXON_DASH_XREF() throws JastorException {
		if (TAXON_DASH_XREF != null)
			return TAXON_DASH_XREF;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, TAXON_DASH_XREFProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": TAXON_DASH_XREF getProperty() in fr.curie.BiNoM.pathways.biopax.bioSource model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		TAXON_DASH_XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getunificationXref(resource,_model);
		return TAXON_DASH_XREF;
	}

	public void setTAXON_DASH_XREF(fr.curie.BiNoM.pathways.biopax.unificationXref TAXON_DASH_XREF) throws JastorException {
		if (_model.contains(_resource,TAXON_DASH_XREFProperty)) {
			_model.removeAll(_resource,TAXON_DASH_XREFProperty,null);
		}
		this.TAXON_DASH_XREF = TAXON_DASH_XREF;
		if (TAXON_DASH_XREF != null) {
			_model.add(_model.createStatement(_resource,TAXON_DASH_XREFProperty, TAXON_DASH_XREF.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.unificationXref setTAXON_DASH_XREF() throws JastorException {
		if (_model.contains(_resource,TAXON_DASH_XREFProperty)) {
			_model.removeAll(_resource,TAXON_DASH_XREFProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.unificationXref TAXON_DASH_XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.createunificationXref(_model.createResource(),_model);
		this.TAXON_DASH_XREF = TAXON_DASH_XREF;
		_model.add(_model.createStatement(_resource,TAXON_DASH_XREFProperty, TAXON_DASH_XREF.resource()));
		return TAXON_DASH_XREF;
	}
	
	public fr.curie.BiNoM.pathways.biopax.unificationXref setTAXON_DASH_XREF(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,TAXON_DASH_XREFProperty)) {
			_model.removeAll(_resource,TAXON_DASH_XREFProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.unificationXref TAXON_DASH_XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getunificationXref(resource,_model);
		this.TAXON_DASH_XREF = TAXON_DASH_XREF;
		_model.add(_model.createStatement(_resource,TAXON_DASH_XREFProperty, TAXON_DASH_XREF.resource()));
		return TAXON_DASH_XREF;
	}
	
	public java.lang.String getNAME() throws JastorException {
		if (NAME != null)
			return NAME;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, NAMEProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": NAME getProperty() in fr.curie.BiNoM.pathways.biopax.bioSource model not Literal", stmt.getObject());
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
 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof bioSourceListener))
			throw new IllegalArgumentException("ThingListener must be instance of bioSourceListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((bioSourceListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof bioSourceListener))
			throw new IllegalArgumentException("ThingListener must be instance of bioSourceListener"); 
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
						bioSourceListener listener=(bioSourceListener)iter.next();
						listener.COMMENTAdded(fr.curie.BiNoM.pathways.biopax.bioSourceImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(CELLTYPEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				CELLTYPE = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						CELLTYPE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
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
						bioSourceListener listener=(bioSourceListener)iter.next();
						listener.CELLTYPEChanged(fr.curie.BiNoM.pathways.biopax.bioSourceImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(TISSUEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				TISSUE = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						TISSUE = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getopenControlledVocabulary(resource,_model);
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
						bioSourceListener listener=(bioSourceListener)iter.next();
						listener.TISSUEChanged(fr.curie.BiNoM.pathways.biopax.bioSourceImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(TAXON_DASH_XREFProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				TAXON_DASH_XREF = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						TAXON_DASH_XREF = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory.getunificationXref(resource,_model);
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
						bioSourceListener listener=(bioSourceListener)iter.next();
						listener.TAXON_DASH_XREFChanged(fr.curie.BiNoM.pathways.biopax.bioSourceImpl.this);
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
						bioSourceListener listener=(bioSourceListener)iter.next();
						listener.NAMEChanged(fr.curie.BiNoM.pathways.biopax.bioSourceImpl.this);
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
						bioSourceListener listener=(bioSourceListener)iter.next();
						listener.COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.bioSourceImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(CELLTYPEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (CELLTYPE != null && CELLTYPE.resource().equals(resource))
						CELLTYPE = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						bioSourceListener listener=(bioSourceListener)iter.next();
						listener.CELLTYPEChanged(fr.curie.BiNoM.pathways.biopax.bioSourceImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(TISSUEProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (TISSUE != null && TISSUE.resource().equals(resource))
						TISSUE = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						bioSourceListener listener=(bioSourceListener)iter.next();
						listener.TISSUEChanged(fr.curie.BiNoM.pathways.biopax.bioSourceImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(TAXON_DASH_XREFProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (TAXON_DASH_XREF != null && TAXON_DASH_XREF.resource().equals(resource))
						TAXON_DASH_XREF = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						bioSourceListener listener=(bioSourceListener)iter.next();
						listener.TAXON_DASH_XREFChanged(fr.curie.BiNoM.pathways.biopax.bioSourceImpl.this);
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
						bioSourceListener listener=(bioSourceListener)iter.next();
						listener.NAMEChanged(fr.curie.BiNoM.pathways.biopax.bioSourceImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}