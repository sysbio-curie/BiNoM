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
package fr.curie.BiNoM.cytoscape.biopax;

import java.awt.Color;
import java.util.Vector;
import cytoscape.Cytoscape;
import cytoscape.visual.*;
import cytoscape.visual.calculators.*;
import cytoscape.visual.mappings.DiscreteMapping;
import cytoscape.visual.mappings.ObjectMapping;
import cytoscape.visual.mappings.PassThroughMapping;

public class BioPAXVisualStyleDefinition
    extends fr.curie.BiNoM.cytoscape.lib.VisualStyleDefinition {

    // ----- nodes -----
    // discriminating node attribute

    public static final String NODE_ATTR = "BIOPAX_NODE_TYPE";

//    // node attribute values
//    public static final String NODE_INTERACTION = "interaction";
//    public static final String NODE_PHYSICAL_INTERACTION = "physicalInteraction";
//    public static final String NODE_CONTROL = "control";
//    public static final String NODE_CATALYSIS = "catalysis";
//    public static final String NODE_MODULATION = "modulation";
//    public static final String NODE_CONVERSION = "conversion";
//    public static final String NODE_TRANSPORT = "transport";
//    public static final String NODE_BIOCHEMICAL_REACTION = "biochemicalReaction";
//    public static final String NODE_TRANSPORT_WITH_BIOCHEMICAL_REACTION = "transportWithBiochemicalReaction";
//    public static final String NODE_COMPLEX_ASSEMBLY = "complexAssembly";
//
//    public static final String NODE_PHYSICAL_ENTITY = "physicalEntity";
//    public static final String NODE_COMPLEX = "complex";
//    public static final String NODE_DNA = "dna";
//    public static final String NODE_RNA = "rna";
//    public static final String NODE_SMALL_MOLECULE = "smallMolecule";
//    public static final String NODE_PROTEIN = "protein";
//
//    public static final String NODE_PATHWAY = "pathway";
//    public static final String NODE_PATHWAY_STEP = "pathwayStep";
//
//    public static final String NODE_PUBLICATION = "publication";

//  // node attribute values change ebonnet 11.2011
  public static final String NODE_INTERACTION = "Interaction";
  public static final String NODE_PHYSICAL_INTERACTION = "physicalInteraction";
  public static final String NODE_CONTROL = "Control";
  public static final String NODE_CATALYSIS = "Catalysis";
  public static final String NODE_MODULATION = "Modulation";
  public static final String NODE_CONVERSION = "Conversion";
  public static final String NODE_TRANSPORT = "Transport";
  public static final String NODE_BIOCHEMICAL_REACTION = "BiochemicalReaction";
  public static final String NODE_TRANSPORT_WITH_BIOCHEMICAL_REACTION = "TransportWithBiochemicalReaction";
  public static final String NODE_COMPLEX_ASSEMBLY = "ComplexAssembly";

  public static final String NODE_PHYSICAL_ENTITY = "PhysicalEntity";
  public static final String NODE_COMPLEX = "Complex";
  public static final String NODE_DNA = "Dna";
  public static final String NODE_RNA = "Rna";
  public static final String NODE_SMALL_MOLECULE = "SmallMolecule";
  public static final String NODE_PROTEIN = "Protein";

  public static final String NODE_PATHWAY = "Pathway";
  public static final String NODE_PATHWAY_STEP = "PathwayStep";

  public static final String NODE_PUBLICATION = "Publication";

    
    
    // ----- edges -----
    // discriminating edge attribute
    public static final String EDGE_ATTR = "BIOPAX_EDGE_TYPE";

    // edge attribute values
    public static final String EDGE_ACTIVATION = "ACTIVATION";

    public static final String EDGE_CATALYSIS_UNKNOWN = "CATALYSIS_UNKNOWN";
    public static final String EDGE_CATALYSIS_ACTIVATION = "CATALYSIS_ACTIVATION";
    public static final String EDGE_CATALYSIS_INHIBITION = "CATALYSIS_INHIBITION";

    public static final String EDGE_MODULATION_UNKNOWN = "MODULATION_UNKNOWN";
    public static final String EDGE_MODULATION_ACTIVATION = "MODULATION_ACTIVATION";
    public static final String EDGE_MODULATION_INHIBITION = "MODULATION_INHIBITION";

    public static final String EDGE_CONTROL_UNKNOWN = "CONTROL_UNKNOWN";
    public static final String EDGE_CONTROL_ACTIVATION = "CONTROL_ACTIVATION";
    public static final String EDGE_CONTROL_INHIBITION = "CONTROL_INHIBITION";

    public static final String EDGE_INHIBITION = "INHIBITION";
    public static final String EDGE_CONTAINS = "CONTAINS";
    public static final String EDGE_STEP = "STEP";
    public static final String EDGE_NEXT = "NEXT";
    public static final String EDGE_LEFT = "LEFT";
    public static final String EDGE_RIGHT = "RIGHT";
    public static final String EDGE_SPECIESOF = "SPECIESOF";
    public static final String EDGE_physicalInteraction = "physicalInteraction";
    public static final String EDGE_REFERENCE = "REFERENCE";
    public static final String EDGE_INTERSECTION = "INTERSECTION";


    @SuppressWarnings("unchecked")
	private BioPAXVisualStyleDefinition() {
	super("BiNoM BioPAX");

	nodeAttr = NODE_ATTR;
	edgeAttr = EDGE_ATTR;

	// default node values
	defaultNodeShape = NodeShape.ROUND_RECT; //new Byte(ShapeNodeRealizer.ROUND_RECT);
	defaultNodeSize = new Double(30);
	defaultNodeColor = Color.WHITE;
	//defaultNodeBorderLineType = LineType.LINE_1;
	// Changed in Cytoscape 2.7.0
	defaultNodeBorderLineStyle = cytoscape.visual.LineStyle.SOLID;
	defaultNodeBorderLineWidth = 1;
	defaultNodeBorderColor = Color.BLACK;
	
	// mapping node values

	// node shapes
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_INTERACTION,
                              NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_PHYSICAL_INTERACTION,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_CONTROL,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_CONVERSION,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_CATALYSIS,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_MODULATION,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_BIOCHEMICAL_REACTION,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_TRANSPORT,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_TRANSPORT_WITH_BIOCHEMICAL_REACTION,
                            		 NodeShape.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_COMPLEX_ASSEMBLY,
                            		 NodeShape.DIAMOND));

        nodeShapeMapping.add(new ObjectMapping
                             (NODE_PHYSICAL_ENTITY,
                            		 NodeShape.ROUND_RECT));
	nodeShapeMapping.add(new ObjectMapping
			     (NODE_COMPLEX,
			    		 NodeShape.ROUND_RECT));
	nodeShapeMapping.add(new ObjectMapping
			     (NODE_PROTEIN,
			    		 NodeShape.ROUND_RECT));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_RNA,
                            		 NodeShape.PARALLELOGRAM));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_DNA,
                            		 NodeShape.RECT));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_SMALL_MOLECULE,
                            		 NodeShape.ELLIPSE));

        nodeShapeMapping.add(new ObjectMapping
                             (NODE_PATHWAY,
                            		 NodeShape.OCTAGON));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_PATHWAY_STEP,
                            		 NodeShape.TRIANGLE));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_PUBLICATION,
                            		 NodeShape.HEXAGON));

	// node images
	/*
	nodeImageMapping.add(new ObjectMapping
			     (NODE_BIOCHEMICAL_REACTION,
			      new ImageDefinition("images/alour.jpg", 0.4)));

	nodeImageMapping.add(new ObjectMapping
			     (NODE_COMPLEX,
			      new ImageDefinition("images/nerius.jpg")));
	*/

	// node sizes
        nodeSizeMapping.add(new ObjectMapping
                            (NODE_INTERACTION, new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                            (NODE_PHYSICAL_INTERACTION, new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                            (NODE_CONTROL, new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                            (NODE_CATALYSIS, new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                            (NODE_MODULATION, new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                            (NODE_CONVERSION, new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                            (NODE_BIOCHEMICAL_REACTION, new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                            (NODE_COMPLEX_ASSEMBLY, new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                            (NODE_TRANSPORT, new Double(20)));
        nodeSizeMapping.add(new ObjectMapping
                            (NODE_TRANSPORT_WITH_BIOCHEMICAL_REACTION, new Double(20)));

        nodeSizeMapping.add(new ObjectMapping
                            (NODE_PHYSICAL_ENTITY, new Double(40)));
	nodeSizeMapping.add(new ObjectMapping
			    (NODE_COMPLEX, new Double(50)));
	nodeSizeMapping.add(new ObjectMapping
			    (NODE_PROTEIN, new Double(40)));
        nodeSizeMapping.add(new ObjectMapping
                            (NODE_RNA, new Double(40)));
        nodeSizeMapping.add(new ObjectMapping
                            (NODE_DNA, new Double(40)));
        nodeSizeMapping.add(new ObjectMapping
                            (NODE_SMALL_MOLECULE, new Double(30)));


	// node color
        nodeColorMapping.add(new ObjectMapping
                            (NODE_INTERACTION, Color.gray));
        nodeColorMapping.add(new ObjectMapping
                            (NODE_PHYSICAL_INTERACTION, Color.gray));
        nodeColorMapping.add(new ObjectMapping
                            (NODE_CONTROL, Color.gray));
        nodeColorMapping.add(new ObjectMapping
                            (NODE_CATALYSIS, Color.gray));
        nodeColorMapping.add(new ObjectMapping
                            (NODE_MODULATION, Color.gray));
        nodeColorMapping.add(new ObjectMapping
                            (NODE_CONVERSION, Color.gray));
        nodeColorMapping.add(new ObjectMapping
                            (NODE_BIOCHEMICAL_REACTION, Color.gray));
        nodeColorMapping.add(new ObjectMapping
                            (NODE_COMPLEX_ASSEMBLY, Color.gray));
        nodeColorMapping.add(new ObjectMapping
                            (NODE_TRANSPORT, Color.gray));
        nodeColorMapping.add(new ObjectMapping
                            (NODE_TRANSPORT_WITH_BIOCHEMICAL_REACTION, Color.gray));

        nodeColorMapping.add(new ObjectMapping
                             (NODE_PHYSICAL_ENTITY, Color.WHITE));
	nodeColorMapping.add(new ObjectMapping
			     (NODE_COMPLEX, Color.lightGray));
	nodeColorMapping.add(new ObjectMapping
			     (NODE_PROTEIN, Color.white));
        nodeColorMapping.add(new ObjectMapping
                             (NODE_DNA, Color.WHITE));
        nodeColorMapping.add(new ObjectMapping
                             (NODE_RNA, Color.WHITE));
        nodeColorMapping.add(new ObjectMapping
                             (NODE_SMALL_MOLECULE, Color.WHITE));
        nodeColorMapping.add(new ObjectMapping
                             (NODE_PATHWAY, Color.GREEN));
        nodeColorMapping.add(new ObjectMapping
                             (NODE_PATHWAY_STEP, new Color(255,128,255)));
        nodeColorMapping.add(new ObjectMapping
                             (NODE_PUBLICATION, Color.cyan));


	// node border line type
	nodeBorderLineStyleMapping.add(new ObjectMapping
				      (NODE_BIOCHEMICAL_REACTION, LineStyle.SOLID));
	nodeBorderLineWidthMapping.add(new ObjectMapping
		      (NODE_BIOCHEMICAL_REACTION, 1));
	

	nodeBorderLineStyleMapping.add(new ObjectMapping
				      (NODE_COMPLEX, LineStyle.SOLID));
        nodeBorderLineWidthMapping.add(new ObjectMapping
                                      (NODE_COMPLEX, 2));

    	nodeBorderLineStyleMapping.add(new ObjectMapping
			      (NODE_PATHWAY, LineStyle.SOLID));
  nodeBorderLineWidthMapping.add(new ObjectMapping
                                (NODE_PATHWAY, 2));

        
	// node border color
	nodeBorderColorMapping.add(new ObjectMapping
				   (NODE_BIOCHEMICAL_REACTION, Color.black));

	nodeBorderColorMapping.add(new ObjectMapping
				   (NODE_COMPLEX, Color.BLACK));

	nodeBorderColorMapping.add(new ObjectMapping
				   (NODE_PROTEIN, Color.BLACK));

	// default edge values
	defaultEdgeSourceArrow = Arrow.NONE; 
	defaultEdgeTargetArrow = new Arrow(ArrowShape.ARROW,Color.GRAY);
	defaultEdgeLineType = LineStyle.SOLID;
	defaultEdgeLineColor = Color.GRAY;

	// mapping edge values

	// edge source arrow
	edgeSourceArrowMapping.add(new ObjectMapping
				   (EDGE_ACTIVATION, Arrow.NONE));

	edgeSourceArrowMapping.add(new ObjectMapping
				   (EDGE_LEFT, Arrow.NONE));

	edgeSourceArrowMapping.add(new ObjectMapping
				   (EDGE_RIGHT, Arrow.NONE));


	// edge line color
	edgeLineColorMapping.add(new ObjectMapping
				 (EDGE_ACTIVATION, Color.RED));
        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_INHIBITION, Color.BLUE));
	edgeLineColorMapping.add(new ObjectMapping
				 (EDGE_LEFT, Color.BLACK));
	edgeLineColorMapping.add(new ObjectMapping
				 (EDGE_RIGHT, Color.BLACK));
        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_CONTAINS, Color.GREEN));
        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_STEP, Color.GREEN));
        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_NEXT, Color.BLUE));
        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_SPECIESOF, Color.gray));
        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_physicalInteraction, Color.gray));

        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_CATALYSIS_UNKNOWN, Color.GREEN));

	// EV changed 19/12/06 to see edge selection
        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_CATALYSIS_ACTIVATION, Color.RED));
	// was:
	// edgeLineColorMapping.add(new ObjectMapping
	// (EDGE_CATALYSIS_ACTIVATION, Color.RED));
        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_CATALYSIS_INHIBITION, Color.BLUE));

        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_MODULATION_UNKNOWN, Color.GREEN));
        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_MODULATION_ACTIVATION, Color.MAGENTA));
        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_MODULATION_INHIBITION, Color.PINK));

        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_CONTROL_UNKNOWN, Color.GREEN));
        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_CONTROL_ACTIVATION, new Color(128,0,0)));
        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_CONTROL_INHIBITION, new Color(0,0,128)));
        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_REFERENCE, Color.gray));
        edgeLineColorMapping.add(new ObjectMapping
                (EDGE_INTERSECTION, Color.CYAN));
	
	
	
    	// edge target arrow
        edgeTargetArrowMapping.add(new ObjectMapping
        						   (EDGE_ACTIVATION, new Arrow(ArrowShape.CIRCLE,Color.RED)));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_INHIBITION, new Arrow(ArrowShape.T,Color.BLUE)));
        edgeTargetArrowMapping.add(new ObjectMapping
				                   (EDGE_LEFT, new Arrow(ArrowShape.ARROW,Color.BLACK)));

        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_CATALYSIS_UNKNOWN, new Arrow(ArrowShape.CIRCLE,Color.GREEN)));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_CATALYSIS_ACTIVATION, new Arrow(ArrowShape.CIRCLE,Color.RED)));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_CATALYSIS_INHIBITION, new Arrow(ArrowShape.T,Color.BLUE)));

        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_MODULATION_UNKNOWN, new Arrow(ArrowShape.CIRCLE,Color.GREEN)));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_MODULATION_ACTIVATION, new Arrow(ArrowShape.CIRCLE,Color.MAGENTA)));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_MODULATION_INHIBITION, new Arrow(ArrowShape.T,Color.PINK)));

        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_CONTROL_UNKNOWN, new Arrow(ArrowShape.CIRCLE,Color.GREEN)));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_CONTROL_ACTIVATION, new Arrow(ArrowShape.CIRCLE,new Color(128,0,0))));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_CONTROL_INHIBITION, new Arrow(ArrowShape.T,new Color(0,0,128))));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_REFERENCE, Arrow.NONE));
        edgeTargetArrowMapping.add(new ObjectMapping
                (EDGE_INTERSECTION, new Arrow(ArrowShape.DIAMOND,Color.CYAN)));

        edgeTargetArrowMapping.add(new ObjectMapping
                (EDGE_RIGHT, new Arrow(ArrowShape.ARROW,Color.BLACK)));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_LEFT, new Arrow(ArrowShape.ARROW,Color.BLACK)));

        edgeTargetArrowMapping.add(new ObjectMapping
				   (EDGE_NEXT, new Arrow(ArrowShape.ARROW,Color.BLUE)));

        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_CONTAINS, new Arrow(ArrowShape.DIAMOND,Color.BLACK)));

        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_STEP, new Arrow(ArrowShape.ARROW,Color.GREEN)));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_SPECIESOF, Arrow.NONE));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_physicalInteraction, Arrow.NONE));


	// edge line type
	/*edgeLineTypeMapping.add(new ObjectMapping
				(EDGE_ACTIVATION, LineType.LINE_1));

	edgeLineTypeMapping.add(new ObjectMapping
				(EDGE_LEFT, LineType.LINE_1));

	edgeLineTypeMapping.add(new ObjectMapping
				(EDGE_RIGHT, LineType.LINE_1));*/

        edgeLineTypeMapping.add(new ObjectMapping
                                 (EDGE_CATALYSIS_UNKNOWN, LineStyle.LONG_DASH));
        edgeLineTypeMapping.add(new ObjectMapping
                                 (EDGE_MODULATION_UNKNOWN, LineStyle.LONG_DASH));
        edgeLineTypeMapping.add(new ObjectMapping
                                 (EDGE_REFERENCE, LineStyle.LONG_DASH));

    }

    private static BioPAXVisualStyleDefinition instance;

    public static BioPAXVisualStyleDefinition getInstance() {
	if (instance == null)
	    instance = new BioPAXVisualStyleDefinition();
	return instance;
    }
}
