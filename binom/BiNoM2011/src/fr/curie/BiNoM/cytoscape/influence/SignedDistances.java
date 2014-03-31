package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2014 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cytoscape.Cytoscape;
import fr.curie.BiNoM.cytoscape.utils.TextBox;
import fr.curie.BiNoM.pathways.utils.*;
	/**
	 * Display matrix of signed distance between nodes using BFS route
	 * Distance is counted by number of edges and signed by signs of paths based on weights
	 * 
	 * @author Daniel.Rovera@curie.fr
	 */
public class SignedDistances extends ModelMenuUtils{
	private static final long serialVersionUID = 1L;
	final public static String title="Display Signed Distances";
	public void actionPerformed(ActionEvent e){
		WeightGraphStructure wgs=new WeightGraphStructure(Cytoscape.getCurrentNetwork());		
		if(!wgs.initWeights()){
			JOptionPane.showMessageDialog(Cytoscape.getDesktop(),errorWeigth,title,JOptionPane.ERROR_MESSAGE);
			return;
		}
		updatePathModel();
		updateFade();
		getSrcTgt(wgs,title);
		ArrayList<Integer>[][] sdMx;
		if(ifMultiPath){
			ComputingByDFS cpt=new ComputingByDFS(wgs,maxDepth());
			sdMx=cpt.signedDistances(srcDialog);
			new TextBox(Cytoscape.getDesktop(),addTitle(title),matrixToTxt(cpt,sdMx)).setVisible(true);
		}else{
			ComputingByBFS cpt=new ComputingByBFS(wgs);
			sdMx=cpt.signedDistances(srcDialog);
			new TextBox(Cytoscape.getDesktop(),addTitle(title),matrixToTxt(cpt,sdMx)).setVisible(true);
		}
	}
}
