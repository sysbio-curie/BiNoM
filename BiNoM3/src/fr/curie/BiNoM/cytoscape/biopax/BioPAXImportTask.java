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

package fr.curie.BiNoM.cytoscape.biopax;

import fr.curie.BiNoM.cytoscape.lib.*;
import cytoscape.Cytoscape;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import cytoscape.visual.VisualMappingManager;
import cytoscape.visual.VisualStyle;
import cytoscape.task.ui.JTaskConfig;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import edu.rpi.cs.xgmml.*;
import cytoscape.CyNode;
import cytoscape.CyEdge;
import cytoscape.view.CyNetworkView;
import cytoscape.data.Semantics;
import cytoscape.visual.*;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.net.URL;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.app.CyAppAdapter;

import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.lib.AbstractTask;
import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;

public class BioPAXImportTask extends fr.curie.BiNoM.biopax.BioPAXImportBaseTask implements Task, AbstractTask {

    private TaskMonitor taskMonitor;
    private CyNetwork cyNetwork;
    

    public BioPAXImportTask(File file, URL url, String name, int algos[],
			    BioPAXToCytoscapeConverter.Option option,
			    boolean applyLayout) {
	super(file, url, name, algos, option, applyLayout);
    }

    public void halt() {
    }

	    public void setTaskMonitor(TaskMonitor taskMonitor)
	            throws IllegalThreadStateException {
	        this.taskMonitor = taskMonitor;
    }

    public String getTitle() {
    	return "BiNoM: Import BioPAX " + bioPAXName;
    }

    public CyNetwork getCyNetwork() {
    	return cyNetwork;
    }

    public void execute() {
    	fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(this);
    }

    public void run() {
	try {
	    for (int n = 0; n < bioPAXAlgos.length; n++) {
		BioPAXToCytoscapeConverter.Graph graph = makeGraph(n);
		if (graph != null) {
		    cyNetwork = NetworkFactory.createNetwork(makeName(bioPAXAlgos[n], bioPAXName), graph.graphDocument,BioPAXVisualStyleDefinition.getInstance(),
		    		applyLayout,taskMonitor);

		    if (cyNetwork != null) {
		    	BioPAXSourceDB.getInstance().setBioPAX(cyNetwork, graph.biopax);
		    }
		}
	    }
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setPercentCompleted(100);
	    taskMonitor.setStatus("Error importing BioPAX file " +
				  bioPAXName + ": " + e);
	}
    }
}

