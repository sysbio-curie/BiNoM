package fr.curie.BiNoM.cytoscape.nestmanager;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE  
*/
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.TreeMap;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.util.CytoscapeAction;
/**
 * Pack nodes inside selected networks in nest pointing to these networks
 * Position of nest are the mean position of nodes
 * Every Edges are recreated between nests and nodes with the same attributes without compacting them
 * When a node is here several times, edges connecting it are duplicated
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class PackInNestNode extends CytoscapeAction{
	private static final long serialVersionUID = 1L;
	final public static String title="Pack Network In Modules";
	public void actionPerformed(ActionEvent v){
		TreeMap<String,CyNetwork> networks=NestUtils.getNetworksMap();
		ArrayList<String> selection=NestUtils.selectNetworks(networks,title+": "+Cytoscape.getCurrentNetwork().getIdentifier(),"Select pack networks");
		if(selection.isEmpty()){
			NestUtils.cloneCurrent(" copy");
			return;
		}
		CyNetwork cloneNW=NestUtils.cloneCurrent(" packed");
		String cloneName=Cytoscape.getCurrentNetwork().getTitle();
		int ni=0;while(networks.keySet().contains(cloneName+ni)) ni++;
		cloneNW.setTitle(cloneName+ni);
		ArrayList<CyNetwork> nestNetworks=new ArrayList<CyNetwork>();
		for(int i=0;i<selection.size();i++) nestNetworks.add(networks.get(selection.get(i)));
		if(!NestUtils.createAndPlaceNests(cloneNW,nestNetworks)){
			Cytoscape.destroyNetwork(cloneNW);
			return;
		}
		NestUtils.explicitConnectNestAndNode(cloneNW);
		for(CyNetwork nestNetwork:nestNetworks)
			for(CyNode node:NestUtils.getNodeList(nestNetwork)) cloneNW.removeNode(cloneNW.getIndex(node),false);
	}
}
