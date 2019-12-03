package fr.curie.BiNoM.cytoscape.utils;


import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;

import fr.curie.BiNoM.cytoscape.utils.TextBox;
import Main.Launcher;
/**
 * List edges in the current network under the SIF format
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class ListEdges extends AbstractCyAction{	
	private static final long serialVersionUID = 1L;
	final static String title="List Edges as SIF";
	public ListEdges(){
		super(title,Launcher.getAdapter().getCyApplicationManager(),"network",Launcher.getAdapter().getCyNetworkViewManager());		
        setPreferredMenu(Launcher.appName + ".BiNoM I/O");
	}
	public void actionPerformed(ActionEvent e) {		
		CyApplicationManager applicationManager=Launcher.getAdapter().getCyApplicationManager();
		CySwingApplication swingApplication=Launcher.getCySwingAppAdapter().getCySwingApplication();
		CyNetwork network=applicationManager.getCurrentNetwork();
		String text="Edge\tSource\tInteraction\tTarget\r\n";			
		for(CyEdge edge:network.getEdgeList()){
			text=text+network.getRow(edge).get(CyNetwork.NAME,String.class);
			text=text+"\t"+network.getRow(edge.getSource()).get(CyNetwork.NAME,String.class);
			text=text+"\t"+network.getRow(edge).get("interaction",String.class);
			text=text+"\t"+network.getRow(edge.getTarget()).get(CyNetwork.NAME,String.class)+"\r\n";
		}
		new TextBox(swingApplication.getJFrame(),title+" of "+network.getRow(network).get(CyNetwork.NAME,String.class),text).setVisible(true);
	}
}