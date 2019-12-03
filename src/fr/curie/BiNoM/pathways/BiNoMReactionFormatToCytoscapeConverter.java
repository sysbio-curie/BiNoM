package fr.curie.BiNoM.pathways;

import java.io.File;
import java.util.Vector;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.utils.CellDesignerGenerator;
import fr.curie.BiNoM.pathways.utils.ReactionStructure;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

import vdaoengine.utils.Utils;

public class BiNoMReactionFormatToCytoscapeConverter {

	/**
	 * @param args
	 */
	
	public Vector<String> statements = null;
	public CellDesignerGenerator celldesignergenerator = new CellDesignerGenerator();
	public String name = "";
	
	
	public static void main(String[] args) {
		
		try{
		// TODO Auto-generated method stub
		BiNoMReactionFormatToCytoscapeConverter brf2c = new BiNoMReactionFormatToCytoscapeConverter();
		//String prefix = "c:/datas/binomtest/brf/testCreateCD";
		//String prefix = "c:/datas/binomtest/brf/Apoptosis3_1";
		//String prefix = "c:/datas/binomtest/brf/Apop_temp";
		//String prefix = "c:/datas/BinomTest/brf/test";
		//String prefix = "C:/Users/ZINOVYEV/Desktop/autophagy_1";
		//String prefix = "c:/datas/BinomTest/M-phase2";
		String prefix = "c:/datas/BinomTest/Biopax3/test";
		brf2c.loadFile(prefix+".txt");
		Graph graph = brf2c.convertToGraph();
		CellDesigner.saveCellDesigner(brf2c.celldesignergenerator.cd, prefix+"_1.xml");
		XGMML.saveToXGMML(graph, prefix+".xgmml");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadFile(String fn){
		statements = Utils.loadStringListFromFile(fn);
		try{
		name = (new File(fn)).getName();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Graph convertToGraph() throws Exception{
		Graph graph = null;
		celldesignergenerator.createNewCellDesignerFile(name,1000,1500);
		
		addStatements();
		celldesignergenerator.processStatements();
		
		//CellDesigner.saveCellDesigner(celldesignergenerator.cd, "c:/datas/binomtest/brf/test.xml");
		
		CellDesignerToCytoscapeConverter csc = new CellDesignerToCytoscapeConverter();
		csc.sbml = celldesignergenerator.cd;
		CellDesigner.entities = CellDesigner.getEntities(csc.sbml);		
		GraphDocument gr = csc.getXGMMLGraph(name, csc.sbml.getSbml());
		
		graph = XGMML.convertXGMMLToGraph(gr);
		
		return graph;
	}
	
	public void addStatements(){
		for(String s: statements){
			String parts[] = s.split("\t");
			String attrs = "";
			if(parts.length>1)
				attrs = parts[1];
			boolean isreaction = false;
			for(int i=0;i<ReactionStructure.possibleReactionSymbols.length;i++)
				if(parts[0].contains(ReactionStructure.possibleReactionSymbols[i]))
					isreaction = true;
			if(isreaction)
				celldesignergenerator.addStatement(parts[0], celldesignergenerator.REACTION, attrs);
			else
				celldesignergenerator.addStatement(parts[0], celldesignergenerator.SPECIES, attrs);
		}
	}

}
