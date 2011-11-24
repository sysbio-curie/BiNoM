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

import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
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

import edu.rpi.cs.xgmml.*;
import fr.curie.BiNoM.pathways.*;

/**
 * Creates a BioPAX index - mapping of BioPAX content onto an attributed (di)graph 
 */
public class BioPAXGraphMapper {

  /**
   * Map from node ids to Node objects 
   */
  public HashMap<String,Node> nodes = new HashMap<String,Node>();
  /**
   * Map from complete uris to physicalEntityParticipants
   */
  public HashMap participants = new HashMap();
  /**
   * Map from complete uris to entity objects
   */
  public HashMap entities = new HashMap();
  /**
   * Map from complete uris to interaction objects
   */
  public HashMap interactions = new HashMap();
  /**
   * Map from complete uris to pathwaySteps objects
   */
  public HashMap pathwaySteps = new HashMap();
  /**
   * Map from complete uris to pathway objects
   */
  public HashMap pathways = new HashMap();
  /**
   * Map from edge ids to Edge objects 
   */
  public HashMap<String,Edge> edges = new HashMap<String,Edge>();
  /**
   * Naming service used for the mapping
   */
  public BioPAXNamingService biopaxNaming = new BioPAXNamingService();
  /**
   * Input BioPAX object
   */
  public BioPAX biopax = null;
  /**
   * BioPAXToSBML Converter is needed to created the Reaction Network part of the index
   */
  public BioPAXToSBMLConverter bsc = new BioPAXToSBMLConverter();
  /**
   * Resulting GraphDocument object
   */
  //public GraphDocument graph = GraphDocument.Factory.newInstance();;
  public fr.curie.BiNoM.pathways.analysis.structure.Graph graph = new fr.curie.BiNoM.pathways.analysis.structure.Graph();
  
  public Vector<String> pathwaysAdded = null; 

