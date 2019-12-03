package fr.curie.BiNoM.pathways.scripts;

import java.io.FileWriter;
import java.util.HashSet;
import java.util.Vector;

import fr.curie.BiNoM.pathways.utils.SetOverlapAnalysis;

public class CalcSetCentrality {

	public static String gmtSetFile = null;
	public static String gmtQueryFile = null;
	public static String gmtExpansionFile = null;
	public static String outFile = null;	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			for(int i=0;i<args.length;i++){
				if(args[i].equals("-gmtbase"))
					gmtSetFile = args[i+1];
				if(args[i].equals("-gmtquery"))
					gmtQueryFile = args[i+1];
				if(args[i].equals("-gmtexpand"))
					gmtExpansionFile = args[i+1];
				if(args[i].equals("-out"))
					outFile = args[i+1]; 
				
			}
			
			calcSetCentrality();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void calcSetCentrality() throws Exception{
		SetOverlapAnalysis query = new SetOverlapAnalysis();
		query.LoadSetsFromGMT(gmtQueryFile);

		SetOverlapAnalysis so = new SetOverlapAnalysis();
		
		if(gmtExpansionFile!=null){
			String fn = gmtSetFile.substring(0, gmtSetFile.length()-4)+"_expanded.gmt";
			so.expandSetsOfLists_ExpandSets(gmtSetFile, gmtExpansionFile, fn);
			//so.expandSetsOfLists_SplitSets(gmtSetFile, gmtExpansionFile, fn);
			gmtSetFile = fn;
		}

		so.LoadSetsFromGMT(gmtSetFile);
		
		
		FileWriter fw = new FileWriter(outFile);
		for(int i=0;i<query.setnames.size();i++){
			fw.write(query.setnames.get(i)+"\t");
			HashSet<String> setq = query.sets.get(i);
			Vector<String> hitSets = new Vector<String>();
			Vector<String> genesUsed = new Vector<String>();
			for(int j=0;j<so.setnames.size();j++){
				HashSet<String> set = so.sets.get(j);
				Vector<String> inters = so.calcIntersectionOfSets(setq, set);
				if(inters.size()>0){
					hitSets.add(so.setnames.get(j));
					for(String s: inters)
						if(!genesUsed.contains(s))
							genesUsed.add(s);
				}
			}
			String s = ""; for(int k=0;k<hitSets.size();k++) s+=hitSets.get(k)+";"; if(s.endsWith(";")) s = s.substring(0,s.length()-1);
			String genes = ""; for(int k=0;k<genesUsed.size();k++) genes+=genesUsed.get(k)+";"; if(genes.endsWith(";")) genes = genes.substring(0,genes.length()-1);
			fw.write(hitSets.size()+"\t"+s+"\t"+genesUsed.size()+"\t"+genes+"\t");
			fw.write("\n");
		}
		fw.close();
	}
	
	

}
