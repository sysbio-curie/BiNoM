package fr.curie.BiNoM.pathways.utils;

import java.io.*;
import java.util.*;

public class GMTFile{
	
	public Vector<HashSet<String>> sets = null;
	public Vector<String> setnames = null; 
	public Vector<String> allnames = null;
	
	public static void main(String args[]){
		try{
			GMTFile gmt = new GMTFile();
			//gmt.load("c:/datas/acsn/acsn_only/acsn_src/temp.gmt");
			gmt.load("c:/datas/acsn/acsn_master.xml.gmt");
			gmt.saveAllNamesToFile("c:/datas/acsn/allnames.txt",true);
			//gmt.load("c:/datas/navicell/test/merged/temp.gmt");
			//gmt.saveAllNamesToFile("c:/datas/navicell/test/merged/allnames.txt");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void load(String fn){
		try{
		sets = new Vector<HashSet<String>>();
		setnames = new Vector<String>();
		HashSet<String> setnamesSet = new HashSet<String>();
		LineNumberReader lr = new LineNumberReader(new FileReader(fn));
		String s = null;
		allnames = new Vector<String>();
		HashSet<String> allnamesSet = new HashSet<String>();
		while((s=lr.readLine())!=null){
			StringTokenizer st = new StringTokenizer(s,"\t");
			if(st.hasMoreTokens()){ 
			String groupName = st.nextToken().trim();
			if(st.hasMoreTokens()){
			setnames.add(groupName);
			setnamesSet.add(groupName);
			String description = st.nextToken();
			HashSet<String> proteins = new HashSet<String>();
			while((st.hasMoreTokens())){
				String protein = st.nextToken();
				if(protein.contains("[")){
					//System.out.println("before:"+protein);
					protein = protein.substring(0, protein.indexOf("["));
					//System.out.println("after:"+protein);
				}
				if(!protein.trim().equals(""))
					proteins.add(protein);
				if(!protein.contains(":")) // this is for DNA repair map!!!
				if(!allnamesSet.contains(protein)){
					allnames.add(protein);
					allnamesSet.add(protein);
				}
			}
			sets.add(proteins); //System.out.print(groupName+":"+proteins.size()+"\t");
			}}
		}
		System.out.println(fn+": total "+allnames.size()+" names.");
		//Collections.sort(allnames);
		//for(String ss: allnames)
		//	System.out.println(ss);
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
	
	public void saveAllNamesToFile(String fn, boolean onlyThoseInCapitals){
		try{
			FileWriter fw = new FileWriter(fn);
			Collections.sort(allnames);
			for(int i=0;i<allnames.size();i++)
				if((!onlyThoseInCapitals)||(allnames.get(i).equals(allnames.get(i).toUpperCase())))
					fw.write(allnames.get(i)+"\n");
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Vector<String> findSet(String name){
		Vector<String> res = new Vector<String>();
		for(int i=0;i<sets.size();i++){
			if(sets.get(i).contains(name))
				res.add(setnames.get(i));
		}
		return res;
	}

}