  /**
   * The mapping implementation 
   */
  public void map() throws Exception{
	  
    System.out.println("Hashing participants...");	  

    List ppl = biopax_DASH_level2_DOT_owlFactory.getAllphysicalEntityParticipant(biopax.model);
    for (int i=0;i<ppl.size();i++) participants.put(((physicalEntityParticipant)ppl.get(i)).uri(),(physicalEntityParticipant)ppl.get(i));
    ppl = biopax_DASH_level2_DOT_owlFactory.getAllsequenceParticipant(biopax.model);
    for (int i=0;i<ppl.size();i++) participants.put(((physicalEntityParticipant)ppl.get(i)).uri(),(physicalEntityParticipant)ppl.get(i));

    System.out.println("Hashing pathways...");
    
    List pl = biopax_DASH_level2_DOT_owlFactory.getAllpathway(biopax.model);
    for (int i=0;i<pl.size();i++) {
        pathway p = (pathway)pl.get(i);
        pathways.put(p.uri(),p);
    }
    pl = biopax_DASH_level2_DOT_owlFactory.getAllpathwayStep(biopax.model);
    for (int i=0;i<pl.size();i++) {
        pathwayStep p = (pathwayStep)pl.get(i);
        pathwaySteps.put(p.uri(),p);
    }

      System.out.println("Generating names...");
      biopaxNaming.generateNames(biopax,false);
      //String nm = biopaxNaming.getNameByUri();
      bsc.biopax = biopax;
      bsc.bpnm = biopaxNaming;
      System.out.println("Finding independent species...");
      bsc.findIndependentSpecies();
      
      /*System.out.println("All independent species:");
      Iterator iti = bsc.independentSpecies.keySet().iterator();
      while(iti.hasNext()){
    	  String s = (String)iti.next();
    	  System.out.println(s);
      }*/
      
      //graph.addNewGraph();

      // Add publications

      System.out.println("Adding publications...");
      pl = biopax_DASH_level2_DOT_owlFactory.getAllpublicationXref(biopax.model);
      for (int i=0;i<pl.size();i++) addPublicationNode((publicationXref)pl.get(i));

      // add physicalEntities
      System.out.println("Adding entities...");
      
      List el = biopax_DASH_level2_DOT_owlFactory.getAllprotein(biopax.model);
      for(int i=0;i<el.size();i++) addEntityNode((entity)el.get(i));
      el = biopax_DASH_level2_DOT_owlFactory.getAllsmallMolecule(biopax.model);
      for(int i=0;i<el.size();i++) addEntityNode((entity)el.get(i));
      el = biopax_DASH_level2_DOT_owlFactory.getAlldna(biopax.model);
      for(int i=0;i<el.size();i++) addEntityNode((entity)el.get(i));
      el = biopax_DASH_level2_DOT_owlFactory.getAllrna(biopax.model);
      for(int i=0;i<el.size();i++) addEntityNode((entity)el.get(i));
      el = biopax_DASH_level2_DOT_owlFactory.getAllphysicalEntity(biopax.model);
      for(int i=0;i<el.size();i++) addEntityNode((entity)el.get(i));
      
      el = biopax_DASH_level2_DOT_owlFactory.getAllcomplex(biopax.model);
      for(int i=0;i<el.size();i++) {
    	  //System.out.println(""+(i+1)+":\t"+biopaxNaming.getNameByUri(((complex)el.get(i)).uri()));
    	  addEntityNode((complex)el.get(i));
      }

      el = biopax_DASH_level2_DOT_owlFactory.getAllcomplex(biopax.model);
      for(int i=0;i<el.size();i++) addComplexNode((complex)el.get(i));

      // Add species
      System.out.println("Adding species...");

      Iterator it = bsc.independentSpecies.keySet().iterator();
      while(it.hasNext()){
        String id = (String)it.next();
        BioPAXToSBMLConverter.BioPAXSpecies bs = (BioPAXToSBMLConverter.BioPAXSpecies)bsc.independentSpecies.get(id);
        addSpeciesNode(bs);
      }

      // Add conversion reactions
      System.out.println("Adding conversions...");

      el = biopax_DASH_level2_DOT_owlFactory.getAllconversion(biopax.model);
      for(int i=0;i<el.size();i++) addConversionNode((conversion)el.get(i));
      el = biopax_DASH_level2_DOT_owlFactory.getAllbiochemicalReaction(biopax.model);
      for(int i=0;i<el.size();i++) addConversionNode((conversion)el.get(i));
      el = biopax_DASH_level2_DOT_owlFactory.getAlltransport(biopax.model);
      for(int i=0;i<el.size();i++) addConversionNode((conversion)el.get(i));
      el = biopax_DASH_level2_DOT_owlFactory.getAlltransportWithBiochemicalReaction(biopax.model);
      for(int i=0;i<el.size();i++) addConversionNode((conversion)el.get(i));
      el = biopax_DASH_level2_DOT_owlFactory.getAllcomplexAssembly(biopax.model);
      for(int i=0;i<el.size();i++) addConversionNode((conversion)el.get(i));

      // Add physical interactions
      System.out.println("Adding interactions...");

      el = biopax_DASH_level2_DOT_owlFactory.getAllphysicalInteraction(biopax.model);
      for(int i=0;i<el.size();i++) addPhysicalInteractionNode((physicalInteraction)el.get(i));
      el = biopax_DASH_level2_DOT_owlFactory.getAllinteraction(biopax.model);
      for(int i=0;i<el.size();i++) addInteractionNode((interaction)el.get(i));
      

      // Add pathways
      System.out.println("Adding pathways...");

      pathwaysAdded = new Vector<String>();
      pl = biopax_DASH_level2_DOT_owlFactory.getAllpathway(biopax.model);
      for (int i=0;i<pl.size();i++) {
          pathway p = (pathway)pl.get(i);
          addPathwayNode(p);
      }
      pl = biopax_DASH_level2_DOT_owlFactory.getAllpathwayStep(biopax.model);
      for (int i=0;i<pl.size();i++) {
          pathwayStep p = (pathwayStep)pl.get(i);
          addPathwayStepNode(p);
      }
      addNextLinks();

      // Add controls
      System.out.println("Adding controls...");

      el = biopax_DASH_level2_DOT_owlFactory.getAllcontrol(biopax.model);
      for(int i=0;i<el.size();i++) addControlEdge((control)el.get(i));
      el = biopax_DASH_level2_DOT_owlFactory.getAllcatalysis(biopax.model);
      for(int i=0;i<el.size();i++) addControlEdge((control)el.get(i));
      el = biopax_DASH_level2_DOT_owlFactory.getAllmodulation(biopax.model);
      for(int i=0;i<el.size();i++) addControlEdge((control)el.get(i));

      
  }

