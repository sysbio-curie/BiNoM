package fr.curie.BiNoM.pathways.test;

import java.io.*;
import java.util.*;

import com.ibm.adtech.jastor.*;
import com.hp.hpl.jena.rdf.model.*;

public class testJastor {

  public testJastor() {
  }
  public static void main(String[] args) {

    try{
    JastorContext ctx = new JastorContext();
    //ctx.addOntologyToGenerate(new FileInputStream("biopax-level2.owl"),"http://www.biopax.org/release/biopax-level2.owl","pathways.biopax");
    ctx.addOntologyToGenerate(new FileInputStream("biopaxmodel.owl"),"http://www.ihes.fr/~zinovyev/biopax/biopaxmodel.owl","pathways.biopaxmodel");
    JastorGenerator gen = new JastorGenerator(new File("gensrc").getCanonicalFile(),ctx);
    gen.run();
    }catch(Exception e){
      e.printStackTrace();
    }

    /*try{

    Model model = ModelFactory.createDefaultModel();
    //model.setNsPrefix("bp","http://www.biopax.org/release/biopax-level2.owl#");
    //biopax_DASH_level2_DOT_owlFactory.createpathway("http://bioinfo.curie.fr/celldesigner/celldesigner2biopax#pathway1",model);

    BioPAX biopax= new BioPAX(BioPAX.biopaxString,"http://bioinfo.curie.fr/celldesigner/celldesigner2biopax#","http://bioinfo.curie.fr/biopaxmodel#");

    pathway path = biopax_DASH_level2_DOT_owlFactory.createpathway(biopax.namespaceString+"#pathway1",biopax.biopaxmodel);
    interaction inter = biopax_DASH_level2_DOT_owlFactory.createinteraction(biopax.namespaceString+"#interaction1",biopax.biopaxmodel);
    inter.setNAME("Interaction_name");
    //pathway path = biopax_DASH_level2_DOT_owlFactory.createpathway("http://bioinfo.curie.fr/celldesigner/celldesigner2biopax#pathway1",model);
    //biopax.biopaxmodel.createIndividual(path.uri(),path.resource());
    path.setNAME("pathway_name");
    path.setSHORT_DASH_NAME("short_name");
    path.addCOMMENT("This is a comment to the pathway");
    path.addPATHWAY_DASH_COMPONENTS(inter);

    protein prot = biopax_DASH_level2_DOT_owlFactory.createprotein(biopax.namespaceString+"protein1",biopax.biopaxmodel);


    BioPAX.saveToFile("test.owl",biopax.biopaxmodel);

    }catch(Exception e){
      e.printStackTrace();
    }*/


  }
}