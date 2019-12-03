package fr.curie.BiNoM.cytoscape.nestmanager;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE  
*/
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import fr.curie.BiNoM.pathways.utils.ComputingByBFS;
/**
 * Algorithm of clustering based on
 * a distance computed by minimum linkage of the oriented shortest path
 * when shortest paths are equal, the bigger number of links is get as minimum
 * The ceiling size of cluster and the max length of shortest paths are parameters
 * the condition to stop clustering is the
 * - the maximal length of shortest paths is reached
 * - the number of clusters remaining in the queue is less than 2
 * When the size of an union of clusters exceed the ceiling size, it is not created and
 * the biggest is out of the queue and kept apart
 * the start list of nodes is sorted by in degree, so sources are first
 * every created cluster is placed at the end of the queue to slow the increasing of size
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class ShortPathClustering {
	protected ComputingByBFS cBFS;
	protected int[][] distances;
	public ShortPathClustering(CyNetwork network){cBFS=new ComputingByBFS(network);}
	protected class Distance implements Comparable<Distance>{
		Integer pathLength,pathNumber;
		protected Distance(){pathLength=Integer.MAX_VALUE;pathNumber=0;}
		protected Distance(Integer pathLength, Integer pathNumber){this.pathLength=pathLength;this.pathNumber=pathNumber;}
		protected void set(Distance dist){this.pathLength=dist.pathLength;this.pathNumber=dist.pathNumber;}
		void minInLoop(Integer pathLength){
			if(pathLength<this.pathLength){
				this.pathLength=pathLength;
				pathNumber=0;
			}else if(this.pathLength==pathLength) pathNumber++;
		}
		public int compareTo(Distance dist){
			if(this.pathLength<dist.pathLength) return -1;
			if(this.pathLength>dist.pathLength) return 1;
			if(this.pathNumber<dist.pathNumber) return 1;
			if(this.pathNumber>dist.pathNumber) return -1;
			return 0;
		}
		protected boolean minByComp(Distance dist){
			if(this.compareTo(dist)>-1){
				this.pathLength=dist.pathLength;
				this.pathNumber=dist.pathNumber;
				return true;
			}else return false;		
		}
		protected Distance setDistance(Distance dist){this.pathLength=dist.pathLength;this.pathNumber=dist.pathNumber;return this;}
		public String toString(){if(pathLength==Integer.MAX_VALUE) return "OO\t1";else return pathLength+"\t"+(pathNumber+1);}
	}
	protected class Cluster{
		HashSet<Integer> set;
		Distance createDistance;
		String name;
		protected Cluster(){set=null;createDistance=null;name="";};
		protected Cluster(Integer one){
			set=new HashSet<Integer>();
			set.add(one);
			createDistance=new Distance();
			name="";
		}
		protected Cluster(Cluster cluster1,Cluster cluster2,Distance createDistance){
			set=new HashSet<Integer>(cluster1.set.size()+cluster2.set.size());
			set.addAll(cluster1.set);
			set.addAll(cluster2.set);
			this.createDistance=new Distance();
			this.createDistance.setDistance(createDistance);
		}
		public Distance getCreateDistance(){return createDistance;}
		public HashSet<Integer> getSet(){return set;}
		public void setName(String name){this.name=name;}
		protected String getName(){return name;}
		protected HashSet<CyNode> getNodeSet(){
			HashSet<CyNode> nodeSet=new HashSet<CyNode>();
			for(int node:set) nodeSet.add(cBFS.nodes.get(node));
			return nodeSet;
		}
		public String toString(){
			Iterator<Integer> it=set.iterator();
			String txt="["+cBFS.nodes.get(it.next()).getIdentifier();
			while(it.hasNext()) txt=txt+","+cBFS.nodes.get(it.next()).getIdentifier();
			return (txt+"]");
		}
	}
	protected Distance distance(Cluster cluster1,Cluster cluster2){
		Distance dist=new Distance();
		for(Integer c1:cluster1.set) for(Integer c2:cluster2.set){
			dist.minInLoop(distances[c1][c2]);
			dist.minInLoop(distances[c2][c1]);
		}
		return dist;
	}
	void insertionSort(ArrayList<Integer> keys,ArrayList<Integer> values){
		for (int i=1;i<keys.size();i++){
			int j=i;
			int ti=keys.get(i);
			Integer ts=values.get(i);
			while ((j > 0) && (keys.get(j-1)>ti)){
				keys.set(j,keys.get(j-1));
				values.set(j,values.get(j-1));
				j--;
			}
			keys.set(j,ti);
			values.set(j,ts);
		}
	}
	private ArrayDeque<Cluster> initClustering(){
		distances=cBFS.shortPathMatrix();
		ArrayDeque<Cluster> initIndexList=new ArrayDeque<Cluster>();
		ArrayList<Integer> inDgr=new ArrayList<Integer>(cBFS.nodes.size());
		for(int i=0;i<cBFS.nodes.size();i++) inDgr.add(0);
		for(int i=0;i<cBFS.edges.size();i++) inDgr.set(cBFS.tgts.get(i),inDgr.get(cBFS.tgts.get(i))+1);
		ArrayList<Integer> sortedNodes=new ArrayList<Integer>(cBFS.nodes.size());
		for(int i=0;i<cBFS.nodes.size();i++) sortedNodes.add(i);
		insertionSort(inDgr,sortedNodes);
		for(int i=0;i<sortedNodes.size();i++) initIndexList.add(new Cluster(sortedNodes.get(i)));
		return initIndexList;
	}
	protected HashSet<Cluster> clustering(int maxPathLength,int sizeCeiling){
		ArrayDeque<Cluster> listToCompare=initClustering();
		HashSet<Cluster> outCompare=new HashSet<Cluster>();
		Distance maxDist=new Distance(maxPathLength,0);				
		Distance infinity=new Distance(),dist=new Distance(),minDist=new Distance();
		Cluster minClt1=new Cluster(),minClt2=new Cluster();
		Cluster clt1,clt2;
		while(listToCompare.size()>1){			
			dist.set(infinity);
			Iterator<Cluster> it1,it2;			
			it1=listToCompare.iterator();
			while(it1.hasNext()){
				clt1=it1.next();
				it2=listToCompare.iterator();
				while(!clt1.equals(clt2=it2.next())){	
					if(dist.minByComp(distance(clt1,clt2))){
						minClt1=clt1;
						minClt2=clt2;
						minDist.setDistance(dist);
					}
				}
			}
			if(minDist.compareTo(maxDist)==1) break;
			if((minClt1.getSet().size()+minClt2.getSet().size())<sizeCeiling){
				listToCompare.remove(minClt1);
				listToCompare.remove(minClt2);
				Cluster cluster=new Cluster(minClt1,minClt2,minDist);
				listToCompare.addFirst(cluster);
			}else{
				if(minClt1.getSet().size()<minClt2.getSet().size()){
					listToCompare.remove(minClt2);
					outCompare.add(minClt2);
				}else{
					listToCompare.remove(minClt1);
					outCompare.add(minClt1);
				}				
			}
		}
		HashSet<Cluster> set=new HashSet<Cluster>(listToCompare.size()+outCompare.size());
		set.addAll(listToCompare);
		set.addAll(outCompare);
		return set;
	}
}
