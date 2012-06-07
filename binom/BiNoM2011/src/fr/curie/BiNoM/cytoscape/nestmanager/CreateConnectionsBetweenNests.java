package fr.curie.BiNoM.cytoscape.nestmanager;
/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import fr.curie.BiNoM.cytoscape.utils.TextBox;
import giny.model.GraphPerspective;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import cytoscape.CyEdge;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;
import cytoscape.data.Semantics;
import cytoscape.util.CytoscapeAction;
/**
 * Create edges between nests from a reference network
 * Delete all old edges between nests
 * Search if every edge of reference network belongs to different network nest
 * Several edges having same interaction type become one edge (left and right are got as same)
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class CreateConnectionsBetweenNests  extends CytoscapeAction{
	private static final long serialVersionUID = 1L;
	final public static String title="Create Connections between Modules";
	private CyNetwork currentNW,referenceNW;
	private HashMap<CyNode,HashSet<CyNode>> nodesToNest;
	private void doNodeToNests(){
		nodesToNest=new HashMap<CyNode,HashSet<CyNode>>(); 	
		for(CyNode nest:NestUtils.getNodeList(currentNW)){
			GraphPerspective nestNW=nest.getNestedNetwork();
			if(nestNW==null) continue;		
			for(CyNode node:NestUtils.getNodeList(nestNW)){
				if(nodesToNest.containsKey(node)) nodesToNest.get(node).add(nest);
				else{
					HashSet<CyNode> nests=new HashSet<CyNode>();
					nests.add(nest);
					nodesToNest.put(node,nests);
				}
			}
		}
	}
	private void displayWarning(){
		String warning=new String("");
		for(CyNode node:nodesToNest.keySet()){			
			HashSet<CyNode> nodeList=nodesToNest.get(node);
			if(nodeList.size()>1){
				warning=warning+"Node in several networks\t"+node.getIdentifier();
				for(CyNode nest:nodesToNest.get(node)){
					warning=warning+"\t"+nest.getIdentifier();
				}
			warning=warning+"\r\n";
			}			
		}
		HashSet<CyNode> nodesNotInRef=new HashSet<CyNode>();
		nodesNotInRef.addAll(nodesToNest.keySet());
		nodesNotInRef.removeAll(referenceNW.nodesList());
		for(CyNode node:nodesNotInRef) warning=warning+"Not in reference network\t"+node.getIdentifier()+"\r\n";
		if(!warning.equals(""))	new TextBox(Cytoscape.getDesktop(),"Warning Update Connections Between Nests",warning).setVisible(true);
	}
	private String getInteraction(CyEdge edgeFrom){
		CyAttributes edgeAttrs = Cytoscape.getEdgeAttributes();
		String interaction=edgeAttrs.getStringAttribute(edgeFrom.getIdentifier(),Semantics.INTERACTION);
		if(interaction.equalsIgnoreCase("RIGHT")||interaction.equalsIgnoreCase("LEFT")) interaction="MOLECULEFLOW";
		return interaction;
	}
	private void buildEdgesBetweenNests(){
		for(CyEdge edge:NestUtils.getEdgeList(referenceNW)){
			HashSet<CyNode> srcList=nodesToNest.get(edge.getSource());
			if(srcList==null) continue;
			HashSet<CyNode> tgtList=nodesToNest.get(edge.getTarget());
			if(tgtList==null) continue;
			String interaction=getInteraction(edge);
			for(CyNode src:srcList){
				for(CyNode tgt:tgtList){
					if(!src.equals(tgt)) currentNW.addEdge(Cytoscape.getCyEdge(src,tgt,Semantics.INTERACTION,interaction,true,true));					
				}
			}
		}
	}
	public void actionPerformed(ActionEvent e){
		currentNW=Cytoscape.getCurrentNetwork();
		TreeMap<String,CyNetwork> networks=NestUtils.getNetworksMap();
		String selected=NestUtils.selectOneNetwork(networks,title,"Select a network as reference");
		if (selected==null) return; else referenceNW=networks.get(selected);
		NestUtils.deleteNestEdges(currentNW);
		doNodeToNests();
		displayWarning();
		buildEdgesBetweenNests();
		Cytoscape.getCurrentNetworkView().redrawGraph(true,true);
	}
}
