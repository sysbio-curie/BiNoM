package fr.curie.BiNoM.pathways.analysis.structure;

import fr.curie.BiNoM.cytoscape.ain.ImportFromAINDialogFamily;
import fr.curie.BiNoM.cytoscape.analysis.*;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.test.TestGraphAlgo.ElemScore;
import fr.curie.BiNoM.pathways.utils.*;
import edu.rpi.cs.xgmml.*;

import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;

import javax.swing.JFrame;

import cytoscape.Cytoscape;

public class DataPathConsistencyAnalyzer {
	
	public Graph graph = null;
	
	public Vector<Node> pathwayNodes = new Vector<Node>();
	
	public boolean testSignificance = true;
	public int testSignificanceMode = 0;
	public int numberOfPermutations = 1000;
	
	// SA stands for Significance Analysis, so this group of variables is used for it
	public float SA_activities[] = null;	
	public float SA_observedTargetScores[] = null;
	public float SA_targetScoreRandomDistribution[][] = null;
	public float SA_observedPathScores[] = null;
	public float SA_pathScoresRandomDistribution[][] = null;
	
	public int searchPathMode = SHORTEST_PATHS; 
	public static int SHORTEST_PATHS = 0;
	public static int SUBOPTIMAL_SHORTEST_PATHS = 1;
	public static int ALL_PATHS = 2;
	public double searchRadius = Double.MAX_VALUE;
	
	public static int PERMUTE_NODE_ACTIVITIES = 0;
	
	/**
	 * Ocsana search option: Berge's algorithm
	 */
	public static int OCS_BERGE = 0;
	/**
	 * Ocsana search option: partial search
	 */
	public static int OCS_PARTIAL = 1;
	/**
	 * Ocsana search option: seed based search
	 */
	public static int OCS_SEED = 2;
	
	/**
	 * Ocsana search option by default
	 */
	public int ocsSearch = OCS_BERGE;

	public Vector<Vector<Vector<Path>>> EnrichedNodePaths = new Vector<Vector<Vector<Path>>>(); 
	public Vector<Node> EnrichedNodes = new Vector<Node>(); // Those nodes with present attribute which are selected by user
	public Vector<Node> AllNodesWithActivities = new Vector<Node>(); // All nodes with non-zero attribute
	
	public static float defaultActivityThreshold = 0.5f;
	
	public String activityAttribute = "ACTIVITY";
	
	public boolean checkedForConsistency = false;
	
	public boolean useSignAlgebra = false;
	public boolean useAllnodesAttributes = false;
	
	private Random randomize = new Random();
	
	public HashMap<Node,Integer> nodeInconsistencies = new HashMap<Node,Integer>(); 
	
	/*
	 *  Optimal intervention set search data structures
	 */

	/**
	 * List of optimal intervention sets as node objects.
	 */
	public ArrayList<ArrayList<Node>> optIntSets = new ArrayList<ArrayList<Node>>();
	
	/**
	 * Ocsana list of "source" graphs
	 */
	public HashMap<String, Graph> sourceGraphs = new HashMap<String, Graph>();
	
	/**
	 * List of all paths from source nodes to target nodes
	 */
	private ArrayList<Path> allPaths = new ArrayList<Path>();
	
	/**
	 *  Score values are stored in an array, with a map [target x nodes] pointing to the indexes.
	 */
	private HashMap<Node, HashMap<Node, Integer>> scoreMap = new HashMap<Node, HashMap<Node, Integer>>();
	
	/**
	 * Score matrices for PIQUANT and OCSANA various scores.
	 */
	private double [] piquantScore;
	private double [] piquantScorePos;
	private double [] piquantScoreNeg;
	private double [] piquantSetScore;
	private HashMap<Node,Double> overallScore;
	
	/**
	 * System independent newline character
	 */
	public static String newline = System.getProperty("line.separator");
	
	/**
	 * List of source nodes
	 */
	public ArrayList<Node> sourceNodes = new ArrayList<Node>();
	
	/**
	 * List of target nodes
	 */
	public ArrayList<Node> targetNodes = new ArrayList<Node>();
	
	/**
	 * List of nodes to be considered for scoring side effects
	 */
	public ArrayList<Node> sideNodes = new ArrayList<Node>();

	/**
	 *  set of unique nodes from elementary paths
	 */
	public HashSet<Node> elemNodes = new HashSet<Node>();
	
	/**
	 * elementary paths, set of paths sinking to target nodes
	 */ 
	public ArrayList<Path> elemPaths = new ArrayList<Path>();
	
	/**
	 *  all individual nodes from elementary paths (target nodes excepted)
	 */
	public HashSet<Node> allNodes = new HashSet<Node>();

	/**
	 * Omega score per elementary node = OCSANA Score
	 */
	public HashMap<String, Double> omegaScoreMap = new HashMap<String, Double>();
	
	public HashMap<String, Double> piquantAbsSetScoreMap = new HashMap<String, Double>();
	public HashMap<String, Double> sideScoreMap = new HashMap<String, Double>();
	
	/**
	 * Ocsana Omega scores
	 */
	public ArrayList<OmegaScoreData> omegaScores = new ArrayList<OmegaScoreData>();
	
	/**
	 * Report for the optimal intervention set search
	 */
	public StringBuffer optCutSetReport = new StringBuffer();
	
	/**
	 * Maximum set number cutoff value
	 */
	public long maxSetNb;
	
	/**
	 * Maximum set size cutoff value.
	 */
	public int maxSetSize;
	
	/**
	 * Flag for using maximum set size
	 */
	public boolean useMaxSetSize;
	
	/**
	 * Flag for restricting Berge's algorithm search
	 */
	public boolean restrictBerge;
	
	/**
	 * Node Id to attribute value map
	 */
	public HashMap<String, String> nodeID2attribute;

	/**
	 * Elementary paths matrix
	 */
	public int[][] pathMatrix;
	
	/**
	 * Elementary paths matrix node names list
	 */
	public ArrayList<String> pathMatrixNodeList = new ArrayList<String>();
	
	/**
	 * Path matrix number of rows and columns
	 */
	public int pathMatrixNbRow, pathMatrixNbCol;
	
	/**
	 * List of optimal intervention sets with their scores
	 */
	private ArrayList<optCutSetData> optCutSetList;
	
	/**
	 * Simple data structure to store optimal intervention sets data
	 */
	public class optCutSetData implements Comparable {
		
		public HashSet<String> optCutSet;
		public double score;
		public double sumPiquantAbsSetScore;
		public double sumSideScore;
		public int optCutSetSize;
		
		public optCutSetData(HashSet<String> optCutSet, double score) {
			this.optCutSet = optCutSet; 
			this.score = score;
			this.optCutSetSize = optCutSet.size();
		}
		
		public optCutSetData(HashSet<String> optCutSet, double score, double sumPiquantAbsSetScore, double sumSideScore) {
			this.optCutSet = optCutSet; 
			this.score = score;
			this.optCutSetSize = optCutSet.size();
			this.sumPiquantAbsSetScore = sumPiquantAbsSetScore;
			this.sumSideScore = sumSideScore;
		}
		
		/*
		 * Sort by decreasing absolute value of the score.
		 */
		public int compareTo(Object b) {
			
			optCutSetData m = (optCutSetData) b;

			if(Math.abs(this.score) < Math.abs(m.score)) {
				return 1;
			} else if(Math.abs(this.score) == Math.abs(m.score)) {
				return 0;
			} else {
				return -1;
			}
		}
	}
	
	public void loadGraph(String file) throws Exception{
		GraphDocument gr = XGMML.loadFromXMGML(file);
		graph = XGMML.convertXGMMLToGraph(gr);
	}
	
	public void getPathwayNodes(){
		for(int i=0;i<graph.Nodes.size();i++){
			Node n = (Node)graph.Nodes.get(i);
 			Vector v = n.getAttributesWithSubstringInName("NODE_TYPE");
			if(v!=null)if(v.size()>0){
				String type = ((Attribute)v.get(0)).value;
				if(type.toLowerCase().equals("pathway")){
					pathwayNodes.add(n);
				}
			}
		}
	}
	
