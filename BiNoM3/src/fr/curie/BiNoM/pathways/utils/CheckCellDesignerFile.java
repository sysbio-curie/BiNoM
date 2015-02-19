package fr.curie.BiNoM.pathways.utils;

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;
import fr.curie.BiNoM.pathways.analysis.structure.BiographUtils;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import java.util.*;

public class CheckCellDesignerFile {

	public SbmlDocument sbmlDoc = null;
	public String report = "";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
		CheckCellDesignerFile cf = new CheckCellDesignerFile();
		//String nameCD = "C:/Datas/binomtest/problems";
		//String nameCD = "C:/Datas/NaviCell2.2/dnarepair_src_2014/dnarepair_FANCONI";
		String nameCD = "C:/Datas/NaviCell2.2/iron_metabolism_src/master";
		//String nameCD = "C:/Datas/acsn/assembly/survival_src/survival_HEDGEHOG"; 
		cf.sbmlDoc = CellDesigner.loadCellDesigner(nameCD+".xml");
		cf.checkIfReactionConnectedToIncludedSpecies();
		cf.checkIfSpeciesIsWithoutAlias();
		cf.checkComplexFormationConsistency();
		CellDesigner.saveCellDesigner(cf.sbmlDoc, nameCD+"_fixed.xml");
		System.out.println(cf.report);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void checkIfReactionConnectedToIncludedSpecies() throws Exception{
		HashSet<String> includedSpecies = new HashSet<String>();
		HashMap<String, String> speciesComplexMap = new HashMap<String, String>();
		if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
			CelldesignerSpeciesDocument.CelldesignerSpecies cs = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
			includedSpecies.add(cs.getId());
			String complex = Utils.getValue(cs.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
			speciesComplexMap.put(cs.getId(), complex);
			//System.out.println(cs.getId()+" in "+complex);
		}
		HashMap<String, String> speciesAliasComplexAliasMap = new HashMap<String, String>();
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csa = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			if(csa.getComplexSpeciesAlias()!=null)if(!csa.getComplexSpeciesAlias().trim().equals("")){
				speciesAliasComplexAliasMap.put(csa.getId(),csa.getComplexSpeciesAlias());
			}
		}
		
		System.out.println("Number of reactions = "+sbmlDoc.getSbml().getModel().getListOfReactions().sizeOfReactionArray());
		if(sbmlDoc.getSbml().getModel().getListOfReactions()!=null)
		for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			ReactionDocument.Reaction r = sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray(i);
			try{
			if(r.getAnnotation().getCelldesignerBaseReactants()!=null){
				for(int j=0;j<r.getAnnotation().getCelldesignerBaseReactants().sizeOfCelldesignerBaseReactantArray();j++){
					CelldesignerBaseReactantDocument.CelldesignerBaseReactant sr = r.getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray(j);
					String spid = Utils.getValue(sr.getSpecies());
					if(includedSpecies.contains(spid)){
						report+="ERROR: Reaction "+r.getId()+": included species "+spid+" is used as reactant";
						// fix
						try{
							XmlString xs = XmlString.Factory.newInstance();
							xs.setStringValue(speciesComplexMap.get(spid));
							sr.setSpecies(xs);
							sr.setAlias(speciesAliasComplexAliasMap.get(sr.getAlias()));
							report+=" (FIXED)\n";
						}catch(Exception e){
							report+=" (NOT FIXED)\n";
							e.printStackTrace();
						}
					}
				}
			}
			if(r.getAnnotation().getCelldesignerBaseProducts()!=null){
				for(int j=0;j<r.getAnnotation().getCelldesignerBaseProducts().sizeOfCelldesignerBaseProductArray();j++){
					CelldesignerBaseProductDocument.CelldesignerBaseProduct sr = r.getAnnotation().getCelldesignerBaseProducts().getCelldesignerBaseProductArray(j);
					String spid = Utils.getValue(sr.getSpecies());
					if(includedSpecies.contains(spid)){
						report+="ERROR: Reaction "+r.getId()+": included species "+spid+" is used as product ";
						// fix
						try{
							XmlString xs = XmlString.Factory.newInstance();
							xs.setStringValue(speciesComplexMap.get(spid));
							sr.setSpecies(xs);
							sr.setAlias(speciesAliasComplexAliasMap.get(sr.getAlias()));
							report+=" (FIXED)\n";
						}catch(Exception e){
							report+=" (NOT FIXED)\n";
							e.printStackTrace();
						}
					}
				}
			}
			if(r.getAnnotation().getCelldesignerListOfModification()!=null){
				for(int j=0;j<r.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();j++){
					CelldesignerModificationDocument.CelldesignerModification sr = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
					String spid = sr.getModifiers();
					if(spid!=null){
					StringTokenizer st = new StringTokenizer(spid,",");
					boolean found = false;
					while(st.hasMoreTokens())
						if(includedSpecies.contains(st.nextToken()))
							found = true;
					if(found){
						report+="ERROR: Reaction "+r.getId()+": included species "+spid+" is used as modifier ";
						// fix
						try{
							
							String modifiers = sr.getModifiers();
							if(modifiers!=null){
							String aliases = sr.getAliases();
							StringTokenizer stm = new StringTokenizer(modifiers,",");
							StringTokenizer sta = new StringTokenizer(aliases,",");
							String new_modifiers = "";
							String new_aliases = "";
							while(stm.hasMoreTokens()){
								String s = stm.nextToken();
								if(speciesComplexMap.get(s)==null)
									new_modifiers+=s+",";
								else
									new_modifiers+=speciesComplexMap.get(s)+",";
							}
							while(sta.hasMoreTokens()){
								String s = sta.nextToken();
								if(speciesAliasComplexAliasMap.get(s)==null)
									new_aliases+=s+",";
								else
									new_aliases+=speciesAliasComplexAliasMap.get(s)+",";
							}
							if(!new_modifiers.equals("")) new_modifiers=new_modifiers.substring(0, new_modifiers.length()-1);
							if(!new_aliases.equals("")) new_aliases=new_aliases.substring(0, new_aliases.length()-1);							
							sr.setModifiers(new_modifiers);
							sr.setAliases(new_aliases);
							if(sr.getCelldesignerLinkTarget()!=null){
								sr.getCelldesignerLinkTarget().setSpecies(new_modifiers);
								sr.getCelldesignerLinkTarget().setAlias(new_aliases);	
							}
							
							report+=" (FIXED)\n";
							}
						}catch(Exception e){
							report+=" (NOT FIXED)\n";
							e.printStackTrace();
						}
					}}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			report+="ERROR: Reaction "+r.getId()+" "+e.getLocalizedMessage()+"\n";
		}
		}
	}
	
