package fr.curie.BiNoM.cytoscape.utils;

import java.awt.event.ActionEvent;
import org.cytoscape.application.swing.AbstractCyAction;
import Main.Launcher;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerSourceDB;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;

public class ListAllReactions extends AbstractCyAction {
	
	public ListAllReactions(){
		super("List all reactions...",
        		Launcher.getAdapter().getCyApplicationManager(),
            "pippo",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu("Plugin.BiNoM 3.BiNoM I/O");
	}

	public void actionPerformed(ActionEvent e) {
		
		ListAllReactionsDialog dialog = new ListAllReactionsDialog();
		BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
		org.sbml.x2001.ns.celldesigner.SbmlDocument sbmlDoc = CellDesignerSourceDB.getInstance().getCellDesigner(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
		dialog.pop(GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()),sbmlDoc,biopax);
	}
}
