package fr.curie.BiNoM.cytoscape.nestmanager;
/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import fr.curie.BiNoM.cytoscape.utils.TextBox;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import org.biopax.paxtools.controller.ModelUtils;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

import Main.Launcher;
/**
 * Create edges between nests from a reference network
 * Delete all old edges between nests
 * Search if every edge of reference network belongs to different network nest
 * Several edges having same interaction type become one edge (left and right are got as same)
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class CreateConnectionsBetweenNests  extends AbstractCyAction{
	
	public CreateConnectionsBetweenNests(){
		super(title,Launcher.getAdapter().getCyApplicationManager(),"pippo",Launcher.getAdapter().getCyNetworkViewManager());		
		setPreferredMenu("Plugin.BiNoM 3.BiNoM Module Manager");
	}
	
	private static final long serialVersionUID = 1L;
	final public static String title="Create Connections between Modules";
	private CyNetwork currentNW,referenceNW;
	private HashMap<CyNode,HashSet<CyNode>> nodeToNests;
	
	
	private void displayWarning(){
		String warning=new String("");
		for(CyNode node:nodeToNests.keySet()){			
			HashSet<CyNode> nodeList=nodeToNests.get(node);
			if(nodeList.size()>1){
				warning=warning+"Node in several networks\t"+currentNW.getRow(node).get(CyNetwork.NAME, String.class);
				for(CyNode nest:nodeToNests.get(node)){
					warning=warning+"\t"+currentNW.getRow(nest).get(CyNetwork.NAME, String.class);
				}
			warning=warning+"\r\n";
			}			
		}
		HashSet<CyNode> nodesNotInRef=new HashSet<CyNode>();
		nodesNotInRef.addAll(nodeToNests.keySet());
		nodesNotInRef.removeAll(referenceNW.getNodeList());
		for(CyNode node:nodesNotInRef) warning=warning+"Not in reference network\t"+ Launcher.findNetworkofNode(node.getSUID()).getRow(node).get(CyNetwork.NAME, String.class)+"\r\n";
		if(!warning.equals(""))	new TextBox(null, /*Cytoscape.getDesktop(),*/"Warning Update Connections Between Nests",warning).setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent e){
		currentNW=Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
		ModuleUtils moduleUtils = new ModuleUtils();
		
		referenceNW=moduleUtils.selectOneNetwork(Launcher.getAdapter().getCyNetworkManager(), Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(),title,"Select a network as reference");
		if(referenceNW == null)
			JOptionPane.showMessageDialog(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(), "You must select a network first");
		else {
			moduleUtils.deleteModulesEdges(currentNW);
			nodeToNests=moduleUtils.doNodeToModules(currentNW);
			displayWarning();
			moduleUtils.createModuleConnection(referenceNW,currentNW,nodeToNests);
			Launcher.getAdapter().getCyApplicationManager().getCurrentNetworkView().updateView();
		}
	}
}
