package fr.curie.BiNoM.cytoscape.nestmanager;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE  
*/
import java.awt.event.ActionEvent;
import java.util.HashSet;
import javax.swing.JOptionPane;

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.util.CytoscapeAction;
import fr.curie.BiNoM.cytoscape.lib.TaskManager;
import fr.curie.BiNoM.cytoscape.nestmanager.ShortPathClustering.Cluster;
import fr.curie.BiNoM.cytoscape.utils.TextBoxDialog;
/**
 * Dialog of function clustering by oriented shortest paths
 * Launch the task of creating clusters
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class ClusterByShortPath extends CytoscapeAction{
	private static final long serialVersionUID = 1L;
	final public static String title="Agglomerate the Nearest Nodes in Modules";
	private int maxPathLength;
	int sizeCeiling;
	String maxPathLengthS;
	ActionEvent event;
	CyNetwork currentNW;
	HashSet<Cluster> clusters;
	private boolean inputParameters(){
		String input=JOptionPane.showInputDialog(null,"Input maximal distance (OO=infinity) and maximal number of nodes as 99/999","Parameters of clustering process",JOptionPane.QUESTION_MESSAGE);
		if (input==null) return false;
		try{
			maxPathLengthS=input.substring(0,input.indexOf("/")).trim();
			if(maxPathLengthS.equals("OO")) maxPathLength=Integer.MAX_VALUE; else maxPathLength=Integer.parseInt(maxPathLengthS);
			sizeCeiling=Integer.parseInt(input.substring(input.indexOf("/")+1).trim());
			return true;
		}
		catch(StringIndexOutOfBoundsException e){}
		catch(NumberFormatException e){}
		JOptionPane.showMessageDialog(null,"Wrong input format: "+input,"Warning",JOptionPane.ERROR_MESSAGE);
		return false;
	}
	private boolean createNetworksDialog(){
		String mess="Create "+clusters.size()+" modules with parameters:"+maxPathLengthS+"/"+sizeCeiling;
		String txt="Name\tSize\tLastDistance\tLinkNumber\r\n";
		int n=0;
		for(Cluster clt:clusters){			
			String name="Module_"+maxPathLengthS+"/"+sizeCeiling+"_";
			if(n++<9) name=name+"0"+n; else name=name+n;
			clt.setName(name);
			txt=txt+name+"\t"+clt.getSet().size()+"\t"+clt.getCreateDistance().toString()+"\r\n";
		}
		TextBoxDialog ifCreate=new TextBoxDialog(Cytoscape.getDesktop(),title,mess,txt);
		ifCreate.setVisible(true);	
		return ifCreate.getYN();
	}
	public void actionPerformed(ActionEvent e){
		if(!inputParameters()) return;
		event=e;
		currentNW=Cytoscape.getCurrentNetwork();
		clusters=(new ShortPathClustering(currentNW)).clustering(maxPathLength,sizeCeiling);
		if(createNetworksDialog()) TaskManager.executeTask(new ClusterByShortPathTask(this));;
	}
}