package fr.curie.BiNoM.cytoscape.nestmanager;




import java.util.*;
import java.awt.event.ActionEvent;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.*;
import org.cytoscape.view.model.*;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import Main.Launcher;
/**
 * Create a new network composed of modules from networks selected in the network panel
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class CreateModuleNetwork extends AbstractCyAction{	
	
	public CreateModuleNetwork(){
		super(title,Launcher.getAdapter().getCyApplicationManager(),"network",Launcher.getAdapter().getCyNetworkViewManager());		
		setPreferredMenu(Launcher.appName + ".Module Manager");
	}
	
	private static final long serialVersionUID = 1L;
	public final static String title="Create Network of Modules";
	
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
		Launcher.getAdapter().getVisualMappingManager().setVisualStyle(ModuleVisualStyle.getVisualStyle(), view);
		executeLayout(view);
		view.fitContent();
		view.updateView();
		viewManager.addNetworkView(view);

	}
	
	public static void executeLayout(CyNetworkView networkView) {

		    Iterator<CyNode> i = networkView.getModel().getNodeList().iterator();
		    
		    final int X_OFFSET = 100;
		    final int Y_OFFSET = 100;
		    final int MAX_COL_CNT = 20;

		    int row_num = 0;
		    int col_num = 0;

		    while (i.hasNext()) {
				CyNode node = (CyNode) i.next();
			    View<CyNode> nodeView = networkView.getNodeView(node);
			    double newY = row_num * Y_OFFSET;
			    double newX = col_num * X_OFFSET;
			    nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, newX);
	        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, newY);    	
				//nodeView. setXPosition(col_num * X_OFFSET);
				//nodeView.setYPosition(row_num * Y_OFFSET);
				
				col_num++;
				
				if (col_num == MAX_COL_CNT) {
				    col_num = 0;
				    row_num++;
				}
				
		    }
		}
}
