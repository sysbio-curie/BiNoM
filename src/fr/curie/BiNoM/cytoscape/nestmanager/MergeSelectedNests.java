package fr.curie.BiNoM.cytoscape.nestmanager;
import java.awt.event.ActionEvent;
import cytoscape.Cytoscape;
import cytoscape.util.CytoscapeAction;
/**
 *  Merge selected nests using NestMerging
 *  
 *  @author Daniel.Rovera@curie.fr
 */ 
public class MergeSelectedNests extends CytoscapeAction{
	private static final long serialVersionUID = 1L;
	final public static String title="Create Network from Union of Selected Modules";
	public void actionPerformed(ActionEvent v) {
		NestMerging merging=new NestMerging(Cytoscape.getCurrentNetwork(),Cytoscape.getCurrentNetworkView());
		if(merging.perform(NestUtils.getSelectedNodes(Cytoscape.getCurrentNetwork()))) Cytoscape.getCurrentNetworkView().redrawGraph(true,true);
	}
}
