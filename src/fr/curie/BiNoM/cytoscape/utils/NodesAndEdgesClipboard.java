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
package fr.curie.BiNoM.cytoscape.utils;

import org.cytoscape.model.CyNetwork;
import java.util.*;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyEdge;

public class NodesAndEdgesClipboard {

    static NodesAndEdgesClipboard instance;

    private CyNetwork network;
    private HashMap nodes;
    private HashMap edges;
    private HashMap cEdges, cNodes, cEdgeNodes;

    public static NodesAndEdgesClipboard getInstance() {
	if (instance == null)
	    instance = new NodesAndEdgesClipboard();
	return instance;
    }

    private NodesAndEdgesClipboard() {
	reset();
    }

    public void reset() {
	nodes = new HashMap();
	edges = new HashMap();
    }

    public void add(CyNode node, CyNetwork netw) {
	nodes.put(node, netw);
    }

    public void add(CyEdge edge, CyNetwork netw) {
	edges.put(edge, netw);
    }

    public HashMap getNodes() {
	return cNodes;
    }

    public HashMap getEdges() {
	return cEdges;
    }

    public HashMap getEdgeNodes() {
	return cEdgeNodes;
    }

    public boolean isEmpty() {
	return nodes.size() == 0 && edges.size() == 0;
    }

    public void compile() {
	cEdges = new HashMap();
	cNodes = new HashMap();
	cEdgeNodes = new HashMap();

	for (Iterator i = edges.entrySet().iterator(); i.hasNext(); ) {
		Map.Entry pair = (Map.Entry)i.next();
	
	    CyEdge edge = (CyEdge) pair.getKey();
	    CyNetwork netw = (CyNetwork) pair.getValue();

	    cEdges.put(edge, netw);
	    cEdgeNodes.put(edge.getSource(), netw);
	    cEdgeNodes.put(edge.getTarget(), netw);
	}
	
	for (Iterator i = nodes.entrySet().iterator(); i.hasNext(); ) {
		Map.Entry pair = (Map.Entry)i.next();
	    CyNode node = (CyNode) pair.getKey();
	    CyNetwork netw = (CyNetwork) pair.getValue();
	    
	    if (!cEdgeNodes.containsKey(node))
	    	cNodes.put(node, netw);
	}
    }

    public void setNetwork(CyNetwork network) {
	this.network = network;
    }

    public CyNetwork getNetwork() {
	return network;
    }
}
