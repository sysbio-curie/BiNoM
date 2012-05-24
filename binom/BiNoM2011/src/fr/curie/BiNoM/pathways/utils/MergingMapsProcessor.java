package fr.curie.BiNoM.pathways.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.xmlbeans.XmlString;
import org.sbml.x2001.ns.celldesigner.CelldesignerBaseProductDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerBaseReactantDocument;
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
 * Cell designer merging maps algorithms.
 * 
 * Most of the original code from AZ, additions and wrappers from EB.
 * 
 *  
 */
public class MergingMapsProcessor {

	private Vector<String> proteinMap;
	private Vector<String> speciesMap;
	private SbmlDocument cd1;
	private SbmlDocument cd2;
	
	/**
	 * Constructor
	 */
	public MergingMapsProcessor() {
		// void. void. void.
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
		String file1Text = Utils.loadString(fileName);
		file1Text = addPrefixToIds(file1Text,"rb_");
		cd1 = CellDesigner.loadCellDesignerFromText(file1Text);
		countAll(cd1);
	}
	
	public void setAndLoadFileName2 (String fileName) {
		this.cd2 = CellDesigner.loadCellDesigner(fileName);
	}
	
	public void setMergeLists() {
		proteinMap = new Vector<String>();
		speciesMap = new Vector<String>();
		produceCandidateMergeLists(cd1, cd2, proteinMap, speciesMap);
	}
	
	public void mergeTwoMaps() {
		mergeDiagrams(cd1,cd2);
		rewireDiagram(cd1, speciesMap,proteinMap);
	}
	
	public void saveCd1File(String fileName) {
		CellDesigner.saveCellDesigner(cd1, fileName);
	}
	
	public Vector<String> getSpeciesMap() {
		return this.speciesMap;
	}
	
	public void setSpeciesMap(Vector<String> data) {
		this.speciesMap = data;
	}
	
