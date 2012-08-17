package fr.curie.BiNoM.pathways.utils;

import java.io.*;
import java.util.*;

import vdaoengine.utils.Utils;

public class SetOverlapAnalysis {


	public Vector<Vector<String>> lists = new Vector<Vector<String>>();
	public Vector<HashSet<String>> sets = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			
			String prefix = "C:/Datas/Kairov/DifferentialExpression4BC/lists/";
			
			SetOverlapAnalysis so = new SetOverlapAnalysis();
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

}
