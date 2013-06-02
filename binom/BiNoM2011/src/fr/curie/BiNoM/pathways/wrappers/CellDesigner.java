/*
   BiNoM Cytoscape Plugin
   Copyright (C) 2006-2007 Curie Institute, 26 rue d'Ulm, 75005 Paris - FRANCE

   BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
*/

/*
  BiNoM authors:
	Andrei Zinovyev : http://www.ihes.fr/~zinovyev
	Eric Viara : http://www.sysra.com/viara
	Laurence Calzone :	http://leibniz.biol.vt.edu/people/laurence/laurence.html
*/

package fr.curie.BiNoM.pathways.wrappers;


import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.utils.*;
import edu.rpi.cs.xgmml.*;
import java.util.*;
import java.io.*;

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

/**
 * Wrapper class for CellDesigner with some utilities implemented
 * @author Andrei Zinovyev
 *
 */
public class CellDesigner {
	
	
public static String reactionNodeTypes[] = {
  "HETERODIMER_ASSOCIATION",
  "HETERODIMER_DISSOCIATION",
  "DISSOCIATION",
  "STATE_TRANSITION",
  "UNKNOWN_TRANSITION",
  "UNKNOWN_REACTION",
  "KNOWN_TRANSITION_OMITTED",
  "TRANSCRIPTIONAL_ACTIVATION",
  "TRANSCRIPTIONAL_INHIBITION",
  "TRANSLATIONAL_ACTIVATION",
  "TRANSLATIONAL_INHIBITION",
  "TRUNCATION",
  "INHIBITION"    
  //"CATALYSIS"
  };    
  
  
  /**
   * Map from Species Ids to all CellDesigner objects
   */
  public static HashMap entities = null;

  public CellDesigner() {
  }

  /**
   * Save org.sbml.x2001.ns.celldesigner.SbmlDocument to file
   * @param sbml
   * @param fn
   */
  
