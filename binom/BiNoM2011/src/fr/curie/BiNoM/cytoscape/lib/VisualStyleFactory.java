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
import java.util.Vector;
import cytoscape.Cytoscape;
import cytoscape.visual.*;
import cytoscape.visual.calculators.*;
import cytoscape.visual.mappings.DiscreteMapping;
import cytoscape.visual.mappings.ObjectMapping;
import cytoscape.visual.mappings.PassThroughMapping;

import cytoscape.data.CyAttributes;
import cytoscape.CyNode;
import cytoscape.view.CyNetworkView;
import giny.view.NodeView;

public class VisualStyleFactory {

    private CalculatorCatalog catalog;

    private static VisualStyleFactory instance;

    public static VisualStyleFactory getInstance() {
	if (instance == null)
	    instance = new VisualStyleFactory();
	return instance;
    }

    VisualStyleFactory() {
    	// Changed for Cytoscape 2.6.0
        //catalog = Cytoscape.getDesktop().getVizMapManager().
	    //getCalculatorCatalog();
    	catalog = Cytoscape.getVisualMappingManager().getCalculatorCatalog();
    }

    public void apply(VisualStyleDefinition vizsty_def,
		      CyNetworkView networkView) {

	applyVisualStyle(vizsty_def, networkView);
	applyImageMapping(vizsty_def, networkView);
    }

    private void applyVisualStyle(VisualStyleDefinition vizsty_def,
				 CyNetworkView networkView) {
	networkView.applyVizmapper(create(vizsty_def));
    }

