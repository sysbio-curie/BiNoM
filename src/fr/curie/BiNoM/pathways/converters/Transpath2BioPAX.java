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

package fr.curie.BiNoM.pathways.converters;

import java.io.*;
import java.util.*;

import com.biobaseInternational.NetworkDocument;

import fr.curie.BiNoM.pathways.wrappers.*;

public class Transpath2BioPAX {
  public static void main(String[] args) {
    try{

      //String prefix = "c:/datas/BioBase/ver9/";
      //String prefix = "c:/datas/biobase/";
      String prefix = "/bioinfo/users/zinovyev/Datas/BioBase/ver9/";

      Transpath tp = new Transpath();
      tp.initializeModel();
      tp.biopax.makeCompartments();

      /*//tp.loadFromFile(prefix+"molecule.xml");
      tp.loadFromFile(prefix+"gene.xml");
      tp.createAccessionTable(prefix+"accgenes");
      System.exit(0);*/

      /*tp.speciesFilter = new Vector();
      tp.speciesFilter.add("human, Homo sapiens");
      tp.speciesFilter.add("mouse, Mus musculus");
      tp.speciesFilter.add("rat, Rattus norvegicus");
      tp.speciesFilter.add("taxonomic class Mammalia");*/

      /*System.out.println("Converting genes...");
      tp.loadFromFile(prefix+"gene.xml");
      tp.populateModel();
      tp.biopax.saveToFile(prefix+"gene.owl",tp.biopax.biopaxmodel);*/

      /*System.out.println("Loading annotations...");
      Transpath annotations = new Transpath();
      String annf = prefix+"annotate.xml";
      if(args.length>1){
        annf = args[1];
        System.out.println("found "+annf);
      }
      System.out.println("from "+annf);
      annotations.loadFromFile(annf);
      tp.annotations = annotations.network;
      
      System.out.println("Annotatioins loaded: "+tp.annotations.getNetwork().getAnnotateArray().length+" Annotate records");
      
      /*System.out.println("Converting genes...");
      String filename = prefix+"gene.xml";
      if(args.length>0)
        filename = args[0];
      System.out.println("from "+filename);
      tp.loadFromFile(filename);
      System.out.println("Genes loaded "+tp.network.getNetwork().getGeneArray().length+" Gene records");
      tp.populateModel();
      tp.biopax.saveToFile(prefix+"gene.owl",tp.biopax.biopaxmodel);*/

      /*System.out.println("Converting molecules...");
      String filename = prefix+"molecule.xml";
      //String filename = prefix+"test/test_molecules.xml";
      if(args.length>0)
        filename = args[0];
      System.out.println("from "+filename);
      tp.loadFromFile(filename);
      NetworkDocument molecules = tp.network;
      System.out.println("Molecules loaded "+tp.network.getNetwork().getMoleculeArray().length+" Molecule records");
      for(int i=1;i<10;i++){

          tp = new Transpath();
          tp.initializeModel();
          tp.network = molecules;
          tp.biopax.makeCompartments();
          tp.annotations = annotations.network;
    	  
    	  tp.moleculeStart = i*10000;
    	  tp.moleculeEnd = (i+1)*10000-1;
    	  tp.populateModel();
    	  tp.biopax.saveToFile(prefix+"molecule"+(i+1)+".owl",tp.biopax.biopaxmodel);
      }*/
      //tp.biopax.saveToFile(prefix+"test/test_molecules.owl",tp.biopax.biopaxmodel);*/

      /*System.out.println("Converting reactions...");

      System.out.println("Loading annotations...");
      Transpath annotations = new Transpath();
      String annf = prefix+"annotate.xml";      
      if(args.length>1)
        annf = args[1];
      System.out.println("from "+annf);
      annotations.loadFromFile(annf);
      tp.annotations = annotations.network;
      System.out.println("Annotatioins loaded: "+tp.annotations.getNetwork().getAnnotateArray().length+" Annotate records");

      System.out.println("Loading reactions...");
      String filename = prefix+"reaction.xml";
      if(args.length>0)
        filename = args[0];
      System.out.println("from "+filename);
      tp.loadFromFile(filename);
      System.out.println("Reactions loaded "+tp.network.getNetwork().getReactionArray().length+" Reaction records");      
      //tp.typeOfReactionExtraction = tp.SEMANTIC;
      //tp.typeOfReactionExtraction = tp.PATHWAY_STEP;
      tp.typeOfReactionExtraction = tp.MOLECULAR_EVIDENCE;
      System.out.println("Populating model...");
      tp.populateModel();
      tp.biopax.saveToFile(prefix+"reaction_evidence.owl",tp.biopax.biopaxmodel);
      //tp.biopax.saveToFile(prefix+"reaction_semantic.owl",tp.biopax.biopaxmodel);*/

      System.out.println("Loading references...");
      tp.loadFromFile(prefix+"reference.xml");
      System.out.println("References loaded: "+tp.network.getNetwork().getReferenceArray().length+" Reference records");
      tp.populateModel();
      tp.biopax.saveToFile(prefix+"reference.owl",tp.biopax.biopaxmodel);

      /*System.out.println("Loading pathways...");
      tp.loadFromFile(prefix+"pathway.xml");
      tp.populateModel();
      tp.biopax.saveToFile(prefix+"pathway.owl",tp.biopax.biopaxmodel);*/


    }catch(Exception e){
      e.printStackTrace();
    }
  }
}