	public void calcPathProperties(Path p){
		p.length = p.graph.Nodes.size()-1;
		p.label = ((Node)p.graph.Nodes.get(p.source)).NodeLabel;
		int ind = p.source;
		p.influence = 1f;
		Vector visited = new Vector();
		visited.add((Node)p.graph.Nodes.get(p.source));
		p.nodeSequence.add((Node)p.graph.Nodes.get(p.source));
		while(ind!=p.target){
			StringBuffer effect = new StringBuffer(""); 
			Node n = getNextNode(p.graph,ind,effect,visited);
			Float sign = 1f;
			//System.out.println(effect.toString());
			if(n!=null){
				ind = p.graph.getNodeIndex(n.Id);
				String conn = "-";
				if(effect.toString().equalsIgnoreCase("activation")){
					conn = "->"; p.influence*=1f;
				}
				if(effect.toString().equalsIgnoreCase("inhibition")){
					conn = "-|"; p.influence*=-1f; sign = -1f;
				}
				if(conn.equals("-"))
					p.influence*=0f; sign = 0f;
				p.label+=conn+n.NodeLabel;
				visited.add(n);
				p.nodeSequence.add(n);
				//p.nodeSignSequence.add(sign);
			}else{
				break;
			}
		}
		if(p.target==-1){
			System.out.println("ERROR: p.target=-1 for "+p.label);
		}else
		if(!p.label.endsWith(((Node)p.graph.Nodes.get(p.target)).NodeLabel))
			System.out.println("WARNING!!!! Something wrong with "+p.label);
		p.label = Utils.replaceString(p.label, "@", "");
	}
	
	private Node getNextNode(Graph gr, int ind, StringBuffer effect, Vector visited){
		Node currentNode = (Node)gr.Nodes.get(ind);
		Node n = null;
		String overall = "";
		for(int i=0;i<gr.Edges.size();i++){
			Edge e = (Edge)gr.Edges.get(i);
			if(e.Node1.Id.equals(currentNode.Id))if(!e.Node2.Id.equals(currentNode.Id))if(visited.indexOf(e.Node2)<0){
				n = e.Node2;
				String ef = e.getFirstAttributeValue("EFFECT");
				
				if(ef!=null){
					ef = ef.toLowerCase();
					if(ef.indexOf("activation")>=0)if(effect.toString().equals("")){
						effect.append("activation"); overall+=effect.toString();
					}
					if(ef.indexOf("inhibition")>=0)if(effect.toString().equals("")){
						effect.append("inhibition"); overall+=effect.toString();
					}
				}else{
					ef = e.getFirstAttributeValue("interaction");
					
					if(ef!=null){
						ef = ef.toLowerCase();
						if(ef.indexOf("activation")>=0)if(effect.toString().equals("")){
							effect.append("activation"); overall+=effect.toString();
						}
						if(ef.indexOf("inhibition")>=0)if(effect.toString().equals("")){
							effect.append("inhibition"); overall+=effect.toString();
						}
					}					
				}
				Vector v = e.getAttributesWithSubstringInName("EDGE_TYPE");
				if(v.size()>0){
					ef = ((Attribute)v.get(0)).value.toLowerCase();
					if(ef!=null){
						if(ef.indexOf("activation")>=0)if(effect.toString().equals("")){
							effect.append("activation"); overall+=effect.toString();
						}
						if(ef.indexOf("inhibition")>=0)if(effect.toString().equals("")){
							effect.append("inhibition"); overall+=effect.toString();
						}
					}
				}
			}
		}
		if(!effect.toString().equals(overall))
			System.out.println("WARNING!!! Ambiguous connection "+currentNode.Id+"-"+n.Id);
		return n;
	}
	
	public float[] calcPValues(float scores[], float distributions[][]){
		float pv[] = new float[distributions.length];
		for(int i=0;i<pv.length;i++){
			float pvalleft = 0;
			float pvalright = 0;
			for(int j=0;j<distributions[i].length;j++)
				if(distributions[i][j]>=scores[i]) pvalright+=1f;
			for(int j=0;j<distributions[i].length;j++)
				if(distributions[i][j]<=scores[i]) pvalleft+=1f;
			float pval = Math.min(pvalleft,pvalright);
			pval/=distributions[i].length;
			pv[i] = pval;
		}
		return pv;
	}
	
	public float calcSpecialPValue(float scores[], float distributions[][]){
		float p = 0f;
		for(int i=0;i<distributions[1].length;i++){
				if(distributions[3][i]<=scores[3])if(distributions[4][i]>=scores[4]) 
					p+=1f;
		}
		return p/distributions[3].length;
	}
	
	
	public void findPaths(){
		for(int p=0;p<pathwayNodes.size();p++){
			
			Node pathwayNode = (Node)pathwayNodes.get(p);
			
			System.out.println("Pathway "+pathwayNode.NodeLabel);
			
			Vector<Vector<Path>> nodePaths = new Vector<Vector<Path>>();
			EnrichedNodePaths.add(nodePaths);
			
			for(int i=0;i<EnrichedNodes.size();i++){
				Node n = (Node)EnrichedNodes.get(i);
				Vector<Graph> pts = null;
				switch(searchPathMode){
				   case 0: pts = GraphAlgorithms.Dijkstra(graph, n, pathwayNode, true, false, searchRadius); break;
				   case 1: pts = GraphAlgorithms.Dijkstra(graph, n, pathwayNode, true, true, searchRadius); break;
				   case 2: Set<Node> targets = new HashSet<Node>(); targets.add(pathwayNode); pts = GraphAlgorithms.FindAllPaths(graph, n, targets, true, searchRadius); break; 
				}
				
				Vector<Path> vpaths = new Vector<Path>();
				nodePaths.add(vpaths);
				System.out.println(pts.size()+" paths found:");
				if(pts.size()>0){
					for(int j=0;j<pts.size();j++){
						Graph pth = pts.get(j);
						Path ppath = new Path(pth,n.Id);
						ppath.target = pth.getNodeIndex(pathwayNode.Id);
						calcPathProperties(ppath);
						vpaths.add(ppath);
						System.out.print("\t"+(j+1)+")");
						//for(int k=0;k<pth.Nodes.size();k++)
						//	System.out.print(pth.Nodes.get(k).Id+"-");
						System.out.println(ppath.label);
					}
				}
				System.out.println(n.Id+"\t"+vpaths.size()+" paths found.");
			}
		}
		System.out.println("Total "+pathwayNodes.size()+" pathways nodes");
		
	}
	
	public float calcPathInfluence(Path p){
		float res = 0;
		res = p.influence/p.length;
		return res;
	}
	
	public float calcAveragePathLength(Vector<Path> vpaths){
		float averageLength = 0f;
		int num = 0;
		for(int j=0;j<vpaths.size();j++){
			Path pth = vpaths.get(j);
			if(pth.selected){
				averageLength+=pth.length;
				num++;
			}
		}
		if(num!=0)
			averageLength/=num;
		return averageLength;
	}
	
	public float calcNodeInfluence(Node n, Vector<Path> vpaths, boolean verbose){

		DecimalFormat df = new DecimalFormat("#.##");
		float activity = getNodeActivity(n);
		float influence = calcNodeInfluence(activity,vpaths);
		
		if(verbose)
			System.out.println(Utils.replaceString(n.NodeLabel,"@","")+"\t"+df.format(activity)+"\tNumPath="+vpaths.size()+"\tAvPathLen="+calcAveragePathLength(vpaths)+"\tInfl="+df.format(influence)+"\tContribution="+df.format(influence*activity));
		
		return influence;
	}
	
	public float calcNodeInfluence(float nodeActivity, Vector<Path> vpaths){
		float influence = Float.NaN;
		int numOfSelectedPaths = 0;
		for(int j=0;j<vpaths.size();j++){
			Path pp = vpaths.get(j);
			if(pp.selected){
				numOfSelectedPaths++;
				if(!useSignAlgebra){
					if(Float.isNaN(influence)) 
							influence=pp.influence/pp.length;
						else
							influence+=pp.influence/pp.length;
				}else{
				if(Float.isNaN(influence)) influence=Math.signum(pp.influence);
					else{
						if(influence*pp.influence<=0) influence = 0f;
					}
				}
			}
		}
		if(numOfSelectedPaths==0) influence = 0f;
		return influence*nodeActivity;
	}	
	
