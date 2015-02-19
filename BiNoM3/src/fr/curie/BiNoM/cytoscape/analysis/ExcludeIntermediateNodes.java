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

import java.awt.event.ActionEvent;
import java.util.*;


import org.cytoscape.application.swing.AbstractCyAction;
import Main.Launcher;
import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.pathways.analysis.structure.BiographUtils;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.wrappers.XGMML;
import fr.curie.BiNoM.analysis.ExcludeIntermediateNodesDialog;

public class ExcludeIntermediateNodes extends AbstractCyAction {
	
	public ExcludeIntermediateNodes(){
		super("Exclude intermediate Nodes",
        		Launcher.getAdapter().getCyApplicationManager(),
            "pippo",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu("Plugin.BiNoM 3.Analysis");
	}
	
    public void actionPerformed(ActionEvent e) {
		
		GraphDocument graphDocument = GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());

		Graph graph = XGMML.convertXGMMLToGraph(graphDocument);
		Vector nodesToExclude = new Vector();
		BiographUtils.ExcludeIntermediateNodes(graph, nodesToExclude, true);
	    
		ExcludeIntermediateNodesDialog.getInstance().raise(new ExcludeIntermediateNodesTaskFactory(), graph, nodesToExclude);

	/*
	if (dialog.result > 0) {
	    Graph grres = BiographUtils.ExcludeIntermediateNodes(graph, dialog.nodesToExclude,false);
	    GraphDocument grDoc = XGMML.convertGraphToXGMML(grres);

	    try {
		BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(Cytoscape.getCurrentNetwork());
    	
		CyNetwork cyNetwork = NetworkFactory.createNetwork
		    (grDoc.getGraph().getName()+".inter_excluded",
		     grDoc,
		     Cytoscape.getCurrentNetworkView().getVisualStyle(),
		     false, // applyLayout
		     null);
	
		if (biopax != null) {
		    BioPAXSourceDB.getInstance().setBioPAX(cyNetwork, biopax);
		}

	    } catch(Exception ee) {
		ee.printStackTrace();
	    }
	}
	*/
    }
}
