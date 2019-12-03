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

import java.io.*;
import java.util.*;

import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.biopax.*;
import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;


import edu.rpi.cs.xgmml.*;

/**
 * Deprecated class. It was used before for exporting BioPAX interface, but now a more
 * universal method is used (implemented in BioPAXUtilities) 
 */
public class CytoscapeToBioPAXConverter {
	
	public static BioPAX convert(GraphDocument graph, BioPAX biopax) throws Exception{
		BioPAX result = new BioPAX();
		Graph gr = XGMML.convertXGMMLToGraph(graph);
		
		//System.out.println("Statements = "+BioPAXUtilities.numberOfStatements(biopax.model));
		/*StmtIterator it = biopax.model.listStatements();
		for(int i=0;i<10;i++){
			Statement st = it.nextStatement();
			System.out.println(st.toString());
		}*/
		
	
		Model model = BioPAXUtilities.extractFromModel(biopax.model, gr, result.biopaxString,result.importString);
		result.biopaxmodel = (OntModel)model;
		result.model = model;
		return result;
	}
	
    public static void filterIDs(BioPAX biopax, GraphDocument gr) throws Exception{

	Vector species = new Vector();
	Vector reactions = new Vector();

	for (int i=0;i<gr.getGraph().getNodeArray().length;i++) {
	    GraphicNode n = gr.getGraph().getNodeArray(i);
	    if (XGMML.getAttValue(n, "BIOPAX_SPECIES")!=null) {
		species.add(XGMML.getAttValue(n, "BIOPAX_SPECIES"));
	    }
	    if (XGMML.getAttValue(n, "BIOPAX_REACTION")!=null) {
		    reactions.add(XGMML.getAttValue(n, "BIOPAX_REACTION"));
	    }
	}

	filterIDs(biopax, species, reactions);
    }

