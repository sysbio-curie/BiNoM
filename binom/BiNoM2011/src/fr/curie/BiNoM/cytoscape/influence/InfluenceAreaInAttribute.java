package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import java.util.HashSet;

import cytoscape.Cytoscape;
import fr.curie.BiNoM.cytoscape.utils.ComputingByBFS;
/**
 * Empty attribute with influence computed as in influence area
 * Avoid to crash attribute of previous computing
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class InfluenceAreaInAttribute  extends FromToNodes {
	private static final long serialVersionUID = 1L;
	final public static String title="Influence Reach Area as Attribute";
	public void actionPerformed(ActionEvent e){
		bfs=new ComputingByBFS(Cytoscape.getCurrentNetwork(),true);
		getSrcAllTgt(title);
		if(srcDialog.isEmpty()) return;
		double fade=reachParameter();
		double[] startNodes=new double[bfs.nodes.size()];
		for(int s=0;s<bfs.nodes.size();s++) if(srcDialog.contains(tgtDialog.get(s))) startNodes[s]=1.0; else startNodes[s]=0.0;
		double[] inflOnNodes=bfs.reachAreaFromStarts(fade,startNodes);
		String influenceAreaAttr="INFLUENCE_AREA_";
		String[] attrNames=Cytoscape.getNodeAttributes().getAttributeNames();
		HashSet<String> attribSet=new HashSet<String>(attrNames.length);
		for(int i=0;i<attrNames.length;i++) attribSet.add(attrNames[i]);
		int ni=0;while(attribSet.contains(influenceAreaAttr+ni)) ni++;
		influenceAreaAttr=influenceAreaAttr+ni;
		for(int t=0;t<bfs.nodes.size();t++) Cytoscape.getNodeAttributes().setAttribute(bfs.nodes.get(t).getIdentifier(),influenceAreaAttr,inflOnNodes[t]);
	}
}
