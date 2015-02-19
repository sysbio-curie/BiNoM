package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2014 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import fr.curie.BiNoM.cytoscape.nestmanager.*;
import fr.curie.BiNoM.cytoscape.utils.TextBox;
import fr.curie.BiNoM.pathways.utils.*;
import giny.model.GraphPerspective;
/**
 * Display Influence Array between modules
 * See nest manager
 * Compute influence array by adding influence 
 * from reference network
 * by group of nodes in sub-networks/nests
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class InfluenceArrayBetweenModule extends ModelMenuUtils {
	private static final long serialVersionUID = 1L;
	final public static String title="Display Influence Array Between Modules";
	private boolean addNodeIndex(CyNode node,ArrayList<Integer> nodes,WeightGraphStructure refWgs){
		int index=refWgs.nodes.indexOf(node);
		if(index==-1){
			JOptionPane.showMessageDialog(Cytoscape.getDesktop(),node.getIdentifier()+" not in reference network","Stop computing",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		nodes.add(index);
		return true;
	}
	ArrayList<ArrayList<Integer>> getNodesByNest(WeightGraphStructure refWgs,WeightGraphStructure modWgs){
		ArrayList<ArrayList<Integer>> nodesByNest=new ArrayList<ArrayList<Integer>>(modWgs.nodes.size());
		for(int i=0;i<modWgs.nodes.size();i++) nodesByNest.add(new ArrayList<Integer>());
		for(int n=0;n<modWgs.nodes.size();n++){
			GraphPerspective nest=modWgs.nodes.get(n).getNestedNetwork();
			if(nest==null){
				if(!addNodeIndex(modWgs.nodes.get(n),nodesByNest.get(n),refWgs)) return null;				
			}else{
				for(CyNode node:NestUtils.getNodeList(nest)) if(!addNodeIndex(node,nodesByNest.get(n),refWgs)) return null;			
			}
		}
		return nodesByNest;
	}
	public void actionPerformed(ActionEvent e){
		CyNetwork referenceNW;
		TreeMap<String,CyNetwork> networks=NestUtils.getNetworksMap();
		String selected=NestUtils.selectOneNetwork(networks,title,"Select a network as reference");
		if (selected==null) return; else referenceNW=networks.get(selected);
		WeightGraphStructure refWgs=new WeightGraphStructure(referenceNW);
		WeightGraphStructure modWgs=new WeightGraphStructure(Cytoscape.getCurrentNetwork());
		ArrayList<ArrayList<Integer>> nodesByNest=getNodesByNest(refWgs,modWgs);
		if(nodesByNest==null) return;
		if(!refWgs.initWeights()){
			JOptionPane.showMessageDialog(Cytoscape.getDesktop(),errorWeigth,title,JOptionPane.ERROR_MESSAGE);
			return;
		}
		updatePathModel();
		updateFade();
		getAllSrcAllTgt(refWgs);
		Double[][] refInflMx;
		if(ifMultiPath){
			ComputingByDFS cpt=new ComputingByDFS(refWgs,maxDepth());
			refInflMx=cpt.allInfluence(fade, srcDialog);
		}else{
			ComputingByBFS cpt=new ComputingByBFS(refWgs);
			refInflMx=cpt.allInfluence(fade, srcDialog);
		}
		Double[][] modInflMx=new Double[modWgs.nodes.size()][modWgs.nodes.size()];
		for(int t=0;t<modWgs.nodes.size();t++) for(int s=0;s<modWgs.nodes.size();s++) modInflMx[t][s]=0.0;
		for(int t=0;t<modWgs.nodes.size();t++) for(int s=0;s<modWgs.nodes.size();s++){
			for(int tt:nodesByNest.get(t))for(int ss:nodesByNest.get(s)){
				if(!refInflMx[tt][ss].isNaN()) modInflMx[t][s]=modInflMx[t][s]+refInflMx[tt][ss];
			}
		}
		getAllSrcAllTgt(modWgs);
		new TextBox(Cytoscape.getDesktop(),addTitle(title),matrixToFormatTxt(modWgs,modInflMx,null).toString()).setVisible(true);	
	}
}