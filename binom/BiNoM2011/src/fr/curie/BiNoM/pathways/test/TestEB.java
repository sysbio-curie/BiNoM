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

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.biopax.BioPAXImportDialog;
import fr.curie.BiNoM.cytoscape.biopax.BioPAXImportTaskFactory;
import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.pathways.BioPAXToSBMLConverter;
import fr.curie.BiNoM.pathways.BioPAXToSBMLConverter.BioPAXSpecies;
import fr.curie.BiNoM.pathways.analysis.structure.BiographUtils;
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

			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/biopax3-short-metabolic-pathway.owl";
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/M-Phase-L3.owl";
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
			String fn = "/bioinfo/users/ebonnet/Binom/biopax/H_sapiens_L3.owl";
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/biopax3-phosphorylation-reaction.owl";
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/biopax3-protein-interaction.owl";
			//String fn = "/bioinfo/users/ebonnet/Binom/biopax/biopax3-genetic-interaction.owl";
			
			//String fn = "/bioinfo/users/ebonnet/test.owl";
			

			/*
			 * retrieve sequence vocabulary
			 */
//			BioPAX bp = new BioPAX();
//			bp.loadBioPAX(new FileInputStream(fn));
//			List l = biopax_DASH_level3_DOT_owlFactory.getAllSequenceModificationVocabulary(bp.model);
//			
//			for (int i=0;i<l.size();i++) {
//				SequenceModificationVocabulary voc = (SequenceModificationVocabulary) l.get(i);
//				
//				Iterator it = voc.getTerm();
//				String name = "";
//				if (it.hasNext()) {
//					name = name + ":"+(String)it.next();
//				}
//				//System.out.println(voc.uri()+"\t"+name);
//				System.out.println(name);
//			}
			
			
			/*
			 * genetic interactions
			 */
			BioPAX bp = new BioPAX();
			bp.loadBioPAX(new FileInputStream(fn));
			List l = biopax_DASH_level3_DOT_owlFactory.getAllGene(bp.model);
			
			for (int i=0;i<l.size();i++) {
				Gene  gn = (Gene) l.get(i);
				
				Iterator it = gn.getName();
				String name = "";
				if (it.hasNext()) {
					name = name + ":"+(String)it.next();
				}
				//System.out.println(voc.uri()+"\t"+name);
				System.out.println(gn.uri()+"\t"+name);
			}
			
			
//			BioPAXToSBMLConverter b2s = new BioPAXToSBMLConverter();
//			b2s.biopax.loadBioPAX(new FileInputStream(fn));
//			b2s.populateSbml();
//			
//			Iterator it = b2s.independentSpeciesIds.keySet().iterator();
//			while(it.hasNext()) {
//				String cut_uri = (String) it.next();
//				System.out.println("independent species id: "+cut_uri);
//			}
//			
//			it = b2s.includedSpecies.keySet().iterator();
//			while(it.hasNext()) {
//				String cut_uri = (String) it.next();
//				System.out.println("included species id: "+cut_uri);
//			}
			
			
//			BioPAXToCytoscapeConverter b2c = new BioPAXToCytoscapeConverter(); 
//			BioPAXToCytoscapeConverter.Graph gr = b2c.convert(BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION, fn, new BioPAXToCytoscapeConverter.Option());
//			System.out.println(gr.graphDocument.toString());
			
//			Graph graphDoc = XGMML.convertXGMMLToGraph(gr.graphDocument);
//	    	Graph grres = BiographUtils.ShowMonoMolecularReactionsAsEdges(graphDoc);
//	    	GraphDocument grDoc = XGMML.convertGraphToXGMML(grres);
	    	
//	    	XGMML.saveToXGMML(grDoc, "/bioinfo/users/ebonnet/test.xgmml");
			
//			BioPAX bp = new BioPAX();
//			bp.loadBioPAX(new FileInputStream(fn));
//			BioPAXNamingService ns = new BioPAXNamingService(bp,true);
//			
//			String pname = ns.getNameByUri("http://www.biopax.org/release/biopax-level3.owl#(E2F4,E2F5)");
//			System.out.println("pname = " +pname);
//			System.out.println(GraphUtils.correctId(pname));
			
//			List l = biopax_DASH_level3_DOT_owlFactory.getAllProteinReference(bp.model);
//			
//			for (int i=0;i<l.size();i++) {
//				ProteinReference p = (ProteinReference) l.get(i);
//				//System.out.println(p.uri());
//				Iterator it = p.getName();
//				String name = "";
//				if (it.hasNext()) {
//					name = name + ":"+(String)it.next();
//				}
//				System.out.println(p.uri()+"\t"+name);
//			}

//			l = biopax_DASH_level3_DOT_owlFactory.getAllBiochemicalReaction(bp.model);
//			
//			for (int i=0;i<l.size();i++) {
//				BiochemicalReaction p = (BiochemicalReaction) l.get(i);
//				System.out.println(">>> br: "+p.uri());
//				Iterator it = p.getLeft();
//				System.out.print("left: ");
//				while(it.hasNext()) {
//					PhysicalEntity pe = (PhysicalEntity) it.next();
//					System.out.print(Utils.cutUri(pe.uri())+" ");
//				}
//				System.out.println();
//				it = p.getRight();
//				System.out.print("right: ");
//				while(it.hasNext()) {
//					PhysicalEntity pe = (PhysicalEntity) it.next();
//					System.out.print(Utils.cutUri(pe.uri())+" ");
//				}
//				System.out.println();
//			}
//			
//			l = biopax_DASH_level3_DOT_owlFactory.getAllComplexAssembly(bp.model);
//			for (int i=0;i<l.size();i++) {
//				ComplexAssembly ca = (ComplexAssembly) l.get(i);
//				System.out.println(">>> ca: "+ca.uri());
//				Iterator it = ca.getLeft();
//				while(it.hasNext()) {
//					PhysicalEntity pe = (PhysicalEntity) it.next();
//					System.out.println("left: "+pe.uri());
//				}
//				it = ca.getRight();
//				while(it.hasNext()) {
//					PhysicalEntity pe = (PhysicalEntity) it.next();
//					System.out.println("right: "+pe.uri());
//				}
//			}
			
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

//		      BioPAXToCytoscapeConverter.Option option = new BioPAXToCytoscapeConverter.Option();
//		      option.makeRootPathwayNode = false;
//		      option.includeNextLinks = true;
//		      option.includePathways = true;
//		      option.includeInteractions = true;
//
//		      BioPAXToCytoscapeConverter b2c = new BioPAXToCytoscapeConverter();
//		      BioPAXToCytoscapeConverter.Graph gr = b2c.convert(BioPAXToCytoscapeConverter.PATHWAY_STRUCTURE_CONVERSION, fn, option);
//		      
//		      //System.out.println(gr.graphDocument.toString());
//		      
//		      try{
//		    	  FileWriter fw = new FileWriter("/bioinfo/users/ebonnet/out.xgmml");
//		    	  String s = gr.graphDocument.toString();
//		    	  s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n"+s;
//		    	  fw.write(s);
//		    	  fw.close();
//		      }catch(Exception e){
//		    	  e.printStackTrace();
//		      }


			
			
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
