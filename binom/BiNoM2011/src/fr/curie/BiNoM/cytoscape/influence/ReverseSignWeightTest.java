package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2014 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import cytoscape.Cytoscape;
import fr.curie.BiNoM.cytoscape.lib.TaskManager;
import fr.curie.BiNoM.cytoscape.utils.TextBox;
/**
 * Display list of edges improving score by reversing weight, weight=-weight
 * sorted by decreasing kappa 
 * 
 * @author Daniel.Rovera@curie.fr
 *
 */
public class ReverseSignWeightTest extends SignEqualityScore  {
	private static final long serialVersionUID = 1L;
	final public static String title="Test Score by Reversing Sign Weight";
	public String title(){return title;}
	public void changeWeight(int edge){
		wgs.weights.set(edge,-wgs.weights.get(edge));
	}
	double weightValue(int edge){return -wgs.weights.get(edge);}
	public void actionPerformed(ActionEvent e){
		init();
		if(getSetData()){
			TaskManager.executeTask(new ChangeWeightEdgeTestTask(this));
		}else txt.append(aimError);
		new TextBox(Cytoscape.getDesktop(),title,txt.toString()).setVisible(true);
	}
}
