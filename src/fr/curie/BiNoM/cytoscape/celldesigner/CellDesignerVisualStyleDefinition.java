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
package fr.curie.BiNoM.cytoscape.celldesigner;

import java.awt.Color;
import java.util.Vector;
import cytoscape.Cytoscape;
import cytoscape.visual.*;
import cytoscape.visual.calculators.*;
import cytoscape.visual.mappings.DiscreteMapping;
import cytoscape.visual.mappings.ObjectMapping;
import cytoscape.visual.mappings.PassThroughMapping;

public class CellDesignerVisualStyleDefinition
    extends fr.curie.BiNoM.cytoscape.lib.VisualStyleDefinition {

    // ----- nodes -----
    // discriminating node attribute

    public static final String NODE_ATTR = "CELLDESIGNER_NODE_TYPE";

    // node attribute values
    public static final String NODE_COMPLEX = "COMPLEX";
    public static final String NODE_PROTEIN = "PROTEIN";
    public static final String NODE_ION = "ION";
    public static final String NODE_GENE = "GENE";
    public static final String NODE_RNA = "RNA";
    public static final String NODE_SIMPLE_MOLECULE = "SIMPLE_MOLECULE";
    public static final String NODE_PHENOTYPE = "PHENOTYPE";

    public static final String NODE_HETERODIMER_ASSOCIATION = "HETERODIMER_ASSOCIATION";
    public static final String NODE_HETERODIMER_DISSOCIATION = "DISSOCIATION";
    public static final String NODE_STATE_TRANSITION = "STATE_TRANSITION";
    public static final String NODE_UNKNOWN_TRANSITION = "UNKNOWN_TRANSITION";
    public static final String NODE_UNKNOWN_REACTION = "UNKNOWN_REACTION";
    public static final String NODE_KNOWN_TRANSITION_OMITTED = "KNOWN_TRANSITION_OMITTED";
    public static final String NODE_TRANSCRIPTIONAL_ACTIVATION = "TRANSCRIPTIONAL_ACTIVATION";
    public static final String NODE_TRANSCRIPTION = "TRANSCRIPTION";    
    public static final String NODE_TRANSCRIPTIONAL_INHIBITION = "TRANSCRIPTIONAL_INHIBITION";
    public static final String NODE_TRANSLATIONAL_ACTIVATION = "TRANSLATIONAL_ACTIVATION";
    public static final String NODE_TRANSLATION = "TRANSLATION";    
    public static final String NODE_TRANSLATIONAL_INHIBITION = "TRANSLATIONAL_INHIBITION";
    public static final String NODE_TRUNCATION = "TRUNCATION";
    public static final String NODE_INHIBITION = "INHIBITION";    
    public static final String NODE_CATALYSIS = "CATALYSIS";    
    
    public static final String NODE_NEGATIVE_INFLUENCE = "NEGATIVE_INFLUENCE";    
    public static final String NODE_UNKNOWN_NEGATIVE_INFLUENCE = "UNKNOWN_NEGATIVE_INFLUENCE";
    public static final String NODE_UNKNOWN_POSITIVE_INFLUENCE = "UNKNOWN_POSITIVE_INFLUENCE";
    public static final String NODE_POSITIVE_INFLUENCE = "POSITIVE_INFLUENCE";    

    public static final String NODE_TRANSPORT = "TRANSPORT";
    
    public static final String NODE_PATHWAY = "PATHWAY";    

    // ----- edges -----
    // discriminating edge attribute
    public static final String EDGE_ATTR = "CELLDESIGNER_EDGE_TYPE";

    // edge attribute values
    public static final String EDGE_CATALYSIS = "CATALYSIS";
    public static final String EDGE_CATALYSIS_UNKNOWN = "UNKNOWN_CATALYSIS";
    public static final String EDGE_MODIFIES = "MODIFIES";
    public static final String EDGE_INHIBITION = "INHIBITION";
    public static final String EDGE_LEFT = "LEFT";
    public static final String EDGE_RIGHT = "RIGHT";
    public static final String EDGE_TRANSCRIPTIONAL_INHIBITION = "TRANSCRIPTIONAL_INHIBITION";
    public static final String EDGE_TRANSCRIPTIONAL_ACTIVATION = "TRANSCRIPTIONAL_ACTIVATION";
    public static final String EDGE_INTERSECTION = "INTERSECTION";
    
    public static final String EDGE_TRIGGER = "TRIGGER";    
    public static final String EDGE_UNKNOWN_INHIBITION = "UNKNOWN_INHIBITION";
    public static final String EDGE_PHYSICAL_STIMULATION = "PHYSICAL_STIMULATION";    
    public static final String EDGE_MODULATION = "MODULATION";
    public static final String EDGE_POSITIVE_INFLUENCE = "POSITIVE_INFLUENCE";
    public static final String EDGE_NEGATIVE_INFLUENCE = "NEGATIVE_INFLUENCE";
    public static final String EDGE_UNKNOWN_POSITIVE_INFLUENCE = "UNKNOWN_POSITIVE_INFLUENCE";
    public static final String EDGE_UNKNOWN_NEGATIVE_INFLUENCE = "UNKNOWN_NEGATIVE_INFLUENCE";
    public static final String EDGE_UNKNOWN_TRANSITION = "UNKNOWN_TRANSITION";

    

    private CellDesignerVisualStyleDefinition() {
	super("BiNoM CellDesigner");

	nodeAttr = NODE_ATTR;
	edgeAttr = EDGE_ATTR;

	// default node values

	defaultNodeShape = NodeShape.ROUND_RECT;
	defaultNodeSize = new Double(30);
	defaultNodeColor = Color.WHITE;
	defaultNodeBorderLineStyle = LineStyle.SOLID;
	defaultNodeBorderLineWidth = 1;
	defaultNodeBorderColor = Color.BLACK;


	// mapping node values

	// node shapes
	nodeShapeMapping.add(new ObjectMapping
			     (NODE_PROTEIN,
			      NodeShape.ROUND_RECT));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_ION,
                            		 NodeShape.ELLIPSE));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_SIMPLE_MOLECULE,
                            		 NodeShape.ELLIPSE));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_GENE,
                            		 NodeShape.RECT));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_RNA,
                            		 NodeShape.PARALLELOGRAM));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_PHENOTYPE,
                            		 NodeShape.OCTAGON));

        nodeShapeMapping.add(new ObjectMapping
                             (NODE_HETERODIMER_ASSOCIATION,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_HETERODIMER_DISSOCIATION,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_STATE_TRANSITION,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_UNKNOWN_TRANSITION,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_UNKNOWN_REACTION,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_KNOWN_TRANSITION_OMITTED,
                            		 NodeShape.DIAMOND));

        nodeShapeMapping.add(new ObjectMapping
                             (NODE_TRANSCRIPTIONAL_ACTIVATION,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                (NODE_TRANSCRIPTION,
                		NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_TRANSCRIPTIONAL_INHIBITION,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_TRANSLATIONAL_ACTIVATION,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                (NODE_TRANSLATION,
                		NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_TRANSLATIONAL_INHIBITION,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_TRUNCATION,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                (NODE_INHIBITION,
                		NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                (NODE_CATALYSIS,
                		NodeShape.DIAMOND));
        
        nodeShapeMapping.add(new ObjectMapping
                (NODE_NEGATIVE_INFLUENCE,
                		NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                (NODE_UNKNOWN_NEGATIVE_INFLUENCE,
                		NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                (NODE_UNKNOWN_POSITIVE_INFLUENCE,
                		NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                (NODE_POSITIVE_INFLUENCE,
                		NodeShape.DIAMOND));

        nodeShapeMapping.add(new ObjectMapping
                             (NODE_TRANSPORT,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                (NODE_PATHWAY,
                		NodeShape.OCTAGON));


	// node sizes
	nodeSizeMapping.add(new ObjectMapping
			    (NODE_COMPLEX, new Double(40)));


        nodeSizeMapping.add(new ObjectMapping
                             (NODE_HETERODIMER_ASSOCIATION,
                              new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                             (NODE_HETERODIMER_DISSOCIATION,
                              new Double(20)));

        nodeSizeMapping.add(new ObjectMapping
                             (NODE_STATE_TRANSITION,
                              new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                             (NODE_UNKNOWN_TRANSITION,
                              new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                             (NODE_UNKNOWN_REACTION,
                              new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                             (NODE_KNOWN_TRANSITION_OMITTED,
                              new Double(20)));


        nodeSizeMapping.add(new ObjectMapping
                             (NODE_TRANSCRIPTIONAL_ACTIVATION,
                              new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                (NODE_TRANSCRIPTION,
                 new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                             (NODE_TRANSCRIPTIONAL_INHIBITION,
                              new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                             (NODE_TRANSLATIONAL_ACTIVATION,
                              new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                (NODE_TRANSLATION,
                 new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                             (NODE_TRANSLATIONAL_INHIBITION,
                              new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                             (NODE_TRUNCATION,
                              new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                (NODE_INHIBITION,
                 new Double(20)));
        
        nodeSizeMapping.add(new ObjectMapping
                (NODE_NEGATIVE_INFLUENCE,
                		new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                (NODE_UNKNOWN_NEGATIVE_INFLUENCE,
                		new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                (NODE_UNKNOWN_POSITIVE_INFLUENCE,
                		new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                (NODE_POSITIVE_INFLUENCE,
                		new Double(20)));
        
        
        nodeSizeMapping.add(new ObjectMapping
                (NODE_CATALYSIS,
                 new Double(20)));

        nodeSizeMapping.add(new ObjectMapping
                             (NODE_TRANSPORT,
                              new Double(20)));


	// node color
	nodeColorMapping.add(new ObjectMapping
			     (NODE_COMPLEX, Color.lightGray));

        nodeColorMapping.add(new ObjectMapping
                             (NODE_HETERODIMER_ASSOCIATION,
                              Color.gray));
        nodeColorMapping.add(new ObjectMapping
                             (NODE_HETERODIMER_DISSOCIATION,
                              Color.gray));

        nodeColorMapping.add(new ObjectMapping
                             (NODE_STATE_TRANSITION,
                              Color.gray));
        nodeColorMapping.add(new ObjectMapping
                             (NODE_UNKNOWN_TRANSITION,
                              Color.gray));
        nodeColorMapping.add(new ObjectMapping
                             (NODE_UNKNOWN_REACTION,
                              Color.gray));
        nodeColorMapping.add(new ObjectMapping
                             (NODE_KNOWN_TRANSITION_OMITTED,
                              Color.gray));

        nodeColorMapping.add(new ObjectMapping
                             (NODE_TRANSCRIPTIONAL_ACTIVATION,
                              Color.gray));
        nodeColorMapping.add(new ObjectMapping
                (NODE_TRANSCRIPTION,
                 Color.gray));
        nodeColorMapping.add(new ObjectMapping
                             (NODE_TRANSCRIPTIONAL_INHIBITION,
                              Color.gray));
        nodeColorMapping.add(new ObjectMapping
                             (NODE_TRANSLATIONAL_ACTIVATION,
                              Color.gray));
        nodeColorMapping.add(new ObjectMapping
                (NODE_TRANSLATION,
                 Color.gray));
        nodeColorMapping.add(new ObjectMapping
                             (NODE_TRANSLATIONAL_INHIBITION,
                              Color.gray));
        nodeColorMapping.add(new ObjectMapping
                             (NODE_TRUNCATION,
                              Color.gray));
        nodeColorMapping.add(new ObjectMapping
                (NODE_INHIBITION,
                 Color.gray));
        nodeColorMapping.add(new ObjectMapping
                (NODE_CATALYSIS,
                 Color.gray));
        
        nodeColorMapping.add(new ObjectMapping
                (NODE_NEGATIVE_INFLUENCE,
                		Color.GREEN));
        nodeColorMapping.add(new ObjectMapping
                (NODE_UNKNOWN_NEGATIVE_INFLUENCE,
                		Color.GREEN.darker()));
        nodeColorMapping.add(new ObjectMapping
                (NODE_UNKNOWN_POSITIVE_INFLUENCE,
                		Color.RED.darker()));
        nodeColorMapping.add(new ObjectMapping
                (NODE_POSITIVE_INFLUENCE,
                		Color.RED));
        

        nodeColorMapping.add(new ObjectMapping
                             (NODE_TRANSPORT,
                              Color.gray));

        nodeColorMapping.add(new ObjectMapping
                (NODE_PATHWAY, Color.GREEN));


	// node border line type

	nodeBorderLineWidthMapping.add(new ObjectMapping
		      (NODE_COMPLEX, 2));

        nodeBorderLineWidthMapping.add(new ObjectMapping
                (NODE_PHENOTYPE, 2));
        
        nodeBorderLineWidthMapping.add(new ObjectMapping
                (NODE_PATHWAY, 2));


	// node border color

        nodeBorderColorMapping.add(new ObjectMapping
                             (NODE_TRANSCRIPTIONAL_ACTIVATION,
                              Color.RED));
        nodeBorderColorMapping.add(new ObjectMapping
                (NODE_TRANSCRIPTION,
                 Color.RED));
        nodeBorderColorMapping.add(new ObjectMapping
                             (NODE_TRANSCRIPTIONAL_INHIBITION,
                              Color.BLUE));
        nodeBorderColorMapping.add(new ObjectMapping
                             (NODE_TRANSLATIONAL_ACTIVATION,
                              Color.RED));
        nodeBorderColorMapping.add(new ObjectMapping
                (NODE_TRANSLATION,
                 Color.RED));
        nodeBorderColorMapping.add(new ObjectMapping
                             (NODE_TRANSLATIONAL_INHIBITION,
                              Color.BLUE));
        nodeBorderColorMapping.add(new ObjectMapping
                             (NODE_TRANSPORT,
                              Color.GREEN));


	//nodeBorderColorMapping.add(new ObjectMapping
	//			   (NODE_STATE_TRANSITION, Color.RED));


	// default edge values
	defaultEdgeSourceArrow = Arrow.NONE;
	defaultEdgeTargetArrow = new Arrow(ArrowShape.ARROW,Color.BLACK);
	defaultEdgeLineType = LineStyle.SOLID;
	defaultEdgeLineColor = Color.BLACK;

	// mapping edge values

	// edge source arrow
	//edgeSourceArrowMapping.add(new ObjectMapping
	//			   (EDGE_CATALYSIS, Arrow.BLACK_T));


	// edge target arrow
	edgeTargetArrowMapping.add(new ObjectMapping
				(EDGE_CATALYSIS, new Arrow(ArrowShape.CIRCLE,Color.RED)));
	edgeTargetArrowMapping.add(new ObjectMapping
			   	(EDGE_CATALYSIS_UNKNOWN, new Arrow(ArrowShape.CIRCLE, Color.RED.darker())));
    edgeTargetArrowMapping.add(new ObjectMapping
    			(EDGE_INHIBITION, new Arrow(ArrowShape.T,Color.GREEN)));
    edgeTargetArrowMapping.add(new ObjectMapping
    			(EDGE_TRANSCRIPTIONAL_INHIBITION, new Arrow(ArrowShape.T,Color.GREEN)));
    edgeTargetArrowMapping.add(new ObjectMapping
                (EDGE_INTERSECTION, new Arrow(ArrowShape.NONE,Color.CYAN)));
    edgeTargetArrowMapping.add(new ObjectMapping
			(EDGE_NEGATIVE_INFLUENCE, new Arrow(ArrowShape.T,Color.GREEN)));
	edgeTargetArrowMapping.add(new ObjectMapping
			(EDGE_POSITIVE_INFLUENCE, new Arrow(ArrowShape.ARROW,Color.RED)));
    edgeTargetArrowMapping.add(new ObjectMapping
			(EDGE_UNKNOWN_NEGATIVE_INFLUENCE, new Arrow(ArrowShape.T,Color.GREEN)));
	edgeTargetArrowMapping.add(new ObjectMapping
			(EDGE_UNKNOWN_POSITIVE_INFLUENCE, new Arrow(ArrowShape.ARROW,Color.RED)));

    
    edgeTargetArrowMapping.add(new ObjectMapping
			(EDGE_TRIGGER, new Arrow(ArrowShape.CIRCLE,new Color(0.82f,0.125f,0.56f))));
    edgeTargetArrowMapping.add(new ObjectMapping
			(EDGE_UNKNOWN_INHIBITION, new Arrow(ArrowShape.T,Color.GREEN.darker())));
    edgeTargetArrowMapping.add(new ObjectMapping
			(EDGE_PHYSICAL_STIMULATION, new Arrow(ArrowShape.CIRCLE,Color.ORANGE)));
    edgeTargetArrowMapping.add(new ObjectMapping
			(EDGE_MODULATION, new Arrow(ArrowShape.CIRCLE, Color.BLUE)));
            

	/*edgeTargetArrowMapping.add(new ObjectMapping
					(EDGE_CATALYSIS, ArrowShape.CIRCLE));
	edgeTargetArrowMapping.add(new ObjectMapping
					(EDGE_CATALYSIS_UNKNOWN, ArrowShape.CIRCLE));
	edgeTargetArrowMapping.add(new ObjectMapping
                    (EDGE_INHIBITION, ArrowShape.T));
	edgeTargetArrowMapping.add(new ObjectMapping
                    (EDGE_TRANSCRIPTIONAL_INHIBITION, ArrowShape.T));
	edgeTargetArrowMapping.add(new ObjectMapping
					(EDGE_INTERSECTION, ArrowShape.DIAMOND));*/	


	// edge line type
	edgeLineTypeMapping.add(new ObjectMapping
				(EDGE_TRANSCRIPTIONAL_INHIBITION, LineStyle.LONG_DASH));
        edgeLineTypeMapping.add(new ObjectMapping
                                (EDGE_TRANSCRIPTIONAL_ACTIVATION, LineStyle.LONG_DASH));
        edgeLineTypeMapping.add(new ObjectMapping
                                 (EDGE_MODIFIES, LineStyle.LONG_DASH));
    	edgeLineTypeMapping.add(new ObjectMapping
   			 (EDGE_CATALYSIS_UNKNOWN, LineStyle.LONG_DASH));
    	edgeLineTypeMapping.add(new ObjectMapping
      			 (EDGE_UNKNOWN_TRANSITION, LineStyle.LONG_DASH));
        

	// edge line color
	edgeLineColorMapping.add(new ObjectMapping
				 (EDGE_CATALYSIS, Color.RED));
	edgeLineColorMapping.add(new ObjectMapping
			 (EDGE_CATALYSIS_UNKNOWN, Color.RED.darker()));
        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_INHIBITION, Color.GREEN));

        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_MODIFIES, Color.ORANGE));

	edgeLineColorMapping.add(new ObjectMapping
				 (EDGE_TRANSCRIPTIONAL_ACTIVATION, Color.RED));
        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_TRANSCRIPTIONAL_INHIBITION, Color.BLUE));
        edgeLineColorMapping.add(new ObjectMapping
                (EDGE_INTERSECTION, Color.CYAN));
	edgeLineColorMapping.add(new ObjectMapping
			 (EDGE_POSITIVE_INFLUENCE, Color.RED));
	edgeLineColorMapping.add(new ObjectMapping
			 (EDGE_NEGATIVE_INFLUENCE, Color.GREEN));
	edgeLineColorMapping.add(new ObjectMapping
			 (EDGE_UNKNOWN_POSITIVE_INFLUENCE, Color.RED));
	edgeLineColorMapping.add(new ObjectMapping
			 (EDGE_UNKNOWN_NEGATIVE_INFLUENCE, Color.GREEN));

        
        edgeLineColorMapping.add(new ObjectMapping
    			(EDGE_TRIGGER, Color.RED));
        edgeLineColorMapping.add(new ObjectMapping
    			(EDGE_UNKNOWN_INHIBITION, Color.GREEN));
        edgeLineColorMapping.add(new ObjectMapping
    			(EDGE_PHYSICAL_STIMULATION, Color.PINK));
        

        edgeLineColorMapping.add(new ObjectMapping
    			(EDGE_TRIGGER, new Color(0.82f,0.125f,0.56f)));
        edgeLineColorMapping.add(new ObjectMapping
    			(EDGE_UNKNOWN_INHIBITION, Color.GREEN.darker()));
        edgeLineColorMapping.add(new ObjectMapping
    			(EDGE_PHYSICAL_STIMULATION, Color.ORANGE));
        edgeLineColorMapping.add(new ObjectMapping
    			(EDGE_MODULATION, Color.BLUE));
        
        
    }

    private static CellDesignerVisualStyleDefinition instance;

    public static CellDesignerVisualStyleDefinition getInstance() {
	if (instance == null)
	    instance = new CellDesignerVisualStyleDefinition();
	return instance;
    }
}
