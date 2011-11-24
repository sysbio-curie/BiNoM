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
import fr.curie.BiNoM.cytoscape.biopax.*;
import fr.curie.BiNoM.cytoscape.biopax.query.*;
import fr.curie.BiNoM.cytoscape.celldesigner.*;
import fr.curie.BiNoM.pathways.utils.*;

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
import org.sbml.x2001.ns.celldesigner.*;
import edu.rpi.cs.xgmml.*;

import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerExportTask;

public class IndexPathAnalysisTask implements Task {

    private TaskMonitor taskMonitor;
    private VisualStyle vizsty;
    private Vector<String> sources;
    private Vector<String> targets;
    private StructureAnalysisUtils.Option options;
    
    private boolean outputCurrentNetwork = false;
    
    public Set<String> SelectedNodes;


    public IndexPathAnalysisTask(Vector<String> sources, Vector<String> targets, StructureAnalysisUtils.Option options, VisualStyle vizsty, boolean ocn) {
	this.vizsty = vizsty;
	this.sources = sources;
	this.targets = targets;
	this.options = options;
	this.outputCurrentNetwork = ocn;
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
            throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    public String getTitle() {
	return "BiNoM: Index path analysis";
    }

    public CyNetwork getCyNetwork() {
	return Cytoscape.getCurrentNetwork();
    }

    public void run() {
	try {
		
		BioPAXGraphQueryEngine beng = BioPAXIndexRepository.getInstance().getBioPAXGraphQueryEngine();
		if(beng!=null){
			if(beng.databaseCopyForPathAnalysis==null)
				beng.prepareDatabaseCopyForIndexPathAnalysis();
			SelectedNodes = StructureAnalysisUtils.findPaths(beng.databaseCopyForPathAnalysis,sources,targets,options);
			Graph gr = new Graph();
			Iterator sn = SelectedNodes.iterator();
			while(sn.hasNext()){
				Node n = beng.databaseCopyForPathAnalysis.getNode((String)(sn.next()));
				gr.addNode(n);
			}
			gr.addConnections(beng.databaseCopyForPathAnalysis);
			if(outputCurrentNetwork){
				CyNetwork current = Cytoscape.getCurrentNetwork();
				cytoscape.data.CyAttributes nodeAttrs = Cytoscape.getNodeAttributes();
				cytoscape.data.CyAttributes edgeAttrs = Cytoscape.getEdgeAttributes();
				GraphDocument currentGraphDocument = GraphDocumentFactory.getInstance().createGraphDocument(Cytoscape.getCurrentNetwork());
				Graph currentGraph = XGMML.convertXGMMLToGraph(currentGraphDocument);
				for(int i=0;i<gr.Nodes.size();i++){
					Node n = (Node)gr.Nodes.get(i);
					if(currentGraph.getNode(n.Id)==null){
				    CyNode nd = Cytoscape.getCyNode(NetworkFactory.toId(n.Id),true);
				    current.addNode(nd);
				    for(int j=0;j<n.Attributes.size();j++){
				    	Attribute attr = (Attribute)n.Attributes.get(j);
						nodeAttrs.setAttribute(nd.getIdentifier(), attr.name,attr.value);
				    }
				    nd.setIdentifier(nd.getIdentifier());
					}
				}
				for(int i=0;i<gr.Edges.size();i++){
					Edge e = (Edge)gr.Edges.get(i);
					if(currentGraph.getEdge(e.Id)==null){
				    CyEdge ed = Cytoscape.getCyEdge(
				    		Cytoscape.getCyNode(NetworkFactory.toId(e.Node1.Id)),
						    Cytoscape.getCyNode(NetworkFactory.toId(e.Node2.Id)),
						    Semantics.INTERACTION,
						    e.EdgeLabel,
						    true
				    );
				    ed.setIdentifier(e.Id);
				    //System.out.println(""+(i+1)+") Creating edge: "+e.EdgeLabel+"\t"+ed.getIdentifier());
				    current.addEdge(ed);
				    for(int j=0;j<e.Attributes.size();j++){
				    	Attribute attr = (Attribute)e.Attributes.get(j);
						edgeAttrs.setAttribute(ed.getIdentifier(), attr.name,attr.value);
				    }
					}
				}
				CyNetworkView networkView = Cytoscape.createNetworkView(current);
				networkView.redrawGraph(true, false);
			}else{
				GraphDocument grDoc = XGMML.convertGraphToXGMML(gr);
				CyNetwork cyNetwork = NetworkFactory.createNetwork
			    (grDoc.getGraph().getName(),
			     grDoc,
			     Cytoscape.getCurrentNetworkView().getVisualStyle(),
			     true, // applyLayout
			     taskMonitor);
			}
			
		}else
			taskMonitor.setStatus("ERROR: Index is not loaded.");
		
		taskMonitor.setPercentCompleted(100);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setPercentCompleted(100);
	    taskMonitor.setStatus("Error in index path analysis " + e);
	}
    }
}