	public float calcPathwayInfluence(Vector<Vector<Path>> vvpaths){
		float influence = Float.NaN;
		for(int i=0;i<vvpaths.size();i++){
			Vector<Path> vpath = vvpaths.get(i);
			float nodeInfl = calcNodeInfluence((Node)EnrichedNodes.get(i),vpath,false); 
			if(!useSignAlgebra){
				if(Float.isNaN(influence)) 
						influence=nodeInfl;
					else
						influence+=nodeInfl;
			}else{
			if(Float.isNaN(influence)) influence=Math.signum(nodeInfl);
				else{
					if(influence*nodeInfl<=0) influence = 0f;
				}
			}
		}
		return influence;
	}
	
	public float calcRandomPathwayInfluence(Vector<Vector<Path>> vvpaths, float activities[]){
		float influence = Float.NaN;
		for(int i=0;i<vvpaths.size();i++){
			Vector<Path> vpath = vvpaths.get(i);
			float nodeInfl = calcNodeInfluence(activities[i],vpath);
			if(!useSignAlgebra){
				if(Float.isNaN(influence)) 
						influence=nodeInfl;
					else
						influence+=nodeInfl;
			}else{
			if(Float.isNaN(influence)) influence=Math.signum(nodeInfl);
				else{
					if(influence*nodeInfl<=0) influence = 0f;
				}
			}
		}
		return influence;
	}	
	
	public float getNodeActivity(Node n){
		float activity = 0;
		try{
			activity = Float.parseFloat(n.getFirstAttributeValue(activityAttribute));
		}catch(Exception e){}
		return activity;
	}
	
	public float[] getActivityDistribution(){
		float activities[] = new float[EnrichedNodes.size()];
		for(int i=0;i<EnrichedNodes.size();i++){
			activities[i] = getNodeActivity((Node)EnrichedNodes.get(i));
		}
		return activities;
	}	
	
	public Graph makePathNetwork(Vector<Path> listOfPath){
		Graph gr = new Graph();
		for(int i=0;i<listOfPath.size();i++){
			Path p = listOfPath.get(i);
			if(p.selected){
				for(int j=0;j<p.graph.Nodes.size();j++){
					Node n = (Node)p.graph.Nodes.get(j);
					gr.addNode(n);
				}
			}
		}
		gr.addConnections(graph);
		return gr;
	}
	
	public String generateReport(){
		StringBuffer sb = new StringBuffer();
		DecimalFormat df = new DecimalFormat("##.##"); 
		//HashMap<Node,Vector<Path>> target_paths = new HashMap<Node,Vector<Path>>();
		for(int i=0;i<pathwayNodes.size();i++){
			Node pathway = (Node)pathwayNodes.get(i);
			print("------------------------------------------------\n",sb);
			Vector<Vector<Path>> listOfNodes = EnrichedNodePaths.get(i);
			print("Target:\t"+pathway.Id+"\tInfl="+df.format(this.calcPathwayInfluence(listOfNodes))+"\n",sb);
			for(int j=0;j<listOfNodes.size();j++){
				Vector<Path> vpath = listOfNodes.get(j);
				
				if(vpath.size()>0){
					Node node = (Node)EnrichedNodes.get(j);
					print(node.Id+"\tAct="+df.format(getNodeActivity(node))+"\tInfl="+df.format(calcNodeInfluence(node, vpath, false))+"\tAvLen="+df.format(calcAveragePathLength(vpath))+"\n",sb);
					int count = 1;
					for(int k=0;k<vpath.size();k++){
						Path p = vpath.get(k);
						if(p.selected){
							print("\t"+count+"\t"+df.format(calcPathInfluence(p))+"\t"+p.label+"\n",sb);
							count++;
						}
					}
				}
			}
			print("------------------------------------------------\n",sb);
			print("\n",sb);
		}
		// List of systematically inconsistent nodes
		sortPathsByActivities();
		Vector<Node> listOfSystematicallyInconsistentNodes = getSystematicallyInconsistentNodes();
		print("List of systematically inconsistent nodes ("+listOfSystematicallyInconsistentNodes.size()+")\n",sb);
		print("=============================================\n",sb);
		for(int i=0;i<listOfSystematicallyInconsistentNodes.size();i++){
			Node n = listOfSystematicallyInconsistentNodes.get(i);
			print(n.Id+"\n",sb);
		}
		print("\n",sb);
		
		// Node influence table
		print("Node influence table\n",sb);
		print("====================\n",sb);
		print("NODEID\t",sb);
			for(int i=0;i<pathwayNodes.size();i++){
				print(((Node)pathwayNodes.get(i)).Id+"\t",sb);
			}
		print("\n",sb);

		for(int j=0;j<EnrichedNodes.size();j++){
			Node node = (Node)EnrichedNodes.get(j);
			print(node.Id+"\t",sb);
			for(int i=0;i<pathwayNodes.size();i++){
				Vector<Path> vpath = EnrichedNodePaths.get(i).get(j);
				print(df.format(calcNodeInfluence(node, vpath, false))+"\t",sb);
			}
			print("\n",sb);
		}
		print("\n",sb);
		
		print("Summary table\n",sb);
		print("=============\n",sb);
		if(SA_targetScoreRandomDistribution!=null)
			print("TARGET\tSUMMARY_INFLUENCE\tPVALUE\n",sb);
		else
			print("TARGET\tSUMMARY_INFLUENCE\n",sb);
		float pvalues[] = new float[pathwayNodes.size()];
		if(SA_targetScoreRandomDistribution!=null){
			pvalues = calcPValues(SA_observedTargetScores,SA_targetScoreRandomDistribution);
		}
		for(int i=0;i<pathwayNodes.size();i++){
			Node pathway = (Node)pathwayNodes.get(i);
			Vector<Vector<Path>> listOfNodes = EnrichedNodePaths.get(i);
			print(pathway.Id+"\t"+df.format(this.calcPathwayInfluence(listOfNodes)),sb);
			if(SA_targetScoreRandomDistribution!=null){
				print("\t"+pvalues[i],sb);
			}
			print("\n",sb);
		}
		
		return sb.toString();
	}
	
	public void print(String line, StringBuffer sb){
		sb.append(line);
		System.out.print(line);
	}
	
	public Vector<Path> sortPathsByActivities(){
		return sortPathsByActivities(null);
	}
	
	public Vector<Path> sortPathsByActivities(float randomNodeActivities[]){
		Vector<Path> temp = new Vector<Path>();
		for(int i=0;i<pathwayNodes.size();i++){
			Node pathway = (Node)pathwayNodes.get(i);
			Vector<Vector<Path>> listOfNodes = EnrichedNodePaths.get(i);
			for(int j=0;j<listOfNodes.size();j++){
				Vector<Path> vpath = listOfNodes.get(j);
				for(int k=0;k<vpath.size();k++){
					Path p = vpath.get(k);
					if(p.selected){
						temp.add(p);
					}
				}
			}
		}
		nodeInconsistencies.clear();
		float activities[] = new float[temp.size()];
		for(int i=0;i<temp.size();i++){
			getPathConsistencyAndSummaryActivity(temp.get(i),randomNodeActivities);
			activities[i] = Math.abs(temp.get(i).summaryActivity);
		}
		int inds[] = Utils.SortMass(activities);
		Vector<Path> res = new Vector<Path>();		
		for(int i=0;i<inds.length;i++)
			res.add(temp.get(inds[inds.length-1-i]));
		checkedForConsistency = true;
		return res;
	}
	
	public void getPathConsistencyAndSummaryActivity(Path p){
		getPathConsistencyAndSummaryActivity(p,null);
	}
	
