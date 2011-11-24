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
import fr.curie.BiNoM.cytoscape.biopax.query.*;

import java.io.*;
import java.util.*;

import edu.rpi.cs.xgmml.*;
import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

/**
 * The actual implementation of the standard query 
 */
public class BioPAXGraphQueryEngine {

/**
 * The query to be made
 */
public BioPAXGraphQuery query = null;
/**
 * The index with which the query is performed
 */
public fr.curie.BiNoM.pathways.analysis.structure.Graph database = null;
/**
 * Copy of the database to perform index path analysis
 */
public fr.curie.BiNoM.pathways.analysis.structure.Graph databaseCopyForPathAnalysis = null;
/**
 * Map from node names and synonyms to the Vector of Node objects
 */
public HashMap entitySynonym = null;
/**
 * Map from node xrefs to the Vector of Node objects 
 */
public HashMap entityXREF = null;

/**
 * If false, all smallMolecules are excluded in queries
 */
public boolean excludeSmallMolecules = true;

/**
 * Performs a query of queryType 
 */
public void doQuery(BioPAXGraphQuery q, int queryType){
  query = q;
  query.queryType = queryType;
  if(queryType==BioPAXGraphQuery.SELECT_ENTITIES)
    selectEntities();
  if(queryType==BioPAXGraphQuery.ADD_COMPLEXES_EXPAND)
    addComplexes(true);
  if(queryType==BioPAXGraphQuery.ADD_COMPLEXES_NOEXPAND)
    addComplexes(false);
  if(queryType==BioPAXGraphQuery.ADD_SPECIES)
    addSpecies();
  if(queryType==BioPAXGraphQuery.ADD_CONNECTING_REACTIONS)
    addConnectingReactions();
  if(queryType==BioPAXGraphQuery.ADD_PUBLICATIONS)
    addPublications();
  if(queryType==BioPAXGraphQuery.LIST_PUBLICATIONS)
    listPublications();
  if(queryType==BioPAXGraphQuery.ADD_ALL_REACTIONS)
    addAllReactions();
  if(queryType==BioPAXGraphQuery.COMPLETE_REACTIONS)
    completeReactions();
  if(queryType==BioPAXGraphQuery.LIST_PROTEINS)
    listObjects("protein");
  if(queryType==BioPAXGraphQuery.LIST_COMPLEXES)
    listObjects("complex");
  if(queryType==BioPAXGraphQuery.LIST_REACTIONS){
    listObjects("physicalInteraction");
    listObjects("interaction");
    listObjects("conversion");
    listObjects("biochemicalReaction");
    listObjects("complexAssembly");
    listObjects("transport");
    listObjects("transportWithBiochemicalReaction");
  }
  if(queryType==BioPAXGraphQuery.LIST_SMALL_MOLECULES){
    listObjects("smallMolecule");
  }
}

private void selectEntities(){
  query.result = new fr.curie.BiNoM.pathways.analysis.structure.Graph();
  int found = 0, total = 0;
  for(int i=0;i<query.input.Nodes.size();i++){
	Vector resultNodeList = new Vector();
    fr.curie.BiNoM.pathways.analysis.structure.Node nd = (fr.curie.BiNoM.pathways.analysis.structure.Node)query.input.Nodes.get(i);
    //if(nd.Id.equals("CDK1"))
    //	System.out.println();
    //System.out.println(nd.Id+"\t"+nd.getFirstAttributeValue("BIOPAX_NODE_TYPE"));
    if(nd.getFirstAttributeValue("BIOPAX_NODE_TYPE").equals("protein")||
       nd.getFirstAttributeValue("BIOPAX_NODE_TYPE").equals("gene")||
       nd.getFirstAttributeValue("BIOPAX_NODE_TYPE").equals("dna")||
       (nd.getFirstAttributeValue("BIOPAX_NODE_TYPE").equals("smallMolecule")&&(!excludeSmallMolecules))
       //||nd.getFirstAttributeValue("BIOPAX_NODE_TYPE").equals("complex")
       )if(nd.getFirstAttributeValue("BIOPAX_SPECIES")==null){
    Vector ndident = null;
    Vector xrefs = nd.getAttributeValues("BIOPAX_NODE_XREF");
    if(xrefs!=null)
    for(int j=0;j<xrefs.size();j++){
      String s = (String)xrefs.get(j); s = s.toLowerCase();
      //System.out.println("Selecting by xref "+s);
      ndident = (Vector)entityXREF.get(s);
      if(ndident!=null)
      for(int k=0;k<ndident.size();k++){
    	  Node n = (Node)ndident.get(k);
    	  if((!excludeSmallMolecules)||(!n.getFirstAttributeValue("BIOPAX_NODE_TYPE").equals("smallMolecule")))
    		  if(resultNodeList.indexOf(n)<0)
    			  resultNodeList.add(ndident.get(k));
      }
    }
    //if(resultNodeList.size()==0){
    Vector syns = nd.getAttributeValues("BIOPAX_NODE_SYNONYM");
    //for(int j=0;j<nd.Attributes.size();j++)
    //	System.out.println(((Attribute)nd.Attributes.get(j)).name+"\t"+((Attribute)nd.Attributes.get(j)).value);
    if(syns!=null){
    if(syns.indexOf(nd.Id)<0) syns.add(nd.Id); 
    for(int j=0;j<syns.size();j++){
      String s = (String)syns.get(j); s = s.toLowerCase();
      s = correctXREF(s);
      //System.out.print("Selecting by synonym "+s+"\t");
      ndident = (Vector)entitySynonym.get(s);
      if(ndident!=null)
      for(int k=0;k<ndident.size();k++){
    	  Node n = (Node)ndident.get(k);
    	  if((!excludeSmallMolecules)||(!n.getFirstAttributeValue("BIOPAX_NODE_TYPE").equals("smallMolecule")))
    		 if(resultNodeList.indexOf(n)<0) 
    		    resultNodeList.add(n);
      }
      /*if(ndident!=null)
        System.out.println("found ("+resultNodeList.size()+")");
      else
    	System.out.println("not found");*/ 
    }
    }
    //}
  if(resultNodeList.size()!=0){
    found++;
    total++;
    String mes = ""+(total)+"\t"+nd.getFirstAttributeValue("BIOPAX_NODE_TYPE")+":\t"+nd.Id+"\t"+resultNodeList.size()+" found";
    System.out.print(mes);
    BioPAXIndexRepository.getInstance().addToReport(mes);
    System.out.print(":");
    BioPAXIndexRepository.getInstance().addToReport(":");
    for(int k=0;k<resultNodeList.size();k++){
      fr.curie.BiNoM.pathways.analysis.structure.Node n = (Node)resultNodeList.get(k);
      String uri = n.getFirstAttributeValue("BIOPAX_URI");
      uri = Utils.cutUri(uri);
      if(uri.length()>20) uri = uri.substring(0,20)+"..";
      mes = "\t"+n.Id+"("+uri+")";
      System.out.print(mes);
      BioPAXIndexRepository.getInstance().addToReport(mes);
      query.result.addNode(n);
    }
    System.out.print("\n");
    BioPAXIndexRepository.getInstance().addToReport("\n");
  }else{
    total++;
    String mes = ""+(total)+"\t"+nd.getFirstAttributeValue("BIOPAX_NODE_TYPE")+":\t"+nd.Id+"\t"+"NOTHING\n";
    System.out.print(mes);
    BioPAXIndexRepository.getInstance().addToReport(mes);
  } }
  }
  if(!excludeSmallMolecules){
	 String mes = "Total "+found+" proteins/genes/smallMolecules from the list were identified and "+query.result.Nodes.size()+" distinct entities have been found\n";
     System.out.print(mes);
     BioPAXIndexRepository.getInstance().addToReport(mes);
  }
  else{
	 String mes = "Total "+found+" proteins/genes from the list were identified and "+query.result.Nodes.size()+" distinct entities have been found\n";
     System.out.print(mes);
     BioPAXIndexRepository.getInstance().addToReport(mes);
  }
}

/**
 * Setting the BioPAX index file for performing the query 
 */
public void setDatabase(edu.rpi.cs.xgmml.GraphDocument xgml){
  System.out.println("Converting to graph..."); Date tm = new Date();
  database = XGMML.convertXGMMLToGraph(xgml);
  System.out.println("Time spent "+((new Date()).getTime()-tm.getTime()));
  setDatabase(database);
}

/**
 * Setting the BioPAX index file for performing the query
 * @param graph
 */
public void setDatabase(Graph graph){
  database = graph;
  entitySynonym = new HashMap();
  entityXREF = new HashMap();
  System.out.println("Found "+database.Nodes.size()+" nodes");
  for(int i=0;i<database.Nodes.size();i++){
     //if(i==(int)(0.001f*i)*1000)
     //  System.out.print(i+" ");
     fr.curie.BiNoM.pathways.analysis.structure.Node nd = (fr.curie.BiNoM.pathways.analysis.structure.Node)database.Nodes.get(i);
     Vector syns = nd.getAttributeValues("BIOPAX_NODE_SYNONYM");
     for(int j=0;j<syns.size();j++){
        String s = (String)syns.get(j); s = s.toLowerCase();
        /*if(entitySynonym.get(s)!=null){
          fr.curie.BiNoM.pathways.analysis.structure.Node nd1 = (fr.curie.BiNoM.pathways.analysis.structure.Node)entitySynonym.get(s);
          System.out.println("WARNING!!! Duplicate name "+s+" for "+nd1.Id+" and "+nd.Id);
        }*/
        Vector v = (Vector)entitySynonym.get(s);
        if(v==null) v = new Vector();
        v.add(nd); entitySynonym.put(s,v);
        //System.out.println("Put "+s);
     }
     Vector xrefs = nd.getAttributeValues("BIOPAX_NODE_XREF");
     for(int j=0;j<xrefs.size();j++){
        String s = (String)xrefs.get(j); s = s.toLowerCase();
        /*if(entityXREF.get(s)!=null){
          fr.curie.BiNoM.pathways.analysis.structure.Node nd1 = (fr.curie.BiNoM.pathways.analysis.structure.Node)entityXREF.get(s);
          System.out.println("WARNING!!! Duplicate XREF "+s+" for "+nd1.Id+" and "+nd.Id);
        }*/
        Vector v = (Vector)entityXREF.get(s);
        if(v==null) v = new Vector();
        v.add(nd); entityXREF.put(correctXREF(s),v);
        //System.out.println("Put "+s);
     }
     // we also add the BioPAX uri to XREF
     String s = nd.getFirstAttributeValue("BIOPAX_URI");
     if(nd.getFirstAttributeValue("BIOPAX_SPECIES")==null)
     if(s!=null){
    	 s = Utils.cutUri(s);
    	 Vector v = new Vector(); v.add(nd);
    	 entityXREF.put(correctXREF(s).toLowerCase(),v);
     }
  }
  System.out.println();
}

/**
 * Result of the query as GraphDocument object
 * @return
 */
public GraphDocument resultAsXgmml(){
  GraphDocument gr = XGMML.convertGraphToXGMML(query.result);
  return gr;
}

private void addComplexes(boolean expand){
  database.calcNodesInOut();
  query.result = new fr.curie.BiNoM.pathways.analysis.structure.Graph();
  for(int i=0;i<query.input.Nodes.size();i++){
    Node n = (Node)query.input.Nodes.get(i);
    //System.out.println((i+1)+":"+n.NodeLabel);
    query.result.addNode(n);
    Node nd = (Node)database.getNode(n.Id);
    if(nd==null)
    	System.out.println("ERROR: "+n.Id+" is not found in index!!!");
    else
    for(int j=0;j<nd.outcomingEdges.size();j++){
      Edge e = (Edge)nd.outcomingEdges.get(j);
      //System.out.println(e.getFirstAttributeValue("BIOPAX_EDGE_TYPE"));
      String type = e.getFirstAttributeValue("BIOPAX_EDGE_TYPE");
      if(type==null){
    	  System.out.println("WARNING: "+e.Id+" BIOPAX_EDGE_TYPE is not found");
    	  type = "";
      }
      if(type.equals("CONTAINS")){
        if(e.Node2.getFirstAttributeValue("BIOPAX_NODE_TYPE").equals("complex")){
          boolean add = true;
          if(!expand){
            for(int k=0;k<e.Node2.incomingEdges.size();k++){
              Edge ec = (Edge)e.Node2.incomingEdges.get(k);
              String id = ec.Node1.Id;
              if(query.input.getNode(id)==null)
                add = false;
            }
            /*Vector components = e.Node2.getAttributeValues("BIOPAX_ENTITY_COMPONENT");
            for(int k=0;k<components.size();k++){
              String compid = (String)components.get(k);
              if(query.input.getNode(compid)==null)
                add = false;
            }*/
          }
          if(add){
            query.result.addNode(e.Node2);
            String mes = n.NodeLabel+"\t"+e.Node2.NodeLabel+"\n";
            System.out.print(mes);
            BioPAXIndexRepository.getInstance().addToReport(mes);
            // Now add complex components to the graph
            if(expand){
              Node complex = e.Node2;
              for(int k=0;k<complex.incomingEdges.size();k++){
                Edge ei = (Edge)complex.incomingEdges.get(k);
                if(ei.getFirstAttributeValue("BIOPAX_EDGE_TYPE").equals("CONTAINS")){
                  query.result.addNode(ei.Node1);
                  mes = "Component:\t"+ei.Node1.NodeLabel+"\n";
                  System.out.print(mes);
                  BioPAXIndexRepository.getInstance().addToReport(mes);
                }
              }
            }
          }
        }
      }
    }
  }
  query.result.addConnections(database);
  String mes = (query.result.Nodes.size()-query.input.Nodes.size())+" complexes added\n";
  System.out.print(mes);
  BioPAXIndexRepository.getInstance().addToReport(mes);
}

private void addSpecies(){
  database.calcNodesInOut();
  query.result = new fr.curie.BiNoM.pathways.analysis.structure.Graph();
  for(int i=0;i<query.input.Nodes.size();i++){
    Node n = (Node)query.input.Nodes.get(i);
    query.result.addNode(n);
    Node nd = (Node)database.getNode(n.Id);
    if(nd==null){
      String mes = "QUERY ERROR: "+n.Id+" is not in the database";
      System.out.println(mes);
      BioPAXIndexRepository.getInstance().addToReport(mes);
    }
    else{
    Vector species = new Vector();
    for(int j=0;j<nd.outcomingEdges.size();j++){
      Edge e = (Edge)nd.outcomingEdges.get(j);
      String type = e.getFirstAttributeValue("BIOPAX_EDGE_TYPE");
      if(type.equals("SPECIESOF")){
        if(e.Node2.getFirstAttributeValue("BIOPAX_SPECIES")!=null){
            query.result.addNode(e.Node2);
            //System.out.print(n.NodeLabel+"\t"+e.Node2.NodeLabel+"\n");
            species.add(e.Node2);
        }
      }
    }
    String mes = "\n"+(i+1)+"."+n.NodeLabel+" "+n.getFirstAttributeValue("BIOPAX_NODE_TYPE")+" ("+species.size()+" species):\n";
    System.out.print(mes);
    BioPAXIndexRepository.getInstance().addToReport(mes);
    for(int k=0;k<species.size();k++){
       mes = "   "+(k+1)+") "+((Node)species.get(k)).NodeLabel+"\n";
       System.out.print(mes);
       BioPAXIndexRepository.getInstance().addToReport(mes);
    }
    }
  }
  query.result.addConnections(database);
  String mes = (query.result.Nodes.size()-query.input.Nodes.size())+" species added\n";
  System.out.print(mes);
  BioPAXIndexRepository.getInstance().addToReport(mes);
}

private void addConnectingReactions(){
  database.calcNodesInOut();
  query.result = new fr.curie.BiNoM.pathways.analysis.structure.Graph();
  int nreactions = 0;
  int leftrightreactions = 0;
  for(int i=0;i<query.input.Nodes.size();i++){
    Node n = (Node)query.input.Nodes.get(i);
    query.result.addNode(n);
    Node nd = (Node)database.getNode(n.Id);
    if(nd==null){
      String mes = "QUERY ERROR: "+n.Id+" is not in the database";
      System.out.println(mes);
      BioPAXIndexRepository.getInstance().addToReport(mes);
    }else{
    if(nd.getFirstAttributeValue("BIOPAX_SPECIES")!=null){
      // as reactant
      boolean include1 = false, include2 = false;
      boolean nospecies = false;
      for(int j=0;j<nd.outcomingEdges.size();j++){
          Edge e = (Edge)nd.outcomingEdges.get(j);
          if(e.Node2.getFirstAttributeValue("BIOPAX_REACTION")!=null){
            include1 = false; nospecies = true;
            for(int k=0;k<e.Node2.outcomingEdges.size();k++){
              Edge es = (Edge)e.Node2.outcomingEdges.get(k);
              String id = es.Node2.Id;
              if(es.Node2.getFirstAttributeValue("BIOPAX_SPECIES")!=null)
              if((!excludeSmallMolecules)||(!es.Node2.getFirstAttributeValue("BIOPAX_NODE_TYPE").equals("smallMolecule")))
                  nospecies = false;
              //if((query.input.getNode(id)!=null)&&(!id.equals(nd.Id)))
              if(query.input.getNode(id)!=null)
                include1 = true;
            }
          }
          if(include1||nospecies){
            if(query.result.getNode(e.Node2.Id)==null){
              nreactions++;
              String effect = "";
              if(e.Node2.getFirstAttributeValue("EFFECT")!=null)
            	  effect = e.Node2.getFirstAttributeValue("EFFECT");
              String mes = "\n"+nreactions+". "+e.Node2.NodeLabel+"\t"+e.Node2.getFirstAttributeValue("BIOPAX_NODE_ID")+"\t"+effect+"\n";
              System.out.print(mes);
              BioPAXIndexRepository.getInstance().addToReport(mes);
              for(int k=0;k<e.Node2.incomingEdges.size();k++){
                Edge e1 = (Edge)e.Node2.incomingEdges.get(k);
                mes = "\t"+e1.EdgeLabel+"->"+e1.Node1.NodeLabel+"\n";
                System.out.print(mes);
                BioPAXIndexRepository.getInstance().addToReport(mes);
              }
              for(int k=0;k<e.Node2.outcomingEdges.size();k++){
                Edge e1 = (Edge)e.Node2.outcomingEdges.get(k);
                mes = "\t"+e1.EdgeLabel+"<-"+e1.Node2.NodeLabel+"\n";
                System.out.print(mes);
                BioPAXIndexRepository.getInstance().addToReport(mes);
              }
            }
            query.result.addNode(e.Node2);
          }
      }
      // as product
      for(int j=0;j<nd.incomingEdges.size();j++){
          Edge e = (Edge)nd.incomingEdges.get(j);
          if(e.Node1.getFirstAttributeValue("BIOPAX_REACTION")!=null){
            include2 = false; nospecies = true;
            for(int k=0;k<e.Node1.incomingEdges.size();k++){
              Edge es = (Edge)e.Node1.incomingEdges.get(k);
              String id = es.Node1.Id;
              if(es.Node1.getFirstAttributeValue("BIOPAX_SPECIES")!=null)
              if((!excludeSmallMolecules)||(!es.Node1.getFirstAttributeValue("BIOPAX_NODE_TYPE").equals("smallMolecule")))            	  
                nospecies = false;
              //if((query.input.getNode(id)!=null)&&(!id.equals(nd.Id)))
              if(query.input.getNode(id)!=null)
                include2 = true;
            }
          }
          if(include2||nospecies){
            if(query.result.getNode(e.Node1.Id)==null){
              nreactions++;
              String effect = "";
              if(e.Node1.getFirstAttributeValue("EFFECT")!=null)
            	  effect = e.Node1.getFirstAttributeValue("EFFECT");
              String mes = "\n"+nreactions+". "+e.Node1.NodeLabel+"\t"+e.Node1.getFirstAttributeValue("BIOPAX_NODE_ID")+"\t"+effect+"\n";
              System.out.print(mes);
              BioPAXIndexRepository.getInstance().addToReport(mes);
              for(int k=0;k<e.Node1.incomingEdges.size();k++){
                Edge e1 = (Edge)e.Node1.incomingEdges.get(k);
                mes = "\t"+e1.EdgeLabel+"->"+e1.Node1.NodeLabel+"\n";
                System.out.print(mes);
                BioPAXIndexRepository.getInstance().addToReport(mes);
              }
              for(int k=0;k<e.Node1.outcomingEdges.size();k++){
                Edge e1 = (Edge)e.Node1.outcomingEdges.get(k);
                mes = "\t"+e1.EdgeLabel+"<-"+e1.Node2.NodeLabel+"\n";
                System.out.print(mes);
                BioPAXIndexRepository.getInstance().addToReport(mes);
              }
            }
            query.result.addNode(e.Node1);
          }
      }
    }
    }
  }
  query.result.addConnections(database);
  for(int i=0;i<query.result.Nodes.size();i++){
    Node nd = (Node)query.result.Nodes.get(i);
    if(nd.getFirstAttributeValue("BIOPAX_REACTION")!=null){
      boolean left = false, right = false;
      for(int j=0;j<nd.incomingEdges.size();j++){
        Edge e = (Edge)nd.incomingEdges.get(j);
        if(e.getFirstAttributeValue("BIOPAX_EDGE_TYPE").equals("LEFT"))
          left = true;
      }
      for(int j=0;j<nd.outcomingEdges.size();j++){
        Edge e = (Edge)nd.outcomingEdges.get(j);
        if(e.getFirstAttributeValue("BIOPAX_EDGE_TYPE").equals("RIGHT"))
          right = true;
      }
      if(left&&right){
        leftrightreactions++;
      }
    }
  }
  String mes = (query.result.Nodes.size()-query.input.Nodes.size())+" reactions added ("+leftrightreactions+" from them complete)\n";
  System.out.print(mes);
  BioPAXIndexRepository.getInstance().addToReport(mes);
}

private void addPublications(){
  database.calcNodesInOut();
  query.result = new fr.curie.BiNoM.pathways.analysis.structure.Graph();
  int npubs = 0;
  for(int i=0;i<query.input.Nodes.size();i++){
    Node n = (Node)query.input.Nodes.get(i);
    query.result.addNode(n);
    Node nd = (Node)database.getNode(n.Id);
    if(nd==null){
      String mes = "QUERY ERROR: "+n.Id+" is not in the database";
      System.out.println(mes);
      BioPAXIndexRepository.getInstance().addToReport(mes);
    }else{
      for(int j=0;j<nd.incomingEdges.size();j++){
          Edge e = (Edge)nd.incomingEdges.get(j);
          if(e.Node1.getFirstAttributeValue("BIOPAX_NODE_TYPE").equals("publication")){
            e.Node1.NodeLabel = e.Node1.NodeLabel.replace('@','\t');
            if(query.result.getNode(e.Node1.Id)==null){
              String mes = (++npubs)+"\t"+e.Node1.NodeLabel+"\n";
              System.out.print(mes);
              BioPAXIndexRepository.getInstance().addToReport(mes);
              query.result.addNode(e.Node1);
            }
          }
      }
    }
  }
  query.result.addConnections(database);
  String mes = (query.result.Nodes.size()-query.input.Nodes.size())+" publications added\n";
  System.out.print(mes);
  BioPAXIndexRepository.getInstance().addToReport(mes);
}

private void listPublications(){
  database.calcNodesInOut();
  HashMap pubs = new HashMap();
  query.result = new fr.curie.BiNoM.pathways.analysis.structure.Graph();
  int npubs = 0;
  for(int i=0;i<query.input.Nodes.size();i++){
    Node n = (Node)query.input.Nodes.get(i);
    query.result.addNode(n);
    Node nd = (Node)database.getNode(n.Id);
    if(nd==null){
      String mes = "QUERY ERROR: "+n.Id+" is not in the database"; 
      System.out.println(mes);
      BioPAXIndexRepository.getInstance().addToReport(mes);
    }else{
      for(int j=0;j<nd.incomingEdges.size();j++){
          Edge e = (Edge)nd.incomingEdges.get(j);
          if(e.Node1.getFirstAttributeValue("BIOPAX_NODE_TYPE").equals("publication")){
            if(pubs.get(e.Node1.NodeLabel)==null){
              e.Node1.NodeLabel = e.Node1.NodeLabel.replace('@','\t');
              String mes = (++npubs)+"\t"+e.Node1.NodeLabel+"\n";
              System.out.print(mes);
              BioPAXIndexRepository.getInstance().addToReport(mes);
              pubs.put(e.Node1.NodeLabel,e.Node1);
            }
          }
      }
    }
  }
}

private void listObjects(String type){
  HashMap objects = new HashMap();
  query.result = new fr.curie.BiNoM.pathways.analysis.structure.Graph();
  int nobjects = 0;
  for(int i=0;i<query.input.Nodes.size();i++){
    Node n = (Node)query.input.Nodes.get(i);
    query.result.addNode(n);
    Node nd = (Node)database.getNode(n.Id);
    //System.out.println(nd.getFirstAttributeValue("BIOPAX_NODE_TYPE"));
    if(nd==null){
      System.out.println("QUERY ERROR: "+n.Id+" is not in the database");
    }else{
      if((nd.getFirstAttributeValue("BIOPAX_NODE_TYPE").equals(type))&&(nd.getFirstAttributeValue("BIOPAX_SPECIES")==null)){
        if(objects.get(n.Id)==null){
          objects.put(n.Id,n);
          String mes = type+"\t"+(++nobjects)+"\t"+n.Id;
          System.out.println(mes);
          BioPAXIndexRepository.getInstance().addToReport(mes);
        }
      }
    }
  }
}


private static String correctXREF(String s){
  String scor = s;
  if(!s.equals("")){
    StringTokenizer st = new StringTokenizer(s,"@");
    String id = st.nextToken();
    String db = "";
    if(st.hasMoreTokens())
        db = st.nextToken();
    //if(db.toLowerCase().startsWith("uniprot"))
      if(db.indexOf("_")>=0){
        db = db.substring(0,db.indexOf("_"));
        scor = id+"@"+db;
      }
  }
  return scor;
}

private void completeReactions(){
  database.calcNodesInOut();
  query.result = new fr.curie.BiNoM.pathways.analysis.structure.Graph();
  int nreactions = 0;
  for(int i=0;i<query.input.Nodes.size();i++){
    Node n = (Node)query.input.Nodes.get(i);
    query.result.addNode(n);
    Node nd = (Node)database.getNode(n.Id);
    if(nd==null){
      String mes = "QUERY ERROR: "+n.Id+" is not in the database";
      System.out.println(mes);
      BioPAXIndexRepository.getInstance().addToReport(mes);
    }else{
    if(nd.getFirstAttributeValue("BIOPAX_REACTION")!=null){
      for(int j=0;j<nd.incomingEdges.size();j++){
          Edge e = (Edge)nd.incomingEdges.get(j);
          if(query.result.getNode(e.Node1.Id)==null)
            query.result.addNode(e.Node1);
      }
      for(int j=0;j<nd.outcomingEdges.size();j++){
          Edge e = (Edge)nd.outcomingEdges.get(j);
          if(query.result.getNode(e.Node2.Id)==null)
            query.result.addNode(e.Node2);
      }
    }
    }
  }
  query.result.addConnections(database);
  String mes = (query.result.Nodes.size()-query.input.Nodes.size())+" nodes added\n";
  System.out.print(mes);
  BioPAXIndexRepository.getInstance().addToReport(mes);
}


private void addAllReactions(){
  database.calcNodesInOut();
  query.result = new fr.curie.BiNoM.pathways.analysis.structure.Graph();
  int nreactions = 0;
  for(int i=0;i<query.input.Nodes.size();i++){
    Node n = (Node)query.input.Nodes.get(i);
    query.result.addNode(n);
    Node nd = (Node)database.getNode(n.Id);
    if(nd==null){
      String mes = "QUERY ERROR: "+n.Id+" is not in the database";
      System.out.println(mes);
      BioPAXIndexRepository.getInstance().addToReport(mes);
    }else{
    if(nd.getFirstAttributeValue("BIOPAX_SPECIES")!=null){
      // as reactant
      boolean include1 = false, include2 = false;
      boolean nospecies = false;
      for(int j=0;j<nd.outcomingEdges.size();j++){
          Edge e = (Edge)nd.outcomingEdges.get(j);
          if(e.Node2.getFirstAttributeValue("BIOPAX_REACTION")!=null){
            include1 = false; nospecies = true;
            for(int k=0;k<e.Node2.outcomingEdges.size();k++){
              Edge es = (Edge)e.Node2.outcomingEdges.get(k);
              String id = es.Node2.Id;
              if(es.Node2.getFirstAttributeValue("BIOPAX_SPECIES")!=null)
                nospecies = false;
              if(query.input.getNode(id)!=null)
                include1 = true;
            }
          }
          /*if(include1||nospecies)*/{
            if(query.result.getNode(e.Node2.Id)==null){
              nreactions++;
              String mes = "\n"+nreactions+". "+e.Node2.NodeLabel+"\n";
              System.out.print(mes);
              BioPAXIndexRepository.getInstance().addToReport(mes);
              for(int k=0;k<e.Node2.incomingEdges.size();k++){
                Edge e1 = (Edge)e.Node2.incomingEdges.get(k);
                mes = "\t"+e1.EdgeLabel+"->"+e1.Node1.NodeLabel+"\n";
                System.out.print(mes);
                BioPAXIndexRepository.getInstance().addToReport(mes);
              }
              for(int k=0;k<e.Node2.outcomingEdges.size();k++){
                Edge e1 = (Edge)e.Node2.outcomingEdges.get(k);
                mes = "\t"+e1.EdgeLabel+"<-"+e1.Node2.NodeLabel+"\n"; 
                System.out.print(mes);
                BioPAXIndexRepository.getInstance().addToReport(mes);
              }
            }
            query.result.addNode(e.Node2);
          }
      }
      // as product
      for(int j=0;j<nd.incomingEdges.size();j++){
          Edge e = (Edge)nd.incomingEdges.get(j);
          if(e.Node1.getFirstAttributeValue("BIOPAX_REACTION")!=null){
            include2 = false; nospecies = true;
            for(int k=0;k<e.Node1.incomingEdges.size();k++){
              Edge es = (Edge)e.Node1.incomingEdges.get(k);
              String id = es.Node1.Id;
              if(es.Node1.getFirstAttributeValue("BIOPAX_SPECIES")!=null)
                nospecies = false;
              if(query.input.getNode(id)!=null)
                include2 = true;
            }
          }
          /*if(include2)*/{
            if(query.result.getNode(e.Node1.Id)==null){
              nreactions++;
              System.out.print("\n"+nreactions+". "+e.Node1.NodeLabel+"\n");
              for(int k=0;k<e.Node1.incomingEdges.size();k++){
                Edge e1 = (Edge)e.Node1.incomingEdges.get(k);
                String mes = "\t"+e1.EdgeLabel+"->"+e1.Node1.NodeLabel+"\n";
                System.out.print(mes);
                BioPAXIndexRepository.getInstance().addToReport(mes);
              }
              for(int k=0;k<e.Node1.outcomingEdges.size();k++){
                Edge e1 = (Edge)e.Node1.outcomingEdges.get(k);
                String mes = "\t"+e1.EdgeLabel+"<-"+e1.Node2.NodeLabel+"\n";
                System.out.print(mes);
                BioPAXIndexRepository.getInstance().addToReport(mes);
              }
            }
            query.result.addNode(e.Node1);
          }
      }
    }
    }
  }
  query.result.addConnections(database);
  String mes = (query.result.Nodes.size()-query.input.Nodes.size())+" reactions added\n";
  System.out.print(mes);
  BioPAXIndexRepository.getInstance().addToReport(mes);
}

/**
 * 
 * @param entities List of entities 
 * @param numbers Empty vector in which after Integer numbers are found
 */
public void countEntities(Vector entities, Vector numbers){
	int countComplexes = 1;
	for(int i=0;i<database.Nodes.size();i++){
		Node n = (Node)database.Nodes.get(i);
		String typ = n.getFirstAttributeValue("BIOPAX_NODE_TYPE");
		if(n.getFirstAttributeValue("BIOPAX_SPECIES")==null)
		if((typ!=null)&&(!typ.equals(""))){
			int k = entities.indexOf(typ);
			if(k==-1){
				entities.add(typ);
				numbers.add(new Integer(0));
				k = entities.size()-1;
			}
			Integer numb = (Integer)numbers.get(k);
			numbers.set(k, new Integer(numb.intValue()+1));
			if(typ.equals("complex"))
				System.out.println((countComplexes++)+"\t"+n.Id);
		}
	}
}

/**
 * Prepares a copy of database for Index Path Analysis.
 * Removes publications, pathways, pathwaySteps, revert direction of some edges.
 */
public void prepareDatabaseCopyForIndexPathAnalysis(){
	databaseCopyForPathAnalysis = database.makeCopy();
	System.out.print("Removing publication\t");
	BiographUtils.RemoveNodesOfType(databaseCopyForPathAnalysis, "NODE_TYPE", "publication");
	System.out.print("pathway\t");
	BiographUtils.RemoveNodesOfType(databaseCopyForPathAnalysis, "NODE_TYPE", "pathway");
	System.out.print("pathwayStep\t");
	BiographUtils.RemoveNodesOfType(databaseCopyForPathAnalysis, "NODE_TYPE", "pathwayStep");
	if(excludeSmallMolecules){
		System.out.print("smallMolecules\t");
	    BiographUtils.RemoveNodesOfType(databaseCopyForPathAnalysis, "NODE_TYPE", "smallMolecule");
	}
	System.out.println("catalysis\t");
	BiographUtils.RemoveNodesOfType(databaseCopyForPathAnalysis, "NODE_TYPE", "catalysis");
	databaseCopyForPathAnalysis.removeObsoleteEdges();
	databaseCopyForPathAnalysis.calcNodesInOut();
	System.out.println("Doubling edges");
	for(int i=0;i<databaseCopyForPathAnalysis.Edges.size();i++){
		Edge e = (Edge)databaseCopyForPathAnalysis.Edges.get(i);
		String type = e.getFirstAttributeValue("BIOPAX_EDGE_TYPE");
		if(type!=null){
		if(type.equals("CONTAINS")||type.equals("physicalInteraction")||type.equals("SPECIESOF"))
			databaseCopyForPathAnalysis.makeEdgeDoubleSense(e.Id);
		}
	}
}

}