package fr.curie.BiNoM.cytoscape.nestmanager;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import giny.model.GraphPerspective;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import cytoscape.CyEdge;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.Semantics;
/**
 * Put the graph from Cytoscape network in practical data structure 
 * Compute degrees and put them in a structure which can be sorted by several ways
 * Compute the shortest path matrix used in clustering by breadth first search
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class InteractionGraph{
	protected GraphPerspective network;	
	protected ArrayList<CyNode> nodes;
	protected ArrayList<IntEdge> graph;
	protected ArrayList<String> interaction;
	protected ArrayList<NodeDegree> degrees;
	protected class IntEdge{
		int src,iv,tgt;
		public IntEdge(int source,int target){src=source;tgt=target;iv=-1;}
		public IntEdge(int source,int target, int indexOrValue){src=source;tgt=target;iv=indexOrValue;}
		boolean equals(IntEdge e){return (this.src==e.src)&(this.tgt==e.tgt)&(this.iv==e.iv);}
		public void set(int indexOrValue){iv=indexOrValue;}
		IntEdge reverse(){int $t=src;src=tgt;tgt=$t;return this;}
		public String toString(){return (nodes.get(src).getIdentifier()+"\t"+interaction.get(iv)+"\t"+nodes.get(tgt).getIdentifier());}
	}
	public class NodeDegree{
		int node;
		int outDgr;
		int inDgr;
		public NodeDegree(int node){this.node=node;outDgr=0;inDgr=0;}
		public int getNode(){return node;}
		public int compareIn(NodeDegree nodeDgr){return (inDgr-nodeDgr.inDgr);}
		public int compareOut(NodeDegree nodeDgr){return (outDgr-nodeDgr.outDgr);}
		public int compare(NodeDegree nodeDgr){return (inDgr+outDgr-nodeDgr.inDgr-nodeDgr.outDgr);}	
		NodeDegree outDgrPlusUn(){outDgr++;return this;}
		NodeDegree inDgrPlusUn(){inDgr++;return this;}
		public String toString(){return (nodes.get(node).getIdentifier()+" out:"+outDgr+" in:"+inDgr);}		
	}
	InteractionGraph(){}
	InteractionGraph(CyNetwork network){		
		this.network=network;
		createGraph();
	}
	protected void createGraph(){
		graph=new ArrayList<IntEdge>();
		nodes=NestUtils.getNodeArray(network);
		interaction=new ArrayList<String>();		
		for(CyEdge edge:NestUtils.getEdgeList(network)){
			int src=nodes.indexOf(edge.getSource());
			int tgt=nodes.indexOf(edge.getTarget());
			int ii=interactionIndex(edge);
			graph.add(new IntEdge(src,tgt,ii));
		}
	}
	private int interactionIndex(CyEdge edge){
		String interactionName=Cytoscape.getEdgeAttributes().getStringAttribute(edge.getIdentifier(),Semantics.INTERACTION);
		int index=interaction.indexOf(interactionName);
		if(index==-1){
			interaction.add(interactionName);
			index=interaction.size()-1;
		}
		return index;
	}
	protected void computeDegrees(){
		degrees=new ArrayList<NodeDegree>(nodes.size());
		for(int i=0;i<nodes.size();i++)	degrees.add(new NodeDegree(i));
		for(int i=0;i<graph.size();i++){
			degrees.set(graph.get(i).src,degrees.get(graph.get(i).src).outDgrPlusUn());
			degrees.set(graph.get(i).tgt,degrees.get(graph.get(i).tgt).inDgrPlusUn());
		}		
	}
	private Integer getEdgeWithSrc(Integer node,HashSet<Integer> bag){
		for(int edgeIndex:bag){
			if(graph.get(edgeIndex).src==node) {
				bag.remove(edgeIndex);
				return edgeIndex;}
			}
		return -1;
	}
	protected int[][] shortPathMatrixByBFS(){
		int[][] spl=new int[nodes.size()][nodes.size()];
		HashSet<Integer> edgeBag=new HashSet<Integer>(graph.size());
		HashSet<Integer> nodeBag=new HashSet<Integer>(nodes.size());
		for(int root=0;root<nodes.size();root++){
			Integer node,edge;
			for(int i=0;i<graph.size();i++) edgeBag.add(i);
			for(int l=0;l<nodes.size();l++) {
				spl[l][root]=Integer.MAX_VALUE;
				nodeBag.add(l);
			}
			spl[root][root]=0;
			nodeBag.remove(root);
			LinkedList<Integer> queue=new LinkedList<Integer>();
			queue.add(root);
			while(!queue.isEmpty()){
				node=queue.remove();
				while((edge=getEdgeWithSrc(node,edgeBag))!=-1){
					if(nodeBag.contains(graph.get(edge).tgt)){
						spl[graph.get(edge).tgt][root]=spl[graph.get(edge).src][root]+1;
						nodeBag.remove(graph.get(edge).tgt);
					}
					queue.add(graph.get(edge).tgt);
				}
			}
			edgeBag.clear();
			nodeBag.clear();
		}
		return spl;
	}
}
