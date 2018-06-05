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

import java.io.*;
import java.util.*;

import vdaoengine.data.VDataTable;

/**
 * Simple implementation of a table of entries
 * @author Andrei Zinovyev
 *
 */
public class SimpleTable {

  public int colCount = 0;
  public int rowCount = 0;
  public String fieldNames[] = null;
  public int fieldTypes[] = null;
  public String stringTable[][] = null;
  public String filename = null;

  public static int NUMERICAL = 1;
  public static int STRING = 0;
  
  public boolean makeUpperCaseInIndex = false;
  
  public HashMap<String,Vector<Integer>> index = new HashMap<String,Vector<Integer>>();
  public HashMap<String,Vector<Integer>> secondaryIndex = new HashMap<String,Vector<Integer>>();  
  
  public SimpleTable() {
  }
  public static void main(String[] args) {
    SimpleTable simpleTable1 = new SimpleTable();
  }
  
  public void createIndex(String field){
	  	index.clear();
	  	int ifield = fieldNumByName(field);
	  	if(ifield!=-1){
	  		for(int i=0;i<stringTable.length;i++){
	  			Vector<Integer> rows = null;
	  			if(!makeUpperCaseInIndex)
	  				rows = index.get(stringTable[i][ifield]);
	  			else
	  				rows = index.get(stringTable[i][ifield].toUpperCase());
	  			if(rows==null) rows = new Vector<Integer>();
	  			rows.add(new Integer(i));
	  			if(!makeUpperCaseInIndex)	  			
	  				index.put(stringTable[i][ifield], rows);
	  			else
	  				index.put(stringTable[i][ifield].toUpperCase(), rows);
	  		}
	  	}
	  }

  public void createSecondaryIndex(String field){
	  	secondaryIndex.clear();
	  	int ifield = fieldNumByName(field);
	  	if(ifield!=-1){
	  		for(int i=0;i<stringTable.length;i++){
	  			Vector<Integer> rows = null;
	  			if(!makeUpperCaseInIndex)
	  				rows = secondaryIndex.get(stringTable[i][ifield]);
	  			else
	  				rows = secondaryIndex.get(stringTable[i][ifield].toUpperCase());
	  			if(rows==null) rows = new Vector<Integer>();
	  			rows.add(new Integer(i));
	  			if(!makeUpperCaseInIndex)	  			
	  				secondaryIndex.put(stringTable[i][ifield], rows);
	  			else
	  				secondaryIndex.put(stringTable[i][ifield].toUpperCase(), rows);
	  		}
	  	}
	  }
  
  
  public void LoadFromSimpleDatFile(String FileName,boolean firstLineFNames,String delim){
	  try{
		  LoadFromSimpleDatFile(new FileReader(FileName),firstLineFNames,delim,1000000000);
		  this.filename = FileName;
	  }catch (Exception e) { System.out.println("Error in VDatReadWrite: "+e.toString());}
  }

  public void LoadFromSimpleDatFile(String FileName,boolean firstLineFNames,String delim, int mark){
	  try{
		  LoadFromSimpleDatFile(new FileReader(FileName),firstLineFNames,delim,mark);
		  this.filename = FileName;
	  }catch (Exception e) { System.out.println("Error in VDatReadWrite: "+e.toString());}
  }
  
  
  public void LoadFromSimpleDatFileString(String text,boolean firstLineFNames,String delim){
	  try{
		  LoadFromSimpleDatFile(new StringReader(text),firstLineFNames,delim,1000000000);		  
	  }catch (Exception e) { System.out.println("Error in VDatReadWrite: "+e.toString());}
  }
  

