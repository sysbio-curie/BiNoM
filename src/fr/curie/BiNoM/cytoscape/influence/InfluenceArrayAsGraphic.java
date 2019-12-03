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
 * Display Influence Array as Paved Window using paving window dialog
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class InfluenceArrayAsGraphic extends ModelMenuUtils {
	private static final long serialVersionUID = 1L;
	final public static String title="Display Influence Array as Paved Window";
	final public static String titleF="Green Black Red";
	final public static String titleT="Blue White Red";
	private boolean blueWhiteRed;
	public InfluenceArrayAsGraphic(boolean blueWhiteRed){
		this.blueWhiteRed=blueWhiteRed;
	}
	public void actionPerformed(ActionEvent e) {
		WeightGraphStructure wgs=new WeightGraphStructure(Cytoscape.getCurrentNetwork());		
		if(!wgs.initWeights()){
			JOptionPane.showMessageDialog(Cytoscape.getDesktop(),errorWeigth,title,JOptionPane.ERROR_MESSAGE);
			return;
		}
		updatePathModel();
		updateFade();
		getSrcTgt(wgs,title);
		PavingData pd=new PavingData();
		pd.xNames=new String[srcDialog.size()];
		for(int s=0;s<srcDialog.size();s++) pd.xNames[s]=wgs.nodes.get(srcDialog.get(s)).getIdentifier();
		pd.yNames=new String[tgtDialog.size()];
		for(int t=0;t<tgtDialog.size();t++) pd.yNames[t]=wgs.nodes.get(tgtDialog.get(t)).getIdentifier();		
		pd.values=new double[tgtDialog.size()][srcDialog.size()];
		if(srcDialog.isEmpty()|tgtDialog.isEmpty()) return;
		Double[][] inflMx;
		if(ifMultiPath){
			ComputingByDFS cpt=new ComputingByDFS(wgs,maxDepth());
			inflMx=cpt.allInfluence(fade, srcDialog);
			for(int s=0;s<srcDialog.size();s++){
				for(int t=0;t<tgtDialog.size();t++){
					pd.values[t][s]=inflMx[tgtDialog.get(t)][srcDialog.get(s)];
				}						
			}	
			PavingDialog pw=new PavingDialog(Cytoscape.getDesktop(),addTitle(title),"Source\tTarget\tInfluence",pd,false,blueWhiteRed);
			pw.setVisible(true);
		}else{
			ComputingByBFS cpt=new ComputingByBFS(wgs);
			inflMx=cpt.allInfluence(fade, srcDialog);
			for(int s=0;s<srcDialog.size();s++){
				for(int t=0;t<tgtDialog.size();t++){
					pd.values[t][s]=inflMx[tgtDialog.get(t)][srcDialog.get(s)];
				}						
			}	
			PavingDialog pw=new PavingDialog(Cytoscape.getDesktop(),addTitle(title),"Source\tTarget\tInfluence",pd,false,blueWhiteRed);
			pw.setVisible(true);
		}
	}
}
