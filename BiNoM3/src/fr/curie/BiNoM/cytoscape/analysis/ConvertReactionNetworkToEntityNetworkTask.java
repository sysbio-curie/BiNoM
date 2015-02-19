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
import Main.Launcher;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import org.cytoscape.view.vizmap.VisualStyle;
import edu.rpi.cs.xgmml.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.XGMML;


public class ConvertReactionNetworkToEntityNetworkTask implements Task {

    private TaskMonitor taskMonitor;
    private GraphDocument graphDocument;
    private VisualStyle vizsty;

    public ConvertReactionNetworkToEntityNetworkTask(GraphDocument graphDocument, VisualStyle vizsty) {
	this.graphDocument = graphDocument;
	this.vizsty = vizsty;
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
            throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    public String getTitle() {
	return "BiNoM: Converting Reaction Network";
    }

    public CyNetwork getCyNetwork() {
    	return Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
    }

    public void run() {
	try {

		Graph graph = XGMML.convertXGMMLToGraph(graphDocument);
	    Graph grres = BiographUtils.convertReactionNetworkIntoEntityNetwork(graph);
	    GraphDocument grDoc = XGMML.convertGraphToXGMML(grres);
		//GraphDocument grDoc = null;

		CyNetwork cyNetwork = NetworkFactory.createNetwork
		    (grDoc.getGraph().getName()+".entity",
		     grDoc,
		     vizsty,
		     false, // applyLayout
		     taskMonitor);

		if(taskMonitor==null)
			System.out.println("taskMonitor==null");
		else
	    taskMonitor.setPercentCompleted(100);
		
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setPercentCompleted(100);
	    taskMonitor.setStatus("Error Converting Reaction Network " + e);
	}
    }
}
