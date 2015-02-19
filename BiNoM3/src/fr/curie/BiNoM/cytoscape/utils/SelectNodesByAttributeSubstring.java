package fr.curie.BiNoM.cytoscape.utils;

import java.awt.event.ActionEvent;
import Main.Launcher;
import fr.curie.BiNoM.cytoscape.lib.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import org.cytoscape.application.swing.AbstractCyAction;

public class SelectNodesByAttributeSubstring extends AbstractCyAction {
	
	public SelectNodesByAttributeSubstring(){
		super("Select nodes by substring in attribute",
        		Launcher.getAdapter().getCyApplicationManager(),
            "pippo",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu("Plugin.BiNoM 3.BiNoM Utilities");
	}

	public void actionPerformed(ActionEvent e) {
		
		SelectNodesByAttributeSubstringDialog dialog = new SelectNodesByAttributeSubstringDialog();
		
		dialog.pop(XGMML.convertXGMMLToGraph(GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork())));
		
	}

}
