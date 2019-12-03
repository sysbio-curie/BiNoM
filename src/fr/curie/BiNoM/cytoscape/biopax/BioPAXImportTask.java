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
import fr.curie.BiNoM.biopax.BioPAXImportBaseTask;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import java.io.File;
import java.net.URL;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.AbstractTask;
import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;

public class BioPAXImportTask extends AbstractTask {

    private CyNetwork cyNetwork;
    BioPAXImportBaseTask baseTask;
    

    public BioPAXImportTask(File file, URL url, String name, int algos[],
			    BioPAXToCytoscapeConverter.Option option,
			    boolean applyLayout) {
    	
    	baseTask = new BioPAXImportBaseTask(file, url, name, algos, option, applyLayout);	
    	
    }



    public String getTitle() {
    	return "BiNoM: Import BioPAX " + baseTask.bioPAXName;
    }


	public void run(org.cytoscape.work.TaskMonitor taskMonitor) throws Exception{
		taskMonitor.setTitle(getTitle());
	try {
	    for (int n = 0; n < baseTask.bioPAXAlgos.length; n++) {

			BioPAXToCytoscapeConverter.Graph graph = baseTask.makeGraph(n);
	
			if (graph != null) {
			    cyNetwork = NetworkFactory.createNetwork(baseTask.makeName(baseTask.bioPAXAlgos[n], baseTask.bioPAXName), graph.graphDocument,BioPAXVisualStyleDefinition.getInstance(),
			    		baseTask.applyLayout,taskMonitor);
	
			    if (cyNetwork != null) {
			    	BioPAXSourceDB.getInstance().setBioPAX(cyNetwork, graph.biopax);
			    }
			}
	    }
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setProgress(1);;
	    taskMonitor.setStatusMessage("Error importing BioPAX file " +
	    		baseTask.bioPAXName + ": " + e);
	    System.exit(0);
	}
    }


}

