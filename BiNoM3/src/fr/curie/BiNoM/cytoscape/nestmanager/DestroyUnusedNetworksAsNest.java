package fr.curie.BiNoM.cytoscape.nestmanager;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE  
*/
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

import org.cytoscape.model.CyNetwork;

import cytoscape.util.CytoscapeAction;
import fr.curie.BiNoM.cytoscape.lib.TaskManager;
import fr.curie.BiNoM.cytoscape.utils.TextBoxDialog;
import giny.model.GraphPerspective;
/**
 * Propose a list of unused networks by the active nest network
 * After selecting networks to be destroyed, ask confirmation
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class DestroyUnusedNetworksAsNest extends CytoscapeAction {
	private static final long serialVersionUID = 1L;
	final public static String title="Destroy Networks Unused as Module";
	public void actionPerformed(ActionEvent e) {		
		HashSet<String> usedNetworks=new HashSet<String>();
		for(CyNode node:NestUtils.getNodeList(Cytoscape.getCurrentNetwork())){
			GraphPerspective nestNW=node.getNestedNetwork();
			if(nestNW!=null) usedNetworks.add(((CyNetwork)nestNW).getTitle());
		}
		TreeMap<String,CyNetwork> unusedNetworks=NestUtils.getNetworksMap();
		unusedNetworks.remove(Cytoscape.getCurrentNetwork().getTitle());
		for(String network:usedNetworks) unusedNetworks.remove(network);
		ArrayList<String> selection=NestUtils.selectNetworks(unusedNetworks,title,"Select unused networks as nest to be destroyed");
		if(selection.size()!=0){
			String text="";
			for(String s:selection) text=text+s+"\r\n";
			TextBoxDialog ifDestroy=new TextBoxDialog(Cytoscape.getDesktop(),title,"Confirm destruction of these "+selection.size()+" networks",text);
			ifDestroy.setVisible(true);	
			if(ifDestroy.getYN()){
				TaskManager.executeTask(new DestroyUnusedNetworksAsNestTask(selection));
			}
		}
	}
}
