package fr.curie.BiNoM.cytoscape.utils;

import fr.curie.BiNoM.cytoscape.lib.*;

import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.model.CyNetwork;
import Main.Launcher;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import edu.rpi.cs.xgmml.*;
import java.util.Vector;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.XGMML;
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
    	return Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
    }

    public void execute() {
	fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(this);
    }

    public void run() {
	try {
	    Vector<Graph> networks = new Vector<Graph>();
	    String name = "";
	    for(int i = 0; i < idxs.length; i++) {
			Graph gr = XGMML.convertXGMMLToGraph(GraphDocumentFactory.getInstance().createGraphDocument(NetworkUtils.getNetwork(idxs[i])));
			networks.add(gr);
			if(i == 0)
				name +=  NetworkUtils.getNetwork(idxs[i]).getRow(NetworkUtils.getNetwork(idxs[i])).get(CyNetwork.NAME, String.class);
		    else
		    	name += "|" + NetworkUtils.getNetwork(idxs[i]).getRow(NetworkUtils.getNetwork(idxs[i])).get(CyNetwork.NAME, String.class);;
			
	    }

	    Graph mergedGraph = BiographUtils.MergeNetworkAndFilter(networks, intersectionThreshold);
	    	    
	    mergedGraph.name = name;
		
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

