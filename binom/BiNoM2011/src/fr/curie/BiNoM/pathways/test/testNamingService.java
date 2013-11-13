package fr.curie.BiNoM.pathways.test;

import java.io.*;
import java.util.*;

import org.apache.xmlbeans.XmlObject;

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
    	//String fn = "c:/datas/binomtest/mapk_reactome.owl";
    	//String fn = "c:/datas/reactome/pathway18.owl";
    	//String fn = "c:/datas/binomtest/biopax3/WP615_60842.owl";
    	String fn = "c:/datas/binomtest/biopax3/Apoptosis3.owl";
    	//String fn = "c:/datas/binomtest/biopax3/Apaf1.owl";
    	
    	
        /*BioPAX bp = new BioPAX();
        bp.loadBioPAX(fn);
        
        Map<String, String> prefixes = bp.model.getNsPrefixMap();
        for(String o: prefixes.keySet()){
        	System.out.println(o+"\t"+prefixes.get(o));
        	
        }
        
        List<Protein> proteins = biopax_DASH_level3_DOT_owlFactory.getAllProtein(bp.model);
        //for(int i=0;i<proteins.size();i++){
        for(int i=0;i<1;i++){
        	Protein p = proteins.get(i);
        	
        	List<Statement> list =p.listStatements();
        	for(Statement s: list){
        		System.out.println(s.toString());
        	}
        	
        	//Iterator it = p.getComment();
        	Iterator it = p.getName();
        	while(it.hasNext()){
        		String name = (String)it.next();
        		System.out.println("NAME: "+name);
        	}
        	//System.out.println(p);
        }
        System.exit(0);*/
    	
    	
    	
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