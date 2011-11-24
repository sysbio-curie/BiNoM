package fr.curie.BiNoM.pathways.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import edu.rpi.cs.xgmml.*;
import fr.curie.BiNoM.pathways.analysis.structure.DataPathConsistencyAnalyzer;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.GraphAlgorithms;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.analysis.structure.Path;
import fr.curie.BiNoM.pathways.analysis.structure.StructureAnalysisUtils;
import fr.curie.BiNoM.pathways.utils.CombinationGenerator;
import fr.curie.BiNoM.pathways.wrappers.XGMML;


public class TestGraphAlgo {


	public static class ElemScore {
		public Node source;
		public Node target;
		public Double value;

		public ElemScore(Node source, Node target, Double value) {
			this.value = value;
			this.source = source;
			this.target = target;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String file = "/bioinfo/users/ebonnet/Binom/signal.xgmml";

		// load xgmml file as graphdoc
		GraphDocument gr = null;
		try {
			gr = GraphDocument.Factory.parse(new File(file));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// convert GraphDocument to binom graph
		Graph graph = XGMML.convertXGMMLToGraph(gr);

		ArrayList<Node> sourceNodes = new ArrayList<Node>();
		ArrayList<Node> outputNodes = new ArrayList<Node>();
		ArrayList<Node> targetNodes = new ArrayList<Node>();
		ArrayList<Node> sideNodes = new ArrayList<Node>();

		sourceNodes.add(graph.getNode("I1"));
		sourceNodes.add(graph.getNode("I2"));

		targetNodes.add(graph.getNode("O1"));
		//targetNodes.add(graph.getNode("O2"));
		
		sideNodes.add(graph.getNode("O2"));

		// output nodes are the union of target and side-effect nodes
		for (Node n : targetNodes)
			outputNodes.add(n);

		for (Node n : sideNodes)
			outputNodes.add(n);

		// Find all paths from source nodes to output nodes
		//HashMap<String, Vector<Vector<Graph>>> allGraphs = new HashMap<String, Vector<Vector<Graph>>>();

		DataPathConsistencyAnalyzer dpc = new DataPathConsistencyAnalyzer();
		//dpc.graph = graph;

		// simple data structure to select unique paths
		HashSet<String> nodeSequence = new HashSet<String>();

		// store all unique paths to output nodes
		ArrayList<Path> allPaths = new ArrayList<Path>();

		// all paths split in individual components e.g. A->B->C: A->B->C + B->C 
		ArrayList<Path> splitPaths = new ArrayList<Path>();
		
		// all individual nodes from elementary paths (target nodes excepted)
		HashSet<Node> allNodes = new HashSet<Node>();

		for (Node so : sourceNodes) {
			for (Node out : outputNodes) {

				Vector<Graph> tmp = GraphAlgorithms.Dijkstra(graph, graph.getNode(so.Id), graph.getNode(out.Id), true, false, Double.POSITIVE_INFINITY);

				for (Graph g : tmp) {
					Path p = new Path();
					p.graph = new Graph();
					for (int j=0;j<g.Nodes.size();j++) {
						p.graph.addNode(g.Nodes.get(j));
					}
					p.graph.addConnections(g);
					p.source = p.graph.getNodeIndex(so.Id);
					p.target = p.graph.getNodeIndex(out.Id);
					dpc.calcPathProperties(p);
					if (nodeSequence.contains(p.label) == false) {
						nodeSequence.add(p.label);
						allPaths.add(p);
						//System.out.println(p.label);
					}
				}
			}
		}

		
		//split all paths
		nodeSequence = new HashSet<String>();
		for (Path p : allPaths) {
			for (int i=0;i<p.nodeSequence.size()-1;i++) {
				
				allNodes.add(p.nodeSequence.get(i));
				
				Path pa = new Path();
				pa.graph = new Graph();

				String targetId = p.nodeSequence.get(p.nodeSequence.size()-1).Id;
				String sourceId = p.nodeSequence.get(i).Id;

				for (int k=i;k<p.nodeSequence.size();k++) {
					//System.out.print(p.nodeSequence.get(k).Id+":");
					pa.graph.addNode(p.nodeSequence.get(k));
				}
				//System.out.println();

				pa.graph.addConnections(p.graph);
				pa.source = pa.graph.getNodeIndex(sourceId);
				pa.target = pa.graph.getNodeIndex(targetId);
				//System.out.println(sourceId+"::"+targetId);
				dpc.calcPathProperties(pa);
				if (nodeSequence.contains(pa.label) == false) {
					nodeSequence.add(pa.label);
					splitPaths.add(pa);
					//System.out.println(pa.label);
				}
			}
		}

		/*
		 *  select paths and nodes associated to target nodes
		 */

		// elementary paths, set of paths sinking to target nodes
		ArrayList<Path> elemPaths = new ArrayList<Path>();

		// same but split in individual components
		ArrayList<Path> elemSplitPaths = new ArrayList<Path>();

		// set of unique nodes from elementary paths
		HashSet<Node> elemNodes = new HashSet<Node>();

		// scores for each elementary node and targets
		//ArrayList<ElemScore> elemScores = new ArrayList<ElemScore>();

		for (Node n : targetNodes) {
			for (Path p : allPaths) {
				if (p.nodeSequence.get(p.target) == n) {
					elemPaths.add(p);
					// take all nodes except last one, by definition the target node
					for (int i=0;i<p.nodeSequence.size()-1;i++)
						elemNodes.add(p.nodeSequence.get(i));
				}
			}
		}

		for (Node n : targetNodes) {
			for (Path p : splitPaths) {
				if (p.nodeSequence.get(p.target) == n) {
					elemSplitPaths.add(p);
				}
			}
		}

		// initialize scores
//		for (Node t : targetNodes)
//			for (Node s : elemNodes)
//				elemScores.add(new ElemScore(s, t, 0.0));

		
		int ct = 0;
		HashMap<Node, HashMap<Node, Integer>> scoreMap = new HashMap<Node, HashMap<Node, Integer>>();
		double [] scoreVal = new double[targetNodes.size() * allNodes.size()];
		
		for (Node t : targetNodes)
			scoreMap.put(t, new HashMap<Node, Integer>());
		
		for (Node t : targetNodes)
			for (Node s : elemNodes) {
				System.out.println(t.Id+"--"+s.Id);
				scoreMap.get(t).put(s, ct);
				scoreVal[ct] = 0.0;
				ct++;
			}
		
		for (Node t : targetNodes)
			for (Node s : elemNodes) {
				int idx = scoreMap.get(t).get(s);
				System.out.println(t.Id+"-"+s.Id+" = "+scoreVal[idx]);
			}
		
		// score nodes
		//System.out.println("Scores");
//		for (Node s : elemNodes) {
//			for (Path p : elemSplitPaths) {
//				if (p.nodeSequence.get(p.source) == s) {
//					for (ElemScore e : elemScores) {
//						if (e.source == s && e.target == p.nodeSequence.get(p.target)) {
//							double sco = p.influence / p.length;
//							e.value += p.influence / p.length;
//							//System.out.println(e.source.Id+":"+e.target.Id+" "+p.label+" infl="+p.influence+" len="+p.length+" score="+sco+" val="+e.value);
//						}
//					}
//				}
//			}
//		}

		System.out.println("\nScores");
		//for (Node s : elemNodes) {
			for (Path p : elemSplitPaths) {
				//if (p.nodeSequence.get(p.source) == s) {
					Node target = p.nodeSequence.get(p.target);
					Node source = p.nodeSequence.get(p.source);
					int idx = scoreMap.get(target).get(source);
					double sco = p.influence / p.length;
					scoreVal[idx] += p.influence / p.length;
					System.out.println(source.Id+":"+target.Id+" "+p.label+" infl="+p.influence+" len="+p.length+" score="+sco+" val="+ scoreVal[idx]);
				//}
			}
		//}
		
		// apply score penalty for side effects
		//System.out.println("Penalty scores");
//		for (Node s : elemNodes) {
//			for (Node side : sideNodes) {
//				for (Path p : splitPaths) {
//					if (p.nodeSequence.get(p.target) == side && p.nodeSequence.get(p.source) == s) {
//						for (ElemScore e : elemScores) {
//							if (e.source == s) {
//								double sco = p.influence / p.length;
//								e.value -= p.influence / p.length;
//								//System.out.println(e.source.Id+":"+e.target.Id+" "+p.label+" infl="+p.influence+" len="+p.length+" score="+sco+" val="+e.value);
//								if (e.value <= 0.0)
//									e.value = 0.0;
//							}
//						}
//					}
//				}
//			}
//		}

		System.out.println("\nPenalty scores");
		for (Node source : elemNodes) {
			for (Node target : targetNodes) {
				for (Node side : sideNodes) {
					for (Path ps : splitPaths) {
						Node split_target = ps.nodeSequence.get(ps.target);
						Node split_source = ps.nodeSequence.get(ps.source); 
						if (split_target == side && split_source == source) {
							int idx = scoreMap.get(target).get(source);
							double sco = ps.influence / ps.length;
							scoreVal[idx] -= ps.influence / ps.length;
							System.out.println(source.Id+":"+target.Id+" "+ps.label+" infl="+ps.influence+" len="+ps.length+" score="+sco+" val="+scoreVal[idx]);
							if (scoreVal[idx] <= 0.0)
								scoreVal[idx] = 0.0;
						}
					}
				}
			}
		}
		

		
		System.out.println("\nfinal score");
		for (Node t : targetNodes)
			for (Node s : elemNodes) {
				int idx = scoreMap.get(t).get(s);
				System.out.println(t.Id+"-"+s.Id+" = "+scoreVal[idx]);
			}
	
		System.exit(1);
		
		
		/*
		 * optimal cut set search
		 */

		// initial number of elementary paths
		int k = elemPaths.size();

		// store elementary nodes as an array of strings
		ArrayList<String> elts  = new ArrayList<String>(elemNodes.size());
		for (Node n : elemNodes) {
			elts.add(n.Id);
		}

		// convert elementary paths to hashsets of strings
		ArrayList<HashSet<String>> allSets = new ArrayList<HashSet<String>>();

		for (int i=0;i<elemPaths.size();i++)
			allSets.add(new HashSet<String>());

		for (int i=0;i<elemPaths.size();i++)
			for (int j=0;j<elemPaths.get(i).nodeSequence.size()-1;j++)
				allSets.get(i).add(elemPaths.get(i).nodeSequence.get(j).Id);

		ArrayList<HashSet<String>> optCutSets = new ArrayList<HashSet<String>>();

		optSearch:
			for (int i=0;i<k;i++) {

				int setSize = i+1;

				System.out.println("opt search size " + setSize);

				// opt cut sets of size 1
				if (setSize == 1) {
					for (int j=0;j<elts.size();j++) {
						int sum=0;
						for (HashSet<String> h : allSets) {
							if (h.contains(elts.get(j)))
								sum++;
						}

						//System.out.println(elements[j]+":"+sum+"/"+allSets.size());
						if (sum == allSets.size()) {
							HashSet<String> nw = new HashSet<String>();
							nw.add(elts.get(j));
							optCutSets.add(nw);
						}
					}

					for (HashSet<String> h : optCutSets)
						System.out.println(h.toString());
				}



				if (setSize > 1) {

					// remove nodes associated to previous cut sets
					if (optCutSets.size() > 0) {
						for (HashSet<String> h : optCutSets) {
							for (String id : h) {

								// check elementary sets
								for (HashSet<String> as : allSets) {
									as.remove(id);
								}

								// removed covered nodes from node list
								for (int j=0;j<elts.size();j++) {
									if (elts.get(j).equals(id))
										elts.remove(j);
								}
							}
						}

						// remove empty sets 
						for (int j=0;j<allSets.size();j++) {
							if (allSets.get(j).size() == 0) {
								allSets.remove(j);
							}
						}
					}

					// stop if all nodes were covered
					if (elts.size() == 0) {
						System.out.println("Break at size "+setSize);
						break optSearch;
					}

					System.out.println("allSets");
					for (HashSet h : allSets)
						System.out.println(h.toString());

					System.out.println("elts");
					for (String id : elts) 
						System.out.print(id+":");
					System.out.println();


					// get all combinations of size setSize
					CombinationGenerator cg = new CombinationGenerator(elts.size(), setSize);
					int[] indices;
					ArrayList<HashSet<String>> candidates = new ArrayList<HashSet<String>>();

					while(cg.hasMore()) {
						candidates.add(new HashSet<String>());
						indices = cg.getNext();
						for (int j=0;j<indices.length;j++) {
							candidates.get(candidates.size()-1).add(elts.get(indices[j]));
						}
					}

					System.out.println("combinations");
					for (HashSet h : candidates)
						System.out.println(h.toString());

					int[] countHit = new int[allSets.size()];
					for (HashSet<String> ca : candidates) {

						for (int j=0;j<allSets.size();j++)
							countHit[j] = 0;
						for (String ca_id : ca) {
							for (int l=0;l<allSets.size();l++) {
								if (allSets.get(l).contains(ca_id))
									countHit[l]++;
							}
						}

						int sum=0;
						for (int l=0;l<allSets.size();l++)
							if (countHit[l]>0)
								sum++;

						if (sum == allSets.size()) {
							optCutSets.add(ca);
						}
					}
					
					System.out.println("opt cut set size" +setSize);
					for (HashSet<String> o : optCutSets) {
						if (o.size() == setSize)
							System.out.println(o.toString());
					}
				}
			}
	}
}