	public void printSpeciesMap() {
		System.out.println("#----- species map---------");
		for (String s : this.speciesMap)
			System.out.println(s);
	}
//	private void setCandidateMergingLists() {
//		// load file1 in a string
//		String file1Text = Utils.loadString(fileName1);
//		
//		// add a prefix to all IDs
//		file1Text = addPrefixToIds(file1Text,"rb_");
//		
//		// load file1_id in SbmlDocument object
//		cd1 = CellDesigner.loadCellDesignerFromText(file1Text);
//		System.out.println("Loaded.");
//		
//		countAll(cd1);
//		
//		// load file 2 as SbmlDocument object
//		cd2 = CellDesigner.loadCellDesigner(fileName2);
//		
//		// define and write maps of common things
//		produceCandidateMergeLists(cd1, cd2, proteinMap, speciesMap);
//	}
	
	
	private static void countAll(SbmlDocument cd){
		
		HashMap<String,Integer> rt = new HashMap<String,Integer>();
		
		for(int i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			ReactionDocument.Reaction reaction = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			Integer num = rt.get(Utils.getValue(reaction.getAnnotation().getCelldesignerReactionType()).trim());
			if(num==null) 
				num = new Integer(0);
			num = new Integer(num+1);
			rt.put(Utils.getValue(reaction.getAnnotation().getCelldesignerReactionType()).trim(), num);
		}
		
		Set keys = rt.keySet(); 
		Iterator<String> it = keys.iterator();
		Vector<String> types = new Vector<String>();
		while(it.hasNext()) {
			types.add(it.next()); 
			Collections.sort(types);
		}
		
		for(int i=0;i<types.size();i++)
			System.out.println(types.get(i)+"\t"+rt.get(types.get(i)));
		
		rt = new HashMap<String,Integer>();
		
		for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			Integer num = rt.get("SPECIES_"+Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()));
			if(num==null) num = new Integer(0);
			num = new Integer(num+1);
			rt.put("SPECIES_"+Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()), num);
		}
		
		keys = rt.keySet(); 
		it = keys.iterator(); 
		types = new Vector<String>(); 
		while(it.hasNext()) { 
			types.add(it.next()); 
			Collections.sort(types);
		}
		
		for(int i=0;i<types.size();i++)
			System.out.println(types.get(i)+"\t"+rt.get(types.get(i)));
		
		System.out.println("PROTEIN\t"+cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray());
		System.out.println("GENE\t"+cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray());
		System.out.println("RNA\t"+cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray());
	}
	
	private static String addPrefixToIds(String text, String prefix){
		Vector<String> ids = Utils.extractAllStringBetween(text, "id=\"", "\"");
		for(int i=0;i<ids.size();i++)
			System.out.print(ids.get(i)+" ");
		System.out.println("\n"+ids.size());
		for(int i=0;i<ids.size();i++)if(!ids.get(i).equals("default")){
			if(i==(int)(0.02f*i)*50)
				System.out.print(i+" ");
			text = Utils.replaceString(text, "\""+ids.get(i)+"\"", "\""+prefix+""+ids.get(i)+"\"");
			text = Utils.replaceString(text, ">"+ids.get(i)+"<", ">"+prefix+""+ids.get(i)+"<");
	    }
		System.out.println();
		return text;
	}
	
	private void mergeDiagrams(SbmlDocument cd, SbmlDocument cd2){
		// Compartments
		for(int i=0;i<cd2.getSbml().getModel().getListOfCompartments().sizeOfCompartmentArray();i++)
			if(!cd2.getSbml().getModel().getListOfCompartments().getCompartmentArray(i).getId().equals("default"))
				cd.getSbml().getModel().getListOfCompartments().addNewCompartment().set(cd2.getSbml().getModel().getListOfCompartments().getCompartmentArray(i));
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().sizeOfCelldesignerCompartmentAliasArray();i++)
			cd.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().addNewCelldesignerCompartmentAlias().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray(i));
		// Proteins, Genes, RNA
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++)
			cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().addNewCelldesignerProtein().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i));
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++)
			cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().addNewCelldesignerGene().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i));
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++)
			cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().addNewCelldesignerRNA().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i));
		// Species and Reactions
		for(int i=0;i<cd2.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++)
			cd.getSbml().getModel().getListOfSpecies().addNewSpecies().set(cd2.getSbml().getModel().getListOfSpecies().getSpeciesArray(i));
		if(cd2.getSbml().getModel().getListOfReactions()!=null)
		for(int i=0;i<cd2.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++)
			cd.getSbml().getModel().getListOfReactions().addNewReaction().set(cd2.getSbml().getModel().getListOfReactions().getReactionArray(i));
		// Included, simple and complex Aliases
		if(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++)
			cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().addNewCelldesignerSpecies().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i));
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++)
			cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().addNewCelldesignerSpeciesAlias().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i));
		if(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++)
			cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().addNewCelldesignerComplexSpeciesAlias().set(cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i));
	}
	
	
	private void produceCandidateMergeLists(SbmlDocument cd, SbmlDocument cd2, Vector<String> proteinMap, Vector<String> speciesMap) {

		// map of protein names to IDs for file1
		HashMap<String,String> proteinNames = new HashMap<String,String>();
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein p = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			proteinNames.put(Utils.getValue(p.getName()), p.getId());
			//System.out.println(Utils.getValue(p.getName())+"\t"+p.getId());
		}

		// get file1 entities
		CellDesigner.entities = CellDesigner.getEntities(cd);

		// map of species ID to species for file1 
		HashMap<String,SpeciesDocument.Species> species = new HashMap<String,SpeciesDocument.Species>();
		for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			species.put(sp.getId(), sp);
			//System.out.println(sp.getId());
		}

		// map of unique species Cytoscape name to id (including aliases)
		HashMap<String,String> speciesIds = new HashMap<String,String>(); 
		HashMap<String,Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>> speciesAliases = new HashMap<String,Vector<CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias>>(); // From species IDs to all corresponding aliases 
		CellDesignerToCytoscapeConverter c2c = new CellDesignerToCytoscapeConverter();
		c2c.sbml = cd;

		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias cas = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			String spId = cas.getSpecies();
			String id = cas.getId();
			SpeciesDocument.Species sp = species.get(spId);
			if(sp!=null){
				String spName = c2c.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), spId, Utils.getValue(sp.getName()), sp.getCompartment(), true, true, "", cd);
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
		for(int i=0;i<cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein p = cd2.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			String name = Utils.getValue(p.getName());
			if(proteinNames.containsKey(name)){
				//fw.write(proteinNames.get(name)+"\t"+name+"\t"+p.getId()+"\t"+name+"\n");
				proteinMap.add(proteinNames.get(name)+"\t"+name+"\t"+p.getId()+"\t"+name);
			}
		}
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
					speciesMap.add(vsa.get(0).getId()+"\t"+id1+"\t"+species.get(id1).getCompartment()+"\t"+spName+"\t"+cas.getId()+"\t"+spId+"\t"+sp.getCompartment()+"\t"+spName);
				}
			}
		}
		//fw.close();
	}

	private void rewireDiagram(SbmlDocument cd, Vector<String> subs, Vector<String> subsP){
		CellDesigner.entities = CellDesigner.getEntities(cd);
		XmlString xs = XmlString.Factory.newInstance();
		HashMap<String,String> aliasMap = new HashMap<String,String>();
		HashMap<String,String> speciesMap = new HashMap<String,String>();
		Vector<String> subsAliases = new Vector<String>();
		Vector<String> subsSpecies = new Vector<String>();
		for(int i=0;i<subs.size();i++){
			String s = subs.get(i); 
			StringTokenizer st = new StringTokenizer(s,"\t");
			//StringTokenizer st = new StringTokenizer(s," ");
			String ato = st.nextToken(); String sto = st.nextToken(); st.nextToken(); st.nextToken();
			String afrom = st.nextToken(); String sfrom = st.nextToken(); st.nextToken(); st.nextToken();
			aliasMap.put(afrom,ato);
			speciesMap.put(sfrom, sto);
			subsAliases.add(afrom);
			subsSpecies.add(sfrom);
		}
		CellDesignerToCytoscapeConverter.createSpeciesMap(cd.getSbml());
		int numberOfReactions = 0;
		if(cd.getSbml().getModel().getListOfReactions()!=null){
		numberOfReactions = cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();
		for(int i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			ReactionDocument.Reaction reaction = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			String reactionString = getReactionString(reaction,cd,false,true);
			for(int j=0;j<reaction.getListOfReactants().sizeOfSpeciesReferenceArray();j++){
				String spid = reaction.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
				String nspid = speciesMap.get(spid);
				if(nspid!=null)
					reaction.getListOfReactants().getSpeciesReferenceArray(j).setSpecies(nspid);
				String al = Utils.getValue(reaction.getListOfReactants().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias());
				String nal = aliasMap.get(al);
				if(nal!=null){
					xs.setStringValue(nal);
					reaction.getListOfReactants().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias().set(xs);
				}
			}
			for(int j=0;j<reaction.getAnnotation().getCelldesignerBaseReactants().sizeOfCelldesignerBaseReactantArray();j++){
				CelldesignerBaseReactantDocument.CelldesignerBaseReactant cr = reaction.getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray(j);
				String spid = Utils.getValue(cr.getSpecies());
				String nspid = speciesMap.get(spid);
				if(nspid!=null){
					xs.setStringValue(nspid);
					cr.setSpecies(xs);
				}
				String al = cr.getAlias();
				String nal = aliasMap.get(al);
				if(nal!=null){
					cr.setAlias(nal);
				}
			}
			for(int j=0;j<reaction.getListOfProducts().sizeOfSpeciesReferenceArray();j++){
				String spid = reaction.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
				String nspid = speciesMap.get(spid);
				if(nspid!=null)
					reaction.getListOfProducts().getSpeciesReferenceArray(j).setSpecies(nspid);
				String al = Utils.getValue(reaction.getListOfProducts().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias());
				String nal = aliasMap.get(al);
				if(nal!=null){
					xs.setStringValue(nal);
					reaction.getListOfProducts().getSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias().set(xs);
				}
			}
			for(int j=0;j<reaction.getAnnotation().getCelldesignerBaseProducts().sizeOfCelldesignerBaseProductArray();j++){
				CelldesignerBaseProductDocument.CelldesignerBaseProduct cr = reaction.getAnnotation().getCelldesignerBaseProducts().getCelldesignerBaseProductArray(j);
				String spid = Utils.getValue(cr.getSpecies());
				String nspid = speciesMap.get(spid);
				if(nspid!=null){
					xs.setStringValue(nspid);
					cr.setSpecies(xs);
				}
				String al = cr.getAlias();
				String nal = aliasMap.get(al);
				if(nal!=null){
					cr.setAlias(nal);
				}
			}
			if(reaction.getListOfModifiers()!=null)
			for(int j=0;j<reaction.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
				String spid = reaction.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
				String nspid = speciesMap.get(spid);
				if(nspid!=null){
					reaction.getListOfModifiers().getModifierSpeciesReferenceArray(j).setSpecies(nspid);
					SpeciesDocument.Species nsp = getSpecies(cd,nspid);
				}
				String al = Utils.getValue(reaction.getListOfModifiers().getModifierSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias());
				String nal = aliasMap.get(al);
				if(nal!=null){
					xs.setStringValue(nal);
					reaction.getListOfModifiers().getModifierSpeciesReferenceArray(j).getAnnotation().getCelldesignerAlias().set(xs);
				}
			}
			if(reaction.getAnnotation().getCelldesignerListOfModification()!=null)
			for(int j=0;j<reaction.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();j++){
				CelldesignerModificationDocument.CelldesignerModification cr = reaction.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
				String spid = cr.getModifiers();
				String nspid = speciesMap.get(spid);
				if(nspid!=null){
					cr.setModifiers(nspid);
					cr.getCelldesignerLinkTarget().setSpecies(nspid);
				}
				String al = cr.getAliases();
				String nal = aliasMap.get(al);
				if(nal!=null){
					cr.setAliases(nal);
					cr.getCelldesignerLinkTarget().setAlias(nal);
				}
			}			
			
			String reactionAfter = getReactionString(reaction,cd,false,true);
			if(!reactionString.equals(reactionAfter)){
			//	System.out.println("Reaction "+reaction.getId()+" rewired:");
			//else
				System.out.println("Reaction "+reaction.getId()+" rewired:");
				System.out.println("Before "+reactionString+" ("+getReactionString(reaction,cd,true,true)+")");
				System.out.println("After  "+reactionAfter+" ("+getReactionString(reaction,cd,true,true)+")");
				System.out.println("");
			}
		}}
		// Now remove species and aliases
		int i=0; int numberOfSpecies = cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray(); 
		while(i<numberOfSpecies){
			SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			if(subsSpecies.indexOf(sp.getId())>=0){
				numberOfSpecies--;
				System.out.println("Species "+sp.getId()+" removed ("+CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), Utils.getValue(sp.getName()), Utils.getValue(sp.getName()), sp.getCompartment(), true, false, "", cd.getSbml())+")");
				cd.getSbml().getModel().getListOfSpecies().removeSpecies(i);				
			}else{
				i++;
			}
		}
		i=0; int numberOfAliases = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray(); 
		while(i<numberOfAliases){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias al = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			if(subsAliases.indexOf(al.getId())>=0){
				System.out.println("Alias "+al.getId()+" (species "+al.getSpecies()+") removed.");				
				cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().removeCelldesignerSpeciesAlias(i);
				numberOfAliases--;
			}else{
				i++;
			}
		}
		// Now find redundant reactions (same reactants, same products), make them unique and combine the modifiers
		System.out.println();
		if(cd.getSbml().getModel().getListOfReactions()!=null){
		i=0; numberOfReactions = cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();
		
		HashMap<ReactionDocument.Reaction,String> reactionStringMap = new HashMap<ReactionDocument.Reaction,String>();
		//HashMap<ReactionDocument.Reaction,String> reactionStringRealNameMap = new HashMap<ReactionDocument.Reaction,String>();		
		for(i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			//Date t = new Date();
			String reactionString = getReactionString(cd.getSbml().getModel().getListOfReactions().getReactionArray(i),cd,false,false);
			//System.out.println("1) Spend "+((new Date()).getTime()-t.getTime()));
			//t = new Date();
			//String reactionStringRealName = getReactionString(cd.getSbml().getModel().getListOfReactions().getReactionArray(i),cd,true,true);
			//System.out.println("2) Spend "+((new Date()).getTime()-t.getTime()));
			reactionStringMap.put(cd.getSbml().getModel().getListOfReactions().getReactionArray(i), reactionString);
			//reactionStringRealNameMap.put(cd.getSbml().getModel().getListOfReactions().getReactionArray(i), reactionStringRealName);
			//if(i==(int)(0.02*i)*50)
			//	System.out.print(i+"/"+numberOfReactions+" ");
		}
		
		i=0;
		while(i<numberOfReactions){
			ReactionDocument.Reaction reaction = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			//if(i==(int)(0.02*i)*50)
			//	System.out.print(i+"/"+numberOfReactions+" ");
			boolean modified = false;
			//String reactionString = getReactionString(reaction,cd,false,false);
			//String reactionStringRealNames = getReactionString(reaction,cd,true,true);
			String reactionString = reactionStringMap.get(reaction);
			//String reactionStringRealNames = reactionStringRealNameMap.get(reaction);
			int j=i+1; 
			while(j<numberOfReactions){
				ReactionDocument.Reaction reactiontest = cd.getSbml().getModel().getListOfReactions().getReactionArray(j);
				//String reactionTestString = getReactionString(reactiontest,cd,false,false);
				//String reactionTestStringRealNames = getReactionString(reactiontest,cd,true,true);
				String reactionTestString = reactionStringMap.get(reactiontest);
				//String reactionTestStringRealNames = reactionStringRealNameMap.get(reactiontest);
				if(reactionString.equals(reactionTestString)){
					modified = true;
					System.out.println();
					System.out.println("Reactions "+reaction.getId()+" and "+reactiontest.getId()+" are found redundant:");
					System.out.println(reaction.getId()+": "+getReactionString(reactiontest,cd,true,true));
					System.out.println(reactiontest.getId()+": "+getReactionString(reactiontest,cd,true,true));
					if(reactiontest.getListOfModifiers()!=null)
					for(int k=0;k<reactiontest.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();k++){
						if(reaction.getListOfModifiers()==null) reaction.addNewListOfModifiers();
						ModifierSpeciesReferenceDocument.ModifierSpeciesReference mod = reaction.getListOfModifiers().addNewModifierSpeciesReference();
						mod.set(reactiontest.getListOfModifiers().getModifierSpeciesReferenceArray(k));
					}
					if(reactiontest.getAnnotation().getCelldesignerListOfModification()!=null)
					for(int k=0;k<reactiontest.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();k++){
						if(reaction.getAnnotation().getCelldesignerListOfModification()==null)
							reaction.getAnnotation().addNewCelldesignerListOfModification();
						CelldesignerModificationDocument.CelldesignerModification mod = reaction.getAnnotation().getCelldesignerListOfModification().addNewCelldesignerModification();
						mod.set(reactiontest.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(k));
					}
					cd.getSbml().getModel().getListOfReactions().removeReaction(j);
					numberOfReactions--;
				}else
					j++;
			}
			i++;
			if(modified){
				System.out.println("Reaction "+reaction.getId()+" rewired:");
				System.out.println(getReactionString(reaction,cd,true,true));
			}
			//System.out.println();
		}}
		
		HashMap<String,String> idMap = new HashMap<String,String>();
		Vector<String> subsIds = new Vector<String>();
		for(i=0;i<subsP.size();i++){
			String s = subsP.get(i); 
			StringTokenizer st = new StringTokenizer(s,"\t");
			//StringTokenizer st = new StringTokenizer(s," ");
			String ato = st.nextToken(); st.nextToken(); 
			String afrom = st.nextToken(); 
			idMap.put(afrom,ato);
			//System.out.println(afrom+"->"+ato);
			subsIds.add(afrom);
		}
		// Deal with redundant proteins, genes and rnas
		System.out.println();
		if(cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
		for(i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
			CelldesignerSpeciesDocument.CelldesignerSpecies csp = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
			if(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null){
				String pr = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
				//System.out.println(pr);
				if(idMap.get(pr)!=null){
					xs.setStringValue(idMap.get(pr));
					//System.out.println(pr+"->"+idMap.get(pr));
					CellDesigner.entities = CellDesigner.getEntities(cd);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
					csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference().set(xs);
					xs.setStringValue(Utils.getValue(getProtein(cd,idMap.get(pr)).getName())); csp.setName(xs);
					System.out.println("Changed protein reference in "+csp.getId()+" from "+pr+" ("+Utils.getValue(getProtein(cd,pr).getName())+") to "+idMap.get(pr)+" ("+Utils.getValue(getProtein(cd,idMap.get(pr)).getName())+")");
					CellDesigner.entities = CellDesigner.getEntities(cd);
					System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true));
					System.out.println();
				}
			}
			if(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference()!=null){
				String pr = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference());
				//System.out.println(pr);
				if(idMap.get(pr)!=null){
					xs.setStringValue(idMap.get(pr));
					//System.out.println(pr+"->"+idMap.get(pr));
					CellDesigner.entities = CellDesigner.getEntities(cd);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
					csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference().set(xs);
					xs.setStringValue(getGene(cd,idMap.get(pr)).getName()); csp.setName(xs);
					System.out.println("Changed gene reference in "+csp.getId()+" from "+pr+" ("+getGene(cd,pr).getName()+") to "+idMap.get(pr)+" ("+getGene(cd,idMap.get(pr)).getName()+")");
					CellDesigner.entities = CellDesigner.getEntities(cd);
					System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true));
					System.out.println();
				}
			}
			if(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference()!=null){
				String pr = Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference());
				//System.out.println(pr);
				if(idMap.get(pr)!=null){
					xs.setStringValue(idMap.get(pr));
					//System.out.println(pr+"->"+idMap.get(pr));
					CellDesigner.entities = CellDesigner.getEntities(cd);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true);
					csp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference().set(xs);
					xs.setStringValue(getRNA(cd,idMap.get(pr)).getName()); csp.setName(xs);
					System.out.println("Changed rna reference in "+csp.getId()+" from "+pr+" ("+getRNA(cd,pr).getName()+") to "+idMap.get(pr)+" ("+getRNA(cd,idMap.get(pr)).getName()+")");
					CellDesigner.entities = CellDesigner.getEntities(cd);
					System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,Utils.getValue(csp.getCelldesignerAnnotation().getCelldesignerComplexSpecies()), true, true));
					System.out.println();
				}
			}			
		}
		for(i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species csp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			if(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null){
				String pr = Utils.getValue(csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
				//System.out.println(pr);
				if(idMap.get(pr)!=null){
					xs.setStringValue(idMap.get(pr));
					//System.out.println(pr+"->"+idMap.get(pr));
					CellDesigner.entities = CellDesigner.getEntities(cd);
					String cspname = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,csp.getId(), true, true);
					csp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference().set(xs);
					System.out.println("Changed protein reference in "+csp.getId()+" from "+pr+" ("+Utils.getValue(getProtein(cd,pr).getName())+") to "+idMap.get(pr)+" ("+Utils.getValue(getProtein(cd,idMap.get(pr)).getName())+")");
					CellDesigner.entities = CellDesigner.getEntities(cd);
					System.out.println("Species "+cspname+" -> "+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd,csp.getId(), true, true));
					System.out.println();
				}
			}			
		}
		i=0; int numberOfProteins = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();
		while(i<numberOfProteins){
			CelldesignerProteinDocument.CelldesignerProtein protein = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			if(subsIds.indexOf(protein.getId())>=0){
				String pto = idMap.get(protein.getId());
				CelldesignerProteinDocument.CelldesignerProtein proteinto = getProtein(cd,pto);
				if(protein.getCelldesignerNotes()!=null){
					String comment = Utils.getValue(protein.getCelldesignerNotes()).trim();
					if(proteinto.getCelldesignerNotes()==null)
						proteinto.addNewCelldesignerNotes();
					String commentto = Utils.getValue(proteinto.getCelldesignerNotes()).trim();
					xs.setStringValue("<&html><&body>"+commentto+"\n"+comment+"<&/body><&/html>");
					proteinto.getCelldesignerNotes().set(xs);
				}
				if(protein.getCelldesignerListOfModificationResidues()!=null){
					if(proteinto.getCelldesignerListOfModificationResidues()==null)
						proteinto.addNewCelldesignerListOfModificationResidues();
				for(int j=0;j<protein.getCelldesignerListOfModificationResidues().sizeOfCelldesignerModificationResidueArray();j++)
					proteinto.getCelldesignerListOfModificationResidues().addNewCelldesignerModificationResidue().set(protein.getCelldesignerListOfModificationResidues().getCelldesignerModificationResidueArray(j));
				}
				System.out.println("Protein "+protein.getId()+" ("+Utils.getValue(protein.getName())+") removed.");				
				cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().removeCelldesignerProtein(i);
				numberOfProteins--;
			}else i++;
		}
		i=0; int numberOfGenes = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();
		while(i<numberOfGenes){
			CelldesignerGeneDocument.CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			if(subsIds.indexOf(gene.getId())>=0){
				String pto = idMap.get(gene.getId());
				CelldesignerGeneDocument.CelldesignerGene geneto = getGene(cd,pto);
				if(geneto==null)
					System.out.println("Substitution not found for "+gene.getId()+" ("+pto+")");
				if(gene.getCelldesignerNotes()!=null){
					String comment = Utils.getValue(gene.getCelldesignerNotes()).trim();
					if(geneto.getCelldesignerNotes()==null)
						geneto.addNewCelldesignerNotes();
					String commentto = Utils.getValue(geneto.getCelldesignerNotes()).trim();
					xs.setStringValue("<&html><&body>"+commentto+"\n"+comment+"<&/body><&/html>");
					geneto.getCelldesignerNotes().set(xs);
				}
				System.out.println("Gene "+gene.getId()+" ("+gene.getName()+") removed.");				
				cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().removeCelldesignerGene(i);
				numberOfGenes--;
			}else i++;
		}
		i=0; int numberOfRNAs = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();
		while(i<numberOfRNAs){
			CelldesignerRNADocument.CelldesignerRNA rna = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
			if(subsIds.indexOf(rna.getId())>=0){
				String pto = idMap.get(rna.getId());
				CelldesignerRNADocument.CelldesignerRNA rnato = getRNA(cd,pto);
				if(rna.getCelldesignerNotes()!=null){
					String comment = Utils.getValue(rna.getCelldesignerNotes()).trim();
					if(rnato.getCelldesignerNotes()==null)
						rnato.addNewCelldesignerNotes();
					String commentto = Utils.getValue(rnato.getCelldesignerNotes()).trim();
					xs.setStringValue("<&html><&body>"+commentto+"\n"+comment+"<&/body><&/html>");
					rnato.getCelldesignerNotes().set(xs);
				}
				System.out.println("RNA "+rna.getId()+" ("+rna.getName()+") removed.");				
				cd.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().removeCelldesignerRNA(i);
				numberOfRNAs--;
				//i++;
			}else i++;
		}		
	}
	
	private String getReactionString(ReactionDocument.Reaction r, SbmlDocument sbmlDoc, boolean realNames, boolean addModifiers){
		  String reactionString = "";
		  String rtype = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
		  ListOfModifiersDocument.ListOfModifiers lm = r.getListOfModifiers();
		  if(!addModifiers) lm = null;
		  
		  Vector<String> listReactants = new Vector<String>();
		  Vector<String> listProducts = new Vector<String>();
		  for(int j=0;j<r.getListOfReactants().sizeOfSpeciesReferenceArray();j++)
			  listReactants.add(r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies());
		  for(int j=0;j<r.getListOfProducts().sizeOfSpeciesReferenceArray();j++)
			  listProducts.add(r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies());
		  Collections.sort(listReactants);
		  Collections.sort(listProducts);
		  
		  for(int j=0;j<listReactants.size();j++){
		    String s = listReactants.get(j);
		    if(realNames){
		      s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,s,true,true);
		    }
		    if((s!=null)&&(!s.startsWith("null"))){
		    reactionString+=s;
		    if(j<r.getListOfReactants().sizeOfSpeciesReferenceArray()-1) reactionString+="+";
		    }
		  }
		  if(lm!=null){
		  reactionString+=" - ";
		  for(int j=0;j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
		    String s = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
		    if(realNames){
		      s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,s,true,true);
		    }
		    if((s!=null)&&(!s.startsWith("null"))){
		    //reactionString+=s;
  		reactionString+=s;
		    if(j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray()-1) reactionString+=" - ";
		    }
		  }}
		  String reaction="-";
		  if(rtype.toLowerCase().indexOf("transcription")>=0)
			  reaction="--";
		  if(rtype.toLowerCase().indexOf("unknown")>=0)
			  reaction+="?";
		  if(rtype.toLowerCase().indexOf("inhibition")>=0)
			  reaction+="|";
		  else
			  reaction+=">";
		  if(rtype.toLowerCase().indexOf("transport")>=0)
			  reaction="-t->";
		  reaction = " "+reaction+" ";
		  reactionString +=reaction;
		  for(int j=0;j<listProducts.size();j++){
		    String s = listProducts.get(j);
		    if(realNames){
		      s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,s,true,true);
		    }
		    if((s!=null)&&(!s.startsWith("null"))){
		    //reactionString+=s;
  		reactionString+=s;
		    if(j<r.getListOfProducts().sizeOfSpeciesReferenceArray()-1) reactionString+="+";
		    }
		  }
		  /*if(lm!=null){
		  reactionString+="+";
		  for(int j=0;j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
		    String s = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
		    if(realNames){
		      s = convertSpeciesToName(sbmlDoc,s,true,true);
		    }
		    if((s!=null)&&(!s.startsWith("null"))){
		    reactionString+=s;
		    if(j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray()-1) reactionString+="+";
		    }
		  }}*/
		  return reactionString;
		}
	
	private SpeciesDocument.Species getSpecies(SbmlDocument cd, String id){
		SpeciesDocument.Species sp = null;
		for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species a = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			if(a.getId().equals(id))
				sp = a;
		}
		return sp;
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
}
