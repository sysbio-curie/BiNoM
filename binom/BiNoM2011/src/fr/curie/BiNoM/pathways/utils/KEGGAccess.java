package fr.curie.BiNoM.pathways.utils;

import java.io.*;
import java.util.*;

import vdaoengine.data.VDataTable;
import vdaoengine.data.io.VDatReadWrite;

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
			String organism = "Rat";
			String organism_prefix = "rno";
			String prefix = "C:/Datas/KEGG/"+organism+"/";
			String file = "pathway_list";

			Vector<String> pathwayList = Utils.loadStringListFromFile(prefix+file);
			
			
			String gmt = loadKEGGasGMTString(pathwayList,organism_prefix);
			FileWriter fw = new FileWriter(prefix+file+"_KEGGID");
			fw.write(gmt);
			fw.close();
			
			
			VDataTable vt = VDatReadWrite.LoadFromSimpleDatFile("C:/Datas/KEGG/"+organism+"/NCBI_Gene_ID2KEGGID_"+organism	+".txt", true, "\t");
			vt.makePrimaryHash("KEGGID");
			fw = new FileWriter(prefix+file+"_KEGG_NAME");
			Vector<String> list = Utils.loadStringListFromFile(prefix+file+"_KEGGID");
			for(String s:list){
				String parts[] = s.split("\t");
				fw.write(parts[0]+"\t"+parts[1]+"\t");
				for(int i=2;i<parts.length;i++){
					String keggid = parts[i];
					String name = "unknown_"+keggid;
					if(vt.tableHashPrimary.get(keggid)!=null){
						name = vt.stringTable[vt.tableHashPrimary.get(keggid).get(0)][vt.fieldNumByName("SYMBOL")];
					}
					fw.write(name+"\t");
				}
				fw.write("\n");
			}
			fw.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String loadKEGGasGMTString(Vector<String> listOfPathwayIDs, String organism_prefix) throws Exception{
		String gmt = "";
		for(int i=0;i<listOfPathwayIDs.size();i++){
			String pathway = listOfPathwayIDs.get(i);
			String organism = pathway.substring(0,3);
			String pathwayID = pathway.substring(3,pathway.length());
			Vector<String> genes = getGenesFromKEGGPathway(organism,pathwayID,organism_prefix);
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
	
	
	public static Vector<String> getGenesFromKEGGPathway(String organism, String pathway, String organism_prefix) throws Exception{
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
				String s0 = s.substring(0,s.indexOf("a>"));
				String KEGGID = "unknown";
				StringTokenizer st0 = new StringTokenizer(s0,"<>");
				while(st0.hasMoreTokens()){
					String temp = st0.nextToken();
					if(temp.startsWith(organism_prefix+":")) KEGGID = temp;//.substring(4, temp.length());
				}
				//System.out.println(s);
				/*s = s.substring(s.indexOf("</a>")+4,s.length());
				System.out.println(s);
				StringTokenizer st = new StringTokenizer(s,"\t ,;|()");
				//String hugo = st.nextToken();
				String hugo = "unknown_"+KEGGID;
				while(st.hasMoreTokens()){
					if(st.nextToken().equals("RefSeq")){
						if(st.hasMoreTokens())
							hugo = st.nextToken();
					}
				}*/
				genes.add(KEGGID);
				//name2KEGGID.put(hugo, KEGGID);
			}
		}
		return genes;
	}
	

}
