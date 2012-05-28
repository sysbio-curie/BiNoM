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

import java.io.*;
import java.util.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.shared.*;

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;
import edu.rpi.cs.xgmml.*;

public class BioPAX2SBML {

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
    //String prefix = "c:/datas/binomtest/il6_7pathway";
    //String prefix = "c:/datas/binomtest/biopax3-short-metabolic-pathway"; 
    //String prefix = "/Users/eric/work/binom/manual_v1.0/Apoptosis";
      String prefix = "c:/datas/binomtest/BioPAX3/simplest";
    	
    if(args.length>0){
      prefix = args[0];
      if(prefix.toLowerCase().endsWith(".owl"))
        prefix = prefix.substring(0,prefix.length()-4);
    }

    BioPAXToSBMLConverter b2s = new BioPAXToSBMLConverter();

    b2s.biopax.model = ModelFactory.createDefaultModel();
    b2s.biopax.loadBioPAX(prefix+".owl");
    //BioPAX.printDump(model);

    //GraphDocument gr = XGMML.loadFromXMGML(prefix+".xgmml");
    
    //Vector species = new Vector();
    //Vector reactions = new Vector();
    
	/*for(int i=0;i<gr.getGraph().getNodeArray().length;i++) {
		GraphicNode n = gr.getGraph().getNodeArray(i);
		AttDocument.Att at = Utils.getFirstAttribute(n, "BIOPAX_SPECIES");
		if(at!=null)
			species.add(n.getId());
		AttDocument.Att at = Utils.getFirstAttribute(n, "BIOPAX_REACTION");
		if(at!=null)
			reactions.add(n.getId());
	}*/
	
	//CytoscapeToBioPAXConverter.filterIDs(b2s.biopax, gr);
    
    
    b2s.populateSbml();
    CellDesigner.saveCellDesigner(b2s.sbmlDoc,prefix+"_sbml.xml");

    }catch(Exception e){
      e.printStackTrace();
    }


  }


}
