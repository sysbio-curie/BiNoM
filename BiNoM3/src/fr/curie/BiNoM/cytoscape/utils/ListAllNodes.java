package fr.curie.BiNoM.cytoscape.utils;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.AbstractCyAction;

import Main.Launcher;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerSourceDB;
import fr.curie.BiNoM.cytoscape.lib.*;
import fr.curie.BiNoM.pathways.wrappers.*;

public class ListAllNodes extends AbstractCyAction {
	
	public ListAllNodes(){
		super("List all nodes...",
        		Launcher.getAdapter().getCyApplicationManager(),
            "pippo",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".BiNoM I/O");
	}

	public void actionPerformed(ActionEvent e) {
		
		ListAllNodesTask task= new ListAllNodesTask();
		fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);

	}

}
