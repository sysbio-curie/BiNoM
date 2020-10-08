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


public class InsertCellDesignerParameters {
	
    static HashMap parIdName = new HashMap(); 
    static HashMap spIdName = new HashMap();

  public static void main(String[] args) {

    try{
    	
    	
        String path1 = "c:/docs/mypapers/jtheorbiol/simbiol/full/";
        String cdfile1 = path1+"M_14_30_41";
        SbmlDocument sbmlDoc1 = SBML.loadSBML(cdfile1+".xml");
        
        for(int i=0;i<sbmlDoc1.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
        	Species sp = sbmlDoc1.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
        	spIdName.put(sp.getId(), sp.getName());
        	sp.setId(sp.getName());
        }
        /*for(int i=0;i<sbmlDoc1.getSbml().getModel().getListOfParameters().sizeOfParameterArray();i++){
        	Parameter par = sbmlDoc1.getSbml().getModel().getListOfParameters().getParameterArray(i);
        	parIdName.put(par.getId(), par.getName());
        	par.setId(par.getName());
        }*/
        sbmlDoc1.getSbml().getModel().addNewListOfParameters();
        for(int i=0;i<sbmlDoc1.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
        	Reaction r = sbmlDoc1.getSbml().getModel().getListOfReactions().getReactionArray(i);
        	for(int j=0;j<r.getKineticLaw().getListOfParameters().sizeOfParameterArray();j++){
            	Parameter par = r.getKineticLaw().getListOfParameters().getParameterArray(j);
            	Parameter par1 = sbmlDoc1.getSbml().getModel().getListOfParameters().addNewParameter();
            	par1.setId(par.getName());
            	par1.setName(par.getName());
            	par1.setValue(par.getValue());
            	parIdName.put(par.getId(), par.getName());
            	//par.setId(par.getName());
        	}
        }
        for(int i=0;i<sbmlDoc1.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
        	Reaction r = sbmlDoc1.getSbml().getModel().getListOfReactions().getReactionArray(i);
        	r.setId("R"+(i+1));
        	String s = repl(r.getKineticLaw().getMath().xmlText());
        	s = Utils.replaceString(s, "<xml-fragment xmlns:celldesigner=\"http://www.sbml.org/2001/ns/celldesigner\">","");
        	s = Utils.replaceString(s, "<xml-fragment>", "");
        	s = Utils.replaceString(s, "<mat:apply xmlns:mat=\"http://www.w3.org/1998/Math/MathML\">", "");
        	s = Utils.replaceString(s, "mat:", "");
        	s = Utils.replaceString(s, "</xml-fragment>", "");
        	if(s.indexOf("</apply>")>=0)
        		s = "<apply>"+s;
        	System.out.println(s);
        	
        	//r.getKineticLaw().setMath(null);
        	//r.getKineticLaw().addNewMath();
        	Utils.setValue(r.getKineticLaw().getMath(), s);
        	
        	
        	if(r.getListOfReactants()!=null)
        	for(int k=0;k<r.getListOfReactants().sizeOfSpeciesReferenceArray();k++){
        		SpeciesReference sr = r.getListOfReactants().getSpeciesReferenceArray(k);
        		String ref = repl(sr.getSpecies());
        		sr.setSpecies(ref);
        	}
        	if(r.getListOfProducts()!=null)
        	for(int k=0;k<r.getListOfProducts().sizeOfSpeciesReferenceArray();k++){
        		SpeciesReference sr = r.getListOfProducts().getSpeciesReferenceArray(k);
        		String ss = sr.getSpecies();
        		String ref = repl(ss);
        		sr.setSpecies(ref);
        	}
        	if(r.getListOfModifiers()!=null)
        	for(int k=0;k<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();k++){
        		ModifierSpeciesReference sr = r.getListOfModifiers().getModifierSpeciesReferenceArray(k);
        		String ref = repl(sr.getSpecies());
        		sr.setSpecies(ref);
        	}        	
        	//System.out.println(s);
        }
        SBML.saveSBML(sbmlDoc1,cdfile1+"_mod.xml");
    	System.exit(0);
    	

    String path = "c:/docs/mypapers/jtheorbiol/figures/";
    //String cdfile = path+"xml/M_4_6_13_celldesigner1_compact";
    //String parfile = path+"M_4_6_13.txt";
    //String cdfile = path+"xml/M14_25_29_celldesigner1_compact";
    //String cdfile = path+"xml/M_5_8_15_celldesigner1_compact";
    String cdfile = path+"xml/M39_65_90_celldesigner3";
    String parfile = path+"xml_M_39_65_90_1.txt";
    String spnames = path+"speciesNames1.xls";
    
    Vector spnamesFrom = new Vector();
    Vector spnamesTo = new Vector();
    
    double NA = 6e23;
    double volume = 5e-13;
    double conv = 1e6/(NA*volume);
    double initialComplex = 0.06;
    
    LineNumberReader lr = new LineNumberReader(new FileReader(spnames));
    String s = null;
    while((s=lr.readLine())!=null){
    	StringTokenizer st = new StringTokenizer(s,"\t");
    	st.nextToken();
    	String to = st.nextToken();
    	st.nextToken(); st.nextToken();
    	String from = st.nextToken();
    	spnamesFrom.add(from.toLowerCase());
    	spnamesTo.add(to);
    }
    lr.close();

    SbmlDocument sbmlDoc = SBML.loadSBML(cdfile+".xml");
    
    if(sbmlDoc.getSbml().getModel().getListOfParameters()==null){
    	sbmlDoc.getSbml().getModel().addNewListOfParameters();
    }
    
    for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
    	Species sp = sbmlDoc.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
    	String id = sp.getId();
    	int k = spnamesTo.indexOf(id);
    	System.out.println(id+" "+k);
    	if(k>=0){
    		String rname = (String)spnamesFrom.get(k);
    		if(rname.equals("ikbp50p65")) sp.setInitialAmount(initialComplex);
    		if(rname.equals("ftax")) sp.setInitialAmount(100*conv);
    		if(rname.equals("ftay")) sp.setInitialAmount(100*conv);
    		if(rname.equals("ftaz")) sp.setInitialAmount(100*conv);
    		if(rname.equals("prop105rnap")) sp.setInitialAmount(2*conv);
    		if(rname.equals("prop65rnap")) sp.setInitialAmount(2*conv);
    		if(rname.equals("proikbarnap")) sp.setInitialAmount(2*conv);
    	}
    }
    

    LineNumberReader ln = new LineNumberReader(new FileReader(parfile));
    s = null;
    while((s=ln.readLine())!=null){
    	StringTokenizer st = new StringTokenizer(s,"\t,");
    	String rid = st.nextToken();
    	//System.out.println(rid);
    	Reaction reaction = null;
    	for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray().length;i++){
    		Reaction rr = sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray(i);
    		if(rr.getId().equals(rid)){
    			reaction = rr; break;
    		}
    	}
    	if(reaction==null){
    		System.out.println("ERROR: reaction "+rid+" not found");
    	}else{
    		String rstring = st.nextToken();
    		String rateLaw = st.nextToken();
    		String pars = st.nextToken();
    		StringTokenizer st1 = new StringTokenizer(pars," "); 
    		while(st1.hasMoreTokens()){
    			String par = st1.nextToken();
    			int k = par.indexOf("=");
    			String parname = par.substring(0,k).trim();
    			String parvalue = par.substring(k+1).trim();
    			Parameter param = sbmlDoc.getSbml().getModel().getListOfParameters().addNewParameter();
    			param.setId(parname);
    			param.setValue(Double.parseDouble(parvalue));
    		}
    		
    		rateLaw = rateLaw.trim().substring(2);
    		rateLaw = correctKineticLaw(rateLaw, spnamesFrom, spnamesTo);
    		
    		String xml = createKineticLawXML(rateLaw);
    		
    		System.out.println(reaction.getId()+"\t"+rateLaw);
    		
    		if(xml!=null){
    			reaction.addNewKineticLaw().addNewMath();
    			Utils.setValue(reaction.getKineticLaw().getMath(),xml);
    		}
    	}
    	
    }

