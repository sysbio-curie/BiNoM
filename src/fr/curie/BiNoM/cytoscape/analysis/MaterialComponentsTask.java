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

import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import Main.Launcher;
import edu.rpi.cs.xgmml.*;

import java.util.HashMap;
import java.util.Vector;

import fr.curie.BiNoM.pathways.analysis.structure.*;

public class MaterialComponentsTask extends AbstractTask {

    private GraphDocument graphDocument;
    private VisualStyle vizsty;

    public MaterialComponentsTask(GraphDocument graphDocument, VisualStyle vizsty) {
	this.graphDocument = graphDocument;
	this.vizsty = vizsty;
    }


    public String getTitle() {
	return "BiNoM: Material Components";
    }


	public void run(TaskMonitor taskMonitor) throws Exception {
		taskMonitor.setTitle(getTitle());
	try {

	    // begin of test
	    /*Vector v = new Vector();
	    v.add(GraphDocumentFactory.createGraphDocument(Cytoscape.getCurrentNetwork()));
	    v.add(GraphDocumentFactory.createGraphDocument(Cytoscape.getCurrentNetwork()));
	    v.add(GraphDocumentFactory.createGraphDocument(Cytoscape.getCurrentNetwork()));*/
	    // end of test

	    // should be something like:
	    Vector v = StructureAnalysisUtils.getMaterialComponents(graphDocument);

	    int size = v.size();
	    CyNetwork netw = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
	    HashMap nodeMap = Launcher.getNodeMap(netw);
		HashMap edgeMap = Launcher.getEdgeMap(netw);
		
	    for (int n = 0; n < size; n++) {
		GraphDocument grDoc = (GraphDocument)v.get(n);
		CyNetwork cyNetwork = NetworkFactorySubNetwork.createNetwork
		    (grDoc.getGraph().getName(),
		     grDoc,
		     vizsty,
		     false, // applyLayout
		     taskMonitor, netw, nodeMap,edgeMap);
	    }
	    nodeMap = null;
	    edgeMap = null;
	    taskMonitor.setProgress(1);;
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setProgress(1);
	    taskMonitor.setStatusMessage("Error getting Material components " + e);
	}
    }

}
