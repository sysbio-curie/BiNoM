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

package fr.curie.BiNoM.pathways.analysis.structure;

import java.io.*;
import java.util.*;
import java.awt.*;

import vdaoengine.utils.Utils;

/**
 * Set of functions using specific graph node semantics of BiNoM 
 *
 */
public class BiographUtils extends Graph {

  public static boolean mapSignOfConservationCoeffs = false;

  public BiographUtils() {
  }
  
  public static void main(String[] args) {
    BiographUtils biographUtils1 = new BiographUtils();
  }

  /**
   * Finds all reactions on the graph gri having only one reactant and one product
   * and no modifiers and substitutes them by edge.
   * The type of the edge is guessed from any reaction Attribute, having "EFFECT" 
   * substring in its name (like "TRANSPATH_EFFECT:"). If the attribute's value
   * contains 'activation' or 'inhibition' substring (or some others), the
   * corresponding edge sign is assigned
   * @param gri
   * @return
   */
  public static Graph ShowMonoMolecularReactionsAsEdges(Graph gri){
  Graph gr = gri.makeCopy();
  gri.calcNodesInOut();
  for(int i=0;i<gri.Nodes.size();i++){
    Node n = (Node)gri.Nodes.elementAt(i);
    if(n.getAttributesWithSubstringInName("REACTION").size()>0)
    	n.NodeClass = 1;
    if(n.NodeClass==1){
      if((n.incomingEdges.size()==1)&&(n.outcomingEdges.size()==1)){
        Edge ein = (Edge)n.incomingEdges.elementAt(0);
        Edge eout = (Edge)n.outcomingEdges.elementAt(0);
        gr.removeNode(n.Id);
        Edge e = new Edge();
        e.Node1 = ein.Node1;
        e.Node2 = eout.Node2;
        gr.Edges.add(e);
        e.Id = n.Id;
        int sign_in = 0;
        int sign_out = 1;
        String edgetype = "";
        Vector vin = ein.getAttributesWithSubstringInName("TYPE");
        Vector vinef = ein.getAttributesWithSubstringInName("EFFECT");
        for(int kk=0;kk<vinef.size();kk++)
        	vin.add(vinef.get(kk));
        for(int kk=0;kk<vin.size();kk++){
        	Attribute at = (Attribute)vin.get(kk);
        	if(at.value!=null){
        	if(at.value.toLowerCase().indexOf("catalysis")>=0)
        		edgetype = at.value;
        	if(at.value.toLowerCase().indexOf("activation")>=0)
        		sign_in = 1;
        	if(at.value.toLowerCase().indexOf("inhibition")>=0)
        		sign_in = -1;
        	if(at.value.toLowerCase().indexOf("suppression")>=0)
        		sign_in = -1;
        	}
        }
        
        for(int kk=0;kk<n.Attributes.size();kk++){
        	Attribute at = (Attribute)n.Attributes.get(kk);
        	if(at.value!=null){
        	if(at.value.toLowerCase().indexOf("effect")>=0){
        		if(at.value.toLowerCase().indexOf("activation")>=0) sign_in = 1;
        		if(at.value.toLowerCase().indexOf("expression")>=0) sign_in = 1;        		
        		if(at.value.toLowerCase().indexOf("inhibition")>=0) sign_in = -1;
        		if(at.value.toLowerCase().indexOf("suppression")>=0) sign_in = -1;
        		if(at.value.toLowerCase().indexOf("repression")>=0) sign_in = -1;
        	}
        	e.Attributes.add(at);
        	}
        }
        
        if(sign_in*sign_out<0){
        	e.setAttributeValueUnique("BIOPAX_EDGE_TYPE", "INHIBITION",Attribute.ATTRIBUTE_TYPE_STRING);
        	e.setAttributeValueUnique("interaction", "INHIBITION",Attribute.ATTRIBUTE_TYPE_STRING);
        	e.setAttributeValueUnique("CELLDESIGNGER_EDGE_TYPE", "INHIBITION",Attribute.ATTRIBUTE_TYPE_STRING);
        }
        if(sign_in*sign_out>0){
        	e.setAttributeValueUnique("BIOPAX_EDGE_TYPE", "ACTIVATION",Attribute.ATTRIBUTE_TYPE_STRING);
        	e.setAttributeValueUnique("interaction", "ACTIVATION",Attribute.ATTRIBUTE_TYPE_STRING);
        	e.setAttributeValueUnique("CELLDESIGNGER_EDGE_TYPE", "ACTIVATION",Attribute.ATTRIBUTE_TYPE_STRING);
        }
        if(!edgetype.equals("")){
        	e.setAttributeValueUnique("BIOPAX_EDGE_TYPE", edgetype,Attribute.ATTRIBUTE_TYPE_STRING);
        	e.setAttributeValueUnique("interaction", "UNDEFINED",Attribute.ATTRIBUTE_TYPE_STRING);
        	e.setAttributeValueUnique("CELLDESIGNGER_EDGE_TYPE", edgetype,Attribute.ATTRIBUTE_TYPE_STRING);
        }
        
        /*e.Id = e.Node1.Id+"_"+e.Node2.Id;
        String id = e.Node2.Id+"_"+e.Node1.Id;
        int ek = gr.getEdgeIndex(id);
        if(ek>=0)
          e.EdgeLabel = n.NodeInfo+"_";
        else
          e.EdgeLabel = n.NodeInfo;
        if(ein.EdgeLabel.indexOf("CATALYSIS")>=0){
          e.EdgeLabel = ein.EdgeLabel;
        }*/
      }
    }
  }
  gr.removeObsoleteEdges();
  return gr;
}

  
  public static Graph LinearizeNetwork(Graph gri){
	  Graph gr = gri.makeCopy();
	  gri.calcNodesInOut();
	  for(int i=0;i<gri.Nodes.size();i++){
		    Node n = (Node)gri.Nodes.elementAt(i);
		    if(n.getAttributesWithSubstringInName("REACTION").size()>0)
		    	n.NodeClass = 1;
		    if(n.NodeClass==1)if(n.incomingEdges.size()>0)if(n.outcomingEdges.size()>0){
		    	gr.removeNode(n.Id);
		    	String nodeType = "biochemicalReaction";
		    	if(n.getAttributesWithSubstringInName("NODE_TYPE").size()>0)
		    		nodeType = ((Attribute)n.getAttributesWithSubstringInName("NODE_TYPE").get(0)).value;
		    	//System.out.println(n.Id+" nodeType = "+nodeType);
		    	for(int j=0;j<n.outcomingEdges.size();j++)
		    		for(int k=0;k<n.incomingEdges.size();k++){
		    			Edge eout = (Edge)n.incomingEdges.get(k);
		    			String edgeType = "";
		    			if(eout.getAttributesWithSubstringInName("EDGE_TYPE").size()>0){
		    				edgeType = ((Attribute)eout.getAttributesWithSubstringInName("EDGE_TYPE").get(0)).value;
		    			}
		    			System.out.println(eout.Id+" edgeType = "+edgeType);
		    			Node nin = ((Edge)n.incomingEdges.get(k)).Node1;
		    			Node nout = ((Edge)n.outcomingEdges.get(j)).Node2;
		    			Edge e = new Edge();
		    			e.Id = nin.Id+"_ACTIVATION_"+nout.Id;
		    			if(nodeType.toLowerCase().contains("complex"))
		    				e.Id = nin.Id+"_ACTIVATIONINCOMPLEX_"+nout.Id;
		    			if(nodeType.toLowerCase().contains("heterodimer"))
		    				e.Id = nin.Id+"_ACTIVATIONINCOMPLEX_"+nout.Id;
		    			e.Node1 = nin; e.Node2 = nout;
		    			e.setAttributeValueUnique("BIOPAX_EDGE_TYPE", "ACTIVATION",Attribute.ATTRIBUTE_TYPE_STRING);
		    			e.setAttributeValueUnique("interaction", "ACTIVATION",Attribute.ATTRIBUTE_TYPE_STRING);
		    			e.setAttributeValueUnique("CELLDESIGNGER_EDGE_TYPE", "ACTIVATION",Attribute.ATTRIBUTE_TYPE_STRING);
		    			if(edgeType.toLowerCase().contains("inhibition")){
			    			e.Id = nin.Id+"_INHIBITION_"+nout.Id;		    				
			    			e.setAttributeValueUnique("BIOPAX_EDGE_TYPE", "INHIBITION",Attribute.ATTRIBUTE_TYPE_STRING);
			    			e.setAttributeValueUnique("interaction", "INHIBITION",Attribute.ATTRIBUTE_TYPE_STRING);
			    			e.setAttributeValueUnique("CELLDESIGNGER_EDGE_TYPE", "INHIBITION",Attribute.ATTRIBUTE_TYPE_STRING);
		    			}
		    			gr.addEdge(e);
		    	}	
		    }
	  }
	  gr.removeObsoleteEdges();
	  return gr;
  }
  
