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


package fr.curie.BiNoM.pathways.converters;

import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import giny.view.NodeView;

import java.io.*;
import java.util.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.shared.*;

import cytoscape.CyNode;

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;
import edu.rpi.cs.xgmml.*;

public class BioPAX2CellDesigner {

  public static void main(String[] args) {

    try{

    //String prefix = "dna_replication";
    //String prefix = "BIOMD0000000012";
    //String prefix = "Notch";
    //String prefix = "lxx00230";
    //String prefix = "Nfkb_simplest_plus";
    //String prefix = "Notch";
    //String prefix = "lxx00230";
    //String prefix = "c:/datas/binomtest/apoptosis";
    //String prefix = "c:/datas/biopax/cellmap/notch";
    //String prefix = "Notch_signaling_pathway";
    //String prefix = "biopax-example-short-pathway";
    //String prefix = "test";
    //String prefix = "rb-e2f";
    //String prefix = "egfr";
    //String prefix = "M-Phase2";
    //String prefix = "testrb";
    String prefix = "c:/datas/binomtest/temp";
    //String prefix = "c:/datas/binomtest/Apoptosis";

    if(args.length>0){
      prefix = args[0];
      if(prefix.toLowerCase().endsWith(".owl"))
        prefix = prefix.substring(0,prefix.length()-4);
    }

    BioPAXToCellDesignerConverter b2s = new BioPAXToCellDesignerConverter();

    b2s.biopax.model = ModelFactory.createDefaultModel();
    b2s.biopax.loadBioPAX(prefix+".owl");
    b2s.populateSbml();
    //CellDesigner.saveCellDesigner(b2s.sbmlDoc,prefix+"_interm.xml");
    
    b2s.sbmlDoc = CellDesigner.loadCellDesigner(prefix+"_interm.xml");
    b2s.convertToCellDesigner();
    CellDesigner.saveCellDesigner(b2s.sbmlDoc,prefix+"_celldesigner.xml");

    }catch(Exception e){
      e.printStackTrace();
    }


  }


}
