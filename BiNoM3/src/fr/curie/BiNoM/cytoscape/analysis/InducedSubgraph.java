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
  Author:
	Nadir Sella
	
*/

package fr.curie.BiNoM.cytoscape.analysis;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;

import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.pathways.wrappers.XGMML;
import Main.Launcher;

public class InducedSubgraph extends AbstractCyAction{
	
	public InducedSubgraph(){
		super("Extract Induced Subnetwork",
        		Launcher.getAdapter().getCyApplicationManager(),
            "network",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".Analysis");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()!=null){
			List<CyNode> selected = CyTableUtil.getNodesInState(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true);
        	if(selected.size()>0){
        		ExtractInducedNetworkDialog.getInstance().network = XGMML.convertXGMMLToGraph(GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()));
    			ExtractInducedNetworkDialog.getInstance().raise();
        	}
        	else
        		JOptionPane.showMessageDialog(new javax.swing.JFrame(),"Select al least one node\n");
    	}
		
	}

}
