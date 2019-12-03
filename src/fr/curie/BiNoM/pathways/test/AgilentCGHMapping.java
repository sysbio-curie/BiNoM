package fr.curie.BiNoM.pathways.test;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

import fr.curie.BiNoM.pathways.utils.SimpleTable;

public class AgilentCGHMapping {

	/**
	 * @param args
	 */
	HashMap<String, genomePosition> hugoGenomePosition = new HashMap<String, genomePosition>();
	SimpleTable table = new SimpleTable();
	
	private class genomePosition{
		String chr = null;
		int start = 0;
		int end = 0;
		int gnl = Integer.MAX_VALUE;
		int out = 0;
	}
	
	private class cghProfile{
		HashMap<String, Vector<genomePosition>> chr2GNL= new HashMap<String, Vector<genomePosition>>();
		public void loadProfile(String fn){
			SimpleTable table = new SimpleTable();
			table.LoadFromSimpleDatFile(fn, true, ",");
			for(int i=0;i<table.rowCount;i++){
				String chr = table.stringTable[i][table.fieldNumByName("Chr")];
				int pos = Integer.parseInt(table.stringTable[i][table.fieldNumByName("X")]);
				int gnl = Integer.parseInt(table.stringTable[i][table.fieldNumByName("Gnl")]);				
				int size = Integer.parseInt(table.stringTable[i][table.fieldNumByName("Size")]);			
				int out = Integer.parseInt(table.stringTable[i][table.fieldNumByName("Out")]);							
				genomePosition gp = new genomePosition();
				gp.chr = "chr"+chr;
				gp.start = pos;
				gp.end = pos+size;
				gp.gnl = gnl;
				gp.out = out;
				Vector<genomePosition> v = chr2GNL.get(gp.chr);				
				if(v==null){ 
					v = new Vector<genomePosition>();
					chr2GNL.put(gp.chr, v);
				}
				v.add(gp);
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			String prefix = "c:/datas/breastcancer/IVOIRE/";
			//extractGenomePosition(prefix);
			
			AgilentCGHMapping am = new AgilentCGHMapping();
			am.loadGenePositions(prefix+"genePosition.txt");
			//am.mapProfile("C:/Datas/BreastCancer/IVOIRE/cgh/11155_10090_CGH_107_Sep09.csv");
			am.createCGHTableFromDir(prefix+"cgh");
			am.table.saveToSimpleTxtTabDelimited(prefix+"cghTable.txt");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void extractGenomePosition(String prefix){
		SimpleTable uH = new SimpleTable();
		uH.LoadFromSimpleDatFile(prefix+"hgFixed.txt", true, "\t");
		uH.createIndex("geneName");
		SimpleTable kg = new SimpleTable();
		kg.LoadFromSimpleDatFile(prefix+"knownGenes.txt", true, "\t");
		kg.createIndex("name");
		Iterator<String> it = uH.index.keySet().iterator();
		
		try{
		FileWriter fw = new FileWriter(prefix+"genePosition.txt");
		fw.write("HUGO\tCHR\ttxStart\ttxEnd\n");
		while(it.hasNext()){
			String key = it.next();
			if(uH.index.get(key)!=null){
				Vector<Integer> rows = uH.index.get(key);
				String id = uH.stringTable[rows.get(0)][uH.fieldNumByName("id")];
				id = id.substring(0,id.length()-2);
				if(kg.index.get(id)!=null){
					rows = kg.index.get(id);
					fw.write(key+"\t"+kg.stringTable[rows.get(0)][kg.fieldNumByName("chrom")]+"\t"+kg.stringTable[rows.get(0)][kg.fieldNumByName("txStart")]+"\t"+kg.stringTable[rows.get(0)][kg.fieldNumByName("txEnd")]+"\n");
				}
			}
		}
		fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadGenePositions(String fn){
		SimpleTable tab = new SimpleTable();
		tab.LoadFromSimpleDatFile(fn, true, "\t");
		for(int i=0;i<tab.stringTable.length;i++){
			String gene = tab.stringTable[i][tab.fieldNumByName("HUGO")];
			genomePosition gp = new genomePosition();
			gp.chr = tab.stringTable[i][tab.fieldNumByName("CHR")];
			gp.start = Integer.parseInt(tab.stringTable[i][tab.fieldNumByName("txStart")]);
			gp.end = Integer.parseInt(tab.stringTable[i][tab.fieldNumByName("txEnd")]);
			hugoGenomePosition.put(gene, gp);
		}
	}
	
	public HashMap<String,Float> mapProfile(String fn){
		HashMap<String,Float> gnls = new HashMap<String, Float>();
		cghProfile p = new cghProfile();
		p.loadProfile(fn);
		Iterator<String> it = hugoGenomePosition.keySet().iterator();
		try{
		FileWriter fw = new FileWriter(fn+".txt");
		while(it.hasNext()){
			String gene = it.next();
			genomePosition gp = hugoGenomePosition.get(gene);
			float gnl = 0; int nprobes = 0;
			Vector<genomePosition> gpv = p.chr2GNL.get(gp.chr);
			//if(gpv==null) System.out.println(gp.chr+" NOT FOUND");
			if(gpv!=null)
			for(int i=0;i<gpv.size();i++){
				genomePosition g = gpv.get(i);
				if((g.start>=gp.start)&&(g.start<gp.end))if(g.out==0){
					//gnl+=g.gnl;
					//nprobes++;
					if(Math.abs(g.gnl)>Math.abs(gnl)) gnl = g.gnl;
					nprobes = 1;
				}
			}
			if(nprobes>0) gnl/=nprobes;
			fw.write(gene+"\t"+gnl+"\n");
			gnls.put(gene, gnl);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return gnls;
	}
	
	public void createCGHTableFromDir(String dir){
		try{
			File fdir[] = (new File(dir)).listFiles();
			
			Vector<String> geneNames = new Vector<String>();
			int nprofiles = 0;
			for(int i=0;i<fdir.length;i++)if(fdir[i].getName().endsWith(".csv")) nprofiles++;
			
			table.colCount = nprofiles+4;
			table.rowCount = hugoGenomePosition.size();
			table.fieldNames = new String[table.colCount];
			table.fieldNames[0] = "HUGO";
			table.fieldNames[1] = "CHR";
			table.fieldNames[2] = "START";
			table.fieldNames[3] = "END";
			table.stringTable = new String[table.rowCount][table.colCount];
			for(int i=4;i<table.colCount;i++) table.fieldNames[i] = "";

			Iterator<String> it = hugoGenomePosition.keySet().iterator();			
			while(it.hasNext()) geneNames.add(it.next());
			Collections.sort(geneNames);
			for(int i=0;i<geneNames.size();i++){
				genomePosition gp = hugoGenomePosition.get(geneNames.get(i));
				table.fieldNumByName("HUGO");
				table.stringTable[i][table.fieldNumByName("HUGO")] = geneNames.get(i);
				table.stringTable[i][table.fieldNumByName("CHR")] = gp.chr;
				table.stringTable[i][table.fieldNumByName("START")] = ""+gp.start;
				table.stringTable[i][table.fieldNumByName("END")] = ""+gp.end;				
			}
			
			int k=0;
			for(int i=0;i<fdir.length;i++)if(fdir[i].getName().endsWith(".csv")){
				table.fieldNames[k+4] = fdir[i].getName().substring(0,fdir[i].getName().length()-4);
				System.out.println((k+1)+") Loading "+table.fieldNames[k+4]+"...");
				cghProfile p = new cghProfile();
				HashMap<String,Float> gnls = mapProfile(fdir[i].getAbsolutePath());
				Iterator<String> itg = gnls.keySet().iterator();
				while(itg.hasNext()){
					String gene = itg.next();
					table.stringTable[geneNames.indexOf(gene)][k+4] = ""+gnls.get(gene);
				}
				k++;
			}

			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
