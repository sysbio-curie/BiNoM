package fr.curie.BiNoM.cytoscape.nestmanager;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;

/**
 * Class of utilities mainly for module manager
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class ModuleUtils {
	final String  previousEdge="previous_edge";
	final String  hasModule="has_nested_network";
	final String  moduleNest="nested_network";
	/**
	 * Several methods to create and update attributes in tables
	 */
	public void checkEdgeColumn(CyNetwork net,String columnTitle,Class<?> type){
		if(net.getDefaultEdgeTable().getColumn(columnTitle)==null) net.getDefaultEdgeTable().createColumn(columnTitle,type,false);
	}
	public void checkNodeColumn(CyNetwork net,String columnTitle,Class<?> type){
		if(net.getDefaultNodeTable().getColumn(columnTitle)==null) net.getDefaultNodeTable().createColumn(columnTitle,type,false);
	}
	void checkModuleColumn(CyNetwork net){
		checkNodeColumn(net,hasModule,Boolean.class);
		checkNodeColumn(net,moduleNest,Long.class);
	}
	/**
	 * Create the node/module/nest pointing to a network 
	 */
	CyNode addModule(CyNetwork inNet,CyNetwork netPointer){
		CyNode newNode=inNet.addNode();
		newNode.setNetworkPointer(netPointer);
		inNet.getRow(newNode).set(CyNetwork.NAME,netPointer.getRow(netPointer).get(CyNetwork.NAME,String.class));			
		inNet.getRow(newNode).set(hasModule,true);
		inNet.getRow(newNode).set(moduleNest,netPointer.getSUID());
		return newNode;
	}
	/**
	 * Create an edge between a source and a target in the case of all edges are kept
	 * Put the name of the previous edge in table
	 */
	void createAllEdge(CyNetwork current,CyNode src,String interaction,CyNode tgt,String previousEdgeName){
		CyEdge newEdge=current.addEdge(src,tgt,true);
		String newEdgeName=current.getRow(src).get(CyNetwork.NAME,String.class)+
		"("+interaction+")"+current.getRow(tgt).get(CyNetwork.NAME,String.class);
		current.getRow(newEdge).set(CyNetwork.NAME,newEdgeName);
		current.getRow(newEdge).set(CyEdge.INTERACTION,interaction);
		current.getRow(newEdge).set(previousEdge,previousEdgeName);
	}
	/**
	 * Select networks in network panel
	 * Display a warning message when only one network is selected
	 */
	List<CyNetwork> selectNetsFromPanel(CyApplicationManager applicationManager,JFrame desktop){
		List<CyNetwork> selectedNets=applicationManager.getSelectedNetworks();
		if(selectedNets.size()<2){
			if(JOptionPane.showConfirmDialog(desktop,				
					"Less than 2 networks are selected, Confirm to Continue or Cancel\r\n (Selection of Several Networks in Network Panel by Control Click)",
					"List of Selected networks",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.CANCEL_OPTION) return null;
		}
		return selectedNets;
	}
	/**
	 * Select one network in the list of all networks
	 */	
	public static CyNetwork selectOneNetwork(CyNetworkManager networkManager,JFrame desktop,String title, String label){
		TreeMap<String,CyNetwork> nameToNet=new TreeMap<String,CyNetwork>();
		for(CyNetwork net:networkManager.getNetworkSet()) nameToNet.put(net.getRow(net).get(CyNetwork.NAME,String.class),net);
		String[] netNames=new String[nameToNet.keySet().size()];
		int ni=0;for(String s:nameToNet.keySet()) netNames[ni++]=s;
		String selected=(String)JOptionPane.showInputDialog(desktop,label,title,JOptionPane.PLAIN_MESSAGE,null,netNames,netNames[0]);
		if(selected==null) return null; else return nameToNet.get(selected);
	}
	/**
	 * Map reversing access from nodes to modules
	 */
	TreeMap<String,HashSet<CyNode>> getNodeToModules(CyNetwork modularNet){
		TreeMap<String,HashSet<CyNode>> nodeToModules=new TreeMap<String,HashSet<CyNode>>();
		for(CyNode moduleNode:modularNet.getNodeList()){
			CyNetwork containerNet=moduleNode.getNetworkPointer();
			if(containerNet==null) continue;
			for(CyNode nodeIn:containerNet.getNodeList()){
				String nodeInName=containerNet.getRow(nodeIn).get(CyNetwork.NAME,String.class);
				if(nodeToModules.containsKey(nodeInName)){
					nodeToModules.get(nodeInName).add(moduleNode);
				}else{
					HashSet<CyNode> modules= new HashSet<CyNode>();
					modules.add(moduleNode);
					nodeToModules.put(nodeInName,modules);
				}
			}
		}
		return nodeToModules;
	}
	/**
	 * Return new name of modular network to avoid to give the same name to 2 networks
	 */
	String noSynonymInNets(CyNetworkManager networkManager,String prefix){
		HashSet<String> netNames=new HashSet<String>();
		for(CyNetwork net:networkManager.getNetworkSet()) netNames.add(net.getRow(net).get(CyNetwork.NAME,String.class));
		int ni=0;while(netNames.contains(prefix+ni)) ni++;
		return prefix+ni;
	}
	
	
	
	
	// Nadir code
	/**
	 * Delete only edges between modules 
	 */
	public void deleteModulesEdges(CyNetwork network){
		List lst = new ArrayList<CyEdge>();
		for(CyEdge edge:network.getEdgeList()){
			if((edge.getSource().getNetworkPointer()!=null)&&(edge.getTarget().getNetworkPointer()!=null))
				lst.add(edge);
		}
		network.removeEdges(lst);

	}
	
	/**
	 * Create a useful logical link from nodes to modules used in several functions
	 */
	 public HashMap<CyNode,HashSet<CyNode>> doNodeToModules(CyNetwork network){
		HashMap<CyNode,HashSet<CyNode>> nodeToNests=new HashMap<CyNode,HashSet<CyNode>>();
		for(CyNode nest:network.getNodeList()){
			CyNetwork nestNW=nest.getNetworkPointer();
			if(nestNW==null) continue;		
			for(CyNode node:nestNW.getNodeList()){
				if(nodeToNests.containsKey(node)) 
					nodeToNests.get(node).add(nest);
				else{
					HashSet<CyNode> nests=new HashSet<CyNode>();
					nests.add(nest);
					nodeToNests.put(node,nests);
				}
			}
		}
		return nodeToNests;
	}
	 
	 
	static void createModuleConnection(CyNetwork referenceNW,CyNetwork currentNW,HashMap<CyNode,HashSet<CyNode>> nodesToNest){
		for(CyEdge edge:referenceNW.getEdgeList()){
			HashSet<CyNode> srcList=nodesToNest.get(edge.getSource());
			if(srcList==null) continue;
			HashSet<CyNode> tgtList=nodesToNest.get(edge.getTarget());
			if(tgtList==null) continue;
			
			String interaction=referenceNW.getRow(edge).get("interaction", String.class);
			if(interaction.equalsIgnoreCase("RIGHT")||interaction.equalsIgnoreCase("LEFT")) 
				interaction="MOLECULEFLOW";
			for(CyNode src:srcList){
				for(CyNode tgt:tgtList){
					if(!src.equals(tgt)){
						CyEdge ed = currentNW.addEdge(src, tgt, true);
						currentNW.getRow(ed).set("interaction", interaction);
						//currentNW.addEdge(Cytoscape.getCyEdge(src,tgt,"interaction",interaction,true,true));			
					}
				}
			}
		}	
	}
	
	
	public static HashSet<CyEdge> edgesLinkingNodes(CyNetwork edgeNetwork,HashSet<CyNode> nodeSet){
		HashSet<CyEdge> edges=new HashSet<CyEdge>();
		for(CyEdge edge:edgeNetwork.getEdgeList()){
			if(nodeSet.contains(edge.getSource())&&nodeSet.contains(edge.getTarget())) edges.add(edge);
		}
		return edges;
	}
	
	
	public static ArrayList<CyNode> getSelectedNodes(CyNetwork network){
		ArrayList<CyNode> array=new ArrayList<CyNode>();
		array.addAll(CyTableUtil.getNodesInState(network,"selected",true));
		return array;
	}
	
	
	public static HashSet<CyNode> getNodeSet(CyNetwork network){
		HashSet<CyNode> set=new HashSet<CyNode>();
		set.addAll(network.getNodeList());
		return set;
	}
	public static HashSet<CyEdge> getEdgeSet(CyNetwork network){
		HashSet<CyEdge> set=new HashSet<CyEdge>();
		set.addAll(network.getEdgeList());
		return set;
	}
	
}
