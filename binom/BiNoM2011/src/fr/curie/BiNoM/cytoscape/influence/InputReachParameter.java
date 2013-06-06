package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
/**
 * Action to input reach parameter, methods in FromToNodes
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class InputReachParameter extends FromToNodes {
	private static final long serialVersionUID = 1L;
	final public static String title="Input Reach Parameter";
	public void actionPerformed(ActionEvent e){
		inputReach();
	}
}
