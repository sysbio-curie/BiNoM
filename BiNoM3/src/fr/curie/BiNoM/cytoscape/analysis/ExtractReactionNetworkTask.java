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

import fr.curie.BiNoM.cytoscape.lib.*;

import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.model.CyNetwork;

import Main.Launcher;
import edu.rpi.cs.xgmml.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.XGMML;


public class ExtractReactionNetworkTask implements Task {

    private GraphDocument graphDocument;
    private VisualStyle vizsty;

    public ExtractReactionNetworkTask(GraphDocument graphDocument, VisualStyle vizsty) {
	this.graphDocument = graphDocument;
	this.vizsty = vizsty;
    }


    public String getTitle() {
	return "BiNoM: Extract Reaction Network";
    }


    public void run(TaskMonitor taskMonitor) {
    	taskMonitor.setTitle(getTitle());
	try {

		Graph graph = XGMML.convertXGMMLToGraph(graphDocument);
	    Graph grres = BiographUtils.ExtractReactionNetwork(graph);
	    GraphDocument grDoc = XGMML.convertGraphToXGMML(grres);
		//GraphDocument grDoc = null;
	    
	    CyNetwork netw = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
	    String netname = netw.getRow(netw).get(CyNetwork.NAME, String.class) + ".RN";
	    HashMap nodeMap = Launcher.getNodeMap(netw);
		HashMap edgeMap = Launcher.getEdgeMap(netw);

		CyNetwork cyNetwork = NetworkFactorySubNetwork.createNetwork
		    (/*grDoc.getGraph().getName()+".RN"*/netname,
		     grDoc,
		     vizsty,
		     false, // applyLayout
		     taskMonitor, netw,nodeMap,edgeMap);

		if(taskMonitor==null)
			System.out.println("taskMonitor==null");
		else
	    taskMonitor.setProgress(1);
		
		nodeMap = null;
	    edgeMap = null;
		
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setProgress(1);;
	    taskMonitor.setStatusMessage("Error Extracting Reaction Network " + e);
	}
    }

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}
}
