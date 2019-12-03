

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.GeneticInteraction}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#GeneticInteraction)</p>
 * <br>
 */
public class GeneticInteractionImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.GeneticInteraction {
	

	private static com.hp.hpl.jena.rdf.model.Property dataSourceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#dataSource");
	private java.util.ArrayList dataSource;
	private static com.hp.hpl.jena.rdf.model.Property availabilityProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#availability");
	private java.util.ArrayList availability;
	private static com.hp.hpl.jena.rdf.model.Property commentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#comment");
	private java.util.ArrayList comment;
	private static com.hp.hpl.jena.rdf.model.Property nameProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#name");
	private java.util.ArrayList name;
	private static com.hp.hpl.jena.rdf.model.Property evidenceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#evidence");
	private java.util.ArrayList evidence;
	private static com.hp.hpl.jena.rdf.model.Property xrefProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#xref");
	private java.util.ArrayList xref;
	private static com.hp.hpl.jena.rdf.model.Property interactionTypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#interactionType");
	private fr.curie.BiNoM.pathways.biopax.InteractionVocabulary interactionType;
	private static com.hp.hpl.jena.rdf.model.Property participantProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#participant");
	private java.util.ArrayList participant;
	private java.util.ArrayList participant_asGene;
	private static com.hp.hpl.jena.rdf.model.Property interactionScoreProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#interactionScore");
	private java.util.ArrayList interactionScore;
	private static com.hp.hpl.jena.rdf.model.Property phenotypeProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#phenotype");
	private fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary phenotype;

	GeneticInteractionImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static GeneticInteractionImpl getGeneticInteraction(Resource resource, Model model) throws JastorException {
		return new GeneticInteractionImpl(resource, model);
	}
	    