    SBML.saveSBML(sbmlDoc,cdfile+"_pars.xml");

    }catch(Exception e){
      e.printStackTrace();
    }

  }
  
  public static String correctKineticLaw(String s, Vector from, Vector to){
	  String res = s.toLowerCase();
	  for(int i=0;i<from.size();i++)
		  res = fr.curie.BiNoM.pathways.utils.Utils.replaceString(res, (String)from.get(i), (String)to.get(i));
	  return res;
  }
  
  public static String createKineticLawXML(String law) throws Exception{
	  String res = null;
	  if(law.indexOf("(")<0)if(law.indexOf("+")<0)if(law.indexOf("/")<0)if(law.indexOf("-")<0){
		  StringTokenizer st = new StringTokenizer(law,"*");
		  //res="<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><apply><times/>";
		  res="<apply>";
		  Vector tokens = new Vector();
		  while(st.hasMoreTokens())
			  tokens.add(st.nextToken());
		  if(tokens.size()>1)
			  res+="<times/>";
		  for(int i=0;i<tokens.size();i++){
			  res+="<ci>"+(String)tokens.get(i)+"</ci>";
		  }
		  //res+="</apply></math>";
		  res+="</apply>";
	  }
	  return res;
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
    //System.out.println(t.toString()+"\t"+s);
    return s;
  }
  
  public static String repl(String s){
	  String res = s;
	  Iterator it = parIdName.keySet().iterator();
	  while(it.hasNext()){
		 String id = (String)it.next();
		 String name = (String)parIdName.get(id);
		 res = Utils.replaceString(res, id, name);
	  }
	  it = spIdName.keySet().iterator();
	  while(it.hasNext()){
		 String id = (String)it.next();
		 String name = (String)spIdName.get(id);
		 if(!id.equals(name))
			 res = Utils.replaceString(res, id, name);
	  }
	  return res;
  }

}