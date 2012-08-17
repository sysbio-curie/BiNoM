package fr.curie.BiNoM.pathways.utils;

import java.util.*;
import java.io.*;

import vdaoengine.utils.Utils;
import fr.curie.BiNoM.pathways.converters.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.utils.*; 
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class OFTENAnalysis {

	/**
	 * @param args
	 */
	
	public Graph hprd = null;
	public Vector<String> listOfRankedGenes = null;
	public Graph extractedNetwork = null;
	public Vector<String> rankedGeneList = null;
	public SubnetworkProperties snp = null;
	public int numberOfPermutationsForSizeTest = 100;
	public float thresholdSelectionConnectedComponents = 0.1f;
	public float thresholdIntersectionOfGraphs = 0.4f;
	public String fileName = "";
	
	public static void main(String[] args) {
		try{
			
			//String prefix = "C:/Datas/EWING/ICA/";
			String prefix = "C:/Datas/Kairov/DifferentialExpression4BC/lists/"; 
			String pathToHPRD = "C:/Datas/HPRD9/hprd9_pc_clicks.xgmml";	
			int nstart = 530;
			int nend = nstart;
			int step = 10;
			
			int valuesToTest[] = new int[(int)((nend-nstart)/step)+1];
			int k = 0;
			for(int i=nstart;i<=nend;i+=step)
				valuesToTest[k++] = i;
			OFTENAnalysis metaoften = new OFTENAnalysis();
			metaoften.loadHPRD(pathToHPRD);
			
			Vector<String> listOfGroups = Utils.loadStringListFromFile(prefix+"list");
			
			for(int i=0;i<listOfGroups.size();i++){
				Vector<String> listOfFileNames = new Vector<String>();
				StringTokenizer st = new StringTokenizer(listOfGroups.get(i),"_");
				while(st.hasMoreTokens())
					listOfFileNames.add(prefix+st.nextToken());
				OFTENAnalysis often = new OFTENAnalysis();
				often.hprd = metaoften.hprd;
				often.makeMetaOFTENFromRandkedGeneListFiles(listOfFileNames, valuesToTest);
				often.extractedNetwork.name = listOfGroups.get(i);
				XGMML.saveToXGMML(often.extractedNetwork, prefix+listOfGroups.get(i)+".xgmml");
			}
			
			System.exit(0);
			
			
			
			String fileRankedGenes = "1ic4";
			OFTENAnalysis often = new OFTENAnalysis();
			often.loadHPRD(pathToHPRD);
			often.loadRandkedGeneList(prefix+fileRankedGenes);
			often.fileName = fileRankedGenes;
			often.makeOFTENAnalysis(valuesToTest);
			often.extractedNetwork.name = fileRankedGenes;
			XGMML.saveToXGMML(often.extractedNetwork, prefix+fileRankedGenes+".xgmml");
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
	public void makeOFTENAnalysis(int valuesToTest[]) throws Exception{
		snp = new SubnetworkProperties();
		snp.modeOfSubNetworkConstruction = snp.SIMPLY_CONNECT;
		
		//fr.curie.BiNoM.pathways.utils.Utils.saveStringListToFile(this.rankedGeneList, fileName+"_");
		
		snp.network = hprd;
    	String reportSizeSignificance = snp.calcSignificanceVsNumberOfGenes(hprd, rankedGeneList, numberOfPermutationsForSizeTest, valuesToTest);
	
    	SimpleTable tb = new SimpleTable();
    	tb.LoadFromSimpleDatFileString(reportSizeSignificance, true, "\t");
    	int optimalNumber = -1;
    	float optimalScore = -1f;
    	for(int i=0; i<tb.rowCount;i++){
    		int ngenes = Integer.parseInt(tb.stringTable[i][tb.fieldNumByName("NGENES")]);
    		float score = Float.parseFloat(tb.stringTable[i][tb.fieldNumByName("SCORE")]);
    		if(score>optimalScore){
    			optimalScore = score;
    			optimalNumber = ngenes;
    		}
    	}
    	System.out.println("Optimal number of genes for OFTEN ("+fileName+") = "+optimalNumber);
    	extractSubnetwork(optimalNumber);
    	
		int components[][] = snp.calcDistributionOfConnectedComponentSizes(extractedNetwork);
		System.out.print("Distribution of connected components: ");
		for(int i=0;i<components.length;i++){
			System.out.print(components[i][0]+":"+components[i][1]+"\t");
		}
		int sizeOfComponentToSelect = (int)(thresholdSelectionConnectedComponents*components[components.length-1][0]);
		System.out.println("sizeOfComponentToSelect = "+sizeOfComponentToSelect);
		Vector<Graph> comps = GraphAlgorithms.ConnectedComponents(extractedNetwork);
		extractedNetwork = new Graph();
		for(int i=0;i<comps.size();i++){
			if(comps.get(i).Nodes.size()>=sizeOfComponentToSelect)
				extractedNetwork.addNodes(comps.get(i));
		}
		extractedNetwork.addConnections(hprd);
	}
	
	public void loadHPRD(String pathToHPRD) throws Exception{
		GraphXGMMLParser parser = new GraphXGMMLParser();
		parser.parse(pathToHPRD);
		hprd = parser.graph;
	}
	
	public void loadRandkedGeneList(String fileName){
		rankedGeneList = new Vector<String>();
		Vector<String> list = Utils.loadStringListFromFile(fileName);
		for(int i=0;i<list.size();i++){
			String geneName = list.get(i);
			StringTokenizer st = new StringTokenizer(geneName,"\t /");
			while(st.hasMoreTokens()){
				String s = st.nextToken();
				if(!rankedGeneList.contains(s))
					rankedGeneList.add(s);
			}
		}
	}
	
	public void extractSubnetwork(int ngenes){
		hprd.selectedIds.clear();
		for(int i=0;i<ngenes;i++){
			hprd.selectedIds.add(rankedGeneList.get(i));
		}
		extractedNetwork = hprd.getSelectedNodes();
	}
	
	public void makeMetaOFTENFromRandkedGeneListFiles(Vector<String> listOfFileNames, int valuesToTest[]) throws Exception{
		Vector<Graph> graphs = new Vector<Graph>();
		for(int i=0;i<listOfFileNames.size();i++){
			File f = new File(listOfFileNames.get(i)+".xgmml");
			Graph graph = null;
			if(!f.exists()){
				OFTENAnalysis often = new OFTENAnalysis();
				often.hprd = hprd;
				often.loadRandkedGeneList(listOfFileNames.get(i));
				often.fileName = listOfFileNames.get(i);
				often.makeOFTENAnalysis(valuesToTest);
				graph = often.extractedNetwork;
				StringTokenizer st = new StringTokenizer(often.fileName,"/");
				while(st.hasMoreTokens())
					graph.name = st.nextToken();
				XGMML.saveToXGMML(often.extractedNetwork, listOfFileNames.get(i)+".xgmml");
			}else{
				GraphXGMMLParser parser = new GraphXGMMLParser();
				parser.parse(listOfFileNames.get(i)+".xgmml");
				graph = parser.graph;;
			}
			graphs.add(graph);
		}
		extractedNetwork = intersectGraphs(graphs);
	}
	
	public Graph intersectGraphs(Vector<Graph> graphs){
	    Graph mergedGraph = BiographUtils.MergeNetworkAndFilter(graphs, thresholdIntersectionOfGraphs);
		return mergedGraph;
	}

}
