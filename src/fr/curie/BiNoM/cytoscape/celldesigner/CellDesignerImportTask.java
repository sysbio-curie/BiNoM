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
package fr.curie.BiNoM.cytoscape.celldesigner;
import fr.curie.BiNoM.cytoscape.lib.*;

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
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

public class CellDesignerImportTask implements Task {

    private TaskMonitor taskMonitor;
    private File cellDesignerFile;
    private CyNetwork cyNetwork;

    public CellDesignerImportTask(File file) {
        this.cellDesignerFile = file;
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
            throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    public String getTitle() {
	return "BiNoM: Import CellDesigner File " + cellDesignerFile;
    }

    public CyNetwork getCyNetwork() {
	return cyNetwork;
    }

    public void run() {
	try {
	    if (!cellDesignerFile.canRead()) {
		taskMonitor.setStatus("Cannot read file: " + cellDesignerFile);
		taskMonitor.setPercentCompleted(100);
		return;
	    }

	    System.out.println("Ready to convert the file...");
	    CellDesignerToCytoscapeConverter.Graph graph = CellDesignerToCytoscapeConverter.convert(cellDesignerFile.getAbsolutePath());
            //XGMML.saveToXMGML(graph.graphDocument,"temp.xml");

	    cyNetwork = NetworkFactory.createNetwork
		(cellDesignerFile.getName(),
		 graph.graphDocument,
		 CellDesignerVisualStyleDefinition.getInstance(),
		 false,
		 taskMonitor);


	    if (cyNetwork != null)
		CellDesignerSourceDB.getInstance().setCellDesigner(cyNetwork, graph.sbml);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setPercentCompleted(100);
	    taskMonitor.setStatus("Error importing CellDesigner file " +
				  cellDesignerFile.getAbsolutePath() +
				  ": " + e);
	}
    }
}

