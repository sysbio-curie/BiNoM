package fr.curie.BiNoM.test;

import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.XGMML;
import fr.curie.BiNoM.pathways.BioPAXToSBMLConverter.BioPAXSpecies;
import fr.curie.BiNoM.pathways.analysis.structure.BiographUtils;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.pathways.BioPAXToSBMLConverter;
import fr.curie.BiNoM.pathways.utils.BioPAXGraphMappingService;
import fr.curie.BiNoM.pathways.utils.BioPAXNamingService;
import fr.curie.BiNoM.pathways.utils.Utils;
import java.util.*;
import java.util.regex.*;
import java.io.*;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class testEB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			String fn = "/Users/eric/work/binom/biopax/biopax3-short-metabolic-pathway.owl";
			//String fn = "/Users/eric/work/binom/biopax/A_thaliana_L3.owl";
			//String fn = "/Users/eric/work/binom/biopax/H_sapiens_L3.owl";
			//String fn = "/Users/eric/work/binom/biopax/biopax3-phosphorylation-reaction.owl";
			//String fn = "/Users/eric/work/binom/biopax/biopax3-protein-interaction.owl";
			
			String prefix = "/Users/eric/";
			BioPAXToCytoscapeConverter b2c = new BioPAXToCytoscapeConverter();
			BioPAXToCytoscapeConverter.Graph gr = b2c.convert(BioPAXToCytoscapeConverter.REACTION_NETWORK_CONVERSION, fn, new BioPAXToCytoscapeConverter.Option());

			fr.curie.BiNoM.pathways.analysis.structure.Graph graph  = XGMML.convertXGMMLToGraph(gr.graphDocument);

			XGMML.saveToXGMML(graph, prefix+"test_before.xgmml");
			//graph = BiographUtils.LinearizeNetwork(graph);
			//XGMML.saveToXGMML(graph, prefix+"test_after.xgmml");
			
			
			//BioPAX bp = new BioPAX();
			//bp.loadBioPAX(fn);
//
//			List l = biopax_DASH_level3_DOT_owlFactory.getAllSmallMolecule(bp.model);
//			for (int i=0;i<l.size();i++) {
//				
//				SmallMolecule e = (SmallMolecule) l.get(i);
//			}

			// GraphMapper test
			//BioPAXGraphMappingService bgms = new BioPAXGraphMappingService();
			//System.out.println("Mapping BioPAX...");
			//Graph graph = bgms.mapBioPAXToGraph(bp);
		    //Utils.printUsedMemory();

			//BioPAXNamingService ns = new BioPAXNamingService(bp,true);
//			BioPAXToSBMLConverter bsc = new BioPAXToSBMLConverter();
//			bsc.biopax = bp;
//			bsc.bpnm = ns;
//			//bsc.findIndependentSpecies();
//			bsc.populateSbml();
			
			
//			System.out.println("start");
//			List l = biopax_DASH_level3_DOT_owlFactory.getAllConversion(bp.model);
//			printDir(l);
//			List l1 = biopax_DASH_level3_DOT_owlFactory.getAllBiochemicalReaction(bp.model);
//			printDir(l1);
//			List l2 = biopax_DASH_level3_DOT_owlFactory.getAllComplexAssembly(bp.model);
//			printDir(l2);
//			List l3 = biopax_DASH_level3_DOT_owlFactory.getAllDegradation(bp.model);
//			printDir(l3);
//			List l4 = biopax_DASH_level3_DOT_owlFactory.getAllTransport(bp.model);
//			printDir(l4);
//			List l5 = biopax_DASH_level3_DOT_owlFactory.getAllTransportWithBiochemicalReaction(bp.model);
//			printDir(l5);
			
