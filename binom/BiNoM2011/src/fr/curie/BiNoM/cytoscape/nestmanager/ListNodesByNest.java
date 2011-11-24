package fr.curie.BiNoM.cytoscape.nestmanager;
/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import fr.curie.BiNoM.cytoscape.utils.TextBox;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.util.CytoscapeAction;
/**
 *  List nodes in current network inside nest or not
 *  Result in a text box
 *  
 *  @author Daniel.Rovera@curie.fr
 */
public class ListNodesByNest extends CytoscapeAction{
	private static final long serialVersionUID = 1L;
	final public static String title="List nodes of modules and network";
	public void actionPerformed(ActionEvent e){		
		CyNetwork network=Cytoscape.getCurrentNetwork();
		String text="Network\tNode\r\n";
		ArrayList<CyNode> nestList=new ArrayList<CyNode>();
		for(CyNode node:NestUtils.getNodeList(network))
			if(node.getNestedNetwork()==null) text=text+network.getTitle()+"\t"+node+"\r\n"; else nestList.add(node);			
		for(CyNode nest:nestList) for(CyNode node:NestUtils.getNodeList(nest.getNestedNetwork())) text=text+nest+"\t"+node+"\r\n";
		new TextBox(Cytoscape.getDesktop(),title,text).setVisible(true);
	}	
}
