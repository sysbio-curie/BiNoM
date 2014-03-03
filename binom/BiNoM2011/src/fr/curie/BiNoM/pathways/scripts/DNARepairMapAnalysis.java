package fr.curie.BiNoM.pathways.scripts;

import java.util.Random;
import java.util.Vector;

import vdaoengine.utils.Utils;

import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.utils.SetOverlapAnalysis;
import fr.curie.BiNoM.pathways.utils.SimpleTable;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class DNARepairMapAnalysis {

	/**
	 * @param args
	 */
	
	public static int NodeSize = 30;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			SetOverlapAnalysis so = new SetOverlapAnalysis();
			String folder = "C:/Datas/DNARepairAnalysis/ver3/";
			String prefix = "paths_all";
			String mapFileName = "dnarepair_master_12122013_nophenotype.xgmml";
			String stgFileName = "STG.xgmml";
			int order = 3;
			
			// Process the paths file (extracted from the OCSANA report)
			//so.createGMTFromOCSANAOutput(folder+"ocsana_report");
			//System.exit(0);
			
			Graph stg = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(folder+stgFileName));

			// Extract regulators along the paths as gmt file with "reg" suffix
			so.LoadSetsFromGMT(folder+prefix+".gmt");
			RenamePaths(so, stg);
			so.saveSetsAsGMT(folder+prefix+"1.gmt", Integer.MAX_VALUE, true);
			
			String typesOfPositiveRegulations[] = new String[]{"CATALYSIS","TRIGGER","MODULATION","PHYSICAL_STIMULATION","UNKNOWN_CATALYSIS"};
			String typesOfNegativeRegulations[] = new String[]{"INHIBITION","UNKNOWN_INHIBITION"};
			Graph graph = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(folder+mapFileName));
			so.makeGMTOfReactionRegulators(folder+prefix+"_reg"+order, graph, typesOfPositiveRegulations, typesOfNegativeRegulations, order);
			
			Graph gr = ProduceSTGRegulatorsGraph(stg, folder+prefix+"_reg"+order+"_protein_reaction.txt");
			XGMML.saveToXGMML(gr, folder+prefix+"_reg"+order+"_protein_reaction.xgmml");
			
			// Now find all hit sets
			so.LoadSetsFromGMT(folder+prefix+"_reg"+order+".gmt");
			so.findMinimalHittingSet(3, folder+prefix+"_reg"+	order);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public static void RenamePaths(SetOverlapAnalysis so, Graph stg){
		String exceptionlabels[] = {"gBER:M","gBER:B","galtNHEJ:","gFanconi"};
		String replacelabels[] = {"BERM","BERB","ANHEJ","FANC"};
		stg.calcNodesInOut();
		Vector<Vector<String>> lists = new Vector<Vector<String>>();
		for(int i=0;i<so.setdescriptions.size();i++){
			String desc = so.setdescriptions.get(i);
			Vector<String> l = new Vector<String>();
			String ls[] = desc.split(">");
			for(String s: ls){
				if(s.endsWith("-")) s = s.substring(0,s.length()-1);
				s = Utils.replaceString(s, "*Nucleus", "*@Nucleus");
				l.add(s);
			}
			lists.add(l);
		}
		
		//for(Node n: stg.Nodes) System.out.println("Node: "+n.Id);
		
		
		for(int i=0;i<lists.size();i++){
			Vector<String> list = lists.get(i);
			String newName = "";
			String oldlabel = "";
			for(int k=0;k<list.size()-1;k++){
				String s = list.get(k);
				if(!s.contains("st0")){
					Node n = stg.getNode(s);
					if(n!=null)
					if((n.getFirstAttributeValue("CELLDESIGNER_REACTION")==null)||(n.getFirstAttributeValue("CELLDESIGNER_REACTION").equals(""))){
						int degree = n.outcomingEdges.size();
						//if(true||(degree>1)||(s.contains("st1"))){
						if(true){
							String label = s.split(":")[0];
							if(label.startsWith("g")) label = label.substring(1,label.length());
							for(int j=0;j<exceptionlabels.length;j++)
								if(s.startsWith(exceptionlabels[j]))
									label = replacelabels[j];
							if(!oldlabel.equals(label)){
								newName+=label+"_";
							}
							oldlabel = label;
						}
					}
				}
			}
		if(newName.endsWith("_")) newName = newName.substring(0,newName.length()-1);
		so.setnames.set(i, newName);
		}
	}
	
	public static Graph ProduceSTGRegulatorsGraph(Graph stg, String tableName){
		SimpleTable st = new SimpleTable();
		Random r = new Random();
		st.LoadFromSimpleDatFile(tableName, true, "\t");
		for(int i=0;i<st.rowCount;i++){
			float x = 0f;
			float y = 0f;
			int num = 0;
			String name = st.stringTable[i][0];
			Node node = stg.getCreateNode(name);
			node.setAttributeValueUnique("CELLDESIGNER_NODE_TYPE", st.stringTable[i][1], Attribute.ATTRIBUTE_TYPE_STRING);
			
			for(int j=1;j<st.colCount;j++){
				String re = st.fieldNames[j];
				Node n = stg.getNode(re);
				if(n!=null){
					int sign = Integer.parseInt(st.stringTable[i][j]);
					if(sign!=0){
						num++;
						String type = "CATALYSIS";
						if(sign<0)
							type = "INHIBITION";
						float nx = n.x;
						float ny = n.y;
						x+=nx;
						y+=ny;
						
						Edge e = stg.getCreateEdge(Utils.correctName(name)+"_"+re);
						e.setAttributeValueUnique("CELLDESIGNER_EDGE_TYPE", type, Attribute.ATTRIBUTE_TYPE_STRING);
						e.Node1 = node;
						e.Node2 = n;
					}
				}
			}
			x/=num;
			y/=num;
			float scale = 3f;
			float dx = (scale*r.nextFloat()-scale/2f)*NodeSize;
			float dy = (scale*r.nextFloat()-scale/2f)*NodeSize;
			node.x = x+dx;
			node.y = y+dy;
			
		}
		stg.name = stg.name+"_regulators";
		return stg;
	}

}
