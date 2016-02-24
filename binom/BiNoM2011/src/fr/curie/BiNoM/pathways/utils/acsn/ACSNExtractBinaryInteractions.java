package fr.curie.BiNoM.pathways.utils.acsn;

import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.sbml.x2001.ns.celldesigner.SbmlDocument;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.cytoscape.analysis.ConvertReactionNetworkToEntityNetwork;
import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.analysis.structure.Attribute;
import fr.curie.BiNoM.pathways.analysis.structure.BiographUtils;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.analysis.structure.StructureAnalysisUtils;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class ACSNExtractBinaryInteractions {
	
	public class interaction_record{
		public String id = null;
		public String interactor1 = null;
		public String interactor2 = null;
		public Vector<String> interactionTypes = new Vector<String>();
		public Vector<String> pmids = new Vector<String>();
		public interaction_record(String n1, String n2, String types, String pmds){
			id = n1+"_interacts_"+n2;
			if(n1.compareTo(n2)>0){
				//String s = n1;
				//n1 = n2;
				//n2 = s;
				id = n2+"_interacts_"+n1;
			}
			//id = n1+"_interacts_"+n2;
			interactor1 = n1;
			interactor2 = n2;
			StringTokenizer st1 = new StringTokenizer(types,"@;");
			while(st1.hasMoreTokens()) interactionTypes.add(st1.nextToken());
			StringTokenizer st2 = new StringTokenizer(pmds,"@;");
			while(st2.hasMoreTokens()) pmids.add(st2.nextToken());
		}
		public String toString(){
			Collections.sort(interactionTypes);
			Collections.sort(pmids);
			String res = "";
			res+=interactor1+"\t";
			for(String s:interactionTypes) res+=s+";"; if(interactionTypes.size()>0) res=res.substring(0, res.length()-1);
			res+="\t"+interactor2+"\t";
			for(String s:pmids) res+=s+";"; if(pmids.size()>0) res=res.substring(0, res.length()-1);
			return res;
		}
		public void merge(interaction_record rd){
			for(String s:rd.interactionTypes) if(!interactionTypes.contains(s)) interactionTypes.add(s); 
			for(String s:rd.pmids) if(!pmids.contains(s)) pmids.add(s);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			MergeParts("C:/Datas/acsn/downloads/acsn_ppi/"); System.exit(0);
			
			//String folder = "C:/Datas/acsn/assembly/acsn_src/";
			String folder = "C:/Datas/acsn/downloads/ppi_test/";
			//String fn = "acsn_master.xml";
			//String fn = "cellcycle_master";
			//String fn = "dnarepair_master";
			//String fn = "apoptosis_master";
			//String fn = "emtcellmotility_master";
			String fn = "survival_master";
			//String fn = "test1";
			
			CellDesigner cd = new CellDesigner();
			SbmlDocument sbml = cd.loadCellDesigner(folder+fn+".xml");
			CellDesigner.entities = CellDesigner.getEntities(sbml);		
			GraphDocument graph = CellDesignerToCytoscapeConverter.getXGMMLGraph(fn+"_ppi", sbml.getSbml());
			System.out.println("Converting to graph...");
			Graph gr = XGMML.convertXGMMLToGraph(graph);

			//Extract name to HUGO correspondence
			Vector<String> vhugos = Utils.loadStringListFromFile(folder+"names.gmt");
			HashMap<String, Vector<String>> hugos = new HashMap<String, Vector<String>>();
			Vector<String> foundEntities = new Vector<String>();
			for(String line: vhugos){
				StringTokenizer st = new StringTokenizer(line,"\t");
				String name = st.nextToken();
				String description = st.nextToken();
				Vector<String> hs = hugos.get(name);
				if(hs==null) hs = new Vector<String>();
				while(st.hasMoreTokens()){
					String hugo = st.nextToken();
					if(!hs.contains(hugo))
						hs.add(hugo);
				}
				if(hs.size()>0) hugos.put(name, hs);
			}
			System.out.println(hugos.size()+" entities found");
			
			System.out.println("Saving...");
			XGMML.saveToXGMML(gr, folder+fn+".xgmml");
			
			ConvertReactionNetworkToEntityNetwork crn = new ConvertReactionNetworkToEntityNetwork();
			System.out.println("Converting to binary network...");
			Graph grres = BiographUtils.convertReactionNetworkIntoEntityNetwork(gr);
			
			System.out.println("Removing reciprocal edges...");
			grres = removeReciprocalEdgesForACSN(grres);
			
			System.out.println("Saving...");
			XGMML.saveToXGMML(grres, folder+fn+"_ppi.xgmml");
			
			Vector<String> allpmids_cd = new Vector<String>();
			Vector<String> allpmids_graph = new Vector<String>();
			Vector<String> pmids_documented = new Vector<String>();
			
			// Find all pmids in cd and in the graph
			System.out.println("Extracting pmids from CD...");
			Vector<String> lines = Utils.loadStringListFromFile(folder+fn+".xml");
			for(String s: lines){
				StringTokenizer st = new StringTokenizer(s,":, ;.");
				while(st.hasMoreTokens()){
					String token = st.nextToken();
					if(token.toUpperCase().equals("PMID"))if(st.hasMoreTokens()){
						String pmid = st.nextToken();
						pmid = filterNonNumeric(pmid);
						if(!allpmids_cd.contains(pmid))
							allpmids_cd.add(pmid);
					}
				}
			}
			System.out.println("Extracting pmids from graph...");
			for(Node n: gr.Nodes){
				Vector<String> pmids_atts = n.getAttributeValues("PMID");
				Vector<String> pmids = new Vector<String>();
				for(String att: pmids_atts){
					String pms[] = att.split("@");
					for(String s:pms)if(!s.equals("")){
						if(s.endsWith(",")||s.endsWith(";")||s.endsWith(".")) s = s.substring(0, s.length()-1);
						s = filterNonNumeric(s);
							if(!allpmids_graph.contains(s)){
								if(!allpmids_cd.contains(s))
									System.out.println("PMID:"+s+" found in graph but not in cd");
								allpmids_graph.add(s);
							}
					}
				}
			}
			
			
			FileWriter fw = new FileWriter(folder+fn+"_ppi.sif");
			FileWriter fw1 = new FileWriter(folder+fn+"_ppi_documented.txt");
			fw1.write("INTERACTOR1\tINTERACTION_TYPE\tINTERACTOR2\tPMID\n");
			System.out.println("Writing files...");
			for(int i=0;i<grres.Edges.size();i++){
				Edge e = grres.Edges.get(i);
				//String interaction = e.getFirstAttributeValue("interaction");
				String interaction = e.getFirstAttributeValue("CELLDESIGNER_EDGE_TYPE");
				interaction = Utils.replaceString(interaction, "INTERSECTION", "HETERODIMER_ASSOCIATION");
				Node n1 = e.Node1;
				Node n2 = e.Node2;
				
				if(getHugos(n1.Id,hugos).size()>0)if(getHugos(n2.Id,hugos).size()>0){
				
				if(!foundEntities.contains(n1.Id)) foundEntities.add(n1.Id);				
				if(!foundEntities.contains(n2.Id)) foundEntities.add(n2.Id);
					
				//if(n2.Id.equals("CyclinD*"))if(n1.Id.equals("p27KIP1*"))
				fw.write(n1.Id+"\t"+interaction+"\t"+n2.Id+"\n");
				fw1.write(n1.Id+"\t"+interaction+"\t"+n2.Id+"\t");
				Vector<String> pmids_atts = e.getAttributeValues("PMID");
				Vector<String> pmids = new Vector<String>();
				for(String att: pmids_atts){
					String pms[] = att.split("@");
					for(String s:pms)if(!s.equals("")){
						if(s.endsWith(",")) s = s.substring(0, s.length()-1);
						s = filterNonNumeric(s);
						if(!pmids.contains(s)){
							pmids.add(s);
							if(!pmids_documented.contains(s)) pmids_documented.add(s);
						}
					}
				}
				Collections.sort(pmids);
				String pmid = "";
				for(String s: pmids) if(!s.equals("")) pmid+=s+";";
				if(pmid.length()>0) pmid = pmid.substring(0,pmid.length()-1);
				if(pmid.equals("")) pmid = "N/A";
				fw1.write(pmid+"\n");
				}
			}
			fw.close();
			fw1.close();
			
			Collections.sort(pmids_documented);
			//for(String s: pmids_documented)
			//	System.out.println(s);
			System.out.println("PMIDs not documented");
			int k=1;
			for(String s: allpmids_cd) 
				if(!pmids_documented.contains(s))
					System.out.println((k++)+":"+s);
			System.out.println("Documented PMIDs: "+pmids_documented.size()+" from "+allpmids_cd.size()+" found in CellDesigner file and "+allpmids_graph.size()+" found in the graph.");
			
			//Create new gmt file with good entity names
			FileWriter fwh = new FileWriter(folder+fn+".gmt");
			for(String s: foundEntities){
				Vector<String> hgs = getHugos(s,hugos);
				fwh.write(s+"\tna\t");
				for(String hg: hgs)
					fwh.write(hg+"\t");
				fwh.write("\n");
			}
			fwh.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	  public static Graph removeReciprocalEdgesForACSN(Graph graph){
		  Graph gr = graph;
		  float connectivity[] = new float[graph.Nodes.size()];
		  gr.calcNodesInOut();
		  for(int i=0;i<graph.Nodes.size();i++){
			  connectivity[i] = 0f+graph.Nodes.get(i).incomingEdges.size()+graph.Nodes.get(i).outcomingEdges.size();
		  }
		  int ind[] = Utils.SortMass(connectivity);
		  for(int i=0;i<ind.length;i++){
			  Node n = graph.Nodes.get(ind[i]);
			  gr.calcNodesInOut();
			  for(int j=0;j<n.incomingEdges.size();j++)for(int k=0;k<n.outcomingEdges.size();k++){
				  Edge ej = n.incomingEdges.get(j);
				  Edge ek = n.outcomingEdges.get(k);
				  if(ej.Node1==ek.Node2)if(ej.Node2==ek.Node1){

					  //if(ej.Node2.Id.equals("APC1*"))if(ej.Node1.Id.equals("CyclinA1*"))
					  //	  System.out.println();
					  
					     String tpk = ek.getFirstAttributeValue("CELLDESIGNER_EDGE_TYPE");
					     String tpj = ej.getFirstAttributeValue("CELLDESIGNER_EDGE_TYPE");
					     if(tpj!=null)if(tpk!=null){
					     tpj = mergeTypes(tpj,tpk);
					     if(tpj!=null)
					    	 ej.setAttributeValueUnique("CELLDESIGNER_EDGE_TYPE", tpj, fr.curie.BiNoM.pathways.analysis.structure.Attribute.ATTRIBUTE_TYPE_STRING);
					     	 Vector<String> pmids = ek.getAttributeValues("PMID");
					     	 for(String pmid: pmids)
					     		 //ej.Attributes.add(new Attribute("PMID",pmid));
					     		 BiographUtils.updatePMIDs(ej, pmid);
					     }
						 gr.removeEdge(ek.Id);
				  }
			  }
			  gr.calcNodesInOut();
			  for(int j=0;j<n.incomingEdges.size();j++)for(int k=j+1;k<n.incomingEdges.size();k++){
				  Edge ej = n.incomingEdges.get(j);
				  Edge ek = n.incomingEdges.get(k);	
				  if(ej.Node1==ek.Node1)if(ej.Node2==ek.Node2){
					  
					 // if(ej.Node2.Id.equals("APC1*"))if(ej.Node1.Id.equals("CyclinA1*"))
					 //	  System.out.println();
					  
					     String tpk = ek.getFirstAttributeValue("CELLDESIGNER_EDGE_TYPE");
					     String tpj = ej.getFirstAttributeValue("CELLDESIGNER_EDGE_TYPE");
					     if(tpj!=null)if(tpk!=null){
					    	 tpj = mergeTypes(tpj,tpk);
					    	 if(tpj!=null)
					    		 ej.setAttributeValueUnique("CELLDESIGNER_EDGE_TYPE", tpj, fr.curie.BiNoM.pathways.analysis.structure.Attribute.ATTRIBUTE_TYPE_STRING);
					    	 	 Vector<String> pmids = ek.getAttributeValues("PMID");
					    	 	 for(String pmid: pmids)
					    	 		 //ej.Attributes.add(new Attribute("PMID",pmid));
					    	 		BiographUtils.updatePMIDs(ej, pmid);
					     }
						 gr.removeEdge(ek.Id);
				  }
			  }
		  }
		  return gr;
	  }
	  
	  public static String mergeTypes(String tpj,String tpk){
		  String tp = "";
		  tpj = Utils.replaceString(tpj, "INTERSECTION", "HETERODIMER_ASSOCIATION");
		  tpk = Utils.replaceString(tpk, "INTERSECTION", "HETERODIMER_ASSOCIATION");
		  String tpjm[] = tpj.split("@");
		  String tpkm[] = tpk.split("@");
		  Vector<String> tps = new Vector<String>();
		  for(String s: tpjm){ if(s!=null)if(!tps.contains(s)) tps.add(s); }
		  for(String s: tpkm){ if(s!=null)if(!tps.contains(s)) tps.add(s); }
		  Collections.sort(tps);
		  for(String s: tps) tp+=s+"@";
		  if(tp.length()>0) tp = tp.substring(0, tp.length()-1);
		  return tp;
	  }
	  
	  public static String filterNonNumeric(String s){
		  char sc[] = s.toCharArray();
		  String number = "";
		  for(int i=0;i<sc.length;i++){
			  if(sc[i]>='0')if(sc[i]<='9')
				  number+=sc[i];
		  }
		  return number;
	  }
	  
	  public static Vector<String> getHugos(String id, HashMap<String,Vector<String>> hugos){
		  Vector<String> found = new Vector<String>();
		  if(hugos.get(id)!=null){
			  Vector<String> hgs = hugos.get(id);
			  for(String s: hgs) if(!s.equals("")) found.add(s);
		  }
		  if(hugos.get("g"+id)!=null){
			  Vector<String> hgs = hugos.get("g"+id);
			  for(String s: hgs) if(!s.equals("")) if(!found.contains(s)) found.add(s);
		  }
		  if(hugos.get("r"+id)!=null){
			  Vector<String> hgs = hugos.get("r"+id);
			  for(String s: hgs) if(!s.equals("")) if(!found.contains(s)) found.add(s);
		  }
		  if(hugos.get("ar"+id)!=null){
			  Vector<String> hgs = hugos.get("ar"+id);
			  for(String s: hgs) if(!s.equals("")) if(!found.contains(s)) found.add(s);
		  }
		  return found;
	  }
	  
	  public static void MergeParts(String folder) throws Exception{
		  File files[] = (new File(folder)).listFiles();
		  HashMap<String, Vector<String>> hugos = new HashMap<String, Vector<String>>();
		  HashMap<String, interaction_record> interactions = new HashMap<String, interaction_record>();
		  for(File f: files){
			  if(f.getName().endsWith(".gmt")){
				  Vector<String> lines = Utils.loadStringListFromFile(f.getAbsolutePath());
				  for(String s: lines){
					  StringTokenizer st = new StringTokenizer(s,"\t");
					  String name = st.nextToken();
					  String description = st.nextToken();
					  Vector<String> hgs = hugos.get(name);
					  if(hgs==null) hgs = new Vector<String>();
					  while(st.hasMoreTokens()){
						  String hg = st.nextToken();
						  if(!hg.equals("")) if(!hgs.contains(hg)) hgs.add(hg);
					  }
					  hugos.put(name, hgs);
				  }
			  }
			  if(f.getName().endsWith(".txt")){
				  Vector<String> lines = Utils.loadStringListFromFile(f.getAbsolutePath());
				  for(String s: lines){
					  StringTokenizer st = new StringTokenizer(s,"\t");
					  String n1 = st.nextToken();
					  String types = st.nextToken();
					  String n2 = st.nextToken();
					  String pmids = st.nextToken();
					  interaction_record rd = new ACSNExtractBinaryInteractions().new interaction_record(n1,n2,types,pmids);
					  interaction_record rd1 = interactions.get(rd.id);
					  if(rd1!=null){
						  rd.merge(rd1);
					  }
					  interactions.put(rd.id, rd);
				  }
			  }
		  }
		  // Write new hugos
		  FileWriter fwh = new FileWriter(folder+"acsn_names.gmt");
		  Set<String> keys = hugos.keySet();
		  for(String s: keys){
			  fwh.write(s+"\tna\t");
			  Vector<String> hgs = hugos.get(s);
			  for(String hg:hgs) fwh.write(hg+"\t");
			  fwh.write("\n");
		  }
		  fwh.close();
		  // Write new interactions
		  FileWriter fwi = new FileWriter(folder+"acsn_ppi.txt");
		  fwi.write("INTERACTOR1\tINTERACTION_TYPE\tINTERACTOR2\tPMID\n");
		  keys = interactions.keySet();
		  for(String s: keys){
			  interaction_record rd = interactions.get(s);
			  fwi.write(rd.toString());
			  fwi.write("\n");
		  }
		  fwi.close();
	  }


}
