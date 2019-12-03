package fr.curie.BiNoM.pathways.test;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFrame;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.GraphAlgorithms;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class TestFindAllPaths {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			String fn = "/bioinfo/users/ebonnet/Binom/signal.xgmml";
			
			GraphDocument gr = XGMML.loadFromXMGML(fn);
			Graph graph = XGMML.convertXGMMLToGraph(gr);
			
			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/merged_net_0pc.xgmml");
			//dpc.loadGraph("/bioinfo/users/ebonnet/Binom/merged_net_30p.xgmml");
			
			Vector<Graph> pts = null;
			Set<Node> targets = new HashSet<Node>(); 
			targets.add(graph.getNode("O1"));
			Node so = graph.getNode("I1");
			pts = GraphAlgorithms.FindAllPaths(graph, so, targets, true, Double.MAX_VALUE);
			
			for (Graph g : pts) {
				for (Node n : g.Nodes) {
					System.out.print(n.Id+" ");
				}
				System.out.println();
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
}
