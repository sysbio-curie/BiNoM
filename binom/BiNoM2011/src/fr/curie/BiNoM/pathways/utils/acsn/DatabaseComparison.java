package fr.curie.BiNoM.pathways.utils.acsn;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import vdaoengine.utils.Utils;

import fr.curie.BiNoM.pathways.analysis.structure.Attribute;
import fr.curie.BiNoM.pathways.analysis.structure.BiographUtils;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.biopax.PublicationXref;
import fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory;
import fr.curie.BiNoM.pathways.utils.BioPAXGraphMappingService;
import fr.curie.BiNoM.pathways.utils.GraphXGMMLParser;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class DatabaseComparison {

	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
		
			//String fileName = "c:/datas/binomtest/biopaxtest/test.owl";
			//String fileName = "c:/datas/binomtest/biopaxtest/cellcycle_master.owl";
			//String fileName = "c:/datas/binomtest/biopaxtest/dnarepair_master.owl";
			//String fileName = "c:/datas/binomtest/biopaxtest/apoptosis_master.owl";
			//String fileName = "c:/datas/binomtest/biopaxtest/emtcellmotility_master.owl";
			//String fileName = "c:/datas/binomtest/biopaxtest/survival_master.owl";
			//String fileName = "c:/datas/binomtest/biopaxtest/acsn_master.owl";
			String fileName = "c:/datas/reactome/reactome_human.owl";
			
			DatabaseComparison dc = new DatabaseComparison();
			BioPAX biopax = new BioPAX();
			biopax.loadBioPAX(fileName);
			
			List xrefs = biopax_DASH_level3_DOT_owlFactory.getAllPublicationXref(biopax.model);
			System.out.println(xrefs.size()+" found");
			for(Object xref: xrefs){
				PublicationXref xr = (PublicationXref)xref;
				if(xr!=null){
				if(xr.getYear()!=null){	
				int year = xr.getYear();
				String pmid = "null";
				if(xr.getId()!=null){
					pmid = xr.getId();
				}
				System.out.println(pmid+"\t"+year);
				}}
			}
			
			
			System.exit(0);
			
			//Graph index = dc.createIndexForBioPAX(biopax);
			//XGMML.saveToXGMML(index, fileName+".xgmml");
			GraphXGMMLParser gp = new GraphXGMMLParser();
			gp.parse(fileName+".xgmml");
			Graph index = gp.graph;

			//Graph part = dc.selectEntityReferencesByXREFList(index, Utils.loadStringListFromFile("c:/datas/acsn/uniprots.txt"), "UniProt");
			Graph part = dc.selectAllReferencesType(index,"ProteinReference");
			Graph newIndex = dc.addAssociatedReactionNetworkToGraphFromIndex(part, index);
			
			Vector<String> pmids = dc.extractPubMedIds(index);
			FileWriter fw = new FileWriter(fileName+".pmids.txt");
			for(String s: pmids) fw.write(s+"\n");
			fw.close();
			
			//dc.BioPAXEntityReactionDistribution(index, "proteinreference", true);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Graph createIndexForBioPAX(BioPAX biopax) throws Exception{
		Graph index = new Graph();
		BioPAXGraphMappingService bgms = new BioPAXGraphMappingService();
		index = bgms.mapBioPAXToGraph(biopax);
		return index;
	}
	
	public HashMap<String, Vector<String>> BioPAXEntityReactionDistribution(Graph indexFile, String containsString, boolean verbose){
		indexFile.calcNodesInOut();
		Vector<String> allEntityReferences = new Vector<String>();
		for(Node n: indexFile.Nodes){
			String tp = n.getFirstAttributeValue("BIOPAX_NODE_TYPE");
			if(tp!=null)if(tp.toLowerCase().contains(containsString)){
				allEntityReferences.add(n.Id);
			}
		}
		HashMap<String, Vector<String>> reactionNeighb = new HashMap<String, Vector<String>>();  
		for(String s: allEntityReferences){
			Vector<Node> neighbs = BiographUtils.getNodeNeighbors(indexFile, indexFile.getNode(s), true, true, 2);
			Vector<String> reactions = new Vector<String>();
			for(Node n: neighbs){
				String tp = n.getFirstAttributeValue("BIOPAX_REACTION");
				if(tp!=null)if(!tp.trim().equals("")){
					reactions.add(n.Id);
				}
			}
			reactionNeighb.put(s, reactions);			
		}
		
		if(verbose){
			System.out.println("----------------------------");
			System.out.println("Entity-reaction distribution");
			System.out.println("----------------------------");
			for(String s: reactionNeighb.keySet()){
				System.out.print(s+"\t"+reactionNeighb.get(s).size()+"\t");
				Vector<String> reactions = reactionNeighb.get(s);
				for(String reid: reactions)
					System.out.print(reid+"\t");
				System.out.println();
			}
		}
		return reactionNeighb;
	}
	
	public Graph selectEntityReferencesByXREFList(Graph index, Vector<String> ids, String db){
		Graph graph = new Graph();
		Vector<String> corrids = new Vector<String>();
		for(String id: ids){
			String corrid = id+"@"+db;
			if(!id.equals(""))if(!corrids.contains(corrid))
				corrids.add(corrid);
		}
		for(Node n: index.Nodes){
			Vector<Attribute> xrefs = n.getAttributesWithSubstringInName("XREF");
			for(Attribute at: xrefs){
				if(corrids.contains(at.value)){
					graph.addNode(n);
				}
			}
		}
		System.out.println("From "+corrids.size()+" unique ids "+graph.Nodes.size()+" references found");
		return graph;
	}
	
	public Graph addAssociatedReactionNetworkToGraphFromIndex(Graph query, Graph index){
		Graph graph = new Graph();
		graph.addNodes(query);
		index.calcNodesInOut();
		int initialNodeNumber = graph.Nodes.size();
		// First, collect all simple species
		for(Node n: query.Nodes){
			Node ni = index.getNode(n.Id);
			for(Edge e: ni.outcomingEdges){
				String etyp = e.getFirstAttributeValue("BIOPAX_EDGE_TYPE");
				if(etyp!=null)if(etyp.equals("SPECIESOF")){
					Node nsp = e.Node2;
					String ntyp = nsp.getFirstAttributeValue("BIOPAX_NODE_TYPE");
					if(!ntyp.equals("Complex")){
						graph.addNode(nsp);
					}
				}
			}
		}
		int nodeNumberWithSpecies = graph.Nodes.size();
		System.out.println((nodeNumberWithSpecies-initialNodeNumber)+" simple species were found.");
		// Collect all complexes in which only these proteins participate
		for(Node n: query.Nodes){
			Node ni = index.getNode(n.Id);
			for(Edge e: ni.outcomingEdges){
				String etyp = e.getFirstAttributeValue("BIOPAX_EDGE_TYPE");
				if(etyp!=null)if(etyp.equals("CONTAINS")){
					Node nsp = e.Node2;
					String ntyp = nsp.getFirstAttributeValue("BIOPAX_NODE_TYPE");
					if(ntyp.equals("Complex")){
						// Check if complex have only components from the query
						boolean goodComplex = true;
						for(Edge ei: nsp.incomingEdges){
							if(query.getNode(ei.Node1.Id)==null){
								goodComplex = false;
								break;
							}
						}
						if(goodComplex)
							graph.addNode(nsp);
					}
				}
			}
		}
		
		String id1 = "MCH5:TRAIL:TRAIL_receptor_2:FADD_complex@plasma_membrane";
		String id2 = "TRAIL:TRAIL_receptor_2:FADD_complex:procaspase_8_dimer@plasma_membrane";
		System.out.println(graph.getNode(id1)==null);
		System.out.println(graph.getNode(id2)==null);
		
		System.out.println((graph.Nodes.size()-nodeNumberWithSpecies)+" containing complexes found.");
		// Collect all connecting reactions		
		Vector<String> listOfReactions = new Vector<String>();
		int numReactions = 0;
		int numFullReactions = 0;
		index.calcNodesInOut();
		for(Node n: index.Nodes){
			String rid = n.getFirstAttributeValue("BIOPAX_REACTION");
			if(rid!=null)if(!rid.equals("")){
				numReactions++;
				// At least one reactant and one product should be from the selected simple and complex species
				int numberOfReactants = 0;
				int numberOfProducts = 0;
				
				int numberOfGoodReactants = 0;
				int numberOfGoodProducts = 0;
				String reactantString = "";
				String productString = "";
				for(Edge ei: n.incomingEdges){
					String sp = ei.Node1.getFirstAttributeValue("BIOPAX_SPECIES");
					if(sp!=null)if(!sp.equals("")){
						numberOfReactants++;
						reactantString+=ei.Node1.Id+"+";
					}
					if(graph.getNode(ei.Node1.Id)!=null)
						numberOfGoodReactants++;
				}
				for(Edge eo: n.outcomingEdges){
					String sp = eo.Node2.getFirstAttributeValue("BIOPAX_SPECIES");
					if(sp!=null)if(!sp.equals("")){
						numberOfProducts++;
						productString+=eo.Node2.Id+"+";
					}
					if(graph.getNode(eo.Node2.Id)!=null)
						numberOfGoodProducts++;
				}
				if((numberOfGoodReactants>0)&&(numberOfGoodProducts>0))
					listOfReactions.add(n.Id);
				if((numberOfReactants>0)&&(numberOfProducts>0)){
					numFullReactions++;
					if((numberOfGoodReactants==0)||(numberOfGoodProducts==0)){
						//System.out.println(rid+":"+reactantString+"->"+productString);
					}
				}
			}
		}
		for(String rid: listOfReactions)
			graph.addNode(index.getNode(rid));
		System.out.println(listOfReactions.size()+" connecting reactions (from "+numReactions+" and "+numFullReactions+" full) were found.");
		graph.addConnections(index);
		return graph;
	}
	
	public Graph selectAllReferencesType(Graph index, String refType){
		Graph graph = new Graph();
		for(Node n: index.Nodes){
			String typ = n.getFirstAttributeValue("BIOPAX_NODE_TYPE");
			if(typ!=null)if(typ.equals(refType))
				graph.addNode(n);
		}
		System.out.println(graph.Nodes.size()+" "+refType+"s found.");
		return graph;
	}
	
	public Vector<String> extractPubMedIds(Graph index){
		Vector<String> pmids = new Vector<String>();
		for(Node n: index.Nodes){
			String typ = n.getFirstAttributeValue("BIOPAX_NODE_TYPE");
			if(typ.toLowerCase().contains("publication")){
				if(n.Id.toLowerCase().contains("pubmed")){
				String id = n.Id.split("@")[0];
				try{
				int idi = Integer.parseInt(id);
				if(idi<50000000){
				id = ""+idi;
				if(!pmids.contains(id))
						pmids.add(id);
				}}
				catch(Exception e){
					
				}
			}
		}
		}
		return pmids;
	}
	
}
