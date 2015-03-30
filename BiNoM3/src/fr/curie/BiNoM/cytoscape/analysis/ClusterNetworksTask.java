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

import java.util.Vector;

import fr.curie.BiNoM.pathways.analysis.structure.*;

public class ClusterNetworksTask extends AbstractTask {

    private TaskMonitor taskMonitor;
    private int idxs[];
    private VisualStyle vizsty;
    private float intersectionThreshold = 0.3f;

    /*
    public ClusterNetworksTask(Vector<GraphDocument> networks, StructureAnalysisUtils.Option options, VisualStyle vizsty) {
	this.networks = networks;
	this.vizsty = vizsty;
	this.intersectionThreshold = options.intersectionThreshold;
    }
    */

    public ClusterNetworksTask(int idxs[], StructureAnalysisUtils.Option options, VisualStyle vizsty) {
	this.idxs = idxs;
	this.vizsty = vizsty;
	this.intersectionThreshold = options.intersectionThreshold;
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
	throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    public String getTitle() {
	return "BiNoM: Clustering networks";
    }

    public CyNetwork getCyNetwork() {
    	return Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
    }

	public void run(TaskMonitor arg0) throws Exception {
	try {
	    Vector<GraphDocument> networks = new Vector<GraphDocument>();

	    for(int i = 0; i < idxs.length; i++) {
	    	System.out.println("net " + NetworkUtils.getNetwork(idxs[i]).getEdgeCount());
			GraphDocument gr = GraphDocumentFactory.getInstance().createGraphDocument(NetworkUtils.getNetwork(idxs[i]));
			networks.add(gr);
	    }

	    Vector<GraphDocument> v = StructureAnalysisUtils.getClusteredNetworks(networks, intersectionThreshold);
		
	    int size = v.size();
	    for (int n = 0; n < size; n++) {
		GraphDocument grDoc = (GraphDocument)v.get(n);
		CyNetwork cyNetwork = NetworkFactory.createNetwork
		    (grDoc.getGraph().getName(),
		     grDoc,
		     vizsty,
		     false, // applyLayout
		     taskMonitor);
	    }

	    taskMonitor.setProgress(1);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setProgress(1);
	    taskMonitor.setStatusMessage("Error in clustering networks " + e);
	}
    }
}
