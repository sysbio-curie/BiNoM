

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
 * Implementation of {@link fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep}
 * Use the fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory to create instances of this class.
 * <p>(URI: http://www.biopax.org/release/biopax-level3.owl#BiochemicalPathwayStep)</p>
 * <br>
 */
public class BiochemicalPathwayStepImpl extends com.ibm.adtech.jastor.ThingImpl implements fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep {
	

	private static com.hp.hpl.jena.rdf.model.Property commentProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#comment");
	private java.util.ArrayList comment;
	private static com.hp.hpl.jena.rdf.model.Property nextStepProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#nextStep");
	private java.util.ArrayList nextStep;
	private static com.hp.hpl.jena.rdf.model.Property stepProcessProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#stepProcess");
	private java.util.ArrayList stepProcess_asControl;
	private java.util.ArrayList stepProcess_asInteraction;
	private java.util.ArrayList stepProcess_asPathway;
	private static com.hp.hpl.jena.rdf.model.Property evidenceProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#evidence");
	private java.util.ArrayList evidence;
	private static com.hp.hpl.jena.rdf.model.Property stepConversionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#stepConversion");
	private fr.curie.BiNoM.pathways.biopax.Conversion stepConversion;
	private static com.hp.hpl.jena.rdf.model.Property stepDirectionProperty = ResourceFactory.createProperty("http://www.biopax.org/release/biopax-level3.owl#stepDirection");
	private java.lang.String stepDirection;

	BiochemicalPathwayStepImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static BiochemicalPathwayStepImpl getBiochemicalPathwayStep(Resource resource, Model model) throws JastorException {
		return new BiochemicalPathwayStepImpl(resource, model);
	}
	    
	static BiochemicalPathwayStepImpl createBiochemicalPathwayStep(Resource resource, Model model) throws JastorException {
		BiochemicalPathwayStepImpl impl = new BiochemicalPathwayStepImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, BiochemicalPathwayStep.TYPE)))
			impl._model.add(impl._resource, RDF.type, BiochemicalPathwayStep.TYPE);
		impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE));     
		if (!_model.contains(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.PathwayStep.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, fr.curie.BiNoM.pathways.biopax.PathwayStep.TYPE));     
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
		it = _model.listStatements(_resource,nextStepProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,stepProcessProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,evidenceProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,stepConversionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,stepDirectionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.UtilityClass.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, fr.curie.BiNoM.pathways.biopax.PathwayStep.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
	
	public void clearCache() {
		comment = null;
		nextStep = null;
		stepProcess_asControl = null;
		stepProcess_asInteraction = null;
		stepProcess_asPathway = null;
		evidence = null;
		stepConversion = null;
		stepDirection = null;
	}


	private void initComment() throws JastorException {
		comment = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, commentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#comment properties in BiochemicalPathwayStep model not a Literal", stmt.getObject());
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


	private void initNextStep() throws JastorException {
		this.nextStep = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, nextStepProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#nextStep properties in BiochemicalPathwayStep model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				fr.curie.BiNoM.pathways.biopax.PathwayStep nextStep = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathwayStep(resource,_model);
				this.nextStep.add(nextStep);
			}
		}
	}

	public java.util.Iterator getNextStep() throws JastorException {
		if (nextStep == null)
			initNextStep();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(nextStep,_resource,nextStepProperty,true);
	}

	public void addNextStep(fr.curie.BiNoM.pathways.biopax.PathwayStep nextStep) throws JastorException {
		if (this.nextStep == null)
			initNextStep();
		if (this.nextStep.contains(nextStep)) {
			this.nextStep.remove(nextStep);
			this.nextStep.add(nextStep);
			return;
		}
		this.nextStep.add(nextStep);
		_model.add(_model.createStatement(_resource,nextStepProperty,nextStep.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.PathwayStep addNextStep() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PathwayStep nextStep = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createPathwayStep(_model.createResource(),_model);
		if (this.nextStep == null)
			initNextStep();
		this.nextStep.add(nextStep);
		_model.add(_model.createStatement(_resource,nextStepProperty,nextStep.resource()));
		return nextStep;
	}
	
	public fr.curie.BiNoM.pathways.biopax.PathwayStep addNextStep(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.PathwayStep nextStep = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathwayStep(resource,_model);
		if (this.nextStep == null)
			initNextStep();
		if (this.nextStep.contains(nextStep))
			return nextStep;
		this.nextStep.add(nextStep);
		_model.add(_model.createStatement(_resource,nextStepProperty,nextStep.resource()));
		return nextStep;
	}
	
	public void removeNextStep(fr.curie.BiNoM.pathways.biopax.PathwayStep nextStep) throws JastorException {
		if (this.nextStep == null)
			initNextStep();
		if (!this.nextStep.contains(nextStep))
			return;
		if (!_model.contains(_resource, nextStepProperty, nextStep.resource()))
			return;
		this.nextStep.remove(nextStep);
		_model.removeAll(_resource, nextStepProperty, nextStep.resource());
	}
		 

	private void initStepProcess_asControl() throws JastorException {
		this.stepProcess_asControl = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, stepProcessProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#stepProcess properties in BiochemicalPathwayStep model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Control.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.Control stepProcess = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getControl(resource,_model);
				this.stepProcess_asControl.add(stepProcess);
			}
		}
	}

	public java.util.Iterator getStepProcess_asControl() throws JastorException {
		if (stepProcess_asControl == null)
			initStepProcess_asControl();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(stepProcess_asControl,_resource,stepProcessProperty,true);
	}

	public void addStepProcess(fr.curie.BiNoM.pathways.biopax.Control stepProcess) throws JastorException {
		if (this.stepProcess_asControl == null)
			initStepProcess_asControl();
		if (this.stepProcess_asControl.contains(stepProcess)) {
			this.stepProcess_asControl.remove(stepProcess);
			this.stepProcess_asControl.add(stepProcess);
			return;
		}
		this.stepProcess_asControl.add(stepProcess);
		_model.add(_model.createStatement(_resource,stepProcessProperty,stepProcess.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Control addStepProcess_asControl() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Control stepProcess = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createControl(_model.createResource(),_model);
		if (this.stepProcess_asControl == null)
			initStepProcess_asControl();
		this.stepProcess_asControl.add(stepProcess);
		_model.add(_model.createStatement(_resource,stepProcessProperty,stepProcess.resource()));
		return stepProcess;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Control addStepProcess_asControl(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Control stepProcess = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getControl(resource,_model);
		if (this.stepProcess_asControl == null)
			initStepProcess_asControl();
		if (this.stepProcess_asControl.contains(stepProcess))
			return stepProcess;
		this.stepProcess_asControl.add(stepProcess);
		_model.add(_model.createStatement(_resource,stepProcessProperty,stepProcess.resource()));
		return stepProcess;
	}
	
	public void removeStepProcess(fr.curie.BiNoM.pathways.biopax.Control stepProcess) throws JastorException {
		if (this.stepProcess_asControl == null)
			initStepProcess_asControl();
		if (!this.stepProcess_asControl.contains(stepProcess))
			return;
		if (!_model.contains(_resource, stepProcessProperty, stepProcess.resource()))
			return;
		this.stepProcess_asControl.remove(stepProcess);
		_model.removeAll(_resource, stepProcessProperty, stepProcess.resource());
	}
		
	private void initStepProcess_asInteraction() throws JastorException {
		this.stepProcess_asInteraction = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, stepProcessProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#stepProcess properties in BiochemicalPathwayStep model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Interaction.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.Interaction stepProcess = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteraction(resource,_model);
				this.stepProcess_asInteraction.add(stepProcess);
			}
		}
	}

	public java.util.Iterator getStepProcess_asInteraction() throws JastorException {
		if (stepProcess_asInteraction == null)
			initStepProcess_asInteraction();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(stepProcess_asInteraction,_resource,stepProcessProperty,true);
	}

	public void addStepProcess(fr.curie.BiNoM.pathways.biopax.Interaction stepProcess) throws JastorException {
		if (this.stepProcess_asInteraction == null)
			initStepProcess_asInteraction();
		if (this.stepProcess_asInteraction.contains(stepProcess)) {
			this.stepProcess_asInteraction.remove(stepProcess);
			this.stepProcess_asInteraction.add(stepProcess);
			return;
		}
		this.stepProcess_asInteraction.add(stepProcess);
		_model.add(_model.createStatement(_resource,stepProcessProperty,stepProcess.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Interaction addStepProcess_asInteraction() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Interaction stepProcess = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createInteraction(_model.createResource(),_model);
		if (this.stepProcess_asInteraction == null)
			initStepProcess_asInteraction();
		this.stepProcess_asInteraction.add(stepProcess);
		_model.add(_model.createStatement(_resource,stepProcessProperty,stepProcess.resource()));
		return stepProcess;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Interaction addStepProcess_asInteraction(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Interaction stepProcess = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteraction(resource,_model);
		if (this.stepProcess_asInteraction == null)
			initStepProcess_asInteraction();
		if (this.stepProcess_asInteraction.contains(stepProcess))
			return stepProcess;
		this.stepProcess_asInteraction.add(stepProcess);
		_model.add(_model.createStatement(_resource,stepProcessProperty,stepProcess.resource()));
		return stepProcess;
	}
	
	public void removeStepProcess(fr.curie.BiNoM.pathways.biopax.Interaction stepProcess) throws JastorException {
		if (this.stepProcess_asInteraction == null)
			initStepProcess_asInteraction();
		if (!this.stepProcess_asInteraction.contains(stepProcess))
			return;
		if (!_model.contains(_resource, stepProcessProperty, stepProcess.resource()))
			return;
		this.stepProcess_asInteraction.remove(stepProcess);
		_model.removeAll(_resource, stepProcessProperty, stepProcess.resource());
	}
		
	private void initStepProcess_asPathway() throws JastorException {
		this.stepProcess_asPathway = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, stepProcessProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#stepProcess properties in BiochemicalPathwayStep model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Pathway.TYPE)) {
				fr.curie.BiNoM.pathways.biopax.Pathway stepProcess = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
				this.stepProcess_asPathway.add(stepProcess);
			}
		}
	}

	public java.util.Iterator getStepProcess_asPathway() throws JastorException {
		if (stepProcess_asPathway == null)
			initStepProcess_asPathway();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(stepProcess_asPathway,_resource,stepProcessProperty,true);
	}

	public void addStepProcess(fr.curie.BiNoM.pathways.biopax.Pathway stepProcess) throws JastorException {
		if (this.stepProcess_asPathway == null)
			initStepProcess_asPathway();
		if (this.stepProcess_asPathway.contains(stepProcess)) {
			this.stepProcess_asPathway.remove(stepProcess);
			this.stepProcess_asPathway.add(stepProcess);
			return;
		}
		this.stepProcess_asPathway.add(stepProcess);
		_model.add(_model.createStatement(_resource,stepProcessProperty,stepProcess.resource()));
	}
	
	public fr.curie.BiNoM.pathways.biopax.Pathway addStepProcess_asPathway() throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Pathway stepProcess = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createPathway(_model.createResource(),_model);
		if (this.stepProcess_asPathway == null)
			initStepProcess_asPathway();
		this.stepProcess_asPathway.add(stepProcess);
		_model.add(_model.createStatement(_resource,stepProcessProperty,stepProcess.resource()));
		return stepProcess;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Pathway addStepProcess_asPathway(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		fr.curie.BiNoM.pathways.biopax.Pathway stepProcess = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
		if (this.stepProcess_asPathway == null)
			initStepProcess_asPathway();
		if (this.stepProcess_asPathway.contains(stepProcess))
			return stepProcess;
		this.stepProcess_asPathway.add(stepProcess);
		_model.add(_model.createStatement(_resource,stepProcessProperty,stepProcess.resource()));
		return stepProcess;
	}
	
	public void removeStepProcess(fr.curie.BiNoM.pathways.biopax.Pathway stepProcess) throws JastorException {
		if (this.stepProcess_asPathway == null)
			initStepProcess_asPathway();
		if (!this.stepProcess_asPathway.contains(stepProcess))
			return;
		if (!_model.contains(_resource, stepProcessProperty, stepProcess.resource()))
			return;
		this.stepProcess_asPathway.remove(stepProcess);
		_model.removeAll(_resource, stepProcessProperty, stepProcess.resource());
	}
		 

	private void initEvidence() throws JastorException {
		this.evidence = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, evidenceProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://www.biopax.org/release/biopax-level3.owl#evidence properties in BiochemicalPathwayStep model not a Resource", stmt.getObject());
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
		 

	public fr.curie.BiNoM.pathways.biopax.Conversion getStepConversion() throws JastorException {
		if (stepConversion != null)
			return stepConversion;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, stepConversionProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": stepConversion getProperty() in fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		stepConversion = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getConversion(resource,_model);
		return stepConversion;
	}

	public void setStepConversion(fr.curie.BiNoM.pathways.biopax.Conversion stepConversion) throws JastorException {
		if (_model.contains(_resource,stepConversionProperty)) {
			_model.removeAll(_resource,stepConversionProperty,null);
		}
		this.stepConversion = stepConversion;
		if (stepConversion != null) {
			_model.add(_model.createStatement(_resource,stepConversionProperty, stepConversion.resource()));
		}			
	}
		
	public fr.curie.BiNoM.pathways.biopax.Conversion setStepConversion() throws JastorException {
		if (_model.contains(_resource,stepConversionProperty)) {
			_model.removeAll(_resource,stepConversionProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.Conversion stepConversion = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.createConversion(_model.createResource(),_model);
		this.stepConversion = stepConversion;
		_model.add(_model.createStatement(_resource,stepConversionProperty, stepConversion.resource()));
		return stepConversion;
	}
	
	public fr.curie.BiNoM.pathways.biopax.Conversion setStepConversion(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,stepConversionProperty)) {
			_model.removeAll(_resource,stepConversionProperty,null);
		}
		fr.curie.BiNoM.pathways.biopax.Conversion stepConversion = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getConversion(resource,_model);
		this.stepConversion = stepConversion;
		_model.add(_model.createStatement(_resource,stepConversionProperty, stepConversion.resource()));
		return stepConversion;
	}
	
	public java.lang.String getStepDirection() throws JastorException {
		if (stepDirection != null)
			return stepDirection;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, stepDirectionProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": stepDirection getProperty() in fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStep model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
		stepDirection = (java.lang.String)obj;
		return stepDirection;
	}
	
	public void setStepDirection(java.lang.String stepDirection) throws JastorException {
		if (_model.contains(_resource,stepDirectionProperty)) {
			_model.removeAll(_resource,stepDirectionProperty,null);
		}
		this.stepDirection = stepDirection;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (stepDirection != null) {
			_model.add(_model.createStatement(_resource,stepDirectionProperty, _model.createTypedLiteral(stepDirection)));
		}	
	}
 


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof BiochemicalPathwayStepListener))
			throw new IllegalArgumentException("ThingListener must be instance of BiochemicalPathwayStepListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((BiochemicalPathwayStepListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof BiochemicalPathwayStepListener))
			throw new IllegalArgumentException("ThingListener must be instance of BiochemicalPathwayStepListener"); 
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
						BiochemicalPathwayStepListener listener=(BiochemicalPathwayStepListener)iter.next();
						listener.commentAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(nextStepProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.PathwayStep _nextStep = null;
					try {
						_nextStep = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathwayStep(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (nextStep == null) {
						try {
							initNextStep();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!nextStep.contains(_nextStep))
						nextStep.add(_nextStep);
					if (listeners != null) {
						java.util.ArrayList consumersForNextStep;
						synchronized (listeners) {
							consumersForNextStep = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForNextStep.iterator();iter.hasNext();){
							BiochemicalPathwayStepListener listener=(BiochemicalPathwayStepListener)iter.next();
							listener.nextStepAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.this,_nextStep);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(stepProcessProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Control.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Control _stepProcess_asControl = null;
					try {
						_stepProcess_asControl = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getControl(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (stepProcess_asControl == null) {
						try {
							initStepProcess_asControl();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!stepProcess_asControl.contains(_stepProcess_asControl))
						stepProcess_asControl.add(_stepProcess_asControl);
					if (listeners != null) {
						java.util.ArrayList consumersForStepProcess_asControl;
						synchronized (listeners) {
							consumersForStepProcess_asControl = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForStepProcess_asControl.iterator();iter.hasNext();){
							BiochemicalPathwayStepListener listener=(BiochemicalPathwayStepListener)iter.next();
							listener.stepProcessAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.this,_stepProcess_asControl);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Interaction.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Interaction _stepProcess_asInteraction = null;
					try {
						_stepProcess_asInteraction = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteraction(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (stepProcess_asInteraction == null) {
						try {
							initStepProcess_asInteraction();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!stepProcess_asInteraction.contains(_stepProcess_asInteraction))
						stepProcess_asInteraction.add(_stepProcess_asInteraction);
					if (listeners != null) {
						java.util.ArrayList consumersForStepProcess_asInteraction;
						synchronized (listeners) {
							consumersForStepProcess_asInteraction = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForStepProcess_asInteraction.iterator();iter.hasNext();){
							BiochemicalPathwayStepListener listener=(BiochemicalPathwayStepListener)iter.next();
							listener.stepProcessAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.this,_stepProcess_asInteraction);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Pathway.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Pathway _stepProcess_asPathway = null;
					try {
						_stepProcess_asPathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (stepProcess_asPathway == null) {
						try {
							initStepProcess_asPathway();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!stepProcess_asPathway.contains(_stepProcess_asPathway))
						stepProcess_asPathway.add(_stepProcess_asPathway);
					if (listeners != null) {
						java.util.ArrayList consumersForStepProcess_asPathway;
						synchronized (listeners) {
							consumersForStepProcess_asPathway = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForStepProcess_asPathway.iterator();iter.hasNext();){
							BiochemicalPathwayStepListener listener=(BiochemicalPathwayStepListener)iter.next();
							listener.stepProcessAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.this,_stepProcess_asPathway);
						}
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
							BiochemicalPathwayStepListener listener=(BiochemicalPathwayStepListener)iter.next();
							listener.evidenceAdded(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.this,_evidence);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(stepConversionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				stepConversion = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						stepConversion = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getConversion(resource,_model);
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
						BiochemicalPathwayStepListener listener=(BiochemicalPathwayStepListener)iter.next();
						listener.stepConversionChanged(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(stepDirectionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				stepDirection = (java.lang.String)Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						BiochemicalPathwayStepListener listener=(BiochemicalPathwayStepListener)iter.next();
						listener.stepDirectionChanged(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.this);
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
						BiochemicalPathwayStepListener listener=(BiochemicalPathwayStepListener)iter.next();
						listener.commentRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(nextStepProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					fr.curie.BiNoM.pathways.biopax.PathwayStep _nextStep = null;
					if (nextStep != null) {
						boolean found = false;
						for (int i=0;i<nextStep.size();i++) {
							fr.curie.BiNoM.pathways.biopax.PathwayStep __item = (fr.curie.BiNoM.pathways.biopax.PathwayStep) nextStep.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_nextStep = __item;
								break;
							}
						}
						if (found)
							nextStep.remove(_nextStep);
						else {
							try {
								_nextStep = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathwayStep(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_nextStep = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathwayStep(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForNextStep;
						synchronized (listeners) {
							consumersForNextStep = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForNextStep.iterator();iter.hasNext();){
							BiochemicalPathwayStepListener listener=(BiochemicalPathwayStepListener)iter.next();
							listener.nextStepRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.this,_nextStep);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(stepProcessProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Control.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Control _stepProcess_asControl = null;
					if (stepProcess_asControl != null) {
						boolean found = false;
						for (int i=0;i<stepProcess_asControl.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Control __item = (fr.curie.BiNoM.pathways.biopax.Control) stepProcess_asControl.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_stepProcess_asControl = __item;
								break;
							}
						}
						if (found)
							stepProcess_asControl.remove(_stepProcess_asControl);
						else {
							try {
								_stepProcess_asControl = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getControl(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_stepProcess_asControl = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getControl(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForStepProcess_asControl;
						synchronized (listeners) {
							consumersForStepProcess_asControl = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForStepProcess_asControl.iterator();iter.hasNext();){
							BiochemicalPathwayStepListener listener=(BiochemicalPathwayStepListener)iter.next();
							listener.stepProcessRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.this,_stepProcess_asControl);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Interaction.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Interaction _stepProcess_asInteraction = null;
					if (stepProcess_asInteraction != null) {
						boolean found = false;
						for (int i=0;i<stepProcess_asInteraction.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Interaction __item = (fr.curie.BiNoM.pathways.biopax.Interaction) stepProcess_asInteraction.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_stepProcess_asInteraction = __item;
								break;
							}
						}
						if (found)
							stepProcess_asInteraction.remove(_stepProcess_asInteraction);
						else {
							try {
								_stepProcess_asInteraction = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteraction(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_stepProcess_asInteraction = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getInteraction(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForStepProcess_asInteraction;
						synchronized (listeners) {
							consumersForStepProcess_asInteraction = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForStepProcess_asInteraction.iterator();iter.hasNext();){
							BiochemicalPathwayStepListener listener=(BiochemicalPathwayStepListener)iter.next();
							listener.stepProcessRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.this,_stepProcess_asInteraction);
						}
					}
				}
				if (_model.contains(resource,RDF.type,fr.curie.BiNoM.pathways.biopax.Pathway.TYPE)) {
					fr.curie.BiNoM.pathways.biopax.Pathway _stepProcess_asPathway = null;
					if (stepProcess_asPathway != null) {
						boolean found = false;
						for (int i=0;i<stepProcess_asPathway.size();i++) {
							fr.curie.BiNoM.pathways.biopax.Pathway __item = (fr.curie.BiNoM.pathways.biopax.Pathway) stepProcess_asPathway.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_stepProcess_asPathway = __item;
								break;
							}
						}
						if (found)
							stepProcess_asPathway.remove(_stepProcess_asPathway);
						else {
							try {
								_stepProcess_asPathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_stepProcess_asPathway = fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory.getPathway(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForStepProcess_asPathway;
						synchronized (listeners) {
							consumersForStepProcess_asPathway = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForStepProcess_asPathway.iterator();iter.hasNext();){
							BiochemicalPathwayStepListener listener=(BiochemicalPathwayStepListener)iter.next();
							listener.stepProcessRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.this,_stepProcess_asPathway);
						}
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
							BiochemicalPathwayStepListener listener=(BiochemicalPathwayStepListener)iter.next();
							listener.evidenceRemoved(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.this,_evidence);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(stepConversionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (stepConversion != null && stepConversion.resource().equals(resource))
						stepConversion = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						BiochemicalPathwayStepListener listener=(BiochemicalPathwayStepListener)iter.next();
						listener.stepConversionChanged(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(stepDirectionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(literal.getValue(),"java.lang.String");
				if (stepDirection != null && stepDirection.equals(obj))
					stepDirection = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						BiochemicalPathwayStepListener listener=(BiochemicalPathwayStepListener)iter.next();
						listener.stepDirectionChanged(fr.curie.BiNoM.pathways.biopax.BiochemicalPathwayStepImpl.this);
					}
				}
				return;
			}
		}

	//}
	


}