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

package fr.curie.BiNoM.pathways;

import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import java.io.*;
import java.util.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.shared.*;

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

/**
 * <p>Converts BioPAX (represented by BioPAX object) to SBML (represented by org.sbml.x2001.ns.celldesigner.SbmlDocument object)
 */
@SuppressWarnings("unchecked")
public class BioPAXToSBMLConverter {

	/**
	 * Result of conversion
	 */
    public SbmlDocument sbmlDoc = null;
	/**
	 * Input for conversion
	 */
    public BioPAX biopax = new BioPAX();
	/**
	 * BioPAX Naming service used for conversion 
	 */
    public BioPAXNamingService bpnm = new BioPAXNamingService();

    /**
	 * Check to show compartment information in species names 
	 */
    public static boolean alwaysMentionCompartment = false;
    /**
	 * not used 
	 */
    public static boolean useBiopaxModelOntology = false;

    /**
	 * Check off if you want to use compartment id and not name 
	 */
    public static boolean considerCompartmentNameRatherThanId = true;

    /**
	 * Map from cut uris (after # separator) to BioPAXSpecies
	 */
    public HashMap independentSpeciesIds = new HashMap();
    /**
	 * Auxiliary map
	 */
    public HashMap independentSpecies = new HashMap();
    /**
	 * Map from cut uris (after # separator) to BioPAXSpecies
	 * included into entities (these species group complex
	 * components, for example)
	 */
    public HashMap includedSpecies = new HashMap();
    /**
	 * Map from cut uris (after # separator) to 'complex' entities
	 */
    public HashMap complexList = new HashMap();
    /**
	 * Map from cut uris (after # separator) to 'protein' entities
	 */
    public HashMap proteinList = new HashMap();
    /**
	 * Map from cut uris (after # separator) to 'dna' entities
	 */
    public HashMap dnaList = new HashMap();
    /**
	 * Map from cut uris (after # separator) to 'rna' entities
	 */
    public HashMap rnaList = new HashMap();
    /**
	 * Map from cut uris (after # separator) to 'smallMolecule' entities
	 */
    public HashMap smallMoleculeList = new HashMap();
    /**
	 * List of compartments (represented as openControlledVocabulary objects)
	 */
    public Vector compartments = null;
    //HashMap compartmentsHash = new HashMap();
    /**
	 * Map from BioPAX conversion (cutted) uris to 
	 * SBML reactions (represented by ReactionDocument.Reaction objects)
	 */
    public HashMap reactions = new HashMap();
    /**
	 * Map from cutted uris (after # separator) to BioPAX 'conversion' objects 
	 */
    public HashMap bioPAXreactions = new HashMap();
    /**
	 * Map from cutted uris (after # separator) to BioPAX 'control' objects 
	 */
    public HashMap controls = new HashMap();
    /**
	 * Map from SBML species ids to SpeciesDocument.Species objects 
	 */
    public HashMap species = new HashMap();

    
    /**
	 * Represents BioPAX physicalEntityParticipants grouped into 
	 * distinctive chemical species (entity, characterized by
	 * a definite location and a definite modification). 
	 */
    public class BioPAXSpecies {
    	public String id;
    	public String name;
    	/**
    	 * Type of BioPAX object linked to the species (protein, complex, dna)
    	 */
    	public String type;
    	/**
    	 * List of identical (from the point of view of SBML) physicalEntityParticipants 
    	 */
    	public Vector sinonymSpecies = new Vector();
    	public String toString() {
    		String desc = "";
    		desc += name+"\n";
    		for(int i=0;i<sinonymSpecies.size();i++){
    			//physicalEntityParticipant prt = (physicalEntityParticipant)sinonymSpecies.elementAt(i);
    			//TODO check this change
    			PhysicalEntity prt = (PhysicalEntity) sinonymSpecies.elementAt(i);
    			int k = prt.uri().indexOf("#");
    			desc+= "\t"+prt.uri().substring(k+1,prt.uri().length())+"\n";
    		}
    		return desc;
    	}
    }

