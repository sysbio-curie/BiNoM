package fr.curie.BiNoM.pathways.utils;

import java.io.*;
import java.util.*;

public class GMTFile{
	
	public Vector<HashSet<String>> sets = null;
	public Vector<String> setnames = null; 
	public Vector<String> allnames = null; 
	
	public void load(String fn){
		try{
		sets = new Vector<HashSet<String>>();
		setnames = new Vector<String>();
		LineNumberReader lr = new LineNumberReader(new FileReader(fn));
		String s = null;
		allnames = new Vector<String>();
		while((s=lr.readLine())!=null){
			StringTokenizer st = new StringTokenizer(s,"\t");
			String groupName = st.nextToken().trim();
			setnames.add(groupName);
			String description = st.nextToken();
			HashSet<String> proteins = new HashSet<String>();
			while((st.hasMoreTokens())){
				String protein = st.nextToken();
				if(!protein.trim().equals(""))
					proteins.add(protein);
				if(!protein.contains(":")) // this is for DNA repair map!!!
				if(!allnames.contains(protein))
					allnames.add(protein);	
			}
			sets.add(proteins); //System.out.print(groupName+":"+proteins.size()+"\t");
		}
		System.out.println(fn+": total "+allnames.size()+" names.");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Vector<String> getListOfSets(String node){
		Vector<String> res = new Vector<String>();
		for(int i=0;i<sets.size();i++){
			HashSet set = sets.get(i);
			if(set.contains(node))
				res.add(setnames.get(i));
		}
		return res;
	}

}
