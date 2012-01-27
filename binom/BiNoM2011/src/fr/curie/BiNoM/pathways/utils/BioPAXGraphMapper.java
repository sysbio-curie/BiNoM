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
@SuppressWarnings("unchecked")
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
  public fr.curie.BiNoM.pathways.analysis.structure.Graph graph = new fr.curie.BiNoM.pathways.analysis.structure.Graph();
  
  public Vector<String> pathwaysAdded = null; 

  /**
   * The mapping implementation 
   */
  public void map() throws Exception{
	
	/* those guys do not exist anymore
    System.out.println("Hashing participants...");	  
    List ppl = biopax_DASH_level3_DOT_owlFactory.getAllphysicalEntityParticipant(biopax.model);
    for (int i=0;i<ppl.size();i++) participants.put(((physicalEntityParticipant)ppl.get(i)).uri(),(physicalEntityParticipant)ppl.get(i));
    ppl = biopax_DASH_level3_DOT_owlFactory.getAllsequenceParticipant(biopax.model);
    for (int i=0;i<ppl.size();i++) participants.put(((physicalEntityParticipant)ppl.get(i)).uri(),(physicalEntityParticipant)ppl.get(i));
	*/
    
	System.out.println("Hashing pathways...");
    
    List pl = biopax_DASH_level3_DOT_owlFactory.getAllPathway(biopax.model);
    for (int i=0;i<pl.size();i++) {
        Pathway p = (Pathway)pl.get(i);
        pathways.put(p.uri(),p);
    }
    pl = biopax_DASH_level3_DOT_owlFactory.getAllPathwayStep(biopax.model);
    for (int i=0;i<pl.size();i++) {
        PathwayStep p = (PathwayStep)pl.get(i);
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
      
      // Add publications

      System.out.println("Adding publications...");
      pl = biopax_DASH_level3_DOT_owlFactory.getAllPublicationXref(biopax.model);
      for (int i=0;i<pl.size();i++) addPublicationNode((PublicationXref)pl.get(i));

      // add physicalEntities
      System.out.println("Adding entities...");
      
      List el = biopax_DASH_level3_DOT_owlFactory.getAllProtein(biopax.model);
      for(int i=0;i<el.size();i++) addEntityNode((Entity)el.get(i));
      el = biopax_DASH_level3_DOT_owlFactory.getAllSmallMolecule(biopax.model);
      for(int i=0;i<el.size();i++) addEntityNode((Entity)el.get(i));
      el = biopax_DASH_level3_DOT_owlFactory.getAllDna(biopax.model);
      for(int i=0;i<el.size();i++) addEntityNode((Entity)el.get(i));
      el = biopax_DASH_level3_DOT_owlFactory.getAllRna(biopax.model);
      for(int i=0;i<el.size();i++) addEntityNode((Entity)el.get(i));
      el = biopax_DASH_level3_DOT_owlFactory.getAllPhysicalEntity(biopax.model);
      for(int i=0;i<el.size();i++) addEntityNode((Entity)el.get(i));
      
      el = biopax_DASH_level3_DOT_owlFactory.getAllComplex(biopax.model);
      for(int i=0;i<el.size();i++) {
    	  addEntityNode((Complex)el.get(i));
      }

      el = biopax_DASH_level3_DOT_owlFactory.getAllComplex(biopax.model);
      for(int i=0;i<el.size();i++) addComplexNode((Complex)el.get(i));

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

      el = biopax_DASH_level3_DOT_owlFactory.getAllConversion(biopax.model);
      for(int i=0;i<el.size();i++) addConversionNode((Conversion)el.get(i));
      el = biopax_DASH_level3_DOT_owlFactory.getAllBiochemicalReaction(biopax.model);
      for(int i=0;i<el.size();i++) addConversionNode((Conversion)el.get(i));
      el = biopax_DASH_level3_DOT_owlFactory.getAllTransport(biopax.model);
      for(int i=0;i<el.size();i++) addConversionNode((Conversion)el.get(i));
      el = biopax_DASH_level3_DOT_owlFactory.getAllTransportWithBiochemicalReaction(biopax.model);
      for(int i=0;i<el.size();i++) addConversionNode((Conversion)el.get(i));
      el = biopax_DASH_level3_DOT_owlFactory.getAllComplexAssembly(biopax.model);
      for(int i=0;i<el.size();i++) addConversionNode((Conversion)el.get(i));

      // Add physical interactions
      System.out.println("Adding interactions...");

      el = biopax_DASH_level3_DOT_owlFactory.getAllMolecularInteraction(biopax.model);
      for(int i=0;i<el.size();i++) addMolecularInteractionNode((MolecularInteraction)el.get(i));
      el = biopax_DASH_level3_DOT_owlFactory.getAllInteraction(biopax.model);
      for(int i=0;i<el.size();i++) addInteractionNode((Interaction)el.get(i));
      

      // Add pathways
      System.out.println("Adding pathways...");

      pathwaysAdded = new Vector<String>();
      pl = biopax_DASH_level3_DOT_owlFactory.getAllPathway(biopax.model);
      for (int i=0;i<pl.size();i++) {
          Pathway p = (Pathway)pl.get(i);
          addPathwayNode(p);
      }
      pl = biopax_DASH_level3_DOT_owlFactory.getAllPathwayStep(biopax.model);
      for (int i=0;i<pl.size();i++) {
          PathwayStep p = (PathwayStep)pl.get(i);
          addPathwayStepNode(p);
      }
      addNextLinks();

      // Add controls
      System.out.println("Adding controls...");

      el = biopax_DASH_level3_DOT_owlFactory.getAllControl(biopax.model);
      for(int i=0;i<el.size();i++) addControlEdge((Control)el.get(i));
      el = biopax_DASH_level3_DOT_owlFactory.getAllCatalysis(biopax.model);
      for(int i=0;i<el.size();i++) addControlEdge((Control)el.get(i));
      el = biopax_DASH_level3_DOT_owlFactory.getAllModulation(biopax.model);
      for(int i=0;i<el.size();i++) addControlEdge((Control)el.get(i));
  }

  private Node addEntityNode(Entity en) throws Exception{
	  Node n = null;
	  String name = biopaxNaming.getNameByUri(en.uri());
	  String id = getID(en);
	  if(!nodes.containsKey(id)){
		  n = new Node(); 
		  n.Id = name; 
		  n.NodeLabel = name;
		  graph.addNode(n);
		  String clName = en.getClass().getName();
		  clName = Utils.replaceString(clName,"Impl","");
		  clName = Utils.replaceString(clName,"fr.curie.BiNoM.pathways.biopax.","");
		  n.Attributes.add(new Attribute("BIOPAX_NODE_TYPE",clName));
		  n.Attributes.add(new Attribute("BIOPAX_URI",en.uri()));
		  Vector syns = new Vector();
		  Iterator it = en.getName();
		  while(it.hasNext()){
			  String s = (String)it.next();
			  n.Attributes.add(new Attribute("BIOPAX_NODE_SYNONYM",s));
			  syns.add(s);
		  }
		  if(syns.indexOf(name)<0){
			  n.Attributes.add(new Attribute("BIOPAX_NODE_SYNONYM",name));
			  syns.add(name);
		  }

		  it = en.getXref();
		  while(it.hasNext()){
			  Object obj = it.next();
			  Xref xrf = null;
			  if(obj instanceof Xref){
				  xrf = (Xref)obj;
				  String xrefname = biopaxNaming.getNameByUri(xrf.uri());
				  n.Attributes.add(new Attribute("BIOPAX_NODE_XREF",xrefname));
			  }
		  }
		  nodes.put(id,n);
		  nodes.put(en.uri(),n);
		  connectEntityToPublications(en);
	  }else{
		  System.out.println("WARNING!!! DOUBLED NAME "+id+"\tName: "+name);
		  n = nodes.get(id);
	  }
	  return n;
  }

 
  private Node addComplexNode(Complex compl) throws Exception{
	  Node n = nodes.get(getID(compl));
	  if(n==null)
		  System.out.println("WARNING!!! Something strange, complex node is not found: "+getID(compl));
	  else{
		  Iterator itc = compl.getComponent();
		  while(itc.hasNext()){
			  //TODO this change should be tested!!!
			  PhysicalEntity pep = (PhysicalEntity)itc.next();
			  String idcomp = getID(pep);
			  Node n1 = nodes.get(idcomp);
			  if(n1==null)
				  System.out.println("WARNING!!! COMPLEX COMPONENT IS NOT INCLUDED: "+idcomp);
			  else{
				  Edge ee = new Edge(); 
				  ee.Id = n1.Id+" (CONTAINS) "+n.Id;
				  ee.EdgeLabel = "CONTAINS";
				  graph.addEdge(ee);
				  ee.Node1 = n1; ee.Node2 = n;
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
		  n = new Node();
		  n.Id = name; 
		  n.NodeLabel = name;
		  graph.addNode(n);
		  nodes.put(id,n);
		  n.Attributes.add(new Attribute("BIOPAX_SPECIES",getID(bs)));
		  n.Attributes.add(new Attribute("BIOPAX_NODE_TYPE",bs.type));
		  for(int i=0;i<bs.sinonymSpecies.size();i++){
			  PhysicalEntity pep = (PhysicalEntity)bs.sinonymSpecies.get(i);
			  n.Attributes.add(new Attribute("BIOPAX_URI",getID(pep)));
			  n.Attributes.add(new Attribute("BIOPAX_URI",pep.uri()));
			  nodes.put(pep.uri(),n);
		  }
		  PhysicalEntity ent = (PhysicalEntity)bs.sinonymSpecies.get(0);
		  if(ent!=null){
			  Node n1 = nodes.get(getID(ent));
			  /*
			   * ebo 01.2012
			   * little kludge here: to avoid systematic self loops, test if 
			   * the two Id are different with expression !n1.Id.equals(n.Id)
			   *  
			   */
			  if(n1!=null && !n1.Id.equals(n.Id)){
				  Edge ee = new Edge();  
				  ee.Id = n1.Id+" (SPECIESOF) "+n.Id; 
				  ee.EdgeLabel = "SPECIESOF";
				  graph.addEdge(ee);
				  ee.Node1 = n1;
				  ee.Node2 = n;
				  ee.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","SPECIESOF"));
				  ee.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"SPECIESOF"+")"+n1.Id));
			  }
		  }else{
			  System.out.println("ERROR: entity is null for "+getID(bs)+" ("+bs.name+")");
		  }
	  }
	  return n;
  }

  private Node addConversionNode(Conversion conv) throws Exception{
    Node n = null;
    String name = biopaxNaming.getNameByUri(conv.uri());
    String id = getID(conv);
    if(!nodes.containsKey(id)){
      n = new Node(); 
      n.Id = name; n.NodeLabel = name;
      graph.addNode(n);
      String clName = conv.getClass().getName();
      clName = Utils.replaceString(clName,"Impl","");
      clName = Utils.replaceString(clName,"fr.curie.BiNoM.pathways.biopax.","");
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
      for(int i=0;i<reactants.size();i++) {
        PhysicalEntity pep = (PhysicalEntity)reactants.get(i);
        BioPAXToSBMLConverter.BioPAXSpecies part = (BioPAXToSBMLConverter.BioPAXSpecies)bsc.independentSpecies.get(Utils.cutUri(pep.uri()));
        if(part!=null){
          Node np = nodes.get(getID(part));
          Edge e = new Edge(); 
          e.Id = np.Id+" (LEFT) "+n.Id;
          e.EdgeLabel = "LEFT";
          graph.addEdge(e);
          e.Node1 = np;
          e.Node2 = n;
          e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","LEFT"));
          e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",np.Id+"(LEFT)"+n.Id));
        }else{
            System.out.println("WARNING!!! BioPAXSpecies NOT FOUND: "+Utils.cutUri(pep.uri()));
        }
      }
      for(int i=0;i<products.size();i++){
        PhysicalEntity pep = (PhysicalEntity)products.get(i);
        BioPAXToSBMLConverter.BioPAXSpecies part = (BioPAXToSBMLConverter.BioPAXSpecies)bsc.independentSpecies.get(Utils.cutUri(pep.uri()));
        if(part!=null){
          Node np = nodes.get(getID(part));
          Edge e = new Edge();  
          e.Id = n.Id+" (RIGHT) "+np.Id;
          e.EdgeLabel = "RIGHT";
          graph.addEdge(e);
          e.Node1 = n;
          e.Node2 = np;
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


  private static void getParticipants(Conversion conv, Vector reactants, Vector products) throws Exception{
	  Boolean spont = conv.getSpontaneous();
	  String dir = conv.getConversionDirection();
	  
	  Iterator _reactants = null;
	  Iterator _products = null;
	  
	  if((spont==null)||(spont==false)||(dir.equals("REVERSIBLE"))||(dir.equals("PHYSIOL-LEFT-TO-RIGHT"))||(dir.equals("IRREVERSIBLE-LEFT-TO-RIGHT"))){
		  _reactants = conv.getLeft();
		  _products = conv.getRight();
	  }
	  else {
		  if ((dir.equals("PHYSIOL-RIGHT-TO-LEFT"))||(dir.equals("IRREVERSIBLE-RIGHT-TO-LEFT"))) {
			  _reactants = conv.getRight();
			  _products = conv.getLeft();
		  }
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

  /*
   * Level 2 code
   * 
   *   
   *   private void addControlEdge(control cntrl) throws Exception{
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
              e.EdgeLabel = mtyp;
              e.Node1 = controller_node;
              e.Node2 = controlled_node;
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
   */
  
  /**
   * Add control edge to the graph for Control instance and its subclasses Catalysis and Modulation
   * 
   */
  private void addControlEdge(Control co) throws Exception{
	  
	  // Controlled elements
	  Interaction inter_controlled = co.getControlled_asInteraction();
	  Pathway pat_controlled = co.getControlled_asPathway();
	  Conversion conv_controlled = null;
	  if (co instanceof Catalysis)
		  conv_controlled = ((Catalysis)co).getControlled_asConversion();
	  
	  
	  // Controller elements
	  Iterator pat_controller = co.getController_asPathway();
	  Iterator pe_controller = co.getController_asPhysicalEntity();
	  
	  
	  if (inter_controlled != null) {
		  while(pat_controller.hasNext()) {
			  Pathway p = (Pathway) pat_controller.next();
			  Node controller_node = nodes.get(getID(p));
			  Node controlled_node = nodes.get(getID(inter_controlled));
			  if (controller_node != null && controlled_node != null)
				  this.addControlEdgeElement(co, controller_node, controlled_node);
		  }
		  while(pe_controller.hasNext()) {
			  PhysicalEntity pe = (PhysicalEntity) pe_controller.next();
			  Node controller_node = nodes.get(getID(pe));
			  Node controlled_node = nodes.get(getID(inter_controlled));
			  if (controller_node != null && controlled_node != null)
				  this.addControlEdgeElement(co, controller_node, controlled_node);
		  }
	  }
	  
	  if (pat_controlled != null) {
		  while(pat_controller.hasNext()) {
			  Pathway p = (Pathway) pat_controller.next();
			  Node controller_node = nodes.get(getID(p));
			  Node controlled_node = nodes.get(getID(pat_controlled));
			  if (controller_node != null && controlled_node != null)
				  this.addControlEdgeElement(co, controller_node, controlled_node);
		  }
		  while(pe_controller.hasNext()) {
			  PhysicalEntity pe = (PhysicalEntity) pe_controller.next();
			  Node controller_node = nodes.get(getID(pe));
			  Node controlled_node = nodes.get(getID(pat_controlled));
			  if (controller_node != null && controlled_node != null)
				  this.addControlEdgeElement(co, controller_node, controlled_node);
		  }
	  }
	  
	  if (conv_controlled != null) {
		  while(pat_controller.hasNext()) {
			  Pathway p = (Pathway) pat_controller.next();
			  Node controller_node = nodes.get(getID(p));
			  Node controlled_node = nodes.get(getID(conv_controlled));
			  if (controller_node != null && controlled_node != null)
				  this.addControlEdgeElement(co, controller_node, controlled_node);
		  }
		  while(pe_controller.hasNext()) {
			  PhysicalEntity pe = (PhysicalEntity) pe_controller.next();
			  Node controller_node = nodes.get(getID(pe));
			  Node controlled_node = nodes.get(getID(conv_controlled));
			  if (controller_node != null && controlled_node != null)
				  this.addControlEdgeElement(co, controller_node, controlled_node);
		  }
	  }
  }

  private void addControlEdgeElement(Control co, Node controller, Node controlled) throws Exception {
	  String mtyp = getControlType(co);
	  Edge e = new Edge();
	  e.Id = controller.Id+" ("+mtyp+") "+controlled.Id;
	  graph.addEdge(e);
	  e.EdgeLabel = mtyp;
	  e.Node1 = controller;
	  e.Node2 = controlled;
	  e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE",mtyp));
	  e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",controller.Id+"("+mtyp+")"+controlled.Id));
	  e.Attributes.add(new Attribute("BIOPAX_URI",co.uri()));
	  interactions.put(co.uri(),co);
  }
  
  
// not used anymore  
//  private String getControlledUri(Control cntrl) throws Exception{
//    String controlled_uri = Utils.getPropertyURI(cntrl,"controlled");
//    if(controlled_uri==null){
//        Vector parts = Utils.getPropertyURIs(cntrl,"participant");
//        Iterator it = cntrl.getController_asPhysicalEntity();
//        String controller_uri = controller.uri();
//        for(int i=0;i<parts.size();i++){
//            String uri = (String)parts.elementAt(i);
//            if(!uri.equals(controller_uri))
//                controlled_uri = uri;
//        }
//    }
//    return controlled_uri;
//  }

  private static String getControlType(Control cont) throws Exception{
	  String mtyp = "CONTROLLER";
	  mtyp = cont.getClass().getName();
	  mtyp = Utils.replaceString(mtyp,"Impl","");
	  mtyp = Utils.replaceString(mtyp,"fr.curie.BiNoM.pathways.biopax.","");
	  String mtypct = cont.getControlType();
	  if((mtypct==null)||(mtypct.equals("")))
		  mtypct = "unknown";
	  if(mtypct.indexOf("ACTIVATION")>=0) mtypct = "ACTIVATION";
	  if(mtypct.indexOf("INHIBITION")>=0) mtypct = "INHIBITION";
	  mtyp+="_"+mtypct;
	  mtyp = mtyp.toUpperCase();
	  return mtyp;
  }

  
  
  /**
   * Add a molecular interaction to the graph (former physicalInteractin in level2)
   * 
   * @param MolecularInteraction instance
   * @return Node n
   * 
   */
  private Node addMolecularInteractionNode(MolecularInteraction mi) throws Exception{
	  Node n = null;
	  String id = getID(mi);
	  if(nodes.get(id)!=null){

		  Iterator it  = mi.getParticipant();
		  Vector<String> v = new Vector<String>();
		  while(it.hasNext()) {
			  Entity e = (Entity) it.next();
			  v.add(e.uri());
		  }

		  if(v.size()>0) {
			  n = new Node();  
			  String intn = biopaxNaming.getNameByUri(mi.uri());
			  n.Id = intn; 
			  n.NodeLabel = intn;
			  graph.addNode(n);
			  n.Attributes.add(new Attribute("BIOPAX_NODE_TYPE","MolecularInteraction"));
			  n.Attributes.add(new Attribute("BIOPAX_URI",mi.uri()));
			  nodes.put(intn,n);
			  nodes.put(mi.uri(),n);
			  connectEntityToPublications(mi);
			  for (int i=0;i<v.size();i++) {
				  String ur = v.get(i);
				  Node n1 = nodes.get(ur);
				  if (n1==null) {
					  System.out.println("ENTITY NOT FOUND ON THE GRAPH: "+Utils.cutUri(ur));
				  } else {
					  id = n1.Id+" (molecularInteraction) "+n.Id;
					  if(edges.get(id)==null){
						  Edge ee = new Edge(); 
						  ee.Id = id; ee.EdgeLabel = "molecularInteraction";
						  graph.addEdge(ee);
						  ee.Node1 = n1; ee.Node2 = n;
						  ee.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","molecularInteraction"));
						  ee.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"molecularInteraction"+")"+n1.Id));
						  edges.put(id,ee);
					  }
				  }
			  }
		  }
	  }
	  return n;
  }
  
  
  
  private Node addPathwayNode(Pathway p) throws Exception{
      Node n = nodes.get(p.uri());
      if (n == null) {
          String id = getID(p);
          String name = biopaxNaming.getNameByUri(p.uri());
          if (nodes.get(name)!=null) {
              System.out.println("WARNING!!! DOUBLED INTERACTION NAME: "+name+"->"+Utils.cutUri(p.uri()));
              name = name+"("+Utils.cutUri(p.uri())+")";
          }
          n = new Node(); 
          n.Id = name; 
          n.NodeLabel = name;
          graph.addNode(n);
          nodes.put(p.uri(),n);
          nodes.put(id,n);
          nodes.put(name,n);
          n.Attributes.add(new Attribute("BIOPAX_NODE_TYPE","Pathway"));
          n.Attributes.add(new Attribute("BIOPAX_URI",p.uri()));
          connectEntityToPublications(p);
      }
      
      System.out.println("\tAdding pathway "+biopaxNaming.getNameByUri(p.uri())+" "+p.uri());
      Vector uris = Utils.getPropertyURIs(p,"PATHWAY-COMPONENTS");

      pathwaysAdded.add(p.uri());
      
      for (int i=0;i<uris.size();i++) {
          String s = (String)uris.elementAt(i);
          if (pathways.containsKey(s)) {
        	  Node n1 = graph.getNode(getID((Pathway)pathways.get(s)));
        	  if(pathwaysAdded.indexOf(s)<0){
        		  n1 = addPathwayNode((Pathway)pathways.get(s));
              Edge e = new Edge(); 
              e.Id = n.Id+" ("+"CONTAINS"+") "+n1.Id;
              e.EdgeLabel = "CONTAINS";
              graph.addEdge(e);
              e.Node1 = n;
              e.Node2 = n1;
              e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","CONTAINS"));
              e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"CONTAINS"+")"+n1.Id));
        	  }
              }
          if (pathwaySteps.containsKey(s)) {
              Node n1 = addPathwayStepNode((PathwayStep)pathwaySteps.get(s));
              Edge e = new Edge(); 
              e.Id = n.Id+" ("+"CONTAINS"+") "+n1.Id;
              e.EdgeLabel = "CONTAINS";
              graph.addEdge(e);
              e.Node1 = n;
              e.Node2 = n1;
              e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","CONTAINS"));
              e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"CONTAINS"+")"+n1.Id));
              }
          if (interactions.containsKey(s)) {
              Node n1 = addInteractionNode((Interaction)interactions.get(s));
              Edge e = new Edge(); 
              e.Id = n.Id+" ("+"CONTAINS"+") "+n1.Id;
              e.EdgeLabel = "CONTAINS";
              graph.addEdge(e);
              e.Node1 = n;
              e.Node2 = n1;
              e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","CONTAINS"));
              e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"CONTAINS"+")"+n1.Id));
          }
      }
      return n;
  }

  private Node addPathwayStepNode(PathwayStep ps) throws Exception{
      
	  Node n = nodes.get(ps.uri());
      if (n == null) {
          n = new Node(); 
          n.Id = Utils.cutUri(ps.uri());
          graph.addNode(n);
          n.NodeLabel = Utils.cutUri(ps.uri());
          nodes.put(ps.uri(),n);
          n.Attributes.add(new Attribute("BIOPAX_NODE_TYPE","PathwayStep"));
          n.Attributes.add(new Attribute("BIOPAX_URI",ps.uri()));
      }
      
      Vector uris = Utils.getPropertyURIs(ps,"STEP-INTERACTIONS");
      for (int i=0;i<uris.size();i++) {
          String s = (String)uris.elementAt(i);
              if (pathways.containsKey(s)) {
                  Node n1 = addPathwayNode((Pathway)pathways.get(s));
                  Edge e = new Edge(); 
                  e.Id = n.Id+" ("+"STEP"+") "+n1.Id;
                  e.EdgeLabel = "STEP";
                  graph.addEdge(e);
                  e.Node1 = n;
                  e.Node2 = n1;
                  e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","STEP"));
                  e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"STEP"+")"+n1.Id));
              }
              if (interactions.containsKey(s)) {
                  Node n1 = addInteractionNode((Interaction)interactions.get(s));
                  Edge e = new Edge(); 
                  e.Id = n.Id+" ("+"STEP"+") "+n1.Id;
                  e.EdgeLabel = "STEP";
                  graph.addEdge(e);
                  e.Node1 = n;
                  e.Node2 = n1;
                  e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","STEP"));
                  e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"STEP"+")"+n1.Id));
              }
      }
      return n;
  }

  private Node addInteractionNode(Interaction in) throws Exception{
      Node n = nodes.get(in.uri());
      if (n == null) {
          String name = biopaxNaming.getNameByUri(in.uri());
          if (nodes.get(name)!=null) {
              System.out.println("WARNING!!! DOUBLED INTERACTION NAME: "+name+"->"+Utils.cutUri(in.uri()));
              name = name+"("+Utils.cutUri(in.uri())+")";
          }
                 n = new Node();  
          n.Id = name; n.NodeLabel = name;
          graph.addNode(n);
          nodes.put(in.uri(),n);
          nodes.put(name,n);
          String iname = in.getClass().getName();
          iname = Utils.replaceString(iname,"Impl","");
          iname = Utils.replaceString(iname,"fr.curie.BiNoM.pathways.biopax.","");
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
  public static void setReactionEffectAttribute(Interaction in, Node node, String substring) throws Exception{
      Iterator it = in.getComment();
      while(it.hasNext()){
    	  String s = (String)it.next();
    	  if(s.toLowerCase().indexOf(substring.toLowerCase()+":")>=0)
    		 node.Attributes.add(new Attribute(substring,s));
      }
  }
  
  public static void setReactionEffectAttribute(Interaction in, GraphicNode node, String substring) throws Exception{
      Iterator it = in.getComment();
      while(it.hasNext()){
    	  String s = (String)it.next();
    	  if(s.toLowerCase().indexOf(substring.toLowerCase()+":")>=0)
    		 Utils.addAttribute(node,substring,substring,s,ObjectType.STRING);
      }
  }
  

  private void addNextLinks() throws Exception{
    Node n = null;
    List stl = biopax_DASH_level3_DOT_owlFactory.getAllPathwayStep(biopax.model);
    for (int i=0;i<stl.size();i++) {
        PathwayStep ps = (PathwayStep)stl.get(i);
        n = nodes.get(ps.uri());
        Vector uris = Utils.getPropertyURIs(ps,"NEXT-STEP");
        for (int j=0;j<uris.size();j++) {
            String s = (String)uris.elementAt(j);
            PathwayStep ps1 = (PathwayStep)pathwaySteps.get(s);
            Node n1 = nodes.get(ps1.uri());;
            Edge e = new Edge(); 
            e.Id = n.Id+" ("+"NEXT"+") "+n1.Id;
            e.EdgeLabel = "NEXT";
            graph.addEdge(e);
            e.Node1 = n;
            e.Node2 = n1;
            e.Attributes.add(new Attribute("BIOPAX_EDGE_TYPE","NEXT"));
            e.Attributes.add(new Attribute("BIOPAX_EDGE_ID",n.Id+"("+"NEXT"+")"+n1.Id));
        }
      }
  }

  private Node addPublicationNode(PublicationXref pub){
    Node n = null;
    String id = getID(pub);
    String name = biopaxNaming.getNameByUri(pub.uri());
    if(nodes.get(id)==null){
        n = new Node();  
        n.Id = name; n.NodeLabel = name;
        graph.addNode(n);
        n.Attributes.add(new Attribute("BIOPAX_NODE_TYPE","Publication"));
        n.Attributes.add(new Attribute("BIOPAX_URI",pub.uri()));
        nodes.put(id,n);
        nodes.put(pub.uri(),n);
      }else{
        System.out.println("WARNING!!! DOUBLED NAME "+id+"\tName: "+name);
        n = nodes.get(id);
      }
    return n;
  }

  private void connectEntityToPublications(Entity en) throws Exception{
    Iterator it = en.getXref();
    if(it!=null){
      while(it.hasNext()){
        Xref xrf = (Xref)it.next();
        Node ent = nodes.get(en.uri());
        Node pub = nodes.get(xrf.uri());
        if((ent!=null)&&(pub!=null)){
          Edge e = new Edge(); 
          e.Id = pub.Id+" ("+"REFERENCE"+") "+ent.Id;
          e.EdgeLabel = "REFERENCE";
          graph.addEdge(e);
          e.Node1 = pub;
          e.Node2 = ent;
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