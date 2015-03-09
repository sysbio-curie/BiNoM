package fr.curie.BiNoM.cytoscape.analysis;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import fr.curie.BiNoM.pathways.utils.WeightGraphStructure;

/**
 * Search the strong connected components
 * by the algorithm from Cormen, Leiserson, Rivest abd Stein
 * 
 * @author Daniel.Rovera@curie.fr
 */


public class SCCinTable extends WeightGraphStructure{
	
	public SCCinTable(WeightGraphStructure wgs){
		super();
		this.graph=wgs.graph;
		this.nodes=wgs.nodes;
		this.edges=wgs.edges;
		this.srcs=wgs.srcs;
		this.tgts=wgs.tgts;
		this.adjacency=wgs.adjacency;
		this.weights=wgs.weights;
	}
	void reverseGraph(){
		ArrayList<ArrayList<Integer>> revAdj=new ArrayList<ArrayList<Integer>>(nodes.size());
		for(int i=0;i<nodes.size();i++) revAdj.add(new ArrayList<Integer>());
		for(int i=0;i<tgts.size();i++){
			revAdj.get(tgts.get(i)).add(i);
			int tn=tgts.get(i);
			tgts.set(i,srcs.get(i));
			srcs.set(i,tn);
		}
		adjacency=revAdj;
	}
	int[] goThroughDFS(int[] order){
		int[] goThrough=new int[2*nodes.size()]; 
		ArrayList<HashSet<Integer>> adjTmp=copy(adjacency);
		Stack<Integer> stack=new Stack<Integer>();
		for(int i=0;i<goThrough.length;i++) goThrough[i]=-1;
		int date=-1;
		while(true){
			int startInd=0;
			while((startInd<order.length)&&(goThrough[order[startInd]]!=-1)) startInd++;
			if(startInd==order.length) break;
			int start=order[startInd];
			date++;
			goThrough[start]=date;
			stack.push(start);
			while(!stack.isEmpty()){			
				int node=stack.peek();
				HashSet<Integer> edges=adjTmp.get(node);
				if(!edges.isEmpty()){
					int edge=edges.iterator().next();
					edges.remove(edge);
					int child=tgts.get(edge);
					if(goThrough[child]<0){
						date++;
						goThrough[child]=date;
						stack.push(child);
					}
				}else{				
					node=stack.pop();
					date++;
					goThrough[node+nodes.size()]=date;
				}
			}
		}		
		return goThrough;
	}
	ArrayList<HashSet<Integer>> extractSubTrees(int[] goThrough){
		int[] rank=new int[2*nodes.size()];
		for(int i=0;i<nodes.size();i++) rank[goThrough[i]]=i;
		for(int i=nodes.size();i<goThrough.length;i++) rank[goThrough[i]]=i-nodes.size();
		ArrayList<HashSet<Integer>> subTrees=new ArrayList<HashSet<Integer>>();
		int i=0;		
		while(i<rank.length){
			int root=rank[i];
			HashSet<Integer> subTree=new HashSet<Integer>();
			subTrees.add(subTree);
			do subTree.add(rank[i++]); while(rank[i]!=root);
			i++;
		}
		return subTrees;
	}
	public ArrayList<HashSet<Integer>> SCC(){
		int[]order=new int[nodes.size()];
		for(int i=0;i<order.length;i++) order[i]=i;	
		int[] goThrough=goThroughDFS(order);	
		for (int i=1;i<order.length;i++){
			int j=i;
			int tk=goThrough[i+order.length];;
			int tv=order[i];
			while ((j > 0) && (goThrough[j-1+order.length]<tk)){
				goThrough[j+order.length]=goThrough[j-1+order.length];
				order[j]=order[j-1];
				j--;
			}
			goThrough[j+order.length]=tk;
			order[j]=tv;
		}
		reverseGraph();
		ArrayList<HashSet<Integer>> subTrees=extractSubTrees(goThroughDFS(order));
		return subTrees;		
	}
}
