package fr.curie.BiNoM.pathways.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.xmlbeans.XmlString;
import org.sbml.x2001.ns.celldesigner.CelldesignerAntisenseRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerCompartmentAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerGateMemberDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerGeneDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerCompartmentAliasDocument.CelldesignerCompartmentAlias;
import org.sbml.x2001.ns.celldesigner.CelldesignerLayerCompartmentAliasDocument.CelldesignerLayerCompartmentAlias;
import org.sbml.x2001.ns.celldesigner.CelldesignerLayerDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerLayerDocument.CelldesignerLayer;
import org.sbml.x2001.ns.celldesigner.CelldesignerLayerFreeLineDocument.CelldesignerLayerFreeLine;
import org.sbml.x2001.ns.celldesigner.CelldesignerListOfFreeLinesDocument.CelldesignerListOfFreeLines;
import org.sbml.x2001.ns.celldesigner.CelldesignerListOfSquaresDocument.CelldesignerListOfSquares;
import org.sbml.x2001.ns.celldesigner.CelldesignerListOfTextsDocument.CelldesignerListOfTexts;
import org.sbml.x2001.ns.celldesigner.CelldesignerModificationDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerModificationResidueDocument.CelldesignerModificationResidue;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument.CelldesignerSpecies;
import org.sbml.x2001.ns.celldesigner.ReactionDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;

import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;


/**
 * Cell Designer merging maps procedures.
 */
public class MergingMapsProcessor {

	/**
	 * Not really used at the moment
	 */
	private Vector<String> speciesMapStr = new Vector<String>();
	
	/**
	 * Cell Designer file 1
	 * 
	 * All files will be merged into this file during the process.
	 */
	private SbmlDocument cd1;
	
	/**
	 * Cell Designer file 2
	 * 
	 * Files from the list are loaded as cd2, and then merged into cd1.
	 * 
	 */
	private SbmlDocument cd2;
	
	/**
	 * Cell Designer file string.
	 * 
	 * This string will be used to set and save unique Ids.
	 */
	private String cdFileString;
	
	/**
	 * random 4-letters prefix to be added to CellDesigner Ids
	 */
	private String prefix;
	
	/**
	 * map protein ID2 => protein ID1
	 */
	private HashMap<String, String> proteinMap = new HashMap<String, String>();
	
	/**
	 * List of protein ID2
	 */
	private ArrayList<String> proteinIdList;
	
	/**
	 * map geneID2 => geneID1
	 */
	private HashMap<String, String> geneMap = new HashMap<String, String>();
	
	/**
	 * map rnaID2 => rnaID1
	 */
	private HashMap<String, String> rnaMap = new HashMap<String, String>();
	
	/**
	 * map asRNA_ID2 => asRna_ID1
	 */
	private HashMap<String, String> asRnaMap = new HashMap<String, String>();
	
	/**
	 * List of gene ID2
	 */
	private ArrayList<String> geneIdList;
	
	/**
	 * List of rna ID2
	 */
	private ArrayList<String> rnaIdList;
	
	/**
	 * List of asRNA ID2
	 */
	private ArrayList<String> asRnaIdList;

	/**
	 * Simple class to store file name and coordinates for the maps to be merged.
	 */
	private class MapData {
		public String fileName;
		public int deltaX;
		public int deltaY;
		
		// constructor
		MapData(String fn, int dx, int dy) {
			fileName = fn;
			deltaX = dx;
			deltaY = dy;
		}
	}
	
	/**
	 * List of files to be merged + coordinates
	 */
	private ArrayList<MapData> mapList = new ArrayList<MapData>();
	
	/**
	 * Size X for merged map
	 */
	private String sizeX;
	
	/**
	 * Size Y for merged map
	 */
	private String sizeY;

	/**
	 * Map of files to be updated with global Ids
	 */
	private HashMap<String,String> updateMap = new HashMap<String,String>();
	
	/** 
	 * Print extended informations while merging elements
	 */
	public boolean verbose = false;
	
	/**
	 * Constructor
	 */
	public MergingMapsProcessor() {
		// there is no life in the void.
	}
	
	public static void main(String args[]){

		/*Vector<String> ids = new Vector<String>();
		ids.add("re1");
		ids.add("re12");
		ids.add("re2");
		ids.add("sa123");
		String text = "some words and \"re1\" \"sa123\" and after >re1< and\nsuddenly \"re2,\n\"re12\" after.everything.goes.until.point.";
		System.out.println(replaceCellDesignerPrefixList(text, ids, "test_"));
		System.exit(0);*/
		
		String configFile = "";
		String outputFileName = "";
		
		boolean mergeImages = false;
		boolean mergeMaps = false;
		boolean preprocess = false;
		boolean postprocess = false;		
		int zoomLevel = 3;
		int numberOfTimesToScale = 0;
		
		for(int i=0;i<args.length;i++){
			if(args[i].equals("--config"))
				configFile = args[i+1];
			if(args[i].equals("--out"))
				outputFileName = args[i+1];
			if(args[i].equals("--mergeimages"))
				mergeImages = true;
			if(args[i].equals("--mergemaps"))
				mergeMaps = true;
			if(args[i].equals("--preprocess"))
				preprocess = true;
			if(args[i].equals("--postprocess"))
				postprocess = true;
			if(args[i].equals("--zoomlevel"))
				zoomLevel = Integer.parseInt(args[i+1]);
			if(args[i].equals("--numberofscaleimages"))
				numberOfTimesToScale = Integer.parseInt(args[i+1]);
		}
		
		try{
			MergingMapsProcessor mm = new MergingMapsProcessor();
			mm.loadConfigFile(configFile);

			if(preprocess)
				mm.preProcessMergedMaps();

			if(mergeMaps)
				mm.mergeAll(outputFileName);

			if(postprocess)
				mm.postProcessMergedMap(outputFileName);


			if(mergeImages){
				String outputFileName_prefix = outputFileName;
				if(outputFileName.endsWith(".xml"))
					outputFileName_prefix = outputFileName.substring(0, outputFileName.length()-4);
				mm.mergeMapImages(outputFileName_prefix, zoomLevel, numberOfTimesToScale);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}	

	/**
	 * Add map data (filename, deltaX, deltaY) to the internal list of maps to be merged.
	 * 
	 * @param fileName
	 * @param deltaX upper-left X coordinate of the map on the merged map.
	 * @param deltaY upper-left Y coordinate of the map on the merged map.
	 */
	public void addMap(String fileName, int coordX, int coordY) {
		mapList.add(new MapData(fileName,coordX,coordY));
	}
	
	/**
	 * Set size values for new map
	 * 
	 * @param X
	 * @param Y
	 */
	public void setMapSize(int X, int Y) {
		sizeX = Integer.toString(X);
		sizeY = Integer.toString(Y);
	}
	
	/**
	 * Merge all maps into one.
	 * 
	 * @param sizeX
	 * @param sizeY
	 */
	public void mergeAll(String outputFileName) {
		
		
		System.out.println("==============================");
		System.out.println("=======   Merge maps    ======");		
		System.out.println("==============================");		
		
		
		int nbFiles = mapList.size();
		
		// load file 1 and add prefix
		System.out.println("loading file "+mapList.get(0).fileName+"...");
		Date time = new Date();
		String text = Utils.loadString(mapList.get(0).fileName);
		//generateRandomPrefifx();
		makePrefix(text);
		text = addPrefixToIds(text);
		cdFileString = text;
		setIdsAndSave(mapList.get(0).fileName);
		this.cd1 = CellDesigner.loadCellDesignerFromText(text);
		//System.out.println(" took "+(int)(((new Date()).getTime()-time.getTime())*0.001f)+" sec");
		this.cd1.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeX(sizeX);
		this.cd1.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeY(sizeY);
		shiftCoordinates(cd1,mapList.get(0).deltaX, mapList.get(0).deltaY);
		
		setAndLoadFileName2(mapList.get(1).fileName);
		time = new Date();
		System.out.println("merging file "+mapList.get(1).fileName+"...");
		shiftCoordinates(cd2, mapList.get(1).deltaX, mapList.get(1).deltaY);
		//System.out.println(" shift "+(int)(((new Date()).getTime()-time.getTime())*0.001f)+" sec");
		produceCandidateMergeLists();
		//System.out.println(" candidate "+(int)(((new Date()).getTime()-time.getTime())*0.001f)+" sec");
		setIdsAndSave(mapList.get(1).fileName);
		//System.out.println(" setids "+(int)(((new Date()).getTime()-time.getTime())*0.001f)+" sec");
		mergeDiagrams();
		//System.out.println(" merge diagrams "+(int)(((new Date()).getTime()-time.getTime())*0.001f)+" sec");
		mergeElements();
		//System.out.println(" took "+(int)(((new Date()).getTime()-time.getTime())*0.001f)+" sec");
		Utils.printUsedMemory();
		

		for (int i=2;i<nbFiles;i++) {
			// set new file 2 name
			setAndLoadFileName2(mapList.get(i).fileName);
			time = new Date();
			System.out.println("merging file "+mapList.get(i).fileName+"...");
			shiftCoordinates(cd2, mapList.get(i).deltaX, mapList.get(i).deltaY);
			// create new common species names
			produceCandidateMergeLists();
			setIdsAndSave(mapList.get(i).fileName);
			// merge maps
			mergeDiagrams();
			mergeElements();
			//System.out.println(" took "+(int)(((new Date()).getTime()-time.getTime())*0.001f)+" sec");
			Utils.printUsedMemory();
		}
		
		formatLayers();
		System.out.println("saving merged file "+outputFileName+"...");
		CellDesigner.saveCellDesigner(cd1, outputFileName);
		
		updateMapAll();
	}
	
	/**
	 * Load CD file 2. Add a prefix to all element IDs.
	 * 
	 * @param fileName
	 */
	private void setAndLoadFileName2 (String fileName) {
		Date time = new Date();
		System.out.println("loading file "+fileName+"...");
		String text = Utils.loadString(fileName);
		//generateRandomPrefix();
		makePrefix(text);
		text = addPrefixToIds(text);
		this.cdFileString = text;
		this.cd2 = CellDesigner.loadCellDesignerFromText(text);
		//System.out.println(" took "+(int)(((new Date()).getTime()-time.getTime())*0.001f)+" sec");
	}
	
	/**
	 * shift coordinates for elements of a CellDesigner file 
	 * 
	 * @param cd CellDesigner file
	 * @param deltaX float delta x value
	 * @param deltaY float delta y value
	 */
	private void shiftCoordinates(SbmlDocument cd,float deltaX, float deltaY) {

		// species aliases
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias cdal = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			float  x = Float.parseFloat(cdal.getCelldesignerBounds().getX());
			float  y = Float.parseFloat(cdal.getCelldesignerBounds().getY());
			x += deltaX;
			y += deltaY;
			cdal.getCelldesignerBounds().setX(Float.toString(x));
			cdal.getCelldesignerBounds().setY(Float.toString(y));
		}

		// compartments
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray().length;i++){
			CelldesignerCompartmentAliasDocument.CelldesignerCompartmentAlias csa = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray(i);
			if(csa.getCelldesignerBounds()!=null){
				float x = Float.parseFloat(csa.getCelldesignerBounds().getX());
				float y = Float.parseFloat(csa.getCelldesignerBounds().getY());
				x += deltaX;
				y += deltaY;
				csa.getCelldesignerBounds().setX(Float.toString(x));
				csa.getCelldesignerBounds().setY(Float.toString(y));
			}
		}

		// complex species aliases
		if(cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null) {
			for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
				CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias cdal = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
				float  x = Float.parseFloat(cdal.getCelldesignerBounds().getX());
				float  y = Float.parseFloat(cdal.getCelldesignerBounds().getY());
				x += deltaX;
				y += deltaY;
				cdal.getCelldesignerBounds().setX(Float.toString(x));
				cdal.getCelldesignerBounds().setY(Float.toString(y));
			}
		}
		
