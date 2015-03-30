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

//import vdaoengine.data.io.*;
//import vdaoengine.data.*;
import java.io.*;
import java.util.*;

public class LaplaceMatToSif {

  public static void main(String[] args) {
    try{
      /*FileWriter fw = new FileWriter("sce.sif");
    VDataTable vt = vdaoengine.data.io.VDatReadWrite.LoadFromSimpleDatFile("sce.mat",true,"\t");
    for(int i=0;i<vt.rowCount;i++)
      for(int j=i+2;j<vt.colCount;j++){
        if(vt.stringTable[i][j].equals("1"))
          fw.write(vt.stringTable[i][0]+"  interactsWith  "+vt.stringTable[j-1][0]+"\r\n");
      }
      fw.close();*/

      /*String name = "clust";
      FileWriter fw = new FileWriter(name+".lst");
      VDataTable vt = vdaoengine.data.io.VDatReadWrite.LoadFromSimpleDatFile(name+".sif",false," \t");
      Vector nms = new Vector();
      for(int i=0;i<vt.rowCount;i++){
        String nm = vt.stringTable[i][0];
        if(nms.indexOf(nm)<0)
           nms.add(nm);
        nm = vt.stringTable[i][3];
        if(nms.indexOf(nm)<0)
           nms.add(nm);
      }
      for(int i=0;i<nms.size();i++)
         fw.write((String)nms.elementAt(i)+"\t#000080\r\n");
      fw.close();

    vt = vdaoengine.data.io.VDatReadWrite.LoadFromSimpleDatFile("c:/datas/kernelchip/results/dutreix/separator.threshold.0.color.txt",false," \t");
      vt.fieldNames[1] = "thresh0";
    for(int i=5;i<=95;i+=5){
      String fn = "c:/datas/kernelchip/results/dutreix/separator.threshold."+i+".color.txt";
      VDataTable vti = vdaoengine.data.io.VDatReadWrite.LoadFromSimpleDatFile(fn,false," \t");;
      vti.fieldNames[1] = "thresh"+i;
      vt = vdaoengine.utils.VSimpleProcedures.MergeTables(vt,"N1",vti,"N1","NA");
    }
    VDataTable vtg = vdaoengine.data.io.VDatReadWrite.LoadFromSimpleDatFile("glycolisis.xls",false," \t");;
    vt = vdaoengine.utils.VSimpleProcedures.MergeTables(vt,"N1",vtg,"N1","NA");
    vdaoengine.data.io.VDatReadWrite.saveToSimpleDatFile(vt,"separ2c.xls");*/

    }catch(Exception e){
        e.printStackTrace();
    }
  }
}