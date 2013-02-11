package fr.curie.BiNoM.pathways.utils;

import java.io.*;
import java.util.*;

import fr.curie.BiNoM.pathways.analysis.structure.BiographUtils;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.analysis.structure.OmegaScoreData;
import fr.curie.BiNoM.pathways.analysis.structure.OptimalCombinationAnalyzer;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

import vdaoengine.utils.Utils;

public class SetOverlapAnalysis {


	public Vector<Vector<String>> lists = new Vector<Vector<String>>();
	public Vector<HashSet<String>> sets = null;
	public Vector<String> setnames = null;
	public Vector<String> allproteins = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{

			SetOverlapAnalysis so = new SetOverlapAnalysis();
			
			//String prefix = "C:/Datas/Kairov/DifferentialExpression4BC/lists/";
			//String prefix = "C:/Datas/KEGG/Human/dnarepair_KEGG";
			//String prefix = "C:/Datas/KEGG/Human/dnarepair_map_PARP12BRCA12";
			//String prefix = "C:/Datas/KEGG/Test/test";
			//String prefix = "C:/Datas/KEGG/Test/ocsana_report_cellfate";
			//String prefix = "C:/Datas/KEGG/Human/dnarepair_map_onlyRepair";
			//String prefix = "C:/Datas/DNARepairAnalysis/dnarepair_path";
			String prefix = "C:/Datas/DNARepairAnalysis/dnarepair_REPAIRtoire_onlyBER";
			
			so.LoadSetsFromGMT(prefix+".gmt");
			
			//String typesOfRegulations[] = new String[]{"CATALYSIS","TRIGGER","MODULATION","PHYSICAL_STIMULATION","UNKNOWN_CATALYSIS"};
			//Graph graph = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("C:/Datas/DNARepairAnalysis/dnarepair.xml.xgmml"));
			//so.makeGMTOfReactionRegulators(prefix+"_reg", graph, typesOfRegulations);
			
			//so.printSetSizes();
			//so.printSetIntersections();

			so.findMinimalHittingSet(4, prefix);
			//so.listSetsIncludingSet(prefix+".minhitsets",new String[]{"BRCA1"});
			
			//so.createGMTFromOCSANAOutput("C:/Datas/DNARepairAnalysis/dnarepair_OCSANA_report");
						
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
		allproteins = new Vector<String>();
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
	
	public void findMinimalHittingSet(int maxSetSize, String fileNamePreifix) throws Exception{
		OptimalCombinationAnalyzer oca = new OptimalCombinationAnalyzer();
		ArrayList<BitSet> pathMatrixRowBin = new ArrayList<BitSet>();
		oca.pathMatrixNbRow = sets.size();
		oca.pathMatrixNbCol = allproteins.size();
		int pathMatrix[][] = new int[oca.pathMatrixNbRow][oca.pathMatrixNbCol];
		Iterator<HashSet<String>> it = sets.iterator();
		int k=0;
		while(it.hasNext()){
			HashSet<String> set = it.next();
			BitSet b = new BitSet(allproteins.size());
			Iterator<String> proteins = set.iterator();
			while(proteins.hasNext()){
				String name = proteins.next();
				if(allproteins.indexOf(name)!=-1){
					b.set(allproteins.indexOf(name));
					pathMatrix[k][allproteins.indexOf(name)] = 1;
				}
				else
					System.out.println(name+" is not found");
			}
			k++;
			pathMatrixRowBin.add(b);
		}
		
		//oca.pathMatrixRowBin = pathMatrixRowBin;
		oca.pathMatrix = pathMatrix;
		oca.pathMatrixNodeList = new ArrayList<String>();
		oca.omegaScoreList = new ArrayList<OmegaScoreData>();
		oca.orderedNodesByScore = new ArrayList<String>();
		for(int i=0;i<allproteins.size();i++){
			oca.pathMatrixNodeList.add(allproteins.get(i));
			oca.omegaScoreList.add(new OmegaScoreData(allproteins.get(i),i));
			oca.orderedNodesByScore.add(allproteins.get(i));
		}
		oca.initOrderedNodesList();
		oca.searchHitSetSizeOne();
		oca.convertPathMatrixColToBinary();
		oca.convertPathMatrixRowToBinary();
		
		oca.report = new StringBuffer();
		
		//oca.initOrderedNodesList();
		
		//oca.checkRows();
		//oca.searchHitSetSizeOne();

		oca.maxNbHitSet = (long)50e6;
		oca.maxHitSetSize = maxSetSize;

		if(maxSetSize!=-1){
			//oca.searchHitSetFull(maxSetSize);
			oca.searchHitSetPartial();
		}
		else
			oca.mainBerge(true);
		
		
		// Printing the results

		Iterator<BitSet> itb = oca.hitSetSB.iterator();
		int maxFoundSize = 0;
		while(itb.hasNext()){
			int size = 0; BitSet bs = itb.next();
			for(int i=0;i<bs.size();i++) if(bs.get(i)) size++;
			if(size>maxFoundSize) maxFoundSize = size;
		}
		
		
		FileWriter fw = new FileWriter(fileNamePreifix+".minhitsets");

		System.out.print("SIZE\t"); fw.write("SIZE\t");
		for(int sz=1;sz<=maxFoundSize;sz++) { System.out.print("N"+sz+"\t"); fw.write("N"+sz+"\t"); } 
		for(int sz=1;sz<=maxFoundSize;sz++) { System.out.print("SETS"+sz+"\t"); fw.write("SETS"+sz+"\t"); } 		
		System.out.println(); fw.write("\n");
		
		
		Iterator<String> it1 = oca.hitSetSizeOne.iterator();
		while(it1.hasNext()){
			String node = it1.next();
			System.out.println("1\t"+node);
			fw.write("1\t"+node+"\n");
		}

		
		Vector<Vector<OmegaScoreData>> frequencies = new Vector<Vector<OmegaScoreData>>();
		
		for(int sz=2;sz<=maxFoundSize;sz++){
		
		Vector<Vector<String>> hitSetString = new Vector<Vector<String>>();
		itb = oca.hitSetSB.iterator();
		while(itb.hasNext()){
			BitSet bs = itb.next();
			int size = 0;
			Vector<String> set = new Vector<String>();
			for(int i=0;i<bs.size();i++)
				if(bs.get(i)){
					size++;
					set.add(oca.pathMatrixNodeList.get(i));
				}
			Collections.sort(set);
			if(sz==size){
				System.out.print(size+"\t");
				fw.write(size+"\t");
				for(int i=0;i<set.size();i++){
					System.out.print(set.get(i)+"\t");
					fw.write(set.get(i)+"\t");
				}
			for(int i=set.size();i<=maxFoundSize;i++){
				System.out.print("\t");
				fw.write("\t");
			}
			for(int i=0;i<set.size();i++){
				String node = set.get(i);
				Vector<String> names = getListOfSets(node);
				String s = "(";
				for(int j=0;j<names.size();j++) if(j==names.size()-1) s+=names.get(j); else s+=names.get(j)+";";
				s+=")";
				System.out.print(s+"\t");
				fw.write(s+"\t");
			}
			System.out.println();
			fw.write("\n");
			hitSetString.add(set);
			}
		}
		frequencies.add(analyzeHitFrequency(hitSetString));		
		}
		
		FileWriter fw1 = new FileWriter(fileNamePreifix+".freqmhs");		
		System.out.println();
		System.out.print("NODE\tSETS\t"); fw1.write("NODE\tSETS\t");
		for(int sz=2;sz<=maxFoundSize;sz++) { System.out.print("SZ"+sz+"\t"); fw1.write("SZ"+sz+"\t"); } System.out.println(); fw1.write("\n");
		for(int i=0;i<allproteins.size();i++){
			Vector<String> names = getListOfSets(allproteins.get(i));
			String s = "(";
			for(int j=0;j<names.size();j++) if(j==names.size()-1) s+=names.get(j); else s+=names.get(j)+";";
			s+=")";
			System.out.print(allproteins.get(i)+"\t"+s+"\t");
			fw1.write(allproteins.get(i)+"\t"+s+"\t");
			for(int j=2;j<=maxFoundSize;j++){
				System.out.print(frequencies.get(j-2).get(i).score+"\t");
				fw1.write(frequencies.get(j-2).get(i).score+"\t");
			}
			System.out.println(); fw1.write("\n");
		}
		fw1.close();
		
		
		fw.close();
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
	
	public Vector<OmegaScoreData> analyzeHitFrequency(Vector<Vector<String>> hitSet){
		Vector<OmegaScoreData> frequencies = new Vector<OmegaScoreData>();
		for(int i=0;i<allproteins.size();i++)
			frequencies.add(new OmegaScoreData(allproteins.get(i),0));
		for(int i=0;i<hitSet.size();i++){
			Vector<String> bs = hitSet.get(i);
			for(int j=0;j<bs.size();j++)
				frequencies.get(allproteins.indexOf(bs.get(j))).score+=1;
		}
		//Collections.sort(frequencies);
		return frequencies;
	}
	
	public void createGMTFromOCSANAOutput(String fn) throws Exception{
		LineNumberReader lr = new LineNumberReader(new FileReader(fn));
		FileWriter fw = new FileWriter(fn+".gmt");
		String s = null;
		while((s=lr.readLine())!=null){
			if(s.startsWith("Found ")){
				StringTokenizer st = new StringTokenizer(s," \t");
				st.nextToken();
				int npaths = Integer.parseInt(st.nextToken());
				lr.readLine();
				for(int i=0;i<npaths;i++){
					s = lr.readLine();
					st = new StringTokenizer(s,"->|");
					fw.write("path"+(i+1)+"\t"+s+"\t");
					while(st.hasMoreTokens()){
						String node = st.nextToken();
						if(!node.contains(":"))
							fw.write(node+"\t");
					}
					fw.write("\n");
				}
				break;
			}
		}
		fw.close();
	}
	
	public void makeGMTOfReactionRegulators(String prefix, Graph reactionGraph, String typesOfRegulations[]) throws Exception{
		FileWriter fw = new FileWriter(prefix+".gmt");
		for(int i=0;i<setnames.size();i++){
			fw.write(setnames.get(i)+"\tna\t");
			Vector<Node> regulators = BiographUtils.findReactionRegulators(reactionGraph, sets.get(i), typesOfRegulations);
			Vector<String> regnames = BiographUtils.extractProteinNamesFromNodeNames(regulators);
			for(int j=0;j<regnames.size();j++){
				fw.write(regnames.get(j)+"\t");
			}
			fw.write("\n");
		}
		fw.close();
	}
	
	public void listSetsIncludingSet(String fileName, String subset[]){
		HashSet<String> _subset = new HashSet<String>();
		for(int i=0;i<subset.length;i++)
			_subset.add(subset[i]);
		listSetsIncludingSet(fileName,_subset);
	}
	
	public void listSetsIncludingSet(String fileName, HashSet<String> subset){
		loadSetsFromTable(fileName);
		listSetsIncludingSet(subset);
	}
	
	public int listSetsIncludingSet(HashSet<String> subset){
		int numberOfResults = 0;
		int maxsetsize = getMaximumSetSize();
		
		Vector<String> vsubset = convertSetToVector(subset);
		
		System.out.print("NAME\t");
		for(int i=0;i<maxsetsize;i++) System.out.print("N"+(i+1)+"\t"); System.out.println();
		
		for(int i=0;i<sets.size();i++){
			HashSet<String> set = sets.get(i);
			Vector<String> vset = convertSetToVector(set);
			String name = setnames.get(i);
			Vector<String> inters = calcIntersectionOfSets(set, subset);
			if(inters.size()==subset.size()){
				numberOfResults++;
				System.out.print(name+"\t");
				for(int j=0;j<vsubset.size();j++) System.out.print(vsubset.get(j)+"\t"); 
				for(int j=0;j<vset.size();j++) if(!subset.contains(vset.get(j))) System.out.print(vset.get(j)+"\t"); 
				System.out.println();
			}
		}
		return numberOfResults;
	}
	
	public int getMaximumSetSize(){
		int maxsetsize = 0;
		for(int i=0;i<sets.size();i++){
			HashSet<String> set = sets.get(i);
			if(set.size()>maxsetsize) maxsetsize = set.size();
		}
		return maxsetsize;
	}
	
	public Vector<String> convertSetToVector(HashSet<String> set){
		Vector<String> res = new Vector<String>();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			res.add(it.next());
		}
		Collections.sort(res);
		return res;
	}
	
	
	public void loadSetsFromTable(String fileName){
		sets = new Vector<HashSet<String>>();
		setnames = new Vector<String>();
		SimpleTable tab = new SimpleTable();
		tab.LoadFromSimpleDatFile(fileName, true, "\t");
		for(int i=0;i<tab.rowCount;i++){
			HashSet<String> set = new HashSet<String>();
			int size = Integer.parseInt(tab.stringTable[i][tab.fieldNumByName("SIZE")]);
			for(int j=1;j<=size;j++){
				String fn = "N"+j;
				String s = tab.stringTable[i][tab.fieldNumByName(fn)];
				set.add(s);
			}
			sets.add(set);
			if(tab.fieldNumByName("NAME")!=-1){
				setnames.add(tab.stringTable[i][tab.fieldNumByName("NAME")]);
			}else{
				setnames.add("set"+i);
			}
		}
	}
	

}
