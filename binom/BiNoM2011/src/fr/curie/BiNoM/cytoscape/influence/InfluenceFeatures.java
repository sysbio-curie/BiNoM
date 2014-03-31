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
 * Display the Network  size and Parameter Features as 
 * connectivity, rate of connected nodes in influence matrix
 * mean, minimum and maximum influence
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class InfluenceFeatures extends ModelMenuUtils{
	private static final long serialVersionUID = 1L;
	final public static String title="Display Network and Parameter Features";
	private void features(WeightGraphStructure cpt,Double[][] infl,String model){
		int nc=0;
		double mean=0.0;
		double sqrSum=0;
		double min=Double.MAX_VALUE;
		double max=Double.MIN_VALUE;
		for(int t=0;t<cpt.nodes.size();t++)	for(int s=0;s<cpt.nodes.size();s++){
			if((infl[t][s]).isNaN()) nc++;
			else{
				mean=mean+infl[t][s];
				sqrSum=sqrSum+infl[t][s]*infl[t][s];
				if(infl[t][s]<min) min=infl[t][s]; else if(infl[t][s]>max) max=infl[t][s];
			}
		}		
		double connect=(double)(cpt.nodes.size()*cpt.nodes.size()-nc)/(double)(cpt.nodes.size()*cpt.nodes.size());
		mean=mean/(cpt.nodes.size()*cpt.nodes.size()-nc);
		sqrSum=sqrSum/(cpt.nodes.size()*cpt.nodes.size()-nc);
		double sigma=Math.sqrt(sqrSum-mean*mean);
		String txt="Network\t"+Cytoscape.getCurrentNetwork().getIdentifier();
		txt=txt+"\r\nReach\t"+reach+"\r\nFade\t"+fade+"\r\nModel\t"+model+"\r\nConnectivity\t"+connect;
		txt=txt+"\r\nMeanInfluence\t"+mean+"\r\nStandardDeviation\t"+sigma+"\r\nMinInfluence\t"+min+"\r\nMaxInfluence\t"+max;
		new TextBox(Cytoscape.getDesktop(),title,txt).setVisible(true);	
	}
	public void actionPerformed(ActionEvent e){
		WeightGraphStructure wgs=new WeightGraphStructure(Cytoscape.getCurrentNetwork());		
		if(!wgs.initWeights()){
			JOptionPane.showMessageDialog(Cytoscape.getDesktop(),errorWeigth,title,JOptionPane.ERROR_MESSAGE);
			return;
		}
		updatePathModel();
		updateFade();
		srcDialog=new ArrayList<Integer>();
		for(int n=0;n<wgs.nodes.size();n++) srcDialog.add(n);
		if(ifMultiPath){
			ComputingByDFS cpt=new ComputingByDFS(wgs,maxDepth());
			features(cpt,cpt.allInfluence(fade,srcDialog),"MultiPath");
		}else{
			ComputingByBFS cpt=new ComputingByBFS(wgs);
			features(cpt,cpt.allInfluence(fade,srcDialog),"MonoPath");
		}		
	}
}
