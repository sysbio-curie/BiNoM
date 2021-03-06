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

import java.util.Collection;
import java.util.Iterator;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;

import Main.Launcher;
import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;

public class NetworkIntersection extends NetworkBinaryOperation {

    public NetworkIntersection(CyNetwork left, CyNetwork right) {
    	super("Intersection", "INTERSECT", left, right);
    }

    public CyNetwork eval(CyNetwork netw, CyNetworkView view) {

	java.util.Iterator i;
	
	Collection<CyNetworkView> networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(left);
	Iterator<CyNetworkView> networkViewIterator = networkViews.iterator();
	CyNetworkView fromView =null;
	while(networkViewIterator.hasNext())
		fromView = networkViewIterator.next();

	i = left.getNodeList().iterator();
	while (i.hasNext()) {
	    CyNode node = (CyNode)i.next();
	    if (containsNode(right, node))
		NetworkUtils.addNodeAndReportPosition(node, left, fromView, netw, view);
	}

	i = left.getEdgeList().iterator();
	while (i.hasNext()) {
	    CyEdge edge = (CyEdge)i.next();
	    if (containsEdge(right, edge))
		NetworkUtils.addEdgeAndConnectedNodesAndReportPositions(edge, left, fromView, netw, view);
	}
	return netw;
    }

    public boolean containsNode(CyNetwork netw, CyNode node) {
	return netw.containsNode(node);
    }

    public boolean containsEdge(CyNetwork netw, CyEdge edge) {
	return netw.containsEdge(edge);
    }

    public void estimate(CyNetwork netw, Estimator estim) {

	java.util.Iterator i;

	i = left.getNodeList().iterator();
	while (i.hasNext()) {
	    CyNode node = (CyNode)i.next();
	    if (right.containsNode(node) && !netw.containsNode(node))
		estim.addedNode(node);
	}

	i = left.getEdgeList().iterator();
	while (i.hasNext()) {
	    CyEdge edge = (CyEdge)i.next();
	    if (right.containsEdge(edge) && !netw.containsEdge(edge))
		estim.addedEdge(edge);
	}
    }
}
