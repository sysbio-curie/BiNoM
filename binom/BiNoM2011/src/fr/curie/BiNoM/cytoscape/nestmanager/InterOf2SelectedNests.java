package fr.curie.BiNoM.cytoscape.nestmanager;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JOptionPane;
import cytoscape.CyEdge;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.util.CytoscapeAction;
import cytoscape.view.CyNetworkView;
import fr.curie.BiNoM.cytoscape.utils.TextBoxDialog;
import giny.model.GraphPerspective;
/**
 * Create networks from intersection of 2 nest networks (nodes and edges)
 * Delete edges and nodes of intersection in 2 networks
 * Dialog of confirmation displaying nodes to bet deleted
 * Transfer positions to created intersection network
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class InterOf2SelectedNests extends CytoscapeAction {
	private static final long serialVersionUID = 1L;
	final public static String title="Create network from intersection of 2 selected modules";
	private CyNetwork currentNW;
	private GraphPerspective network1;
	private GraphPerspective network2;
	private String name1;
	private String name2;
	private HashSet<CyNode> nodeInter;
	private HashSet<CyEdge> edgeInter;
	private String interName;
	private int searchIntersection(){
		nodeInter=NestUtils.getNodeSet(network1);
		nodeInter.retainAll(NestUtils.getNodeSet(network2));
		edgeInter=NestUtils.edgesLinkingNodes(network1,nodeInter);
		return nodeInter.size();
	}
	private void deleteNodes(){		
		for(CyNode node:nodeInter){			
			((CyNetwork)network1).removeNode(network1.getIndex(node),false);			
			((CyNetwork)network2).removeNode(network2.getIndex(node),false);
		}
	}
	private boolean createNetworksDialog(){
		name1=((CyNetwork)network1).getIdentifier();
		name2=((CyNetwork)network2).getIdentifier();
		String text=nodeInter.size()+" deleted nodes"+"\r\n";
		for(CyNode node:nodeInter) text=text+node.getIdentifier()+"\r\n";
		interName=name1+"|"+name2;		
		TextBoxDialog ifCreate=new TextBoxDialog(Cytoscape.getDesktop(),title,"Can Create "+interName+" by deleting nodes?",text);
		ifCreate.setVisible(true);	
		return ifCreate.getYN();
	}
	private void warningMessage(){
		JOptionPane.showMessageDialog(Cytoscape.getDesktop(),"2 nest nodes must be selected","Cannot create intersection",JOptionPane.WARNING_MESSAGE);
	}
	public void actionPerformed(ActionEvent v) {
		currentNW=Cytoscape.getCurrentNetwork();
		CyNetworkView cView=Cytoscape.createNetworkView(currentNW);
		double nx=0.0,ny=0.0;
		ArrayList<CyNode> selected=NestUtils.getSelectedNodes(currentNW);
		if(selected.size()!=2){warningMessage();return;} 
		CyNode nest1=selected.get(0);
		nx=cView.getNodeView(nest1).getXPosition();
		ny=cView.getNodeView(nest1).getYPosition();
		network1=nest1.getNestedNetwork();
		CyNode nest2=selected.get(1);
		nx=(nx+cView.getNodeView(nest2).getXPosition())/2;
		ny=(ny+cView.getNodeView(nest2).getYPosition())/2;
		network2=nest2.getNestedNetwork();	
		if((network1==null)|(network2==null)){warningMessage();return;}
		if(searchIntersection()==0) return;
		if(createNetworksDialog()){
			CyNetwork interNW=Cytoscape.createNetwork(nodeInter,edgeInter,interName);
			NestUtils.reportPosition(Cytoscape.getNetworkView(name1),Cytoscape.createNetworkView(interNW));
			CyNode interNest=Cytoscape.getCyNode(interName,true);
			currentNW.addNode(interNest);
			interNest.setNestedNetwork(interNW);
			cView.getNodeView(interNest).setXPosition(nx);
			cView.getNodeView(interNest).setYPosition(ny);
			deleteNodes();
			NestUtils.deleteNestEdges(currentNW);
			Cytoscape.getCurrentNetworkView().redrawGraph(true,true);
		}
	}
}
