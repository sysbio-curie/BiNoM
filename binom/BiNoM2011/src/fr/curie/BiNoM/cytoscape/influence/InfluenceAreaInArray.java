package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import cytoscape.Cytoscape;
import fr.curie.BiNoM.cytoscape.utils.ComputingByBFS;
import fr.curie.BiNoM.cytoscape.utils.TextBox;
/**
 * Display Influence Area from start nodes
 * in text box
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class InfluenceAreaInArray  extends FromToNodes {
	private static final long serialVersionUID = 1L;
	final public static String title="Display Influence Reach Area in Array";
	public void actionPerformed(ActionEvent e){
		double fade=reachParameter();
		bfs=new ComputingByBFS(Cytoscape.getCurrentNetwork(),true);
		getSrcAllTgt(title);
		if(srcDialog.isEmpty()) return;
		String txt=influenceToString(bfs,bfs.reachArea(fade),null,srcDialog,tgtDialog);
		new TextBox(Cytoscape.getDesktop(),titleOfArray(),txt).setVisible(true);		
	}
}
