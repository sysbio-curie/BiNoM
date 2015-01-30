package fr.curie.BiNoM.pathways.navicell;

import java.util.HashMap;
import java.util.Vector;

import org.sbml.x2001.ns.celldesigner.ReactionDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.analysis.structure.BiographUtils;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class FindNeighbours {

	public static void test_main(String[] args) {
		try{
			
			//SbmlDocument sbml = CellDesigner.loadCellDesigner("C:/Datas/BinomTest/M-phase2.xml");
			SbmlDocument sbml = CellDesigner.loadCellDesigner("C:/Datas/BinomTest/BioPAXtest/acsn_master.xml");
			CellDesigner.entities = CellDesigner.getEntities(sbml);
			
			Graph reactionGraph = getReactionGraph(sbml);
			HashMap<String, Vector<String>> speciesId2name = mapSpeciesId2name(reactionGraph);
			HashMap<String, String> name2speciesId = mapName2SpeciesId(reactionGraph);
			Graph entityGraph = getEntityGraph(reactionGraph);
			HashMap<String, String> entityId2name = mapEntityId2Name(sbml);
			HashMap<String, String> name2entityId = mapName2EntityId(sbml);
			for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
				SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
				String spid = sp.getId();
				String sp_class = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
				if(!sp_class.equals("DEGRADED")){
					Vector<String> spid_rv = getReactionGraphNeighbourReactions(reactionGraph, speciesId2name, name2speciesId, spid);
					System.out.print(spid+":\t");
					for(String s: spid_rv) System.out.print(s+"\t"); System.out.println();
				}
			}
			for(int i=0;i<sbml.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
				ReactionDocument.Reaction re = sbml.getSbml().getModel().getListOfReactions().getReactionArray(i);
				String reid = re.getId();
				Vector<String> reid_nv = getReactionGraphNeighbourSpecies(reactionGraph, speciesId2name, name2speciesId, reid);
				System.out.print(reid+":\t"); 
				for(String s: reid_nv) System.out.print(s+"\t"); System.out.println("");
			}
			
			System.out.println(); System.out.println("Proteins:");
			for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
				String pid = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i).getId();
				Vector<String> ev = getEntityGraphNeighbours(entityGraph, entityId2name, name2entityId, pid);
				System.out.print(pid+":\t");
				for(String s: ev) System.out.print(s+"\t"); System.out.println();
			}
			System.out.println(); System.out.println("RNAs:");
			for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
				String pid = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i).getId();
				Vector<String> ev = getEntityGraphNeighbours(entityGraph, entityId2name, name2entityId, pid);
				System.out.print(pid+":\t");
				for(String s: ev) System.out.print(s+"\t"); System.out.println();
			}
			System.out.println(); System.out.println("Genes:");
			for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
				String pid = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i).getId();
				Vector<String> ev = getEntityGraphNeighbours(entityGraph, entityId2name, name2entityId, pid);
				System.out.print(pid+":\t");
				for(String s: ev) System.out.print(s+"\t"); System.out.println();
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Graph getReactionGraph(SbmlDocument sbml){
		GraphDocument grD = CellDesignerToCytoscapeConverter.getXGMMLGraph("test", sbml.getSbml());
		Graph graph = XGMML.convertXGMMLToGraph(grD);
		graph.calcNodesInOut();
		return graph;
	}
	
	public static Graph getEntityGraph(Graph reactionGraph){
		Graph graph = BiographUtils.convertReactionNetworkIntoEntityNetwork(reactionGraph);
		graph.calcNodesInOut();
		return graph;
	}
	
	public static HashMap<String, Vector<String>> mapSpeciesId2name(Graph reactionGraph){
		HashMap<String, Vector<String>> res = new HashMap<String, Vector<String>>();
		for(int i=0;i<reactionGraph.Nodes.size();i++){
			Node n = reactionGraph.Nodes.get(i);
			if(n.getFirstAttribute("CELLDESIGNER_SPECIES")!=null)if(!(n.getFirstAttributeValue("CELLDESIGNER_SPECIES")).equals("")){
				String spid = n.getFirstAttributeValue("CELLDESIGNER_SPECIES");
				Vector<String> v = res.get(spid);
				if(v==null) v = new Vector<String>();
				if(!v.contains(n.Id)) v.add(n.Id);
				res.put(spid, v);
			}
		}
		return res;
	}
	
	public static HashMap<String, String> mapName2SpeciesId(Graph reactionGraph){
		HashMap<String, String> res = new HashMap<String, String>();
		for(int i=0;i<reactionGraph.Nodes.size();i++){
			Node n = reactionGraph.Nodes.get(i);
			if(n.getFirstAttribute("CELLDESIGNER_SPECIES")!=null)if(!(n.getFirstAttributeValue("CELLDESIGNER_SPECIES")).equals("")){
				String spid = n.getFirstAttributeValue("CELLDESIGNER_SPECIES");
				res.put(n.Id,spid);
			}
		}
		return res;
	}	
	
	public static HashMap<String, String> mapEntityId2Name(SbmlDocument sbml){
		HashMap<String, String> res = CellDesigner.getEntitiesNames(sbml);
		
		return res;
	}
	
	public static HashMap<String, String> mapName2EntityId(SbmlDocument sbml){
		HashMap<String, String> id2name = CellDesigner.getEntitiesNames(sbml);
		HashMap<String, String> res = new HashMap<String, String>();
		for(String id: id2name.keySet()){
			String name = id2name.get(id);
			res.put(name, id);
		}
		return res;
	}	
	
	public static Vector<String> getReactionGraphNeighbourReactions(Graph reactionGraph, HashMap<String, Vector<String>> speciesId2name, HashMap<String, String> name2speciesId, String speciesId){
		Vector<String> reactionIDs = new Vector<String>();
		Vector<String> names = speciesId2name.get(speciesId);
		if(names==null){
			//System.out.println("No names found for "+speciesId);
		}else
		for(String name: names){
			Node n = reactionGraph.getNode(name);
			for(Edge e: n.incomingEdges)
				if(!reactionIDs.contains(e.Node1.Id))
					reactionIDs.add(e.Node1.Id);
			for(Edge e: n.outcomingEdges)
				if(!reactionIDs.contains(e.Node2.Id))
					reactionIDs.add(e.Node2.Id);
		}
		return reactionIDs;
	}
	
	public static Vector<String> getReactionGraphNeighbourSpecies(Graph reactionGraph, HashMap<String, Vector<String>> speciesId2name, HashMap<String, String> name2speciesId, String reactionId){
		Vector<String> speciesIDs = new Vector<String>();
		Node n = reactionGraph.getNode(reactionId);
		for(Edge e: n.incomingEdges)
			if(!speciesIDs.contains(name2speciesId.get(e.Node1.Id)))
				speciesIDs.add(name2speciesId.get(e.Node1.Id));
		for(Edge e: n.outcomingEdges)
			if(!speciesIDs.contains(name2speciesId.get(e.Node2.Id)))
				speciesIDs.add(name2speciesId.get(e.Node2.Id));
		return speciesIDs;
	}
	
	public static Vector<String> getEntityGraphNeighbours(Graph entityGraph, HashMap<String, String> entityId2name, HashMap<String, String> name2entityId, String entityId){
		Vector<String> entityIDs = new Vector<String>();
		String name = entityId2name.get(entityId);
		Node n = entityGraph.getNode(name);
		if(n!=null){
		for(Edge e: n.incomingEdges)
			if(name2entityId.get(e.Node1.Id)!=null)if(!entityIDs.contains(name2entityId.get(e.Node1.Id)))
				entityIDs.add(name2entityId.get(e.Node1.Id));
		for(Edge e: n.outcomingEdges)
			if(name2entityId.get(e.Node2.Id)!=null)if(!entityIDs.contains(name2entityId.get(e.Node2.Id)))
				entityIDs.add(name2entityId.get(e.Node2.Id));
		}else{
			//System.out.println(name+" node is not found in the entity graph");
		}
		return entityIDs;
	}
	
	
	



}
