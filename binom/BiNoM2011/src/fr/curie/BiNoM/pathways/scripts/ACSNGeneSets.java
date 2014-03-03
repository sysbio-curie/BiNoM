package fr.curie.BiNoM.pathways.scripts;

import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import org.sbml.x2001.ns.celldesigner.SbmlDocument;

import vdaoengine.utils.Utils;

import fr.curie.BiNoM.pathways.analysis.structure.BiographUtils;
import fr.curie.BiNoM.pathways.analysis.structure.BiographUtils.ReactionRegulator;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.utils.SetOverlapAnalysis;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class ACSNGeneSets {
	
	public static String typesOfPositiveRegulations[] = new String[]{"CATALYSIS","TRIGGER","MODULATION","PHYSICAL_STIMULATION","UNKNOWN_CATALYSIS"};
	public static String typesOfNegativeRegulations[] = new String[]{"INHIBITION","UNKNOWN_INHIBITION"};


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			//String prefix = "C:/Datas/DNARepairAnalysis/ver3/";
			//String filename = "dnarepair_master_12122013";
			//String shortname = "dnr";
			String prefix = "C:/Datas/acsn/genesets/";
			String filename = "acsn";
			String shortname = "acsn";
			
			
			CellDesigner cd = new CellDesigner();
			//SbmlDocument sbml = cd.loadCellDesigner(prefix+filename+".xml");
			Graph reactiongraph = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(prefix+filename+"_sets.xgmml"));
			//Graph entitygraph = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(prefix+filename+"_entity.xgmml"));
			SetOverlapAnalysis nameHugo = new SetOverlapAnalysis();
			nameHugo.LoadSetsFromGMT(prefix+filename+".gmt");
						
			// All reactions (reactants, products + regulators of the 3rd order)
			
			//ExtractReactions(reactiongraph, prefix+shortname+"_re.gmt", false);
			//(new SetOverlapAnalysis()).expandSetsOfLists_ExpandSets(prefix+shortname+"_re.gmt",prefix+filename+".gmt",prefix+shortname+"_re_hugo.gmt");
			
			// All reactions (only regulators up to the 3rd order)
			
			//ExtractReactions(reactiongraph, prefix+shortname+"_rereg.gmt", true);
			//(new SetOverlapAnalysis()).expandSetsOfLists_ExpandSets(prefix+shortname+"_rereg.gmt",prefix+filename+".gmt",prefix+shortname+"_rereg_hugo.gmt");
			
			// All complex components only
			
			ExtractAllComplexComponents(reactiongraph,prefix+shortname+"_complexes.gmt");
			(new SetOverlapAnalysis()).expandSetsOfLists_ExpandSets(prefix+shortname+"_complexes.gmt",prefix+filename+".gmt",prefix+shortname+"_complexes_hugo.gmt");
			
			// All physical interactions of an entity
			//ExtractAllRegulatorsAndPartnersOfEntity(entitygraph, prefix+shortname+"_entpartn.gmt", true);
			//(new SetOverlapAnalysis()).expandSetsOfLists_ExpandSets(prefix+shortname+"_entpartn.gmt",prefix+filename+".gmt",prefix+shortname+"_entpartn_hugo.gmt");
			
			// All regulators and partners of an entity
			//ExtractAllRegulatorsAndPartnersOfEntity(entitygraph, prefix+shortname+"_entregpartn.gmt", false);
			//(new SetOverlapAnalysis()).expandSetsOfLists_ExpandSets(prefix+shortname+"_entregpartn.gmt",prefix+filename+".gmt",prefix+shortname+"_entregpartn_hugo.gmt");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void ExtractAllRegulatorsAndPartnersOfEntity(Graph entitygraph, String gmtFileName, boolean onlyPhisicalInteractions) throws Exception{
		FileWriter fw = new FileWriter(gmtFileName);
		entitygraph.calcNodesInOut();
		for(int i=0;i<entitygraph.Nodes.size();i++){
			Vector<String> names = new Vector<String>();
			Node n = entitygraph.Nodes.get(i);
			String proteinName = n.Id;
			names.add(proteinName);
			for(Edge e: n.incomingEdges){
				boolean addtolist = onlyPhisicalInteractions;
				String etype = e.getFirstAttributeValue("CELLDESIGNER_EDGE_TYPE");
				if(etype.equals("INTERSECTION"))
					addtolist = true;
				if(!names.contains(e.Node1.Id))
					names.add(e.Node1.Id);
			}
			Collections.sort(names);
			if(onlyPhisicalInteractions)
				fw.write(Utils.correctName(proteinName)+"_partn\tna\t");
			else
				fw.write(Utils.correctName(proteinName)+"_regpartn\tna\t");
			for(String s: names)
				fw.write(s+"\t");
			fw.write("\n");
		}
		fw.close();
	}
	
	public static void ExtractAllComplexComponents(Graph reactiongraph, String gmtFileName) throws Exception{
		FileWriter fw = new FileWriter(gmtFileName);
		Vector<String> complexes = new Vector<String>();
		for(int i=0;i<reactiongraph.Nodes.size();i++){
			Node n = reactiongraph.Nodes.get(i);
			String type = n.getFirstAttributeValue("CELLDESIGNER_NODE_TYPE");
			if(type.equals("COMPLEX")){
				Vector<String> names = BiographUtils.extractProteinNamesFromNodeName(n.Id);
				Collections.sort(names);
				String complexname = "";
				for(String s: names) complexname+=s+":"; if(complexname.endsWith(":")) complexname = complexname.substring(0, complexname.length()-1);
				if(!complexes.contains(complexname))
					complexes.add(complexname);
			}
		}
		for(String compl: complexes){
			fw.write(Utils.correctName(compl)+"_complex\tna\t");
			String comps[] = compl.split(":");
			for(String comp: comps)
				fw.write(comp+"\t");
			fw.write("\n");
		}
			
		fw.close();
	}
	
	public static void ExtractReactions(Graph reactiongraph, String gmtFileName, boolean onlyRegulators) throws Exception{
		FileWriter fw = new FileWriter(gmtFileName);
		reactiongraph.calcNodesInOut();
		for(int i=0;i<reactiongraph.Nodes.size();i++){
			Node n = reactiongraph.Nodes.get(i);
			String reaction = n.getFirstAttributeValue("CELLDESIGNER_REACTION");
			String type = n.getFirstAttributeValue("CELLDESIGNER_NODE_TYPE");
			if(reaction!=null){
				
				Vector<String> reactants_products = new Vector<String>();
				Vector<String> regulators = new Vector<String>();
				
				for(Edge ein: n.incomingEdges){
					String etype = ein.getFirstAttributeValue("CELLDESIGNER_EDGE_TYPE");
					if(etype.equals("LEFT")){
						Vector<String> names = BiographUtils.extractProteinNamesFromNodeName(ein.Node1.Id);
						for(String s: names)
							if(!reactants_products.contains(s))
								reactants_products.add(s);
					}
				}
				for(Edge eout: n.outcomingEdges){
					String etype = eout.getFirstAttributeValue("CELLDESIGNER_EDGE_TYPE");
					if(etype.equals("RIGHT")){
						Vector<String> names = BiographUtils.extractProteinNamesFromNodeName(eout.Node2.Id);
						for(String s: names)
							if(!reactants_products.contains(s))
								reactants_products.add(s);
					}
				}
				Vector<ReactionRegulator> regs = BiographUtils.findReactionRegulators(reactiongraph, reaction, typesOfPositiveRegulations, typesOfNegativeRegulations,3);
				HashMap<String, Float> signs = new HashMap<String, Float>();  
				for(ReactionRegulator reg: regs){
					Vector<String> names = BiographUtils.extractProteinNamesFromNodeName(reg.node.Id);
					for(String s: names){
						if(!regulators.contains(s))
							regulators.add(s);
						Float sign = signs.get(s); 
						if(sign==null)
							sign = new Float(reg.sign);
						else
							sign+=(float)reg.sign/(float)reg.level;
						signs.put(s, sign);
					}
				}
				
				boolean nonempty = false;
				if(regulators.size()>0) nonempty = true;
				if((reactants_products.size()>0)&&(!onlyRegulators)) nonempty = true;
				if(nonempty){
				fw.write(reaction+"\t"+type+"\t");
				if(!onlyRegulators){
					for(String s: reactants_products)
						fw.write(s+"[1]\t");
				}
				for(String s: regulators){
					if(signs.get(s)!=0)
						fw.write(s+"["+signs.get(s)+"]\t"); 
					else
						fw.write(s+"\t");
				}
				fw.write("\n");
				}
			}
		}
		fw.close();
	}
	
	
}