	public void checkIfSpeciesIsWithoutAlias() throws Exception{
		Vector<String> speciesList = new Vector<String>();
		Vector<String> speciesNotConnected = null;
		for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			speciesList.add(sbmlDoc.getSbml().getModel().getListOfSpecies().getSpeciesArray(i).getId());
		}
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csa = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			String spid = csa.getSpecies();
			int k = speciesList.indexOf(spid);
			if(k!=-1)
				speciesList.remove(k);
		}
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
			CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias csa = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
			String spid = csa.getSpecies();
			int k = speciesList.indexOf(spid);
			if(k!=-1)
				speciesList.remove(k);			
		}
		
		speciesNotConnected = speciesList;
		
		for(int i=0;i<speciesNotConnected.size();i++){
			try{
				report+="No alias is defined for species "+speciesNotConnected.get(i)+" ";
				//System.out.println("Looking for species "+speciesNotConnected.get(i)+" total="+sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray());
				for(int k=0;k<sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();k++){
					String id = sbmlDoc.getSbml().getModel().getListOfSpecies().getSpeciesArray(k).getId();
					if(id.equals(speciesNotConnected.get(i))){
						//System.out.println("Removing species "+id+" ("+k+" from "+sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray()+")");
						sbmlDoc.getSbml().getModel().getListOfSpecies().removeSpecies(k); 
						//System.out.println("Number of species "+sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray());
						break;
					}
				}
				report+=" (FIXED)\n";
			}catch(Exception e){
				report+=" (NOT FIXED)\n";
				e.printStackTrace();
			}
		}
		
	}
	
	public void checkComplexFormationConsistency(){
		CellDesigner.entities = CellDesigner.getEntities(sbmlDoc);
		Graph graph = XGMML.convertXGMMLToGraph(CellDesignerToCytoscapeConverter.getXGMMLGraph("empty", sbmlDoc.getSbml()));
		Vector<String> badReactions = CheckComplexFormationConsistency(graph);
		if(badReactions.size()>0){
			for(String r: badReactions)
				report+=r+"(NOT FIXED)\n";
		}
	}
	
	
	public static Vector<String> CheckComplexFormationConsistency(Graph graph){
		Vector<String> inconsistentReactionsMessages = new Vector<String>();
		graph.calcNodesInOut();
		
		Vector<String> simpleMolecules = new Vector<String>();
		Vector<String> genes = new Vector<String>();
		Vector<String> rnas = new Vector<String>();
		Vector<String> asrnas = new Vector<String>();
		for(fr.curie.BiNoM.pathways.analysis.structure.Node n: graph.Nodes){
			//System.out.println(n.getFirstAttributeValue("CELLDESIGNER_NODE_TYPE"));
			if(n.getFirstAttributeValue("CELLDESIGNER_NODE_TYPE").equals("SIMPLE_MOLECULE")){
				Vector<String> ps = BiographUtils.extractProteinNamesFromNodeName(n.Id);
				for(String s: ps)
					if(!simpleMolecules.contains(s))
						simpleMolecules.add(s);
			}
			if(n.getFirstAttributeValue("CELLDESIGNER_NODE_TYPE").equals("GENE")){
				Vector<String> ps = BiographUtils.extractProteinNamesFromNodeName(n.Id);
				for(String s: ps)
					if(!genes.contains(s))
						genes.add(s);
			}
			if(n.getFirstAttributeValue("CELLDESIGNER_NODE_TYPE").equals("RNA")){
				Vector<String> ps = BiographUtils.extractProteinNamesFromNodeName(n.Id);
				for(String s: ps)
					if(!rnas.contains(s))
						rnas.add(s);
			}
			if(n.getFirstAttributeValue("CELLDESIGNER_NODE_TYPE").equals("ANTISENSE_RNA")){
				Vector<String> ps = BiographUtils.extractProteinNamesFromNodeName(n.Id);
				for(String s: ps)
					if(!asrnas.contains(s))
						asrnas.add(s);
			}
		}
		
		for(fr.curie.BiNoM.pathways.analysis.structure.Node n: graph.Nodes){
			String reactionId = n.getFirstAttributeValue("CELLDESIGNER_REACTION");
			String tp = n.getFirstAttributeValue("CELLDESIGNER_NODE_TYPE");
			if(reactionId!=null)if(!reactionId.equals(""))if(tp.equals("HETERODIMER_ASSOCIATION")){
				if(reactionId.equals("s_shh2_re84")){
					System.out.println();
				}
				Vector<String> complexComponents = new Vector<String>();
				Vector<String> reactants = new Vector<String>();
				String complex = null;
				for(fr.curie.BiNoM.pathways.analysis.structure.Edge e: n.outcomingEdges)if(e.getFirstAttributeValue("CELLDESIGNER_EDGE_TYPE").equals("RIGHT")){
					String id = e.Node2.Id;
					if(e.Node2.getFirstAttributeValue("CELLDESIGNER_NODE_TYPE").equals("COMPLEX")){
						complexComponents = BiographUtils.extractProteinNamesFromNodeName(e.Node2.Id);
						complex = id;
					}
				}
				for(fr.curie.BiNoM.pathways.analysis.structure.Edge e: n.incomingEdges)if(e.getFirstAttributeValue("CELLDESIGNER_EDGE_TYPE").equals("LEFT")){
					String reactant = e.Node1.Id;
					Vector<String> ps = BiographUtils.extractProteinNamesFromNodeName(e.Node1.Id);
					for(String s: ps) if(!reactants.contains(s)) reactants.add(s);
				}
				
				// Filter out simple molecules, take into account prefixes
				for(String s: simpleMolecules){
					if(reactants.contains(s)) 
						reactants.remove(reactants.indexOf(s));
					if(complexComponents.contains(s)) 
						complexComponents.remove(complexComponents.indexOf(s));
				}
				for(String s: genes){
					if(reactants.contains(s))if(s.startsWith("g")){
						reactants.set(reactants.indexOf(s), s.substring(1, s.length()));
					}
				}
				
				// check if there is complex in the reactants
				if(complex==null){
					inconsistentReactionsMessages.add("warning: reaction "+n.Id+": there is no complexes in the reaction products (dissociation?)");
				}else{
				
				// check if all reactants appear in complex
				for(String reactant: reactants){
					if(!complexComponents.contains(reactant)){
						//System.out.println("ERROR: reaction "+n.Id+": reactant "+reactant+" is not present in the complex "+complex);
						inconsistentReactionsMessages.add("warning: reaction "+n.Id+": reactant "+reactant+" is not present in the complex "+complex);
						//if(!inconsistentReactions.contains(n.Id)) inconsistentReactions.add(n.Id);
					}
				}
				// check if all complex components appear in reactants
				for(String component: complexComponents){
					if(!reactants.contains(component)){
						//System.out.println("ERROR: reaction "+n.Id+": component "+component+" is not present among reactants.");
						inconsistentReactionsMessages.add("warning: reaction "+n.Id+": component "+component+" is not present among reactants.");
						//if(!inconsistentReactions.contains(n.Id)) inconsistentReactions.add(n.Id);						
					}
				}}
			}
		}
		return inconsistentReactionsMessages;
	}

	
}
