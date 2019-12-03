package fr.curie.BiNoM.pathways.test;

import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import edu.rpi.cs.xgmml.*;
import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;
import java.io.*;
import java.util.*;

import edu.rpi.cs.xgmml.*;
import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;


public class testBioPAXQueryEngine {
  public static void main(String[] args) {
    try{
    	
        Vector names1 = new Vector();
        Vector xrefs1 = new Vector();
        LineNumberReader lr1 = new LineNumberReader(new FileReader("c:/datas/biopax/reactomedec2009/rbegfr"));
        String s1 = null;
        while((s1=lr1.readLine())!=null){
          StringTokenizer st = new StringTokenizer(s1,"\t");
          Vector ids = new Vector();
          //st.nextToken(); 
          names1.add(st.nextToken()); //st.nextToken();
          while(st.hasMoreTokens())
            ids.add(st.nextToken()+"@uniprot");
          xrefs1.add(ids);
        }

      BioPAXGraphQuery q2 = BioPAXGraphQuery.convertListOfNamesToQuery(names1,xrefs1);
      XGMML.saveToXGMML(q2.input, "c:/datas/biopax/reactomedec2009/rbegfr.xgmml");
      
      System.exit(0);
      
      

      /*BioPAXGraphQueryEngine beng2 = new BioPAXGraphQueryEngine();      

      GraphXGMMLParser gp2 = new GraphXGMMLParser();
      //gp2.parse("c:/datas/binomtest/apoptosis.xgmml");
      gp2.parse("c:/datas/binomtest/reaction_semantic_full.xgmml");
      //gp2.parse("c:/datas/binomtest/reaction_pathway_full.xgmml");
      
      beng2.setDatabase(gp2.graph);
      
      System.out.println("Preparing database...");
      beng2.prepareDatabaseCopyForIndexPathAnalysis();
      //XGMML.saveToXGMML(XGMML.convertGraphToXGMML(beng2.databaseCopyForPathAnalysis), "c:/datas/binomtest/apoptosis_path.xgmml");
      System.out.println(beng2.databaseCopyForPathAnalysis.Nodes.size()+" nodes left");
      
      //Node source = beng2.databaseCopyForPathAnalysis.getNode("NMT");
      //Node target = beng2.databaseCopyForPathAnalysis.getNode("BAD");
      Node target = beng2.databaseCopyForPathAnalysis.getNode("p53");
      Node source = beng2.databaseCopyForPathAnalysis.getNode("IGFBP_3");
      //Node target = beng2.databaseCopyForPathAnalysis.getNode("Apaf_1");
      //Node target = beng2.databaseCopyForPathAnalysis.getNode("ERK1");
      
      System.out.println("Finding paths...");
      Vector<Graph> v = GraphAlgorithms.Dijkstra(beng2.databaseCopyForPathAnalysis, source, target, true, false, 20);
      //Vector<Graph> v = GraphAlgorithms.FindAllPaths(beng2.databaseCopyForPathAnalysis, source, target, true);
      System.out.println(v.size()+" paths are found");
      Graph grt = new Graph();
      for(int i=0;i<v.size();i++){
    	  
    	  int found = 0;
    	  for(int kk=0;kk<v.size();kk++){
    		if(v.get(i).identicalNodes(v.get(kk)))
    			found++;
    	  }
    	  
    	  System.out.print(found+"\t"+v.get(i).Nodes.size()+":\t");
    	  
    	    for(int j=0;j<v.get(i).Nodes.size();j++)
    	    	System.out.print(((Node)v.get(i).Nodes.get(j)).Id+"\t");
    	    System.out.println();
    	  Graph gr = new Graph();
    	  gr.addNodes(v.get(i));
    	  gr.addConnections(beng2.database);
    	  grt.addNodes(v.get(i));
    	  if(i<5)
    		  XGMML.saveToXGMML(XGMML.convertGraphToXGMML(gr), "c:/datas/binomtest/igfbp3_path"+(i+1)+".xgmml");
      }
      grt.addConnections(beng2.database);
      XGMML.saveToXGMML(XGMML.convertGraphToXGMML(grt), "c:/datas/binomtest/igfbp3_patht.xgmml");
      
      System.exit(0);*/
    	
        BioPAXGraphQueryEngine beng = new BioPAXGraphQueryEngine();      

        GraphXGMMLParser gp2 = new GraphXGMMLParser();
        //gp2.parse("c:/datas/binomtest/apoptosis.xgmml");
        //gp2.parse("c:/datas/binomtest/reaction_semantic_full.xgmml");
        gp2.parse("c:/datas/biopax/reactomeapril2008/homosapiens.xgmml");
        //gp2.parse("c:/datas/biopax/reactomeapril2008/dna_repair.xgmml");
        //gp2.parse("c:/datas/binomtest/reaction_pathway_full.xgmml");
        
        //System.exit(0);
        
        beng.setDatabase(gp2.graph);
    	
      
      /*Vector v = (Vector)beng2.entitySynonym.get("mdm2");
      for(int i=0;i<v.size();i++)
    	  System.out.print(((Node)v.get(i)).Id+"\t");
      System.out.println();*/

      //BioPAXGraphQuery qq = BioPAXGraphQuery.parseXGMML("c:/datas/binomtest/test.xgmml");
      //BioPAXGraphQuery qq = BioPAXGraphQuery.parseXGMML("c:/datas/binomtest/tgfb.xgmml");
      //BioPAXGraphQuery qq = BioPAXGraphQuery.parseXGMML("c:/datas/binomtest/ewing.xgmml");
      //BioPAXGraphQuery qq = BioPAXGraphQuery.parseXGMML("c:/datas/binomtest/mdm2.xgmml");
        
      BioPAXGraphQuery qq = BioPAXGraphQuery.parseXGMML("c:/datas/biopax/reactomeapril2008/test.xgmml");

      Utils.CorrectCytoscapeNodeIds(qq.input);

      //AccessionNumberTable act = new AccessionNumberTable();
      //act.loadTable("c:/datas/binomtest/accmolecules");
      //act.addSynonyms(qq);
      
      //XGMML.saveToXGMML(XGMML.convertGraphToXGMML(qq.input), "c:/datas/binomtest/ewing_test.xgmml");
      
      beng.doQuery(qq, qq.SELECT_ENTITIES);
      
      //XGMML.saveToXGMML(XGMML.convertGraphToXGMML(qq.result), "c:/datas/binomtest/ewing_result.xgmml");
      
      beng.query.input = beng.query.result;
      beng.doQuery(qq,BioPAXGraphQuery.ADD_COMPLEXES_EXPAND);
      beng.query.input = beng.query.result;
      beng.doQuery(qq,BioPAXGraphQuery.ADD_SPECIES);
      beng.query.input = beng.query.result;
      beng.doQuery(qq,BioPAXGraphQuery.ADD_CONNECTING_REACTIONS);
      beng.query.input = beng.query.result;
      //beng.doQuery(q,BioPAXGraphQuery.ADD_PUBLICATIONS);
      XGMML.saveToXGMML(beng.resultAsXgmml(),"c:/datas/biopax/reactomeapril2008/out.xgmml");
      
      
      System.exit(0);
    	
      /*Utils.printUsedMemory();
      edu.rpi.cs.xgmml.GraphDocument grd = XGMML.loadFromXGMML("c:/datas/biopax/reactomerel19/homosapiens_global.xgmml");
      Utils.printUsedMemorySinceLastTime();
      System.gc();
      Utils.printUsedMemory();

      edu.rpi.cs.xgmml.GraphicGraph gr = grd.getGraph();
      Utils.printUsedMemorySinceLastTime();
      String name = gr.getName();
      GraphicNode ar[] = gr.getNodeArray();
      GraphicEdge are[] = gr.getEdgeArray();
      int size = ar.length;
      Utils.printUsedMemorySinceLastTime();
      //for(int j=0;j<10;j++)
      for(int i=0;i<size;i++){
        //if(i==(int)(0.01f*i)*100)
        //    System.gc();
        GraphicNode xn = ar[i];
        xn.getId();
        //System.out.print(i+"\t"+Utils.getUsedMemorySinceLastTime()+"\n"); //Utils.printUsedMemorySinceLastTimeOnlyNumber();
      }
      System.out.print("After node call: ");  Utils.printUsedMemory();
      for(int i=0;i<are.length;i++){
        GraphicEdge ed = are[i];
        ed.getId();
      }
      System.out.print("After edge call: ");  Utils.printUsedMemory();
      System.gc();
      Utils.printUsedMemory();*/


      Vector names = new Vector();
      Vector xrefs = new Vector();
      LineNumberReader lr = new LineNumberReader(new FileReader("c:/datas/biopax/reactome21/testrb/common.txt"));
      String s = null;
      while((s=lr.readLine())!=null){
        StringTokenizer st = new StringTokenizer(s,"\t");
        Vector ids = new Vector();
        st.nextToken(); 
        names.add(st.nextToken()); //st.nextToken();
        while(st.hasMoreTokens())
          ids.add(st.nextToken()+"@uniprot");
        xrefs.add(ids);
      }

      BioPAXGraphQueryEngine beng1 = new BioPAXGraphQueryEngine();
      BioPAXGraphQuery q1 = BioPAXGraphQuery.convertListOfNamesToQuery(names,xrefs);
      XGMML.saveToXGMML(XGMML.convertGraphToXGMML(q1.input),"c:/datas/biopax/reactome21/testrb/common.xgmml");
    	
      /*BioPAXGraphQuery qq = BioPAXGraphQuery.parseXGMML("c:/datas/biobase/testRB/queryRB.xgmml");
      qq.parseAccessionTable("c:/datas/biobase/accall");
      XGMML.saveToXGMML(XGMML.convertGraphToXGMML(qq.result),"c:/datas/biobase/testRB/queryRBid.xgmml");
      System.exit(0);*/

      /*GraphXGMMLParser gp = new GraphXGMMLParser();
      gp.parse("c:/datas/biopax/reactomerel19/homosapiens_global.xgmml");
      beng.setDatabase(gp.graph);
      beng.doQuery(q,q.SELECT_ENTITIES);
      XGMML.saveToXGMML(beng.resultAsXgmml(),"c:/datas/biopax/reactomerel19/result.xgmml");*/

      /*vdaoengine.data.VDataTable vt1 = vdaoengine.data.io.VDatReadWrite.LoadFromSimpleDatFile("c:/datas/calzone/pubrb",false," \t");
      vdaoengine.data.VDataTable vt2 = vdaoengine.data.io.VDatReadWrite.LoadFromSimpleDatFile("c:/datas/calzone/pubreactome",false," \t");
      Vector v1 = new Vector(); Vector v2 = new Vector();
      for(int i=0;i<vt1.rowCount;i++) v1.add(vt1.stringTable[i][0]);
      for(int i=0;i<vt2.rowCount;i++) v2.add(vt2.stringTable[i][0]);
      Utils.compareTwoSets(v1,v2);*/


      /*BioPAXGraphQueryEngine beng = new BioPAXGraphQueryEngine();
      GraphXGMMLParser gp = new GraphXGMMLParser();
      //gp.parse("c:/datas/biopax/reactomerel19/homosapiens_global.xgmml");
      //gp.parse("c:/datas/calzone/2103_global.xgmml");
      gp.parse("c:/datas/calzone/2903final_global.xgmml");
      beng.setDatabase(gp.graph);
      System.out.println("Database loaded");*/

      //BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/calzone/2903final_global.xgmml");
      //BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/biopax/reactomerel19/testcomplex.xgmml");
      //BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/biopax/reactomerel19/rb_complexes1.xgmml");
      //BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/biopax/reactomerel19/rb_species.xgmml");
      //BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/biopax/reactomerel19/rb_reactions.xgmml");
      //BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/calzone/2103_global.xgmml");

      //BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/calzone/rb_entities.xgmml");
      //BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/calzone/rb_complexes.xgmml");
      //BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/calzone/rb_species.xgmml");

      //BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/calzone/2103_global.xgmml");
      //BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/calzone/test_entities.xgmml");
      //BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/calzone/test_complexes.xgmml");
      //BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/calzone/test_species.xgmml");

   	
      /*GraphXGMMLParser gp = new GraphXGMMLParser();
      //gp.parse("c:/datas/biobase/biopax/reaction_semantic_updated_global.xgmml");
      //gp.parse("c:/datas/biobase/biopax/reaction_pathway_updated_global.xgmml");
      gp.parse("c:/datas/calzone/modules1/test.xgmml");
      System.exit(0);
      BioPAXGraphQueryEngine beng = new BioPAXGraphQueryEngine();      
      beng.setDatabase(gp.graph);
      System.out.println("Database loaded");
    	
      BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/biobase/testRB/queryRBid.xgmml");

      beng.doQuery(q,BioPAXGraphQuery.SELECT_ENTITIES);
      XGMML.saveToXGMML(beng.resultAsXgmml(),"c:/datas/biobase/testRB/rb_entities.xgmml");
      beng.query.input = beng.query.result;
      beng.doQuery(q,BioPAXGraphQuery.ADD_COMPLEXES_NOEXPAND);
      XGMML.saveToXGMML(beng.resultAsXgmml(),"c:/datas/biobase/testRB/rb_complexes.xgmml");
      beng.query.input = beng.query.result;
      beng.doQuery(q,BioPAXGraphQuery.ADD_SPECIES);
      XGMML.saveToXGMML(beng.resultAsXgmml(),"c:/datas/biobase/testRB/rb_species.xgmml");
      beng.query.input = beng.query.result;
      beng.doQuery(q,BioPAXGraphQuery.ADD_CONNECTING_REACTIONS);
      XGMML.saveToXGMML(beng.resultAsXgmml(),"c:/datas/biobase/testRB/rb_reactions.xgmml");
      beng.query.input = beng.query.result;
      beng.doQuery(q,BioPAXGraphQuery.ADD_PUBLICATIONS);
      XGMML.saveToXGMML(beng.resultAsXgmml(),"c:/datas/biobase/testRB/rb_final.xgmml");*/
      
      //beng.doQuery(q,BioPAXGraphQuery.ADD_COMPLEXES_NOEXPAND);
      //beng.doQuery(q,BioPAXGraphQuery.ADD_SPECIES);
      //beng.doQuery(q,BioPAXGraphQuery.ADD_CONNECTING_REACTIONS);
      //beng.doQuery(q,BioPAXGraphQuery.ADD_PUBLICATIONS);

      /*BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/calzone/2903final_global.xgmml");
      //BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/biopax/reactomerel19/homosapiens_global.xgmml");
      beng.doQuery(q,BioPAXGraphQuery.LIST_REACTIONS);
      XGMML.saveToXGMML(beng.resultAsXgmml(),"c:/datas/calzone/result.xgmml");*/

       /*GraphXGMMLParser gp = new GraphXGMMLParser();
       gp.parse("c:/datas/biopax/reactome21/testRb/040407.xgmml");
       BioPAXGraphQueryEngine beng = new BioPAXGraphQueryEngine();      
       beng.setDatabase(gp.graph);
       System.out.println("Database loaded");    	
       BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/biopax/Reactome21/testRB/common.xgmml");
       beng.doQuery(q,BioPAXGraphQuery.SELECT_ENTITIES);
       beng.query.input = beng.query.result;
       beng.doQuery(q,BioPAXGraphQuery.ADD_COMPLEXES_NOEXPAND);
       beng.query.input = beng.query.result;
       beng.doQuery(q,BioPAXGraphQuery.ADD_SPECIES);
       beng.query.input = beng.query.result;
       beng.doQuery(q,BioPAXGraphQuery.ADD_CONNECTING_REACTIONS);
       beng.query.input = beng.query.result;
       beng.doQuery(q,BioPAXGraphQuery.ADD_PUBLICATIONS);*/
    	

    }catch(Exception e){
      e.printStackTrace();
    }
  }
}