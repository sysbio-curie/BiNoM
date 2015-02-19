

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.ProteinReference}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#ProteinReference)</p>
 * <br>
 */
public class ProteinReferenceImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.ProteinReference {
	

	private static com.hp.hpl.jena.rdf.model.Property commentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#comment");
	private java.util.ArrayList comment;
	private static com.hp.hpl.jena.rdf.model.Property memberEntityReferenceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#memberEntityReference");
	private java.util.ArrayList memberEntityReference;
	private java.util.ArrayList memberEntityReference_asProteinReference;
	private static com.hp.hpl.jena.rdf.model.Property entityReferenceTypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#entityReferenceType");
	private fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary entityReferenceType;
	private static com.hp.hpl.jena.rdf.model.Property entityFeatureProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#entityFeature");
	private java.util.ArrayList entityFeature;
	private static com.hp.hpl.jena.rdf.model.Property nameProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#name");
	private java.util.ArrayList name;
	private static com.hp.hpl.jena.rdf.model.Property evidenceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#evidence");
	private java.util.ArrayList evidence;
	private static com.hp.hpl.jena.rdf.model.Property xrefProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#xref");
	private java.util.ArrayList xref;
	private static com.hp.hpl.jena.rdf.model.Property sequenceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#sequence");
	private java.lang.String sequence;
	private static com.hp.hpl.jena.rdf.model.Property organismProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#organism");
	private fr.curie.BiNoM.pathways.biopax.BioSource organism;

	ProteinReferenceImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static ProteinReferenceImpl getProteinReference(Resource resource, Model model) throws JastorException {
		return new ProteinReferenceImpl(resource, model);
	}
	    