	public void getPathConsistencyAndSummaryActivity(Path p, float randomNodeActivities[]){
		float activity = 0;
		int consCheck = 0;
		p.consistency = 1;
		p.summaryActivity = 0f;
		p.numberOfActiveNodes = 0;
		String targetId = ((Node)p.graph.Nodes.get(p.target)).Id;
		for(int i=0;i<p.nodeSequence.size()-1;i++){
			Node n = p.nodeSequence.get(i);
			Vector<Node> NodesToCheckForConsistency = new Vector<Node>();
			if(useAllnodesAttributes) 
				NodesToCheckForConsistency = AllNodesWithActivities;
			else
				NodesToCheckForConsistency = EnrichedNodes; 
			if(NodesToCheckForConsistency.indexOf(n)>=0){
				p.numberOfActiveNodes++;
				Path subPath = new Path();
				subPath.graph = new Graph();
				for(int j=i;j<p.nodeSequence.size();j++)
					subPath.graph.addNode(p.nodeSequence.get(j));
				subPath.graph.addConnections(p.graph);
				subPath.source = subPath.graph.getNodeIndex(n.Id);
				subPath.target = subPath.graph.getNodeIndex(targetId);
				if((subPath.source>=0)&&(subPath.target>=0)){
					calcPathProperties(subPath);
					float nodeActivity = getNodeActivity(n);
					if(randomNodeActivities!=null){
						nodeActivity = randomNodeActivities[EnrichedNodes.indexOf(n)];
					}
					float subInfluence = nodeActivity*subPath.influence/subPath.length;
					activity+=subInfluence;
					if(subInfluence!=0)if(p.consistency>0){
						if(consCheck==0) consCheck = subInfluence>0?1:-1;
						if((subInfluence>0)&&(consCheck<0)) p.consistency = -1;
						if((subInfluence<0)&&(consCheck>0)) p.consistency = -1;
					}
					Integer inconc  = nodeInconsistencies.get(n);
					if(inconc==null){
						nodeInconsistencies.put(n,p.consistency);
					}else{
						if(p.consistency>0) 
							nodeInconsistencies.put(n,1);
					}
				}else{
					System.out.println("ERROR!!! Source or target not found: src="+subPath.source+" trg="+subPath.target+", in:"+p.label+", subpath from "+n.Id);
				}
			}
		}
		p.summaryActivity = activity;
	}
	
	public String generatePathActivitiesReport(){
		StringBuffer sb = new StringBuffer();
		DecimalFormat df = new DecimalFormat("##.##");
		Vector<Path> vpath = sortPathsByActivities();
		if(SA_pathScoresRandomDistribution!=null)
			print("SUMACTIVITY\tPVALUE\tNACTIVENODES\tCONSISTENCY\tPATH\n",sb);
		else
			print("SUMACTIVITY\tNACTIVENODES\tCONSISTENCY\tPATH\n",sb);
		for(int i=0;i<vpath.size();i++){
			Path p = vpath.get(i);

			if(SA_pathScoresRandomDistribution!=null){			
				float sumActivity[] = new float[1]; 
				sumActivity[0] = p.summaryActivity;
				float pval[] = calcPValues(sumActivity, SA_pathScoresRandomDistribution);
				print(df.format(p.summaryActivity)+"\t"+pval[0]+"\t"+p.numberOfActiveNodes+"\t"+p.consistency+"\t"+p.label+"\n",sb);
			}else
				print(df.format(p.summaryActivity)+"\t"+p.numberOfActiveNodes+"\t"+p.consistency+"\t"+p.label+"\n",sb);
		}
		return sb.toString();
	}
	
	
	public void initializeSignificanceAnalysis(){
		
		if(testSignificanceMode==PERMUTE_NODE_ACTIVITIES){
			SA_observedTargetScores = new float[pathwayNodes.size()];
			SA_targetScoreRandomDistribution = new float[pathwayNodes.size()][numberOfPermutations];
			
			SA_activities = getActivityDistribution();
			
			Vector<Path> sortedPaths = sortPathsByActivities(null);
			SA_observedPathScores = new float[sortedPaths.size()];
			SA_pathScoresRandomDistribution = new float[1][sortedPaths.size()*numberOfPermutations];
			
			for(int i=0;i<pathwayNodes.size();i++){
				Node pathway = (Node)pathwayNodes.get(i);
				Vector<Vector<Path>> listOfNodes = EnrichedNodePaths.get(i);
				SA_observedTargetScores[i] = calcPathwayInfluence(listOfNodes);
			}			
			
			for(int i=0;i<sortedPaths.size();i++){
				Path p = sortedPaths.get(i);
				SA_observedPathScores[i] = p.summaryActivity;
			}
		}
	}
	
	public void significanceAnalysisDoPermutation(int k){
		
	if(testSignificanceMode==PERMUTE_NODE_ACTIVITIES){		
		float randomActivities[] = new float[SA_activities.length];
		
		for(int i=0;i<SA_activities.length;i++){
			randomActivities[i] = SA_activities[randomize.nextInt(SA_activities.length)];  
		}
		
		for(int i=0;i<pathwayNodes.size();i++){
			Node pathway = (Node)pathwayNodes.get(i);
			Vector<Vector<Path>> listOfNodes = EnrichedNodePaths.get(i);
			SA_targetScoreRandomDistribution[i][k] = calcRandomPathwayInfluence(listOfNodes,randomActivities);
		}
		Vector<Path> sortedPaths = sortPathsByActivities(randomActivities);
		for(int i=0;i<sortedPaths.size();i++){
			Path p = sortedPaths.get(i);
			SA_pathScoresRandomDistribution[0][k*sortedPaths.size()+i] = p.summaryActivity;
		}
	}
	}
	
	public void printSignificanceAnalysisReport(){
		
		DecimalFormat df = new DecimalFormat("##.##");
		
		Vector<Path> sortedPaths = sortPathsByActivities(null);
		System.out.println("============================================================\n");
		System.out.println("PATH_SCORE\tPVALUE\tNACTIVENODES\tCONSISTENCY\tPATH\n");
		for(int i=0;i<sortedPaths.size();i++){
			Path p = sortedPaths.get(i);
			float sumActivity[] = new float[1]; 
			sumActivity[0] = p.summaryActivity;
			float pval[] = calcPValues(sumActivity, SA_pathScoresRandomDistribution);
			System.out.println(df.format(p.summaryActivity)+"\t"+pval[0]+"\t"+p.numberOfActiveNodes+"\t"+p.consistency+"\t"+p.label);
		}
		
		float pvalues[] = calcPValues(SA_observedTargetScores, SA_targetScoreRandomDistribution);
		
		for(int k=0;k<pvalues.length;k++){
			Node pathway = (Node)pathwayNodes.get(k);
			System.out.println(pathway.Id+"\t"+SA_observedTargetScores[k]+"\t"+pvalues[k]);
		}
		
		System.out.println("S<,A>\t"+calcSpecialPValue(SA_observedTargetScores, SA_targetScoreRandomDistribution));		
	}
		
	
	public void significanceAnalysis(int mode, int _numberOfPermutations){

		testSignificanceMode = mode;
		numberOfPermutations = _numberOfPermutations;
		initializeSignificanceAnalysis();
			
			for(int k=0;k<numberOfPermutations;k++){
				if(k==Math.round(10*(int)(0.1f*k)))
					System.out.print(k+"\t");
				significanceAnalysisDoPermutation(k);
			}
			System.out.println();
			
			printSignificanceAnalysisReport();
	}
	
	public float[][] calcAllToAllInfluences(){
		float inf[][] = new float[graph.Nodes.size()][graph.Nodes.size()];
		pathwayNodes = graph.Nodes;
		EnrichedNodes = graph.Nodes;
		findPaths();
		for(int j=0;j<pathwayNodes.size();j++){
			Vector<Vector<Path>> nodePath = EnrichedNodePaths.get(j);
			for(int i=0;i<EnrichedNodes.size();i++){
				Vector<Path> paths = nodePath.get(i);
				for(int k=0;k<paths.size();k++)
					inf[i][j] += paths.get(k).influence/paths.get(k).length;
			}
		}
		return inf;
	}
	
