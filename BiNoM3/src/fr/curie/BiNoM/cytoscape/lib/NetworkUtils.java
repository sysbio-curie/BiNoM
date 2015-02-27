/*
   BiNoM Cytoscape Plugin
   Copyright (C) 2006-2007 Curie Institute, 26 rue d'Ulm, 75005 Paris - FRANCE

   BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
*/

/*
  BiNoM authors:
	Andrei Zinovyev : http://www.ihes.fr/~zinovyev
	Eric Viara : http://www.sysra.com/viara
	Laurence Calzone :	http://leibniz.biol.vt.edu/people/laurence/laurence.html
*/
package fr.curie.BiNoM.cytoscape.lib;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.vizmap.VisualStyle;

import Main.Launcher;

import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import java.util.Set;
import java.util.HashMap;

public class NetworkUtils {	
		
	public static CyNetwork addNd(CyNetwork network, CyNetwork fromNet, CyNode node){
		
		CyNode newNode = network.addNode();
		//set node name 
		
		System.out.println(fromNet.getRow(node).get(CyNetwork.NAME, String.class));
	
		network.getDefaultNodeTable().getRow(newNode.getSUID()).set("name", fromNet.getRow(node).get(CyNetwork.NAME, String.class));
		
		//set node attributes
		Iterator<CyColumn> columnsIterator = fromNet.getDefaultNodeTable().getColumns().iterator();
    	
	    while(columnsIterator.hasNext()){
	    	CyColumn col = columnsIterator.next();
	    	
	    	boolean set = false;
	    	try{		
		    	String value = fromNet.getRow(node).get(col.getName(), String.class);
		    	
		    	if(network.getDefaultNodeTable().getColumn(col.getName()) == null)
	    			network.getDefaultNodeTable().createColumn(col.getName(), String.class, false);
		    	
		    	network.getRow(newNode).set(col.getName(), value );
		    	set = true;
		    }catch(Exception e){}
		    
		    if (!set){
			    try{	
			    	double value = fromNet.getRow(node).get(col.getName(), Double.class);
			    	
			    	if(network.getDefaultNodeTable().getColumn(col.getName()) == null)
		    			network.getDefaultNodeTable().createColumn(col.getName(), Double.class, false);
			    	
			    	network.getRow(newNode).set(col.getName(), value );
			    	set = true;
			    }catch(Exception e){}
		    }
		    if (!set){
			    try{	
			    	int value = fromNet.getRow(node).get(col.getName(), Integer.class);
			    	
			    	if(network.getDefaultNodeTable().getColumn(col.getName()) == null)
		    			network.getDefaultNodeTable().createColumn(col.getName(), Integer.class, false);
			    	
			    	network.getRow(newNode).set(col.getName(), value );
			    	set = true;
			    }catch(Exception e){}
		    }    	
	    }
	    		
		return network;
	}
	
public static CyNetwork addEd(CyNetwork network, CyNetwork fromNet, CyEdge ed){
	
		//System.out.println("Node name sorce node : " + fromNet.getRow(ed.getSource()).get(CyNetwork.NAME, String.class));
		
		CyNode source = Launcher.getNodeWithName(network, network.getDefaultNodeTable(), "name", fromNet.getRow(ed.getSource()).get(CyNetwork.NAME, String.class));
		CyNode target = Launcher.getNodeWithName(network, network.getDefaultNodeTable(), "name", fromNet.getRow(ed.getTarget()).get(CyNetwork.NAME, String.class));
	
		System.out.println("source: "+source);
		System.out.println("target: "+target);
		
		CyEdge newEdge = network.addEdge(source, target, true);
		//set node name 
		network.getDefaultEdgeTable().getRow(newEdge.getSUID()).set("name", fromNet.getRow(ed).get(CyNetwork.NAME, String.class));
		
		//set node attributes
		Iterator<CyColumn> columnsIterator = fromNet.getDefaultEdgeTable().getColumns().iterator();
    	
	    while(columnsIterator.hasNext()){
	    	CyColumn col = columnsIterator.next();
	    	
	    	boolean set = false;
	    	try{
		    	String value = fromNet.getRow(ed).get(col.getName(), String.class);
		    	
		    	if(network.getDefaultEdgeTable().getColumn(col.getName()) == null)
	    			network.getDefaultEdgeTable().createColumn(col.getName(), String.class, false);
		    	
		    	network.getRow(newEdge).set(col.getName(), value );
		    	set = true;
		    }catch(Exception e){}
		    
		    if (!set){
			    try{	
			    	double value = fromNet.getRow(ed).get(col.getName(), Double.class);
			    	
			    	if(network.getDefaultEdgeTable().getColumn(col.getName()) == null)
		    			network.getDefaultEdgeTable().createColumn(col.getName(), Double.class, false);
			    	
			    	network.getRow(newEdge).set(col.getName(), value );
			    	set = true;
			    }catch(Exception e){}
		    }
		    if (!set){
			    try{	
			    	int value = ed.getSource().getNetworkPointer().getRow(ed).get(col.getName(), Integer.class);
			    	
			    	if(network.getDefaultEdgeTable().getColumn(col.getName()) == null)
		    			network.getDefaultEdgeTable().createColumn(col.getName(), Integer.class, false);
			    	
			    	network.getRow(newEdge).set(col.getName(), value );
			    	set = true;
			    }catch(Exception e){}
		    }    	
	    }	
		return network;
	}
	
	

