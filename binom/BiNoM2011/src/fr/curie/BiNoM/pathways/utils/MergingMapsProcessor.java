package fr.curie.BiNoM.pathways.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.xmlbeans.XmlString;
import org.sbml.x2001.ns.celldesigner.CelldesignerAntisenseRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerBaseProductDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerBaseReactantDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerCompartmentAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerEditPointsDocument.CelldesignerEditPoints;
import org.sbml.x2001.ns.celldesigner.CelldesignerGateMemberDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerGeneDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerModificationDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerRNADocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument;
import org.sbml.x2001.ns.celldesigner.ListOfModifiersDocument;
import org.sbml.x2001.ns.celldesigner.ModifierSpeciesReferenceDocument;
import org.sbml.x2001.ns.celldesigner.ReactionDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;

import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;


/**
 * Cell designer merging maps procedures.
 */
public class MergingMapsProcessor {

	//private Vector<String> proteinMap;
	private Vector<String> speciesMapStr = new Vector<String>();
	private SbmlDocument cd1;
	private SbmlDocument cd2;
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
	 * Constructor
	 */
	public MergingMapsProcessor() {
		// there is no life in the void.
	}

	//  ------ full process to merge two maps ---------------------------------------------	
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


	public void setAndLoadFileName1(String fileName) {

		/*
		 * old way: add prefix to first file
		 */
		//		String file1Text = Utils.loadString(fileName);
		//		file1Text = addPrefixToIds(file1Text,"rb_");
		//		cd1 = CellDesigner.loadCellDesignerFromText(file1Text);
		//		countAll(cd1);

		this.cd1 = CellDesigner.loadCellDesigner(fileName);
	}

	public void setAndLoadFileName2 (String fileName) {
		//this.cd2 = CellDesigner.loadCellDesigner(fileName);

		/*
		 * new way: add prefix to second file
		 * use an internal counter to generate a new prefix each time a new file is loaded
		 */
		String text = Utils.loadString(fileName);
		this.counter++;
		String prefix = "rb" + counter + "_";
		text = addPrefixToIds(text, prefix);
		this.cd2 = CellDesigner.loadCellDesignerFromText(text);
	}

	public void setMergeLists() {
		//proteinMap = new Vector<String>();
		speciesMapStr = new Vector<String>();
		produceCandidateMergeLists();
	}

	public void mergeTwoMaps() {
		mergeDiagrams();
		mergeElements();
	}

	public void saveCd1File(String fileName) {
		CellDesigner.saveCellDesigner(cd1, fileName);
	}

	public Vector<String> getSpeciesMap() {
		return this.speciesMapStr;
	}

	public void setSpeciesMap(Vector<String> data) {
		this.speciesMapStr = data;
	}

	public void printSpeciesMap() {
		System.out.println("#----- species map---------");
		for (String s : this.speciesMapStr)
			System.out.println(s);
	}

//	public void testShiftCoord() {
//		this.cd1.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeX("2000");
//		this.shiftCoordinates(cd2, 600, 0);
//	}

	public void setCd1MapSizeX(String val) {
		this.cd1.getSbml().getModel().getAnnotation().getCelldesignerModelDisplay().setSizeX(val);
	}
	
	/**
	 * shift coordinates for CellDesigner file cd2.
	 * 
	 * @param deltaX float delta x value
	 * @param deltaY float delta y value
	 */
	public void shiftCoordinatesCd2( float deltaX, float deltaY) {

		// species aliases
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias cdal = cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			float  x = Float.parseFloat(cdal.getCelldesignerBounds().getX());
			float  y = Float.parseFloat(cdal.getCelldesignerBounds().getY());
			x += deltaX;
			y += deltaY;
			cdal.getCelldesignerBounds().setX(Float.toString(x));
			cdal.getCelldesignerBounds().setY(Float.toString(y));
		}

