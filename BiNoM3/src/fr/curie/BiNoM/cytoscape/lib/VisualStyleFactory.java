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

import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;

import Main.Launcher;
import cytoscape.Cytoscape;
import cytoscape.visual.*;
import cytoscape.visual.calculators.*;
import cytoscape.visual.mappings.ObjectMapping;
import cytoscape.data.CyAttributes;

import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyEdge;

import fr.curie.BiNoM.cytoscape.biopax.BioPAXVisualStyleDefinition;
import giny.view.NodeView;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;
import org.cytoscape.view.vizmap.mappings.DiscreteMapping;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;



public class VisualStyleFactory {
	
	CyAppAdapter adapter;

    private CalculatorCatalog catalog;

    private static VisualStyleFactory instance;

    public static VisualStyleFactory getInstance() {
		if (instance == null)
		    instance = new VisualStyleFactory();
		return instance;
    }

    VisualStyleFactory() {
    	adapter = Launcher.getAdapter();
    }

    public void apply(VisualStyleDefinition vizsty_def, CyNetworkView networkView) {

		applyVisualStyle(vizsty_def, networkView);		
		//applyImageMapping(vizsty_def, networkView);
    }

    private void applyVisualStyle(VisualStyleDefinition vizsty_def,
				 CyNetworkView networkView) {
		//tolto networkView. applyVizmapper(create(vizsty_def));
		adapter.getVisualMappingManager().setVisualStyle(create(vizsty_def), networkView);
    }

    private void applyImageMapping(VisualStyleDefinition vizsty_def, CyNetworkView networkView) {
		Vector v = vizsty_def.getNodeImageMapping();
		if (v.size() == 0)
		    return;
	
		CyAttributes nodeAttrs = Cytoscape.getNodeAttributes();
		String node_attr = vizsty_def.getNodeAttribute();
		
		Iterator<CyNode> i = networkView.getModel().getNodeList().iterator();
	
		while (i.hasNext()) {
		    CyNode node = (CyNode)i.next();
		    View<CyNode> nodeView = networkView.getNodeView(node);
		    String value = nodeAttrs.getStringAttribute
			(node.SUID, node_attr);
	
		    for (int n = 0; n < v.size(); n++) {
				VisualStyleDefinition.ObjectMapping m =
				    (VisualStyleDefinition.ObjectMapping)v.get(n);
		
				if (value.equals(m.getAttributeValue())) {
				    VisualStyleDefinition.ImageDefinition img_def =
					(VisualStyleDefinition.ImageDefinition)
					m.getMappingValue();
		
				    try {
					java.awt.image.BufferedImage img =
					    javax.imageio.ImageIO.read(new java.io.File(img_def.img));
		
					int width = (int)(img.getWidth() * img_def.scale);
					int height = (int)(img.getHeight() * img_def.scale);
					java.awt.geom.Rectangle2D.Double rect = new
				    java.awt.geom.Rectangle2D.Double
				    (-width/2, -height/2, width, height);
					ding.view.DNodeView dnv = (ding.view.DNodeView)nodeView;
					java.awt.Paint paint =
					    new java.awt.TexturePaint(img, null);
					//dnv.addCustomGraphic(rect, paint, 0);
		
				    }
				    catch(Exception e) {
					e.printStackTrace();
				    }
				    break;
				}
		    }
		}
    }