    public static void filterIDs(BioPAX biopax, Vector species, Vector reactions) throws Exception {
	HashMap participants = new HashMap();
	HashMap allparticipants = new HashMap();
	HashMap interactions = new HashMap();
	HashMap entities = new HashMap();
	HashMap pathwaySteps = new HashMap();

	BioPAXToSBMLConverter b2s = new BioPAXToSBMLConverter();
        b2s.bpnm.generateNames(biopax,false);
	b2s.biopax = biopax;
	b2s.findIndependentSpecies();
	
	List parts = biopax_DASH_level2_DOT_owlFactory.getAllsequenceParticipant(biopax.model);
	for(int i=0;i<parts.size();i++){
		physicalEntityParticipant prt = (physicalEntityParticipant)parts.get(i);
		if(prt.getPHYSICAL_DASH_ENTITY()!=null)
			allparticipants.put(prt.uri(), prt);
	}
	parts = biopax_DASH_level2_DOT_owlFactory.getAllphysicalEntityParticipant(biopax.model);
	for(int i=0;i<parts.size();i++){
		physicalEntityParticipant prt = (physicalEntityParticipant)parts.get(i);
		if(prt.getPHYSICAL_DASH_ENTITY()!=null)
			allparticipants.put(prt.uri(), prt);
	}

	Set keys = b2s.independentSpecies.keySet();
        Iterator it = keys.iterator();
        while(it.hasNext()) {
	    BioPAXToSBMLConverter.BioPAXSpecies pt = (BioPAXToSBMLConverter.BioPAXSpecies)b2s.independentSpecies.get(it.next());
	    if (species.indexOf(pt.id)>=0) {
		for (int i=0;i<pt.sinonymSpecies.size();i++) {
		    physicalEntityParticipant part = (physicalEntityParticipant)pt.sinonymSpecies.elementAt(i);
		    if(part.getPHYSICAL_DASH_ENTITY()!=null){
		    	participants.put(part.uri(), part);
		    	//System.out.println("ADDED "+part.uri());
		    }
		    else
		    	System.out.println("WARNING: Null entity for a participant "+part.uri());
		    entities.put(part.getPHYSICAL_DASH_ENTITY().uri(), part.getPHYSICAL_DASH_ENTITY());
		    //System.out.println("ENTITY: "+part.getPHYSICAL_DASH_ENTITY().uri());
		}
	    }
	}

	List col = biopax_DASH_level2_DOT_owlFactory.getAllcomplex(biopax.model);
	for (int i=0;i<col.size();i++) {
	    complex comp = (complex)col.get(i);
	    if (entities.containsKey(comp.uri())) {
		Iterator itc = comp.getCOMPONENTS();
		while(itc.hasNext()) {
		    physicalEntityParticipant part = (physicalEntityParticipant)itc.next();
		    if(part.getPHYSICAL_DASH_ENTITY()!=null){
		    	participants.put(part.uri(), part);
		    	//System.out.println("ADDED "+part.uri());		    	
		    }
		    else
		    	System.out.println("WARNING: Null entity for a participant "+part.uri());
		    if (!entities.containsKey((part.getPHYSICAL_DASH_ENTITY()).uri()));
		    entities.put(part.getPHYSICAL_DASH_ENTITY().uri(), part.getPHYSICAL_DASH_ENTITY());
		}
	    }
	}

	List brl = biopax_DASH_level2_DOT_owlFactory.getAllbiochemicalReaction(biopax.model);
	for (int i=0;i<brl.size();i++) {
	    biochemicalReaction br = (biochemicalReaction)brl.get(i);
	    removeConversion(br, participants, allparticipants, reactions, interactions, b2s);
	}

	brl = biopax_DASH_level2_DOT_owlFactory.getAlltransportWithBiochemicalReaction(biopax.model);
	for (int i=0;i<brl.size();i++) {
	    transportWithBiochemicalReaction br = (transportWithBiochemicalReaction)brl.get(i);
	    removeConversion(br, participants, allparticipants, reactions, interactions, b2s);
	}

	brl = biopax_DASH_level2_DOT_owlFactory.getAlltransport(biopax.model);
	for (int i=0;i<brl.size();i++) {
	    transport br = (transport)brl.get(i);
	    removeConversion(br, participants, allparticipants, reactions, interactions, b2s);
	}

	brl = biopax_DASH_level2_DOT_owlFactory.getAllcomplexAssembly(biopax.model);
	for (int i=0;i<brl.size();i++) {
	    complexAssembly br = (complexAssembly)brl.get(i);
	    removeConversion(br, participants, allparticipants, reactions, interactions, b2s);
	}

	brl = biopax_DASH_level2_DOT_owlFactory.getAllconversion(biopax.model);
	for (int i=0;i<brl.size();i++) {
	    conversion br = (conversion)brl.get(i);
	    removeConversion(br, participants, allparticipants, reactions, interactions, b2s);
	}

	List cl = biopax_DASH_level2_DOT_owlFactory.getAllcatalysis(biopax.model);
	for (int i=0;i<cl.size();i++) {
	    catalysis cntrl = (catalysis)cl.get(i);
	    String controlled_uri = getControlledURI(cntrl);
	    String name = b2s.bpnm.getNameByUri(controlled_uri);
	    String id = GraphUtils.correctId(name);	    
	    if (reactions.indexOf(id)<0)
		cntrl.removeStatements();
	}
	cl = biopax_DASH_level2_DOT_owlFactory.getAllmodulation(biopax.model);
	for (int i=0;i<cl.size();i++) {
	    modulation cntrl = (modulation)cl.get(i);
	    String controlled_uri = getControlledURI(cntrl);
	    String name = b2s.bpnm.getNameByUri(controlled_uri);
	    String id = GraphUtils.correctId(name);	    
	    if (reactions.indexOf(id)<0)
		cntrl.removeStatements();
	}
	cl = biopax_DASH_level2_DOT_owlFactory.getAllcontrol(biopax.model);
	for (int i=0;i<cl.size();i++) {
	    control cntrl = (control)cl.get(i);
	    String controlled_uri = getControlledURI(cntrl);
	    String name = b2s.bpnm.getNameByUri(controlled_uri);
	    String id = GraphUtils.correctId(name);	    
	    if (reactions.indexOf(id)<0)
		cntrl.removeStatements();
	}


	List spl = biopax_DASH_level2_DOT_owlFactory.getAllsequenceParticipant(biopax.model);
	for (int i=0;i<spl.size();i++) {
	    sequenceParticipant sp = (sequenceParticipant)spl.get(i);
	    if (!participants.containsKey(sp.uri()))
		sp.removeStatements();
	}
	spl = biopax_DASH_level2_DOT_owlFactory.getAllphysicalEntityParticipant(biopax.model);
	for (int i=0;i<spl.size();i++) {
	    physicalEntityParticipant sp = (physicalEntityParticipant)spl.get(i);
	    if (!participants.containsKey(sp.uri()))
		sp.removeStatements();
	}

	List sel = biopax_DASH_level2_DOT_owlFactory.getAllcomplex(biopax.model);
	for (int i=0;i<sel.size();i++) {
	    physicalEntity pe = (physicalEntity)sel.get(i);
	    if (!entities.containsKey(pe.uri()))
		pe.removeStatements();
	}
	sel = biopax_DASH_level2_DOT_owlFactory.getAllprotein(biopax.model);
	for (int i=0;i<sel.size();i++) {
	    physicalEntity pe = (physicalEntity)sel.get(i);
	    if (!entities.containsKey(pe.uri()))
		pe.removeStatements();
	}
	sel = biopax_DASH_level2_DOT_owlFactory.getAlldna(biopax.model);
	for (int i=0;i<sel.size();i++) {
	    physicalEntity pe = (physicalEntity)sel.get(i);
	    if (!entities.containsKey(pe.uri()))
		pe.removeStatements();
	}
	sel = biopax_DASH_level2_DOT_owlFactory.getAllrna(biopax.model);
	for (int i=0;i<sel.size();i++) {
	    physicalEntity pe = (physicalEntity)sel.get(i);
	    if (!entities.containsKey(pe.uri()))
		pe.removeStatements();
	}
	sel = biopax_DASH_level2_DOT_owlFactory.getAllsmallMolecule(biopax.model);
	for (int i=0;i<sel.size();i++) {
	    physicalEntity pe = (physicalEntity)sel.get(i);
	    if (!entities.containsKey(pe.uri()))
		pe.removeStatements();
	}

	HashMap pathways = new HashMap();
	List psl = biopax_DASH_level2_DOT_owlFactory.getAllpathway(biopax.model);
	for (int i=0;i>psl.size();i++) {
	    pathway p = (pathway)psl.get(i);
	    pathways.put(p.uri(), p);
	}

	HashMap pathwayStepsRemove = new HashMap();
	filterPathwaySteps(biopax, interactions, pathwaySteps, pathwayStepsRemove);

	psl = biopax_DASH_level2_DOT_owlFactory.getAllpathway(biopax.model);
	for (int i=0;i<psl.size();i++) {
	    pathway p = (pathway)psl.get(i);
	    Iterator itc = ((Utils.getPropertyURIs(p, "PATHWAY-COMPONENTS"))).iterator();
	    while(itc.hasNext()) {
		String cmp = (String)itc.next();
		if (!interactions.containsKey(cmp))
		    p.removePATHWAY_DASH_COMPONENTS((interaction)interactions.get(cmp));
		if (!pathwaySteps.containsKey(cmp)) {
		    if (pathways.containsKey(cmp))
			p.removePATHWAY_DASH_COMPONENTS((pathway)pathwayStepsRemove.get(cmp));
		    else {
			p.removePATHWAY_DASH_COMPONENTS((pathwayStep)pathwayStepsRemove.get(cmp));
		    }
		}
	    }
	    if (pathwayStepsRemove.containsKey(p.uri())) {
		p.removeStatements();
	    }
	}

	psl = biopax_DASH_level2_DOT_owlFactory.getAllpathwayStep(biopax.model);
	for (int i=0;i<psl.size();i++) {
	    pathwayStep p = (pathwayStep)psl.get(i);
	    Iterator itc = ((Utils.getPropertyURIs(p, "STEP-INTERACTIONS"))).iterator();
	    while(itc.hasNext()) {
		String cmp = (String)itc.next();
		if (!interactions.containsKey(cmp))
		    p.removeSTEP_DASH_INTERACTIONS((interaction)interactions.get(cmp));
		if (!pathwaySteps.containsKey(cmp))
		    p.removeSTEP_DASH_INTERACTIONS((pathway)pathwayStepsRemove.get(cmp));
	    }
            itc = ((Utils.getPropertyURIs(p, "NEXT-STEP"))).iterator();
            while(itc.hasNext()) {
                String cmp = (String)itc.next();
                if (!pathwaySteps.containsKey(cmp))
                    p.removeNEXT_DASH_STEP((pathwayStep)pathwayStepsRemove.get(cmp));
            }


	    if (pathwayStepsRemove.containsKey(p.uri())) {
		p.removeStatements();
	    }
	}
    }

