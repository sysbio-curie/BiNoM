package fr.curie.DeDaL.scripts;

import java.io.FileWriter;

import vdaoengine.analysis.grammars.Edge;
import vdaoengine.data.VDataTable;
import vdaoengine.data.io.VDatReadWrite;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.wrappers.XGMML;


public class GeneticInteractionExample {

	public static void main(String[] args) {
		try{
			
			String prefix = "C:/Datas/DeDaL/genetic_interactions/dna_repair_genes";
			
			fr.curie.BiNoM.pathways.analysis.structure.Graph graph = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(prefix+".xgmml"));
			
			float matrix[][] = new float[graph.Nodes.size()][graph.Nodes.size()];
			graph.calcNodesInOut();
			for(int i=0;i<graph.Nodes.size();i++){
					Node n = graph.Nodes.get(i);
					for(fr.curie.BiNoM.pathways.analysis.structure.Edge e: n.incomingEdges){
						float eps = Float.parseFloat(e.getFirstAttributeValue("SCORE_EPS"));
						int k = graph.Nodes.indexOf(e.Node1);
						matrix[i][k] = eps;
					}
					for(fr.curie.BiNoM.pathways.analysis.structure.Edge e: n.outcomingEdges){
						float eps = Float.parseFloat(e.getFirstAttributeValue("SCORE_EPS"));
						int k = graph.Nodes.indexOf(e.Node2);
						matrix[i][k] = eps;
					}					
			}
			
			FileWriter fw = new FileWriter(prefix+".txt");
			fw.write("NODE\t"); 
			for(int i=0;i<graph.Nodes.size();i++) fw.write(graph.Nodes.get(i).Id+"\t");
			fw.write("\n");
			for(int i=0;i<graph.Nodes.size();i++){
				fw.write(graph.Nodes.get(i).Id+"\t"); 
				for(int j=0;j<graph.Nodes.size();j++) fw.write(matrix[i][j]+"\t");
				fw.write("\n");
			}
			fw.close();
			
			VDataTable vt = VDatReadWrite.LoadFromSimpleDatFile(prefix+".txt", true, "\t", false);
			for(int i=1;i<vt.colCount;i++)
				vt.fieldTypes[i] = vt.NUMERICAL;
			VDatReadWrite.saveToVDatFile(vt, prefix+".dat");
					
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
