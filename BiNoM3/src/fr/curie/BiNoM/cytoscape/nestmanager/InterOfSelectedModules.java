package fr.curie.BiNoM.cytoscape.nestmanager;


import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.vizmap.VisualStyle;

import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import Main.Launcher;
/**
 *  Merge selected nests using NestMerging
 *  
 *  @author Nadir Sella
 */ 
public class InterOfSelectedModules extends AbstractCyAction{
	
	public InterOfSelectedModules(){
		super(title,Launcher.getAdapter().getCyApplicationManager(),"network",Launcher.getAdapter().getCyNetworkViewManager());		
		setPreferredMenu(Launcher.appName + ".Module Manager");
	}
	
	private static final long serialVersionUID = 1L;
	final public static String title="Create Network from Intersection of Selected Modules";
	public void actionPerformed(ActionEvent v) {
//		NestMerging merging=new NestMerging(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork(),Launcher.getAdapter().getCyApplicationManager().getCurrentNetworkView());
//		if(merging.perform(ModuleUtils.getSelectedNodes(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()))) 
//			Launcher.getAdapter().getCyApplicationManager().getCurrentNetworkView().updateView();

	CyNetwork currentNetw = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
	String name = "";
	List<CyNode> nodes = CyTableUtil.getNodesInState(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true);
	
	ArrayList<ArrayList<String>> nodeNames = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> edgeNames = new ArrayList<ArrayList<String>>();
	
	if(nodes.size() > 1){
		CyNetwork netw = Launcher.getAdapter().getCyNetworkFactory().createNetwork();
		org.cytoscape.view.model.CyNetworkView netView = Launcher.getAdapter().getCyNetworkViewFactory().createNetworkView(netw);	
		
		for(CyNode node:nodes){
			if(node.getNetworkPointer() != null){
				CyNetwork netPointer = node.getNetworkPointer();
				nodeNames.add(getNodeNames(netPointer));
				edgeNames.add(getEdgeNames(netPointer));
			}
			if(name.compareTo("") == 0)
				name += currentNetw.getRow(node).get(CyNetwork.NAME, String.class);
			else
				name += "_" + currentNetw.getRow(node).get(CyNetwork.NAME, String.class);
		}
		
		netw.getRow(netw).set(CyNetwork.NAME, Launcher.noSynonymNetwork(name)+"_intersection");
		
		Launcher.getAdapter().getCyNetworkManager().addNetwork(netw);
		Launcher.getAdapter().getCyNetworkViewManager().addNetworkView(netView);
		
		
		
		CyNetwork netToCopy = nodes.get(0).getNetworkPointer();
				
		Collection<CyNetworkView> networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(netToCopy);
		Iterator<CyNetworkView> networkViewIterator = networkViews.iterator();
		CyNetworkView fromView =null;
		while(networkViewIterator.hasNext()){
			fromView = networkViewIterator.next();
				
				List<CyNode> nodeList = netToCopy.getNodeList();
				
				for(CyNode nd:nodeList){
					if(allContains(nodeNames, netToCopy.getRow(nd).get(CyNetwork.NAME, String.class)))
						NetworkUtils.addNodeAndReportPosition(nd, netToCopy, fromView, netw, netView);
				}
				
				List<CyEdge> edgeList = netToCopy.getEdgeList();
				
				for(CyEdge ed:edgeList){
					if(allContains(edgeNames, netToCopy.getRow(ed).get(CyNetwork.NAME, String.class)))
						try {
							NetworkUtils.addEd(netw, netToCopy, ed);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}		
			
	
		VisualStyle vs = Launcher.getAdapter().getVisualMappingManager().getVisualStyle(fromView);			
		Launcher.getAdapter().getVisualMappingManager().setVisualStyle(vs, netView);
	
	
		netView.fitContent();			
		netView.updateView();
		
		
	}
	else
		JOptionPane.showMessageDialog(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(), "Select al least two modules first");
	}
	
	public ArrayList getNodeNames(CyNetwork net){
		ArrayList list = new ArrayList<String>();
		
		List<CyNode> nodes = net.getNodeList();
		for(CyNode node:nodes){
			list.add(net.getRow(node).get(CyNetwork.NAME, String.class));
		}
		
		return list;
	}
	
	public ArrayList getEdgeNames(CyNetwork net){
		ArrayList list = new ArrayList<String>();
		
		List<CyEdge> edges = net.getEdgeList();
		for(CyEdge edge:edges){
			list.add(net.getRow(edge).get(CyNetwork.NAME, String.class));
		}
		
		return list;
	}
	
	public boolean allContains(ArrayList list_list, String str){
		Iterator iter = list_list.iterator();
		while(iter.hasNext()){
			ArrayList l = (ArrayList) iter.next();
			if(!l.contains(str))
				return false;
		}
			
		return true;
		
	}
}
