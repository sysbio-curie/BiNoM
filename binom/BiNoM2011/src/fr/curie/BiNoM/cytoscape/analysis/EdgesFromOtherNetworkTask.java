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
import fr.curie.BiNoM.pathways.wrappers.*;

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import cytoscape.visual.VisualMappingManager;
import cytoscape.visual.VisualStyle;
import java.io.*;
import cytoscape.task.ui.JTaskConfig;

import edu.rpi.cs.xgmml.*;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.CyEdge;
import cytoscape.view.CyNetworkView;
import cytoscape.data.Semantics;
import cytoscape.visual.*;
import java.io.InputStream;

import java.io.File;
import java.net.URL;

import org.biojava.bio.program.homologene.OrthoPairSet.Iterator;


public class EdgesFromOtherNetworkTask implements Task {
    private TaskMonitor taskMonitor;

    CyNetwork networks[];
    CyNetwork netwFrom;

    public EdgesFromOtherNetworkTask(CyNetwork networks[], CyNetwork netwFrom) {
	this.networks = networks;
	this.netwFrom = netwFrom;
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
            throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    public String getTitle() {
	return "BiNoM: Update Connections from other network";
    }

    public CyNetwork getCyNetwork() {
	return null;
    }

    public void run() {
	try {

	    /*taskMonitor.setStatus(netw_cnt +
				  " Network" + (netw_cnt != 1 ? "s" : "") +
				  " Updated");*/
		fr.curie.BiNoM.pathways.analysis.structure.Graph gnetworks[] = new fr.curie.BiNoM.pathways.analysis.structure.Graph[networks.length];
		fr.curie.BiNoM.pathways.analysis.structure.Graph gnetwFrom = XGMML.convertXGMMLToGraph(GraphDocumentFactory.getInstance().createGraphDocument(netwFrom));
		for(int i=0;i<networks.length;i++){
		    gnetworks[i] = XGMML.convertXGMMLToGraph(GraphDocumentFactory.getInstance().createGraphDocument(networks[i]));
			gnetworks[i].addConnections(gnetwFrom);
			java.util.Iterator it = netwFrom.edgesIterator();
			while(it.hasNext()){
				CyEdge edge = (CyEdge)it.next();
				if(gnetworks[i].getEdge(edge.getIdentifier())!=null){
					if(!networks[i].containsEdge(edge))
						networks[i].addEdge(edge);
				}
			}
			
			CyNetworkView networkView = Cytoscape.createNetworkView(networks[i]);
			networkView.redrawGraph(true, true);
			
			taskMonitor.setPercentCompleted((int)(100f*i/networks.length));			
		}
		
		
		
	    taskMonitor.setPercentCompleted(100);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setPercentCompleted(100);
	    taskMonitor.setStatus("Error updating networks: " + e);
	}
    }
}
