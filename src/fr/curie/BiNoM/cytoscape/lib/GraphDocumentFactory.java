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

import java.util.Collection;
import java.util.Iterator;

import Main.Launcher;
import edu.rpi.cs.xgmml.*;
import fr.curie.BiNoM.pathways.utils.Utils;

import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyEdge;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;


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
		
		grf.setName(cyNetwork.getRow(cyNetwork).get(CyNetwork.NAME, String.class));
	
		Iterator<CyNode> iNodes = cyNetwork.getNodeList().iterator();
		Collection<CyNetworkView> networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(cyNetwork);
		Iterator<CyNetworkView> networkViewIterator = networkViews.iterator();
		CyNetworkView networkView =null;
		while(networkViewIterator.hasNext())
			networkView = networkViewIterator.next();
		
		while (iNodes.hasNext()) {
		    CyNode node = (CyNode)iNodes.next();
		    String id = cyNetwork.getRow(node).get(CyNetwork.NAME, String.class);
	
		    GraphicNode gr_node = grf.addNewNode();
		    gr_node.setId(id);
	
		    gr_node.setName(id);
		    gr_node.setLabel(id);
	
            GraphicsDocument.Graphics graphics = gr_node.addNewGraphics();

            
            View<CyNode> nv = networkView.getNodeView(node);
            
            graphics.setX(Double.parseDouble(nv.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION).toString()));
            graphics.setY(Double.parseDouble(nv.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION).toString()));
		    	
		    
            String value = null;		
            Iterator<CyColumn> columnsIterator = cyNetwork.getDefaultNodeTable().getColumns().iterator();
        	
		    while(columnsIterator.hasNext()){
		    	CyColumn col = columnsIterator.next();
		    	try{
				    try{
				    	value = cyNetwork.getRow(node).get(col.getName(), String.class);
				    }catch(Exception e){}
				    
				    if (value == null){
					    try{	
					    	value = cyNetwork.getRow(node).get(col.getName(), Double.class).toString();
					    }catch(Exception e){}
				    }
				    if (value == null){
					    try{	
					    	value = cyNetwork.getRow(node).get(col.getName(), Integer.class).toString();
					    }catch(Exception e){}
				    }
				    if (value == null){
					    try{	
					    	value = cyNetwork.getRow(node).get(col.getName(), Boolean.class).toString();
					    }catch(Exception e){}
			    	}
				    if(value != null)
				    	if(!value.trim().equals(""))
				    		if(!value.trim().equals("null"))
				    			Utils.addAttribute(gr_node, col.getName(), col.getName(), value, ObjectType.STRING);
				    
				    
			    }
		    	catch(Exception e){
			    	System.out.println("WARNING: "+col.getName()+" in "+id+" seems to be not of type string or double or integer!");	    	
		    	}	
		    }

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

	
		
		Iterator<CyEdge> iEdges = cyNetwork.getEdgeList().iterator();
		// cytoscape.data.CyAttributes edgeAttrs = Cytoscape.getEdgeAttributes();
		while (iEdges.hasNext()) {
		    CyEdge edge = (CyEdge)iEdges.next();
		    String id = cyNetwork.getRow(edge).get(CyNetwork.NAME, String.class);
		    
		    
		    GraphicEdge gr_edge = grf.addNewEdge();
		    gr_edge.setId(id);
	
		    gr_edge.setName(id);
		    gr_edge.setLabel(id);
		   
	
		    gr_edge.setSource(cyNetwork.getRow(edge.getSource()).get(CyNetwork.NAME, String.class));
		    gr_edge.setTarget(cyNetwork.getRow(edge.getTarget()).get(CyNetwork.NAME, String.class));
		    
			Iterator<CyColumn> columnsIterator = cyNetwork.getDefaultEdgeTable().getColumns().iterator();
    
		    while(columnsIterator.hasNext()){
		    	CyColumn col = columnsIterator.next();
		    
		    	String value = null;
		    	try{
			    	value = cyNetwork.getRow(edge).get(col.getName(), String.class);
			    }catch(Exception e){}
			    
		    	if (value == null){
				    try{	
				    	value = cyNetwork.getRow(edge).get(col.getName(), Double.class).toString();
				    }catch(Exception e){}
		    	}
		    	if (value == null){
				    try{	
				    	value = cyNetwork.getRow(edge).get(col.getName(), Integer.class).toString();
				    }catch(Exception e){}
		    	}
		    	if (value == null){
				    try{	
				    	value = cyNetwork.getRow(edge).get(col.getName(), Boolean.class).toString();
				    }catch(Exception e){}
		    	}
			    
			    
				if (value != null){
				    Utils.addAttribute(gr_edge, col.getName(), col.getName(), value, ObjectType.STRING);
				}
		    }
		}
	
		edu.rpi.cs.xgmml.GraphicNode nodes[] = grf.getNodeArray();
		edu.rpi.cs.xgmml.GraphicEdge edges[] = grf.getEdgeArray();
		
		return gr;
    }
}