    public VisualStyle create(VisualStyleDefinition vizsty_def) {	
	
    	Iterator it = Launcher.getAdapter().getVisualMappingManager().getAllVisualStyles().iterator();
    	VisualStyle vs;
    	while(it.hasNext()){
    		vs = (VisualStyle)it.next();
    		if(vs.getTitle().compareTo(vizsty_def.getName()) == 0)
    			return vs;
    	}

    	
    	// If the style already existed, remove it first
		it = adapter.getVisualMappingManager().getAllVisualStyles().iterator();
		while (it.hasNext()){
			VisualStyle curVS = (VisualStyle)it.next();
			if (curVS.getTitle().equalsIgnoreCase("Sample Visual Style"))
			{
				adapter.getVisualMappingManager().removeVisualStyle(curVS);
				break;
			}
		}

		VisualStyle vizsty = adapter.getVisualStyleFactory().createVisualStyle(vizsty_def.getName());
				
		adapter.getVisualMappingManager().addVisualStyle(vizsty);
			
		// nodes
		createNodeShape(vizsty_def, vizsty);
		createNodeSize(vizsty_def, vizsty);
		createNodeLabel(vizsty_def, vizsty);
		createNodeColor(vizsty_def, vizsty);
		createNodeBorderColor(vizsty_def, vizsty);
		createNodeBorderLineStyle(vizsty_def, vizsty);
					
			
		// edges
		createEdgeSourceArrow(vizsty_def, vizsty);
		createEdgeTargetArrow(vizsty_def, vizsty);
		createEdgeLineType(vizsty_def, vizsty);
		createEdgeLineColor(vizsty_def, vizsty);	
		
		//colour view
		vizsty.setDefaultValue(BasicVisualLexicon.NETWORK_BACKGROUND_PAINT, new Color(255,255,204));
		return vizsty;
    }
    
    
  private void createNodeShape(VisualStyleDefinition vizsty_def, VisualStyle vs) {
	  
	  
	  DiscreteMapping discreteMapping = (DiscreteMapping) adapter.getVisualMappingFunctionDiscreteFactory().
			  createVisualMappingFunction(vizsty_def.getNodeAttribute(), String.class, BasicVisualLexicon.NODE_SHAPE);
	  

		Vector v = vizsty_def.getNodeShapeMapping();
		for (int n = 0; n < v.size(); n++) {
		    VisualStyleDefinition.ObjectMapping m = (VisualStyleDefinition.ObjectMapping)v.get(n);
		    // System.out.println(m.getAttributeValue()+"   "+m.getMappingValue());
		    discreteMapping.putMapValue(m.getAttributeValue(), m.getMappingValue());
		}
	    
	    vs.addVisualMappingFunction(discreteMapping);	    
  }

  
  
    private void createNodeSize(VisualStyleDefinition vizsty_def, VisualStyle vs) {

  	  DiscreteMapping discreteMapping = (DiscreteMapping) adapter.getVisualMappingFunctionDiscreteFactory().createVisualMappingFunction(vizsty_def.getNodeAttribute(), String.class, BasicVisualLexicon.NODE_SIZE);

		Vector v = vizsty_def.getNodeSizeMapping();
		for (int n = 0; n < v.size(); n++) {
		    VisualStyleDefinition.ObjectMapping m =(VisualStyleDefinition.ObjectMapping)v.get(n);
		    discreteMapping.putMapValue(m.getAttributeValue(),m.getMappingValue());
		}
		
		vs.addVisualMappingFunction(discreteMapping);
    }
    

    private void createNodeLabel(VisualStyleDefinition vizsty_def, VisualStyle vs) {

    	PassthroughMapping passThroughMapping = (PassthroughMapping) adapter.getVisualMappingFunctionPassthroughFactory().createVisualMappingFunction("name", String.class, BasicVisualLexicon.NODE_LABEL);        
        
        vs.addVisualMappingFunction(passThroughMapping);
        
    }

    private void createNodeColor(VisualStyleDefinition vizsty_def, VisualStyle vs) {

	  DiscreteMapping discreteMapping = (DiscreteMapping) adapter.getVisualMappingFunctionDiscreteFactory().createVisualMappingFunction(vizsty_def.getNodeAttribute(), String.class, BasicVisualLexicon.NODE_FILL_COLOR);

		Vector v = vizsty_def.getNodeColorMapping();
		for (int n = 0; n < v.size(); n++) {
		    VisualStyleDefinition.ObjectMapping m =
			(VisualStyleDefinition.ObjectMapping)v.get(n);
		    discreteMapping.putMapValue(m.getAttributeValue(), m.getMappingValue());
		}
		
		vs.addVisualMappingFunction(discreteMapping);
    }

