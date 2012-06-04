package fr.curie.BiNoM.cytoscape.nestmanager;
/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import giny.model.GraphPerspective;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.util.CytoscapeAction;
/**
 *  Create a node attribute containing nest name where it is in the current network
 *  
 *  @author Daniel.Rovera@curie.fr
 */
public class NestInNodeAttribute extends CytoscapeAction{
	private static final long serialVersionUID = 1L;
	final public static String title="Assign module names to node attribute";
	public void actionPerformed(ActionEvent e){
		String nodeAttr="IN_"+Cytoscape.getCurrentNetwork().getTitle();	
		for(CyNode nest:NestUtils.getNodeList(Cytoscape.getCurrentNetwork())){
			GraphPerspective nestNW=nest.getNestedNetwork();
			if(nestNW==null) continue;				
			for(CyNode node:NestUtils.getNodeList(nestNW))
				Cytoscape.getNodeAttributes().setAttribute(node.getIdentifier(),nodeAttr,nest.getIdentifier());
		}
		Cytoscape.firePropertyChange(Cytoscape.ATTRIBUTES_CHANGED, null, null);
		JOptionPane.showMessageDialog(null,"Attribute "+nodeAttr+" created",title,JOptionPane.INFORMATION_MESSAGE);
	}	
}