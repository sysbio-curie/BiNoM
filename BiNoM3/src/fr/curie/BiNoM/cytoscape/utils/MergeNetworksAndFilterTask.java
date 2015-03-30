package fr.curie.BiNoM.cytoscape.utils;

import fr.curie.BiNoM.cytoscape.lib.*;

import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.model.CyNetwork;

import Main.Launcher;
import edu.rpi.cs.xgmml.*;

import java.util.Vector;

import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.XGMML;
import fr.curie.BiNoM.lib.AbstractTask;

public class MergeNetworksAndFilterTask implements Task {

    private int idxs[];
    private VisualStyle vizsty;
    private float intersectionThreshold = 0.3f;

    public MergeNetworksAndFilterTask(int idxs[], StructureAnalysisUtils.Option options, VisualStyle vizsty) {
	this.idxs = idxs;
	this.vizsty = vizsty;
	this.intersectionThreshold = options.intersectionThreshold;
    }

    public String getTitle() {
	return "BiNoM: Merging and filtering networks";
    }

    public void run(TaskMonitor taskMonitor) {
    	taskMonitor.setTitle(getTitle());
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

	    taskMonitor.setProgress(1);;
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setProgress(1);
	    taskMonitor.setStatusMessage("Error in clustering networks " + e);
	}
    }

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}
}