    private void createNodeBorderColor(VisualStyleDefinition vizsty_def, VisualStyle vs) {

  	  DiscreteMapping discreteMapping = (DiscreteMapping) adapter.getVisualMappingFunctionDiscreteFactory().createVisualMappingFunction(vizsty_def.getNodeAttribute(), String.class, BasicVisualLexicon.NODE_BORDER_PAINT);

		Vector v = vizsty_def.getNodeBorderColorMapping();
		for (int n = 0; n < v.size(); n++) {
		    VisualStyleDefinition.ObjectMapping m =
			(VisualStyleDefinition.ObjectMapping)v.get(n);
		    discreteMapping.putMapValue(m.getAttributeValue(),
						m.getMappingValue());
		}
		
		vs.addVisualMappingFunction(discreteMapping);
    }

    private void createNodeBorderLineStyle(VisualStyleDefinition vizsty_def, VisualStyle vs) {

    	DiscreteMapping discreteMapping = (DiscreteMapping) adapter.getVisualMappingFunctionDiscreteFactory().createVisualMappingFunction(vizsty_def.getNodeAttribute(), String.class, BasicVisualLexicon.NODE_BORDER_LINE_TYPE);


		Vector v = vizsty_def.getNodeBorderLineStyleMapping();
		for (int n = 0; n < v.size(); n++) {
		    VisualStyleDefinition.ObjectMapping m =
			(VisualStyleDefinition.ObjectMapping)v.get(n);
		    discreteMapping.putMapValue(m.getAttributeValue(),
						m.getMappingValue());
		}
		
		vs.addVisualMappingFunction(discreteMapping);
	    
		DiscreteMapping discreteMapping1 = (DiscreteMapping) adapter.getVisualMappingFunctionDiscreteFactory().createVisualMappingFunction(vizsty_def.getNodeAttribute(), String.class, BasicVisualLexicon.NODE_BORDER_WIDTH);

	
		v = vizsty_def.getNodeBorderLineWidthMapping();
	    for (int n = 0; n < v.size(); n++) {
	    VisualStyleDefinition.ObjectMapping m =
		(VisualStyleDefinition.ObjectMapping)v.get(n);
	    discreteMapping1.putMapValue(m.getAttributeValue(),
					m.getMappingValue());
	    }
	    
	    vs.addVisualMappingFunction(discreteMapping1);
        }

    
 
    private void createEdgeSourceArrow(VisualStyleDefinition vizsty_def, VisualStyle vs) {

    	DiscreteMapping discreteMappingShape = (DiscreteMapping) adapter.getVisualMappingFunctionDiscreteFactory().createVisualMappingFunction(vizsty_def.getEdgeAttribute(), String.class, BasicVisualLexicon.EDGE_SOURCE_ARROW_SHAPE);
    	//DiscreteMapping discreteMappingColor = (DiscreteMapping) adapter.getVisualMappingFunctionDiscreteFactory().createVisualMappingFunction("BIOPAX_EDGE_TYPE", String.class, BasicVisualLexicon.);

		Vector v = vizsty_def.getEdgeSourceArrowMapping();
		for (int n = 0; n < v.size(); n++) {
		    VisualStyleDefinition.ObjectMapping m =
			(VisualStyleDefinition.ObjectMapping)v.get(n);
		    if(m.getMappingValue() != null)
		    	discreteMappingShape.putMapValue(m.getAttributeValue(),
					((BioPAXVisualStyleDefinition.Arrow)(m.getMappingValue())).getShape());
		}
		
		vs.addVisualMappingFunction(discreteMappingShape);
    }

