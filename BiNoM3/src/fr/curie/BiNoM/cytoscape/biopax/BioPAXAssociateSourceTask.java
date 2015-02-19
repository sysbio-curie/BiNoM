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

import Main.Launcher;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import edu.rpi.cs.xgmml.*;
import cytoscape.data.Semantics;
import cytoscape.visual.*;

import java.io.InputStream;

import fr.curie.BiNoM.biopax.BioPAXSourceDB;

import java.io.File;
import java.net.URL;

import org.cytoscape.model.CyNetwork;

import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;

public class BioPAXAssociateSourceTask implements Task {
    private TaskMonitor taskMonitor;
    private CyNetwork cyNetwork;
    private File file;
    private String name;

    public BioPAXAssociateSourceTask(File file, String name) {
	this.file = file;
	int idx = name.lastIndexOf('.');
	if (idx >= 0)
	    name = name.substring(0, idx);

	idx = name.lastIndexOf('/');
	if (idx >= 0)
	    name = name.substring(idx+1, name.length());

	this.name = name;
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
            throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    public String getTitle() {
	return "BiNoM: Associate BioPAX Source " + file;
    }

    public CyNetwork getCyNetwork() {
	return cyNetwork;
    }

    public void run() {
	try {
	    /*int algo = BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION;
	    FileInputStream is = new FileInputStream(file);
	    BioPAXToCytoscapeConverter.Graph graph =
		BioPAXToCytoscapeConverter.convert
		(algo,
		 is,
		 BioPAXImportTask.makeName(algo, name),
		 new BioPAXToCytoscapeConverter.Option());

	    if (graph != null) {
		edu.rpi.cs.xgmml.GraphicGraph grf = graph.graphDocument.getGraph();
		edu.rpi.cs.xgmml.GraphicNode nodes[] = grf.getNodeArray();
		edu.rpi.cs.xgmml.GraphicEdge edges[] = grf.getEdgeArray();

		System.out.println("length " + nodes.length + " " +
				   edges.length);*/
		String fkey = BioPAXToCytoscapeConverter.getFileKey(file.getAbsolutePath());
		BioPAX biopax = (BioPAX)BioPAXToCytoscapeConverter.biopax_map.get(fkey);
		if(biopax==null){
			biopax = new BioPAX();
			biopax.loadBioPAX(file.getAbsolutePath());
		}
		BioPAXSourceDB.getInstance().setBioPAX
		    (Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork(), biopax);
	    //}
	    //is.close();
	    taskMonitor.setStatus("File associated");
	    taskMonitor.setPercentCompleted(100);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setPercentCompleted(100);
	    taskMonitor.setStatus("Error importing BioPAX file " +
				  file + ": " + e);
	}
    }
}

