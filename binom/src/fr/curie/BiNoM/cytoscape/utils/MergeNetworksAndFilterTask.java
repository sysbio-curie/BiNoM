package fr.curie.BiNoM.cytoscape.utils;

import fr.curie.BiNoM.cytoscape.lib.*;
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
import fr.curie.BiNoM.lib.AbstractTask;

public class MergeNetworksAndFilterTask implements Task, AbstractTask {

    private TaskMonitor taskMonitor;
    private int idxs[];
    private VisualStyle vizsty;
    private float intersectionThreshold = 0.3f;

    /*
    public ClusterNetworksTask(Vector<GraphDocument> networks, StructureAnalysisUtils.Option options, VisualStyle vizsty) {
	this.networks = networks;
	this.vizsty = vizsty;
	this.intersectionThreshold = options.intersectionThreshold;
    }
    */

    public MergeNetworksAndFilterTask(int idxs[], StructureAnalysisUtils.Option options, VisualStyle vizsty) {
	this.idxs = idxs;
	this.vizsty = vizsty;
	this.intersectionThreshold = options.intersectionThreshold;
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
	throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    public String getTitle() {
	return "BiNoM: Merging and filtering networks";
    }

    public CyNetwork getCyNetwork() {
	return Cytoscape.getCurrentNetwork();
    }

    public void execute() {
	fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(this);
    }

    public void run() {
	try {
	    Vector<Graph> networks = new Vector<Graph>();

	    for(int i = 0; i < idxs.length; i++) {
		Graph gr = XGMML.convertXGMMLToGraph(GraphDocumentFactory.getInstance().createGraphDocument(NetworkUtils.getNetwork(idxs[i])));
		networks.add(gr);
	    }

	    Graph mergedGraph = BiographUtils.MergeNetworkAndFilter(networks, intersectionThreshold);
		
		GraphDocument grDoc = XGMML.convertGraphToXGMML(mergedGraph);
		CyNetwork cyNetwork = NetworkFactory.createNetwork
		    (grDoc.getGraph().getName(),
		     grDoc,
		     vizsty,
		     false, // applyLayout
		     taskMonitor);

	    taskMonitor.setPercentCompleted(100);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setPercentCompleted(100);
	    taskMonitor.setStatus("Error in clustering networks " + e);
	}
    }
}

