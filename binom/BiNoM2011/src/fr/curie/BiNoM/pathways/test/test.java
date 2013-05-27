package fr.curie.BiNoM.pathways.test;

import java.io.*;
import java.util.*;
//import org.sbml.sbml.level2.*;
import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.utils.*;

public class test {
  public static void main(String[] args) {
    try{

      GraphDocument xgml = XGMML.loadFromXMGML("c:/datas/binomtest/biopax3/merged_master.xgmml");
      Graph gr = XGMML.convertXGMMLToGraph(xgml);
      XGMML.saveToXGMML(gr, "c:/datas/binomtest/biopax3/merged_master1.xgmml");
    	
      /*System.out.println("Loading sbmlex...");
      SbmlDocument sbmlex = CellDesigner.loadCellDesigner("c:/datas/calzone/problem7/gene0.xml");

      //CellDesigner.entities = CellDesigner.getEntities(sbmlex);
      //CelldesignerProteinDocument.CelldesignerProtein prot = (CelldesignerProteinDocument.CelldesignerProtein)CellDesigner.entities.get("s1093");
      //System.out.println(prot.getName());
      //String name1 = CellDesigner.convertSpeciesToName(sbmlex.getSbml(),"s1014",true,true,true);
      //String name2 = CellDesigner.convertSpeciesToName(sbmlex.getSbml(),"s1093",true,true,true);
      //System.out.println(name1+" "+name2);
      //HashMap IdMap = CellDesigner.MapSpeciesId(sbmlex);
      //System.out.println("Id for CSDA@default "+(String)IdMap.get("CSDA@default"));

      System.out.println("Loading sbmlin...");
      SbmlDocument sbmlin = CellDesigner.loadCellDesigner("c:/datas/calzone/problem7/rbe2f_0801.xml");
      //SbmlDocument sbmlbase = CellDesigner.loadCellDesigner("c:/datas/calzone/problem2/rb-e2f-412.xml");

      System.out.println("Checking...");
      //String errString = CellDesigner.checkAndModifySpeciesIDs(sbmlex,sbmlin);
      String errString = CellDesigner.checkAndModifyEntitiesIDs(sbmlex,sbmlin);
      System.out.println(errString);

      //System.out.println("Merging...");
      //CellDesigner.mergeCellDesignerFiles(sbmlex,sbmlin);

      System.out.println("Saving...");
      CellDesigner.saveCellDesigner(sbmlex,"c:/datas/calzone/problem7/gene0fixed.xml");*/
    	
      //String path = "c:/datas/simon/";
      //String path = "c:/datas/binomtest/";
      //String path = "C:/Datas/Basal/PARPCaseStudy/";
      //String path = "C:/Datas/Basal/DNARepairMap/";
    	String path = "c:/datas/mirnaproject/";

      //String name = "rb260907";
      //String name = "ERK_grieco_4.0.1";
      //String name = "100603";
      //String name = "CC_DNArepair_11_06_2010test";
      //String name = "test";
      //String name = "CC_DNArepair_11_06_2010test_t_test_Basal_Luminaux"; 
      //String name = "test_logic_gates";
      //String name = "modules";
    	String name = "normal_translation1_miRNA";
    	
      System.out.println("Loading sbmlex...");
      SbmlDocument sbmlex = CellDesigner.loadCellDesigner(path+name+".xml");

      ScaleSizes(sbmlex,0.70f,false);
      RemoveResidueNames(sbmlex);
      //RemoveNames(sbmlex);
      
      //ScaleSizes(sbmlex,0.60f,false);

      System.out.println("Saving...");
      CellDesigner.saveCellDesigner(sbmlex,path+name+"_small.xml");


    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public static void ScaleSizes(SbmlDocument sbml, float factor, boolean onlyDistances){
    for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;i++){
      CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
      float h = Float.parseFloat(csa.getCelldesignerBounds().getH());
      float w = Float.parseFloat(csa.getCelldesignerBounds().getW());
      float x = Float.parseFloat(csa.getCelldesignerBounds().getX());
      float y = Float.parseFloat(csa.getCelldesignerBounds().getY());
      if(!onlyDistances){
      csa.getCelldesignerBounds().setH(""+h*factor);
      csa.getCelldesignerBounds().setW(""+w*factor);
      }
      csa.getCelldesignerBounds().setX(""+x*factor);
      csa.getCelldesignerBounds().setY(""+y*factor);
    }
    for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
      CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias csa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
      float h = Float.parseFloat(csa.getCelldesignerBounds().getH());
      float w = Float.parseFloat(csa.getCelldesignerBounds().getW());
      float x = Float.parseFloat(csa.getCelldesignerBounds().getX());
      float y = Float.parseFloat(csa.getCelldesignerBounds().getY());
      if(!onlyDistances){      
      csa.getCelldesignerBounds().setH(""+h*factor);
      csa.getCelldesignerBounds().setW(""+w*factor);
      }
      csa.getCelldesignerBounds().setX(""+x*factor);
      csa.getCelldesignerBounds().setY(""+y*factor);
    }
    for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray().length;i++){
      CelldesignerCompartmentAliasDocument.CelldesignerCompartmentAlias csa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfCompartmentAliases().getCelldesignerCompartmentAliasArray(i);
      System.out.println("Compartment "+csa.getId());
      if(csa.getCelldesignerBounds()!=null){
    	  float x = Float.parseFloat(csa.getCelldesignerBounds().getX());
    	  float y = Float.parseFloat(csa.getCelldesignerBounds().getY());
    	  csa.getCelldesignerBounds().setX(""+x*factor);
    	  csa.getCelldesignerBounds().setY(""+y*factor);
    	  if(csa.getCelldesignerBounds().getH()!=null)if(csa.getCelldesignerBounds().getW()!=null){
    		  float h = Float.parseFloat(csa.getCelldesignerBounds().getH());
    		  float w = Float.parseFloat(csa.getCelldesignerBounds().getW());
    		  csa.getCelldesignerBounds().setH(""+h*factor);
    		  csa.getCelldesignerBounds().setW(""+w*factor);
    	  }
      }else{
    	  if(csa.getCelldesignerPoint()!=null){
        	  float x = Float.parseFloat(csa.getCelldesignerPoint().getX());
        	  float y = Float.parseFloat(csa.getCelldesignerPoint().getY());
        	  csa.getCelldesignerPoint().setX(""+x*factor);
        	  csa.getCelldesignerPoint().setY(""+y*factor);
    	  }
      }
    }
    for(int i=0;i<sbml.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
        ReactionDocument.Reaction r = sbml.getSbml().getModel().getListOfReactions().getReactionArray(i);
        if(r.getAnnotation().getCelldesignerListOfModification()!=null){
        	for(int j=0;j<r.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();j++){
        		CelldesignerModificationDocument.CelldesignerModification mod = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(j);
        		if(mod.getType().contains("BOOLEAN")){
        			String editPoints = Utils.getValue(mod.getEditPoints());
        			StringTokenizer st = new StringTokenizer(editPoints,","); 
        			float x = Float.parseFloat(st.nextToken());
        			float y = Float.parseFloat(st.nextToken());
        			XmlString xs = XmlString.Factory.newInstance();
        			xs.setStringValue(""+x*factor+","+y*factor);
        			mod.setEditPoints(xs);
        		}
        	}
        }
      }
  }

  public static void RemoveResidueNames(SbmlDocument sbml){
    for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray().length;i++){
      CelldesignerProteinDocument.CelldesignerProtein prot = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
      if(prot.getCelldesignerListOfModificationResidues()!=null){
        for(int j=0;j<prot.getCelldesignerListOfModificationResidues().getCelldesignerModificationResidueArray().length;j++){
          CelldesignerModificationResidueDocument.CelldesignerModificationResidue mr = prot.getCelldesignerListOfModificationResidues().getCelldesignerModificationResidueArray(j);
          mr.setName(null);
        }
      }
    }
  }
  
  public static void RemoveNames(SbmlDocument sbml){
	    for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
	      SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray()[i];
	      String type = Utils.getText(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
	      //System.out.print("Before "+Utils.getValue(sp.getName())+" ");
	      //sp.setName(XmlString.Factory.newInstance());
	      //System.out.println("after "+Utils.getValue(sp.getName()));
	      CelldesignerNameDocument.CelldesignerName cdn = CelldesignerNameDocument.CelldesignerName.Factory.newInstance();
	      //XmlString str = XmlString.Factory.newInstance();
	      //str.setStringValue("_");
	      //cdn.set(str);
	      //cdn.set(XmlString.Factory.newInstance());
	      if((!type.equals("SIMPLE_MOLECULE"))&&(!type.equals("ION"))&&(!type.equals("PHENOTYPE"))){
	    	  Utils.setValue(cdn, "...");
	      	  sp.getAnnotation().getCelldesignerSpeciesIdentity().setCelldesignerName(cdn);
	      }
	    }
	  }  

}