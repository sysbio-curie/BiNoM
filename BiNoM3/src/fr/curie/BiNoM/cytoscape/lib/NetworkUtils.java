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
import org.cytoscape.model.subnetwork.CyRootNetwork;
import org.cytoscape.model.subnetwork.CySubNetwork;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;

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
			    	boolean value = fromNet.getRow(node).get(col.getName(), Boolean.class);
			    	
			    	if(network.getDefaultNodeTable().getColumn(col.getName()) == null)
		    			network.getDefaultNodeTable().createColumn(col.getName(), Boolean.class, false);
			    	
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
	
public static CyNetwork addEd(CyNetwork network, CyNetwork fromNet, CyEdge ed) throws Exception{
	
		
		CyNode source = Launcher.getNodeWithName(network, network.getDefaultNodeTable(), "name", fromNet.getRow(ed.getSource()).get(CyNetwork.NAME, String.class));
		CyNode target = Launcher.getNodeWithName(network, network.getDefaultNodeTable(), "name", fromNet.getRow(ed.getTarget()).get(CyNetwork.NAME, String.class));
	
		
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
	    	
	    CyNode nd = (Launcher.getNodeWithName(toView.getModel(), toView.getModel().getDefaultNodeTable(), "name", id));
	    
	    View<CyNode> toNodeView = toView.getNodeView(nd);
	    
	    toView.updateView();
	 	    
	    View<CyNode> fromNodeView = fromView.getNodeView(node);
	    	    
	    toNodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, fromNodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION));
	    toNodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, fromNodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION));
    }
    
    public static void addNode(CyNode node, CyNetwork fromNetw, CyNetwork toNetw){
    	toNetw = addNd(toNetw, fromNetw, node);
    }

    public static void addNodeAndReportPosition(CyNode node, CyNetwork fromNetw, CyNetworkView fromNetView, CyNetwork toNetw, CyNetworkView toNetView) {
		toNetw = addNd(toNetw, fromNetw, node);
		
		toNetView.updateView();
		reportPosition(node, fromNetView, toNetView);
    }

    public static void setNetworkContents(CyNetwork toNetw, CyNetwork fromNetw) {
    	clearNetwork(toNetw);
    	
    	Iterator i = fromNetw.getNodeList().iterator();
    	while(i.hasNext()){
    		CyNode nd = (CyNode) i.next();
    		toNetw = addNd(toNetw, fromNetw, nd);
    	}
    	
    	i = fromNetw.getEdgeList().iterator();
    	while(i.hasNext()){
    		CyEdge ed = (CyEdge) i.next();
    		try {
				toNetw = addEd(toNetw, fromNetw, ed);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
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

    public static void clearNetwork(CyNetwork netw) {
		java.util.Iterator i;
	
		i = netw.getNodeList().iterator();
		ArrayList<CyNode> nodes = new ArrayList<CyNode>();
		while (i.hasNext()) {
		    CyNode node = (CyNode)i.next();
		    nodes.add(node);
		}
		netw.removeNodes(nodes);
		
	
		i = netw.getEdgeList().iterator();
		ArrayList<CyEdge> edges = new ArrayList<CyEdge>();
		while (i.hasNext()) {
		    CyEdge edge = (CyEdge)i.next();
		    edges.add(edge);
		}
		netw.removeEdges(edges);
    }

    public static void addEdgeAndConnectedNodes(CyEdge edge, CyNetwork fromNetw, CyNetwork toNetw) {
		CyNode src = (CyNode)edge.getSource();
		if (!toNetw.containsNode(src))
			toNetw = addNd(toNetw, fromNetw, src);
	
		CyNode target = (CyNode)edge.getTarget();
		if (!toNetw.containsNode(target))
			toNetw = addNd(toNetw, fromNetw, target);
	
		if (!toNetw.containsEdge(edge))
			try {
				toNetw = addEd(toNetw, fromNetw, edge);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }

    public static void addEdgeAndConnectedNodesAndReportPositions(CyEdge edge, CyNetwork fromNetw, CyNetworkView fromNetView, CyNetwork toNetw, CyNetworkView netView) {
	

	CyNode src = (CyNode)edge.getSource();
	String id = fromNetw.getRow(src).get(CyNetwork.NAME, String.class);
	if (Launcher.getNodeWithName(toNetw, toNetw.getDefaultNodeTable(), "name", id) == null)
	    addNodeAndReportPosition(src, fromNetw, fromNetView, toNetw, netView);

	CyNode target = (CyNode)edge.getTarget();
	id = fromNetw.getRow(target).get(CyNetwork.NAME, String.class);
	if (Launcher.getNodeWithName(toNetw, toNetw.getDefaultNodeTable(), "name", id) == null)
	    addNodeAndReportPosition(target, fromNetw, fromNetView, toNetw, netView);

	if (!toNetw.containsEdge(edge))
		try {
			toNetw = addEd(toNetw, fromNetw, edge);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

//    public static CyNetwork copy(String title, CyNetwork src_netw) {
//	CyNetwork dst_netw = Launcher.getAdapter().getCyNetworkFactory().createNetwork();
//	CyNetworkView dst_netwView = Launcher.getAdapter().getCyNetworkViewFactory().createNetworkView(dst_netw);
//	dst_netw.getRow(dst_netw).set(CyNetwork.NAME, title);
//
//	Launcher.getAdapter().getCyNetworkManager().addNetwork(dst_netw);
//	copy(dst_netw, src_netw, dst_netwView);
//	Launcher.getAdapter().getCyNetworkViewManager().addNetworkView(dst_netwView);
//	
//	Collection<CyNetworkView> networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(src_netw);
//	Iterator<CyNetworkView> networkViewIterator = networkViews.iterator();
//	CyNetworkView fromView =null;
//	while(networkViewIterator.hasNext())
//		fromView = networkViewIterator.next();
//	
//	VisualStyle vs = Launcher.getAdapter().getVisualMappingManager().getVisualStyle(fromView);			
//	Launcher.getAdapter().getVisualMappingManager().setVisualStyle(vs, dst_netwView);
//	dst_netwView.fitContent();			
//	dst_netwView.updateView();
//	
//	return dst_netw;
//    }
    
    public static CyNetwork createAndCopyInSameNetworkCollection(String title, CyNetwork srcNetw) {
		CyRootNetwork networkCollection = Launcher.getAdapter().getCyRootNetworkManager().getRootNetwork(srcNetw);
		
		CySubNetwork dstNetw = networkCollection.addSubNetwork();
		CyNetworkView dst_netwView = Launcher.getAdapter().getCyNetworkViewFactory().createNetworkView(dstNetw);
		
		dstNetw.getRow(dstNetw).set(CyNetwork.NAME, title);
	
		//add network
		Launcher.getAdapter().getCyNetworkManager().addNetwork(dstNetw);
		
		//add nodes
		Iterator i = srcNetw.getNodeList().iterator();
		while (i.hasNext()) {
		    CyNode node = (CyNode)i.next();
		    dstNetw.addNode(node);
		}
		
		//add edges
		i = srcNetw.getEdgeList().iterator();
		while (i.hasNext()) {
		    CyEdge edge = (CyEdge)i.next();
		    dstNetw.addEdge(edge);
		}
		
		Launcher.getAdapter().getCyNetworkViewManager().addNetworkView(dst_netwView);	
		
		Collection<CyNetworkView> networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(srcNetw);
		Iterator<CyNetworkView> networkViewIterator = networkViews.iterator();
		CyNetworkView fromView =null;
		while(networkViewIterator.hasNext())
			fromView = networkViewIterator.next();
	
		i = srcNetw.getNodeList().iterator();
		while (i.hasNext()) {
		    CyNode node = (CyNode)i.next();
		    View<CyNode> nodeViewNew = dst_netwView.getNodeView(node);
		    View<CyNode> nodeViewOld = fromView.getNodeView(node);
		    
	    	Double newX = nodeViewOld.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
	    	Double newY = nodeViewOld.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
	    	
	    	nodeViewNew.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, newX);
	    	nodeViewNew.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, newY); 
		}
		
		BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(srcNetw);
    	if(biopax!=null)
    		BioPAXSourceDB.getInstance().setBioPAX(dstNetw,biopax);
		
		dst_netwView.updateView();
		VisualStyle vs = Launcher.getAdapter().getVisualMappingManager().getVisualStyle(fromView);	
		Launcher.getAdapter().getVisualMappingManager().setVisualStyle(vs, dst_netwView);
		vs.apply(dst_netwView);
		dst_netwView.fitContent();
		dst_netwView.updateView();
		
		return dstNetw;
    }
    
    
    
    
    
    
    public static void copyNodesAndEdgesInSameNetworkCollection(CySubNetwork dstNetw, CyNetwork srcNetw, CyNetworkView netView) {
		CyRootNetwork networkCollection = Launcher.getAdapter().getCyRootNetworkManager().getRootNetwork(srcNetw);	
		
		Collection<CyNetworkView> networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(srcNetw);
		Iterator<CyNetworkView> networkViewIterator = networkViews.iterator();
		CyNetworkView fromView =null;
		while(networkViewIterator.hasNext())
			fromView = networkViewIterator.next();
		
//		VisualStyle vs = Launcher.getAdapter().getVisualMappingManager().getVisualStyle(fromView);			
//		Launcher.getAdapter().getVisualMappingManager().setVisualStyle(vs, netView);
		
		
		//add nodes
		Iterator i = srcNetw.getNodeList().iterator();
		while (i.hasNext()) {
		    CyNode node = (CyNode)i.next();
		    if(!dstNetw.containsNode(node)){
		    	dstNetw.addNode(node);
	    		
	    		netView.updateView();
	    		
	    		View<CyNode> nodeViewNew = netView.getNodeView(node);
			    View<CyNode> nodeViewOld = fromView.getNodeView(node);
			    
		    	Double newX = nodeViewOld.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
		    	Double newY = nodeViewOld.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
		    	
		    	nodeViewNew.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, newX);
		    	nodeViewNew.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, newY); 
		    }
		}
		
		//add edges
		i = srcNetw.getEdgeList().iterator();
		while (i.hasNext()) {
		    CyEdge edge = (CyEdge)i.next();
		    if(!dstNetw.containsEdge(edge))
		    	dstNetw.addEdge(edge);
		}
		
		
		netView.updateView();
		VisualStyle vs = Launcher.getAdapter().getVisualMappingManager().getVisualStyle(netView);	
		Launcher.getAdapter().getVisualMappingManager().setVisualStyle(vs, netView);
		vs.apply(netView);
		netView.fitContent();
		netView.updateView();
    }
    
    

    public static void copyInDifferentNetworkCollection(CyNetwork dstNetw, CyNetwork srcNetw, CyNetworkView dstNetView) {
		java.util.Iterator i;
		
		Collection<CyNetworkView> networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(srcNetw);
		Iterator<CyNetworkView> networkViewIterator = networkViews.iterator();
		CyNetworkView fromNetwView =null;
		while(networkViewIterator.hasNext())
			fromNetwView = networkViewIterator.next();
		
		i = srcNetw.getNodeList().iterator();
		while (i.hasNext()) {
		    CyNode node = (CyNode)i.next();
		    NetworkUtils.addNodeAndReportPosition(node, srcNetw, fromNetwView, dstNetw, dstNetView);
		}
	
		i = srcNetw.getEdgeList().iterator();
		while (i.hasNext()) {
		    CyEdge edge = (CyEdge)i.next();
		    NetworkUtils.addEdgeAndConnectedNodesAndReportPositions(edge, srcNetw, fromNetwView, dstNetw, dstNetView);
		}	
		
		dstNetView.updateView();
		VisualStyle vs = Launcher.getAdapter().getVisualMappingManager().getVisualStyle(dstNetView);	
		Launcher.getAdapter().getVisualMappingManager().setVisualStyle(vs, dstNetView);
		vs.apply(dstNetView);
		dstNetView.fitContent();
	    dstNetView.updateView();
    }


    public static CyNetwork makeBackupNetwork(CyNetwork netw, String suffix) {
		String title = netw.getRow(netw).get(CyNetwork.NAME, String.class) + suffix;
		for (int i = 1; ; i++) {
		    if (NetworkUtils.getNetwork(title + i) == null) {
			title = title + i;
			break;
		    }
		}
		return NetworkUtils.createAndCopyInSameNetworkCollection(title, netw);
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
    
    public static void clearAndCopy(CyNetwork dstNetw, CyNetwork srcNetw) {
    	clearNetwork(dstNetw);
    	Collection<CyNetworkView> networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(dstNetw);
		Iterator<CyNetworkView> networkViewIterator = networkViews.iterator();
		CyNetworkView destNetwView =null;
		
		while(networkViewIterator.hasNext())
			destNetwView = networkViewIterator.next();
		
		System.out.println("destNetwView: "+destNetwView);
		
		copyInDifferentNetworkCollection(dstNetw, srcNetw, destNetwView);
    	BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(srcNetw);
    	if(biopax!=null)
    		BioPAXSourceDB.getInstance().setBioPAX(dstNetw,biopax);
  	
    }
}

