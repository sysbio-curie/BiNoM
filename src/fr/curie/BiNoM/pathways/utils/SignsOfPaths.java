package fr.curie.BiNoM.pathways.utils;

import java.io.*;
import java.util.*;

import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class SignsOfPaths {
	public static void main(String[] args) {
		try{
			
			String path = "C:/datas/ewing/network1/";
			
			Graph network = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(path+"network.xgmml"));
			
			Utils.CorrectCytoscapeNodeIds(network);
			Utils.CorrectCytoscapeEdgeIds(network);
			
			Vector<Node> proteins = new Vector<Node>();
			for(int i=0;i<network.Nodes.size();i++){
				Node n = network.Nodes.get(i);
				String type = n.getFirstAttributeValue("BIOPAX_NODE_TYPE");
				if(type.equals("protein"))
					proteins.add(n);
			}
			
			System.out.print("Protein\t");
			for(int i=0;i<proteins.size();i++){
				System.out.print(proteins.get(i).Id+"\t");
			}
			System.out.println();
			
			for(int i=0;i<proteins.size();i++){
				Node ni = proteins.get(i);
				System.out.print(ni.Id+"\t");				
				Vector<Node> targets = new Vector<Node>();  
				for(int j=0;j<proteins.size();j++)if(i!=j){
					targets.add(proteins.get(j));
				}
				GraphAlgorithms.verbose = false;
				Vector<Graph> paths = GraphAlgorithms.DijkstraAlgorithm(network, ni, targets, true, Float.MAX_VALUE);
				for(int j=0;j<proteins.size();j++){
					int numberOfPositivePaths = 0;
					int numberOfNegativePaths = 0;
					int numberOfZeroPaths = 0;
					int length = 0;
					Node nj = proteins.get(j);
					for(int k=0;k<paths.size();k++){
						Graph pth = paths.get(k);
						if(pth.Nodes.get(0)==ni)if(pth.Nodes.get(pth.Nodes.size()-1)==nj){
							int sign = getPathSign(pth);
							if(sign==1) numberOfPositivePaths++;
							if(sign==-1) numberOfNegativePaths++;
							if(sign==0) numberOfZeroPaths++;
							length = pth.Nodes.size()-1;
						}
					}
					//System.out.print(""+length+"/"+numberOfPositivePaths+"/"+numberOfNegativePaths+"/"+numberOfZeroPaths+"\t");
					System.out.print(""+length+"/"+numberOfPositivePaths+"/"+numberOfNegativePaths+"\t");
				}
				System.out.println();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
	public static int getPathSign(Graph graph){
		int sign = 1;
		for(int i=0;i<graph.Nodes.size()-1;i++){
			Node source = graph.Nodes.get(i);
			Node target = graph.Nodes.get(i+1);
			boolean found = false;
			for(int j=0;j<graph.Edges.size();j++){
				Edge e = graph.Edges.get(j);
				if(e.Node1==source)if(e.Node2==target){
					//String effect = e.getFirstAttributeValue("EFFECT");
					String effect = e.getFirstAttributeValue("BIOPAX_EDGE_TYPE").toLowerCase();
					if(effect==null){
						System.out.println("WARNING: Edge "+e.Id+" does not have BIOPAX_EDGE_TYPE attribute");
					}else{
					if(effect.indexOf("activation")>=0)
						sign*=1;
					else if(effect.indexOf("inhibition")>=0)
						sign*=-1;
					else{ 
						System.out.println("WARNING: Edge "+e.Id+" does not have BIOPAX_EDGE_TYPE attribute with value ACTIVATION/INHIBITION");
						sign*=0;
					}
					}
					found = true;
				}
				if(found)
					break;
			}
		}
		return sign;
	}

}
