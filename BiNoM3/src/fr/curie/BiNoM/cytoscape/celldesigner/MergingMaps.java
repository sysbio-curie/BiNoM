package fr.curie.BiNoM.cytoscape.celldesigner;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.AbstractCyAction;

import Main.Launcher;


public class MergingMaps extends AbstractCyAction {
	
	public MergingMaps(){
		super("Merging CellDesigner map files...",
        		Launcher.getAdapter().getCyApplicationManager(),
            "pippo",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".BiNoM I/O");
	}

    public void actionPerformed(ActionEvent e) {
    	//ProduceNaviCellMapFilesDialog.getInstance().raise(null);
    	MergingMapsDialog dialog = new MergingMapsDialog();
    	dialog.setVisible(true);
    }
}
