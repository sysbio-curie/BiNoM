package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE 
*/  
import java.awt.event.ActionEvent;
import cytoscape.Cytoscape;
import fr.curie.BiNoM.cytoscape.utils.TextBox;
import fr.curie.BiNoM.pathways.utils.ComputingByDFS;
/**
 * List opened back edges by sources during DFS
 * MultiPath model only
 * So avoid loops in adjacency matrix and allow using actThroughTree
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class OpenedBackEdges extends ModelMenuUtils {
	private static final long serialVersionUID = 1L;
	final public static String title="List Opened Edges MultiPath Only";
	public void actionPerformed(ActionEvent e){
		ComputingByDFS dfs=new ComputingByDFS(Cytoscape.getCurrentNetwork(),maxDepth());		
		getSrcTgt(dfs,title);
		StringBuffer txt=new StringBuffer("Back Edges by Sources Opened for No Loop, MultiPath only, max depth="+maxDepth()+"\r\n");
		for(int s:srcDialog){
			txt.append(dfs.nodes.get(s).getIdentifier());			
			for(int edge:dfs.backEdgesByDFS(s,maxDepth())){
				txt.append("\t");
				txt.append(dfs.edges.get(edge).getIdentifier());					
			}				
			txt.append("\r\n");
		}
		new TextBox(Cytoscape.getDesktop(),title+"/MultiPath",txt.toString()).setVisible(true);
	}
}


