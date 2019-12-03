package fr.curie.BiNoM.pathways.MaBoSS;

import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
//import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import vdaoengine.data.VDataTable;
import vdaoengine.data.io.VDatReadWrite;
import fr.curie.BiNoM.pathways.utils.GeneticInteractionNetworks;
import fr.curie.BiNoM.pathways.utils.SimpleTable;
import fr.curie.BiNoM.pathways.utils.Utils;

public class MaBoSSProbTrajFile_svn {

	int type = 0; // 0 - wild type, 1 - single mutant, 2 - double mutant
	String interactor1 = "_";
	String interactor2 = "_";
	int interactorType1 = 0; // 0 - undefined, -1 - knockout, +1 - overexpression, +2 - complex mutant, +3 - logic rule mutant
	int interactorType2 = 0; 	
	//SimpleTable table = new SimpleTable();
	Vector<String> phenotypes = new Vector<String>();
	Vector<Float> probabilities = new Vector<Float>();
	
	public static boolean swapPhenotypesToHaveSameDirection = true;
	
	public static float thresholdForDistinctFitness = 0.2f;
	
	public static float numberOfStandardDeviations = 1f;
	
	public static void main(String[] args) {
		try{
		MaBoSSProbTrajFile_svn prob = new MaBoSSProbTrajFile_svn();
		String folder = "";
		String prefix = "";
		String out = "";
		String phenotype = "";
		String table = "";
		String short_name = "";
		String mergedPhenotypes = null;
		String descriptionFile = "";
		boolean makeTable = false;
		boolean makeLogicMutantTable = false;
		boolean normTable = false;
		boolean computeGeneticInteractions = false;
		
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-folder"))
				folder = args[i+1];
			if(args[i].equals("-prefix"))
				prefix = args[i+1];
			if(args[i].equals("-out"))
				out = args[i+1];
			if(args[i].equals("-phenotype"))
				phenotype = args[i+1];
			if(args[i].equals("-phenotype_short"))
				short_name = args[i+1];
			if(args[i].equals("-table"))
				table = args[i+1];
			if(args[i].equals("-mergedphenotypes"))
				mergedPhenotypes = args[i+1];
			if(args[i].equals("-maketable"))
				makeTable = true;
			if(args[i].equals("-makelogicmutanttable"))
				makeLogicMutantTable = true;
			if(args[i].equals("-description"))
				descriptionFile = args[i+1];
			if(args[i].equals("-normtable"))
				normTable = true;
			if(args[i].equals("-makeinter"))
				computeGeneticInteractions = true;
		}
		
		if(makeTable){
			prob.makeProbabilityTableFromFolder(folder,prefix,out);
		}
		if(makeLogicMutantTable){
			prob.makeLogicMutantTableFromFolder(folder,prefix,descriptionFile, out);
		}		
		if(normTable){
			MaBoSSProbTrajFile_svn.normalizeProbabilityTable(table, 0.01f, mergedPhenotypes);
		}
		if(computeGeneticInteractions){
			VDataTable vtable = VDatReadWrite.LoadFromVDatFile(table);
			makeGeneticInteractionTable(vtable,phenotype,short_name,out);
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void load(String fn, String prefix){
		String fileName = (new File(fn)).getName();
		String pair = "";
		if(fileName.endsWith("_probtraj.csv"))
			pair = fileName.substring(0, fileName.length()-13);
		// modified the declaration of type and pair to WT
		if(pair.equals(prefix)) {
			pair = "";
			type = 0;
		}else{
		if(pair.startsWith(prefix))
			pair = pair.substring(prefix.length()+1, pair.length());
		}
//		if(pair.equals(prefix.substring(0,prefix.length()-1)))
//			pair = "";
		if(!pair.equals("")){
			type = 1;
			pair = pair.replaceFirst("--", "%");
			StringTokenizer st = new StringTokenizer(pair,"%");
			interactor1 = st.nextToken();
			if(st.hasMoreTokens()){
				interactor2 = st.nextToken();
				type = 2;
			}
			if(interactor1.endsWith("_ko")) interactorType1 = -1;
			if(interactor2.endsWith("_ko")) interactorType2 = -1;
			if(interactor1.endsWith("_oe")) interactorType1 = +1;
			if(interactor2.endsWith("_oe")) interactorType2 = +1;
			if(interactor1.endsWith("_cm")) { interactorType1 = +2; interactor1 = interactor1.substring(0,interactor1.length()-3); }
			if(interactor2.endsWith("_cm")) { interactorType2 = +2; interactor2 = interactor2.substring(0,interactor2.length()-3); } 
			if(interactor1.endsWith("_lm")) { interactorType1 = +3; interactor1	= interactor1.substring(0,interactor1.length()-3); }
			if(interactor2.endsWith("_lm")) { interactorType2 = +3; interactor2 = interactor2.substring(0,interactor2.length()-3); }
			}
//		else{
//			type = 0;
//		}
		SimpleTable table = new SimpleTable();
		//need to increase the load buffer so that it reaches the end of the file
//		table.LoadFromSimpleDatFile(fn, true, "\t", 10000000);
		table.LoadFromSimpleDatFile(fn, true, "\t", 100000000);
		int last = table.rowCount-1;
		int k=5;
		for(int i=0;i<table.colCount;i++)
			if(table.fieldNames[i].equals("State")){
				k = i; break;
			}
		while(k<table.colCount-2){
			String phenotype = table.stringTable[last][k];
			if(phenotype!=null)if(!phenotype.trim().equals("")){
				float prob = Float.parseFloat(table.stringTable[last][k+1]);
				//errprob not used throughout the file
//				float errprob = Float.parseFloat(table.stringTable[last][k+2]);
				phenotypes.add(phenotype);
				probabilities.add(prob);
			}
			k+=3;
		}
	}
	
	public void makeProbabilityTableFromFolder(String folder, String prefix, String outfile) throws Exception{
		File dir = new File(folder);
		Vector<MaBoSSProbTrajFile_svn> trajs = new Vector<MaBoSSProbTrajFile_svn>();
		Vector<String> phenotypes = new Vector<String>();
		int kk =0;
		for(File f: dir.listFiles())if(f.getName().endsWith("probtraj.csv")){
			kk++;
			System.out.println(kk+": Loading "+f.getName());
			MaBoSSProbTrajFile_svn traj = new MaBoSSProbTrajFile_svn();
			traj.load(f.getAbsolutePath(), prefix);
			trajs.add(traj);
			for(String phen: traj.phenotypes){
				if(!phenotypes.contains(phen))
					phenotypes.add(phen);
			}
		}
		Collections.sort(phenotypes);
		// Sort phenotypes
		Vector<String> sorted_phenotypes = new Vector<String>();
		if(phenotypes.contains("<nil>"))
			sorted_phenotypes.add("<nil>");
		for(String s: phenotypes){
			if(!s.contains(" -- "))if(!s.equals("<nil>"))
				sorted_phenotypes.add(s);
		}
		for(String s: phenotypes){
			if(!sorted_phenotypes.contains(s))
				sorted_phenotypes.add(s);
		}
		
		FileWriter fw = new FileWriter(outfile);
		fw.write("ID\tTYPE\tINTERACTOR1\tINTERACTOR_TYPE1\tINTERACTOR2\tINTERACTOR_TYPE2\t");
		for(String s: sorted_phenotypes)
			fw.write(s.replace(" -- ", "/")+"\t");
		fw.write("\n");
		for(int i=0;i<trajs.size();i++){
			MaBoSSProbTrajFile_svn traj = trajs.get(i);
			String id = traj.interactor1;
			if(!traj.interactor2.equals("_"))
				id+="__"+traj.interactor2;
			String inter1 = traj.interactor1;
			String inter2 = traj.interactor2;
			String inter_type1 = "UNDEFINED";
			String inter_type2 = "UNDEFINED";
			String type = "WT";
			if(traj.type>=1)
				type = traj.type==1?"SINGLE":"DOUBLE";
			if(traj.type>=1)
				inter_type1 = traj.interactorType1>0?"OE":"KO";
			if(traj.type==2)
				inter_type2 = traj.interactorType2>0?"OE":"KO";			
			fw.write(id+"\t"+type+"\t"+inter1+"\t"+inter_type1+"\t"+inter2+"\t"+inter_type2+"\t");
			for(String s: sorted_phenotypes){
				int k = traj.phenotypes.indexOf(s);
				float f = 0f;
				if(k>-1) f = traj.probabilities.get(k);
				fw.write(f+"\t");	
			}
			fw.write("\n");
		}
		fw.close();
		
		VDataTable vt = VDatReadWrite.LoadFromSimpleDatFile(outfile, true, "\t");
		for(String s: sorted_phenotypes)
			vt.fieldTypes[vt.fieldNumByName(s.replace(" -- ", "/"))] = VDataTable.NUMERICAL;
		VDatReadWrite.saveToVDatFile(vt, outfile+".dat");
	}
	
	public void makeLogicMutantTableFromFolder(String folder, String prefix, String descriptionFile, String outfile) throws Exception{
		
		Vector<String> descs = Utils.loadStringListFromFile(descriptionFile);
		HashMap<String, String> descriptions = new HashMap<String, String>();
		for(String s: descs)if(!s.trim().equals("")){
			StringTokenizer st = new StringTokenizer(s,"\t");
			String id = st.nextToken();
			String logics = st.nextToken();
//			String select = "0";
//			if(st.hasMoreTokens())
//				select = st.nextToken();
//			if(select.equals("1"))
				descriptions.put(id,logics);
		}
		
		File dir = new File(folder);
		Vector<MaBoSSProbTrajFile_svn> trajs = new Vector<MaBoSSProbTrajFile_svn>();
		Vector<String> phenotypes = new Vector<String>();
		File list[] = dir.listFiles();
		int kk =0;
		for(File f: list)if(f.getName().endsWith("probtraj.csv")){
			kk++;
			System.out.println(kk+": Loading "+f.getName());
			MaBoSSProbTrajFile_svn traj = new MaBoSSProbTrajFile_svn();
			traj.load(f.getAbsolutePath(), prefix);
			trajs.add(traj);
			for(String phen: traj.phenotypes){
				if(!phenotypes.contains(phen))
					phenotypes.add(phen);
			}
		}
		Collections.sort(phenotypes);
		// Sort phenotypes
		Vector<String> sorted_phenotypes = new Vector<String>();
		if(phenotypes.contains("<nil>"))
			sorted_phenotypes.add("<nil>");
		for(String s: phenotypes){
			if(!s.contains(" -- "))if(!s.equals("<nil>"))
				sorted_phenotypes.add(s);
		}
		for(String s: phenotypes){
			if(!sorted_phenotypes.contains(s))
				sorted_phenotypes.add(s);
		}
		
		FileWriter fw = new FileWriter(outfile);
//		fw.write("ID\tTYPE\tDESCRIPTION\tLEVEL\tINTERACTOR1\tINTERACTOR_TYPE1\tINTERACTOR2\tINTERACTOR_TYPE2\tCONDITION\t");
		fw.write("ID\tTYPE\tDESCRIPTION\tLEVEL\tINTERACTOR1\tINTERACTOR_TYPE1\tINTERACTOR2\tINTERACTOR_TYPE2\t");
		for(String s: sorted_phenotypes)
			fw.write(s.replace(" -- ", "/")+"\t");
		fw.write("\n");
		for(int i=0;i<trajs.size();i++){
			MaBoSSProbTrajFile_svn traj = trajs.get(i);
			String id = traj.interactor1;
			if(!traj.interactor2.equals("_"))
				id+="__"+traj.interactor2;
			String inter1 = traj.interactor1;
			String inter2 = traj.interactor2;
			String inter_type1 = "UNDEFINED";
			String inter_type2 = "UNDEFINED";
			String type = "WT";
			if(traj.type>=1)
				type = traj.type==1?"SINGLE":"DOUBLE";
			if(traj.type>=1){
				if(traj.interactorType1==-1) inter_type1 = "KO";
				if(traj.interactorType1==+1) inter_type1 = "OE";
				if(traj.interactorType1==+2) inter_type1 = "CM";
				if(traj.interactorType1==+3) inter_type1 = "LM";
			}
			if(traj.type==2){
				if(traj.interactorType2==-1) inter_type2 = "KO";
				if(traj.interactorType2==+1) inter_type2 = "OE";
				if(traj.interactorType2==+2) inter_type2 = "CM";
				if(traj.interactorType2==+3) inter_type2 = "LM";
			}
			String description = "NA";
			String id1 = traj.interactor1;
			if(id1.endsWith("_lm")) id1 = id1.substring(0, id1.length()-3);
			if(descriptions.get(id1)!=null)
			description = descriptions.get(id1);
			String level = "0";
			StringTokenizer st = new StringTokenizer(description,";");
			while(st.hasMoreTokens()){
				String s = st.nextToken();
				if(s.startsWith("LEVEL"))
					level = s.substring(5, s.length());
			}
//			if(!description.equals("NA")){		//better to leave the NA if no description on description file
			fw.write(id+"\t"+type+"\t"+description+"\t"+level+"\t"+inter1+"\t"+inter_type1+"\t"+inter2+"\t"+inter_type2+"\t");
			for(String s: sorted_phenotypes){
				int k = traj.phenotypes.indexOf(s);
				float f = 0f;
				if(k>-1) f = traj.probabilities.get(k);
				fw.write(f+"\t");	
			}
			fw.write("\n");
//			}
		}
		fw.close();
		
		VDataTable vt = VDatReadWrite.LoadFromSimpleDatFile(outfile, true, "\t");
		for(String s: sorted_phenotypes)
			vt.fieldTypes[vt.fieldNumByName(s.replace(" -- ", "/"))] = VDataTable.NUMERICAL;
		VDatReadWrite.saveToVDatFile(vt, outfile+".dat");
	}
	
	
	public static void makeGeneticInteractionTable(VDataTable vt, String phenotype, String phenotype_short, String outfile_prefix) throws Exception{
		makeGeneticInteractionTable(vt, phenotype, phenotype_short, outfile_prefix, -1);
	}
	
	public static void makeGeneticInteractionTable(VDataTable vt, String phenotype, String phenotype_short, String outfile_prefix, int forcedModelChoice) throws Exception{
		
		Vector<String> elementaryPhenotypes = new Vector<String>();
		StringTokenizer st = new StringTokenizer(phenotype,"+");
		while(st.hasMoreTokens()){
			elementaryPhenotypes.add(st.nextToken());
		}
		
		FileWriter fwn = new FileWriter(outfile_prefix+"_"+phenotype_short+"_nodes.txt");
		fwn.write("INTERACTOR\tINTERACTOR_TYPE1\tP\tINTERACTOR_NAME\n");
		FileWriter fwea = new FileWriter(outfile_prefix+"_"+phenotype_short+"_edges.txt");
		FileWriter fwe = new FileWriter(outfile_prefix+"_"+phenotype_short+"_edges_nonzero.txt");
		FileWriter fwes = new FileWriter(outfile_prefix+"_"+phenotype_short+"_edges_selected.txt");
		FileWriter fwess = new FileWriter(outfile_prefix+"_"+phenotype_short+"_edges_selected_stringent2.txt");
		FileWriter fwes3 = new FileWriter(outfile_prefix+"_"+phenotype_short+"_edges_selected_stringent3.txt");
		  fwe.write("INTERACTOR1\tINTERACTOR_TYPE1\tINTERACTOR2\tINTERACTOR_TYPE2\tINTERACTION_ID\tP1\tP2\tP12\tEPS_ADD\tEPS_MULT\tEPS_MIN\tEPS_MAX\tEPS_LOG\tEPS_BEST\tEPS_BEST_NORM\tINEQUALITY\tINEQ_TYPE\tINEQ_DIR\n");
		 fwes.write("INTERACTOR1\tINTERACTOR_TYPE1\tINTERACTOR2\tINTERACTOR_TYPE2\tINTERACTION_ID\tP1\tP2\tP12\tEPS_ADD\tEPS_MULT\tEPS_MIN\tEPS_MAX\tEPS_LOG\tEPS_BEST\tEPS_BEST_NORM\tINEQUALITY\tINEQ_TYPE\tINEQ_DIR\n");
		 fwea.write("INTERACTOR1\tINTERACTOR_TYPE1\tINTERACTOR2\tINTERACTOR_TYPE2\tINTERACTION_ID\tP1\tP2\tP12\tEPS_ADD\tEPS_MULT\tEPS_MIN\tEPS_MAX\tEPS_LOG\tEPS_BEST\tEPS_BEST_NORM\tINEQUALITY\tINEQ_TYPE\tINEQ_DIR\n");
		fwess.write("INTERACTOR1\tINTERACTOR_TYPE1\tINTERACTOR2\tINTERACTOR_TYPE2\tINTERACTION_ID\tP1\tP2\tP12\tEPS_ADD\tEPS_MULT\tEPS_MIN\tEPS_MAX\tEPS_LOG\tEPS_BEST\tEPS_BEST_NORM\tINEQUALITY\tINEQ_TYPE\tINEQ_DIR\n");
		fwes3.write("INTERACTOR1\tINTERACTOR_TYPE1\tINTERACTOR2\tINTERACTOR_TYPE2\tINTERACTION_ID\tP1\tP2\tP12\tEPS_ADD\tEPS_MULT\tEPS_MIN\tEPS_MAX\tEPS_LOG\tEPS_BEST\tEPS_BEST_NORM\tINEQUALITY\tINEQ_TYPE\tINEQ_DIR\n");
		HashMap<String, Integer> singles = new HashMap<String, Integer>();
//		int wtline = -1;
		float wt_prob = 1f;
		Vector<String> singleko = new Vector<String>();
		Vector<String> singleoe = new Vector<String>();
		Vector<String> singles_v = new Vector<String>();
		for(int i=0;i<vt.rowCount;i++){
			String type = vt.stringTable[i][vt.fieldNumByName("TYPE")];
			if(type.equals("SINGLE")){
				singles.put(vt.stringTable[i][vt.fieldNumByName("ID")], i);
				String id1 = vt.stringTable[i][vt.fieldNumByName("ID")];
				String name = id1.substring(0,id1.length()-3);
				float P = sumColumns(i,vt,elementaryPhenotypes);
				fwn.write(vt.stringTable[i][vt.fieldNumByName("ID")]+"\t"+vt.stringTable[i][vt.fieldNumByName("INTERACTOR_TYPE1")]+"\t"+P+"\t"+name+"\n");
				if(vt.stringTable[i][vt.fieldNumByName("INTERACTOR_TYPE1")].equals("KO")){
					String id = vt.stringTable[i][vt.fieldNumByName("ID")];
					if(!singleko.contains(id))
						singleko.add(id);
					if(!singles_v.contains(id))
						singles_v.add(id);
				}
				if(vt.stringTable[i][vt.fieldNumByName("INTERACTOR_TYPE1")].equals("OE")){
					String id = vt.stringTable[i][vt.fieldNumByName("ID")];
					if(!singleoe.contains(id))
						singleoe.add(id);
					if(!singles_v.contains(id))
						singles_v.add(id);
			}
			if(type.equals("WT")){
//				wtline = i;
				wt_prob = sumColumns(i,vt,elementaryPhenotypes);
			}
		}
		}
		
		Collections.sort(singleko);
		Collections.sort(singleoe);
		Collections.sort(singles_v);
		float epimatrix[][] = new float[singles_v.size()][singles_v.size()];
		float epimatrix_ko[][] = new float[singleko.size()][singleko.size()];
		float epimatrix_oe[][] = new float[singleoe.size()][singleoe.size()];
		
		// 1) Determine the best null model
		// 2) For each null model, find bias correction factor
		// 3) Write corrected value into the EPS_BEST column 
		
		Vector<Float> P1s = new Vector<Float>();
		Vector<Float> P2s = new Vector<Float>();
		Vector<Float> P12s = new Vector<Float>();
		for(int i=0;i<vt.rowCount;i++){
			String type = vt.stringTable[i][vt.fieldNumByName("TYPE")];
			if(type.equals("DOUBLE")){
				String inter1 = vt.stringTable[i][vt.fieldNumByName("INTERACTOR1")];
				String inter2 = vt.stringTable[i][vt.fieldNumByName("INTERACTOR2")];
//				String inter1_type = vt.stringTable[i][vt.fieldNumByName("INTERACTOR_TYPE1")];
//				String inter2_type = vt.stringTable[i][vt.fieldNumByName("INTERACTOR_TYPE2")];
				//float P12 = Float.parseFloat(vt.stringTable[i][vt.fieldNumByName(phenotype)]);
				float P12 = sumColumns(i,vt,elementaryPhenotypes);
				int i1 = singles.get(inter1);
				int i2 = singles.get(inter2);
				//float P1 = Float.parseFloat(vt.stringTable[i1][vt.fieldNumByName(phenotype)]);
				float P1 = sumColumns(i1,vt,elementaryPhenotypes);
				//float P2 = Float.parseFloat(vt.stringTable[i2][vt.fieldNumByName(phenotype)]);
				float P2 = sumColumns(i2,vt,elementaryPhenotypes);
				P1s.add(P1);
				P2s.add(P2);
				P12s.add(P12);
			}
		}
		
		GeneticInteractionNetworks gn = new GeneticInteractionNetworks();
		gn.ChooseBestNullEpistasisModel(P1s, P2s, P12s, forcedModelChoice);
		System.out.println("\nPhenotype: "+phenotype+" ("+phenotype_short+")");
		for(int i=0;i<GeneticInteractionNetworks.numberofnullmodels;i++){
			System.out.println(GeneticInteractionNetworks.modelNames[i]+":\t corr="+gn.correlations[i]+"\t alpha="+gn.alphas[i]);			
		}
		System.out.println("Best model: "+GeneticInteractionNetworks.modelNames[GeneticInteractionNetworks.bestNullEpistasisModel]);
		System.out.println("Negative threshold = "+gn.threshold_negative);
		System.out.println("Positive threshold = "+gn.threshold_positive);
		
		
		for(int i=0;i<vt.rowCount;i++){
			String type = vt.stringTable[i][vt.fieldNumByName("TYPE")];
			if(type.equals("DOUBLE")){
				String inter1 = vt.stringTable[i][vt.fieldNumByName("INTERACTOR1")];
				String inter2 = vt.stringTable[i][vt.fieldNumByName("INTERACTOR2")];
				String inter1_type = vt.stringTable[i][vt.fieldNumByName("INTERACTOR_TYPE1")];
				String inter2_type = vt.stringTable[i][vt.fieldNumByName("INTERACTOR_TYPE2")];
				//float P12 = Float.parseFloat(vt.stringTable[i][vt.fieldNumByName(phenotype)]);
				float P12 = sumColumns(i,vt,elementaryPhenotypes);
				int i1 = singles.get(inter1);
				int i2 = singles.get(inter2);
				//float P1 = Float.parseFloat(vt.stringTable[i1][vt.fieldNumByName(phenotype)]);
				//float P2 = Float.parseFloat(vt.stringTable[i2][vt.fieldNumByName(phenotype)]);
				float P1 = sumColumns(i1,vt,elementaryPhenotypes);
				float P2 = sumColumns(i2,vt,elementaryPhenotypes);
				
				float EPS_ADD = P12-gn.alphas[GeneticInteractionNetworks.EPISTASIS_NULL_MODEL_ADDITIVE]*(P1+P2);
				float EPS_MLT = P12-gn.alphas[GeneticInteractionNetworks.EPISTASIS_NULL_MODEL_MULTIPLICATIVE]*(P1*P2);
				float EPS_MIN = P12-gn.alphas[GeneticInteractionNetworks.EPISTASIS_NULL_MODEL_MIN]*Math.min(P1,P2);
				float EPS_MAX = P12-gn.alphas[GeneticInteractionNetworks.EPISTASIS_NULL_MODEL_MAX]*Math.max(P1,P2);
				float EPS_LOG = P12-gn.alphas[GeneticInteractionNetworks.EPISTASIS_NULL_MODEL_LOG]*(gn.log2((float)((Math.pow(2,P1)-1)*(Math.pow(2,P2)-1)+1)));
				
				float EPS_BEST = 0f;
				
				String ineq = GeneticInteractionNetworks.interactionInequality(wt_prob, P1, P2, P12, thresholdForDistinctFitness);
				String ineq_type = GeneticInteractionNetworks.inequalityType(ineq);
				int ineq_dir_i = GeneticInteractionNetworks.inequalityDirection(ineq);
				String ineq_dir = "symmetric";
				if(ineq_dir_i==1) ineq_dir="A->B";
				if(ineq_dir_i==-1) ineq_dir="A<-B";
				
				if(GeneticInteractionNetworks.bestNullEpistasisModel==GeneticInteractionNetworks.EPISTASIS_NULL_MODEL_ADDITIVE) EPS_BEST = EPS_ADD;
				if(GeneticInteractionNetworks.bestNullEpistasisModel==GeneticInteractionNetworks.EPISTASIS_NULL_MODEL_MULTIPLICATIVE) EPS_BEST = EPS_MLT;
				if(GeneticInteractionNetworks.bestNullEpistasisModel==GeneticInteractionNetworks.EPISTASIS_NULL_MODEL_MIN) EPS_BEST = EPS_MIN;
				if(GeneticInteractionNetworks.bestNullEpistasisModel==GeneticInteractionNetworks.EPISTASIS_NULL_MODEL_MAX) EPS_BEST = EPS_MAX;
				if(GeneticInteractionNetworks.bestNullEpistasisModel==GeneticInteractionNetworks.EPISTASIS_NULL_MODEL_LOG) EPS_BEST = EPS_LOG;
				
				if(swapPhenotypesToHaveSameDirection){
					if(ineq_dir_i==-1){
						String temp = inter1; inter1=inter2; inter2=temp;
							   temp = inter1_type; inter1_type=inter2_type; inter2_type=temp;
						float ftemp = P1; P1=P2; P2=ftemp;
						ineq = Utils.replaceString(ineq, "A", "$");
						ineq = Utils.replaceString(ineq, "B", "A");
						ineq = Utils.replaceString(ineq, "$", "B");
						ineq = Utils.replaceString(ineq, "BA", "AB");
						ineq_dir = "A->B";
					}
				}
				
				String interaction_id = inter1+"__"+inter2;
				if(inter1.compareTo(inter2)<0)
					interaction_id = inter2+"__"+inter1;
				float EPS_BEST_NORMALIZED = 0f;
				if(EPS_BEST>0)
					EPS_BEST_NORMALIZED = EPS_BEST/gn.threshold_positive;
				else
					EPS_BEST_NORMALIZED = EPS_BEST/gn.threshold_negative;
				
				
				fwea.write(inter1+"\t"+inter1_type+"\t"+inter2+"\t"+inter2_type+"\t"+interaction_id+"\t"+P1+"\t"+P2+"\t"+P12+"\t"+EPS_ADD+"\t"+EPS_MLT+"\t"+EPS_MIN+"\t"+EPS_MAX+"\t"+EPS_LOG+"\t"+EPS_BEST+"\t"+EPS_BEST_NORMALIZED+"\t"+ineq+"\t"+ineq_type+"\t"+ineq_dir+"\n");
				if(EPS_BEST!=0){
						 fwe.write(inter1+"\t"+inter1_type+"\t"+inter2+"\t"+inter2_type+"\t"+interaction_id+"\t"+P1+"\t"+P2+"\t"+P12+"\t"+EPS_ADD+"\t"+EPS_MLT+"\t"+EPS_MIN+"\t"+EPS_MAX+"\t"+EPS_LOG+"\t"+EPS_BEST+"\t"+EPS_BEST_NORMALIZED+"\t"+ineq+"\t"+ineq_type+"\t"+ineq_dir+"\n");
				}
				if((EPS_BEST>gn.threshold_positive*numberOfStandardDeviations)||(EPS_BEST<-gn.threshold_negative*numberOfStandardDeviations)){
						fwes.write(inter1+"\t"+inter1_type+"\t"+inter2+"\t"+inter2_type+"\t"+interaction_id+"\t"+P1+"\t"+P2+"\t"+P12+"\t"+EPS_ADD+"\t"+EPS_MLT+"\t"+EPS_MIN+"\t"+EPS_MAX+"\t"+EPS_LOG+"\t"+EPS_BEST+"\t"+EPS_BEST_NORMALIZED+"\t"+ineq+"\t"+ineq_type+"\t"+ineq_dir+"\n");
				}
				if((EPS_BEST>2*gn.threshold_positive*numberOfStandardDeviations)||(EPS_BEST<-2*gn.threshold_negative*numberOfStandardDeviations)){
					fwess.write(inter1+"\t"+inter1_type+"\t"+inter2+"\t"+inter2_type+"\t"+interaction_id+"\t"+P1+"\t"+P2+"\t"+P12+"\t"+EPS_ADD+"\t"+EPS_MLT+"\t"+EPS_MIN+"\t"+EPS_MAX+"\t"+EPS_LOG+"\t"+EPS_BEST+"\t"+EPS_BEST_NORMALIZED+"\t"+ineq+"\t"+ineq_type+"\t"+ineq_dir+"\n");
			    }
				if((EPS_BEST>3*gn.threshold_positive*numberOfStandardDeviations)||(EPS_BEST<-3*gn.threshold_negative*numberOfStandardDeviations)){
					fwes3.write(inter1+"\t"+inter1_type+"\t"+inter2+"\t"+inter2_type+"\t"+interaction_id+"\t"+P1+"\t"+P2+"\t"+P12+"\t"+EPS_ADD+"\t"+EPS_MLT+"\t"+EPS_MIN+"\t"+EPS_MAX+"\t"+EPS_LOG+"\t"+EPS_BEST+"\t"+EPS_BEST_NORMALIZED+"\t"+ineq+"\t"+ineq_type+"\t"+ineq_dir+"\n");
			    }				
				int is1 = singles_v.indexOf(inter1);
				int is2 = singles_v.indexOf(inter2);
				int iko1 = singleko.indexOf(inter1);
				int iko2 = singleko.indexOf(inter2);
				int ioe1 = singleoe.indexOf(inter1);
				int ioe2 = singleoe.indexOf(inter2);
				epimatrix[is1][is2] = EPS_BEST;
				epimatrix[is2][is1] = EPS_BEST;
				if((iko1>=0)&&(iko2>=0)) {epimatrix_ko[iko1][iko2] = EPS_BEST; epimatrix_ko[iko1][iko2] = EPS_BEST;}
				if((ioe1>=0)&&(ioe2>=0)) {epimatrix_oe[ioe2][ioe1] = EPS_BEST; epimatrix_oe[ioe2][ioe1] = EPS_BEST;}
			}
			if(type.equals("SINGLE")){
				String inter1 = vt.stringTable[i][vt.fieldNumByName("INTERACTOR1")];
				//float P1 = Float.parseFloat(vt.stringTable[i][vt.fieldNumByName(phenotype)]);
				float P1 = sumColumns(i,vt,elementaryPhenotypes);
				int is1 = singles_v.indexOf(inter1);
				int iko1 = singleko.indexOf(inter1);
				int ioe1 = singleoe.indexOf(inter1);
				epimatrix[is1][is1] = P1;
				if((iko1>=0)) epimatrix_ko[iko1][iko1] = P1;
				if((ioe1>=0)) epimatrix_oe[ioe1][ioe1] = P1;
			}
		}
		fwn.close();
		fwe.close();
		fwea.close();
		fwes.close();
		fwess.close();
		fwes3.close();
		
		VDataTable vte = VDatReadWrite.LoadFromSimpleDatFile(outfile_prefix+"_"+phenotype_short+"_edges.txt", true, "\t");
		vte.fieldTypes[vte.fieldNumByName("P1")] = VDataTable.NUMERICAL;
		vte.fieldTypes[vte.fieldNumByName("P2")] = VDataTable.NUMERICAL;
		vte.fieldTypes[vte.fieldNumByName("P12")] = VDataTable.NUMERICAL;
		for(int i=0;i<vte.colCount;i++)
			if(vte.fieldNames[i].startsWith("EPS_"))
				vte.fieldTypes[i] = VDataTable.NUMERICAL;
		VDatReadWrite.saveToVDatFile(vte, outfile_prefix+"_"+phenotype_short+"_edges.dat");
		// Writing down the epistatic profile matrices
		FileWriter fw = new FileWriter(outfile_prefix+"_"+phenotype_short+"_epi.txt");
		fw.write("MUTANT\t");
		for(int i=0;i<singles_v.size();i++)
			fw.write(singles_v.get(i)+"\t");
		fw.write("\n");
		for(int i=0;i<singles_v.size();i++){
			fw.write(singles_v.get(i)+"\t");
			for(int j=0;j<singles_v.size();j++){
				fw.write(epimatrix[i][j]+"\t");
			}
			fw.write("\n");
		}
		fw.close();
		fw = new FileWriter(outfile_prefix+"_"+phenotype_short+"_epiko.txt");
		fw.write("MUTANT\t");
		for(int i=0;i<singleko.size();i++)
			fw.write(singleko.get(i)+"\t");
		fw.write("\n");
		for(int i=0;i<singleko.size();i++){
			fw.write(singleko.get(i)+"\t");
			for(int j=0;j<singleko.size();j++){
				fw.write(epimatrix_ko[i][j]+"\t");
			}
			fw.write("\n");
		}
		fw.close();
		fw = new FileWriter(outfile_prefix+"_"+phenotype_short+"_epioe.txt");
		fw.write("MUTANT\t");
		for(int i=0;i<singleoe.size();i++)
			fw.write(singleoe.get(i)+"\t");
		fw.write("\n");
		for(int i=0;i<singleoe.size();i++){
			fw.write(singleoe.get(i)+"\t");
			for(int j=0;j<singleoe.size();j++){
				fw.write(epimatrix_oe[i][j]+"\t");
			}
			fw.write("\n");
		}		
		fw.close();

		VDataTable vt1 = VDatReadWrite.LoadFromSimpleDatFile(outfile_prefix+"_"+phenotype_short+"_epi.txt", true, "\t");
		for(int i=1;i<vt1.colCount;i++)
			vt1.fieldTypes[i] = VDataTable.NUMERICAL;
		VDatReadWrite.saveToVDatFile(vt1, outfile_prefix+"_"+phenotype_short+"_epi.dat");
		vt1 = VDatReadWrite.LoadFromSimpleDatFile(outfile_prefix+"_"+phenotype_short+"_epiko.txt", true, "\t");
		for(int i=1;i<vt1.colCount;i++)
			vt1.fieldTypes[i] = VDataTable.NUMERICAL;
		VDatReadWrite.saveToVDatFile(vt1, outfile_prefix+"_"+phenotype_short+"_epiko.dat");
		vt1 = VDatReadWrite.LoadFromSimpleDatFile(outfile_prefix+"_"+phenotype_short+"_epioe.txt", true, "\t");
		for(int i=1;i<vt1.colCount;i++)
			vt1.fieldTypes[i] = VDataTable.NUMERICAL;
		VDatReadWrite.saveToVDatFile(vt1, outfile_prefix+"_"+phenotype_short+"_epioe.dat");
		
	}
	
	public static float sumColumns(int rowNumber, VDataTable vt, Vector<String> columnNames){
		float res = 0f;
		for(int i=0;i<columnNames.size();i++)if(vt.fieldNumByName(columnNames.get(i))!=-1){
			res+=Float.parseFloat(vt.stringTable[rowNumber][vt.fieldNumByName(columnNames.get(i))]);
		}
		return res;
	}
	
	
	/*
	 * Example of merged phenotypes
	 * Apoptosis=Apoptosis+NonACD/Apoptosis;NonACD=NonACD+NonACD/Apoptosis
	 * Use no spaces!!
	 */
	public static void normalizeProbabilityTable(String fn, float thresh, String mergedPhenotypes){
		VDataTable vt = VDatReadWrite.LoadFromSimpleDatFile(fn, true, "\t");
		// modified the number of first phenotype column
//		int numberOfFirstColumns = 9;
		int numberOfFirstColumns = 6;
		String fn1 = fn.substring(0, fn.length()-4)+"_norm";
		float wt_probs[] = new float[vt.colCount-numberOfFirstColumns];
		float prob_averages[] = new float[vt.colCount-numberOfFirstColumns];
		for(int i=0;i<vt.rowCount;i++){
			String id = vt.stringTable[i][vt.fieldNumByName("ID")];
			if(id.equals("_")){
				for(int k=numberOfFirstColumns;k<vt.colCount;k++)
					wt_probs[k-numberOfFirstColumns] = Float.parseFloat(vt.stringTable[i][k]);
			break;
			}
		}
		
		if((mergedPhenotypes==null)||(mergedPhenotypes.equals(""))){
			
		for(int i=0;i<vt.rowCount;i++){
			for(int k=numberOfFirstColumns;k<vt.colCount;k++){
				float f = Float.parseFloat(vt.stringTable[i][k]);
				prob_averages[k-numberOfFirstColumns]+=f;
				if(wt_probs[k-numberOfFirstColumns]<0.01)
					f = f;
				else
					f/=wt_probs[k-numberOfFirstColumns];
				vt.stringTable[i][k] = ""+f;
			}
		}
		}else{// do merge phenotypes
		
			System.out.println("Merging phenotypes: "+mergedPhenotypes);
			
			HashMap<String, Vector<String>> phenotypeMap = new HashMap<String, Vector<String>>();
			StringTokenizer st = new StringTokenizer(mergedPhenotypes,";");
			while(st.hasMoreTokens()){
				String eq = st.nextToken();
				StringTokenizer st1 = new StringTokenizer(eq,"=");
				String phen = st1.nextToken();
				String phensum = st1.nextToken();
				Vector<String> phensumv = new Vector<String>();
				StringTokenizer st2 = new StringTokenizer(phensum,"+");
				while(st2.hasMoreTokens())
					phensumv.add(st2.nextToken());
				phenotypeMap.put(phen,phensumv);
			}
			
			Iterator<String> it = phenotypeMap.keySet().iterator();
			while(it.hasNext()){
				String phname = it.next();
				if(vt.fieldNumByName(phname)==-1){
					vt.addNewColumn(phname, "", "", VDataTable.NUMERICAL, "0");
				}
			}
			prob_averages = new float[vt.colCount-numberOfFirstColumns];
			
			for(int i=0;i<vt.rowCount;i++){
				for(int k=numberOfFirstColumns;k<vt.colCount;k++){
					String phen = vt.fieldNames[k];
					Vector<String> phenotypes = new Vector<String>();
					if(phenotypeMap.containsKey(phen))
						phenotypes = phenotypeMap.get(phen);
					else
						phenotypes.add(phen);
					//float f = Float.parseFloat(vt.stringTable[i][k]);
					float f = sumColumns(i,vt,phenotypes);
					
					float wt_probs_sum = 0f;
					for(int s=0;s<phenotypes.size();s++)if(vt.fieldNumByName(phenotypes.get(s))>=0)if((vt.fieldNumByName(phenotypes.get(s))-numberOfFirstColumns)<wt_probs.length)
						wt_probs_sum+=wt_probs[vt.fieldNumByName(phenotypes.get(s))-numberOfFirstColumns];
					prob_averages[k-numberOfFirstColumns]+=f;
					if(wt_probs_sum<0.01)
						f = f;
					else
						f/=wt_probs_sum;
					vt.stringTable[i][k] = ""+f;
				}
			}
			
		}	
		
		for(int k=numberOfFirstColumns;k<vt.colCount;k++)
			prob_averages[k-numberOfFirstColumns]/=vt.rowCount;
		VDatReadWrite.saveToSimpleDatFile(vt, fn1+".xls", true);
		for(int i=numberOfFirstColumns;i<vt.colCount;i++)
			if(prob_averages[i-numberOfFirstColumns]>thresh)
				vt.fieldTypes[i] = VDataTable.NUMERICAL;
		VDatReadWrite.saveToVDatFile(vt, fn1+".dat");
	}
	
	public static void normalizeProbabilityTable(String fn, float thresh, File f){
		Vector<String> ls = Utils.loadStringListFromFile(f.getAbsolutePath());
		String cond = "";
		for(String s: ls)
			cond+=s+";";
		if(cond.endsWith(";")) cond = cond.substring(0, cond.length()-1);
		normalizeProbabilityTable(fn,thresh,cond);
	}
	
	public static void compressGeneInteractionTable(VDataTable vt,String field, String outFileNamePrefix) throws Exception{
		HashMap<String,Vector<Float>> pairs = new HashMap<String,Vector<Float>>();
		
		for(int i=0;i<vt.rowCount;i++){
			String int1 = vt.stringTable[i][vt.fieldNumByName("INTERACTOR1")];
			String int2 = vt.stringTable[i][vt.fieldNumByName("INTERACTOR2")];
			
			if(int1.endsWith("_ko")) int1 = int1.substring(0, int1.length()-3);
			if(int1.endsWith("_oe")) int1 = int1.substring(0, int1.length()-3);
			if(int2.endsWith("_ko")) int2 = int2.substring(0, int2.length()-3);
			if(int2.endsWith("_oe")) int2 = int2.substring(0, int2.length()-3);
			
			String tp1 = vt.stringTable[i][vt.fieldNumByName("INTERACTOR_TYPE1")];
			String tp2 = vt.stringTable[i][vt.fieldNumByName("INTERACTOR_TYPE2")];
			float f = Float.parseFloat(vt.stringTable[i][vt.fieldNumByName(field)]);
			String id = int1+"%"+int2;
			if(int1.compareTo(int2)<0){
				id = int2+"%"+int1;
				String temp = tp1; tp1=tp2; tp2=temp;
			}
			Vector<Float> vals = pairs.get(id);
			if(vals==null) { vals = new Vector<Float>(); vals.add(0f); vals.add(0f); vals.add(0f); vals.add(0f); }
			if(tp1.equals("KO")&&tp2.equals("KO")) vals.set(0, f);
			if(tp1.equals("KO")&&tp2.equals("OE")) vals.set(1, f);
			if(tp1.equals("OE")&&tp2.equals("KO")) vals.set(2, f);
			if(tp1.equals("OE")&&tp2.equals("OE")) vals.set(3, f);
			pairs.put(id, vals);
		}
		
		FileWriter fw = new FileWriter(outFileNamePrefix+".txt");
		fw.write("ID\tGENE1\tGENE2\tKO_KO\tKO_OE\tOE_KO\tOE_OE\tKO_OE_sym_add\tKO_OE_sym_diff\tMIN\tMAX\tABS_MIN\tAMPLITUDE\n");
		Set<String> ids = pairs.keySet();
		for(String id: ids){
			StringTokenizer st = new StringTokenizer(id,"%");
			String gene1 = st.nextToken();
			String gene2 = st.nextToken();
			Vector<Float> vals = pairs.get(id);
			float minv = Float.MAX_VALUE;
			float maxv = -Float.MAX_VALUE;
			for(Float f: vals){
				if(f<minv) minv = f;
				if(f>maxv) maxv = f;
			}
			fw.write(gene1+"__"+gene2+"\t"+gene1+"\t"+gene2+"\t"+vals.get(0)+"\t"+vals.get(1)+"\t"+vals.get(2)+"\t"+vals.get(3)+"\t"+(0.5*vals.get(2)+0.5*vals.get(1))+"\t"+Math.abs(vals.get(2)-vals.get(1))+"\t"+minv+"\t"+maxv+"\t"+Math.abs(minv)+"\t"+Math.abs(maxv-minv)+"\n");
		}
		fw.close();
		VDataTable vt1 = VDatReadWrite.LoadFromSimpleDatFile(outFileNamePrefix+".txt", true, "\t");
		for(int i=3;i<vt1.colCount;i++)
			vt1.fieldTypes[i] = VDataTable.NUMERICAL;
		VDatReadWrite.saveToVDatFile(vt1, outFileNamePrefix+".dat");
	}
	

}
