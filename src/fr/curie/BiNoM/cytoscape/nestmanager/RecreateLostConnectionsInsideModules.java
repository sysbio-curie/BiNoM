package fr.curie.BiNoM.cytoscape.nestmanager;

/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.work.TaskIterator;

import Main.Launcher;

/**
 * Create edges inside nested network from a reference network
 * Delete all old edges of nested network
 * Search edges which nodes belong to the nested network
 * Copy edges attributes included from the reference network to every nested network
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class RecreateLostConnectionsInsideModules  extends AbstractCyAction {
	
	
	public RecreateLostConnectionsInsideModules(){
			super(title,Launcher.getAdapter().getCyApplicationManager(),"network",Launcher.getAdapter().getCyNetworkViewManager());		
			setPreferredMenu(Launcher.appName + ".Module Manager");
		// TODO Auto-generated constructor stub
	}
	
	private static final long serialVersionUID = 1L;
	final public static String title="Recreate Lost Connections inside Modules";
	private CyNetwork referenceNW;
	
	void updateConnections(CyNetwork network){
		if(network==referenceNW) 
			return;
		ArrayList toRemove = new ArrayList();
		for(CyEdge edge:network.getEdgeList())
			toRemove.add(edge);
		network.removeEdges(toRemove);
		
		HashSet<CyEdge> edges=ModuleUtils.edgesLinkingNodes(referenceNW,ModuleUtils.getNodeSet(network));
		for(CyEdge edge:edges)
			try {
				addEd(network, referenceNW, edge);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static CyNetwork addEd(CyNetwork network, CyNetwork fromNet, CyEdge ed) throws Exception{
		
		//System.out.println("Node name sorce node : " + fromNet.getRow(ed.getSource()).get(CyNetwork.NAME, String.class));
		
		CyNode source = Launcher.getNodeWithName(network, network.getDefaultNodeTable(), "name", fromNet.getRow(ed.getSource()).get(CyNetwork.NAME, String.class));
		CyNode target = Launcher.getNodeWithName(network, network.getDefaultNodeTable(), "name", fromNet.getRow(ed.getTarget()).get(CyNetwork.NAME, String.class));
	
		//System.out.println("source: "+source);
		//System.out.println("target: "+target);
		
		CyEdge newEdge = network.addEdge(source, target, true);
		//set node name 
		network.getDefaultEdgeTable().getRow(newEdge.getSUID()).set("name", fromNet.getRow(ed).get(CyNetwork.NAME, String.class));
		
		//set node attributes
		Iterator<CyColumn> columnsIterator = fromNet.getDefaultEdgeTable().getColumns().iterator();
    	
	    while(columnsIterator.hasNext()){
	    	CyColumn col = columnsIterator.next();
	    	
	    	boolean set = false;
	    	try{
		    	String value = fromNet.getRow(ed).get(col.getName(), String.class);
		    	
		    	if(network.getDefaultEdgeTable().getColumn(col.getName()) == null)
	    			network.getDefaultEdgeTable().createColumn(col.getName(), String.class, false);
		    	
		    	network.getRow(newEdge).set(col.getName(), value );
		    	set = true;
		    }catch(Exception e){}
		    
		    if (!set){
			    try{	
			    	double value = fromNet.getRow(ed).get(col.getName(), Double.class);
			    	
			    	if(network.getDefaultEdgeTable().getColumn(col.getName()) == null)
		    			network.getDefaultEdgeTable().createColumn(col.getName(), Double.class, false);
			    	
			    	network.getRow(newEdge).set(col.getName(), value );
			    	set = true;
			    }catch(Exception e){}
		    }
		    if (!set){
			    try{	
			    	int value = ed.getSource().getNetworkPointer().getRow(ed).get(col.getName(), Integer.class);
			    	
			    	if(network.getDefaultEdgeTable().getColumn(col.getName()) == null)
		    			network.getDefaultEdgeTable().createColumn(col.getName(), Integer.class, false);
			    	
			    	network.getRow(newEdge).set(col.getName(), value );
			    	set = true;
			    }catch(Exception e){}
		    }    	
	    }	
		return network;
	}
	
	public void actionPerformed(ActionEvent v) {
		
			referenceNW=ModuleUtils.selectOneNetwork(Launcher.getAdapter().getCyNetworkManager(), 
					Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(),
					title,"Select a network as reference");
		
	}
}