//			List l = biopax_DASH_level3_DOT_owlFactory.getAllControl(bp.model);
//			for (int i=0;i<l.size();i++) {
//				Control co = (Control)l.get(i);
//				Pathway pat = co.getControlled_asPathway();
//				Iterator controller_pat_it = co.getController_asPathway();
//				Iterator controller_pe =  co.getController_asPhysicalEntity();
//				
//				if (pat != null) {
//					System.out.println("got pathway as controller");
//					if (controller_pat_it != null)
//						System.out.println(i+"pathway");
//						while(controller_pat_it.hasNext()) {
//							Pathway p = (Pathway)controller_pat_it.next();
//							System.out.println(i+":controller=pathway: "+p.uri());
//						}
//					if (controller_pe != null)
//						System.out.println(i+"PE");
//						while(controller_pe.hasNext()) {
//							PhysicalEntity pe = (PhysicalEntity) controller_pe.next();
//							System.out.println(i+":PE "+ pe.uri());
//						}
//				}
//			}

			
			
			
			
//			l = biopax_DASH_level3_DOT_owlFactory.getAllCatalysis(bp.model);
//			System.out.println("Catalysis: "+l.size());
//			l = biopax_DASH_level3_DOT_owlFactory.getAllModulation(bp.model);
//			System.out.println("Modulation: "+l.size());
//			
//			l = biopax_DASH_level3_DOT_owlFactory.getAllInteraction(bp.model);
//			for (int i=0;i<l.size();i++) {
//				Interaction inter = (Interaction)l.get(i);
//			}
//			/*for (int i=0;i<l.size();i++) {
//				String dir = ((Conversion)l.get(i)).getConversionDirection();
//				System.out.println(dir);
//			}
//			
//			*/System.out.println("done.");
			
			
			/*BioPAXNamingService ns = new BioPAXNamingService(bp,false);
			BioPAXToSBMLConverter bsc = new BioPAXToSBMLConverter();
			bsc.biopax = bp;
			bsc.bpnm = ns;
			bsc.findIndependentSpecies();*/
			
			//countEntities(bp);
			//System.exit(1);
			
			/*HashMap<String,String> mf_map = new HashMap<String, String>();
			List l = biopax_DASH_level3_DOT_owlFactory.getAllModificationFeature(bp.model);
			for (int i=0;i<l.size();i++) {
				ModificationFeature mf = (ModificationFeature) l.get(i);
				ControlledVocabulary cv = mf.getModificationType();
				Iterator it = cv.getTerm();
				String term = null;
				while(it.hasNext()) {
					term = (String) it.next();
				}
				mf_map.put(mf.uri(), Utils.correctName(term));
			}
			Pattern pat = Pattern.compile("phospho\\w+_at_\\d+");
			l  = biopax_DASH_level3_DOT_owlFactory.getAllProtein(bp.model);
			for (int i=0;i<l.size();i++) {
				Protein p = (Protein)l.get(i);
				//System.out.println(p.getName());
				Iterator it = p.getFeature();
				while(it.hasNext()) {
					EntityFeature ef = (EntityFeature) it.next();
					String str = mf_map.get(ef.uri());
					
					
					if (str != null) {
						Matcher m = pat.matcher(ef.uri());
						if (m.find())
							System.out.println(ef.uri()+"\t"+str);
					}
				}
			}*/
			
			
			
			
			/*List l  = biopax_DASH_level3_DOT_owlFactory.getAllModificationFeature(bp.model);
			for (int i=0;i<l.size();i++) {
				ModificationFeature mf = (ModificationFeature) l.get(i);
				System.out.println(mf);
				Iterator z = mf.getFeatureLocation();
				while(z.hasNext()) {
					SequenceLocation sl = (SequenceLocation) z.next();
					//System.out.println(sl);
				}
				ControlledVocabulary cv = mf.getModificationType();
				Iterator it = cv.getTerm();
				while(it.hasNext()) {
					String term = (String) it.next();
					System.out.println(term);
				}
				
			}*/
			
			/*HashMap<String,String> mf_map = new HashMap<String, String>();
			HashMap<String,Integer> ss_map = new HashMap<String, Integer>();
			
			List l = biopax_DASH_level3_DOT_owlFactory.getAllModificationFeature(bp.model);
			for (int i=0;i<l.size();i++) {
				ModificationFeature mf = (ModificationFeature) l.get(i);
				ControlledVocabulary cv = mf.getModificationType();
				Iterator it = cv.getTerm();
				String term = null;
				while(it.hasNext()) {
					term = (String) it.next();
				}
				mf_map.put(mf.uri(), Utils.correctName(term));
			}
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllSequenceSite(bp.model);
			for (int i=0;i<l.size();i++) {
				SequenceSite ss = (SequenceSite) l.get(i);
				int pos = ss.getSequencePosition();
				ss_map.put(ss.uri(), pos);
			}
			
			l  = biopax_DASH_level3_DOT_owlFactory.getAllProtein(bp.model);
			for (int i=0;i<l.size();i++) {
				Protein p = (Protein)l.get(i);
				//System.out.println(p.getName());
				Iterator it = p.getFeature();
				while(it.hasNext()) {
					EntityFeature ef = (EntityFeature) it.next();
					String str = mf_map.get(ef.uri());
					if (str != null) {
						ArrayList<SequenceLocation> fl = new ArrayList<SequenceLocation>();
						Iterator z = ef.getFeatureLocation();
						while(z.hasNext()) {
							fl.add((SequenceLocation) z.next());
						}
						String str_pos = "unknown";
						if (fl.size()>0 && ss_map.get(fl.get(0).uri()) != null) {
							str_pos = Integer.toString(ss_map.get(fl.get(0).uri()));
						}
						String modif = str + "_at_" + str_pos;
						System.out.println(i+"\t"+ef.uri()+"\t"+modif);
					}
				}
			}*/
			
