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
import Main.Launcher;

import java.awt.event.ActionEvent;
import java.util.*;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

public class SelectEdgesBetweenSelectedNodes extends AbstractCyAction {
	
	public SelectEdgesBetweenSelectedNodes(){
		super("Select Edges between Selected Nodes",
        		Launcher.getAdapter().getCyApplicationManager(),
            "network",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".BiNoM Utilities[5]");
	}

    public void actionPerformed(ActionEvent e) {
	CyNetwork network = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
	CyNetworkView view = Launcher.getAdapter().getCyApplicationManager().getCurrentNetworkView();

	for (Iterator i = view.getEdgeViews().iterator(); i.hasNext();) {
	    View <CyEdge> eView = (View<CyEdge>) i.next();
	    CyEdge edge = (CyEdge)eView.getModel();

	    CyNode source = edge.getSource();
	    CyNode target = edge.getTarget();    
	    
	    List<CyNode> nodes = CyTableUtil.getNodesInState(network,"selected",true);
	    
	    if (nodes.contains(source) && nodes.contains(target)) {
			System.out.println("select edge " + network.getRow(edge).get(CyNetwork.NAME, String.class));
			//EdgeView eView = view.getEdgeView(edge);
			eView.setLockedValue(BasicVisualLexicon.EDGE_SELECTED, true);
			//eView.select();
	    }
	}
	view.updateView();
	//view.redrawGraph(true, false);
    }
}

