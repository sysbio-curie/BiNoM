package fr.curie.BiNoM.pathways;

import java.util.StringTokenizer;
import java.util.Vector;

import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.*;

import org.csml.csml.version3.ConnectorDocument;
import org.csml.csml.version3.EntityDocument;
import org.csml.csml.version3.ProcessDocument;
import org.csml.csml.version3.ProjectDocument;

public class CSMLToCytoscapeConverter {
	
	public static void main(String[] args) {
		try{
			
			String fn = "c:/datas/csml/BasalvsNormal_4_Andrei/AkaP13-Proto-Lbc";
			//String fn = "c:/datas/csml/BasalvsNormal_4_Andrei/test";
			
			/*Graph graph = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(fn+".xgmml"));
			System.out.println("Nodes "+graph.Nodes.size());
			graph = BiographUtils.ShowMonoMolecularReactionsAsEdges(graph);
			System.out.println("Nodes "+graph.Nodes.size());
			System.exit(0);*/
			
			
			
			ProjectDocument project = CSML.loadCSML(fn+".xml");
			
			CSMLToCytoscapeConverter ccc = new CSMLToCytoscapeConverter();
			Graph gr = ccc.getGraph(project);
			
			System.out.println("Nodes "+gr.Nodes.size());
			gr = BiographUtils.ShowMonoMolecularReactionsAsEdges(gr);
			System.out.println("Nodes "+gr.Nodes.size());
			
			XGMML.saveToXGMML(XGMML.convertGraphToXGMML(gr), fn+".xgmml");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Graph getGraph(ProjectDocument project){
		Graph graph = new Graph();
		for(int i=0;i<project.getProject().getModel().getEntitySet().sizeOfEntityArray();i++){
			EntityDocument.Entity ent = project.getProject().getModel().getEntitySet().getEntityArray(i);
			Node n = graph.getCreateNode(ent.getId());
			String type = ent.getType();
			if(ent.getType().endsWith("Protein")) type = "protein";
			if(ent.getType().endsWith("SmallMolecult")) type = "smallMolecule";
			if(ent.getType().endsWith("Complex")) type = "complex";
			n.Attributes.add(new Attribute("BIOPAX_NODE_TYPE",type));
			String comp = "";
			if(ent.getBiologicalProperty()!=null)
				if(ent.getBiologicalProperty().getRefCellComponentID()!=null)
					comp = ent.getBiologicalProperty().getRefCellComponentID();
			StringTokenizer st = new StringTokenizer(comp,":");
			while(st.hasMoreTokens()) comp = st.nextToken();
			n.Attributes.add(new Attribute("NODE_NAME",ent.getName()+"@"+comp));
		}
		for(int i=0;i<project.getProject().getModel().getProcessSet().sizeOfProcessArray();i++){
			ProcessDocument.Process proc = project.getProject().getModel().getProcessSet().getProcessArray(i);
			//System.out.println(proc.getId()+"\t"+proc.getName()+"\t"+proc.getType());
			Vector<String> ins = new Vector<String>();
			Vector<String> outs = new Vector<String>();
			Vector<String> inEffects = new Vector<String>();
			
			for(int j=0;j<proc.sizeOfConnectorArray();j++){
				ConnectorDocument.Connector cn = proc.getConnectorArray(j);
				if(cn.getType().endsWith("InputProcess")){
					ins.add(cn.getRefID());
					inEffects.add("");
				}else
					if(cn.getType().endsWith("InputInhibitor")){
						ins.add(cn.getRefID());
						inEffects.add("INHIBITION");
					}else					
						if(cn.getType().endsWith("InputAssociation")){
							ins.add(cn.getRefID());
							inEffects.add("ACTIVATION");
						}else
							if(cn.getType().contains("Input")){
								ins.add(cn.getRefID());
								inEffects.add("");
							}							
				if(cn.getType().endsWith("OutputProcess"))
					outs.add(cn.getRefID());
			}
			Node n = graph.getCreateNode(proc.getId());
			n.Attributes.add(new Attribute("BIOPAX_NODE_TYPE","biochemicalReaction"));
			n.Attributes.add(new Attribute("BIOPAX_REACTION",proc.getId()));			
			String name = proc.getName();
			name = fr.curie.BiNoM.pathways.utils.Utils.replaceString(name, "&lt;", "<");
			name = fr.curie.BiNoM.pathways.utils.Utils.replaceString(name, "&gt;", ">");
			n.Attributes.add(new Attribute("NODE_NAME",name));
			for(int k=0;k<ins.size();k++){
				Edge e = graph.getCreateEdge(ins.get(k)+"_"+n.Id);
				if(graph.getNode(ins.get(k))!=null){
					e.Node1 = graph.getNode(ins.get(k));
					e.Node2 = n;
					if(!inEffects.get(k).equals(""))
						e.Attributes.add(new Attribute("EFFECT",inEffects.get(k)));
				}else{
					System.out.println("ERROR: process "+proc.getId()+", node "+ins.get(k)+" not found!");
				}
			}
			for(int k=0;k<outs.size();k++){
				Edge e = graph.getCreateEdge(n.Id+"_"+outs.get(k));
				if(graph.getNode(outs.get(k))!=null){
					e.Node1 = n;
					e.Node2 = graph.getNode(outs.get(k));					
				}else{
					System.out.println("ERROR: process "+proc.getId()+", node "+outs.get(k)+" not found!");
				}
			}
		}
		return graph;
	}
	

}
