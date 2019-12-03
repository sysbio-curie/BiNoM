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

import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.cytoscape.lib.NetworkFactory;
import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class SelectUpstreamNeighbours implements ActionListener {

    public SelectUpstreamNeighbours() {
    }

    public void actionPerformed(ActionEvent e) {
    if(Cytoscape.getCurrentNetwork()!=null)
    	if(Cytoscape.getCurrentNetwork().getSelectedNodes().size()>0){

    		Graph gr = XGMML.convertXGMMLToGraph(GraphDocumentFactory.getInstance().createGraphDocument(Cytoscape.getCurrentNetwork()));
    		
    		Vector<String> selectedIds = new Vector<String>();
    		CyNetworkView view = Cytoscape.getCurrentNetworkView();
    		for (Iterator i = view.getNodeViewsIterator(); i.hasNext(); ) {
			    NodeView nView = (NodeView)i.next();
			    CyNode node = (CyNode)nView.getNode();
			    if(nView.isSelected()){
			    	selectedIds.add(node.getIdentifier());
			    }
			}
    		
    		gr.calcNodesInOut();
    		Vector<String> newSelectedIds = new Vector<String>();
    		for(int i=0;i<selectedIds.size();i++){
    			Node n = gr.getNode(selectedIds.get(i));
    			for(int j=0;j<n.incomingEdges.size();j++){
    				Edge ed = n.incomingEdges.get(j);
    				if(!newSelectedIds.contains(ed.Node1.Id)){
    					newSelectedIds.add(ed.Node1.Id);
    				}
    			}
    		}
    		
    		for (Iterator i = view.getNodeViewsIterator(); i.hasNext(); ) {
			    NodeView nView = (NodeView)i.next();
			    CyNode node = (CyNode)nView.getNode();
			    if(newSelectedIds.contains(node.getIdentifier()))
			    	nView.setSelected(newSelectedIds.contains(node.getIdentifier()));
			}

			view.redrawGraph(true, false);    		
    		
    	}
    		
    }
}

