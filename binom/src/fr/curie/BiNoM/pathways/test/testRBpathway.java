package fr.curie.BiNoM.pathways.test;

import java.io.*;
import java.util.*;

import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;;

public class testRBpathway {

	public static void main(String[] args) {
		try{
			
			// First look in the accession numbers table to get molecules list
			AccessionNumberTable act = new AccessionNumberTable();
			act.loadTable("c:/datas/biobase/accmolecules");
			
			String prefix = "c:/datas/biobase/testRB/";
			
			Vector names = new Vector();
		      LineNumberReader lr = new LineNumberReader(new FileReader(prefix+"queryrb_inters2.txt"));
		      String s = null;
		      while((s=lr.readLine())!=null){
		        StringTokenizer st = new StringTokenizer(s,"\t");
		        Vector ids = new Vector();
		        //st.nextToken(); 
		        names.add(st.nextToken()); st.nextToken();
		      }
		    //Vector ids = act.getIDList(names);
		    
		   FileWriter fw = new FileWriter(prefix+"molecules");
		   //for(int i=0;i<ids.size();i++)
		   //	   fw.write((String)ids.get(i)+"\t"++"\r\n");
		   for(int i=0;i<names.size();i++){
			   String name = (String)names.get(i);
			   Vector ids = (Vector)act.nameid.get(name.toLowerCase());
			   fw.write(name+"\t");
			   if(ids!=null)
			   for(int j=0;j<ids.size();j++){
				   String id = (String)ids.get(j);
				   fw.write(id+"\t");
			   }
			   fw.write("\r\n");
		   }
		   fw.close();
			
		      Vector xrefs1 = new Vector();
		      Vector names1 = new Vector();
		      LineNumberReader lr1 = new LineNumberReader(new FileReader(prefix+"molecules"));
		      String s1 = null;
		      while((s1=lr1.readLine())!=null){
		        StringTokenizer st = new StringTokenizer(s1,"\t");
		        //String id = st.nextToken()+"e";
		        Vector ids = new Vector();
		        String name = st.nextToken();
		        names1.add(name);
		        while(st.hasMoreTokens())
		            ids.add(st.nextToken()+"e");
		        xrefs1.add(ids);
		      }
	      BioPAXGraphQuery q1 = BioPAXGraphQuery.convertListOfNamesToQuery(names1,xrefs1);
	      XGMML.saveToXGMML(XGMML.convertGraphToXGMML(q1.input),prefix+"query.xgmml");
			
		      GraphXGMMLParser gp = new GraphXGMMLParser();
		      //gp.parse("c:/datas/biobase/biopax/reaction_pathway_full.xgmml");
		      //gp.parse("c:/datas/biobase/biopax/reaction_pathway_full.xgmml");
		      gp.parse("c:/datas/biobase/testRB/2903final.xgmml");
		      BioPAXGraphQueryEngine beng = new BioPAXGraphQueryEngine();      
		      beng.setDatabase(gp.graph);
		      System.out.println("Database loaded");
		      
		      BioPAXGraphQuery q = BioPAXGraphQuery.parseXGMML(prefix+"query.xgmml");
		      beng.doQuery(q,BioPAXGraphQuery.SELECT_ENTITIES);
		      XGMML.saveToXGMML(beng.resultAsXgmml(),prefix+"entities.xgmml");
		      beng.query.input = beng.query.result;
		      //beng.doQuery(q,BioPAXGraphQuery.ADD_COMPLEXES_NOEXPAND);
		      beng.doQuery(q,BioPAXGraphQuery.ADD_COMPLEXES_NOEXPAND);
		      XGMML.saveToXGMML(beng.resultAsXgmml(),prefix+"complexes.xgmml");
		      beng.query.input = beng.query.result;
		      beng.doQuery(q,BioPAXGraphQuery.ADD_SPECIES);
		      XGMML.saveToXGMML(beng.resultAsXgmml(),prefix+"species.xgmml");
		      beng.query.input = beng.query.result;
		      beng.doQuery(q,BioPAXGraphQuery.ADD_CONNECTING_REACTIONS);
		      XGMML.saveToXGMML(beng.resultAsXgmml(),prefix+"reactions.xgmml");
		      beng.query.input = beng.query.result;
		      beng.doQuery(q,BioPAXGraphQuery.ADD_PUBLICATIONS);
		      XGMML.saveToXGMML(beng.resultAsXgmml(),prefix+"final.xgmml");
	       
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
