/*
   BiNoM Cytoscape Plugin
   Copyright (C) 2006-2007 Curie Institute, 26 rue d'Ulm, 75005 Paris - FRANCE

   BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
*/

/*
  BiNoM authors:
	Andrei Zinovyev : http://www.ihes.fr/~zinovyev
	Eric Viara : http://www.sysra.com/viara
	Laurence Calzone :	http://leibniz.biol.vt.edu/people/laurence/laurence.html
*/
package fr.curie.BiNoM.cytoscape.lib;

import org.cytoscape.work.TaskMonitor;
import org.cytoscape.app.CyAppAdapter;

import edu.rpi.cs.xgmml.*;

import org.cytoscape.view.model.CyNetworkView;

import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.pathways.wrappers.*;

import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.subnetwork.CyRootNetwork;
import org.cytoscape.model.subnetwork.CySubNetwork;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;

import Main.Launcher;

public class NetworkFactorySubNetwork {
	
	private static CyNode getNodeWithName(
            final CyNetwork net, final CyTable table,
            final String colname, final Object value){
        final Collection<CyRow> matchingRows = table.getMatchingRows(colname, value);
        final String primaryKeyColname = table.getPrimaryKey().getName();
        for (final CyRow row : matchingRows)
        {
            final Long nodeId = row.get(primaryKeyColname, Long.class);
            if (nodeId == null)
                continue;
            final CyNode node = net.getNode(nodeId);
            if (node == null)
                continue;
            return node;
        }
        return null;
    }

    public static final String ATTR_SEP = "@@@";

    public static CyNetwork createNetwork(String networkName,GraphDocument graphDocument,VisualStyle vizsty,boolean applyLayout, TaskMonitor taskMonitor, CyNetwork from, HashMap nodeMap, HashMap edgeMap) 
    		throws Exception {

    	return createNetworkPerform(networkName, graphDocument, vizsty, null, applyLayout, taskMonitor, from, nodeMap, edgeMap);
    }

    public static CyNetwork createNetwork(String networkName,GraphDocument graphDocument,VisualStyleDefinition vizsty_def,boolean applyLayout,
    	 TaskMonitor taskMonitor, CyNetwork from, HashMap nodeMap, HashMap edgeMap) throws Exception {
    	return createNetworkPerform(networkName, graphDocument, null, vizsty_def, applyLayout, taskMonitor, from,nodeMap, edgeMap);
    }

