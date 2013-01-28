package fr.curie.BiNoM.pathways.utils;

import java.io.*;
import java.util.*;

import vdaoengine.utils.Utils;

public class SetOverlapAnalysis {


	public Vector<Vector<String>> lists = new Vector<Vector<String>>();
	public Vector<HashSet<String>> sets = null;
	public Vector<String> setnames = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{

			SetOverlapAnalysis so = new SetOverlapAnalysis();
			
			//String prefix = "C:/Datas/Kairov/DifferentialExpression4BC/lists/";
			//String prefix = "C:/Datas/KEGG/Human/dnarepair_KEGG";
			//String prefix = "C:/Datas/KEGG/Human/dnarepair_map_PARP12BRCA12";
			String prefix = "C:/Datas/KEGG/Test/test";
			so.LoadSetsFromGMT(prefix+".gmt");
			so.printSetSizes();
			so.printSetIntersections();
			
			System.exit(-1);
			
			so.LoadNewOrderedList(prefix+"1ic1");
			so.LoadNewOrderedList(prefix+"2ic1");
			so.LoadNewOrderedList(prefix+"3ic1");
			so.LoadNewOrderedList(prefix+"4ic2");
			
			so.generateSetsFromRankedLists(100);
			Vector<String> frequent = so.getNamesOfGivenOccurence(3);
			for(int i=0;i<frequent.size();i++){
				System.out.println(frequent.get(i));
			}
			System.exit(-1);
			
		
			System.out.print("N\t");
			for(int i=0;i<so.lists.size();i++) System.out.print("P"+(i+1)+"\t");
			System.out.println();
			
			for(int size=1;size<1000;size++){
				so.generateSetsFromRankedLists(size);
				Vector<Float> perc = new Vector<Float>();
				so.countOccurenciesInSets(so.calcUnionOfSets(), perc);
				System.out.print(size+"\t");
				for(int i=0;i<so.lists.size();i++) System.out.print(perc.get(i)+"\t");
				System.out.println();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void LoadNewOrderedList(String fn){
		Vector<String> rankedList = loadRandkedGeneList(fn);
		lists.add(rankedList);
	}
	
	public void LoadSetsFromGMT(String fn){
		try{
		sets = new Vector<HashSet<String>>();
		setnames = new Vector<String>();
		LineNumberReader lr = new LineNumberReader(new FileReader(fn));
		String s = null;
		Vector<String> allproteins = new Vector<String>();
		while((s=lr.readLine())!=null){
			StringTokenizer st = new StringTokenizer(s,"\t");
			String groupName = st.nextToken();
			setnames.add(groupName);
			String description = st.nextToken();
			HashSet<String> proteins = new HashSet<String>();
			while((st.hasMoreTokens())){
				String protein = st.nextToken();
				proteins.add(protein);
				if(!protein.contains(":")) // this is for DNA repair map!!!
				if(!allproteins.contains(protein))
					allproteins.add(protein);	
			}
			sets.add(proteins);
		}
		System.out.println("Totally "+allproteins.size()+" genes are found");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Vector<String> loadRandkedGeneList(String fileName){
		Vector<String> rankedGeneList = new Vector<String>();
		Vector<String> list = Utils.loadStringListFromFile(fileName);
		for(int i=0;i<list.size();i++){
			String geneName = list.get(i);
			StringTokenizer st = new StringTokenizer(geneName,"\t /");
			while(st.hasMoreTokens()){
				String s = st.nextToken();
				if(!rankedGeneList.contains(s))
					rankedGeneList.add(s);
			}
		}
		return rankedGeneList;
	}	
	
	public void generateSetsFromRankedLists(int numberOfTopRanked){
		sets = new Vector<HashSet<String>>();
		for(int j=0;j<lists.size();j++){
			HashSet<String> set = new HashSet<String>();
			for(int i=0;i<numberOfTopRanked;i++)if(i<lists.get(j).size()){
				set.add(lists.get(j).get(i));
			}
			sets.add(set);
		}
	}
	
	public Vector<String> calcUnionOfSets(){
		Vector<String> union = new Vector<String>();
		for(int i=0;i<sets.size();i++){
			HashSet<String> set = sets.get(i);
			Iterator<String> entries = set.iterator();
			while(entries.hasNext()){
				String entry = entries.next();
				if(!union.contains(entry))
					union.add(entry);
			}
		}
		Collections.sort(union);
		return union;
	}
	
	public Vector<String> calcIntersectionOfSets(HashSet<String> set1, HashSet<String> set2){
		Vector<String> inters = new Vector<String>();
		Iterator<String> it = set1.iterator();
		while(it.hasNext()){
			String s = it.next();
			if(set2.contains(s))
				inters.add(s);
		}
		return inters;
	}
	
	public Vector<Integer> countOccurenciesInSets(Vector<String> list, Vector<Float> percentages){
		Vector<Integer> occs = new Vector<Integer>();
		for(int i=0;i<list.size();i++){
			String entry = list.get(i);
			int count = 0;
			for(int j=0;j<sets.size();j++){
				if(sets.get(j).contains(entry))
					count++;
			}
			occs.add(count);
		}
		percentages.clear();
		for(int i=0;i<sets.size();i++)
			percentages.add(0f);
		for(int i=0;i<occs.size();i++){
			percentages.set(occs.get(i)-1,(percentages.get(occs.get(i)-1)+1f));
		}
		for(int i=0;i<sets.size();i++)
			percentages.set(i,percentages.get(i)/sets.get(0).size()/sets.size());
		return occs;
	}
	
	public Vector<String> getNamesOfGivenOccurence(int occ){
		Vector<String> names = new Vector<String>();
		Vector<Float> perc = new Vector<Float>();
		Vector<String> union = this.calcUnionOfSets();
		Vector<Integer> occs = this.countOccurenciesInSets(union, perc);
		for(int i=0;i<occs.size();i++){
			if(occs.get(i)==occ)
				names.add(union.get(i));
		}
		return names;
	}
	
	public void printSetSizes(){
		System.out.println("NAME\tSIZE\tSET");
		for(int i=0;i<sets.size();i++){
			System.out.print(setnames.get(i)+"\t"+sets.get(i).size()+"\t");
			Vector<String> names = new Vector<String>();
			Iterator<String> it = sets.get(i).iterator();
			while(it.hasNext()) names.add(it.next());
			Collections.sort(names);
			for(int j=0;j<names.size();j++)
				if(j==names.size()-1)
					System.out.print(names.get(j)+"\n");
				else
					System.out.print(names.get(j)+",");					
		}
	}
	
	public void printSetIntersections(){
		System.out.println("SET1\tSET2\tINTER\tINTERSECTION_SIZE\tINTERSECTION");		
		for(int i=0;i<sets.size();i++){
			for(int j=i+1;j<sets.size();j++){
				HashSet<String> seti = sets.get(i);
				HashSet<String> setj = sets.get(j);		
				Vector<String> names = calcIntersectionOfSets(seti, setj);
				Collections.sort(names);
				if(names.size()>0)
					System.out.print(setnames.get(i)+"\t"+setnames.get(j)+"\tintersects\t"+names.size()+"\t");
				for(int k=0;k<names.size();k++)
					if(k==names.size()-1)
						System.out.print(names.get(k)+"\n");
					else
						System.out.print(names.get(k)+",");					
				
			}
		}
	}
	
	public void findMinimalHittingSet(){
		
	}

}
