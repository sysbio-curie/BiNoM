package fr.curie.BiNoM.cytoscape.nestmanager;
/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE  
*/
import giny.model.GraphPerspective;
import java.util.ArrayList;
import java.util.HashSet;
import cytoscape.CyEdge;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.view.CyNetworkView;
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
			GraphPerspective nestNW=node.getNestedNetwork();
			if(nestNW==null) continue;			
			mergeNodes.addAll(NestUtils.getNodeSet(nestNW));
			nx=nx+currentV.getNodeView(node).getXPosition();
			ny=ny+currentV.getNodeView(node).getYPosition();
			mergeEdges.addAll(NestUtils.getEdgeSet(nestNW));
		}
		if(mergeNodes.size()==0) return null;
		nx=nx/size;
		ny=ny/size;
		mergeName=nestSet.get(0).getIdentifier();
		for(int i=1;i<nestSet.size();i++) mergeName=mergeName+"&"+nestSet.get(i).getIdentifier();
		CyNode mergeNest=Cytoscape.getCyNode(mergeName,true);
		currentNW.addNode(mergeNest);
		currentV.getNodeView(mergeNest).setXPosition(nx);
		currentV.getNodeView(mergeNest).setYPosition(ny);
		return mergeNest;
	}
	private void deleteSet(){
		int[] indexes=new int[nestSet.size()];
		int i=0;
		for(CyNode node:nestSet) indexes[i++]=currentNW.getIndex(node);
		for(i=0;i<indexes.length;i++) currentNW.removeNode(indexes[i],false);
	}
	private CyNetwork createMergeNetwork(){
		CyNetwork mergeNW=Cytoscape.createNetwork(mergeNodes,mergeEdges,mergeName);
		CyNetworkView mergeView=Cytoscape.createNetworkView(mergeNW);
		for(CyNode node:nestSet){
			CyNetwork nestNW=(CyNetwork)node.getNestedNetwork();
			NestUtils.reportPosition(Cytoscape.getNetworkView(nestNW.getIdentifier()),mergeView);		
		}
		mergeView.redrawGraph(true,true);
		return mergeNW;
	}
	public boolean perform(ArrayList<CyNode> nestSetToMerge){	
		nestSet=nestSetToMerge;
		CyNode mergeNest=createMergeNest();
		if(mergeNest==null) return false;
		mergeNest.setNestedNetwork(createMergeNetwork());
		deleteSet();
		NestUtils.deleteNestEdges(currentNW);
		return true;
	}
}
