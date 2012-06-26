/*
   BiNoM Cytoscape Plugin
   Copyright (C) 2006-2007 Curie Institute, 26 rue d'Ulm, 75005 Paris - FRANCE

   BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

/*
  BiNoM authors:
	Andrei Zinovyev : http://www.ihes.fr/~zinovyev
	Eric Viara : http://www.sysra.com/viara
	Laurence Calzone :	http://leibniz.biol.vt.edu/people/laurence/laurence.html
 */

package fr.curie.BiNoM.pathways.utils;


import java.util.*;
import java.util.regex.*;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import com.ibm.adtech.jastor.JastorException;
import com.ibm.adtech.jastor.JastorInvalidRDFNodeException;

/**
 * BioPAX Naming Service. Used to construct short, unique and 'meaningfull' names for BioPAX objects
 * @author Andrei Zinovyev
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class BioPAXNamingService {

	/**
	 * Map from complete uri to the names generated 
	 */
	private HashMap uri2name = new HashMap();
	
	/**
	 * Map from complete uri to the ids generated 
	 */
	private HashMap uri2id = new HashMap();
	
	/**
	 * Map from ids to the uris 
	 */
	private HashMap id2uri = new HashMap();
	
	/**
	 * Map from the names to the uris 
	 */
	private HashMap name2uri = new HashMap();
	
	/**
	 * Names of utilities
	 */
	public HashMap genericUtilityName = new HashMap();

	/**
	 * Map of URI to all ModificationFeature terms (phosphorylation events)
	 */
	private HashMap<String,String> uri2ModificationFeatureName = new HashMap<String, String>();
	
	/**
	 * Map of URI to all SequenceSite positions
	 */
	private HashMap<String, Integer> uri2SequenceSitePosition = new HashMap<String, Integer>();
	
	private HashMap<String, Entity> listOfComplexComponents = new HashMap<String, Entity>();

	
	
	/**
	 * Empty constructor
	 */
	public BioPAXNamingService() {
	}

	/**
	 * Constructor calling the generateNames function.
	 * 
	 * @param biopax BioPAX object
	 * @param verbose boolean for printing out objects URI/names
	 * @throws Exception
	 */
	public BioPAXNamingService(BioPAX biopax, boolean verbose) throws Exception {
		generateNames(biopax, verbose);
	}

	/**
	 * Constructor calling the constructor above (?!??), calling generateNames without printing anything. 
	 * 
	 * @param biopax
	 * @throws Exception
	 */
	public BioPAXNamingService(BioPAX biopax) throws Exception {
		this(biopax, false);
	}

	/**
	 * Function for generating names for BioPAX objects from a file.
	 * 
	 * @param biopax BioPAX object
	 * @param verbose if true then some log is provided
	 * @throws Exception
	 */
	public void generateNames(BioPAX biopax, boolean verbose) throws Exception{
		
		// First, name reference entities
		// Selected utility classes--------------------------------------------
		List el = biopax_DASH_level3_DOT_owlFactory.getAllProteinReference(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));

		el = biopax_DASH_level3_DOT_owlFactory.getAllDnaReference(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllRnaReference(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));

		el = biopax_DASH_level3_DOT_owlFactory.getAllSmallMoleculeReference(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));

		el = biopax_DASH_level3_DOT_owlFactory.getAllEntityReference(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));

		
		// Populate list of complex modifications
		
		List listOfComplexes = biopax_DASH_level3_DOT_owlFactory.getAllComplex(biopax.model);
		for(int i=0;i<listOfComplexes.size();i++){ 
			Complex compl = (Complex)listOfComplexes.get(i);
			Iterator<PhysicalEntity> it = compl.getComponent();
			while(it.hasNext()){
				PhysicalEntity pent = it.next();
				listOfComplexComponents.put(pent.uri(), pent);
		}}
		
		/*
		 * Populate internal maps to ModificationFeature and SequenceSite objects
		 * 
		 * These maps will be used to create names for phosphorylation sites
		 * 
		 */
		el = biopax_DASH_level3_DOT_owlFactory.getAllModificationFeature(biopax.model);
		for (int i=0;i<el.size();i++) {
			ControlledVocabulary cv = ((ModificationFeature) el.get(i)).getModificationType();
			Iterator it = cv.getTerm();
			String term = null;
			while(it.hasNext()) {
				term = (String) it.next();
			}
			this.uri2ModificationFeatureName.put(((ModificationFeature) el.get(i)).uri(), term);
		}
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllSequenceSite(biopax.model);
		for (int i=0;i<el.size();i++) {
			SequenceSite ss = (SequenceSite) el.get(i);
			int pos = ss.getSequencePosition();
			this.uri2SequenceSitePosition.put(ss.uri(), pos);
		}

		
		/*
		 * 
		 *  Entities------------------------------------------------------------
		 *  
		 */
		el = biopax_DASH_level3_DOT_owlFactory.getAllProtein(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllCatalysis(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllModulation(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllTransportWithBiochemicalReaction(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllBiochemicalReaction(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));

		el = biopax_DASH_level3_DOT_owlFactory.getAllTemplateReaction(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));

		el = biopax_DASH_level3_DOT_owlFactory.getAllTemplateReactionRegulation(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllComplexAssembly(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllTransport(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));

		el = biopax_DASH_level3_DOT_owlFactory.getAllControl(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllConversion(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		
		//PhysicalInteraction (L2) replaced by MolecularInteraction (L3)
		el = biopax_DASH_level3_DOT_owlFactory.getAllMolecularInteraction(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllInteraction(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));

		el = biopax_DASH_level3_DOT_owlFactory.getAllGeneticInteraction(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));

		el = biopax_DASH_level3_DOT_owlFactory.getAllPathway(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllGene(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllComplex(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllDna(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllDnaRegion(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));

		
		el = biopax_DASH_level3_DOT_owlFactory.getAllRna(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllRnaRegion(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllSmallMolecule(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllPhysicalEntity(biopax.model);
		for(int i=0;i<el.size();i++) putEntity((Entity)el.get(i));
		

		/*
		 *
		 * Now Utility BioPAX classes 
		 * 
		 */
		el = biopax_DASH_level3_DOT_owlFactory.getAllChemicalStructure(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		//Confidence (L2) replaced by Score (L3)
		el = biopax_DASH_level3_DOT_owlFactory.getAllScore(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllExperimentalForm(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		/*
		 * ExternalReferenceUtility (L2) suppressed in L3. Direct access to
		 * subclasses Xref, ControlledVocabulary, Biosource and Provenance
		 * 
		 */
		el = biopax_DASH_level3_DOT_owlFactory.getAllXref(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllControlledVocabulary(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllBioSource(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllProvenance(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		// subclasses of ControlledVocabulary often used
		el = biopax_DASH_level3_DOT_owlFactory.getAllCellularLocationVocabulary(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllSequenceModificationVocabulary(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));

		el = biopax_DASH_level3_DOT_owlFactory.getAllRelationshipTypeVocabulary(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllPublicationXref(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllRelationshipXref(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllUnificationXref(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllPathwayStep(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllBiochemicalPathwayStep(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllEntityFeature(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));

		// classes related to EntityFeature, Fragment, Modification, Binding, CovalentBinding
		el = biopax_DASH_level3_DOT_owlFactory.getAllFragmentFeature(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));

		el = biopax_DASH_level3_DOT_owlFactory.getAllModificationFeature(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));

		el = biopax_DASH_level3_DOT_owlFactory.getAllBindingFeature(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));

		el = biopax_DASH_level3_DOT_owlFactory.getAllCovalentBindingFeature(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));

		
		el = biopax_DASH_level3_DOT_owlFactory.getAllSequenceInterval(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllSequenceSite(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));
		
		el = biopax_DASH_level3_DOT_owlFactory.getAllSequenceLocation(biopax.model);
		for(int i=0;i<el.size();i++) putUtilityClass((UtilityClass)el.get(i));

		if(verbose){
			el = biopax_DASH_level3_DOT_owlFactory.getAllGene(biopax.model);
			for(int i=0;i<el.size();i++) printEntityName((Entity)el.get(i));
			el = biopax_DASH_level3_DOT_owlFactory.getAllGeneticInteraction(biopax.model);
			for(int i=0;i<el.size();i++) printEntityName((Entity)el.get(i));
			el = biopax_DASH_level3_DOT_owlFactory.getAllTemplateReaction(biopax.model);
			for(int i=0;i<el.size();i++) printEntityName((Entity)el.get(i));
			el = biopax_DASH_level3_DOT_owlFactory.getAllTemplateReactionRegulation(biopax.model);
			for(int i=0;i<el.size();i++) printEntityName((Entity)el.get(i));
			
			el = biopax_DASH_level3_DOT_owlFactory.getAllProtein(biopax.model);
			for(int i=0;i<el.size();i++) printEntityName((Entity)el.get(i));
			el = biopax_DASH_level3_DOT_owlFactory.getAllComplex(biopax.model);
			for(int i=0;i<el.size();i++) printEntityName((Entity)el.get(i));
			el = biopax_DASH_level3_DOT_owlFactory.getAllDna(biopax.model);
			for(int i=0;i<el.size();i++) printEntityName((Entity)el.get(i));
			el = biopax_DASH_level3_DOT_owlFactory.getAllRna(biopax.model);
			for(int i=0;i<el.size();i++) printEntityName((Entity)el.get(i));
			el = biopax_DASH_level3_DOT_owlFactory.getAllSmallMolecule(biopax.model);
			for(int i=0;i<el.size();i++) printEntityName((Entity)el.get(i));
			el = biopax_DASH_level3_DOT_owlFactory.getAllPathway(biopax.model);
			for(int i=0;i<el.size();i++) printEntityName((Entity)el.get(i));

			el = biopax_DASH_level3_DOT_owlFactory.getAllPhysicalEntity(biopax.model);
			for(int i=0;i<el.size();i++) printEntityName((Entity)el.get(i));
			el = biopax_DASH_level3_DOT_owlFactory.getAllEntityFeature(biopax.model);
			for(int i=0;i<el.size();i++) printUtilityClassName((UtilityClass)el.get(i));
			el = biopax_DASH_level3_DOT_owlFactory.getAllPathwayStep(biopax.model);
			for(int i=0;i<el.size();i++) printUtilityClassName((UtilityClass)el.get(i));
		}
	}

	/**
	 * Adds an entity for naming
	 * @param e
	 * @throws Exception
	 */
	public void putEntity(Entity e) throws Exception{
		String id = createEntityId(e);
		String name = createEntityName(e);

		putID(e.uri(),id);
		putName(e.uri(),name);
	}

	/**
	 * Adds an utilityClass for naming
	 */
	public void putUtilityClass(UtilityClass e) throws Exception{
		String id = createUtilityId(e);
		String name = createUtilityName(e);
		putID(e.uri(),id);
		putName(e.uri(),name);
		if(!genericUtilityName.containsKey(e.uri()))
			genericUtilityName.put(e.uri(),name);
	}

	public void printEntityName(Entity e){
		String nm = e.getClass().getName();
		nm = Utils.replaceString(nm,"fr.curie.BiNoM.pathways.biopax.","");
		nm = Utils.replaceString(nm,"Impl","");
		System.out.println(nm+"\t"+getNameByUri(e.uri())+"\t\t\t"+getIdByUri(e.uri()));
	}
	
	public void printUtilityClassName(UtilityClass e){
		String nm = e.getClass().getName();
		nm = Utils.replaceString(nm,"fr.curie.BiNoM.pathways.biopax.","");
		nm = Utils.replaceString(nm,"Impl","");
		System.out.println(nm+"\t"+getNameByUri(e.uri())+"\t\t\t"+getIdByUri(e.uri()));
	}

	public void putID(String uri, String id){
		String iid = (String)uri2id.get(uri);
		if(iid==null){
			uri2id.put(uri,id);
			uri2id.put(Utils.cutUri(uri),id);

			String iuri = (String)id2uri.get(id);
			if(iuri==null){
				id2uri.put(id,uri);
			}
			else{
				System.out.println("BADLY CREATED ID: "+id);
				id2uri.put(generateUniqueID(id,id2uri),uri);
			}

		}
	}

	/**
	 * Adds a (uri,name) pair 
	 * 
	 * @param uri
	 * @param name
	 */
	public void putName(String uri, String name){
		String iname = (String)uri2name.get(uri);
		if(iname==null){
			uri2name.put(uri,name);
			uri2name.put(Utils.cutUri(uri),name);

			String iuri = (String)name2uri.get(name);
			if(iuri==null){
				name2uri.put(name,uri);
			}
			else{
				String prevName = (String)uri2name.get(iuri);
				String prevAdd = "["+(String)uri2id.get(iuri)+"]";
				if(!prevName.endsWith(prevAdd))
					prevName += prevAdd;
				name += "["+(String)uri2id.get(uri)+"]";
				name2uri.put(prevName,iuri);
				name2uri.put(name,uri);
				uri2name.put(iuri,prevName);
				uri2name.put(Utils.cutUri(iuri),prevName);
				uri2name.put(uri,name);
				uri2name.put(Utils.cutUri(uri),name);
			}
		}
	}
	
	/**
	 * Adds # symbols until the id is unique
	 * @param id
	 * @param hm
	 * @return
	 */
	public String generateUniqueID(String id, HashMap hm){
		String r = id;
		for(int i=1;i<100000;i++){
			if(!hm.containsKey(id+"#"+i)){
				r = id+"#"+i; break;
			}
		}
		return r;
	}

	/**
	 * Used to get name of the thing with uri
	 * @param uri
	 * @return
	 */
	public String getNameByUri(String uri){
		String s = (String)uri2name.get(uri);
		if(s==null) System.out.println("WARNING!!! BIOPAX NAMING SYSTEM FAULT! Cannot get name for "+uri);
		//else
			//s = Utils.correctName(s);
		return s;
	}
	
	/**
	 * Used to get id of the thing with uri
	 * @param uri
	 * @return Id as string
	 */
	public String getIdByUri(String uri){
		String s = (String)uri2id.get(uri);
		if(s==null) System.out.println("WARNING!!! BIOPAX NAMING SYSTEM FAULT! Cannot get id for "+uri);
		return s;
	}
	
	/**
	 * Return the URI given an ID
	 * 
	 * @param String id
	 * @return String URI
	 */
	public String getUriById(String id){
		String s = (String)id2uri.get(id);
		if(s==null) System.out.println("WARNING!!! BIOPAX NAMING SYSTEM FAULT! Cannot get uri for "+id);
		return s;
	}
	
	/**
	 * Return the URI for a given name
	 * 
	 * @param String name
	 * @return String URI
	 */
	public String getUriByName(String name){
		String s = (String)name2uri.get(name);
		if(s==null) System.out.println("WARNING!!! BIOPAX NAMING SYSTEM FAULT! Cannot get uri for "+name);
		return s;
	}

	/**
	 * Creates a name for entity pe
	 * @param Entity pe
	 * @return String entity name
	 * @throws Exception
	 */
	public String createEntityName(Entity pe) throws Exception{
		
		String name = "";
		
		if (pe instanceof Complex) {
			name = createNameForComplex((Complex) pe);
		}
		else if (pe instanceof Protein) {
			
			//if(((Protein) pe).getEntityReference()!=null)if(!listOfComplexComponents.containsKey(pe.uri())){
			if(((Protein) pe).getEntityReference()!=null){
				name = getShortestName(((Protein) pe).getEntityReference().getName());
			}else{
				name = getShortestName(pe.getName());
			}
			//System.out.println("Protein "+pe.uri()+"\t"+name);
			// add ModificationFeatures such as phosphorylations.
			Iterator it = ((Protein)pe).getFeature();
			while(it.hasNext()) {
				EntityFeature ef = (EntityFeature) it.next();
				String str = this.getPhosphorylationSiteName(ef);
				if (str != null) {
					name += "|" + str;
				}
				//System.out.println("\t\tFeature:"+name);
			}
			
			// add cellular compartment to the name, remove compartment string if already there
			// handle UCSD signaling-gateway strange biopax3 format causing invalidRDF errors
			try {
				name = addCellularCompartmentName(name, ((Protein) pe).getCellularLocation());
			}
			catch (JastorInvalidRDFNodeException e) {
				name += "@";
			}
		}
		else if (pe instanceof SmallMolecule) {
			name = getShortestName(pe.getName());
			name = addCellularCompartmentName(name, ((SmallMolecule) pe).getCellularLocation());
		}
		else if (pe instanceof Rna){
			if(((Rna) pe).getEntityReference()!=null)if(!listOfComplexComponents.containsKey(pe.uri())){
				name = getShortestName(((Rna)pe).getEntityReference().getName());
			}else{
				name = getShortestName(pe.getName());
			}
			name = addCellularCompartmentName(name, ((Rna) pe).getCellularLocation());
		}
		else if (pe instanceof Dna){
			if(((Dna) pe).getEntityReference()!=null)if(!listOfComplexComponents.containsKey(pe.uri())){
				name = getShortestName(((Dna)pe).getEntityReference().getName());
			}else{
				name = getShortestName(pe.getName());
			}
			name = addCellularCompartmentName(name, ((Rna) pe).getCellularLocation());
		}
		else if (pe instanceof PhysicalEntity) {
			name = getShortestName(pe.getName());
			name = addCellularCompartmentName(name, ((PhysicalEntity) pe).getCellularLocation());
		}
		else {
			name = getShortestName(pe.getName());
		}
		
		// if name is empty or too long, take URI as name
		if((name==null)||(name.trim().equals(""))||(name.length()>100))
			name = Utils.cutUri(pe.uri());
		
		// add prefix for specific types
		if(pe instanceof Dna)
			name = "g"+name;
		if(pe instanceof Rna)
			name = "r"+name;

		return name;
	}

	/**
	 * Creates entity id 
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public String createEntityId(Entity e)  throws Exception{
		String id = "";
		id = Utils.cutUri(e.uri());
		return id;
	}
	
	
	/**
	 * Creates a name for utilityClass object
	 * 
	 * @param UtilityClass e
	 * @return String name
	 * @throws Exception
	 */
	public String createUtilityName(UtilityClass e)  throws Exception{
		
		String name = null;
		if((e instanceof ProteinReference)||(e instanceof RnaReference)||(e instanceof DnaReference)||(e instanceof SmallMoleculeReference)||(e instanceof EntityReference)){
			String s = getShortestName(((EntityReference)e).getName());
			if(!s.equals("")) name = s;
		}
		
		if(e instanceof ControlledVocabulary){
			String s = getVocabularyTerm(((ControlledVocabulary)e).getTerm());
			if(!s.equals("")) name = s;
		}
		if(e instanceof Xref){
			Xref px = (Xref)e;
			if((px.getDb()!=null)&&(px.getId()!=null))
				name = px.getId()+"@"+px.getDb();
		}
		if(e instanceof PublicationXref){
			PublicationXref px = (PublicationXref)e;
			String s = "";
			if((px.getDb()!=null)&&(px.getId()!=null))
				s = px.getId()+"@"+px.getDb();
			String sa = "";
			if(px.getAuthor()!=null){
				Iterator it = px.getAuthor();
				int k = 0;
				while(it.hasNext()){
					String aname = (String)it.next();
					int ic = aname.indexOf(",");
					if(ic>0)
						aname = aname.substring(0,ic);
					sa+=aname+" "; k++;
					if(k>2) break;
				}
			}
			if(px.getSource()!=null)
				if(!getVocabularyTerm(px.getSource()).equals(""))
					sa+="/"+getVocabularyTerm(px.getSource())+" ";
			if(px.getYear()!=null)
				if(!px.getYear().equals(""))
					sa+="/"+px.getYear();
			if(s.equals(""))
				name = sa;
			else if(!sa.equals(""))
				name = s + "/" + sa;
		}

		/*
		 * ModificationFeature = covalent modif feature, such as post-translational modification
		 */
		if (e instanceof ModificationFeature) {
			ModificationFeature modif = (ModificationFeature) e;
			if (modif.getModificationType() != null) {
				ControlledVocabulary cv = modif.getModificationType();
				Iterator it = cv.getTerm();
				if (it != null)
					name = BioPAXNamingService.getVocabularyTerm(it);
			}
		}
		

		// last option, create the name from URI
		if((name==null)||(name.equals("")))
			name = createUtilityId(e);
		name = Utils.correctName(name);
		return name;
	}
	
	/**
	 * Creates an id for utilityClass
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public static String createUtilityId(UtilityClass e) throws Exception{
		String id = "";
		id = Utils.cutUri(e.uri());
		return id;
	}
	
		
	/**
	 * Create a name for a Complex.
	 * 
	 * In this case the stoichiometric coefficients for a given physical entity
	 * represent how many times the entity is present in the complex. It is
	 * encoded as a double but always represent an integer value.
	 * 
	 * @param Complex object.
	 * @return String name for the complex.
	 */
	public String createNameForComplex(Complex co) {
		
		String name = "";

		try {
			Iterator it = co.getComponentStoichiometry();
			
			if (it.hasNext()) {
				ArrayList<String> names = new ArrayList<String>();
				while (it.hasNext()) {
					Stoichiometry cs = (Stoichiometry) it.next();
					
					PhysicalEntity pe = cs.getPhysicalEntity();
					String peName = (String) uri2name.get(pe.uri());
					if (peName != null) {
						// remove compartment info if any
						String tk[] = peName.split("@");
						int coef = cs.getStoichiometricCoefficient().intValue();
						for (int i=0;i<coef;i++)
							names.add(tk[0]);
					}
					else {
						// entity is not in uri2name list
						String n = getShortestName(pe.getName());
						int coef = cs.getStoichiometricCoefficient().intValue();
						for (int i=0;i<coef;i++)
							names.add(n);
					}
				}
				
				Collections.sort(names);
				for (String n : names)
					name += n + ":";
				name = name.substring(0,name.length()-1);
			}
			// we have 0 componentStoichiometry elements look into components
			else {
				ArrayList<String> names = new ArrayList<String>();
				Iterator itco = co.getComponent();
				if (itco.hasNext()) {
					while(itco.hasNext()) {
						PhysicalEntity pe = (PhysicalEntity) itco.next();
						String peName = (String) uri2name.get(pe.uri());
						if (peName != null) {
							// remove compartment info if any
							String tk[] = peName.split("@");
							names.add(tk[0]);
						}
						else {
							// entity is not is uri2name list
							String n = getShortestName(pe.getName());
							names.add(n);
						}
					}
					
					Collections.sort(names);
					for (String n : names)
						name += n + ":";
					name = name.substring(0,name.length()-1);
				}
				else {
					// no components, use name of the complex
					name = getShortestName(co.getName());
				}
			}
		}
		catch (JastorException e){
			System.out.println("WARNING: a JastorException occurred while getting the components of a complex.");
			e.printStackTrace();
		}
		
		try {
			name = addCellularCompartmentName(name, co.getCellularLocation());
		}
		catch (JastorInvalidRDFNodeException e) {
			name += "@";
		}
		catch (JastorException e) {
			e.printStackTrace();
			name += "@";
		}
		
		//name = Utils.correctName(name);
		return(name);
	}
	
	
	/**
	 * Return the first occurrence of a sorted Controlled Vocabulary list
	 * 
	 * @param ControlledVocabullary Iterator
	 * @return String term
	 */
	public static String getVocabularyTerm(Iterator it){
		Vector v = new Vector();
		while(it.hasNext()){
			v.add(it.next());
		}
		Collections.sort(v);
		if(v.size()>0)
			return (String)v.get(0);
		else
			return "";
	}

	/**
	 * Return the shortest string from a given list passed as an Iterator.
	 * 
	 * @param iterator of String objects.
	 * @return The shortest string.
	 */
	private String getShortestName(Iterator it) {
		String ret = "";
		int len = 100000;
		while(it.hasNext()) {
			String n = (String) it.next();
			if (n.length() < len) {
				len = n.length();
				ret = n;
			}
		}
		ret = Utils.correctName(ret);
		return(ret);
	}

	
	/**
	 * Given an EntityFeature corresponding to a ModificationFeature, return the formatted name
	 * of the modification (type + position).
	 * 
	 * Some level3 files (eg human reactome) do not encode the position of the feature in the 
	 * SequenceSite object, as it should be, but in the URI. This probably due to some lazy conversion
	 * from level2 to level3. In those case, take the URI as the name for the feature.
	 * 
	 * @param ef EntityFeature.
	 * @return String if there is a corresponding modification feature, else null.
	 */
	private String getPhosphorylationSiteName(EntityFeature ef) throws JastorException {

		String modif_name  = uri2ModificationFeatureName.get(ef.uri());

		if (modif_name != null) {
			Pattern pat = Pattern.compile("_at_\\d+");
			String short_uri = Utils.cutUri(ef.uri());
			Matcher m = pat.matcher(short_uri);
			if (m.find()==true) {
				modif_name = short_uri;
			}
			else {
				ArrayList<SequenceLocation> fl = new ArrayList<SequenceLocation>();
				// catch UCSD signaling gateway biopax3 format errors
				try {
					Iterator it = ef.getFeatureLocation();

					while(it.hasNext()) {
						fl.add((SequenceLocation) it.next());
					}

					if (fl.size() > 0 && uri2SequenceSitePosition.get(fl.get(0).uri()) != null) {
						modif_name += "_at_" + Integer.toString(uri2SequenceSitePosition.get(fl.get(0).uri()));
					}
//					else {
//						modif_name += "_at_unknown"; 
//					}
				}
				catch (JastorInvalidRDFNodeException e) {
//					modif_name += "_at_unknown";
				}
			}
			modif_name = Utils.correctName(modif_name);
		}
		return(modif_name);
	}
	
	/**
	 * Add a cellular compartment to an entity name.
	 * 
	 * @param name entity name.
	 * @param cv ControlledVocabulary cellular compartment.
	 * @return string entity name.
	 */
	private String addCellularCompartmentName(String name, ControlledVocabulary cv) {
		name.trim();
		if (cv != null) {
			try {
				String compartment = createUtilityName(cv);
				if (!compartment.equals(""))
					if (name.indexOf(compartment) < 0)
						name += "@" + compartment;
					else {
						Utils.replaceString(name, compartment, "");
						name += "@" + compartment;
					}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			name += "@";
		
		return name;
	}

}
