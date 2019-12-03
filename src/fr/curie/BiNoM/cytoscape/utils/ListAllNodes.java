package fr.curie.BiNoM.cytoscape.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cytoscape.*;

import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerSourceDB;
import fr.curie.BiNoM.cytoscape.lib.*;
import fr.curie.BiNoM.pathways.wrappers.*;

public class ListAllNodes implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		
		ListAllNodesDialog dialog = new ListAllNodesDialog();
		
		BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(Cytoscape.getCurrentNetwork());

		org.sbml.x2001.ns.celldesigner.SbmlDocument sbmlDoc = CellDesignerSourceDB.getInstance().getCellDesigner(Cytoscape.getCurrentNetwork());
		
		dialog.pop(GraphDocumentFactory.getInstance().createGraphDocument(Cytoscape.getCurrentNetwork()),sbmlDoc,biopax);
		
	}

}
