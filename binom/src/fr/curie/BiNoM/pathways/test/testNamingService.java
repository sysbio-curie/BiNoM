package fr.curie.BiNoM.pathways.test;

import java.io.*;
import java.util.*;

import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.shared.*;


public class testNamingService {
  public static void main(String[] args) {
    try{

      //String fn = "c:/datas/biopax/testset/apoptosis.owl";
      //String fn = "c:/myprograms/binom/nfkb_simplest_plus.owl";
      //String fn = "c:/myprograms/binom/glycolysis.owl";
      //String fn = "c:/datas/binomtest/path_full.owl";
      //String fn = "c:/docs/tutorials/cdbinom/mapk.owl";
    	String fn = "c:/datas/binomtest/mapk_reactome.owl";
      test(fn);

      /*String dir = "c:/datas/biopax/testset/";
      File f = new File(dir);
      File fs[] = f.listFiles();
      for(int i=0;i<fs.length;i++){
        String fn = fs[i].getAbsolutePath();
        if(fn.indexOf("biopax-level")<0)if(fn.endsWith("owl"))
          try{
          System.out.println("\n\n\nFILE : "+fn);
          test(fn);
          }catch(Exception e){
            System.out.println("PROBLEM IN FILE "+fn+"");
            e.printStackTrace();
          }
      }*/

    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public static void test(String fn) throws Exception{
    BioPAX bp = new BioPAX();
    bp.loadBioPAX(fn);
    BioPAXNamingService bpns = new BioPAXNamingService();
    bpns.generateNames(bp,true);
  }

}