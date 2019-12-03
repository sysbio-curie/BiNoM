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

import java.util.Vector;
import java.util.HashMap;

import fr.curie.BiNoM.pathways.utils.Utils;
import javax.swing.JOptionPane;

public class GraphDocumentFactory {

    private static GraphDocumentFactory instance;

    public static GraphDocumentFactory getInstance() {
	if (instance == null) {
	    instance = new GraphDocumentFactory();
	}
	return instance;
    }

    public GraphDocument createGraphDocument(CyNetwork cyNetwork) {
	GraphDocument gr = GraphDocument.Factory.newInstance();
	GraphicGraph grf = gr.addNewGraph();
        grf.setName(cyNetwork.getTitle());

	java.util.Iterator i = cyNetwork.nodesIterator();
	cytoscape.data.CyAttributes nodeAttrs = Cytoscape.getNodeAttributes();
	while (i.hasNext()) {
	    CyNode node = (CyNode)i.next();
	    String id = node.getIdentifier();

	    GraphicNode gr_node = grf.addNewNode();
	    gr_node.setId(id);

	    gr_node.setName(id);
	    gr_node.setLabel(id);

            GraphicsDocument.Graphics graphics = gr_node.addNewGraphics();

            CyNetworkView networkView = Cytoscape.getNetworkView(cyNetwork.getIdentifier());
            NodeView nv = networkView.getNodeView(node);

            graphics.setX(nv.getXPosition());
            graphics.setY(nv.getYPosition());

	    String attrNames[] = nodeAttrs.getAttributeNames();
	    for (int n = 0; n < attrNames.length; n++) {
	    	
	    try{
	    	String value = "";	
	    
	    if(nodeAttrs.getType(attrNames[n])==nodeAttrs.TYPE_STRING)
	    	 value = nodeAttrs.getStringAttribute(id, attrNames[n]);
	    if(nodeAttrs.getType(attrNames[n])==nodeAttrs.TYPE_FLOATING)
	    	if(value != null)
	    		value = ""+nodeAttrs.getDoubleAttribute(id, attrNames[n]);
	    if(nodeAttrs.getType(attrNames[n])==nodeAttrs.TYPE_INTEGER)
	    	if(value != null)
	    		value = ""+nodeAttrs.getIntegerAttribute(id, attrNames[n]);
	    
	    if(value != null)if(!value.trim().equals(""))if(!value.trim().equals("null"))
	    	Utils.addAttribute(gr_node, attrNames[n], attrNames[n], ""+value, ObjectType.STRING);
	    
	    }catch(Exception e){
	    	System.out.println("WARNING: "+attrNames[n]+" in "+id+" seems to be not of type string or double or integer! type="+nodeAttrs.getType(attrNames[n]));	    	
	    }
	    	
	    	
	    /*try{ //Attribute might be not of type string
		String value = nodeAttrs.getStringAttribute(id, attrNames[n]);
		//System.out.println("Node " + attrNames[n] + " -> " + value);
		if (value != null)if(!value.trim().equals(""))
		    Utils.addAttribute(gr_node, attrNames[n], attrNames[n], value, ObjectType.STRING);
	    }catch(Exception e){
	    	//System.out.println("WARNING: "+attrNames[n]+" in "+id+" seems to be not of type STRING");
	    	// Let us try now obtain it as double
	    	try{
	    		double d = nodeAttrs.getDoubleAttribute(id, attrNames[n]);
	    		Utils.addAttribute(gr_node, attrNames[n], attrNames[n], ""+d, ObjectType.STRING);
	    	}catch(Exception ee){
	    		
	    		try{
	    			int integ = nodeAttrs.getIntegerAttribute(id, attrNames[n]);
	    			Utils.addAttribute(gr_node, attrNames[n], attrNames[n], ""+integ, ObjectType.STRING);
	    		}catch(Exception eee){
	    			//eee.printStackTrace();
	    			System.out.println("WARNING: "+attrNames[n]+" in "+id+" seems to be not of type string or double or integer! type="+nodeAttrs.getType(attrNames[n]));
	    		}
	    	}
	    }*/
	    }
	}

	i = cyNetwork.edgesIterator();
	cytoscape.data.CyAttributes edgeAttrs = Cytoscape.getEdgeAttributes();
	while (i.hasNext()) {
	    CyEdge edge = (CyEdge)i.next();
	    String id = edge.getIdentifier();

	    GraphicEdge gr_edge = grf.addNewEdge();
	    gr_edge.setId(id);

	    gr_edge.setName(id);
	    gr_edge.setLabel(id);

	    gr_edge.setSource(edge.getSource().getIdentifier());
	    gr_edge.setTarget(edge.getTarget().getIdentifier());

	    String attrNames[] = edgeAttrs.getAttributeNames();
	    for (int n = 0; n < attrNames.length; n++) {
		String value = edgeAttrs.getStringAttribute(id, attrNames[n]);
		//System.out.println("Edge " + attrNames[n] + " -> " + value);
		if (value != null)
		    Utils.addAttribute(gr_edge, attrNames[n], attrNames[n], value, ObjectType.STRING);
	    }
	}

	edu.rpi.cs.xgmml.GraphicNode nodes[] = grf.getNodeArray();
	edu.rpi.cs.xgmml.GraphicEdge edges[] = grf.getEdgeArray();

	System.out.println("nodes: " + nodes.length);
	System.out.println("edges: " + edges.length);

	return gr;
    }
}

