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
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.KeyStroke;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.application.swing.AbstractCyAction;

import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class SelectUpstreamNeighbours extends AbstractCyAction {
	
	public SelectUpstreamNeighbours(){
		super("Select upstream neighbours",
        		Launcher.getAdapter().getCyApplicationManager(),
            "network",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".BiNoM Utilities");
        setAcceleratorKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_8, KeyEvent.CTRL_MASK));
      	}


    public void actionPerformed(ActionEvent e) {
    CyNetwork network = null;
	if(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()!=null)
    	network = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
    	if(CyTableUtil.getNodesInState(network,"selected",true).size()>0){

    		Graph gr = XGMML.convertXGMMLToGraph(GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()));
    		
    		Vector<String> selectedIds = new Vector<String>();
    		CyNetworkView view = Launcher.getAdapter().getCyApplicationManager().getCurrentNetworkView();
    		List<CyNode> nodes = CyTableUtil.getNodesInState(network,"selected",true);
    		for (Iterator i = view.getNodeViews().iterator(); i.hasNext(); ) {
			    View <CyNode> nView = (View<CyNode>) i.next();
			    CyNode node = nView.getModel();
			   	    
			    if(nodes.contains(node)){
			    	selectedIds.add(network.getRow(node).get(CyNetwork.NAME, String.class));
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
    		
    		for (Iterator i = view.getNodeViews().iterator(); i.hasNext(); ) {
    			View <CyNode> nView = (View<CyNode>) i.next();
			    CyNode node = nView.getModel();
			    if(newSelectedIds.contains(network.getRow(node).get(CyNetwork.NAME, String.class)))
			    	network.getRow(node).set("selected", newSelectedIds.contains(network.getRow(node).get(CyNetwork.NAME, String.class)));
			}
    		view.updateView();    		
    	}
    		
    }
}

