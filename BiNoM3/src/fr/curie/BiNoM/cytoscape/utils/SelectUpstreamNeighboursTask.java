package fr.curie.BiNoM.cytoscape.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import Main.Launcher;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class SelectUpstreamNeighboursTask implements Task {
	
	public void run(TaskMonitor arg0) throws Exception {
		arg0.setTitle("Selecting upstream neighbours");
	    CyNetwork network = null;
		if(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()!=null){
	    	network = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
	    	if(CyTableUtil.getNodesInState(network,"selected",true).size()>0){
	
	    		Graph gr = XGMML.convertXGMMLToGraph(GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()));
	    		
	    		Vector<String> selectedIds = new Vector<String>();
	    		CyNetworkView view = Launcher.getAdapter().getCyApplicationManager().getCurrentNetworkView();
	    		List<CyNode> nodes = CyTableUtil.getNodesInState(network,"selected",true);
	    		for (Iterator i = view.getNodeViews().iterator(); i.hasNext(); ) {
				    View <CyNode> nView = (View<CyNode>) i.next();
				    CyNode node = nView.getModel();
				   	    
				    if(nodes.contains(node)){
				    	selectedIds.add(network.getRow(node).get(CyNetwork.NAME, String.class));
				    }
				}
	    		
	    		gr.calcNodesInOut();
	    		Vector<String> newSelectedIds = new Vector<String>();
	    		for(int i=0;i<selectedIds.size();i++){
	    			Node n = gr.getNode(selectedIds.get(i));
	    			for(int j=0;j<n.incomingEdges.size();j++){
	    				Edge ed = n.incomingEdges.get(j);
	    				if(!newSelectedIds.contains(ed.Node1.Id)){
	    					newSelectedIds.add(ed.Node1.Id);
	    				}
	    			}
	    		}
	    		
	    		for (Iterator i = view.getNodeViews().iterator(); i.hasNext(); ) {
	    			View <CyNode> nView = (View<CyNode>) i.next();
				    CyNode node = nView.getModel();
				    if(newSelectedIds.contains(network.getRow(node).get(CyNetwork.NAME, String.class)))
				    	network.getRow(node).set("selected", newSelectedIds.contains(network.getRow(node).get(CyNetwork.NAME, String.class)));
				}
	    		view.updateView();    		
	    	}
		}
    	arg0.setProgress(1);
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}


}
