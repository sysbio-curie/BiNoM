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
import java.awt.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import org.sbml.x2001.ns.celldesigner.*;

/*
 * Small script for coloring proteins in CellDesigner files
 */
public class CellDesignerColorSpecies {

  private class SimpleTable{
    String tab[][];
    String fnames[];
    int colCount;
    int rowCount;
  }


  public static void main(String[] args) {

    try{

    args = new String[2];
    
    args[0] = "c:/datas/basal/220210/test.xml";
    args[1] = "c:/datas/basal/220210/diffgenes.txt";
    
    //args[0] = "c:/datas/moduleactivities/cd/RB_Apo_full.xml";
    //args[1] = "c:/datas/moduleactivities/cd/private.txt";
    
    //args[0] = "c:/datas/nfkb/nfkb_alain0707_p.xml";
    //args[1] = "c:/datas/nfkb/070706_1/models/pca_order";
    //args[0] = "c:/datas/nfkb/nfkb_simplest.xml";
    //args[1] = "c:/datas/nfkb/hierarchical/pca_order_simplest";

    //args[0] = "c:/datas/nfkb/nfkb_hoffman2_steady.xml";
    //args[1] = "c:/datas/nfkb/070706_1/models/pca_order_hoff";

    //args[0] = "c:/datas/nfkb/nfkb_lipniacki.xml";
    //args[1] = "c:/datas/nfkb/070706_1/models/pca_order_lipniacki";


    CellDesignerColorSpecies cl = new CellDesignerColorSpecies();
    SimpleTable tabl = cl.loadAttribTable(args[1]);

    for(int i=1;i<tabl.fnames.length;i++){
      //SAXReader reader = new SAXReader();
      //Document document = reader.read(new File(args[0]));

      CellDesigner cd3 = new CellDesigner();
      SbmlDocument sbml = cd3.loadCellDesigner(args[0]);

      for(int k=0;k<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;k++){
        CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(k);
        csa.getCelldesignerUsualView().getCelldesignerPaint().getColor().setStringValue("ffffffff");
      }

      Color cls[] = cl.getColorCode(tabl,i, false,false);
      System.out.println("Processing "+tabl.fnames[i]+" ...");
      for(int j=0;j<cls.length;j++){
    	 System.out.print(j+" ");
    	 String name = tabl.tab[j][0];
         cl.assignColorToSpecies(sbml,name,cls[j]);
      }
      System.out.println("\nFinished with "+tabl.fnames[i]);
      String fn = args[0];
      if(fn.endsWith(".xml")) fn = fn.substring(0,fn.length()-4);

      System.out.println("Saving "+tabl.fnames[i]+" ...");
      cd3.saveCellDesigner(sbml,fn+"_"+tabl.fnames[i]+".xml");
      //FileWriter out = new FileWriter(fn+"_"+tabl.fnames[i]+".xml");
      //document.write(out);
      //out.close();
    }


    }catch(Exception e){
      e.printStackTrace();
    }


  }

