package fr.curie.BiNoM.cytoscape.nestmanager;
/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.TreeMap;
import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.util.CytoscapeAction;
import cytoscape.view.CyNetworkView;
/**
 * Create a new network composed of nests from networks selected in a list by NestUtils.selectNetworks
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class CreateNestNetwork  extends CytoscapeAction{
	private static final long serialVersionUID = 1L;
	final public static String title="Create Network of Modules";
	public void actionPerformed(ActionEvent e){
		TreeMap<String,CyNetwork> networks=NestUtils.getNetworksMap();
		ArrayList<String> selection=NestUtils.selectNetworks(networks,title,"Select networks to be nested");
		if(selection.size()!=0){
			CyNetworkView view=Cytoscape.createNetworkView(NestUtils.createNestNetwork(selection, networks));
			view.setVisualStyle(ModuleVisualStyle.NAME);
		}
	}
}

