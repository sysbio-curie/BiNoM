package fr.curie.BiNoM.pathways.utils;

import org.w3c.dom.*;
import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

import java.io.*;
import java.util.*;

import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.wrappers.*;
import simtec.flux.xml.mathml.*;
import simtec.flux.symbolicmath.*;

public class testCellDesignerFile {

	public static void main(String[] args) {
	     try{

	         //String prefix = "Nfkb_simplest_plus";
	         //String prefix = "c:/datas/nfkb/Nfkb_Alain0707";
	    	 String prefix = "c:/datas/celldesigner/merge_andrei1";

	         SbmlDocument sbmlDoc = CellDesigner.loadCellDesigner(prefix+".xml");
	         HashMap<String,Vector<String>> aliases = new HashMap<String,Vector<String>>();
	         HashMap<String,String> includedspecies = new HashMap<String,String>();
	         Vector<String> species = new Vector<String>();
	         Vector<String> names = new Vector<String>();
	         HashMap<String,String> proteins = new HashMap<String,String>();
	         
	         for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
	        	 CelldesignerProteinDocument.CelldesignerProtein cp = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
	        	 proteins.put(cp.getId(), Utils.getValue(cp.getName()));
	         }
	         
	         for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
	        	 SpeciesDocument.Species sp = sbmlDoc.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
	        	 species.add(sp.getId());
	        	 names.add(Utils.getValue(sp.getName()));
	         }
	         
	         for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().sizeOfCelldesignerSpeciesArray();i++){
	        	 CelldesignerSpeciesDocument.CelldesignerSpecies cs = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
	        	 String iid = cs.getId();
	        	 CelldesignerProteinReferenceDocument.CelldesignerProteinReference cpr = cs.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference();
	        	 if(cpr!=null){
	        		 String prid = Utils.getValue(cpr);
	        		 String pname = proteins.get(prid);
	        		 if(pname==null){
	        			 System.out.println("ERROR!!! protein "+prid+" not found! in "+iid);
	        		 }
	        	 }
	         }
	         
	         for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().sizeOfCelldesignerSpeciesAliasArray();i++){
	        	 CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csa = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
	        	 String spid = csa.getSpecies();
	        	 String cid = csa.getId();
	        	 Vector<String> v = aliases.get(spid);
	        	 if(v==null){
	        		 v = new Vector<String>();
	        	 }
	        	 v.add(cid);
	        	 aliases.put(spid, v);
	         }
	         
	         for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
	        	 CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias ccsa = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
	        	 String spid = ccsa.getSpecies();
	        	 String cid = ccsa.getId();
	        	 Vector<String> v = aliases.get(spid);
	        	 if(v==null){
	        		 v = new Vector<String>();
	        	 }
	        	 v.add(cid);
	        	 aliases.put(spid, v);
	         }
	         
	         // Test 1, if for any species, there is at least one alias
	         for(int i=0;i<species.size();i++){
	        	 String spid = species.get(i);
	        	 Vector<String> v = aliases.get(spid);
	        	 if(v==null)
	        		 System.out.println(spid+"\t"+names.get(i)+"\tERROR: no alias found");
	        	 //else
	        	 //	 System.out.println(spid+"\t"+names.get(i)+"\t"+v.size());
	         }
	         
	         

	         //CellDesigner.saveCellDesigner(sbmlDoc,prefix+"_f.xml");

	         }catch(Exception e){
	           e.printStackTrace();
	         }
	}

}
