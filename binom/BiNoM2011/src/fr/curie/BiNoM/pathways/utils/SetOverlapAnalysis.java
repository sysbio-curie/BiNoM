package fr.curie.BiNoM.pathways.utils;

import java.io.*;
import java.util.*;

import javax.swing.JFileChooser;

import cytoscape.Cytoscape;

import fr.curie.BiNoM.pathways.analysis.structure.Attribute;
import fr.curie.BiNoM.pathways.analysis.structure.BiographUtils;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
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

			//makeSLNetworkFromYeastScreen();
			//System.exit(0);
			
			SetOverlapAnalysis so = new SetOverlapAnalysis();

			/*Vector<String> current = new Vector<String>();
			Vector<Vector<String>> setofsets = new Vector<Vector<String>>();
			Vector<String> setnames = new Vector<String>();
			Vector<HashSet<String>> sets = new Vector<HashSet<String>>();			
			Vector<String> set1 = new Vector<String>(); set1.add("PARP1"); //set1.add("PARP2"); 
			Vector<String> set2 = new Vector<String>(); set2.add("RFC1"); set2.add("RFC2"); set2.add("RFC3");  
			Vector<String> set3 = new Vector<String>();	set3.add("RR1"); set3.add("RR2");
			setofsets.add(set1); setofsets.add(set2); setofsets.add(set3);
			so.generateAllCombinations(current, setofsets, setnames, sets, "set",0);
			System.exit(0);
			*/
			
			//String prefix = "C:/Datas/Kairov/DifferentialExpression4BC/lists/";
			//String prefix = "C:/Datas/KEGG/Human/dnarepair_KEGG";
			//String prefix = "C:/Datas/KEGG/Human/dnarepair_map_PARP12BRCA12";
			//String prefix = "C:/Datas/KEGG/Test/test";
			//String prefix = "C:/Datas/KEGG/Test/ocsana_report_cellfate";
			//String prefix = "C:/Datas/KEGG/Human/dnarepair_map_onlyRepair";
			//String prefix = "C:/Datas/DNARepairAnalysis/dnarepair_path";
			//String prefix = "C:/Datas/DNARepairAnalysis/dnarepair_path_reg_re";
			String prefix = "C:/Datas/DNARepairAnalysis/dna_repair_genes";
			
			

			
			//so.convertTableSetToGMT(prefix+".minhitsets",prefix+".minhitsets.gmt",3);
			//so.expandSetsOfLists_SplitSets(prefix+".minhitsets.gmt", "C:/Datas/DNARepairAnalysis/dna_repair_genes.gmt", prefix+".minhitsets_hugo.gmt");			
			//so.expandSetsOfLists_ExpandSets(prefix+".gmt", "C:/Datas/DNARepairAnalysis/dna_repair_genes.gmt", prefix+"_hugo.gmt");
			//so.expandSetsOfLists_ExpandSets(prefix+".gmt", "C:/Datas/DNARepairAnalysis/dna_repair_genes.gmt", prefix+"_hugo.gmt");
			//so.LoadSetsFromGMT(prefix+".minhitsets_hugo.gmt"); for(int i=0;i<so.allproteins.size();i++) System.out.println(so.allproteins.get(i));
			//so.convertXGMMLtoGMT("C:/Datas/SyntheticInteractions/Caso2009/SL_human.sif.xgmml","C:/Datas/SyntheticInteractions/Caso2009/SL_human.sif.gmt", false);
			//so.expandSetsOfLists_SplitSets("c:/datas/biogrid/yeast_genetic_header_compr_ORF_negative.gmt", "C:/Datas/SyntheticInteractions/yeast_human_orthologs.gmt", "c:/datas/biogrid/yeast_genetic_humanized_negative_pairs.gmt");

			//int inters = so.intersect2ListsOfSets("C:/Datas/SyntheticInteractions/Caso2009/SL_human.sif.gmt",prefix+".minhitsets_hugo.gmt", 10000000, 10);
			//int inters = so.intersect2ListsOfSets("C:/Datas/SyntheticInteractions/Constanzo2010/stringent_humanized_negative_pairs.gmt",prefix+".minhitsets_hugo.gmt", 10000000, 0);
			//int inters = so.intersect2ListsOfSets("C:/Datas/SyntheticInteractions/Constanzo2010/stringent_humanized_negative_pairs.gmt","C:/Datas/SyntheticInteractions/Caso2009/SL_human.sif.gmt", 10000000, 0);
			//int inters = so.intersect2ListsOfSets("C:/Datas/SyntheticInteractions/Constanzo2010/stringent_humanized_negative_pairs.gmt","C:/Datas/SyntheticInteractions/Caso2009/SL_human.sif.gmt", 10000000, 0);	
			//int inters = so.intersect2ListsOfSets("C:/Datas/BioGrid/human_mouse_genetic.gmt",prefix+".minhitsets_hugo.gmt", 10000000, 0);
			//int inters = so.intersect2ListsOfSets("C:/Datas/BioGrid/yeast_genetic_humanized_negative.gmt",prefix+".minhitsets_hugo.gmt", 10000000, 0);
			//int inters = so.intersect2ListsOfSets("C:/Datas/BioGrid/yeast_genetic_humanized_negative_pairs.gmt",prefix+".minhitsets_hugo_noWRN.gmt", 10000000, 0);
			//System.out.println("Total "+inters+" complete overlaps");

			//generateAllPairwiseGMT(prefix+"_hugo_noWRN.gmt", prefix+"_hugo_noWRN_allpairs.gmt");
			
			for(int i=0;i<0;i++){
			//Graph connectionGraph = so.getBiPartiteSetConnectionGraph(prefix+"_hugo.gmt","C:/Datas/SyntheticInteractions/Caso2009/SL_human.sif.gmt",1000000);
				//Graph connectionGraph = so.getBiPartiteSetConnectionGraph(prefix+"_hugo.gmt","C:/Datas/SyntheticInteractions/Constanzo2010/stringent_humanized_negative_pairs.gmt",10000000);
				//Graph connectionGraph = so.getBiPartiteSetConnectionGraph(prefix+"_hugo_noWRN.gmt",prefix+"_hugo_noWRN_allpairs.gmt",0000000);
				//Graph connectionGraph = so.getBiPartiteSetConnectionGraph(prefix+"_hugo_noWRN.gmt","c:/datas/biogrid/human_mouse_genetic.gmt",0000000);
				Graph connectionGraph = so.getBiPartiteSetConnectionGraph(prefix+"_hugo_noWRN.gmt","c:/datas/biogrid/yeast_genetic_humanized_negative_pairs.gmt",0000000);
			    //Graph connectionGraph = so.getBiPartiteSetConnectionGraph(prefix+"_hugo.gmt","C:/Datas/DNARepairAnalysis/dnarepair_path_reg_ber.minhitsets_hugo.gmt",1000000);
			XGMML.saveToXGMML(connectionGraph, "C:/Datas/DNARepairAnalysis/temp.xgmml");
			System.out.println("Connection graph coverage score = "+getConnectionGraphCoverageScore(connectionGraph));
			}
			//System.exit(0);
			
			so.LoadSetsFromGMT(prefix+".gmt");
			//so.findMinimalHittingSet(3, prefix);
			
			//String typesOfRegulations[] = new String[]{"CATALYSIS","TRIGGER","MODULATION","PHYSICAL_STIMULATION","UNKNOWN_CATALYSIS"};
			///Graph graph = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("C:/Datas/DNARepairAnalysis/dnarepair.xml.xgmml"));
			//so.makeGMTOfReactionRegulators(prefix+"_reg", graph, typesOfRegulations);
			
			//so.printSetSizes();
			//so.printSetIntersections();
			
			JFileChooser j = new JFileChooser();
			j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			Integer opt = j.showOpenDialog(Cytoscape.getDesktop());
			if(opt!=-1){
				//printSetIntersectionsInFolder("C:/Datas/acsn/naming/");
				printSetIntersectionsInFolder(j.getSelectedFile().getAbsolutePath());
			}
			System.exit(0);
			

			//so.listSetsIncludingSet(prefix+".minhitsets",new String[]{"BRCA1"});
			
			//Vector<String> gmts = new Vector<String>(); gmts.add(prefix+".gmt"); gmts.add(prefix+"_re.gmt");
			//convertSetofGMTsToTable(gmts);
			
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
			String groupName = st.nextToken().trim();
			setnames.add(groupName);
			String description = st.nextToken();
			HashSet<String> proteins = new HashSet<String>();
			while((st.hasMoreTokens())){
				String protein = st.nextToken();
				if(!protein.trim().equals(""))
					proteins.add(protein);
				if(!protein.contains(":")) // this is for DNA repair map!!!
				if(!allproteins.contains(protein))
					allproteins.add(protein);	
			}
			sets.add(proteins); //System.out.print(groupName+":"+proteins.size()+"\t");
		}
		System.out.println(fn+": totally "+allproteins.size()+" genes are found");
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
	
	public void printSetIntersections(String filename) throws Exception{
		FileWriter fw = null;
		if(filename!=null)
			fw = new FileWriter(filename);
		System.out.println("SET1\tSET2\tINTER\tINTERSECTION_SIZE\tINTERSECTION");
		if(fw!=null)
			fw.write("SET1\tSET2\tINTER\tINTERSECTION_SIZE\tINTERSECTION\n");
		for(int i=0;i<sets.size();i++){
			for(int j=i+1;j<sets.size();j++){
				HashSet<String> seti = sets.get(i);
				HashSet<String> setj = sets.get(j);		
				Vector<String> names = calcIntersectionOfSets(seti, setj);
				Collections.sort(names);
				if(names.size()>0){
					System.out.print(setnames.get(i)+"\t"+setnames.get(j)+"\tintersects\t"+names.size()+"\t");
					if(fw!=null)
						fw.write(setnames.get(i)+"\t"+setnames.get(j)+"\tintersects\t"+names.size()+"\t");
				}
				for(int k=0;k<names.size();k++)
					if(k==names.size()-1){
						System.out.print(names.get(k)+"\n");
						if(fw!=null)
							fw.write(names.get(k)+"\n");
					}
					else{
						System.out.print(names.get(k)+",");
						if(fw!=null)
							fw.write(names.get(k)+",");
					}
				String namei = setnames.get(i).toLowerCase();
				String namej = setnames.get(j).toLowerCase();
				namei = Utils.replaceString(namei, " ", "");
				namei = Utils.replaceString(namei, "_", "");
				namei = Utils.replaceString(namei, "*", "");
				namei = Utils.replaceString(namei, "'", "");
				namei = Utils.replaceString(namei, "endsub", "");
				namei = Utils.replaceString(namei, "sub", "");
				namei = Utils.replaceString(namei, "endsuper", "");
				namei = Utils.replaceString(namei, "super", "");
				namei = Utils.replaceString(namei, "(", "");
				namei = Utils.replaceString(namei, ")", "");
				namei = Utils.replaceString(namei, "-", "");
				namei = Utils.replaceString(namei, "br", "");
				namei = Utils.replaceString(namei, "/", "");
				namei = Utils.replaceString(namei, "~", "");				
				
				namej = Utils.replaceString(namej, " ", "");
				namej = Utils.replaceString(namej, "_", "");
				namej = Utils.replaceString(namej, "*", "");
				namej = Utils.replaceString(namej, "'", "");
				namej = Utils.replaceString(namej, "endsub", "");
				namej = Utils.replaceString(namej, "sub", "");
				namej = Utils.replaceString(namej, "endsuper", "");
				namej = Utils.replaceString(namej, "super", "");
				namej = Utils.replaceString(namej, "(", "");
				namej = Utils.replaceString(namej, ")", "");
				namej = Utils.replaceString(namej, "-", "");
				namej = Utils.replaceString(namej, "br", "");
				namej = Utils.replaceString(namej, "/", "");
				namej = Utils.replaceString(namej, "~", "");				
				
				if(namei.equals(namej)){
					System.out.print(setnames.get(i)+"\t"+setnames.get(j)+"\tintersectsname\t"+names.size()+"\tname");
					if(fw!=null)
						fw.write(setnames.get(i)+"\t"+setnames.get(j)+"\tintersectsname\t"+names.size()+"\tname\n");
					
				}
				
				
			}
		}
		if(fw!=null)
			fw.close();
	}
	
	public static void printSetIntersectionsInFolder(String folder) throws Exception{
		File f = new File(folder);
		File files[] = f.listFiles();
		SetOverlapAnalysis so = new SetOverlapAnalysis();
		so.setnames = new Vector<String>();
		so.sets = new Vector<HashSet<String>>();
		for(int i=0;i<files.length;i++)if(files[i].getName().endsWith("gmt")){
			SetOverlapAnalysis si = new SetOverlapAnalysis();
			si.LoadSetsFromGMT(files[i].getAbsolutePath());
			for(int j=0;j<si.setnames.size();j++){
				String name = si.setnames.get(j);
				name = name+"_"+files[i].getName().substring(0, 2);
				so.setnames.add(name);
				so.sets.add(si.sets.get(j));
			}
		}
		so.printSetIntersections(folder+"/_intersection.txt");
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

		/*System.out.print("SIZE\t");*/ fw.write("SIZE\t");
		for(int sz=1;sz<=maxFoundSize;sz++) { /*System.out.print("N"+sz+"\t");*/ fw.write("N"+sz+"\t"); } 
		for(int sz=1;sz<=maxFoundSize;sz++) { /*System.out.print("SETS"+sz+"\t");*/ fw.write("SETS"+sz+"\t"); } 		
		/*System.out.println();*/ fw.write("\n");
		
		
		Iterator<String> it1 = oca.hitSetSizeOne.iterator();
		while(it1.hasNext()){
			String node = it1.next();
			//System.out.println("1\t"+node);
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
				//System.out.print("\t");
				fw.write("\t");
			}
			for(int i=0;i<set.size();i++){
				String node = set.get(i);
				Vector<String> names = getListOfSets(node);
				String s = "(";
				for(int j=0;j<names.size();j++) if(j==names.size()-1) s+=names.get(j); else s+=names.get(j)+";";
				s+=")";
				//System.out.print(s+"\t");
				fw.write(s+"\t");
			}
			//System.out.println();
			fw.write("\n");
			hitSetString.add(set);
			}
		}
		frequencies.add(analyzeHitFrequency(hitSetString));		
		}
		
		FileWriter fw1 = new FileWriter(fileNamePreifix+".freqmhs");		
		/*System.out.println();
		System.out.print("NODE\tSETS\tNSETS\t");*/ fw1.write("NODE\tSETS\tNSETS\t");
		for(int sz=2;sz<=maxFoundSize;sz++) { /*System.out.print("SZ"+sz+"\t");*/ fw1.write("SZ"+sz+"\t"); } /*System.out.println();*/ fw1.write("\n");
		for(int i=0;i<allproteins.size();i++){
			Vector<String> names = getListOfSets(allproteins.get(i));
			String s = "(";
			for(int j=0;j<names.size();j++) if(j==names.size()-1) s+=names.get(j); else s+=names.get(j)+";";
			s+=")";
			//System.out.print(allproteins.get(i)+"\t"+s+"\t"+names.size()+"\t");
			fw1.write(allproteins.get(i)+"\t"+s+"\t"+names.size()+"\t");
			for(int j=2;j<=maxFoundSize;j++){
				//System.out.print(frequencies.get(j-2).get(i).score+"\t");
				fw1.write(frequencies.get(j-2).get(i).score+"\t");
			}
			/*System.out.println();*/ fw1.write("\n");
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
	
	public void makeGMTOfReactionRegulators(String prefix, Graph reactionGraph, String typesOfRegulations[], int order) throws Exception{
		FileWriter fw = new FileWriter(prefix+".gmt");
		Vector<String> reactions = new Vector<String>();
		for(int i=0;i<setnames.size();i++){
			fw.write(setnames.get(i)+"\tna\t");
			Vector<Node> regulators = BiographUtils.findReactionRegulators(reactionGraph, sets.get(i), typesOfRegulations, order);
			for(String s:sets.get(i)){
				if(!reactions.contains(s))
					reactions.add(s);
			}
			Vector<String> regnames = BiographUtils.extractProteinNamesFromNodeNames(regulators);
			for(int j=0;j<regnames.size();j++){
				fw.write(regnames.get(j)+"\t");
			}
			fw.write("\n");
		}
		fw.close();
		Collections.sort(reactions);
		// Make also gmt of reaction regulators per set
		fw = new FileWriter(prefix+"_re.gmt");
		for(String re: reactions){
			HashSet<String> set = new HashSet<String>();
			set.add(re);
			Vector<Node> regulators = BiographUtils.findReactionRegulators(reactionGraph, set, typesOfRegulations, order);
			Vector<String> regnames = BiographUtils.extractProteinNamesFromNodeNames(regulators);
			//regnames.remove("G1 cell cycle phase");
			//regnames.remove("S cell cycle phase");
			if(regnames.size()>0){
			fw.write(re+"\tna\t");						
			for(int j=0;j<regnames.size();j++){
				fw.write(regnames.get(j)+"\t");
			}
			fw.write("\n");		
			}
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
	
	public void convertTableSetToGMT(String fnSetTable, String fnSetGMT, int maxSize) throws Exception{
		loadSetsFromTable(fnSetTable);
		saveSetsAsGMT(fnSetGMT,maxSize);
	}
	
	public void saveSetsAsGMT(String fnSetGMT, int maxSize) throws Exception{
		FileWriter fw = new FileWriter(fnSetGMT);
		for(int i=0;i<setnames.size();i++)if((maxSize==-1)||(sets.get(i).size()<=maxSize)){
			fw.write(setnames.get(i)+"\tna\t");
				HashSet<String> hs = sets.get(i);
				Iterator<String> its = hs.iterator();
				while(its.hasNext()){
					fw.write(its.next()+"\t");
				}
				fw.write("\n");
		}
		fw.close();
	}
	
	public static void convertSetofGMTsToTable(Vector<String> gmts){
		Vector<SetOverlapAnalysis> sos = new Vector<SetOverlapAnalysis>();
		Vector<String> allproteins = new Vector<String>();
		for(int i=0;i<gmts.size();i++){
			SetOverlapAnalysis so = new SetOverlapAnalysis();
			so.LoadSetsFromGMT(gmts.get(i));
			sos.add(so);
			for(int j=0;j<so.allproteins.size();j++)
				if(!allproteins.contains(so.allproteins.get(j)))
					allproteins.add(so.allproteins.get(j));
		}
		Collections.sort(allproteins);
		System.out.print("GENE\t"); 
		for(int i=0;i<gmts.size();i++){
			String fn = (new File(gmts.get(i))).getName();
			System.out.print(fn.substring(0,fn.length()-4)+"\t");
		}
		for(int i=0;i<gmts.size();i++){
			SetOverlapAnalysis so = sos.get(i);
			for(int j=0;j<so.setnames.size();j++) System.out.print(so.setnames.get(j)+"\t");
		}
		System.out.println();
		for(int i=0;i<allproteins.size();i++){
			String pn = allproteins.get(i);
			System.out.print(pn+"\t");
			for(int j=0;j<gmts.size();j++) System.out.print(sos.get(j).getListOfSets(pn).size()+"\t");
			for(int j=0;j<gmts.size();j++){
				for(int k=0;k<sos.get(j).setnames.size();k++)
					if(sos.get(j).sets.get(k).contains(pn))
						System.out.print("1\t");
					else
						System.out.print("0\t");
			}
		System.out.println();					
		}
	}
	
	
	public void expandSetsOfLists_ExpandSets(String setGMT, String expansionSetGMT, String fn) throws Exception{
		LoadSetsFromGMT(setGMT);
		SetOverlapAnalysis expansionSets = new SetOverlapAnalysis();
		expansionSets.LoadSetsFromGMT(expansionSetGMT);
		
		Vector<String> newsetnames = new Vector<String>();
		Vector<HashSet<String>> newsets = new Vector<HashSet<String>>();
		
		for(int i=0;i<setnames.size();i++){
			HashSet<String> set = sets.get(i);
			Iterator<String> its = set.iterator();
			Vector<String> items = new Vector<String>();
			while(its.hasNext())
				items.add(its.next());
			Vector<Vector<String>> tempItemsExpanded = new Vector<Vector<String>>();
			for(int j=0;j<items.size();j++){
				String item = items.get(j);
				int k = expansionSets.setnames.indexOf(item);
				if(k!=-1){
					HashSet eset = expansionSets.sets.get(k);
					Iterator<String> ite = eset.iterator();
					set.remove(item);
					while(ite.hasNext())
						set.add(ite.next());
				}
			}
		}
		saveSetsAsGMT(fn,-1);
	}
	
	public void expandSetsOfLists_SplitSets(String setGMT, String expansionSetGMT, String fn) throws Exception{
		LoadSetsFromGMT(setGMT);
		SetOverlapAnalysis expansionSets = new SetOverlapAnalysis();
		expansionSets.LoadSetsFromGMT(expansionSetGMT);
		
		Vector<String> newsetnames = new Vector<String>();
		Vector<HashSet<String>> newsets = new Vector<HashSet<String>>();
		
		for(int i=0;i<setnames.size();i++){
			HashSet<String> set = sets.get(i);
			Iterator<String> its = set.iterator();
			Vector<String> items = new Vector<String>();
			while(its.hasNext())
				items.add(its.next());
			Vector<Vector<String>> tempItemsExpanded = new Vector<Vector<String>>();
			for(int j=0;j<items.size();j++){
				String item = items.get(j);
				int k = expansionSets.setnames.indexOf(item);
				Vector<String> temp = new Vector<String>();
				if(k!=-1){
					Iterator<String> itt = expansionSets.sets.get(k).iterator();
					while(itt.hasNext()){ 
						String s = itt.next(); 
						if(!s.trim().equals("")) 
							if(!temp.contains(s)) 
								temp.add(s); 
					}
					System.out.print("Expansion set("+temp.size()+"): "); for(int kk=0;kk<temp.size();kk++) System.out.print(temp.get(kk)+"\t"); System.out.println();
				}else{
				    temp.add(item);
				}
				tempItemsExpanded.add(temp);
			}
			Vector<String> currentSet = new Vector<String>();
			generateAllCombinations(currentSet, tempItemsExpanded, newsetnames, newsets, setnames.get(i), newsets.size());
		}
		
		this.setnames = newsetnames;
		this.sets = newsets;	
		saveSetsAsGMT(fn,-1);
	}
	
	public void generateAllCombinations(Vector<String> currentSet, Vector<Vector<String>> setofsets, Vector<String> names, Vector<HashSet<String>> sets, String name, int number){
		if(setofsets.size()==1){
		for(int i=0;i<setofsets.get(0).size();i++){
			HashSet<String> set = new HashSet<String>();
			names.add(name+"_"+(sets.size()-number)); System.out.print(name+"_"+(sets.size()-number)+":\t");
			for(int j=0;j<currentSet.size();j++){
				set.add(currentSet.get(j)); System.out.print(currentSet.get(j)+"\t");
			}
			set.add(setofsets.get(0).get(i)); System.out.println(setofsets.get(0).get(i));
			sets.add(set);
			
		}   //currentSet.clear();
			//String s = null; if(currentSet.size()>0) s = currentSet.get(0); currentSet.clear(); if(s!=null) currentSet.add(s);
		}else{
			for(int i=0;i<setofsets.get(0).size();i++){			
				Vector<Vector<String>> stemp = new Vector<Vector<String>>();
				for(int j=1;j<setofsets.size();j++)
					stemp.add(setofsets.get(j));
				currentSet.add(setofsets.get(0).get(i)); //System.out.println("Generating from "+setofsets.get(0).get(i));
				generateAllCombinations(currentSet, stemp, names, sets, name, number);
				//currentSet.clear();
				if(currentSet.size()>0) 
					currentSet.remove(currentSet.size()-1);
			}
		}
	}
	
	public static void convertXGMMLtoGMT(String xgmmlFileName, String gmtFileName, boolean splitIntoPairs) throws Exception{
		Graph  graph = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(xgmmlFileName));
		SetOverlapAnalysis so = new SetOverlapAnalysis();
		so.setnames = new Vector<String>();
		so.sets = new Vector<HashSet<String>>();
		for(int i=0;i<graph.Edges.size();i++)if(!graph.Edges.get(i).Node1.Id.equals(graph.Edges.get(i).Node2.Id)){
			String id1 = graph.Edges.get(i).Node1.Id;
			String id2 = graph.Edges.get(i).Node2.Id;			
			StringTokenizer st1 = new StringTokenizer(id1," ,");
			StringTokenizer st2 = new StringTokenizer(id2," ,");
			/*HashSet<String> set = new HashSet<String>();
			set.add(id1);
			set.add(id2);
			sets.add(set);*/

			int k=0;
			if(splitIntoPairs){
			while(st1.hasMoreTokens()){
				String st1s = st1.nextToken();
				while(st2.hasMoreTokens()){
					String st2s = st2.nextToken();
					HashSet<String> set = new HashSet<String>();
					if(!st1s.equals(st2s)){
						so.setnames.add(graph.Edges.get(i).Id+"_"+k);
						set.add(st1s);
						set.add(st2s);
						so.sets.add(set);
						k++;
					}
				}
			}
			}else{
				HashSet<String> set = new HashSet<String>();
				while(st1.hasMoreTokens()){
					String st1s = st1.nextToken();
					while(st2.hasMoreTokens()){
						String st2s = st2.nextToken();
						set.add(st1s);
						set.add(st2s);
					}
				}
				if(set.size()>1){
					so.setnames.add(graph.Edges.get(i).Id);
					so.sets.add(set);
				}
			}
		}
		so.saveSetsAsGMT(gmtFileName, -1);
	}
	
	public int intersect2ListsOfSets(String gmt1, String gmt2){
		return intersect2ListsOfSets(gmt1, gmt2, 0, 0);
	}
	
	public int intersect2ListsOfSets(String gmt1, String gmt2, int numberOfReshuffles, int numbeOfPermutationsForPValue){
		int count = 0;
		SetOverlapAnalysis so1 = new SetOverlapAnalysis();
		SetOverlapAnalysis so2 = new SetOverlapAnalysis();	
		so1.LoadSetsFromGMT(gmt1);
		so2.LoadSetsFromGMT(gmt2);
		count = intersect2ListsOfSets(so1, so2);
		int pcount = 0;
		for(int i=0;i<numbeOfPermutationsForPValue;i++){
			System.out.println("Permutation "+(i+1)+":");
			so1.reshuffleSets(numberOfReshuffles,true);
			int lcount = intersect2ListsOfSets(so1, so2);
			System.out.println(lcount+" overlaps in random case.");
			if(lcount>=count) pcount++;
		}
		System.out.println("Estimated p-value = "+1.0f*pcount/numbeOfPermutationsForPValue);
		return count;
	}
	
	public int intersect2ListsOfSets(SetOverlapAnalysis so1, SetOverlapAnalysis so2){
		int count = 0;
		for(int i=0;i<so1.setnames.size();i++){
			HashSet<String> set1 = so1.sets.get(i);
			int countCompleteOverlap = 0;
			for(int j=0;j<so2.setnames.size();j++){
				HashSet<String> set2 = so2.sets.get(j);
				Vector<String> overlap = calcIntersectionOfSets(set1, set2);
				if((overlap.size()==set2.size())||(overlap.size()==set1.size()))
					countCompleteOverlap++;
			}
			if(countCompleteOverlap>0){
			System.out.print(so1.setnames.get(i)+"\t");
			Iterator<String> it1 = set1.iterator();
			while(it1.hasNext()){
				System.out.print(it1.next());
				if(it1.hasNext()) System.out.print(",");
			}
			System.out.println("\t"+countCompleteOverlap);
			//count+=countCompleteOverlap;
			count++;
			}
		}
		return count;
	}
	
	public void reshuffleSets(int numberOfPermutations, boolean conserveSizes){
		Random r = new Random();
		int efp =0;
		for(int i=0;i<numberOfPermutations;i++){
			int i1 = r.nextInt(sets.size());
			int i2 = r.nextInt(sets.size());
			int j1 = r.nextInt(sets.get(i1).size());
			int j2 = r.nextInt(sets.get(i2).size());
			//System.out.println(i1+"("+j1+")"+"\t"+i2+"("+j2+")");
			HashSet set1 = sets.get(i1);
			HashSet set2 = sets.get(i2);
			//if(set1.size()==1)
			//	System.out.println(setnames.get(i1)+":\t"+set1.size()+"\t"+set2.size());
			String el1 = ""; Iterator<String> it1 = set1.iterator(); for(int k=0;k<=j1;k++) el1 = it1.next();
			String el2 = ""; Iterator<String> it2 = set2.iterator(); for(int k=0;k<=j2;k++) el2 = it2.next();
			//if((!el1.equals(""))&&(!el2.equals("")))
			if((!set1.contains(el2))&&(!set2.contains(el1))||(!conserveSizes)){
				set1.remove(el1);
				set2.remove(el2);
				set1.add(el2);
				set2.add(el1);
				efp++;
			}
		}
		System.out.println(efp+" effective permutations were made");
	}
	
	public Graph getBiPartiteSetConnectionGraph(String gmtSets, String gmtConnectors, int numberOfPermutations) throws Exception{
		SetOverlapAnalysis sets = new SetOverlapAnalysis();
		SetOverlapAnalysis connectors = new SetOverlapAnalysis();		
		sets.LoadSetsFromGMT(gmtSets);
		connectors.LoadSetsFromGMT(gmtConnectors);		

		connectors.reshuffleSets(numberOfPermutations, true);
		
		//sets.reshuffleSets(numberOfPermutations, false);

		//sets.saveSetsAsGMT("C:/Datas/DNARepairAnalysis/temp.gmt", -1);		
		
		Graph connectionGraph = new Graph();
		for(int i=0;i<sets.setnames.size();i++){
			Node n = connectionGraph.getCreateNode(sets.setnames.get(i));
			n.setAttributeValueUnique("NODE_TYPE", "SET", Attribute.ATTRIBUTE_TYPE_STRING);
		}
		Date d = new Date();
		for(int i=0;i<connectors.setnames.size();i++){
			if(i==(int)(0.0001f*i)*10000){
				System.out.println(i+"\tMem="+Utils.getUsedMemoryMb()+"\tTime="+((new Date()).getTime()-d.getTime())/1000);
				d = new Date();
			}
			HashSet<String> set = connectors.sets.get(i);
			String connectorLabel = "";
			Iterator<String> its = set.iterator();
			Vector<Vector<String>> connected = new Vector<Vector<String>>();
			while(its.hasNext()){
				String item = its.next();
				if(item.equals(""))
					System.out.println(connectors.setnames.get(i)+"("+set.size()+")");
				connectorLabel+=item+"_";
				Vector<String> found = sets.getListOfSets(item);
				connected.add(found);
			}
			
			// Variant 1 - connector nodes should belong to at least one set (allconnectedsets)
			Vector<String> allconnectedsets = new Vector<String>();
			for(int s=0;s<connected.size();s++){
				Vector<String> found = connected.get(s);
				if(found.size()==0){
					allconnectedsets.clear(); break;
				}
				for(int j=0;j<found.size();j++)
					if(!allconnectedsets.contains(found.get(j)))
						allconnectedsets.add(found.get(j));
			}
			
			if(connectorLabel.length()>0) connectorLabel = connectorLabel.substring(0, connectorLabel.length()-1);
			/*Vector<String> uniquelyConnected = new Vector<String>();
			for(int j=0;j<allconnectedsets.size();j++){
				String setname = allconnectedsets.get(j);
				int count = 0;
				for(int k=0;k<connected.size();k++)
					if(connected.get(k).contains(setname))
						count++;
				if(count==1)
					uniquelyConnected.add(setname);
			}*/
			Collections.sort(allconnectedsets);
			String label = ""; 
			for(int k=0;k<allconnectedsets.size();k++) label+=allconnectedsets.get(k)+"|"; 
			if(label.length()>0) label = label.substring(0, label.length()-1);
			label = "C:"+label;
			Node connector = connectionGraph.getCreateNode(label);
			connector.setAttributeValueUnique("NODE_TYPE", "CONNECTORS", Attribute.ATTRIBUTE_TYPE_STRING);			
			if(connector.getFirstAttributeValue("NUMBER_OF_CONNECTORS")==null){
				connector.setAttributeValueUnique("NUMBER_OF_CONNECTORS", "1", Attribute.ATTRIBUTE_TYPE_REAL);	
				connector.setAttributeValueUnique("NUMBER_OF_CONNECTED_SETS", ""+allconnectedsets.size(), Attribute.ATTRIBUTE_TYPE_REAL);					
				connector.setAttributeValueUnique("CONNECTED_SETS", label, Attribute.ATTRIBUTE_TYPE_STRING);	
				connector.setAttributeValueUnique("CONNECTOR",connectorLabel,Attribute.ATTRIBUTE_TYPE_STRING);
				for(int k=0;k<allconnectedsets.size();k++){
					Edge e = new Edge();
					e.Node1 = connector;
					e.Node2 = connectionGraph.getNode(allconnectedsets.get(k));
					e.Id = e.Node1+"_"+e.Node2;
					connectionGraph.addEdge(e);
				}
			}else{
				int num = Integer.parseInt(connector.getFirstAttributeValue("NUMBER_OF_CONNECTORS"));
				connector.setAttributeValueUnique("NUMBER_OF_CONNECTORS", ""+(num+1), Attribute.ATTRIBUTE_TYPE_REAL);
				String cat = connector.getFirstAttributeValue("CONNECTOR");
				connector.setAttributeValueUnique("CONNECTOR",cat+";"+connectorLabel,Attribute.ATTRIBUTE_TYPE_STRING);				
			}
		}
		connectionGraph.assignEdgeIds();
		return connectionGraph;
	}
	
	public static float getConnectionGraphCoverageScore(Graph graph){
		float score = 0f;
		for(int i=0;i<graph.Nodes.size();i++){
			Node n =  graph.Nodes.get(i);
			if(n.getFirstAttributeValue("NODE_TYPE").equals("CONNECTORS")){
				int num_connected_sets = Integer.parseInt(n.getFirstAttributeValue("NUMBER_OF_CONNECTED_SETS"));
				int num_connectors = Integer.parseInt(n.getFirstAttributeValue("NUMBER_OF_CONNECTORS"));
				score+=num_connected_sets*num_connectors;
			}
		}
		return score;
	}
	
	public static void generateAllPairwiseGMT(String gmtFile, String newFile) throws Exception{
		SetOverlapAnalysis so = new SetOverlapAnalysis();
		SetOverlapAnalysis sonew = new SetOverlapAnalysis();		
		so.LoadSetsFromGMT(gmtFile);
		sonew.setnames = new Vector<String>();
		sonew.sets = new Vector<HashSet<String>>();
		for(int i=0;i<so.allproteins.size();i++)for(int j=i+1;j<so.allproteins.size();j++){
			sonew.setnames.add(so.allproteins.get(i)+"_"+so.allproteins.get(j));
			HashSet<String> pair = new HashSet<String>();
			pair.add(so.allproteins.get(i));
			pair.add(so.allproteins.get(j));
			sonew.sets.add(pair);
		}
		sonew.saveSetsAsGMT(newFile, -1);
	}

}
