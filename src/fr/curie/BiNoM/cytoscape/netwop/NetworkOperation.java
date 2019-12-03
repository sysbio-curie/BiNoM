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

import java.util.HashMap;
import java.util.Collection;
import java.util.Vector;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;

public abstract class NetworkOperation {

    public static class Estimator {
	
	private Vector addNodes, addEdges;
	private Vector supNodes, supEdges;

	public Estimator() {
	    reset();
	}

	public void reset() {
	    addNodes = new Vector();
	    addEdges = new Vector();
	    supNodes = new Vector();
	    supEdges = new Vector();
	}

	public void addedNode(CyNode node) {
	    addNodes.add(node);
	}

	public void addedEdge(CyEdge edge) {
	    addEdges.add(edge);
	}

	public void suppressedNode(CyNode node) {
	    supNodes.add(node);
	}

	public void suppressedEdge(CyEdge edge) {
	    supEdges.add(edge);
	}

	public Vector getAddedNodes() {return addNodes;}

	public Vector getAddedEdges() {return addEdges;}

	public int getAddedNodeCount() {return addNodes.size();}

	public int getAddedEdgeCount() {return addEdges.size();}

	public Vector getSuppressedNodes() {return supNodes;}

	public Vector getSuppressedEdges() {return supEdges;}

	public int getSuppressedNodeCount() {return supNodes.size();}

	public int getSuppressedEdgeCount() {return supEdges.size();}
    }

    private String name;
    private String symb;

    protected NetworkOperation(String name, String symb) {
	this.name = name;
	this.symb = symb;
	//opmap.put(name, this);
    }

    public String getName() {
	return name;
    }

    public String getSymbol() {
	return symb;
    }

    abstract public CyNetwork eval(CyNetwork netw, CyNetworkView netView);
    abstract public void estimate(CyNetwork netw, Estimator estim);
}