    /**
     * Performs actual BioPAX->SBML conversion
     * Requires this.biopax object to be specified  
     */
    public void populateSbml() throws Exception {
    	
    	bpnm.generateNames(biopax,false);
    	findIndependentSpecies();
    	compartments = getAllCompartments();

    	sbmlDoc = SbmlDocument.Factory.newInstance();
    	SbmlDocument.Sbml sbml = sbmlDoc.addNewSbml();
    	sbml.setLevel("2");
    	sbml.setVersion("1");
    	ModelDocument.Model m = sbml.addNewModel();
    	ListOfSpeciesDocument.ListOfSpecies spl = m.addNewListOfSpecies();
    	ListOfCompartmentsDocument.ListOfCompartments cml = m.addNewListOfCompartments();
    	ListOfReactionsDocument.ListOfReactions lr = m.addNewListOfReactions();

    	// get Compartments
    	CompartmentDocument.Compartment cmp = cml.addNewCompartment();
    	cmp.setId("default");
    	for(int i=0;i<compartments.size();i++){
    		ControlledVocabulary voc = (ControlledVocabulary)compartments.elementAt(i);
    		cmp = cml.addNewCompartment();
    		cmp.setId("c"+(i+1));
    		XmlAnySimpleType nm = cmp.addNewName();
    		if(voc.getTerm().hasNext())
    			nm.setStringValue((String)voc.getTerm().next());
    		else
    			nm.setStringValue(Utils.cutUri(voc.uri()));
    	}
    	
    	// get Species
    	Set keys = independentSpeciesIds.keySet();
    	Iterator it = keys.iterator();
    	HashMap speciesNames = new HashMap();
    	while(it.hasNext()){
    		String id = (String)it.next();
    		// get the participant as BioPAXSpecies object
    		BioPAXSpecies part = (BioPAXSpecies)independentSpeciesIds.get(Utils.cutUri(id));
    		// get the first identical species, as a PEP, now converted to a PhysicalEntity
    		PhysicalEntity pep = (PhysicalEntity)part.sinonymSpecies.elementAt(0);

    		// test if the PEP as at least one physical entity defined
    		//if(pep.getPHYSICAL_DASH_ENTITY()==null)
    		//	System.out.println("WARNING: PHYSICAL_DASH_ENTITY is null for "+pep.uri());
    		
    		// comment string
    		String comm = "";
    		//if(pep.getPHYSICAL_DASH_ENTITY()!=null){
    		if(pep!=null){
    			// format comments as multi-lines string
    			comm = makeComment(pep.getComment());
    			SpeciesDocument.Species sp = spl.addNewSpecies();    
    			if(comm.length()>0){
    				NotesDocument.Notes not = sp.addNewNotes();
    				Utils.setNoteHtmlBodyValue(not,comm);
    			}
    			sp.setId(GraphUtils.correctId(part.id));
    			species.put(sp.getId(),sp);
    			XmlAnySimpleType nm = sp.addNewName();
    			nm.setStringValue(part.name);
    			// add controlled vocabulary terms to sbml obj.
    			//openControlledVocabulary voc = (openControlledVocabulary)pep.getCELLULAR_DASH_LOCATION();
    			ControlledVocabulary voc = (ControlledVocabulary)pep.getCellularLocation();
    			if(voc!=null){
    				int kc = compartments.indexOf(voc);
    				if(kc!=-1)
    					sp.setCompartment("c"+(kc+1));
    			}
    			if(speciesNames.get(sp.getName())!=null){
    				System.out.println("DUPLICATE SPECIES NAME! : "+sp.getName().getStringValue());
    			}
    			speciesNames.put(sp.getName(),sp);
    		}
    	}
    	
    	// get Reactions
    	HashMap allReactions = new HashMap();
    	List reactionList = biopax_DASH_level3_DOT_owlFactory.getAllTransportWithBiochemicalReaction(biopax.model);
    	it = reactionList.iterator();
    	while(it.hasNext()){
    		TransportWithBiochemicalReaction trb = (TransportWithBiochemicalReaction)it.next();
    		allReactions.put(trb.uri(),trb);
    		addConversionReaction(trb,lr);
    	}
    	reactionList = biopax_DASH_level3_DOT_owlFactory.getAllTransport(biopax.model);
    	it = reactionList.iterator();
    	while(it.hasNext()){
    		Transport tr = (Transport)it.next();
    		allReactions.put(tr.uri(),tr);
    		addConversionReaction(tr,lr);
    	}
    	reactionList = biopax_DASH_level3_DOT_owlFactory.getAllComplexAssembly(biopax.model);
    	it = reactionList.iterator();
    	while(it.hasNext()){
    		ComplexAssembly ca = (ComplexAssembly)it.next();
    		allReactions.put(ca.uri(),ca);
    		addConversionReaction(ca,lr);
    	}
    	reactionList = biopax_DASH_level3_DOT_owlFactory.getAllBiochemicalReaction(biopax.model);
    	it = reactionList.iterator();
    	while(it.hasNext()){
    		BiochemicalReaction br = (BiochemicalReaction)it.next();
    		allReactions.put(br.uri(),br);
    		addConversionReaction(br,lr);
    	}

    	// From higher level
    	reactionList = biopax_DASH_level3_DOT_owlFactory.getAllConversion(biopax.model);
    	it = reactionList.iterator();
    	while(it.hasNext()){
    		Conversion conv = (Conversion)it.next();
    		if(allReactions.get(conv.uri())==null){
    			allReactions.put(conv.uri(),conv);
    			addConversionReaction(conv,lr);
    		}
    	}

    	// Let us try to add also physicalInteractions and interactions
    	reactionList = biopax_DASH_level3_DOT_owlFactory.getAllMolecularInteraction(biopax.model);
    	it = reactionList.iterator();
    	while(it.hasNext()){
    		Interaction inter = (Interaction)it.next();
    		if(allReactions.get(inter.uri())==null){
    			allReactions.put(inter.uri(),inter);
    			addInteractionReaction(inter,lr);
    		}
    	}
    	reactionList = biopax_DASH_level3_DOT_owlFactory.getAllInteraction(biopax.model);
    	it = reactionList.iterator();
    	while(it.hasNext()){
    		Interaction inter = (Interaction)it.next();
    		if(allReactions.get(inter.uri())==null){
    			allReactions.put(inter.uri(),inter);
    			addInteractionReaction(inter,lr);
    		}
    	}

    	// Now process controls
    	List catlist  = biopax_DASH_level3_DOT_owlFactory.getAllCatalysis(biopax.model);
    	it = catlist.iterator();
    	while(it.hasNext()){
    		Catalysis ct = (Catalysis)it.next();
    		allReactions.put(ct.uri(),ct);
    		addControl(ct);
    	}
    	List modlist  = biopax_DASH_level3_DOT_owlFactory.getAllModulation(biopax.model);
    	it = modlist.iterator();
    	while(it.hasNext()){
    		Modulation ct = (Modulation)it.next();
    		//System.out.println("Adding modulation: "+ct.uri());
    		allReactions.put(ct.uri(),ct);
    		addControl(ct);
    	}

    	reactionList = biopax_DASH_level3_DOT_owlFactory.getAllControl(biopax.model);
    	it = reactionList.iterator();
    	while(it.hasNext()){
    		Control contr = (Control)it.next();
    		if(allReactions.get(contr.uri())==null){
    			allReactions.put(contr.uri(),contr);
    			addControl(contr);
    		}
    	}


    	//AnnotationDocument.Annotation an = m.addNewAnnotation();


    } // end populateSBML function

    
    /**
     * Procedure of grouping physicalEntityParticipants into
     * BioPAXSpecies (chemical species)
     */
    public void findIndependentSpecies() throws Exception {
    	
    	makeLists();
    	
    	// First process complexes
    	System.out.println("Finding included species...");
    	List allcompl = biopax_DASH_level3_DOT_owlFactory.getAllComplex(biopax.model);
    	Iterator allcompi = allcompl.iterator();
    	while(allcompi.hasNext()){
    		Complex compl = (Complex)allcompi.next();
    		complexList.put(Utils.cutUri(compl.uri()),compl);
    		Iterator comps = compl.getComponent();
    		while(comps.hasNext()){
    			//physicalEntityParticipant part = (physicalEntityParticipant)comps.next();
    			PhysicalEntity pe = (PhysicalEntity) comps.next();
    			addIncludedParticipant(pe);
    		}
    	}
    	
    	
    	/*
    	 * sequenceParticipants do not exist in Level 3
    	 */
    	/*
    	// Second, process all others
    	System.out.println("Finding species...");
    	List allsparts = biopax_DASH_level3_DOT_owlFactory.getAllsequenceParticipant(biopax.model);
    	Iterator allspartsi = allsparts.iterator();
    	while(allspartsi.hasNext()){
    		sequenceParticipant spart = (sequenceParticipant)allspartsi.next();
    		if(spart.getPHYSICAL_DASH_ENTITY()==null)
    			System.out.println("WARNING: PHYSICAL_DASH_ENTITY is null for "+Utils.cutUri(spart.uri()));
    		else
    			addParticipant(spart);
    	}
    	*/
    	
    	List l = biopax_DASH_level3_DOT_owlFactory.getAllPhysicalEntity(biopax.model);
    	for (int i=0;i<l.size();i++) {
    		addParticipant((PhysicalEntity)l.get(i));
    	}
    	
    	l = biopax_DASH_level3_DOT_owlFactory.getAllProtein(biopax.model);
    	for (int i=0;i<l.size();i++) {
    		addParticipant((PhysicalEntity)l.get(i));
    	}

    	l = biopax_DASH_level3_DOT_owlFactory.getAllDna(biopax.model);
    	for (int i=0;i<l.size();i++) {
    		addParticipant((PhysicalEntity)l.get(i));
    	}
    	
    	l = biopax_DASH_level3_DOT_owlFactory.getAllRna(biopax.model);
    	for (int i=0;i<l.size();i++) {
    		addParticipant((PhysicalEntity)l.get(i));
    	}

    	l = biopax_DASH_level3_DOT_owlFactory.getAllSmallMolecule(biopax.model);
    	for (int i=0;i<l.size();i++) {
    		addParticipant((PhysicalEntity)l.get(i));
    	}


    	// Now we will deal with the situation when some included species are erroneously used as reactants, products and controllers
    	// This is, unfortunately, the case for NatureNCI BioPAX dump (24 April 2008)
    	findIncludedSpeciesInvolvedInReactionsPatch();

    	Set keys = independentSpeciesIds.keySet();
    	Iterator kit = keys.iterator();
    	int i = 0;
    	int n = 0;
    	while(kit.hasNext()){
    		BioPAXSpecies part = (BioPAXSpecies)independentSpeciesIds.get(kit.next());
    		System.out.print(""+((i++)+1)+": "+part.toString());
    		n+=part.sinonymSpecies.size();
    	}

    	System.out.println("Total: "+(independentSpecies.size()+includedSpecies.size())+" participants, "+independentSpeciesIds.size()+" species, "+n+" synonyms, "+includedSpecies.size()+" included ");
    } // end findindependentspecies function
    
    
    private void findIncludedSpeciesInvolvedInReactionsPatch() throws Exception{
    	List allbr = biopax_DASH_level3_DOT_owlFactory.getAllBiochemicalReaction(biopax.model);
    	Iterator allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		BiochemicalReaction br = (BiochemicalReaction)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}
    	allbr = biopax_DASH_level3_DOT_owlFactory.getAllComplexAssembly(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		ComplexAssembly br = (ComplexAssembly)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}
    	allbr = biopax_DASH_level3_DOT_owlFactory.getAllTransport(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		Transport br = (Transport)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}
    	allbr = biopax_DASH_level3_DOT_owlFactory.getAllTransportWithBiochemicalReaction(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		TransportWithBiochemicalReaction br = (TransportWithBiochemicalReaction)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}
    	allbr = biopax_DASH_level3_DOT_owlFactory.getAllConversion(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		Conversion br = (Conversion)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}    	
    	allbr = biopax_DASH_level3_DOT_owlFactory.getAllCatalysis(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		Catalysis br = (Catalysis)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}
    	allbr = biopax_DASH_level3_DOT_owlFactory.getAllModulation(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		Modulation br = (Modulation)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}
    	allbr = biopax_DASH_level3_DOT_owlFactory.getAllControl(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		Control br = (Control)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}
    	allbr = biopax_DASH_level3_DOT_owlFactory.getAllMolecularInteraction(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		MolecularInteraction br = (MolecularInteraction)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}    	       	
    	allbr = biopax_DASH_level3_DOT_owlFactory.getAllInteraction(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		Interaction br = (Interaction)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}    	       	
    	
    }
    
    
    private void findIncludedSpeciesInvolvedInReactionsPatch(Interaction inter) throws Exception{
    	Vector pall = new Vector();
    	Vector p = Utils.getPropertyURIs(inter,"participant");
    	for(int i=0;i<p.size();i++) pall.add(p.get(i));
    	
    	// those properties are not defined for level3
    	/*p = Utils.getPropertyURIs(inter,"LEFT");
    	for(int i=0;i<p.size();i++) pall.add(p.get(i));
    	p = Utils.getPropertyURIs(inter,"RIGHT");
    	for(int i=0;i<p.size();i++) pall.add(p.get(i));
    	p = Utils.getPropertyURIs(inter,"CONTROLLER");
    	for(int i=0;i<p.size();i++) pall.add(p.get(i));
    	*/
    	Iterator parts = pall.iterator();
    	while(parts.hasNext()){
    		String uri = (String)parts.next();
    		String curi = Utils.cutUri(uri);
    		if(includedSpecies.get(curi)!=null){
    			String pname = (String)bpnm.genericUtilityName.get(uri);
    			PhysicalEntity part = (PhysicalEntity)includedSpecies.get(curi);
    			//String id = pname;
    			String id = GraphUtils.correctId(pname);
    			String tpe = getTypeForParticipant(part);
    			//System.out.println(part.uri());
    			BioPAXSpecies pt = (BioPAXSpecies)independentSpeciesIds.get(id);
    			if(pt==null){
    			    pt = new BioPAXSpecies();
    			    pt.id = id;
    			    pt.name = pname;
    			    pt.type = tpe;
    			    independentSpeciesIds.put(id,pt);
    			}
    			pt.sinonymSpecies.add(part);
    			independentSpecies.put(Utils.cutUri(part.uri()),pt);    			
    		}
    	}
    }

    
    /*
     * Analyze what to do with a physicalEntityParticipant
     * (add it to already specified species or create new species)
     */
    private void addParticipant(PhysicalEntity pe) throws Exception{
    	if(independentSpecies.get(Utils.cutUri(pe.uri()))==null)
    		if(includedSpecies.get(Utils.cutUri(pe.uri()))==null){
    			//String pname = (String)bpnm.genericUtilityName.get(pe.uri());
    			String pname = bpnm.getNameByUri(pe.uri());
    			String id = GraphUtils.correctId(pname);
    			String tpe = getTypeForParticipant(pe);
    			//System.out.println(pe.uri());
    			BioPAXSpecies pt = (BioPAXSpecies)independentSpeciesIds.get(id);
    			if(pt==null){
    				pt = new BioPAXSpecies();
    				pt.id = id;
    				pt.name = pname;
    				pt.type = tpe;
    				independentSpeciesIds.put(id,pt);
    			}
    			pt.sinonymSpecies.add(pe);
    			independentSpecies.put(Utils.cutUri(pe.uri()),pt);
    		}
    }

    /**
     * Adds included PhysicalEntity (such as the components of a complex)
     */
    public void addIncludedParticipant(PhysicalEntity pe) throws Exception{
	if(independentSpecies.get(Utils.cutUri(pe.uri()))==null)
	    if(includedSpecies.get(Utils.cutUri(pe.uri()))==null){
		includedSpecies.put(Utils.cutUri(pe.uri()),pe);
	    }
    }

    /*
     * Creates a list of compartments (openControlledVocabulary terms)
     */
    public Vector getAllCompartments() throws Exception{
    	Vector v = new Vector();
    	Set keys = independentSpeciesIds.keySet();
    	Iterator it = keys.iterator();
    	while(it.hasNext()){
    		String id = (String)it.next();
    		BioPAXSpecies part = (BioPAXSpecies)independentSpeciesIds.get(id);;
    		//TODO ebo todo here
    		PhysicalEntity pep = (PhysicalEntity)part.sinonymSpecies.elementAt(0);
    		ControlledVocabulary comp = pep.getCellularLocation();
    		if(comp!=null){
    			String compname = "";
    			if(comp.getTerm().hasNext())
    				compname = (String)(comp.getTerm().next());
    			else
    				compname = Utils.cutUri(comp.uri());
    			if(!considerCompartmentNameRatherThanId)
    			{
    				if(v.indexOf(comp)<0){
    					v.add(comp);
    				}}
    			else{
    				boolean found = false;
    				for(int i=0;i<v.size();i++){
    					ControlledVocabulary compv = (ControlledVocabulary)v.elementAt(i);
    					String compvname = "";
    					if(compv.getTerm().hasNext())
    						compvname = (String)(compv.getTerm().next());
    					else
    						compvname = Utils.cutUri(compv.uri());
    					if(compvname.equals(compname))
    						found = true;
    				}
    				if(!found)
    					v.add(comp);
    			}
    		}
    	}
    	for(int i=0;i<v.size();i++){
    		ControlledVocabulary comp = (ControlledVocabulary)v.elementAt(i);
    		String compt = "";
    		if(comp.getTerm().hasNext())
    			compt = (String)comp.getTerm().next();
    		else
    			compt = Utils.cutUri(comp.uri());
    		System.out.println("Compartment "+(i+1)+": "+compt);
    	}
    	return v;
    }

    public void addConversionReaction(Conversion conv, ListOfReactionsDocument.ListOfReactions lr) throws Exception{
    	Boolean spont = conv.getSpontaneous();
    	String dir = conv.getConversionDirection();
    	
    	Iterator reactants = null;
    	Iterator products = null;
    	int nreact = 0;
    	int nprods = 0;
    	//if((spont==null)||(spont.equals("L-R"))||(spont==false)){
    	if((spont==null)||(spont==false)||(dir.equals("REVERSIBLE"))||(dir.equals("PHYSIOL-LEFT-TO-RIGHT"))||(dir.equals("IRREVERSIBLE-LEFT-TO-RIGHT"))){
    		reactants = conv.getLeft();
    		products = conv.getRight();
    		while(reactants.hasNext()){
    			nreact++;
    			reactants.next();
    		}
    		while(products.hasNext()){
    			nprods++;
    			products.next();
    		}
    		reactants = conv.getLeft();
    		products = conv.getRight();
    	}else
    		if(spont.equals("R-L")){
    			reactants = conv.getRight();
    			products = conv.getLeft();
    			while(reactants.hasNext()){
    				nreact++;
    				reactants.next();
    			}
    			while(products.hasNext()){
    				nprods++;
    				products.next();
    			}
    			reactants = conv.getRight();
    			products = conv.getLeft();
    		}
    	if((reactants==null)||(products==null)){
    		System.out.println("UNKNOWN SPONTANEOUS VALUE:"+spont+" "+conv.uri());
    	}
    	ReactionDocument.Reaction reaction = lr.addNewReaction();
    	String comm = makeComment(conv.getComment());
    	if(conv instanceof Transport) comm+=" ; BIOPAX_REACTION_TYPE : transport";
    	if(conv instanceof BiochemicalReaction) comm+=" ; BIOPAX_REACTION_TYPE : biochemicalReaction";
    	if(conv instanceof TransportWithBiochemicalReaction) comm+=" ; BIOPAX_REACTION_TYPE : transportWithBiochemicalReaction";
    	if(conv instanceof ComplexAssembly) comm+=" ; BIOPAX_REACTION_TYPE : complexAssembly";
    	if(comm.length()>0){
    		NotesDocument.Notes not = reaction.addNewNotes();
    		Utils.setNoteHtmlBodyValue(not,comm);
    	}
    	reactions.put(conv.uri(),reaction);
    	bioPAXreactions.put(bpnm.getNameByUri(conv.uri()),conv);
    	bioPAXreactions.put(Utils.cutUri(conv.uri()),conv);
    	String id = GraphUtils.correctId(bpnm.getNameByUri(conv.uri()));
    	bioPAXreactions.put(id,conv);	
    	reaction.setId(id);
    	ListOfReactantsDocument.ListOfReactants lreact = null;
    	if(nreact>0)
    		lreact = reaction.addNewListOfReactants();
    	while(reactants.hasNext()){
    		PhysicalEntity pep = (PhysicalEntity)reactants.next();
    		BioPAXSpecies part = (BioPAXSpecies)independentSpecies.get(Utils.cutUri(pep.uri()));
    		if(id.equals("pid_i_205209"))
    			System.out.println("REACTANT "+part.id+"\t"+part.name+"\t"+Utils.cutUri(pep.uri()));
    		if(part!=null){
    			SpeciesReferenceDocument.SpeciesReference spr = lreact.addNewSpeciesReference();
    			spr.setSpecies(Utils.cutUri(part.id));
    		}else{
    			System.out.println("BioPAXSpecies NOT FOUND: "+Utils.cutUri(pep.uri()));
    		}
    	}
    	ListOfProductsDocument.ListOfProducts lreactp = null;
    	if(nprods>0)
    		lreactp = reaction.addNewListOfProducts();
    	while(products.hasNext()){
    		PhysicalEntity pep = (PhysicalEntity)products.next();
    		BioPAXSpecies part = (BioPAXSpecies)independentSpecies.get(Utils.cutUri(pep.uri()));
    		if(part!=null){
    			SpeciesReferenceDocument.SpeciesReference spr = lreactp.addNewSpeciesReference();
    			spr.setSpecies(Utils.cutUri(part.id));
    		}else{
    			System.out.println("BioPAXSpecies NOT FOUND: "+Utils.cutUri(pep.uri()));
    		}
    	}
    }
    
    public void addInteractionReaction(Interaction inter, ListOfReactionsDocument.ListOfReactions lr) throws Exception{
    	ReactionDocument.Reaction reaction = lr.addNewReaction();
    	String comm = makeComment(inter.getComment());
    	//if(inter instanceof physicalInteraction) comm+=" ; BIOPAX_REACTION_TYPE : physicalInteraction";
    	if(inter instanceof MolecularInteraction) comm+=" ; BIOPAX_REACTION_TYPE : MolecularInteraction";
    	if(inter instanceof BiochemicalReaction) comm+=" ; BIOPAX_REACTION_TYPE : interaction";
    	if(comm.length()>0){
    	    NotesDocument.Notes not = reaction.addNewNotes();
    	    Utils.setNoteHtmlBodyValue(not,comm);
    	}
    	reactions.put(inter.uri(),reaction);
    	bioPAXreactions.put(bpnm.getNameByUri(inter.uri()),inter);
    	bioPAXreactions.put(Utils.cutUri(inter.uri()),inter);
    	String id = GraphUtils.correctId(bpnm.getNameByUri(inter.uri()));
    	reaction.setId(id);
    	reaction.addNewListOfReactants();
    	reaction.addNewListOfProducts();
    	reaction.addNewListOfModifiers();
    }

