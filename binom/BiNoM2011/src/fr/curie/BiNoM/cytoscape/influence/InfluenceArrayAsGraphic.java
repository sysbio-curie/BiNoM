package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import cytoscape.Cytoscape;
import fr.curie.BiNoM.cytoscape.utils.*;
import fr.curie.BiNoM.pathways.utils.ComputingByBFS;
/**
 * Display Influence Array as Paved Window using paving window dialog
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class InfluenceArrayAsGraphic extends FromToNodes {
	private static final long serialVersionUID = 1L;
	final public static String title="Display Influence Array as Paved Window";
	final public static String titleF="Green Black Red";
	final public static String titleT="Blue White Red";
	private boolean blueWhiteRed;
	public InfluenceArrayAsGraphic(boolean blueWhiteRed){
		this.blueWhiteRed=blueWhiteRed;
	}
	public void actionPerformed(ActionEvent e) {
		double fade=reachParameter();	
		bfs=new ComputingByBFS(Cytoscape.getCurrentNetwork(),true);
		getSrcTgt(title,true);
		if(srcDialog.isEmpty()|tgtDialog.isEmpty()) return;
		if(!bfs.initWeigths()){
			JOptionPane.showMessageDialog(null,errorWeigth,title,JOptionPane.ERROR_MESSAGE);
			return;
		}			
		double[][] inflMx=bfs.allInfluence(fade);		
		PavingData pd=new PavingData();
		pd.xNames=new String[srcDialog.size()];
		for(int s=0;s<srcDialog.size();s++) pd.xNames[s]=bfs.nodes.get(srcDialog.get(s)).getIdentifier();
		pd.yNames=new String[tgtDialog.size()];
		for(int t=0;t<tgtDialog.size();t++) pd.yNames[t]=bfs.nodes.get(tgtDialog.get(t)).getIdentifier();		
		pd.values=new double[tgtDialog.size()][srcDialog.size()];
		for(int s=0;s<srcDialog.size();s++){
			for(int t=0;t<tgtDialog.size();t++){
				pd.values[t][s]=inflMx[tgtDialog.get(t)][srcDialog.get(s)];
			}						
		}			
		PavingDialog pw=new PavingDialog(null,titleOfArray(),"Source\tTarget\tInfluence",pd,false,blueWhiteRed);
		pw.setVisible(true);
	}
}
