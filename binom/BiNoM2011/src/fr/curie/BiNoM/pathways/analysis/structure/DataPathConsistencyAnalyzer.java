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
	
	// Optimal cut set search data
	
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
	 * Report for the optimal cut set search
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
	 * Node Id to attribute value map
	 */
	public HashMap<String, String> nodeID2attribute;

	/**
	 * Simple data structure to store optimal cut sets data
	 *
	 */
	private class optCutSetData implements Comparable {
		
		public HashSet<String> optCutSet;
		public double score;
		public int optCutSetSize;
		
		public optCutSetData(HashSet<String> optCutSet, double score) {
			this.optCutSet = optCutSet; 
			this.score = score;
			this.optCutSetSize = optCutSet.size();
		}

		// sort by decreasing score value
		public int compareTo(Object b) {
			
			optCutSetData m = (optCutSetData) b;

			if(this.score < m.score) {
				return 1;
			} else if(this.score == m.score) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	/**
	 * Simple class to store Omega score data for optimal cut sets
	 * elementary nodes.
	 * 
	 * @author ebonnet
	 *
	 */
	private class OmegaScoreData implements Comparable {
		
		public String nodeId;
		public double score;
		
		public OmegaScoreData(String id, double score) {
			this.nodeId = id; 
			this.score = score;
		}

		// sort by decreasing score value
		public int compareTo(Object b) {
			
			OmegaScoreData m = (OmegaScoreData) b;

			if(this.score < m.score) {
				return 1;
			} else if(this.score == m.score) {
				return 0;
			} else {
				return -1;
			}
		}
	}
	
	
	public static void main(String args[]){
		try{
			
			//String path = "c:/datas/ewing/pathanalysis/";
			//String fname = "network_nop53";
			//String fname = "ewing_network";
			String path = "c:/datas/simon/";
			String fname = "toy3_";
			//String fname = "network";
			
			DataPathConsistencyAnalyzer dpc = new DataPathConsistencyAnalyzer();
			dpc.loadGraph(path+fname+".xgmml");
			
			/*float inf[][] = dpc.calcAllToAllInfluences();
			System.out.println(dpc.printAllToAllInfluenceTable(inf));
			System.exit(0);*/

    		JFrame window = new JFrame();
    		PathConsistencyAnalyzerDialog dialog = new PathConsistencyAnalyzerDialog(window,"Path Consistency Analyzer",true);
    		dialog.analyzer = dpc;
    		dialog.fillTheData();
    		dialog.setVisible(true);
    		
    		if(dialog.result>0){
    			PathConsistencyAnalyzerPathDialog dialogPath = new PathConsistencyAnalyzerPathDialog(window,"Path Browser",true);
        		dialogPath.analyzer = dpc;
        		dialogPath.fillTheData();
        		dialogPath.setVisible(true);
    		}
    		
    		System.out.println(dpc.generateReport());
    		
    		//dpc.significanceAnalysis(PERMUTE_NODE_ACTIVITIES, 100);    		
    		
			System.exit(0);
			
			/*dpc.getPathwayNodes();
			
			Vector summaryInfls = new Vector();
			
			for(int p=0;p<dpc.pathwayNodes.size();p++){
				
				Node pathwayNode = (Node)dpc.pathwayNodes.get(p);
				
				float summaryPathwayInfluence = 0f; 
				
				System.out.println("Pathway "+pathwayNode.NodeLabel+">> begin");
				
				Vector<Vector<Path>> nodePaths = new Vector<Vector<Path>>();
				dpc.EnrichedNodePaths.add(nodePaths);
				
				for(int i=0;i<dpc.graph.Nodes.size();i++){
					Node n = (Node)dpc.graph.Nodes.get(i);
					String color = n.getFirstAttributeValue("COLOR");
					
					if(color!=null)if(!color.equals(""))if(!color.equals("unknown")){
						PrintStream std = System.out;
						System.setOut(new PrintStream(new FileOutputStream(path+"temp")));
						Vector<Graph> pts = GraphAlgorithms.Dijkstra(dpc.graph, n, pathwayNode, true, false, Double.MAX_VALUE);
						System.setOut(std);
						if(p==0)
							dpc.EnrichedNodes.add(n);
						Vector<Path> vpaths = new Vector<Path>();
						nodePaths.add(vpaths);
						if(pts.size()>0){
							for(int j=0;j<pts.size();j++){
								Graph pth = pts.get(j);
								Path ppath = new Path();
								ppath.graph = pth;
								ppath.source = pth.getNodeIndex(n.Id);
								ppath.target = pth.getNodeIndex(pathwayNode.Id);
								dpc.calcPathProperties(ppath);
								vpaths.add(ppath);
							}
							float influence = dpc.calcNodeInfluence(n, vpaths, color, true);
							
							int expressionSign = 0;
							if(color.equals("red"))
								expressionSign = 1;
							if(color.equals("green"))
								expressionSign = -1;
							summaryPathwayInfluence+=influence*expressionSign;
						}
					}
				}
				System.out.println("Pathway "+pathwayNode.NodeLabel+">> end\tSumInfl="+summaryPathwayInfluence);
				System.out.println();
				summaryInfls.add(new Float(summaryPathwayInfluence));
			}
			
			
			float means[] = null;
			float stdvs[] = null;
			float scoreDistribution[][] = null;
			if(dpc.testSignificance){
				scoreDistribution = dpc.sampleInfluences();
				means = new float[scoreDistribution.length];
				stdvs = new float[scoreDistribution.length];
			}
			for(int k=0;k<scoreDistribution.length;k++){
				means[k] = Utils.calcMean(scoreDistribution[k]);
				stdvs[k] = Utils.calcStandardDeviation(scoreDistribution[k]);
			}
			
			
			float scores[] = dpc.getSummaryInfluences(dpc.pathwayNodes,summaryInfls);
			float pvalues[] = null;
			if(dpc.testSignificance){
				pvalues = dpc.calcPValues(scores, scoreDistribution);
			}
			System.out.println("Summary table:");
			System.out.println("--------------");
			float finalScore = 0f;
			for(int p=0;p<dpc.pathwayNodes.size();p++){
				Node pathwayNode = (Node)dpc.pathwayNodes.get(p);
				System.out.print(Utils.replaceString(pathwayNode.NodeLabel,"@","")+"\t"+scores[p]);
				if(dpc.testSignificance){
					System.out.print("\tMean="+means[p]+"\tInterval=["+(means[p]-stdvs[p])+";"+(means[p]+stdvs[p])+"]\tp-val="+pvalues[p]);
				}
				System.out.println();
			}
			System.out.print("Final score:\t"+scores[dpc.pathwayNodes.size()]);
			if(dpc.testSignificance){
				int p = dpc.pathwayNodes.size();
				System.out.print("\tMean="+means[p]+"\tInterval=["+(means[p]-stdvs[p])+";"+(means[p]+stdvs[p])+"]\tp-val="+pvalues[p]);
			}
			System.out.println();
			*/
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void calcEnrichedNodePaths(){
		
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
				if(effect.toString().equals("activation")){
					conn = "->"; p.influence*=1f;
				}
				if(effect.toString().equals("inhibition")){
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
					if(ef.indexOf("activation")>=0)if(effect.toString().equals("")){
						effect.append("activation"); overall+=effect.toString();
					}
					if(ef.indexOf("inhibition")>=0)if(effect.toString().equals("")){
						effect.append("inhibition"); overall+=effect.toString();
					}
				}else{
					ef = e.getFirstAttributeValue("interaction");
					if(ef!=null){
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
	
	
	/*public float[] getSummaryInfluences(Vector<Node> pathwayNodes,Vector<Float> summaryInfls){
			float finalScore = 0f;
			float scores[] = new float[pathwayNodes.size()+1];
			for(int p=0;p<pathwayNodes.size();p++){
				Node pathwayNode = (Node)pathwayNodes.get(p);
				if(pathwayNode.NodeLabel.indexOf("apoptosis")>=0)
					finalScore+=((Float)summaryInfls.get(p)).floatValue();
				if(pathwayNode.NodeLabel.indexOf("cell_cycle")>=0)
					finalScore+=-((Float)summaryInfls.get(p)).floatValue();
				scores[p] = ((Float)summaryInfls.get(p)).floatValue();
			}
			scores[pathwayNodes.size()] = finalScore; 
			return scores;
	}*/
	
	/*public float[][] sampleInfluences(){
		float dist[][] = new float[pathwayNodes.size()+1][numberOfPermutations];
		for(int i=0;i<numberOfPermutations;i++){
			for(int j=0;j<EnrichedNodePaths.size();j++){
				Vector<Vector<Path>> nodePaths = EnrichedNodePaths.get(j);
				for(int k=0;k<nodePaths.size();k++){
					Vector<Path> vpaths = nodePaths.get(k);
					float influence = calcNodeInfluence(EnrichedNodes.get(k), vpaths, false);
					boolean positive = randomize.nextBoolean();
					if(positive)
						dist[j][i]+=influence;
					else
						dist[j][i]-=influence;
				}
				Vector sumInfl = new Vector();
				for(int k=0;k<pathwayNodes.size();k++)
					sumInfl.add(new Float(dist[k][i]));
				float scores[] = getSummaryInfluences(pathwayNodes,sumInfl);
				dist[pathwayNodes.size()][i]+=scores[pathwayNodes.size()];
			}
		}
		return dist;
	}*/
	
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
						for(int k=0;k<pth.Nodes.size();k++)
							System.out.print(pth.Nodes.get(k).Id+"-");
						System.out.println();
						ppath.target = pth.getNodeIndex(pathwayNode.Id);
						calcPathProperties(ppath);
						vpaths.add(ppath);
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
			
			/*for(int k=0;k<numberOfPermutations;k++)
				System.out.print(targetScoreDistribution[3][k]+"\t");
			System.out.println();
			for(int k=0;k<numberOfPermutations;k++)
				System.out.print(targetScoreDistribution[4][k]+"\t");
			System.out.println();
			try{
			FileWriter fw = new FileWriter("c:/datas/ewing/pathanalysis/pathDistribution");
			for(int k=0;k<pathScoresDistribution[0].length;k++)
				fw.write(pathScoresDistribution[0][k]+"\n");
			fw.close();
			}catch(Exception e){
				e.printStackTrace();
			}*/
			
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
	 * Optimal cut set analysis.
	 * 
	 * @author ebonnet
	 */
	public void findOptimalCutSet() {
		
		this.optCutSetReport.append("--- Optimal cut set search report ---\n\n");
		
		String line = "";
		for (Node n : this.sourceNodes)
			if (nodeID2attribute == null)
				line += n.Id + " ";
			else
				line += n.Id + "(" + nodeID2attribute.get(n.Id) + ")" + " ";
		
		this.optCutSetReport.append("Source nodes: "+line+"\n");
		
		line = "";
		for (Node n : this.targetNodes)
			if (nodeID2attribute == null)
				line += n.Id + " ";
			else
				line += n.Id + "(" + nodeID2attribute.get(n.Id) + ")" + " ";
		this.optCutSetReport.append("Target nodes: "+line+"\n");
		
		line = "";
		for (Node n : this.sideNodes)
			if (nodeID2attribute == null)
				line += n.Id + " ";
			else
				line += n.Id + "(" + nodeID2attribute.get(n.Id) + ")" + " ";
		this.optCutSetReport.append("Side effect nodes: "+line+"\n\n");
		
//		for (int i=0;i<this.graph.Edges.size();i++) {
//			Edge e = this.graph.Edges.get(i);
//			for (int j=0;j< e.Attributes.size();j++) {
//				Attribute att = (Attribute)e.Attributes.get(j);
//				System.out.println(e.EdgeLabel+":"+att.name+"="+att.value);
//			}
//		}
		
		// convert every "neutral" edge to activation
		int nbConversions = this.checkEdgeInfluence();
		this.optCutSetReport.append(nbConversions+" edges were converted to activation out of "+this.graph.Edges.size()+"\n\n");
		System.out.println(nbConversions+" edges were converted to activation out of "+this.graph.Edges.size()+"\n\n");
		
		ArrayList<Node> outputNodes = new ArrayList<Node>();
		
		// output nodes are the union of target and side-effect nodes
		for (Node n : targetNodes)
			outputNodes.add(n);

		for (Node n : sideNodes)
			outputNodes.add(n);
		
		
		// simple data structure to select unique paths
		HashSet<String> nodeSequence = new HashSet<String>();

		// store all unique paths to output nodes
		ArrayList<Path> allPaths = new ArrayList<Path>();

		// all paths split in individual components e.g. A->B->C: A->B->C + B->C 
		ArrayList<Path> splitPaths = new ArrayList<Path>();

		// all individual nodes from elementary paths (target nodes excepted)
		HashSet<Node> allNodes = new HashSet<Node>();

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
							//System.out.println(p.label);
						}
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

		// elementary paths, set of paths sinking to target nodes
		ArrayList<Path> elemPaths = new ArrayList<Path>();

		// same but split in individual components
		ArrayList<Path> elemSplitPaths = new ArrayList<Path>();

		// set of unique nodes from elementary paths
		HashSet<Node> elemNodes = new HashSet<Node>();

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

//		System.out.println(">>>test<<<");
//		printPaths(elemPaths);
//		System.exit(1);
		
		this.optCutSetReport.append("Found "+elemPaths.size()+" elementary paths and "+elemNodes.size()+" elementary nodes"+"\n\n");
		System.out.println("Found "+elemPaths.size()+" elementary paths and "+elemNodes.size()+" elementary nodes.");
		
		for (Path p : elemPaths) {
			if (nodeID2attribute == null) {
				this.optCutSetReport.append(p.label+"\n");
				System.out.println(p.label);
			}
			else {
				this.optCutSetReport.append(formatPathLabel(p)+"\n");
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
		this.optCutSetReport.append("\n");

		// score values are stored in an array, with a map [target x nodes] pointing to the indexes.
		int ct = 0;
		HashMap<Node, HashMap<Node, Integer>> scoreMap = new HashMap<Node, HashMap<Node, Integer>>();
		double [] scoreVal = new double[targetNodes.size() * allNodes.size()];
		
		for (Node t : targetNodes)
			scoreMap.put(t, new HashMap<Node, Integer>());
		
		for (Node t : targetNodes)
			for (Node s : elemNodes) {
				//System.out.println(t.Id+"--"+s.Id);
				scoreMap.get(t).put(s, ct);
				scoreVal[ct] = 0.0;
				ct++;
			}
		
//		for (Node t : targetNodes)
//			for (Node s : elemNodes) {
//				int idx = scoreMap.get(t).get(s);
//				System.out.println(t.Id+"-"+s.Id+" = "+scoreVal[idx]);
//			}
		
//		System.out.println("\nScores");
		for (Path p : elemSplitPaths) {
			Node target = p.nodeSequence.get(p.target);
			Node source = p.nodeSequence.get(p.source);
			int idx = scoreMap.get(target).get(source);
			double sco = p.influence / p.length;
			scoreVal[idx] += p.influence / p.length;
//			System.out.println(source.Id+":"+target.Id+" "+p.label+" infl="+p.influence+" len="+p.length+" score="+sco+" val="+ scoreVal[idx]);
		}


		// negative scores are set to 0
		for (Node t : targetNodes)
			for (Node s : elemNodes) {
				int idx = scoreMap.get(t).get(s);
				if (scoreVal[idx] < 0)
					scoreVal[idx] = 0;
			}

//		System.out.println("\nPenalty scores");
		for (Node source : elemNodes) {
			for (Node target : targetNodes) {
				for (Node side : sideNodes) {
					for (Path ps : splitPaths) {
						Node split_target = ps.nodeSequence.get(ps.target);
						Node split_source = ps.nodeSequence.get(ps.source); 
						if (split_target == side && split_source == source) {
							int idx = scoreMap.get(target).get(source);
							// do not take into account the sign for the penalty score
							double sco = Math.abs(ps.influence / ps.length);
							scoreVal[idx] -= Math.abs(ps.influence / ps.length);
//							System.out.println(source.Id+":"+target.Id+" "+ps.label+" infl="+ps.influence+" len="+ps.length+" score="+sco+" val="+scoreVal[idx]);
						}
					}
				}
			}
		}

		// negative scores are set to 0
		for (Node t : targetNodes)
			for (Node s : elemNodes) {
				int idx = scoreMap.get(t).get(s);
				if (scoreVal[idx] < 0)
					scoreVal[idx] = 0;
			}

		
		System.out.println("\nScores:");
		for (Node t : targetNodes)
			for (Node s : elemNodes) {
				int idx = scoreMap.get(t).get(s);
				if (nodeID2attribute == null)
					System.out.println(t.Id+"-"+s.Id+" = "+scoreVal[idx]);
				else
					System.out.println(t.Id+"("+nodeID2attribute.get(t.Id)+")"+"-"+s.Id+"("+nodeID2attribute.get(s.Id)+")"+" = "+scoreVal[idx]);
			}
	
		
		/*
		 * Calculate Omega scores for all elementary nodes
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
		
		HashMap<String, Double> omegaScoreMap = new HashMap<String, Double>(elemNodes.size());
		ArrayList<OmegaScoreData> omegaScores = new ArrayList<OmegaScoreData>(elemNodes.size());
		
		for (Node source : elemNodes) {
			double omegaScore = 0;
			for (Node target : targetNodes) {
				int factor = 0;
				for (int i=0;i<elemPathsByTarget.get(target).size();i++) {
					//System.out.println(elemPathsByTarget.get(target).get(i));
					if (elemPathsByTarget.get(target).get(i).contains(source.Id))
						factor++;
				}
				double tmp = factor * scoreVal[scoreMap.get(target).get(source)];
				omegaScore += tmp;
				//System.out.println("source="+source.Id+" target="+target.Id+" factor="+factor+" om="+omegaScore + " score="+ scoreVal[scoreMap.get(target).get(source)]);
			}
			omegaScoreMap.put(source.Id, omegaScore);
		}
		
		for (String id : omegaScoreMap.keySet()) {
			omegaScores.add(new OmegaScoreData(id, omegaScoreMap.get(id)));
		}
		Collections.sort(omegaScores);
		
		/*
		 * optimal cut set search
		 */
		
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

		// Optimal cut sets 
		ArrayList<HashSet<String>> optCutSets = new ArrayList<HashSet<String>>();

		// The maximum number of set size to search for
		int k = this.maxSetSize;
		if (k > allNodes.size())
			k = allNodes.size();
		
		// covered nodes
		HashSet<String> covered = new HashSet<String>();

		System.out.println("maxSetSize: "+this.maxSetSize);
		System.out.println("maxSetNb: "+this.maxSetNb);
		System.out.println();
		
		/*
		 * main loop to search for hit sets of size 1, 2, 3 etc...
		 */
		optSearch:
			for (int i=0;i<k;i++) {

				int setSize = i+1;

				// opt cut sets of size 1
				if (setSize == 1) {
					
					this.optCutSetReport.append("Searching for hitting sets of size 1\n");
					System.out.println("Searching for hitting sets of size 1");
					
					int countSets = 0;
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
							covered.add(elts.get(j));
							optCutSets.add(nw);
							countSets++;
						}
					}

					this.optCutSetReport.append("Found "+countSets+" hitting sets\n");
					System.out.println("Found "+countSets+" hitting sets");
					System.out.println();
				}



				if (setSize > 1) {

					this.optCutSetReport.append("Searching for hitting sets of size "+setSize+".\n");
					System.out.println("Searching for hitting sets of size "+setSize+".");
					
					// stop if all nodes were covered
					if (covered.size() >= elts.size() ) {
						System.out.println("Break at size "+ setSize+ " all nodes covered.");
						this.optCutSetReport.append("Stop searching at size "+ setSize +", all elementary nodes have been covered.\n");
						break optSearch;
					}

					/*
					 * Calculate the number of combinations of setSize elements within elts.size
					 * 
					 * If this number is greater than the threshold, switch to selection mode:
					 * select nodes having max score so that the nb of combinations is lower 
					 * than the threshold.
					 * 
					 */
					long nbComb =  calcCombinations(elts.size(), setSize);
					long cutoff = this.maxSetNb;
					ArrayList<String> selection = new ArrayList<String>();
					boolean selTest = false;
					if (nbComb > cutoff) {
						
						System.out.println("Selection activated.");
						this.optCutSetReport.append("Selection activated.\n");
						int nbElements = this.optimalSetSize(setSize, cutoff);
						
						this.selectElements(nbElements, elts, omegaScores, selection);
						System.out.println("Selection size: "+selection.size());
						
						selTest = true;
						
						if (setSize > selection.size()) {
							System.out.println("Break at size "+ setSize +" selection.size() > setSize.");
							this.optCutSetReport.append("Stop searching at size " + setSize + ".\n");
							break optSearch;
						}
					}
					
					int nbElem = elts.size();
					if (selTest == true)
						nbElem = selection.size();
					
					// generate all combinations
					CombinationGenerator cg = new CombinationGenerator(nbElem, setSize);
					
					int[] indices;
					ArrayList<HashSet<String>> candidates = new ArrayList<HashSet<String>>();

					while(cg.hasMore()) {
						candidates.add(new HashSet<String>());
						indices = cg.getNext();
						for (int j=0;j<indices.length;j++) {
							if (selTest == true)
								candidates.get(candidates.size()-1).add(selection.get(indices[j]));
							else
								candidates.get(candidates.size()-1).add(elts.get(indices[j]));
						}
					}

//					System.out.println("combinations");
//					for (HashSet h : candidates)
//						System.out.println(h.toString());

					System.out.println("There are "+candidates.size()+" candidate sets.");
					
					/*
					 * Check if:
					 * 
					 * 1/ the candidate set is a hitting set, i.e. hits every elem. path
					 * 
					 * 2/ the candidate set does not contain any previously found minimal hitting set.
					 * 
					 */
					int[] countHit = new int[allSets.size()];
					int countSets = 0;
					long ticHS, tocHS=0; // hitting sets
					long ticPS, tocPS=0; // previous sets
					for (HashSet<String> ca : candidates) {

						// check true hitting sets
						ticHS = getTic();
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
						tocHS += getToc(ticHS);
						
						if (sum == allSets.size()) {
							// check against previous hitting sets
							ticPS = getTic();
							boolean testCheckSet = checkSet(optCutSets,ca);
							tocPS += getToc(ticPS);
							//if (checkSet(optCutSets,ca) == true) {
							if (testCheckSet == true) {
								optCutSets.add(ca);
								for (String s : ca)
									covered.add(s);
								countSets++;
							}
						}
					}
					System.out.println("OptCutSet size: "+optCutSets.size());
					this.optCutSetReport.append("Found "+countSets+" hitting sets.\n");
					System.out.println("Found "+countSets+" hitting sets.");
					System.out.println();
				}
			} // end main loop over set size

		// calculate and order sets according to their score
		ArrayList<optCutSetData> optCutSetList = new ArrayList<optCutSetData>();
		
		for (HashSet<String> set : optCutSets) {
			double score = 0.0;
			for (String nodeID : set) {
				score += omegaScoreMap.get(nodeID);
			}
			optCutSetList.add(new optCutSetData(set, score));
		}
		
		Collections.sort(optCutSetList);

		this.optCutSetReport.append("Found " + optCutSetList.size() + " optimal cut sets.\n\n");
		
		this.optCutSetReport.append("Cut set\tSize\tScore\n\n");
		//DecimalFormat df = new DecimalFormat("#.##");
		for (optCutSetData d : optCutSetList) {
			//this.optCutSetReport.append(d.optCutSet.toString()+"\t"+ d.optCutSetSize+"\t"+df.format(d.score)+"\n");
			String str = "[";
			for (String id : d.optCutSet)
				if (nodeID2attribute == null)
					str += id + " , ";
				else
					str += id + "(" + nodeID2attribute.get(id) + ")" + " , ";
			str = str.substring(0, str.length()-3);
			str += "]";
			this.optCutSetReport.append(str+"\t"+ d.optCutSetSize+"\t"+d.score+"\n");
		}
		
	} // end findOptimalCutSet()
	
	
	/**
	 * check if a candidade set elements have been found in a previous
	 * optimal cut set.
	 * 
	 * @param optCutSets list of previously found opt. cut sets
	 * @param candidateSet candidate set
	 * @return boolean true if there is no hit with previous sets, false otherwise
	 */
	private boolean checkSet(ArrayList<HashSet<String>> optCutSets, HashSet<String> candidateSet) {
		Boolean ret = true;
		//System.out.println("candidateSet: "+candidateSet);
		for (HashSet<String> s : optCutSets) {
			int count = 0;
			//System.out.println("check set: "+s);
			for (String c : candidateSet) {
				//System.out.println("candidate: "+c);
				if (s.contains(c))
					count++;
			}
			//System.out.println("count: "+count+ " set size: "+s.size());
			if (count == s.size())
				ret = false;
		}
		return(ret);
	}
	
	/**
	 * Calculate the number of combinations of k size for a set of n elements.
	 * 
	 * @author ebonnet
	 * 
	 * @param n the total number of elements
	 * @param k the size of the subset
	 * @return BigInteger the number of combinations
	 */
	private long calcCombinations(int n, int k) {
		BigInteger num = Utils.factorial(n);
		BigInteger den = Utils.factorial(k);
		den = den.multiply(Utils.factorial(n-k));
		return num.divide(den).longValue();
	}
	
	
	/**
	 * Convert every edge with no influence attribute to "activation".
	 * 
	 * @author ebonnet
	 */
	public int checkEdgeInfluence() {
		
		int ctChanges = 0;
		
		// set edges with no influence to activation
		for (Edge e : this.graph.Edges) {
			
			String ef = e.getFirstAttributeValue("EFFECT");
			String inter = e.getFirstAttributeValue("interaction");
			
//			if (ef != null) {
//				if (ef.indexOf("ACTIVATION")>=0)
//					e.setAttributeValueUnique("EFFECT", "activation", Attribute.ATTRIBUTE_TYPE_STRING);
//				if (ef.indexOf("INHIBITION")>=0)
//					e.setAttributeValueUnique("EFFECT", "inhibition", Attribute.ATTRIBUTE_TYPE_STRING);
//			}
//			
//			
//			if (ef != null && inter != null) { 
//				if (ef.indexOf("inhibition")>=0 && inter.indexOf("inhibition")<0)
//					e.setAttributeValueUnique("interaction", "inhibition", Attribute.ATTRIBUTE_TYPE_STRING);
//
//				if (ef.indexOf("activation")>=0 && inter.indexOf("activation")<0)
//					e.setAttributeValueUnique("interaction", "activation", Attribute.ATTRIBUTE_TYPE_STRING);
//			}
//			
//			if (ef == null && inter != null) {
//				if (!(inter.indexOf("activation")>=0 || inter.indexOf("inhibition")>=0)) {
//					e.setAttributeValueUnique("interaction", "activation", Attribute.ATTRIBUTE_TYPE_STRING);
//					ctChanges++;
//				}
//			}
//			
//			if (ef == null && inter == null) {
//				e.setAttributeValueUnique("EFFECT", "activation", Attribute.ATTRIBUTE_TYPE_STRING);
//				ctChanges++;
//			}
			

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
	
	/**
	 * Calculate maximum n size for a number of combinations < cutoff.
	 * 
	 * @author ebonnet
	 * 
	 * @param int setSize
	 * @param long cutoff
	 * @return int n
	 */
	private int optimalSetSize(int setSize, long cutoff) {
		
		int n = setSize;
		int k = setSize;
		long comb = 0;
		while(comb < cutoff) {
			n++;
			comb = calcCombinations(n,k);
		}
		n--;
		return(n);
	}
	
	/**
	 * Select max nbElements from a given array.
	 * 
	 * @param nbElements
	 * @param elts
	 * @param omegaScores
	 * @param selection
	 */
	private void selectElements(int nbElements, ArrayList<String> elts, ArrayList<OmegaScoreData> omegaScores, ArrayList<String> selection) {
		
		// loop over elements by decreasing score
		for (OmegaScoreData om : omegaScores) {
			if (selection.size() >= nbElements)
				break;
			// elements must be part of the current elements set
			if (elts.contains(om.nodeId)) {
				selection.add(om.nodeId);
			}
		}
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

	private long getTic() {
		return System.currentTimeMillis();
	}
	
	private long getToc(long tic) {
		long ret = System.currentTimeMillis() - tic;
		return ret;
	}
	
	
	public void printPaths (ArrayList<Path> p) {



		try {
			
			PrintWriter out = new PrintWriter(new FileWriter("/bioinfo/users/ebonnet/path.data.txt"));
			PrintWriter names = new PrintWriter(new FileWriter("/bioinfo/users/ebonnet/path.nodes.txt"));

			ArrayList<String> path = new ArrayList<String>();
			ArrayList<String> nodes = new ArrayList<String>();

			for (int i=0;i<p.size();i++) {
				String pname = "p"+i;
				if (!path.contains(pname))
					path.add(pname);

				for (int j=0;j<p.get(i).nodeSequence.size()-1;j++) {

					Node n = p.get(i).nodeSequence.get(j);

					if(!nodes.contains(n.Id))
						nodes.add(n.Id);
				}
			}

			for (String s : nodes)
				names.write(s+"\n");

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
	
}
