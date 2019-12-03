package fr.curie.BiNoM.cytoscape.analysis;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

/**
 * Put the graph from Cytoscape network in practical data structure:
 * arrays of nodes, edges, sources, targets and adjacency
 * Sort nodes and edges by identifier
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class WeightGraphStructure {
	public CyNetwork graph;
	public ArrayList<CyNode> nodes;
	public ArrayList<CyEdge> edges;
	public ArrayList<Integer> srcs;
	public ArrayList<Integer> tgts;
	public ArrayList<Double> weights;
	public ArrayList<ArrayList<Integer>> adjacency;
	final String influenceAttr="WEIGHT";
	public WeightGraphStructure(){}
	public WeightGraphStructure(CyNetwork gp){
		cyLoad(gp);
		doStructure();
		initWeights();
	};
	void cyLoad(CyNetwork gp){
		graph=gp;
		nodes=new ArrayList<CyNode>(graph.getNodeList());
		edges=new ArrayList<CyEdge>(graph.getEdgeList());
	}
	public boolean initWeights(){

	weights=new ArrayList<Double>(edges.size());
		for(int i=0;i<edges.size();i++){
			Double weigth= graph.getRow(edges.get(i)).get(influenceAttr, Double.class);
			if(weigth!=null) 
				weights.add(weigth); else return false;
		}
		weights=null;
		return false;
	}
	void doStructure(){
		srcs=new ArrayList<Integer>(edges.size());
		tgts=new ArrayList<Integer>(edges.size());
		adjacency=new ArrayList<ArrayList<Integer>>(nodes.size());
		Collections.sort(nodes,new Comparator<CyNode>(){
			public int compare(CyNode n1, CyNode n2){
				return graph.getRow(n1).get(CyNetwork.NAME,String.class).compareTo(graph.getRow(n2).get(CyNetwork.NAME,String.class));
			}});
		Collections.sort(edges,new Comparator<CyEdge>(){
			public int compare(CyEdge n1, CyEdge n2){
				return graph.getRow(n1).get(CyNetwork.NAME,String.class).compareTo(graph.getRow(n2).get(CyNetwork.NAME,String.class));
			}});

		for(int i=0;i<nodes.size();i++) adjacency.add(new ArrayList<Integer>());
		for(int e=0;e<edges.size();e++){	
			int src=nodes.indexOf(edges.get(e).getSource());
			srcs.add(src);
			adjacency.get(src).add(e);
			tgts.add(nodes.indexOf(edges.get(e).getTarget()));
		}	
	}
	protected ArrayList<HashSet<Integer>> copy(ArrayList<ArrayList<Integer>> old_){
		ArrayList<HashSet<Integer>> new_=new ArrayList<HashSet<Integer>>(old_.size());
		for(int n=0;n<old_.size();n++) new_.add(n,new HashSet<Integer>(old_.get(n)));
		return new_;
	}
	protected ArrayList<LinkedList<Integer>> copyList(ArrayList<ArrayList<Integer>> old_){
		ArrayList<LinkedList<Integer>> new_=new ArrayList<LinkedList<Integer>>(old_.size());
		for(int n=0;n<old_.size();n++) new_.add(n,new LinkedList<Integer>(old_.get(n)));
		return new_;
	}
	protected ArrayList<ArrayList<Integer>> copyArray(ArrayList<ArrayList<Integer>> old_){
		ArrayList<ArrayList<Integer>> new_=new ArrayList<ArrayList<Integer>>(old_.size());
		for(int n=0;n<old_.size();n++) new_.add(n,new ArrayList<Integer>(old_.get(n)));
		return new_;
	}	
}
