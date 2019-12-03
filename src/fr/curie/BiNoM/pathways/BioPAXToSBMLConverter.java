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
	 * Map from cutted uris (after # separator) to BioPAXSpecies
	 */
    public HashMap independentSpeciesIds = new HashMap();
    /**
	 * Auxilary map
	 */
    public HashMap independentSpecies = new HashMap();
    /**
	 * Map from cutted uris (after # separator) to BioPAXSpecies
	 * included into entities (these species group complex
	 * components, for example)
	 */
    public HashMap includedSpecies = new HashMap();
    /**
	 * Map from cutted uris (after # separator) to 'complex' entities
	 */
    public HashMap complexList = new HashMap();
    /**
	 * Map from cutted uris (after # separator) to 'protein' entities
	 */
    public HashMap proteinList = new HashMap();
    /**
	 * Map from cutted uris (after # separator) to 'dna' entities
	 */
    public HashMap dnaList = new HashMap();
    /**
	 * Map from cutted uris (after # separator) to 'rna' entities
	 */
    public HashMap rnaList = new HashMap();
    /**
	 * Map from cutted uris (after # separator) to 'smallMolecule' entities
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
		physicalEntityParticipant prt = (physicalEntityParticipant)sinonymSpecies.elementAt(i);
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

	// Compartments
	CompartmentDocument.Compartment cmp = cml.addNewCompartment();
	cmp.setId("default");
	for(int i=0;i<compartments.size();i++){
	    openControlledVocabulary voc = (openControlledVocabulary)compartments.elementAt(i);
	    cmp = cml.addNewCompartment();
	    cmp.setId("c"+(i+1));
	    XmlAnySimpleType nm = cmp.addNewName();
	    if(voc.getTERM().hasNext())
		nm.setStringValue((String)voc.getTERM().next());
	    else
		nm.setStringValue(Utils.cutUri(voc.uri()));
	}
	// Species
	Set keys = independentSpeciesIds.keySet();
	Iterator it = keys.iterator();
	HashMap speciesNames = new HashMap();
	int itn = 1;
	while(it.hasNext()){
	    String id = (String)it.next();
	    BioPAXSpecies part = (BioPAXSpecies)independentSpeciesIds.get(Utils.cutUri(id));
	    physicalEntityParticipant pep = (physicalEntityParticipant)part.sinonymSpecies.elementAt(0);
	    
            if(pep.getPHYSICAL_DASH_ENTITY()==null)
              System.out.println("WARNING: PHYSICAL_DASH_ENTITY is null for "+pep.uri());
            String comm = "";
        if(pep.getPHYSICAL_DASH_ENTITY()!=null){
        
        comm = makeComment(pep.getPHYSICAL_DASH_ENTITY().getCOMMENT());
            
        SpeciesDocument.Species sp = spl.addNewSpecies();    
	    if(comm.length()>0){
		NotesDocument.Notes not = sp.addNewNotes();
		Utils.setNoteHtmlBodyValue(not,comm);
	    }
	    sp.setId(GraphUtils.correctId(part.id));
	    //sp.setId(part.id);
	    species.put(sp.getId(),sp);
	    XmlAnySimpleType nm = sp.addNewName();
	    nm.setStringValue(part.name);
	    openControlledVocabulary voc = (openControlledVocabulary)pep.getCELLULAR_DASH_LOCATION();
	    if(voc!=null){
		int kc = compartments.indexOf(voc);
		if(kc!=-1)
		    sp.setCompartment("c"+(kc+1));
	    }
	    //System.out.println("Species ("+(itn++)+"/"+keys.size()+"):\t"+sp.getName().getStringValue()+"\t"+sp.getId()+"");
	    if(speciesNames.get(sp.getName())!=null){
		System.out.println("DUBLICATE SPECIES NAME! : "+sp.getName().getStringValue());
	    }
	    speciesNames.put(sp.getName(),sp);

	    //SpeciesAnnotationDocument.SpeciesAnnotation span = sp.addNewSpeciesAnnotation();
        }
	}
	// Reactions
	HashMap allReactions = new HashMap();
	List reactionList = biopax_DASH_level2_DOT_owlFactory.getAlltransportWithBiochemicalReaction(biopax.model);
	it = reactionList.iterator();
	while(it.hasNext()){
	    transportWithBiochemicalReaction trb = (transportWithBiochemicalReaction)it.next();
	    allReactions.put(trb.uri(),trb);
	    addConversionReaction(trb,lr);
	}
	reactionList = biopax_DASH_level2_DOT_owlFactory.getAlltransport(biopax.model);
	it = reactionList.iterator();
	while(it.hasNext()){
	    transport tr = (transport)it.next();
	    allReactions.put(tr.uri(),tr);
	    addConversionReaction(tr,lr);
	}
	reactionList = biopax_DASH_level2_DOT_owlFactory.getAllcomplexAssembly(biopax.model);
	it = reactionList.iterator();
	while(it.hasNext()){
	    complexAssembly ca = (complexAssembly)it.next();
	    allReactions.put(ca.uri(),ca);
	    addConversionReaction(ca,lr);
	}
	reactionList = biopax_DASH_level2_DOT_owlFactory.getAllbiochemicalReaction(biopax.model);
	it = reactionList.iterator();
	while(it.hasNext()){
	    biochemicalReaction br = (biochemicalReaction)it.next();
	    allReactions.put(br.uri(),br);
	    addConversionReaction(br,lr);
	}

        // From higher level
        reactionList = biopax_DASH_level2_DOT_owlFactory.getAllconversion(biopax.model);
        it = reactionList.iterator();
        while(it.hasNext()){
            conversion conv = (conversion)it.next();
            if(allReactions.get(conv.uri())==null){
                allReactions.put(conv.uri(),conv);
                addConversionReaction(conv,lr);
            }
        }
        
        // Let us try to add also physicalInteractions and interactions
        reactionList = biopax_DASH_level2_DOT_owlFactory.getAllphysicalInteraction(biopax.model);
        it = reactionList.iterator();
        while(it.hasNext()){
        	interaction inter = (interaction)it.next();
            if(allReactions.get(inter.uri())==null){
                allReactions.put(inter.uri(),inter);
                addInteractionReaction(inter,lr);
            }
        }
        reactionList = biopax_DASH_level2_DOT_owlFactory.getAllinteraction(biopax.model);
        it = reactionList.iterator();
        while(it.hasNext()){
        	interaction inter = (interaction)it.next();
            if(allReactions.get(inter.uri())==null){
                allReactions.put(inter.uri(),inter);
                addInteractionReaction(inter,lr);
            }
        }

        // Now process controls
        List catlist  = biopax_DASH_level2_DOT_owlFactory.getAllcatalysis(biopax.model);
        it = catlist.iterator();
        while(it.hasNext()){
            catalysis ct = (catalysis)it.next();
            allReactions.put(ct.uri(),ct);
            addControl(ct);
        }
        List modlist  = biopax_DASH_level2_DOT_owlFactory.getAllmodulation(biopax.model);
        it = modlist.iterator();
        while(it.hasNext()){
            modulation ct = (modulation)it.next();
            //System.out.println("Adding modulation: "+ct.uri());
            allReactions.put(ct.uri(),ct);
            addControl(ct);
        }

	reactionList = biopax_DASH_level2_DOT_owlFactory.getAllcontrol(biopax.model);
	it = reactionList.iterator();
	while(it.hasNext()){
	    control contr = (control)it.next();
	    if(allReactions.get(contr.uri())==null){
		allReactions.put(contr.uri(),contr);
		addControl(contr);
	    }
	}


	//AnnotationDocument.Annotation an = m.addNewAnnotation();


    }

	/**
	 * Procedure of groupping physicalEntityParticipants into
	 * BioPAXSpecies (chemical species)
	 */
    public void findIndependentSpecies() throws Exception{
	makeLists();
	// First process complexes
	System.out.println("Finding included species...");
	List allcompl = biopax_DASH_level2_DOT_owlFactory.getAllcomplex(biopax.model);
	Iterator allcompi = allcompl.iterator();
	while(allcompi.hasNext()){
	    complex compl = (complex)allcompi.next();
	    complexList.put(Utils.cutUri(compl.uri()),compl);
	    Iterator comps = compl.getCOMPONENTS();
	    while(comps.hasNext()){
		physicalEntityParticipant part = (physicalEntityParticipant)comps.next();
		addIncludedParticipant(part);
	    }
	}
	// Second, process all others
	System.out.println("Finding species...");
	List allsparts = biopax_DASH_level2_DOT_owlFactory.getAllsequenceParticipant(biopax.model);
	Iterator allspartsi = allsparts.iterator();
	while(allspartsi.hasNext()){
	    sequenceParticipant spart = (sequenceParticipant)allspartsi.next();
        if(spart.getPHYSICAL_DASH_ENTITY()==null)
            System.out.println("WARNING: PHYSICAL_DASH_ENTITY is null for "+Utils.cutUri(spart.uri()));
        else
        	addParticipant(spart);
	}
	List allparts = biopax_DASH_level2_DOT_owlFactory.getAllphysicalEntityParticipant(biopax.model);
	Iterator allpartsi = allparts.iterator();
	while(allpartsi.hasNext()){
	    physicalEntityParticipant part = (physicalEntityParticipant)allpartsi.next();
        if(part.getPHYSICAL_DASH_ENTITY()==null)
            System.out.println("WARNING: PHYSICAL_DASH_ENTITY is null for "+Utils.cutUri(part.uri()));
        else
	   	    addParticipant(part);
	}
	// Now we will deal with the situation when some included species are errorneously used as reactants, products and controllers
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

	System.out.println("Total: "+(independentSpecies.size()+includedSpecies.size())+" participants, "+independentSpeciesIds.size()+" species, "+n+" synonims, "+includedSpecies.size()+" included ");
    }
    
    private void findIncludedSpeciesInvolvedInReactionsPatch() throws Exception{
    	List allbr = biopax_DASH_level2_DOT_owlFactory.getAllbiochemicalReaction(biopax.model);
    	Iterator allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		biochemicalReaction br = (biochemicalReaction)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}
    	allbr = biopax_DASH_level2_DOT_owlFactory.getAllcomplexAssembly(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		complexAssembly br = (complexAssembly)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}
    	allbr = biopax_DASH_level2_DOT_owlFactory.getAlltransport(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		transport br = (transport)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}
    	allbr = biopax_DASH_level2_DOT_owlFactory.getAlltransportWithBiochemicalReaction(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		transportWithBiochemicalReaction br = (transportWithBiochemicalReaction)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}
    	allbr = biopax_DASH_level2_DOT_owlFactory.getAllconversion(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		conversion br = (conversion)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}    	
    	allbr = biopax_DASH_level2_DOT_owlFactory.getAllcatalysis(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		catalysis br = (catalysis)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}
    	allbr = biopax_DASH_level2_DOT_owlFactory.getAllmodulation(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		modulation br = (modulation)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}
    	allbr = biopax_DASH_level2_DOT_owlFactory.getAllcontrol(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		control br = (control)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}
    	allbr = biopax_DASH_level2_DOT_owlFactory.getAllphysicalInteraction(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		physicalInteraction br = (physicalInteraction)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}    	       	
    	allbr = biopax_DASH_level2_DOT_owlFactory.getAllinteraction(biopax.model);
    	allbri = allbr.iterator();
    	while(allbri.hasNext()){
    		interaction br = (interaction)allbri.next();
    		findIncludedSpeciesInvolvedInReactionsPatch(br);
    	}    	       	
    	
    }
    
    private void findIncludedSpeciesInvolvedInReactionsPatch(interaction inter) throws Exception{
    	//Iterator parts = inter.getPARTICIPANTS_asphysicalEntityParticipant();
    	Vector pall = new Vector();
    	Vector p = Utils.getPropertyURIs(inter,"PARTICIPANTS");
    	for(int i=0;i<p.size();i++) pall.add(p.get(i));
    	p = Utils.getPropertyURIs(inter,"LEFT");
    	for(int i=0;i<p.size();i++) pall.add(p.get(i));
    	p = Utils.getPropertyURIs(inter,"RIGHT");
    	for(int i=0;i<p.size();i++) pall.add(p.get(i));
    	p = Utils.getPropertyURIs(inter,"CONTROLLER");
    	for(int i=0;i<p.size();i++) pall.add(p.get(i));
    	
    	Iterator parts = pall.iterator();
    	while(parts.hasNext()){
    		//physicalEntityParticipant part = (physicalEntityParticipant)parts.next();
    		String uri = (String)parts.next();
    		String curi = Utils.cutUri(uri);
    		if(includedSpecies.get(curi)!=null){
    			String pname = (String)bpnm.genericUtilityName.get(uri);
    			physicalEntityParticipant part = (physicalEntityParticipant)includedSpecies.get(curi);
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
    private void addParticipant(physicalEntityParticipant part) throws Exception{
	if(independentSpecies.get(Utils.cutUri(part.uri()))==null)
	    if(includedSpecies.get(Utils.cutUri(part.uri()))==null){
		//String pname = bpnm.getNameByUri(part.uri());
		String pname = (String)bpnm.genericUtilityName.get(part.uri());
		String id = GraphUtils.correctId(pname);
		String tpe = getTypeForParticipant(part);
		System.out.println(part.uri());
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

    /**
     * Adds included physicalEntityParticipant (such as complex components)
     */
    public void addIncludedParticipant(physicalEntityParticipant part) throws Exception{
	if(independentSpecies.get(Utils.cutUri(part.uri()))==null)
	    if(includedSpecies.get(Utils.cutUri(part.uri()))==null){
		includedSpecies.put(Utils.cutUri(part.uri()),part);
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
	    physicalEntityParticipant pep = (physicalEntityParticipant)part.sinonymSpecies.elementAt(0);
	    openControlledVocabulary comp = pep.getCELLULAR_DASH_LOCATION();
	    if(comp!=null){
		String compname = "";
		if(comp.getTERM().hasNext())
		    compname = (String)(comp.getTERM().next());
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
			openControlledVocabulary compv = (openControlledVocabulary)v.elementAt(i);
			String compvname = "";
			if(compv.getTERM().hasNext())
			    compvname = (String)(compv.getTERM().next());
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
	    openControlledVocabulary comp = (openControlledVocabulary)v.elementAt(i);
	    String compt = "";
	    if(comp.getTERM().hasNext())
		compt = (String)comp.getTERM().next();
	    else
		compt = Utils.cutUri(comp.uri());
	    System.out.println("Compartment "+(i+1)+": "+compt);
	}
	return v;
    }

    public void addConversionReaction(conversion conv, ListOfReactionsDocument.ListOfReactions lr) throws Exception{
	String spont = conv.getSPONTANEOUS();
	Iterator reactants = null;
	Iterator products = null;
	int nreact = 0;
	int nprods = 0;
	if((spont==null)||(spont.equals("L-R"))||(spont.equals("NOT-SPONTANEOUS"))){
	    reactants = conv.getLEFT();
	    products = conv.getRIGHT();
	    while(reactants.hasNext()){
		nreact++;
		reactants.next();
	    }
	    while(products.hasNext()){
		nprods++;
		products.next();
	    }
	    reactants = conv.getLEFT();
	    products = conv.getRIGHT();
	}else
	    if(spont.equals("R-L")){
		reactants = conv.getRIGHT();
		products = conv.getLEFT();
		while(reactants.hasNext()){
		    nreact++;
		    reactants.next();
		}
		while(products.hasNext()){
		    nprods++;
		    products.next();
		}
		reactants = conv.getRIGHT();
		products = conv.getLEFT();
	    }
	if((reactants==null)||(products==null)){
	    System.out.println("UNKNOWN SPONTANEOUS VALUE:"+spont+" "+conv.uri());
	}
	ReactionDocument.Reaction reaction = lr.addNewReaction();
	String comm = makeComment(conv.getCOMMENT());
	if(conv instanceof transport) comm+=" ; BIOPAX_REACTION_TYPE : transport";
	if(conv instanceof biochemicalReaction) comm+=" ; BIOPAX_REACTION_TYPE : biochemicalReaction";
	if(conv instanceof transportWithBiochemicalReaction) comm+=" ; BIOPAX_REACTION_TYPE : transportWithBiochemicalReaction";
	if(conv instanceof complexAssembly) comm+=" ; BIOPAX_REACTION_TYPE : complexAssembly";
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
	    physicalEntityParticipant pep = (physicalEntityParticipant)reactants.next();
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
	    physicalEntityParticipant pep = (physicalEntityParticipant)products.next();
	    BioPAXSpecies part = (BioPAXSpecies)independentSpecies.get(Utils.cutUri(pep.uri()));
	    if(part!=null){
		SpeciesReferenceDocument.SpeciesReference spr = lreactp.addNewSpeciesReference();
		spr.setSpecies(Utils.cutUri(part.id));
	    }else{
		System.out.println("BioPAXSpecies NOT FOUND: "+Utils.cutUri(pep.uri()));
	    }
	}
    }
    
    public void addInteractionReaction(interaction inter, ListOfReactionsDocument.ListOfReactions lr) throws Exception{
    	ReactionDocument.Reaction reaction = lr.addNewReaction();
    	String comm = makeComment(inter.getCOMMENT());
    	if(inter instanceof physicalInteraction) comm+=" ; BIOPAX_REACTION_TYPE : physicalInteraction";
    	if(inter instanceof biochemicalReaction) comm+=" ; BIOPAX_REACTION_TYPE : interaction";
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
    
    public String getControlledLowLevel(control cntrl) throws Exception{
    	String controlled_uri = null;
    	controlled_uri = Utils.getPropertyURI(cntrl,"CONTROLLED");
    	if(controlled_uri==null){
	    Vector parts = Utils.getPropertyURIs(cntrl,"PARTICIPANTS");
	    physicalEntityParticipant controller = cntrl.getCONTROLLER();
	    String controller_uri = controller.uri();
	    for(int i=0;i<parts.size();i++){
		String uri = (String)parts.elementAt(i);
		if(!uri.equals(controller_uri))
		    controlled_uri = uri;
	    }}
	    return controlled_uri;
    }
    
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

    public void addControl(control cntrl) throws Exception{
	//interaction controlled = cntrl.getCONTROLLED_asinteraction();
	String controlled_uri = getControlledLowLevel(cntrl);
	if(controlled_uri!=null){
	    bioPAXreactions.put(Utils.cutUri(cntrl.uri()),cntrl);
	    physicalEntityParticipant controller = cntrl.getCONTROLLER();
            if(controller!=null){
	    BioPAXSpecies part = (BioPAXSpecies)independentSpecies.get(Utils.cutUri(controller.uri()));
	    if(part!=null){
		ReactionDocument.Reaction reaction = (ReactionDocument.Reaction)reactions.get(controlled_uri);
                if(reaction!=null){
                  addModifierToReaction(controlled_uri,part);
                  //AnnotationDocument.Annotation anr = mspr.addNewAnnotation();
                  //setValue(anr,makeComment(cntrl.getCOMMENT()));
                  String cid = reaction.getId()+"_"+Utils.cutUri(part.id);
                  controls.put(cid,cntrl);
                  controls.put(cntrl.uri(),cntrl);
                  if(cntrl instanceof catalysis){
                	  Iterator cofactors = ((catalysis)cntrl).getCOFACTOR();
                	  while(cofactors.hasNext()){
                		  physicalEntityParticipant phpart = (physicalEntityParticipant)cofactors.next();
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
                   if(cntrl instanceof modulation){
                	   modulation mod = (modulation)cntrl;
                	   control cntrl2 = (control)controls.get(controlled_uri);
                	   String controlled_uri2 = getControlledLowLevel(cntrl2);
                	   reaction = (ReactionDocument.Reaction)reactions.get(controlled_uri2);                	   
                	   if(reaction!=null){
                    	   addModifierToReaction(controlled_uri2,part);                		   
                		   String cid = reaction.getId()+"_"+Utils.cutUri(part.id);
                		   controls.put(cid,cntrl);
                		   controls.put(cntrl.uri(),cntrl);
                	   }
                   }else
                	   System.out.println("WARNING!!! CONTROLLED IS NOT REACTION IN "+Utils.cutUri(cntrl.uri()));
                }
	    }else{
		System.out.println("WARNING!!! BioPAXSpecies NOT FOUND "+Utils.cutUri(controller.uri()));
	    }
	    }else{
		System.out.println("WARNING!!! CONTROLLER IS NOT SET FOR "+Utils.cutUri(cntrl.uri()));
        }
	}
    }
    


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

    public String getTypeForParticipant(physicalEntityParticipant part) throws Exception{
	String tpe = "physicalEntity";
	physicalEntity ent = part.getPHYSICAL_DASH_ENTITY();
	if(ent!=null){
	    if(complexList.get(Utils.cutUri(ent.uri()))!=null) tpe = "complex";
	    if(proteinList.get(Utils.cutUri(ent.uri()))!=null) tpe = "protein";
	    if(dnaList.get(Utils.cutUri(ent.uri()))!=null) tpe = "dna";
	    if(rnaList.get(Utils.cutUri(ent.uri()))!=null) tpe = "rna";
	    if(smallMoleculeList.get(Utils.cutUri(ent.uri()))!=null) tpe = "smallMolecule";
	    
	    Iterator it = ent.getCOMMENT();
	    while(it.hasNext()){
	    	String comm = (String)it.next();
	    	if(comm.startsWith("SHOW_TYPE"))if(comm.indexOf(":")>=0){
	    		StringTokenizer st = new StringTokenizer(comm,":");
	    		st.nextToken();
	    		tpe = st.nextToken().trim();
	    	}
	    }
	    
	}
    /*String tpe = "sequenceParticipant";
    if(part instanceof physicalEntityParticipant)
    	tpe = "physicalEntityParticipant";
    else
    	tpe = "sequenceParticipant";*/
	return tpe;
    }

    /**
     * Fills all maps for needed conversion
     */
    public void makeLists() throws Exception{
	List lst = biopax_DASH_level2_DOT_owlFactory.getAllprotein(biopax.model);
	Iterator it = lst.iterator();
	while(it.hasNext()){
	    protein pe = (protein)it.next();
	    proteinList.put(Utils.cutUri(pe.uri()),pe);
	}
	lst = biopax_DASH_level2_DOT_owlFactory.getAlldna(biopax.model);
	it = lst.iterator();
	while(it.hasNext()){
	    dna pe = (dna)it.next();
	    dnaList.put(Utils.cutUri(pe.uri()),pe);
	}
	lst = biopax_DASH_level2_DOT_owlFactory.getAllrna(biopax.model);
	it = lst.iterator();
	while(it.hasNext()){
	    rna pe = (rna)it.next();
	    rnaList.put(Utils.cutUri(pe.uri()),pe);
	}
	lst = biopax_DASH_level2_DOT_owlFactory.getAllsmallMolecule(biopax.model);
	it = lst.iterator();
	while(it.hasNext()){
	    smallMolecule pe = (smallMolecule)it.next();
	    smallMoleculeList.put(Utils.cutUri(pe.uri()),pe);
	}
    }
}
