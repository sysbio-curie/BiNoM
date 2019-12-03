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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.*;

import fr.curie.BiNoM.pathways.wrappers.XGMML;


/**
 * Set of functions using specific graph node semantics of BiNoM 
 *
 */
public class BiographUtils2 extends Graph {
	
  public class ReactionRegulator{
	  public String reactionId = null;
	  public Node node = null;
	  public String addInfo = null;
	  public int level = 0;
	  public int sign = 0;
	  public boolean contains(ReactionRegulator r, Vector<ReactionRegulator> regs){
		  boolean found = false;
		  for(ReactionRegulator reg: regs)
			  if(reg.node.Id.equals(r.node.Id))
				  found = true;
		  return found;
	  }
  }

  public static boolean mapSignOfConservationCoeffs = false;

  public BiographUtils2() {
  }
  
  public static HashMap getNeighborhoodSets(Graph network, Vector<String> selected, boolean goUpstream, boolean goDownstream, int searchRadius, int minimumNumberOfGenes){
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

		  names = BiographUtils2.extractNames(nodes);
		  
		  
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
			  

			  names = getNodeNamesFromAnAttribute(nodes,"HUGO");			  
	
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
  
  public static Vector<String> extractNames(Vector<Node> nodes){
	  Vector<String> res = new Vector<String>();
	  for(int i=0;i<nodes.size();i++){
		  Node n = nodes.get(i);
		  res.add(n.Id);
	  }
	  return res;
  }
}