	static GeneticInteractionImpl createGeneticInteraction(Resource resource, Model model) throws JastorException {
		GeneticInteractionImpl impl = new GeneticInteractionImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, GeneticInteraction.TYPE)))
			impl._model.add(impl._resource, RDF.type, GeneticInteraction.TYPE);
		impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Entity.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Entity.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Interaction.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.Interaction.TYPE));     
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
		it = _model.listStatements(_resource,dataSourceProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,availabilityProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,commentProperty,(RDFNode)null);
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
		it = _model.listStatements(_resource,interactionTypeProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,participantProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,interactionScoreProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,phenotypeProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.GeneticInteraction.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.Entity.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.Interaction.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
	
	public void clearCache() {
		dataSource = null;
		availability = null;
		comment = null;
		name = null;
		evidence = null;
		xref = null;
		interactionType = null;
		participant = null;
		participant_asGene = null;
		interactionScore = null;
		phenotype = null;
	}


	private void initDataSource() throws JastorException {
		this.dataSource = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, dataSourceProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#dataSource properties in GeneticInteraction model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.Provenance dataSource = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getProvenance(resource,_model);
				this.dataSource.add(dataSource);
			}
		}
	}

	public java.util.Iterator getDataSource() throws JastorException {
		if (dataSource == null)
			initDataSource();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(dataSource,_resource,dataSourceProperty,true);
	}

	public void addDataSource(fr.curie.BiNoM.pathways.biopax.Provenance dataSource) throws JastorException {
		if (this.dataSource == null)
			initDataSource();
		if (this.dataSource.contains(dataSource)) {
			this.dataSource.remove(dataSource);
			this.dataSource.add(dataSource);
			return;
		}
		this.dataSource.add(dataSource);
		_model.add(_model.createStatement(_resource,dataSourceProperty,dataSource.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Provenance addDataSource() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Provenance dataSource = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createProvenance(_model.createResource(),_model);
		if (this.dataSource == null)
			initDataSource();
		this.dataSource.add(dataSource);
		_model.add(_model.createStatement(_resource,dataSourceProperty,dataSource.resource()));
		return dataSource;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Provenance addDataSource(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Provenance dataSource = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getProvenance(resource,_model);
		if (this.dataSource == null)
			initDataSource();
		if (this.dataSource.contains(dataSource))
			return dataSource;
		this.dataSource.add(dataSource);
		_model.add(_model.createStatement(_resource,dataSourceProperty,dataSource.resource()));
		return dataSource;
	}
	
	public void removeDataSource(fr.curie.BiNoM.pathways.biopax.Provenance dataSource) throws JastorException {
		if (this.dataSource == null)
			initDataSource();
		if (!this.dataSource.contains(dataSource))
			return;
		if (!_model.contains(_resource, dataSourceProperty, dataSource.resource()))
			return;
		this.dataSource.remove(dataSource);
		_model.removeAll(_resource, dataSourceProperty, dataSource.resource());
	}
		 

	private void initAvailability() throws JastorException {
		availability = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, availabilityProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#availability properties in GeneticInteraction model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			availability.add(Util.fixLiteral(literal.getValue(),"java.lang.String"));
		}
	}

	public java.util.Iterator getAvailability() throws JastorException {
		if (availability == null)
			initAvailability();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(availability,_resource,availabilityProperty,false);
	}

	public void addAvailability(java.lang.String availability) throws JastorException {
		if (this.availability == null)
			initAvailability();
		if (this.availability.contains(availability))
			return;
		if (_model.contains(_resource, availabilityProperty, _model.createTypedLiteral(availability)))
			return;
		this.availability.add(availability);
		_model.add(_resource, availabilityProperty, _model.createTypedLiteral(availability));
	}
	
	public void removeAvailability(java.lang.String availability) throws JastorException {
		if (this.availability == null)
			initAvailability();
		if (!this.availability.contains(availability))
			return;
		if (!_model.contains(_resource, availabilityProperty, _model.createTypedLiteral(availability)))
			return;
		this.availability.remove(availability);
		_model.removeAll(_resource, availabilityProperty, _model.createTypedLiteral(availability));
	}


	private void initComment() throws JastorException {
		comment = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, commentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#comment properties in GeneticInteraction model not a Literal", stmt.getObject());
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


	private void initName() throws JastorException {
		name = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, nameProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#name properties in GeneticInteraction model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#evidence properties in GeneticInteraction model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#xref properties in GeneticInteraction model not a Resource", stmt.getObject());
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
		 

	public fr.curie.BiNoM.pathways.biopax.InteractionVocabulary getInteractionType() throws JastorException {
		if (interactionType != null)
			return interactionType;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, interactionTypeProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": interactionType getProperty() in fr.curie.BiNoM.pathways.biopax.GeneticInteraction model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		interactionType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteractionVocabulary(resource,_model);
		return interactionType;
	}

	public void setInteractionType(fr.curie.BiNoM.pathways.biopax.InteractionVocabulary interactionType) throws JastorException {
		if (_model.contains(_resource,interactionTypeProperty)) {
			_model.removeAll(_resource,interactionTypeProperty,null);
		}
		this.interactionType = interactionType;
		if (interactionType != null) {
			_model.add(_model.createStatement(_resource,interactionTypeProperty, interactionType.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.InteractionVocabulary setInteractionType() throws JastorException {
		if (_model.contains(_resource,interactionTypeProperty)) {
			_model.removeAll(_resource,interactionTypeProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.InteractionVocabulary interactionType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createInteractionVocabulary(_model.createResource(),_model);
		this.interactionType = interactionType;
		_model.add(_model.createStatement(_resource,interactionTypeProperty, interactionType.resource()));
		return interactionType;
	}
	
	public fr.curie.BiNoM.pathways.biopax.InteractionVocabulary setInteractionType(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,interactionTypeProperty)) {
			_model.removeAll(_resource,interactionTypeProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.InteractionVocabulary interactionType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteractionVocabulary(resource,_model);
		this.interactionType = interactionType;
		_model.add(_model.createStatement(_resource,interactionTypeProperty, interactionType.resource()));
		return interactionType;
	}
	

	private void initParticipant() throws JastorException {
		this.participant = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, participantProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#participant properties in GeneticInteraction model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.Entity participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntity(resource,_model);
				this.participant.add(participant);
			}
		}
	}

	public java.util.Iterator getParticipant() throws JastorException {
		if (participant == null)
			initParticipant();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(participant,_resource,participantProperty,true);
	}

	public void addParticipant(fr.curie.BiNoM.pathways.biopax.Entity participant) throws JastorException {
		if (this.participant == null)
			initParticipant();
		if (this.participant.contains(participant)) {
			this.participant.remove(participant);
			this.participant.add(participant);
			return;
		}
		this.participant.add(participant);
		_model.add(_model.createStatement(_resource,participantProperty,participant.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Entity addParticipant() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Entity participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createEntity(_model.createResource(),_model);
		if (this.participant == null)
			initParticipant();
		this.participant.add(participant);
		_model.add(_model.createStatement(_resource,participantProperty,participant.resource()));
		return participant;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Entity addParticipant(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Entity participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntity(resource,_model);
		if (this.participant == null)
			initParticipant();
		if (this.participant.contains(participant))
			return participant;
		this.participant.add(participant);
		_model.add(_model.createStatement(_resource,participantProperty,participant.resource()));
		return participant;
	}
	
	public void removeParticipant(fr.curie.BiNoM.pathways.biopax.Entity participant) throws JastorException {
		if (this.participant == null)
			initParticipant();
		if (!this.participant.contains(participant))
			return;
		if (!_model.contains(_resource, participantProperty, participant.resource()))
			return;
		this.participant.remove(participant);
		_model.removeAll(_resource, participantProperty, participant.resource());
	}
		
	private void initParticipant_asGene() throws JastorException {
		this.participant_asGene = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, participantProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#participant properties in GeneticInteraction model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Gene.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.Gene participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getGene(resource,_model);
				this.participant_asGene.add(participant);
			}
		}
	}

	public java.util.Iterator getParticipant_asGene() throws JastorException {
		if (participant_asGene == null)
			initParticipant_asGene();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(participant_asGene,_resource,participantProperty,true);
	}

	public void addParticipant(fr.curie.BiNoM.pathways.biopax.Gene participant) throws JastorException {
		if (this.participant_asGene == null)
			initParticipant_asGene();
		if (this.participant_asGene.contains(participant)) {
			this.participant_asGene.remove(participant);
			this.participant_asGene.add(participant);
			return;
		}
		this.participant_asGene.add(participant);
		_model.add(_model.createStatement(_resource,participantProperty,participant.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Gene addParticipant_asGene() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Gene participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createGene(_model.createResource(),_model);
		if (this.participant_asGene == null)
			initParticipant_asGene();
		this.participant_asGene.add(participant);
		_model.add(_model.createStatement(_resource,participantProperty,participant.resource()));
		return participant;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Gene addParticipant_asGene(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Gene participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getGene(resource,_model);
		if (this.participant_asGene == null)
			initParticipant_asGene();
		if (this.participant_asGene.contains(participant))
			return participant;
		this.participant_asGene.add(participant);
		_model.add(_model.createStatement(_resource,participantProperty,participant.resource()));
		return participant;
	}
	
	public void removeParticipant(fr.curie.BiNoM.pathways.biopax.Gene participant) throws JastorException {
		if (this.participant_asGene == null)
			initParticipant_asGene();
		if (!this.participant_asGene.contains(participant))
			return;
		if (!_model.contains(_resource, participantProperty, participant.resource()))
			return;
		this.participant_asGene.remove(participant);
		_model.removeAll(_resource, participantProperty, participant.resource());
	}
		 

	private void initInteractionScore() throws JastorException {
		this.interactionScore = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, interactionScoreProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#interactionScore properties in GeneticInteraction model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.Score interactionScore = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getScore(resource,_model);
				this.interactionScore.add(interactionScore);
			}
		}
	}

	public java.util.Iterator getInteractionScore() throws JastorException {
		if (interactionScore == null)
			initInteractionScore();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(interactionScore,_resource,interactionScoreProperty,true);
	}

	public void addInteractionScore(fr.curie.BiNoM.pathways.biopax.Score interactionScore) throws JastorException {
		if (this.interactionScore == null)
			initInteractionScore();
		if (this.interactionScore.contains(interactionScore)) {
			this.interactionScore.remove(interactionScore);
			this.interactionScore.add(interactionScore);
			return;
		}
		this.interactionScore.add(interactionScore);
		_model.add(_model.createStatement(_resource,interactionScoreProperty,interactionScore.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Score addInteractionScore() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Score interactionScore = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createScore(_model.createResource(),_model);
		if (this.interactionScore == null)
			initInteractionScore();
		this.interactionScore.add(interactionScore);
		_model.add(_model.createStatement(_resource,interactionScoreProperty,interactionScore.resource()));
		return interactionScore;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Score addInteractionScore(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Score interactionScore = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getScore(resource,_model);
		if (this.interactionScore == null)
			initInteractionScore();
		if (this.interactionScore.contains(interactionScore))
			return interactionScore;
		this.interactionScore.add(interactionScore);
		_model.add(_model.createStatement(_resource,interactionScoreProperty,interactionScore.resource()));
		return interactionScore;
	}
	
	public void removeInteractionScore(fr.curie.BiNoM.pathways.biopax.Score interactionScore) throws JastorException {
		if (this.interactionScore == null)
			initInteractionScore();
		if (!this.interactionScore.contains(interactionScore))
			return;
		if (!_model.contains(_resource, interactionScoreProperty, interactionScore.resource()))
			return;
		this.interactionScore.remove(interactionScore);
		_model.removeAll(_resource, interactionScoreProperty, interactionScore.resource());
	}
		 

	public fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary getPhenotype() throws JastorException {
		if (phenotype != null)
			return phenotype;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, phenotypeProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": phenotype getProperty() in fr.curie.BiNoM.pathways.biopax.GeneticInteraction model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		phenotype = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhenotypeVocabulary(resource,_model);
		return phenotype;
	}

	public void setPhenotype(fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary phenotype) throws JastorException {
		if (_model.contains(_resource,phenotypeProperty)) {
			_model.removeAll(_resource,phenotypeProperty,null);
		}
		this.phenotype = phenotype;
		if (phenotype != null) {
			_model.add(_model.createStatement(_resource,phenotypeProperty, phenotype.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary setPhenotype() throws JastorException {
		if (_model.contains(_resource,phenotypeProperty)) {
			_model.removeAll(_resource,phenotypeProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary phenotype = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createPhenotypeVocabulary(_model.createResource(),_model);
		this.phenotype = phenotype;
		_model.add(_model.createStatement(_resource,phenotypeProperty, phenotype.resource()));
		return phenotype;
	}
	
	public fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary setPhenotype(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,phenotypeProperty)) {
			_model.removeAll(_resource,phenotypeProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.PhenotypeVocabulary phenotype = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhenotypeVocabulary(resource,_model);
		this.phenotype = phenotype;
		_model.add(_model.createStatement(_resource,phenotypeProperty, phenotype.resource()));
		return phenotype;
	}
	 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof GeneticInteractionListener))
			throw new IllegalArgumentException("ThingListener must be instance of GeneticInteractionListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((GeneticInteractionListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof GeneticInteractionListener))
			throw new IllegalArgumentException("ThingListener must be instance of GeneticInteractionListener"); 
		if (listeners == null)
			return;
		if (this.listeners.contains(listener)){
			listeners.remove(listener);
		}
	}



	
		public void addedStatement(com.hp.hpl.jena.rdf.model.Statement stmt) {

			if (stmt.getPredicate().equals(dataSourceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Provenance _dataSource = null;
					try {
						_dataSource = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getProvenance(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (dataSource == null) {
						try {
							initDataSource();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!dataSource.contains(_dataSource))
						dataSource.add(_dataSource);
					if (listeners != null) {
						java.util.ArrayList consumersForDataSource;
						synchronized (listeners) {
							consumersForDataSource = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForDataSource.iterator();iter.hasNext();){
							GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
							listener.dataSourceAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,_dataSource);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(availabilityProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (availability == null) {
					try {
						initAvailability();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (!availability.contains(obj))
					availability.add(obj);
				java.util.ArrayList consumersForAvailability;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForAvailability = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForAvailability.iterator();iter.hasNext();){
						GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
						listener.availabilityAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
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
						GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
						listener.commentAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,(java.lang.String)obj);
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
						GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
						listener.nameAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,(java.lang.String)obj);
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
							GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
							listener.evidenceAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,_evidence);
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
							GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
							listener.xrefAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,_xref);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(interactionTypeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				interactionType = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						interactionType = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteractionVocabulary(resource,_model);
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
						GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
						listener.interactionTypeChanged(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(participantProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Entity _participant = null;
					try {
						_participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntity(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (participant == null) {
						try {
							initParticipant();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!participant.contains(_participant))
						participant.add(_participant);
					if (listeners != null) {
						java.util.ArrayList consumersForParticipant;
						synchronized (listeners) {
							consumersForParticipant = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForParticipant.iterator();iter.hasNext();){
							GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
							listener.participantAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,_participant);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Gene.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Gene _participant_asGene = null;
					try {
						_participant_asGene = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getGene(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (participant_asGene == null) {
						try {
							initParticipant_asGene();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!participant_asGene.contains(_participant_asGene))
						participant_asGene.add(_participant_asGene);
					if (listeners != null) {
						java.util.ArrayList consumersForParticipant_asGene;
						synchronized (listeners) {
							consumersForParticipant_asGene = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForParticipant_asGene.iterator();iter.hasNext();){
							GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
							listener.participantAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,_participant_asGene);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(interactionScoreProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Score _interactionScore = null;
					try {
						_interactionScore = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getScore(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (interactionScore == null) {
						try {
							initInteractionScore();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!interactionScore.contains(_interactionScore))
						interactionScore.add(_interactionScore);
					if (listeners != null) {
						java.util.ArrayList consumersForInteractionScore;
						synchronized (listeners) {
							consumersForInteractionScore = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForInteractionScore.iterator();iter.hasNext();){
							GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
							listener.interactionScoreAdded(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,_interactionScore);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(phenotypeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				phenotype = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						phenotype = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPhenotypeVocabulary(resource,_model);
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
						GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
						listener.phenotypeChanged(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this);
					}
				}
				return;
			}
		}
		
		public void removedStatement(com.hp.hpl.jena.rdf.model.Statement stmt) {
//			if (!stmt.getSubject().equals(_resource))
//				return;
			if (stmt.getPredicate().equals(dataSourceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Provenance _dataSource = null;
					if (dataSource != null) {
						boolean found = false;
						for (int i=0;i<dataSource.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Provenance __item = (fr.curie.BiNoM.pathways.biopax.Provenance) dataSource.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_dataSource = __item;
								break;
							}
						}
						if (found)
							dataSource.remove(_dataSource);
						else {
							try {
								_dataSource = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getProvenance(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_dataSource = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getProvenance(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForDataSource;
						synchronized (listeners) {
							consumersForDataSource = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForDataSource.iterator();iter.hasNext();){
							GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
							listener.dataSourceRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,_dataSource);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(availabilityProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (availability != null) {
					if (availability.contains(obj))
						availability.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
						listener.availabilityRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
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
						GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
						listener.commentRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,(java.lang.String)obj);
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
						GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
						listener.nameRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,(java.lang.String)obj);
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
							GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
							listener.evidenceRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,_evidence);
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
							GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
							listener.xrefRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,_xref);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(interactionTypeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (interactionType != null && interactionType.resource().equals(resource))
						interactionType = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
						listener.interactionTypeChanged(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(participantProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Entity _participant = null;
					if (participant != null) {
						boolean found = false;
						for (int i=0;i<participant.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Entity __item = (fr.curie.BiNoM.pathways.biopax.Entity) participant.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_participant = __item;
								break;
							}
						}
						if (found)
							participant.remove(_participant);
						else {
							try {
								_participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntity(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_participant = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getEntity(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForParticipant;
						synchronized (listeners) {
							consumersForParticipant = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForParticipant.iterator();iter.hasNext();){
							GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
							listener.participantRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,_participant);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Gene.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Gene _participant_asGene = null;
					if (participant_asGene != null) {
						boolean found = false;
						for (int i=0;i<participant_asGene.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Gene __item = (fr.curie.BiNoM.pathways.biopax.Gene) participant_asGene.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_participant_asGene = __item;
								break;
							}
						}
						if (found)
							participant_asGene.remove(_participant_asGene);
						else {
							try {
								_participant_asGene = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getGene(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_participant_asGene = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getGene(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForParticipant_asGene;
						synchronized (listeners) {
							consumersForParticipant_asGene = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForParticipant_asGene.iterator();iter.hasNext();){
							GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
							listener.participantRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,_participant_asGene);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(interactionScoreProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.Score _interactionScore = null;
					if (interactionScore != null) {
						boolean found = false;
						for (int i=0;i<interactionScore.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Score __item = (fr.curie.BiNoM.pathways.biopax.Score) interactionScore.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_interactionScore = __item;
								break;
							}
						}
						if (found)
							interactionScore.remove(_interactionScore);
						else {
							try {
								_interactionScore = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getScore(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_interactionScore = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getScore(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForInteractionScore;
						synchronized (listeners) {
							consumersForInteractionScore = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForInteractionScore.iterator();iter.hasNext();){
							GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
							listener.interactionScoreRemoved(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this,_interactionScore);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(phenotypeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (phenotype != null && phenotype.resource().equals(resource))
						phenotype = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						GeneticInteractionListener listener=(GeneticInteractionListener)iter.next();
						listener.phenotypeChanged(fr.curie.BiNoM.pathways.biopax.GeneticInteractionImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}