  public static Graph ExcludeIntermediateNodes(Graph gri, Vector v, boolean firstPass){
	  Graph gr = gri.makeCopy();
	  gri.calcNodesInOut();
	  int k = 0;
	  //for(int i=0;i<3;i++){
	  while(k<gr.Nodes.size()){
	  //while(k<1){
		    Node n = (Node)gr.Nodes.elementAt(k);
		    if(n.getAttributesWithSubstringInName("REACTION").size()==0)
		    	n.NodeClass = 0;
		    if(((!firstPass)&&v.indexOf(n.Id)>=0)||((firstPass)&&(n.NodeClass==0)&&(n.incomingEdges.size()==1)&&(n.outcomingEdges.size()==1))){
		    	        k--;
		    		    gr.removeNode(n.Id);
		    		    if(firstPass) v.add(n.Id);
		    	        int signin = getEdgeSign((Edge)n.incomingEdges.get(0));
		    	        int signout = getEdgeSign((Edge)n.outcomingEdges.get(0));
		    			Node nin = ((Edge)n.incomingEdges.get(0)).Node1;
		    			Node nout = ((Edge)n.outcomingEdges.get(0)).Node2;
		    			Edge e = new Edge();
		    			e.Node1 = nin; e.Node2 = nout;
		    			e.Id = nin.Id+"_"+((Edge)n.incomingEdges.get(0)).Id+"_"+((Edge)n.outcomingEdges.get(0)).Id+"_"+nout.Id;
		    			int sign = signin*signout;
		    			if(sign>0){
		    				e.setAttributeValueUnique("BIOPAX_EDGE_TYPE", "ACTIVATION",Attribute.ATTRIBUTE_TYPE_STRING);
		    				e.setAttributeValueUnique("interaction", "ACTIVATION",Attribute.ATTRIBUTE_TYPE_STRING);
		    				e.setAttributeValueUnique("CELLDESIGNGER_EDGE_TYPE", "ACTIVATION",Attribute.ATTRIBUTE_TYPE_STRING);
		    			}
		    			if(sign<0){
		    				e.setAttributeValueUnique("BIOPAX_EDGE_TYPE", "INHIBITION",Attribute.ATTRIBUTE_TYPE_STRING);
		    				e.setAttributeValueUnique("interaction", "INHIBITION",Attribute.ATTRIBUTE_TYPE_STRING);
		    				e.setAttributeValueUnique("CELLDESIGNGER_EDGE_TYPE", "INHIBITION",Attribute.ATTRIBUTE_TYPE_STRING);
		    			}
		    			if(sign==0){
		    				e.setAttributeValueUnique("BIOPAX_EDGE_TYPE", "UNDEFINED",Attribute.ATTRIBUTE_TYPE_STRING);
		    				e.setAttributeValueUnique("interaction", "UNDEFINED",Attribute.ATTRIBUTE_TYPE_STRING);
		    				e.setAttributeValueUnique("CELLDESIGNGER_EDGE_TYPE", "UNDEFINED",Attribute.ATTRIBUTE_TYPE_STRING);
		    			}
		    			
		    			Vector uris = n.getAttributesWithSubstringInName("URI");
		    			for(int i=0;i<uris.size();i++) e.Attributes.add(uris.get(i));
		    			uris = ((Edge)n.incomingEdges.get(0)).getAttributesWithSubstringInName("URI");
		    			for(int i=0;i<uris.size();i++) e.Attributes.add(uris.get(i));
		    			uris = ((Edge)n.outcomingEdges.get(0)).getAttributesWithSubstringInName("URI");
		    			for(int i=0;i<uris.size();i++) e.Attributes.add(uris.get(i));
		    			
		    			gr.addEdge(e);
	  }
	  k++;
	  gr.removeObsoleteEdges();
	  gr.calcNodesInOut();
	  }
	  return gr;
  }
  
