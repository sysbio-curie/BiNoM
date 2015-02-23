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

import cytoscape.task.TaskMonitor;

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
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;

import Main.Launcher;


public class NetworkFactory {
	
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

    public static CyNetwork createNetwork(String networkName,GraphDocument graphDocument,VisualStyle vizsty,boolean applyLayout, TaskMonitor taskMonitor) 
    		throws Exception {

    	return createNetworkPerform(networkName, graphDocument, vizsty, null, applyLayout, taskMonitor);
    }

    public static CyNetwork createNetwork(String networkName,GraphDocument graphDocument,VisualStyleDefinition vizsty_def,boolean applyLayout,
    		TaskMonitor taskMonitor) throws Exception {
    	return createNetworkPerform(networkName, graphDocument, null, vizsty_def, applyLayout, taskMonitor);
    }

    private static CyNetwork createNetworkPerform(String networkName,GraphDocument graphDocument,VisualStyle vizsty,VisualStyleDefinition vizsty_def,
    		boolean applyLayout,TaskMonitor taskMonitor) throws Exception {

	if (graphDocument == null)
	    return null;

	edu.rpi.cs.xgmml.GraphicGraph grf = graphDocument.getGraph();
	edu.rpi.cs.xgmml.GraphicNode nodes[] = grf.getNodeArray();
	edu.rpi.cs.xgmml.GraphicEdge edges[] = grf.getEdgeArray();

	if (nodes.length == 0) {
	    if (taskMonitor != null) {
		taskMonitor.setStatus("File imported (" + nodes.length +
				      " nodes, " + edges.length + " edges)");
		taskMonitor.setPercentCompleted(100);
	    }
	    return null;
	}

	CyAppAdapter adapter = Launcher.getAdapter();
	//VisualMappingManager vmm = Cytoscape.getDesktop().getVizMapManager();
	//CalculatorCatalog calculatorCatalog = vmm.getCalculatorCatalog();
	
	BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(adapter.getCyApplicationManager().getCurrentNetwork());
	
		
	if(biopax!=null)
		BioPAXSourceDB.getInstance().setBioPAX(adapter.getCyApplicationManager().getCurrentNetwork(),biopax);	
	
	
	//create a new network
	CyNetwork netw = adapter.getCyNetworkFactory().createNetwork();
	CyNetworkView networkView = null;

	netw.getRow(netw).set(CyNetwork.NAME, networkName);
	System.out.println("NODES " + nodes.length + " EDGES " + edges.length);
	log("NODES " + nodes.length + " EDGES " + edges.length);
		
	ArrayList nodesList = new ArrayList();
	//create all nodes
	for (int n = 0; n < nodes.length; n++) {
	    edu.rpi.cs.xgmml.GraphicNode node = nodes[n];
	    
	    //if(Launcher.findNodeWithName(netw, toId(node.getId())) == null){
	    if(!nodesList.contains(toId(node.getId()))){
	    	nodesList.add(toId(node.getId()));
		    CyNode nd= netw.addNode();
		    netw.getDefaultNodeTable().getRow(nd.getSUID()).set("name", toId(node.getId()));
		    //System.out.println(toId(node.getId()));
		    
		    log("nodes #" + n + " " + node.getId() + " " +	node.getLabel() + " " + node.getName());
				
		    //save nodes attributes
		    edu.rpi.cs.xgmml.AttDocument.Att attrs[] = node.getAttArray();
		    HashMap listAttrMap = getListAttrMap(attrs);
		
		    //System.out.println("ND: " + nd.getIdentifier() + " (" + attrs.length + ")");
		    for (int j = 0; j < attrs.length; j++) {
		    	
				edu.rpi.cs.xgmml.AttDocument.Att attr = attrs[j];
				
			
				String attrValue = attr.getValue();
				
				//System.out.println(attr.getLabel() + ": " + attr.getValue());					
				
				if (listAttrMap.get(attr.getLabel()) != null) {
				    // forse String o_attrValue = nodeAttrs.getStringAttribute(nd.getIdentifier(), attr.getLabel());
					// String o_attrValue = nodeAttrs.getStringAttribute(nd.SUID, attr.getLabel());
					String o_attrValue = netw.getRow(nd).get(attr.getLabel(), String.class);
				    if (o_attrValue != null && o_attrValue.length() > 0) {
				    	attrValue = o_attrValue + ATTR_SEP + attr.getValue();
				    	//System.out.println(o_attrValue + ATTR_SEP + attr.getValue());
				    }
				}
		
				//if (attr.getLabel().equals("BIOPAX_URI")) {
				//    System.out.println("   --> " + attr.getValue());
				//}						
				
				if(attrValue!=null){
					try{
						if(attr.getType()==ObjectType.REAL){
							try{
							//create a new column if necessary
							if(netw.getDefaultNodeTable().getColumn(attr.getLabel()) == null)
								netw.getDefaultNodeTable().createColumn(attr.getLabel(), Double.class, false);
							
							//insert value in the table
							netw.getRow(nd).set(attr.getLabel(), attr.getValue());
									
							// forse nodeAttrs.setAttribute(nd.getIdentifier(), attr.getLabel(),Double.parseDouble(attrValue));
							// nodeAttrs.setAttribute(nd.SUID, attr.getLabel(),Double.parseDouble(attrValue));
							}catch(Exception e){
								e.printStackTrace();
							}
						}
						else {
							//create string column if not exists
							if(netw.getDefaultNodeTable().getColumn(attr.getLabel()) == null)
								netw.getDefaultNodeTable().createColumn(attr.getLabel(), String.class, false);
							
							//insert value in the table
							netw.getRow(nd).set(attr.getLabel(), attr.getValue());
							
							//nodeAttrs.setAttribute(nd.getIdentifier(), attr.getLabel(),attrValue);
							// nodeAttrs.setAttribute(nd.SUID, attr.getLabel(),attrValue);
						}
					}catch(Exception e){
					}
				}		
				log("\t" + attr.getLabel() + " " + attr.getName() + " = " + attr.getValue());
		    }
	    }
	}				
	
			
	//tolto CyNetworkView networkView = Cytoscape.createNetworkView(netw);

	// edges part
	ArrayList edgesList = new ArrayList();
	for (int n = 0; n < edges.length; n++) {
	    edu.rpi.cs.xgmml.GraphicEdge edge = edges[n];
	    
	    //if(Launcher.findEdgeWithName(netw, toId(edge.getId())) == null){	 
	    if(!edgesList.contains(toId(edge.getId()))){
	    	edgesList.add(toId(edge.getId()));
		    log("edge #" + n + " " + edge.getId() + " " + edges[n].getLabel() + " " + edges[n].getSource() + " " + edges[n].getTarget());
		    
		    //System.out.println("Adding edge id="+edges[n].getId()+" label="+edges[n].getLabel()+" name="+edges[n].getName());
		    
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
		    
		    
		    //System.out.println("Edge requested "+"source:"+toId(edges[n].getSource())+" target:"+toId(edges[n].getTarget())+" interaction:"+interaction);
		    
		    /*CyEdge ed = Cytoscape.getCyEdge(Cytoscape.getCyNode(toId(edges[n].getSource())),
						    Cytoscape.getCyNode(toId(edges[n].getTarget())),
						    Semantics.INTERACTION,
						    interaction,
						    true);*/
		    // System.out.println("Edge requested source:"+getNodeWithName(netw, netw.getDefaultNodeTable(), "name", toId(edges[n].getSource()))+" target:"+getNodeWithName(netw, netw.getDefaultNodeTable(), "name", toId(edges[n].getTarget()))+" interaction:"+interaction);
		     CyEdge ed = netw.addEdge(getNodeWithName(netw, netw.getDefaultNodeTable(), "name", toId(edges[n].getSource())), 
		    		getNodeWithName(netw, netw.getDefaultNodeTable(), "name", toId(edges[n].getTarget())), true);  
		    	    
		    
		    //tolto CyEdge ed = Cytoscape.getCyEdge(toId(edges[n].getSource()), edges[n].getId(), toId(edges[n].getTarget()), interaction);    
		    //tolto CyEdge ed = netw.addEdge(netw.getNode(arg0), arg1, arg2)
		    		
		    if(ed!=null){
			    //System.out.println("Edge got "+ed.getIdentifier());
			    // modificato con sotto ed.setIdentifier(edges[n].getId());
			    netw.getDefaultEdgeTable().getRow(ed.getSUID()).set("name", toId(edges[n].getId()));
			    netw.getDefaultEdgeTable().getRow(ed.getSUID()).set("interaction", interaction);
			    //System.out.println("Setting id "+));
			    //System.out.println("ID changed "+ed.getIdentifier());
			    
			    edu.rpi.cs.xgmml.AttDocument.Att attrs[] = edge.getAttArray();
			    for (int j = 0; j < attrs.length; j++) {
			    	edu.rpi.cs.xgmml.AttDocument.Att attr = attrs[j];
			    	log("\t" + attr.getLabel() + " " +
			    			attr.getName() + " = " + attr.getValue());
			    	if(attr.getValue()!=null){
			    		try{
			    			//create colum if not exist
			    			if(attr.getType()==ObjectType.REAL){
			    				//create string column if not exists
								if(netw.getDefaultEdgeTable().getColumn(attr.getLabel()) == null)
									netw.getDefaultEdgeTable().createColumn(attr.getLabel(), Double.class, false);
			    				try{
			    					//insert value in the table
									netw.getRow(ed).set(attr.getLabel(), attr.getValue());
			    					
			    					// edgeAttrs.setAttribute(ed.SUID, attr.getLabel(),Double.parseDouble(attr.getValue()));
			    				}catch(Exception e){
			    					e.printStackTrace();
			    				}
			    			}
			    			else {
			    				//create string column if not exists
								if(netw.getDefaultEdgeTable().getColumn(attr.getLabel()) == null)
									netw.getDefaultEdgeTable().createColumn(attr.getLabel(), String.class, false);
								
								
			    				// edgeAttrs.setAttribute(ed.SUID, attr.getLabel(),attr.getValue());
								//insert value in the table
								netw.getRow(ed).set(attr.getLabel(), attr.getValue());
			    			}
			    		}catch(Exception e){
			    		}
			    		//edgeAttrs.setAttribute(ed.getIdentifier(), attr.getName(),attr.getValue());
			    	}
			    	// tolto netw.addEdge(ed);
				    else{
				    	System.out.println("WARNING! Edge "+edges[n].getId()+" can not be created!");
				    }
				    //System.out.println("Number of edges = "+netw.getEdgeCount()+"\n");
				}
		    }
		}
	}
	
	nodesList=null;
	edgesList=null;
	
	networkView = adapter.getCyNetworkViewFactory().createNetworkView(netw);
	
	if(netw.getEdgeCount()!=graphDocument.getGraph().getEdgeArray().length){
		System.out.println("ERROR: The number of edges in XGMML ("+graphDocument.getGraph().getEdgeArray().length+") is not equal to the Cytoscape ("+netw.getEdgeCount()+")");
	}

	/*
	  VisualStyle vizsty = VisualStyleFactory.getInstance().create(BioPAXVisualStyleDefinition.getInstance());
	  networkView.applyVizmapper(vizsty);
	*/

	if (vizsty_def != null){
	    VisualStyleFactory.getInstance().apply(vizsty_def, networkView);
	}
	 else if (vizsty != null)
		adapter.getVisualMappingManager().setVisualStyle(vizsty, networkView);
	    //sostituito con sopra adapter.getCyNetworkViewManager().a networkView.applyVizmapper(vizsty);

	//apply a layout
	if (applyLayout)
	    executeLayout(networkView, taskMonitor);
	else{
		for (int n = 0; n < nodes.length; n++) {
		    edu.rpi.cs.xgmml.GraphicNode node = nodes[n];
		    CyNode nd = getNodeWithName(netw, netw.getDefaultNodeTable(), "name", toId(node.getId()));
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

//	networkView.redrawGraph(true, false);
//	
	if (taskMonitor != null) {
	    taskMonitor.setStatus("File imported (" + nodes.length +
				  " nodes, " + edges.length + " edges)");
	}
	else {
	    System.out.println("File imported (" + nodes.length +
				  " nodes, " + edges.length + " edges)");
	}
//
//	SwingUtilities.invokeLater(new Runnable() {
//		public void run() {
//		    DingNetworkView view = (DingNetworkView) Cytoscape
//			.getCurrentNetworkView();
//		    view.setGraphLOD(new CyGraphLOD());
//		    ((DGraphView) view).fitContent();
//		}
//	    });
//
//
//	if (taskMonitor != null) {
//	    taskMonitor.setPercentCompleted(100);
//	}
	

	adapter.getCyNetworkManager().addNetwork(netw);
	adapter.getCyNetworkViewManager().addNetworkView(networkView);
	networkView.fitContent();
	

	
	
//     java.util.List<CyNode> nodess = netw.getNodeList();
//
//	   // Double the border width for all the selected nodes		
//	   Iterator<CyNode> it = nodess.iterator();		
//	   while (it.hasNext()){
//		CyNode node = it.next();
//		View<CyNode> nodeView = networkView.getNodeView(node);
//
//		double newLineWidth = nodeView.getVisualProperty(BasicVisualLexicon.NODE_BORDER_WIDTH)*12;
//		//nodeView.setVisualProperty(BasicVisualLexicon.NODE_BORDER_WIDTH, lineWidth);
//		nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH, newLineWidth);
//	}

	
	// Apply the change to the view
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
			//nodeView. setXPosition(col_num * X_OFFSET);
			//nodeView.setYPosition(row_num * Y_OFFSET);
			
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

//    public static CyNetwork getNullEmptyNetwork() {
//    	return NetworkUtils.clearNetwork(Launcher.getAdapter().getCyNetworkFactory().);
//    	return NetworkUtils.clearNetwork(Cytoscape.getNullNetwork());
//    }
}
