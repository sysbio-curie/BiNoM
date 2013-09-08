package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import cytoscape.Cytoscape;
import fr.curie.BiNoM.pathways.utils.ComputingByBFS;
/**
 * Influence by Active Nodes from ACTIV_IN to Attribute result in ACTIV_OUT
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class InfluenceByAttribute  extends FromToNodes {
	private static final long serialVersionUID = 1L;
	final public static String title="Influence by Active Nodes as Attribute";
	final String activInAttr="ACTIV_IN";
	final String activOutAttr="ACTIV_OUT";
	public void actionPerformed(ActionEvent e){
		bfs=new ComputingByBFS(Cytoscape.getCurrentNetwork(),true);
		if(!bfs.initWeigths()){
			JOptionPane.showMessageDialog(Cytoscape.getDesktop(),errorWeigth,title,JOptionPane.ERROR_MESSAGE);
			return;
		}
		double[] activIn=new double[bfs.nodes.size()];
		for(int s=0;s<bfs.nodes.size();s++){
			Double activ=Cytoscape.getNodeAttributes().getDoubleAttribute(bfs.nodes.get(s).getIdentifier(),activInAttr);
			if(activ!=null) activIn[s]=activ; else activIn[s]=0.0;
		}		
		double fade=reachParameter();
		double[] activOut=bfs.activityFromIn(fade,activIn);
		for(int t=0;t<bfs.nodes.size();t++) Cytoscape.getNodeAttributes().setAttribute(bfs.nodes.get(t).getIdentifier(),activOutAttr,activOut[t]);
	}
}