  public static int getEdgeSign(Edge e){
	  int sign_in = 0;
      for(int kk=0;kk<e.Attributes.size();kk++){
      	Attribute at = (Attribute)e.Attributes.get(kk);
      	if(at.value!=null){
      	if((at.value.toLowerCase().indexOf("effect")>=0)||(at.name.toLowerCase().indexOf("interaction")>=0)||(at.name.toLowerCase().indexOf("effect")>=0)){
      		if(at.value.toLowerCase().indexOf("activation")>=0) sign_in = 1;
      		if(at.value.toLowerCase().indexOf("expression")>=0) sign_in = 1;        		
      		if(at.value.toLowerCase().indexOf("inhibition")>=0) sign_in = -1;
      		if(at.value.toLowerCase().indexOf("suppression")>=0) sign_in = -1;
      		if(at.value.toLowerCase().indexOf("repression")>=0) sign_in = -1;
      	}
      	}
      }
      return sign_in;
  }
  
/**
 * Simply extraxts a subgraph with nodes whose name have label as a substring
 * @param gri
 * @param label
 * @return
 */
public static Graph getSubGraphByLabelInclusions(Graph gri, String label){
  Graph gr = gri.getNodesByLabelInclusion(label);
  gri.calcNodesInOut();
  for(int i=0;i<gri.Nodes.size();i++){
     Node n = (Node)gri.Nodes.elementAt(i);
     if((n.NodeClass==1)||(n.NodeLabel.toLowerCase().startsWith("black")))
     for(int j=0;j<n.incomingEdges.size();j++){
       Edge ein = (Edge)n.incomingEdges.elementAt(j);
       int k = gr.getNodeIndex(ein.Node1.Id);
       if(k>=0){
         for(int l=0;l<n.outcomingEdges.size();l++){
           Edge eout = (Edge)n.outcomingEdges.elementAt(l);
           int k1 = gr.getNodeIndex(eout.Node2.Id);
           if(k1>=0)
             gr.addNode(n);
         }
       }
     }
  }
  gr.addConnections(gri);
  return gr;
}

/**
 * For every protein name, creates a subgraph with all its modifications,
 * complexes, locations
 * @param gri
 * @return
 */
public static Vector calcAllMaterialComponents(Graph gri){
  Vector cyc = new Vector();
  gri.calcNodesInOut();
  for(int i=0;i<gri.Nodes.size();i++){
     Node n = (Node)gri.Nodes.elementAt(i);
     if(n.NodeClass==0){
       Vector v = calcMaterialComponents(gri,n,cyc);
       for(int k=0;k<v.size();k++)
         cyc.add(v.elementAt(k));
     }
  }
  return cyc;
}

private static Vector calcMaterialComponents(Graph gri, Node n, Vector cyc){
  Vector v = new Vector();
  String lab = n.NodeLabel;
  //StringTokenizer st = new StringTokenizer(lab,"/");
  StringTokenizer st = new StringTokenizer(lab,":/");
  while(st.hasMoreTokens()){
    String s = st.nextToken();
    //StringTokenizer st1 = new StringTokenizer(s,"_");
    StringTokenizer st1 = new StringTokenizer(s,"@_");
    String label = st1.nextToken();
    boolean doit = true;
    for(int i=0;i<cyc.size();i++){
      Graph grt = (Graph)cyc.elementAt(i);
      if((grt.name.equals(label))||(grt.name.startsWith(label+"_"))){
        doit = false;
        break;
      }
    }
    if(doit){
      Graph grc = BiographUtils.getSubGraphByLabelInclusions(gri,label);
      System.out.println(label+"\t"+grc.Nodes.size()+" nodes "+grc.Edges.size()+" edges");
      //grc.prune();
      if(grc.Nodes.size()>1){
        grc.name = label;
        /*Vector comps = GraphAlgorithms.ConnectedComponents(grc);
        for(int j=0;j<comps.size();j++){
          Graph comp = (Graph)comps.elementAt(j);
          if(comps.size()==1)
            comp.name = label;
          else
            comp.name = label+"_"+(j+1);
          v.add(comp);
        }*/
        v.add(grc);
      }
    }
  }
  return v;
}

/**
 * Creates a new graph where nodes represent other graphs and edges
 * represent intersections in nodes between these graphs.
 * @param graphs
 * @return
 */
public static Graph inclusionGraph(Vector graphs){
  Graph ig = new Graph();
  int inclusionMatrix[][] = new int[graphs.size()][graphs.size()];
  for(int i=0;i<graphs.size();i++){
    Graph gi = (Graph)graphs.elementAt(i);
    for(int j=0;j<graphs.size();j++){
      Graph gj = (Graph)graphs.elementAt(j);
      //if((gj.name.equals("SWI"))&&(gi.name.equals("SNF")))
      //  System.out.println(gi.name+" "+gj.name+" "+inclusionMatrix[i][j]);
      inclusionMatrix[i][j] = gi.includesNodes(gj).size();
    }
  }

  Graph fullInclusion = new Graph();
  for(int k=0;k<graphs.size();k++){
    Graph gk = (Graph)graphs.elementAt(k);
    Node n = fullInclusion.getCreateNode(gk.name);
    n.NodeLabel = gk.name;
    n.NodeClass = 2;
  }

  for(int i=0;i<graphs.size();i++){
    Graph gi = (Graph)graphs.elementAt(i);
    for(int j=0;j<graphs.size();j++) if(i!=j) {
       Graph gj = (Graph)graphs.elementAt(j);
       if(inclusionMatrix[i][j]==gj.Nodes.size()){
         Edge e = new Edge();
         int ki = fullInclusion.getNodeIndex(gi.name);
         int kj = fullInclusion.getNodeIndex(gj.name);
         e.Node1 = (Node)fullInclusion.Nodes.elementAt(kj);
         e.Node2 = (Node)fullInclusion.Nodes.elementAt(ki);
         if(i>j)
           e.EdgeLabel = "INCLUDED"; //+e.Node1.Id+"_"+e.Node2.Id;
         else
           e.EdgeLabel = "INCLUDED_";
         fullInclusion.Edges.add(e);
       } else
       if(inclusionMatrix[i][j]>0){
         Edge e = new Edge();
         int ki = fullInclusion.getNodeIndex(gi.name);
         int kj = fullInclusion.getNodeIndex(gj.name);
         e.Node1 = (Node)fullInclusion.Nodes.elementAt(kj);
         e.Node2 = (Node)fullInclusion.Nodes.elementAt(ki);
         int inter = inclusionMatrix[i][j];
         float interf = 1f*inter/gj.Nodes.size()*10f;
         inter = (int)interf;
         if(inter>6) inter = 7;
         if(i>j)
           e.EdgeLabel = "INTERSECT_"+inter;
         else
           e.EdgeLabel = "INTERSECT_"+inter+"_";
         fullInclusion.Edges.add(e);
       }
    }
  }
  fullInclusion.assignEdgeIds();
  //fullInclusion.saveAsCytoscapeSif("fullinclusion.sif");

  return fullInclusion;
}


public static Graph mapClassesToNodeProps(Graph gr){
  for(int i=0;i<gr.Nodes.size();i++){
    Node n = (Node)gr.Nodes.elementAt(i);
    switch(n.NodeClass){
      case 0: n.NodeShape = 0; break; // species
      case 1: n.NodeShape = 3; break; // reactions
      case 2: n.NodeShape = 1; break; // cycles
      case 3: n.NodeShape = 5; n.NodeBorderWidth = 2; break; // metanodes
    }
    if(mapSignOfConservationCoeffs)if(n.NodeClass==0){
      if(n.includedInConservationLaw){
      if(n.coefficientInConservationLaw>0)
        n.NodeColor = Color.red;
      if(n.coefficientInConservationLaw<0)
        n.NodeColor = Color.blue;
      }
    }
  }
  return gr;
}

/**
 * Reads a file produced by Structural Analysis GUI software in SB Workbench
 * and finds subgraphs, corresponding to the conservation laws (P-invariants?)
 * @param gr
 * @param fn
 * @param useSpeciesIDs
 * @return list of subgraphs
 */
public static Vector readConservationLaws(Graph gr, String fn, boolean useSpeciesIDs){
  Vector v = new Vector();
  int ln = 0;
  try{
    LineNumberReader lr = new LineNumberReader(new FileReader(fn));
    FileWriter fw = new FileWriter(fn+"_");
    String s = null;
    while((s=lr.readLine())!=null){
      ln++;
      if(s.trim().length()>0){
      StringTokenizer st = new StringTokenizer(s," \t");
      String t1 = st.nextToken();
      String t2 = st.nextToken();
      if(t2.equals(":")){
        Graph grc = new Graph();
        v.add(grc);
        while(st.hasMoreTokens()){
          float mult = 1f;
          String sign = st.nextToken();
          if(sign.equals("-"))
            mult = -mult;
          String id = st.nextToken();
          if(isNumber(id)){
            mult = mult*Float.parseFloat(id);
            id = st.nextToken();
          }
          //if(id.equals("s530"))
          //  System.out.println(id);
          Node n = gr.getNode(id);
          if(n!=null){
            n = n.makeCopy();
            n.includedInConservationLaw = true;
            n.coefficientInConservationLaw = mult;
            //System.out.println(""+mult+" "+id+",");
            grc.Nodes.add(n);
          }
        }
      }
      }
    }

    lr.close();

    for(int i=0;i<v.size();i++){
      Graph g = (Graph)v.elementAt(i);
      fw.write(""+i+"\t:\t");
      for(int j=0;j<g.Nodes.size();j++){
        Node n = (Node)g.Nodes.elementAt(j);
        if(n.coefficientInConservationLaw>0)
          fw.write(" + ");
        else
          fw.write(" - ");
        fw.write(" "+Math.abs(n.coefficientInConservationLaw)+" ");
        fw.write(" "+n.NodeLabel+" ");
      }
      fw.write("\r\n");
    }
    fw.close();

  }catch(Exception e){
    System.out.println("Problem in line "+ln);
    e.printStackTrace();
  }
  return v;
}

public static boolean isNumber(String s){
  boolean r = true;
  try{
    float f = Float.parseFloat(s);
  }catch(Exception e){
    r = false;
  }
  return r;
}

/**
 * Adds to gr all connecting reactions, found in grglobal
 * @param gr
 * @param grglobal
 * @return
 */
public static Graph addCommonReactions(Graph gr, Graph grglobal){
  grglobal.calcNodesInOut();
  for(int i=0;i<grglobal.Nodes.size();i++){
    Node n = (Node)grglobal.Nodes.elementAt(i);
    if(n.NodeClass==1){
      boolean foundIn = false;
      for(int j=0;j<n.incomingEdges.size();j++){
        Edge e = (Edge)n.incomingEdges.elementAt(j);
        if(gr.getNodeIndex(e.Node1.Id)>=0){
          foundIn = true;
          break;
        }
      }
      boolean foundOut = false;
      for(int j=0;j<n.outcomingEdges.size();j++){
        Edge e = (Edge)n.outcomingEdges.elementAt(j);
        if(gr.getNodeIndex(e.Node2.Id)>=0){
          foundOut = true;
          break;
        }
      }
      if(foundIn&&foundOut){
        gr.Nodes.add(n);
      }
    }
  }
  return gr;
}

public static void printSpeciesReactions(Graph gr){
  int k = 1;
  for(int i=0;i<gr.Nodes.size();i++){
    Node n = (Node)gr.Nodes.elementAt(i);
    if(n.NodeClass==0)
       System.out.println("Species ("+(k++)+"): "+n.NodeLabel+" ("+n.Id+")");
  }
  k = 1;
  for(int i=0;i<gr.Nodes.size();i++){
    Node n = (Node)gr.Nodes.elementAt(i);
    if(n.NodeClass==1)
       System.out.println("Reaction ("+(k++)+"): "+n.NodeLabel+" ("+n.Id+")");
  }

}
/**
 * Calculates modular representation of graph global, using Graph.Metanodes list
 * of subgraphs
 * 
 * showIntersections - if true then all metanode intersections will be shown explicitly
 * nodeIntersectionView - if true then all the edges connecting nodes in the intersection will not be shown,
 * 						  but instead for every common node there will be a 'INTERSECT' edge shown
 */
public static Graph CollapseMetaNodes(Graph global, boolean showIntersections, boolean nodeIntersectionView){
    Graph meta = new Graph();
    Graph copy = global.makeCopy();
    // First we will insert metaNodes, excluding their intersections
    for(int i=0;i<global.metaNodes.size();i++){
      Graph gi = (Graph)global.metaNodes.elementAt(i);
      Node n = meta.getCreateNode(gi.name);
      n.NodeLabel = gi.name;
      n.NodeClass = 3;
      Attribute at = new Attribute("BIOPAX_NODE_TYPE","pathway");
      n.Attributes.add(at);
      at = new Attribute("CELLDESIGNER_NODE_TYPE","PATHWAY");
      n.Attributes.add(at);
      n.link = gi;
      copy.removeNodes(gi);
      //System.out.println(""+copy.Nodes.size()+" "+copy.getNodeIndex("re188"));
    }
    
    copy.removeObsoleteEdges();
        
    float intersections[][] = new float[global.metaNodes.size()][global.metaNodes.size()];
    
    // First pass, process all intersections
    for(int i=0;i<global.metaNodes.size();i++){
      Graph gi = (Graph)global.metaNodes.elementAt(i);
      for(int j=i+1;j<global.metaNodes.size();j++){
        Graph gj = (Graph)global.metaNodes.elementAt(j);
        Graph inters = gi.intersection(gj);
        intersections[i][j] = inters.Nodes.size();
        intersections[j][i] = inters.Nodes.size();
        for(int k=0;k<inters.Nodes.size();k++){
          //boolean addNode = false|showIntersections;
          Node in = (Node)inters.Nodes.get(k);
          if(showIntersections)
        	  meta.addNode(in);
          /*if(!showIntersections){
        	  Node tin = global.getNode(in.Id);
        	  for(int kk=0;kk<tin.outcomingEdges.size();kk++){
        		  Edge ee = (Edge)tin.outcomingEdges.get(kk);
        		  if(copy.getNode(ee.Node2.Id)!=null)
        			  addNode = true;
        	  }
        	  for(int kk=0;kk<tin.incomingEdges.size();kk++){
        		  Edge ee = (Edge)tin.incomingEdges.get(kk);
        		  if(copy.getNode(ee.Node1.Id)!=null)
        			  addNode = true;
        	  }
          }*/
          //if(addNode)
          //	  meta.addNode(in);
        }
      }
    }
    // Second pass, add connections
    global.calcNodesInOut();
    for(int i=0;i<global.metaNodes.size();i++){
        Graph gi = (Graph)global.metaNodes.elementAt(i);
        for(int j=i+1;j<global.metaNodes.size();j++){
          Graph gj = (Graph)global.metaNodes.elementAt(j);
          Graph inters = gi.intersection(gj);
          for(int k=0;k<inters.Nodes.size();k++)if(meta.getNode(((Node)inters.Nodes.get(k)).Id)!=null){
            
            Node in = (Node)inters.Nodes.get(k);          
    		Node tin = (Node)global.getNode(in.Id);
   		      		  
            for(int kk=0;kk<tin.outcomingEdges.size();kk++){
          	  Edge ed = (Edge)tin.outcomingEdges.get(kk);
          	  if(meta.getNode(ed.Node2.Id)!=null){
              		Edge ned = ed.copy();
              		meta.addEdgeIdUnique(ned);
           	  }else
          	  if(inters.getNode(ed.Node2.Id)!=null){
            		Edge ned = ed.copy();
            		meta.addEdgeIdUnique(ned);
          	  }else{
          	  if(gi.getNode(ed.Node2.Id)!=null){
          		Edge ned = ed.copy();
          		ned.Node2 = meta.getNode(gi.name);
          		ned.Id+="_in_"+gi.name;
          		meta.addEdgeIdUnique(ned);
          	  }
          	  if(gj.getNode(ed.Node2.Id)!=null){
            		Edge ned = ed.copy();
            		ned.Node2 = meta.getNode(gj.name);
            		ned.Id+="_in_"+gj.name;
            		meta.addEdgeIdUnique(ned);
            	  }}
            }
            for(int kk=0;kk<tin.incomingEdges.size();kk++){
          	  Edge ed = (Edge)tin.incomingEdges.get(kk);
             	  if(meta.getNode(ed.Node1.Id)!=null){
            		Edge ned = ed.copy();
            		meta.addEdgeIdUnique(ned);
             	  }else
          	  if(inters.getNode(ed.Node1.Id)!=null){
              	Edge ned = ed.copy();
            		meta.addEdgeIdUnique(ned);
            	  }else{
          	  if(gi.getNode(ed.Node1.Id)!=null){
          		Edge ned = ed.copy();
          		ned.Node1 = meta.getNode(gi.name);
          		ned.Id = "in_"+gi.name+"_"+ned.Id;
          		meta.addEdgeIdUnique(ned);
          	  }
          	  if(gj.getNode(ed.Node1.Id)!=null){
            		Edge ned = ed.copy();
            		ned.Id = "in_"+gj.name+"_"+ned.Id;
            		ned.Node1 = meta.getNode(gj.name);
            		meta.addEdgeIdUnique(ned);
            	  }}
            }
          }
        }
      }    
    //meta.addConnections(this);
    for(int i=0;i<copy.Nodes.size();i++){
      Node n = (Node)copy.Nodes.elementAt(i);
      meta.addNode(n);
      for(int j=0;j<global.Edges.size();j++){
        Edge e = (Edge)global.Edges.elementAt(j);
        if(n.Id.equals(e.Node1.Id)){
          String id2 = e.Node2.Id;
          for(int k=0;k<global.metaNodes.size();k++){
            Graph mn = (Graph)global.metaNodes.elementAt(k);
            if(mn.getNodeIndex(id2)>=0){
              Edge em = new Edge();
              em.Node1 = n;
              em.Node2 = meta.getNode(mn.name);
              //em.EdgeLabel = e.EdgeLabel;
              em.Attributes = e.Attributes;
              em.Id = e.Id + "_in_"+mn.name;
              meta.addEdgeIdUnique(em);
            }
          }
        }
        if(n.Id.equals(e.Node2.Id)){
          String id1 = e.Node1.Id;
          for(int k=0;k<global.metaNodes.size();k++){
            Graph mn = (Graph)global.metaNodes.elementAt(k);
            if(mn.getNodeIndex(id1)>=0){
              Edge em = new Edge();
              em.Node1 = meta.getNode(mn.name);
              em.Node2 = n;
              //em.EdgeLabel = e.EdgeLabel;
              em.Attributes = e.Attributes;
              em.Id = "in_"+mn.name+"_"+e.Id;
              meta.addEdgeIdUnique(em);
            }
          }
        }
      }
    }
    meta.removeObsoleteEdges();
    meta.addConnections(global);
    //if(!showIntersections)
    	meta.addMetanodeConnections(global, nodeIntersectionView, showIntersections);
    return meta;
  }

