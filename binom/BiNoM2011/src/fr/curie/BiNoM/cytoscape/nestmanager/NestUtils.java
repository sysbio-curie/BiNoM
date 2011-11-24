package fr.curie.BiNoM.cytoscape.nestmanager;
/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE  
*/
import fr.curie.BiNoM.cytoscape.utils.ListDialog;
import giny.model.GraphPerspective;
import giny.model.Node;
import giny.view.NodeView;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import cytoscape.CyEdge;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;
import cytoscape.data.Semantics;
import cytoscape.view.CyNetworkView;
/**
 * Class gathering useful functions shared by several classes of nest manager:
 * - Access by different structure to list of nodes or edges
 * - Edges linking nodes
 * - Reconnection between 2 nodes by copy an edge from a reference network
 * - Create network made of nests
 * - Pack part of network in nest keeping connections 
 * - Transfer positions, delete edges
 * - Dialog to select one or several networks
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class NestUtils {
	public static List<CyNode> getNodeList(GraphPerspective network){return network.nodesList();}
	public static List<CyEdge> getEdgeList(GraphPerspective network){return network.edgesList();}
	public static ArrayList<CyNode> getSelectedNodes(CyNetwork network){
		ArrayList<CyNode> array=new ArrayList<CyNode>();
		array.addAll(network.getSelectedNodes());
		return array;
	}
	public static HashSet<CyNode> getNodeSet(GraphPerspective network){
		HashSet<CyNode> set=new HashSet<CyNode>();
		set.addAll(getNodeList(network));
		return set;
	}
	public static HashSet<CyEdge> getEdgeSet(GraphPerspective network){
		HashSet<CyEdge> set=new HashSet<CyEdge>();
		set.addAll(getEdgeList(network));
		return set;
	}
	public static ArrayList<CyNode> getNodeArray(GraphPerspective network){
		ArrayList<CyNode> array=new ArrayList<CyNode>();
		array.addAll(getNodeList(network));
		return array;
	}
	public static HashSet<CyEdge> edgesLinkingNodes(GraphPerspective edgeNetwork,HashSet<CyNode> nodeSet){
		HashSet<CyEdge> edges=new HashSet<CyEdge>();
		for(CyEdge edge:NestUtils.getEdgeSet(edgeNetwork)){
			if(nodeSet.contains(edge.getSource())&&nodeSet.contains(edge.getTarget())) edges.add(edge);
		}
		return edges;
	}
	public static CyEdge reconnect2Nodes(Node src,Node tgt,CyEdge fromEdge){
		CyAttributes attr=Cytoscape.getEdgeAttributes();
		CyEdge edge=Cytoscape.getCyEdge(src,tgt,Semantics.INTERACTION,attr.getStringAttribute(fromEdge.getIdentifier(),Semantics.INTERACTION),true,true);
		String[] attrNames=attr.getAttributeNames();
		for(int i=0;i<attrNames.length;i++){
			switch(attr.getType(attrNames[i])){
			case CyAttributes.TYPE_STRING:
				String string=attr.getStringAttribute(fromEdge.getIdentifier(),attrNames[i]);
				if(string!=null) attr.setAttribute(edge.getIdentifier(),attrNames[i],string);			
				break;
			case CyAttributes.TYPE_INTEGER:
				Integer integer=attr.getIntegerAttribute(fromEdge.getIdentifier(),attrNames[i]);
				if(integer!=null) attr.setAttribute(edge.getIdentifier(),attrNames[i],integer);		
				break;
			case CyAttributes.TYPE_FLOATING:
				Double doubl=attr.getDoubleAttribute(fromEdge.getIdentifier(),attrNames[i]);
				if(doubl!=null) attr.setAttribute(edge.getIdentifier(),attrNames[i],doubl);				
				break;
			}
		}
		return edge;
	}
	final static String modular="Modular";
	public static CyNetwork createNestNetwork(ArrayList<String> networksToNest,TreeMap<String,CyNetwork> networks){
		int ni=0;while(networks.keySet().contains(modular+ni)) ni++;
		CyNetwork network=Cytoscape.createNetwork(modular+ni,false);
		for(int i=0;i<networksToNest.size();i++){
			CyNode node=Cytoscape.getCyNode(networksToNest.get(i),true);
			network.addNode(node);
			node.setNestedNetwork(networks.get(networksToNest.get(i)));			
		}
		return network;
	}
	static String createConnectNestPack(CyNetwork network,CyNetwork nestIntoPack){
		double nx=0.0,ny=0.0;
		HashSet<CyNode> packSet=NestUtils.getNodeSet(nestIntoPack);
		if(packSet.size()==0) return null;
		CyNetworkView view=Cytoscape.getNetworkView(network.getIdentifier());
		for(CyNode node:packSet){
			NodeView nv=view.getNodeView(node);
			if(nv==null){
				return node.getIdentifier();
			}
			nx=nx+nv.getXPosition();
			ny=ny+nv.getYPosition();			
		}
		nx=nx/packSet.size();
		ny=ny/packSet.size();		
		CyNode pack=Cytoscape.getCyNode(nestIntoPack.getTitle(),true);
		network.addNode(pack);
		Cytoscape.getNodeAttributes().setAttribute(pack.getIdentifier(),"BIOPAX_NODE_TYPE","pathway");
		pack.setNestedNetwork(nestIntoPack);
		view.getNodeView(pack).setXPosition(nx);
		view.getNodeView(pack).setYPosition(ny);	
		for(CyEdge edge:NestUtils.getEdgeList(network)){
			Node src=edge.getSource();
			Node tgt=edge.getTarget();
			if(packSet.contains(src)){
				if(!packSet.contains(tgt)) network.addEdge(NestUtils.reconnect2Nodes(pack,tgt,edge));
			}else{
				if(packSet.contains(tgt)) network.addEdge(NestUtils.reconnect2Nodes(src,pack,edge));				
			}
		}
		for(CyNode node:packSet) network.removeNode(network.getIndex(node),false);
		return null;		
	}
	public static void deleteNestEdges(CyNetwork network){
		for(CyEdge edge:NestUtils.getEdgeList(network)){
			if((edge.getSource().getNestedNetwork()!=null)||(edge.getTarget().getNestedNetwork()!=null))
				network.removeEdge(network.getIndex(edge),true);
		}
	}
	public static void reportPosition(CyNetworkView fromView,CyNetworkView toView){
		for(CyNode node:getNodeList(toView.getNetwork())){
			NodeView toNodeView=toView.getNodeView(node);
			NodeView fromNodeView=fromView.getNodeView(node);
			if (fromNodeView!=null){
				toNodeView.setXPosition(fromNodeView.getXPosition());
				toNodeView.setYPosition(fromNodeView.getYPosition());
			}
		}
	}
	public static TreeMap<String,CyNetwork> getNetworksMap(){
		TreeMap<String,CyNetwork> networks=new TreeMap<String,CyNetwork>();
		for(CyNetwork network:Cytoscape.getNetworkSet()) networks.put(network.getTitle(),network);
		return networks;
	}
	public static String selectOneNetwork(TreeMap<String,CyNetwork> networks,String title, String label){
		String[] netNames=new String[networks.keySet().size()];
		int ni=0;for(String s:networks.keySet()) netNames[ni++]=s;
		if(netNames.length==0) return null;
		String selected=(String)JOptionPane.showInputDialog (Cytoscape.getDesktop(),label,title,
				JOptionPane.PLAIN_MESSAGE, null, netNames, netNames[0]);
		return selected;
	}
	public static ArrayList<String> selectNetworks(TreeMap<String,CyNetwork> networks,String title, String label){
		String[] netNames=new String[networks.keySet().size()];
		int ni=0;for(String s:networks.keySet()) netNames[ni++]=s;
		ArrayList<String> selection=new ArrayList<String>();
		ListDialog listBox=new ListDialog(Cytoscape.getDesktop(),title,label,netNames);
		listBox.launchDialog(selection);
		return selection;
	}
}
