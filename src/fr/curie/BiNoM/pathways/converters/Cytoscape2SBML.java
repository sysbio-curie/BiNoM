package fr.curie.BiNoM.pathways.converters;

import java.util.*;
import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.utils.GraphUtils;
import fr.curie.BiNoM.pathways.utils.SimpleTable;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;
import fr.curie.BiNoM.pathways.analysis.structure.*;

public class Cytoscape2SBML {
	
	public static void main(String args[]){
		try{
			
		      String prefix = "c:/datas/binomtest/mapk";
		      GraphDocument gr = XGMML.loadFromXMGML(prefix+".xgmml");
		      Graph graph = XGMML.convertXGMMLToGraph(gr);
		      
		      org.sbml.x2001.ns.celldesigner.SbmlDocument celldesigner = null;
		      
		      String s = GraphUtils.getListOfReactionsTable(graph, null, null);
		      
		      System.out.println(s);
		      
		    	SimpleTable st = new SimpleTable();
		    	st.LoadFromSimpleDatFileString(s, true, "\t");
		    	
		    	Vector reactions = new Vector();
		    	Vector species = new Vector();
		    	
		    	if(st.rowCount>0){
		    		// Convert as a table of reactions
		    		for(int i=0;i<st.rowCount;i++)
		    			reactions.add(st.stringTable[i][st.fieldNumByName("REACTION")]);
		    		celldesigner = GraphUtils.convertFromListOfReactionsToSBML(reactions);
		    	}else{
		    		// If no traces of annotations were discovered, do some stupid stuff
		    		for(int i=0;i<graph.Edges.size();i++){
		    			Edge e = (Edge)graph.Edges.get(i);
		    			String reactionstring = e.Node1.NodeLabel+"->"+e.Node2.NodeLabel;
		    			reactions.add(reactionstring);
			    		celldesigner = GraphUtils.convertFromListOfReactionsToSBML(reactions);	    			
		    		}
		    	}
	    		CellDesigner.saveCellDesigner(celldesigner, prefix+"_sbml.xml");
		      
		      
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
