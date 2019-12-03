package fr.curie.BiNoM.pathways.test;

import java.io.*;
import java.util.*;

import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.shared.*;

import org.apache.xmlbeans.*;
import edu.rpi.cs.xgmml.*;


public class testGraphMappingService {
  public static void main(String[] args) {

    try{
    BioPAXGraphMappingService bgms = new BioPAXGraphMappingService();
    //String prefix = "Apoptosis";
    //String prefix = "example";
    //String prefix0 = "apoptosis";
    //String prefix0 = "test";
    //String prefix0 = "homosapiens";
    //String prefix0 = "reaction_semantic_full";
    //String prefix0 = "rb_network";
    //String prefix = "c:/datas/biopax/reactomerel19/"+prefix0;
    //String prefix = "c:/datas/biopax/reactome21/"+prefix0;
    //prefix = "c:/datas/calzone/040407";
    //prefix = "c:/datas/binomtest/"+prefix0;
    //String prefix = "testrb";
    //String prefix0 = "2103";
    //String prefix0 = "2903final";
    //String prefix0 = "test";
    //String prefix = "c:/datas/calzone/"+prefix0;
    //String prefix = "c:/datas/biobase/testrb/"+prefix0;
    //String prefix0 = "reaction_semantic_full";
    //String prefix = "c:/datas/biobase/biopax/"+prefix0;
    //String prefix0 = "2903final";
    //String prefix = "c:/myprograms/binom2007/test/"+prefix0;
    
    //String prefix0 = "homosapiens";
    //String prefix0 = "dna_repair";
    //String prefix0 = "mapk_reactome";
    //String prefix = "c:/datas/biopax/ReactomeApril2008/"+prefix0;
    //String prefix = "c:/docs/tutorials/Pathway charting/BioBaseSemantic/"+prefix0;
    //String prefix0 = "NCINature_1";
    //String prefix0 = "temp";
    //String prefix0 = "Ceramide";
    //String prefix = "c:/datas/biopax/naturenci/"+prefix0;
    
    //String prefix0 = "biopax";
    //String prefix = "c:/datas/biopax/HumanCyc/"+prefix0;
    //String prefix0 = "reaction_pathway_full";
    //String prefix0 = "reaction_evidence_full";
    //String prefix = "c:/Datas/BioBase/ver9/index/"+prefix0;
    
    String prefix0 = "NCI_example";
    String prefix = "c:/Datas/binomtest/"+prefix0;
    

    BioPAX biopax = new BioPAX();
    biopax.idName = prefix0;
    System.out.println("Loading BioPAX...");
    biopax.loadBioPAX(prefix+".owl");
    Utils.printUsedMemory();
    //System.exit(0);
    System.out.println("Mapping BioPAX...");
    Graph graph = bgms.mapBioPAXToGraph(biopax);
    Utils.printUsedMemory();

    //System.out.println("Converting graphdocument to graph...");
    //Graph gr = XGMML.convertXGMMLToGraph(graph);
    //Utils.printUsedMemory();    
    
    System.out.println("Saving graph...");
    graph.saveAsCytoscapeXGMML(prefix+".xgmml");
    
    Utils.printUsedMemory();    

    //XGMML.saveToXGMML(graph,prefix+".xgmml");

    }catch(Exception e){
      e.printStackTrace();
    }
  }
}