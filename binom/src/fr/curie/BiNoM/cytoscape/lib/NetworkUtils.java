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

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import cytoscape.visual.VisualMappingManager;
import cytoscape.visual.VisualStyle;
import java.io.*;
import cytoscape.task.ui.JTaskConfig;

import javax.swing.SwingUtilities;
import ding.view.DGraphView;
import edu.rpi.cs.xgmml.*;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.CyEdge;
import cytoscape.view.CyNetworkView;
import cytoscape.data.Semantics;
import cytoscape.visual.*;

import giny.view.NodeView;
import cytoscape.ding.CyGraphLOD;
import cytoscape.ding.DingNetworkView;

import java.util.Vector;
import java.util.Set;
import java.util.HashMap;

public class NetworkUtils {

    public static void reportPosition(CyNode node, CyNetworkView fromView,
				      CyNetworkView toView) {
	NodeView toNodeView = toView.getNodeView(node);
	if (toNodeView == null) {
	    toNodeView = toView.addNodeView(node.getRootGraphIndex());
	    //System.out.println("build toNodeView for " + node.getIdentifier());
	}

	NodeView fromNodeView = fromView.getNodeView(node);
	if (fromNodeView == null) {
	    fromNodeView = fromView.addNodeView(node.getRootGraphIndex());
	    //System.out.println("build fromNodeView for " + node.getIdentifier());
	}

	toNodeView.setXPosition(fromNodeView.getXPosition());
	toNodeView.setYPosition(fromNodeView.getYPosition());
    }

    public static void addNodeAndReportPosition(CyNode node, CyNetwork fromNetw,
						CyNetwork toNetw) {
	toNetw.addNode(node);

	CyNetworkView fromView = Cytoscape.getNetworkView(fromNetw.getIdentifier());
	CyNetworkView toView = Cytoscape.getNetworkView(toNetw.getIdentifier());
	reportPosition(node, fromView, toView);
    }

    public static void setNetworkContents(CyNetwork toNetw, CyNetwork fromNetw) {
	clearNetwork(toNetw).appendNetwork(fromNetw);
    }

    public static void setNetworkContentsAndReportPositions(CyNetwork toNetw, CyNetwork fromNetw) {
	setNetworkContents(toNetw, fromNetw);

	CyNetworkView fromView = Cytoscape.getNetworkView(fromNetw.getIdentifier());
	CyNetworkView toView = Cytoscape.getNetworkView(toNetw.getIdentifier());

	java.util.Iterator i = toNetw.nodesIterator();
	while (i.hasNext()) {
	    CyNode node = (CyNode) i.next();
	    NodeView nodeView = toView.getNodeView(node);
	    reportPosition(node, fromView, toView);
	}
    }

    public static CyNetwork clearNetwork(CyNetwork netw) {
	java.util.Iterator i;

	i = netw.nodesIterator();
	while (i.hasNext()) {
	    CyNode node = (CyNode)i.next();
	    netw.removeNode(node.getRootGraphIndex(), false);
	}

	i = netw.edgesIterator();
	while (i.hasNext()) {
	    CyEdge edge = (CyEdge)i.next();
	    netw.removeEdge(edge.getRootGraphIndex(), false);
	}

	return netw;
    }

    public static void addEdgeAndConnectedNodes(CyEdge edge, CyNetwork toNetw) {
	//toNetw.addEdge(edge);
	
	CyNode src = (CyNode)edge.getSource();
	if (!toNetw.containsNode(src))
	    toNetw.addNode(src);

	CyNode target = (CyNode)edge.getTarget();
	if (!toNetw.containsNode(target))
	    toNetw.addNode(target);

	if (!toNetw.containsEdge(edge))
	    toNetw.addEdge(edge);
    }

    public static void addEdgeAndConnectedNodesAndReportPositions(CyEdge edge, CyNetwork fromNetw, CyNetwork toNetw) {
	
	//toNetw.addEdge(edge);

	CyNode src = (CyNode)edge.getSource();
	if (!toNetw.containsNode(src))
	    addNodeAndReportPosition(src, fromNetw, toNetw);

	CyNode target = (CyNode)edge.getTarget();
	if (!toNetw.containsNode(target))
	    addNodeAndReportPosition(target, fromNetw, toNetw);

	if (!toNetw.containsEdge(edge))
	    toNetw.addEdge(edge);
    }

    public static CyNetwork getNetwork(String title) {
	Set netwSet = Cytoscape.getNetworkSet();

	java.util.Iterator i = netwSet.iterator();
	for (int n = 0; i.hasNext(); n++) {
	    CyNetwork netw = (CyNetwork)i.next();
	    if (netw.getTitle().equals(title))
		return netw;
	}	

	return null;
    }

    public static String [] getNetworkNames() {
	return getNetworkNames("");
    }

    public static String [] getNetworkNames(String suffix) {
	Set netwSet = Cytoscape.getNetworkSet();

	String netwNames[] = new String[netwSet.size()];
	java.util.Iterator i = netwSet.iterator();
	for (int n = 0; i.hasNext(); n++) {
	    CyNetwork netw = (CyNetwork)i.next();
	    netwNames[n] = netw.getTitle() + suffix;
	}	

	return netwNames;
    }

    public static CyNetwork getNetwork(int idx) {
	Set netwSet = Cytoscape.getNetworkSet();

	java.util.Iterator i = netwSet.iterator();
	for (int n = 0; i.hasNext(); n++) {
	    CyNetwork netw = (CyNetwork)i.next();
	    if (n == idx)
		return netw;
	}	

	return null;
    }

    public static CyNetwork copy(String title, CyNetwork src_netw) {
	CyNetwork dst_netw = Cytoscape.createNetwork(title);
	copy(dst_netw, src_netw);
	return dst_netw;
    }

    public static void copy(CyNetwork dst_netw, CyNetwork src_netw) {
	java.util.Iterator i;
	
	i = src_netw.nodesIterator();
	while (i.hasNext()) {
	    CyNode node = (CyNode)i.next();
	    NetworkUtils.addNodeAndReportPosition(node, src_netw, dst_netw);
	}

	i = src_netw.edgesIterator();
	while (i.hasNext()) {
	    CyEdge edge = (CyEdge)i.next();
	    NetworkUtils.addEdgeAndConnectedNodesAndReportPositions(edge, src_netw, dst_netw);
	}
    }

    public static void clearAndCopy(CyNetwork dst_netw, CyNetwork src_netw) {
	clearNetwork(dst_netw);
	copy(dst_netw, src_netw);
    }

    public static CyNetwork makeBackupNetwork(CyNetwork netw, String suffix) {
	String title = netw.getTitle() + suffix;
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