  public static void main(String args[]){
	  try{
		  
		  CellDesigner cd = new CellDesigner();
		  SbmlDocument sbml = cd.loadCellDesigner("c:/datas/navicell/test/merged/merged_master.xml");
		  cd.saveCellDesigner(sbml,"c:/datas/navicell/test/merged/merged_master1.xml");
		  
	  }catch(Exception e){
		  e.printStackTrace();
	  }
  }
  
  
  public static void saveCellDesigner(SbmlDocument sbml, String fn){
   try{
     FileWriter fw = new FileWriter(fn);
     String s = sbml.toString();
     //s = parseCellDesigner.replaceString(s,"xmlns=\"http://www.sbml.org/2001/ns/celldesigner\"","xmlns=\"http://www.sbml.org/sbml/level2\" xmlns:celldesigner=\"http://www.sbml.org/2001/ns/celldesigner\"");
     s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n"+s;
     
     
     /*s = Utils.replaceStringCount(s,"xmlns=\"http://www.sbml.org/2001/ns/celldesigner\"","xmlns=\"http://www.sbml.org/sbml/level2\"");
     s = Utils.replaceStringCount(s,"SpeciesAnnotation","annotation");
     s = Utils.replaceStringCount(s,"ReactionAnnotation","annotation");
     s = Utils.replaceStringChar(s,"celldesigner_","celldesigner:");
     s = Utils.replaceStringCount(s,"<![CDATA[","");
     s = Utils.replaceStringCount(s,"]]>","");
     s = Utils.replaceStringCount(s,"<math>","<math xmlns=\"http://www.w3.org/1998/Math/MathML\">");
     s = Utils.replaceStringCount(s,"<html>","<html xmlns=\"http://www.w3.org/1999/xhtml\">");
     s = Utils.replaceStringCount(s,"&lt;body xmlns=\"http://www.w3.org/1999/xhtml\">\n","");
     s = Utils.replaceStringCount(s,"&lt;body xmlns=\"http://www.w3.org/1999/xhtml\">","");
     s = Utils.replaceStringCount(s,"<body xmlns=\"http://www.w3.org/1999/xhtml\">","");
     s = Utils.replaceStringCount(s,"<body xmlns=\"http://www.w3.org/1999/xhtml\">\n","");
     s = Utils.replaceStringCount(s,"<body xmlns=\"http://www.w3.org/1999/xh","");
     s = Utils.replaceStringCount(s, "<celldesigner:notes xsi:nil=\"true\"/>", "");
     s = Utils.replaceStringCount(s, "<notes xsi:nil=\"true\"/>", "");
     s = Utils.replaceStringCount(s,"<sbml xmlns=\"http://www.sbml.org/sbml/level2\">","<sbml xmlns=\"http://www.sbml.org/sbml/level2/version4\" xmlns:celldesigner=\"http://www.sbml.org/2001/ns/celldesigner\" level=\"2\" version=\"4\">");*/

     // We have to do these replacements sequentially because other depend on them
     s = s.replaceAll("<&","^^^");
     
     // Now the fast replacement mode
     HashMap<String, String> replaceMap = new HashMap<String, String>();
     replaceMap.put("^^^","<");
     replaceMap.put("celldesigner_","celldesigner:");
     replaceMap.put("&lt;&amp;","<");
     replaceMap.put("xmlns=\"http://www.sbml.org/2001/ns/celldesigner\"","xmlns=\"http://www.sbml.org/sbml/level2\"");
     replaceMap.put("SpeciesAnnotation","annotation");
     replaceMap.put("ReactionAnnotation","annotation");
     replaceMap.put("<![CDATA[","");
     replaceMap.put("]]>","");
     replaceMap.put("<math>","<math xmlns=\"http://www.w3.org/1998/Math/MathML\">");
     replaceMap.put("<html>","<html xmlns=\"http://www.w3.org/1999/xhtml\">");
     replaceMap.put("&lt;body xmlns=\"http://www.w3.org/1999/xhtml\">\n","");
     replaceMap.put("&lt;body xmlns=\"http://www.w3.org/1999/xhtml\">","");
     replaceMap.put("<body xmlns=\"http://www.w3.org/1999/xhtml\">","");
     replaceMap.put("<body xmlns=\"http://www.w3.org/1999/xhtml\">\n","");
     replaceMap.put("<body xmlns=\"http://www.w3.org/1999/xh","");
     replaceMap.put("<notes xsi:nil=\"true\"/>", "");
     replaceMap.put("<sbml xmlns=\"http://www.sbml.org/sbml/level2\">","<sbml xmlns=\"http://www.sbml.org/sbml/level2/version4\" xmlns:celldesigner=\"http://www.sbml.org/2001/ns/celldesigner\" level=\"2\" version=\"4\">");
     
     s = Utils.replaceByList(s, replaceMap);
     
     s = s.replaceAll("<celldesigner:notes xsi:nil=\"true\"/>", "");
     
     fw.write(s);
     fw.close();
   }catch(Exception e){
     e.printStackTrace();
   }
 }
  