  /**
   * From arbitrary interface extract standard Reaction Network interface
   * @param gr
   * @return
   */
  public static Graph ExtractReactionNetwork(Graph gr){
	  Graph rn = new Graph();
	  for(int i=0;i<gr.Nodes.size();i++){
		  Node n = (Node)gr.Nodes.get(i);
		  Vector vs = n.getAttributesWithSubstringInName("SPECIES");
		  Vector vr = n.getAttributesWithSubstringInName("REACTION");
		  if((vs.size()!=0)||(vr.size()!=0)){
			  rn.addNode(n);
		  }
	  }
	  rn.addConnections(gr);
	  return rn;
  }
  
  /**
   * Removes nodes which have an attribute with substringAttName in its name
   * and substringAttValue in its value
   * @param substringAttName
   * @param substringAttValue
   */
  public static void RemoveNodesOfType(Graph gr, String substringAttName, String substringAttValue){
	  int i=0;
	  while(i<gr.Nodes.size()){
		  Node n = (Node)gr.Nodes.get(i);
		  //if((i>0)&&(i==((int)(0.001f*i))*1000))
		  //	  System.out.print(i+"\t");
		  Vector vs = n.getAttributesWithSubstringInName(substringAttName);
		  boolean remove = false;
		  for(int j=0;j<vs.size();j++){
			  Attribute at = (Attribute)vs.get(j);
			  if(at.value.toLowerCase().indexOf(substringAttValue.toLowerCase())>=0)
				  remove = true;
		  }
		  if(remove)
			  gr.removeNode(n.Id);
		  else
			  i++;
	  }
	  //System.out.println("Done");
  }
  
