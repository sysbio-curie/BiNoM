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
import fr.curie.BiNoM.cytoscape.biopax.*;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import java.io.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.CSMLToCytoscapeConverter;

import org.csml.csml.version3.*;
import org.cytoscape.model.CyNetwork;

public class CSMLImportTask implements Task {

    private TaskMonitor taskMonitor;
    private File CSMLFile;
    private CyNetwork cyNetwork;

    public CSMLImportTask(File file) {
        this.CSMLFile = file;
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
            throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    public String getTitle() {
	return "BiNoM: Import CSML File " + CSMLFile;
    }

    public CyNetwork getCyNetwork() {
	return cyNetwork;
    }

    public void run() {
	try {
	    if (!CSMLFile.canRead()) {
		taskMonitor.setStatus("Cannot read file: " + CSMLFile);
		taskMonitor.setPercentCompleted(100);
		return;
	    }

	    System.out.println("Ready to convert the file...");
	    CSMLToCytoscapeConverter ccc = new CSMLToCytoscapeConverter();
	    ProjectDocument project = CSML.loadCSML(CSMLFile.getAbsolutePath());
	    Graph graph = ccc.getGraph(project);

	    cyNetwork = NetworkFactory.createNetwork
		(CSMLFile.getName(),
		 XGMML.convertGraphToXGMML(graph),
		 BioPAXVisualStyleDefinition.getInstance(),
		 false,
		 taskMonitor);


	    //if (cyNetwork != null)
		//CellDesignerSourceDB.getInstance().setCellDesigner(cyNetwork, graph.sbml);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setPercentCompleted(100);
	    taskMonitor.setStatus("Error importing CellDesigner file " +
				  CSMLFile.getAbsolutePath() +
				  ": " + e);
	}
    }
}

