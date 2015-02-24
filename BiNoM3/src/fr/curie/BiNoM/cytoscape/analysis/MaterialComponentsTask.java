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
import fr.curie.BiNoM.cytoscape.biopax.*;
import fr.curie.BiNoM.cytoscape.celldesigner.*;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.vizmap.VisualStyle;

import cytoscape.Cytoscape;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import cytoscape.visual.VisualMappingManager;
import java.io.*;
import cytoscape.task.ui.JTaskConfig;

import edu.rpi.cs.xgmml.*;
import cytoscape.CyNode;
import cytoscape.CyEdge;
import cytoscape.view.CyNetworkView;
import cytoscape.data.Semantics;
import cytoscape.visual.*;
import java.io.InputStream;

import java.util.Vector;
import java.util.Iterator;

import java.io.File;
import java.net.URL;
import giny.view.NodeView;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;

import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerSourceDB;
import fr.curie.BiNoM.pathways.CytoscapeToBioPAXConverter;
import fr.curie.BiNoM.pathways.CytoscapeToCellDesignerConverter;
import fr.curie.BiNoM.pathways.CellDesignerToBioPAXConverter;
import org.sbml.x2001.ns.celldesigner.*;

import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerExportTask;
import fr.curie.BiNoM.pathways.analysis.structure.*;

public class MaterialComponentsTask implements Task {

    private TaskMonitor taskMonitor;
    private GraphDocument graphDocument;
    private VisualStyle vizsty;

    public MaterialComponentsTask(GraphDocument graphDocument, VisualStyle vizsty) {
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
	return "BiNoM: Material Components";
    }

    public CyNetwork getCyNetwork() {
    	return Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
    }

    public void run() {
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
	    taskMonitor.setStatus("Error getting Material components " + e);
	}
    }
}