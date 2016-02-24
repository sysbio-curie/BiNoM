package fr.curie.BiNoM.pathways.MaBoSS;

import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import vdaoengine.data.VDataTable;
import vdaoengine.data.io.VDatReadWrite;
import fr.curie.BiNoM.pathways.utils.Utils;

public class MaBoSSStatDistFile {

	public String id = "id";
	public String description = "none";
	public Vector<String> nodeNames = new Vector<String>();
	public Vector<Vector<Integer>> fixedPoints = new Vector<Vector<Integer>>();
	
	public Vector<String> phenotypes = new Vector<String>();
	
	public static void main(String[] args) {
		try{
			
			//String folder = "C:/Datas/Metastasis/Cohen2014/logic_sensitivity/metastasis_mutants_logics/";
			//String folder = "C:/Datas/Metastasis/Cohen2014/logic_sensitivity/FinalAnalysis010915/metastasis_mutants_logics/";
			String folder = "D:/temp/metastasis_mutants_logics/";
			/*MaBoSSStatDistFile ms = new MaBoSSStatDistFile();
			ms.extractFixedPoints(folder+"metastasis__WT_lm_statdist.csv");
			System.out.println(ms);*/
			
			Vector<String> mutants = Utils.loadStringListFromFile(folder+"descriptions.txt");
			
			FileWriter fw = new FileWriter(folder+"_mutants.txt");
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
				String fn = folder+"metastasis_"+id+"_lm--metastasis_cm_statdist.csv";
				if(!desc.contains("up &")&&(new File(fn).exists())){
				count++;
				MaBoSSStatDistFile ms = new MaBoSSStatDistFile();
				ms.phenotypes.add("Invasion");
				ms.phenotypes.add("EMT");
				ms.phenotypes.add("Migration");
				ms.phenotypes.add("Metastasis");
				ms.phenotypes.add("Apoptosis");
				ms.phenotypes.add("CellCycleArrest");
				ms.id = id;
				ms.description = desc;
				//String fn = folder+"metastasis_"+id+"_lm_statdist.csv";
				
				ms.extractFixedPoints(fn);
				System.out.println((i+1)+": "+id);
				if(i==0)
					fw.write(ms.toString(true));
				else
					if(ms.fixedPoints.size()>0)
						fw.write(ms.toString());
				if(i!=mutants.size()-1)if(ms.fixedPoints.size()>0)
					fw.write("\n");
				}
				}
			}
			fw.close();
			System.out.println(count+" mutants are counted");
			
			VDataTable vt = VDatReadWrite.LoadFromSimpleDatFile(folder+"_mutants.txt", true, "\t");
			for(int i=4;i<vt.colCount;i++) vt.fieldTypes[i] = vt.NUMERICAL;
			VDatReadWrite.saveToVDatFile(vt,folder+"_mutants.dat");
			
			/*
			 * Count steady state frequencies
			 */
			VDataTable vt1 = VDatReadWrite.LoadFromVDatFile(folder+"_mutants.dat");
			HashMap<String, Integer> counts = new HashMap<String, Integer>();
			for(int i=0;i<vt1.rowCount;i++){
				String fp = vt1.stringTable[i][vt1.fieldNumByName("FP_STRING")];
				count = 0;
				if(counts.get(fp)!=null) count = counts.get(fp);
				counts.put(fp, count+1);
			}
			vt1.addNewColumn("COUNT", "", "", vt1.NUMERICAL, "1");
			for(int i=0;i<vt1.rowCount;i++){
				String fp = vt1.stringTable[i][vt1.fieldNumByName("FP_STRING")];
				vt1.stringTable[i][vt1.fieldNumByName("COUNT")] = ""+counts.get(fp);
				vt1.stringTable[i][vt1.fieldNumByName("FP_STRING")] = "_"+fp;
			}
			VDatReadWrite.saveToVDatFile(vt1,folder+"_mutants_count.dat");
			VDatReadWrite.saveToSimpleDatFile(vt1, folder+"_mutants_count.txt",false);
			
			Iterator<String> it = counts.keySet().iterator();
			while(it.hasNext()){
				String s = it.next();
				System.out.println("_"+s+"\t"+counts.get(s));
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}	
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
	
	
	public void extractFixedPoints(String fn){
		Vector<String> lines = Utils.loadStringListFromFile(fn);
		for(String s: lines){
			StringTokenizer st = new StringTokenizer(s,"\t: ");
			while(st.hasMoreTokens()){
				String n = st.nextToken();
				if(n.equals("Node")){
					String nodeName = st.nextToken();
					if(phenotypes.contains(nodeName))
						nodeName = "0_"+nodeName;
					if(!nodeNames.contains(nodeName))
						nodeNames.add(nodeName);
				}
			}
		}
		Collections.sort(nodeNames);
		Vector<Integer> fixedPoint = null;
		for(String s: lines){
			StringTokenizer st = new StringTokenizer(s,"\t: ");
			while(st.hasMoreTokens()){
				String n = st.nextToken();
				if(n.equals("FP")){
					if(fixedPoint!=null){
						fixedPoints.add(fixedPoint);
					}
					fixedPoint = new Vector<Integer>();
					for(int i=0;i<nodeNames.size();i++)
						fixedPoint.add(0);
				}
				if(n.equals("Node")){
					String nodeName = st.nextToken();
					if(phenotypes.contains(nodeName))
						nodeName = "0_"+nodeName;
					Integer value = Integer.parseInt(st.nextToken());
					fixedPoint.set(nodeNames.indexOf(nodeName), value);
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

}
