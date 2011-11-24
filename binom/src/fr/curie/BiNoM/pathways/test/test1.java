package fr.curie.BiNoM.pathways.test;

import java.io.*;
import java.util.*;

import fr.curie.BiNoM.pathways.biopax.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.shared.*;


public class test1 {
  public static void main(String[] args) {
    try{
    Model model = ModelFactory.createDefaultModel();
    model.read(new FileInputStream("test.owl"),"");
    List catlist  = biopax_DASH_level2_DOT_owlFactory.getAllcatalysis(model);
    Iterator it = catlist.iterator();
    while(it.hasNext()){
      catalysis ct = (catalysis)it.next();
      System.out.println(ct.uri());
      System.out.println(ct.getCONTROLLER().uri());

      System.out.println(ct.toString());

      if(ct.getCONTROLLED_asconversion()!=null)
        System.out.println("asconv - "+ct.getCONTROLLED_asconversion().uri());
      if(ct.getCONTROLLED_asinteraction()!=null)
        System.out.println("asinter - "+ct.getCONTROLLED_asinteraction().uri());
      if(ct.getCONTROLLED_aspathway()!=null)
        System.out.println("aspathw - "+ct.getCONTROLLED_aspathway().uri());

      // Some checking of the RDF content
      Resource res = model.getResource(ct.uri());
      StmtIterator si = res.listProperties();
      while(si.hasNext()){
        Statement st = si.nextStatement();
        System.out.println(st.toString());
      }
      /*System.out.println("--------------------");
      catalysis ct1 = biopax_DASH_level2_DOT_owlFactory.getcatalysis(ct.uri(),model);
      System.out.println(ct1.getCONTROLLER().uri());
      System.out.println(ct1.getDIRECTION());
      List lst = ct1.listStatements();
      Iterator lsti = lst.iterator();
      while(lsti.hasNext()){
        Statement st = (Statement)lsti.next();
        System.out.println(st.toString());
      }*/
    }
    }catch(Exception e){
      e.printStackTrace();
    }

  }
}