  private Node addEntityNode(entity en) throws Exception{
    //GraphicNode n = null;
	Node n = null;
    String name = biopaxNaming.getNameByUri(en.uri());
    String id = getID(en);
    if(!nodes.containsKey(id)){
      //n = graph.getGraph().addNewNode();
      n = new Node(); 
      //n.setId(name); n.setName(name); n.setLabel(name);
      n.Id = name; n.NodeLabel = name;
      graph.addNode(n);
      String clName = en.getClass().getName();
      clName = Utils.replaceString(clName,"Impl","");
      clName = Utils.replaceString(clName,"fr.curie.BiNoM.pathways.biopax.","");
      //Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE",clName,ObjectType.STRING);
      //Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",en.uri(),ObjectType.STRING);
      n.Attributes.add(new Attribute("BIOPAX_NODE_TYPE",clName));
      n.Attributes.add(new Attribute("BIOPAX_URI",en.uri()));
      Vector syns = new Vector();
      Iterator it = en.getSYNONYMS();
      while(it.hasNext()){
          String s = (String)it.next();
          //Utils.addAttribute(n,"BIOPAX_NODE_SYNONYM","BIOPAX_NODE_SYNONYM",s,ObjectType.STRING);
          n.Attributes.add(new Attribute("BIOPAX_NODE_SYNONYM",s));
          syns.add(s);
      }
      if(syns.indexOf(name)<0){
         //Utils.addAttribute(n,"BIOPAX_NODE_SYNONYM","BIOPAX_NODE_SYNONYM",name,ObjectType.STRING);
    	 n.Attributes.add(new Attribute("BIOPAX_NODE_SYNONYM",name));
         syns.add(name);
      }
      String owlname = en.getNAME();
      if(owlname!=null)
      if(syns.indexOf(owlname)<0){
          //Utils.addAttribute(n,"BIOPAX_NODE_SYNONYM","BIOPAX_NODE_SYNONYM",owlname,ObjectType.STRING);
    	  n.Attributes.add(new Attribute("BIOPAX_NODE_SYNONYM",owlname));
          syns.add(name);
      }
      String owlshortname = en.getSHORT_DASH_NAME();
      if(owlshortname!=null)
      if(syns.indexOf(owlshortname)<0){
          //Utils.addAttribute(n,"BIOPAX_NODE_SYNONYM","BIOPAX_NODE_SYNONYM",owlshortname,ObjectType.STRING);
    	  n.Attributes.add(new Attribute("BIOPAX_NODE_SYNONYM",owlshortname));
          syns.add(name);
      }
      
      it = en.getXREF();
      while(it.hasNext()){
          Object obj = it.next();
          xref xrf = null;
          if(obj instanceof xref){
            xrf = (xref)obj;
            String xrefname = biopaxNaming.getNameByUri(xrf.uri());
            //Utils.addAttribute(n,"BIOPAX_NODE_XREF","BIOPAX_NODE_XREF",xrefname,ObjectType.STRING);
      	  n.Attributes.add(new Attribute("BIOPAX_NODE_XREF",xrefname));
          }
      }
      nodes.put(id,n);
      nodes.put(en.uri(),n);
      connectEntityToPublications(en);
      //System.out.println("Added "+id);
    }else{
      System.out.println("WARNING!!! DOUBLED NAME "+id+"\tName: "+name);
      //n = (GraphicNode)nodes.get(id);
      n = nodes.get(id);
    }
    return n;
  }