    public static void removeConversion(conversion br,  HashMap participants,  HashMap allparticipants, Vector reactions,  HashMap interactions, BioPAXToSBMLConverter b2s) throws Exception{
	boolean defined = true;
	Vector v = Utils.getPropertyURIs(br, "PARTICIPANTS");
	if (v.size()==0) {
	    Vector vl = Utils.getPropertyURIs(br, "LEFT");
	    Vector vr = Utils.getPropertyURIs(br, "RIGHT");
	    for (int i=0;i<vl.size();i++) v.add(vl.get(i));
	    for (int i=0;i<vr.size();i++) v.add(vr.get(i));
	}
	Iterator it = v.iterator();
	while(it.hasNext()) {
	    String ur = (String)it.next();
	    if ((!participants.containsKey(ur))&&(allparticipants.containsKey(ur)))
      		defined = false;
	}
	String name = b2s.bpnm.getNameByUri(br.uri());
	String id = GraphUtils.correctId(name);
	if ((!defined)||(reactions.indexOf(id)<0)){
		//System.out.println("REMOVING "+br.uri()+" "+b2s.bpnm.getNameByUri(br.uri())+" "+reactions.indexOf(b2s.bpnm.getNameByUri(br.uri()))+" "+defined);
		/*it = v.iterator();
		while(it.hasNext()) {
		    String ur = (String)it.next();
		    System.out.println(ur+"\t"+participants.containsKey(ur));
		}*/
	    br.removeStatements();
	}
	else
	    interactions.put(br.uri(), br);
    }

