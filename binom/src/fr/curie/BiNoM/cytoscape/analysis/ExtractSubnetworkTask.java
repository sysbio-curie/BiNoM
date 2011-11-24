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

import fr.curie.BiNoM.cytoscape.lib.*;
import fr.curie.BiNoM.cytoscape.utils.ShowTextDialog;
import fr.curie.BiNoM.cytoscape.biopax.*;
import fr.curie.BiNoM.cytoscape.celldesigner.*;

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
import java.io.InputStream;

import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;

import java.io.File;
import java.net.URL;
import giny.view.NodeView;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.pathways.analysis.structure.*;

import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerSourceDB;
import fr.curie.BiNoM.pathways.CytoscapeToBioPAXConverter;
import fr.curie.BiNoM.pathways.CytoscapeToCellDesignerConverter;
import fr.curie.BiNoM.pathways.CellDesignerToBioPAXConverter;
import fr.curie.BiNoM.pathways.utils.*;
import org.sbml.x2001.ns.celldesigner.*;
import edu.rpi.cs.xgmml.*;

import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerExportTask;

public class ExtractSubnetworkTask implements Task {

    private TaskMonitor taskMonitor;
    private GraphDocument network;
    private VisualStyle vizsty;
    private Vector<String> selected;
    private StructureAnalysisUtils.Option options;
    
    public Set<String> SelectedNodes;


    public ExtractSubnetworkTask(GraphDocument network, Vector<String> selected, StructureAnalysisUtils.Option options, VisualStyle vizsty) {
	this.network = network;
	this.vizsty = vizsty;
	this.selected = selected;
	this.options = options;
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
            throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    public String getTitle() {
	return "BiNoM: Extract subnetwork";
    }

    public CyNetwork getCyNetwork() {
	return Cytoscape.getCurrentNetwork();
    }

    public void run() {
	try {
		
		SubnetworkProperties snp = new SubnetworkProperties();
		snp.network = XGMML.convertXGMMLToGraph(network);
		for(int i=0;i<snp.network.Nodes.size();i++){
			Node n = snp.network.Nodes.get(i);
			n.setAttributeValueUnique("SUBNETWORK_NODE_TYPE", "",Attribute.ATTRIBUTE_TYPE_STRING);
		}
		
		snp.modeOfSubNetworkConstruction = options.methodOfSubnetworkExtraction;
		
		snp.subnetwork = new Graph();
		for(int i=0;i<selected.size();i++){
			snp.subnetwork.addNode(snp.network.getNode(selected.get(i)));
			Node n = snp.subnetwork.getNode(selected.get(i));
			n.setAttributeValueUnique("SUBNETWORK_NODE_TYPE", "SEED",Attribute.ATTRIBUTE_TYPE_STRING);
		}
		
		if(snp.modeOfSubNetworkConstruction == snp.SIMPLY_CONNECT){
			snp.subnetwork.addConnections(snp.network);
		}

		if(snp.modeOfSubNetworkConstruction == snp.SIMPLY_CONNECT_WITH_SECOND_ORDER_CONNECTIONS){
			snp.subnetwork.addConnections(snp.network);
			snp.addFirstNeighbours(snp.subnetwork,snp.network,true);
		}
		
		if(snp.modeOfSubNetworkConstruction == snp.SIMPLY_CONNECT_WITH_COMPLEX_NODES){
			System.out.println("In subnetwork: "+snp.subnetwork.Nodes.size()+" nodes, "+snp.subnetwork.Edges.size()+" edges");
			snp.subnetwork.addConnections(snp.network);
			snp.addComplexNodes();
		}
		
		if(snp.modeOfSubNetworkConstruction == snp.ADD_FIRST_NEIGHBOURS){
			snp.subnetwork.addConnections(snp.network);
			snp.addFirstNeighbours(snp.subnetwork,snp.network,true);
		}
		if(snp.modeOfSubNetworkConstruction == snp.CONNECT_BY_SHORTEST_PATHS){
			snp.connectByShortestPaths();
		}

		StringBuffer report = new StringBuffer();
		
		if(options.makeConnectivityTable){
			StringBuffer table = new StringBuffer();
			table.append("Table of degrees:\n");
			table.append("NODE\tSUBNETWORK\tGLOBAL\tRATIO\tINITIAL\n");
			for(int k=0;k<snp.subnetwork.Nodes.size();k++){
				Node n = snp.subnetwork.Nodes.get(k);
				snp.subnetwork.calcNodesInOut();
				int local = (n.incomingEdges.size()+n.outcomingEdges.size());
				snp.network.calcNodesInOut();
				int global = (n.incomingEdges.size()+n.outcomingEdges.size());
				if(global==0) global=1;
				String initial = "FALSE";
				if(selected.indexOf(n.Id)>=0)
					initial = "TRUE";
				table.append(n.Id+"\t"+local+"\t"+global+"\t"+((float)(local)/global)+"\t"+initial+"\n");
				n.setAttributeValueUnique("CONNECTIVITY_LOCAL", ""+local,Attribute.ATTRIBUTE_TYPE_REAL);
				n.setAttributeValueUnique("CONNECTIVITY_GLOBAL", ""+global,Attribute.ATTRIBUTE_TYPE_REAL);
				n.setAttributeValueUnique("CONNECTIVITY_RATIO", ""+((float)(local)/global),Attribute.ATTRIBUTE_TYPE_REAL);
			}
			report.append(table.toString());
		}
		
		if(options.checkComponentSignificance){
			snp.calcDegreeDistribution(snp.network, snp.degreeDistribution, snp.degrees, true);
			Vector<String> fixedNodes = options.fixedNodeList;
			String reportCompactness = snp.makeTestOfConnectivity(options.numberOfPermutations, true, null, 0, fixedNodes);
			report.append("\n\n"+reportCompactness);
		}
		
		System.out.println("REPORT:\n"+report.toString());
		
		if(!report.equals("")){
			ShowTextDialog dialog = new ShowTextDialog();
			dialog.pop("Extract subnetwork report", report.toString());
		}
		
		GraphDocument subnetwork = XGMML.convertGraphToXGMML(snp.subnetwork);		
		
		CyNetwork cyNetwork = NetworkFactory.createNetwork
	    (subnetwork.getGraph().getName(),
	     subnetwork,
	     vizsty,
	     false,//true, // applyLayout
	     taskMonitor);
	    taskMonitor.setPercentCompleted(100);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setPercentCompleted(100);
	    taskMonitor.setStatus("Error in extracting subnetwork " + e);
	}
    }
}
