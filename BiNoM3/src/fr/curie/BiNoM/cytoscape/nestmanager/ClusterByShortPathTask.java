package fr.curie.BiNoM.cytoscape.nestmanager;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE  
*/
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.TaskIterator;

import Main.Launcher;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import fr.curie.BiNoM.cytoscape.nestmanager.ShortPathClustering.Cluster;
/**
 * Task of creating networks from clusters by copy of nodes and edges from the current network
 * and creating a network made of clusters, edges are compacted as connections between nodes
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class ClusterByShortPathTask implements Task{
	ClusterByShortPath a;
	public ClusterByShortPathTask(ClusterByShortPath action){a=action;}	
	public String getTitle(){return ClusterByShortPath.title;}
	public void halt(){}
	public void run(){
		try{
			Iterator iter = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(a.currentNW).iterator();
			
			while(iter.hasNext())
				((CyNetworkView)iter.next()).updateView();			
			//Cytoscape.getNetworkView(a.currentNW.getIdentifier()).redrawGraph(true,true);
			
			
//			TaskIterator ti = Launcher.getAdapter().get_CloneNetworkTaskFactory().createTaskIterator(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
//			Launcher.getAdapter().getTaskManager().execute(ti);
			
			//taskM
			//CyNetwork cloneNW=NestUtils.cloneCurrent(" packed_"+a.maxPathLengthS+"/"+a.sizeCeiling);
				
			ArrayList<CyNetwork> clusterNetworks=new ArrayList<CyNetwork>();			
			HashSet<Cluster> clusters=a.clusters;
			
			Collection<CyNetworkView> networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(a.currentNW);
			Iterator<CyNetworkView> networkViewIterator = networkViews.iterator();
			CyNetworkView fromView =null;
			while(networkViewIterator.hasNext())
				fromView = networkViewIterator.next();
			
			
			int i=0,c=clusters.size();
			for(Cluster cluster:clusters){
				HashSet<CyNode> nodes=cluster.getNodeSet();
				HashSet<CyEdge> edges=ModuleUtils.edgesLinkingNodes(a.currentNW,nodes);
				
				
				// method 1 				
				CyNetwork netw = Launcher.getAdapter().getCyNetworkFactory().createNetwork();
				org.cytoscape.view.model.CyNetworkView netView = Launcher.getAdapter().getCyNetworkViewFactory().createNetworkView(netw);	
				
				netw.getRow(netw).set(CyNetwork.NAME, cluster.getName());
				
				Launcher.getAdapter().getCyNetworkManager().addNetwork(netw);
				
				Iterator it = nodes.iterator();
				while(it.hasNext())
					NetworkUtils.addNodeAndReportPosition((CyNode)(it.next()), a.currentNW, fromView, netw, netView);
				
				it = edges.iterator();
				while(it.hasNext())
					NetworkUtils.addEdgeAndConnectedNodesAndReportPositions((CyEdge)(it.next()), a.currentNW, fromView, netw, netView);
				
				Launcher.getAdapter().getCyNetworkViewManager().addNetworkView(netView);
							
				VisualStyle vs = Launcher.getAdapter().getVisualMappingManager().getVisualStyle(fromView);			
				Launcher.getAdapter().getVisualMappingManager().setVisualStyle(vs, netView);

				netView.fitContent();			
				netView.updateView();
				
				//method 2
//				CyNetwork n2 = Launcher.getAdapter().getCyRootNetworkManager().getRootNetwork(a.currentNW).addSubNetwork(nodes, edges);//
//				Launcher.getAdapter().getCyNetworkManager().addNetwork(n2);
				
				
				
				//CyNetwork network=Cytoscape.createNetwork(nodes,edges,cluster.getName(),a.currentNW,true);
				//CyNetworkView clusterView=Launcher.getAdapter().getCyNetworkViewFactory().createNetworkView(network);
				//clusterNetworks.add(netw);
				//NestUtils.reportPosition(Cytoscape.getNetworkView(a.currentNW.getIdentifier()),clusterView);				
				//clusterView.updateView();
				taskMonitor.setPercentCompleted(100*i++/c);
			}
			
//			NestUtils.createAndPlaceNests(cloneNW,clusterNetworks);
//			NestUtils.createNestConnection(a.currentNW,cloneNW,NestUtils.doNodeToNests(cloneNW));
//			for(CyNetwork nestNetwork:clusterNetworks)
//				for(CyNode node:NestUtils.getNodeList(nestNetwork)) cloneNW.removeNode(cloneNW.getIndex(node),false);
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