    private void applyImageMapping(VisualStyleDefinition vizsty_def,
				  CyNetworkView networkView) {
	Vector v = vizsty_def.getNodeImageMapping();
	if (v.size() == 0)
	    return;

	CyAttributes nodeAttrs = Cytoscape.getNodeAttributes();
	String node_attr = vizsty_def.getNodeAttribute();

	java.util.Iterator i = networkView.getNetwork().nodesIterator();

	while (i.hasNext()) {
	    CyNode node = (CyNode)i.next();
	    NodeView nodeView = networkView.getNodeView(node);
	    String value = nodeAttrs.getStringAttribute
		(node.getIdentifier(), node_attr);

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
        VisualStyle vizsty = catalog.getVisualStyle(vizsty_def.getName());
	if (vizsty != null)
	    return vizsty;

	vizsty = new VisualStyle(vizsty_def.getName());

	// nodes
	NodeAppearanceCalculator nac = new NodeAppearanceCalculator();
	createNodeShape(vizsty_def, nac);
	createNodeSize(vizsty_def, nac);
	createNodeLabel(vizsty_def, nac);
	createNodeColor(vizsty_def, nac);
	createNodeBorderColor(vizsty_def, nac);
	createNodeBorderLineStyle(vizsty_def, nac);
	vizsty.setNodeAppearanceCalculator(nac);

	// edges
	EdgeAppearanceCalculator eac = new EdgeAppearanceCalculator();
	createEdgeSourceArrow(vizsty_def, eac);
	createEdgeTargetArrow(vizsty_def, eac);
	createEdgeLineType(vizsty_def, eac);
	createEdgeLineColor(vizsty_def, eac);
	vizsty.setEdgeAppearanceCalculator(eac);

	// global
	GlobalAppearanceCalculator gac = new GlobalAppearanceCalculator();
	gac.setDefaultBackgroundColor(new Color(255,255,204));

	vizsty.setGlobalAppearanceCalculator(gac);

	catalog.addVisualStyle(vizsty);
	return vizsty;
    }

    private void createNodeShape(VisualStyleDefinition vizsty_def,
				 NodeAppearanceCalculator nac) {

        DiscreteMapping discreteMapping = new DiscreteMapping
	    (vizsty_def.getDefaultNodeShape(),
	     vizsty_def.getNodeAttribute(),
	     ObjectMapping.NODE_MAPPING);

	Vector v = vizsty_def.getNodeShapeMapping();
	for (int n = 0; n < v.size(); n++) {
	    VisualStyleDefinition.ObjectMapping m =
		(VisualStyleDefinition.ObjectMapping)v.get(n);
	    discreteMapping.putMapValue(m.getAttributeValue(),
					m.getMappingValue());
	}

	
	    BasicCalculator bc = new BasicCalculator(vizsty_def.getName() + " Node Shape",discreteMapping,VisualPropertyType.NODE_SHAPE);
	    nac.setCalculator(bc);
	    nac.getDefaultAppearance().set(VisualPropertyType.NODE_SHAPE, vizsty_def.getDefaultNodeShape());
	    
	    // Cytoscape 2.5
		//nac.setCalculator(new );
		//nac.setNodeShapeCalculator
	    //(new GenericNodeShapeCalculator(vizsty_def.getName() + " Node Shape", discreteMapping));
	    // hack
	    //nac.setDefaultNodeShape(vizsty_def.getDefaultNodeShape().byteValue());
	    
    }

    private void createNodeSize(VisualStyleDefinition vizsty_def,
				NodeAppearanceCalculator nac) {

        DiscreteMapping discreteMapping = new DiscreteMapping
	    (vizsty_def.getDefaultNodeSize(),
	     vizsty_def.getNodeAttribute(),
	     ObjectMapping.NODE_MAPPING);

	Vector v = vizsty_def.getNodeSizeMapping();
	for (int n = 0; n < v.size(); n++) {
	    VisualStyleDefinition.ObjectMapping m =
		(VisualStyleDefinition.ObjectMapping)v.get(n);
	    discreteMapping.putMapValue(m.getAttributeValue(),
					m.getMappingValue());
	}

    BasicCalculator bc = new BasicCalculator(vizsty_def.getName() + " Node Size",discreteMapping,VisualPropertyType.NODE_SIZE);
    nac.setCalculator(bc);
    nac.getDefaultAppearance().set(VisualPropertyType.NODE_SIZE, vizsty_def.getDefaultNodeSize());
	
		// Cytoscape 2.5
		//nac.setNodeHeightCalculator
	    //(new GenericNodeSizeCalculator(vizsty_def.getName() + " Node Size", discreteMapping));
		// hack
		//nac.setDefaultNodeHeight(vizsty_def.getDefaultNodeSize().doubleValue());
    }

    private void createNodeLabel(VisualStyleDefinition vizsty_def,
				 NodeAppearanceCalculator nac) {

        PassThroughMapping passThroughMapping = new PassThroughMapping
	    ("", ObjectMapping.NODE_MAPPING);
        passThroughMapping.setControllingAttributeName
                ("ID", null, false);
        
        BasicCalculator bc = new BasicCalculator(vizsty_def.getName() + " Node Label",passThroughMapping,VisualPropertyType.NODE_LABEL);
        nac.setCalculator(bc);
        nac.getDefaultAppearance().set(VisualPropertyType.NODE_LABEL, "id_undefined");
    	
   		// Cytoscape 2.5
        //GenericNodeLabelCalculator nodeLabelCalculator =
	    //new GenericNodeLabelCalculator(vizsty_def.getName() + " Node Label",
		//			   passThroughMapping);
        //nac.setNodeLabelCalculator(nodeLabelCalculator);
    }

    private void createNodeColor(VisualStyleDefinition vizsty_def,
				 NodeAppearanceCalculator nac) {

        DiscreteMapping discreteMapping = new DiscreteMapping
	    (vizsty_def.getDefaultNodeColor(),
	     vizsty_def.getNodeAttribute(),
	     ObjectMapping.NODE_MAPPING);

	Vector v = vizsty_def.getNodeColorMapping();
	for (int n = 0; n < v.size(); n++) {
	    VisualStyleDefinition.ObjectMapping m =
		(VisualStyleDefinition.ObjectMapping)v.get(n);
	    discreteMapping.putMapValue(m.getAttributeValue(),
					m.getMappingValue());
	}

	
    BasicCalculator bc = new BasicCalculator(vizsty_def.getName() + " Node Color",discreteMapping,VisualPropertyType.NODE_FILL_COLOR);
    nac.setCalculator(bc);
    nac.getDefaultAppearance().set(VisualPropertyType.NODE_FILL_COLOR, vizsty_def.defaultNodeColor);
	
		// Cytoscape 2.5
        //GenericNodeColorCalculator nodeColorCalculator =
	    //new GenericNodeColorCalculator(vizsty_def.getName() + " Node Color",
		//			   discreteMapping);
        //nac.setNodeFillColorCalculator(nodeColorCalculator);

        // i think this is a hack, but its the only way to make it work
        //nac.setDefaultNodeFillColor(vizsty_def.getDefaultNodeColor());
    }

    private void createNodeBorderColor(VisualStyleDefinition vizsty_def,
				       NodeAppearanceCalculator nac) {

        DiscreteMapping discreteMapping = new DiscreteMapping
	    (vizsty_def.getDefaultNodeBorderColor(),
	     vizsty_def.getNodeAttribute(),
	     ObjectMapping.NODE_MAPPING);

	Vector v = vizsty_def.getNodeBorderColorMapping();
	for (int n = 0; n < v.size(); n++) {
	    VisualStyleDefinition.ObjectMapping m =
		(VisualStyleDefinition.ObjectMapping)v.get(n);
	    discreteMapping.putMapValue(m.getAttributeValue(),
					m.getMappingValue());
	}

    BasicCalculator bc = new BasicCalculator(vizsty_def.getName() + " Node Border Color",discreteMapping,VisualPropertyType.NODE_BORDER_COLOR);
    nac.setCalculator(bc);
    nac.getDefaultAppearance().set(VisualPropertyType.NODE_BORDER_COLOR, vizsty_def.defaultNodeBorderColor);
	
		// Cytoscape 2.5
        //GenericNodeColorCalculator nodeColorCalculator =
	    //new GenericNodeColorCalculator(vizsty_def.getName() + " Node Border Color",
		//			   discreteMapping);
        //nac.setNodeBorderColorCalculator(nodeColorCalculator);
    	// hack
    	//nac.setDefaultNodeBorderColor(vizsty_def.getDefaultNodeBorderColor());
    }

    private void createNodeBorderLineStyle(VisualStyleDefinition vizsty_def,
					  NodeAppearanceCalculator nac) {

        DiscreteMapping discreteMapping = new DiscreteMapping
	    (vizsty_def.getDefaultNodeBorderLineStyle(),
	     vizsty_def.getNodeAttribute(),
	     ObjectMapping.NODE_MAPPING);

	Vector v = vizsty_def.getNodeBorderLineStyleMapping();
	for (int n = 0; n < v.size(); n++) {
	    VisualStyleDefinition.ObjectMapping m =
		(VisualStyleDefinition.ObjectMapping)v.get(n);
	    discreteMapping.putMapValue(m.getAttributeValue(),
					m.getMappingValue());
	}
	
    BasicCalculator bc = new BasicCalculator(vizsty_def.getName() + " Node Border Line Style",discreteMapping,VisualPropertyType.NODE_LINE_STYLE);
    nac.setCalculator(bc);
    nac.getDefaultAppearance().set(VisualPropertyType.NODE_LINE_STYLE, vizsty_def.defaultNodeBorderLineStyle);
    
    discreteMapping = new DiscreteMapping
    (vizsty_def.getDefaultNodeBorderLineWidth(),
     vizsty_def.getNodeAttribute(),
     ObjectMapping.NODE_MAPPING);

    v = vizsty_def.getNodeBorderLineWidthMapping();
    for (int n = 0; n < v.size(); n++) {
    VisualStyleDefinition.ObjectMapping m =
	(VisualStyleDefinition.ObjectMapping)v.get(n);
    discreteMapping.putMapValue(m.getAttributeValue(),
				m.getMappingValue());
    }
    
    bc = new BasicCalculator(vizsty_def.getName() + " Node Border Line Width",discreteMapping,VisualPropertyType.NODE_LINE_WIDTH);
    nac.setCalculator(bc);
    nac.getDefaultAppearance().set(VisualPropertyType.NODE_LINE_WIDTH, vizsty_def.defaultNodeBorderLineWidth);
    
	
		// Cytoscape 2.5
        //GenericNodeLineTypeCalculator nodeLineTypeCalculator =
	    //new GenericNodeLineTypeCalculator(vizsty_def.getName() + " Node Border Line Type",
		//			   discreteMapping);
        //nac.setNodeLineTypeCalculator(nodeLineTypeCalculator);
    	// hack
    	//nac.setDefaultNodeLineType(vizsty_def.getDefaultNodeBorderLineType());
    }

    private void createEdgeSourceArrow(VisualStyleDefinition vizsty_def,
				       EdgeAppearanceCalculator eac) {

        DiscreteMapping discreteMappingShape = new DiscreteMapping
	    (vizsty_def.getDefaultEdgeSourceArrow().getShape(),
	     vizsty_def.getEdgeAttribute(),
	     ObjectMapping.EDGE_MAPPING);
        DiscreteMapping discreteMappingColor = new DiscreteMapping
	    (vizsty_def.getDefaultEdgeSourceArrow().getColor(),
	     vizsty_def.getEdgeAttribute(),
	     ObjectMapping.EDGE_MAPPING);
        

	Vector v = vizsty_def.getEdgeSourceArrowMapping();
	for (int n = 0; n < v.size(); n++) {
	    VisualStyleDefinition.ObjectMapping m =
		(VisualStyleDefinition.ObjectMapping)v.get(n);
	    discreteMappingShape.putMapValue(m.getAttributeValue(),
				((Arrow)(m.getMappingValue())).getShape());
	    discreteMappingColor.putMapValue(m.getAttributeValue(),
	    		((Arrow)(m.getMappingValue())).getColor());
	}

	BasicCalculator bc = new BasicCalculator(vizsty_def.getName() + " Edge Source Arrow Shape",discreteMappingShape,VisualPropertyType.EDGE_SRCARROW_SHAPE);
    eac.setCalculator(bc);
    eac.getDefaultAppearance().set(VisualPropertyType.EDGE_SRCARROW_SHAPE, vizsty_def.defaultEdgeSourceArrow.getShape());

	bc = new BasicCalculator(vizsty_def.getName() + " Edge Source Arrow Color",discreteMappingColor,VisualPropertyType.EDGE_SRCARROW_COLOR);
    eac.setCalculator(bc);
    eac.getDefaultAppearance().set(VisualPropertyType.EDGE_SRCARROW_COLOR, vizsty_def.defaultEdgeSourceArrow.getColor());
    
		// Cytoscape 2.5
        //GenericEdgeArrowCalculator edgeArrowCalculator =
	    //new GenericEdgeArrowCalculator(vizsty_def.getName() + " Edge Source Arrow",
		//			   discreteMapping);
        //eac.setEdgeSourceArrowCalculator(edgeArrowCalculator);
		// hack
		//eac.setDefaultEdgeSourceArrow(vizsty_def.getDefaultEdgeSourceArrow());
    }

    private void createEdgeTargetArrow(VisualStyleDefinition vizsty_def,
				       EdgeAppearanceCalculator eac) {

        DiscreteMapping discreteMappingShape = new DiscreteMapping
	    (vizsty_def.getDefaultEdgeTargetArrow().getShape(),
	     vizsty_def.getEdgeAttribute(),
	     ObjectMapping.EDGE_MAPPING);

        DiscreteMapping discreteMappingColor = new DiscreteMapping
	    (vizsty_def.getDefaultEdgeTargetArrow().getColor(),
	     vizsty_def.getEdgeAttribute(),
	     ObjectMapping.EDGE_MAPPING);
        

	Vector v = vizsty_def.getEdgeTargetArrowMapping();
	for (int n = 0; n < v.size(); n++) {
	    VisualStyleDefinition.ObjectMapping m =
		(VisualStyleDefinition.ObjectMapping)v.get(n);
	    discreteMappingShape.putMapValue(m.getAttributeValue(),
					((Arrow)(m.getMappingValue())).getShape());
	    discreteMappingColor.putMapValue(m.getAttributeValue(),
				((Arrow)(m.getMappingValue())).getColor());
	}

	BasicCalculator bc = new BasicCalculator(vizsty_def.getName() + " Edge Target Arrow Shape",discreteMappingShape,VisualPropertyType.EDGE_TGTARROW_SHAPE);
    eac.setCalculator(bc);
	bc = new BasicCalculator(vizsty_def.getName() + " Edge Target Arrow Color",discreteMappingColor,VisualPropertyType.EDGE_TGTARROW_COLOR);
    eac.setCalculator(bc);
    
    eac.getDefaultAppearance().set(VisualPropertyType.EDGE_TGTARROW_SHAPE, vizsty_def.defaultEdgeTargetArrow.getShape());
    eac.getDefaultAppearance().set(VisualPropertyType.EDGE_TGTARROW_COLOR, vizsty_def.defaultEdgeTargetArrow.getColor());    
    
    
    
	
		// Cytoscape 2.5
        //GenericEdgeArrowCalculator edgeArrowCalculator =
	    //new GenericEdgeArrowCalculator(vizsty_def.getName() + " Edge Target Arrow",
		//			   discreteMapping);
        //eac.setEdgeTargetArrowCalculator(edgeArrowCalculator);
    	// hack
    	// eac.setDefaultEdgeTargetArrow(vizsty_def.getDefaultEdgeTargetArrow());
    }

    private void createEdgeLineType(VisualStyleDefinition vizsty_def,
				    EdgeAppearanceCalculator eac) {

        DiscreteMapping discreteMapping = new DiscreteMapping
	    (vizsty_def.getDefaultEdgeLineType(),
	     vizsty_def.getEdgeAttribute(),
	     ObjectMapping.EDGE_MAPPING);

	Vector v = vizsty_def.getEdgeLineTypeMapping();
	for (int n = 0; n < v.size(); n++) {
	    VisualStyleDefinition.ObjectMapping m =
		(VisualStyleDefinition.ObjectMapping)v.get(n);
	    discreteMapping.putMapValue(m.getAttributeValue(),
					m.getMappingValue());
	    //System.out.println(m.getAttributeValue()+"->"+m.getMappingValue());
	}

	BasicCalculator bc = new BasicCalculator(vizsty_def.getName() + " Edge Line Style",discreteMapping,VisualPropertyType.EDGE_LINE_STYLE);
    eac.setCalculator(bc);
    eac.getDefaultAppearance().set(VisualPropertyType.EDGE_LINE_STYLE, vizsty_def.defaultEdgeLineType);
	
		// Cytoscape 2.5
        //GenericEdgeLineTypeCalculator edgeLineTypeCalculator =
	    //new GenericEdgeLineTypeCalculator(vizsty_def.getName() + " Edge Line Type",
		//			      discreteMapping);
        //eac.setEdgeLineTypeCalculator(edgeLineTypeCalculator);
    	// hack
    	//eac.setDefaultEdgeLineType(vizsty_def.getDefaultEdgeLineType());
    }

    private void createEdgeLineColor(VisualStyleDefinition vizsty_def,
				     EdgeAppearanceCalculator eac) {

        DiscreteMapping discreteMapping = new DiscreteMapping
	    (vizsty_def.getDefaultEdgeLineColor(),
	     vizsty_def.getEdgeAttribute(),
	     ObjectMapping.EDGE_MAPPING);

	Vector v = vizsty_def.getEdgeLineColorMapping();
	for (int n = 0; n < v.size(); n++) {
	    VisualStyleDefinition.ObjectMapping m =
		(VisualStyleDefinition.ObjectMapping)v.get(n);
	    discreteMapping.putMapValue(m.getAttributeValue(),
					m.getMappingValue());
	}
	
	BasicCalculator bc = new BasicCalculator(vizsty_def.getName() + " Edge Color",discreteMapping,VisualPropertyType.EDGE_COLOR);
    eac.setCalculator(bc);
    eac.getDefaultAppearance().set(VisualPropertyType.EDGE_COLOR, vizsty_def.defaultEdgeLineColor);
	
		// Cytoscape 2.5
        //GenericEdgeColorCalculator edgeColorCalculator =
	    //new GenericEdgeColorCalculator(vizsty_def.getName() + " Edge Color",
		//			   discreteMapping);
        //eac.setEdgeColorCalculator(edgeColorCalculator);
    	// hack
    	//eac.setDefaultEdgeColor(vizsty_def.getDefaultEdgeLineColor());
    }
}

