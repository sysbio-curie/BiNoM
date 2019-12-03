package fr.curie.BiNoM.pathways.test;

import fr.curie.BiNoM.pathways.analysis.structure.*;
import java.util.*;

public class TestMinCutSet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		Graph g = new Graph();


		Node d1 = new Node();
		d1.Id="D1";
		Node d2 = new Node();
		d2.Id="D2";
		Node d3 = new Node();
		d3.Id="D3";
		Node d4 = new Node();
		d4.Id="D4";
		Node d5 = new Node();
		d5.Id="D5";

		Node s1 = new Node();
		s1.Id="S1";
		Node s2 = new Node();
		s2.Id="S2";
		Node s3 = new Node();
		s3.Id = "S3";
		Node s4 = new Node();
		s4.Id="S4";
		Node s5 = new Node();
		s5.Id="S5";
		Node s6 = new Node();
		s6.Id="S6";

		g.addNode(s6);
		g.addNode(s5);
		g.addNode(s4);
		g.addNode(s3);
		g.addNode(s2);
		g.addNode(s1);
		g.addNode(d1);
		g.addNode(d2);
		g.addNode(d3);
		g.addNode(d4);
		g.addNode(d5);
		g.addNode(d5);

		Edge e1 = new Edge();
		e1.Node1=d1;
		e1.Node2=s1;
		e1.Id="e1";
		e1.weight=1;
		g.addEdge(e1);

		Edge e2 = new Edge();
		e2.Node1=d1;
		e2.Node2=s2;
		e2.Id="e2";
		e2.weight=1;
		g.addEdge(e2);

		Edge e3 = new Edge();
		e3.Node1=d1;
		e3.Node2=s3;
		e3.Id="e3";
		e3.weight=1;
		g.addEdge(e3);

		Edge e4 = new Edge();
		e4.Node1=d1;
		e4.Node2=s4;
		e4.Id="e4";
		e4.weight=1;
		g.addEdge(e4);

		Edge e5 = new Edge();
		e5.Node1=d2;
		e5.Node2=s3;
		e5.Id="e5";
		e5.weight=1;
		g.addEdge(e5);

		Edge e6 = new Edge();
		e6.Node1=d2;
		e6.Node2=s5;
		e6.Id="e6";
		e6.weight=1;
		g.addEdge(e6);

		Edge e7 = new Edge();
		e7.Node1=d3;
		e7.Node2=s2;
		e7.Id="e7";
		e7.weight=1;
		g.addEdge(e7);

		Edge e8 = new Edge();
		e8.Node1=d3;
		e8.Node2=s4;
		e8.Id="e8";
		e8.weight=1;
		g.addEdge(e8);

		Edge e9 = new Edge();
		e9.Node1=d4;
		e9.Node2=s5;
		e9.Id="e9";
		e9.weight=1;
		g.addEdge(e9);

		Edge e10 = new Edge();
		e10.Node1=d4;
		e10.Node2=s6;
		e10.Id="e10";
		e10.weight=1;
		g.addEdge(e10);

		Edge e11 = new Edge();
		e11.Node1=d5;
		e11.Node2=s5;
		e11.Id="e11";
		e11.weight=1;
		g.addEdge(e11);

		g.calcNodesInOut();

		//System.out.println(g.getNode("S6").incomingEdges.size());

		ArrayList<Node> sourceNodes = new ArrayList<Node>();
		sourceNodes.add(g.getNode("D1"));
		sourceNodes.add(g.getNode("D2"));
		sourceNodes.add(g.getNode("D3"));
		sourceNodes.add(g.getNode("D4"));
		sourceNodes.add(g.getNode("D5"));

		ArrayList<Node> targetNodes =  new ArrayList<Node>();
		targetNodes.add(g.getNode("S1"));
		targetNodes.add(g.getNode("S2"));
		targetNodes.add(g.getNode("S3"));
		targetNodes.add(g.getNode("S4"));
		targetNodes.add(g.getNode("S5"));
		targetNodes.add(g.getNode("S6"));

		
		HashSet<Node> res = GraphAlgorithms.HittingSetHighestDegreeFirst(g, sourceNodes, targetNodes);
		for (Node n : res) {
			System.out.println(n.Id);
		}
		System.exit(1);
		
		
//		ArrayList<String> covered = new ArrayList<String>();
//		
//		while (targetNodes.size()>0) {
//
//			System.out.println("source size"+sourceNodes.size());
//
//			// determine source nodes having max degree
//			int maxDeg = 0;
//			for (Node n : sourceNodes) {
//				//Node n = g.getNode(id);
//				if (n.outcomingEdges.size()>maxDeg)
//					maxDeg=n.outcomingEdges.size();
//			}
//			//System.out.println(maxDeg);
//
//			// select nodes having max degree
//			ArrayList<Node> selected = new ArrayList<Node>(); 
//			for (Node n : sourceNodes) {
//				//Node n = g.getNode(id);
//				if (n.outcomingEdges.size() == maxDeg) {
//					selected.add(n);
//				}
//			}
//
//			// select one of them randomly
//			Random rand = new Random();
//			int idx = rand.nextInt(selected.size());
//			//System.out.println(idx);
//
//			// set it covered 
//			covered.add(selected.get(idx).Id);
//			Node n = g.getNode(selected.get(idx).Id);
//
//			// remove targets of selected node from the graph
//			for (Edge t : n.outcomingEdges) {
//				targetNodes.remove(targetNodes.indexOf(t.Node2));
//				g.removeNode(t.Node2.Id);
//			}
//
//			// remove selected node
//			g.removeNode(n.Id);
//			sourceNodes.remove(sourceNodes.indexOf(n));
//
//			// update graph
//			g.removeObsoleteEdges();
//
//			// re-calculate stats
//			g.calcNodesInOut();
//
//			System.out.println(sourceNodes.size());
//			System.out.println(targetNodes.size());
//		}
//		
//		for (String id : covered) {
//			System.out.println(id);
//		}
	}
	
	


}