		// compartments
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray().length;i++){
			CelldesignerCompartmentAliasDocument.CelldesignerCompartmentAlias csa = cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray(i);
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
		if(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null) {
			for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
				CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias cdal = cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
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
		for(int i=0;i<cd2.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			ReactionDocument.Reaction r = cd2.getSbml().getModel().getListOfReactions().getReactionArray(i);
			
			// cd version 4.1
			if(r.getAnnotation().getCelldesignerListOfModification()!=null) {
				for(int j=0;j<r.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();j++){
					CelldesignerModificationDocument.CelldesignerModification cmd = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
					String type = cmd.getType();
					if (type.contains("BOOLEAN_LOGIC")) {
						String str = Utils.getValue(cmd.getEditPoints());
						String[] coord = str.split(",");
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
			
			// cd files version 4.2
			if(r.getAnnotation().getCelldesignerListOfGateMember()!=null) {
				for(int j=0;j<r.getAnnotation().getCelldesignerListOfGateMember().sizeOfCelldesignerGateMemberArray();j++){
					CelldesignerGateMemberDocument.CelldesignerGateMember cmd = r.getAnnotation().getCelldesignerListOfGateMember().getCelldesignerGateMemberArray(j);
					String type = cmd.getType();
					if (type.contains("BOOLEAN_LOGIC")) {
						String str = Utils.getValue(cmd.getEditPoints());
						String[] coord = str.split(",");
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

	/**
	 * Just add a given prefix to all IDs on a given CellDesigner map.
	 * 
	 * @param text the whole map as a string
	 * @param prefix a string representing the prefix to add
	 * @return string the whole modified map as a string
	 */
	private static String addPrefixToIds(String text, String prefix){
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
	 * Merge "mechanically" CellDesigner components of file cd2 into file cd1.
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

		/*
		 *  before: find proteins sharing the same name, save them to "P" file
		 *  
		 *  now: for proteins having same name add string "id1-name1-id2-name2" to vector proteinMap
		 *  
		 */
		//FileWriter fw = new FileWriter(prefix+"P.txt");
//		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
//			CelldesignerProteinDocument.CelldesignerProtein p = cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
//			String name = Utils.getValue(p.getName());
//			if(proteinNames.containsKey(name)){
//				//fw.write(proteinNames.get(name)+"\t"+name+"\t"+p.getId()+"\t"+name+"\n");
//				proteinMap.add(proteinNames.get(name)+"\t"+name+"\t"+p.getId()+"\t"+name);
//			}
//		}
		//fw.close();

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

//		HashMap<String,String> aliasMap = new HashMap<String,String>();
//		HashMap<String,String> speciesMap = new HashMap<String,String>();
//		Vector<String> subsAliases = new Vector<String>();
//		Vector<String> subsSpecies = new Vector<String>();
//
//		for(int i=0;i<speciesMapStr.size();i++){
//			String s = speciesMapStr.get(i); 
//			StringTokenizer st = new StringTokenizer(s,"\t");
//			// "alias_ID1 species_ID1 compartment1 species_name1 alias_ID2 species_ID2 compartment2 species_name2"
//			String ato = st.nextToken(); //alias_ID1 
//			String sto = st.nextToken(); // species_ID1
//			st.nextToken();
//			st.nextToken();
//			String afrom = st.nextToken(); // alias_ID2
//			String sfrom = st.nextToken(); // species_ID2
//			st.nextToken(); 
//			st.nextToken();
//			aliasMap.put(afrom,ato); // alias_ID2 => alias_ID1
//			speciesMap.put(sfrom, sto); // species_ID2 => species_ID1
//			subsAliases.add(afrom); // alias_ID2
//			subsSpecies.add(sfrom); // species_ID2
//		}

		CellDesignerToCytoscapeConverter.createSpeciesMap(cd1.getSbml());
		
//		int i;
//		HashMap<String,String> idMap = new HashMap<String,String>();
//		Vector<String> subsIds = new Vector<String>();
//		for(i=0;i<proteinMapStr.size();i++){
//			String s = proteinMapStr.get(i); 
//			// "protein_id1 protein_name1 protein_id2 protein_name2"
//			StringTokenizer st = new StringTokenizer(s,"\t");
//			String ato = st.nextToken(); // protein_id1 
//			st.nextToken(); 
//			String afrom = st.nextToken(); // protein_id2
//			idMap.put(afrom,ato); // protein_id2 => protein_id1
//			subsIds.add(afrom); // list of protein_id2
//		}

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
						System.out.println("IS Changed protein reference in "+csp.getId()+" from "+pr+" ("+Utils.getValue(getProtein(cd1,pr).getName())+") to "+proteinMap.get(pr)+" ("+Utils.getValue(getProtein(cd1,proteinMap.get(pr)).getName())+")");
						CellDesigner.entities = CellDesigner.getEntities(cd1);
						System.out.println("IS Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true));
						System.out.println();
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
						System.out.println("Changed gene reference in "+csp.getId()+" from "+ge+" ("+getGene(cd1,ge).getName()+") to "+geneMap.get(ge)+" ("+getGene(cd1,proteinMap.get(ge)).getName()+")");
						CellDesigner.entities = CellDesigner.getEntities(cd1);
						System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true));
						System.out.println();
					}
				}
				
				// RNAs
				if(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()!=null){
					String rna = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference());
					if(rnaMap.get(rna)!=null){
						xs.setStringValue(proteinMap.get(rna));
						CellDesigner.entities = CellDesigner.getEntities(cd1);
						String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
						csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference().set(xs);
						xs.setStringValue(getRNA(cd1,rnaMap.get(rna)).getName()); 
						csp.setName(xs);
						System.out.println("Changed rna reference in "+csp.getId()+" from "+rna+" ("+getRNA(cd1,rna).getName()+") to "+rnaMap.get(rna)+" ("+getRNA(cd1,rnaMap.get(rna)).getName()+")");
						CellDesigner.entities = CellDesigner.getEntities(cd1);
						System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true));
						System.out.println();
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
					System.out.println("Changed protein reference in "+csp.getId()+" from "+pr+" ("+Utils.getValue(getProtein(cd1,pr).getName())+") to "+proteinMap.get(pr)+" ("+Utils.getValue(getProtein(cd1,proteinMap.get(pr)).getName())+")");
					CellDesigner.entities = CellDesigner.getEntities(cd1);
					System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true));
					System.out.println();
				}
			}
			if(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference()!=null){
				String str = Utils.getValue(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference());
				//System.out.println(">>> pr= "+str+" species "+csp.getName());
				if(geneMap.get(str)!=null){
					xs.setStringValue(geneMap.get(str));
					CellDesigner.entities = CellDesigner.getEntities(cd1);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true);
					csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference().set(xs);
					System.out.println("Changed gene reference in "+csp.getId()+" from "+str+" ("+getGene(cd1,str).getName()+") to "+geneMap.get(str)+" ("+getGene(cd1,geneMap.get(str)).getName()+")");
					CellDesigner.entities = CellDesigner.getEntities(cd1);
					System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true));
					System.out.println();
				}
			}
			if(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()!=null){
				String str = Utils.getValue(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference());
				if(rnaMap.get(str)!=null){
					xs.setStringValue(rnaMap.get(str));
					CellDesigner.entities = CellDesigner.getEntities(cd1);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true);
					csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference().set(xs);
					System.out.println("Changed RNA reference in "+csp.getId()+" from "+str+" ("+getRNA(cd1,str).getName()+") to "+rnaMap.get(str)+" ("+getRNA(cd1,rnaMap.get(str)).getName()+")");
					CellDesigner.entities = CellDesigner.getEntities(cd1);
					System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true));
				}
			}
			if(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference()!=null){
				String str = Utils.getValue(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference());
				if(asRnaMap.get(str)!=null){
					xs.setStringValue(asRnaMap.get(str));
					CellDesigner.entities = CellDesigner.getEntities(cd1);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd1,csp.getId(), true, true);
					csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerAntisensernaReference().set(xs);
					System.out.println("Changed Antisense RNA reference in "+csp.getId()+" from "+str+" ("+getAsRNA(cd1,str).getName()+") to "+asRnaMap.get(str)+" ("+getAsRNA(cd1,asRnaMap.get(str)).getName()+")");
					CellDesigner.entities = CellDesigner.getEntities(cd1);
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
			CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA rna = cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
			if(asRnaIdList.indexOf(rna.getId())>=0){
				String asRna = asRnaMap.get(rna.getId());
				CelldesignerRNADocument.CelldesignerRNA rnato = getRNA(cd1,asRna);
				if(rna.getCelldesignerNotes()!=null){
					String comment = Utils.getValue(rna.getCelldesignerNotes()).trim();
					if(rnato.getCelldesignerNotes()==null)
						rnato.addNewCelldesignerNotes();
					String commentto = Utils.getValue(rnato.getCelldesignerNotes()).trim();
					xs.setStringValue("<&html><&body>"+commentto+"\n"+comment+"<&/body><&/html>");
					rnato.getCelldesignerNotes().set(xs);
				}
				System.out.println("RNA "+rna.getId()+" ("+rna.getName()+") removed.");				
				cd1.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().removeCelldesignerAntisenseRNA(i);
				numberOfAsRNAs--;
			}
			else 
				i++;
		}
	} 

