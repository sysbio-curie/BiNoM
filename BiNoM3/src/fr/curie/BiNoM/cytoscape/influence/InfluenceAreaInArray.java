package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;

import Main.Launcher;
import fr.curie.BiNoM.cytoscape.utils.TextBox;
import fr.curie.BiNoM.pathways.utils.*;
/**
 * Display Influence Area from start nodes
 * in text box
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class InfluenceAreaInArray  extends ModelMenuUtils {
	private static final long serialVersionUID = 1L;
	final public static String title="Display Influence Reach Area in Array";
	public void actionPerformed(ActionEvent e){
		WeightGraphStructure wgs=new WeightGraphStructure(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());		
		updatePathModel();
		updateFade();
		getSrcAllTgt(wgs,title);
		if(srcDialog.isEmpty()) return;;
		Double[][] areaMx;
		if(ifMultiPath){
			ComputingByDFS cpt=new ComputingByDFS(wgs,maxDepth());
			areaMx=cpt.reachArea(fade, srcDialog);
			new TextBox(null, /*Cytoscape.getDesktop(),*/addTitle(title),matrixToFormatTxt(cpt,areaMx,null).toString()).setVisible(true);
		}else{
			ComputingByBFS cpt=new ComputingByBFS(wgs);
			areaMx=cpt.reachArea(fade, srcDialog);
			new TextBox(null, /*Cytoscape.getDesktop(),*/addTitle(title),matrixToFormatTxt(cpt,areaMx,null)).setVisible(true);
		}
	}
}
