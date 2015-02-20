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

import java.awt.event.ActionEvent;
import java.util.*;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyEdge;

import Main.Launcher;

public class ShowClipboardContents extends AbstractCyAction {
	
	public ShowClipboardContents(){
		super("Show Clipboard Contents",
        		Launcher.getAdapter().getCyApplicationManager(),
            "network",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".BiNoM Utilities.Clipboard");
	}

    public void actionPerformed(ActionEvent e) {

	NodesAndEdgesClipboard clipboard = NodesAndEdgesClipboard.getInstance();

	HashMap addEdges = new HashMap();
	HashMap addEdgeNodes = new HashMap();
	HashMap addNodes = new HashMap();

	for (Iterator i = clipboard.getEdges().iterator(); i.hasNext(); ) {
	    CyEdge edge = (CyEdge)i.next();
	    addEdges.put(edge, new Boolean(true));
	}

	for (Iterator i = clipboard.getEdgeNodes().iterator(); i.hasNext(); ) {
	    CyNode node = (CyNode)i.next();
	    addEdgeNodes.put(node, new Boolean(true));
	}

	for (Iterator i = clipboard.getNodes().iterator(); i.hasNext(); ) {
	    CyNode node = (CyNode)i.next();
	    addNodes.put(node, new Boolean(true));
	}

	ShowClipboardContentsDialog.getInstance().pop(addNodes, addEdges, addEdgeNodes);
    }
}

