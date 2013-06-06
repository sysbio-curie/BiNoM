package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2013 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JOptionPane;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.util.CytoscapeAction;
import fr.curie.BiNoM.cytoscape.utils.*;
/**
 * In menu, this class selects nodes and edges between 2 lists of nodes
 * by intersection of 2 sets made get by descending and ascending the graph
 * This class is also used as up class for other menu classes providing 
 * input of reach parameter,formatted output of arrays and two lists node dialog
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class FromToNodes extends CytoscapeAction {
	private static final long serialVersionUID = 1L;
	final public static String title="Select Sub-network from Sources to Targets";
	final public static String errorWeigth="Influence weigth attribute not rigthly updated\r\nCannot display influence";
	ArrayList<Integer> srcDialog;
	ArrayList<Integer> tgtDialog;
	ComputingByBFS bfs;
	final String reachAttrib="Influence_Reach";
	final double reachDefault=5;
	protected double inputReach(){
		String input=JOptionPane.showInputDialog(Cytoscape.getDesktop(),"Input the number of paths beyond the signal is under 5%","Parameter of signal fading",JOptionPane.QUESTION_MESSAGE);
		try{
			double reach=Double.valueOf(input);
			Cytoscape.getNetworkAttributes().setAttribute(Cytoscape.getCurrentNetwork().getIdentifier(),reachAttrib,reach);
			return reach;
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(Cytoscape.getDesktop(),"Wrong input, default value "+reachDefault,"Warning",JOptionPane.ERROR_MESSAGE);
			Cytoscape.getNetworkAttributes().setAttribute(Cytoscape.getCurrentNetwork().getIdentifier(),reachAttrib,reachDefault);
			return reachDefault;
		}	
	}
	protected double reachParameter(){
		Double reach=Cytoscape.getNetworkAttributes().getDoubleAttribute(Cytoscape.getCurrentNetwork().getIdentifier(),reachAttrib);
		if(reach==null) reach=inputReach();	
		return Math.exp(Math.log(0.05)/reach);
	}
	protected String titleOfArray(){
		String nwId=Cytoscape.getCurrentNetwork().getIdentifier();
		return "Influence Array of "+nwId+" Reach="+Cytoscape.getNetworkAttributes().getDoubleAttribute(nwId,reachAttrib);
	}
	String influenceToString(ComputingByBFS bfs,double[][] inflMx,String decFormatS,ArrayList<Integer> srcDialog,ArrayList<Integer> tgtDialog){		
		int size=0;
		for(int i=0;i<srcDialog.size();i++) size=size+bfs.nodes.get(srcDialog.get(i)).getIdentifier().length()+1;
		for(int i=0;i<tgtDialog.size();i++) size=size+bfs.nodes.get(tgtDialog.get(i)).getIdentifier().length()+1;
		if(decFormatS==null) for(int i=0;i<srcDialog.size();i++)for(int j=0;j<tgtDialog.size();j++) size=size+21;
		else for(int i=0;i<srcDialog.size();i++)for(int j=0;j<tgtDialog.size();j++)
			if(Double.isNaN(inflMx[tgtDialog.get(j)][srcDialog.get(i)])) size=size+2; else size=size+decFormatS.length()+2;
		StringBuffer txt=new StringBuffer(size);
		if(decFormatS==null){
			for(int s=0;s<srcDialog.size();s++) txt.append("\t"+bfs.nodes.get(srcDialog.get(s)).getIdentifier());
			txt=txt.append("\r\n");
			for(int t=0;t<tgtDialog.size();t++){
				txt.append(bfs.nodes.get(tgtDialog.get(t)).getIdentifier());
				for(int s=0;s<srcDialog.size();s++)
					if(Double.isNaN(inflMx[tgtDialog.get(t)][srcDialog.get(s)])) txt.append("\t0.0");
					else txt.append("\t"+inflMx[tgtDialog.get(t)][srcDialog.get(s)]);
				txt.append("\r\n");
			}
		}else{
			DecimalFormat decFormat=new DecimalFormat(decFormatS);
			for(int s=0;s<srcDialog.size();s++) txt.append("\t"+bfs.nodes.get(srcDialog.get(s)).getIdentifier());
			txt=txt.append("\r\n");
			for(int t=0;t<tgtDialog.size();t++){
				txt.append(bfs.nodes.get(tgtDialog.get(t)).getIdentifier());
				for(int s=0;s<srcDialog.size();s++)
					if(Double.isNaN(inflMx[tgtDialog.get(t)][srcDialog.get(s)])) txt.append("\tnc");
					else txt.append("\t"+decFormat.format(inflMx[tgtDialog.get(t)][srcDialog.get(s)]));
				txt.append("\r\n");
			}
		}
		return txt.toString();
	}
	int[] endsToArray(ArrayList<HashSet<Integer>> thisAdjacency){
		ArrayList<Integer> ends=new ArrayList<Integer>();
		for(int i=0;i<thisAdjacency.size();i++) if(thisAdjacency.get(i).isEmpty()) ends.add(i);
		int[] endArray=new int[ends.size()];
		for(int i=0;i<ends.size();i++) endArray[i]=ends.get(i);		
		return endArray;
	}
	void getSrcAllTgt(String title){
		tgtDialog=new ArrayList<Integer>();
		for(int i=0;i<bfs.nodes.size();i++) tgtDialog.add(i);
		String[] nodeNames=new String[bfs.nodes.size()];
		for(int i=0;i<bfs.nodes.size();i++) nodeNames[i]=bfs.nodes.get(i).getIdentifier();		
		ArrayList<String> selection=new ArrayList<String>();
		ListDialog listBox=new ListDialog(Cytoscape.getDesktop(),title,"Select Start Nodes",nodeNames);
		listBox.launchDialog(selection);
		srcDialog=new ArrayList<Integer>();
		for(String nodeId:selection) srcDialog.add(bfs.nodes.indexOf(Cytoscape.getCyNode(nodeId,false)));
	}
	void getSrcTgt(String title,boolean prefilled){
		ArrayList<String> nodeIds=new ArrayList<String>();		
		for(CyNode node:bfs.nodes) nodeIds.add(node.getIdentifier());
		String[] nodeNames=new String[nodeIds.size()];
		for(int i=0;i<nodeIds.size();i++) nodeNames[i]=nodeIds.get(i);
		ArrayList<String> selectNode1=new ArrayList<String>();
		ArrayList<String> selectNode2=new ArrayList<String>();
		int[] preSrc=null;
		int[] preTgt=null;
		if(prefilled){
			ArrayList<HashSet<Integer>> reverseAdjacency=new ArrayList<HashSet<Integer>>(bfs.nodes.size());
			for(int i=0;i<bfs.nodes.size();i++) reverseAdjacency.add(new HashSet<Integer>());
			for(int i=0;i<bfs.edges.size();i++) reverseAdjacency.get(bfs.tgts.get(i)).add(i);
			preSrc=endsToArray(reverseAdjacency);
			preTgt=endsToArray(bfs.adjacency);			
		}
		TwoListDialog dialog=new TwoListDialog(Cytoscape.getDesktop(),title,"Select nodes as","Source","Target",nodeNames,nodeNames);
		dialog.launchDialog(preSrc,preTgt,selectNode1,selectNode2);		
		srcDialog=new ArrayList<Integer>();
		for(String nodeId:selectNode1) srcDialog.add(bfs.nodes.indexOf(Cytoscape.getCyNode(nodeId,false)));
		tgtDialog=new ArrayList<Integer>();
		for(String nodeId:selectNode2) tgtDialog.add(bfs.nodes.indexOf(Cytoscape.getCyNode(nodeId,false)));
	}	
	public void actionPerformed(ActionEvent e){
		bfs=new ComputingByBFS(Cytoscape.getCurrentNetwork(),true);
		getSrcTgt(title,false);
		HashSet<Integer> nodes=bfs.extractNodes(srcDialog,tgtDialog);		
		for(int node:nodes)	Cytoscape.getCurrentNetwork().setSelectedNodeState(bfs.nodes.get(node),true);		
		Cytoscape.getCurrentNetworkView().redrawGraph(true,true);
	}
}
