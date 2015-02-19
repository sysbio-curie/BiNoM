package fr.curie.BiNoM.cytoscape.nestmanager;


import java.util.*;
import java.awt.event.ActionEvent;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.*;
import org.cytoscape.view.model.*;

import Main.Launcher;
/**
 * Create a new network composed of modules from networks selected in the network panel
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class CreateModuleNetwork extends AbstractCyAction{	
	
	public CreateModuleNetwork(){
		super(title,Launcher.getAdapter().getCyApplicationManager(),"network",Launcher.getAdapter().getCyNetworkViewManager());		
		setPreferredMenu("Plugin.BiNoM 3.BiNoM Module Manager");
	}
	
	private static final long serialVersionUID = 1L;
	final static String title="Create Network of Modules";
	
	public void actionPerformed(ActionEvent e) {
		CyNetworkManager networkManager=Launcher.getAdapter().getCyNetworkManager();
		CyNetworkFactory networkFactory=Launcher.getAdapter().getCyNetworkFactory();
		CyApplicationManager applicationManager=Launcher.getAdapter().getCyApplicationManager();
		CyNetworkViewFactory viewFactory=Launcher.getAdapter().getCyNetworkViewFactory();
		CyNetworkViewManager viewManager=Launcher.getAdapter().getCyNetworkViewManager();
		CySwingApplication swingApplication=Launcher.getCySwingAppAdapter().getCySwingApplication();
		ModuleUtils utils=new ModuleUtils();
		List<CyNetwork> selectedNets=utils.selectNetsFromPanel(applicationManager,swingApplication.getJFrame());
		if(selectedNets==null) 
			return;
		
		CyNetwork modularNet=networkFactory.createNetwork();		
		utils.checkModuleColumn(modularNet);
		modularNet.getRow(modularNet).set(CyNetwork.NAME,(new ModuleUtils()).noSynonymInNets(networkManager,"Modular"));
		networkManager.addNetwork(modularNet);
		for(CyNetwork netNode:selectedNets) 
			utils.addModule(modularNet,netNode);
		CyNetworkView view=viewFactory.createNetworkView(modularNet);
		viewManager.addNetworkView(view);
	}
}