//	private void rewireDiagram(SbmlDocument cd, Vector<String> subs, Vector<String> subsP){
//		CellDesigner.entities = CellDesigner.getEntities(cd);
//		XmlString xs = XmlString.Factory.newInstance();
//		HashMap<String,String> aliasMap = new HashMap<String,String>();
//		HashMap<String,String> speciesMap = new HashMap<String,String>();
//		Vector<String> subsAliases = new Vector<String>();
//		Vector<String> subsSpecies = new Vector<String>();
//		for(int i=0;i<subs.size();i++){
//			String s = subs.get(i); 
//			StringTokenizer st = new StringTokenizer(s,"\t");
//			//StringTokenizer st = new StringTokenizer(s," ");
//			String ato = st.nextToken(); String sto = st.nextToken(); st.nextToken(); st.nextToken();
//			String afrom = st.nextToken(); String sfrom = st.nextToken(); st.nextToken(); st.nextToken();
//			aliasMap.put(afrom,ato);
//			speciesMap.put(sfrom, sto);
//			subsAliases.add(afrom);
//			subsSpecies.add(sfrom);
//		}
//		CellDesignerToCytoscapeConverter.createSpeciesMap(cd.getSbml());
//		int numberOfReactions = 0;
//		if(cd.getSbml().getModel().getListOfReactions()!=null){
//			numberOfReactions = cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();
//			for(int i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
//				ReactionDocument.Reaction reaction = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
//				String reactionString = getReactionString(reaction,cd,false,true);
//				for(int j=0;j<reaction.getListOfReactants().sizeOfSpeciesReferenceArray();j++){
//					String spid = reaction.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
//					String nspid = speciesMap.get(spid);
//					if(nspid!=null)
//						reaction.getListOfReactants().getSpeciesReferenceArray(j).setSpecies(nspid);
//					String al = Utils.getValue(reaction.getListOfReactants().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias());
//					String nal = aliasMap.get(al);
//					if(nal!=null){
//						xs.setStringValue(nal);
//						reaction.getListOfReactants().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias().set(xs);
//					}
//				}
//				for(int j=0;j<reaction.getAnnotation().getCelldesignerBaseReactants().sizeOfCelldesignerBaseReactantArray();j++){
//					CelldesignerBaseReactantDocument.CelldesignerBaseReactant cr = reaction.getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray(j);
//					String spid = Utils.getValue(cr.getSpecies());
//					String nspid = speciesMap.get(spid);
//					if(nspid!=null){
//						xs.setStringValue(nspid);
//						cr.setSpecies(xs);
//					}
//					String al = cr.getAlias();
//					String nal = aliasMap.get(al);
//					if(nal!=null){
//						cr.setAlias(nal);
//					}
//				}
//				for(int j=0;j<reaction.getListOfProducts().sizeOfSpeciesReferenceArray();j++){
//					String spid = reaction.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
//					String nspid = speciesMap.get(spid);
//					if(nspid!=null)
//						reaction.getListOfProducts().getSpeciesReferenceArray(j).setSpecies(nspid);
//					String al = Utils.getValue(reaction.getListOfProducts().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias());
//					String nal = aliasMap.get(al);
//					if(nal!=null){
//						xs.setStringValue(nal);
//						reaction.getListOfProducts().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias().set(xs);
//					}
//				}
//				for(int j=0;j<reaction.getAnnotation().getCelldesignerBaseProducts().sizeOfCelldesignerBaseProductArray();j++){
//					CelldesignerBaseProductDocument.CelldesignerBaseProduct cr = reaction.getAnnotation().getCelldesignerBaseProducts().getCelldesignerBaseProductArray(j);
//					String spid = Utils.getValue(cr.getSpecies());
//					String nspid = speciesMap.get(spid);
//					if(nspid!=null){
//						xs.setStringValue(nspid);
//						cr.setSpecies(xs);
//					}
//					String al = cr.getAlias();
//					String nal = aliasMap.get(al);
//					if(nal!=null){
//						cr.setAlias(nal);
//					}
//				}
//				if(reaction.getListOfModifiers()!=null)
//					for(int j=0;j<reaction.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
//						String spid = reaction.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
//						String nspid = speciesMap.get(spid);
//						if(nspid!=null){
//							reaction.getListOfModifiers().getModifierSpeciesReferenceArray(j).setSpecies(nspid);
//							SpeciesDocument.Species nsp = getSpecies(cd,nspid);
//						}
//						String al = Utils.getValue(reaction.getListOfModifiers().getModifierSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias());
//						String nal = aliasMap.get(al);
//						if(nal!=null){
//							xs.setStringValue(nal);
//							reaction.getListOfModifiers().getModifierSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias().set(xs);
//						}
//					}
//				if(reaction.getAnnotation().getCelldesignerListOfModification()!=null)
//					for(int j=0;j<reaction.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();j++){
//						CelldesignerModificationDocument.CelldesignerModification cr = reaction.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
//						String spid = cr.getModifiers();
//						String nspid = speciesMap.get(spid);
//						if(nspid!=null){
//							cr.setModifiers(nspid);
//							cr.getCelldesignerLinkTarget().setSpecies(nspid);
//						}
//						String al = cr.getAliases();
//						String nal = aliasMap.get(al);
//						if(nal!=null){
//							cr.setAliases(nal);
//							cr.getCelldesignerLinkTarget().setAlias(nal);
//						}
//					}			
//
//				String reactionAfter = getReactionString(reaction,cd,false,true);
//				if(!reactionString.equals(reactionAfter)){
//					//	System.out.println("Reaction "+reaction.getId()+" rewired:");
//					//else
//					System.out.println("Reaction "+reaction.getId()+" rewired:");
//					System.out.println("Before "+reactionString+" ("+getReactionString(reaction,cd,true,true)+")");
//					System.out.println("After  "+reactionAfter+" ("+getReactionString(reaction,cd,true,true)+")");
//					System.out.println("");
//				}
//			}}
//		// Now remove species and aliases
//		int i=0; int numberOfSpecies = cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray(); 
//		while(i<numberOfSpecies){
//			SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
//			if(subsSpecies.indexOf(sp.getId())>=0){
//				numberOfSpecies--;
//				System.out.println("Species "+sp.getId()+" removed ("+CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), Utils.getValue(sp.getName()), Utils.getValue(sp.getName()), sp.getCompartment(), true, false, "", cd.getSbml())+")");
//				cd.getSbml().getModel().getListOfSpecies().removeSpecies(i);				
//			}else{
//				i++;
//			}
//		}
//		i=0; int numberOfAliases = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray(); 
//		while(i<numberOfAliases){
//			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias al = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
//			if(subsAliases.indexOf(al.getId())>=0){
//				System.out.println("Alias "+al.getId()+" (species "+al.getSpecies()+") removed.");				
//				cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().removeCelldesignerSpeciesAlias(i);
//				numberOfAliases--;
//			}else{
//				i++;
//			}
//		}
//		// Now find redundant reactions (same reactants, same products), make them unique and combine the modifiers
//		System.out.println();
//		if(cd.getSbml().getModel().getListOfReactions()!=null){
//			i=0; numberOfReactions = cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();
//
//			HashMap<ReactionDocument.Reaction,String> reactionStringMap = new HashMap<ReactionDocument.Reaction,String>();
//			//HashMap<ReactionDocument.Reaction,String> reactionStringRealNameMap = new HashMap<ReactionDocument.Reaction,String>();		
//			for(i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
//				//Date t = new Date();
//				String reactionString = getReactionString(cd.getSbml().getModel().getListOfReactions().getReactionArray(i),cd,false,false);
//				//System.out.println("1) Spend "+((new Date()).getTime()-t.getTime()));
//				//t = new Date();
//				//String reactionStringRealName = getReactionString(cd.getSbml().getModel().getListOfReactions().getReactionArray(i),cd,true,true);
//				//System.out.println("2) Spend "+((new Date()).getTime()-t.getTime()));
//				reactionStringMap.put(cd.getSbml().getModel().getListOfReactions().getReactionArray(i), reactionString);
//				//reactionStringRealNameMap.put(cd.getSbml().getModel().getListOfReactions().getReactionArray(i), reactionStringRealName);
//				//if(i==(int)(0.02*i)*50)
//				//	System.out.print(i+"/"+numberOfReactions+" ");
//			}
//
//			i=0;
//			while(i<numberOfReactions){
//				ReactionDocument.Reaction reaction = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
//				//if(i==(int)(0.02*i)*50)
//				//	System.out.print(i+"/"+numberOfReactions+" ");
//				boolean modified = false;
//				//String reactionString = getReactionString(reaction,cd,false,false);
//				//String reactionStringRealNames = getReactionString(reaction,cd,true,true);
//				String reactionString = reactionStringMap.get(reaction);
//				//String reactionStringRealNames = reactionStringRealNameMap.get(reaction);
//				int j=i+1; 
//				while(j<numberOfReactions){
//					ReactionDocument.Reaction reactiontest = cd.getSbml().getModel().getListOfReactions().getReactionArray(j);
//					//String reactionTestString = getReactionString(reactiontest,cd,false,false);
//					//String reactionTestStringRealNames = getReactionString(reactiontest,cd,true,true);
//					String reactionTestString = reactionStringMap.get(reactiontest);
//					//String reactionTestStringRealNames = reactionStringRealNameMap.get(reactiontest);
//					if(reactionString.equals(reactionTestString)){
//						modified = true;
//						System.out.println();
//						System.out.println("Reactions "+reaction.getId()+" and "+reactiontest.getId()+" are found redundant:");
//						System.out.println(reaction.getId()+": "+getReactionString(reactiontest,cd,true,true));
//						System.out.println(reactiontest.getId()+": "+getReactionString(reactiontest,cd,true,true));
//						if(reactiontest.getListOfModifiers()!=null)
//							for(int k=0;k<reactiontest.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();k++){
//								if(reaction.getListOfModifiers()==null) reaction.addNewListOfModifiers();
//								ModifierSpeciesReferenceDocument.ModifierSpeciesReference mod = reaction.getListOfModifiers().addNewModifierSpeciesReference();
//								mod.set(reactiontest.getListOfModifiers().getModifierSpeciesReferenceArray(k));
//							}
//						if(reactiontest.getAnnotation().getCelldesignerListOfModification()!=null)
//							for(int k=0;k<reactiontest.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();k++){
//								if(reaction.getAnnotation().getCelldesignerListOfModification()==null)
//									reaction.getAnnotation().addNewCelldesignerListOfModification();
//								CelldesignerModificationDocument.CelldesignerModification mod = reaction.getAnnotation().getCelldesignerListOfModification().addNewCelldesignerModification();
//								mod.set(reactiontest.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(k));
//							}
//						cd.getSbml().getModel().getListOfReactions().removeReaction(j);
//						numberOfReactions--;
//					}else
//						j++;
//				}
//				i++;
//				if(modified){
//					System.out.println("Reaction "+reaction.getId()+" rewired:");
//					System.out.println(getReactionString(reaction,cd,true,true));
//				}
//				//System.out.println();
//			}}
//
//		HashMap<String,String> idMap = new HashMap<String,String>();
//		Vector<String> subsIds = new Vector<String>();
//		for(i=0;i<subsP.size();i++){
//			String s = subsP.get(i); 
//			StringTokenizer st = new StringTokenizer(s,"\t");
//			//StringTokenizer st = new StringTokenizer(s," ");
//			String ato = st.nextToken(); st.nextToken(); 
//			String afrom = st.nextToken(); 
//			idMap.put(afrom,ato);
//			//System.out.println(afrom+"->"+ato);
//			subsIds.add(afrom);
//		}
//		// Deal with redundant proteins, genes and rnas
//		System.out.println();
//		if(cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
//			for(i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
//				CelldesignerSpeciesDocument.CelldesignerSpecies csp = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
//				if(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null){
//					String pr = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
//					//System.out.println(pr);
//					if(idMap.get(pr)!=null){
//						xs.setStringValue(idMap.get(pr));
//						//System.out.println(pr+"->"+idMap.get(pr));
//						CellDesigner.entities = CellDesigner.getEntities(cd);
//						String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
//						csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference().set(xs);
//						xs.setStringValue(Utils.getValue(getProtein(cd,idMap.get(pr)).getName())); csp.setName(xs);
//						System.out.println("Changed protein reference in "+csp.getId()+" from "+pr+" ("+Utils.getValue(getProtein(cd,pr).getName())+") to "+idMap.get(pr)+" ("+Utils.getValue(getProtein(cd,idMap.get(pr)).getName())+")");
//						CellDesigner.entities = CellDesigner.getEntities(cd);
//						System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true));
//						System.out.println();
//					}
//				}
//				if(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference()!=null){
//					String pr = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference());
//					//System.out.println(pr);
//					if(idMap.get(pr)!=null){
//						xs.setStringValue(idMap.get(pr));
//						//System.out.println(pr+"->"+idMap.get(pr));
//						CellDesigner.entities = CellDesigner.getEntities(cd);
//						String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
//						csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference().set(xs);
//						xs.setStringValue(getGene(cd,idMap.get(pr)).getName()); csp.setName(xs);
//						System.out.println("Changed gene reference in "+csp.getId()+" from "+pr+" ("+getGene(cd,pr).getName()+") to "+idMap.get(pr)+" ("+getGene(cd,idMap.get(pr)).getName()+")");
//						CellDesigner.entities = CellDesigner.getEntities(cd);
//						System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true));
//						System.out.println();
//					}
//				}
//				if(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()!=null){
//					String pr = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference());
//					//System.out.println(pr);
//					if(idMap.get(pr)!=null){
//						xs.setStringValue(idMap.get(pr));
//						//System.out.println(pr+"->"+idMap.get(pr));
//						CellDesigner.entities = CellDesigner.getEntities(cd);
//						String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
//						csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference().set(xs);
//						xs.setStringValue(getRNA(cd,idMap.get(pr)).getName()); csp.setName(xs);
//						System.out.println("Changed rna reference in "+csp.getId()+" from "+pr+" ("+getRNA(cd,pr).getName()+") to "+idMap.get(pr)+" ("+getRNA(cd,idMap.get(pr)).getName()+")");
//						CellDesigner.entities = CellDesigner.getEntities(cd);
//						System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true));
//						System.out.println();
//					}
//				}			
//			}
//		for(i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
//			SpeciesDocument.Species csp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
//			if(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null){
//				String pr = Utils.getValue(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
//				//System.out.println(pr);
//				if(idMap.get(pr)!=null){
//					xs.setStringValue(idMap.get(pr));
//					//System.out.println(pr+"->"+idMap.get(pr));
//					CellDesigner.entities = CellDesigner.getEntities(cd);
//					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,csp.getId(), true, true);
//					csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference().set(xs);
//					System.out.println("Changed protein reference in "+csp.getId()+" from "+pr+" ("+Utils.getValue(getProtein(cd,pr).getName())+") to "+idMap.get(pr)+" ("+Utils.getValue(getProtein(cd,idMap.get(pr)).getName())+")");
//					CellDesigner.entities = CellDesigner.getEntities(cd);
//					System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,csp.getId(), true, true));
//					System.out.println();
//				}
//			}			
//		}
//		i=0; int numberOfProteins = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();
//		while(i<numberOfProteins){
//			CelldesignerProteinDocument.CelldesignerProtein protein = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
//			if(subsIds.indexOf(protein.getId())>=0){
//				String pto = idMap.get(protein.getId());
//				CelldesignerProteinDocument.CelldesignerProtein proteinto = getProtein(cd,pto);
//				if(protein.getCelldesignerNotes()!=null){
//					String comment = Utils.getValue(protein.getCelldesignerNotes()).trim();
//					if(proteinto.getCelldesignerNotes()==null)
//						proteinto.addNewCelldesignerNotes();
//					String commentto = Utils.getValue(proteinto.getCelldesignerNotes()).trim();
//					xs.setStringValue("<&html><&body>"+commentto+"\n"+comment+"<&/body><&/html>");
//					proteinto.getCelldesignerNotes().set(xs);
//				}
//				if(protein.getCelldesignerListOfModificationResidues()!=null){
//					if(proteinto.getCelldesignerListOfModificationResidues()==null)
//						proteinto.addNewCelldesignerListOfModificationResidues();
//					for(int j=0;j<protein.getCelldesignerListOfModificationResidues().sizeOfCelldesignerModificationResidueArray();j++)
//						proteinto.getCelldesignerListOfModificationResidues().addNewCelldesignerModificationResidue().set(protein.getCelldesignerListOfModificationResidues().getCelldesignerModificationResidueArray(j));
//				}
//				System.out.println("Protein "+protein.getId()+" ("+Utils.getValue(protein.getName())+") removed.");				
//				cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().removeCelldesignerProtein(i);
//				numberOfProteins--;
//			}else i++;
//		}
//		i=0; int numberOfGenes = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();
//		while(i<numberOfGenes){
//			CelldesignerGeneDocument.CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
//			if(subsIds.indexOf(gene.getId())>=0){
//				String pto = idMap.get(gene.getId());
//				CelldesignerGeneDocument.CelldesignerGene geneto = getGene(cd,pto);
//				if(geneto==null)
//					System.out.println("Substitution not found for "+gene.getId()+" ("+pto+")");
//				if(gene.getCelldesignerNotes()!=null){
//					String comment = Utils.getValue(gene.getCelldesignerNotes()).trim();
//					if(geneto.getCelldesignerNotes()==null)
//						geneto.addNewCelldesignerNotes();
//					String commentto = Utils.getValue(geneto.getCelldesignerNotes()).trim();
//					xs.setStringValue("<&html><&body>"+commentto+"\n"+comment+"<&/body><&/html>");
//					geneto.getCelldesignerNotes().set(xs);
//				}
//				System.out.println("Gene "+gene.getId()+" ("+gene.getName()+") removed.");				
//				cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().removeCelldesignerGene(i);
//				numberOfGenes--;
//			}else i++;
//		}
//		i=0; int numberOfRNAs = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();
//		while(i<numberOfRNAs){
//			CelldesignerRNADocument.CelldesignerRNA rna = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
//			if(subsIds.indexOf(rna.getId())>=0){
//				String pto = idMap.get(rna.getId());
//				CelldesignerRNADocument.CelldesignerRNA rnato = getRNA(cd,pto);
//				if(rna.getCelldesignerNotes()!=null){
//					String comment = Utils.getValue(rna.getCelldesignerNotes()).trim();
//					if(rnato.getCelldesignerNotes()==null)
//						rnato.addNewCelldesignerNotes();
//					String commentto = Utils.getValue(rnato.getCelldesignerNotes()).trim();
//					xs.setStringValue("<&html><&body>"+commentto+"\n"+comment+"<&/body><&/html>");
//					rnato.getCelldesignerNotes().set(xs);
//				}
//				System.out.println("RNA "+rna.getId()+" ("+rna.getName()+") removed.");				
//				cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().removeCelldesignerRNA(i);
//				numberOfRNAs--;
//				//i++;
//			}else i++;
//		}		
//	} // end rewireDiagram


