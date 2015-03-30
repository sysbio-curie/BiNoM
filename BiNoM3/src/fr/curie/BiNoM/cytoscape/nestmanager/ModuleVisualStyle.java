package fr.curie.BiNoM.cytoscape.nestmanager;


/*
   BiNoM Cytoscape Plugin under GNU Lesser General Public License 
   Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE  
*/
// Must be rewritten without depreciated classes (it is not a critical class)
import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.plaf.basic.BasicScrollPaneUI.VSBChangeListener;

import org.cytoscape.view.presentation.property.ArrowShapeVisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.mappings.DiscreteMapping;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;

import Main.Launcher;

/**
 * Create a style to visualize nest networks
 * 
 * @author Daniel.Rovera@curie.fr 
 */
public class ModuleVisualStyle{
	public static final String NAME = "Module Style";
	public static VisualStyle vsMoudule = null;
	
	public static VisualStyle getVisualStyle(){
		if(vsMoudule == null)
			create();
		
		return vsMoudule;
	}
	
	public static void create(){	
		
		Set<VisualStyle> visualStyles = Launcher.getAdapter().getVisualMappingManager().getAllVisualStyles();
		ArrayList visualStylesNames = new ArrayList();
		for(VisualStyle vs: visualStyles){
			visualStylesNames.add(vs.getTitle());
			if(vs.getTitle().compareTo(NAME) == 0){
				vsMoudule = vs;		
			}
		}
		
		if(vsMoudule == null)
			vsMoudule= Launcher.getAdapter().getVisualStyleFactory().createVisualStyle(NAME);
		
		PassthroughMapping pm = (PassthroughMapping) Launcher.getAdapter().getVisualMappingFunctionPassthroughFactory().createVisualMappingFunction("name", String.class, BasicVisualLexicon.NODE_LABEL);
		vsMoudule.addVisualMappingFunction(pm); 
		
		
		DiscreteMapping nodeShape = (DiscreteMapping) Launcher.getAdapter().getVisualMappingFunctionDiscreteFactory().createVisualMappingFunction("has_nested_network", Boolean.class, BasicVisualLexicon.NODE_SHAPE);
		nodeShape.putMapValue(true, NodeShapeVisualProperty.OCTAGON);
		vsMoudule.addVisualMappingFunction(nodeShape); 

		DiscreteMapping nodeColour = (DiscreteMapping) Launcher.getAdapter().getVisualMappingFunctionDiscreteFactory().createVisualMappingFunction("has_nested_network", Boolean.class, BasicVisualLexicon.NODE_FILL_COLOR);
		nodeColour.putMapValue(true, Color.GREEN);
		vsMoudule.addVisualMappingFunction(nodeColour); 
		
		
		DiscreteMapping arrowShape = (DiscreteMapping) Launcher.getAdapter().getVisualMappingFunctionDiscreteFactory().createVisualMappingFunction("interaction", String.class, BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE);
		arrowShape.putMapValue("MOLECULEFLOW", ArrowShapeVisualProperty.ARROW);
		arrowShape.putMapValue("RIGHT", ArrowShapeVisualProperty.ARROW);
		arrowShape.putMapValue("LEFT", ArrowShapeVisualProperty.ARROW);
		arrowShape.putMapValue("CATALYSIS", ArrowShapeVisualProperty.CIRCLE);
		arrowShape.putMapValue("ACTIVATION", ArrowShapeVisualProperty.CIRCLE);
		arrowShape.putMapValue("INHIBITION", ArrowShapeVisualProperty.T);	
		vsMoudule.addVisualMappingFunction(arrowShape); 

		
		DiscreteMapping arrowColour = (DiscreteMapping) Launcher.getAdapter().getVisualMappingFunctionDiscreteFactory().createVisualMappingFunction("interaction", String.class, BasicVisualLexicon.EDGE_UNSELECTED_PAINT);
		//mapping=new DiscreteMapping(Color.BLACK,"interaction",ObjectMapping.EDGE_MAPPING);
		arrowColour.putMapValue("MOLECULEFLOW", Color.BLACK);
		arrowColour.putMapValue("RIGHT", Color.BLACK);
		arrowColour.putMapValue("LEFT", Color.BLACK);
		arrowColour.putMapValue("CATALYSIS", Color.RED);
		arrowColour.putMapValue("ACTIVATION", Color.RED);
		arrowColour.putMapValue("INHIBITION", Color.BLUE);
		arrowColour.putMapValue("INTERSECTION", Color.GREEN);
		vsMoudule.addVisualMappingFunction(arrowColour); 
		
		Launcher.getAdapter().getVisualMappingManager().addVisualStyle(vsMoudule);
	}
}
