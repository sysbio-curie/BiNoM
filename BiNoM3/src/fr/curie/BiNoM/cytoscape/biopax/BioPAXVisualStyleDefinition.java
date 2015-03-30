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
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.presentation.property.ArrowShapeVisualProperty;
import org.cytoscape.view.presentation.property.LineTypeVisualProperty;;


public class BioPAXVisualStyleDefinition
    extends fr.curie.BiNoM.cytoscape.lib.VisualStyleDefinition {
	
	 public static class Arrow{
		 
		public static Arrow NONE = null;
		
		org.cytoscape.view.presentation.property.values.ArrowShape avs;
		Color c;
		
    	public Arrow(org.cytoscape.view.presentation.property.values.ArrowShape avs, Color c){
    		this.avs = avs;
    		this.c=c;
    		
    	}
    	
    	public Color getColor(){
    		return c;
    	}
    	
    	public org.cytoscape.view.presentation.property.values.ArrowShape getShape(){
    		return avs;
    	}
    }

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
  public static final String NODE_PHYSICAL_INTERACTION = "PhysicalInteraction";
  public static final String NODE_GENETIC_INTERACTION = "GeneticInteraction";
  public static final String NODE_CONTROL = "Control";
  public static final String NODE_CATALYSIS = "Catalysis";
  public static final String NODE_MODULATION = "Modulation";
  public static final String NODE_CONVERSION = "Conversion";
  public static final String NODE_TRANSPORT = "Transport";
  public static final String NODE_BIOCHEMICAL_REACTION = "BiochemicalReaction";
  public static final String NODE_TEMPLATE_REACTION = "TemplateReaction";
  public static final String NODE_TRANSPORT_WITH_BIOCHEMICAL_REACTION = "TransportWithBiochemicalReaction";
  public static final String NODE_COMPLEX_ASSEMBLY = "ComplexAssembly";

  public static final String NODE_PHYSICAL_ENTITY = "PhysicalEntity";
  public static final String NODE_COMPLEX = "Complex";
  public static final String NODE_DNA = "Dna";
  public static final String NODE_DNAREGION = "DnaRegion";
  public static final String NODE_GENE = "Gene";
  public static final String NODE_RNA = "Rna";
  public static final String NODE_RNAREGION = "RnaRegion";
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
    public static final String EDGE_TEMPLATEREACTIONREGULATION_ACTIVATION = "TEMPLATEREACTIONREGULATION_ACTIVATION";
    public static final String EDGE_TEMPLATEREACTIONREGULATION_INHIBITION = "TEMPLATEREACTIONREGULATION_INHIBITION";
    public static final String EDGE_TEMPLATEREACTIONREGULATION_UNKNOWN = "TEMPLATEREACTIONREGULATION_UNKNOWN";

    public static final String EDGE_INHIBITION = "INHIBITION";
    public static final String EDGE_CONTAINS = "CONTAINS";
    public static final String EDGE_STEP = "STEP";
    public static final String EDGE_NEXT = "NEXT";
    public static final String EDGE_LEFT = "LEFT";
    public static final String EDGE_RIGHT = "RIGHT";
    public static final String EDGE_SPECIESOF = "SPECIESOF";
    public static final String EDGE_physicalInteraction = "PhysicalInteraction";
    public static final String EDGE_geneticInteraction = "GeneticInteraction";
    public static final String EDGE_REFERENCE = "REFERENCE";
    public static final String EDGE_INTERSECTION = "INTERSECTION";


    @SuppressWarnings("unchecked")
	private BioPAXVisualStyleDefinition() {
	super("BiNoM BioPAX");
	
	nodeAttr = NODE_ATTR;
	edgeAttr = EDGE_ATTR;

	// default node values
	defaultNodeShape = NodeShapeVisualProperty.ROUND_RECTANGLE; //new Byte(ShapeNodeRealizer.ROUND_RECT);
	defaultNodeSize = new Double(30);
	defaultNodeColor = Color.WHITE;
	//defaultNodeBorderLineType = LineType.LINE_1;
	// Changed in Cytoscape 2.7.0
	defaultNodeBorderLineStyle = LineTypeVisualProperty.SOLID;
	defaultNodeBorderLineWidth = 1;
	defaultNodeBorderColor = Color.BLACK;
	
	// mapping node values

	// node shapes
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_INTERACTION,
                            		 NodeShapeVisualProperty.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_PHYSICAL_INTERACTION,
                            		 NodeShapeVisualProperty.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
        					(NODE_GENETIC_INTERACTION,
        							NodeShapeVisualProperty.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_CONTROL,
                            		 NodeShapeVisualProperty.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_CONVERSION,
                            		 NodeShapeVisualProperty.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_CATALYSIS,
                            		 NodeShapeVisualProperty.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_MODULATION,
                            		 NodeShapeVisualProperty.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_BIOCHEMICAL_REACTION,
                            		 NodeShapeVisualProperty.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                			(NODE_TEMPLATE_REACTION,
                					 NodeShapeVisualProperty.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_TRANSPORT,
                            		 NodeShapeVisualProperty.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_TRANSPORT_WITH_BIOCHEMICAL_REACTION,
                            		 NodeShapeVisualProperty.DIAMOND));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_COMPLEX_ASSEMBLY,
                            		 NodeShapeVisualProperty.DIAMOND));

        nodeShapeMapping.add(new ObjectMapping
                             (NODE_PHYSICAL_ENTITY,
                            		 NodeShapeVisualProperty.ROUND_RECTANGLE));
	nodeShapeMapping.add(new ObjectMapping
			     (NODE_COMPLEX,
			    		 NodeShapeVisualProperty.ROUND_RECTANGLE));
	nodeShapeMapping.add(new ObjectMapping
			     (NODE_PROTEIN,
			    		 NodeShapeVisualProperty.ROUND_RECTANGLE));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_RNA,
                            		 NodeShapeVisualProperty.PARALLELOGRAM));
        nodeShapeMapping.add(new ObjectMapping
                (NODE_RNAREGION,
               		 NodeShapeVisualProperty.PARALLELOGRAM));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_DNA,
                            		 NodeShapeVisualProperty.RECTANGLE));
        nodeShapeMapping.add(new ObjectMapping
                (NODE_DNAREGION,
               		 NodeShapeVisualProperty.RECTANGLE));
        nodeShapeMapping.add(new ObjectMapping
                (NODE_GENE,
               		 NodeShapeVisualProperty.RECTANGLE));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_SMALL_MOLECULE,
                            		 NodeShapeVisualProperty.ELLIPSE));

        nodeShapeMapping.add(new ObjectMapping
                             (NODE_PATHWAY,
                            		 NodeShapeVisualProperty.OCTAGON));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_PATHWAY_STEP,
                            		 NodeShapeVisualProperty.TRIANGLE));
        nodeShapeMapping.add(new ObjectMapping
                             (NODE_PUBLICATION,
                            		 NodeShapeVisualProperty.HEXAGON));

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
        					(NODE_GENETIC_INTERACTION, new Double(20)));
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
        					(NODE_TEMPLATE_REACTION, new Double(20)));
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
                			(NODE_RNAREGION, new Double(40)));
        nodeSizeMapping.add(new ObjectMapping
                			(NODE_DNAREGION, new Double(40)));
        nodeSizeMapping.add(new ObjectMapping
                		    (NODE_GENE, new Double(40)));
        nodeSizeMapping.add(new ObjectMapping
                            (NODE_SMALL_MOLECULE, new Double(30)));


	// node color
        nodeColorMapping.add(new ObjectMapping
                            (NODE_INTERACTION, Color.gray));
        nodeColorMapping.add(new ObjectMapping
                            (NODE_PHYSICAL_INTERACTION, Color.gray));
        nodeColorMapping.add(new ObjectMapping
                		    (NODE_GENETIC_INTERACTION, Color.pink));
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
                			(NODE_TEMPLATE_REACTION, Color.red));
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
                			(NODE_DNAREGION, Color.WHITE));
        nodeColorMapping.add(new ObjectMapping
        					 (NODE_GENE, Color.WHITE));
        nodeColorMapping.add(new ObjectMapping
                             (NODE_RNA, Color.WHITE));
        nodeColorMapping.add(new ObjectMapping
                			(NODE_RNAREGION, Color.WHITE));
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
				      (NODE_BIOCHEMICAL_REACTION, LineTypeVisualProperty.SOLID));
	nodeBorderLineWidthMapping.add(new ObjectMapping
		      		  (NODE_BIOCHEMICAL_REACTION, 1));
	nodeBorderLineStyleMapping.add(new ObjectMapping
		      	      (NODE_TEMPLATE_REACTION, LineTypeVisualProperty.SOLID));
	nodeBorderLineWidthMapping.add(new ObjectMapping
				      (NODE_TEMPLATE_REACTION, 1));
	

	nodeBorderLineStyleMapping.add(new ObjectMapping
				      (NODE_COMPLEX, LineTypeVisualProperty.SOLID));
        nodeBorderLineWidthMapping.add(new ObjectMapping
                                      (NODE_COMPLEX, 2));

    	nodeBorderLineStyleMapping.add(new ObjectMapping
			      (NODE_PATHWAY, LineTypeVisualProperty.SOLID));

    	nodeBorderLineWidthMapping.add(new ObjectMapping
                                (NODE_PATHWAY, 2));
    	nodeBorderLineWidthMapping.add(new ObjectMapping
                				(NODE_DNAREGION, 2));
    	nodeBorderLineWidthMapping.add(new ObjectMapping
                				(NODE_RNAREGION, 2));

        
	// node border color
	nodeBorderColorMapping.add(new ObjectMapping
				   (NODE_BIOCHEMICAL_REACTION, Color.black));
	nodeBorderColorMapping.add(new ObjectMapping
			   	   (NODE_TEMPLATE_REACTION, Color.black));

	nodeBorderColorMapping.add(new ObjectMapping
				   (NODE_COMPLEX, Color.BLACK));

	nodeBorderColorMapping.add(new ObjectMapping
				   (NODE_PROTEIN, Color.BLACK));

	// default edge values
	defaultEdgeSourceArrow = Arrow.NONE; 
	defaultEdgeTargetArrow = new Arrow(ArrowShapeVisualProperty.ARROW, Color.GRAY);
	defaultEdgeLineType = LineTypeVisualProperty.SOLID;
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
                				 (EDGE_geneticInteraction, Color.pink));

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
                				 (EDGE_TEMPLATEREACTIONREGULATION_UNKNOWN, Color.GREEN));
        edgeLineColorMapping.add(new ObjectMapping
                				 (EDGE_TEMPLATEREACTIONREGULATION_ACTIVATION, new Color(128,0,0)));
        edgeLineColorMapping.add(new ObjectMapping
                				 (EDGE_TEMPLATEREACTIONREGULATION_INHIBITION, new Color(0,0,128)));
        
        
        edgeLineColorMapping.add(new ObjectMapping
                                 (EDGE_REFERENCE, Color.gray));
        edgeLineColorMapping.add(new ObjectMapping
                (EDGE_INTERSECTION, Color.CYAN));
	
	
	
    	// edge target arrow
        edgeTargetArrowMapping.add(new ObjectMapping
        						   (EDGE_ACTIVATION, new Arrow(ArrowShapeVisualProperty.CIRCLE,Color.RED)));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_INHIBITION, new Arrow(ArrowShapeVisualProperty.T,Color.BLUE)));
        edgeTargetArrowMapping.add(new ObjectMapping
				                   (EDGE_LEFT, new Arrow(ArrowShapeVisualProperty.ARROW,Color.BLACK)));

        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_CATALYSIS_UNKNOWN, new Arrow(ArrowShapeVisualProperty.CIRCLE,Color.GREEN)));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_CATALYSIS_ACTIVATION, new Arrow(ArrowShapeVisualProperty.CIRCLE,Color.RED)));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_CATALYSIS_INHIBITION, new Arrow(ArrowShapeVisualProperty.T,Color.BLUE)));

        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_MODULATION_UNKNOWN, new Arrow(ArrowShapeVisualProperty.CIRCLE,Color.GREEN)));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_MODULATION_ACTIVATION, new Arrow(ArrowShapeVisualProperty.CIRCLE,Color.MAGENTA)));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_MODULATION_INHIBITION, new Arrow(ArrowShapeVisualProperty.T,Color.PINK)));

        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_CONTROL_UNKNOWN, new Arrow(ArrowShapeVisualProperty.CIRCLE,Color.GREEN)));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_CONTROL_ACTIVATION, new Arrow(ArrowShapeVisualProperty.CIRCLE,new Color(128,0,0))));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_CONTROL_INHIBITION, new Arrow(ArrowShapeVisualProperty.T,new Color(0,0,128))));

        edgeTargetArrowMapping.add(new ObjectMapping
                				    (EDGE_TEMPLATEREACTIONREGULATION_UNKNOWN, new Arrow(ArrowShapeVisualProperty.CIRCLE,Color.GREEN)));
        edgeTargetArrowMapping.add(new ObjectMapping
                				    (EDGE_TEMPLATEREACTIONREGULATION_ACTIVATION, new Arrow(ArrowShapeVisualProperty.CIRCLE,new Color(128,0,0))));
        edgeTargetArrowMapping.add(new ObjectMapping
                					(EDGE_TEMPLATEREACTIONREGULATION_INHIBITION, new Arrow(ArrowShapeVisualProperty.T,new Color(0,0,128))));
        
        
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_REFERENCE, Arrow.NONE));
        edgeTargetArrowMapping.add(new ObjectMapping
                (EDGE_INTERSECTION, new Arrow(ArrowShapeVisualProperty.NONE,Color.CYAN)));

        edgeTargetArrowMapping.add(new ObjectMapping
                (EDGE_RIGHT, new Arrow(ArrowShapeVisualProperty.ARROW,Color.BLACK)));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_LEFT, new Arrow(ArrowShapeVisualProperty.ARROW,Color.BLACK)));

        edgeTargetArrowMapping.add(new ObjectMapping
				   (EDGE_NEXT, new Arrow(ArrowShapeVisualProperty.ARROW,Color.BLUE)));

        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_CONTAINS, new Arrow(ArrowShapeVisualProperty.DIAMOND,Color.BLACK)));

        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_STEP, new Arrow(ArrowShapeVisualProperty.ARROW,Color.GREEN)));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_SPECIESOF, Arrow.NONE));
        edgeTargetArrowMapping.add(new ObjectMapping
                                   (EDGE_physicalInteraction, Arrow.NONE));
        edgeTargetArrowMapping.add(new ObjectMapping
                				   (EDGE_geneticInteraction, Arrow.NONE));


	// edge line type
	/*edgeLineTypeMapping.add(new ObjectMapping
				(EDGE_ACTIVATION, LineType.LINE_1));

	edgeLineTypeMapping.add(new ObjectMapping
				(EDGE_LEFT, LineType.LINE_1));

	edgeLineTypeMapping.add(new ObjectMapping
				(EDGE_RIGHT, LineType.LINE_1));*/

        edgeLineTypeMapping.add(new ObjectMapping
                                 (EDGE_CATALYSIS_UNKNOWN, LineTypeVisualProperty.LONG_DASH));
        edgeLineTypeMapping.add(new ObjectMapping
                                 (EDGE_MODULATION_UNKNOWN, LineTypeVisualProperty.LONG_DASH));
        edgeLineTypeMapping.add(new ObjectMapping
                                 (EDGE_REFERENCE, LineTypeVisualProperty.LONG_DASH));

    }

    private static BioPAXVisualStyleDefinition instance;

    public static BioPAXVisualStyleDefinition getInstance() {
	if (instance == null)
	    instance = new BioPAXVisualStyleDefinition();
	return instance;
    }
    
    
}
