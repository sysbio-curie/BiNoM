package fr.curie.BiNoM.pathways.utils;

import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import fr.curie.BiNoM.pathways.analysis.structure.Attribute;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class GeneticInteractionNetworks {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			
			//makeYeastORFNameTable();
			makeHumanizedBioGrid();
			//makeSLNetworkFromYeastScreen();
			//extractBioGridMammalianNetwork();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/*
	 * Produces Humanized version of yeast genetic screen
	 */
	public static void makeSLNetworkFromYeastScreen() throws Exception{
		SimpleTable orthologs = new SimpleTable();
		orthologs.LoadFromSimpleDatFile("C:/Datas/SyntheticInteractions/human_yeast_orthologs.txt", true, "\t");
		SimpleTable network = new SimpleTable();
		network.LoadFromSimpleDatFile("C:/Datas/SyntheticInteractions/Constanzo2010/sgadata_costanzo2009_stringentCutoff_101120.txt", true, "\t");

		// First generate table of yeast-human orthologs
		SetOverlapAnalysis so_yeast_human = new SetOverlapAnalysis(); 
		so_yeast_human.sets = new Vector<HashSet<String>>();
		so_yeast_human.setnames = new Vector<String>();
		for(int i=0;i<orthologs.rowCount;i++){
			String yeast_id = orthologs.stringTable[i][orthologs.fieldNumByName("YEAST_ID")];
			String human_gene_name = orthologs.stringTable[i][orthologs.fieldNumByName("HUMAN_GENE_NAME")];		
			if(!so_yeast_human.setnames.contains(yeast_id)){
				so_yeast_human.setnames.add(yeast_id);
				HashSet<String> set = new HashSet<String>();
				set.add(human_gene_name);
				so_yeast_human.sets.add(set);
			}else{
				so_yeast_human.sets.get(so_yeast_human.setnames.indexOf(yeast_id)).add(human_gene_name);
			}
		}
		so_yeast_human.saveSetsAsGMT("C:/Datas/SyntheticInteractions/yeast_human_orthologs.gmt", -1);
		// Second generate table of yeast-human orthologs
		SetOverlapAnalysis so_human_yeast = new SetOverlapAnalysis(); 
		so_human_yeast.sets = new Vector<HashSet<String>>();
		so_human_yeast.setnames = new Vector<String>();
		for(int i=0;i<orthologs.rowCount;i++){
			String yeast_id = orthologs.stringTable[i][orthologs.fieldNumByName("YEAST_ID")];
			String human_gene_name = orthologs.stringTable[i][orthologs.fieldNumByName("HUMAN_GENE_NAME")];		
			if(!so_human_yeast.setnames.contains(human_gene_name)){
				so_human_yeast.setnames.add(human_gene_name);
				HashSet<String> set = new HashSet<String>();
				set.add(yeast_id);
				so_human_yeast.sets.add(set);
			}else{
				so_human_yeast.sets.get(so_human_yeast.setnames.indexOf(human_gene_name)).add(yeast_id);
			}
		}
		so_human_yeast.saveSetsAsGMT("C:/Datas/SyntheticInteractions/human_yeast_orthologs.gmt", -1);
		// Create graph of "humanized" interactions for Costanzo 2010
		Graph graph = new Graph();
		for(int i=0;i<network.rowCount;i++){
			String source = network.stringTable[i][network.fieldNumByName("ORFQUERY")];
			String target = network.stringTable[i][network.fieldNumByName("ORFARRAY")];
			float score = Float.parseFloat(network.stringTable[i][network.fieldNumByName("SCORE_EPS")]);
			if(score<0){
			double pvalue = Double.parseDouble(network.stringTable[i][network.fieldNumByName("PVALUE")]);
			StringTokenizer st = new StringTokenizer(source,"_"); 
			source = st.nextToken();
			st = new StringTokenizer(target,"_"); 
			target = st.nextToken();	
			int is = so_yeast_human.setnames.indexOf(source);
			int it = so_yeast_human.setnames.indexOf(target);
			if(is==-1) System.out.println(source+" not found in the orthologs.");
			if(it==-1) System.out.println(target+" not found in the orthologs.");		
			if((is!=-1)&&(it!=-1)){
				String sourceh = "";
				Iterator<String> iter = so_yeast_human.sets.get(is).iterator();
				while(iter.hasNext()) sourceh+=iter.next()+" "; if(sourceh.length()>0) sourceh = sourceh.substring(0, sourceh.length()-1);
				iter = so_yeast_human.sets.get(it).iterator();
				String targeth = "";
				while(iter.hasNext()) targeth+=iter.next()+" "; if(targeth.length()>0) targeth = targeth.substring(0, targeth.length()-1);
				Node sourcen = graph.getCreateNode(sourceh);
				Node targetn = graph.getCreateNode(targeth);			
				sourcen.setAttributeValueUnique("YEAST_ID", source, Attribute.ATTRIBUTE_TYPE_STRING);
				targetn.setAttributeValueUnique("YEAST_ID", target, Attribute.ATTRIBUTE_TYPE_STRING);		
				Edge e = graph.getCreateEdge(source+"_"+target);
				e.Node1 = sourcen;
				e.Node2 = targetn;
				e.setAttributeValueUnique("SCORE_EPS", ""+score, Attribute.ATTRIBUTE_TYPE_REAL);
				e.setAttributeValueUnique("PVALUE", ""+pvalue, Attribute.ATTRIBUTE_TYPE_REAL);
			}}
		}
		XGMML.saveToXGMML(graph, "C:/Datas/SyntheticInteractions/Constanzo2010/stringent_humanized_negative.xgmml");
		SetOverlapAnalysis.convertXGMMLtoGMT("C:/Datas/SyntheticInteractions/Constanzo2010/stringent_humanized_negative.xgmml","C:/Datas/SyntheticInteractions/Constanzo2010/stringent_humanized_negative_pairs.gmt", true);
	}
	
	/*
	 * Extracts mouse and human genetic interactions from BioGrid database dump
	 */
	public static void extractBioGridMammalianNetwork() throws Exception{
		//INTERACTOR_A	
		//INTERACTOR_B	
		//OFFICIAL_SYMBOL_A	
		//OFFICIAL_SYMBOL_B	
		//ALIASES_FOR_A	
		//ALIASES_FOR_B	
		//EXPERIMENTAL_SYSTEM	
		//SOURCE	
		//PUBMED_ID	
		//ORGANISM_A_ID	
		//ORGANISM_B_ID
		SimpleTable tab = new SimpleTable();
		tab.LoadFromSimpleDatFile("c:/datas/biogrid/BIOGRID-ALL-3.2.97.tab.txt", true, "\t");
		Graph graph = new Graph();
		FileWriter fw = new FileWriter("c:/datas/biogrid/human_mouse_genetic.txt");
		FileWriter fwm = new FileWriter("c:/datas/biogrid/human_mouse_genetic.gmt");
		FileWriter fwp = new FileWriter("c:/datas/biogrid/human_mouse_physical.txt");		
		FileWriter fwpm = new FileWriter("c:/datas/biogrid/human_mouse_physical.gmt");		

		FileWriter fwy = new FileWriter("c:/datas/biogrid/yeast_genetic.txt");
		FileWriter fwmy = new FileWriter("c:/datas/biogrid/yeast_genetic.gmt");
		FileWriter fwpy = new FileWriter("c:/datas/biogrid/yeast_physical.txt");		
		FileWriter fwpmy = new FileWriter("c:/datas/biogrid/yeast_physical.gmt");		
		
		HashSet<String> fwm_set = new HashSet<String>(); 		
		HashSet<String> fwmy_set = new HashSet<String>(); 
		HashSet<String> fwpm_set = new HashSet<String>(); 		
		HashSet<String> fwpmy_set = new HashSet<String>(); 
		
		
		HashSet<String> allsystems = new HashSet<String>();
		for(int i=0;i<tab.rowCount;i++){
			String geneA = tab.stringTable[i][tab.fieldNumByName("OFFICIAL_SYMBOL_A")].toUpperCase();
			String geneB = tab.stringTable[i][tab.fieldNumByName("OFFICIAL_SYMBOL_B")].toUpperCase();
			String organismA = tab.stringTable[i][tab.fieldNumByName("ORGANISM_A_ID")];
			String organismB = tab.stringTable[i][tab.fieldNumByName("ORGANISM_B_ID")];
			String pubmed = tab.stringTable[i][tab.fieldNumByName("PUBMED_ID")];
			String source = tab.stringTable[i][tab.fieldNumByName("SOURCE")];			
			String system = tab.stringTable[i][tab.fieldNumByName("EXPERIMENTAL_SYSTEM")];
			
			allsystems.add(system);
			
			// Human interactions
			if((organismA.equals("9606")&&organismB.equals("9606"))||
					(organismA.equals("9606")&&organismB.equals("10090"))||
					(organismA.equals("10090")&&organismB.equals("9606"))||
					(organismA.equals("10090")&&organismB.equals("10090"))){
				
				if(system.contains("Phenotypic")||system.contains("Genetic")||system.contains("Synthetic")||system.contains("Dosage")){
					fw.write(geneA.toUpperCase()+"\t"+geneB.toUpperCase()+"\t"+source+"\tPMID:"+pubmed+"\t"+organismA+"\t"+organismB+"\t"+system+"\n");
				
					if(geneA.compareTo(geneB)>0){
						String temp = geneA; geneA = geneB; geneB = temp;
					}
					if(!fwm_set.contains(geneA+"_"+geneB))
						fwm.write(geneA+"_"+geneB+"\tna\t"+geneA+"\t"+geneB+"\n");
					fwm_set.add(geneA+"_"+geneB);
					
				}else{
					fwp.write(geneA.toUpperCase()+"\t"+geneB.toUpperCase()+"\t"+source+"\tPMID:"+pubmed+"\t"+organismA+"\t"+organismB+"\t"+system+"\n");					

					if(geneA.compareTo(geneB)>0){
						String temp = geneA; geneA = geneB; geneB = temp;
					}
					if(!fwpm_set.contains(geneA+"_"+geneB))
						fwpm.write(geneA+"_"+geneB+"\tna\t"+geneA+"\t"+geneB+"\n");
					fwpm_set.add(geneA+"_"+geneB);
				}
				
			}
			
			if(organismA.equals("559292")&&organismB.equals("559292")){

				if(system.contains("Phenotypic")||system.contains("Genetic")||system.contains("Synthetic")||system.contains("Dosage")){
					fwy.write(geneA.toUpperCase()+"\t"+geneB.toUpperCase()+"\t"+source+"\tPMID:"+pubmed+"\t"+organismA+"\t"+organismB+"\t"+system+"\n");
				
					if(geneA.compareTo(geneB)>0){
						String temp = geneA; geneA = geneB; geneB = temp;
					}
					if(!fwmy_set.contains(geneA+"_"+geneB))
						fwmy.write(geneA+"_"+geneB+"\tna\t"+geneA+"\t"+geneB+"\n");
					fwmy_set.add(geneA+"_"+geneB);
				}else{
					fwpy.write(geneA.toUpperCase()+"\t"+geneB.toUpperCase()+"\t"+source+"\tPMID:"+pubmed+"\t"+organismA+"\t"+organismB+"\t"+system+"\n");					

					if(geneA.compareTo(geneB)>0){
						String temp = geneA; geneA = geneB; geneB = temp;
					}
					if(!fwpmy_set.contains(geneA+"_"+geneB))
						fwpmy.write(geneA+"_"+geneB+"\tna\t"+geneA+"\t"+geneB+"\n");
					fwpmy_set.add(geneA+"_"+geneB);
				}
				
				
			}
		}
		fw.close();
		fwm.close();
		fwp.close();
		fwpm.close();
		
		fwy.close();
		fwmy.close();
		fwpy.close();
		fwpmy.close();		
		
		//for(String s : allsystems){
		//	System.out.println(s);
		//}

	}
	
	
	public static void makeHumanizedBioGrid() throws Exception{
		SimpleTable yeastData = new SimpleTable();
		yeastData.LoadFromSimpleDatFile("C:/Datas/BioGrid/yeast_genetic_header.txt", true, "\t");
		SetOverlapAnalysis so = new SetOverlapAnalysis();
		so.LoadSetsFromGMT("C:/Datas/SyntheticInteractions/yeast_human_orthologs.gmt");
		SimpleTable yeastORFNames = new SimpleTable();
		yeastORFNames.LoadFromSimpleDatFile("C:/Datas/SyntheticInteractions/ORF_Name_Yeast.txt", true, "\t");

		yeastORFNames.makeUpperCaseInIndex = true;
		yeastORFNames.createIndex("NAME");
		yeastORFNames.createSecondaryIndex("ORF");		
		
		HashMap<String,String> pmids = new HashMap<String,String>();
		HashMap<String,String> systems = new HashMap<String,String>();		
		
		for(int i=0;i<yeastData.rowCount;i++){
			String geneA = yeastData.stringTable[i][yeastData.fieldNumByName("GENEA")].toUpperCase();
			String geneB = yeastData.stringTable[i][yeastData.fieldNumByName("GENEB")].toUpperCase();
			String pubmed = yeastData.stringTable[i][yeastData.fieldNumByName("PMID")];
			String system = yeastData.stringTable[i][yeastData.fieldNumByName("SYSTEM")];			
			if(geneA.compareTo(geneB)>0){
				String temp = geneA; geneA = geneB; geneB = temp;
			}
			String id = geneA+"_"+geneB;
			if(pmids.get(id)==null){ pmids.put(id, pubmed); systems.put(id, system); }else{
				String pms = pmids.get(id); pms+=";"+pubmed; pmids.put(id, pms);
				String sms = systems.get(id); sms+=";"+system; systems.put(id, sms);				
			}
		}
		
		FileWriter fw = new FileWriter("C:/Datas/BioGrid/yeast_genetic_header_compr.txt");
		FileWriter fw_orf = new FileWriter("C:/Datas/BioGrid/yeast_genetic_header_compr_ORF.gmt");
		FileWriter fw_orf_negative = new FileWriter("C:/Datas/BioGrid/yeast_genetic_header_compr_ORF_negative.gmt");		
		FileWriter fwh = new FileWriter("C:/Datas/BioGrid/yeast_genetic_humanized.txt");		
		FileWriter fwhg = new FileWriter("C:/Datas/BioGrid/yeast_genetic_humanized.gmt");				
		FileWriter fwhg_negative = new FileWriter("C:/Datas/BioGrid/yeast_genetic_humanized_negative.gmt");						
		fw.write("ID\tGENEA\tGENEB\tPMID\tSYSTEM\tEVIDENCES\n");
		fwh.write("ID\tHUMANGENEA\tHUMANGENEB\tSYSTEM\tEVIDENCES\n");		
		for(String s: pmids.keySet()){
			StringTokenizer st = new StringTokenizer(s,"_");
			String ps = systems.get(s);
			int count_evidences = 0;
			StringTokenizer st1 = new StringTokenizer(ps,";");
			Vector<String> sms = new Vector<String>();
			while(st1.hasMoreTokens()) { count_evidences++; String ss = st1.nextToken(); if(!sms.contains(ss)) sms.add(ss); }
			String systemLabel = "";
			Collections.sort(sms); for(int i=0;i<sms.size();i++) systemLabel+=sms.get(i)+";"; if(systemLabel.length()>0) systemLabel = systemLabel.substring(0, systemLabel.length()-1); 
			String geneA = st.nextToken().toUpperCase();
			String geneB = st.nextToken().toUpperCase();
			fw.write(s+"\t"+geneA+"\t"+geneB+"\t"+pmids.get(s)+"\t"+systems.get(s)+"\t"+count_evidences+"\n");
			
			String orfA = null;
			String humangeneA = "";
			String orfB = null;
			String humangeneB = "";
			
			if((yeastORFNames.index.get(geneA)==null)&&(yeastORFNames.secondaryIndex.get(geneA)==null)){
				System.out.println(geneA+"\tORF not found!");
			}else{
				int k = -1;
				if(yeastORFNames.index.get(geneA)!=null) k = yeastORFNames.index.get(geneA).get(0); else
				if(yeastORFNames.secondaryIndex.get(geneA)!=null) k = yeastORFNames.secondaryIndex.get(geneA).get(0);				
				orfA = yeastORFNames.stringTable[k][yeastORFNames.fieldNumByName("ORF")];
				if(so.setnames.indexOf(orfA)==-1){
					System.out.println(orfA+"\tcan not be humanized!");
				}else{
					HashSet<String> set = so.sets.get(so.setnames.indexOf(orfA));
					for(String ss: set) humangeneA+=ss+" "; if(humangeneA.length()>0) humangeneA = humangeneA.substring(0, humangeneA.length()-1);
				}
			}

			if((yeastORFNames.index.get(geneB)==null)&&(yeastORFNames.secondaryIndex.get(geneB)==null)){
				System.out.println(geneB+"\tORF not found!");
			}else{
				int k = -1;
				if(yeastORFNames.index.get(geneB)!=null) k = yeastORFNames.index.get(geneB).get(0); else
				if(yeastORFNames.secondaryIndex.get(geneB)!=null) k = yeastORFNames.secondaryIndex.get(geneB).get(0);				
				orfB = yeastORFNames.stringTable[k][yeastORFNames.fieldNumByName("ORF")];
				if(so.setnames.indexOf(orfB)==-1){
					System.out.println(orfB+"\tcan not be humanized!");
				}else{
					HashSet<String> set = so.sets.get(so.setnames.indexOf(orfB));
					for(String ss: set) humangeneB+=ss+" "; if(humangeneB.length()>0) humangeneB = humangeneB.substring(0, humangeneB.length()-1);
				}
			}
			
			boolean negative = false;
			if(systemLabel.contains("Negative")||systemLabel.contains("Synthetic Lethality")||systemLabel.contains("Synthetic Growth")||systemLabel.contains("Phenotypic Enhancement")||systemLabel.contains("Dosage Lethality")||systemLabel.contains("Dosage Growth Defect")||systemLabel.contains("Synthetic Haploinsufficiency"))
				negative = true;

			if((orfA!=null)&&(orfB!=null)){
				fw_orf.write(s+"\t"+systemLabel+"\t"+orfA+"\t"+orfB+"\n");
			}
			if(negative)
			if((orfA!=null)&&(orfB!=null)){
				fw_orf_negative.write(s+"\t"+systemLabel+"\t"+orfA+"\t"+orfB+"\n");
			}
			
			
			if((!humangeneA.equals(""))&&(!humangeneB.equals(""))){
				fwh.write(s+"\t"+humangeneA+"\t"+humangeneB+"\t"+systemLabel+"\t"+count_evidences+"\n");
				fwhg.write(s+"\t"+systemLabel+";"+count_evidences+"\t");
				if(negative)
					fwhg_negative.write(s+"\t"+systemLabel+";"+count_evidences+"\t");
				HashSet<String> set = new HashSet<String>();
				st = new StringTokenizer(humangeneA," "); while(st.hasMoreTokens()) set.add(st.nextToken());
				st = new StringTokenizer(humangeneB," "); while(st.hasMoreTokens()) set.add(st.nextToken());
				for(String ss: set) fwhg.write(ss+"\t"); fwhg.write("\n");
				if(negative){				
					for(String ss: set) fwhg_negative.write(ss+"\t"); fwhg_negative.write("\n");
				}
			}
		}
		fw.close(); fwh.close(); fwhg.close(); fwhg_negative.close(); fw_orf.close(); fw_orf_negative.close();
	}
	

}
