package fr.curie.BiNoM.pathways.utils;

import java.io.*;
import java.util.*;

public class KEGGAccess {

	/**
	 * @param args
	 */
	
	public static String KEGGgenesPrefix = "http://www.genome.jp/dbget-bin/get_linkdb?-t+genes+path:";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			//String prefix = "C:/Datas/KEGG/Yeast/";
			//String file = "pathway_list";
			String prefix = "C:/Datas/KEGG/ZebraFish/";
			String file = "pathway_list";

			Vector<String> pathwayList = Utils.loadStringListFromFile(prefix+file);
			String gmt = loadKEGGasGMTString(pathwayList);
			FileWriter fw = new FileWriter(prefix+file+"_KEGG");
			fw.write(gmt);
			fw.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String loadKEGGasGMTString(Vector<String> listOfPathwayIDs) throws Exception{
		String gmt = "";
		for(int i=0;i<listOfPathwayIDs.size();i++){
			String pathway = listOfPathwayIDs.get(i);
			String organism = pathway.substring(0,3);
			String pathwayID = pathway.substring(3,pathway.length());
			Vector<String> genes = getGenesFromKEGGPathway(organism,pathwayID);
			System.out.println(""+(i+1)+")"+pathway+"\t"+organism+"\t"+pathwayID+"\t"+genes.size());
			if(genes.size()>1){
			gmt+=pathway+"\tna\t";
			for(int j=0;j<genes.size();j++){
				gmt+=genes.get(j)+"\t";
			}
			gmt+="\n";
			}   
		}
		return gmt;
	}
	
	
	public static Vector<String> getGenesFromKEGGPathway(String organism, String pathway) throws Exception{
		Vector<String> genes = new Vector<String>();
		//System.out.println(KEGGgenesPrefix+organism+pathway);
		String page = Utils.downloadURL(KEGGgenesPrefix+organism+pathway);
		//System.out.println(page);
		LineNumberReader lr = new LineNumberReader(new StringReader(page));
		String s = null;
		while((s=lr.readLine())!=null){
			/*// This worked for yeast 
			StringTokenizer st = new StringTokenizer(s,"<>");
			while(st.hasMoreTokens()){
				String token = st.nextToken();
				if(token.startsWith(organism+":")){
					String gene = token.substring(organism.length()+1,token.length());
					genes.add(gene);
				}
			}*/
			// This is for human (HUGO)
			if(s.contains("</a>"))if(s.contains("/dbget-bin/")){
				s = s.substring(s.indexOf("</a>")+4,s.length());
				//System.out.println(s);
				StringTokenizer st = new StringTokenizer(s,"\t ,;");
				String hugo = st.nextToken();
				genes.add(hugo);
			}
		}
		return genes;
	}
	

}
