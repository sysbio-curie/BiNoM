package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import cytoscape.Cytoscape;
import fr.curie.BiNoM.cytoscape.utils.*;
import fr.curie.BiNoM.pathways.utils.*;
/**
 * Display Influence Array in a list
 * for computing, non connected=0, all possible digits * 
 * @author Daniel.Rovera@curie.fr
 */
public class InfluenceAsList  extends ModelMenuUtils {
	private static final long serialVersionUID = 1L;
	final public static String title="Display Influence As List";
	public void actionPerformed(ActionEvent e){		
		WeightGraphStructure wgs=new WeightGraphStructure(Cytoscape.getCurrentNetwork());		
		if(!wgs.initWeights()){
			JOptionPane.showMessageDialog(Cytoscape.getDesktop(),errorWeigth,title,JOptionPane.ERROR_MESSAGE);
			return;
		}
		updatePathModel();
		updateFade();
		getSrcTgt(wgs,title);
		if(srcDialog.isEmpty()|tgtDialog.isEmpty()) return;
		Double[][] inflMx;
		if(ifMultiPath){
			ComputingByDFS cpt=new ComputingByDFS(wgs,maxDepth());
			inflMx=cpt.allInfluence(fade, srcDialog);
			new TextBox(Cytoscape.getDesktop(),addTitle(title),matrixToList(cpt,inflMx)).setVisible(true);
		}else{
			ComputingByBFS cpt=new ComputingByBFS(wgs);
			inflMx=cpt.allInfluence(fade, srcDialog);
			new TextBox(Cytoscape.getDesktop(),addTitle(title),matrixToList(cpt,inflMx)).setVisible(true);
		}
	}
}
