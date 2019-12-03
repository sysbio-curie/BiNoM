package fr.curie.BiNoM.pathways.test;

import java.util.*;

import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.*;
import edu.rpi.cs.xgmml.*;


public class TestAutomaticStructureAnalysis {

	/**
	 * 
	 */
	public static void main(String[] args) {
		try{
			
			
			GraphDocument grd = XGMML.loadFromXMGML("c:/datas/binomtest/test2.xgmml");
			Graph graph = XGMML.convertXGMMLToGraph(grd);
			Vector v = GraphAlgorithms.CycleDecomposition(graph, 1);
			for(int i=0;i<v.size();i++){
				XGMML.saveToXGMML(XGMML.convertGraphToXGMML((Graph)v.get(i)), "c:/datas/binomtest/test2_"+(i+1)+".xgmml");
			}
			System.exit(0);
			
            /*GraphDocument gr1 = (CellDesignerToCytoscapeConverter.convert("c:/datas/binomtest/test1/M-Phase2.xml")).graphDocument;
            Vector v = StructureAnalysisUtils.getMaterialComponents(gr1);
            System.out.println("Number of componenets = "+v.size());
            System.exit(0);*/

			/*GraphDocument grd = XGMML.loadFromXMGML("c:/datas/binomtest/test2.xgmml");
			Graph graph = XGMML.convertXGMMLToGraph(grd);
			//Vector v = GraphAlgorithms.PruneGraph(graph);
			//Graph g = (Graph)v.get(0);
			XGMML.saveToXGMML(XGMML.convertGraphToXGMML(graph), "c:/datas/binomtest/test2_in.xgmml");
			System.exit(0);*/
			
			GraphDocument grDoc = XGMML.loadFromXMGML("c:/datas/binomtest/test.xgmml");
			Graph global = XGMML.convertXGMMLToGraph(grDoc);
			Node source = global.getNode("node0");
			Node target = global.getNode("node1");
			Set<Node> sources = new HashSet<Node>();
			Set<Node> targets = new HashSet<Node>();
			targets.add(target);
			//targets.add(source);
			sources.add(source);
			
			Vector ssources = new Vector(); ssources.add("node0"); ssources.add("node1");
			Vector stargets = new Vector(); stargets.add("node1"); stargets.add("node0");

			StructureAnalysisUtils.Option options = new StructureAnalysisUtils.Option();
			options.pathFindMode = options.ALL_PATHS;
			options.searchRadius = 10;			
			
			Set SelectedNodes = StructureAnalysisUtils.findPaths(global, ssources, stargets, options);
			
			System.out.println("Finished\n");
			
		    Vector<Graph> paths = GraphAlgorithms.FindAllPaths(global, source, targets, true, 10.0);

			if(paths!=null){
				System.out.println(""+paths.size()+" paths found");
				for(int k=0;k<paths.size();k++){
					Graph gr = paths.get(k);
					for(int kk=0;kk<gr.Nodes.size();kk++){
						System.out.print(((Node)gr.Nodes.get(kk)).Id+"\t");
					}
					System.out.println();
				}
			}
			
			
			/*Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/m2.xgmml"));
		    Vector<Graph> v = GraphAlgorithms.CycleDecomposition(global,0);
			for(int i=0;i<v.size();i++){
				XGMML.saveToXMGML(XGMML.convertGraphToXGMML(v.get(i)),"c:/datas/calzone/modules1/temp/cycle"+(i+1)+".xgmml");
			}
			global.metaNodes = v;
			Graph meta = BiographUtils.CollapseMetaNodes(global, false, true);
			XGMML.saveToXMGML(XGMML.convertGraphToXGMML(meta),"c:/datas/calzone/modules1/temp/meta.xgmml");*/

			// simple pruning
			/*Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/310507_proteins.xml.xgmml"));
			Vector v = global.prune();
			XGMML.saveToXMGML(XGMML.convertGraphToXGMML(global), "c:/datas/calzone/modules1/pruned.xgmml");*/
			
			//Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/manually_prepared.xgmml"));			//Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/p7.xgmml"));
			//Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/mphase2.xgmml"));
			//Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/m1.xgmml"));
			//Graph global = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/calzone/modules1/test5.xgmml"));
			//Vector v = StructureAnalysisUtils.getCyclicMaterialComponents(global, new StructureAnalysisUtils.Option());
			
			/*GraphDocument gr = XGMML.loadFromXMGML("c:/datas/calzone/modules1/test7.xgmml");
			for(int i=0;i<gr.getGraph().getEdgeArray().length;i++){
				GraphicEdge e = (GraphicEdge)gr.getGraph().getEdgeArray(i);
				System.out.println(e.getSource()+"\t"+e.getTarget());
			}*/
			

			/*for(int i=0;i<global.Edges.size();i++){
				Edge e = (Edge)global.Edges.get(i);
				System.out.println(e.Node1.Id+"\t"+e.Node2.Id);
			}*/
			
			
		    /*Vector<Graph> v = GraphAlgorithms.CycleDecomposition(global,0);
			//int minimumComponentSize = 2;
			//Vector<Graph> v = GraphAlgorithms.StronglyConnectedComponentsTarjan(global,minimumComponentSize);
			
			//Vector<Graph> v = new Vector<Graph>();
			
			for(int i=0;i<v.size();i++){
				XGMML.saveToXMGML(XGMML.convertGraphToXGMML(v.get(i)),"c:/datas/calzone/modules1/temp/cycle"+(i+1)+".xgmml");
			}
			
			global.metaNodes = v;
			Graph meta = BiographUtils.CollapseMetaNodes(global, false, true);
			XGMML.saveToXMGML(XGMML.convertGraphToXGMML(meta),"c:/datas/calzone/modules1/temp/meta.xgmml");

			// Now cycle clustering
			Vector<Graph> modules = GraphAlgorithms.CombineIncludedGraphsApprox(v,0.37f);
			for(int i=0;i<modules.size();i++){
				XGMML.saveToXMGML(XGMML.convertGraphToXGMML(modules.get(i)),"c:/datas/calzone/modules1/temp/module"+(i+1)+".xgmml");
			}

			global.metaNodes = modules;
			meta = BiographUtils.CollapseMetaNodes(global, false, true);
			XGMML.saveToXMGML(XGMML.convertGraphToXGMML(meta),"c:/datas/calzone/modules1/temp/meta_module.xgmml");
			*/
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
