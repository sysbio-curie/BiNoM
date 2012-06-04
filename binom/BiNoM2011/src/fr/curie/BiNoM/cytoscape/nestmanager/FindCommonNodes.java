package fr.curie.BiNoM.cytoscape.nestmanager;

/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE  
*/
import fr.curie.BiNoM.cytoscape.nestmanager.NestUtils;
import fr.curie.BiNoM.cytoscape.utils.TextBox;
import giny.model.GraphPerspective;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import cytoscape.CyEdge;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.Semantics;
import cytoscape.util.CytoscapeAction;
/**
 * Create an belonging matrix for every node in module
 * Complete it by node size and frequency in modules
 * Delete and create intersection edges containing number of common nodes
 * Put number of nodes in node attribute
 * Put number of common nodes in edge attributes
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class FindCommonNodes extends CytoscapeAction{
	private static final long serialVersionUID = 1L;
	final public static String title="Find common nodes in modules";
	final String interaction="INTERSECTION";
	final String edgeAttr="COMMON_NODES";
	final String nodeAttr="NODE_NUMBER";
	public void actionPerformed(ActionEvent e){
		CyNetwork currentNW = Cytoscape.getCurrentNetwork();
		ArrayList<CyNode> nests=new ArrayList<CyNode>();
		HashSet<CyNode> nodeSet=new HashSet<CyNode>();
		for(CyNode node:NestUtils.getNodeList(currentNW)){
			GraphPerspective nest=node.getNestedNetwork();
			if(nest!=null){
				nests.add(node);
				nodeSet.addAll(NestUtils.getNodeList(nest));
				Cytoscape.getNodeAttributes().setAttribute(node.getIdentifier(),nodeAttr,NestUtils.getNodeList(nest).size());
			}
		}
		ArrayList<CyNode> nodes=new ArrayList<CyNode>(nodeSet.size());
		nodes.addAll(nodeSet);
		int[][] bm=new int[nodes.size()][nests.size()];
		int[] sizes=new int[nests.size()];
		for(int c=0;c<nests.size();c++){
			sizes[c]=NestUtils.getNodeList(nests.get(c).getNestedNetwork()).size();
			for(int r=0;r<nodes.size();r++) bm[r][c]=0;
		}		
		for(int c=0;c<nests.size();c++){
			for(CyNode node:NestUtils.getNodeList(nests.get(c).getNestedNetwork())){
				int r=nodes.indexOf(node);
				if(r!=-1) bm[r][c]=1;
			} 
		}
		int[] frequency=new int[nodes.size()];
		for(int r=0;r<nodes.size();r++){
			frequency[r]=0;
			for(int c=0;c<nests.size();c++) if(bm[r][c]==1) frequency[r]++;
		}
		String text="";
		for(int c=0;c<nests.size();c++) text=text+"\t"+nests.get(c);
		text=text+"\tFrequency\r\n";
		for(int r=0;r<nodes.size();r++){
			text=text+nodes.get(r);
			for(int c=0;c<nests.size();c++){
				text=text+"\t"+bm[r][c];
			}
			text=text+"\t"+frequency[r]+"\r\n";
		}		
		text=text+"Size";
		for(int c=0;c<nests.size();c++) text=text+"\t"+sizes[c];
		new TextBox(Cytoscape.getDesktop(),title,text).setVisible(true);		
		for(int c=0;c<nests.size();c++) Cytoscape.getNodeAttributes().setAttribute(nests.get(c).getIdentifier(),nodeAttr,sizes[c]);
		for(CyEdge edge:NestUtils.getEdgeList(currentNW))
			if(Cytoscape.getEdgeAttributes().getAttribute(edge.getIdentifier(),Semantics.INTERACTION).equals(interaction)) 
				currentNW.removeEdge(currentNW.getIndex(edge),true);
		for(int c1=0;c1<nests.size();c1++){
			for(int c2=c1+1;c2<nests.size();c2++){
				int common12=0;
				for(int r=0;r<nodes.size();r++) if((bm[r][c1]==1)&&(bm[r][c2]==1)) common12++;				
				if(common12>0){
					CyEdge edge=Cytoscape.getCyEdge(nests.get(c1),nests.get(c2),Semantics.INTERACTION,interaction,true);
					currentNW.addEdge(edge);
					Cytoscape.getEdgeAttributes().setAttribute(edge.getIdentifier(),edgeAttr,common12);					
				}
			}
		}
		Cytoscape.firePropertyChange(Cytoscape.ATTRIBUTES_CHANGED, null, null);
		Cytoscape.getCurrentNetworkView().redrawGraph(true,true);
	}
}