	static ProteinReferenceImpl createProteinReference(Resource resource, Model model) throws JastorException {
		ProteinReferenceImpl impl = new ProteinReferenceImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, ProteinReference.TYPE)))
			impl._model.add(impl._resource, RDF.type, ProteinReference.TYPE);
		impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.EntityReference.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.EntityReference.TYPE));     
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
		it = _model.listStatements(_resource,memberEntityReferenceProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,entityReferenceTypeProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,entityFeatureProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,nameProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,evidenceProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,xrefProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,sequenceProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,organismProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.ProteinReference.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.EntityReference.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
	
	public void clearCache() {
		comment = null;
		memberEntityReference = null;
		memberEntityReference_asProteinReference = null;
		entityReferenceType = null;
		entityFeature = null;
		name = null;
		evidence = null;
		xref = null;
		sequence = null;
		organism = null;
	}


	private void initComment() throws JastorException {
		comment = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, commentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#comment properties in ProteinReference model not a Literal", stmt.getObject());
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


	private void initMemberEntityReference() throws JastorException {
		this.memberEntityReference = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, memberEntityReferenceProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#memberEntityReference properties in ProteinReference model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.EntityReference memberEntityReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityReference(resource,_model);
				this.memberEntityReference.add(memberEntityReference);
			}
		}
	}

	public java.util.Iterator getMemberEntityReference() throws JastorException {
		if (memberEntityReference == null)
			initMemberEntityReference();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(memberEntityReference,_resource,memberEntityReferenceProperty,true);
	}

	public void addMemberEntityReference(fr.curie.BiNoM.pathways.biopax.EntityReference memberEntityReference) throws JastorException {
		if (this.memberEntityReference == null)
			initMemberEntityReference();
		if (this.memberEntityReference.contains(memberEntityReference)) {
			this.memberEntityReference.remove(memberEntityReference);
			this.memberEntityReference.add(memberEntityReference);
			return;
		}
		this.memberEntityReference.add(memberEntityReference);
		_model.add(_model.createStatement(_resource,memberEntityReferenceProperty,memberEntityReference.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.EntityReference addMemberEntityReference() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.EntityReference memberEntityReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createEntityReference(_model.createResource(),_model);
		if (this.memberEntityReference == null)
			initMemberEntityReference();
		this.memberEntityReference.add(memberEntityReference);
		_model.add(_model.createStatement(_resource,memberEntityReferenceProperty,memberEntityReference.resource()));
		return memberEntityReference;
	}
	
	public fr.curie.BiNoM.pathways.biopax.EntityReference addMemberEntityReference(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.EntityReference memberEntityReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityReference(resource,_model);
		if (this.memberEntityReference == null)
			initMemberEntityReference();
		if (this.memberEntityReference.contains(memberEntityReference))
			return memberEntityReference;
		this.memberEntityReference.add(memberEntityReference);
		_model.add(_model.createStatement(_resource,memberEntityReferenceProperty,memberEntityReference.resource()));
		return memberEntityReference;
	}
	
	public void removeMemberEntityReference(fr.curie.BiNoM.pathways.biopax.EntityReference memberEntityReference) throws JastorException {
		if (this.memberEntityReference == null)
			initMemberEntityReference();
		if (!this.memberEntityReference.contains(memberEntityReference))
			return;
		if (!_model.contains(_resource, memberEntityReferenceProperty, memberEntityReference.resource()))
			return;
		this.memberEntityReference.remove(memberEntityReference);
		_model.removeAll(_resource, memberEntityReferenceProperty, memberEntityReference.resource());
	}
		
	private void initMemberEntityReference_asProteinReference() throws JastorException {
		this.memberEntityReference_asProteinReference = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, memberEntityReferenceProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#memberEntityReference properties in ProteinReference model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.ProteinReference.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.ProteinReference memberEntityReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getProteinReference(resource,_model);
				this.memberEntityReference_asProteinReference.add(memberEntityReference);
			}
		}
	}

	public java.util.Iterator getMemberEntityReference_asProteinReference() throws JastorException {
		if (memberEntityReference_asProteinReference == null)
			initMemberEntityReference_asProteinReference();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(memberEntityReference_asProteinReference,_resource,memberEntityReferenceProperty,true);
	}

	public void addMemberEntityReference(fr.curie.BiNoM.pathways.biopax.ProteinReference memberEntityReference) throws JastorException {
		if (this.memberEntityReference_asProteinReference == null)
			initMemberEntityReference_asProteinReference();
		if (this.memberEntityReference_asProteinReference.contains(memberEntityReference)) {
			this.memberEntityReference_asProteinReference.remove(memberEntityReference);
			this.memberEntityReference_asProteinReference.add(memberEntityReference);
			return;
		}
		this.memberEntityReference_asProteinReference.add(memberEntityReference);
		_model.add(_model.createStatement(_resource,memberEntityReferenceProperty,memberEntityReference.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.ProteinReference addMemberEntityReference_asProteinReference() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.ProteinReference memberEntityReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createProteinReference(_model.createResource(),_model);
		if (this.memberEntityReference_asProteinReference == null)
			initMemberEntityReference_asProteinReference();
		this.memberEntityReference_asProteinReference.add(memberEntityReference);
		_model.add(_model.createStatement(_resource,memberEntityReferenceProperty,memberEntityReference.resource()));
		return memberEntityReference;
	}
	
	public fr.curie.BiNoM.pathways.biopax.ProteinReference addMemberEntityReference_asProteinReference(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.ProteinReference memberEntityReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getProteinReference(resource,_model);
		if (this.memberEntityReference_asProteinReference == null)
			initMemberEntityReference_asProteinReference();
		if (this.memberEntityReference_asProteinReference.contains(memberEntityReference))
			return memberEntityReference;
		this.memberEntityReference_asProteinReference.add(memberEntityReference);
		_model.add(_model.createStatement(_resource,memberEntityReferenceProperty,memberEntityReference.resource()));
		return memberEntityReference;
	}
	
	public void removeMemberEntityReference(fr.curie.BiNoM.pathways.biopax.ProteinReference memberEntityReference) throws JastorException {
		if (this.memberEntityReference_asProteinReference == null)
			initMemberEntityReference_asProteinReference();
		if (!this.memberEntityReference_asProteinReference.contains(memberEntityReference))
			return;
		if (!_model.contains(_resource, memberEntityReferenceProperty, memberEntityReference.resource()))
			return;
		this.memberEntityReference_asProteinReference.remove(memberEntityReference);
		_model.removeAll(_resource, memberEntityReferenceProperty, memberEntityReference.resource());
	}
		 

	public fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary getEntityReferenceType() throws JastorException {
		if (entityReferenceType != null)
			return entityReferenceType;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, entityReferenceTypeProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": entityReferenceType getProperty() in fr.curie.BiNoM.pathways.biopax.ProteinReference model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		entityReferenceType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityReferenceTypeVocabulary(resource,_model);
		return entityReferenceType;
	}

	public void setEntityReferenceType(fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary entityReferenceType) throws JastorException {
		if (_model.contains(_resource,entityReferenceTypeProperty)) {
			_model.removeAll(_resource,entityReferenceTypeProperty,null);
		}
		this.entityReferenceType = entityReferenceType;
		if (entityReferenceType != null) {
			_model.add(_model.createStatement(_resource,entityReferenceTypeProperty, entityReferenceType.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary setEntityReferenceType() throws JastorException {
		if (_model.contains(_resource,entityReferenceTypeProperty)) {
			_model.removeAll(_resource,entityReferenceTypeProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary entityReferenceType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createEntityReferenceTypeVocabulary(_model.createResource(),_model);
		this.entityReferenceType = entityReferenceType;
		_model.add(_model.createStatement(_resource,entityReferenceTypeProperty, entityReferenceType.resource()));
		return entityReferenceType;
	}
	
	public fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary setEntityReferenceType(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,entityReferenceTypeProperty)) {
			_model.removeAll(_resource,entityReferenceTypeProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.EntityReferenceTypeVocabulary entityReferenceType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityReferenceTypeVocabulary(resource,_model);
		this.entityReferenceType = entityReferenceType;
		_model.add(_model.createStatement(_resource,entityReferenceTypeProperty, entityReferenceType.resource()));
		return entityReferenceType;
	}
	

	private void initEntityFeature() throws JastorException {
		this.entityFeature = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, entityFeatureProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#entityFeature properties in ProteinReference model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.EntityFeature entityFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
				this.entityFeature.add(entityFeature);
			}
		}
	}

	public java.util.Iterator getEntityFeature() throws JastorException {
		if (entityFeature == null)
			initEntityFeature();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(entityFeature,_resource,entityFeatureProperty,true);
	}

	public void addEntityFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature entityFeature) throws JastorException {
		if (this.entityFeature == null)
			initEntityFeature();
		if (this.entityFeature.contains(entityFeature)) {
			this.entityFeature.remove(entityFeature);
			this.entityFeature.add(entityFeature);
			return;
		}
		this.entityFeature.add(entityFeature);
		_model.add(_model.createStatement(_resource,entityFeatureProperty,entityFeature.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addEntityFeature() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.EntityFeature entityFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createEntityFeature(_model.createResource(),_model);
		if (this.entityFeature == null)
			initEntityFeature();
		this.entityFeature.add(entityFeature);
		_model.add(_model.createStatement(_resource,entityFeatureProperty,entityFeature.resource()));
		return entityFeature;
	}
	
	public fr.curie.BiNoM.pathways.biopax.EntityFeature addEntityFeature(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.EntityFeature entityFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
		if (this.entityFeature == null)
			initEntityFeature();
		if (this.entityFeature.contains(entityFeature))
			return entityFeature;
		this.entityFeature.add(entityFeature);
		_model.add(_model.createStatement(_resource,entityFeatureProperty,entityFeature.resource()));
		return entityFeature;
	}
	
	public void removeEntityFeature(fr.curie.BiNoM.pathways.biopax.EntityFeature entityFeature) throws JastorException {
		if (this.entityFeature == null)
			initEntityFeature();
		if (!this.entityFeature.contains(entityFeature))
			return;
		if (!_model.contains(_resource, entityFeatureProperty, entityFeature.resource()))
			return;
		this.entityFeature.remove(entityFeature);
		_model.removeAll(_resource, entityFeatureProperty, entityFeature.resource());
	}
		 

	private void initName() throws JastorException {
		name = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, nameProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#name properties in ProteinReference model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			name.add(Util.fixLiteral(literal.getValue(),"java.lang.String"));
		}
	}

	public java.util.Iterator getName() throws JastorException {
		if (name == null)
			initName();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(name,_resource,nameProperty,false);
	}

	public void addName(java.lang.String name) throws JastorException {
		if (this.name == null)
			initName();
		if (this.name.contains(name))
			return;
		if (_model.contains(_resource, nameProperty, _model.createTypedLiteral(name)))
			return;
		this.name.add(name);
		_model.add(_resource, nameProperty, _model.createTypedLiteral(name));
	}
	
	public void removeName(java.lang.String name) throws JastorException {
		if (this.name == null)
			initName();
		if (!this.name.contains(name))
			return;
		if (!_model.contains(_resource, nameProperty, _model.createTypedLiteral(name)))
			return;
		this.name.remove(name);
		_model.removeAll(_resource, nameProperty, _model.createTypedLiteral(name));
	}


	private void initEvidence() throws JastorException {
		this.evidence = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, evidenceProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#evidence properties in ProteinReference model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.Evidence evidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEvidence(resource,_model);
				this.evidence.add(evidence);
			}
		}
	}

	public java.util.Iterator getEvidence() throws JastorException {
		if (evidence == null)
			initEvidence();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(evidence,_resource,evidenceProperty,true);
	}

	public void addEvidence(fr.curie.BiNoM.pathways.biopax.Evidence evidence) throws JastorException {
		if (this.evidence == null)
			initEvidence();
		if (this.evidence.contains(evidence)) {
			this.evidence.remove(evidence);
			this.evidence.add(evidence);
			return;
		}
		this.evidence.add(evidence);
		_model.add(_model.createStatement(_resource,evidenceProperty,evidence.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Evidence addEvidence() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Evidence evidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createEvidence(_model.createResource(),_model);
		if (this.evidence == null)
			initEvidence();
		this.evidence.add(evidence);
		_model.add(_model.createStatement(_resource,evidenceProperty,evidence.resource()));
		return evidence;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Evidence addEvidence(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Evidence evidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEvidence(resource,_model);
		if (this.evidence == null)
			initEvidence();
		if (this.evidence.contains(evidence))
			return evidence;
		this.evidence.add(evidence);
		_model.add(_model.createStatement(_resource,evidenceProperty,evidence.resource()));
		return evidence;
	}
	
	public void removeEvidence(fr.curie.BiNoM.pathways.biopax.Evidence evidence) throws JastorException {
		if (this.evidence == null)
			initEvidence();
		if (!this.evidence.contains(evidence))
			return;
		if (!_model.contains(_resource, evidenceProperty, evidence.resource()))
			return;
		this.evidence.remove(evidence);
		_model.removeAll(_resource, evidenceProperty, evidence.resource());
	}
		 

	private void initXref() throws JastorException {
		this.xref = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, xrefProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#xref properties in ProteinReference model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.Xref xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getXref(resource,_model);
				this.xref.add(xref);
			}
		}
	}

	public java.util.Iterator getXref() throws JastorException {
		if (xref == null)
			initXref();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(xref,_resource,xrefProperty,true);
	}

	public void addXref(fr.curie.BiNoM.pathways.biopax.Xref xref) throws JastorException {
		if (this.xref == null)
			initXref();
		if (this.xref.contains(xref)) {
			this.xref.remove(xref);
			this.xref.add(xref);
			return;
		}
		this.xref.add(xref);
		_model.add(_model.createStatement(_resource,xrefProperty,xref.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Xref addXref() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Xref xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createXref(_model.createResource(),_model);
		if (this.xref == null)
			initXref();
		this.xref.add(xref);
		_model.add(_model.createStatement(_resource,xrefProperty,xref.resource()));
		return xref;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Xref addXref(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Xref xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getXref(resource,_model);
		if (this.xref == null)
			initXref();
		if (this.xref.contains(xref))
			return xref;
		this.xref.add(xref);
		_model.add(_model.createStatement(_resource,xrefProperty,xref.resource()));
		return xref;
	}
	
	public void removeXref(fr.curie.BiNoM.pathways.biopax.Xref xref) throws JastorException {
		if (this.xref == null)
			initXref();
		if (!this.xref.contains(xref))
			return;
		if (!_model.contains(_resource, xrefProperty, xref.resource()))
			return;
		this.xref.remove(xref);
		_model.removeAll(_resource, xrefProperty, xref.resource());
	}
		 
	public java.lang.String getSequence() throws JastorException {
		if (sequence != null)
			return sequence;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, sequenceProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": sequence getProperty() in fr.curie.BiNoM.pathways.biopax.ProteinReference model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		sequence = (java.lang.String)obj;
		return sequence;
	}
	
	public void setSequence(java.lang.String sequence) throws JastorException {
		if (_model.contains(_resource,sequenceProperty)) {
			_model.removeAll(_resource,sequenceProperty,null);
		}
		this.sequence = sequence;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (sequence != null) {
			_model.add(_model.createStatement(_resource,sequenceProperty, _model.createTypedLiteral(sequence)));
		}	
	}


	public fr.curie.BiNoM.pathways.biopax.BioSource getOrganism() throws JastorException {
		if (organism != null)
			return organism;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, organismProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": organism getProperty() in fr.curie.BiNoM.pathways.biopax.ProteinReference model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		organism = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getBioSource(resource,_model);
		return organism;
	}

	public void setOrganism(fr.curie.BiNoM.pathways.biopax.BioSource organism) throws JastorException {
		if (_model.contains(_resource,organismProperty)) {
			_model.removeAll(_resource,organismProperty,null);
		}
		this.organism = organism;
		if (organism != null) {
			_model.add(_model.createStatement(_resource,organismProperty, organism.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.BioSource setOrganism() throws JastorException {
		if (_model.contains(_resource,organismProperty)) {
			_model.removeAll(_resource,organismProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.BioSource organism = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createBioSource(_model.createResource(),_model);
		this.organism = organism;
		_model.add(_model.createStatement(_resource,organismProperty, organism.resource()));
		return organism;
	}
	
	public fr.curie.BiNoM.pathways.biopax.BioSource setOrganism(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,organismProperty)) {
			_model.removeAll(_resource,organismProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.BioSource organism = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getBioSource(resource,_model);
		this.organism = organism;
		_model.add(_model.createStatement(_resource,organismProperty, organism.resource()));
		return organism;
	}
	 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof ProteinReferenceListener))
			throw new IllegalArgumentException("ThingListener must be instance of ProteinReferenceListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((ProteinReferenceListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof ProteinReferenceListener))
			throw new IllegalArgumentException("ThingListener must be instance of ProteinReferenceListener"); 
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
						ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
						listener.commentAdded(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(memberEntityReferenceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.EntityReference _memberEntityReference = null;
					try {
						_memberEntityReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityReference(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (memberEntityReference == null) {
						try {
							initMemberEntityReference();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!memberEntityReference.contains(_memberEntityReference))
						memberEntityReference.add(_memberEntityReference);
					if (listeners != null) {
						java.util.ArrayList consumersForMemberEntityReference;
						synchronized (listeners) {
							consumersForMemberEntityReference = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMemberEntityReference.iterator();iter.hasNext();){
							ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
							listener.memberEntityReferenceAdded(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this,_memberEntityReference);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.ProteinReference.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.ProteinReference _memberEntityReference_asProteinReference = null;
					try {
						_memberEntityReference_asProteinReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getProteinReference(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (memberEntityReference_asProteinReference == null) {
						try {
							initMemberEntityReference_asProteinReference();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!memberEntityReference_asProteinReference.contains(_memberEntityReference_asProteinReference))
						memberEntityReference_asProteinReference.add(_memberEntityReference_asProteinReference);
					if (listeners != null) {
						java.util.ArrayList consumersForMemberEntityReference_asProteinReference;
						synchronized (listeners) {
							consumersForMemberEntityReference_asProteinReference = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMemberEntityReference_asProteinReference.iterator();iter.hasNext();){
							ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
							listener.memberEntityReferenceAdded(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this,_memberEntityReference_asProteinReference);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(entityReferenceTypeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				entityReferenceType = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						entityReferenceType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityReferenceTypeVocabulary(resource,_model);
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
						ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
						listener.entityReferenceTypeChanged(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(entityFeatureProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.EntityFeature _entityFeature = null;
					try {
						_entityFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (entityFeature == null) {
						try {
							initEntityFeature();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!entityFeature.contains(_entityFeature))
						entityFeature.add(_entityFeature);
					if (listeners != null) {
						java.util.ArrayList consumersForEntityFeature;
						synchronized (listeners) {
							consumersForEntityFeature = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEntityFeature.iterator();iter.hasNext();){
							ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
							listener.entityFeatureAdded(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this,_entityFeature);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(nameProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (name == null) {
					try {
						initName();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (!name.contains(obj))
					name.add(obj);
				java.util.ArrayList consumersForName;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForName = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForName.iterator();iter.hasNext();){
						ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
						listener.nameAdded(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(evidenceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Evidence _evidence = null;
					try {
						_evidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEvidence(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (evidence == null) {
						try {
							initEvidence();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!evidence.contains(_evidence))
						evidence.add(_evidence);
					if (listeners != null) {
						java.util.ArrayList consumersForEvidence;
						synchronized (listeners) {
							consumersForEvidence = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEvidence.iterator();iter.hasNext();){
							ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
							listener.evidenceAdded(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this,_evidence);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(xrefProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Xref _xref = null;
					try {
						_xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getXref(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (xref == null) {
						try {
							initXref();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!xref.contains(_xref))
						xref.add(_xref);
					if (listeners != null) {
						java.util.ArrayList consumersForXref;
						synchronized (listeners) {
							consumersForXref = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForXref.iterator();iter.hasNext();){
							ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
							listener.xrefAdded(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this,_xref);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(sequenceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				sequence = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
						listener.sequenceChanged(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(organismProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				organism = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						organism = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getBioSource(resource,_model);
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
						ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
						listener.organismChanged(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this);
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
						ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
						listener.commentRemoved(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(memberEntityReferenceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.EntityReference _memberEntityReference = null;
					if (memberEntityReference != null) {
						boolean found = false;
						for (int i=0;i<memberEntityReference.size();i++) {
							fr.curie.BiNoM.pathways.biopax.EntityReference __item = (fr.curie.BiNoM.pathways.biopax.EntityReference) memberEntityReference.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_memberEntityReference = __item;
								break;
							}
						}
						if (found)
							memberEntityReference.remove(_memberEntityReference);
						else {
							try {
								_memberEntityReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityReference(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_memberEntityReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityReference(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForMemberEntityReference;
						synchronized (listeners) {
							consumersForMemberEntityReference = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMemberEntityReference.iterator();iter.hasNext();){
							ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
							listener.memberEntityReferenceRemoved(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this,_memberEntityReference);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.ProteinReference.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.ProteinReference _memberEntityReference_asProteinReference = null;
					if (memberEntityReference_asProteinReference != null) {
						boolean found = false;
						for (int i=0;i<memberEntityReference_asProteinReference.size();i++) {
							fr.curie.BiNoM.pathways.biopax.ProteinReference __item = (fr.curie.BiNoM.pathways.biopax.ProteinReference) memberEntityReference_asProteinReference.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_memberEntityReference_asProteinReference = __item;
								break;
							}
						}
						if (found)
							memberEntityReference_asProteinReference.remove(_memberEntityReference_asProteinReference);
						else {
							try {
								_memberEntityReference_asProteinReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getProteinReference(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_memberEntityReference_asProteinReference = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getProteinReference(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForMemberEntityReference_asProteinReference;
						synchronized (listeners) {
							consumersForMemberEntityReference_asProteinReference = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMemberEntityReference_asProteinReference.iterator();iter.hasNext();){
							ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
							listener.memberEntityReferenceRemoved(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this,_memberEntityReference_asProteinReference);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(entityReferenceTypeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (entityReferenceType != null && entityReferenceType.resource().equals(resource))
						entityReferenceType = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
						listener.entityReferenceTypeChanged(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(entityFeatureProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.EntityFeature _entityFeature = null;
					if (entityFeature != null) {
						boolean found = false;
						for (int i=0;i<entityFeature.size();i++) {
							fr.curie.BiNoM.pathways.biopax.EntityFeature __item = (fr.curie.BiNoM.pathways.biopax.EntityFeature) entityFeature.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_entityFeature = __item;
								break;
							}
						}
						if (found)
							entityFeature.remove(_entityFeature);
						else {
							try {
								_entityFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_entityFeature = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntityFeature(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForEntityFeature;
						synchronized (listeners) {
							consumersForEntityFeature = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEntityFeature.iterator();iter.hasNext();){
							ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
							listener.entityFeatureRemoved(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this,_entityFeature);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(nameProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (name != null) {
					if (name.contains(obj))
						name.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
						listener.nameRemoved(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(evidenceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Evidence _evidence = null;
					if (evidence != null) {
						boolean found = false;
						for (int i=0;i<evidence.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Evidence __item = (fr.curie.BiNoM.pathways.biopax.Evidence) evidence.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_evidence = __item;
								break;
							}
						}
						if (found)
							evidence.remove(_evidence);
						else {
							try {
								_evidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEvidence(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_evidence = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEvidence(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForEvidence;
						synchronized (listeners) {
							consumersForEvidence = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEvidence.iterator();iter.hasNext();){
							ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
							listener.evidenceRemoved(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this,_evidence);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(xrefProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Xref _xref = null;
					if (xref != null) {
						boolean found = false;
						for (int i=0;i<xref.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Xref __item = (fr.curie.BiNoM.pathways.biopax.Xref) xref.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_xref = __item;
								break;
							}
						}
						if (found)
							xref.remove(_xref);
						else {
							try {
								_xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getXref(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_xref = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getXref(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForXref;
						synchronized (listeners) {
							consumersForXref = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForXref.iterator();iter.hasNext();){
							ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
							listener.xrefRemoved(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this,_xref);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(sequenceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (sequence != null && sequence.equals(obj))
					sequence = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
						listener.sequenceChanged(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(organismProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (organism != null && organism.resource().equals(resource))
						organism = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						ProteinReferenceListener listener=(ProteinReferenceListener)iter.next();
						listener.organismChanged(fr.curie.BiNoM.pathways.biopax.ProteinReferenceImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}