  public static int getPathSign(Path path){
	  int res = 0;
	  Vector<Edge> edges = path.getAsEdgeVector();
	  for(int i=0;i<edges.size();i++)
		  res*=getEdgeSign((Edge)edges.get(i));
	  return res;
  }
  
  public static int getPathSign(Graph graph, String sourceNodeId){
	  Path path = new Path(graph,sourceNodeId);
	  return getPathSign(path);
  }  
  
  /*public static int getEdgeSign(Edge e){
	  int res = 0;
      Vector vin = e.getAttributesWithSubstringInName("TYPE");
      Vector vinef = e.getAttributesWithSubstringInName("EFFECT");
      for(int kk=0;kk<vinef.size();kk++)
      	vin.add(vinef.get(kk));
      for(int kk=0;kk<vin.size();kk++){
      	Attribute at = (Attribute)vin.get(kk);
      	String edgetype = "";
      	if(at.value!=null){
      	if(at.value.toLowerCase().indexOf("catalysis")>=0)
      		edgetype = at.value;
      	if(at.value.toLowerCase().indexOf("activation")>=0)
      		res = 1;
      	if(at.value.toLowerCase().indexOf("inhibition")>=0)
      		res = -1;
      	if(at.value.toLowerCase().indexOf("suppression")>=0)
      		res = -1;
      	}
      }
      return res;
  }*/
  
