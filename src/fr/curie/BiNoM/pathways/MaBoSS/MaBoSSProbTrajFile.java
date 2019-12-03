package fr.curie.BiNoM.pathways.MaBoSS;

import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import vdaoengine.data.VDataTable;
import vdaoengine.data.io.VDatReadWrite;
import fr.curie.BiNoM.pathways.utils.SimpleTable;

public class MaBoSSProbTrajFile {

	int type = 0; // 0 - wild type, 1 - single mutant, 2 - double mutant
	String interactor1 = "_";
	String interactor2 = "_";
	int interactorType1 = 0; // 0 - undefined, -1 - knockout, +1 - overexpression
	int interactorType2 = 0; 	
	SimpleTable table = new SimpleTable();
	Vector<String> phenotypes = new Vector<String>();
	Vector<Float> probabilities = new Vector<Float>();
	
	public static void main(String[] args) {
		try{
		MaBoSSProbTrajFile prob = new MaBoSSProbTrajFile();
		/*prob.load("C:/Datas/Calzone/EMT/ModNet_mutants/ModNet_probtraj.csv", "ModNet_");
		for(int i=0;i<prob.phenotypes.size();i++){
			System.out.println(prob.phenotypes.get(i)+"\t"+prob.probabilities.get(i));
		}
		System.out.println(prob.interactor1+"\t"+prob.interactorType1);
		System.out.println(prob.interactor2+"\t"+prob.interactorType2);
		System.out.println(prob.type);*/
		//prob.makeProbabilityTableFromFolder("C:/Datas/Calzone/EMT/ModNet_mutants/","ModNet_","C:/Datas/Calzone/EMT/ModNet_mutants.xls");
		//prob.makeProbabilityTableFromFolder("C:/Datas/Calzone/EMT/metastasis_mutants/","metastasis_","C:/Datas/Calzone/EMT/metastasis_mutants.xls");
		//VDataTable vt = VDatReadWrite.LoadFromVDatFile("C:/Datas/Calzone/EMT/ModNet_mutants.xls.dat");
		//makeGeneticInteractionTable(vt,"Invasion/Migration/Metastasis/CellCycleArrest","metastasis","C:/Datas/Calzone/EMT/ModNet_mutants");
		VDataTable vt1 = VDatReadWrite.LoadFromVDatFile("C:/Datas/Calzone/EMT/metastasis_mutants.xls.dat");
		makeGeneticInteractionTable(vt1,"Invasion/EMT/Migration/Metastasis/CellCycleArrest","metastasis","C:/Datas/Calzone/EMT/metastasis_mutants");
		
		
		String folder = "";
		String prefix = "";
		String out = "";
		String phenotype = "";
		String table = "";
		String short_name = "";
		boolean makeTable = false;
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
			if(args[i].equals("-maketable"))
				makeTable = true;
			if(args[i].equals("-makeinter"))
				computeGeneticInteractions = true;
		}
		
		if(makeTable){
			prob.makeProbabilityTableFromFolder(folder,prefix,out);
		}
		if(computeGeneticInteractions){
			VDataTable vt = VDatReadWrite.LoadFromVDatFile(table);
			makeGeneticInteractionTable(vt,phenotype,short_name,out);
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void load(String fn, String prefix){
		String fileName = (new File(fn)).getName();
		String pair = "";
		if(fileName.equals("ModNet_probtraj.csv"))
			System.out.println();
		if(fileName.endsWith("_probtraj.csv"))
			pair = fileName.substring(0, fileName.length()-13);
		if(pair.startsWith(prefix))
			pair = pair.substring(prefix.length(), pair.length());
		if(pair.equals(prefix.substring(0,prefix.length()-1)))
			pair = "";
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
		}else{
			type = 0;
		}
		table.LoadFromSimpleDatFile(fn, true, "\t");
		int last = table.rowCount-1;
		int k=5;
		while(k<table.colCount-2){
			String phenotype = table.stringTable[last][k];
			if(phenotype!=null)if(!phenotype.trim().equals("")){
				float prob = Float.parseFloat(table.stringTable[last][k+1]);
				float errprob = Float.parseFloat(table.stringTable[last][k+2]);
				phenotypes.add(phenotype);
				probabilities.add(prob);
			}
			k+=3;
		}
	}
	
	public void makeProbabilityTableFromFolder(String folder, String prefix, String outfile) throws Exception{
		File dir = new File(folder);
		Vector<MaBoSSProbTrajFile> trajs = new Vector<MaBoSSProbTrajFile>();
		Vector<String> phenotypes = new Vector<String>();
		for(File f: dir.listFiles())if(f.getName().endsWith("probtraj.csv")){
			System.out.println("Loading "+f.getName());
			MaBoSSProbTrajFile traj = new MaBoSSProbTrajFile();
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
			MaBoSSProbTrajFile traj = trajs.get(i);
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
			vt.fieldTypes[vt.fieldNumByName(s.replace(" -- ", "/"))] = vt.NUMERICAL;
		VDatReadWrite.saveToVDatFile(vt, outfile+".dat");
	}
	
	public static void makeGeneticInteractionTable(VDataTable vt, String phenotype, String phenotype_short, String outfile_prefix) throws Exception{
		FileWriter fwn = new FileWriter(outfile_prefix+"_"+phenotype_short+"_nodes.txt");
		fwn.write("INTERACTOR\tINTERACTOR_TYPE1\tP\n");
		FileWriter fwe = new FileWriter(outfile_prefix+"_"+phenotype_short+"_edges.txt");
		fwe.write("INTERACTOR1\tINTERACTOR_TYPE1\tINTERACTOR2\tINTERACTOR_TYPE2\tP1\tP2\tP12\tEPS\n");
		HashMap<String, Integer> singles = new HashMap<String, Integer>();
		int wtline = -1;
		Vector<String> singleko = new Vector<String>();
		Vector<String> singleoe = new Vector<String>();
		Vector<String> singles_v = new Vector<String>();
		for(int i=0;i<vt.rowCount;i++){
			String type = vt.stringTable[i][vt.fieldNumByName("TYPE")];
			if(type.equals("SINGLE")){
				singles.put(vt.stringTable[i][vt.fieldNumByName("ID")], i);
				fwn.write(vt.stringTable[i][vt.fieldNumByName("ID")]+"\t"+vt.stringTable[i][vt.fieldNumByName("INTERACTOR_TYPE1")]+"\t"+vt.stringTable[i][vt.fieldNumByName(phenotype)]+"\n");
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
			if(type.equals("WT"))
				wtline = i;
		}
		}
		
		Collections.sort(singleko);
		Collections.sort(singleoe);
		Collections.sort(singles_v);
		float epimatrix[][] = new float[singles_v.size()][singles_v.size()];
		float epimatrix_ko[][] = new float[singleko.size()][singleko.size()];
		float epimatrix_oe[][] = new float[singleoe.size()][singleoe.size()];
		
		for(int i=0;i<vt.rowCount;i++){
			String type = vt.stringTable[i][vt.fieldNumByName("TYPE")];
			if(type.equals("DOUBLE")){
				String inter1 = vt.stringTable[i][vt.fieldNumByName("INTERACTOR1")];
				String inter2 = vt.stringTable[i][vt.fieldNumByName("INTERACTOR2")];
				String inter1_type = vt.stringTable[i][vt.fieldNumByName("INTERACTOR_TYPE1")];
				String inter2_type = vt.stringTable[i][vt.fieldNumByName("INTERACTOR_TYPE2")];
				float P12 = Float.parseFloat(vt.stringTable[i][vt.fieldNumByName(phenotype)]);
				int i1 = singles.get(inter1);
				int i2 = singles.get(inter2);
				float P1 = Float.parseFloat(vt.stringTable[i1][vt.fieldNumByName(phenotype)]);
				float P2 = Float.parseFloat(vt.stringTable[i2][vt.fieldNumByName(phenotype)]);
				float EPS = (1-P12)-(1-P1)*(1-P2);
				if(EPS!=0)
					fwe.write(inter1+"\t"+inter1_type+"\t"+inter2+"\t"+inter2_type+"\t"+P1+"\t"+P2+"\t"+P12+"\t"+EPS+"\n");
				int is1 = singles_v.indexOf(inter1);
				int is2 = singles_v.indexOf(inter2);
				int iko1 = singleko.indexOf(inter1);
				int iko2 = singleko.indexOf(inter2);
				int ioe1 = singleoe.indexOf(inter1);
				int ioe2 = singleoe.indexOf(inter2);
				epimatrix[is1][is2] = EPS;
				epimatrix[is2][is1] = EPS;
				if((iko1>=0)&&(iko2>=0)) {epimatrix_ko[iko1][iko2] = EPS; epimatrix_ko[iko1][iko2] = EPS;}
				if((ioe1>=0)&&(ioe2>=0)) {epimatrix_oe[ioe2][ioe1] = EPS; epimatrix_oe[ioe2][ioe1] = EPS;}
			}
			if(type.equals("SINGLE")){
				String inter1 = vt.stringTable[i][vt.fieldNumByName("INTERACTOR1")];
				float P1 = Float.parseFloat(vt.stringTable[i][vt.fieldNumByName(phenotype)]);
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
		
		VDataTable vte = VDatReadWrite.LoadFromSimpleDatFile(outfile_prefix+"_"+phenotype_short+"_edges.txt", true, "\t");
		vte.fieldTypes[vte.fieldNumByName("P1")] = vt.NUMERICAL;
		vte.fieldTypes[vte.fieldNumByName("P2")] = vt.NUMERICAL;
		vte.fieldTypes[vte.fieldNumByName("P12")] = vt.NUMERICAL;
		vte.fieldTypes[vte.fieldNumByName("EPS")] = vt.NUMERICAL;
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
			vt1.fieldTypes[i] = vt1.NUMERICAL;
		VDatReadWrite.saveToVDatFile(vt1, outfile_prefix+"_"+phenotype_short+"_epi.dat");
		vt1 = VDatReadWrite.LoadFromSimpleDatFile(outfile_prefix+"_"+phenotype_short+"_epiko.txt", true, "\t");
		for(int i=1;i<vt1.colCount;i++)
			vt1.fieldTypes[i] = vt1.NUMERICAL;
		VDatReadWrite.saveToVDatFile(vt1, outfile_prefix+"_"+phenotype_short+"_epiko.dat");
		vt1 = VDatReadWrite.LoadFromSimpleDatFile(outfile_prefix+"_"+phenotype_short+"_epioe.txt", true, "\t");
		for(int i=1;i<vt1.colCount;i++)
			vt1.fieldTypes[i] = vt1.NUMERICAL;
		VDatReadWrite.saveToVDatFile(vt1, outfile_prefix+"_"+phenotype_short+"_epioe.dat");
		
	}
	
	

}
