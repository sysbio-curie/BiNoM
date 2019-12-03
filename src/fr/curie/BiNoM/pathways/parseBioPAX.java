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


package fr.curie.BiNoM.pathways;

/**
 * <p>BiNoM Description: Various tools for managing and analyzing biological pathways</p>
 * <p>Copyright: Copyright (c) 2005-2006</p>
 * <p>Company: Bioinformatics Service of Institute of Curie, Paris, France (http://bioinfo.curie.fr)</p>
 * @author Andrei Zinovyev http://www.ihes.fr/~zinovyev
 * @author Eric Viara
 * @version alpha
 */

import java.io.*;
import java.util.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.shared.*;

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.biopax.*;

/**
 * Utility class for listing some BioPAX information as a readable text  
 */
public class parseBioPAX extends BioPAXToSBMLConverter{
  public static void main(String[] args) {

     try{
     //String prefix = "Notch";
     //String prefix = "Nfkb_simplest_plus";
     String prefix = "c:/datas/binomtest/Curated_v1_0";
     if(args.length>0){
       prefix = args[0];
       if(prefix.toLowerCase().endsWith(".owl"))
         prefix = prefix.substring(0,prefix.length()-4);
     }
     parseBioPAX pb = new parseBioPAX();
     pb.biopax.model = ModelFactory.createDefaultModel();
     pb.biopax.model.read(new FileInputStream(prefix+".owl"),"");
     pb.populateSbml();

     System.out.println("--------------------------------------------------");

     // Listing entities
     List lst = biopax_DASH_level2_DOT_owlFactory.getAllprotein(pb.biopax.model);
     Iterator it = lst.iterator();
     int k=1;
     while(it.hasNext()){
       protein pe = (protein)it.next();
       System.out.println("Protein "+(k++)+": "+pe.getNAME());
     }
     lst = biopax_DASH_level2_DOT_owlFactory.getAlldna(pb.biopax.model);
     it = lst.iterator();
     k = 1;
     while(it.hasNext()){
       dna pe = (dna)it.next();
       System.out.println("DNA "+(k++)+": "+pe.getNAME());
     }
     lst = biopax_DASH_level2_DOT_owlFactory.getAllrna(pb.biopax.model);
     it = lst.iterator();
     k = 1;
     while(it.hasNext()){
       rna pe = (rna)it.next();
       System.out.println("RNA "+(k++)+": "+pe.getNAME());
     }
     lst = biopax_DASH_level2_DOT_owlFactory.getAllsmallMolecule(pb.biopax.model);
     it = lst.iterator();
     k = 1;
     while(it.hasNext()){
       smallMolecule pe = (smallMolecule)it.next();
       System.out.println("SMALLMOLECULE "+(k++)+": "+pe.getNAME());
     }
     lst = biopax_DASH_level2_DOT_owlFactory.getAllcomplex(pb.biopax.model);
     it = lst.iterator();
     k = 1;
     while(it.hasNext()){
       complex pe = (complex)it.next();
       System.out.println("COMPLEX "+(k++)+": "+pe.getNAME());
     }

     // Now print reactions
     for(int i=0;i<pb.sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray().length;i++){
           ReactionDocument.Reaction r = pb.sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray(i);
           System.out.print(""+(i+1)+":\t");
           String sLeft = "";
           String sRight = "";
           if(r.getListOfReactants()!=null)
           for(int j=0;j<r.getListOfReactants().getSpeciesReferenceArray().length;j++){
             String id = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
             BioPAXSpecies part = (BioPAXSpecies)pb.independentSpeciesIds.get(id);
             sLeft += part.name+"+";
           }
           if(r.getListOfProducts()!=null)
           for(int j=0;j<r.getListOfProducts().getSpeciesReferenceArray().length;j++){
             String id = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
             BioPAXSpecies part = (BioPAXSpecies)pb.independentSpeciesIds.get(id);
             sRight += part.name+"+";
           }
           if(r.getListOfModifiers()!=null)
           for(int j=0;j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length;j++){
             String id = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
             BioPAXSpecies part = (BioPAXSpecies)pb.independentSpeciesIds.get(id);
             sLeft += part.name+"+";
             sRight += part.name+"+";
           }
           if(sLeft.length()>0)
             sLeft = sLeft.substring(0,sLeft.length()-1);
           if(sRight.length()>0)
             sRight = sRight.substring(0,sRight.length()-1);
           System.out.println(sLeft+" -> "+sRight+"\t("+r.getId()+")");
     }


     }catch(Exception e){
       e.printStackTrace();
     }

  }
  
  public String reactionString(SbmlDocument.Sbml sbml, String _id, boolean realNames, boolean cutAmpersand){
	  String reactionString = "";
	     for(int i=0;i<sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray().length;i++){
	           ReactionDocument.Reaction r = sbmlDoc.getSbml().getModel().getListOfReactions().getReactionArray(i);
	           if(r.getId().equals(_id)){
	           String sLeft = "";
	           String sRight = "";
	           if(r.getListOfReactants()!=null)
	           for(int j=0;j<r.getListOfReactants().getSpeciesReferenceArray().length;j++){
	             String id = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
	             BioPAXSpecies part = (BioPAXSpecies)independentSpeciesIds.get(id);
	             sLeft += Utils.cutFinalAmpersand(part.name)+"+";
	           }
	           if(r.getListOfProducts()!=null)
	           for(int j=0;j<r.getListOfProducts().getSpeciesReferenceArray().length;j++){
	             String id = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
	             BioPAXSpecies part = (BioPAXSpecies)independentSpeciesIds.get(id);
	             sRight += Utils.cutFinalAmpersand(part.name)+"+";
	           }
	           if(r.getListOfModifiers()!=null)
	           for(int j=0;j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length;j++){
	             String id = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
	             BioPAXSpecies part = (BioPAXSpecies)independentSpeciesIds.get(id);
	             sLeft += Utils.cutFinalAmpersand(part.name)+"+";
	             sRight += Utils.cutFinalAmpersand(part.name)+"+";
	           }
	           if(sLeft.length()>0)
	             sLeft = sLeft.substring(0,sLeft.length()-1);
	           if(sRight.length()>0)
	             sRight = sRight.substring(0,sRight.length()-1);
	           reactionString = sLeft+" -> "+sRight;
	           }
	     }
	  return reactionString;
  }
  
}