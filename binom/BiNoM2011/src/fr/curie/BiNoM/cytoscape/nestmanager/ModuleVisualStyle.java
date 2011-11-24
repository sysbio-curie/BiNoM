package fr.curie.BiNoM.cytoscape.nestmanager;
/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE  
*/
// Must be rewritten without depreciated classes (it is not a critical class)
import java.awt.Color;
import cytoscape.Cytoscape;
import cytoscape.visual.ArrowShape;
import cytoscape.visual.CalculatorCatalog;
import cytoscape.visual.EdgeAppearanceCalculator;
import cytoscape.visual.GlobalAppearanceCalculator;
import cytoscape.visual.NodeAppearanceCalculator;
import cytoscape.visual.NodeShape;
import cytoscape.visual.VisualMappingManager;
import cytoscape.visual.VisualPropertyType;
import cytoscape.visual.VisualStyle;
import cytoscape.visual.calculators.BasicCalculator;
import cytoscape.visual.calculators.Calculator;
import cytoscape.visual.mappings.DiscreteMapping;
import cytoscape.visual.mappings.ObjectMapping;
import cytoscape.visual.mappings.PassThroughMapping;
/**
 * Create a style to visualize nest networks
 * 
 * @author Daniel.Rovera@curie.fr 
 */
public class ModuleVisualStyle{
	public static final String NAME = "Module Style";
	public static void create(){	
		VisualMappingManager manager = Cytoscape.getVisualMappingManager();
		CalculatorCatalog catalog = manager.getCalculatorCatalog();
		if (catalog.getVisualStyle(NAME)!= null) catalog.removeVisualStyle(NAME);
		NodeAppearanceCalculator nodeAppCalc=new NodeAppearanceCalculator();
		EdgeAppearanceCalculator edgeAppCalc=new EdgeAppearanceCalculator();
		GlobalAppearanceCalculator globalAppCalc=new GlobalAppearanceCalculator();
		Calculator calculator;
		DiscreteMapping mapping;		
		PassThroughMapping pm=new PassThroughMapping(new String(),"ID");
		calculator=new BasicCalculator("Node Label Calc",pm,VisualPropertyType.NODE_LABEL);
		nodeAppCalc.setCalculator(calculator);	
		mapping=new DiscreteMapping(NodeShape.RECT,"has_nested_network",ObjectMapping.NODE_MAPPING);
		mapping.putMapValue("yes", NodeShape.OCTAGON);
		calculator=new BasicCalculator("Node Shape Calc",mapping,VisualPropertyType.NODE_SHAPE);
		nodeAppCalc.setCalculator(calculator);			
		mapping=new DiscreteMapping(Color.WHITE,"has_nested_network",ObjectMapping.NODE_MAPPING);
		mapping.putMapValue("yes", Color.GREEN);
		calculator=new BasicCalculator("Node Color Calc",mapping,VisualPropertyType.NODE_FILL_COLOR);
		nodeAppCalc.setCalculator(calculator);			
		mapping=new DiscreteMapping(ArrowShape.NONE,"interaction",ObjectMapping.EDGE_MAPPING);
		mapping.putMapValue("MOLECULEFLOW", ArrowShape.ARROW);
		mapping.putMapValue("RIGHT", ArrowShape.ARROW);
		mapping.putMapValue("LEFT", ArrowShape.ARROW);
		mapping.putMapValue("CATALYSIS", ArrowShape.CIRCLE);
		mapping.putMapValue("ACTIVATION", ArrowShape.CIRCLE);
		mapping.putMapValue("INHIBITION", ArrowShape.T);
		calculator=new BasicCalculator("Edge Arrow Shape Calc",mapping, VisualPropertyType.EDGE_TGTARROW_SHAPE);
		edgeAppCalc.setCalculator(calculator);		
		mapping=new DiscreteMapping(Color.BLACK,"interaction",ObjectMapping.EDGE_MAPPING);
		mapping.putMapValue("MOLECULEFLOW", Color.BLACK);
		mapping.putMapValue("RIGHT", Color.BLACK);
		mapping.putMapValue("LEFT", Color.BLACK);
		mapping.putMapValue("CATALYSIS", Color.RED);
		mapping.putMapValue("ACTIVATION", Color.RED);
		mapping.putMapValue("INHIBITION", Color.BLUE);
		mapping.putMapValue("INTERSECTION", Color.GREEN);
		calculator = new BasicCalculator("Edge Color Calc",mapping, VisualPropertyType.EDGE_COLOR);
		edgeAppCalc.setCalculator(calculator);
		calculator=new BasicCalculator("Arrow Color Calc",mapping, VisualPropertyType.EDGE_TGTARROW_COLOR);
		edgeAppCalc.setCalculator(calculator);				
		VisualStyle visualStyle = new VisualStyle(NAME, nodeAppCalc, edgeAppCalc, globalAppCalc);
		catalog.addVisualStyle(visualStyle);
		manager.setVisualStyle(visualStyle);
	}
}
