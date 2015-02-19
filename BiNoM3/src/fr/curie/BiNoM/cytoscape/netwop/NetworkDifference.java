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
package fr.curie.BiNoM.cytoscape.netwop;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import Main.Launcher;
import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;

public class NetworkDifference extends NetworkBinaryOperation {

    public NetworkDifference(CyNetwork left, CyNetwork right) {
    	super("Difference", "DIFF", left, right);
    }
    
    private static CyNode getNodeWithName(
            final CyNetwork net, final CyTable table,
            final String colname, final Object value){
        final Collection<CyRow> matchingRows = table.getMatchingRows(colname, value);
        final String primaryKeyColname = table.getPrimaryKey().getName();
        for (final CyRow row : matchingRows)
        {
            final Long nodeId = row.get(primaryKeyColname, Long.class);
            if (nodeId == null)
                continue;
            final CyNode node = net.getNode(nodeId);
            if (node == null)
                continue;
            return node;
        }
        return null;
    }
    
    
    private static CyEdge getEdge(
            final CyNetwork netFrom, CyNetwork netToCheck, CyEdge edge){
        CyNode from = edge.getSource();
        String idFrom = netFrom.getRow(from).get(CyNetwork.NAME, String.class);
        CyNode to = edge.getTarget();
        String idTo = netFrom.getRow(to).get(CyNetwork.NAME, String.class);
        
        Iterator i = netToCheck.getEdgeList().iterator();
        while(i.hasNext()){
        	CyEdge ed = (CyEdge) i.next();
        	if(netToCheck.getRow(ed.getSource()).get(CyNetwork.NAME, String.class).compareTo(idFrom) == 0)
        		if(netToCheck.getRow(ed.getTarget()).get(CyNetwork.NAME, String.class).compareTo(idTo) == 0)
        			return ed;
        }	   	
    	return null;
    }

    public CyNetwork eval(CyNetwork netw, CyNetworkView netView) {
	Iterator<CyNode> i = left.getNodeList().iterator();
	
		Collection<CyNetworkView> networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(left);
		Iterator<CyNetworkView> networkViewIterator = networkViews.iterator();
		CyNetworkView fromView =null;
		while(networkViewIterator.hasNext())
			fromView = networkViewIterator.next();
		
		while (i.hasNext()) {
		    CyNode node = (CyNode)i.next();
		    String id = left.getRow(node).get(CyNetwork.NAME, String.class);
		    if (getNodeWithName(right, right.getDefaultNodeTable(), "name", id) == null){
		    	
		    	
		    	
			    NetworkUtils.addNodeAndReportPosition(node, left, fromView, netw, netView);
				System.out.println("Node name: " + left.getRow(node).get(CyNetwork.NAME, String.class));

		    }
		}
	
		Iterator<CyEdge> ii = left.getEdgeList().iterator();
		while (ii.hasNext()) {
		    CyEdge edge = (CyEdge)ii.next();
		    //if (!right.containsEdge(edge))
		    if(getEdge(left, right, edge) == null)
		    	NetworkUtils.addEdgeAndConnectedNodesAndReportPositions(edge, left, fromView,netw, netView);
			/*
		    if (!right.containsEdge(edge))
			NetworkUtils.addEdgeAndConnectedNodesAndReportPositions(edge, left, netw);
		    else {
			CyNode src = (CyNode)edge.getSource();
			if (!right.containsNode(src))
			    NetworkUtils.addNodeAndReportPosition(src, left, netw);
			CyNode target = (CyNode)edge.getTarget();
			if (!right.containsNode(target))
			    NetworkUtils.addNodeAndReportPosition(target, left, netw);
		    }
			*/
		}
		
		return netw;
    }

    public void estimate(CyNetwork netw, Estimator estim) {
	java.util.Iterator i;
	
	i = left.getNodeList().iterator();
	while (i.hasNext()) {
	    CyNode node = (CyNode)i.next();
	    if (!right.containsNode(node)) {
		if (!netw.containsNode(node))
		    estim.addedNode(node);
	    } else {
		if (netw.containsNode(node))
		    estim.suppressedNode(node);
	    }
	}

	i = left.getEdgeList().iterator();
	while (i.hasNext()) {
	    CyEdge edge = (CyEdge)i.next();
	    if (!right.containsEdge(edge)) {
		if (!netw.containsEdge(edge))
		    estim.addedEdge(edge);
	    }
	    else {
		if (netw.containsEdge(edge))
		    estim.suppressedEdge(edge);
	    }
	}
    }
}
