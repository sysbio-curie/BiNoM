package fr.curie.BiNoM.cytoscape.nestmanager;
/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE  
*/

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualStyle;

import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import Main.Launcher;

/**
 *  Merge several nests and create merged networks
 *  Replace selected nodes by one merged and delete edges in nest network
 *  Transfer positions to created network
 *  
 *  @author Daniel.Rovera@curie.fr
 */ 
public class NestMerging{
	private CyNetwork currentNW;
	private CyNetworkView currentV;
	private ArrayList<CyNode> nestSet;
	private HashSet<CyNode> mergeNodes;
	private HashSet<CyEdge> mergeEdges;
	private String mergeName;
	public NestMerging(CyNetwork currentNetwork, CyNetworkView currentView){
		currentNW=currentNetwork;
		currentV=currentView;
	}
	private CyNode createMergeNest(){
		int size=nestSet.size();
		if(size==1) return null;
		mergeNodes=new HashSet<CyNode>();
		mergeEdges=new HashSet<CyEdge>();
		double nx=0.0,ny=0.0;
		for(CyNode node:nestSet){
			CyNetwork nestNW=node.getNetworkPointer();
			if(nestNW==null) continue;			
			mergeNodes.addAll(ModuleUtils.getNodeSet(nestNW));
			nx=nx+ Double.parseDouble(currentV.getNodeView(node).getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION).toString());
			ny=ny+ Double.parseDouble(currentV.getNodeView(node).getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION).toString());
			mergeEdges.addAll(ModuleUtils.getEdgeSet(nestNW));
		}
		if(mergeNodes.size()==0) return null;
		nx=nx/size;
		ny=ny/size;
		mergeName=nestSet.get(0).getNetworkPointer().getRow(nestSet.get(0)).get(CyNetwork.NAME, String.class);
		for(int i=1;i<nestSet.size();i++) mergeName=mergeName+"&"+nestSet.get(0).getNetworkPointer().getRow(nestSet.get(i)).get(CyNetwork.NAME, String.class);
		
		
		CyNode mergeNest=currentNW.addNode();
		currentNW.getDefaultNodeTable().getRow(mergeNest).set("name", mergeName);		
		currentV.getNodeView(mergeNest).setVisualProperty(BasicVisualLexicon.NETWORK_CENTER_X_LOCATION, nx);
		currentV.getNodeView(mergeNest).setVisualProperty(BasicVisualLexicon.NETWORK_CENTER_Y_LOCATION, ny);
		
		//currentV.getNodeView(mergeNest).setXPosition(nx);
		//currentV.getNodeView(mergeNest).setYPosition(ny);
		return mergeNest;
	}
	
	
	private void deleteSet(){
		int[] indexes=new int[nestSet.size()];
		currentNW.removeNodes(nestSet);
		
//		int i=0;
//		for(CyNode node:nestSet) 
//			indexes[i++]=currentNW.getIndex(node);
//		for(i=0;i<indexes.length;i++) 
//			currentNW.removeNode(indexes[i],false);
	}
	
	private CyNetwork createMergeNetwork(){
		CyNetwork mergeNW = Launcher.getAdapter().getCyNetworkFactory().createNetwork();
		org.cytoscape.view.model.CyNetworkView netView = Launcher.getAdapter().getCyNetworkViewFactory().createNetworkView(netw);	
		
		mergeNW.getRow(mergeNW).set(CyNetwork.NAME, cluster.getName());
		
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
				
				
				
				
				
				
				
				
				
				
				
				
				Cytoscape.createNetwork(mergeNodes,mergeEdges,mergeName);
		CyNetworkView mergeView=Launcher.getAdapter().getCyNetworkViewFactory().createNetworkView(mergeNW);
		for(CyNode node:nestSet){
			CyNetwork nestNW=(CyNetwork)node.getNetworkPointer();
			NestUtils.reportPosition(Cytoscape.getNetworkView(nestNW.getIdentifier()),mergeView);		
		}
		mergeView.redrawGraph(true,true);
		return mergeNW;
	}
	
	public boolean perform(ArrayList<CyNode> nestSetToMerge){	
		nestSet=nestSetToMerge;
		CyNode mergeNest=createMergeNest();
		if(mergeNest==null) return false;
		mergeNest.setNetworkPointer(createMergeNetwork());
		deleteSet();
		NestUtils.deleteNestEdges(currentNW);
		return true;
	}
}
