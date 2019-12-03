
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

import java.util.Vector;

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import cytoscape.visual.VisualStyle;
import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.cytoscape.lib.NetworkFactory;
import fr.curie.BiNoM.pathways.analysis.structure.StructureAnalysisUtils;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;

import fr.curie.BiNoM.lib.AbstractTask;

public class ExcludeIntermediateNodesTask implements Task, AbstractTask {
	
    private TaskMonitor taskMonitor;
    private Graph graph = null;
    private Vector nodesToExclude;

    public ExcludeIntermediateNodesTask(Graph graph, Vector nodesToExclude) {
 	this.graph = graph;
	this.nodesToExclude = nodesToExclude;
    }
	
    public String getTitle() {
	return "Exclude intermediate nodes...";	
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
	throws IllegalThreadStateException {
	this.taskMonitor = taskMonitor;
    }

    public CyNetwork getCyNetwork() {
 	return Cytoscape.getCurrentNetwork();
    }

    public void execute() {
	fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(this);
    }

    public void run() {
	try {
	    Graph grres = BiographUtils.ExcludeIntermediateNodes(graph, nodesToExclude,false);
	    GraphDocument grDoc = XGMML.convertGraphToXGMML(grres);

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

	    taskMonitor.setPercentCompleted(100);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setPercentCompleted(100);
	    taskMonitor.setStatus("Error substituting mono-molecular interactions... " + e);
	}		

    }
}