    public static String getControlledURI(control cntrl) throws Exception{
	String controlled_uri = Utils.getPropertyURI(cntrl, "CONTROLLED");
	if (controlled_uri==null) {
	    Vector parts = Utils.getPropertyURIs(cntrl, "PARTICIPANTS");
	    physicalEntityParticipant controller = cntrl.getCONTROLLER();
	    String controller_uri = controller.uri();
	    for (int j=0;j<parts.size();j++) {
		String uri = (String)parts.elementAt(j);
		if (!uri.equals(controller_uri))
		    controlled_uri = uri;
	    }
	}
	return controlled_uri;
    }

    public static void filterPathwaySteps(BioPAX biopax, HashMap interactions, HashMap pathwaySteps,  HashMap pathwayStepsRemove) throws Exception{
	int num = pathwaySteps.size();
	List psl = biopax_DASH_level2_DOT_owlFactory.getAllpathwayStep(biopax.model);
	for (int i=0;i<psl.size();i++) {
	    pathwayStep ps = (pathwayStep)psl.get(i);
	    //if (ps.uri().indexOf("aspartic_type_endopeptidase")>=0)
	    //  System.out.println("");
	    Vector iurs = Utils.getPropertyURIs(ps, "STEP-INTERACTIONS");
	    Iterator iti = iurs.iterator();
	    boolean defined = false;
	    //System.out.println(Utils.cutUri(ps.uri()));
	    while(iti.hasNext()) {
		String inter = (String)iti.next();
		//System.out.println("\t\t"+Utils.cutUri(inter));
		if (interactions.containsKey(inter))
		    defined = true;
		if (pathwaySteps.containsKey(inter))
		    defined = true;
	    }
	    if (!defined) {
		//ps.removeStatements();
	    } else {
		if (!pathwaySteps.containsKey(ps.uri()))
		    pathwaySteps.put(ps.uri(), ps);
	    }
	}

	psl = biopax_DASH_level2_DOT_owlFactory.getAllpathway(biopax.model);
	for (int i=0;i<psl.size();i++) {
	    pathway ps = (pathway)psl.get(i);
	    Vector iurs = Utils.getPropertyURIs(ps, "PATHWAY-COMPONENTS");
	    Iterator iti = iurs.iterator();
	    boolean defined = false;
	    while(iti.hasNext()) {
		String inter = (String)iti.next();
		if (interactions.containsKey(inter))
		    defined = true;
		if (pathwaySteps.containsKey(inter))
		    defined = true;
	    }
	    if (!defined) {
		//ps.removeStatements();
	    } else {
		if (!pathwaySteps.containsKey(ps.uri()))
		    pathwaySteps.put(ps.uri(), ps);
	    }
	}

	if (pathwaySteps.size()!=num)
	    filterPathwaySteps(biopax, interactions, pathwaySteps, pathwayStepsRemove);
	else {
	    psl = biopax_DASH_level2_DOT_owlFactory.getAllpathwayStep(biopax.model);
	    for (int i=0;i<psl.size();i++) {
		pathwayStep ps = (pathwayStep)psl.get(i);
		if (!pathwaySteps.containsKey(ps.uri()))
		    //ps.removeStatements();
		    pathwayStepsRemove.put(ps.uri(), ps);
	    }
	    psl = biopax_DASH_level2_DOT_owlFactory.getAllpathway(biopax.model);
	    for (int i=0;i<psl.size();i++) {
		pathway ps = (pathway)psl.get(i);
		if (!pathwaySteps.containsKey(ps.uri()))
		    //ps.removeStatements();
		    pathwayStepsRemove.put(ps.uri(), ps);
	    }
	}
    }
}
