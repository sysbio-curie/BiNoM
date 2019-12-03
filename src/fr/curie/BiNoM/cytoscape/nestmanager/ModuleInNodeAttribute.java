package fr.curie.BiNoM.cytoscape.nestmanager;

/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

import Main.Launcher;

/**
 *  Create a node attribute containing nest name where it is in the current network
 *  
 *  @author Daniel.Rovera@curie.fr
 */
public class ModuleInNodeAttribute extends AbstractCyAction{
	
	public ModuleInNodeAttribute(){
		super(title,Launcher.getAdapter().getCyApplicationManager(),"network",Launcher.getAdapter().getCyNetworkViewManager());		
		setPreferredMenu(Launcher.appName + ".Module Manager");
	}
	
	
	private static final long serialVersionUID = 1L;
	final public static String title="Assign Module Names to Node Attribute";
	public void actionPerformed(ActionEvent e){
		CyNetwork cyNetwork = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
		String nodeAttr="IN_"+ cyNetwork.getRow(cyNetwork).get(CyNetwork.NAME, String.class);
		
		for(CyNode nest:cyNetwork.getNodeList()){
			CyNetwork nestNW=nest.getNetworkPointer();
			if(nestNW==null) 
				continue;				
			for(CyNode node:nestNW.getNodeList()){
				//create a new column if necessary
				if(nestNW.getDefaultNodeTable().getColumn(nodeAttr) == null)
					nestNW.getDefaultNodeTable().createColumn(nodeAttr, String.class, false);
				nestNW.getRow(node).set(nodeAttr, cyNetwork.getRow(nest).get(CyNetwork.NAME, String.class));
			}
		}
		//Cytoscape.firePropertyChange(Cytoscape.ATTRIBUTES_CHANGED, null, null);
		JOptionPane.showMessageDialog(null,"Attribute "+nodeAttr+" created",title,JOptionPane.INFORMATION_MESSAGE);
	}	
}