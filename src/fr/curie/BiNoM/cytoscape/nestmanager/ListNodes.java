package fr.curie.BiNoM.cytoscape.nestmanager;


/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

import fr.curie.BiNoM.cytoscape.utils.TextBox;
import Main.Launcher;

/**
 *  List nodes in current network inside nest or not
 *  Result in a text box
 *  
 *  @author Daniel.Rovera@curie.fr
 */
public class ListNodes extends AbstractCyAction{
	
	private static final long serialVersionUID = 1L;
	public final static String title="List Nodes & Within Nodes";
	public ListNodes(){
		super(title,Launcher.getAdapter().getCyApplicationManager(),"network",Launcher.getAdapter().getCyNetworkViewManager());		
		setPreferredMenu("BiNoM Info");
	}
	public void actionPerformed(ActionEvent e) {
		CyApplicationManager applicationManager=Launcher.getAdapter().getCyApplicationManager();
		CySwingApplication swingApplication=Launcher.getCySwingAppAdapter().getCySwingApplication();
		CyNetwork network=applicationManager.getCurrentNetwork();
		String text="Nodes\tWithin_Nodes\r\n";			
		for(CyNode node:network.getNodeList())
			if(node.getNetworkPointer()==null) text=text+network.getRow(node).get(CyNetwork.NAME,String.class)+"\r\n"; 
			else for(CyNode nodeIn:node.getNetworkPointer().getNodeList())  text=text+network.getRow(node).get(CyNetwork.NAME,String.class)+
					"\t"+node.getNetworkPointer().getRow(nodeIn).get(CyNetwork.NAME,String.class)+"\r\n";
		new TextBox(swingApplication.getJFrame(),title+" of "+network.getRow(network).get(CyNetwork.NAME,String.class),text).setVisible(true);
	}
}
