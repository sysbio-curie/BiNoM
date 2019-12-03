package fr.curie.BiNoM.cytoscape.nestmanager;



import java.util.Collections;
import java.util.HashSet;
import java.util.TreeMap;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.vizmap.VisualStyle;

import Main.Launcher;
/**
 * Create edges between modules from a reference network
 * Delete all old edges before
 * Search if every edge of reference network belongs to different modules
 * If all=true, same name for several edges is possible
 * else edges linking same nodes and getting same interaction (left and right identical) are compacted in one edge
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class CreateEdgesBetweenModules{
	CyApplicationManager applicationManager;
	CyNetworkManager networkManager;
	CySwingApplication swingApplication;
	public CreateEdgesBetweenModules(CyApplicationManager applicationManager,CyNetworkManager networkManager,CySwingApplication swingApplication){
		this.applicationManager=applicationManager;
		this.networkManager=networkManager;
		this.swingApplication=swingApplication;
	}
	void createDistinctEdge(CyNetwork current,CyNode src,String interaction,CyNode tgt,HashSet<String> alreadyCreated){
		if(interaction.equalsIgnoreCase("RIGHT")||interaction.equalsIgnoreCase("LEFT")) interaction="MOLECULEFLOW";
		String newEdgeName=current.getRow(src).get(CyNetwork.NAME,String.class)+
		"("+interaction+")"+current.getRow(tgt).get(CyNetwork.NAME,String.class);
		if(!alreadyCreated.contains(newEdgeName)){
			alreadyCreated.add(newEdgeName);
			CyEdge newEdge=current.addEdge(src,tgt,true);
			current.getRow(newEdge).set(CyNetwork.NAME,newEdgeName);
			current.getRow(newEdge).set(CyEdge.INTERACTION,interaction);
		}
	}
	public void actionPerformed(String title,boolean all){	
		ModuleUtils utils=new ModuleUtils();
		CyNetwork reference=utils.selectOneNetwork(networkManager,swingApplication.getJFrame(),title,"Select a network as reference");
		if (reference==null) return;
		CyNetwork current=applicationManager.getCurrentNetwork();
		TreeMap<String,HashSet<CyNode>> nodeToModules=utils.getNodeToModules(current);
		HashSet<String> alreadyCreated=new HashSet<String>();
		for(CyEdge edge:current.getEdgeList()){
			if((edge.getSource().getNetworkPointer()!=null)|(edge.getTarget().getNetworkPointer()!=null)) 
				current.removeEdges(Collections.singletonList(edge));
		}
		if(all) utils.checkEdgeColumn(current,utils.previousEdge,String.class);		
		for(CyEdge edge:reference.getEdgeList()){
			String edgeName=reference.getRow(edge).get(CyNetwork.NAME,String.class);
			CyNode src=edge.getSource();
			String srcName=reference.getRow(src).get(CyNetwork.NAME,String.class);
			CyNode tgt=edge.getTarget();
			String tgtName=reference.getRow(tgt).get(CyNetwork.NAME,String.class);
			String interaction=reference.getRow(edge).get(CyEdge.INTERACTION,String.class);		
			HashSet<CyNode> srcModules=nodeToModules.get(srcName);			
			HashSet<CyNode> tgtModules=nodeToModules.get(tgtName);			
			if((srcModules!=null)&(tgtModules!=null)){			
				for(CyNode srcModule:srcModules)for(CyNode tgtModule:tgtModules){
					if(all) utils.createAllEdge(current,srcModule,interaction,tgtModule,edgeName);
					else createDistinctEdge(current,srcModule,interaction,tgtModule,alreadyCreated);
				}
				continue;
			}
			if((srcModules==null)&(tgtModules!=null)){
				for(CyNode tgtModule:tgtModules){
					if(all) utils.createAllEdge(current,src,interaction,tgtModule,edgeName);
					else createDistinctEdge(current,src,interaction,tgtModule,alreadyCreated);
				}
				continue;
			}
			if((srcModules!=null)&(tgtModules==null)){
				for(CyNode srcModule:srcModules){
					if(all) utils.createAllEdge(current,srcModule,interaction,tgt,edgeName);
					else createDistinctEdge(current,srcModule,interaction,tgt,alreadyCreated);
				}
				continue;				
			}			
		}
		CyNetworkView networkView = applicationManager.getCurrentNetworkView();
		
		networkView.updateView();
		VisualStyle vs = Launcher.getAdapter().getVisualMappingManager().getVisualStyle(networkView);	
		Launcher.getAdapter().getVisualMappingManager().setVisualStyle(vs, networkView);
		vs.apply(networkView);
		networkView.fitContent();
		networkView.updateView();
	}
}