		/*
		 * Special case: shift coordinates for boolean logic gates
		 */
		if (cd.getSbml().getModel().getListOfReactions() != null) {
			for(int i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
				ReactionDocument.Reaction r = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);

				// cd version 4.1
				if(r.getAnnotation().getCelldesignerListOfModification()!=null) {
					for(int j=0;j<r.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();j++){
						CelldesignerModificationDocument.CelldesignerModification cmd = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
						String type = cmd.getType();
						if (type.contains("BOOLEAN_LOGIC")) {
							String str = Utils.getValue(cmd.getEditPoints());
							String[] pair = str.split("\\s+");
							for (int n=0;n<pair.length;n++) {
								String[] coord = pair[n].split(",");
								float x = Float.parseFloat(coord[0]);
								float y = Float.parseFloat(coord[1]);
								x += deltaX;
								y += deltaY;
								str = x+","+y;
								Utils.setValue(cmd.getEditPoints(),str);
							}
						}
					}
				}

				// cd files version 4.2
				if(r.getAnnotation().getCelldesignerListOfGateMember()!=null) {
					for(int j=0;j<r.getAnnotation().getCelldesignerListOfGateMember().sizeOfCelldesignerGateMemberArray();j++){
						CelldesignerGateMemberDocument.CelldesignerGateMember cmd = r.getAnnotation().getCelldesignerListOfGateMember().getCelldesignerGateMemberArray(j);
						String type = cmd.getType();
						if (type.contains("BOOLEAN_LOGIC")) {
							String str = Utils.getValue(cmd.getEditPoints());
							String[] pair = str.split("\\s+");
							for (int n=0;n<pair.length;n++) {
								String[] coord = pair[n].split(",");
								float x = Float.parseFloat(coord[0]);
								float y = Float.parseFloat(coord[1]);
								x += deltaX;
								y += deltaY;
								str = x+","+y;
								Utils.setValue(cmd.getEditPoints(),str);
							}
						}
					}
				}
			}
		}
		
		/*
		 * Layers: squares and lines
		 */
		if (cd.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers() != null) {
			for (int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().sizeOfCelldesignerLayerArray(); i++) {
				CelldesignerListOfSquares squares = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().getCelldesignerLayerArray(i).getCelldesignerListOfSquares();
				if (squares != null) {
					for (int j=0;j<squares.sizeOfCelldesignerLayerCompartmentAliasArray();j++) {
						CelldesignerLayerCompartmentAlias sq =  squares.getCelldesignerLayerCompartmentAliasArray(j);
						float  x = Float.parseFloat(sq.getCelldesignerBounds().getX());
						float  y = Float.parseFloat(sq.getCelldesignerBounds().getY());
						x += deltaX;
						y += deltaY;
						sq.getCelldesignerBounds().setX(Float.toString(x));
						sq.getCelldesignerBounds().setY(Float.toString(y));
					}
				}
				
				CelldesignerListOfFreeLines lines = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().getCelldesignerLayerArray(i).getCelldesignerListOfFreeLines();
				if (lines != null) {
					for (int j=0;j<lines.sizeOfCelldesignerLayerFreeLineArray();j++) {
						CelldesignerLayerFreeLine l =  lines.getCelldesignerLayerFreeLineArray(j);
						float  sx = Float.parseFloat(l.getCelldesignerBounds().getSx());
						float  sy = Float.parseFloat(l.getCelldesignerBounds().getSy());
						float ex = Float.parseFloat(l.getCelldesignerBounds().getEx());
						float ey = Float.parseFloat(l.getCelldesignerBounds().getEy());
						sx += deltaX;
						sy += deltaY;
						ex += deltaX;
						ey += deltaY;
						l.getCelldesignerBounds().setSx(Float.toString(sx));
						l.getCelldesignerBounds().setEx(Float.toString(sy));
						l.getCelldesignerBounds().setEx(Float.toString(ex));
						l.getCelldesignerBounds().setEy(Float.toString(ey));
					}
				}
			}
		}
	}
	
	/**
	 * Just add a given prefix to all IDs on a given CellDesigner map.
	 * 
	 * @param text the whole map as a string
	 * @return string the whole modified map as a string
	 */
	private String addPrefixToIds(String text){
		//System.out.println("---- add prefix to IDs");
		
		/*
		 * find out model ID, we don't want to add a prefix to this ID.
		 */
		//Pattern pat = Pattern.compile("model\\s+\\S+\\s+id=\"(\\S+)\"");
		Pattern pat = Pattern.compile("model\\s+metaid=\"(\\S+)\"\\s+id=\"(\\S+)\"");
		Matcher mat = pat.matcher(text);
		String id1 = "";
		String id2 = "";
		if(mat.find()) {
			//System.out.println(">>>pat: "+mat.group(0)+" "+mat.group(1));
			id1 = mat.group(1);
			id2 = mat.group(2);
		}
		
		Vector<String> ids = Utils.extractAllStringBetween(text, "id=\"", "\"");
		//System.out.println("ids = "+ids.size());
		Vector<String> newids = new Vector<String>();
		for(int i=0;i<ids.size();i++) {
			if(!ids.get(i).equals(id1) && !ids.get(i).equals(id2) && ids.get(i).equals("default") == false && isNumeric(ids.get(i)) == false) {
				//System.out.println("--> id= "+ids.get(i));
				//System.out.println("replace: "+"\""+ids.get(i)+"\""+ " with "+ "\""+prefix+""+ids.get(i)+"\"");
				//System.out.println("replace: " + ">"+ids.get(i)+"<" + " with "+ ">"+prefix+""+ids.get(i)+"<");
				
				//text = Utils.replaceString(text, "\""+ids.get(i)+"\"", "\""+prefix+""+ids.get(i)+"\"");
				//text = Utils.replaceString(text, ">"+ids.get(i)+"<", ">"+prefix+""+ids.get(i)+"<");
				/*
				 * Special case: change id in boolean gate elements:
				 * 
				 * model = <celldesigner:modification type="BOOLEAN_LOGIC_GATE_AND" modifiers="cd2_s187,cd2_s801,cd2_s608" aliases="sa43,csa5,sa44"
				 */
				//text = Utils.replaceString(text, "\""+ids.get(i)+",", "\""+prefix+ids.get(i)+",");
				//text = Utils.replaceString(text, ","+ids.get(i)+",", ","+prefix+ids.get(i)+",");
				//text = Utils.replaceString(text, ","+ids.get(i)+"\"", ","+prefix+ids.get(i)+"\"");
				
		
				//Date time = new Date();
				//text = replaceCellDesignerPrefix(text,ids.get(i), prefix);
				//text = text.replaceAll("\""+ids.get(i)+"\"", "\""+prefix+""+ids.get(i)+"\"");
				//text = text.replaceAll(">"+ids.get(i)+"<", ">"+prefix+""+ids.get(i)+"<");
				//text = text.replaceAll("\""+ids.get(i)+",", "\""+prefix+ids.get(i)+",");
				//text = text.replaceAll(","+ids.get(i)+",", ","+prefix+ids.get(i)+",");
				//text = text.replaceAll(","+ids.get(i)+"\"", ","+prefix+ids.get(i)+"\"");
				//System.out.println("Time to replace "+((new Date()).getTime()-time.getTime()));
				newids.add(ids.get(i));
			}
		}
		text = replaceCellDesignerPrefixList(text,newids, prefix);
		
		/*
		 * id replacement in tag "units" is giving a bug in CellDesigner
		 * so here we find all occurences of "units" and set them back 
		 * to an accepted value. 
		 */
		pat = Pattern.compile("units=\"(\\S+)\"");
		mat = pat.matcher(text);
		HashSet<String> w = new HashSet<String>();
		while(mat.find()) {
			if (!mat.group(1).equalsIgnoreCase("volume"))
				w.add(mat.group(1));
		}
		for (String s : w) {
			pat = Pattern.compile("units=\""+s+"\"");
			mat = pat.matcher(text);
			while(mat.find())
				text = mat.replaceAll("units=\"volume\"");
		}
		return text;
	}
	
	public static String replaceCellDesignerPrefixList(String text, Vector<String> ids, String prefix){
		char ctext[] = text.toCharArray();
		char ctextnew[] = new char[ctext.length+(int)(ctext.length*2)];
		// some hashing of ids by first two letters
		HashMap<String, Vector<char[]>> hash = new HashMap<String, Vector<char[]>>();
		int maxidlength = 0;
		for(String s: ids){
			char cs[] = s.toCharArray();
			String s2 = s.substring(0, 2);
			Vector<char[]> vcs = new Vector<char[]>(); 
			if(hash.get(s2)!=null)
				vcs = hash.get(s2);
			vcs.add(cs);
			hash.put(s2, vcs);
			if(cs.length>maxidlength)
				maxidlength = cs.length;
		}
		
		char cprefix[] = prefix.toCharArray();
		int i=0; int textlength = ctext.length-maxidlength-1;
		int in=0;
		while(i<textlength){
			char h[] = new char[2];
			h[0] = ctext[i];
			h[1] = ctext[i+1];
			String s2 = new String(h);
			//if(s2.equals("ks")){
			//	System.out.println(i+"\t"+new String(ctext,i,5));
			//}
			Vector<char[]> candidates = hash.get(s2);
			if(candidates==null){ 
				ctextnew[in++] = ctext[i++];
			}else{

				boolean replacementmade = false;
				for(char cid[] :candidates){
					
				boolean idfound = true;
				
				for(int j=0;j<cid.length;j++)
					if(ctext[i+j]!=cid[j]){
						idfound = false;
						break;
					}

				if(idfound){
				boolean goodcontext = false;
				if((ctext[i-1]=='\"')&&(ctext[i+cid.length]=='\"'))
					goodcontext = true;
				else
				if((ctext[i-1]=='>')&&(ctext[i+cid.length]=='<'))
					goodcontext = true;
				else
				if((ctext[i-1]=='\"')&&(ctext[i+cid.length]==','))
					goodcontext = true;
				else
				if((ctext[i-1]==',')&&(ctext[i+cid.length]==','))
					goodcontext = true;
				else
				if((ctext[i-1]==',')&&(ctext[i+cid.length]=='\"'))
					goodcontext = true;
				if(goodcontext){
					for(int k=0;k<cprefix.length;k++)
						ctextnew[in+k] = cprefix[k];
					in+=cprefix.length;
					for(int k=0;k<cid.length;k++)
						ctextnew[in+k] = cid[k];
					in+=cid.length;
					i+=cid.length;
					replacementmade = true;
					break;
				}
			}
			}
			if(!replacementmade)
				ctextnew[in++] = ctext[i++];
				
			}
		}
		
		for(int k=textlength;k<ctext.length;k++)
			ctextnew[in++] = ctext[k];
		
		
		String res = new String(ctextnew,0,in);
		return res;
	}
	
	public static String replaceCellDesignerPrefixList(String text, HashMap<String, String> map){
		char ctext[] = text.toCharArray();
		char ctextnew[] = new char[ctext.length+(int)(ctext.length*2)];
		// some hashing of ids by first two letters
		HashMap<String, Vector<char[]>> hash = new HashMap<String, Vector<char[]>>();
		int maxidlength = 0;
		
		Vector<String> ids = new Vector<String>();
		for(String s: map.keySet()){
			ids.add(s);
		}
		
		for(String s: ids){
			char cs[] = s.toCharArray();
			String s2 = s.substring(0, 2);
			Vector<char[]> vcs = new Vector<char[]>(); 
			if(hash.get(s2)!=null)
				vcs = hash.get(s2);
			vcs.add(cs);
			hash.put(s2, vcs);
			if(cs.length>maxidlength)
				maxidlength = cs.length;
		}
		
		int i=0; int textlength = ctext.length-maxidlength-1;
		int in=0;
		while(i<textlength){
			char h[] = new char[2];
			h[0] = ctext[i];
			h[1] = ctext[i+1];
			String s2 = new String(h);
			//if(s2.equals("ks")){
			//	System.out.println(i+"\t"+new String(ctext,i,5));
			//}
			Vector<char[]> candidates = hash.get(s2);
			if(candidates==null){ 
				ctextnew[in++] = ctext[i++];
			}else{

				boolean replacementmade = false;
				for(char cid[] :candidates){
					
				boolean idfound = true;
				
				for(int j=0;j<cid.length;j++)
					if(ctext[i+j]!=cid[j]){
						idfound = false;
						break;
					}

				if(idfound){
				boolean goodcontext = true;
				/*if((ctext[i-1]=='\"')&&(ctext[i+cid.length]=='\"'))
					goodcontext = true;
				else
				if((ctext[i-1]=='>')&&(ctext[i+cid.length]=='<'))
					goodcontext = true;
				else
				if((ctext[i-1]=='\"')&&(ctext[i+cid.length]==','))
					goodcontext = true;
				else
				if((ctext[i-1]==',')&&(ctext[i+cid.length]==','))
					goodcontext = true;
				else
				if((ctext[i-1]==',')&&(ctext[i+cid.length]=='\"'))
					goodcontext = true;*/
				char cprefix[] = map.get(new String(cid)).toCharArray();
				if(goodcontext){
					for(int k=0;k<cprefix.length;k++)
						ctextnew[in+k] = cprefix[k];
					in+=cprefix.length;
					i+=cid.length;
					replacementmade = true;
					break;
				}
			}
			}
			if(!replacementmade)
				ctextnew[in++] = ctext[i++];
				
			}
		}
		
		for(int k=textlength;k<ctext.length;k++)
			ctextnew[in++] = ctext[k];
		
		
		String res = new String(ctextnew,0,in);
		return res;
	}
	
	
	public static String replaceCellDesignerPrefix(String text, String id, String prefix){
		//text = text.replaceAll("\""+ids.get(i)+"\"", "\""+prefix+""+ids.get(i)+"\"");
		//text = text.replaceAll(">"+ids.get(i)+"<", ">"+prefix+""+ids.get(i)+"<");
		//text = text.replaceAll("\""+ids.get(i)+",", "\""+prefix+ids.get(i)+",");
		//text = text.replaceAll(","+ids.get(i)+",", ","+prefix+ids.get(i)+",");
		//text = text.replaceAll(","+ids.get(i)+"\"", ","+prefix+ids.get(i)+"\"");
		//StringBuilder res = new StringBuilder();
		char ctext[] = text.toCharArray();
		char ctextnew[] = new char[ctext.length+(int)(ctext.length*2)];
		char cid[] = id.toCharArray();
		char cprefix[] = prefix.toCharArray();
		int i=0; int textlength = ctext.length-cid.length-1;
		int in=0;
		while(i<textlength){
			boolean idfound = true;
			for(int j=0;j<cid.length;j++)
				if(ctext[i+j]!=cid[j]){
					idfound = false;
					break;
				}
			if(idfound){
				boolean goodcontext = false;
				if((ctext[i-1]=='\"')&&(ctext[i+cid.length]=='\"'))
					goodcontext = true;
				else
				if((ctext[i-1]=='>')&&(ctext[i+cid.length]=='<'))
					goodcontext = true;
				else
				if((ctext[i-1]=='\"')&&(ctext[i+cid.length]==','))
					goodcontext = true;
				else
				if((ctext[i-1]==',')&&(ctext[i+cid.length]==','))
					goodcontext = true;
				else
				if((ctext[i-1]==',')&&(ctext[i+cid.length]=='\"'))
					goodcontext = true;
			if(goodcontext){
				for(int k=0;k<cprefix.length;k++)
					ctextnew[in+k] = cprefix[k];
				in+=cprefix.length;
				for(int k=0;k<cid.length;k++)
					ctextnew[in+k] = cid[k];
				in+=cid.length;
				i+=id.length();
			}else{
				ctextnew[in++] = ctext[i++];
			}
			}else{
				ctextnew[in++] = ctext[i++];
			}
		}
		for(int k=textlength;k<ctext.length;k++)
			ctextnew[in++] = ctext[k];
		
		
		String res = new String(ctextnew,0,in);
		return res;
	}

	/**
	 * Merge "mechanically" all the CellDesigner components of file cd2 into file cd1.
	 */
	private void mergeDiagrams() {
		
		// Compartments
		for(int i=0;i<cd2.getSbml().getModel().getListOfCompartments().sizeOfCompartmentArray();i++)
			if(!cd2.getSbml().getModel().getListOfCompartments().getCompartmentArray(i).getId().equals("default"))
				cd1.getSbml().getModel().getListOfCompartments().addNewCompartment().set(cd2.getSbml().getModel().getListOfCompartments().getCompartmentArray(i));
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().sizeOfCelldesignerCompartmentAliasArray();i++)
			cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().addNewCelldesignerCompartmentAlias().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray(i));
		
		// Proteins, Genes, RNA
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++)
			cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().addNewCelldesignerProtein().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i));
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++)
			cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().addNewCelldesignerGene().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i));
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++)
			cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().addNewCelldesignerRNA().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i));
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().sizeOfCelldesignerAntisenseRNAArray();i++)
			cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().addNewCelldesignerAntisenseRNA().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i));
		
		// Species and Reactions
		for(int i=0;i<cd2.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++)
			cd1.getSbml().getModel().getListOfSpecies().addNewSpecies().set(cd2.getSbml().getModel().getListOfSpecies().getSpeciesArray(i));
		if(cd2.getSbml().getModel().getListOfReactions()!=null)
			for(int i=0;i<cd2.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++)
				cd1.getSbml().getModel().getListOfReactions().addNewReaction().set(cd2.getSbml().getModel().getListOfReactions().getReactionArray(i));
		
		// Included, simple and complex Aliases
		if(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
			for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++)
				cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().addNewCelldesignerSpecies().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i));
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++)
			cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().addNewCelldesignerSpeciesAlias().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i));
		if(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
			for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++)
				cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().addNewCelldesignerComplexSpeciesAlias().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i));
	
		// Layers
		if (cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers() != null) {
			for (int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().sizeOfCelldesignerLayerArray(); i++) {
				cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().addNewCelldesignerLayer().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().getCelldesignerLayerArray(i));
			}
		}
	}

	/**
	 * Find proteins and species sharing the same name.
	 * Build maps id2 => id1 and list of id2 for common entities
	 * 
	 * Legacy stuff: for common species, build a string vector:
	 * "alias_ID1 species_ID1 compartment1 species_name1 alias_ID2 species_ID2 compartment2 species_name2" 
	 * 
	 */
	private void produceCandidateMergeLists() {
		
		/*
		 * Build map of entity name1 to id1
		 * Search for entities in file 2 having name1
		 * Build map common entity id2 => id1
		 * Build list common entities id2
		 */
		
		// proteins
		HashMap<String,String> proteinNames = new HashMap<String,String>();
		for(int i=0;i<cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein p = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			proteinNames.put(Utils.getValue(p.getName()), p.getId());
			//System.out.println("Names: "+Utils.getValue(p.getName())+"\t"+p.getId());
		}
		this.proteinMap = new HashMap<String, String>();
		this.proteinIdList = new ArrayList<String>();
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein p = cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			String name = Utils.getValue(p.getName());
			if(proteinNames.containsKey(name)){
				//proteinMap.add(proteinNames.get(name)+"\t"+name+"\t"+p.getId()+"\t"+name);
				//System.out.println(name+"id2: "+p.getId());
				this.proteinMap.put(p.getId(), proteinNames.get(name));
				this.proteinIdList.add(p.getId());
			}
		}

		// Genes
		HashMap<String, String> geneNames = new HashMap<String, String>();
		for(int i=0;i<cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			CelldesignerGeneDocument.CelldesignerGene g = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			geneNames.put(g.getName(), g.getId());
		}
		this.geneMap = new HashMap<String, String>();
		this.geneIdList = new ArrayList<String>();
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
		  CelldesignerGeneDocument.CelldesignerGene g = cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
		  if (geneNames.containsKey(g.getName())) {
			  //System.out.println(">>>duplicated gene name: "+g.getName() + " id: " + g.getId());
			  this.geneMap.put(g.getId(), geneNames.get(g.getName()));
			  this.geneIdList.add(g.getId());
		  }
		}
		
		// RNAs
		HashMap<String, String> rnaNames = new HashMap<String, String>();
		for(int i=0;i<cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
			CelldesignerRNADocument.CelldesignerRNA rna = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
			rnaNames.put(rna.getName(), rna.getId());
		}
		this.rnaMap = new HashMap<String, String>();
		this.rnaIdList = new ArrayList<String>();
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
			CelldesignerRNADocument.CelldesignerRNA rna = cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
			if (rnaNames.containsKey(rna.getName())) {
				//System.out.println(">>>duplicated RNA gene name: "+rna.getName() + " id: " + rna.getId());
				this.rnaMap.put(rna.getId(), rnaNames.get(rna.getName()));
				this.rnaIdList.add(rna.getId());
			}
		}

		// asRNAs
		HashMap<String, String> asRnaNames = new HashMap<String, String>();
		for(int i=0;i<cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().sizeOfCelldesignerAntisenseRNAArray();i++){
			CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA asRna = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
			asRnaNames.put(asRna.getName(), asRna.getId());
			//System.out.println("asRNA file 1: "+asRna.getName() + " id: "+ asRna.getId());
		}
		this.asRnaMap = new HashMap<String, String>();
		this.asRnaIdList = new ArrayList<String>();
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().sizeOfCelldesignerAntisenseRNAArray();i++){
			CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA asRna = cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
			if (asRnaNames.containsKey(asRna.getName())) {
				//System.out.println(">>>duplicated asRNA name: "+asRna.getName() + " id: " + asRna.getId());
				this.asRnaMap.put(asRna.getId(), asRnaNames.get(asRna.getName()));
				this.asRnaIdList.add(asRna.getId());
			}
		}

		// get file1 entities
		CellDesigner.entities = CellDesigner.getEntities(cd1);

		// map of species ID to species for file1 
		HashMap<String,SpeciesDocument.Species> species = new HashMap<String,SpeciesDocument.Species>();
		for(int i=0;i<cd1.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = cd1.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			species.put(sp.getId(), sp);
			//System.out.println(sp.getId());
		}

		// map of unique species Cytoscape name to id (including aliases)
		HashMap<String,String> speciesIds = new HashMap<String,String>(); 
		HashMap<String,Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>> speciesAliases = new HashMap<String,Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>>(); // From species IDs to all corresponding aliases 
		CellDesignerToCytoscapeConverter c2c = new CellDesignerToCytoscapeConverter();
		c2c.sbml = cd1;
		c2c.createSpeciesMap(cd1.getSbml());

		for(int i=0;i<cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias cas = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			String spId = cas.getSpecies();
			String id = cas.getId();
			SpeciesDocument.Species sp = species.get(spId);
			if(sp!=null){
				String spName = c2c.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), spId, Utils.getValue(sp.getName()), sp.getCompartment(), true, true, "", cd1);
				speciesIds.put(spName, spId);
				Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias> vsa = speciesAliases.get(spId);
				if(vsa==null){
					vsa = new Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>();
				}
				vsa.add(cas);
				speciesAliases.put(spId, vsa);
			}
		}

		// map species ID -> species obj
		CellDesigner.entities = CellDesigner.getEntities(cd2);
		HashMap<String,SpeciesDocument.Species> species2 = new HashMap<String,SpeciesDocument.Species>();
		for(int i=0;i<cd2.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = cd2.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			species2.put(sp.getId(), sp);
		}

		// cell designer to cytoscape converter
		c2c = new CellDesignerToCytoscapeConverter();
		c2c.sbml = cd2;

		// write a map of common species names
		//fw = new FileWriter(prefix+".txt");
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias cas = cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			String spId = cas.getSpecies();
			String id = cas.getId();
			SpeciesDocument.Species sp = species2.get(spId);
			if(sp!=null){
				String spName = c2c.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), spId, Utils.getValue(sp.getName()), sp.getCompartment(), true, true, "", cd2);
				/*
				 * check if species alias 2 name is contained in map 1
				 */
				if(speciesIds.containsKey(spName)){
					String id1 = speciesIds.get(spName); 
					Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias> vsa = speciesAliases.get(id1);
					if(vsa==null)
						System.out.println("Vector of aliases is not found for "+id1);
					//fw.write(vsa.get(0).getId()+"\t"+id1+"\t"+species.get(id1).getCompartment()+"\t"+spName+"\t"+cas.getId()+"\t"+spId+"\t"+sp.getCompartment()+"\t"+spName+"\n");
					speciesMapStr.add(vsa.get(0).getId()+"\t"+id1+"\t"+species.get(id1).getCompartment()+"\t"+spName+"\t"+cas.getId()+"\t"+spId+"\t"+sp.getCompartment()+"\t"+spName);
					//System.out.println(">>>"+vsa.get(0).getId()+".."+id1+".."+species.get(id1).getCompartment()+".."+spName+".."+cas.getId()+".."+spId+".."+sp.getCompartment()+".."+spName);
				}
			}
		}
		//fw.close();
	}



	/**
	 * Merge redundant proteins, genes and RNAs.
	 */
	private void mergeElements() {

		CellDesigner.entities = CellDesigner.getEntities(cd1);
		XmlString xs = XmlString.Factory.newInstance();

		CellDesignerToCytoscapeConverter.createSpeciesMap(cd1.getSbml());

		/*
		 *  Deal with redundant proteins, genes and rnas
		 */
		
		/*
		 * Process included species (species within complexes)
		 */
		CellDesigner.entities = CellDesigner.getEntities(cd1);
		if(cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null) {
			for(int i=0;i<cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
				
				CelldesignerSpeciesDocument.CelldesignerSpecies csp = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
				
				// proteins
				if(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null){
					String pr = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
					if(proteinMap.get(pr)!=null){
						xs.setStringValue(proteinMap.get(pr));
						//CellDesigner.entities = CellDesigner.getEntities(cd1);
						String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
						csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference().set(xs);
						xs.setStringValue(Utils.getValue(getProtein(cd1,proteinMap.get(pr)).getName())); 
						csp.setName(xs);
						if (verbose)
							System.out.println("Changed protein reference in "+csp.getId()+" from "+pr+" ("+Utils.getValue(getProtein(cd1,pr).getName())+") to "+proteinMap.get(pr)+" ("+Utils.getValue(getProtein(cd1,proteinMap.get(pr)).getName())+")");
						//CellDesigner.entities = CellDesigner.getEntities(cd1);
						if (verbose) {
							System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true));
							System.out.println();
						}
					}
				}
				
				// genes
				if(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference()!=null){
					String ge = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference());
					if(geneMap.get(ge)!=null){
						xs.setStringValue(geneMap.get(ge));
						//CellDesigner.entities = CellDesigner.getEntities(cd1);
						String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
						csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference().set(xs);
						xs.setStringValue(getGene(cd1,geneMap.get(ge)).getName()); 
						csp.setName(xs);
						if (verbose)
							System.out.print("Changed gene reference in "+csp.getId()+" from "+ge+" ("+getGene(cd1,ge).getName()+") to "+geneMap.get(ge)+" ("+getGene(cd1,geneMap.get(ge)).getName()+")");
						//CellDesigner.entities = CellDesigner.getEntities(cd1);
						if (verbose) {
							System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true));
							System.out.println();
						}
					}
				}
				
				// RNAs
				if(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()!=null){
					String rna = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference());
					if(rnaMap.get(rna)!=null){
						xs.setStringValue(rnaMap.get(rna));
						//CellDesigner.entities = CellDesigner.getEntities(cd1);
						String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
						csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference().set(xs);
						xs.setStringValue(getRNA(cd1,rnaMap.get(rna)).getName()); 
						csp.setName(xs);
						if (verbose)
							System.out.println("Changed rna reference in "+csp.getId()+" from "+rna+" ("+getRNA(cd1,rna).getName()+") to "+rnaMap.get(rna)+" ("+getRNA(cd1,rnaMap.get(rna)).getName()+")");
						//CellDesigner.entities = CellDesigner.getEntities(cd1);
						if (verbose) {
							System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true));
							System.out.println();
						}
					}
				}

				// asRNAs
				if(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference()!=null){
					String asrna = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference());
					if(asRnaMap.get(asrna)!=null){
						xs.setStringValue(asRnaMap.get(asrna));
						//CellDesigner.entities = CellDesigner.getEntities(cd1);
						String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
						csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference().set(xs);
						xs.setStringValue(getAsRNA(cd1,asRnaMap.get(asrna)).getName()); 
						csp.setName(xs);
						if (verbose)
							System.out.println("Changed asrna reference in "+csp.getId()+" from "+asrna+" ("+getRNA(cd1,asrna).getName()+") to "+asRnaMap.get(asrna)+" ("+getAsRNA(cd1,asRnaMap.get(asrna)).getName()+")");
						//CellDesigner.entities = CellDesigner.getEntities(cd1);
						if (verbose) {
							System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true));
							System.out.println();
						}
					}
				}				
				
			}
		}

		/*
		 * Change reference to proteins, genes, RNA, antisenseRNA, in species
		 */
		for(int i=0;i<cd1.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species csp = cd1.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			if(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null){
				String pr = Utils.getValue(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
				if(proteinMap.get(pr)!=null){
					xs.setStringValue(proteinMap.get(pr));
					//CellDesigner.entities = CellDesigner.getEntities(cd1);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true);
					csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference().set(xs);
					if (verbose)
						System.out.println("Changed protein reference in "+csp.getId()+" from "+pr+" ("+Utils.getValue(getProtein(cd1,pr).getName())+") to "+proteinMap.get(pr)+" ("+Utils.getValue(getProtein(cd1,proteinMap.get(pr)).getName())+")");
					//CellDesigner.entities = CellDesigner.getEntities(cd1);
					if (verbose) {
						System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true));
						System.out.println();
					}
				}
			}
			if(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference()!=null){
				String str = Utils.getValue(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference());
				if(geneMap.get(str)!=null){
					xs.setStringValue(geneMap.get(str));
					//CellDesigner.entities = CellDesigner.getEntities(cd1);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true);
					csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference().set(xs);
					if (verbose)
						System.out.println("Changed gene reference in "+csp.getId()+" from "+str+" ("+getGene(cd1,str).getName()+") to "+geneMap.get(str)+" ("+getGene(cd1,geneMap.get(str)).getName()+")");
					//CellDesigner.entities = CellDesigner.getEntities(cd1);
					if (verbose) {
						System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true));
						System.out.println();
					}
				}
			}
			if(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()!=null){
				String str = Utils.getValue(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference());
				if(rnaMap.get(str)!=null){
					xs.setStringValue(rnaMap.get(str));
					//CellDesigner.entities = CellDesigner.getEntities(cd1);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true);
					csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference().set(xs);
					if (verbose)
						System.out.println("Changed RNA reference in "+csp.getId()+" from "+str+" ("+getRNA(cd1,str).getName()+") to "+rnaMap.get(str)+" ("+getRNA(cd1,rnaMap.get(str)).getName()+")");
					//CellDesigner.entities = CellDesigner.getEntities(cd1);
					if (verbose) {
						System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true));
					}
				}
			}
			if(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference()!=null){
				String str = Utils.getValue(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference());
				if(asRnaMap.get(str)!=null){
					xs.setStringValue(asRnaMap.get(str));
					//CellDesigner.entities = CellDesigner.getEntities(cd1);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true);
					csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference().set(xs);
					if (verbose)
						System.out.println("Changed Antisense RNA reference in "+csp.getId()+" from "+str+" ("+getAsRNA(cd1,str).getName()+") to "+asRnaMap.get(str)+" ("+getAsRNA(cd1,asRnaMap.get(str)).getName()+")");
					//CellDesigner.entities = CellDesigner.getEntities(cd1);
					if (verbose)
						System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true));
				}
			}
		}

		/*
		 * Remove redundancy for reference elements
		 * Process annotations and modifications
		 */
		int i=0; 
		int numberOfProteins = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();
		while(i<numberOfProteins){
			CelldesignerProteinDocument.CelldesignerProtein protein = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			//protein (ID) is contained in cd 2 ?
			if(proteinIdList.indexOf(protein.getId())>=0){
				// get corresponding prot id1 and CD object
				String pto = proteinMap.get(protein.getId());
				CelldesignerProteinDocument.CelldesignerProtein proteinto = getProtein(cd1,pto);
				if(proteinto==null)
					System.out.println("ERROR: protein "+protein.getId()+"("+Utils.getValue(protein.getName())+") not found in "+cd1.getSbml().getModel().getId());
				// merge comments into prot 1
				if(protein.getCelldesignerNotes()!=null){
					String comment = Utils.getValue(protein.getCelldesignerNotes()).trim();
					if(proteinto.getCelldesignerNotes()==null)
						proteinto.addNewCelldesignerNotes();
					String commentto = Utils.getValue(proteinto.getCelldesignerNotes()).trim();
					//System.out.println("comment:\n"+comment+"\ncommento:\n"+commentto+"\n\n");
					xs.setStringValue("<&html><&body>"+commentto+"\n"+comment+"<&/body><&/html>");
					proteinto.getCelldesignerNotes().set(xs);
					//System.out.println("merge:"+proteinto.getCelldesignerNotes()+"\n\n");
				}
				// add modifications to prot 1
				if(protein.getCelldesignerListOfModificationResidues()!=null){
					if(proteinto.getCelldesignerListOfModificationResidues()==null)
						proteinto.addNewCelldesignerListOfModificationResidues();
					
					// build list of residue names for target protein
					HashSet<String> residueName = new HashSet<String>();
					for(int j=0;j<proteinto.getCelldesignerListOfModificationResidues().sizeOfCelldesignerModificationResidueArray();j++) {
						CelldesignerModificationResidue res = proteinto.getCelldesignerListOfModificationResidues().getCelldesignerModificationResidueArray(j);
						String name = Utils.getValue(res.getName());
						if (name != null)
							residueName.add(name);
					}
					
					// merge residues from source (protein) to target (proteinto) protein
					for(int j=0;j<protein.getCelldesignerListOfModificationResidues().sizeOfCelldesignerModificationResidueArray();j++) {
						CelldesignerModificationResidue res = protein.getCelldesignerListOfModificationResidues().getCelldesignerModificationResidueArray(j);
						String name = Utils.getValue(res.getName());
						/*
						 * Copy residue to proteinto only if it is not there already
						 * ie there is not a residue having the same name
						 * 
						 * residues with no names (null) are copied anyway
						 */
						if (residueName.contains(name) == false)
							proteinto.getCelldesignerListOfModificationResidues().addNewCelldesignerModificationResidue().set(protein.getCelldesignerListOfModificationResidues().getCelldesignerModificationResidueArray(j));
					}
					
				}
				// remove duplicate in protein list
				if (verbose)
					System.out.println("Protein "+protein.getId()+" ("+Utils.getValue(protein.getName())+") removed.");				
				cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().removeCelldesignerProtein(i);
				numberOfProteins--;
			}
			else 
				i++;
		}

		i=0; 
		int numberOfGenes = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();
		while(i<numberOfGenes){
			CelldesignerGeneDocument.CelldesignerGene gene = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			if(geneIdList.indexOf(gene.getId())>=0){
				String gto = geneMap.get(gene.getId());
				CelldesignerGeneDocument.CelldesignerGene geneto = getGene(cd1,gto);
				if(geneto==null)
					System.out.println("Substitution not found for "+gene.getId()+" ("+gto+")");
				if(gene.getCelldesignerNotes()!=null){
					String comment = Utils.getValue(gene.getCelldesignerNotes()).trim();
					if(geneto.getCelldesignerNotes()==null)
						geneto.addNewCelldesignerNotes();
					String commentto = Utils.getValue(geneto.getCelldesignerNotes()).trim();
					xs.setStringValue("<&html><&body>"+commentto+"\n"+comment+"<&/body><&/html>");
					geneto.getCelldesignerNotes().set(xs);
				}
				if (verbose)
					System.out.println("Gene "+gene.getId()+" ("+gene.getName()+") removed.");				
				cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().removeCelldesignerGene(i);
				numberOfGenes--;
			}else i++;
		}

		i=0; 
		int numberOfRNAs = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();
		while(i<numberOfRNAs){
			CelldesignerRNADocument.CelldesignerRNA rna = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
			if(rnaIdList.indexOf(rna.getId())>=0){
				String pto = rnaMap.get(rna.getId());
				CelldesignerRNADocument.CelldesignerRNA rnato = getRNA(cd1,pto);
				if(rna.getCelldesignerNotes()!=null){
					String comment = Utils.getValue(rna.getCelldesignerNotes()).trim();
					if(rnato.getCelldesignerNotes()==null)
						rnato.addNewCelldesignerNotes();
					String commentto = Utils.getValue(rnato.getCelldesignerNotes()).trim();
					xs.setStringValue("<&html><&body>"+commentto+"\n"+comment+"<&/body><&/html>");
					rnato.getCelldesignerNotes().set(xs);
				}
				if (verbose)
					System.out.println("RNA "+rna.getId()+" ("+rna.getName()+") removed.");				
				cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().removeCelldesignerRNA(i);
				numberOfRNAs--;
			}
			else 
				i++;
		}
		
		i=0; 
		int numberOfAsRNAs = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().sizeOfCelldesignerAntisenseRNAArray();
		while(i<numberOfAsRNAs){
			CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA asrna = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
			if(asRnaIdList.indexOf(asrna.getId())>=0){
				String asRnaStr = asRnaMap.get(asrna.getId());
				CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA asrnato = getAsRNA(cd1,asRnaStr);
				if(asrna.getCelldesignerNotes()!=null){
					String comment = Utils.getValue(asrna.getCelldesignerNotes()).trim();
					if(asrnato.getCelldesignerNotes()==null)
						asrnato.addNewCelldesignerNotes();
					String commentto = Utils.getValue(asrnato.getCelldesignerNotes()).trim();
					xs.setStringValue("<&html><&body>"+commentto+"\n"+comment+"<&/body><&/html>");
					asrnato.getCelldesignerNotes().set(xs);
				}
				if (verbose)
					System.out.println("RNA "+asrna.getId()+" ("+asrna.getName()+") removed.");				
				cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().removeCelldesignerAntisenseRNA(i);
				numberOfAsRNAs--;
			}
			else 
				i++;
		}
	}

	private CelldesignerProteinDocument.CelldesignerProtein getProtein(SbmlDocument cd, String id){
		CelldesignerProteinDocument.CelldesignerProtein sp = null;
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein a = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			if(a.getId().equals(id))
				sp = a;
		}
		return sp;
	}


	private CelldesignerGeneDocument.CelldesignerGene getGene(SbmlDocument cd, String id){
		CelldesignerGeneDocument.CelldesignerGene sp = null;
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			CelldesignerGeneDocument.CelldesignerGene a = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			if(a.getId().equals(id))
				sp = a;
		}
		return sp;
	}

	private CelldesignerRNADocument.CelldesignerRNA getRNA(SbmlDocument cd, String id){
		CelldesignerRNADocument.CelldesignerRNA sp = null;
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
			CelldesignerRNADocument.CelldesignerRNA a = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
			if(a.getId().equals(id))
				sp = a;
		}
		return sp;
	}

	private CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA getAsRNA(SbmlDocument cd, String id){
		CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA sp = null;
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().sizeOfCelldesignerAntisenseRNAArray();i++){
			CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA a = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
			if(a.getId().equals(id))
				sp = a;
		}
		return sp;
	}

	/**
	 * Crude method to test if a string is an integer value
	 * 
	 * @param str
	 * @return boolean
	 */
	private static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
		}
		catch (NumberFormatException nfe) {
			 return false;
		}
		return true;
	}

	/**
	 * modify Id and Names after merging layers. 
	 * 
	 * Id should be ordered integers and names should be unique for correct display and selection in CellDesigner.
	 */
	private void formatLayers() {
		if (cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers() != null) {
			for (int i=0;i<cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().sizeOfCelldesignerLayerArray(); i++) {
				String id = Integer.toString(i+1);
				cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().getCelldesignerLayerArray(i).setId(id);
				String name  = Utils.getValue(cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().getCelldesignerLayerArray(i).getName());
				//name = name + "_" + Integer.toString(i+1);
				XmlString xs = XmlString.Factory.newInstance();
				xs.setStringValue(name);
				cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().getCelldesignerLayerArray(i).setName(xs);
			}
		}
	}
	
	/**
	 * Update cd file 2 with global Ids from cd file 1 (merged file)
	 * Rename file and save it.
	 * 
	 * @param fileName
	 */
	private void setIdsAndSave(String fileName) {
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		for (String id : this.proteinMap.keySet()) 
			if(proteinMap.get(id)!=null)
				map.put(id, proteinMap.get(id));
		//cdFileString = cdFileString.replaceAll(id, proteinMap.get(id));
		
		for (String id : this.rnaMap.keySet())
			if(rnaMap.get(id)!=null)
				map.put(id, rnaMap.get(id));
			//cdFileString = cdFileString.replaceAll(id, rnaMap.get(id));
		
		for (String id : this.geneMap.keySet())
			if(geneMap.get(id)!=null)
				map.put(id, geneMap.get(id));
			//cdFileString = cdFileString.replaceAll(id, geneMap.get(id));
		
		for (String id : this.asRnaMap.keySet())
			if(asRnaMap.get(id)!=null)
				map.put(id, asRnaMap.get(id));
			
			//cdFileString = cdFileString.replaceAll(id, asRnaMap.get(id));
		cdFileString = replaceCellDesignerPrefixList(cdFileString, map);
		
		if (fileName.endsWith(".xml")) {
			fileName = fileName.substring(0, fileName.length() - 4);
			fileName += "_newIds.xml";
		}
		else {
			fileName += "_newIds.xml";
		}
		
		//System.out.println("saving file "+fileName+"...");
		SbmlDocument doc = CellDesigner.loadCellDesignerFromText(cdFileString);
		//addLayerID(doc);
		CellDesigner.saveCellDesigner(doc, fileName);
	}
	
	private void generateRandomPrefix() {
		prefix="";
		String alphabet = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
		Random r = new Random();
		for (int i=0;i<4;i++)
			prefix += alphabet.charAt(r.nextInt(alphabet.length()));
		prefix += "_";
	}
	
	private void makePrefix(String text){
		generateRandomPrefix();
		StringTokenizer st = new StringTokenizer(text," =\"><");
		while(st.hasMoreTokens()){
			String s = st.nextToken();
			if(s.equals("id")){
				prefix = st.nextToken();
				if(prefix.length()>4)
					prefix = prefix.substring(0, 4);
				prefix+="_";
				break;
			}
		}
	}
	
	
	private void addLayerID(SbmlDocument cd) {
		String modelId = cd.getSbml().getModel().getId();
		
	    int size = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().sizeOfCelldesignerLayerArray();
	    System.out.println("layer size: "+size);
	    if (size>0)
	    	size++;
	    CelldesignerLayerDocument.CelldesignerLayer layer = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().addNewCelldesignerLayer();
	    layer.setId(Integer.toString(size));
		XmlString xs = XmlString.Factory.newInstance();
		xs.setStringValue(modelId);
		layer.setName(xs);
		xs.setStringValue("true");
		layer.setVisible(xs);
		xs.setStringValue("false");
		layer.setLocked(xs);
//		cd.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().addNewCelldesignerLayer().set(layer);
		//layer.addNewCelldesignerListOfSquares().addNewCelldesignerLayerCompartmentAlias().addNewCelldesignerBounds();
	}
	
	/**
	 * Update Ids to global Ids, do not merge the map. 
	 */
	private void updateMapAll() {
		for (String target : updateMap.keySet()) {
			String source = updateMap.get(target);
			updateMap(target, source);
		}
	}
	
	/**
	 * Update IDs for species, reaction and entities for a target map from a source map
	 * 
	 * @param target
	 * @param source
	 */
	private void updateMap(String target, String source) {
		
		System.out.println("updating target file "+target+" with source file "+source+"...");
		
		cd1 = CellDesigner.loadCellDesigner(target);
		cd2 = CellDesigner.loadCellDesigner(source);
		
		HashMap<String, String> spMap = new HashMap<String, String>();
		if(cd1.getSbml().getModel().getListOfSpecies()!=null)
		for (SpeciesDocument.Species targetSpecies : cd1.getSbml().getModel().getListOfSpecies().getSpeciesArray()) {
			String targetID = targetSpecies.getId();
			boolean found = false;
			if(cd2.getSbml().getModel().getListOfSpecies()!=null)
			for (SpeciesDocument.Species sourceSpecies : cd2.getSbml().getModel().getListOfSpecies().getSpeciesArray()) {
				String sourceID = sourceSpecies.getId();
				if (sourceID.endsWith(targetID)) {
					//System.out.println(sourceID+" ==> "+targetID);
					spMap.put(targetID, sourceID);
					found = true;
					break;
				}
			}
			if (found == false)
				System.out.println("warning: no match for species "+targetID+" ("+target+") in source file "+ source);
		}
		
		HashMap<String, String> reMap = new HashMap<String, String>();
		if(cd1.getSbml().getModel().getListOfReactions()!=null)
		for (ReactionDocument.Reaction targetReaction : cd1.getSbml().getModel().getListOfReactions().getReactionArray()) {
			String targetID = targetReaction.getId();
			boolean found = false;
			if(cd2.getSbml().getModel().getListOfReactions()!=null)
			for (ReactionDocument.Reaction sourceReaction : cd2.getSbml().getModel().getListOfReactions().getReactionArray()) {
				String sourceID = sourceReaction.getId();
				if (sourceID.endsWith(targetID)) {
					//System.out.println(sourceID+" ==> "+targetID);
					reMap.put(targetID, sourceID);
					found = true;
					break;
				}
			}
			if (found == false)
				System.out.println("warning: no match for reaction "+targetID+" ("+target+") in source file "+ source);
		}
		
		produceCandidateMergeLists();
		
		String targetXml = cd1.toString();
		
		// update species IDs
		for (String targetID : spMap.keySet()) {
			targetXml = Utils.replaceString(targetXml, "\""+targetID+"\"", "\""+spMap.get(targetID)+"\"");
			targetXml = Utils.replaceString(targetXml, ">"+targetID+"<", ">"+spMap.get(targetID)+"<");
		}	
		
		// update reaction IDs
		for (String targetID : reMap.keySet()) {
			targetXml = Utils.replaceString(targetXml, "\""+targetID+"\"", "\""+reMap.get(targetID)+"\"");
			targetXml = Utils.replaceString(targetXml, ">"+targetID+"<", ">"+reMap.get(targetID)+"<");
		}
		
		// update protein IDs
		for (String id : this.proteinMap.keySet()) {
			targetXml = Utils.replaceString(targetXml, "\""+proteinMap.get(id)+"\"", "\""+id+"\"");
			targetXml = Utils.replaceString(targetXml, ">"+proteinMap.get(id)+"<", ">"+id+"<");
		}
		
		// update rna IDs
		for (String id : this.rnaMap.keySet()) {
			targetXml = Utils.replaceString(targetXml, "\""+rnaMap.get(id)+"\"", "\""+id+"\"");
			targetXml = Utils.replaceString(targetXml, ">"+rnaMap.get(id)+"<", ">"+id+"<");
		}
		
		// update asRNA IDs
		for (String id : this.asRnaMap.keySet()) {
			targetXml = Utils.replaceString(targetXml, "\""+asRnaMap.get(id)+"\"", "\""+id+"\"");
			targetXml = Utils.replaceString(targetXml, ">"+asRnaMap.get(id)+"<", ">"+id+"<");
		}
		
		// update gene IDs
		for (String id : this.geneMap.keySet()) {
			targetXml = Utils.replaceString(targetXml, "\""+geneMap.get(id)+"\"", "\""+id+"\"");
			targetXml = Utils.replaceString(targetXml, ">"+geneMap.get(id)+"<", ">"+id+"<");
		}
		
		String outputFile = target;
		if (outputFile.endsWith(".xml")) {
			outputFile = outputFile.substring(0, target.length() - 4);
			outputFile += ".update.xml";
		}
		else {
			outputFile += ".update.xml";
		}
		
		System.out.println("saving updated map "+outputFile+"...");
		SbmlDocument doc = CellDesigner.loadCellDesignerFromText(targetXml);
		CellDesigner.saveCellDesigner(doc, outputFile);
	}
	
	
	/**
	 * Load the configuration file.
	 * 
	 * @param fileName
	 */
	public void loadConfigFile(String fileName) throws Exception {
		BufferedReader buf = new BufferedReader(new FileReader(fileName));	
		String line;
		int ct=0;
		while((line = buf.readLine()) != null) {
			line.trim();
			if (line.length()>0) {
				String[] tk = line.split("\\t|\\s+");
				if (ct==0) {
					int sizeX = Integer.parseInt(tk[1]);
					int sizeY = Integer.parseInt(tk[2]);
					setMapSize(sizeX, sizeY);
				}
				else {
					// maps files to be updated with global Ids
					if (tk[1].equalsIgnoreCase("update")) {
						this.updateMap.put(tk[0], tk[2]);
					}
					// maps to be merged
					else {
						String fn = tk[0];
						int coordX = Integer.parseInt(tk[1]);
						int coordY = Integer.parseInt(tk[2]);
						addMap(fn, coordX, coordY);
					}
				}
			}
			ct++;
		}
	}
	
	public void mergeMapImages(String outputFileName_prefix, int zoomLevel, int numberOfTimesToScale){
		
		System.out.println("==============================");
		System.out.println("=======  Merge images   ======");		
		System.out.println("==============================");		

		try{

			int gWidth = Integer.parseInt(sizeX);
			int gHeight = Integer.parseInt(sizeY);
			System.out.println("Allocating memory for global image...");
			BufferedImage mergedImage = new BufferedImage(gWidth, gHeight, BufferedImage.TYPE_INT_RGB);
			Utils.printUsedMemory();
			Graphics2D g = mergedImage.createGraphics();
			g.setBackground(new Color(1f,1f,1f));
			g.clearRect(0,0,mergedImage.getWidth(),mergedImage.getHeight());


			for(int j=0;j<mapList.size();j++){
				String fn = mapList.get(j).fileName;
				Date time = new Date();
				System.out.println("--------- Merging "+fn);
				fn = fn.substring(0,fn.length()-4);
				fn = fn+"-"+zoomLevel+".png";
				File f = new File(fn);
				if(f.exists()){
					System.out.println("Reading "+fn+"...");
					BufferedImage map = ImageIO.read(new File(fn));
					Image imap = Utils.Transparency.makeColorTransparent(map, new Color(1f, 1f, 1f));
					int x = mapList.get(j).deltaX;
					int y = mapList.get(j).deltaY;
					int width = map.getWidth();
					int height = map.getHeight();
					/*if(x+width>gWidth)
								  width = gWidth-x-1;
							  if(y+height>gHeight)
								  height = gHeight-y-1;*/
					//boolean b = g.drawImage(map, x, y, x+10, y+10, null);
					System.out.println("Drawing "+fn+"...");					
					boolean b = g.drawImage(imap, x, y, null);
					Utils.printUsedMemory();
					//System.out.println(fn+"\tSuccess="+b);

					//mergedImage = ImageIO.read(new File(outputFileName_prefix+"-"+zoomLevel+".png"));


				}else{
					System.out.println("ERROR: "+fn+" image is not found!!!");
				}


			}

			g.dispose();
			System.out.println("Saving "+outputFileName_prefix+"-"+zoomLevel+".png"+"...");
			ImageIO.write(mergedImage, "PNG", new File(outputFileName_prefix+"-"+zoomLevel+".png"));

			for(int i=0;i<numberOfTimesToScale;i++){
				gWidth/=2;
				gHeight/=2;
				System.out.println("Scaling to "+gWidth+","+gHeight);
				BufferedImage im = Utils.getScaledImageSlow(mergedImage, gWidth, gHeight);
				Utils.printUsedMemory();
				System.out.println("Saving "+outputFileName_prefix+"-"+(zoomLevel-i-1)+".png"+"...");				
				ImageIO.write(im, "PNG", new File(outputFileName_prefix+"-"+(zoomLevel-i-1)+".png"));
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
		Utils.printUsedMemory();

	}

	public ModifyCellDesignerNotes postProcessAnnotations(String outputFileName){
		
		ModifyCellDesignerNotes mn = new ModifyCellDesignerNotes();
		mn.generateReadableNamesForReactionsAndSpecies = false;
		mn.allannotations = true;
		mn.formatAnnotation = true;
		mn.moduleGMTFileName = null;
		mn.guessIdentifiers = false;
		mn.insertMapsTagBeforeModules = false;
		mn.removeEmptySections = true;
		mn.removeInvalidTags = true;
		mn.moveNonannotatedTextToReferenceSection = false;

		mn.sbmlDoc = CellDesigner.loadCellDesigner(outputFileName);
		mn.automaticallyProcessNotes();
		System.out.println("Saving...");		
		CellDesigner.saveCellDesigner(mn.sbmlDoc, outputFileName);
		return mn;
	}
	
	
	public void preProcessMergedMaps(){
		
		System.out.println("==============================");
		System.out.println("=======  Preprocessing  ======");		
		System.out.println("==============================");		
		
		Date time = new Date();
		
		for(int i=0;i<mapList.size();i++){
			String fileName = mapList.get(i).fileName;
			System.out.println("---------- Map "+fileName);
			// 1. Add MAP: tag before MODULE: tag
			ModifyCellDesignerNotes mn = new ModifyCellDesignerNotes();
			mn.generateReadableNamesForReactionsAndSpecies = false;
			mn.allannotations = true;
			mn.formatAnnotation = true;
			mn.moduleGMTFileName = null;
			mn.guessIdentifiers = false;
			mn.insertMapsTagBeforeModules = true;
			mn.removeEmptySections = true;
			mn.removeInvalidTags = true;
			mn.moveNonannotatedTextToReferenceSection = false;
			mn.sbmlDoc = CellDesigner.loadCellDesigner(fileName);
			mn.automaticallyProcessNotes();
			System.out.println("Saving...");
			CellDesigner.saveCellDesigner(mn.sbmlDoc, fileName);
		}
		System.out.println("Preprocessing took "+(int)(((new Date()).getTime()-time.getTime())*0.001f)+" sec");
		Utils.printUsedMemory();		

	}
	
	public void postProcessMergedMap(String outputFileName) throws Exception{
		
		System.out.println("==============================");
		System.out.println("=======  Postprocessing  =====");		
		System.out.println("==============================");		

		Date time = new Date();		
		
		System.out.println("Post-processing annotations for "+outputFileName);
		ModifyCellDesignerNotes mcn = postProcessAnnotations(outputFileName);
		// 1. Synchronize ids for entities in all maps

		// 2. Make modules section identical in all maps
		ModifyCellDesignerNotes global = new ModifyCellDesignerNotes();
		global.sbmlDoc = CellDesigner.loadCellDesigner(outputFileName);
		System.out.println("Synchronizing annotations for "+outputFileName);		
		for(int i=0;i<mapList.size();i++){
			String fileName = mapList.get(i).fileName;
			System.out.println("-----------Synchronizing "+fileName);			
			ModifyCellDesignerNotes mn = new ModifyCellDesignerNotes();
			mn.generateReadableNamesForReactionsAndSpecies = false;
			mn.allannotations = true;
			mn.formatAnnotation = true;
			mn.moduleGMTFileName = null;
			mn.guessIdentifiers = false;
			mn.insertMapsTagBeforeModules = true;
			mn.removeEmptySections = true;
			mn.removeInvalidTags = true;
			mn.moveNonannotatedTextToReferenceSection = false;
			mn.sbmlDoc = CellDesigner.loadCellDesigner(fileName);
			mn.synchronizeAnnotations(mcn);
			System.out.println("Saving...");			
			CellDesigner.saveCellDesigner(mn.sbmlDoc, fileName);			
		}
		
		Utils.printUsedMemory();
		System.out.println("Postprocessing took "+(int)(((new Date()).getTime()-time.getTime())*0.001f)+" sec");		
		
	}

	
}
