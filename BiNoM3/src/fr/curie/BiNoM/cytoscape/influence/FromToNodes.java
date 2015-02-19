package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import java.util.HashSet;

import Main.Launcher;
import fr.curie.BiNoM.pathways.utils.ComputingByBFS;
/**
 * In menu, this class selects nodes and edges between 2 lists of nodes
 * by intersection of 2 sets made get by descending and ascending the graph
 * This class is also used as up class for other menu classes providing 
 * input of reach parameter,formatted output of arrays and two lists node dialog
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class FromToNodes extends ModelMenuUtils {
	private static final long serialVersionUID = 1L;
	final public static String title="Select Sub-network from Sources to Targets";
	public void actionPerformed(ActionEvent e){
		ComputingByBFS cpt=new ComputingByBFS(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
		getSrcTgt(cpt,title);
		HashSet<Integer> nodes=cpt.extractNodes(srcDialog,tgtDialog);		
		for(int node:nodes)	Cytoscape.getCurrentNetwork().setSelectedNodeState(cpt.nodes.get(node),true);		
		Cytoscape.getCurrentNetworkView().redrawGraph(true,true);
	}
}