//	private String getReactionString(ReactionDocument.Reaction r, SbmlDocument sbmlDoc, boolean realNames, boolean addModifiers){
//		String reactionString = "";
//		String rtype = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
//		ListOfModifiersDocument.ListOfModifiers lm = r.getListOfModifiers();
//		if(!addModifiers) lm = null;
//
//		Vector<String> listReactants = new Vector<String>();
//		Vector<String> listProducts = new Vector<String>();
//		for(int j=0;j<r.getListOfReactants().sizeOfSpeciesReferenceArray();j++)
//			listReactants.add(r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies());
//		for(int j=0;j<r.getListOfProducts().sizeOfSpeciesReferenceArray();j++)
//			listProducts.add(r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies());
//		Collections.sort(listReactants);
//		Collections.sort(listProducts);
//
//		for(int j=0;j<listReactants.size();j++){
//			String s = listReactants.get(j);
//			if(realNames){
//				s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,s,true,true);
//			}
//			if((s!=null)&&(!s.startsWith("null"))){
//				reactionString+=s;
//				if(j<r.getListOfReactants().sizeOfSpeciesReferenceArray()-1) reactionString+="+";
//			}
//		}
//		if(lm!=null){
//			reactionString+=" - ";
//			for(int j=0;j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
//				String s = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
//				if(realNames){
//					s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,s,true,true);
//				}
//				if((s!=null)&&(!s.startsWith("null"))){
//					//reactionString+=s;
//					reactionString+=s;
//					if(j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray()-1) reactionString+=" - ";
//				}
//			}}
//		String reaction="-";
//		if(rtype.toLowerCase().indexOf("transcription")>=0)
//			reaction="--";
//		if(rtype.toLowerCase().indexOf("unknown")>=0)
//			reaction+="?";
//		if(rtype.toLowerCase().indexOf("inhibition")>=0)
//			reaction+="|";
//		else
//			reaction+=">";
//		if(rtype.toLowerCase().indexOf("transport")>=0)
//			reaction="-t->";
//		reaction = " "+reaction+" ";
//		reactionString +=reaction;
//		for(int j=0;j<listProducts.size();j++){
//			String s = listProducts.get(j);
//			if(realNames){
//				s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,s,true,true);
//			}
//			if((s!=null)&&(!s.startsWith("null"))){
//				//reactionString+=s;
//				reactionString+=s;
//				if(j<r.getListOfProducts().sizeOfSpeciesReferenceArray()-1) reactionString+="+";
//			}
//		}
//		/*if(lm!=null){
//		  reactionString+="+";
//		  for(int j=0;j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
//		    String s = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
//		    if(realNames){
//		      s = convertSpeciesToName(sbmlDoc,s,true,true);
//		    }
//		    if((s!=null)&&(!s.startsWith("null"))){
//		    reactionString+=s;
//		    if(j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray()-1) reactionString+="+";
//		    }
//		  }}*/
//		return reactionString;
//	}

//	private SpeciesDocument.Species getSpecies(SbmlDocument cd, String id){
//		SpeciesDocument.Species sp = null;
//		for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
//			SpeciesDocument.Species a = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
//			if(a.getId().equals(id))
//				sp = a;
//		}
//		return sp;
//	}


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

}
