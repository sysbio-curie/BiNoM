
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

import java.util.HashMap;
import java.util.Vector;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import Main.Launcher;
import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.cytoscape.lib.NetworkFactory;
import fr.curie.BiNoM.cytoscape.lib.NetworkFactorySubNetwork;
import fr.curie.BiNoM.pathways.analysis.structure.StructureAnalysisUtils;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;

public class ExcludeIntermediateNodesTask implements Task {
	
    private Graph graph = null;
    private Vector nodesToExclude;

    public ExcludeIntermediateNodesTask(Graph graph, Vector nodesToExclude) {
 	this.graph = graph;
	this.nodesToExclude = nodesToExclude;
    }
	
    public String getTitle() {
	return "Exclude intermediate nodes...";	
    }

    public void run(TaskMonitor taskMonitor) {
    	taskMonitor.setTitle(getTitle());
	try {
	    Graph grres = BiographUtils.ExcludeIntermediateNodes(graph, nodesToExclude,false);
	    GraphDocument grDoc = XGMML.convertGraphToXGMML(grres);

	    BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());

	    CyNetwork netw = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
	    HashMap nodeMap = Launcher.getNodeMap(netw);
		HashMap edgeMap = Launcher.getEdgeMap(netw);
		
	    CyNetwork cyNetwork = NetworkFactorySubNetwork.createNetwork
		(grDoc.getGraph().getName()+".inter_excluded",
		 grDoc,
		 Launcher.getAdapter().getVisualMappingManager().getCurrentVisualStyle(),
		 false, // applyLayout
		 taskMonitor, netw,nodeMap,edgeMap);
	
	    if (biopax != null) {
		BioPAXSourceDB.getInstance().setBioPAX(cyNetwork, biopax);
	    }

	    taskMonitor.setProgress(1);;
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setProgress(1);;
	    taskMonitor.setStatusMessage("Error substituting mono-molecular interactions... " + e);
	}		

    }

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}
}

