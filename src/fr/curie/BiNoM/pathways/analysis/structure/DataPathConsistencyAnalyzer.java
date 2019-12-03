package fr.curie.BiNoM.pathways.analysis.structure;

import fr.curie.BiNoM.cytoscape.ain.ImportFromAINDialogFamily;
import fr.curie.BiNoM.cytoscape.analysis.*;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import edu.rpi.cs.xgmml.*;

import java.util.*;
import java.io.*;
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
			//System.out.println(effect.toString());
			if(n!=null){
				ind = p.graph.getNodeIndex(n.Id);
				String conn = "-";
				if(effect.toString().equals("activation")){
					conn = "->"; p.influence*=1f;
				}
				if(effect.toString().equals("inhibition")){
					conn = "-|"; p.influence*=-1f;
				}
				if(conn.equals("-"))
					p.influence*=0f;
				p.label+=conn+n.NodeLabel;
				visited.add(n);
				p.nodeSequence.add(n);
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
	
}
