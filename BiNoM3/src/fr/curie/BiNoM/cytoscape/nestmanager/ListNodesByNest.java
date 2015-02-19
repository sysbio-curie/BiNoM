package fr.curie.BiNoM.cytoscape.nestmanager;
/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import fr.curie.BiNoM.cytoscape.utils.TextBox;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

import Main.Launcher;

/**
 *  List nodes in current network inside nest or not
 *  Result in a text box
 *  
 *  @author Daniel.Rovera@curie.fr
 */
public class ListNodesByNest extends AbstractCyAction{
	
	public ListNodesByNest(){
		super(title,Launcher.getAdapter().getCyApplicationManager(),"network",Launcher.getAdapter().getCyNetworkViewManager());		
		setPreferredMenu("Plugin.BiNoM 3.BiNoM Module Manager");
	}
	
	private static final long serialVersionUID = 1L;
	final public static String title="List Nodes of Modules and Network";
	public void actionPerformed(ActionEvent e){		
		CyNetwork network=Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
		String text="Network\tNode\r\n";
		ArrayList<CyNode> nestList=new ArrayList<CyNode>();
		
		for(CyNode node:network.getNodeList())
			if(node.getNetworkPointer()==null) 
				text=text+network.getRow(network).get(CyNetwork.NAME, String.class)+"\t"+node+"\r\n"; 
			else 
				nestList.add(node);			
		
		for(CyNode nest:nestList) 
			for(CyNode node:(nest.getNetworkPointer().getNodeList())) 
				text=text+nest.getNetworkPointer().getRow(nest.getNetworkPointer()).get(CyNetwork.NAME, String.class)+"\t"+nest.getNetworkPointer().getRow(node).get(CyNetwork.NAME, String.class)+"\r\n";
			new TextBox(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(),title,text).setVisible(true);
	}	
}
