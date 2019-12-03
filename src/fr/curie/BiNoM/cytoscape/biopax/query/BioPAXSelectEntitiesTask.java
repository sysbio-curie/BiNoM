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
package fr.curie.BiNoM.cytoscape.biopax.query;

import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import cytoscape.view.CyNetworkView;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.cytoscape.lib.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.XGMML;


public class BioPAXSelectEntitiesTask implements Task {

	private TaskMonitor taskMonitor;
	private BioPAXGraphQuery query = null;
	private int inputType = 0;
	public static int INPUT_CURRENT_NETWORK = 0;
	public static int INPUT_LISTOF_NAMES = 1;
	private int outputType = 1;
	public static int OUTPUT_CURRENT_NETWORK = 0;
	public static int OUTPUT_NEW_NETWORK = 1;

	private boolean outputToCurrentNetwork = false;

	public BioPAXSelectEntitiesTask(BioPAXGraphQuery q, int itype, int otype){
		query = q;
		inputType = itype;
		outputType = otype;
	}

	public BioPAXGraphQuery getBioPAXGraphQuery(){
		return query;
	}

	public void run() {
		try {

			BioPAXIndexRepository.getInstance().addToReport("\nEntities: "+(new Date()).toString()+"\n");

			BioPAXGraphQueryEngine beng = BioPAXIndexRepository.getInstance().getBioPAXGraphQueryEngine();
			AccessionNumberTable act = BioPAXIndexRepository.getInstance().getAccessionNumberTable();

			if(beng!=null){

				if(act!=null){
					taskMonitor.setStatus("Looking for synonyms...");
					act.addSynonyms(query);
				}

				taskMonitor.setStatus("Performing query...");
				beng.doQuery(query, query.SELECT_ENTITIES);

				// Dealing with networks
				if(Cytoscape.getNetworkSet().size()==0) outputType=OUTPUT_NEW_NETWORK;
				if(outputType==OUTPUT_NEW_NETWORK){
					GraphDocument grDoc = XGMML.convertGraphToXGMML(query.result);
					CyNetwork cyNetwork = NetworkFactory.createNetwork
					(grDoc.getGraph().getName(),
							grDoc,
							Cytoscape.getCurrentNetworkView().getVisualStyle(),
							true, // applyLayout
							taskMonitor);
				}
				if(outputType==OUTPUT_CURRENT_NETWORK){
					CyNetwork current = Cytoscape.getCurrentNetwork();
					cytoscape.data.CyAttributes nodeAttrs = Cytoscape.getNodeAttributes();
					GraphDocument currentGraphDocument = GraphDocumentFactory.getInstance().createGraphDocument(Cytoscape.getCurrentNetwork());
					Graph currentGraph = XGMML.convertXGMMLToGraph(currentGraphDocument);
					for(int i=0;i<query.result.Nodes.size();i++){
						Node n = (Node)query.result.Nodes.get(i);
						if(currentGraph.getNode(n.Id)==null){
							CyNode nd = Cytoscape.getCyNode(NetworkFactory.toId(n.Id),true);
							nd.setIdentifier(n.Id);
							current.addNode(nd);
							for(int j=0;j<n.Attributes.size();j++){
								Attribute attr = (Attribute)n.Attributes.get(j);
								nodeAttrs.setAttribute(nd.getIdentifier(), attr.name,attr.value);
							}
						}
					}
					CyNetworkView networkView = Cytoscape.createNetworkView(current);
					networkView.redrawGraph(true, false);
				}
			}else{
				taskMonitor.setStatus("Query was not performed. No index loaded.");
			}

			taskMonitor.setPercentCompleted(100);

		}catch(Exception e){
			e.printStackTrace();
			taskMonitor.setPercentCompleted(100);
			taskMonitor.setStatus("Error selecting entities :" + e);
		}
	}

	public String getTitle() {
		return "BiNoM: Select entities ";
	}

	public void halt() {
	}

	public void setTaskMonitor(TaskMonitor taskMonitor)
	throws IllegalThreadStateException {
		this.taskMonitor = taskMonitor;
	}


}
