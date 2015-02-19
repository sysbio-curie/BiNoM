package fr.curie.BiNoM.cytoscape.nestmanager;


import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeMap;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

import fr.curie.BiNoM.cytoscape.utils.TextBox;
import Main.Launcher;
/**
 * Create a belonging matrix for every node in module
 * Complete it by node size and frequency in modules
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class FindCommonNodes extends AbstractCyAction {	
	private static final long serialVersionUID = 1L;
	final static String title="Find Common Nodes In Modules";
	public CySwingAppAdapter adapter;
	public FindCommonNodes(){
		super(title,Launcher.getAdapter().getCyApplicationManager(),"network",Launcher.getAdapter().getCyNetworkViewManager());		
		setPreferredMenu("Plugin.BiNoM 3.BiNoM Module Manager");
	}
	public void actionPerformed(ActionEvent e) {
		CyApplicationManager applicationManager=Launcher.getAdapter().getCyApplicationManager();
		CySwingApplication swingApplication=Launcher.getCySwingAppAdapter().getCySwingApplication();
		final CyNetwork current=applicationManager.getCurrentNetwork();
		TreeMap<String,HashSet<CyNode>> nodeToModules=(new ModuleUtils()).getNodeToModules(current);
		ArrayList<CyNode> moduleList=new ArrayList<CyNode>();
		for(CyNode moduleNode:current.getNodeList()) if(moduleNode.getNetworkPointer()!=null) moduleList.add(moduleNode);
		Collections.sort(moduleList,new Comparator<CyNode>(){
			public int compare(CyNode n1, CyNode n2){
				return current.getRow(n1).get(CyNetwork.NAME,String.class).compareTo(current.getRow(n2).get(CyNetwork.NAME,String.class));
			}});
		int[] moduleSize=new int[moduleList.size()];
		for(int i=0;i<moduleSize.length;i++) moduleSize[i]=0;
		StringBuffer text=new StringBuffer("\t");
		for(int i=0;i<moduleList.size();i++){
			text.append(current.getRow(moduleList.get(i)).get(CyNetwork.NAME,String.class));text.append("\t");
		}
		text.append("frequency\r\n");
		for(String node:nodeToModules.keySet()){
			int frequency=0;
			text.append(node);text.append("\t");
			for(int i=0;i<moduleList.size();i++){
				if(nodeToModules.get(node).contains(moduleList.get(i))){
					text.append("1");
					frequency++;
					moduleSize[i]++;
				}else text.append("0");
				text.append("\t");
			}
			text.append(frequency);
			frequency=0;
			text.append("\r\n");
		}
		text.append("size");
		for(int i=0;i<moduleSize.length;i++){
			text.append("\t");text.append(moduleSize[i]);
		}
		new TextBox(swingApplication.getJFrame(),title,text.toString()).setVisible(true);	
	}
}
