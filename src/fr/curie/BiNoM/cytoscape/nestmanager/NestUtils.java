package fr.curie.BiNoM.cytoscape.nestmanager;
/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE  
*/
import fr.curie.BiNoM.cytoscape.utils.ListDialog;
import giny.model.GraphPerspective;
import giny.model.Node;
import giny.view.NodeView;

import java.util.Collection;
import java.util.HashMap;
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
 * - Creation of nests and edges in different contexts
 * - Positioning of nodes and nests
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
	/**
	 * Connect 2 nodes using attributes from an edge
	 * ID may be different from node1(interaction)node2 by copying attributes
	*/
	public static CyEdge connect2NodesFrom(Node src,Node tgt,CyEdge fromEdge,String idInterValue){
		CyAttributes attr=Cytoscape.getEdgeAttributes();
		CyEdge edge=Cytoscape.getCyEdge(src,tgt,Semantics.INTERACTION,idInterValue,true,true);
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
	/**
	 * Create a network made of nests,
	 * Avoid same name for 2 networks using TreeMap from dialog
	*/
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
	/**
	 * Delete only edges between nests 
	 */
	public static void deleteNestEdges(CyNetwork network){
		for(CyEdge edge:NestUtils.getEdgeList(network)){
			if((edge.getSource().getNestedNetwork()!=null)&&(edge.getTarget().getNestedNetwork()!=null))
				network.removeEdge(network.getIndex(edge),true);
		}
	}
	/**
	 * Transfer position of all nodes to a view to another
	 */
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
	/**
	 * create nests from a list of networks placing them at the mean coordinate of nodes from networks
	 */
	static boolean createAndPlaceNests(CyNetwork network,Collection<CyNetwork> nestNetworks){
		for(CyNetwork nest:nestNetworks){
			double nx=0.0,ny=0.0;
			HashSet<CyNode> packSet=NestUtils.getNodeSet(nest);
			if(packSet.size()==0){
				continue;
			}
			CyNetworkView view=Cytoscape.getNetworkView(network.getIdentifier());
			for(CyNode node:packSet){
				NodeView nv=view.getNodeView(node);
				if(nv==null){
					JOptionPane.showMessageDialog(Cytoscape.getDesktop(),node.getIdentifier()+" of "+nest.getIdentifier()+
							"\r\nis not in current network","Cannot pack for this network",JOptionPane.WARNING_MESSAGE);
					return false;
				}
				nx=nx+nv.getXPosition();
				ny=ny+nv.getYPosition();			
			}
			nx=nx/packSet.size();
			ny=ny/packSet.size();		
			CyNode pack=Cytoscape.getCyNode(nest.getTitle(),true);
			network.addNode(pack);
			Cytoscape.getNodeAttributes().setAttribute(pack.getIdentifier(),"BIOPAX_NODE_TYPE","pathway");
			pack.setNestedNetwork(nest);
			view.getNodeView(pack).setXPosition(nx);
			view.getNodeView(pack).setYPosition(ny);			
		}			
		return true;
	}
	/**
	 * Clone the current network and add postfix at the end of its name
	 */
	static CyNetwork cloneCurrent(String postfix){
		CyNetwork oldNW=Cytoscape.getCurrentNetwork();
		CyNetwork newNW=Cytoscape.createNetwork(oldNW.nodesList(),oldNW.edgesList(),oldNW.getTitle()+postfix);
		CyNetworkView newView=Cytoscape.createNetworkView(newNW);
		NestUtils.reportPosition(Cytoscape.getNetworkView(oldNW.getIdentifier()),newView);
		Cytoscape.setCurrentNetwork(newNW.getIdentifier());
		newView.redrawGraph(true,true);
		return newNW;
	}
	/**
	 * Create a useful logical link from nodes to nests used in several functions
	 */
	static HashMap<CyNode,HashSet<CyNode>> doNodeToNests(CyNetwork network){
		HashMap<CyNode,HashSet<CyNode>> nodeToNests=new HashMap<CyNode,HashSet<CyNode>>();
		for(CyNode nest:NestUtils.getNodeList(network)){
			GraphPerspective nestNW=nest.getNestedNetwork();
			if(nestNW==null) continue;		
			for(CyNode node:NestUtils.getNodeList(nestNW)){
				if(nodeToNests.containsKey(node)) nodeToNests.get(node).add(nest);
				else{
					HashSet<CyNode> nests=new HashSet<CyNode>();
					nests.add(nest);
					nodeToNests.put(node,nests);
				}
			}
		}
		return nodeToNests;
	}
	/**
	 * Create edges between nests, compacting synonym edges in one
	 */
	static void createNestConnection(CyNetwork referenceNW,CyNetwork currentNW,HashMap<CyNode,HashSet<CyNode>> nodesToNest){
		for(CyEdge edge:NestUtils.getEdgeList(referenceNW)){
			HashSet<CyNode> srcList=nodesToNest.get(edge.getSource());
			if(srcList==null) continue;
			HashSet<CyNode> tgtList=nodesToNest.get(edge.getTarget());
			if(tgtList==null) continue;
			String interaction=Cytoscape.getEdgeAttributes().getStringAttribute(edge.getIdentifier(),Semantics.INTERACTION);
			if(interaction.equalsIgnoreCase("RIGHT")||interaction.equalsIgnoreCase("LEFT")) interaction="MOLECULEFLOW";
			for(CyNode src:srcList){
				for(CyNode tgt:tgtList){
					if(!src.equals(tgt)) currentNW.addEdge(Cytoscape.getCyEdge(src,tgt,Semantics.INTERACTION,interaction,true,true));					
				}
			}
		}	
	}
	/**
	 * Reconnect every edge of the initial network between nodes and nests,
	 * duplicating it if necessary,
	 * Use the initial ID of edges, () are replaced by [] to not disturb Cytoscape
	 */
	static void explicitConnectNestAndNode(CyNetwork network){
		HashMap<CyNode,HashSet<CyNode>> nodesToNest=doNodeToNests(network);
		for(CyEdge edge:NestUtils.getEdgeList(network)){
			Node src=edge.getSource();
			Node tgt=edge.getTarget();
			String idInterValue=edge.getIdentifier();
			idInterValue=idInterValue.replace('(','[');
			idInterValue=idInterValue.replace(')',']');
			if(nodesToNest.get(src)!=null) for(CyNode s:nodesToNest.get(src)) 
				network.addEdge(connect2NodesFrom(s,tgt,edge,idInterValue));
			if(nodesToNest.get(tgt)!=null) for(CyNode t:nodesToNest.get(tgt)) 
				network.addEdge(connect2NodesFrom(src,t,edge,idInterValue));
			if((nodesToNest.get(src)!=null)&&(nodesToNest.get(tgt)!=null))
				for(CyNode s:nodesToNest.get(src)) for(CyNode t:nodesToNest.get(tgt)) if(s!=t) 
					network.addEdge(connect2NodesFrom(s,t,edge,idInterValue));
		}				
	}
	/**
	 * Return a tree map of networks useful in dialog for sorted list
	 */
	public static TreeMap<String,CyNetwork> getNetworksMap(){
		TreeMap<String,CyNetwork> networks=new TreeMap<String,CyNetwork>();
		for(CyNetwork network:Cytoscape.getNetworkSet()) networks.put(network.getTitle(),network);
		return networks;
	}
	/**
	 * Dialog to select one network
	 */
	public static String selectOneNetwork(TreeMap<String,CyNetwork> networks,String title, String label){
		String[] netNames=new String[networks.keySet().size()];
		int ni=0;for(String s:networks.keySet()) netNames[ni++]=s;
		if(netNames.length==0) return null;
		String selected=(String)JOptionPane.showInputDialog (Cytoscape.getDesktop(),label,title,
				JOptionPane.PLAIN_MESSAGE, null, netNames, netNames[0]);
		return selected;
	}
	/**
	 * Dialog to select several networks
	 */
	public static ArrayList<String> selectNetworks(TreeMap<String,CyNetwork> networks,String title, String label){
		String[] netNames=new String[networks.keySet().size()];
		int ni=0;for(String s:networks.keySet()) netNames[ni++]=s;
		ArrayList<String> selection=new ArrayList<String>();
		ListDialog listBox=new ListDialog(Cytoscape.getDesktop(),title,label,netNames);
		listBox.launchDialog(selection);
		return selection;
	}
}
