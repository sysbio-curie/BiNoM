package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import cytoscape.Cytoscape;
import fr.curie.BiNoM.cytoscape.utils.*;
/**
 * Display Influence Array for Visualizing 2 options
 * 1: as text, not connected: nc, 3 digits after point
 * 2: for computing, non connected=0, all possible digits
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class InfluenceArrayAsText extends FromToNodes {
	private static final long serialVersionUID = 1L;
	final public static String title="Display Influence Array As Text";
	final public static String titleV="For Visualizing";
	final public static String titleC="For Computing";
	private String format;
	public InfluenceArrayAsText(String format){
		this.format=format;
	}
	public void actionPerformed(ActionEvent e){
		double fade=reachParameter();
		bfs=new ComputingByBFS(Cytoscape.getCurrentNetwork(),true);
		getSrcTgt(title,true);
		if(srcDialog.isEmpty()|tgtDialog.isEmpty()) return;
		if(!bfs.initWeigths()){
			JOptionPane.showMessageDialog(Cytoscape.getDesktop(),errorWeigth,title,JOptionPane.ERROR_MESSAGE);
			return;
		}	
		String txt=influenceToString(bfs,bfs.allInfluence(fade),format,srcDialog,tgtDialog);
		new TextBox(Cytoscape.getDesktop(),titleOfArray(),txt).setVisible(true);		
	}
}
