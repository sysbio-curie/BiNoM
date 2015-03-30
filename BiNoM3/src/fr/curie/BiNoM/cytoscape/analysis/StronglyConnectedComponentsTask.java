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


import fr.curie.BiNoM.cytoscape.lib.*;

import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import edu.rpi.cs.xgmml.*;

import java.util.HashMap;
import java.util.Vector;

import fr.curie.BiNoM.pathways.analysis.structure.*;

import org.cytoscape.model.CyNetwork;

import Main.Launcher;


public class StronglyConnectedComponentsTask implements Task {

    private GraphDocument graphDocument;
    private VisualStyle vizsty;

    public StronglyConnectedComponentsTask(GraphDocument graphDocument, VisualStyle vizsty) {
	this.graphDocument = graphDocument;
	this.vizsty = vizsty;
    }


    public String getTitle() {
	return "BiNoM: Strongly Connection Components";
    }


	public void run(TaskMonitor taskMonitor) throws Exception {
		taskMonitor.setTitle(getTitle());
	try {
            Vector v = StructureAnalysisUtils.getStronglyConnectedComponents(graphDocument);
            
        CyNetwork netw = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
	    HashMap nodeMap = Launcher.getNodeMap(netw);
		HashMap edgeMap = Launcher.getEdgeMap(netw);

	    int size = v.size();
	    for (int n = 0; n < size; n++) {
		GraphDocument grDoc = (GraphDocument)v.get(n);
		CyNetwork cyNetwork = NetworkFactorySubNetwork.createNetwork
		    (grDoc.getGraph().getName(),
		     grDoc,
		     vizsty,
		     false, // applyLayout
		     taskMonitor, netw, nodeMap, edgeMap);
	    }
	    
	    nodeMap = null;
	    edgeMap = null;

	    taskMonitor.setProgress(1);
	    
	    
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setProgress(1);
	    taskMonitor.setStatusMessage("Error getting strongly connected components " + e);
	}
    }

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}


}
