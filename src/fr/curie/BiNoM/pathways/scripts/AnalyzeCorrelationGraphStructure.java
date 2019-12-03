package fr.curie.BiNoM.pathways.scripts;

import java.util.Vector;

import vdaoengine.utils.Algorithms;
import fr.curie.BiNoM.pathways.analysis.structure.Attribute;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class AnalyzeCorrelationGraphStructure {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			String folder = "C:/Datas/ColonCancer/analysis/corrgraph/";
			Graph gr = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(folder+"test.xgmml"));
			
			Graph gr1 = TrimGraphBasedOnAttributeValue(gr,100,"ABSCORR");
			gr1.name = gr.name+"_1";
			XGMML.saveToXGMML(gr1, folder+"test1.xgmml");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public static Graph TrimGraphBasedOnAttributeValue(Graph gr, int numberOfEdgesToKeep, String attributeName) throws Exception{
		Graph grtrimmed = gr.makeCopy();
		grtrimmed.calcNodesInOut();
		
		// Sort all nodes accordingly to the sum of attribute values
		int n = grtrimmed.Nodes.size();
		float weights[] = new float[grtrimmed.Nodes.size()];
		for(int i=0;i<grtrimmed.Nodes.size();i++){
			for(int j=0;j<grtrimmed.Nodes.get(i).incomingEdges.size();j++) weights[i]+=Float.parseFloat(grtrimmed.Nodes.get(i).incomingEdges.get(j).getFirstAttributeValue(attributeName));
			for(int j=0;j<grtrimmed.Nodes.get(i).outcomingEdges.size();j++) weights[i]+=Float.parseFloat(grtrimmed.Nodes.get(i).outcomingEdges.get(j).getFirstAttributeValue(attributeName));
			grtrimmed.Nodes.get(i).setAttributeValueUnique("SUMABSCORR", ""+weights[i], Attribute.ATTRIBUTE_TYPE_REAL);
		}
		int inds[] = Algorithms.SortMass(weights);
		
		for(int i=0;i<inds.length;i++){
			Node node = grtrimmed.Nodes.get(inds[n-i-1]);
			Vector<Edge> edges = new Vector<Edge>();
			for(int j=0;j<node.incomingEdges.size();j++) edges.add(node.incomingEdges.get(j));
			for(int j=0;j<node.outcomingEdges.size();j++) edges.add(node.outcomingEdges.get(j));
			if(edges.size()>numberOfEdgesToKeep){
			float vals[] = new float[edges.size()];
			for(int j=0;j<edges.size();j++) vals[j]=Float.parseFloat(edges.get(j).getFirstAttributeValue(attributeName));
			int ival[] = Algorithms.SortMass(vals);
			for(int j=numberOfEdgesToKeep;j<edges.size();j++){
				Edge e = edges.get(edges.size()-j-1);
				grtrimmed.removeEdge(e.Id);
			}
			grtrimmed.calcNodesInOut();
			}
		}
		
		return grtrimmed;
	}
	

}
