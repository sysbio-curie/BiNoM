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

import java.util.*;
import Main.Launcher;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;


public class CopySelectedNodesAndEdges {
	private boolean resetCB;
	
	
	public CopySelectedNodesAndEdges(boolean resetCB){
        this.resetCB = resetCB;
        run();
	}

    public void run() {
	CyNetwork network = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();

	NodesAndEdgesClipboard clipboard = NodesAndEdgesClipboard.getInstance();

	if (resetCB) {
	    clipboard.reset();
	    clipboard.setNetwork(network);
	}

	for (Iterator i = CyTableUtil.getNodesInState(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true).iterator(); i.hasNext(); ) {
		CyNode nd = (CyNode) i.next();
	    clipboard.add(nd, network);
	}

	for (Iterator i = CyTableUtil.getEdgesInState(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true).iterator(); i.hasNext(); ) {
		CyEdge ed = (CyEdge) i.next();
	    clipboard.add(ed, network);
	}

	clipboard.compile();
    }
}

