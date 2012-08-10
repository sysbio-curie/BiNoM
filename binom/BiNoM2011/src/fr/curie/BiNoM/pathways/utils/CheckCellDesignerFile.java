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
		String nameCD = "C:/Datas/NaviCell/maps/notchp53wnt_src/master";
		cf.sbmlDoc = CellDesigner.loadCellDesigner(nameCD+".xml");
		cf.checkIfReactionConnectedToIncludedSpecies();
		System.out.println(cf.report);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void checkIfReactionConnectedToIncludedSpecies() throws Exception{
		HashSet<String> includedSpecies = new HashSet<String>();
		if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
		for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
			CelldesignerSpeciesDocument.CelldesignerSpecies cs = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
			includedSpecies.add(cs.getId());
		}
		
		if(sbmlDoc.getSbml().getModel().getListOfReactions()!=null)
		for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			ReactionDocument.Reaction r = sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray(i);
			try{
			if(r.getAnnotation().getCelldesignerBaseReactants()!=null){
				for(int j=0;j<r.getAnnotation().getCelldesignerBaseReactants().sizeOfCelldesignerBaseReactantArray();j++){
					CelldesignerBaseReactantDocument.CelldesignerBaseReactant sr = r.getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray(j);
					String spid = Utils.getValue(sr.getSpecies());
					if(includedSpecies.contains(spid)){
						report+="ERROR: Reaction "+r.getId()+": included species "+spid+" is used as reactant\n";
					}
				}
			}
			if(r.getAnnotation().getCelldesignerBaseProducts()!=null){
				for(int j=0;j<r.getAnnotation().getCelldesignerBaseProducts().sizeOfCelldesignerBaseProductArray();j++){
					CelldesignerBaseProductDocument.CelldesignerBaseProduct sr = r.getAnnotation().getCelldesignerBaseProducts().getCelldesignerBaseProductArray(j);
					String spid = Utils.getValue(sr.getSpecies());
					if(includedSpecies.contains(spid)){
						report+="ERROR: Reaction "+r.getId()+": included species "+spid+" is used as reactant\n";
					}
				}
			}
			if(r.getAnnotation().getCelldesignerListOfModification()!=null){
				for(int j=0;j<r.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();j++){
					CelldesignerModificationDocument.CelldesignerModification sr = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
					String spid = sr.getModifiers();
					if(includedSpecies.contains(spid)){
						report+="ERROR: Reaction "+r.getId()+": included species "+spid+" is used as reactant\n";
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