    public static void reportPosition(CyNode node, CyNetworkView fromView, CyNetworkView toView) {
	
	    String id = fromView.getModel().getRow(node).get(CyNetwork.NAME, String.class);
	    
	    //System.out.println("toView: " + toView);
	
	    CyNode nd = (Launcher.getNodeWithName(toView.getModel(), toView.getModel().getDefaultNodeTable(), "name", id));
	    
	    //System.out.println("node: " + nd);
	    View<CyNode> toNodeView = toView.getNodeView(nd);
	    
	    toView.updateView();
	    
	    //System.out.println("tonodeView : " + toNodeView);
	    
	    View<CyNode> fromNodeView = fromView.getNodeView(node);
	    
	    //System.out.println("FromnodeView : " + fromNodeView);
	    
	    toNodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, fromNodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION));
	    toNodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, fromNodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION));
   	
//    View<CyNode> fromNodeView = toView.getNodeView(node);
//    fromNodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, fromView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION));
//    fromNodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, fromView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION));
//    
    
    
    
//		View<CyNode> toNodeView = toView.getNodeView(node);
//		if (toNodeView == null) {   	
//		    toNodeView = toView.adNodeView(node.getRootGraphIndex());
//		    //System.out.println("build toNodeView for " + node.getIdentifier());
//		}
//	
//		View<CyNode> fromNodeView = fromView.getNodeView(node);
//		if (fromNodeView == null) {
//		    fromNodeView = fromView.addNodeView(node.getRootGraphIndex());
//		    //System.out.println("build fromNodeView for " + node.getIdentifier());
//		}
	
    }
    
    public static void addNode(CyNode node, CyNetwork fromNetw, CyNetwork toNetw){
    	toNetw = addNd(toNetw, fromNetw, node);
    }

    public static void addNodeAndReportPosition(CyNode node, CyNetwork fromNetw, CyNetworkView fromNetView, CyNetwork toNetw, CyNetworkView toNetView) {
		toNetw = addNd(toNetw, fromNetw, node);
		//toNetw.addNode(node);
		
		toNetView.updateView();
		reportPosition(node, fromNetView, toNetView);
    }

    public static void setNetworkContents(CyNetwork toNetw, CyNetwork fromNetw) {
    	toNetw = clearNetwork(toNetw);
    	
    	Iterator i = fromNetw.getNodeList().iterator();
    	while(i.hasNext()){
    		CyNode nd = (CyNode) i.next();
    		toNetw = addNd(toNetw, fromNetw, nd);
    	}
    	
    	i = fromNetw.getEdgeList().iterator();
    	while(i.hasNext()){
    		CyEdge ed = (CyEdge) i.next();
    		toNetw = addEd(toNetw, fromNetw, ed);
    	}
    	//appendNetwork(fromNetw);
    }

    public static void setNetworkContentsAndReportPositions(CyNetwork toNetw, CyNetwork fromNetw, CyNetworkView netView) {
		setNetworkContents(toNetw, fromNetw);
	
		Collection<CyNetworkView> networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(fromNetw);
		Iterator<CyNetworkView> networkViewIterator = networkViews.iterator();
		CyNetworkView fromView =null;
		while(networkViewIterator.hasNext())
			fromView = networkViewIterator.next();
		
		
		
		networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(toNetw);
		networkViewIterator = networkViews.iterator();
		CyNetworkView toView =null;
		while(networkViewIterator.hasNext())
			toView = networkViewIterator.next();
	
		java.util.Iterator i = toNetw.getNodeList().iterator();
		while (i.hasNext()) {
		    CyNode node = (CyNode) i.next();		    
		    
		    reportPosition(node, fromView, netView);
		}
    }

    public static CyNetwork clearNetwork(CyNetwork netw) {
		java.util.Iterator i;
	
		i = netw.getNodeList().iterator();
		ArrayList<CyNode> nodes = new ArrayList<CyNode>();
		while (i.hasNext()) {
		    CyNode node = (CyNode)i.next();
		    nodes.add(node);
		   // netw.removeNodes(node);
		    //netw.removeNode(node.getRootGraphIndex(), false);
		}
		netw.removeNodes(nodes);
		
	
		i = netw.getEdgeList().iterator();
		ArrayList<CyEdge> edges = new ArrayList<CyEdge>();
		while (i.hasNext()) {
		    CyEdge edge = (CyEdge)i.next();
		    edges.add(edge);
		    //netw.removeEdge(edge.getRootGraphIndex(), false);
		}
		netw.removeEdges(edges);
	
		return netw;
    }

    public static void addEdgeAndConnectedNodes(CyEdge edge, CyNetwork fromNetw, CyNetwork toNetw) {
	//toNetw.addEdge(edge);	
		CyNode src = (CyNode)edge.getSource();
		if (!toNetw.containsNode(src))
			toNetw = addNd(toNetw, fromNetw, src);
		    //toNetw.addNode(src);
	
		CyNode target = (CyNode)edge.getTarget();
		if (!toNetw.containsNode(target))
			toNetw = addNd(toNetw, fromNetw, target);
		    //toNetw.addNode(target);
	
		if (!toNetw.containsEdge(edge))
			toNetw = addEd(toNetw, fromNetw, edge);
		    //toNetw.addEdge(edge);
    }

    public static void addEdgeAndConnectedNodesAndReportPositions(CyEdge edge, CyNetwork fromNetw, CyNetworkView fromNetView, CyNetwork toNetw, CyNetworkView netView) {
	
	//toNetw.addEdge(edge);

	CyNode src = (CyNode)edge.getSource();
	//if (!toNetw.containsNode(src))
	String id = fromNetw.getRow(src).get(CyNetwork.NAME, String.class);
	if (Launcher.getNodeWithName(toNetw, toNetw.getDefaultNodeTable(), "name", id) == null)
	    addNodeAndReportPosition(src, fromNetw, fromNetView, toNetw, netView);

	CyNode target = (CyNode)edge.getTarget();
	//if (!toNetw.containsNode(target))
	id = fromNetw.getRow(target).get(CyNetwork.NAME, String.class);
	if (Launcher.getNodeWithName(toNetw, toNetw.getDefaultNodeTable(), "name", id) == null)
	    addNodeAndReportPosition(target, fromNetw, fromNetView, toNetw, netView);

	if (!toNetw.containsEdge(edge))
		toNetw = addEd(toNetw, fromNetw, edge);
	    //toNetw.addEdge(edge);
    }

    public static CyNetwork getNetwork(String title) {
	Set netwSet = Launcher.getAdapter().getCyNetworkManager().getNetworkSet();

	java.util.Iterator i = netwSet.iterator();
	for (int n = 0; i.hasNext(); n++) {
	    CyNetwork netw = (CyNetwork)i.next();
	    if (netw.getRow(netw).get(CyNetwork.NAME, String.class).equals(title))
		return netw;
	}	

	return null;
    }

    public static String [] getNetworkNames() {
	return getNetworkNames("");
    }

    public static String [] getNetworkNames(String suffix) {
	Set<CyNetwork> netwSet = Launcher.getAdapter().getCyNetworkManager().getNetworkSet();

	String netwNames[] = new String[netwSet.size()];
	Iterator<CyNetwork> i = netwSet.iterator();
	for (int n = 0; i.hasNext(); n++) {
	    CyNetwork netw = (CyNetwork)i.next();
	    netwNames[n] = netw.getRow(netw).get(CyNetwork.NAME, String.class) + suffix;
	}	

	return netwNames;
    }

    public static CyNetwork getNetwork(int idx) {
		Set<CyNetwork> netwSet = Launcher.getAdapter().getCyNetworkManager().getNetworkSet();
	
		Iterator<CyNetwork> i = netwSet.iterator();
		for (int n = 0; i.hasNext(); n++) {
		    CyNetwork netw = (CyNetwork)i.next();
		    if (n == idx)
		    	return netw;
		}	
	
		return null;
    }

    public static CyNetwork copy(String title, CyNetwork src_netw) {
	CyNetwork dst_netw = Launcher.getAdapter().getCyNetworkFactory().createNetwork();
	CyNetworkView dst_netwView = Launcher.getAdapter().getCyNetworkViewFactory().createNetworkView(dst_netw);
	
	dst_netw.getRow(dst_netw).set(CyNetwork.NAME, title);
	copy(dst_netw, src_netw, dst_netwView);
	return dst_netw;
    }

    public static void copy(CyNetwork dst_netw, CyNetwork src_netw, CyNetworkView netView) {
		java.util.Iterator i;
		
		
		Collection<CyNetworkView> networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(src_netw);
		Iterator<CyNetworkView> networkViewIterator = networkViews.iterator();
		CyNetworkView fromView =null;
		while(networkViewIterator.hasNext())
			fromView = networkViewIterator.next();
		
		i = src_netw.getNodeList().iterator();
		while (i.hasNext()) {
		    CyNode node = (CyNode)i.next();
		    NetworkUtils.addNodeAndReportPosition(node, src_netw, fromView, dst_netw, netView);
		}
	
		i = src_netw.getEdgeList().iterator();
		while (i.hasNext()) {
		    CyEdge edge = (CyEdge)i.next();
		    NetworkUtils.addEdgeAndConnectedNodesAndReportPositions(edge, src_netw, fromView, dst_netw, netView);
		}
    }

//    public static void clearAndCopy(CyNetwork dst_netw, CyNetwork src_netw) {
//	clearNetwork(dst_netw);
//	copy(dst_netw, src_netw);
//    }

    public static CyNetwork makeBackupNetwork(CyNetwork netw, String suffix) {
	String title = netw.getRow(netw).get(CyNetwork.NAME, String.class) + suffix;
	for (int i = 1; ; i++) {
	    if (NetworkUtils.getNetwork(title + i) == null) {
		title = title + i;
		break;
	    }
	}
	
	return NetworkUtils.copy(title, netw);
    }

    public static CyNetwork makeBackupNetwork(CyNetwork netw) {
	return makeBackupNetwork(netw, "_old.");
    }
    
    public static CyNetwork[] getNetworks(int idxs[]) {
    	CyNetwork networks[] = new CyNetwork[idxs.length];
    	for (int n = 0; n < idxs.length; n++) {
    	    networks[n] = NetworkUtils.getNetwork(idxs[n]);
    	}

    	return networks;
        }
    

}

