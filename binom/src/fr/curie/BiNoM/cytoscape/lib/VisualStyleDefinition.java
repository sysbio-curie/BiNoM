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
import cytoscape.visual.*;

abstract public class VisualStyleDefinition {

    protected VisualStyleDefinition(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public String getNodeAttribute() {
	return nodeAttr;
    }

    public String getEdgeAttribute() {
	return edgeAttr;
    }

    public Vector getNodeShapeMapping() {
	return nodeShapeMapping;
    }

    public Vector getNodeImageMapping() {
	return nodeImageMapping;
    }

    public Vector getNodeSizeMapping() {
	return nodeSizeMapping;
    }

    public Vector getNodeColorMapping() {
	return nodeColorMapping;
    }

    public Vector getNodeBorderColorMapping() {
	return nodeBorderColorMapping;
    }

    public Vector getNodeBorderLineStyleMapping() {
	return nodeBorderLineStyleMapping;
    }
    
    public Vector getNodeBorderLineWidthMapping() {
    	return nodeBorderLineWidthMapping;
    }

    public Vector getEdgeSourceArrowMapping() {
	return edgeSourceArrowMapping;
    }

    public Vector getEdgeTargetArrowMapping() {
	return edgeTargetArrowMapping;
    }

    public Vector getEdgeLineTypeMapping() {
	return edgeLineTypeMapping;
    }

    public Vector getEdgeLineColorMapping() {
	return edgeLineColorMapping;
    }

    public NodeShape getDefaultNodeShape() {
	return defaultNodeShape;
    }

    public Double getDefaultNodeSize() {
	return defaultNodeSize;
    }

    public Color getDefaultNodeColor() {
	return defaultNodeColor;
    }

    public LineStyle getDefaultNodeBorderLineStyle() {
	return defaultNodeBorderLineStyle;
    }
    
    public int getDefaultNodeBorderLineWidth() {
    	return defaultNodeBorderLineWidth;
    }
    

    public Color getDefaultNodeBorderColor() {
	return defaultNodeBorderColor;
    }

    public Arrow getDefaultEdgeSourceArrow() {
	return defaultEdgeSourceArrow;
    }

    public Arrow getDefaultEdgeTargetArrow() {
	return defaultEdgeTargetArrow;
    }

    public LineStyle getDefaultEdgeLineType() {
	return defaultEdgeLineType;
    }

    public Color getDefaultEdgeLineColor() {
	return defaultEdgeLineColor;
    }

    public static class ObjectMapping {
	private String attrValue;
	private Object mappingValue;

	public ObjectMapping(String attrValue, Object mappingValue) {
	    this.attrValue = attrValue;
	    this.mappingValue = mappingValue;
	}

	public String getAttributeValue() {
	    return attrValue;
	}

	public Object getMappingValue() {
	    return mappingValue;
	}
    }

    public static class ImageDefinition {
	public String img;
	public double scale;

	public ImageDefinition(String img, double scale) {
	    this.img = img;
	    this.scale = scale;
	}

	public ImageDefinition(String img) {
	    this(img, 1.0);
	}
    }

    protected String name;

    protected String nodeAttr;
    protected String edgeAttr;

    protected NodeShape defaultNodeShape;
    protected Double defaultNodeSize;
    protected Color defaultNodeColor;
    protected LineStyle defaultNodeBorderLineStyle;
    protected int defaultNodeBorderLineWidth;
    protected Color defaultNodeBorderColor;

    protected Arrow defaultEdgeSourceArrow;
    protected Arrow defaultEdgeTargetArrow;
    protected LineStyle defaultEdgeLineType;
    protected Color defaultEdgeLineColor;

    protected Vector nodeShapeMapping = new Vector();
    protected Vector nodeImageMapping = new Vector();
    protected Vector nodeSizeMapping = new Vector();
    protected Vector nodeColorMapping = new Vector();
    protected Vector nodeBorderColorMapping = new Vector();
    protected Vector nodeBorderLineStyleMapping = new Vector();
    protected Vector nodeBorderLineWidthMapping = new Vector();

    protected Vector edgeSourceArrowMapping = new Vector();
    protected Vector edgeTargetArrowMapping = new Vector();
    protected Vector edgeLineTypeMapping = new Vector();
    protected Vector edgeLineColorMapping = new Vector();
}
