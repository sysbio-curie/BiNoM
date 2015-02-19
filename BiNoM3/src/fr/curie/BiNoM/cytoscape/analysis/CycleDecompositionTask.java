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
import Main.Launcher;
import fr.curie.BiNoM.cytoscape.lib.*;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.vizmap.VisualStyle;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import edu.rpi.cs.xgmml.*;
import java.util.Vector;
import fr.curie.BiNoM.pathways.analysis.structure.*;

public class CycleDecompositionTask implements Task {

    private TaskMonitor taskMonitor;
    private GraphDocument graphDocument;
    private VisualStyle vizsty;

    public CycleDecompositionTask(GraphDocument graphDocument, VisualStyle vizsty) {
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
	return "BiNoM: Cycle Decomposition Components";
    }

    public CyNetwork getCyNetwork() {
    	return Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
    }

    public void run() {
	try {

	    Vector v = StructureAnalysisUtils.getCyclicComponents(graphDocument, new StructureAnalysisUtils.Option());

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

	    taskMonitor.setPercentCompleted(100);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setPercentCompleted(100);
	    taskMonitor.setStatus("Error getting cycle decomposition " + e);
	}
    }
}
