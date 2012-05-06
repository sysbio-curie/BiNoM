package fr.curie.BiNoM.cytoscape.nestmanager;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import cytoscape.CyEdge;
import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.util.CytoscapeAction;
/**
 * Pack nodes inside selected networks in nest pointing to these networks
 * Position of nest are the mean position of nodes
 * Edges are recreated between nests and nodes with the same attributes
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class PackInNestNode extends CytoscapeAction{
	private static final long serialVersionUID = 1L;
	final public static String title="Create Modules from Networks";
	private final String attrName="PREVIOUS_ID";
	public void actionPerformed(ActionEvent v){
		CyNetwork currentNW=Cytoscape.getCurrentNetwork();
		Cytoscape.getNetworkView(currentNW.getIdentifier()).redrawGraph(true,true);
		for(CyEdge edge:NestUtils.getEdgeList(currentNW)) Cytoscape.getEdgeAttributes().setAttribute(edge.getIdentifier(),attrName,edge.getIdentifier());
		TreeMap<String,CyNetwork> networks=NestUtils.getNetworksMap();
		ArrayList<String> selection=NestUtils.selectNetworks(networks,title,"Select pack networks");
		for(int i=0;i<selection.size();i++){
			String absentNode=NestUtils.createConnectNestPack(currentNW,networks.get(selection.get(i)));
			if(absentNode!=null){JOptionPane.showMessageDialog(Cytoscape.getDesktop(),absentNode+" of "+networks.get(selection.get(i)).getIdentifier()+
						"\r\nis not in current network","Cannot pack for this network",JOptionPane.WARNING_MESSAGE);
				return;
			}				
		}		
		Cytoscape.getCurrentNetworkView().redrawGraph(true,true);
	}
}
