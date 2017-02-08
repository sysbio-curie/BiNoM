package fr.curie.BiNoM.pathways.MaBoSS;

import java.io.File;
import java.io.FileWriter;
//import java.io.PrintWriter;
//import java.util.Collections;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.*;

import vdaoengine.data.VDataTable;
import vdaoengine.data.io.VDatReadWrite;
import fr.curie.BiNoM.pathways.utils.Utils;

public class MaBoSSStatDistFile_svn {

	public String id = "id";
	public String description = "none";
	public Vector<String> nodeNames = new Vector<String>();
	public Vector<Vector<Integer>> fixedPoints = new Vector<Vector<Integer>>();
	public Vector<String> phenotypes = new Vector<String>();
	
	public static void main(String[] args) {
		try{
			MaBoSSStatDistFile_svn prob = new MaBoSSStatDistFile_svn();
						
			String folder = "";
			String prefix = "";
			boolean makeTable = false;
			for(int i=0;i<args.length;i++){
				if(args[i].equals("-folder"))
					folder = args[i+1];
				if(args[i].equals("-prefix"))
					prefix = args[i+1];
				if(args[i].equals("-maketable"))
					makeTable = true;
				}
			if(makeTable){
				prob.makeDistTableFromFolder(folder,prefix);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
	
	public void makeDistTableFromFolder(String folder, String prefix) throws Exception{
			Vector<String> mutants = Utils.loadStringListFromFile(folder+"descriptions.txt");
			FileWriter fw = new FileWriter(folder+prefix+"_dist_mutants.txt");
			int count=0;
			for(int i=0;i<mutants.size();i++){
				String m = mutants.get(i);
				StringTokenizer st = new StringTokenizer(m,"\t");
				String id = st.nextToken();
				String desc = st.nextToken();
				String select = "0";
				if(st.hasMoreTokens())
					select = st.nextToken();
				else
					select = "1";
				if(select.equals("1")){
//				String fn = folder+"metastasis_"+id+"_lm--metastasis_cm_statdist.csv";
//				String fp = folder+"metastasis_"+id+"_lm--metastasis_cm_fp.csv";
				String fn = folder+prefix+"_"+id+"_lm--"+prefix+"_cm_statdist.csv";
				String fp = folder+prefix+"_"+id+"_lm--"+prefix+"_cm_fp.csv";
				if(!desc.contains("up &")&&(new File(fn).exists())){
				count++;
				MaBoSSStatDistFile_svn ms = new MaBoSSStatDistFile_svn();
				ms.phenotypes.add("Invasion");
				ms.phenotypes.add("EMT");
				ms.phenotypes.add("Migration");
				ms.phenotypes.add("Metastasis");
				ms.phenotypes.add("Apoptosis");
				ms.phenotypes.add("CellCycleArrest");
				ms.id = id;
				ms.description = desc;
				ms.extractFixedPoints(fp);
//				ms.extractFixedPoints(fn);
				System.out.println((i+1)+": "+id);
				if(i==0){
					fw.write(ms.toString(true));
				}
				else
				if(ms.fixedPoints.size()>0){
						fw.write(ms.toString());
						}
				if(i!=mutants.size()-1)if(ms.fixedPoints.size()>0){
					fw.write("\n");
					}
				}
				}
			}
			fw.close();
			System.out.println(count+" mutants are counted");
			
			VDataTable vt = VDatReadWrite.LoadFromSimpleDatFile(folder+prefix+"_dist_mutants.txt", true, "\t");
			for(int i=4;i<vt.colCount;i++) vt.fieldTypes[i] = VDataTable.NUMERICAL;
			VDatReadWrite.saveToVDatFile(vt,folder+prefix+"_dist_mutants.dat");
			
			/*
			 * Count steady state frequencies
			 */
			VDataTable vt1 = VDatReadWrite.LoadFromVDatFile(folder+prefix+"_dist_mutants.dat");
			HashMap<String, Integer> counts = new HashMap<String, Integer>();
			for(int i=0;i<vt1.rowCount;i++){
				String fp = vt1.stringTable[i][vt1.fieldNumByName("FP_STRING")];
				count = 0;
				if(counts.get(fp)!=null) count = counts.get(fp);
				counts.put(fp, count+1);
			}
			vt1.addNewColumn("COUNT", "", "", VDataTable.NUMERICAL, "1");
			for(int i=0;i<vt1.rowCount;i++){
				String fp = vt1.stringTable[i][vt1.fieldNumByName("FP_STRING")];
				vt1.stringTable[i][vt1.fieldNumByName("COUNT")] = ""+counts.get(fp);
				vt1.stringTable[i][vt1.fieldNumByName("FP_STRING")] = "_"+fp;
			}
			VDatReadWrite.saveToVDatFile(vt1,folder+prefix+"_dist_mutants_count.dat");
			VDatReadWrite.saveToSimpleDatFile(vt1, folder+prefix+"_dist_mutants_count.txt",false);

//			code not used:
//			Iterator<String> it = counts.keySet().iterator();
//			while(it.hasNext()){
//				String s = it.next();
//			}
			
			/*
			 * Hamming distance
			 */
			VDataTable vt2 = VDatReadWrite.LoadFromVDatFile(folder+prefix+"_dist_mutants_count.dat");
			HashMap<String,String> WT = new HashMap<String,String>();
			HashMap<String,String> MUT = new HashMap<String,String>();
			for(int i=0;i<vt2.rowCount;i++){
				String fp_id = vt2.stringTable[i][vt2.fieldNumByName("FP_ID")];
				String fp = vt2.stringTable[i][vt2.fieldNumByName("FP_STRING")];
				String pattern = "_WT";
			    Pattern r = Pattern.compile(pattern);
			    Matcher m = r.matcher(fp_id);
			    if (m.find( )) {
						WT.put(fp_id,fp);
			    }else {
						MUT.put(fp_id,fp);
			      }
			}
			HashMap<String,Integer> distWT = new HashMap<String,Integer>();
			HashMap<String,String> closestWT = new HashMap<String,String>();

			for(Entry<String, String> entrymut: MUT.entrySet()) {
				String mutvalue= entrymut.getValue();
				String mutkey= entrymut.getKey();
			    int countmin=1000;
			    String minWT = null;
			    for(Entry<String, String> entryWT: WT.entrySet()) {
				    int Hdist = getHammingDistance(entryWT.getValue(),mutvalue);
				    if (Hdist < countmin){
				    	countmin = Hdist;
				    	minWT = entryWT.getKey();
				    }
				    else {}
			    }
				distWT.put(mutkey,countmin);
				closestWT.put(mutkey,minWT);
			}
			vt2.addNewColumn("Dist_to_WT", "", "", VDataTable.NUMERICAL, "1");
			for(int i=0;i<vt2.rowCount;i++){
				String fp = vt2.stringTable[i][vt2.fieldNumByName("FP_ID")];
				vt2.stringTable[i][vt2.fieldNumByName("Dist_to_WT")] = ""+distWT.get(fp);
			}
			vt2.addNewColumn("Closest_WT", "", "", VDataTable.STRING, "1");
			for(int i=0;i<vt2.rowCount;i++){
				String fp = vt2.stringTable[i][vt2.fieldNumByName("FP_ID")];
				vt2.stringTable[i][vt2.fieldNumByName("Closest_WT")] = ""+closestWT.get(fp);
			}
			VDatReadWrite.saveToVDatFile(vt2,folder+prefix+"_dist_mutants_Hamming.dat");
			VDatReadWrite.saveToSimpleDatFile(vt2, folder+prefix+"_dist_mutants_Hamming.txt",false);
}

	public String toString(){
		return toString(false);
	}
	
	public String toString(boolean header){
		String s = "";
		if(header){
			s+="ID\tDESCRIPTION\tFP_ID\tFP_STRING\t";
			for(int i=0;i<nodeNames.size();i++) s+=nodeNames.get(i)+"\t";
			s+="\n";
		}
		for(int i=0;i<fixedPoints.size();i++){
			s+=id+"\t";
			s+=description+"\t";
			s+=id+"#"+(i+1)+"\t";
			s+=convertFixedPointToString(fixedPoints.get(i))+"\t";
			for(int j=0;j<fixedPoints.get(i).size();j++)
				s+=fixedPoints.get(i).get(j)+"\t";
			if(i!=fixedPoints.size()-1)
				s+="\n";
		}
		return s;
	}

	public void extractFixedPoints(String fp){
		Vector<String> lines = Utils.loadStringListFromFile(fp);
		for(String s: lines){
			StringTokenizer st = new StringTokenizer(s,"\t");
			while(st.hasMoreTokens()){
				String n = st.nextToken();
				if(n.equals("State")){
					while(st.hasMoreTokens()){
					String nodeName = st.nextToken();
					if(phenotypes.contains(nodeName))
						nodeName = "0_"+nodeName;
					if(!nodeNames.contains(nodeName))
						nodeNames.add(nodeName);
				}
				}
			}
		}
		Vector<Integer> fixedPoint = null;
		for(String s: lines){
			StringTokenizer st = new StringTokenizer(s,"\n");
			while(st.hasMoreTokens()){
				String n = st.nextToken();
				if(n.contains("#")){
					if(fixedPoint!=null){
						fixedPoints.add(fixedPoint);
					}
					fixedPoint = new Vector<Integer>();
					String[] parts = n.split("\t");
					for(int i=3;i<parts.length;i++){ //we need to skip first 3 fields (0-2) and start with 4th (3)
						Integer value = Integer.parseInt(parts[i]);
						fixedPoint.add(value);
						}
				}
				}
			}
		if(fixedPoint!=null){
			fixedPoints.add(fixedPoint);
		}
		for(int i=0;i<nodeNames.size();i++)
			if(nodeNames.get(i).startsWith("0_")){
				nodeNames.set(i, nodeNames.get(i).substring(2, nodeNames.get(i).length()));
			}
	}
	
	public static String convertFixedPointToString(Vector<Integer> f){
		String s = "";
		for(Integer i: f)
			s+=""+i;
		return s;
	}
	 
//  Hamming Distance for two strings of the same length:
	public String compOne;
	public String compTwo;
	public int getHammingDistance(String one, String two){
	    	compOne = one;
	        compTwo = two;

	        if (compOne.length() != compTwo.length())
	        {
	            return -1;
	        }
	        int counter = 0;
	        for (int i = 0; i < compOne.length(); i++)
	        {
	            if (compOne.charAt(i) != compTwo.charAt(i)) counter++;
	        }
//			System.out.println(counter);
	        return counter;
	    }
}