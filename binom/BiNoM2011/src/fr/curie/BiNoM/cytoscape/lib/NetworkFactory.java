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

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import cytoscape.visual.VisualMappingManager;
import cytoscape.visual.VisualStyle;
import java.io.*;
import cytoscape.task.ui.JTaskConfig;

import javax.swing.SwingUtilities;
import ding.view.DGraphView;
import edu.rpi.cs.xgmml.*;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.CyEdge;
import cytoscape.view.CyNetworkView;
import cytoscape.data.Semantics;
import cytoscape.visual.*;

import giny.view.NodeView;
import cytoscape.ding.CyGraphLOD;
import cytoscape.ding.DingNetworkView;

import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.*;

//import legacy.util.GraphConverter;
//import legacy.layout.impl.SpringEmbeddedLayouter2;
//import legacy.layout.algorithm.MutablePolyEdgeGraphLayout;

import java.util.Vector;
import java.util.HashMap;

public class NetworkFactory {

    public static final String ATTR_SEP = "@@@";

    public static CyNetwork createNetwork
	(String networkName,
	 GraphDocument graphDocument,
	 VisualStyle vizsty,
	 boolean applyLayout,
	 TaskMonitor taskMonitor) throws Exception {

	return createNetworkPerform(networkName, graphDocument, vizsty, null, applyLayout, taskMonitor);
    }

    public static CyNetwork createNetwork
	(String networkName,
	 GraphDocument graphDocument,
	 VisualStyleDefinition vizsty_def,
	 boolean applyLayout,
	 TaskMonitor taskMonitor) throws Exception {
	return createNetworkPerform(networkName, graphDocument, null, vizsty_def, applyLayout, taskMonitor);
    }

