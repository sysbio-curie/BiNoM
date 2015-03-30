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

import org.w3c.dom.*;
import org.sbml.sbml.level2.*;
import org.apache.xmlbeans.*;
//import simtec.flux.xml.mathml.*;
//import simtec.flux.symbolicmath.*;

import java.io.*;
import java.util.*;

import fr.curie.BiNoM.pathways.utils.*;

/**
 * Wrapper of 'pure' SBML represented by org.sbml.sbml.level2.SbmlDocument object 
 * @author Andrei Zinovyev
 *
 */
public class SBML {

  public static void main(String[] args) {

    try{

      /*ExprTree tra = new ExprTree("a");
      ExprTree trb = new ExprTree("b");
      ExprTree t = new ExprTree(ExprTree.OP_ADD,tra,trb);
      //System.out.println(t.toString());

      MathMLDocument ml = new MathMLDocument();
      MathMLExpression me = ml.createExpression(t);
      System.out.println(me.toString());

      MathMLDomWriter writer = new MathMLDomWriterImplXerces();
      ml.serializeToDom(writer,ml.MML_CONTENT);
      System.out.println(writer.writeToString());*/


    String prefix = "Nfkb_simplest_plus";
    org.sbml.sbml.level2.SbmlDocument sbmlDoc = SBML.loadSBML(prefix+".xml");

    for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
      Reaction r = sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray(i);
      //if(r.getId().equals("re1")){
        if(r.getKineticLaw()!=null){
          /*String math = r.getKineticLaw().toString();
          math = Utils.replaceString(math,"<xml-fragment xmlns:celldesigner=\"http://www.sbml.org/2001/ns/celldesigner\" xmlns:lev=\"http://www.sbml.org/sbml/level2\">","");
          math = Utils.replaceString(math,"</xml-fragment>","");
          System.out.println(math);
          NodeList nl = n.getChildNodes();
          for(int j=0;j<nl.getLength();j++){
             Node nn = nl.item(j);
             System.out.println(nn.getNodeName()+"\t"+nn.getNodeType()+"\t"+nn.getNodeValue());
             NodeList nnl = nn.getChildNodes();
             for(int k=0;k<nnl.getLength();k++){
               Node nnn = nnl.item(k);
               System.out.println("\t\t"+nnn.getNodeName()+"\t"+nnn.getNodeType()+"\t"+nnn.getNodeValue());
             }
          }*/
          /*Node n = r.getKineticLaw().getMath().getDomNode();
          ExprTree t1 = MathMLExpression.parseExpression(n.getChildNodes().item(1));
          System.out.println(t1.toString());*/
          /*MathField m = r.getKineticLaw().getMath();
          if(m!=null){
            Node n = r.getKineticLaw().getMath().getDomNode();
            ExprTree t1 = MathMLExpression.parseExpression(n);
            System.out.println(t.toString());
        }*/
      }
    }

    }catch(Exception e){
      e.printStackTrace();
    }



  }

  /**
   * Loads SbmlDocument from file
   * @param fn
   * @return
   */
  public static org.sbml.sbml.level2.SbmlDocument loadSBML(String fn){
	org.sbml.sbml.level2.SbmlDocument sbmlDoc = null;
    try{
    String filename = fn;
    LineNumberReader lr = new LineNumberReader(new FileReader(fn));
    String s = null;
    FileWriter fw = new FileWriter("temp.xml");
    while((s=lr.readLine())!=null){
      String sp = s;
      //String sp = Utils.replaceString(s,"celldesigner:","celldesigner_");
      //sp = Utils.replaceString(sp,"xmlns=\"http://www.sbml.org/sbml/level2\"","xmlns=\"http://www.sbml.org/2001/ns/celldesigner\"");
      fw.write(sp+"\r\n");
    }
    fw.close();
    sbmlDoc = org.sbml.sbml.level2.SbmlDocument.Factory.parse(new File("temp.xml"));
    }catch(Exception e){
      e.printStackTrace();
    }
    return sbmlDoc;
  }
  /**
   * Saves org.sbml.sbml.level2.SbmlDocument to file
   * @param sbml
   * @param fn
   */
  public static void saveSBML(org.sbml.sbml.level2.SbmlDocument sbml, String fn){
   try{
     FileWriter fw = new FileWriter(fn);
     String s = sbml.toString();
     //s = Utils.replaceString(s,"xmlns=\"http://www.sbml.org/2001/ns/celldesigner\"","xmlns=\"http://www.sbml.org/sbml/level2\" xmlns:celldesigner=\"http://www.sbml.org/2001/ns/celldesigner\"");
     s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n"+s;
     s = Utils.replaceString(s,"xmlns=\"http://www.sbml.org/2001/ns/celldesigner\"","xmlns=\"http://www.sbml.org/sbml/level2\"");
     s = Utils.replaceString(s,"SpeciesAnnotation","annotation");
     s = Utils.replaceString(s,"ReactionAnnotation","annotation");
     s = Utils.replaceString(s,"<html>","<html xmlns=\"http://www.w3.org/1999/xhtml\">");
     System.out.println("Substituting... "+"celldesigner_");
     s = Utils.replaceString(s,"celldesigner_","celldesigner:");
     System.out.println("Substituting... "+"&lt;&amp;");
     s = Utils.replaceString(s,"&lt;&amp;","<");
     System.out.println("Substituting... "+"<![CDATA[");
     s = Utils.replaceString(s,"<![CDATA[","");
     System.out.println("Substituting... "+"]]>");
     s = Utils.replaceString(s,"]]>","");
     System.out.println("Substituting... "+"<&");
     s = Utils.replaceString(s,"<&","<");
     System.out.println("Substituting... "+"<mat:math>");
     s = Utils.replaceString(s,"<mat:math>","<math>");
     System.out.println("Substituting... "+"</mat:math>");
     s = Utils.replaceString(s,"</mat:math>","</math>");
     System.out.println("Substituting... "+"<math>");
     s = Utils.replaceString(s,"<math>","<math xmlns=\"http://www.w3.org/1998/Math/MathML\">");



     fw.write(s);
     fw.close();
   }catch(Exception e){
     e.printStackTrace();
   }
 }


}