//			List l  = biopax_DASH_level3_DOT_owlFactory.getAllProtein(bp.model);
//			for (int i=0;i<l.size();i++) {
//				Protein p = (Protein)l.get(i);
//				ControlledVocabulary cv = p.getCellularLocation();
//				String name = ns.createEntityName(p);
//				if (cv != null) {
//					String compartment = ns.createUtilityName(cv);
//					// add compartment to the name, remove compartment string if already there
//					if (!compartment.equals(""))
//						name += "@" + compartment;
//					else
//						name = Utils.replaceString(name, compartment, "");
//				}
//				else
//					name += "@";
//				
//				System.out.println(i+":"+name);
				
				
				
//				openControlledVocabulary voc = participant.getCELLULAR_DASH_LOCATION();
//				if(voc!=null){
//					String compartment = createUtilityName(voc);
//					if(!compartment.equals("")){
//						if(name.indexOf(compartment)<0){
//							if(addCompartment)
//								name+="@"+compartment;
//						}else{
//							name = Utils.replaceString(name,compartment,"");
//							if(addCompartment)
//								name+="@"+compartment;
//						}
//					}
//				}
//				else
//					if(addCompartment)
//						//name+="@notdef";
//						name+="@";
//			}
			
//			List toto = biopax_DASH_level3_DOT_owlFactory.getAllComplex(bp.model);
//			BioPAXNamingService test = new BioPAXNamingService(bp,false);
//			for (int i=0;i<toto.size();i++) {
//				Complex co = (Complex)toto.get(i);
//				String s = test.getNameByUri(co.uri());
//				String name = test.createNameForComplex(co);
//				//System.out.println(s);
//				//System.out.println(i+":"+co.uri()+"==>"+name);
//			}
				
//			System.exit(1);
			
//			List l1 = biopax_DASH_level3_DOT_owlFactory.getAllModificationFeature(bp.model);
//			System.out.println("Total number of ModificationFeatures: "+l1.size());
//			
//			for (int i=0;i<l1.size();i++) {
//				ModificationFeature mf = (ModificationFeature)l1.get(i);
//				ControlledVocabulary cv = mf.getModificationType();
//				Iterator<String> it = cv.getTerm();
//				while(it.hasNext())
//					System.out.println(i+":"+Utils.correctName(it.next()));
//			}
			
