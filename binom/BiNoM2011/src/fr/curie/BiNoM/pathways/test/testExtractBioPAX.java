package fr.curie.BiNoM.pathways.test;

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


public class testExtractBioPAX {
  public static void main(String[] args) {
    try{

      String prefix = "Notch";

      BioPAX biopax = new BioPAX();
      biopax.loadBioPAX(prefix+".owl");

      BioPAX biopax_out = new BioPAX();
      biopax_out.model = ModelFactory.createDefaultModel();

      List lst = biopax_DASH_level2_DOT_owlFactory.getAllcomplex(biopax.model);
      complex comp = (complex)lst.get(0);

      System.out.println("Complex "+comp.uri());

      Resource r = comp.resource();

      //biopax_out.model.createResource(r);

      biopax_DASH_level2_DOT_owlFactory.createcomplex(r,biopax_out.model);

      biopax_out.saveToFile(prefix+"_out.owl",biopax_out.model);

    }catch(Exception e){
      e.printStackTrace();
    }
  }
}