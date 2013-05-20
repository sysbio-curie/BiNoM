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
import java.io.File;
import java.awt.*;

import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.*;

import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;

import fr.curie.BiNoM.pathways.*;
import edu.rpi.cs.xgmml.*;

public class CellDesigner2Cytoscape {

    public static void main(String[] args) {
	try{

	    CellDesignerToCytoscapeConverter c2c = new CellDesignerToCytoscapeConverter();
	    
	    //String prefix = "rb-e2f";
            //String prefix = "Nfkb_simplest_plus";
            //String prefix = "AB";
            //String prefix = "lxx00230";
            //String prefix = "feedback";
            //String prefix = "c:/datas/calzone/problem4/doublealias";
	        //String prefix = "c:/datas/binomtest/ebi/test";
	    //String prefix = "c:/datas/binomtest/apoptosis_sbml";
	    //String prefix = "c:/datas/danielrovera/naming_test";
	    //String prefix = "c:/datas/calamar/rb_reduced";
	    //String prefix = "c:/datas/binomtest/components40";
	    //String prefix = "c:/datas/calamar/testEditPoints";
	    //String prefix = "c:/datas/binomtest/M-Phase2";
	    //String prefix = "c:/datas/simon/100526_fixed";
	    //String prefix = "c:/datas/basal/PARPCaseStudy/CC_DNArepair_10_06_2010_SynthLethality";
	    //String prefix = "c:/datas/binomtest/NCI_example";
	    //String prefix = "C:/Datas/Simon/100603";
	    //String prefix = "C:/Datas/Binomtest/M-Phase2_ver41";
	    //String prefix = "C:/Datas/Basal/dnarepairmap2/test";
	    //String prefix = "C:/Datas/Basal/dnarepairmap2/modules/CellCycle_Sphase_c";
	    //String prefix = "C:/Datas/OCSANA/egfr";
	    //String prefix = "C:/Datas/ClickableMap/2011_10_05_Model/2011_10_05_Model_Mistakes/2011_10_05_Model_master_mistakes_OVAL";
	     //String prefix = "c:/datas/binomtest/BIOMD0000000001";
	    //String prefix = "C:/Datas/NaviCell/test/testmap1_src/test1_master";
	    String prefix = "c:/datas/acsn/acsn_only/acsn_src/acsn_master";

	    if(args.length>0){
		prefix = args[0];
		if(prefix.toLowerCase().endsWith(".xml"))
		    prefix = prefix.substring(0,prefix.length()-4);
	    }
	    
	    GraphDocument grDoc = c2c.convert(prefix+".xml").graphDocument;
	    
	    XGMML.saveToXGMML(grDoc, prefix+".xgmml");
	    
	    /*c2c = new CellDesignerToCytoscapeConverter();
		System.out.println("Loading file...");
		c2c.sbml = CellDesigner.loadCellDesigner(prefix+".xml");
		System.out.println("Loaded.");
	    CellDesigner.saveCellDesigner(c2c.sbml, prefix+"_1.xml");*/
	    
	    System.out.println("Nodes "+grDoc.getGraph().getNodeArray().length+" Edges "+grDoc.getGraph().getEdgeArray().length);

	}catch(Exception e){
	    e.printStackTrace();
	}

    }
}