  /**
   * Loads the table from the file. 
   * @param FileName
   * @param firstLineFNames is there column names in the first row?
   * @param delim Table item delimiters
   */
  public void LoadFromSimpleDatFile(Reader reader, boolean firstLineFNames, String delim, int mark){

  String s = null;
  try{
  LineNumberReader lri = new LineNumberReader(reader);
  
  lri.mark(mark);
  //lri.mark(1);
  s = lri.readLine();
  StringTokenizer sti = new StringTokenizer(s,delim);
  colCount = sti.countTokens();
  fieldNames = new String[colCount];
  fieldTypes = new int[colCount];
  if(!firstLineFNames){
    for(int i=0;i<colCount;i++) fieldNames[i] = "N"+(i+1);
  }else{
    int k=0;
    while(sti.hasMoreTokens()){
      fieldNames[k] = cutQuotes(sti.nextToken());
      k++;
    }
  }
  int cr = 0;
  
  
  while(lri.readLine()!= null) cr++;
  if(!firstLineFNames) cr++;
  rowCount = cr;
  //System.out.println("Rows number = "+cr);
  //lri.close(); 
  lri.reset();
  //lri.mark(0);
  //lri = new LineNumberReader(reader);

  stringTable = new String[rowCount][colCount];
  //LineNumberReader lr = new LineNumberReader(reader);
  if(firstLineFNames){
    lri.readLine();
    //rowCount = rowCount-1;
  }
  
  int i=0;
  
  while ( ((s=lri.readLine()) != null)&&(i<=rowCount) )
     {
	 //System.out.println(s);
     PowerfulTokenizer st = new PowerfulTokenizer(s,delim);
     int j=0;
     while ((st.hasMoreTokens())&&(j<colCount)) {
        String ss = st.nextToken();
        if (ss.length()>1)
        if (ss.charAt(0)=='\"')
           ss = new String(ss.substring(1,ss.length()-1));
        stringTable[i][j] = ss;
        j++;
        }
     i++;
     }
  
  
  }
  catch (Exception e) { 
	  System.out.println("Error in VDatReadWrite: "+e.toString()+"\n"+s);
	  }
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

  public static String cutQuotes(String s){
    String r = s;
    if(s.charAt(0)=='"')
      r = s.substring(1,s.length());
    if(s.charAt(s.length()-1)=='"')
      r = r.substring(0,r.length()-1);
    return r;
  }

  public int fieldNumByName(String nam){
    int res=-1;
    for(int i=0;i<fieldNames.length;i++)
      if(fieldNames[i].toLowerCase().equals(nam.toLowerCase())) res=i;
    return res;
  }

  public void saveToSimpleTxtTabDelimited(String fn){
	  try{
		  FileWriter fw = new FileWriter(fn);
		  for(int i=0;i<fieldNames.length;i++)
			  fw.write(fieldNames[i]+"\t");
		  fw.write("\n");
		  for(int i=0;i<rowCount;i++){
			  for(int j=0;j<colCount;j++)
				  fw.write(stringTable[i][j]+"\t");
			  fw.write("\n");
		  }
		  fw.close();
	  }catch(Exception e){
		  e.printStackTrace();
	  }
  }
  
  public SimpleTable SelectColumns(Vector columns){
	  SimpleTable res = new SimpleTable();
	  Vector v = columns;
	  res.rowCount = this.rowCount;
	  res.colCount = v.size();
	  res.stringTable = new String[res.rowCount][res.colCount];
	  res.fieldNames = new String[res.colCount];
	  res.fieldTypes = new int[res.colCount];
	  for(int i=0;i<res.colCount;i++){
	    String fn = (String)v.elementAt(i);
	    int k = this.fieldNumByName(fn);
	    res.fieldNames[i] = this.fieldNames[k];
	    res.fieldTypes[i] = this.fieldTypes[k];
	    for(int j=0;j<res.rowCount;j++)
	      res.stringTable[j][i] = this.stringTable[j][k];
	  }
	  return res;
	}
  
  public SimpleTable SelectRows(Vector lineNumbers){
		    Vector rows = new Vector();
		    int found = 0;
		    Vector found_ids = new Vector();
		    for(int i=0;i<this.rowCount;i++){
		      String r[] = null;
		      if(lineNumbers.indexOf(new Integer(i))>=0){
		        r = this.stringTable[i];
		        rows.add(r);
		        found++;
		      }
		    }
		    SimpleTable res = new SimpleTable();
		    res.copyHeader(this);
		    res.rowCount = rows.size();
		    res.stringTable = new String[res.rowCount][res.colCount];
		    for(int i=0;i<rows.size();i++){
		      res.stringTable[i] = (String[])rows.elementAt(i);
		    }
		    //System.out.println(found+" from "+ids.size()+" found ("+found_ids.size()+" unique)");
		    return res;
	}

  public SimpleTable SelectRowsInOrder(Vector<Integer> lineNumbers){
	    Vector rows = new Vector();
	    int found = 0;
	    Vector found_ids = new Vector();
	    for(int i=0;i<lineNumbers.size();i++){
	      String r[] = this.stringTable[lineNumbers.get(i)];
	        rows.add(r);
	        found++;
	    }
	    SimpleTable res = new SimpleTable();
	    res.copyHeader(this);
	    res.rowCount = rows.size();
	    res.stringTable = new String[res.rowCount][res.colCount];
	    for(int i=0;i<rows.size();i++){
	      res.stringTable[i] = (String[])rows.elementAt(i);
	    }
	    //System.out.println(found+" from "+ids.size()+" found ("+found_ids.size()+" unique)");
	    return res;
}

  
  public void copyHeader(SimpleTable vt){
	    this.fieldNames = vt.fieldNames;
	    this.fieldTypes = vt.fieldTypes;
	    this.colCount = vt.colCount;
	    this.rowCount = vt.rowCount;
	}




}