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

package fr.curie.BiNoM.pathways.utils;

import java.io.*;
import java.util.*;

import edu.rpi.cs.xgmml.*;


/**
 * deprecated
 *
 */public class XGMMLExport {

  GraphDocument graphDoc = null;
  //GraphDocument.
  edu.rpi.cs.xgmml.GraphicGraph graph = null;

  public XGMMLExport() {
  }

  public static void main(String[] args) {

    try{
    XGMMLExport xge = new XGMMLExport();

    xge.graphDoc = GraphDocument.Factory.newInstance();
    xge.graph = xge.graphDoc.addNewGraph();
    GraphicNode nod1 = xge.graph.addNewNode();
    nod1.setId("n1");
    nod1.setLabel("Node1Label");

    AttDocument.Att attn1 = nod1.addNewAtt();
    attn1.setLabel("BIOPAX_NODE_TYPE");
    attn1.setName("BIOPAX_NODE_TYPE");
    attn1.setValue("catalysis");
    attn1.setType(ObjectType.STRING);

    GraphicNode nod2 = xge.graph.addNewNode();
    nod2.setId("n2");
    nod2.setLabel("Node2Label");

    AttDocument.Att attn2 = nod2.addNewAtt();
    attn2.setLabel("BIOPAX_NODE_TYPE");
    attn2.setName("BIOPAX_NODE_TYPE");
    attn2.setValue("smallMolecule");
    attn2.setType(ObjectType.STRING);

    GraphicNode nod3 = xge.graph.addNewNode();
    nod3.setId("n3");
    nod3.setLabel("Node3Label");

    AttDocument.Att attn3 = nod3.addNewAtt();
    attn3.setLabel("BIOPAX_NODE_TYPE");
    attn3.setName("BIOPAX_NODE_TYPE");
    attn3.setValue("protein");
    attn3.setType(ObjectType.STRING);

    GraphicEdge edge1 = xge.graph.addNewEdge();
    edge1.setId("Edge1");
    edge1.setLabel("Edge1Label");
    edge1.setSource(nod1.getId());
    edge1.setTarget(nod2.getId());

    AttDocument.Att att1 = edge1.addNewAtt();
    att1.setLabel("BIOPAX_EDGE_TYPE");
    att1.setName("BIOPAX_EDGE_TYPE");
    att1.setValue("CONTAINS");
    att1.setType(ObjectType.STRING);


    GraphicEdge edge2 = xge.graph.addNewEdge();
    edge2.setId("Edge2");
    edge2.setLabel("Edge2Label");
    edge2.setSource(nod1.getId());
    edge2.setTarget(nod3.getId());

    AttDocument.Att att2 = edge2.addNewAtt();
    att2.setLabel("BIOPAX_EDGE_TYPE");
    att2.setName("BIOPAX_EDGE_TYPE");
    att2.setValue("ACTIVATION");
    att2.setType(ObjectType.STRING);


    //xge.graph.save(new File("test.xgmml"));
    System.out.println(xge.graphDoc.toString());

    saveToXMGML(xge.graphDoc,"test.xgmml");

    }catch(Exception e){
      e.printStackTrace();
    }

  }

  public static GraphDocument loadFromXMGML(String fn) throws Exception{
    GraphDocument gr = null;
    return gr;
  }

  public static void saveToXMGML(GraphDocument graph, String fn) throws Exception{
    try{
      FileWriter fw = new FileWriter(fn);
      String s = graph.toString();
      s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n"+s;
  fw.write(s);
  fw.close();
}catch(Exception e){
  e.printStackTrace();
}

  }


}