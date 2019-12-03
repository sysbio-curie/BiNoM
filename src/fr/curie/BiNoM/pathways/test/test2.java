package fr.curie.BiNoM.pathways.test;

import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import vdaoengine.data.io.*;
import vdaoengine.data.*;
import java.util.*;
import java.io.*;
import fr.curie.BiNoM.cytoscape.plugin.BiNoMPlugin;


public class test2 extends SubnetworkProperties{
	
	public static void main(String[] args) {
		try{
			
			SubnetworkProperties SP = new SubnetworkProperties();
			SP.path = "C:/Datas/HPRD8/";
			SP.loadNetwork(SP.path+"hprd8.xgmml");

			Utils.CorrectCytoscapeEdgeIds(SP.network);
			System.out.println("Loaded network: "+SP.network.Nodes.size()+" nodes, "+SP.network.Edges.size()+" edges");
			
			Vector<String> pathways = new Vector<String>();
			pathways.add("basal_normal_full");
			
			
			SP.modeOfSubNetworkConstruction = SP.SIMPLY_CONNECT;

			SP.readComplexes(SP.path+"HPRD_PC.txt",20);
			SP.addComplexesToNetworkAsClicks(); System.out.println("After adding complexes: "+SP.network.Nodes.size()+" nodes, "+SP.network.Edges.size()+" edges");			
			//SP.addComplexesToNetworksAsNodes(); System.out.println("After adding complexes: "+SP.network.Nodes.size()+" nodes, "+SP.network.Edges.size()+" edges");
			SP.removeDoubleEdges();				System.out.println("After removing double edges: "+SP.network.Nodes.size()+" nodes, "+SP.network.Edges.size()+" edges");
			calcDegreeDistribution(SP.network, SP.degreeDistribution, SP.degrees, true);			
			XGMML.saveToXGMML(SP.network, SP.path+"hprd8_pc_clicks.xgmml");
			
			Vector<String> fulllist = Utils.loadStringListFromFile(SP.path+"pathways/"+pathways.get(0));
			int testSizes[] = {100,150,200,250,260,270,280,290,300,310,320,330,340,350,360,370,380,390,400,420,440,460,480,500,520,540,560,580,600,620,640,660,680,700,1000};
			calcSignificanceVsNumberOfGenes(SP.network,fulllist,2,testSizes); 			
			System.exit(0);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}