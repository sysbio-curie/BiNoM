package fr.curie.BiNoM.cytoscape.celldesigner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MergingMaps implements ActionListener {

    public void actionPerformed(ActionEvent e) {
    	//ProduceNaviCellMapFilesDialog.getInstance().raise(null);
    	MergingMapsDialog dialog = new MergingMapsDialog();
    	dialog.setVisible(true);
    }
}
