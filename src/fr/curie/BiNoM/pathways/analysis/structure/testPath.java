package fr.curie.BiNoM.pathways.analysis.structure;

import java.io.*;
import java.util.*;

import edu.rpi.cs.xgmml.*;
import fr.curie.BiNoM.pathways.wrappers.*;

public class testPath {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{

			GraphDocument gr = null;
			try {
			//gr = XGMML.loadFromXMGML("c:/datas/binomtest/signal1.xgmml");
			gr = XGMML.loadFromXMGML("c:/datas/binomtest/signalb.xgmml");
			} catch (Exception e) {
			e.printStackTrace();
			}
			Graph graph = XGMML.convertXGMMLToGraph(gr);

			ArrayList<Node> sourceNodes = new ArrayList<Node>();
			ArrayList<Node> outputNodes = new ArrayList<Node>();

			//sourceNodes.add(graph.getNode("I1"));
			sourceNodes.add(graph.getNode("n2156"));
			//sourceNodes.add(graph.getNode("n1979"));
			//sourceNodes.add(graph.getNode("I2"));

			//outputNodes.add(graph.getNode("O1"));
			outputNodes.add(graph.getNode("n1963"));

			for (Node so : sourceNodes) {
				for (Node out : outputNodes) {
					Vector<Graph> pts = null;
					Set<Node> targets = new HashSet<Node>();
					targets.add(out);
					Date d = new Date();
					//pts = GraphAlgorithms.FindAllPaths(graph, so, targets, true, Double.POSITIVE_INFINITY);
					pts = GraphAlgorithms.FindAllPaths(graph, so, targets, true, 30);
					System.out.println("Found "+pts.size()+" graphs:");
					System.out.println("in "+((new Date()).getTime()-d.getTime())+" ms");
					for (Graph g : pts) {
						for (int j=0;j<g.Nodes.size();j++) {
							System.out.print(g.Nodes.get(j).Id+":");
						}
						System.out.println();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
