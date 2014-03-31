package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2014 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;

/**
 * Select Mono or Multi Path Model
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class ChooseModelType extends ModelMenuUtils{
	private static final long serialVersionUID = 1L;
	final public static String title="Select Mono or Multi Path Model";
	public void actionPerformed(ActionEvent e){
		inputPathModel();
	}
}
