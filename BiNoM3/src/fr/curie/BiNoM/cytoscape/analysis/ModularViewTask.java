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

import Main.Launcher;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import edu.rpi.cs.xgmml.*;
import java.util.Vector;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.vizmap.VisualStyle;
import fr.curie.BiNoM.lib.AbstractTask;

public class ModularViewTask implements Task, AbstractTask {

    private TaskMonitor taskMonitor;
    private int idxAdd;
    private int idxs[];
    private VisualStyle vizsty;
    private boolean showIntersections = false;
    private boolean nodeIntersectionView = true;

    public ModularViewTask(int idxAdd, int idxs[], boolean showIntersections, boolean nodeIntersectionView) {

	this.idxs = idxs;
	this.idxAdd = idxAdd;
	this.vizsty = Launcher.getAdapter().getVisualMappingManager().getCurrentVisualStyle();
	this.nodeIntersectionView = nodeIntersectionView;
	this.showIntersections = showIntersections;
    }

    /*
    public ModularViewTask(GraphDocument graphDocument, Vector<GraphDocument> modules, boolean showIntersections, boolean nodeIntersectionView, VisualStyle vizsty) {
	this.graphDocument = graphDocument;
	this.modules = modules;
	this.vizsty = vizsty;
	this.nodeIntersectionView = nodeIntersectionView;
	this.showIntersections = showIntersections;
    }
    */

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
            throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    public String getTitle() {
	return "BiNoM: Creating modular view";
    }

    public CyNetwork getCyNetwork() {
    	return Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
    }

    public void execute() {
	fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(this);
    }

    public void run() {
	try {
	    CyNetwork network = NetworkUtils.getNetwork(idxAdd - 1);
	    
	    GraphDocument graphDocument = GraphDocumentFactory.getInstance().createGraphDocument(network);

	    Vector<GraphDocument> modules = new Vector();

	    for(int i = 0; i < idxs.length; i++) {
		GraphDocument gr = GraphDocumentFactory.getInstance().createGraphDocument(NetworkUtils.getNetwork(idxs[i]));
		modules.add(gr);
	    }
	    
	    GraphDocument grDoc = StructureAnalysisUtils.getModularView(graphDocument,modules,showIntersections,nodeIntersectionView);
	    
	    if (grDoc != null) {
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
	    taskMonitor.setStatus("Error in creating modular view " + e);
	}
    }
}
