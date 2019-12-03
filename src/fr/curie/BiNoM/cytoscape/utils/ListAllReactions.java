package fr.curie.BiNoM.cytoscape.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cytoscape.Cytoscape;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerSourceDB;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.cytoscape.utils.*;

public class ListAllReactions implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		
		ListAllReactionsDialog dialog = new ListAllReactionsDialog();
		
		BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(Cytoscape.getCurrentNetwork());

		org.sbml.x2001.ns.celldesigner.SbmlDocument sbmlDoc = CellDesignerSourceDB.getInstance().getCellDesigner(Cytoscape.getCurrentNetwork());
		
		dialog.pop(GraphDocumentFactory.getInstance().createGraphDocument(Cytoscape.getCurrentNetwork()),sbmlDoc,biopax);

	}

}
