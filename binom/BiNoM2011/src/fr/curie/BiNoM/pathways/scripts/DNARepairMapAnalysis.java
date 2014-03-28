package fr.curie.BiNoM.pathways.scripts;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;
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
			String folder = "C:/Datas/DNARepairAnalysis/ver5/";
			String prefix = "paths_all";
			String mapFileName = "dnarepair_master_10032014_nophenotype.xgmml";
			String stgFileName = "STG.xgmml";
			int order = 2;
			
			// Process the paths file (extracted from the OCSANA report)
			//so.createGMTFromOCSANAOutput(folder+"ocsana_report");
			//System.exit(0);
			
			Graph stg = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(folder+stgFileName));
			Graph graph = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(folder+mapFileName));
			String typesOfPositiveRegulations[] = new String[]{"CATALYSIS","TRIGGER","MODULATION","PHYSICAL_STIMULATION","UNKNOWN_CATALYSIS"};
			String typesOfNegativeRegulations[] = new String[]{"INHIBITION","UNKNOWN_INHIBITION"};

			CheckComplexFormationConsistency(graph);
			
			//ExtractReactionsFromModules(stg, folder+"module_reactions.gmt");
			
			//so.LoadSetsFromGMT(folder+"module_reactions.gmt");
			//so.makeGMTOfReactionRegulators(folder+"module_reactions_reg"+order, graph, typesOfPositiveRegulations, typesOfNegativeRegulations, order, true);
			//so.expandSetsOfLists_ExpandSets(folder+"module_reactions_reg"+order+".gmt", folder+"dnarepair_master_10032014.xml.gmt", folder+"module_reactions_reg"+order+"_HUGO.gmt");
			
			//so.LoadSetsFromGMT(folder+"dnarepair_master_10032014.gmt");
			//so.assignWeightsFromAnotherWeightedGMT(folder+"module_reactions_reg3_HUGO.gmt");
			//so.saveSetsAsGMT(folder+"dnarepair_master_10032014_signed.gmt", Integer.MAX_VALUE);
			
			System.exit(0);
			
			
			// Extract regulators along the paths as gmt file with "reg" suffix
			so.LoadSetsFromGMT(folder+prefix+".gmt");
			RenamePaths(so, stg);
			so.saveSetsAsGMT(folder+prefix+"1.gmt", Integer.MAX_VALUE, true);
			
			so.makeGMTOfReactionRegulators(folder+prefix+"_reg"+order, graph, typesOfPositiveRegulations, typesOfNegativeRegulations, order, false);
			
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
	
	public static void ExtractReactionsFromModules(Graph gr, String fn) throws Exception{
		gr.calcNodesInOut();
		FileWriter fw = new FileWriter(fn);
		HashMap<String, Vector<String>> moduleReactions = new HashMap<String, Vector<String>>(); 
		for(int i=0;i<gr.Nodes.size();i++){
			Node n = gr.Nodes.get(i);
			String reactionId = n.getFirstAttributeValue("CELLDESIGNER_REACTION");
			if(!n.Id.startsWith("gIntact"))
			if((reactionId==null)||(reactionId.trim().equals(""))){
			String moduleString = n.getFirstAttributeValue("MODULE");
			//System.out.println(n.Id+"\t"+moduleString);
			StringTokenizer st = new StringTokenizer(moduleString,"@");
			while(st.hasMoreTokens()){
				String module = st.nextToken();
				if(!module.trim().equals("")){
				Vector<String> reactions = moduleReactions.get(module);
				if(reactions==null){
					reactions = new Vector<String>();
					moduleReactions.put(module, reactions);
				}
				for(int j=0;j<n.outcomingEdges.size();j++){
					Edge e = n.outcomingEdges.get(j);
					String reaction = e.Node2.Id;
					if(!reactions.contains(reaction))
						reactions.add(reaction);
				}
				}
			}
			}
		}
		for(String module: moduleReactions.keySet()){
			fw.write(module+"\tna\t");
			Vector<String> reactions = moduleReactions.get(module);
			for(String reaction: reactions){
				fw.write(reaction+"\t");
			}
			fw.write("\n");
		}
		fw.close();
	}
	
	public static Vector<String> CheckComplexFormationConsistency(Graph graph){
		Vector<String> inconsistentReactions = new Vector<String>();
		graph.calcNodesInOut();
		for(Node n: graph.Nodes){
			String reactionId = n.getFirstAttributeValue("CELLDESIGNER_REACTION");
			String tp = n.getFirstAttributeValue("CELLDESIGNER_NODE_TYPE");
			if(reactionId!=null)if(!reactionId.equals(""))if(tp.equals("HETERODIMER_ASSOCIATION")){
				Vector<String> complexComponents = new Vector<String>();
				Vector<String> reactants = new Vector<String>();
				String complex = null;
				for(Edge e: n.outcomingEdges)if(e.getFirstAttributeValue("CELLDESIGNER_EDGE_TYPE").equals("RIGHT")){
					complex = e.Node2.Id;
					complexComponents = BiographUtils.extractProteinNamesFromNodeName(e.Node2.Id);
				}
				for(Edge e: n.incomingEdges)if(e.getFirstAttributeValue("CELLDESIGNER_EDGE_TYPE").equals("LEFT")){
					String reactant = e.Node1.Id;
					Vector<String> ps = BiographUtils.extractProteinNamesFromNodeName(e.Node1.Id);
					for(String s: ps) if(!reactants.contains(s)) reactants.add(s);
				}
				// check if all reactants appear in complex
				for(String reactant: reactants){
					if(!complexComponents.contains(reactant)){
						System.out.println("ERROR: reaction "+n.Id+": reactant "+reactant+" is not present in the complex "+complex);
						if(!inconsistentReactions.contains(n.Id)) inconsistentReactions.add(n.Id);
					}
				}
				// check if all complex components appear in reactants
				for(String component: complexComponents){
					if(!reactants.contains(component)){
						System.out.println("ERROR: reaction "+n.Id+": component "+component+" is not present among reactants.");
						if(!inconsistentReactions.contains(n.Id)) inconsistentReactions.add(n.Id);						
					}
				}
			}
		}
		return inconsistentReactions;
	}

}