//    public String getControlledLowLevel(Control cntrl) throws Exception{
//    	String controlled_uri = null;
//    	controlled_uri = Utils.getPropertyURI(cntrl,"controlled");
//    	if(controlled_uri==null){
//    		Vector parts = Utils.getPropertyURIs(cntrl,"participant");
//    		Iterator controller = cntrl.getController_asPhysicalEntity();
//    		String controller_uri = controller.uri();
//    		for(int i=0;i<parts.size();i++){
//    			String uri = (String)parts.elementAt(i);
//    			if(!uri.equals(controller_uri))
//    				controlled_uri = uri;
//    		}
//    	}
//    	return controlled_uri;
//    }
    
    public void addModifierToReaction(String reaction_id, BioPAXSpecies part){
		ReactionDocument.Reaction reaction = (ReactionDocument.Reaction)reactions.get(reaction_id);
        if(reaction!=null){
          ListOfModifiersDocument.ListOfModifiers lm = reaction.getListOfModifiers();
          if(lm==null)
            lm = reaction.addNewListOfModifiers();
          ModifierSpeciesReferenceDocument.ModifierSpeciesReference mspr = lm.addNewModifierSpeciesReference();
          mspr.setSpecies(part.id);
        }
    }

    public void addControl(Control cntrl) throws Exception{
    	// get controlled entity as string uri
    	//String controlled_uri = getControlledLowLevel(cntrl);
    	String controlled_uri = Utils.getPropertyURI(cntrl, "controlled");
    	Vector<String> controller_vector = Utils.getPropertyURIs(cntrl, "controller");
    	if(controlled_uri!=null){
    		// add control uri to map of 'conversion' objects
    		bioPAXreactions.put(Utils.cutUri(cntrl.uri()),cntrl);
    		// get controller for this conversion
    		//physicalEntityParticipant controller = cntrl.getCONTROLLER();
    		//physicalEntityParticipant controller = cntrl.get
    		//if(controller!=null){
    		for (String controller_uri : controller_vector) {
    			// get BioPAXSpecies corresponding to controller uri 
    			BioPAXSpecies part = (BioPAXSpecies)independentSpecies.get(Utils.cutUri(controller_uri));
    			if(part!=null){
    				// get sbml reaction corresponding to controlled uri
    				ReactionDocument.Reaction reaction = (ReactionDocument.Reaction)reactions.get(controlled_uri);
    				if(reaction!=null){
    					addModifierToReaction(controlled_uri,part);
    					String cid = reaction.getId()+"_"+Utils.cutUri(part.id);
    					controls.put(cid,cntrl);
    					controls.put(cntrl.uri(),cntrl);
    					if(cntrl instanceof Catalysis){
    						Iterator cofactors = ((Catalysis)cntrl).getCofactor();
    						while(cofactors.hasNext()){
    							PhysicalEntity phpart = (PhysicalEntity)cofactors.next();
    							part = (BioPAXSpecies)independentSpecies.get(Utils.cutUri(phpart.uri()));;
    							if(part!=null){
    								addModifierToReaction(controlled_uri,part);
    								cid = reaction.getId()+"_"+Utils.cutUri(part.id);
    								controls.put(cid,cntrl);
    								controls.put(cntrl.uri(),cntrl);
    							}
    						}
    					}
    				}else{
    					if(cntrl instanceof Modulation){
    						Control cntrl2 = (Control)controls.get(controlled_uri);
    						//String controlled_uri2 = getControlledLowLevel(cntrl2);
    						if (cntrl2 != null) {
    							String controlled_uri2 = Utils.getPropertyURI(cntrl2, "controlled");
    							reaction = (ReactionDocument.Reaction)reactions.get(controlled_uri2);                	   
    							if(reaction!=null){
    								addModifierToReaction(controlled_uri2,part);                		   
    								String cid = reaction.getId()+"_"+Utils.cutUri(part.id);
    								controls.put(cid,cntrl);
    								controls.put(cntrl.uri(),cntrl);
    							}
    						}
    					}
    					else
    						System.out.println("WARNING!!! CONTROLLED IS NOT REACTION IN "+Utils.cutUri(cntrl.uri()));
    				}
    			}
    			else{
    				System.out.println("WARNING!!! BioPAXSpecies NOT FOUND "+Utils.cutUri(controller_uri));
    			}
    		}
    		if (controller_vector.size()<1)
    			System.out.println("WARNING!!! CONTROLLER IS NOT SET FOR "+Utils.cutUri(cntrl.uri()));
    	}
    }
    

    /**
     * Compile comments in a string from an iterator, with each comment on a different line.
     * 
     * @param Iterator of String
     * @return String comments
     */
    public String makeComment(Iterator comments){
	String res = "";
	if(comments!=null)
	    while(comments.hasNext()){
		res+=(String)comments.next()+" ; \n";
	    }
	if(res.length()>2)
	    res = res.substring(0,res.length()-3);
	return res;
    }


    /**
     * Determine the type (Complex, Protein, Dna, etc.) of a given Physical Entity
     * 
     * @param pe PhysicalEntity Object
     * @return String type
     * @throws Exception
     */
    public String getTypeForParticipant(PhysicalEntity pe) throws Exception{
    	String tpe = "PhysicalEntity";
    	//physicalEntity ent = part.getPHYSICAL_DASH_ENTITY();
    	if(pe != null){
    		if(complexList.get(Utils.cutUri(pe.uri()))!=null) tpe = "Complex";
    		if(proteinList.get(Utils.cutUri(pe.uri()))!=null) tpe = "Protein";
    		if(dnaList.get(Utils.cutUri(pe.uri()))!=null) tpe = "Dna";
    		if(rnaList.get(Utils.cutUri(pe.uri()))!=null) tpe = "Rna";
    		if(smallMoleculeList.get(Utils.cutUri(pe.uri()))!=null) tpe = "SmallMolecule";

    		/*
    		 * ebo: show_type do not exist in comments for biopax level3, it seems.
    		 */
    		
    		/*Iterator it = pe.getComment();
    		while(it.hasNext()){
    			String comm = (String)it.next();
    			if(comm.startsWith("SHOW_TYPE"))
    				if(comm.indexOf(":")>=0){
    					StringTokenizer st = new StringTokenizer(comm,":");
    					st.nextToken();
    					tpe = st.nextToken().trim();
    				}
    		}*/

    	}
	return tpe;
    }

    /**
     * Fills hashmaps(URI, Object) with Protein, Dna, Rna and SmallMolecule Objects for needed conversion.
     */
    public void makeLists() throws Exception {
    	List lst = biopax_DASH_level3_DOT_owlFactory.getAllProtein(biopax.model);
    	Iterator it = lst.iterator();
    	while(it.hasNext()){
    		Protein pe = (Protein)it.next();
    		proteinList.put(Utils.cutUri(pe.uri()),pe);
    	}
    	lst = biopax_DASH_level3_DOT_owlFactory.getAllDna(biopax.model);
    	it = lst.iterator();
    	while(it.hasNext()){
    		Dna pe = (Dna)it.next();
    		dnaList.put(Utils.cutUri(pe.uri()),pe);
    	}
    	lst = biopax_DASH_level3_DOT_owlFactory.getAllRna(biopax.model);
    	it = lst.iterator();
    	while(it.hasNext()){
    		Rna pe = (Rna)it.next();
    		rnaList.put(Utils.cutUri(pe.uri()),pe);
    	}
    	lst = biopax_DASH_level3_DOT_owlFactory.getAllSmallMolecule(biopax.model);
    	it = lst.iterator();
    	while(it.hasNext()){
    		SmallMolecule pe = (SmallMolecule)it.next();
    		smallMoleculeList.put(Utils.cutUri(pe.uri()),pe);
    	}
    }
}
