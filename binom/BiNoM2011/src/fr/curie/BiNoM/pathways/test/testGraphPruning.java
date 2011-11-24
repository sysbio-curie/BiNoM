package fr.curie.BiNoM.pathways.test;

import java.util.*;

import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import edu.rpi.cs.xgmml.*;


public class testGraphPruning {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			
			String prefix = "c:/datas/binomtest/pruneexample1";
			GraphDocument gr = XGMML.loadFromXMGML(prefix+".xgmml");
			Graph graph = XGMML.convertXGMMLToGraph(gr);
			
			Vector<Float> pplus = new Vector<Float>();
			Vector<Float> pminus = new Vector<Float>();
			
			for(int i=0;i<graph.Edges.size();i++){
				pplus.add(0.5f);
				pminus.add(0f);
			}
			
			Vector<Graph> explanations = new Vector<Graph>();
			Vector<Float> edgeRedundancies = new Vector<Float>(); 
			
			edgeRedundancies = GraphAlgorithms.SimpleEdgePruning(graph, pplus, pminus, 3, explanations);
			System.out.println("Edges = "+graph.Edges.size());
			Graph graphRes = GraphAlgorithms.PruneEdges(graph,edgeRedundancies,1f);
			System.out.println("Edges = "+graphRes.Edges.size());
			/*  Node source = graph.getNode("node0");
			  Node target = graph.getNode("node3");
			  HashSet<Node> trgts = new HashSet<Node>();
			  trgts.add(target);
			  Vector<Graph> paths = GraphAlgorithms.FindAllPaths(graph, source, trgts, true, 3, false);
			  for(int k=0;k<paths.size();k++){
				  Graph grf = paths.get(k);
				  System.out.println((k+1)+": "+(new Path(grf,source.Id)).toString());
			  }*/
			
			//Graph graphRes = GraphAlgorithms.TreschAlgorithm(graph, pplus, pminus, explanations,1.1f);
			
			
			 XGMML.saveToXGMML(graphRes, prefix+"_pruned.xgmml");
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