    private static CyNetwork createNetworkPerform
	(String networkName,
	 GraphDocument graphDocument,
	 VisualStyle vizsty,
	 VisualStyleDefinition vizsty_def,
	 boolean applyLayout,
	 TaskMonitor taskMonitor) throws Exception {

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

	cytoscape.data.CyAttributes nodeAttrs = Cytoscape.getNodeAttributes();
	cytoscape.data.CyAttributes edgeAttrs = Cytoscape.getEdgeAttributes();
	//VisualMappingManager vmm = Cytoscape.getDesktop().getVizMapManager();
	//CalculatorCatalog calculatorCatalog = vmm.getCalculatorCatalog();
	
	BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(Cytoscape.getCurrentNetwork());
	
	CyNetwork netw = Cytoscape.createNetwork(networkName);
	
	if(biopax!=null)
		BioPAXSourceDB.getInstance().setBioPAX(Cytoscape.getCurrentNetwork(),biopax);	
	
	CyNetworkView networkView = Cytoscape.createNetworkView(netw);


	log("NODES " + nodes.length + " EDGES " + edges.length);

	for (int n = 0; n < nodes.length; n++) {
	    edu.rpi.cs.xgmml.GraphicNode node = nodes[n];
	    CyNode nd = Cytoscape.getCyNode(toId(node.getId()), true);

	    netw.addNode(nd);

	    if (!applyLayout) {
		NodeView nodeView = networkView.getNodeView(nd);
                if(node.getGraphics()!=null){
                  nodeView.setXPosition(node.getGraphics().getX());
                  nodeView.setYPosition(node.getGraphics().getY());
                }
	    }

	    log("nodes #" + n + " " + node.getId() + " " +
		node.getLabel() + " " + node.getName());

	    edu.rpi.cs.xgmml.AttDocument.Att attrs[] = node.getAttArray();
	    HashMap listAttrMap = getListAttrMap(attrs);

	    //System.out.println("ND: " + nd.getIdentifier() + " (" + attrs.length + ")");
	    for (int j = 0; j < attrs.length; j++) {
		edu.rpi.cs.xgmml.AttDocument.Att attr = attrs[j];

		String attrValue = attr.getValue();
		//System.out.println(attr.getLabel() + ": " + attr.getValue());
		if (listAttrMap.get(attr.getLabel()) != null) {
		    String o_attrValue = nodeAttrs.getStringAttribute(nd.getIdentifier(), attr.getLabel());
		    if (o_attrValue != null && o_attrValue.length() > 0) {
			attrValue = o_attrValue + ATTR_SEP + attr.getValue();
		    }
		}

		//if (attr.getLabel().equals("BIOPAX_URI")) {
		//    System.out.println("   --> " + attr.getValue());
		//}
		
		if(attrValue!=null){
			try{
			if(attr.getType()==ObjectType.REAL){
				try{
				nodeAttrs.setAttribute(nd.getIdentifier(), attr.getLabel(),Double.parseDouble(attrValue));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			else 
				nodeAttrs.setAttribute(nd.getIdentifier(), attr.getLabel(),attrValue);
			}catch(Exception e){
			}
		}

		log("\t" + attr.getLabel() + " " +
		    attr.getName() + " = " + attr.getValue());
	    }
	}

	for (int n = 0; n < edges.length; n++) {
	    edu.rpi.cs.xgmml.GraphicEdge edge = edges[n];
	    log("edge #" + n + " " + edge.getId() + " " +
		edges[n].getLabel() + " " +
		edges[n].getSource() + " " + edges[n].getTarget());
	    
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
	    CyEdge ed = Cytoscape.getCyEdge(toId(edges[n].getSource()), 
	    		edges[n].getId(), 
	    		toId(edges[n].getTarget()), 
	    		interaction);
	    
	    if(ed!=null){
	    //System.out.println("Edge got "+ed.getIdentifier());
	    ed.setIdentifier(edges[n].getId());
	    //System.out.println("Setting id "+));
	    //System.out.println("ID changed "+ed.getIdentifier());
	    
	    edu.rpi.cs.xgmml.AttDocument.Att attrs[] = edge.getAttArray();
	    for (int j = 0; j < attrs.length; j++) {
	    	edu.rpi.cs.xgmml.AttDocument.Att attr = attrs[j];
	    	log("\t" + attr.getLabel() + " " +
	    			attr.getName() + " = " + attr.getValue());
	    	if(attr.getValue()!=null){
	    		try{
	    			if(attr.getType()==ObjectType.REAL){
	    				try{
	    					edgeAttrs.setAttribute(ed.getIdentifier(), attr.getLabel(),Double.parseDouble(attr.getValue()));
	    				}catch(Exception e){
	    					e.printStackTrace();
	    				}
	    			}
	    			else 
	    				edgeAttrs.setAttribute(ed.getIdentifier(), attr.getLabel(),attr.getValue());
	    		}catch(Exception e){
	    		}
	    		//edgeAttrs.setAttribute(ed.getIdentifier(), attr.getName(),attr.getValue());
	    	}
	    }

	    netw.addEdge(ed);
	    }else{
	    	System.out.println("WARNING! Edge "+edges[n].getId()+" can not be created!");
	    }
	    //System.out.println("Number of edges = "+netw.getEdgeCount()+"\n");
	}
	
	if(netw.getEdgeCount()!=graphDocument.getGraph().getEdgeArray().length){
		System.out.println("ERROR: The number of edges in XGMML ("+graphDocument.getGraph().getEdgeArray().length+") is not equal to the Cytoscape ("+netw.getEdgeCount()+")");
	}

	/*
	  VisualStyle vizsty = VisualStyleFactory.getInstance().create(BioPAXVisualStyleDefinition.getInstance());
	  networkView.applyVizmapper(vizsty);
	*/

	if (vizsty_def != null)
	    VisualStyleFactory.getInstance().apply(vizsty_def, networkView);
	else if (vizsty != null)
	    networkView.applyVizmapper(vizsty);

	if (applyLayout)
	    executeLayout(networkView, taskMonitor);

	networkView.redrawGraph(true, false);
	
	if (taskMonitor != null) {
	    taskMonitor.setStatus("File imported (" + nodes.length +
				  " nodes, " + edges.length + " edges)");
	}
	else {
	    System.out.println("File imported (" + nodes.length +
				  " nodes, " + edges.length + " edges)");
	}

	SwingUtilities.invokeLater(new Runnable() {
		public void run() {
		    DingNetworkView view = (DingNetworkView) Cytoscape
			.getCurrentNetworkView();
		    view.setGraphLOD(new CyGraphLOD());
		    ((DGraphView) view).fitContent();
		}
	    });


	if (taskMonitor != null) {
	    taskMonitor.setPercentCompleted(100);
	}

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
	    java.util.Iterator i = networkView.getNetwork().nodesIterator();
	    
	    final int X_OFFSET = 100;
	    final int Y_OFFSET = 100;
	    final int MAX_COL_CNT = 20;

	    int row_num = 0;
	    int col_num = 0;

	    while (i.hasNext()) {
		CyNode node = (CyNode) i.next();
		NodeView nodeView = networkView.getNodeView(node);
		
		nodeView.setXPosition(col_num * X_OFFSET);
		nodeView.setYPosition(row_num * Y_OFFSET);
		
		col_num++;
		
		if (col_num == MAX_COL_CNT) {
		    row_num++;
		    col_num = 0;
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

    public static CyNetwork getNullEmptyNetwork() {
	return NetworkUtils.clearNetwork(Cytoscape.getNullNetwork());
    }
}
