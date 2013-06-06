package fr.curie.BiNoM.cytoscape.utils;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import fr.curie.BiNoM.cytoscape.nestmanager.NestUtils;
import giny.model.GraphPerspective;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import cytoscape.CyEdge;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
/**
 * Put the graph from Cytoscape network in practical data structure:
 * arrays of nodes, edges, sources, targets and adjacency 
 * Use breadth first search to iterate class for computing
 * 
 * list of edges between nodes
 * Influence by a simple recurrent model
 * the shortest path matrix used in clustering
 * Reverse breadth first search is used by extract edges between nodes
 * 
 * Influence Area by the same model but without weights
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class ComputingByBFS{
	public GraphPerspective graph;
	public ArrayList<CyNode> nodes;
	public ArrayList<CyEdge> edges;
	public ArrayList<Integer> srcs;
	public ArrayList<Integer> tgts;
	protected ArrayList<Double> weigths;
	public ArrayList<HashSet<Integer>> adjacency;
	public ComputingByBFS(){}
	public ComputingByBFS(GraphPerspective gp,boolean sorted){initStructure(gp,sorted);}
	protected void initStructure(GraphPerspective gp,boolean sorted){
		graph=gp;
		nodes=new ArrayList<CyNode>(NestUtils.getNodeList(graph));
		if(sorted) Collections.sort(nodes,new Comparator<CyNode>(){
			public int compare(CyNode n1, CyNode n2){
				return n1.getIdentifier().compareTo(n2.getIdentifier());
			}});
		edges=new ArrayList<CyEdge>(NestUtils.getEdgeList(graph));
		srcs=new ArrayList<Integer>(graph.edgesList().size());
		tgts=new ArrayList<Integer>(graph.edgesList().size());
		adjacency=new ArrayList<HashSet<Integer>>(nodes.size());
		for(int i=0;i<nodes.size();i++) adjacency.add(new HashSet<Integer>());
		for(int e=0;e<edges.size();e++){	
			int src=nodes.indexOf(edges.get(e).getSource());
			srcs.add(src);
			adjacency.get(src).add(e);
			tgts.add(nodes.indexOf(edges.get(e).getTarget()));
		}		
	}
	final String influenceAttr="WEIGHT";
	public boolean initWeigths(){
		weigths=new ArrayList<Double>(edges.size());
		for(int i=0;i<edges.size();i++){
			Double weigth=Cytoscape.getEdgeAttributes().getDoubleAttribute(edges.get(i).getIdentifier(),influenceAttr);
			if(weigth!=null) weigths.add(weigth); else return false;
		}
		return true;
	}
	protected ArrayList<HashSet<Integer>> copy(ArrayList<HashSet<Integer>> old_){
		ArrayList<HashSet<Integer>> new_=new ArrayList<HashSet<Integer>>(old_.size());
		for(int n=0;n<old_.size();n++) new_.add(n,new HashSet<Integer>(old_.get(n)));
		return new_;
	}
	abstract class IterByBFS{
		IterByBFS(){}
		void init(int root){}
		void queueAdd(int edge){}
	}
	class EdgesByBFS extends IterByBFS{
		HashSet<Integer> edges;
		EdgesByBFS(int setSize){
			edges=new HashSet<Integer>(setSize);
		}
		void queueAdd(int edge){
			edges.add(edge);
		}
	}
	class ShortPathByBFS extends IterByBFS{
		int root;
		int[][] spMx;
		ShortPathByBFS(){
			spMx=new int[nodes.size()][nodes.size()];
			for(int i=0;i<nodes.size();i++) for(int j=0;j<nodes.size();j++) spMx[i][j]=Integer.MAX_VALUE; 
		}
		void init(int root){
			this.root=root;
			spMx[root][root]=0;
		}
		void queueAdd(int edge){
			if(spMx[tgts.get(edge)][root]==Integer.MAX_VALUE) spMx[tgts.get(edge)][root]=spMx[srcs.get(edge)][root]+1;	
		}
	}
	class InfluenceByBFS extends IterByBFS{
		double fade;
		int root;
		double[][] influence;
		InfluenceByBFS(double fade){
			this.fade=fade;
			influence=new double[nodes.size()][nodes.size()];
			for(int i=0;i<nodes.size();i++) for(int j=0;j<nodes.size();j++) influence[i][j]=Double.NaN;
		}
		void init(int root){
			this.root=root;
			influence[root][root]=1;
		}
		void queueAdd(int edge){
			if(Double.isNaN(influence[tgts.get(edge)][root])) influence[tgts.get(edge)][root]=0.0;
			influence[tgts.get(edge)][root]=influence[tgts.get(edge)][root]+influence[srcs.get(edge)][root]*weigths.get(edge)*fade;	
		}
	}
	class ReachAreaByBFS extends IterByBFS{
		double fade;
		int root;
		double[][] area;
		ReachAreaByBFS(double fade){
			this.fade=fade;
			area=new double[nodes.size()][nodes.size()];
			for(int i=0;i<nodes.size();i++) for(int j=0;j<nodes.size();j++) area[i][j]=0.0;
		}
		void init(int root){
			this.root=root;
			area[root][root]=1;
		}
		void queueAdd(int edge){
			area[tgts.get(edge)][root]=area[tgts.get(edge)][root]+area[srcs.get(edge)][root]*fade;	
		}
	}
	class ActivityByBFS extends IterByBFS{
		double fade;
		int root;
		double[][] activity;
		ActivityByBFS(double fade){
			this.fade=fade;
			activity=new double[nodes.size()][nodes.size()];
			for(int i=0;i<nodes.size();i++) for(int j=0;j<nodes.size();j++) activity[i][j]=0.0;
		}
		void init(int root){
			this.root=root;
			activity[root][root]=1;
		}
		void queueAdd(int edge){
			activity[tgts.get(edge)][root]=activity[tgts.get(edge)][root]+activity[srcs.get(edge)][root]*weigths.get(edge)*fade;	
		}
	}
	void actDuringBFS(int root,IterByBFS iter){
		LinkedList<Integer> nodeQueue=new LinkedList<Integer>();
		ArrayList<HashSet<Integer>> adjTmp=copy(adjacency);
		nodeQueue.add(root);
		iter.init(root);
		while(!nodeQueue.isEmpty()){
			int node=nodeQueue.remove();
			HashSet<Integer> edges=adjTmp.get(node);
			while(!edges.isEmpty()){
				int edge=edges.iterator().next();
				edges.remove(edge);
				nodeQueue.add(tgts.get(edge));
				iter.queueAdd(edge);
			}
		}
	}
	public int[][] shortPathMatrix(){
		ShortPathByBFS sp=new ShortPathByBFS();
		for(int root=0;root<nodes.size();root++) actDuringBFS(root,sp);
		return sp.spMx;
	}
	public double[][] allInfluence(double fade){
		InfluenceByBFS infl=new InfluenceByBFS(fade);
		for(int root=0;root<nodes.size();root++) actDuringBFS(root,infl);
		return infl.influence;
	}
	public double[][] reachArea(double fade){
		ReachAreaByBFS ra=new ReachAreaByBFS(fade);
		for(int root=0;root<nodes.size();root++) actDuringBFS(root,ra);
		return ra.area;
	}
	public double[][] allActivity(double fade){
		ActivityByBFS activ=new ActivityByBFS(fade);
		for(int root=0;root<nodes.size();root++) actDuringBFS(root,activ);
		return activ.activity;
	}
	public double[] activityFromIn(double fade,double[] activIn){
		double[][] activ=allActivity(fade);
		double[] activOut=new double[nodes.size()];
		for(int t=0;t<nodes.size();t++) {
			activOut[t]=0.0;
			for(int s=0;s<nodes.size();s++) activOut[t]=activOut[t]+activIn[s]*activ[t][s];
		}
		return activOut;
	}
	public double[] reachAreaFromStarts(double fade,double[] startNodes){
		double[][] reachArea=reachArea(fade);
		double[] inflOnNodes=new double[nodes.size()];
		for(int t=0;t<nodes.size();t++) {
			inflOnNodes[t]=0.0;
			for(int s=0;s<nodes.size();s++) inflOnNodes[t]=inflOnNodes[t]+startNodes[s]*reachArea[t][s];
		}
		return inflOnNodes;
	}
	public HashSet<Integer> extractNodes(ArrayList<Integer> srcList,ArrayList<Integer> tgtList){
		EdgesByBFS direct=new EdgesByBFS(edges.size()/2);
		for(int src:srcList) actDuringBFS(src,direct);
		HashSet<Integer> nodesByRevers=new HashSet<Integer>(nodes.size()/2);
		ArrayList<HashSet<Integer>> reverseAdj=new ArrayList<HashSet<Integer>>(nodes.size());
		for(int i=0;i<nodes.size();i++) reverseAdj.add(new HashSet<Integer>());
		for(int i=0;i<edges.size();i++) if(direct.edges.contains(i)) reverseAdj.get(tgts.get(i)).add(i);
		for(int tgt:tgtList){
			LinkedList<Integer> nodeQueue=new LinkedList<Integer>();
			ArrayList<HashSet<Integer>> adjTmp=copy(reverseAdj);
			nodeQueue.add(tgt);
			nodesByRevers.add(tgt);
			while(!nodeQueue.isEmpty()){
				int node=nodeQueue.remove();
				HashSet<Integer> edgeSet=adjTmp.get(node);
				while(!edgeSet.isEmpty()){
					int edge=edgeSet.iterator().next();
					edgeSet.remove(edge);
					nodeQueue.add(srcs.get(edge));
					nodesByRevers.add(srcs.get(edge));
				}
			}
		}
		return nodesByRevers;
	}
}
