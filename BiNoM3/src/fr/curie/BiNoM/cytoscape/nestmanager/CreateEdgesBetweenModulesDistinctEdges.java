package fr.curie.BiNoM.cytoscape.nestmanager;



import java.awt.event.ActionEvent;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetworkManager;

import Main.Launcher;
/**
 * Create edges between modules compacting edges from the reference network
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class CreateEdgesBetweenModulesDistinctEdges   extends AbstractCyAction{
	private static final long serialVersionUID = 1L;
	public final static String title="Create Edges between Modules";
	public final static String subTitle="Distinct Edges Compacted";
	public CreateEdgesBetweenModulesDistinctEdges(){
		super(title+">"+subTitle,Launcher.getAdapter().getCyApplicationManager(),"network",Launcher.getAdapter().getCyNetworkViewManager());		
		setPreferredMenu(Launcher.appName + ".Module Manager");
		//boolean sep = insertSeparatorAfter();
	}
	public void actionPerformed(ActionEvent e) {
		CyApplicationManager applicationManager=Launcher.getAdapter().getCyApplicationManager();
		CyNetworkManager networkManager=Launcher.getAdapter().getCyNetworkManager();
		CySwingApplication swingApplication=Launcher.getCySwingAppAdapter().getCySwingApplication();
		CreateEdgesBetweenModules cebm=new CreateEdgesBetweenModules(applicationManager,networkManager,swingApplication);
		cebm.actionPerformed(title+"."+subTitle,false);
	}
}