  public static HashMap getNeighborhoodSets(Graph network, Vector<String> selected, boolean goUpstream, boolean goDownstream, int searchRadius, int minimumNumberOfGenes, boolean takeFromHUGOatt){
	  HashMap<String,Vector<String>> geneSets = new HashMap<String,Vector<String>>();
	  if(selected.size()==0){
		  for(int i=0;i<network.Nodes.size();i++)
			  selected.add(network.Nodes.get(i).Id);
	  }
	  network.calcNodesInOut();
	  for(int i=0;i<selected.size();i++){
		  Node n = (Node)network.getNode(selected.get(i));
		  Vector<Node> nodes = new Vector<Node>();
		  Vector<Node> nodesU = new Vector<Node>();
		  Vector<Node> nodesD = new Vector<Node>();
		  if(goUpstream)
			  nodesU = getNodeNeighbors(network,n,true,false,searchRadius);
		  if(goDownstream)
			  nodesD = getNodeNeighbors(network,n,false,true,searchRadius);
		  for(int k=0;k<nodesU.size();k++) nodes.add(nodesU.get(k));
		  for(int k=0;k<nodesD.size();k++) nodes.add(nodesD.get(k));
		  Vector<String> names = new Vector<String>();
		  if(takeFromHUGOatt){
			  names = getNodeNamesFromAnAttribute(nodes,"HUGO");			  
		  }else{
			  names = BiographUtils.extractProteinNamesFromNodeNames(nodes);
		  }
		  
		  int sr = searchRadius;
		  while(names.size()<minimumNumberOfGenes){
			  sr++;
			  int numberOfNodes = nodes.size();
			  //nodes = getNodeNeighbors(network,n,goUpstream,goDownstream,sr);
			  nodes = new Vector<Node>();
			  nodesU = new Vector<Node>();
			  nodesD = new Vector<Node>();
			  if(goUpstream)
				  nodesU = getNodeNeighbors(network,n,true,false,sr);
			  if(goDownstream)
				  nodesD = getNodeNeighbors(network,n,false,true,sr);
			  for(int k=0;k<nodesU.size();k++) nodes.add(nodesU.get(k));
			  for(int k=0;k<nodesD.size();k++) nodes.add(nodesD.get(k));
			  
			  if(takeFromHUGOatt){
				  names = getNodeNamesFromAnAttribute(nodes,"HUGO");			  
			  }else{
				  names = BiographUtils.extractProteinNamesFromNodeNames(nodes);
			  }
			  if(nodes.size()==numberOfNodes)
				  break;
		  }
		  geneSets.put(n.Id, names);
	  }
	  return geneSets;
  }
  
