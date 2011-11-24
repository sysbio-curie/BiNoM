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

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.util.CyFileFilter;
import cytoscape.util.FileUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import java.util.*;
import cytoscape.view.CyNetworkView;
import cytoscape.CyNode;
import cytoscape.CyEdge;
import giny.view.NodeView;
import giny.view.EdgeView;

import javax.swing.JOptionPane;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerSourceDB;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;

public class NodesAndEdgesClipboard {

    static NodesAndEdgesClipboard instance;

    private CyNetwork network;
    private Vector nodes;
    private Vector edges;
    private Vector cEdges, cNodes, cEdgeNodes;

    public static NodesAndEdgesClipboard getInstance() {
	if (instance == null)
	    instance = new NodesAndEdgesClipboard();
	return instance;
    }

    private NodesAndEdgesClipboard() {
	reset();
    }

    public void reset() {
	nodes = new Vector();
	edges = new Vector();
    }

    public void add(CyNode node) {
	nodes.add(node);
    }

    public void add(CyEdge edge) {
	edges.add(edge);
    }

    public Vector getNodes() {
	return cNodes;
    }

    public Vector getEdges() {
	return cEdges;
    }

    public Vector getEdgeNodes() {
	return cEdgeNodes;
    }

    public boolean isEmpty() {
	return nodes.size() == 0 && edges.size() == 0;
    }

    public void compile() {
	cEdges = new Vector();
	cNodes = new Vector();
	cEdgeNodes = new Vector();

	for (Iterator i = edges.iterator(); i.hasNext(); ) {
	    CyEdge edge = (CyEdge)i.next();

	    cEdges.add(edge);
	    cEdgeNodes.add(edge.getSource());
	    cEdgeNodes.add(edge.getTarget());
	}
	
	for (Iterator i = nodes.iterator(); i.hasNext(); ) {
	    CyNode node = (CyNode)i.next();
	    if (!cEdgeNodes.contains(node))
		cNodes.add(node);
	}
    }

    public void setNetwork(CyNetwork network) {
	this.network = network;
    }

    public CyNetwork getNetwork() {
	return network;
    }
}
