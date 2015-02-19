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

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;

public class NetworkUnion extends NetworkBinaryOperation {

    public NetworkUnion(CyNetwork left, CyNetwork right) {
	super("Union", "UNION", left, right);
    }

    public void eval(CyNetwork netw) {

	java.util.Iterator i;

	i = left.getNodeList().iterator();
	while (i.hasNext()) {
	    CyNode node = (CyNode)i.next();
	    NetworkUtils.addNodeAndReportPosition(node, left, netw);
	}

	i = left.getEdgeList().iterator();
	while (i.hasNext()) {
	    CyEdge edge = (CyEdge)i.next();
	    NetworkUtils.addEdgeAndConnectedNodesAndReportPositions(edge, left, netw);
	}

	i = right.getNodeList().iterator();
	while (i.hasNext()) {
	    CyNode node = (CyNode)i.next();
	    if (!netw.containsNode(node))
		NetworkUtils.addNodeAndReportPosition(node, right, netw);
	}

	i = right.getEdgeList().iterator();
	while (i.hasNext()) {
	    CyEdge edge = (CyEdge)i.next();
	    //if (!netw.containsEdge(edge))
		NetworkUtils.addEdgeAndConnectedNodesAndReportPositions(edge, right, netw);
	}
    }

    public void estimate(CyNetwork netw, Estimator estim) {

	java.util.Iterator i;

	i = left.getNodeList().iterator();
	while (i.hasNext()) {
	    CyNode node = (CyNode)i.next();
	    if (!netw.containsNode(node))
		estim.addedNode(node);
	}

	i = left.getEdgeList().iterator();
	while (i.hasNext()) {
	    CyEdge edge = (CyEdge)i.next();
	    if (!netw.containsEdge(edge))
		estim.addedEdge(edge);
	}

	i = right.getNodeList().iterator();
	while (i.hasNext()) {
	    CyNode node = (CyNode)i.next();
	    if (!netw.containsNode(node))
		estim.addedNode(node);
	}

	i = right.getEdgeList().iterator();
	while (i.hasNext()) {
	    CyEdge edge = (CyEdge)i.next();
	    if (!netw.containsEdge(edge))
		estim.addedEdge(edge);
	}
    }
}
