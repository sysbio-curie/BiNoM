package fr.curie.BiNoM.pathways.test;

import java.io.*;
import java.util.*;

import com.hp.hpl.jena.rdf.model.ModelFactory;

import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;;

public class testBioBasePathway {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			
			
		//Some problem with BioPAX naming
		    /*BioPAX bp = new BioPAX();
		    bp.loadBioPAX("c:/datas/biobase/biopax/reaction_pathway_full.owl");
		    //bp.loadBioPAX("c:/datas/biobase/test/test1.owl");
		    //bp.loadBioPAX("c:/datas/biobase/biopax/molecule.owl");
		    
			
		    BioPAXNamingService bns = new BioPAXNamingService();
		    System.out.println("Generating names...");
		    bns.generateNames(bp,true);
		    System.out.println("Finished.");
		    
		    //BioPAXToSBMLConverter b2s = new BioPAXToSBMLConverter();
		    //b2s.biopax.model = ModelFactory.createDefaultModel();
		    //b2s.biopax = bp;
			//b2s.bpnm.generateNames(b2s.biopax,false);
			//b2s.findIndependentSpecies();

		    String uri = "MO000038995";
		    String sn = bns.getNameByUri(uri); 
		    System.out.println("name for "+uri+":\t"+sn);
		    sn = bns.getNameByUri(uri+"e"); 
		    System.out.println("name for "+uri+"e:\t"+sn);		    

	    
		    System.exit(0);
		    String uri = "MO000038997";
		    String sn = bns.getNameByUri(uri); 
		    System.out.println("name for "+uri+":\t"+sn);
		    sn = bns.getNameByUri(uri+"e"); 
		    System.out.println("name for "+uri+"e:\t"+sn);		    
		    System.exit(0);
			*/
			
		      /*Vector xrefs = new Vector();
		      Vector names = new Vector();
		      LineNumberReader lr = new LineNumberReader(new FileReader("c:/datas/biobase/testE2FTranspath/molecules_test.txt"));
		      String s = null;
		      while((s=lr.readLine())!=null){
		        StringTokenizer st = new StringTokenizer(s,"\t");
		        String id = st.nextToken()+"e";
		        Vector ids = new Vector();
		        names.add(id);
		        ids.add(id);
		        xrefs.add(ids);
		      }
	      BioPAXGraphQuery q = BioPAXGraphQuery.convertListOfNamesToQuery(names,xrefs,"");
	      XGMML.saveToXGMML(XGMML.convertGraphToXGMML(q.input),"c:/datas/biobase/testE2FTranspath/query_test.xgmml");*/
			
		      GraphXGMMLParser gp = new GraphXGMMLParser();
		      gp.parse("c:/datas/biobase/biopax/reaction_pathway_full.xgmml");
		      //gp.parse("c:/datas/biobase/biopax/reaction_semantic_full.xgmml");
		      BioPAXGraphQueryEngine beng = new BioPAXGraphQueryEngine();      
		      beng.setDatabase(gp.graph);
		      System.out.println("Database loaded");
		      
		      BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML("c:/datas/biobase/testE2FTranspath/query.xgmml");
		      beng.doQuery(q,BioPAXGraphQuery.SELECT_ENTITIES);
		      XGMML.saveToXGMML(beng.resultAsXgmml(),"c:/datas/biobase/testE2FTranspath/entities.xgmml");
		      beng.query.input = beng.query.result;
		      beng.doQuery(q,BioPAXGraphQuery.ADD_COMPLEXES_NOEXPAND);
		      XGMML.saveToXGMML(beng.resultAsXgmml(),"c:/datas/biobase/testE2FTranspath/complexes.xgmml");
		      beng.query.input = beng.query.result;
		      beng.doQuery(q,BioPAXGraphQuery.ADD_SPECIES);
		      XGMML.saveToXGMML(beng.resultAsXgmml(),"c:/datas/biobase/testE2FTranspath/species.xgmml");
		      beng.query.input = beng.query.result;
		      beng.doQuery(q,BioPAXGraphQuery.ADD_CONNECTING_REACTIONS);
		      XGMML.saveToXGMML(beng.resultAsXgmml(),"c:/datas/biobase/testE2FTranspath/reactions.xgmml");
		      beng.query.input = beng.query.result;
		      beng.doQuery(q,BioPAXGraphQuery.ADD_PUBLICATIONS);
		      XGMML.saveToXGMML(beng.resultAsXgmml(),"c:/datas/biobase/testE2FTranspath/final.xgmml");

		      
		      Vector E2Freactions = new Vector();
		      Vector E2Freactions_names = new Vector();
		      LineNumberReader lr = new LineNumberReader(new FileReader("c:/datas/biobase/testE2FTranspath/reactions.txt"));
		      String s = null;
		      while((s=lr.readLine())!=null){
			        StringTokenizer st = new StringTokenizer(s,"\t");
			        E2Freactions.add(st.nextToken());
			        E2Freactions_names.add(st.nextToken());
 		      }
		      int is = 0;
		      System.out.println("Additional reactions: ");
		      beng.query.result.calcNodesInOut();
		      for(int i=0;i<beng.query.result.Nodes.size();i++){
		    	  Node n = (Node)beng.query.result.Nodes.get(i);
		    	  String ss = n.getFirstAttributeValue("BIOPAX_REACTION");
		    	  if(ss!=null){
		    		  if(E2Freactions.indexOf(ss)>=0){
		    			  is++;
		    			  int kk = E2Freactions.indexOf(ss);
		    			  E2Freactions.remove(kk);
		    			  E2Freactions_names.remove(kk);
		    		  }else
		    			  System.out.println(ss+"\t"+n.NodeLabel);
		    		  	  /*for(int k=0;k<n.incomingEdges.size();k++){
		    		  		 Edge e1 = (Edge)n.incomingEdges.get(k);
		    		  		 System.out.print("\t"+e1.EdgeLabel+"->"+e1.Node1.NodeLabel+"\n");
		                   }
		                  for(int k=0;k<n.outcomingEdges.size();k++){
		                     Edge e1 = (Edge)n.outcomingEdges.get(k);
		                     System.out.print("\t"+e1.EdgeLabel+"<-"+e1.Node2.NodeLabel+"\n");
		                   }*/
		    	  }
		      }
		      System.out.println("Intersection "+is);
		      System.out.println("Not found ("+E2Freactions.size()+"): ");
		      for(int i=0;i<E2Freactions.size();i++){
		    	  System.out.println((String)E2Freactions.get(i)+"\t"+(String)E2Freactions_names.get(i));
		      }
		      
			
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
