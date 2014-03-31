package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2014 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import cytoscape.Cytoscape;
import fr.curie.BiNoM.pathways.utils.WeightGraphStructure;
/**
 * Preselect source and target by updating preselected node attribute
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class PreselectSrcTgt extends ModelMenuUtils{
	private static final long serialVersionUID = 1L;
	final public static String title="Preselect Sources and Targets";
	public void actionPerformed(ActionEvent e){
		WeightGraphStructure wgs=new WeightGraphStructure(Cytoscape.getCurrentNetwork());
		getSrcTgt(wgs,title);
		for(int n=0;n<wgs.nodes.size();n++) Cytoscape.getNodeAttributes().setAttribute(wgs.nodes.get(n).getIdentifier(),preselectAttrib,notSelected);
		for(int n:srcDialog) Cytoscape.getNodeAttributes().setAttribute(wgs.nodes.get(n).getIdentifier(),preselectAttrib,selectedAsSrc);
		for(int n:tgtDialog) Cytoscape.getNodeAttributes().setAttribute(wgs.nodes.get(n).getIdentifier(),preselectAttrib,selectedAsTgt);		
	}
}
