package fr.curie.BiNoM.pathways.utils;

import org.w3c.dom.*;
import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.utils.*;

import java.io.*;
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
		String nameCD = "C:/Datas/NaviCell/maps/mtor_src/master";
		cf.sbmlDoc = CellDesigner.loadCellDesigner(nameCD+".xml");
		cf.checkIfReactionConnectedToIncludedSpecies();
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
						}catch(Exception e){
							report+=" (NOT FIXED)\n";
							e.printStackTrace();
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			report+="ERROR: Reaction "+r.getId()+" "+e.getLocalizedMessage()+"\n";
		}
		}
	}
}
