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


import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.utils.*;
import java.io.*;
import java.util.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.shared.*;

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

public class CellDesigner2BioPAX {

  public static void main(String[] args) {

    try{

     //String prefix = "M-phase2";
    //String prefix = "Nfkb_simplest_plus";
    //String prefix = "AB";
    //String prefix = "rb-e2f-2";
    //String prefix = "egfr";
    //String prefix = "test";
    //String prefix = "toll";
    //String prefix = "macrophage";
    //String prefix = "M-Phase2";
    //String prefix = "testrb";
    //String prefix = "c:/datas/aposys/ginsimbinom/work/apop_sbml";
    //String prefix = "c:/datas/calzone/test/phosphotest";
      //String prefix = "c:/datas/calzone/rbe2f_clean";
     //String prefix = "C:/Datas/Basal/220310/CC_DNArepair_22_03_2010_DNA stage_uniform";
    //String prefix = "C:/Datas/binomtest/M-Phase2_ver41";
    	//String prefix = "C:/Datas/binomtest/BioPAX3/simplest";
    	//String prefix = "C:/Datas/binomtest/BioPAX3/M-Phase2";
    	//String prefix = "C:/Datas/binomtest/BioPAX3/simplest";
    	//String prefix = "C:/Datas/binomtest/BioPAX3/modif1";
    	//String prefix = "C:/Datas/binomtest/BioPAX3/complex";
    	String prefix = "C:/Datas/binomtest/BioPAX3/dimer1";

    if(args.length>0){
      prefix = args[0];
      if(prefix.toLowerCase().endsWith(".xml"))
        prefix = prefix.substring(0,prefix.length()-4);
    }


    CellDesignerToBioPAXConverter.useBiopaxModelOntology = false;
    BioPAX.addBiopaxModelOntology = CellDesignerToBioPAXConverter.useBiopaxModelOntology;

    CellDesignerToBioPAXConverter cdbp = new CellDesignerToBioPAXConverter();
    //cdbp.biopax = new BioPAX(BioPAX.biopaxString,"http://bioinfo.curie.fr/binom/"+prefix+"#","http://bioinfo.curie.fr/biopaxmodel#");
    cdbp.biopax = new BioPAX();
    // Reading from CellDesigner XML
    cdbp.sbml = CellDesigner.loadCellDesigner(prefix+".xml");
    //cdbp.loadFromCellDesignerXML("temp2.xml");
    //cdbp.loadFromCellDesignerXML("rb-e2f.xml");
    //cdbp.loadFromCellDesignerXML("Nfkb_simplest_plus3.xml");

    // Converting to BioPAX
    cdbp.alwaysMentionCompartment = true;
    //cdbp.biopax.makeCompartments();
    //cdbp.createCellDesignerTerms();
    cdbp.compartmentHash = cdbp.getCompartmentHash(cdbp.sbml.getSbml());
    CellDesigner.entities = CellDesigner.getEntities(cdbp.sbml);
    cdbp.populateModel();

    // Saving to BioPAX
    BioPAX.saveToFile(prefix+".owl",cdbp.biopax.biopaxmodel);

    // Reading from BioPAX
    com.hp.hpl.jena.rdf.model.Model model = ModelFactory.createDefaultModel();
    model.read(new FileInputStream(prefix+".owl"),"");
    BioPAX.printDump(model);


    }catch(Exception e){
      e.printStackTrace();
    }

  }


}
