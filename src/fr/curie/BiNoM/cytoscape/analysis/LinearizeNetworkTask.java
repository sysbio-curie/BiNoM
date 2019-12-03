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

import java.util.*;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import Main.Launcher;
import edu.rpi.cs.xgmml.*;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.cytoscape.lib.NetworkFactory;
import fr.curie.BiNoM.cytoscape.lib.NetworkFactorySubNetwork;
import fr.curie.BiNoM.pathways.analysis.structure.StructureAnalysisUtils;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;

public class LinearizeNetworkTask implements Task {
	
	private GraphDocument graphDocument = null;
	private VisualStyle vizsty = null;

 public LinearizeNetworkTask(GraphDocument graphDocument, VisualStyle vizsty) {
 	this.graphDocument = graphDocument;
 	this.vizsty = vizsty;
     }
	
	
	public String getTitle() {
		return "\'Linearize\' network...";	
	}


	public void run(TaskMonitor taskMonitor) {
		taskMonitor.setTitle(getTitle());
		try {
			Graph graph = XGMML.convertXGMMLToGraph(graphDocument);
		    Graph grres = BiographUtils.LinearizeNetwork(graph);
		    GraphDocument grDoc = XGMML.convertGraphToXGMML(grres);
			//GraphDocument grDoc = null;
		    
		    /*System.out.println("Cheking the edges in the binom graph:");
		    for(int i=0;i<grres.Edges.size();i++){
		    	Edge e = (Edge)grres.Edges.get(i);
		    	System.out.println((i+1)+") "+e.Id+"\t"+e.EdgeLabel+"\t"+e.getFirstAttributeValue("interaction"));
		    }
		    System.out.println("Cheking the edges in the xgmml graph:");
		    for(int i=0;i<grDoc.getGraph().getEdgeArray().length;i++){
		    	GraphicEdge e = grDoc.getGraph().getEdgeArray(i);
		    	System.out.println((i+1)+") "+e.getId()+"\t"+e.getLabel()+"\t"+Utils.getFirstAttribute(e, "interaction").getValue());
		    }*/
		    

			BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
			
			CyNetwork netw = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
		    HashMap nodeMap = Launcher.getNodeMap(netw);
			HashMap edgeMap = Launcher.getEdgeMap(netw);
			

			CyNetwork cyNetwork = NetworkFactorySubNetwork.createNetwork
			    (grDoc.getGraph().getName()+".me",
			     grDoc,
			     Launcher.getAdapter().getVisualMappingManager().getCurrentVisualStyle(),
			     false, // applyLayout
			     taskMonitor,netw, nodeMap, edgeMap);
			
		    /*System.out.println("Cheking the edges in the Cytoscape graph:");
		    cytoscape.data.CyAttributes edgeAttrs = Cytoscape.getEdgeAttributes();
		    Iterator it = cyNetwork.edgesIterator();
		    int i=0;
		    while(it.hasNext()){
		    	cytoscape.CyEdge e = (cytoscape.CyEdge)it.next();
		    	System.out.println((i+1)+") "+e.getIdentifier()+"\t"+edgeAttrs.getStringAttribute(e.getIdentifier(), "interaction"));
		    	i++;
		    }*/
		    
			if(biopax!=null)
				BioPAXSourceDB.getInstance().setBioPAX(cyNetwork, biopax);

			/*if(taskMonitor==null)
				System.out.println("taskMonitor==null");
			else
			*/
			nodeMap = null;
		    edgeMap = null;
		    taskMonitor.setProgress(1);;
		}
		catch(Exception e) {
		    e.printStackTrace();
		    taskMonitor.setProgress(1);
		    taskMonitor.setStatusMessage("Error substituting mono-molecular interactions... " + e);
		}		

	}


	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}
}
