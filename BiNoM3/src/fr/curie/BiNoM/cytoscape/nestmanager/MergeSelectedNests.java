package fr.curie.BiNoM.cytoscape.nestmanager;
import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.AbstractCyAction;

import Main.Launcher;
/**
 *  Merge selected nests using NestMerging
 *  
 *  @author Daniel.Rovera@curie.fr
 */ 
public class MergeSelectedNests extends AbstractCyAction{
	
	public MergeSelectedNests(){
		super(title,Launcher.getAdapter().getCyApplicationManager(),"network",Launcher.getAdapter().getCyNetworkViewManager());		
		setPreferredMenu("Plugin.BiNoM 3.BiNoM Module Manager");
	}
	
	private static final long serialVersionUID = 1L;
	final public static String title="Create Network from Union of Selected Modules";
	public void actionPerformed(ActionEvent v) {
		NestMerging merging=new NestMerging(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork(),Launcher.getAdapter().getCyApplicationManager().getCurrentNetworkView());
		if(merging.perform(ModuleUtils.getSelectedNodes(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()))) 
			Launcher.getAdapter().getCyApplicationManager().getCurrentNetworkView().updateView();
	}
}
