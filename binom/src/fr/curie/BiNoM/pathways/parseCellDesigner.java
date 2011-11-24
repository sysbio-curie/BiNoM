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

import java.io.*;
import java.util.*;

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.*;

/**
 * Utility class for listing some CellDesigner information as a readable text  
 */
public class parseCellDesigner {

  public static void main(String[] args) {

    try{

      SbmlDocument sbml = null;

      //String prefix = "c:/datas/nfkb/Nfkb_alain0707_pl";
      //String prefix = "Nfkb_simplest_plus";
      //String prefix = "c:/datas/nfkb/nfkb_alain0707onech";
      //String prefix = "m-phase2";
      //String prefix = "rb-e2f";
      String prefix = "egfr";
      //String prefix = "toll";

      if(args.length>0){
        prefix = args[0];
        if(prefix.toLowerCase().endsWith(".xml"))
          prefix = prefix.substring(0,prefix.length()-4);
      }

      String fn = prefix+".xml";

      sbml = CellDesigner.loadCellDesigner(fn);

      CellDesigner.entities = CellDesigner.getEntities(sbml);

      printPhenotypeList(sbml.getSbml());
      printGeneList(sbml.getSbml());
      printRNAList(sbml.getSbml());
      printAntisenseRNAList(sbml.getSbml());
      printSmallMoleculeList(sbml.getSbml());
      printProteinsList(sbml.getSbml(),true);
      ptintComplexList(sbml.getSbml());
      printSpeciesList(sbml.getSbml());
      printReactionList(sbml.getSbml(),true);

      /*String ss = "";
      Vector v = getIncludedSpeciesInComplex(sbml,"s18");
      for(int k=0;k<v.size();k++){
        Celldesigner_species isp = (Celldesigner_species)v.elementAt(k);
        ss+=getNameOfIncludedSpecies(sbml,isp);
        if(k<v.size()-1)
          ss+="/";
      }
      System.out.println(ss);*/


    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public static void printProteinsList(SbmlDocument.Sbml sbml, boolean realNames){
    AnnotationDocument.Annotation an = sbml.getModel().getAnnotation();
    for(int i=0;i<an.getCelldesignerListOfProteins().getCelldesignerProteinArray().length;i++){
      CelldesignerProteinDocument.CelldesignerProtein prot = (CelldesignerProteinDocument.CelldesignerProtein)an.getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
      System.out.println("Protein "+(i+1)+": "+prot.getName().getStringValue());
    }
  }

  public static HashMap getCompartmentHash(SbmlDocument.Sbml sbml){
    HashMap hm = new HashMap();
    for(int i=0;i<sbml.getModel().getListOfCompartments().getCompartmentArray().length;i++){
      CompartmentDocument.Compartment cp = sbml.getModel().getListOfCompartments().getCompartmentArray(i);
      hm.put(cp.getId(),cp);
    }
    return hm;
  }

  //public static HashMap getSpeciesHash(){
  //  sbml.getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesigner_speciesAliass().size()
  //}

  public static void printSpeciesList(SbmlDocument.Sbml sbml){
    HashMap hm = getCompartmentHash(sbml);
    for(int i=0;i<sbml.getModel().getListOfSpecies().getSpeciesArray().length;i++){
      SpeciesDocument.Species sp = sbml.getModel().getListOfSpecies().getSpeciesArray(i);
      System.out.print("Species "+(i+1)+":");
      AnnotationDocument.Annotation an = sp.getAnnotation();
      if(an!=null)
        System.out.print("\t"+Utils.getValue(an.getCelldesignerSpeciesIdentity().getCelldesignerClass()));
      else
        System.out.print("\t_");
      System.out.print("\t"+sp.getId()+"\t"+sp.getName().getStringValue());
      String cn = ((CompartmentDocument.Compartment)hm.get(sp.getCompartment())).getName().getStringValue();
      if(!sp.getCompartment().equals("default"))
        System.out.print("\t"+cn);
      else
        System.out.print("\t_");
      String ss = null;
      CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity ident = sp.getAnnotation().getCelldesignerSpeciesIdentity();
      if(ident!=null){
      CelldesignerStateDocument.CelldesignerState state = ident.getCelldesignerState();
      if(state!=null){
      CelldesignerListOfModificationsDocument.CelldesignerListOfModifications lmodifs = ident.getCelldesignerState().getCelldesignerListOfModifications();
      if(lmodifs!=null){
      CelldesignerModificationDocument.CelldesignerModification modifs[] = lmodifs.getCelldesignerModificationArray();
      for(int j=0;j<modifs.length;j++){
        CelldesignerModificationDocument.CelldesignerModification cm = modifs[j];
        System.out.print("\t"+cm.getResidue()+"_"+cm.getState().getStringValue());
      }
      }
      }
      }
      //if(sp.getId().equals("s17"))
      //  System.out.println(sp.getId());

      System.out.println();
    }
  }

  public static String reactionString(SbmlDocument.Sbml sbml, String id, boolean realNames){
	  String reactionString = "";
	    for(int i=0;i<sbml.getModel().getListOfReactions().getReactionArray().length;i++){
	        ReactionDocument.Reaction r = sbml.getModel().getListOfReactions().getReactionArray(i);
	        if(r.getId().equals(id)){
	        ListOfModifiersDocument.ListOfModifiers lm = r.getListOfModifiers();
	        //System.out.print(""+(i+1)+":\t"+r.getId()+":\t");
	        for(int j=0;j<r.getListOfReactants().getSpeciesReferenceArray().length;j++){
	          String s = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
	          if(realNames){
	            s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,s,true,true);
	          }
	          if(s!=null){
	          reactionString+=s;
	          if(j<r.getListOfReactants().getSpeciesReferenceArray().length-1) reactionString+="+";
	          }
	        }
	        if(lm!=null){
	        reactionString+="+";
	        for(int j=0;j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length;j++){
	          String s = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
	          if(realNames){
	            s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,s,true,true);
	          }
	          if(s!=null){
	          reactionString+=s;
	          if(j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length-1) reactionString+="+";
	          }
	        }}
	        reactionString+=" -> ";
	        for(int j=0;j<r.getListOfProducts().getSpeciesReferenceArray().length;j++){
	          String s = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
	          if(realNames){
	            s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,s,true,true);
	          }
	          if(s!=null){
	          reactionString+=s;
	          if(j<r.getListOfProducts().getSpeciesReferenceArray().length-1) reactionString+="+";
	          }
	        }
	        if(lm!=null){
	        reactionString+="+";
	        for(int j=0;j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length;j++){
	          String s = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
	          if(realNames){
	            s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,s,true,true);
	          }
	          if(s!=null){
	          reactionString+=s;
	          if(j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length-1) reactionString+="+";
	          }
	        }}}
	      }
	  return reactionString;
  }
  
  public static void printReactionList(SbmlDocument.Sbml sbml, boolean realNames){
    for(int i=0;i<sbml.getModel().getListOfReactions().getReactionArray().length;i++){
      ReactionDocument.Reaction r = sbml.getModel().getListOfReactions().getReactionArray(i);
      ListOfModifiersDocument.ListOfModifiers lm = r.getListOfModifiers();
      System.out.print(""+(i+1)+":\t"+r.getId()+":\t");
      for(int j=0;j<r.getListOfReactants().getSpeciesReferenceArray().length;j++){
        String s = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
        if(realNames){
          s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,s,true,true);
        }
        if(s!=null){
        System.out.print(s);
        if(j<r.getListOfReactants().getSpeciesReferenceArray().length-1) System.out.print("+");
        }
      }
      if(lm!=null){
      System.out.print("+");
      for(int j=0;j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length;j++){
        String s = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
        if(realNames){
          s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,s,true,true);
        }
        if(s!=null){
        System.out.print(s);
        if(j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length-1) System.out.print("+");
        }
      }}
      System.out.print(" -> ");
      for(int j=0;j<r.getListOfProducts().getSpeciesReferenceArray().length;j++){
        String s = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
        if(realNames){
          s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,s,true,true);
        }
        if(s!=null){
        System.out.print(s);
        if(j<r.getListOfProducts().getSpeciesReferenceArray().length-1) System.out.print("+");
        }
      }
      if(lm!=null){
      System.out.print("+");
      for(int j=0;j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length;j++){
        String s = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
        if(realNames){
          s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,s,true,true);
        }
        if(s!=null){
        System.out.print(s);
        if(j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length-1) System.out.print("+");
        }
      }}
      System.out.println("");
    }
  }

  public static void printPhenotypeList(SbmlDocument.Sbml sbml){
    //Phenotypes are treated as proteins
    Vector species = CellDesignerToBioPAXConverter.getSpecies(sbml);
    int k = 0;
    for(int i=0;i<species.size();i++){
      SpeciesDocument.Species sp = (SpeciesDocument.Species)species.elementAt(i);
      if(sp.getAnnotation().getCelldesignerSpeciesIdentity()!=null){
        String cl = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
        if(cl.equals("PHENOTYPE")){
          System.out.println("Phenotype "+(++k)+": "+sp.getId()+" - "+sp.getName().getStringValue());
          }
      }
    }
  }

  public static void printGeneList(SbmlDocument.Sbml sbml){
    int k = 0;
    for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray().length;i++){
      CelldesignerGeneDocument.CelldesignerGene gene = sbml.getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
      System.out.println("Gene "+(++k)+": "+gene.getName());
    }
  }

  public static void printRNAList(SbmlDocument.Sbml sbml){
    int k = 0;
    for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray().length;i++){
      CelldesignerRNADocument.CelldesignerRNA RNA = sbml.getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
      System.out.println("RNA "+(++k)+": "+RNA.getName());
    }
  }

  public static void printAntisenseRNAList(SbmlDocument.Sbml sbml){
    int k=0;
    for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray().length;i++){
      CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA aRNA = sbml.getModel().getAnnotation().getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray(i);
      System.out.println("aRNA "+(++k)+": "+aRNA.getName());
    }
  }

  public static void printSmallMoleculeList(SbmlDocument.Sbml sbml){
    int k = 0;
    for(int i=0;i<sbml.getModel().getListOfSpecies().getSpeciesArray().length;i++){
      SpeciesDocument.Species sp = sbml.getModel().getListOfSpecies().getSpeciesArray(i);
      AnnotationDocument.Annotation an = sp.getAnnotation();
      if(an!=null){
        String cl = Utils.getValue(an.getCelldesignerSpeciesIdentity().getCelldesignerClass());
        if(cl.equals("ION")){
          System.out.println("ION "+(++k)+": "+sp.getName().getStringValue());
        }
        if(cl.equals("SIMPLE_MOLECULE")){
          System.out.println("SIMPLE_MOLECULE "+(++k)+": "+sp.getName().getStringValue());
        }
      }
    }
  }

  public static void ptintComplexList(SbmlDocument.Sbml sbml){
    for(int i=0;i<sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
      CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias csa = sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
      String name = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbml,csa.getSpecies(),true,true);
      System.out.println("Complex: ("+(i+1)+"/"+sbml.getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length+"):\t"+csa.getSpecies()+"\t"+name);
    }
  }



}