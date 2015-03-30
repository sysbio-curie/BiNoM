package fr.curie.BiNoM.cytoscape.nestmanager;




import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.TreeMap;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;

import fr.curie.BiNoM.cytoscape.utils.TextBox;
import Main.Launcher;



/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/

/**
 *  List edges from reference network linking nodes in modules
 *  Several edges may matched to one edge from reference network
 *  Precise by text the normal cases: source and target inside modules
 *  Result in a text box
 *  
 *  @author Daniel.Rovera@curie.fr
 */
public class ListEdgesLinkingModules extends AbstractCyAction{
	private static final long serialVersionUID = 1L;
	final public static String title="List Edges Linking Modules";
	final String srcNotInside="\t_Src_Out_Modules";
	final String tgtNotInside="\t_Tgt_Out_Modules";
	final String insideOnly="_Inside_Module:\t";
	public CySwingAppAdapter adapter;
	public ListEdgesLinkingModules(){
		super(title,Launcher.getAdapter().getCyApplicationManager(),"network",Launcher.getAdapter().getCyNetworkViewManager());		
		setPreferredMenu(Launcher.appName + ".Module Manager");
	}
	public void actionPerformed(ActionEvent e){
		CyApplicationManager applicationManager=Launcher.getAdapter().getCyApplicationManager();
		CyNetworkManager networkManager=Launcher.getAdapter().getCyNetworkManager();
		CySwingApplication swingApplication=Launcher.getCySwingAppAdapter().getCySwingApplication();		
		ModuleUtils utils=new ModuleUtils();
		CyNetwork reference=utils.selectOneNetwork(networkManager,swingApplication.getJFrame(),title,"Select a network as reference");
		if (reference==null) return;
		CyNetwork current=applicationManager.getCurrentNetwork();
		TreeMap<String,HashSet<CyNode>> nodeToModules=utils.getNodeToModules(current);		
		StringBuffer text=new StringBuffer("Edge\tSource\tInteraction\tTarget\tSrc_Module\tTgt_Module\n");	
		for(CyEdge edge:reference.getEdgeList()){
			StringBuffer edgeName=new StringBuffer(reference.getRow(edge).get(CyNetwork.NAME,String.class));edgeName.append("\t");
			String srcName=reference.getRow(edge.getSource()).get(CyNetwork.NAME,String.class);
			String tgtName=reference.getRow(edge.getTarget()).get(CyNetwork.NAME,String.class);
			edgeName.append(srcName);edgeName.append("\t");
			edgeName.append(reference.getRow(edge).get("interaction",String.class));edgeName.append("\t");
			edgeName.append(tgtName);
			HashSet<CyNode> srcModules=nodeToModules.get(srcName);			
			HashSet<CyNode> tgtModules=nodeToModules.get(tgtName);
			if((srcModules!=null)&(tgtModules!=null)){			
				for(CyNode srcModule:srcModules)for(CyNode tgtModule:tgtModules){
					text.append(edgeName);text.append("\t");
					if(srcModule==tgtModule){
						text.append(insideOnly);
						text.append(current.getRow(tgtModule).get(CyNetwork.NAME,String.class));	
					}else{
						text.append(current.getRow(srcModule).get(CyNetwork.NAME,String.class));text.append("\t");
						text.append(current.getRow(tgtModule).get(CyNetwork.NAME,String.class));					
					}
					text.append("\r\n");
				}
				continue;
			}	
			if((srcModules==null)&(tgtModules!=null)){
				for(CyNode tgtModule:tgtModules){
					text.append(edgeName);
					text.append(srcNotInside);text.append("\t");
					text.append(current.getRow(tgtModule).get(CyNetwork.NAME,String.class));text.append("\r\n");	
				}
				continue;				
			}
			if((srcModules!=null)&(tgtModules==null)){
				for(CyNode srcModule:srcModules){
					text.append(edgeName);text.append("\t");
					text.append(current.getRow(srcModule).get(CyNetwork.NAME,String.class));
					text.append(tgtNotInside);text.append("\r\n");					
				}
				continue;
			}
			if((srcModules==null)&(tgtModules==null)){
				text.append(edgeName);text.append(srcNotInside);text.append(tgtNotInside);text.append("\r\n");					
			}
		}
		new TextBox(swingApplication.getJFrame(),title,text.toString()).setVisible(true);		
	}
}
