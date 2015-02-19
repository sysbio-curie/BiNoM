package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import java.util.HashSet;
import javax.swing.JOptionPane;
import cytoscape.Cytoscape;
import fr.curie.BiNoM.pathways.utils.*;
/**
 * Empty attribute with influence computed as in influence area
 * Avoid to crash attribute of previous computing
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class InfluenceAreaInAttribute  extends ModelMenuUtils {
	private static final long serialVersionUID = 1L;
	final public static String title="Influence Reach Area as Attribute";
	public void actionPerformed(ActionEvent e){	
		WeightGraphStructure wgs=new WeightGraphStructure(Cytoscape.getCurrentNetwork());		
		getSrcAllTgt(wgs,title);
		if(srcDialog.isEmpty()) return;
		double[] startNodes=new double[wgs.nodes.size()];
		for(int s=0;s<wgs.nodes.size();s++) if(srcDialog.contains(tgtDialog.get(s))) startNodes[s]=1.0; else startNodes[s]=0.0;
		updatePathModel();
		updateFade();
		double[] inflOnNodes;
		if(ifMultiPath){
			ComputingByDFS cpt=new ComputingByDFS(wgs,maxDepth());
			inflOnNodes=cpt.reachAreaFromStarts(fade,srcDialog,startNodes);
		}else{
			ComputingByBFS cpt=new ComputingByBFS(wgs);
			inflOnNodes=cpt.reachAreaFromStarts(fade,srcDialog,startNodes);
		}
		String influenceAreaAttr="INFLUENCE_AREA_";
		String[] attrNames=Cytoscape.getNodeAttributes().getAttributeNames();
		HashSet<String> attribSet=new HashSet<String>(attrNames.length);
		for(int i=0;i<attrNames.length;i++) attribSet.add(attrNames[i]);
		int ni=0;while(attribSet.contains(influenceAreaAttr+ni)) ni++;
		influenceAreaAttr=influenceAreaAttr+ni;
		JOptionPane.showMessageDialog(Cytoscape.getDesktop(),"Results in Created Attribute "+influenceAreaAttr+
				" (parameters in window title)\r\nThink of Keeping Parameters and Start Data",addTitle(title),JOptionPane.WARNING_MESSAGE);
		for(int t=0;t<wgs.nodes.size();t++) Cytoscape.getNodeAttributes().setAttribute(wgs.nodes.get(t).getIdentifier(),influenceAreaAttr,inflOnNodes[t]);
	}
}
