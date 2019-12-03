package fr.curie.BiNoM.pathways.test;

import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import java.io.*;
import java.util.*;

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;
import edu.rpi.cs.xgmml.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;

public class BioPAXConnectedComponents{

  public static void main(String[] args) {

    try{

    String prefix = "c:/datas/binomtest/Apoptosis";

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

	Vector components = StructureAnalysisUtils.getConnectedComponents(grdoc);

	System.out.println(""+BioPAXUtilities.numberOfStatements(graph.biopax.biopaxmodel)+"\t"+BioPAXUtilities.numberOfStatements(graph.biopax.model));
	
	for(int i=0;i<components.size();i++){
		GraphDocument gri = (GraphDocument)components.get(i);
		BioPAX biopax = new BioPAX();		
		biopax.model = BioPAXUtilities.extractFromModel(graph.biopax.model,gri);
		System.out.println(""+BioPAXUtilities.numberOfStatements(biopax.model));
		BioPAXUtilities.saveModel(biopax.model,prefix+"_"+(i+1)+".owl",BioPAX.biopaxString);

		
	}


    }catch(Exception e){
      e.printStackTrace();
    }

  }


}
