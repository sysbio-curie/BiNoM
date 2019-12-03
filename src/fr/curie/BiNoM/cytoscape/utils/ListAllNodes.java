package fr.curie.BiNoM.cytoscape.utils;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.work.TaskIterator;

import Main.Launcher;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.cytoscape.analysis.ConnectedComponentsTask;
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
		
		ListAllNodesDialog dialog = new ListAllNodesDialog();
		
		BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());

		org.sbml.x2001.ns.celldesigner.SbmlDocument sbmlDoc = CellDesignerSourceDB.getInstance().getCellDesigner(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());

		dialog.pop(GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()),sbmlDoc,biopax);			
	}
}