  private Node addComplexNode(complex compl) throws Exception{
    //GraphicNode n = addEntityNode(compl);
    Node n = nodes.get(getID(compl));
    if(n==null)
      System.out.println("WARNING!!! Something strange, complex node is no found: "+getID(compl));
    else{
    Iterator itc = compl.getCOMPONENTS();
    while(itc.hasNext()){
      physicalEntityParticipant pep = (physicalEntityParticipant)itc.next();
      String idcomp = getID(pep.getPHYSICAL_DASH_ENTITY());
      Node n1 = nodes.get(idcomp);
      //Utils.addAttribute(n,"BIOPAX_ENTITY_COMPONENT","BIOPAX_ENTITY_COMPONENT",idcomp,ObjectType.STRING);
      if(n1==null)
        System.out.println("WARNING!!! COMPLEX COMPONENT IS NOT INCLUDED: "+idcomp);
      else{
      Edge ee = new Edge(); 
      ee.Id = n1.Id+" (CONTAINS) "+n.Id;
      ee.EdgeLabel = "CONTAINS";
      graph.addEdge(ee);
      ee.Node1 = n1; ee.Node2 = n;
      //ee.setSource(n1.getId()); ee.setTarget(n.getId());
      //Utils.addAttribute(ee,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","CONTAINS",ObjectType.STRING);
      //Utils.addAttribute(ee,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+"("+"CONTAINS"+")"+n1.getId(),ObjectType.STRING);
      ee.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","CONTAINS"));
      ee.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"CONTAINS"+")"+n1.Id));
      }
    }
    }
    return n;
  }

  private Node addSpeciesNode(BioPAXToSBMLConverter.BioPAXSpecies bs) throws Exception{
    Node n = null;
    String name = bs.name;
    String id = getID(bs);
    if(!nodes.containsKey(id)){
      n = new Node(); //graph.getGraph().addNewNode();
      //n.setId(name); n.setName(name); n.setLabel(name);
      n.Id = name; n.NodeLabel = name;
      graph.addNode(n);
      nodes.put(id,n);
      //Utils.addAttribute(n,"BIOPAX_SPECIES","BIOPAX_SPECIES",getID(bs),ObjectType.STRING);
      //Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE",bs.type,ObjectType.STRING);
      n.Attributes.add(new Attribute("BIOPAX_SPECIES",getID(bs)));
      n.Attributes.add(new Attribute("BIOPAX_NODE_TYPE",bs.type));
      for(int i=0;i<bs.sinonymSpecies.size();i++){
         physicalEntityParticipant pep = (physicalEntityParticipant)bs.sinonymSpecies.get(i);
         //Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",getID(pep),ObjectType.STRING);
         //Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",pep.uri(),ObjectType.STRING);
         n.Attributes.add(new Attribute("BIOPAX_URI",getID(pep)));
         n.Attributes.add(new Attribute("BIOPAX_URI",pep.uri()));
         nodes.put(pep.uri(),n);
      }
      physicalEntity ent = ((physicalEntityParticipant)bs.sinonymSpecies.get(0)).getPHYSICAL_DASH_ENTITY();
      if(ent!=null){
      Node n1 = nodes.get(getID(ent));
      if(n1!=null){
        Edge ee = new Edge();  
        ee.Id = n1.Id+" (SPECIESOF) "+n.Id; ee.EdgeLabel = "SPECIESOF";
        graph.addEdge(ee);
        //ee.setId(n1.getId()+" (SPECIESOF) "+n.getId());
        //ee.setLabel("SPECIESOF");
        //ee.setSource(n1.getId()); ee.setTarget(n.getId());
        ee.Node1 = n1;
        ee.Node2 = n;
        //Utils.addAttribute(ee,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","SPECIESOF",ObjectType.STRING);
        //Utils.addAttribute(ee,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+"("+"SPECIESOF"+")"+n1.getId(),ObjectType.STRING);
        ee.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","SPECIESOF"));
        ee.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"SPECIESOF"+")"+n1.Id));
      }
      }else{
    	  System.out.println("ERROR: entity is null for "+getID(bs)+" ("+bs.name+")");
      }
    }
    return n;
  }

  private Node addConversionNode(conversion conv) throws Exception{
    Node n = null;
    String name = biopaxNaming.getNameByUri(conv.uri());
    String id = getID(conv);
    if(!nodes.containsKey(id)){
      n = new Node(); 
      //n.setId(name); n.setName(name); n.setLabel(name);
      n.Id = name; n.NodeLabel = name;
      graph.addNode(n);
      String clName = conv.getClass().getName();
      clName = Utils.replaceString(clName,"Impl","");
      clName = Utils.replaceString(clName,"fr.curie.BiNoM.pathways.biopax.","");
      //Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE",clName,ObjectType.STRING);
      //Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",conv.uri(),ObjectType.STRING);
      //Utils.addAttribute(n,"BIOPAX_REACTION","BIOPAX_REACTION",id,ObjectType.STRING);
      n.Attributes.add(new Attribute("BIOPAX_NODE_TYPE",clName));
      n.Attributes.add(new Attribute("BIOPAX_URI",conv.uri()));
      n.Attributes.add(new Attribute("BIOPAX_REACTION",id));
      
      setReactionEffectAttribute(conv,n, "EFFECT");
      
      nodes.put(id,n);
      nodes.put(conv.uri(),n);
      connectEntityToPublications(conv);
      interactions.put(conv.uri(),conv);
      Vector reactants = new Vector();
      Vector products = new Vector();
      getParticipants(conv,reactants,products);
      for(int i=0;i<reactants.size();i++){
        physicalEntityParticipant pep = (physicalEntityParticipant)reactants.get(i);
        BioPAXToSBMLConverter.BioPAXSpecies part = (BioPAXToSBMLConverter.BioPAXSpecies)bsc.independentSpecies.get(Utils.cutUri(pep.uri()));
        if(part!=null){
          Node np = nodes.get(getID(part));
          Edge e = new Edge(); 
          e.Id = np.Id+" (LEFT) "+n.Id;
          e.EdgeLabel = "LEFT";
          graph.addEdge(e);
          //e.setSource(np.getId());
          //e.setTarget(n.getId());
          e.Node1 = np;
          e.Node2 = n;
          //Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","LEFT",ObjectType.STRING);
          //Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",np.getId()+"(LEFT)"+n.getId(),ObjectType.STRING);
          e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","LEFT"));
          e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",np.Id+"(LEFT)"+n.Id));
        }else{
            System.out.println("WARNING!!! BioPAXSpecies NOT FOUND: "+Utils.cutUri(pep.uri()));
        }
      }
      for(int i=0;i<products.size();i++){
        physicalEntityParticipant pep = (physicalEntityParticipant)products.get(i);
        BioPAXToSBMLConverter.BioPAXSpecies part = (BioPAXToSBMLConverter.BioPAXSpecies)bsc.independentSpecies.get(Utils.cutUri(pep.uri()));
        if(part!=null){
          Node np = nodes.get(getID(part));
          Edge e = new Edge();  
          e.Id = n.Id+" (RIGHT) "+np.Id;
          //e.setId(n.getId()+" (RIGHT) "+np.getId());
          e.EdgeLabel = "RIGHT";
          graph.addEdge(e);
          //e.setLabel("RIGHT");
          //e.setSource(n.getId());
          //e.setTarget(np.getId());
          e.Node1 = n;
          e.Node2 = np;
          //Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","RIGHT",ObjectType.STRING);
          //Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+"(RIGHT)"+np.getId(),ObjectType.STRING);
          e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","RIGHT"));
          e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"(RIGHT)"+np.Id));
        }else{
            System.out.println("WARNING!!! BioPAXSpecies NOT FOUND: "+Utils.cutUri(pep.uri()));
        }
      }
    }else{
      System.out.println("WARNING!!! DOUBLED NAME "+id+"\tName: "+name);
      n = nodes.get(id);
    }
    return n;
  }

  private static void getParticipants(conversion conv, Vector reactants, Vector products) throws Exception{
    String spont = conv.getSPONTANEOUS();
    Iterator _reactants = null;
    Iterator _products = null;
    int nreact = 0;
    int nprods = 0;
    if((spont==null)||(spont.equals("L-R"))||(spont.equals("NOT-SPONTANEOUS"))){
        _reactants = conv.getLEFT();
        _products = conv.getRIGHT();
    }else
        if(spont.equals("R-L")){
            _reactants = conv.getRIGHT();
            _products = conv.getLEFT();
        }
    if((reactants==null)||(products==null)){
        System.out.println("UNKNOWN SPONTANEOUS VALUE:"+spont+" "+conv.uri());
    }else{
      while(_reactants.hasNext()){
          reactants.addElement(_reactants.next());
      }
      while(_products.hasNext()){
          products.addElement(_products.next());
      }
    }
  }

  private void addControlEdge(control cntrl) throws Exception{
    String controlled_uri = getControlledUri(cntrl);
    if(controlled_uri!=null){
        physicalEntityParticipant controller = cntrl.getCONTROLLER();
        if(controller!=null){
        BioPAXToSBMLConverter.BioPAXSpecies controller_part = (BioPAXToSBMLConverter.BioPAXSpecies)bsc.independentSpecies.get(Utils.cutUri(controller.uri()));
        if(controller_part!=null){
            Node controller_node = nodes.get(getID(controller_part));
            Node controlled_node = nodes.get(controlled_uri);
            if(controlled_node!=null){
              String mtyp = getControlType(cntrl);
              Edge e = new Edge();  
              e.Id = controller_node.Id+" ("+mtyp+") "+controlled_node.Id;
              graph.addEdge(e);
              //e.setId(controller_node.getId()+" ("+mtyp+") "+controlled_node.getId());
              //e.setLabel(mtyp);
              e.EdgeLabel = mtyp;
              e.Node1 = controller_node;
              e.Node2 = controlled_node;
              //e.setSource(controller_node.getId());
              //e.setTarget(controlled_node.getId());
              //Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE",mtyp,ObjectType.STRING);
              //Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",controller_node.getId()+"("+mtyp+")"+controlled_node.getId(),ObjectType.STRING);
              //Utils.addAttribute(e,"BIOPAX_URI","BIOPAX_URI",cntrl.uri(),ObjectType.STRING);
              e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE",mtyp));
              e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",controller_node.Id+"("+mtyp+")"+controlled_node.Id));
              e.Attributes.add(new Attribute("BIOPAX_URI",cntrl.uri()));
              interactions.put(cntrl.uri(),cntrl);
            }else{
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

  private String getControlledUri(control cntrl) throws Exception{
    String controlled_uri = Utils.getPropertyURI(cntrl,"CONTROLLED");
    if(controlled_uri==null){
        Vector parts = Utils.getPropertyURIs(cntrl,"PARTICIPANTS");
        physicalEntityParticipant controller = cntrl.getCONTROLLER();
        String controller_uri = controller.uri();
        for(int i=0;i<parts.size();i++){
            String uri = (String)parts.elementAt(i);
            if(!uri.equals(controller_uri))
                controlled_uri = uri;
        }
    }
    return controlled_uri;
  }

  private static String getControlType(control cont) throws Exception{
    String mtyp = "CONTROLLER";
        mtyp = cont.getClass().getName();
        mtyp = Utils.replaceString(mtyp,"Impl","");
        mtyp = Utils.replaceString(mtyp,"fr.curie.BiNoM.pathways.biopax.","");
           String mtypct = cont.getCONTROL_DASH_TYPE();
        if((mtypct==null)||(mtypct.equals("")))
            mtypct = "unknown";
        if(mtypct.indexOf("ACTIVATION")>=0) mtypct = "ACTIVATION";
        if(mtypct.indexOf("INHIBITION")>=0) mtypct = "INHIBITION";
        mtyp+="_"+mtypct;
        mtyp = mtyp.toUpperCase();
    return mtyp;
  }

  private Node addPhysicalInteractionNode(physicalInteraction pi) throws Exception{
      Node n = null;
      String id = getID(pi);
      if(nodes.get(id)!=null){
        Vector v = Utils.getPropertyURIs(pi,"PARTICIPANTS");
        if(v.size()>0) {
            n = new Node();  
            String intn = biopaxNaming.getNameByUri(pi.uri());
            //n.setId(intn); n.setName(intn); n.setLabel(intn);
            n.Id = intn; n.NodeLabel = intn;
            graph.addNode(n);
            //Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE","physicalInteraction",ObjectType.STRING);
            //Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",pi.uri(),ObjectType.STRING);
            n.Attributes.add(new Attribute("BIOPAX_NODE_TYPE","physicalInteraction"));
            n.Attributes.add(new Attribute("BIOPAX_URI",pi.uri()));
            nodes.put(intn,n);
            nodes.put(pi.uri(),n);
            connectEntityToPublications(pi);
            for (int i=0;i<v.size();i++) {
                String ur = (String)v.get(i);
                physicalEntityParticipant pep = (physicalEntityParticipant)participants.get(ur);
                Node n1 = nodes.get(pep.getPHYSICAL_DASH_ENTITY().uri());
                if (n1==null) {
                    System.out.println("ENTITY NOT FOUND ON THE GRAPH: "+Utils.cutUri(pep.getPHYSICAL_DASH_ENTITY().uri()));
                } else {
                    id = n1.Id+" (physicalInteraction) "+n.Id;
                    if(edges.get(id)==null){
                      Edge ee = new Edge(); 
                      ee.Id = id; ee.EdgeLabel = "physicalInteraction";
                      graph.addEdge(ee);
                      ee.Node1 = n1; ee.Node2 = n;
                      //ee.setId(id);
                      //ee.setLabel("physicalInteraction");
                      //ee.setSource(n1.getId()); ee.setTarget(n.getId());
                      //Utils.addAttribute(ee,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","physycalInteraction",ObjectType.STRING);
                      //Utils.addAttribute(ee,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+"("+"physycalInteraction"+")"+n1.getId(),ObjectType.STRING);
                      ee.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","physycalInteraction"));
                      ee.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"physycalInteraction"+")"+n1.Id));
                      edges.put(id,ee);
                    }
                }
            }
        }
      }
      return n;
  }

  private Node addPathwayNode(pathway p) throws Exception{
      Node n = nodes.get(p.uri());
      if (n == null) {
          String id = getID(p);
          String name = biopaxNaming.getNameByUri(p.uri());
          if (nodes.get(name)!=null) {
              System.out.println("WARNING!!! DOUBLED INTERACTION NAME: "+name+"->"+Utils.cutUri(p.uri()));
              name = name+"("+Utils.cutUri(p.uri())+")";
          }
          n = new Node(); 
          //n.setId(name); n.setName(name); n.setLabel(name);
          n.Id = name; n.NodeLabel = name;
          graph.addNode(n);
          nodes.put(p.uri(),n);
          nodes.put(id,n);
          nodes.put(name,n);
          //Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE","pathway",ObjectType.STRING);
          //Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",p.uri(),ObjectType.STRING);
          n.Attributes.add(new Attribute("BIOPAX_NODE_TYPE","pathway"));
          n.Attributes.add(new Attribute("BIOPAX_URI",p.uri()));
          connectEntityToPublications(p);
      }
      System.out.println("\tAdding pathway "+p.getNAME()+" "+p.uri());
      Vector uris = Utils.getPropertyURIs(p,"PATHWAY-COMPONENTS");

      pathwaysAdded.add(p.uri());
      /*for(int i=0;i<graph.Nodes.size();i++){
    	  Node nn = graph.Nodes.get(i);
    	  if(nn.getFirstAttribute("BIOPAX_NODE_TYPE").equals("pathway"))
    		  System.out.println("\t\t"+(i+1)+": "+nn.Id);
      }
      for(int i=0;i<pathwaysAdded.size();i++){
    	  System.out.println("\t\t"+(i+1)+": "+pathwaysAdded.get(i));
      }*/
      
      for (int i=0;i<uris.size();i++) {
          String s = (String)uris.elementAt(i);
          if (pathways.containsKey(s)) {
        	  Node n1 = graph.getNode(getID((pathway)pathways.get(s)));
        	  //if(n1==null)
        	  if(pathwaysAdded.indexOf(s)<0){
        		  n1 = addPathwayNode((pathway)pathways.get(s));
              Edge e = new Edge(); 
              e.Id = n.Id+" ("+"CONTAINS"+") "+n1.Id;
              e.EdgeLabel = "CONTAINS";
              graph.addEdge(e);
              e.Node1 = n;
              e.Node2 = n1;
              //e.setId(n.getId()+" ("+"CONTAINS"+") "+n1.getId());
              //e.setLabel("CONTAINS");
              //e.setSource(n.getId());
              //e.setTarget(n1.getId());
              //Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","CONTAINS",ObjectType.STRING);
              //Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+"("+"CONTAINS"+")"+n1.getId(),ObjectType.STRING);
              e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","CONTAINS"));
              e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"CONTAINS"+")"+n1.Id));
        	  }
              }
          if (pathwaySteps.containsKey(s)) {
              Node n1 = addPathwayStepNode((pathwayStep)pathwaySteps.get(s));
              Edge e = new Edge(); 
              e.Id = n.Id+" ("+"CONTAINS"+") "+n1.Id;
              e.EdgeLabel = "CONTAINS";
              graph.addEdge(e);
              e.Node1 = n;
              e.Node2 = n1;
              //e.setId(n.getId()+" ("+"CONTAINS"+") "+n1.getId());
              //e.setLabel("CONTAINS");
              //e.setSource(n.getId());
              //e.setTarget(n1.getId());
              //Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","CONTAINS",ObjectType.STRING);
              //Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+"("+"CONTAINS"+")"+n1.getId(),ObjectType.STRING);
              e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","CONTAINS"));
              e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"CONTAINS"+")"+n1.Id));
              }
          if (interactions.containsKey(s)) {
              Node n1 = addInteractionNode((interaction)interactions.get(s));
              Edge e = new Edge(); 
              e.Id = n.Id+" ("+"CONTAINS"+") "+n1.Id;
              e.EdgeLabel = "CONTAINS";
              graph.addEdge(e);
              e.Node1 = n;
              e.Node2 = n1;
              //e.setId(n.getId()+" ("+"CONTAINS"+") "+n1.getId());
              //e.setLabel("CONTAINS");
              //e.setSource(n.getId());
              //e.setTarget(n1.getId());
              //Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","CONTAINS",ObjectType.STRING);
              //Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+"("+"CONTAINS"+")"+n1.getId(),ObjectType.STRING);
              e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","CONTAINS"));
              e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"CONTAINS"+")"+n1.Id));
          }
      }
      return n;
  }

  private Node addPathwayStepNode(pathwayStep ps) throws Exception{
      Node n = nodes.get(ps.uri());
      if (n == null) {
          n = new Node(); 
          n.Id = Utils.cutUri(ps.uri());
          graph.addNode(n);
          n.NodeLabel = Utils.cutUri(ps.uri());
          //n.setId(Utils.cutUri(ps.uri()));
          //n.setName(Utils.cutUri(ps.uri()));
          //n.setLabel(Utils.cutUri(ps.uri()));
          nodes.put(ps.uri(),n);
          //Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE","pathwayStep",ObjectType.STRING);
          //Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",ps.uri(),ObjectType.STRING);
          n.Attributes.add(new Attribute("BIOPAX_NODE_TYPE","pathwayStep"));
          n.Attributes.add(new Attribute("BIOPAX_URI",ps.uri()));
      }
      Vector uris = Utils.getPropertyURIs(ps,"STEP-INTERACTIONS");
      for (int i=0;i<uris.size();i++) {
          String s = (String)uris.elementAt(i);
              if (pathways.containsKey(s)) {
                  Node n1 = addPathwayNode((pathway)pathways.get(s));
                  Edge e = new Edge(); 
                  e.Id = n.Id+" ("+"STEP"+") "+n1.Id;
                  e.EdgeLabel = "STEP";
                  graph.addEdge(e);
                  e.Node1 = n;
                  e.Node2 = n1;
                  //e.setId(n.getId()+" ("+"STEP"+") "+n1.getId());
                  //e.setLabel("STEP");
                  //e.setSource(n.getId());
                  //e.setTarget(n1.getId());
                  //Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","STEP",ObjectType.STRING);
                  //Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+"("+"STEP"+")"+n1.getId(),ObjectType.STRING);
                  e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","STEP"));
                  e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"STEP"+")"+n1.Id));
              }
              if (interactions.containsKey(s)) {
                  Node n1 = addInteractionNode((interaction)interactions.get(s));
                  Edge e = new Edge(); 
                  e.Id = n.Id+" ("+"STEP"+") "+n1.Id;
                  e.EdgeLabel = "STEP";
                  graph.addEdge(e);
                  e.Node1 = n;
                  e.Node2 = n1;
                  //e.setId(n.getId()+" ("+"STEP"+") "+n1.getId());
                  //e.setLabel("STEP");
                  //e.setSource(n.getId());
                  //e.setTarget(n1.getId());
                  //Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","STEP",ObjectType.STRING);
                  //Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+"("+"STEP"+")"+n1.getId(),ObjectType.STRING);
                  e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","STEP"));
                  e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"STEP"+")"+n1.Id));
              }
      }
      return n;
  }

  private Node addInteractionNode(interaction in) throws Exception{
      Node n = nodes.get(in.uri());
      if (n == null) {
          String name = biopaxNaming.getNameByUri(in.uri());
          if (nodes.get(name)!=null) {
              System.out.println("WARNING!!! DOUBLED INTERACTION NAME: "+name+"->"+Utils.cutUri(in.uri()));
              name = name+"("+Utils.cutUri(in.uri())+")";
          }
                 n = new Node();  
          //n.setId(name); n.setName(name); n.setLabel(name);
          n.Id = name; n.NodeLabel = name;
          graph.addNode(n);
          nodes.put(in.uri(),n);
          nodes.put(name,n);
          String iname = in.getClass().getName();
          iname = Utils.replaceString(iname,"Impl","");
          iname = Utils.replaceString(iname,"fr.curie.BiNoM.pathways.biopax.","");
          //Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE",iname,ObjectType.STRING);
          //Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",in.uri(),ObjectType.STRING);
          n.Attributes.add(new Attribute("BIOPAX_NODE_TYPE",iname));
          n.Attributes.add(new Attribute("BIOPAX_URI",in.uri()));
          setReactionEffectAttribute(in, n, "EFFECT");
          connectEntityToPublications(in);
      }
      return n;
    }
  
  /**
   * If there is a comment with which contains substring+":",
   * this comment is added as a value of an attribute named substring 
   */
  public static void setReactionEffectAttribute(interaction in, Node node, String substring) throws Exception{
      Iterator it = in.getCOMMENT();
      while(it.hasNext()){
    	  String s = (String)it.next();
    	  if(s.toLowerCase().indexOf(substring.toLowerCase()+":")>=0)
    		 //Utils.addAttribute(node,substring,substring,s,ObjectType.STRING);
    		 node.Attributes.add(new Attribute(substring,s));
      }
  }
  
  public static void setReactionEffectAttribute(interaction in, GraphicNode node, String substring) throws Exception{
      Iterator it = in.getCOMMENT();
      while(it.hasNext()){
    	  String s = (String)it.next();
    	  if(s.toLowerCase().indexOf(substring.toLowerCase()+":")>=0)
    		 Utils.addAttribute(node,substring,substring,s,ObjectType.STRING);
      }
  }
  

  private void addNextLinks() throws Exception{
    Node n = null;
    List stl = biopax_DASH_level2_DOT_owlFactory.getAllpathwayStep(biopax.model);
    for (int i=0;i<stl.size();i++) {
        pathwayStep ps = (pathwayStep)stl.get(i);
        n = nodes.get(ps.uri());
        Vector uris = Utils.getPropertyURIs(ps,"NEXT-STEP");
        for (int j=0;j<uris.size();j++) {
            String s = (String)uris.elementAt(j);
            pathwayStep ps1 = (pathwayStep)pathwaySteps.get(s);
            Node n1 = nodes.get(ps1.uri());;
            Edge e = new Edge(); 
            e.Id = n.Id+" ("+"NEXT"+") "+n1.Id;
            e.EdgeLabel = "NEXT";
            graph.addEdge(e);
            e.Node1 = n;
            e.Node2 = n1;
            //e.setId(n.getId()+" ("+"NEXT"+") "+n1.getId());
            //e.setLabel("NEXT");
            //e.setSource(n.getId());
            //e.setTarget(n1.getId());
            //Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","NEXT",ObjectType.STRING);
            //Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",n.getId()+"("+"NEXT"+")"+n1.getId(),ObjectType.STRING);
            e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","NEXT"));
            e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"NEXT"+")"+n1.Id));
        }
      }
  }

  private Node addPublicationNode(publicationXref pub){
    Node n = null;
    String id = getID(pub);
    String name = biopaxNaming.getNameByUri(pub.uri());
    if(nodes.get(id)==null){
        n = new Node();  
        //n.setId(name); n.setName(name); n.setLabel(name);
        n.Id = name; n.NodeLabel = name;
        graph.addNode(n);
        //Utils.addAttribute(n,"BIOPAX_NODE_TYPE","BIOPAX_NODE_TYPE","publication",ObjectType.STRING);
        // // Utils.addAttribute(n,"BIOPAX_NODE_ID","BIOPAX_NODE_ID",id,ObjectType.STRING);
        //Utils.addAttribute(n,"BIOPAX_URI","BIOPAX_URI",pub.uri(),ObjectType.STRING);
        n.Attributes.add(new Attribute("BIOPAX_NODE_TYPE","publication"));
        n.Attributes.add(new Attribute("BIOPAX_URI",pub.uri()));
        nodes.put(id,n);
        nodes.put(pub.uri(),n);
      }else{
        System.out.println("WARNING!!! DOUBLED NAME "+id+"\tName: "+name);
        n = nodes.get(id);
      }
    return n;
  }

  private void connectEntityToPublications(entity en) throws Exception{
    Iterator it = en.getXREF();
    if(it!=null){
      while(it.hasNext()){
        xref xrf = (xref)it.next();
        Node ent = nodes.get(en.uri());
        Node pub = nodes.get(xrf.uri());
        if((ent!=null)&&(pub!=null)){
          Edge e = new Edge(); 
          e.Id = pub.Id+" ("+"REFERENCE"+") "+ent.Id;
          e.EdgeLabel = "REFERENCE";
          graph.addEdge(e);
          e.Node1 = pub;
          e.Node2 = ent;
          //e.setId(pub.getId()+" ("+"REFERENCE"+") "+ent.getId());
          //e.setLabel("REFERENCE");
          //e.setSource(pub.getId());
          //e.setTarget(ent.getId());
          //Utils.addAttribute(e,"BIOPAX_EDGE_TYPE","BIOPAX_EDGE_TYPE","REFERENCE",ObjectType.STRING);
          //Utils.addAttribute(e,"BIOPAX_EDGE_ID","BIOPAX_EDGE_ID",pub.getId()+"("+"NEXT"+")"+ent.getId(),ObjectType.STRING);
          e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","REFERENCE"));
          e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",pub.Id+"("+"NEXT"+")"+ent.Id));
        }else{
          if(ent==null)
            System.out.println("WARNING!!! Entity not found "+Utils.cutUri(en.uri()));
        }
      }
    }
  }

  private String getID(com.ibm.adtech.jastor.Thing thing){
    String id = "";
    id = /*biopax.idName+"$"+*/Utils.cutUri(thing.uri());
    return id;
  }

  private String getID(BioPAXToSBMLConverter.BioPAXSpecies bs){
    String id = "";
    id = /*biopax.idName+"$"+*/bs.id;
    return id;
  }


}