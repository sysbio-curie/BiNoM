package fr.curie.BiNoM.pathways;

import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
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
	public int map_width = 1500;
	public int map_height = 1000;
	
	
	public static void main(String[] args) {
		
		try{
		// TODO Auto-generated method stub
		BiNoMReactionFormatToCytoscapeConverter brf2c = new BiNoMReactionFormatToCytoscapeConverter();
		
		String brffile = "";
		String prefix = "";
		String point_names[] = null;
		float point_positions[][] = null; //{{292f,104f},{822f,819f}};;
		String annotationFile =  null;
		String annotationFieldForLink = null;
		String annotationFieldForLinkValue = null;
		String GMTFileHUGOMap = null;

		
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-brf")){
				brffile = args[i+1];
				prefix = brffile.substring(0, brffile.length()-4);
			}
			if(args[i].equals("-points")){
				String parts[] = args[i+1].split(":");
				StringTokenizer st = new StringTokenizer(parts[0],"(,)");
				String pt1[] = new String[3]; pt1[0] = st.nextToken(); pt1[1] = st.nextToken(); pt1[2] = st.nextToken();  
				st = new StringTokenizer(parts[1],"(,)");
				String pt2[] = new String[3]; pt2[0] = st.nextToken(); pt2[1] = st.nextToken(); pt2[2] = st.nextToken();  
				point_names = new String[2];
				point_names[0] = pt1[0];
				point_names[1] = pt2[0];
				
				point_positions = new float[2][2];
				point_positions[0][0] = Float.parseFloat(pt1[1]);
				point_positions[0][1] = Float.parseFloat(pt1[2]);
				point_positions[1][0] = Float.parseFloat(pt2[1]);
				point_positions[1][1] = Float.parseFloat(pt2[2]);
			}
			if(args[i].equals("-annotationFile"))
				annotationFile = args[i+1];
			if(args[i].equals("-annotationFieldForLink"))
				annotationFieldForLink = args[i+1];
			if(args[i].equals("-annotationFieldForLinkValue"))
				annotationFieldForLinkValue = args[i+1];
			if(args[i].equals("-GMTFileHUGOMap"))
				GMTFileHUGOMap = args[i+1];
		}
		
		/*String prefix = "C:/Datas/ROMA/pancancer/corr_net_ROMA/nets_allH&speed/infosigmap"; 
		String point_names[] = {"REACTOME_ER_PHAGOSOME_PATHWAY","GNF2_CDH11"};
		float point_positions[][] = {{856f,962f},{9439f,5281f}};
		String annotationFile =  "C:/Datas/ROMA/pancancer/corr_net_ROMA/nets_allH&speed/annotations_for_celldesigner.txt";
		String annotationFieldForLink = "DATABASE";
		String annotationFieldForLinkValue = "INITIAL_NAME";
		String GMTFileHUGOMap = "C:/Datas/ROMA/pancancer/corr_net_ROMA/nets_allH&speed/all.gmt";
		*/
		
		
		//String prefix = "c:/datas/binomtest/brf/testCreateCD";
		//String prefix = "c:/datas/binomtest/brf/Apoptosis3_1";
		//String prefix = "c:/datas/binomtest/brf/Apop_temp";
		//String prefix = "c:/datas/BinomTest/brf/test";
		//String prefix = "C:/Users/ZINOVYEV/Desktop/autophagy_1";
		//String prefix = "c:/datas/BinomTest/M-phase2";
		//String prefix = "c:/datas/BinomTest/Biopax3/test";
		//String prefix = "C:/Datas/EWING/network/Ewing_influence_nodes";
		//String prefix = "C:/Datas/EWING/network/Ewing_influence_1";
		//String prefix = "C:/Datas/PanMethylome/analysis/correlationgraph/panmeth_navicell/test_influence";
		//String prefix = "C:/Datas/PanMethylome/analysis/correlationgraph/panmeth_navicell/panmeth";
		
		//String prefix = "C:/Datas/ROMA/pancancer/corr_net_ROMA/tonavicell2/materials/avcorrmodulenet";
		
		
		//String point_names[] = {"GSE18791_CTRL_VS_NEWCASTLE_VIRUS_DC_6H","CHIANG_LIVER_CANCER_SUBCLASS_CTNNB1"};
		//float point_positions[][] = {{1547f,231f},{9329f,4213f}};
		//String point_names[] = {"REACTOME_ER_PHAGOSOME_PATHWAY","PROTEINACEOUS_EXTRACELLULAR_MATRIX"};
		//float point_positions[][] = {{3965f,1386f},{16140f,7694f}};
		
		//String point_names[] = {"REACTOME_PEPTIDE_CHAIN_ELONGATION","MARSON_FOXP3_TARGETS_STIMULATED"};
		//float point_positions[][] = {{1735f,263f},{9288f,3691f}};
		

		
		//String point_names[] = {"IC11_KIRPn","IC9_KIRP"};
		//float point_positions[][] = {{689f,127f},{1014f,612f}};
		/*brf2c.celldesignergenerator.shiftY = 332f;
		brf2c.celldesignergenerator.shiftX = -15f;
		brf2c.celldesignergenerator.scaleX = 2*2572f/2640f;
		brf2c.celldesignergenerator.scaleY = 2f-0.05f;*/
		brf2c.loadFile(brffile);
		
		brf2c.celldesignergenerator.addHUGO2ProteinNote = true;
		brf2c.celldesignergenerator.annotationFile = annotationFile;
		brf2c.celldesignergenerator.annotationFieldForLink = annotationFieldForLink;
		brf2c.celldesignergenerator.annotationFieldForLinkValue = annotationFieldForLinkValue;
		brf2c.celldesignergenerator.GMTFileHUGOMap = GMTFileHUGOMap;
		brf2c.celldesignergenerator.repositionAliases = false;

		
		brf2c.celldesignergenerator.shiftX = 0f;
		brf2c.celldesignergenerator.shiftY = 0f;
		brf2c.celldesignergenerator.scaleX = 1f;
		brf2c.celldesignergenerator.scaleY = 1f;
		
		System.out.println("===========================");
		System.out.println("======= PASS 1    =========");
		System.out.println("===========================");
		Graph graph = brf2c.convertToGraph();
		brf2c.rescale(graph, point_names, point_positions);

		
		float shiftX = brf2c.celldesignergenerator.shiftX; 
		float shiftY = brf2c.celldesignergenerator.shiftY;
		float scaleX = brf2c.celldesignergenerator.scaleX;
		float scaleY = brf2c.celldesignergenerator.scaleY;
		brf2c.celldesignergenerator = new CellDesignerGenerator();
		brf2c.celldesignergenerator.addHUGO2ProteinNote = true;
		brf2c.celldesignergenerator.repositionAliases = false;
		brf2c.celldesignergenerator.defaultProteinWidth = 50;
		brf2c.celldesignergenerator.defaultProteinHeight = 50;
		brf2c.celldesignergenerator.shiftX = shiftX;
		brf2c.celldesignergenerator.shiftY = shiftY;
		brf2c.celldesignergenerator.scaleX = scaleX;
		brf2c.celldesignergenerator.scaleY = scaleY;

		brf2c.celldesignergenerator.addHUGO2ProteinNote = true;
		brf2c.celldesignergenerator.annotationFile = annotationFile;
		brf2c.celldesignergenerator.annotationFieldForLink = annotationFieldForLink;
		brf2c.celldesignergenerator.annotationFieldForLinkValue = annotationFieldForLinkValue;
		brf2c.celldesignergenerator.GMTFileHUGOMap = GMTFileHUGOMap;
		
		System.out.println("===========================");
		System.out.println("======= PASS 2    =========");
		System.out.println("===========================");
		
		brf2c.loadFile(brffile);
		graph = brf2c.convertToGraph();
		
		System.out.println("===========================");
		System.out.println("======= SAVING XML ========");
		System.out.println("===========================");
		
		int maxx = 0;
		int maxy = 0;
		
		for(Node n: graph.Nodes){
			if(n.x>maxx) maxx = (int)n.x;
			if(n.y>maxy) maxy = (int)n.y;
		}
		maxx+=brf2c.celldesignergenerator.defaultProteinWidth; maxx=(int)((float)maxx*1.1f);
		maxy+=brf2c.celldesignergenerator.defaultProteinHeight; maxy=(int)((float)maxy*1.1f);

		brf2c.celldesignergenerator.cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeX(""+maxx);
		brf2c.celldesignergenerator.cd.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeY(""+maxy);
		
		CellDesigner.saveCellDesigner(brf2c.celldesignergenerator.cd, prefix+"_1.xml");
		//CellDesigner.saveCellDesigner(brf2c.celldesignergenerator.cd, "C:/Datas/NaviCell2.2/maps/ewing_src/master.xml");
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
		celldesignergenerator.createNewCellDesignerFile(name,map_width,map_height);
		
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
	
	public void rescale(Graph graph, String names[], float positions[][]){
		float xold1 = graph.getNode(names[0]).x;
		float yold1 = graph.getNode(names[0]).y;
		float xold2 = graph.getNode(names[1]).x;
		float yold2 = graph.getNode(names[1]).y;
		
		float xnew1 = positions[0][0];
		float ynew1 = positions[0][1];
		float xnew2 = positions[1][0];
		float ynew2 = positions[1][1];
		

		// xnew = (xold-shiftX)*scaleX; xnew = xold*scaleX-shiftX*scaleX; xnew2-xnew1 = (xold2-xold1)*scaleX; shiftX = (xold1*scaleX - xnew1)/scaleX;
		// 
		celldesignergenerator.scaleX = (xnew2-xnew1)/(xold2-xold1);
		celldesignergenerator.scaleY = (ynew2-ynew1)/(yold2-yold1);
		celldesignergenerator.shiftX = (xold1*celldesignergenerator.scaleX-xnew1)/celldesignergenerator.scaleX;
		celldesignergenerator.shiftY = (yold1*celldesignergenerator.scaleY-ynew1)/celldesignergenerator.scaleY;
		
	}

}