  public static Vector<String> getNodeNamesFromAnAttribute(Vector<Node> nodes, String attname){
	  Vector<String> names = new Vector<String>(); 
		for(int k=0;k<nodes.size();k++){
			Node node = nodes.get(k);
			Vector<String> pnames_node = new Vector<String>(); 
			Vector<Attribute> atts = node.getAttributesWithSubstringInName(attname);
			for(int j=0;j<atts.size();j++){
				String val = atts.get(j).value;
				val = fr.curie.BiNoM.pathways.utils.Utils.replaceString(val, ",", "");
				val = fr.curie.BiNoM.pathways.utils.Utils.replaceString(val, " ", "");
				StringTokenizer st = new StringTokenizer(val,"@@");
				while(st.hasMoreTokens())
					pnames_node.add(st.nextToken());
			}
			if(pnames_node.size()==0){
				Vector<Node> nodes_node = new Vector<Node>();
				nodes_node.add(node);
				//pnames_node = BiographUtils.extractProteinNamesFromNodeNames(nodes_node);
			}
			for(int j=0;j<pnames_node.size();j++)
				if(names.indexOf(pnames_node.get(j))<0)
					names.add(pnames_node.get(j));
		}
		return names;
  }
  
  public static Vector<Node> getNodeNeighbors(Graph graph, Node node, boolean goUpstream, boolean goDownstream, int searchRadius){
	  Vector<Node> neigh = new Vector<Node>();
	  neigh.add(node);
	  int k=0;
	  for(int i=0;i<searchRadius;i++){
		  int numberOfNeighs = neigh.size(); 
		  for(int j=k;j<numberOfNeighs;j++){
			  Vector<Node> firstNodes = getNodeFirstNeighbors(graph, neigh.get(j),goUpstream,goDownstream);
			  for(int l=0;l<firstNodes.size();l++)
				  if(!neigh.contains(firstNodes.get(l))){
					  neigh.add(firstNodes.get(l));
					  //System.out.println("Added: "+firstNodes.get(l).Id);
				  }
		  }
		  k = numberOfNeighs;
	  }
	  return neigh;
  }
  
