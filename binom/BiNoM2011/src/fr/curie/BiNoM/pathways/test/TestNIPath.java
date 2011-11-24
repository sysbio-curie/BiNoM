package fr.curie.BiNoM.pathways.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.GraphAlgorithms;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.analysis.structure.Path;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class TestNIPath {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		GraphDocument gr = null;
		try {
			//gr = XGMML.loadFromXMGML("/bioinfo/users/ebonnet/Binom/signal.xgmml");
			gr = XGMML.loadFromXMGML("/bioinfo/users/ebonnet/Binom/signal16.xgmml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Graph graph = XGMML.convertXGMMLToGraph(gr);
		
		ArrayList<Node> sourceNodes = new ArrayList<Node>();
		ArrayList<Node> outputNodes = new ArrayList<Node>();
		
		sourceNodes.add(graph.getNode("I1"));
		//sourceNodes.add(graph.getNode("I2"));
		
		outputNodes.add(graph.getNode("O1"));
		
		for (Node so : sourceNodes) {
			for (Node out : outputNodes) {
				Vector<Graph> pts = null;
				Set<Node> targets = new HashSet<Node>(); 
				targets.add(out); 
				pts = GraphAlgorithms.FindAllPaths(graph, so, targets, true, Double.POSITIVE_INFINITY); 
				System.out.println("Found graphs:");
				for (Graph g : pts) {
					for (int j=0;j<g.Nodes.size();j++) {
						System.out.print(g.Nodes.get(j).Id+":");
					}
					System.out.println();
				}
			}
		}
	}

}