  public void assignColorToSpecies(SbmlDocument sbml,String id, Color cl){
	  
	String csaid = "";
	for(int i=0;i<sbml.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
		SpeciesDocument.Species sp = sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
		if(fr.curie.BiNoM.pathways.utils.Utils.getValue(sp.getName()).equals(id))
			csaid = sp.getId();
	}
	  
    for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;i++){
      //System.out.println("In assignColorToSpecies, "+i);
      CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias csa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);;
      String spid = csa.getSpecies();
      if(spid.equals(csaid)){
        int rc = cl.getRed();
        int gc = cl.getGreen();
        int bc = cl.getBlue();
        String rcs = Integer.toHexString(rc); if(rcs.length()==1) rcs="0"+rcs;
        String gcs = Integer.toHexString(gc); if(gcs.length()==1) gcs="0"+gcs;
        String bcs = Integer.toHexString(bc); if(bcs.length()==1) bcs="0"+bcs;
        csa.getCelldesignerUsualView().getCelldesignerPaint().getColor().setStringValue("ff"+rcs+gcs+bcs);
        csa.getCelldesignerUsualView().getCelldesignerPaint().setScheme(csa.getCelldesignerUsualView().getCelldesignerPaint().getScheme().forString("Gradation"));
      }
    }
    if(sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()!=null)
    for(int i=0;i<sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
      CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias csa = sbml.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
      String spid = csa.getSpecies();
      if(spid.equals(csaid)){
        int rc = cl.getRed();
        int gc = cl.getGreen();
        int bc = cl.getBlue();
        String rcs = Integer.toHexString(rc); if(rcs.length()==1) rcs="0"+rcs;
        String gcs = Integer.toHexString(gc); if(gcs.length()==1) gcs="0"+gcs;
        String bcs = Integer.toHexString(bc); if(bcs.length()==1) bcs="0"+bcs;
        csa.getCelldesignerUsualView().getCelldesignerPaint().getColor().setStringValue("ff"+rcs+gcs+bcs);
        csa.getCelldesignerUsualView().getCelldesignerPaint().setScheme(csa.getCelldesignerUsualView().getCelldesignerPaint().getScheme().forString("Gradation"));
      }
    }
  }


  public SimpleTable loadAttribTable(String fn){
  String s;
  String delim = "\t";
  String tab[][] = null;
  String fieldNames[] = null;
  int rowCount = 0;
  int colCount = 0;
  try{
  LineNumberReader lri = new LineNumberReader(new FileReader(fn));
  s = lri.readLine();
  StringTokenizer sti = new StringTokenizer(s,delim);
  colCount = sti.countTokens();
  fieldNames = new String[colCount];
  int k=0;
  while(sti.hasMoreTokens()){
    fieldNames[k] = sti.nextToken();
      k++;
    }

  int cr = 1;
  String sss = null;
  while((sss=lri.readLine())!= null) if(!sss.trim().equals("")) cr++;
  rowCount = cr;
  lri.close();

  tab = new String[rowCount-1][colCount];
  LineNumberReader lr = new LineNumberReader(new FileReader(fn));
  lr.readLine();

  int i=0;
  while ( ((s=lr.readLine()) != null)&&(i<rowCount-1) )
    if(!s.trim().equals(""))
     {
     StringTokenizer st = new StringTokenizer(s,delim);
     int j=0;
     while ((st.hasMoreTokens())&&(j<colCount)) {
        String ss = st.nextToken();
        if (ss.length()>1)
        if (ss.charAt(0)=='\"')
           ss = new String(ss.substring(1,ss.length()-1));
        tab[i][j] = ss;
        j++;
        }
     i++;
     }
  }
  catch (Exception e) { System.out.println("Error in Reading table: "+e.toString());}
  SimpleTable tabl = new SimpleTable();
  tabl.tab = tab;
  tabl.fnames = fieldNames;
  tabl.colCount = colCount;
  tabl.rowCount = rowCount-1;
  return tabl;
  }

  public Color[] getColorCode(SimpleTable tab, int colNum, boolean maxInterval, boolean logScale){
    Color cl[] = new Color[tab.rowCount];
    String vec[] = new String[tab.rowCount];
    for(int i=0;i<tab.rowCount;i++){
      vec[i] = tab.tab[i][colNum];
    }
    boolean numeric = true;
    float vecf[] = new float[tab.rowCount];
    try{
      for(int i=0;i<tab.rowCount;i++) vecf[i] = Float.parseFloat(vec[i]);
    }catch(Exception e){
      numeric = false;
    }
    if(numeric){
      float minv = Float.MAX_VALUE;
      float maxv = Float.MIN_VALUE;
      for(int i=0;i<tab.rowCount;i++){
        if(vecf[i]<minv) minv = vecf[i];
        if(vecf[i]>maxv) maxv = vecf[i];
      }
      if(maxInterval){
        for(int k1=0;k1<tab.rowCount;k1++)
          for(int k2=0;k2<tab.colCount;k2++){
            try{
              float x = Float.parseFloat(tab.tab[k1][k2]);
              if(x<minv) minv=x;
              if(x>maxv) maxv=x;
            }catch(Exception e){

            }
          }
      }
      
      if(logScale){
    	  minv = (float)Math.log(Math.abs(minv));
    	  maxv = (float)Math.log(Math.abs(maxv));
      }

      for(int i=0;i<tab.rowCount;i++){
        //float x = (vecf[i]-minv)/(maxv-minv);
        //cl[i] = getSpektrumGR(x);
        float x = 0f;
        //x = (vecf[i]-minv)/(maxv-minv);
        //System.out.println(vecf[i]+"\t"+minv+"\t"+maxv);
        float val = vecf[i];
        float val1 = (float)Math.log(Math.abs(val));        
        if(logScale){ 
        	if(val1<0) val1 = 0;
            if(val>0)
            	x = val1/maxv;
            else
            	x = -val1/minv;
        }else{
        if(val>0)
        	x = val/maxv;
        else
        	x = -val/minv;
        }
        if(i==6)
        	System.out.println("CycD\t"+val+"\t"+"val1="+val1+"\t"+x+"\t"+minv+"\t"+maxv);
        x = (x+1)/2f;
        //x = x*1.5f;
        //if(vecf[i]>0) x = vecf[i]/maxv;
        //if(vecf[i]<0) x = -vecf[i]/minv;
        cl[i] = getSpektrumGR(x);
      }
    }else{
      for(int i=0;i<tab.rowCount;i++){
        float red = 1f;
        float green = 1f;
        float blue = 1f;
        if(vec[i].startsWith("color:")){
          StringTokenizer st = new StringTokenizer(vec[i],",");
          String s = st.nextToken();
          s = s.substring(6);
          red = Integer.parseInt(s.substring(0,2),16)/255f;
          green = Integer.parseInt(s.substring(2,4),16)/255f;
          blue = Integer.parseInt(s.substring(4,6),16)/255f;
          }
        cl[i] = new Color(red,green,blue);
      }
    }
    return cl;
  }

  public static Color getSpektrumGR(float x){
	//if(x>1) x = 1;
    /*int n=5;
    int r=n;
    int g=n;
    int b=n;
    if(x>0.5){
       r=(int)(r+(255-n)*2*(x-0.5)+0.5);
       //g:=round(g-(255-n)*2*(x-0.5));
       //b:=round(b-(255-n)*2*(x-0.5));
    }
    if(x<0.5){
       g=(int)(g+(255-n)*2*(0.5-x)+0.5);
       //r:=round(r-(255-n)*2*(0.5-x));
       //b:=round(b-(255-n)*2*(0.5-x));
    }
    return new Color(r/255f,g/255f,b/255f);*/
    int n=5;
    int r=n;
    int g=n;
    int b=n;
    if(x>0.5){
       r=(int)(r+(255-n)*2*(x-0.5)+0.5);
       //g:=round(g-(255-n)*2*(x-0.5));
       //b:=round(b-(255-n)*2*(x-0.5));
    }
    if(x<0.5){
       g=(int)(g+(255-n)*2*(0.5-x)+0.5);
       //r:=round(r-(255-n)*2*(0.5-x));
       //b:=round(b-(255-n)*2*(0.5-x));
    }
    return new Color(r/255f,g/255f,b/255f);

  }
  
  public static Color getSpektrumGRB(float x){
		//if(x>1) x = 1;
	    /*int n=5;
	    int r=n;
	    int g=n;
	    int b=n;
	    if(x>0.5){
	       r=(int)(r+(255-n)*2*(x-0.5)+0.5);
	       //g:=round(g-(255-n)*2*(x-0.5));
	       //b:=round(b-(255-n)*2*(x-0.5));
	    }
	    if(x<0.5){
	       g=(int)(g+(255-n)*2*(0.5-x)+0.5);
	       //r:=round(r-(255-n)*2*(0.5-x));
	       //b:=round(b-(255-n)*2*(0.5-x));
	    }
	    return new Color(r/255f,g/255f,b/255f);*/
	    int n=5;
	    int r=n;
	    int g=n;
	    int b=n;
	    if(x>0.5){
	       r=(int)(r+(255-n)*2*(x-0.5)+0.5);
	       b=255-(int)(b+n*2*(x-0.5)+0.5);
	       //g:=round(g-(255-n)*2*(x-0.5));
	       //b:=round(b-(255-n)*2*(x-0.5));
	    }
	    if(x<0.5){
	       g=(int)(g+(255-n)*2*(0.5-x)+0.5);
	       b=255-(int)(b+n*2*(0.5-x)+0.5);
	       //r:=round(r-(255-n)*2*(0.5-x));
	       //b:=round(b-(255-n)*2*(0.5-x));
	    }
	    return new Color(r/255f,g/255f,b/255f);

	  }
  

  public static String replaceString(String source,String shabl,String val){
        int i=0;
        String s1 = new String(source);
        while((i=s1.indexOf(shabl))>=0){
                StringBuffer sb = new StringBuffer(s1);
                sb.replace(i,i+shabl.length(),val);
                s1 = sb.toString();
        }
   return s1;
  }

}