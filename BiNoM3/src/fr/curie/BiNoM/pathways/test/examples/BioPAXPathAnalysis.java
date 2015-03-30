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

public class BioPAXPathAnalysis{

  public static void main(String[] args) {

    try{

    String prefix = "c:/temp2/Apoptosis";

    if(args.length>0){
      prefix = args[0];
      if(prefix.toLowerCase().endsWith(".owl"))
        prefix = prefix.substring(0,prefix.length()-4);
    }

	// This example reads BioPAX, generate its index, 
	// and find all paths in the network between two particular proteins
	// and saves the result as new BioPAX file
	
	BioPAXToCytoscapeConverter.Graph graphContainer = 
	  BioPAXToCytoscapeConverter.convert(
             BioPAXToCytoscapeConverter.FULL_INDEX_CONVERSION,
             prefix+".owl",
	     "Apoptosis",
             new BioPAXToCytoscapeConverter.Option()
          );

	GraphDocument grdoc = graphContainer.graphDocument;
	Graph grph = XGMML.convertXGMMLToGraph(grdoc);
	
	BioPAXGraphQueryEngine beng = new BioPAXGraphQueryEngine();
	beng.setDatabase(grph);
	beng.prepareDatabaseCopyForIndexPathAnalysis();
	
	Vector sources = new Vector(); sources.add("MCH5"); sources.add("FAS");
	Vector targets = new Vector(); targets.add("FAS");  targets.add("MCH5");
	
	StructureAnalysisUtils.Option options = new StructureAnalysisUtils.Option();
	options.pathFindMode = options.ALL_PATHS;
	options.searchRadius = 10;
	
	Set SelectedNodes = StructureAnalysisUtils.findPaths(beng.databaseCopyForPathAnalysis, sources, targets, options);
	
	Graph gr = new Graph();
	Iterator sn = SelectedNodes.iterator();
	while(sn.hasNext()){
		Node n = grph.getNode((String)(sn.next()));
		System.out.println(n.Id);
		gr.addNode(n);
	}
	gr.addConnections(grph);
	
	Model newbiopax = BioPAXUtilities.extractFromModel(graphContainer.biopax.model, gr);
	BioPAXUtilities.saveModel(newbiopax, prefix+"_path.owl");


    }catch(Exception e){
      e.printStackTrace();
    }

  }


}