//			List l = biopax_DASH_level3_DOT_owlFactory.getAllProtein(bp.model);
//			for (int i=0;i<l.size();i++) {
//				Protein p = (Protein)l.get(i);
//				System.out.println(p);
//				Iterator it = p.getFeature();
//				while(it.hasNext()) {
//					EntityFeature ef = (EntityFeature)it.next();
//					System.out.println(ef);
//				}
//				System.out.println("###############################");
//				
//			}
			
//			List l = biopax_DASH_level3_DOT_owlFactory.getAllProtein(bp.model);
//			for (int i=0;i<l.size();i++) {
//				Protein p = (Protein)l.get(i);
//				ControlledVocabulary cv = p.getCellularLocation();
//				Iterator it = cv.getTerm();
//				while(it.hasNext()) {
//					 String term = (String)it.next();
//					 System.out.println(term);
//				}
//				System.out.println("###############################");
//				
//			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String getShortestName(Iterator it) {
		String ret = "";
		int len = 100000;
		while(it.hasNext()) {
			String n = (String) it.next();
			if (n.length() < len) {
				len = n.length();
				ret = n;
			}
		}
		ret = Utils.correctName(ret);
		return(ret);
	}
	
	public static void countEntities(BioPAX bp) {
		try {
			List l;

			l = biopax_DASH_level3_DOT_owlFactory.getAllPhysicalEntity(bp.model);
			System.out.println("PhysicalEntity:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllComplex(bp.model);
			System.out.println("Complex:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllDna(bp.model);
			System.out.println("Dna:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllDnaRegion(bp.model);
			System.out.println("DnaRegion:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllProtein(bp.model);
			System.out.println("Protein:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllProteinReference(bp.model);
			System.out.println("ProteinReference:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllModificationFeature(bp.model);
			System.out.println("ModificationFeature:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllDnaRegion(bp.model);
			System.out.println("DnaRegion:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllRna(bp.model);
			System.out.println("Rna:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllRnaRegion(bp.model);
			System.out.println("RnaRegion:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllSmallMolecule(bp.model);
			System.out.println("SmallMolecule:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllControlledVocabulary(bp.model);
			System.out.println("ControlledVocabulary:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllCellularLocationVocabulary(bp.model);
			System.out.println("CellularLocationVocabulary:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllCellVocabulary(bp.model);
			System.out.println("CellVocabulary:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllEntityReferenceTypeVocabulary(bp.model);
			System.out.println("EntityReferenceTypeVocabulary:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllEvidenceCodeVocabulary(bp.model);
			System.out.println("EvidenceCodeVocabulary:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllExperimentalFormVocabulary(bp.model);
			System.out.println("ExperimentalFormVocabulary:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllInteractionVocabulary(bp.model);
			System.out.println("InteractionVocabulary:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllPhenotypeVocabulary(bp.model);
			System.out.println("PhenotypeVocabulary:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllRelationshipTypeVocabulary(bp.model);
			System.out.println("RelationshipTypeVocabulary:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllSequenceModificationVocabulary(bp.model);
			System.out.println("SequenceModificationVocabulary:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllSequenceRegionVocabulary(bp.model);
			System.out.println("SequenceRegionVocabulary:\t" + l.size());
			
			l = biopax_DASH_level3_DOT_owlFactory.getAllTissueVocabulary(bp.model);
			System.out.println("TissueVocabulary:\t" + l.size());
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private static void printDir(List l) throws Exception {
		
		for (int i=0;i<l.size();i++) {
			
			Conversion co = (Conversion)l.get(i);
			System.out.println(co);
			Iterator left = co.getLeft();
			Iterator right = co.getRight();
			int ctLeft=0;
			int ctRight=0;
			while(right.hasNext()) {
				PhysicalEntity pe = (PhysicalEntity) right.next();
				ctRight++;
			}
			while(left.hasNext()) {
				PhysicalEntity pe = (PhysicalEntity) left.next();
				ctLeft++;
			}
				
			//System.out.println(ctRight+":"+ctLeft);
		
		}
	}
}
