package fr.curie.BiNoM.pathways.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.*;

import javax.swing.JOptionPane;

import com.hp.hpl.jena.rdf.model.*;
import com.ibm.adtech.jastor.JastorException;

import cytoscape.Cytoscape;
import cytoscape.util.CyFileFilter;
import cytoscape.util.FileUtil;

import fr.curie.BiNoM.biopax.BioPAXImportDialog;
import fr.curie.BiNoM.cytoscape.biopax.BioPAXImportTaskFactory;
import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.pathways.BioPAXToSBMLConverter;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class TestEB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		try {

			String fn = "/bioinfo/users/ebonnet/Binom/biopax/biopax3-short-metabolic-pathway.owl";
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/tmp.owl";
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/signaling_gateway_biopax3.owl";
			//String fn = "/bioinfo/users/ebonnet/Binom/BIOMD0000000007-biopax3.owl";
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/M-Phase-L3.owl";
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/Apoptosis3.owl";
			
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/signaling_gateway_arf6.owl";
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/signaling_gateway_bcatenin.owl";
			
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/reactome_vegf.owl";
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/Apoptosis2.owl";
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/biopax2-short-metabolic-pathway.owl";
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/A_thaliana_L3.owl";
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/H_sapiens_L3.owl";
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/biopax3-phosphorylation-reaction.owl";
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/biopax3-protein-interaction.owl";

			
			
		      /*
		       * Pathway components
		       */
//		      BioPAX bp = new BioPAX();
//		      bp.loadBioPAX(new FileInputStream(fn));
//		
//		      List l = biopax_DASH_level3_DOT_owlFactory.getAllPathway(bp.model);
//		      for (int i=0;i<l.size();i++) {
//		        Pathway p = (Pathway) l.get(i);
//		        //System.out.println("Pathway: "+p.uri());
//		        
//		        Iterator it = p.getPathwayOrder();
//		        int ct = 0;
//		        while(it.hasNext()) {
//		          PathwayStep ps = (PathwayStep)it.next();
////		          System.out.println("PathwayStep: "+ps.uri());
//		          Vector<String> vec = Utils.getPropertyURIs(ps, "nextStep");
//		          for (String sg : vec)
//		            System.out.println(ps.uri()+" nextStep: "+sg);
//		        }
//		        
//		      }


		      /*
		       * test pathway structure import
		       */

		      BioPAXToCytoscapeConverter.Option option = new BioPAXToCytoscapeConverter.Option();
		      option.makeRootPathwayNode = false;
		      option.includeNextLinks = true;
		      option.includePathways = true;
		      option.includeInteractions = true;

		      BioPAXToCytoscapeConverter b2c = new BioPAXToCytoscapeConverter();
		      BioPAXToCytoscapeConverter.Graph gr = b2c.convert(BioPAXToCytoscapeConverter.PATHWAY_STRUCTURE_CONVERSION, fn, option);
		      
		      //System.out.println(gr.graphDocument.toString());
		      
		      try{
		    	  FileWriter fw = new FileWriter("/bioinfo/users/ebonnet/out.xgmml");
		    	  String s = gr.graphDocument.toString();
		    	  s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n"+s;
		    	  fw.write(s);
		    	  fw.close();
		      }catch(Exception e){
		    	  e.printStackTrace();
		      }


			
			
			/*
			 * Test sbml conversion
			 */
//			BioPAXToCytoscapeConverter b2c = new BioPAXToCytoscapeConverter();
//			BioPAXToCytoscapeConverter.Graph gr = b2c.convert(BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION, fn, new BioPAXToCytoscapeConverter.Option());
			
//		    System.out.println(gr.graphDocument.toString()); // doc looks ok here!!!
		    
		    
//		    CyFileFilter bioPaxFilter = new CyFileFilter();
//
//	        bioPaxFilter.addExtension("owl");
//	        bioPaxFilter.setDescription("BioPAX files");
//
//	        File file = FileUtil.getFile
//		    ("Load BioPAX File", FileUtil.LOAD, new CyFileFilter[]{bioPaxFilter});
//
//	        if (file != null) {
//	        	try {
//	        		FileInputStream is = new FileInputStream(file);
//	        		is.close();
//	        		BioPAXImportDialog.getInstance().raise(new BioPAXImportTaskFactory(), file, file.getName());
//	        	}
//	        	catch(Exception ee) {
//	        		JOptionPane.showMessageDialog
//	        		(Cytoscape.getDesktop(),
//	        				"Cannot open file " + file.getAbsolutePath() + " for reading");
//	        	}
//	        }
		    
		    
			
			//String prefix = "/Users/eric/";
			//BioPAXToCytoscapeConverter b2c = new BioPAXToCytoscapeConverter();
			//BioPAXToCytoscapeConverter.Graph gr = b2c.convert(BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION, fn, new BioPAXToCytoscapeConverter.Option());
			//BioPAXToCytoscapeConverter.Graph gr = b2c.convert(BioPAXToCytoscapeConverter.PROTEIN_PROTEIN_INTERACTION_CONVERSION, fn, new BioPAXToCytoscapeConverter.Option());
			//fr.curie.BiNoM.pathways.analysis.structure.Graph graph  = XGMML.convertXGMMLToGraph(gr.graphDocument);

			//XGMML.saveToXGMML(graph, prefix+"test.xgmml");
			//graph = BiographUtils.LinearizeNetwork(graph);
			//XGMML.saveToXGMML(graph, prefix+"test_after.xgmml");

//			BioPAX bp = new BioPAX();
//			System.out.println("Loading file...");
//			bp.loadBioPAX(fn);
//			System.out.println("done.");
//			
//			BioPAXNamingService bpns = new BioPAXNamingService();
//			bpns.generateNames(bp,true);
//			//System.out.println(bpns.getNameByUri("http://bioinfo.curie.fr/biopax2sbml#re2_Wee1"));
//			
//			System.exit(1);

			//bp.loadBioPAX(new FileInputStream(fn));

//			List l = biopax_DASH_level3_DOT_owlFactory.getAllProtein(bp.model);
//			for (int i=0;i<l.size();i++) {
//				
//				Protein p = (Protein) l.get(i);
//				
//				p.getFeature();
//				
//				System.out.print(Utils.cutUri(co.uri())+"\t");
//				
//				int count = 0;
//				try {
//					Iterator it = co.getComponentStoichiometry();
//					if (it.hasNext())
//						while(it.hasNext()) {
//							it.next(); count++;
//						}
//					System.out.println(count);
//				}
//				catch (JastorException e) {
//					e.printStackTrace();
//				}
				//Iterator it = co.getName();
//				System.out.print(p.uri()+"=");
//				while(it.hasNext()) {
//					String name = (String)it.next();
//					System.out.print(name+":");
//				}
//				System.out.println();
				
//				System.out.println(bpns.getNameByUri(p.uri()));
//			}
			
			
			
			
			
//			BioPAXToCytoscapeConverter b2c = new BioPAXToCytoscapeConverter();
//			BioPAXToCytoscapeConverter.Graph gr = b2c.convert(BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION, fn, new BioPAXToCytoscapeConverter.Option());
			
//			BioPAXToSBMLConverter b2s = new BioPAXToSBMLConverter();
//			
//			b2s.biopax.loadBioPAX(new FileInputStream(fn));
//			
//			b2s.populateSbml();
			
//			Set s = b2s.includedSpecies.keySet();
//			
//			Iterator it = s.iterator();
//			while(it.hasNext()){
//				String n = (String)it.next();
//				System.out.println("ic : "+n+"\n");
//				
//			}
			
			
			// create a naming service
//			BioPAXNamingService ns = new BioPAXNamingService(bp,true);
			
//			List l = biopax_DASH_level3_DOT_owlFactory.getAllProtein(bp.model);
//			for (int i=0;i<l.size();i++) {
//				Protein p = (Protein) l.get(i);
				
				//System.out.println(Utils.cutUri(p.uri()+" = "+bpns.getNameByUri(Utils.cutUri(p.uri()))));
				//Iterator it = p.getName();
				//Vector vals = Utils.getPropertyURIs(p, "name");
				//System.out.print(p.listStatements());
//				System.out.print(Utils.cutUri("..."+p.uri())+"=");
//				while(it.hasNext()) {
//					String name = (String)it.next();
//					System.out.print(name+":");
//				}
//				System.out.println();
				/*if (Utils.cutUri(p.uri()).equals("Protein18")) {
					System.out.println("here");
					List li = p.listStatements();
					Iterator t = li.iterator();
					while (t.hasNext()) {
						Statement st = (Statement)t.next();
						System.out.println(st.toString());
					}
				}*/
//			}
			
			// import biopax3 and save cytoscape
//			String prefix = "/Users/eric/";
//			BioPAXToCytoscapeConverter b2c = new BioPAXToCytoscapeConverter();
//			BioPAXToCytoscapeConverter.Graph gr = b2c.convert(BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION, fn, new BioPAXToCytoscapeConverter.Option());
//			//BioPAXToCytoscapeConverter.Graph gr = b2c.convert(BioPAXToCytoscapeConverter.PROTEIN_PROTEIN_INTERACTION_CONVERSION, fn, new BioPAXToCytoscapeConverter.Option());
//			fr.curie.BiNoM.pathways.analysis.structure.Graph graph  = XGMML.convertXGMMLToGraph(gr.graphDocument);
//			XGMML.saveToXGMML(graph, prefix+"test.xgmml");
			
			
			// test load with inputstream
//			BioPAX bp = new BioPAX();
//			FileInputStream fin = new FileInputStream(fn);
//			bp.loadBioPAX(fin);
			
			
		  // GraphMapper test
//	      BioPAXGraphMappingService bgms = new BioPAXGraphMappingService();
//	      System.out.println("Mapping BioPAX...");
//	      Graph graph = bgms.mapBioPAXToGraph(bp);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
