package fr.curie.BiNoM.cytoscape.nestmanager;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.TreeMap;
import cytoscape.CyEdge;
import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.data.Semantics;
import cytoscape.util.CytoscapeAction;
import fr.curie.BiNoM.cytoscape.lib.TaskManager;
/**
 * Create edges inside nested network from a reference network
 * Delete all old edges of nested network
 * Search edges which nodes belong to the nested network
 * Copy edges attributes included from the reference network to every nested network
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class RecreateLostConnectionsInsideNests  extends CytoscapeAction {
	private static final long serialVersionUID = 1L;
	final public static String title="Recreate Lost Connections inside Modules";
	private CyNetwork referenceNW;
	void updateConnections(CyNetwork network){
		if(network==referenceNW) return;
		for(CyEdge edge:NestUtils.getEdgeList(network))network.removeEdge(network.getIndex(edge),true);
		HashSet<CyEdge> edges=NestUtils.edgesLinkingNodes(referenceNW,NestUtils.getNodeSet(network));
		for(CyEdge edge:edges)(network).addEdge(NestUtils.connect2NodesFrom(edge.getSource(),edge.getTarget(),edge,Cytoscape.getEdgeAttributes().getStringAttribute(edge.getIdentifier(),Semantics.INTERACTION)));
	}
	public void actionPerformed(ActionEvent v) {
		TreeMap<String,CyNetwork> networks=NestUtils.getNetworksMap();
		String selected=NestUtils.selectOneNetwork(networks,title,"Select a network as reference");
		if (selected==null) return; else referenceNW=networks.get(selected);
		TaskManager.executeTask(new RecreateLostConnectionsInsideNestsTask(this));			
	}
}
