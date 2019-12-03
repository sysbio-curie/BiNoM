package fr.curie.DeDaL;

import java.util.Map;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;

public class GraphCreator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static Graph CreateGraphFromCyNetwork(CyNetwork network){
		Graph graph = new Graph();
		for(CyNode node : network.getNodeList()){
			String nodename = getNodeName(node, network);
			graph.getCreateNode(nodename);
		}
		for(CyEdge edge : network.getEdgeList()){
			CyNode source = edge.getSource();
			CyNode target = edge.getTarget();
			String source_name = getNodeName(source, network);
			String target_name = getNodeName(target, network);
			Edge e = new Edge();
			e.Id = source_name+"_"+target_name;
			e.Node1 = graph.getNode(source_name);
			e.Node2 = graph.getNode(target_name);
			graph.addEdge(e);
		}
		return graph;
	}
	
	public static String getNodeName(CyNode node, CyNetwork network){
		String name = "";
		CyRow row = network.getRow(node);		
		Map<String,Object> values = row.getAllValues();
		name = network.getRow(node).get("name", String.class);
		return name;
	}

}
