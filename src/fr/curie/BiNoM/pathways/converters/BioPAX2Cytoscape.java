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
import fr.curie.BiNoM.pathways.analysis.structure.*;
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
import fr.curie.BiNoM.pathways.*;

public class BioPAX2Cytoscape extends BioPAX2SBML {

    public static void main(String[] args) {
            //String prefix = "example";
            //String prefix = "glycolysis";
            //String prefix = "nfkb_simplest_plus";
             //String prefix = "example";
            //String prefix = "BIOMD0000000012";
            //String prefix = "hsa00030";
            //String prefix = "Apoptosis";
            //String prefix = "c:/datas/biopax/reactomerel19/apoptosis";
          //String prefix = "c:/datas/biobase/biopax/pathway";
		//String prefix = "c:/datas/binomtest/extrinsic";
            //String prefix = "c:/datas/netpath/notch";
            //String prefix = "c:/datas/netpath/hedgehog";
            //String prefix = "Apoptosis";
            //String prefix = "BIOMD0000000012";
            //String prefix = "hsa00030";
		
		//String prefix = "c:/datas/binomtest/apoptosis";
		//String prefix = "c:/docs/newbinom/temp";
		//String prefix = "c:/datas/binomtest/pathway-test";
        String prefix = "c:/datas/binomtest/NCI_example";
        int algo = BioPAXToCytoscapeConverter.PROTEIN_PROTEIN_INTERACTION_CONVERSION;

	    if (args.length>0) {
            try {
                prefix = args[0];
                if(prefix.toLowerCase().endsWith(".owl"))
                    prefix = prefix.substring(0,prefix.length()-4);

                if (args.length > 1) {
                    switch (args[1]) {
                        case "PATHWAY_STRUCTURE_CONVERSION":
                            System.out.println("Algo chosen : pathway structure conversion");
                            algo = BioPAXToCytoscapeConverter.PATHWAY_STRUCTURE_CONVERSION;
                            break;
                        
                        case "FULL_INDEX_CONVERSION":
                            System.out.println("Algo chosen : full index conversion");
                            algo = BioPAXToCytoscapeConverter.FULL_INDEX_CONVERSION;
                            break;

                        case "REACTION_NETWORK_CONVERSION":
                            System.out.println("Algo chosen : reaction network conversion");
                            algo = BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION;
                            break;

                        case "PROTEIN_PROTEIN_INTERACTION_CONVERSION":
                            System.out.println("Algo chosen : protein protein interaction conversion");    
                            algo = BioPAXToCytoscapeConverter.PROTEIN_PROTEIN_INTERACTION_CONVERSION;
                            break;

                        default:
                            algo = BioPAXToCytoscapeConverter.PROTEIN_PROTEIN_INTERACTION_CONVERSION;
                            break;

                    }
                }

	            BioPAXToCytoscapeConverter b2c = new BioPAXToCytoscapeConverter();

                /*BioPAXToCytoscapeConverter.Graph gr = b2c.convert(BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION, prefix + ".owl", new BioPAXToCytoscapeConverter.Option());
                edu.rpi.cs.xgmml.GraphicGraph grf = gr.graphDocument.getGraph();
                edu.rpi.cs.xgmml.GraphicNode nodes[] = grf.getNodeArray();
                edu.rpi.cs.xgmml.GraphicEdge edges[] = grf.getEdgeArray();
                System.out.println("NODES " + nodes.length + " EDGES " + edges.length);
                //XGMML.saveToXMGML(gr.graphDocument,prefix+"_reaction.xgmml");
                XGMML.saveToXGMML(gr.graphDocument, prefix + "_RN.xgmml");*/

                BioPAXToCytoscapeConverter.Option options = new BioPAXToCytoscapeConverter.Option();
                options.includeNextLinks = false;
                BioPAXToCytoscapeConverter.Graph grp = b2c.convert(algo, prefix + ".owl", options);
                XGMML.saveToXGMML(grp.graphDocument, prefix + "_pathways.xgmml");

                //BioPAXToCytoscapeConverter.Graph grpp = b2c.convert(BioPAXToCytoscapeConverter.PROTEIN_PROTEIN_INTERACTION_CONVERSION, prefix + ".owl", new BioPAXToCytoscapeConverter.Option());
                //XGMML.saveToXMGML(grpp.graphDocument, prefix + "_proteins.xgmml");


            } 
            catch(Exception e){
                e.printStackTrace();
            }
        } else {
            
            System.out.println("Usage:\tBioPAX2Cytoscape filename [algo]");
            System.out.println("algo:");
            System.out.println("\t- FULL_INDEX_CONVERSION");
            System.out.println("\t- REACTION_NETWORK_CONVERSION");
            System.out.println("\t- PATHWAY_STRUCTURE_CONVERSION (default)");
            System.out.println("\t- PROTEIN_PROTEIN_INTERACTION_CONVERSION");
            System.out.println("");

            return;
        }
    }
}
