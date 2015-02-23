package fr.curie.BiNoM.pathways;

import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Vector;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.analysis.structure.Attribute;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.utils.CellDesignerGenerator;
import fr.curie.BiNoM.pathways.utils.ReactionStructure;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

import vdaoengine.utils.Utils;

public class CytoscapeToBiNoMReactionFormatConverter{

	/**
	 * @param args
	 */

	public Graph graph = null;
	
	public static void main(String[] args) {
		
		try{
		// TODO Auto-generated method stub
		CytoscapeToBiNoMReactionFormatConverter c2brf = new CytoscapeToBiNoMReactionFormatConverter();
		//String prefix = "c:/datas/binomtest/brf/testb";
		String prefix = "c:/datas/binomtest/biopax3/apoptosis3_";
		//String prefix = "c:/datas/binomtest/brf/Apoptosis3";
		//String prefix = "c:/datas/binomtest/brf/influence_network_example";
		//String prefix = "c:/users/Zinovyev/desktop/autophagy";
		c2brf.loadFile(prefix+".xgmml");
		String text = c2brf.convertToBRF();
		FileWriter fw = new FileWriter(prefix+"_1.txt");
		fw.write(text);
		fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadFile(String fn){
		try{
			graph = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(fn));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String convertToBRF() throws Exception{
		String text = "";
		graph.calcNodesInOut();
		Vector<String> species = new Vector<String>();
		Vector<String> reactions = new Vector<String>();
		
		float minx = Float.MAX_VALUE;
		float miny = Float.MAX_VALUE;
		float maxx = -Float.MAX_VALUE;
		float maxy = -Float.MAX_VALUE;
		
		// some pre-processing for names 
		for(int i=0;i<graph.Nodes.size();i++){
			String id = graph.Nodes.get(i).Id;
			String parts[] = id.split("\\["); 
			id = parts[0];
			//id = Utils.replaceString(id, ":", "_");
			//id = Utils.replaceString(id, "|", "_");
			id = Utils.replaceString(id, "?", "unknown");
			id = Utils.replaceString(id, " ", "_");
			graph.Nodes.get(i).Id = id;
		}
		
		for(int i=0;i<graph.Nodes.size();i++){
			Node n = graph.Nodes.get(i);
			if(n.x<minx) minx = n.x;
			if(n.y<miny) miny = n.y;
			if(n.x>maxx) maxx = n.x;
			if(n.y>maxy) maxy = n.y;
		}
		// write down species
		for(int i=0;i<graph.Nodes.size();i++){
			Node n = graph.Nodes.get(i);
			if(n.getAttributesWithSubstringInName("REACTION").size()==0){
				species.add(n.Id);
				text+=n.Id+"\n";
			}
			if(n.getAttributesWithSubstringInName("REACTION").size()>0){
				Attribute at = n.getAttributesWithSubstringInName("REACTION").get(0);
				if(at.value.equals("")){
					species.add(n.Id);
					text+=n.Id+"\n";
				}else{
					reactions.add(n.Id);
				}
			}
		}
		
		// write down reactions
		for(int i=0;i<graph.Nodes.size();i++){
			System.out.println("Numero nodi: " + graph.Nodes.size());
			Node n = graph.Nodes.get(i);
			if(reactions.contains(n.Id)){
				String left = "";
				String right = "";
				String regulators = "";
				String reactionSymbol = "->";
				if(n.getAttributesWithSubstringInName("NODE_TYPE").size()>0){
					String reactionType = "";
					for(int k=0;k<n.getAttributesWithSubstringInName("NODE_TYPE").size();k++){
						String s = n.getAttributesWithSubstringInName("NODE_TYPE").get(k).value;
						if(!s.trim().equals(""))
							reactionType = s;
					}
					reactionSymbol = ReactionStructure.getReactionSymbol(reactionType);
				}
				for(int j=0;j<n.incomingEdges.size();j++){
					Edge e = n.incomingEdges.get(j);
					String edgeType = "";
					if(e.getAttributesWithSubstringInName("EDGE_TYPE").size()>0){
						edgeType = "";
						for(int k=0;k<e.getAttributesWithSubstringInName("EDGE_TYPE").size();k++){
							String s = e.getAttributesWithSubstringInName("EDGE_TYPE").get(k).value;
							if(!s.trim().equals(""))
								edgeType = s;
						}
					}
					if(edgeType.toLowerCase().equals("left"))
						left+=e.Node1.Id+"+";
					else{
						String regulatorSymbol = ReactionStructure.getRegulatorSymbol(edgeType); 
						regulators+=regulatorSymbol+e.Node1.Id;
					}	
				}
				for(int j=0;j<n.outcomingEdges.size();j++){
					Edge e = n.outcomingEdges.get(j);
					right+=e.Node2.Id+"+";
				}
				
				if(left.length()>0) left=left.substring(0, left.length()-1);
				if(right.length()>0) right=right.substring(0, right.length()-1);
				if(right.length()==0) right = "null";
				text+=left+regulators+reactionSymbol+right+"\n";
			}
		}
		
		// now, test if some species are connected directly (influence network)
		for(int i=0;i<graph.Nodes.size();i++){
			Node n = graph.Nodes.get(i);
			if(!reactions.contains(n.Id)){
				for(int j=0;j<n.outcomingEdges.size();j++){
					Edge e = n.outcomingEdges.get(j);
					Node n2 = e.Node2;
					if(!reactions.contains(n2.Id)){
						// we only have to guess - activation or inhibition
						String type = "->"; // state transition by default
						// guess 1
						String interaction = e.getFirstAttributeValue("interaction");
						if(interaction!=null){
						if(interaction.toLowerCase().contains("activat")) type = "-+>";
						if(interaction.toLowerCase().contains("catalys")) type = "-+>";
						if(interaction.toLowerCase().contains("inhib")) type = "-|>";
						}
						// guess 2
						interaction = e.getFirstAttributeValueWithSubstringInName("EDGE_TYPE");
						if(interaction!=null){						
						if(interaction.toLowerCase().contains("activat")) type = "-+>";
						if(interaction.toLowerCase().contains("catalys")) type = "-+>";
						if(interaction.toLowerCase().contains("inhib")) type = "-|>";
						}
						
						text+=n.Id+type+n2.Id+"\n";
						
					}
				}
			}
		}
		
		
		return text;
	}

}
