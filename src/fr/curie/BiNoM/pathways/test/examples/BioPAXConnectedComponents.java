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
package fr.curie.BiNoM.pathways.test.examples;

import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import java.io.*;
import java.util.*;

//import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;
import edu.rpi.cs.xgmml.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;

public class BioPAXConnectedComponents{

  public static void main(String[] args) {

    try{

    String prefix = "c:/datas/binomtest/M-phase";

    if(args.length>0){
      prefix = args[0];
      if(prefix.toLowerCase().endsWith(".owl"))
        prefix = prefix.substring(0,prefix.length()-4);
    }

	// This example reads BioPAX, creates reaction network from it, 
	// decomposes the network into connected components,
	// and saves each component to new BioPAX owl file and to SBML format
	
	BioPAXToCytoscapeConverter.Graph graph = 
	  BioPAXToCytoscapeConverter.convert(
             BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION,
             prefix+".owl",
	     "Apoptosis",
             new BioPAXToCytoscapeConverter.Option()
          );

	GraphDocument grdoc = graph.graphDocument;
	
	grdoc = XGMML.loadFromXMGML("c:/datas/binomtest/M-phase2.xgmml");
	
	for(int i=0;i<grdoc.getGraph().getNodeArray().length;i++){
		System.out.println(grdoc.getGraph().getNodeArray()[i].getId());
	}

	//Vector components = StructureAnalysisUtils.getConnectedComponents(grdoc);
	Vector components = StructureAnalysisUtils.getStronglyConnectedComponents(grdoc);

	for(int i=0;i<components.size();i++){
		GraphDocument gri = (GraphDocument)components.get(i);
		Model model = BioPAXUtilities.extractFromModel(graph.biopax.model,gri);
		BioPAXUtilities.saveModel(model,prefix+"_"+(i+1)+".owl");
		
		BioPAXToSBMLConverter conv = new BioPAXToSBMLConverter();
		conv.biopax = new BioPAX();
		conv.biopax.model = model;
		conv.populateSbml();
		CellDesigner.saveCellDesigner(conv.sbmlDoc, prefix+"_"+(i+1)+".xml");
	}


    }catch(Exception e){
      e.printStackTrace();
    }

  }


}
