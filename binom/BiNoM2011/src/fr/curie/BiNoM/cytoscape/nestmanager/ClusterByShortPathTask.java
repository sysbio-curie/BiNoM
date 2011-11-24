package fr.curie.BiNoM.cytoscape.nestmanager;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE  
*/
import java.util.ArrayList;
import java.util.HashSet;

import cytoscape.CyEdge;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.actions.CloneGraphInNewWindowAction;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import cytoscape.view.CyNetworkView;
import fr.curie.BiNoM.cytoscape.nestmanager.ShortPathClustering.Cluster;
/**
 * Task of creating networks from clusters by copy of nodes and edges from the current network
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class ClusterByShortPathTask implements Task{
	ClusterByShortPath a;
	private final String attrName="PREVIOUS_ID";
	public ClusterByShortPathTask(ClusterByShortPath action){a=action;}	
	public String getTitle(){return ClusterByShortPath.title;}
	public void halt(){}
	public void run(){
		try{
			Cytoscape.getNetworkView(a.currentNW.getIdentifier()).redrawGraph(true,true);
			(new CloneGraphInNewWindowAction()).actionPerformed(a.event);
			CyNetwork cloneNW=Cytoscape.getCurrentNetwork();
			for(CyEdge edge:NestUtils.getEdgeList(cloneNW)) Cytoscape.getEdgeAttributes().setAttribute(edge.getIdentifier(),attrName,edge.getIdentifier());
			String cloneName=Cytoscape.getCurrentNetwork().getTitle();
			cloneName=cloneName.replace("copy","packed_"+a.maxPathLengthS+"/"+a.sizeCeiling);
			cloneNW.setTitle(cloneName);
			ArrayList<CyNetwork> clusterNetworks=new ArrayList<CyNetwork>();			
			HashSet<Cluster> clusters=a.clusters;
			int i=0,c=clusters.size();
			for(Cluster cluster:clusters){
				HashSet<CyNode> nodes=cluster.getNodeSet();
				HashSet<CyEdge> edges=NestUtils.edgesLinkingNodes(a.currentNW,nodes);
				CyNetwork network=Cytoscape.createNetwork(nodes,edges,cluster.getName(),a.currentNW,true);
				CyNetworkView clusterView=Cytoscape.createNetworkView(network);
				clusterNetworks.add(network);
				NestUtils.reportPosition(Cytoscape.getNetworkView(a.currentNW.getIdentifier()),clusterView);				
				clusterView.redrawGraph(true,true);
				taskMonitor.setPercentCompleted(100*i++/c);
			}
			for(CyNetwork network:clusterNetworks) NestUtils.createConnectNestPack(cloneNW, network);
			taskMonitor.setStatus("...Completed");			
		}
		catch(Exception e) {
			e.printStackTrace();
			taskMonitor.setStatus("Error in "+getTitle()+" "+e);
		}	
	}
	private TaskMonitor taskMonitor;
	public void setTaskMonitor(TaskMonitor taskMonitor) throws IllegalThreadStateException{this.taskMonitor=taskMonitor;}
}
