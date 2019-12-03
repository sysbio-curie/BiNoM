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

public class BioPAXStandardQuery{

  public static void main(String[] args) {

    try{

    String prefix = "c:/temp2/Apoptosis";

    if(args.length>0){
      prefix = args[0];
      if(prefix.toLowerCase().endsWith(".owl"))
        prefix = prefix.substring(0,prefix.length()-4);
    }

	// This example reads BioPAX, generate its index, 
	// ask to show all complexes and reactions in which two proteins participate
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
	
	Vector names = new Vector();
	Vector IDS = new Vector(); // This is a vector of vectors (each protein can have many identifiers)
	
	names.add("caspase 8"); 
	Vector ids = new Vector(); ids.add("Q14790@uniprot"); //It should be in the form <id>@<database>
	IDS.add(ids);
	
	names.add("CASP10"); 
	ids = new Vector(); 
	IDS.add(ids);
	
	BioPAXGraphQuery query = BioPAXGraphQuery.convertListOfNamesToQuery(names, IDS);
	
	beng.doQuery(query, BioPAXGraphQuery.SELECT_ENTITIES);
	query.input = query.result;
	beng.doQuery(query, BioPAXGraphQuery.ADD_COMPLEXES_EXPAND);
	query.input = query.result;
	beng.doQuery(query, BioPAXGraphQuery.ADD_SPECIES);
	query.input = query.result;
	beng.doQuery(query, BioPAXGraphQuery.ADD_ALL_REACTIONS);
	
	Model newbiopax = BioPAXUtilities.extractFromModel(graphContainer.biopax.model, query.result);
	BioPAXUtilities.saveModel(newbiopax, prefix+"_query.owl");


    }catch(Exception e){
      e.printStackTrace();
    }

  }


}
