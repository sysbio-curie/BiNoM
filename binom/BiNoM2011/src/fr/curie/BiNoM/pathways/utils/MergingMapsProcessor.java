package fr.curie.BiNoM.pathways.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument;
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
	 * Simple counter to create specific prefix for cd2 files.
	 */
	private int counter = 0;
	
	/**
	 * map protein ID2 => protein ID1
	 */
	private HashMap<String, String> proteinMap;
	/**
	 * List of protein ID2
	 */
	private ArrayList<String> proteinIdList;
	/**
	 * map geneID2 => geneID1
	 */
	private HashMap<String, String> geneMap;
	/**
	 * map rnaID2 => rnaID1
	 */
	private HashMap<String, String> rnaMap;
	/**
	 * map asRNA_ID2 => asRna_ID1
	 */
	private HashMap<String, String> asRnaMap;
	
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
	 * Print extended informations while merging elements
	 */
	public boolean verbose = false;
	
	/**
	 * Constructor
	 */
	public MergingMapsProcessor() {
		// there is no life in the void.
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
	public void mergeAll() {
		int nbFiles = mapList.size();
		
		// load file 1
		System.out.println("loading file "+mapList.get(0).fileName+"...");
		this.cd1 = CellDesigner.loadCellDesigner(mapList.get(0).fileName);
		
		this.cd1.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeX(sizeX);
		this.cd1.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeY(sizeY);
		shiftCoordinates(cd1,mapList.get(0).deltaX, mapList.get(0).deltaY);
		
		setAndLoadFileName2(mapList.get(1).fileName);
		//System.out.println("cd1 getId: "+this.cd1.getSbml().getModel().getName());
		shiftCoordinates(cd2, mapList.get(1).deltaX, mapList.get(1).deltaY);
		produceCandidateMergeLists();
		mergeDiagrams();
		mergeElements();
		
		for (int i=2;i<nbFiles;i++) {
			// set new file 2 name
			setAndLoadFileName2(mapList.get(i).fileName);
			shiftCoordinates(cd2, mapList.get(i).deltaX, mapList.get(i).deltaY);
			// create new common species names
			produceCandidateMergeLists();
			// merge maps
			mergeDiagrams();
			mergeElements();
		}
		
		formatLayers();
		
	}
	
	/**
	 * save merged map
	 * @param fileName
	 */
	public void saveMap(String fileName) {
		System.out.println("saving file " +fileName);
		CellDesigner.saveCellDesigner(cd1, fileName);
		System.out.println("Done.");
	}

	//  ------ full process to merge two maps (original AZ procedure) ------------------	
	//	String file1Text = Utils.loadString(fileName1);
	//	file1Text = addPrefixToIds(file1Text,"rb_");
	//	cd1 = CellDesigner.loadCellDesignerFromText(file1Text);
	//	countAll(cd1);
	//	cd2 = CellDesigner.loadCellDesigner(fileName2);
	//	produceCandidateMergeLists(cd1, cd2, proteinMap, speciesMap);
	//	mergeDiagrams(cd1,cd2);
	//	rewireDiagram(cd1, speciesMap,proteinMap);
	//	CellDesigner.saveCellDesigner(cd1, "/bioinfo/users/ebonnet/rew.xml");
	//	--------------------------------------------------------------------------------

	/**
	 * Load CD file 2. Add a prefix to all element IDs.
	 * 
	 * @param fileName
	 */
	private void setAndLoadFileName2 (String fileName) {
		/*
		 * new way: add prefix to second file
		 * use an internal counter to generate a new prefix each time a new file is loaded
		 */
		System.out.println("loading file "+fileName+"...");
		String text = Utils.loadString(fileName);
		this.counter++;
		String prefix = "cd" + counter + "_";
		text = addPrefixToIds(text, prefix);
		this.cd2 = CellDesigner.loadCellDesignerFromText(text);
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
								//System.out.println(">>>"+ cmd.getEditPoints());
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
								//System.out.println(cmd.getEditPoints());
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
	 * @param prefix a string representing the prefix to add
	 * @return string the whole modified map as a string
	 */
	private String addPrefixToIds(String text, String prefix){
		Vector<String> ids = Utils.extractAllStringBetween(text, "id=\"", "\"");
		for(int i=0;i<ids.size();i++) {
			if(ids.get(i).equals("default") == false && isNumeric(ids.get(i)) == false) {
				//System.out.println(">>> id= "+ids.get(i));
				//System.out.println("replace: "+"\""+ids.get(i)+"\""+ " with "+ "\""+prefix+""+ids.get(i)+"\"");
				//System.out.println("replace: " + ">"+ids.get(i)+"<" + " with "+ ">"+prefix+""+ids.get(i)+"<");
				text = Utils.replaceString(text, "\""+ids.get(i)+"\"", "\""+prefix+""+ids.get(i)+"\"");
				text = Utils.replaceString(text, ">"+ids.get(i)+"<", ">"+prefix+""+ids.get(i)+"<");
			}
		}
		
		/*
		 * id replacement in tag "units" is giving a bug in CellDesigner
		 * so here we find all occurences of "units" and set them back 
		 * to an accepted value. 
		 */
		Pattern p = Pattern.compile("units=\"(\\S+)\"");
		Matcher m = p.matcher(text);
		HashSet<String> w = new HashSet<String>();
		while(m.find()) {
			if (!m.group(1).equalsIgnoreCase("volume"))
				w.add(m.group(1));
		}
		for (String s : w) {
			p = Pattern.compile("units=\""+s+"\"");
			m = p.matcher(text);
			while(m.find())
				text = m.replaceAll("units=\"volume\"");
		}
		return text;
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
			//System.out.println(Utils.getValue(p.getName())+"\t"+p.getId());
		}
		this.proteinMap = new HashMap<String, String>();
		this.proteinIdList = new ArrayList<String>();
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein p = cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			String name = Utils.getValue(p.getName());
			if(proteinNames.containsKey(name)){
				//proteinMap.add(proteinNames.get(name)+"\t"+name+"\t"+p.getId()+"\t"+name);
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
		if(cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null) {
			for(int i=0;i<cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
				
				CelldesignerSpeciesDocument.CelldesignerSpecies csp = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
				
				// proteins
				if(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null){
					String pr = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
					if(proteinMap.get(pr)!=null){
						xs.setStringValue(proteinMap.get(pr));
						CellDesigner.entities = CellDesigner.getEntities(cd1);
						String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
						csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference().set(xs);
						xs.setStringValue(Utils.getValue(getProtein(cd1,proteinMap.get(pr)).getName())); 
						csp.setName(xs);
						if (verbose)
							System.out.println("Changed protein reference in "+csp.getId()+" from "+pr+" ("+Utils.getValue(getProtein(cd1,pr).getName())+") to "+proteinMap.get(pr)+" ("+Utils.getValue(getProtein(cd1,proteinMap.get(pr)).getName())+")");
						CellDesigner.entities = CellDesigner.getEntities(cd1);
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
						CellDesigner.entities = CellDesigner.getEntities(cd1);
						String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
						csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference().set(xs);
						xs.setStringValue(getGene(cd1,geneMap.get(ge)).getName()); 
						csp.setName(xs);
						if (verbose)
							System.out.print("Changed gene reference in "+csp.getId()+" from "+ge+" ("+getGene(cd1,ge).getName()+") to "+geneMap.get(ge)+" ("+getGene(cd1,geneMap.get(ge)).getName()+")");
						CellDesigner.entities = CellDesigner.getEntities(cd1);
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
						CellDesigner.entities = CellDesigner.getEntities(cd1);
						String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
						csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference().set(xs);
						xs.setStringValue(getRNA(cd1,rnaMap.get(rna)).getName()); 
						csp.setName(xs);
						if (verbose)
							System.out.println("Changed rna reference in "+csp.getId()+" from "+rna+" ("+getRNA(cd1,rna).getName()+") to "+rnaMap.get(rna)+" ("+getRNA(cd1,rnaMap.get(rna)).getName()+")");
						CellDesigner.entities = CellDesigner.getEntities(cd1);
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
					CellDesigner.entities = CellDesigner.getEntities(cd1);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true);
					csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference().set(xs);
					if (verbose)
						System.out.println("Changed protein reference in "+csp.getId()+" from "+pr+" ("+Utils.getValue(getProtein(cd1,pr).getName())+") to "+proteinMap.get(pr)+" ("+Utils.getValue(getProtein(cd1,proteinMap.get(pr)).getName())+")");
					CellDesigner.entities = CellDesigner.getEntities(cd1);
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
					CellDesigner.entities = CellDesigner.getEntities(cd1);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true);
					csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference().set(xs);
					if (verbose)
						System.out.println("Changed gene reference in "+csp.getId()+" from "+str+" ("+getGene(cd1,str).getName()+") to "+geneMap.get(str)+" ("+getGene(cd1,geneMap.get(str)).getName()+")");
					CellDesigner.entities = CellDesigner.getEntities(cd1);
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
					CellDesigner.entities = CellDesigner.getEntities(cd1);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true);
					csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference().set(xs);
					if (verbose)
						System.out.println("Changed RNA reference in "+csp.getId()+" from "+str+" ("+getRNA(cd1,str).getName()+") to "+rnaMap.get(str)+" ("+getRNA(cd1,rnaMap.get(str)).getName()+")");
					CellDesigner.entities = CellDesigner.getEntities(cd1);
					if (verbose) {
						System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true));
					}
				}
			}
			if(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference()!=null){
				String str = Utils.getValue(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference());
				if(asRnaMap.get(str)!=null){
					xs.setStringValue(asRnaMap.get(str));
					CellDesigner.entities = CellDesigner.getEntities(cd1);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true);
					csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference().set(xs);
					if (verbose)
						System.out.println("Changed Antisense RNA reference in "+csp.getId()+" from "+str+" ("+getAsRNA(cd1,str).getName()+") to "+asRnaMap.get(str)+" ("+getAsRNA(cd1,asRnaMap.get(str)).getName()+")");
					CellDesigner.entities = CellDesigner.getEntities(cd1);
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
				// merge comments into prot 1
				if(protein.getCelldesignerNotes()!=null){
					String comment = Utils.getValue(protein.getCelldesignerNotes()).trim();
					if(proteinto.getCelldesignerNotes()==null)
						proteinto.addNewCelldesignerNotes();
					String commentto = Utils.getValue(proteinto.getCelldesignerNotes()).trim();
					xs.setStringValue("<&html><&body>"+commentto+"\n"+comment+"<&/body><&/html>");
					proteinto.getCelldesignerNotes().set(xs);
				}
				// add modifications to prot 1
				if(protein.getCelldesignerListOfModificationResidues()!=null){
					if(proteinto.getCelldesignerListOfModificationResidues()==null)
						proteinto.addNewCelldesignerListOfModificationResidues();
					for(int j=0;j<protein.getCelldesignerListOfModificationResidues().sizeOfCelldesignerModificationResidueArray();j++)
						proteinto.getCelldesignerListOfModificationResidues().addNewCelldesignerModificationResidue().set(protein.getCelldesignerListOfModificationResidues().getCelldesignerModificationResidueArray(j));
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
				name = name + "_" + Integer.toString(i+1);
				XmlString xs = XmlString.Factory.newInstance();
				xs.setStringValue(name);
				cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().getCelldesignerLayerArray(i).setName(xs);
			}
		}
	}
	
	public void mergeMapImages(String outputFileName_prefix, int zoomLevel, int numberOfTimesToScale){

		try{

			int gWidth = Integer.parseInt(sizeX);
			int gHeight = Integer.parseInt(sizeY);
			BufferedImage mergedImage = new BufferedImage(gWidth, gHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = mergedImage.createGraphics();
			g.setBackground(new Color(1f,1f,1f));
			g.clearRect(0,0,mergedImage.getWidth(),mergedImage.getHeight());


			for(int j=0;j<mapList.size();j++){
				String fn = mapList.get(j).fileName;
				fn = fn.substring(0,fn.length()-4);
				fn = fn+"-"+zoomLevel+".png";
				File f = new File(fn);
				if(f.exists()){
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
					boolean b = g.drawImage(imap, x, y, null);
					//System.out.println(fn+"\tSuccess="+b);

					//mergedImage = ImageIO.read(new File(outputFileName_prefix+"-"+zoomLevel+".png"));


				}else{
					System.out.println("ERROR: "+fn+" image is not found!!!");
				}


			}

			g.dispose();
			ImageIO.write(mergedImage, "PNG", new File(outputFileName_prefix+"-"+zoomLevel+".png"));

			for(int i=0;i<numberOfTimesToScale;i++){
				gWidth/=2;
				gHeight/=2;
				BufferedImage im = Utils.getScaledImageSlow(mergedImage, gWidth, gHeight);
				ImageIO.write(im, "PNG", new File(outputFileName_prefix+"-"+(zoomLevel-i-1)+".png"));
			}

		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
}