  public static Vector<Node> getNodeFirstNeighbors(Graph graph, Node node, boolean goUpstream, boolean goDownstream){
	  Vector<Node> neigh = new Vector<Node>();
	  if(goUpstream)
		  for(int i=0;i<node.incomingEdges.size();i++){
			  Edge e = (Edge)node.incomingEdges.get(i);
			  if(!neigh.contains(e.Node1)) 
				  neigh.add(e.Node1);
		  }
	  if(goDownstream)
		  for(int i=0;i<node.outcomingEdges.size();i++){
			  Edge e = (Edge)node.outcomingEdges.get(i);
			  if(!neigh.contains(e.Node2)) 
				  neigh.add(e.Node2);
		  }
	  return neigh;
  }
  
  public static Vector<String> extractProteinNamesFromNodeNames(Vector<Node> nodes){
	  Vector<String> res = new Vector<String>();
	  for(int i=0;i<nodes.size();i++){
		  Node n = nodes.get(i);
		  Vector v = n.getAttributesWithSubstringInName("REACTION");
		  if(v.size()==0){
			  Vector<String> pnames = extractProteinNamesFromNodeName(n.Id);
			  for(int j=0;j<pnames.size();j++)
				  if(!res.contains(pnames.get(j)))
					  res.add(pnames.get(j));
		  }
	  }
	  return res;
  }
  
  public static Vector<String> extractProteinNamesFromNodeName(String id){
	  Vector<String> names = new Vector<String>();
	  StringTokenizer st = new StringTokenizer(id,"@");
	  String nameWithoutCompartment = st.nextToken();
	  st = new StringTokenizer(nameWithoutCompartment,":");
	  while(st.hasMoreTokens()){
		  String part = st.nextToken();
		  StringTokenizer st1 = new StringTokenizer(part,"|");
		  String proteinName = st1.nextToken();
		  if(!names.contains(proteinName))
			  names.add(proteinName);
	  }
	  return names;
  }
  
  public static Graph MergeNetworkAndFilter(Vector<Graph> graphs, float pourcentage){
	  Graph graph = new Graph();
	  HashMap<String,Integer> nodeCounts = new HashMap<String,Integer>();
	  HashMap<String,Integer> edgeCounts = new HashMap<String,Integer>();
	  for(int i=0;i<graphs.size();i++){
		  Graph gr = graphs.get(i);
		  for(int j=0;j<gr.Nodes.size();j++){
			  Node n = gr.Nodes.get(j);
			  if(graph.getNode(n.Id)!=null){
				  int count = nodeCounts.get(n.Id);
				  nodeCounts.put(n.Id, count+1);
			  }else{
				  graph.addNode(n);
				  nodeCounts.put(n.Id, 1);
			  }
		  }
		  for(int j=0;j<gr.Edges.size();j++){
			  Edge e = gr.Edges.get(j);
			  if(graph.getEdge(e.Id)!=null){
				  int count = edgeCounts.get(e.Id);
				  edgeCounts.put(e.Id, count+1);
			  }else{
				  Node node1 = graph.getNode(e.Node1.Id);
				  Node node2 = graph.getNode(e.Node2.Id);
				  if(node1==null)
					  System.out.println("ERROR in MergeNetworkAndFilter: edge "+e.Id+" Node1=null ("+e.Node1.Id+")");
				  if(node2==null)
					  System.out.println("ERROR in MergeNetworkAndFilter: edge "+e.Id+" Node2=null ("+e.Node2.Id+")");
				  e.Node1 = node1;
				  e.Node2 = node2;
				  graph.addEdge(e);
				  edgeCounts.put(e.Id, 1);
			  }
		  }
	  }
	  Iterator<String> keys = nodeCounts.keySet().iterator();
	  while(keys.hasNext()){
		  String id = keys.next();
		  Node n = graph.getNode(id);
		  n.Attributes.add(new Attribute("COUNT",""+nodeCounts.get(id)));
	  }
	  keys = edgeCounts.keySet().iterator();
	  while(keys.hasNext()){
		  String id = keys.next();
		  Edge e = graph.getEdge(id);
		  e.Attributes.add(new Attribute("COUNT",""+edgeCounts.get(id)));
	  }
	  keys = nodeCounts.keySet().iterator();
	  while(keys.hasNext()){
		  String id = keys.next();
		  Node n = graph.getNode(id);
		  float f = (float)nodeCounts.get(id)/(float)graphs.size();
		  if(f<pourcentage)
			  graph.removeNode(id);
	  }	  
	  graph.removeObsoleteEdges();
	  return graph;
  }
  
  



}