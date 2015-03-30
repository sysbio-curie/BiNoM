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

import java.util.HashMap;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import fr.curie.BiNoM.cytoscape.lib.*;
import Main.Launcher;
import edu.rpi.cs.xgmml.*;
import fr.curie.BiNoM.pathways.wrappers.XGMML;
import fr.curie.BiNoM.pathways.analysis.structure.*;

public class ConvertToUndirectedGraphTask implements Task {

    private TaskMonitor taskMonitor;
    private GraphDocument graphDocument;
    private VisualStyle vizsty;

    public ConvertToUndirectedGraphTask(GraphDocument graphDocument, VisualStyle vizsty) {
	this.graphDocument = graphDocument;
	this.vizsty = vizsty;
    }

    public String getTitle() {
	return "BiNoM: Remove multiple edges";
    }


    public void run(TaskMonitor taskMonitor) {
    	taskMonitor.setTitle(getTitle());
	try {

	    Graph graph = StructureAnalysisUtils.removeReciprocalEdges(XGMML.convertXGMMLToGraph(graphDocument));
	    
		CyNetwork netw = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();

	    HashMap nodeMap = Launcher.getNodeMap(netw);
		HashMap edgeMap = Launcher.getEdgeMap(netw);

		GraphDocument grDoc = XGMML.convertGraphToXGMML(graph);
		CyNetwork cyNetwork = NetworkFactorySubNetwork.createNetwork
		    (grDoc.getGraph().getName(),
		     grDoc,
		     vizsty,
		     false, // applyLayout
		     taskMonitor, netw, nodeMap, edgeMap);

	    taskMonitor.setProgress(1);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setProgress(1);;
	    taskMonitor.setStatusMessage("Error removing multiple edges " + e);
	}
    }

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}
}
