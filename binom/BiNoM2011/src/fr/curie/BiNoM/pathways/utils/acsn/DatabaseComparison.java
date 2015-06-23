package fr.curie.BiNoM.pathways.utils.acsn;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;



import fr.curie.BiNoM.pathways.analysis.structure.Attribute;
import fr.curie.BiNoM.pathways.analysis.structure.BiographUtils;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.GraphAlgorithms;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.analysis.structure.OmegaScoreData;
import fr.curie.BiNoM.pathways.biopax.PublicationXref;
import fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory;
import fr.curie.BiNoM.pathways.utils.BioPAXGraphMappingService;
import fr.curie.BiNoM.pathways.utils.GraphXGMMLParser;
import fr.curie.BiNoM.pathways.utils.SetOverlapAnalysis;
import fr.curie.BiNoM.pathways.utils.Utils;
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
			//String fileName = "c:/datas/reactome/reactome_human.owl";
			//String fileName = "c:/datas/Reactome/Homo_sapiens.owl";
			//String fileName = "C:/Datas/PID_NCI_pathways/NCI-Nature_Curated.bp3.owl";
			
			
			
			String fn = "C:/Datas/PID_NCI_pathways/PPI_network_extracted.xgmml";
			String fn_out = "C:/Datas/PID_NCI_pathways/PPI_NCI_PID";
			String fn_biopax = "C:/Datas/PID_NCI_pathways/NCI-Nature_Curated.bp3.owl_nouniquenames.xgmml";
			ConvertXGMML2PPINetwork(fn,fn_biopax,fn_out);
			System.exit(0);
			
			
			/*String fileName = "C:/Datas/acsn/biopax/version1.1/acsn_v1.0.owl";
			
			
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
				System.out.println("PMID:"+pmid+"\t"+year);
				}else{
					String pmid = "null";
					if(xr.getId()!=null){
						pmid = xr.getId();
					System.out.println("PMID:"+pmid);
				}}
				}
			}
			
			
			System.exit(0);*/
			
			/*SetOverlapAnalysis sp = new SetOverlapAnalysis();
			sp.LoadNewOrderedList("C:/Datas/acsn/database_comparison/publications/nci_pmids.txt");
			sp.LoadNewOrderedList("C:/Datas/acsn/database_comparison/publications/reactome_pmids.txt");
			sp.LoadNewOrderedList("C:/Datas/acsn/database_comparison/publications/acsn_pmids.txt");
			
			Vector<Vector<OmegaScoreData>> fr = new Vector<Vector<OmegaScoreData>>();
			fr.add(sp.analyzeHitFrequency(sp.lists));
			
			sp.sets = new Vector<HashSet<String>>();
			sp.sets.add(new HashSet<String>());
			sp.sets.get(0).addAll(sp.lists.get(0));
			sp.sets.add(new HashSet<String>());
			sp.sets.get(1).addAll(sp.lists.get(1));
			sp.sets.add(new HashSet<String>());
			sp.sets.get(2).addAll(sp.lists.get(2));
			sp.setnames = new Vector<String>();
			sp.setnames.add("nci");
			sp.setnames.add("reactome");
			sp.setnames.add("acsn");
			
			HashSet<String> set4 = Utils.IntersectionOfSets(sp.sets.get(0), sp.sets.get(1));
			sp.sets.add(set4);
			sp.setnames.add("nci_reactome");
			
			//sp.saveSetFrequencies("C:/Datas/acsn/database_comparison/publications/freqs.txt", sp.lists.size(), fr);
			sp.printSetIntersections("C:/Datas/acsn/database_comparison/publications/pmids_inters.txt");
			sp.printSetSizes();*/
			
			/*HashMap<String,Integer> countJournals = new HashMap<String,Integer>();
			Vector<String> list = Utils.loadStringListFromFile("c:/datas/acsn/database_comparison/publications/reactome_biblio_report.part1");
			//Vector<String> list = Utils.loadStringListFromFile("c:/datas/acsn/database_comparison/publications/acsn_biblio_report.txt");
			//Vector<String> list = Utils.loadStringListFromFile("c:/datas/acsn/database_comparison/publications/nci_biblio_report.txt");
			int counter = 0;
			for(String s: list)if(s.length()>40){
				s=s.trim();
				if(s.endsWith("[REVIEW]")) s = s.substring(0, s.length()-9);
				if(s.contains("J. Biol. Chem.")){
					s = "J. Biol. Chem.";
				}else{
				int k = s.indexOf(".");
				try{
				s = s.substring(k+1, s.length()-1);
				}catch(Exception e){
					System.out.println("UNKNOWN:"+s);
				}
				StringTokenizer st = new StringTokenizer(s,"."); 
				String last = "";
				while(st.hasMoreTokens())
					last = st.nextToken();
				k = s.indexOf(last);
				if(k>0){
				s = s.substring(0, k);
				}else{
					st = new StringTokenizer(s," "); 
					last = "";
					while(st.hasMoreTokens())
						last = st.nextToken();
					k = s.indexOf(last);
					if(k>0)
						s = s.substring(0, k);
				}}
				if(s.length()<80){
					//System.out.println(s);
					Integer n = countJournals.get(s);
					if(n==null){
						n = new Integer(0);
					}
					countJournals.put(s, n+1);
					counter++;
				}
			}
			
			for(String s: countJournals.keySet()){
				System.out.println(s+"\t"+countJournals.get(s));
			}
			System.out.println("Total = "+counter);
			//Graph index = dc.createIndexForBioPAX(biopax);
			//XGMML.saveToXGMML(index, fileName+".xgmml");*/
			
			//decomposeIntoSCC("C:/Datas/acsn/database_comparison/connectivity/acsn_lcc.xgmml");
			//decomposeIntoSCC("C:/Datas/acsn/database_comparison/connectivity/nci_lcc.xgmml");
			decomposeIntoSCC("C:/Datas/acsn/database_comparison/connectivity/reactome_lcc.xgmml");
			//decomposeIntoSCC("C:/Datas/acsn/database_comparison/connectivity/nci_test.xgmml");
			System.exit(0);
			
			DatabaseComparison dc = new DatabaseComparison();
			boolean treatComplexesRecursively = true;
			//String fileName = "c:/datas/Reactome/Homo_sapiens.owl";
			//String fileName = "c:/datas/acsn/biopax/version1.1/acsn_v1.1.owl";
			//String fileName = "c:/datas/acsn/biopax/version1.1/acsn.owl";
			String fileName = "C:/Datas/PID_NCI_pathways/NCI-Nature_Curated.bp3.owl";
			
			GraphXGMMLParser gp = new GraphXGMMLParser();
			//gp.parse(fileName+".xgmml");
			gp.parse(fileName+"_nouniquenames.xgmml");			
			
			Graph index = gp.graph;
			
			if(fileName.endsWith("bp2.owl")){
			System.out.println(index.Nodes.size());
			Node gtp = index.getNode("GTP"); index.removeNode(gtp.Id);
			System.out.println(index.Nodes.size());
			Node gdp = index.getNode("GDP"); index.removeNode(gdp.Id);
			System.out.println(index.Nodes.size());
			Node ca = index.getNode("Ca2+"); index.removeNode(ca.Id);
			System.out.println(index.Nodes.size());
			}
			
			//insertHomodimerContainsEges(index);

			//Graph part = dc.selectEntityReferencesByXREFList(index, Utils.loadStringListFromFile("c:/datas/acsn/uniprots.txt"), "UniProt");
			Graph part = dc.selectAllReferencesType(index,"ProteinReference");
			//Graph part = new Graph(); part.addNode(index.getNode("PFKP"));
			
			Graph newIndex = dc.addAssociatedReactionNetworkToGraphFromIndex(part, index, treatComplexesRecursively);
			
			Vector<String> pmids = dc.extractPubMedIds(index);
			FileWriter fw = new FileWriter(fileName+".pmids.txt");
			for(String s: pmids) fw.write(s+"\n");
			fw.close();
			
			dc.BioPAXEntityReactionDistribution(index, "proteinreference", true, treatComplexesRecursively);
			
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
	
	public HashMap<String, Vector<String>> BioPAXEntityReactionDistribution(Graph indexFile, String containsString, boolean verbose, boolean treatComplexesRecursively){
		indexFile.calcNodesInOut();
		Vector<String> allEntityReferences = new Vector<String>();
		Graph query = new Graph();
		for(Node n: indexFile.Nodes){
			String tp = n.getFirstAttributeValue("BIOPAX_NODE_TYPE");
			if(tp!=null)if(tp.toLowerCase().contains(containsString)){
				allEntityReferences.add(n.Id);
				query.addNode(n);
			}
		}
		HashMap<String, Vector<String>> reactionNeighb = new HashMap<String, Vector<String>>();
		for(String s: allEntityReferences){
			if(s.equals("ATP8"))
				System.out.println();
			Vector<Node> neighbs = BiographUtils.getNodeNeighbors(indexFile, indexFile.getNode(s), false, true, 2);
			Vector<String> reactions = new Vector<String>();
			for(Node n: neighbs){
				Graph graph_ni = new Graph();
				graph_ni.addNode(n);
				collectDownstreamComplexesRecursively(n, graph_ni, query, treatComplexesRecursively);
				/*System.out.print(n.Id+"\t");
				for(int i=0;i<graph_ni.Nodes.size();i++) System.out.print(graph_ni.Nodes.get(i).Id+"\t");
				System.out.println();*/
				for(Node nn: graph_ni.Nodes){
					String ntyp = nn.getFirstAttributeValue("BIOPAX_NODE_TYPE");
					if(ntyp.equals("Complex")){
						Vector<Node> neighbs1 = BiographUtils.getNodeNeighbors(indexFile, nn, true, true, 1);

						/*System.out.print("Neighbours of "+nn.Id+"\t");
						for(int i=0;i<neighbs1.size();i++) System.out.print(neighbs1.get(i).Id+"\t");
						System.out.println();*/
						
						for(Node nnn: neighbs1){
							String tp = nnn.getFirstAttributeValue("BIOPAX_REACTION");
							if(tp!=null)if(!tp.trim().equals("")){
								if(!reactions.contains(nnn.Id))
									reactions.add(nnn.Id);
							}
						}
					}
				}
				
			}
			for(Node n: neighbs){
				String tp = n.getFirstAttributeValue("BIOPAX_REACTION");
				if(tp!=null)if(!tp.trim().equals("")){
					if(!reactions.contains(n.Id))
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
	
	public Graph addAssociatedReactionNetworkToGraphFromIndex(Graph query, Graph index, boolean treatComplexesRecursively){
		Graph graph = new Graph();
		graph.addNodes(query);
		index.calcNodesInOut();
		int initialNodeNumber = graph.Nodes.size();
		// First, collect all simple species
		for(Node n: query.Nodes){
			Node ni = index.getNode(n.Id);
			if(ni.Id.equals("PFKP"))
				System.out.println();
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
			if(ni.Id.equals("Claudin14"))
				System.out.println();
			Graph graph_ni = new Graph();
			graph_ni.addNode(ni);
			collectDownstreamComplexesRecursively(ni, graph_ni, query, treatComplexesRecursively);
			for(Node nn: graph_ni.Nodes){
				String ntyp = nn.getFirstAttributeValue("BIOPAX_NODE_TYPE");
				if(ntyp.equals("Complex")){
					graph.addNode(nn);
				}
			}
		}
		
		System.out.println((graph.Nodes.size()-nodeNumberWithSpecies)+" containing complexes found.");
		Vector<String> listOfReactions = new Vector<String>();
		Vector<String> listOfConnectingReactions = new Vector<String>();
		int numReactions = 0;
		int numFullReactions = 0;
		index.calcNodesInOut();
		for(Node n: index.Nodes){
			String rid = n.getFirstAttributeValue("BIOPAX_REACTION");
			if(rid!=null)if(!rid.equals("")){
				numReactions++;
				// At least one reactant and (correction - OR!!!) one product should be from the selected simple and complex species
				int numberOfReactants = 0;
				int numberOfProducts = 0;
				
				int numberOfGoodReactantsAndRegulators = 0;
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
						numberOfGoodReactantsAndRegulators++;
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
				if((numberOfGoodReactantsAndRegulators>0)||(numberOfGoodProducts>0))
					listOfReactions.add(n.Id);
				if((numberOfGoodReactantsAndRegulators>0)&&(numberOfGoodProducts>0))
					listOfConnectingReactions.add(n.Id);
				if((numberOfReactants>0)&&(numberOfProducts>0)){
					numFullReactions++;
					if((numberOfGoodReactantsAndRegulators==0)||(numberOfGoodProducts==0)){
						//System.out.println(rid+":"+reactantString+"->"+productString);
					}
				}
			}
		}
		for(String rid: listOfReactions)
			graph.addNode(index.getNode(rid));
		System.out.println(listOfConnectingReactions.size()+" connecting reactions and "+listOfReactions.size()+" related reactions (from "+numReactions+" and "+numFullReactions+" full) were found.");
		
		int count = 0;
		Random r = new Random();
		for(int i=0;i<index.Nodes.size();i++){
			Node n = index.Nodes.get(r.nextInt(index.Nodes.size()));
			String rid = n.getFirstAttributeValue("BIOPAX_REACTION");
			if(rid!=null)if(!rid.equals("")){
				if(!listOfReactions.contains(n.Id)){
					System.out.println("Example of reaction not considered: "+n.Id);
					count++;
					if(count>5)
						break;
				}
			}
		}
		
		//graph.addConnections(index);
		return graph;
	}
	
	public void collectDownstreamComplexesRecursively(Node ni, Graph graph_ni, Graph query, boolean treatComplexesRecursively){
	Vector<Node> collectedComplexes = new Vector<Node>();
	int currentGraphSize = graph_ni.Nodes.size();
	for(Edge e: ni.outcomingEdges){
		String etyp = e.getFirstAttributeValue("BIOPAX_EDGE_TYPE");
		if(etyp!=null)if(etyp.equals("CONTAINS")){
			Node nsp = e.Node2;
			String ntyp = nsp.getFirstAttributeValue("BIOPAX_NODE_TYPE");
			if(ntyp.equals("Complex")){
				// Check if complex have only components from the query
				boolean goodComplex = true;
				/*for(Edge ei: nsp.incomingEdges){
					if(query.getNode(ei.Node1.Id)==null){
						goodComplex = false;
						break;
					}
				}*/
				if(goodComplex){
					graph_ni.addNode(nsp);
					collectedComplexes.add(nsp);
				}
			}
		}
	}
	int newGraphSize = graph_ni.Nodes.size();
	if(newGraphSize>currentGraphSize)if(treatComplexesRecursively)
		for(Node n: collectedComplexes){
			collectDownstreamComplexesRecursively(n,graph_ni,query, treatComplexesRecursively);
		}
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
	
	public static void insertHomodimerContainsEges(Graph index){
		
	}
	
	public static void decomposeIntoSCC(String fn) throws Exception{
		String fileName = (new File(fn)).getName();
		String resfold = fileName.substring(0,fileName.indexOf("."));
		String folder = (new File(fn)).getParentFile().getAbsolutePath();
		File rfold = new File(folder+"/"+resfold);
		rfold.mkdir();
		GraphXGMMLParser gp = new GraphXGMMLParser();
		gp.parse(fn);			
		Graph graph = gp.graph;
		//XGMML.saveToXGMML(graph, folder+"/test.xggml");
		Vector<Graph> sccs = GraphAlgorithms.StronglyConnectedComponentsTarjan(graph, 2);
		float sizes[] = new float[sccs.size()];
		int k=0;
		for(Graph gr: sccs){
			sizes[k++] = gr.Nodes.size();
		}
		int inds[] = Utils.SortMass(sizes);
		for(int i=0;i<sccs.size();i++){
			System.out.println(sccs.get(inds[inds.length-i-1]).Nodes.size());
			String id = ""+(i+1);
			if(id.length()<2) id = "0"+id;
			if(id.length()<3) id = "0"+id;
			Graph scc = sccs.get(inds[inds.length-i-1]);
			//for(int kk=0;kk<scc.Nodes.size();kk++)
			//	scc.Nodes.get(kk).Attributes.clear();
			//for(int kk=0;kk<scc.Edges.size();kk++)
			//	scc.Edges.get(kk).Id = ""+(kk+1);
			XGMML.saveToXGMML(sccs.get(inds[inds.length-i-1]), rfold.getAbsolutePath()+"/scc_"+id+"_"+sccs.get(inds[inds.length-i-1]).Nodes.size()+".xgmml");
		}
		
	}
	
	public static void ConvertXGMML2PPINetwork(String xgmml, String biopax, String out) throws Exception{
		Graph gr = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(xgmml));
		Graph gr_complete = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(biopax));
		gr_complete.calcNodesInOut();
		FileWriter fw = new FileWriter(out+".sif");
		FileWriter fw_nodes = new FileWriter(out+".gmt");
		HashSet<String> nodes = new HashSet<String>();
		int k=1;
		for(int i=0;i<gr.Nodes.size();i++){
			Node nd = gr.Nodes.get(i);
			String xref = nd.getFirstAttributeValue("BIOPAX_NODE_XREF");
			String type = nd.getFirstAttributeValue("BIOPAX_NODE_TYPE");
			if(xref!=null){
			if(xref.toLowerCase().endsWith("@uniprot")){
				xref = xref.substring(0, xref.length()-8);
				fw_nodes.write(nd.Id+"\tna\t");
				fw_nodes.write(xref);
				nodes.add(nd.Id);
				fw_nodes.write("\n");
			}			
			}else{
				if(type==null){
					//System.out.println("ERROR: type=null for "+nd.Id);
				}else{
					Node n = gr_complete.getNode(nd.Id);
					Vector<String> ids = new Vector<String>();
					for(Edge e: n.incomingEdges){
						if(e.getFirstAttributeValue("BIOPAX_EDGE_TYPE")!=null)
						if(e.getFirstAttributeValue("BIOPAX_EDGE_TYPE").equals("SPECIESOF")){
							xref = e.Node1.getFirstAttributeValue("BIOPAX_NODE_XREF");
							if(xref!=null){
								if(xref.toLowerCase().endsWith("@uniprot")){
									xref = xref.substring(0, xref.length()-8);
									ids.add(xref);
							}			
							}
						}
					}
					if(ids.size()==0){
					if(type.equals("ProteinReference"))
						System.out.println((k++)+"\t"+nd.Id+": not found reference id.");
					}else{
						fw_nodes.write(nd.Id+"\tna\t");
						for(String id: ids)
						fw_nodes.write(id+"\t");
						nodes.add(nd.Id);
						fw_nodes.write("\n");
					}
				}
			}
			
		}
		for(int i=0;i<gr.Edges.size();i++){
			Edge ed = gr.Edges.get(i);
			if(nodes.contains(ed.Node1.Id)&&nodes.contains(ed.Node2.Id))
				fw.write(ed.Node1.Id+"\tpp\t"+ed.Node2.Id+"\n");
		}		
		fw.close();
		fw_nodes.close();
	}
	
}
