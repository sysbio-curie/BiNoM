package fr.curie.BiNoM.cytoscape.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cytoscape.*;

import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerSourceDB;
import fr.curie.BiNoM.cytoscape.lib.*;
import fr.curie.BiNoM.pathways.wrappers.*;

public class SelectNodesByAttributeSubstring implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		
		SelectNodesByAttributeSubstringDialog dialog = new SelectNodesByAttributeSubstringDialog();
		
		dialog.pop(XGMML.convertXGMMLToGraph(GraphDocumentFactory.getInstance().createGraphDocument(Cytoscape.getCurrentNetwork())));
		
	}

}