    private void createEdgeTargetArrow(VisualStyleDefinition vizsty_def, VisualStyle vs) {
    	DiscreteMapping discreteMappingShape = (DiscreteMapping) adapter.getVisualMappingFunctionDiscreteFactory().createVisualMappingFunction(vizsty_def.getEdgeAttribute(), String.class, BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE);
    	//DiscreteMapping discreteMappingColor = (DiscreteMapping) adapter.getVisualMappingFunctionDiscreteFactory().createVisualMappingFunction("BIOPAX_EDGE_TYPE", String.class, CyEdge.class.getv);   

		Vector v = vizsty_def.getEdgeTargetArrowMapping();
		for (int n = 0; n < v.size(); n++) {
		    VisualStyleDefinition.ObjectMapping m = (VisualStyleDefinition.ObjectMapping)v.get(n);
		    if(m.getMappingValue() != null)
		    	discreteMappingShape.putMapValue(m.getAttributeValue(), ((BioPAXVisualStyleDefinition.Arrow)(m.getMappingValue())).getShape());
		    
		    //if(m.getMappingValue() != null)
		    //System.out.println(m.getAttributeValue() + "  " + ((BioPAXVisualStyleDefinition.Arrow)(m.getMappingValue())).getShape());
		    //discreteMappingColor.putMapValue(m.getAttributeValue(),
			//		((Arrow)(m.getMappingValue())).getColor());
		}
		
		vs.addVisualMappingFunction(discreteMappingShape);

		//BasicCalculator bc = new BasicCalculator(vizsty_def.getName() + " Edge Target Arrow Shape",discreteMappingShape,VisualPropertyType.EDGE_TGTARROW_SHAPE);
	    //eac.setCalculator(bc);
		//bc = new BasicCalculator(vizsty_def.getName() + " Edge Target Arrow Color",discreteMappingColor,VisualPropertyType.EDGE_TGTARROW_COLOR);
	    //eac.setCalculator(bc);
	    
	    //eac.getDefaultAppearance().set(VisualPropertyType.EDGE_TGTARROW_SHAPE, vizsty_def.defaultEdgeTargetArrow.getShape());
	    //eac.getDefaultAppearance().set(VisualPropertyType.EDGE_TGTARROW_COLOR, vizsty_def.defaultEdgeTargetArrow.getColor());    
    }

    private void createEdgeLineType(VisualStyleDefinition vizsty_def, VisualStyle vs) {

    	DiscreteMapping discreteMapping = (DiscreteMapping) adapter.getVisualMappingFunctionDiscreteFactory().createVisualMappingFunction(vizsty_def.getEdgeAttribute(), String.class, BasicVisualLexicon.EDGE_LINE_TYPE);


		Vector v = vizsty_def.getEdgeLineTypeMapping();
		for (int n = 0; n < v.size(); n++) {
		    VisualStyleDefinition.ObjectMapping m =
			(VisualStyleDefinition.ObjectMapping)v.get(n);
		    discreteMapping.putMapValue(m.getAttributeValue(),
						m.getMappingValue());
		    //System.out.println(m.getAttributeValue()+"->"+m.getMappingValue());
		}
		
		vs.addVisualMappingFunction(discreteMapping);
    }

    private void createEdgeLineColor(VisualStyleDefinition vizsty_def, VisualStyle vs) {

		DiscreteMapping discreteMapping = (DiscreteMapping) adapter.getVisualMappingFunctionDiscreteFactory().createVisualMappingFunction(vizsty_def.getEdgeAttribute(), String.class, BasicVisualLexicon.EDGE_UNSELECTED_PAINT);
		
		Vector v = vizsty_def.getEdgeLineColorMapping();
		for (int n = 0; n < v.size(); n++) {
		   VisualStyleDefinition.ObjectMapping m = (VisualStyleDefinition.ObjectMapping)v.get(n);
		   discreteMapping.putMapValue(m.getAttributeValue(), m.getMappingValue());
		}
		
		vs.addVisualMappingFunction(discreteMapping);
	}
}

