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

import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import org.sbml.x2001.ns.celldesigner.*;
import org.apache.xmlbeans.*;
import edu.rpi.cs.xgmml.*;

public class Cytoscape2CellDesigner {
  public static void main(String[] args) {

    try{

      //String prefix = "Nfkb_simplest_plus";
      //String prefix = "rb-e2f";
      //String prefix = "M-Phase2";

      String prefix = "c:/docs/tutorials/cdbinom/Nfkb_final_color";
      String cdFile = "c:/docs/tutorials/cdbinom/Nfkb_final.xml";

      GraphDocument gr = XGMML.loadFromXMGML(prefix+".xgmml");
      SbmlDocument sbml = CellDesigner.loadCellDesigner(cdFile);

      CytoscapeToCellDesignerConverter.filterIDs(sbml,gr);
      CytoscapeToCellDesignerConverter.assignColors(sbml,gr);

      CellDesigner.saveCellDesigner(sbml,prefix+"_x"+".xml");




    }catch(Exception e){
      e.printStackTrace();
    }


  }

}
