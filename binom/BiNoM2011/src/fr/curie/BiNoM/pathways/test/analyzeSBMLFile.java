package fr.curie.BiNoM.pathways.test;

import java.util.*;

import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.*;

import org.apache.xmlbeans.*;
//import org.sbml.sbml.level2.*;
import org.sbml.x2001.ns.celldesigner.*;


public class analyzeSBMLFile {
  public static void main(String[] args) {

    /*String fn = "c:/datas/calzone/test/droso.xml";

    SbmlDocument sb = SBML.loadSBML(fn);

    int i=0;
    while(i<sb.getSbml().getModel().getListOfReactions().sizeOfReactionArray()){
      Reaction r = sb.getSbml().getModel().getListOfReactions().getReactionArray(i);
      if((r.getListOfProducts()==null)||(r.getListOfReactants()==null)){
        sb.getSbml().getModel().getListOfReactions().removeReaction(i);
      }else i++;
    }

    SBML.saveSBML(sb,"c:/datas/calzone/test/droso1.xml");*/

    String fn = "c:/datas/calzone/problem/bad_test.xml";
    String fnbase = "c:/datas/calzone/problem/rb-e2f-412.xml";
    SbmlDocument sb = CellDesigner.loadCellDesigner(fn);
    SbmlDocument sbbase = CellDesigner.loadCellDesigner(fnbase);

    CellDesignerToCytoscapeConverter.checkAndModifySpeciesIDs(sb,sbbase);

    //CellDesigner.saveCellDesigner(sb,"c:/datas/calzone/problem/bad3.xml");

  }

}