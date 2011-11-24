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

package fr.curie.BiNoM.pathways.utils;


import org.w3c.dom.*;
import org.sbml.sbml.level2.*;
import org.apache.xmlbeans.*;
import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.wrappers.*;

import simtec.flux.xml.mathml.*;
import simtec.flux.symbolicmath.*;


import java.io.*;
import java.util.*;


public class insertMassActionLaws {

  public static void main(String[] args) {

    try{
    //String prefix = "Nfkb_simplest_plus_p";
    String prefix = "c:/datas/binomtest/heiner/test1";

    SbmlDocument sbmlDoc = SBML.loadSBML(prefix+".xml");

    insertMassActionLaws(sbmlDoc);

    SBML.saveSBML(sbmlDoc,prefix+"ml.xml");

    }catch(Exception e){
      e.printStackTrace();
    }

  }


  public static void insertMassActionLaws(SbmlDocument sbmlDoc) throws Exception{
    HashMap species = getSpecies(sbmlDoc);
    for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
      Reaction r = sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray(i);
      if(r.getKineticLaw()==null){
        r.addNewKineticLaw().addNewMath();
        ExprTree tr = generateMassActionLaw(r,species);
        String frm = convertExprTreeToMathMLString(tr);
        //System.out.println(frm);
        System.out.println(tr.toString());
        /*ListOfParameters lp = r.getKineticLaw().addNewListOfParameters();
        if(frm.indexOf("kp_"+r.getId())>=0){
          Parameter par = lp.addNewParameter();
          par.setId("kp_"+r.getId());
          par.setName("kp_"+r.getId());
          par.setValue(0);
        }
        if(frm.indexOf("km_"+r.getId())>=0){
          Parameter par = lp.addNewParameter();
          par.setId("km_"+r.getId());
          par.setName("km_"+r.getId());
          par.setValue(0);
        }*/
        Model.ListOfParameters lp = sbmlDoc.getSbml().getModel().getListOfParameters();
        if(lp==null)
          lp = sbmlDoc.getSbml().getModel().addNewListOfParameters();
        if(frm.indexOf("kp_"+r.getId())>=0){
          Parameter par = lp.addNewParameter();
          par.setId("kp_"+r.getId());
          par.setName("kp_"+r.getId());
          par.setValue(0);
        }
        if(frm.indexOf("km_"+r.getId())>=0){
          Parameter par = lp.addNewParameter();
          par.setId("km_"+r.getId());
          par.setName("km_"+r.getId());
          par.setValue(0);
        }
        Utils.setValue(r.getKineticLaw().getMath(),frm);
      }
    }
  }

  public static ExprTree generateMassActionLaw(Reaction r, HashMap species) throws Exception{
    Vector ins = new Vector();
    Vector outs = new Vector();
    Vector inids = new Vector();

    System.out.println(r.getId());
    if(r.getId().equals("re3")){
      System.out.println();
    }

    ListOfModifierSpeciesReferences lm = r.getListOfModifiers();
    for(int j=0;j<r.getListOfReactants().sizeOfSpeciesReferenceArray();j++){
      String s = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
      inids.add(s);
      Species sp = (Species)species.get(s);
      if((sp.getName().indexOf("degraded")<0)&&(!sp.getName().equals(""))&&(!sp.getName().startsWith("null")))
            ins.add(new ExprTree(s));
    }
    if(lm!=null){
    for(int j=0;j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
      String s = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
      Species sp = (Species)species.get(s);
      if((sp.getName().indexOf("degraded")<0)&&(!sp.getName().equals(""))&&(!sp.getName().startsWith("null")))
            ins.add(new ExprTree(s));
    }}

    boolean productsInReactants = false;
    for(int j=0;j<r.getListOfProducts().sizeOfSpeciesReferenceArray();j++){
      String s = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
      if(inids.indexOf(s)>=0) productsInReactants = true;
    }

    // We do not insert the reverse constants for catalytic and self-catalytic reaction
    if((lm==null)&&(!productsInReactants))
    for(int j=0;j<r.getListOfProducts().sizeOfSpeciesReferenceArray();j++){
      String s = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
      Species sp = (Species)species.get(s);
      if((sp.getName().indexOf("degraded")<0)&&(!sp.getName().equals(""))&&(!sp.getName().startsWith("null")))
        outs.add(new ExprTree(s));
    }

    /*if(lm!=null){
    for(int j=0;j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
      String s = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
      Species sp = (Species)species.get(s);
      if((sp.getName().indexOf("degraded")<0)&&(!sp.getName().equals(""))&&(!sp.getName().startsWith("null")))
          outs.add(new ExprTree(s));
    }}*/

    ExprTree c = new ExprTree("kp_"+r.getId());
    for(int i=0;i<ins.size();i++){
      c = new ExprTree(ExprTree.OP_MUL,c,(ExprTree)ins.elementAt(i));
    }
    ExprTree cr = new ExprTree("km_"+r.getId());
    for(int i=0;i<outs.size();i++){
      cr = new ExprTree(ExprTree.OP_MUL,cr,(ExprTree)outs.elementAt(i));
    }
    ExprTree res = null;
    if((ins.size()>0)&&(outs.size()>0))
      res = new ExprTree(ExprTree.OP_SUB,c,cr);
    if(outs.size()==0)
      res = c;
    if(ins.size()==0)
      res = new ExprTree(ExprTree.OP_UMINUS,null,cr);
    return res;
  }

  public static HashMap getSpecies(SbmlDocument sbmlDoc) throws Exception{
    HashMap species = new HashMap();
    for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
      Species sp = sbmlDoc.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
      species.put(sp.getId(),sp);
    }
    return species;
  }

  public static String convertExprTreeToMathMLString(ExprTree t) throws Exception{
    MathMLDocument ml = new MathMLDocument();
    MathMLExpression me = ml.createExpression(t);
    MathMLDomWriterImplXercesDOM3 writer = new MathMLDomWriterImplXercesDOM3();
    ml.serializeToDom(writer,ml.MML_CONTENT);
    //String s = ml.serializeContentMathML(doc);
    String s = writer.writeToString();
    s = Utils.replaceString(s,"<math>","");
    s = Utils.replaceString(s,"</math>","");
    s = Utils.replaceString(s,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>","");
    s = Utils.replaceString(s,"<","^^^");
    s = Utils.replaceString(s,"^^^","<&");
    System.out.println(t.toString()+"\t"+s);
    return s;
  }

}