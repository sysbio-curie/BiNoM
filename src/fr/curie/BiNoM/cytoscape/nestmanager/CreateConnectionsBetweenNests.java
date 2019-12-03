package fr.curie.BiNoM.cytoscape.nestmanager;
/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import fr.curie.BiNoM.cytoscape.utils.TextBox;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
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
	private HashMap<CyNode,HashSet<CyNode>> nodeToNests;
	private void displayWarning(){
		String warning=new String("");
		for(CyNode node:nodeToNests.keySet()){			
			HashSet<CyNode> nodeList=nodeToNests.get(node);
			if(nodeList.size()>1){
				warning=warning+"Node in several networks\t"+node.getIdentifier();
				for(CyNode nest:nodeToNests.get(node)){
					warning=warning+"\t"+nest.getIdentifier();
				}
			warning=warning+"\r\n";
			}			
		}
		HashSet<CyNode> nodesNotInRef=new HashSet<CyNode>();
		nodesNotInRef.addAll(nodeToNests.keySet());
		nodesNotInRef.removeAll(referenceNW.nodesList());
		for(CyNode node:nodesNotInRef) warning=warning+"Not in reference network\t"+node.getIdentifier()+"\r\n";
		if(!warning.equals(""))	new TextBox(Cytoscape.getDesktop(),"Warning Update Connections Between Nests",warning).setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		currentNW=Cytoscape.getCurrentNetwork();
		TreeMap<String,CyNetwork> networks=NestUtils.getNetworksMap();
		String selected=NestUtils.selectOneNetwork(networks,title,"Select a network as reference");
		if (selected==null) return; else referenceNW=networks.get(selected);
		NestUtils.deleteNestEdges(currentNW);
		nodeToNests=NestUtils.doNodeToNests(currentNW);
		displayWarning();
		NestUtils.createNestConnection(referenceNW,currentNW,nodeToNests);
		Cytoscape.getCurrentNetworkView().redrawGraph(true,true);
	}
}