	public String printAllToAllInfluenceTable(float inf[][]){
		StringBuffer sb = new StringBuffer();
		sb.append("NODE\t");
		for(int i=0;i<graph.Nodes.size();i++){
			sb.append(graph.Nodes.get(i).Id+"\t");
		}
		sb.append("\n");
		for(int j=0;j<graph.Nodes.size();j++){
			sb.append(graph.Nodes.get(j).Id+"\t");
			for(int i=0;i<graph.Nodes.size();i++){
				sb.append(inf[j][i]+"\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public Vector<Node> getSystematicallyInconsistentNodes(){
		Vector<Node> list = new Vector<Node>();
		Iterator<Node> nodes = nodeInconsistencies.keySet().iterator();
		while(nodes.hasNext()){
			Node n = nodes.next();
			Integer inc = nodeInconsistencies.get(n);
			if(inc<0)
				list.add(n);
		}
		return list;
	}
	
	/**
	 * Determine paths for optimal combinations sets analysis and calculate the scores
	 * for elementary nodes.
	 * 
	 * @author ebo
	 */
	public boolean ocsanaScore() {
		
		this.optCutSetReport.append("--- Optimal Combinations of Interventions Report ---"+newline+newline);
		this.optCutSetReport.append("OPTIONS"+newline+newline);
		
		String line = "";
		for (Node n : this.sourceNodes)
			if (nodeID2attribute == null)
				line += n.Id + " ";
			else
				line += n.Id + "(" + nodeID2attribute.get(n.Id) + ")" + " ";
		
		this.optCutSetReport.append("Source nodes: "+line+newline);
		
		line = "";
		for (Node n : this.targetNodes)
			if (nodeID2attribute == null)
				line += n.Id + " ";
			else
				line += n.Id + "(" + nodeID2attribute.get(n.Id) + ")" + " ";
		this.optCutSetReport.append("Target nodes: "+line+newline);
		
		line = "";
		for (Node n : this.sideNodes)
			if (nodeID2attribute == null)
				line += n.Id + " ";
			else
				line += n.Id + "(" + nodeID2attribute.get(n.Id) + ")" + " ";
		this.optCutSetReport.append("Side effect nodes: "+line+newline+newline);
		
		if (this.searchPathMode == 0)
			line = "Shortest paths";
		else if (this.searchPathMode == 1)
			line = "Optimal and suboptimal shortest paths";
		else if (this.searchPathMode == 2)
			line = "All non-intersecting paths";
		
		this.optCutSetReport.append("Path search algorithm: "+line+newline);
		String radStr = "inf";
		if (this.searchRadius < 1e300)
			radStr = Double.toString(this.searchRadius);
		this.optCutSetReport.append("Finite search radius for All non-intersecting paths: "+radStr+newline+newline);
		
		if (ocsSearch == OCS_BERGE)
			this.optCutSetReport.append("CI algorithm selected: Exact solution (Berge's algorithm)"+newline+newline);
		else { 
			this.optCutSetReport.append("CI algorithm selected: Selective enumeration"+newline);
			long maxNb = this.maxSetNb / 1000000;
			this.optCutSetReport.append("Max. Nb of million of combinations to test: "+ maxNb + newline+newline);
		}
		this.optCutSetReport.append("RESULTS"+ newline+newline);
		
		// convert every "neutral" edge to activation
		int nbConversions = this.checkEdgeInfluence();
		this.optCutSetReport.append("Modifications to the network: "+nbConversions+" undefined effect edges were converted to activation effect edges out of "+this.graph.Edges.size()+"\n\n");
		System.out.println(nbConversions+" edges were converted to activation out of "+this.graph.Edges.size()+"\n\n");
		
		ArrayList<Node> outputNodes = new ArrayList<Node>();
		
		// output nodes are the union of target and side-effect nodes
		for (Node n : targetNodes)
			outputNodes.add(n);

		for (Node n : sideNodes)
			outputNodes.add(n);
		
		// simple data structure to select unique paths
		HashSet<String> nodeSequence = new HashSet<String>();

		// all paths split in individual components e.g. A->B->C: A->B->C + B->C 
		ArrayList<Path> splitPaths = new ArrayList<Path>();

		// search paths between source and output nodes
		for (Node so : sourceNodes) {
			for (Node out : outputNodes) {
				
				Vector<Graph> pts = null;
				
				switch(searchPathMode){
				case 0: 
					pts = GraphAlgorithms.Dijkstra(graph, so, out, true, false, searchRadius); 
					break;
				case 1: 
					pts = GraphAlgorithms.Dijkstra(graph, so, out, true, true, searchRadius); 
					break;
				case 2: 
					Set<Node> targets = new HashSet<Node>(); 
					targets.add(out); 
					pts = GraphAlgorithms.FindAllPaths(graph, so, targets, true, searchRadius); 
					break; 
				}
				
				for (Graph g : pts) {
					Path p = new Path();
					p.graph = new Graph();
					for (int j=0;j<g.Nodes.size();j++) {
						p.graph.addNode(g.Nodes.get(j));
					}
					p.graph.addConnections(g);
					p.source = p.graph.getNodeIndex(so.Id);
					p.target = p.graph.getNodeIndex(out.Id);
					// case 2 (non-intersecting paths can give incomplete paths, where target is missing, so check this out
					if (p.source != -1 && p.target != -1) {
						calcPathProperties(p);
						if (nodeSequence.contains(p.label) == false) {
							nodeSequence.add(p.label);
							allPaths.add(p);
							//System.out.println(">>> all paths "+p.label);
						}
					}
				}
			}
		}
		
		// check if every source node is reaching a target node in the paths
		ArrayList<String> missing = new ArrayList<String>();
		HashMap<Node,HashSet<Node>> check = new HashMap<Node,HashSet<Node>>();
		for (Node n : sourceNodes)
			check.put(n, new HashSet<Node>());
		
		for (Path p : allPaths) {
			Node source = p.nodeSequence.get(0);
			Node target = p.nodeSequence.get(p.nodeSequence.size()-1);
			check.get(source).add(target);
		}
		
		for (Node s : sourceNodes) {
			for (Node t : outputNodes) {
				if (!check.get(s).contains(t)) {
					missing.add(s.Id + " --/--> " + t.Id);
				}
			}
		}
		
		if (missing.size() > 0) {
			this.optCutSetReport.append(newline+"Warning: No pathways were found for the specified parameters between:"+newline);
			for (String str : missing)
				this.optCutSetReport.append(str+newline);
			this.optCutSetReport.append(newline);
		}
		
		// check that side effects nodes do not overlap with source nodes (i.e. idiot proof )
		for (Node s : sideNodes) {
			if (sourceNodes.contains(s) || targetNodes.contains(s)) {
				this.optCutSetReport.append("Fatal error: side effect node "+s.Id+" is also a source node or a target node."+newline);
				this.optCutSetReport.append(newline+"Search stopped."+newline);
				return false;
			}
		}
		// idiot-proof reloaded: check that source node is not a target node
		for (Node s : sourceNodes) {
			if (targetNodes.contains(s)) {
				this.optCutSetReport.append("Fatal error: target node "+s.Id+" is also a source node."+newline);
				this.optCutSetReport.append(newline+"Search stopped."+newline);
				return false;
			}
		}
		this.optCutSetReport.append(newline);
		
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
				calcPathProperties(pa);
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

		// same but split in individual components
		ArrayList<Path> elemSplitPaths = new ArrayList<Path>();

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

		fillPathMatrix(elemPaths);
		
		this.optCutSetReport.append("Found "+elemPaths.size()+" elementary paths and "+elemNodes.size()+" elementary nodes"+newline+newline);
		System.out.println("Found "+elemPaths.size()+" elementary paths and "+elemNodes.size()+" elementary nodes.");
		
		for (Path p : elemPaths) {
			if (nodeID2attribute == null) {
				this.optCutSetReport.append(p.label+newline);
				System.out.println(p.label);
			}
			else {
				this.optCutSetReport.append(formatPathLabel(p)+newline);
				System.out.println(formatPathLabel(p));
			}
		}
		for (Node n : targetNodes) {
			for (Path p : splitPaths) {
				if (p.nodeSequence.get(p.target) == n) {
					elemSplitPaths.add(p);
				}
			}
		}
		this.optCutSetReport.append(newline);

		int ct = 0;
		
		/*
		 * Initialize various score matrices
		 */
		piquantScore = new double[targetNodes.size() * allNodes.size()];
		piquantScorePos = new double[targetNodes.size() * allNodes.size()];
		piquantScoreNeg = new double[targetNodes.size() * allNodes.size()];
//		piquantAbsScore = new double[targetNodes.size() * allNodes.size()];
//		piquantAbsSetScore = new double[targetNodes.size() * allNodes.size()];
		piquantSetScore = new double[targetNodes.size() * allNodes.size()];
//		sideScore = new double[targetNodes.size() * allNodes.size()];
//		overallScore = new double[targetNodes.size() * allNodes.size()];
//		ocsanaScore = new double[targetNodes.size() * allNodes.size()];
		
		/*
		 * OCSANA score (omega)
		 */
//		ocsanaScore = new double[targetNodes.size() * allNodes.size()];
		
		for (Node t : targetNodes)
			scoreMap.put(t, new HashMap<Node, Integer>());
		
		for (Node t : targetNodes)
			for (Node s : elemNodes) {
				//System.out.println(t.Id+"--"+s.Id);
				scoreMap.get(t).put(s, ct);
				piquantScore[ct] = 0.0;
				piquantScorePos[ct] = 0.0;
				piquantScoreNeg[ct] = 0.0;
//				piquantAbsScore[ct] = 0.0;
//				piquantAbsSetScore[ct] = 0.0;
				piquantSetScore[ct] = 0.0;
//				sideScore[ct] = 0.0;
//				overallScore[ct] = 0.0;
//				ocsanaScore[ct] = 0.0;
				ct++;
			}

		// PIQUANT score
		for (Path p : elemSplitPaths) {
			Node target = p.nodeSequence.get(p.target);
			Node source = p.nodeSequence.get(p.source);
			int idx = scoreMap.get(target).get(source);
			double sco = 0.0;
			sco = p.influence / p.length;
			piquantScore[idx] += sco;
			if (sco > 0)
				piquantScorePos[idx] += sco;
			else 
				piquantScoreNeg[idx] += sco;
		}

		// side effects score
		for (Node source : elemNodes) {
			double sco = 0.0;
			for (Node side : sideNodes) {
				for (Path ps : splitPaths) {
					Node split_target = ps.nodeSequence.get(ps.target);
					Node split_source = ps.nodeSequence.get(ps.source); 
					if (split_target == side && split_source == source) {
						sco +=  Math.abs(ps.influence / ps.length);
					}
				}
			}
			sideScoreMap.put(source.Id, sco);
		}

		// normalization factors
		HashMap<Node, HashSet<Node>> numTargetFactor = new HashMap<Node, HashSet<Node>>();
		HashMap<Node, HashSet<Node>> numSideFactor = new HashMap<Node, HashSet<Node>>();
		HashSet<Node> allReachableTarget = new HashSet<Node>();
		HashSet<Node> allReachableSide = new HashSet<Node>();
		
		for (Node n : elemNodes) {
			numTargetFactor.put(n, new HashSet<Node>());
			numSideFactor.put(n, new HashSet<Node>());
		}
		
		for (Path p : elemSplitPaths) {
			Node target = p.nodeSequence.get(p.target);
			Node source = p.nodeSequence.get(p.source);
			numTargetFactor.get(source).add(target);
			allReachableTarget.add(target);
		}
		
		for (Node source : elemNodes) {
				for (Node side : sideNodes) {
					for (Path ps : splitPaths) {
						Node split_target = ps.nodeSequence.get(ps.target);
						Node split_source = ps.nodeSequence.get(ps.source); 
						if (split_target == side && split_source == source) {
							numSideFactor.get(source).add(side);
							allReachableSide.add(side);
					}
				}
			}
		}
		
		// calculate target and side scaling factors
		HashMap<Node, Double> targetFactor = new HashMap<Node, Double>();
		HashMap<Node, Double> sideFactor = new HashMap<Node, Double>();
		for (Node e : elemNodes) {
			
			double target_factor;
			double side_factor;
			double xi = (double) numTargetFactor.get(e).size();;
			double x = 1.0;
			if (allReachableTarget.size()>0)
				x = (double) allReachableTarget.size();
			double yi = (double) numSideFactor.get(e).size();
			double y = 1;
			if (allReachableSide.size()>0)
				y = (double) allReachableSide.size();
			
			target_factor = xi / x;
			side_factor = yi / y;
				
			targetFactor.put(e, target_factor);
			sideFactor.put(e, side_factor);
		}
		
		overallScore = new HashMap<Node,Double>();
		
		// Absolute value of the PIQUANT score and OVERALL score
		for (Node s : elemNodes) {
			double sumPiquant = 0.0; 
			for (Node t : targetNodes) {
				int idx = scoreMap.get(t).get(s);
//				piquantAbsScore[idx] = Math.abs(piquantScore[idx]);

				sumPiquant += piquantScore[idx];
				//overallScore[idx] = (targetFactor.get(s) * piquantAbsScore[idx]) - (sideFactor.get(s) * sideScoreMap.get(s.Id));
				//System.out.println("elemNode="+s.Id+" targetNode="+t.Id+" targetFactor="+targetFactor+" sidefactor="+sideFactor+" piquantAbsScore="+piquantAbsScore[idx]+" sideScore="+sideScore[idx]+" overallScore="+overallScore[idx]);
				//if (overallScore[idx] < 0)
				//	overallScore[idx] = 0;
			}
			double overall_score = (targetFactor.get(s) * Math.abs(sumPiquant)) - (sideFactor.get(s) * sideScoreMap.get(s.Id));
			//System.out.println(">>> elemNode="+s.Id+" targetFactor="+targetFactor.get(s)+" abs(sumPiquant)="+Math.abs(sumPiquant)+" sideFactor="+sideFactor.get(s)+" sideeffect="+sideScoreMap.get(s.Id));
			if (overall_score < 0)
				overall_score = 0.0;
			overallScore.put(s, overall_score);
			piquantAbsSetScoreMap.put(s.Id, Math.abs(sumPiquant));
		}
		
		// create "source graphs" to be used to create Cytoscape networks 
		createCytoscapeNet();
		
		System.out.println("\nPIQUANT Scores:");
		DecimalFormat df = new DecimalFormat("#.###");
		for (Node t : targetNodes)
			for (Node s : elemNodes) {
				int idx = scoreMap.get(t).get(s);
				if (nodeID2attribute == null)
					System.out.println(t.Id+"-"+s.Id+" = "+df.format(piquantScore[idx]) );
				else
					System.out.println(t.Id+"("+nodeID2attribute.get(t.Id)+")"+"-"+s.Id+"("+nodeID2attribute.get(s.Id)+")"+" = "+df.format(piquantScore[idx]));
			}
	
		/*
		 * Calculate Omega scores for all elementary nodes (=OCSANA score)
		 */
		HashMap<Node,ArrayList<HashSet<String>>> elemPathsByTarget = new HashMap<Node, ArrayList<HashSet<String>>>();
		for (Node t : targetNodes)
			elemPathsByTarget.put(t, new ArrayList<HashSet<String>>());

		for (Path p : elemPaths) {
			Node target = p.nodeSequence.get(p.target);
			elemPathsByTarget.get(target).add(new HashSet<String>());
			int last = elemPathsByTarget.get(target).size()-1;
			for (Node n : p.nodeSequence)
				elemPathsByTarget.get(target).get(last).add(n.Id);
		}
		
		for (Node source : elemNodes) {
			int sumSetScore = 0;
			for (Node target : targetNodes) {
				int factor = 0;
				for (int i=0;i<elemPathsByTarget.get(target).size();i++) {
					if (elemPathsByTarget.get(target).get(i).contains(source.Id))
						factor++;
				}
				//System.out.println("elem node: "+source.Id+" factor: "+factor);
				int idx = scoreMap.get(target).get(source);
				//double sco = factor * overallScore[idx];
				//ocsanaScore[idx] = sco;
				//sumOmegaScore += sco;
				sumSetScore += factor;
				// |piquant| * set 
				//piquantAbsSetScore[idx] = piquantAbsScore[idx] * factor;
				// 1/x * |piquant| * set
				//piquantAbsSetScore[idx] = piquantAbsSetScore[idx] * targetFactor.get(source);
				//sumPiquantAbsSetScore += piquantAbsSetScore[idx]; 
				
				//piquantSetScore[idx] = piquantScore[idx] * factor;
			}
			
			// piquant x SET score
			for (Node target : targetNodes) {
				int idx = scoreMap.get(target).get(source);
				piquantSetScore[idx] = piquantScore[idx] * sumSetScore;
			}
			
			//calculate Ocsana score
			double ocsana_score = overallScore.get(source) * (double) sumSetScore;
			omegaScoreMap.put(source.Id, ocsana_score);
			
			// calculate yi/y * side-effect * set score => display in the report
			double val = sideScoreMap.get(source.Id);
			val = sideFactor.get(source) * val * (double) sumSetScore;
			sideScoreMap.put(source.Id, val);
			
			// calculate xi/x * |piquant| * set score => display in the report
			val = piquantAbsSetScoreMap.get(source.Id);
			val = targetFactor.get(source) * val * (double) sumSetScore;
			piquantAbsSetScoreMap.put(source.Id, val);
		}
		
		for (String id : omegaScoreMap.keySet()) {
			omegaScores.add(new OmegaScoreData(id, omegaScoreMap.get(id)));
		}
		Collections.sort(omegaScores);
		
		return true;
	}
	
	/**
	 * Performs optimal intervention set search.
	 * Ocsana scores should have been called before.
	 * 
	 * @author ebo
	 */
	public void ocsanaSearch() {
		
		/*
		 * determine paths and calculate scores for elementary nodes
		 */
		if (ocsanaScore() == false)
			return;
		
		/*
		 * create ocs object and set data
		 */
		OptimalCombinationAnalyzer oca = new OptimalCombinationAnalyzer();
		oca.pathMatrix = pathMatrix;
		oca.pathMatrixNbCol = pathMatrixNbCol;
		oca.pathMatrixNbRow = pathMatrixNbRow;
		oca.pathMatrixNodeList = pathMatrixNodeList;
		oca.omegaScoreList = omegaScores;
		oca.report = optCutSetReport;
		
		// initialization of the list of nodes ordered by score
		oca.initOrderedNodesList();
		
		// check exception 1 nodes and put them aside
		oca.checkRows();
		
		// search for hitting sets size 1 and put them aside
		oca.searchHitSetSizeOne();
		
		// convert path matrix to BitSet objects; both rows and columns
		oca.convertPathMatrixColToBinary();
		oca.convertPathMatrixRowToBinary();
		
		if (ocsSearch == OCS_BERGE) {
			/*
			 * Full search with Berge's algorithm
			 */
			if (this.restrictBerge == true) {
				this.optCutSetReport.append("Selecting intervention sets having a size <= "+this.maxSetSize+newline);
				/*
				 * take into account exception nodes for the max set size cutoff
				 */
				if (oca.exceptionNode.size()>0) {
					System.out.println("Correcting max. intervention set size for exception nodes ("+oca.exceptionNode.size()+").");
					this.maxSetSize = this.maxSetSize - oca.exceptionNode.size();
				}
				oca.restrictBerge = true;
				oca.maxHitSetSize = this.maxSetSize;
			}
			oca.mainBerge(true);
			
			
//			if (this.useMaxSetSize == true) {
//				this.optCutSetReport.append("Selecting intervention sets having a size <= "+this.maxSetSize+newline);
//				/*
//				 * take into account exception nodes for the max set size cutoff
//				 */
//				if (oca.exceptionNode.size()>0)
//					oca.selectHitSetSize(this.maxSetSize - oca.exceptionNode.size());
//				else 
//					oca.selectHitSetSize(this.maxSetSize);
//			}
		}
		else if (ocsSearch == OCS_PARTIAL) {
			/*
			 * Enumeration approach
			 */
			
			/*
			 * take into account exception nodes for the max set size cutoff
			 */
			if (oca.exceptionNode.size()>0) {
				System.out.println("Correcting max. intervention set size for exception nodes ("+oca.exceptionNode.size()+").");
				this.maxSetSize = this.maxSetSize - oca.exceptionNode.size();
			}
		
			oca.maxHitSetSize = this.maxSetSize;
			oca.maxNbHitSet = this.maxSetNb;
			oca.searchHitSetPartial();
		}
		else if (ocsSearch == OCS_SEED) {
			/*
			 * Seed based enumeration approach
			 */
			
			/*
			 * take into account exception nodes for the max set size cutoff
			 */
			if (oca.exceptionNode.size()>0) {
				System.out.println("Correcting max. intervention set size for exception nodes("+oca.exceptionNode.size()+").");
				this.maxSetSize = this.maxSetSize - oca.exceptionNode.size();
			}
			
			oca.maxHitSetSize = this.maxSetSize;
			oca.maxNbHitSet = this.maxSetNb;
			oca.searchHitSetSeed();
		}
		
		this.optCutSetReport.append(newline);
		
		ArrayList<HashSet<String>> hitSet = oca.formatHitSetSB();
		
		// calculate the score for each set and order them accordingly
		optCutSetList = new ArrayList<optCutSetData>();
		
		for (HashSet<String> set : hitSet) {
			double sumOmegaScore = 0.0;
			double sumPiquantAbsSetScore = 0.0;
			double sumSideScore = 0.0;
			for (String nodeID : set) {
				sumOmegaScore += omegaScoreMap.get(nodeID);
				sumPiquantAbsSetScore += piquantAbsSetScoreMap.get(nodeID);
				sumSideScore += sideScoreMap.get(nodeID);
			}
			optCutSetList.add(new optCutSetData(set, sumOmegaScore, sumPiquantAbsSetScore, sumSideScore));
		}
		
		Collections.sort(optCutSetList);

		this.optCutSetReport.append("Found " + optCutSetList.size() + " optimal CIs."+newline+newline);
		
		//this.optCutSetReport.append("Optimal CI\tSize\tOCSANA score\t|PIQUANT score|*Set score\tSideEffects score"+newline+newline);
		this.optCutSetReport.append("Optimal CI\tSize\tOCSANA score of the CI sets\txi/x*|EFFECT_ON_TARGETS|*SET score of the whole CI set\tyi/y*SIDE-EFFECT*SET of the whole CI set"+newline+newline);
		DecimalFormat df = new DecimalFormat("#.###");
		for (optCutSetData d : optCutSetList) {
			String str = "[";
			for (String id : d.optCutSet)
				if (nodeID2attribute == null)
					str += id + " , ";
				else
					str += id + "(" + nodeID2attribute.get(id) + ")" + " , ";
			str = str.substring(0, str.length()-3);
			str += "]";
			this.optCutSetReport.append(str+"\t"+ d.optCutSetSize+"\t"+df.format(d.score)+"\t"+df.format(d.sumPiquantAbsSetScore)+"\t"+df.format(d.sumSideScore)+newline);
		}
		
		/*
		 * set a list of optimal intervention sets as node objects
		 */
		for (optCutSetData d : optCutSetList) {
			ArrayList<Node> l = new ArrayList<Node>();
			for (String id : d.optCutSet)
				l.add(this.graph.getNode(id));
			this.optIntSets.add(l);
		}
		
		/*
		 * print out OCSANA score matrix on the report
		 */
		this.optCutSetReport.append(newline + "OCSANA Score for each elementary node:" + newline+newline);
		String str = "Elementary node\tOCSANA score";
		this.optCutSetReport.append(str+newline);
		
		for (Node s : elemNodes) {
			str = s.Id + "\t" + df.format(omegaScoreMap.get(s.Id));
			this.optCutSetReport.append(str + newline);
		}
		
		/*
		 * print out PIQUANT*SET score matrix on the report
		 */
		//this.optCutSetReport.append(newline + "PIQUANT x SET Score matrix (elementary nodes x target nodes):" + newline+newline);
		this.optCutSetReport.append(newline + "EFFECT_ON_TARGETS x SET Score matrix (rows = elementary nodes x columns = target nodes):" + newline+newline);
		str = "Elementary node / Target node\t";
		for (Node t : targetNodes)
			str += t.Id + "\t";
		this.optCutSetReport.append(str+newline);
		
		for (Node s : elemNodes) {
			str = s.Id + "\t";
			for (Node t : targetNodes) {
				int idx = scoreMap.get(t).get(s);
				str += df.format(piquantSetScore[idx]) + "\t";
			}
			str = str.substring(0, str.length()-1);
			this.optCutSetReport.append(str + newline);
		}

	}
	
	/**
	 * save optimal intervention set list to a text file.
	 * 
	 * @param file
	 */
	public void saveOptCutSetData(File file) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for (optCutSetData d : optCutSetList) {
				String str = "";
				for (String id : d.optCutSet)
					if (nodeID2attribute == null)
						str += id + ",";
					else
						str += id + "::" + nodeID2attribute.get(id) + ",";
				str = str.substring(0, str.length()-1);
				str += "\t";
				str += d.optCutSetSize + "\t";
				str += d.score;
				str += "\t";
				str += d.sumPiquantAbsSetScore;
				str += "\t";
				str += d.sumSideScore;
				out.write(str+newline);
			}
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Calculate the number of combinations of k size for a set of n elements.
	 * 
	 * @author ebo
	 * 
	 * @param n the total number of elements
	 * @param k the size of the subset
	 * @return BigInteger the number of combinations
	 */
	public long calcCombinations(int n, int k) {
		BigInteger num = Utils.factorial(n);
		BigInteger den = Utils.factorial(k);
		den = den.multiply(Utils.factorial(n-k));
		return num.divide(den).longValue();
	}
	
	
	/**
	 * Convert every edge with no influence attribute to "activation".
	 * 
	 * @author ebo
	 */
	public int checkEdgeInfluence() {
		
		int ctChanges = 0;
		
		// set edges with no influence to activation
		for (Edge e : this.graph.Edges) {
			
			String ef = e.getFirstAttributeValue("EFFECT");
			String inter = e.getFirstAttributeValue("interaction");
			
			boolean found = false;

			if (ef != null && ef.indexOf("ACTIVATION")>=0) {
				e.setAttributeValueUnique("EFFECT", "activation", Attribute.ATTRIBUTE_TYPE_STRING);
				found = true;
			}

			if (found == false && ef != null) {
				if (ef.indexOf("INHIBITION")>=0) {
					e.setAttributeValueUnique("EFFECT", "inhibition", Attribute.ATTRIBUTE_TYPE_STRING);
					found = true;
				}
			}

			if (found == false && inter != null) { 
				if (inter.indexOf("activation") >= 0 || inter.indexOf("activate") >= 0) {
					e.setAttributeValueUnique("interaction", "activation", Attribute.ATTRIBUTE_TYPE_STRING);
					found = true;
				}
			}

			if (found == false && inter != null) {
				if (inter.indexOf("inhibition") >=0 || inter.indexOf("inhibit") >= 0) {
					e.setAttributeValueUnique("interaction", "inhibition", Attribute.ATTRIBUTE_TYPE_STRING);
					found = true;
				}
			}

			if (found == false) {
				e.setAttributeValueUnique("EFFECT", "activation", Attribute.ATTRIBUTE_TYPE_STRING);
				ctChanges++;
			}
			
		}
		return(ctChanges);
	}
	
	private String formatPathLabel (Path p) {
		StringBuffer ret = new StringBuffer(p.label);
		for (Node n : p.nodeSequence) {
			String newStr = n.Id+"("+nodeID2attribute.get(n.Id)+")";
	        int i = 0;
	        if((i=ret.indexOf(n.Id))>=0){
	                ret.replace(i,i+n.Id.length(),newStr);
	        }
		}
		return(ret.toString());
	}

	/**
	 * Print out elementary path matrix data.
	 * 
	 * @param p elementary paths list
	 * @author ebo
	 */
	public void printPaths (ArrayList<Path> p) {

		try {
			
			PrintWriter out = new PrintWriter(new FileWriter("/bioinfo/users/ebonnet/path.data.txt"));
			PrintWriter names = new PrintWriter(new FileWriter("/bioinfo/users/ebonnet/path.nodes.txt"));

			ArrayList<String> path = new ArrayList<String>();
			ArrayList<String> nodes = new ArrayList<String>();

			for (int i=0;i<p.size();i++) {
				// create path name
				String pname = "p"+i;
				if (!path.contains(pname))
					path.add(pname);

				// create a list of all nodes
				for (int j=0;j<p.get(i).nodeSequence.size()-1;j++) {

					Node n = p.get(i).nodeSequence.get(j);

					if(!nodes.contains(n.Id))
						nodes.add(n.Id);
				}
			}

			// write path names to output file
			for (String s : nodes)
				names.write(s+"\n");

			// write path matrix to output file
			for (int i=0;i<p.size();i++) {
				String line = "";;
				for (int j=0;j<nodes.size();j++) {

					String n = nodes.get(j);
					Boolean b = false;

					for (Node no : p.get(i).nodeSequence) {
						if (n.equals(no.Id)) {
							b = true;
						}
					}

					if (b)
						//System.out.print("1 ");
						line = line + "1 ";
					else 
						//System.out.print("0 ");
						line = line + "0 ";
				}
				line = line.trim();
				out.write(line+"\n");
			}
			
			out.close();
			names.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Fill a matrix of integers with elementary paths data.
	 * 
	 * @param p elementary paths list
	 * @author ebo
	 */
	public void fillPathMatrix(ArrayList<Path> p) {

		ArrayList<String> path = new ArrayList<String>();

		for (int i=0;i<p.size();i++) {
			// create path name
			String pname = "p"+i;
			if (!path.contains(pname))
				path.add(pname);

			// create a list of all nodes
			for (int j=0;j<p.get(i).nodeSequence.size()-1;j++) {

				Node n = p.get(i).nodeSequence.get(j);

				if(!pathMatrixNodeList.contains(n.Id))
					pathMatrixNodeList.add(n.Id);
			}
		}
		
		pathMatrixNbCol = pathMatrixNodeList.size();
		pathMatrixNbRow = path.size(); 

		// create array of integers to store elementary paths
		pathMatrix = new int[pathMatrixNbRow][pathMatrixNbCol];

		// fill matrix with corresponding data
		for (int i=0;i<pathMatrixNbRow;i++) {
			for (int j=0;j<pathMatrixNbCol;j++) {
				String n = pathMatrixNodeList.get(j);
				Boolean b = false;
				for (Node no : p.get(i).nodeSequence) {
					if (n.equals(no.Id)) {
						b = true;
					}
				}
				if (b)
					pathMatrix[i][j] = 1;
				else
					pathMatrix[i][j] = 0;
			}
		}
	}
	
	/**
	 * Create Cytoscape networks from Ocsana elementary paths, one network per source node.
	 */
	private void createCytoscapeNet() {
		
		HashMap<Node, HashSet<Node>> sourcePaths = new HashMap<Node, HashSet<Node>>();
		for (Node s: this.sourceNodes)
			sourcePaths.put(s, new HashSet<Node>());
		for (Path p : allPaths) {
			Node so = p.nodeSequence.get(0);
			for (int i=1;i<p.nodeSequence.size();i++)
				sourcePaths.get(so).add(p.nodeSequence.get(i));
		}

		for (Node s : sourcePaths.keySet()) {
			Graph g = new Graph();
			g.addNode(s);
			Iterator<Node> it = sourcePaths.get(s).iterator();
			while(it.hasNext()) {
				Node n = (Node)it.next();
				g.addNode(n);
			}
			// add side effect nodes
			for (Node n : this.sideNodes)
				g.addNode(n);
			g.addConnections(this.graph);
			sourceGraphs.put(s.Id,g);
		}
	
	}
	
}
