package fr.curie.BiNoM.pathways.test;

import java.io.*;
import java.util.*;
import org.xml.sax.*;
import org.apache.xerces.parsers.*;
import org.xml.sax.helpers.DefaultHandler;

import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;

public class testSAX{

public static void main(String args[]){

try{
  GraphXGMMLParser gp = new GraphXGMMLParser();
  Date tm = new Date();
  //gp.parse(new FileInputStream("c:/datas/biopax/reactomerel19/hs2.xgmml"));
  //gp.parse("c:/datas/biopax/reactomerel19/homosapiens_global.xgmml");
  //gp.parse("c:/datas/biopax/reactomerel19/apoptosis_global.xgmml");
  gp.parse("c:/datas/biopax/reactomerel19/test_global.xgmml");
  Utils.printUsedMemory();

  System.out.println(gp.graph.Nodes.size()+" nodes, "+gp.graph.Edges.size()+" edges parsed");
  //for(int i=0;i<gp.graph.Nodes.size();i++)
  //  System.out.println((i+1)+":"+((Node)gp.graph.Nodes.elementAt(i)).Id);
  XGMML.saveToXGMML(XGMML.convertGraphToXGMML(gp.graph),"c:/datas/biopax/reactomerel19/temp.xgmml");


}catch(Exception e){
  e.printStackTrace();
}

}

}