

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.chemicalStructure}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level2.owl#chemicalStructure)</p>
 * <br>
 */
public class chemicalStructureImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.chemicalStructure {
	

	private static com.hp.hpl.jena.rdf.model.Property COMMENTProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#COMMENT");
	private java.util.ArrayList COMMENT;
	private static com.hp.hpl.jena.rdf.model.Property STRUCTURE_DASH_DATAProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#STRUCTURE-DATA");
	private java.lang.String STRUCTURE_DASH_DATA;
	private static com.hp.hpl.jena.rdf.model.Property STRUCTURE_DASH_FORMATProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level2.owl#STRUCTURE-FORMAT");
	private java.lang.String STRUCTURE_DASH_FORMAT;

	chemicalStructureImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static chemicalStructureImpl getchemicalStructure(Resource resource, Model model) throws JastorException {
		return new chemicalStructureImpl(resource, model);
	}
	    
	static chemicalStructureImpl createchemicalStructure(Resource resource, Model model) throws JastorException {
		chemicalStructureImpl impl = new chemicalStructureImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, chemicalStructure.TYPE)))
			impl._model.add(impl._resource, RDF.type, chemicalStructure.TYPE);
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
		it = _model.listStatements(_resource,STRUCTURE_DASH_DATAProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,STRUCTURE_DASH_FORMATProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.chemicalStructure.TYPE);
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
		STRUCTURE_DASH_DATA = null;
		STRUCTURE_DASH_FORMAT = null;
	}


	private void initCOMMENT() throws JastorException {
		COMMENT = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, COMMENTProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level2.owl#COMMENT properties in chemicalStructure model not a Literal", stmt.getObject());
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

	public java.lang.String getSTRUCTURE_DASH_DATA() throws JastorException {
		if (STRUCTURE_DASH_DATA != null)
			return STRUCTURE_DASH_DATA;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, STRUCTURE_DASH_DATAProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": STRUCTURE_DASH_DATA getProperty() in fr.curie.BiNoM.pathways.biopax.chemicalStructure model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		STRUCTURE_DASH_DATA = (java.lang.String)obj;
		return STRUCTURE_DASH_DATA;
	}
	
	public void setSTRUCTURE_DASH_DATA(java.lang.String STRUCTURE_DASH_DATA) throws JastorException {
		if (_model.contains(_resource,STRUCTURE_DASH_DATAProperty)) {
			_model.removeAll(_resource,STRUCTURE_DASH_DATAProperty,null);
		}
		this.STRUCTURE_DASH_DATA = STRUCTURE_DASH_DATA;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (STRUCTURE_DASH_DATA != null) {
			_model.add(_model.createStatement(_resource,STRUCTURE_DASH_DATAProperty, _model.createTypedLiteral(STRUCTURE_DASH_DATA)));
		}	
	}

	public java.lang.String getSTRUCTURE_DASH_FORMAT() throws JastorException {
		if (STRUCTURE_DASH_FORMAT != null)
			return STRUCTURE_DASH_FORMAT;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, STRUCTURE_DASH_FORMATProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": STRUCTURE_DASH_FORMAT getProperty() in fr.curie.BiNoM.pathways.biopax.chemicalStructure model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		STRUCTURE_DASH_FORMAT = (java.lang.String)obj;
		return STRUCTURE_DASH_FORMAT;
	}
	
	public void setSTRUCTURE_DASH_FORMAT(java.lang.String STRUCTURE_DASH_FORMAT) throws JastorException {
		if (_model.contains(_resource,STRUCTURE_DASH_FORMATProperty)) {
			_model.removeAll(_resource,STRUCTURE_DASH_FORMATProperty,null);
		}
		this.STRUCTURE_DASH_FORMAT = STRUCTURE_DASH_FORMAT;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (STRUCTURE_DASH_FORMAT != null) {
			_model.add(_model.createStatement(_resource,STRUCTURE_DASH_FORMATProperty, _model.createTypedLiteral(STRUCTURE_DASH_FORMAT)));
		}	
	}
 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof chemicalStructureListener))
			throw new IllegalArgumentException("ThingListener must be instance of chemicalStructureListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((chemicalStructureListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof chemicalStructureListener))
			throw new IllegalArgumentException("ThingListener must be instance of chemicalStructureListener"); 
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
						chemicalStructureListener listener=(chemicalStructureListener)iter.next();
						listener.COMMENTAdded(fr.curie.BiNoM.pathways.biopax.chemicalStructureImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(STRUCTURE_DASH_DATAProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				STRUCTURE_DASH_DATA = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						chemicalStructureListener listener=(chemicalStructureListener)iter.next();
						listener.STRUCTURE_DASH_DATAChanged(fr.curie.BiNoM.pathways.biopax.chemicalStructureImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(STRUCTURE_DASH_FORMATProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				STRUCTURE_DASH_FORMAT = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						chemicalStructureListener listener=(chemicalStructureListener)iter.next();
						listener.STRUCTURE_DASH_FORMATChanged(fr.curie.BiNoM.pathways.biopax.chemicalStructureImpl.this);
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
						chemicalStructureListener listener=(chemicalStructureListener)iter.next();
						listener.COMMENTRemoved(fr.curie.BiNoM.pathways.biopax.chemicalStructureImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(STRUCTURE_DASH_DATAProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (STRUCTURE_DASH_DATA != null && STRUCTURE_DASH_DATA.equals(obj))
					STRUCTURE_DASH_DATA = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						chemicalStructureListener listener=(chemicalStructureListener)iter.next();
						listener.STRUCTURE_DASH_DATAChanged(fr.curie.BiNoM.pathways.biopax.chemicalStructureImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(STRUCTURE_DASH_FORMATProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (STRUCTURE_DASH_FORMAT != null && STRUCTURE_DASH_FORMAT.equals(obj))
					STRUCTURE_DASH_FORMAT = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						chemicalStructureListener listener=(chemicalStructureListener)iter.next();
						listener.STRUCTURE_DASH_FORMATChanged(fr.curie.BiNoM.pathways.biopax.chemicalStructureImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}