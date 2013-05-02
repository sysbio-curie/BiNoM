package fr.curie.BiNoM.pathways.test;

import java.util.*;
import java.io.*;

import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import edu.rpi.cs.xgmml.*;
import org.apache.xmlbeans.*;


public class testStructureAnalysis {
  public static void main(String[] args) {
    try{

      String prefix = "c:/datas/binomtest/";
    	//String prefix = "c:/datas/simon/";
      String path = prefix+"test";

      GraphDocument gr = XGMML.loadFromXMGML(path+".xgmml");
      Graph graph = XGMML.convertXGMMLToGraph(gr);
      System.out.println(gr.getGraph().getNodeArray().length+"/"+gr.getGraph().getEdgeArray().length+" -> "+graph.Nodes.size()+"/"+graph.Edges.size());
      Vector<GraphDocument> vg = StructureAnalysisUtils.getStronglyConnectedComponents(gr);
      Vector<Vector<GraphDocument>> vv = new Vector<Vector<GraphDocument>>(); 
      for(int i=0;i<vg.size();i++){
    	  Vector<GraphDocument> vgi = StructureAnalysisUtils.getStronglyConnectedComponents(vg.get(i));
          vv.add(vgi);
      }
      for(int i=0;i<vv.size();i++){
    	  Vector<GraphDocument> vgg = vv.get(i);
    	  System.out.println(vg.get(i).getGraph().getNodeArray().length+" nodes");
    	  for(int j=0;j<vgg.size();j++)
    		  System.out.println("\t"+vgg.get(j).getGraph().getNodeArray().length+" nodes");
      }
      System.exit(0);
      
      
      
      /*Vector vv = new Vector();
      Graph grr = BiographUtils.ExcludeIntermediateNodes(graph,vv,true);
      //Graph grr = null;
      XGMML.saveToXGMML(XGMML.convertGraphToXGMML(grr), "c:/datas/binomtest/text/test_.xgmml");
      System.exit(0);*/
      
      Node source = graph.getNode("s1");
      Node target = graph.getNode("P@");
      Vector<Graph> v = GraphAlgorithms.Dijkstra(graph,source,target,true,true,Double.POSITIVE_INFINITY);
      for(int i=0;i<v.size();i++){
    	  Graph gri = v.get(i);
    	  for(int j=0;j<gri.Nodes.size();j++){
    		  System.out.print(((Node)gri.Nodes.get(j)).Id+"\t");
    	  }
    	  System.out.println();
      }
      
      System.exit(0);

      /*Vector grv = StructureAnalysisUtils.getConnectedComponents(gr);
      writeToFiles(grv,path);

      grv = StructureAnalysisUtils.getStronglyConnectedComponents(gr);
      writeToFiles(grv,path);*/

      Vector grv = StructureAnalysisUtils.getMaterialComponents(gr);
      writeToFiles(grv,path);


    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public static void writeToFiles(Vector graphs, String prefix) throws Exception{
    for(int i=0;i<graphs.size();i++){
      GraphDocument gr = (GraphDocument)graphs.get(i);
      String filename = gr.getGraph().getId();
      filename = Utils.correctName(filename);
      XGMML.saveToXGMML(gr,prefix+filename+".xgmml");
    }
  }


}