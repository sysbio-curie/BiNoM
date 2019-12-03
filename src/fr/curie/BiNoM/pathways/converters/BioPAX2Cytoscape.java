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
		try {
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
			//String prefix = "c:/datas/binomtest/NCI_example";
			//String prefix = "c:/datas/binomtest/BioPAX3/complex";
			//String prefix = "c:/datas/binomtest/BioPAX3/simplest";
			//String prefix = "c:/datas/binomtest/BioPAX3/Apoptosome";
			//String prefix = "c:/datas/binomtest/BioPAX3/6examples/biopax3-template-reaction1";
			String prefix = "c:/datas/reactome/pathway100";

			if (args.length>0) {
				prefix = args[0];
				if(prefix.toLowerCase().endsWith(".owl"))
					prefix = prefix.substring(0,prefix.length()-4);
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
			
			BioPAXToCytoscapeConverter.Graph grpr = b2c.convert(BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION, prefix + ".owl", options);
			XGMML.saveToXGMML(grpr.graphDocument, prefix + "_reaction.xgmml");
			
			BioPAXToCytoscapeConverter.Graph grp = b2c.convert(BioPAXToCytoscapeConverter.PATHWAY_STRUCTURE_CONVERSION, prefix + ".owl", options);
			XGMML.saveToXGMML(grp.graphDocument, prefix + "_pathways.xgmml");

			BioPAXToCytoscapeConverter.Graph grpp = b2c.convert(BioPAXToCytoscapeConverter.INTERACTION_CONVERSION, prefix + ".owl", new BioPAXToCytoscapeConverter.Option());
			XGMML.saveToXGMML(grpp.graphDocument, prefix + "_proteins.xgmml");


		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
