package fr.curie.BiNoM.pathways.utils;

/**
 * <p>Title: Pathway library</p>
 * <p>Description: Various tools for managing and analyzing biological pathways</p>
 * <p>Copyright: Copyright (c) 2005-2006</p>
 * <p>Company: Bioinformatics Service of Instiute of Curie, Paris, France (bioinfo.curie.fr)</p>
 * @author Dr. Andrei Zinovyev http://www.ihes.fr/~zinovyev
 * @version alpha
 */

import org.w3c.dom.*;
import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.wrappers.*;
import simtec.flux.xml.mathml.*;
import simtec.flux.symbolicmath.*;


public class prepareCellDesignerFile {

  public prepareCellDesignerFile() {
  }
  public static void main(String[] args) {
     try{

     String prefix = "Nfkb_simplest_plus";
     //String prefix = "c:/datas/nfkb/Nfkb_Alain0707";

     SbmlDocument sbmlDoc = CellDesigner.loadCellDesigner(prefix+".xml");

     CellDesigner.entities = CellDesigner.getEntities(sbmlDoc);

     // First, name species standartly
     renameSpecies(sbmlDoc);
     // Second, make notes for reactions
     makeReactionNotes(sbmlDoc);

     CellDesigner.saveCellDesigner(sbmlDoc,prefix+"_p.xml");

     }catch(Exception e){
       e.printStackTrace();
     }
  }

  public static void renameSpecies(SbmlDocument sbmlDoc){
    for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
      SpeciesDocument.Species sp = sbmlDoc.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
      String s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,sp.getId(),true,true);
      System.out.println(""+(i+1)+"/"+sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray()+":"+sp.getId()+": "+sp.getName().getStringValue()+" : "+CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,sp.getId(),true,true));
      Utils.setValue(sp.getName(),s);
    }
    /*for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
      SpeciesDocument.Species sp = sbmlDoc.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
      sp.setId("s"+(i+1));
    }*/
  }

  public static void makeReactionNotes(SbmlDocument sbmlDoc){
    for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
      ReactionDocument.Reaction r = sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray(i);
      String s = CellDesignerToCytoscapeConverter.getReactionString(r,sbmlDoc,true);
      System.out.println(""+(i+1)+"/"+sbmlDoc.getSbml().getModel().getListOfReactions().sizeOfReactionArray()+":"+r.getId()+": "+s);
      s = Utils.replaceString(s,"->","-");
      String not = "";
      if(r.getNotes()!=null)
        if(r.getNotes().getHtml()!=null)
          not = Utils.getText(r.getNotes().getHtml().getBody());
      if(r.getNotes()==null)
        r.addNewNotes();
      if(r.getNotes().getHtml()==null)
        r.getNotes().addNewHtml().addNewBody();
      Utils.setValue(r.getNotes().getHtml().getBody(),not+"\n"+s);
    }
    /*for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
      ReactionDocument.Reaction r = sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray(i);
      r.setId("r"+(i+1));
    }*/
  }

}