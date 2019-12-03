package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2014 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
/**
 * Action to input Score Threshold
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class InputScoreThreshold extends ModelMenuUtils {
	private static final long serialVersionUID = 1L;
	final public static String title="Input Score Threshold";
	public void actionPerformed(ActionEvent e){
		inputThreshold();
	}
}