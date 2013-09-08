package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import cytoscape.Cytoscape;
import fr.curie.BiNoM.cytoscape.utils.TextBox;
import fr.curie.BiNoM.pathways.utils.ComputingByBFS;
/**
 * Display the Network  size and Parameter Features as min and max influence
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class InfluenceFeatures extends FromToNodes{
	private static final long serialVersionUID = 1L;
	final public static String title="Display Network and Parameter Features";
	public void actionPerformed(ActionEvent e){		
		double fade=reachParameter();
		Integer reach=Cytoscape.getNetworkAttributes().getIntegerAttribute(Cytoscape.getCurrentNetwork().getIdentifier(),reachAttrib);
		bfs=new ComputingByBFS(Cytoscape.getCurrentNetwork(),true);
		if(!bfs.initWeigths()){
			JOptionPane.showMessageDialog(Cytoscape.getDesktop(),errorWeigth,title,JOptionPane.ERROR_MESSAGE);
			return;
		}	
		double[][] infl=bfs.allInfluence(fade);
		int nc=0;
		double mean=0.0;
		double min=Double.MAX_VALUE;
		double max=Double.MIN_VALUE;
		for(int t=0;t<bfs.nodes.size();t++)	for(int s=0;s<bfs.nodes.size();s++){
			if(Double.isNaN(infl[t][s])) nc++;
			else{
				mean=mean+infl[t][s];
				if(infl[t][s]<min) min=infl[t][s]; else if(infl[t][s]>max) max=infl[t][s];
			}
		}		
		double connect=(double)(bfs.nodes.size()*bfs.nodes.size()-nc)/(double)(bfs.nodes.size()*bfs.nodes.size());
		mean=mean/(bfs.nodes.size()*bfs.nodes.size()-nc);
		String txt="";
		txt=txt+"Connectivity\t"+connect+"\r\nReach\t"+reach+"\r\nFade\t"+fade+"\r\nMeanInfluence\t"+mean+"\r\nMinInfluence\t"+min+"\r\nMaxInfluence\t"+max;
		new TextBox(Cytoscape.getDesktop(),titleOfArray(),txt).setVisible(true);		
	}
}
