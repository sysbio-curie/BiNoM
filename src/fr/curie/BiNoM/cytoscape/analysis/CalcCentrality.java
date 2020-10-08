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

package fr.curie.BiNoM.cytoscape.analysis;

import cytoscape.CyNode;
import cytoscape.Cytoscape;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;

import javax.swing.JOptionPane;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import fr.curie.BiNoM.cytoscape.utils.ShowTextDialog;
import fr.curie.BiNoM.pathways.analysis.structure.StructureAnalysisUtils;

import fr.curie.BiNoM.analysis.ClusterNetworksDialog;

public class CalcCentrality implements ActionListener {

    private static final String EMPTY_NAME = "                       ";
    public boolean directed = false;

    public CalcCentrality() {
    }

    public void actionPerformed(ActionEvent e) {
        if(Cytoscape.getCurrentNetwork()!=null){
        	Vector<String> selected = new Vector<String>();
        	if(Cytoscape.getCurrentNetwork().getSelectedNodes().size()>1){
    			Set nodes = Cytoscape.getCurrentNetwork().getSelectedNodes();
    			Iterator it = nodes.iterator();
    			while(it.hasNext())
    			    selected.add(((CyNode)it.next()).getIdentifier());
        	}
			StructureAnalysisUtils.Option options = new StructureAnalysisUtils.Option();
			GraphDocument graphDocument = GraphDocumentFactory.getInstance().createGraphDocument(Cytoscape.getCurrentNetwork());
    		CalcCentralityTask task = new CalcCentralityTask(graphDocument, selected, directed, options, Cytoscape.getCurrentNetworkView().getVisualStyle());
    		task.run();
    		ShowTextDialog dialog = new ShowTextDialog();
    		dialog.pop("Node Inbetweenness", task.getText().toString());
    		
    		/*GraphDocument network = task.getNetwork();
    		cytoscape.data.CyAttributes nodeAttrs = Cytoscape.getNodeAttributes();
    		
    		Set nodes = Cytoscape.getCurrentNetwork().getSelectedNodes();
			Iterator it = nodes.iterator();
			while(it.hasNext()){
				CyNode nd = (CyNode)it.next();
				String id = nd.getIdentifier();
			}*/
    		
        	}
    }
}