    private static CyNetwork createNetworkPerform(String networkName,GraphDocument graphDocument,VisualStyle vizsty,VisualStyleDefinition vizsty_def,
    		boolean applyLayout,TaskMonitor taskMonitor, CyNetwork fromNetw, HashMap nodeMap, HashMap edgeMap) throws Exception {

	if (graphDocument == null)
	    return null;

	edu.rpi.cs.xgmml.GraphicGraph grf = graphDocument.getGraph();
	edu.rpi.cs.xgmml.GraphicNode nodes[] = grf.getNodeArray();
	edu.rpi.cs.xgmml.GraphicEdge edges[] = grf.getEdgeArray();

	if (nodes.length == 0) {
	    if (taskMonitor != null) {
		taskMonitor.setStatusMessage("File imported (" + nodes.length +
				      " nodes, " + edges.length + " edges)");
		taskMonitor.setProgress(1);;
	    }
	    return null;
	}

	CyAppAdapter adapter = Launcher.getAdapter();
	BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(adapter.getCyApplicationManager().getCurrentNetwork());
	
	CyRootNetwork networkCollection = Launcher.getAdapter().getCyRootNetworkManager().getRootNetwork(fromNetw);
			
	//create a subnetwork in the network collection
	CySubNetwork netw = networkCollection.addSubNetwork();
	netw.getRow(netw).set(CyNetwork.NAME, Launcher.noSynonymNetwork(networkName));
	
	// add nodes
	for (int n = 0; n < nodes.length; n++) {
	    edu.rpi.cs.xgmml.GraphicNode node = nodes[n];
	    
    	CyNode nd= (CyNode) nodeMap.get(toId(node.getId()));    	
    	
    	if(nd == null){
    		nd= netw.addNode();
		    netw.getDefaultNodeTable().getRow(nd.getSUID()).set("name", toId(node.getId()));
		   
		    log("nodes #" + n + " " + node.getId() + " " +	node.getLabel() + " " + node.getName());
				
		    //save nodes attributes
		    edu.rpi.cs.xgmml.AttDocument.Att attrs[] = node.getAttArray();
		    HashMap listAttrMap = getListAttrMap(attrs);
		    
		    for (int j = 0; j < attrs.length; j++) {
		    	
				edu.rpi.cs.xgmml.AttDocument.Att attr = attrs[j];			
			
				String attrValue = attr.getValue();					
				if (listAttrMap.get(attr.getLabel()) != null) {
					String o_attrValue = netw.getRow(nd).get(attr.getLabel(), String.class);
				    if (o_attrValue != null && o_attrValue.length() > 0) {
				    	attrValue = o_attrValue + ATTR_SEP + attr.getValue();
				    }
				}					
				
				if(attrValue!=null){
					try{
						if(attr.getType()==ObjectType.REAL){
							try{								
							//insert value in the table
							netw.getRow(nd).set(attr.getLabel(), attr.getValue());
									
							}catch(Exception e){
								e.printStackTrace();
							}
						}
						else {
							//insert value in the table
							netw.getRow(nd).set(attr.getLabel(), attr.getValue());
						}
					}catch(Exception e){
					}
				}		
				log("\t" + attr.getLabel() + " " + attr.getName() + " = " + attr.getValue());
		    }
    		
    	}
	    netw.addNode(nd);
	}
	
	for (int n = 0; n < edges.length; n++) {
	    edu.rpi.cs.xgmml.GraphicEdge edge = edges[n];
	    	    	
    	CyEdge ed = (CyEdge) edgeMap.get(toId(edge.getId()));
    	if(ed == null){
    		
    		 String interaction = "UNKNOWN";
 		    AttDocument.Att intatt = fr.curie.BiNoM.pathways.utils.Utils.getFirstAttribute(edges[n], "interaction");
 		    if(intatt!=null)
 		    	interaction = intatt.getValue();
 		    if(interaction.equals("UNKNOWN")){
 		    	AttDocument.Att atts[] = edges[n].getAttArray();
 		    	for(int kk=0;kk<atts.length;kk++){
 		    		AttDocument.Att att = atts[kk];
 		    		if(att.getName().toLowerCase().endsWith("edge_type"))
 		    			interaction = att.getValue();
 		    	}
 		    }
 		    // System.out.println("Edge requested source:"+getNodeWithName(netw, netw.getDefaultNodeTable(), "name", toId(edges[n].getSource()))+" target:"+getNodeWithName(netw, netw.getDefaultNodeTable(), "name", toId(edges[n].getTarget()))+" interaction:"+interaction);
 		     ed = netw.addEdge(getNodeWithName(netw, netw.getDefaultNodeTable(), "name", toId(edges[n].getSource())), 
 		    		getNodeWithName(netw, netw.getDefaultNodeTable(), "name", toId(edges[n].getTarget())), true);  	 		    	    
 		    		
 		    if(ed!=null){
 			    //System.out.println("Edge got "+ed.getIdentifier());
 			    netw.getDefaultEdgeTable().getRow(ed.getSUID()).set("name", toId(edges[n].getId()));
 			    netw.getDefaultEdgeTable().getRow(ed.getSUID()).set("interaction", interaction);
 			    
 			    edu.rpi.cs.xgmml.AttDocument.Att attrs[] = edge.getAttArray();
 			    for (int j = 0; j < attrs.length; j++) {
 			    	edu.rpi.cs.xgmml.AttDocument.Att attr = attrs[j];
 			    	log("\t" + attr.getLabel() + " " +
 			    			attr.getName() + " = " + attr.getValue());
 			    	if(attr.getValue()!=null){
 			    		try{
 			    			//create colum if not exist
 			    			if(attr.getType()==ObjectType.REAL){
 			    				try{
 			    					//insert value in the table
 									netw.getRow(ed).set(attr.getLabel(), attr.getValue());			    					
 			    				}catch(Exception e){
 			    					e.printStackTrace();
 			    				}
 			    			}
 			    			else {
 								netw.getRow(ed).set(attr.getLabel(), attr.getValue());
 			    			}
 			    		}catch(Exception e){
 			    		}
 			    	}
 				    else{
 				    	System.out.println("WARNING! Edge "+edges[n].getId()+" can not be created!");
 				    }
 				    //System.out.println("Number of edges = "+netw.getEdgeCount()+"\n");
 				}
 		    }
    		
    	}
    	netw.addEdge(ed);
	}
	adapter.getCyNetworkManager().addNetwork(netw);
	CyNetworkView networkView = adapter.getCyNetworkViewFactory().createNetworkView(netw);
	adapter.getVisualMappingManager().setVisualStyle(vizsty, networkView);
	adapter.getCyNetworkViewManager().addNetworkView(networkView);
	
	for (int n = 0; n < nodes.length; n++) {
	    edu.rpi.cs.xgmml.GraphicNode node = nodes[n];
	    CyNode nd = getNodeWithName(netw, netw.getDefaultNodeTable(), "name", toId(node.getId()));
	    if(nd != null){
		    netw.getDefaultNodeTable().getRow(nd.getSUID()).set("name", toId(node.getId()));	   		    			
		    
		    if (!applyLayout) {
		    	View<CyNode> nodeView = networkView.getNodeView(nd);
	    		    	
	            if(node.getGraphics()!=null){
	            	Double newX = node.getGraphics().getX();
	            	Double newY = node.getGraphics().getY();
	            	
	            	nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, newX);
	            	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, newY);
	            }
		    }
	    }
	}
	
	if (taskMonitor != null) {
	    taskMonitor.setStatusMessage("File imported (" + nodes.length +
				  " nodes, " + edges.length + " edges)");
	}
	else {
	    System.out.println("File imported (" + nodes.length +
				  " nodes, " + edges.length + " edges)");
	}
	
	if(biopax!=null)
		BioPAXSourceDB.getInstance().setBioPAX(netw,biopax);
	
	networkView.updateView();
	VisualStyle vs = Launcher.getAdapter().getVisualMappingManager().getVisualStyle(networkView);	
	Launcher.getAdapter().getVisualMappingManager().setVisualStyle(vs, networkView);
	vs.apply(networkView);
	networkView.fitContent();
	networkView.updateView();
	
	return netw;
    }

    static HashMap getListAttrMap(edu.rpi.cs.xgmml.AttDocument.Att attrs[]) {
	HashMap map = new HashMap();
	HashMap attrMap = new HashMap();
	for (int j = 0; j < attrs.length; j++) {
	    edu.rpi.cs.xgmml.AttDocument.Att attr = attrs[j];
	    if (map.get(attr.getLabel()) != null) {
		attrMap.put(attr.getLabel(), new Boolean(true));
	    }
	    else {
		map.put(attr.getLabel(), new Boolean(true));
	    }
	}
	return attrMap;
    }


    static void log(String s) {
    	//System.out.println(s);
    }

    private static void executeLayout(CyNetworkView networkView, TaskMonitor taskMonitor) {

	if (true) {
	    Iterator<CyNode> i = networkView.getModel().getNodeList().iterator();
	    
	    final int X_OFFSET = 100;
	    final int Y_OFFSET = 100;
	    final int MAX_COL_CNT = 20;

	    int row_num = 0;
	    int col_num = 0;

	    while (i.hasNext()) {
			CyNode node = (CyNode) i.next();
		    View<CyNode> nodeView = networkView.getNodeView(node);
		    double newY = row_num * Y_OFFSET;
		    double newX = col_num * X_OFFSET;
		    nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, newX);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, newY);    	
			
			col_num++;
			
			if (col_num == MAX_COL_CNT) {
			    col_num = 0;
			    row_num++;
			}
			
	    }
	}
	else {
	    /*
	    // move a network node to jumpstart the SpringEmbeddedLayouter2
	    java.util.Iterator i = networkView.getNetwork().nodesIterator();
	    if (i.hasNext()) {
		CyNode node = (CyNode) i.next();
		NodeView nodeView = networkView.getNodeView(node);
		double xPos = nodeView.getXPosition();
		double yPos = nodeView.getYPosition();
		nodeView.setXPosition(xPos + 2500);
		nodeView.setYPosition(yPos + 2500);
	    }

	    if (taskMonitor != null) {
		taskMonitor.setStatus("Executing Spring Embedded Layout");
	    }

	    // copy the current network view
	    // => very strange to me: why networkView is not passed as argument !?
	    // instead the 'current' (global variable ?) is used
	    final MutablePolyEdgeGraphLayout nativeGraph =
		GraphConverter.getGraphCopy(0.0d, false, false);

	    Task task = new SpringEmbeddedLayouter2(nativeGraph);
	    //Task task = new cytoscape.graph.layout.impl.ScaleLayouter(nativeGraph);
	    task.setTaskMonitor(taskMonitor);
	    task.run();
	    GraphConverter.updateCytoscapeLayout(nativeGraph);
	    */
	}
    }

    public static String toId(String id) {
	//return id.replaceAll("@", " \n");
    	return id;
    }
}
