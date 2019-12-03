package fr.curie.BiNoM.pathways.utils;

import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import vdaoengine.utils.Algorithms;
import vdaoengine.utils.VSimpleFunctions;
import fr.curie.BiNoM.pathways.analysis.structure.Attribute;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class GeneticInteractionNetworks {

	/**
	 * @param args
	 */
	public static int EPISTASIS_NULL_MODEL_ADDITIVE = 0;
	public static int EPISTASIS_NULL_MODEL_MULTIPLICATIVE = 1;
	public static int EPISTASIS_NULL_MODEL_MIN = 2;
	public static int EPISTASIS_NULL_MODEL_MAX = 3;
	public static int EPISTASIS_NULL_MODEL_LOG = 4;
	
	public static String modelNames[] = {"ADD","MLT","MIN","MAX","LOG"};
	
	public static int INTTYPE_NONINTERACTIVE = 0;
	public static int INTTYPE_SYNTHETIC = 0;
	public static int INTTYPE_ASYNTHETIC = 0;
	public static int INTTYPE_SUPPRESSIVE = 0;
	public static int INTTYPE_EPISTATIC = 0;
	public static int INTTYPE_CONDITIONAL = 0;
	public static int INTTYPE_ADDITIVE = 0;
	public static int INTTYPE_SINGLE_NONMONOTONIC = 0;
	public static int INTTYPE_DOUBLE_NONMONOTONIC = 0;
	
	
	public static int numberofnullmodels = 5;
	
	public static int bestNullEpistasisModel = 0;
	
	
	public Vector<Float> Wx = null; 
	public Vector<Float> Wy = null;
	public Vector<Float> Wxy = null;
	
	/*
	 * Here all initial computed null models are stored
	 */
	public float nullmodels[][] = null;
	public float nullmodels_corrected[][] = null;
	public float alphas[] = null;
	public float correlations[] = null;
			
	public float threshold_positive = 0f;
	public float threshold_negative = 0f;
	
	public Vector<String> interaction_types = new Vector<String>();
	
	/*
	 * Fitness measure of the wild type
	 */
	public float Wwildtype = 1;
	/*
	 * Minimum difference from which two Ws are considered different numbers
	 */
	public float deltaW = 0.2f; 
	
	public static void main(String[] args) {
		try{
			
			//makeYeastORFNameTable();
			//makeHumanizedBioGrid();
			//makeSLNetworkFromYeastScreen();
			//extractBioGridMammalianNetwork();
			//analyzeGeneticInteractionBioGrid();
			String ineq = interactionInequality(1.04f,0.49f,1.01f,1f,0.1f);
			System.out.println(ineq+"\t"+inequalityType(ineq)+"\t"+inequalityDirection(ineq));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void ChooseBestNullEpistasisModel(Vector<Float> _f1, Vector<Float> _f2, Vector<Float> _f12){
		ChooseBestNullEpistasisModel(_f1,_f2,_f12,-1);
	}
	
	public void ChooseBestNullEpistasisModel(Vector<Float> _f1, Vector<Float> _f2, Vector<Float> _f12, int forcedModelChoice){
		Wx = _f1;
		Wy = _f2;
		Wxy = _f12;
		ComputeNullEpistasisModels();
		correlations = new float[numberofnullmodels];
		float wxy[] = new float[Wxy.size()];
		for(int i=0;i<Wxy.size();i++) wxy[i]=Wxy.get(i);
		bestNullEpistasisModel = 0;
		float corr = 0f;
		
		for(int i=0;i<numberofnullmodels;i++){
			//correlations[i] = VSimpleFunctions.calcCorrelationCoeffLeaveOneOut(nullmodels[i], wxy, 2f, 0);
			correlations[i] = VSimpleFunctions.calcCorrelationCoeff(nullmodels[i], wxy);
			if(correlations[i]>corr){
				corr = correlations[i];
				bestNullEpistasisModel = i;
			}
		}
		
		if(forcedModelChoice!=-1)
			bestNullEpistasisModel = forcedModelChoice;
		
		
		Vector<Float> posdistV = new Vector<Float>();
		Vector<Float> negdistV = new Vector<Float>();
		for(int i=0;i<Wx.size();i++){
			float v = Wxy.get(i)-nullmodels_corrected[bestNullEpistasisModel][i];
			if(v>0){
				posdistV.add(v);
				posdistV.add(-v);
			}
			if(v<0){
				negdistV.add(v);
				negdistV.add(-v);
			}
		}
		float posdist[] = new float[posdistV.size()];
		float negdist[] = new float[negdistV.size()];
		for(int i=0;i<posdistV.size();i++) posdist[i] = posdistV.get(i);
		for(int i=0;i<negdistV.size();i++) negdist[i] = negdistV.get(i);
		threshold_negative = VSimpleFunctions.calcStandardDeviation(negdist);
		threshold_positive = VSimpleFunctions.calcStandardDeviation(posdist);
	}
	
	public void ComputeNullEpistasisModels(){
		nullmodels = new float[numberofnullmodels][Wx.size()];
		for(int i=0;i<Wx.size();i++){
			float wx = Wx.get(i);
			float wy = Wy.get(i);
			float F_ADD = wx+wy;
			float F_MULT = wx*wy;
			float F_MIN = Math.min(wx, wy);
			float F_MAX = Math.max(wx, wy);;
			float F_LOG = log2((float)((Math.pow(2,wx)-1)*(Math.pow(2,wy)-1)+1));
			nullmodels[0][i] = F_ADD;
			nullmodels[1][i] = F_MULT;
			nullmodels[2][i] = F_MIN;
			nullmodels[3][i] = F_MAX;
			nullmodels[4][i] = F_LOG;
		}
		// now, correct for bias
		nullmodels_corrected = new float[numberofnullmodels][Wx.size()];
		alphas = new float[numberofnullmodels];
		for(int i=0;i<numberofnullmodels;i++){
			for(int j=0;j<Wx.size();j++)
				nullmodels_corrected[i][j] = nullmodels[i][j];
		}			
		CorrectNullModelBias();
		
	}
	
	public void CorrectNullModelBias(){
		float wxy[] = new float[Wxy.size()];
		alphas = new float[numberofnullmodels];
		nullmodels_corrected = new float[numberofnullmodels][Wx.size()];
		for(int i=0;i<Wxy.size();i++) wxy[i]=Wxy.get(i);
		
		for(int i=0;i<numberofnullmodels;i++){
			alphas[i] = getRegressionCoefficient(nullmodels[i], wxy);
			for(int j=0;j<Wx.size();j++)
				nullmodels_corrected[i][j] = alphas[i]*nullmodels[i][j];
		}		
	}
	
	public static float log2(float x){
		return (float)Math.log(x)/(float)Math.log(2);
	}
	
	public static float getRegressionCoefficient(float x[], float y[]){
		float sxy = 0f;
		float sxx = 0f;
		for(int i=0;i<y.length;i++){
			sxy+=x[i]*y[i];
			sxx+=x[i]*x[i];
		}
		return sxy/sxx;
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
	
	public static void analyzeGeneticInteractionBioGrid() throws Exception{
		SimpleTable tab = new SimpleTable();
		tab.LoadFromSimpleDatFile("c:/datas/biogrid/BIOGRID-ALL-3.2.97.tab.txt", true, "\t");
		HashMap<String, Vector<Integer>> ints = new HashMap<String, Vector<Integer>>(); 
		for(int i=0;i<tab.rowCount;i++){
			String geneA = tab.stringTable[i][tab.fieldNumByName("OFFICIAL_SYMBOL_A")];
			String geneB = tab.stringTable[i][tab.fieldNumByName("OFFICIAL_SYMBOL_B")];
			String pubmed = tab.stringTable[i][tab.fieldNumByName("PUBMED_ID")];
			String organism = tab.stringTable[i][tab.fieldNumByName("ORGANISM_A_ID")];
			if(organism.equals("10090")||organism.equals("9606")){
			String key = "";
			if(geneA.compareTo(geneB)>0)
				key = geneA+"|"+geneB+"|"+pubmed;
			else
				key = geneB+"|"+geneA+"|"+pubmed;
			Vector<Integer> lines = new Vector<Integer>();
			if(ints.get(key)==null){
				lines = new Vector<Integer>();
				ints.put(key, lines);
			}else{
				lines = ints.get(key);
			}
			lines.add(i);
			}
		}
		Set<String> keys = ints.keySet();
		for(String key: keys){
			Vector<Integer> lines = ints.get(key);
			boolean phenotypicEnhancement = false;
			boolean phenotypicSuppression = false;			
			for(int i=0;i<lines.size();i++){
				int k = lines.get(i);
				String syst = tab.stringTable[k][tab.fieldNumByName("EXPERIMENTAL_SYSTEM")];
				if(syst.equals("Phenotypic Enhancement")) phenotypicEnhancement = true;
				if(syst.equals("Phenotypic Suppression")) phenotypicSuppression = true;				
			}
			if(phenotypicEnhancement&&phenotypicSuppression){
				for(int i=0;i<lines.size();i++){
					int k = lines.get(i);
					String syst = tab.stringTable[k][tab.fieldNumByName("EXPERIMENTAL_SYSTEM")];
					String geneA = tab.stringTable[k][tab.fieldNumByName("OFFICIAL_SYMBOL_A")];
					String geneB = tab.stringTable[k][tab.fieldNumByName("OFFICIAL_SYMBOL_B")];
					String source = tab.stringTable[k][tab.fieldNumByName("SOURCE")];
					String pubmed = tab.stringTable[k][tab.fieldNumByName("PUBMED_ID")];
					System.out.println(geneA+"\t"+geneB+"\t"+syst+"\t"+source+"\t"+pubmed);
				}
				System.out.println("-------------------------------------------");
			}
			
		}
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
	
	public static String interactionInequality(float pwt, float pa, float pb, float pab, float thresh){
		String labels[] = {"1W","2A","3B","4C"};
		String ineq = "";
		float nums[] = {pwt,pa,pb,pab};
		int inds[] = Algorithms.SortMass(nums);
		ineq = labels[inds[0]];
		for(int i=1;i<inds.length;i++){
			String sep = "<";
			if(Math.abs(nums[inds[i]]-nums[inds[i-1]])<thresh)
				sep = "=";
			ineq+=sep+labels[inds[i]];
		}
		
		// normalize inequality by swapping equal label
		//System.out.println(ineq);
		while(true){
			boolean swap = false;
			String ls[] = {ineq.substring(0,2),ineq.substring(3,5),ineq.substring(6,8),ineq.substring(9,11)};
			String seps[] = {ineq.substring(2,3),ineq.substring(5,6),ineq.substring(8,9)};
			for(int i=0;i<seps.length;i++){
				if(seps[i].equals("=")){
					if(ls[i].compareTo(ls[i+1])>0){
						String temp = ls[i+1];
						ls[i+1] = ls[i];
						ls[i] = temp;
						swap = true;
					}
				}
			}
			ineq = ls[0]+seps[0]+ls[1]+seps[1]+ls[2]+seps[2]+ls[3];
			//System.out.println(ineq);
			if(!swap) break;
		}
		ineq = Utils.replaceString(ineq, "1W", "WT");
		ineq = Utils.replaceString(ineq, "2A", "A");
		ineq = Utils.replaceString(ineq, "3B", "B");
		ineq = Utils.replaceString(ineq, "4C", "AB");
		return ineq;
	}
	
	
	public static String inequalityType(String ineq){
	String tp = "not_defined";
		
	String types[] = { 
	"A=AB<WT=B","noninteractive",
	"B=AB<WT=A","noninteractive",
	"WT=A<B=AB","noninteractive",
	"WT=B<A=AB","noninteractive",
	"WT=A=B=AB","noninteractive",
	"AB<WT=A=B","synthetic",
	"WT=A=B<AB","synthetic",
	"A=B=AB<WT","asynthetic",
	"WT<A=B=AB","asynthetic",
	"A<WT=B=AB","suppressive",
	"B<WT=A=AB","suppressive",
	"WT=A=AB<B","suppressive",
	"WT=B=AB<A","suppressive",
	"A<B=AB<WT","epistatic",
	"B<A=AB<WT","epistatic",
	"A=AB<B<WT","epistatic",
	"B=AB<A<WT","epistatic",
	"A<WT<B=AB","epistatic",
	"B<WT<A=AB","epistatic",
	"A=AB<WT<B","epistatic",
	"B=AB<WT<A","epistatic",
	"WT<A<B=AB","epistatic",
	"WT<B<A=AB","epistatic",
	"WT<A=AB<B","epistatic",
	"WT<B=AB<A","epistatic",
	"WT=A<AB<B","conditional",
	"WT=B<AB<A","conditional",
	"WT=A<B<AB","conditional",
	"WT=B<A<AB","conditional",
	"A<WT=B<AB","conditional",
	"B<WT=A<AB","conditional",
	"AB<WT=A<B","conditional",
	"AB<WT=B<A","conditional",
	"A<AB<WT=B","conditional",
	"B<AB<WT=A","conditional",
	"AB<A<WT=B","conditional",
	"AB<B<WT=A","conditional",
	"A<AB<WT<B","additive",
	"B<AB<WT<A","additive",
	"A<WT<AB<B","additive",
	"B<WT<AB<A","additive",
	"A<WT=AB<B","additive",
	"B<WT=AB<A","additive",
	"AB<A<B<WT","additive",
	"AB<B<A<WT","additive",
	"WT<A<B<AB","additive",
	"WT<B<A<AB","additive",
	"AB<A=B<WT","additive",
	"WT<A=B<AB","additive",
	"A<AB<B<WT","single-nonmonotonic",
	"B<AB<A<WT","single-nonmonotonic",
	"A<WT<B<AB","single-nonmonotonic",
	"B<WT<A<AB","single-nonmonotonic",
	"AB<A<WT<B","single-nonmonotonic",
	"AB<B<WT<A","single-nonmonotonic",
	"WT<A<AB<B","single-nonmonotonic",
	"WT<B<AB<A","single-nonmonotonic",
	"A<B<AB<WT","double-nonmonotonic",
	"B<A<AB<WT","double-nonmonotonic",
	"A<B<WT<AB","double-nonmonotonic",
	"B<A<WT<AB","double-nonmonotonic",
	"A<B<WT=AB","double-nonmonotonic",
	"B<A<WT=AB","double-nonmonotonic",
	"A=B<AB<WT","double-nonmonotonic",
	"A=B<WT<AB","double-nonmonotonic",
	"A=B<WT=AB","double-nonmonotonic",
	"AB<WT<A<B","double-nonmonotonic",
	"AB<WT<B<A","double-nonmonotonic",
	"AB<WT<A=B","double-nonmonotonic",
	"WT<AB<A<B","double-nonmonotonic",
	"WT<AB<B<A","double-nonmonotonic",
	"WT<AB<A=B","double-nonmonotonic",
	"WT=AB<A<B","double-nonmonotonic",
	"WT=AB<B<A","double-nonmonotonic",
	"WT=AB<A=B","double-nonmonotonic"};
	for(int i=0;i<types.length-1;i++)
		if(ineq.equals(types[i]))
			tp = types[i+1];
	return tp;
	}
	
	/* Direction
	 * 0 - symmetric
	 * 1 - A->B
	 * -1 - A<-B
	 */
	public static int inequalityDirection(String ineq){
	int dir = 0;
		String asymmetric[] = {
		"A<WT=B=AB",
		"WT=B=AB<A",
		"A<B=AB<WT",
		"B=AB<A<WT",
		"A<WT<B=AB",
		"B=AB<WT<A",
		"WT<A<B=AB",
		"WT<B=AB<A",
		"WT=A<AB<B",
		"WT=A<B<AB",
		"B<WT=A<AB",
		"AB<WT=A<B",
		"B<AB<WT=A",
		"AB<B<WT=A",
		"A<AB<B<WT",
		"B<WT<A<AB",
		"AB<A<WT<B",
		"WT<B<AB<A",
		"B<WT=A=AB",
		"WT=A=AB<B",
		"B<A=AB<WT",
		"A=AB<B<WT",
		"B<WT<A=AB",
		"A=AB<WT<B",
		"WT<B<A=AB",
		"WT<A=AB<B",
		"WT=B<AB<A",
		"WT=B<A<AB",
		"A<WT=B<AB",
		"AB<WT=B<A",
		"A<AB<WT=B",
		"AB<A<WT=B",
		"B<AB<A<WT",
		"A<WT<B<AB",
		"AB<B<WT<A",
		"WT<A<AB<B"};
		int asymmetric_dirs[] = {
				-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
				 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,				
		};
		for(int i=0;i<asymmetric.length;i++)
			if(ineq.equals(asymmetric[i]))
				dir = asymmetric_dirs[i];
		return dir;
	}
	

}