 /**
  * Loads org.sbml.x2001.ns.celldesigner.SbmlDocument from file
  * @param fn
  * @return
  */
 public static org.sbml.x2001.ns.celldesigner.SbmlDocument loadCellDesigner(String fn){
   org.sbml.x2001.ns.celldesigner.SbmlDocument sbmlDoc = null;
   try{
   String text = Utils.loadString(fn);
   
   // Here we have to make a correction for the version 4.1 of CellDesigner
   /*
   text = text.replaceAll("<celldesigner:extension>", "");
   text = text.replaceAll("</celldesigner:extension>", "");
   text = text.replaceAll("/version4", "");
   text = text.replaceAll("version=\"4\"", "version=\"1\"");
   text = text.replaceAll("celldesigner:", "celldesigner_");
   text = Utils.replaceString(text,"\"http://www.sbml.org/sbml/level2\"","\"http://www.sbml.org/2001/ns/celldesigner\"");*/
   
   HashMap<String, String> replaceMap = new HashMap<String, String>(); 
   replaceMap.put("<celldesigner:extension>", "");
   replaceMap.put("</celldesigner:extension>", "");
   text = Utils.replaceByList(text, replaceMap);
   replaceMap.put("/version4", "");
   replaceMap.put("version=\"4\"", "version=\"1\"");		   
   replaceMap.put("celldesigner:", "celldesigner_");   
   text = Utils.replaceByList(text, replaceMap);
   
   text = text.replaceAll("\"http://www.sbml.org/sbml/level2\"","\"http://www.sbml.org/2001/ns/celldesigner\"");
		   
   StringReader st = new StringReader(text);
   sbmlDoc = org.sbml.x2001.ns.celldesigner.SbmlDocument.Factory.parse(st);
   
   }catch(Exception e){
     e.printStackTrace();
   }
   return sbmlDoc;
 }
 
 
 /**
  * Load org.sbml.x2001.ns.celldesigner.SbmlDocument from String
  * 
  * 
  * @param text String containing Cell Designer xml text 
  * @return sbmlDoc object
  * 
  */
 public static org.sbml.x2001.ns.celldesigner.SbmlDocument loadCellDesignerFromText(String text){
	 org.sbml.x2001.ns.celldesigner.SbmlDocument sbmlDoc = null;
	 try {

		 // Here we have to make a correction for the version 4.1 of CellDesigner
		 /*text = text.replaceAll("<celldesigner:extension>", "");
		 text = text.replaceAll("</celldesigner:extension>", "");
		 text = text.replaceAll("/version4", "");
		 text = text.replaceAll("version=\"4\"", "version=\"1\"");
		 text = text.replaceAll("celldesigner:", "celldesigner_");
		 text = Utils.replaceString(text,"\"http://www.sbml.org/sbml/level2\"","\"http://www.sbml.org/2001/ns/celldesigner\"");
		 */
		   HashMap<String, String> replaceMap = new HashMap<String, String>(); 
		   replaceMap.put("<celldesigner:extension>", "");
		   replaceMap.put("</celldesigner:extension>", "");
		   text = Utils.replaceByList(text, replaceMap);
		   replaceMap.put("/version4", "");
		   replaceMap.put("version=\"4\"", "version=\"1\"");		   
		   replaceMap.put("celldesigner:", "celldesigner_");   
		   text = Utils.replaceByList(text, replaceMap);
		   
		   text = text.replaceAll("\"http://www.sbml.org/sbml/level2\"","\"http://www.sbml.org/2001/ns/celldesigner\"");
		   
		 StringReader st = new StringReader(text);   
		 sbmlDoc = org.sbml.x2001.ns.celldesigner.SbmlDocument.Factory.parse(st);
	 } catch(Exception e) {
		 e.printStackTrace();
	 }
	 return sbmlDoc;
 }
 
 
 	/**
 	 * Fills entities map
 	 * @param sbmlDoc
 	 * @return
 	 */
	public static HashMap getEntities(SbmlDocument sbmlDoc){
		  HashMap ent = new HashMap();
		  // proteins
		  if(sbmlDoc.getSbml().getModel().getAnnotation()!=null){
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
		    CelldesignerProteinDocument.CelldesignerProtein prot = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
		    ent.put(prot.getId(),prot);
		    //System.out.println(prot.getId()+"\t"+Utils.getText(prot.getName()));
		  }
		  // genes
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
		    CelldesignerGeneDocument.CelldesignerGene gene = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
		    ent.put(gene.getId(),gene);
		  }
		  // rnas
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
		    CelldesignerRNADocument.CelldesignerRNA rna = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
		    ent.put(rna.getId(),rna);
		  }
		  // antisense_rnas
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().sizeOfCelldesignerAntisenseRNAArray();i++){
		    CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA arna = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
		    ent.put(arna.getId(),arna);
		  }
		  // complexes
		  //for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
		  //  CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias compl = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
		  //  ent.put(compl.getId(),compl);
		  //}
		  // all species
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
		    SpeciesDocument.Species sp = sbmlDoc.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
		    ent.put(sp.getId(),sp);
		    //System.out.println(sp.getId());
		  }
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray().length;i++){
		    CelldesignerSpeciesDocument.CelldesignerSpecies sp = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
		    ent.put(sp.getId(),sp);
		  }
		  }
		  return ent;
		}
	
	public static HashMap<String,String> getEntitiesNames(SbmlDocument sbmlDoc){
		  HashMap<String,String> ent = new HashMap<String,String>();
		  // proteins
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
		    CelldesignerProteinDocument.CelldesignerProtein prot = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
		    ent.put(prot.getId(),Utils.getValue(prot.getName()));
		    //System.out.println(prot.getId()+"\t"+Utils.getText(prot.getName()));
		  }
		  // genes
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
		    CelldesignerGeneDocument.CelldesignerGene gene = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
		    ent.put(gene.getId(),gene.getName());
		  }
		  // rnas
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
		    CelldesignerRNADocument.CelldesignerRNA rna = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
		    ent.put(rna.getId(),rna.getName());
		  }
		  // antisense_rnas
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().sizeOfCelldesignerAntisenseRNAArray();i++){
		    CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA arna = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
		    ent.put(arna.getId(),arna.getName());
		  }

		  return ent;
		}
	

		public static HashMap getAllObjectsHash(SbmlDocument sbmlDoc){
		  HashMap ent = new HashMap();
		  // proteins
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
		    CelldesignerProteinDocument.CelldesignerProtein prot = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
		    ent.put(prot.getId(),prot);
		  }
		  // genes
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
		    CelldesignerGeneDocument.CelldesignerGene gene = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
		    ent.put(gene.getId(),gene);
		  }
		  // rnas
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
		    CelldesignerRNADocument.CelldesignerRNA rna = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
		    ent.put(rna.getId(),rna);
		  }
		  // antisense_rnas
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().sizeOfCelldesignerAntisenseRNAArray();i++){
		    CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA arna = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
		    ent.put(arna.getId(),arna);
		  }
		  // complexes
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().sizeOfCelldesignerComplexSpeciesAliasArray();i++){
		    CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias compl = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
		    ent.put(compl.getId(),compl);
		  }
		  // all species
		  if(sbmlDoc.getSbml().getModel().getListOfSpecies()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
		    SpeciesDocument.Species sp = sbmlDoc.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
		    ent.put(sp.getId(),sp);
		  }
		  // speciesAliases
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;i++){
		    CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias sp = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
		    ent.put(sp.getId(),sp);
		  }
		  // complexSpeciesAliases
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
		    CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias sp = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
		    ent.put(sp.getId(),sp);
		  }
		  // includedSpecies
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray().length;i++){
		    CelldesignerSpeciesDocument.CelldesignerSpecies sp = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray(i);
		    ent.put(sp.getId(),sp);
		  }
		  // reactions
		  if(sbmlDoc.getSbml().getModel().getListOfReactions()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray().length;i++){
		    ReactionDocument.Reaction r = sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray(i);
		    ent.put(r.getId(),r);
		  }
		  // compartments
		  if(sbmlDoc.getSbml().getModel().getListOfCompartments()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfCompartments().getCompartmentArray().length;i++){
		    CompartmentDocument.Compartment comp = sbmlDoc.getSbml().getModel().getListOfCompartments().getCompartmentArray(i);
		    ent.put(comp.getId(),comp);
		  }
		  // compartmentAliases
		  if(sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray().length;i++){
		    CelldesignerCompartmentAliasDocument.CelldesignerCompartmentAlias comp = sbmlDoc.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray(i);
		    ent.put(comp.getId(),comp);
		  }
		  // Events
		  if(sbmlDoc.getSbml().getModel().getListOfEvents()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfEvents().getEventArray().length;i++){
		    EventDocument.Event ev = sbmlDoc.getSbml().getModel().getListOfEvents().getEventArray(i);
		    ent.put(ev.getId(),ev);
		  }
		  // Function definition
		  if(sbmlDoc.getSbml().getModel().getListOfFunctionDefinitions()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfFunctionDefinitions().getFunctionDefinitionArray().length;i++){
		    FunctionDefinitionDocument.FunctionDefinition fd = sbmlDoc.getSbml().getModel().getListOfFunctionDefinitions().getFunctionDefinitionArray(i);
		    ent.put(fd.getId(),fd);
		  }
		  // UnitDefinitions
		  if(sbmlDoc.getSbml().getModel().getListOfUnitDefinitions()!=null)
		  for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfUnitDefinitions().getUnitDefinitionArray().length;i++){
		    UnitDefinitionDocument.UnitDefinition ud = sbmlDoc.getSbml().getModel().getListOfUnitDefinitions().getUnitDefinitionArray(i);
		    ent.put(ud.getId(),ud);
		  }

		  return ent;
		}
 

}