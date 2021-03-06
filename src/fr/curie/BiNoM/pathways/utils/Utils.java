/*
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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

import org.apache.xmlbeans.*;
import org.sbml.x2001.ns.celldesigner.BodyDocument;
import org.sbml.x2001.ns.celldesigner.HtmlDocument;
import org.sbml.x2001.ns.celldesigner.NotesDocument;

import vdaoengine.data.VDataTable;
import vdaoengine.utils.Algorithms;
import vdaoengine.utils.VSimpleFunctions;
import edu.rpi.cs.xgmml.*;
import fr.curie.BiNoM.pathways.analysis.structure.Attribute;
import fr.curie.BiNoM.pathways.analysis.structure.Edge;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.analysis.structure.Node;
import fr.curie.BiNoM.pathways.biopax.*;

import com.hp.hpl.jena.rdf.model.*;

import java.net.*;


/**
 * Set of simple functions
 * @author Andrei Zinovyev
 *
 */
@SuppressWarnings("unchecked")
public class Utils {
	
	public static void main(String args[]){
		try{
		
		Vector<String> vs = guessProteinIdentifiers("ccl4");
		for(String s: vs) System.out.println(s);
		System.exit(0);

		//for(int i=0;i<255;i++)
		//	System.out.println(i+"\t"+(char)i);
		String text = Utils.loadString("C:/Datas/acsn/assembly/acsn_src/acsn_bib.txt");
		text = correctIllegalCharacters(text);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private static String eclipse(final String s, final int levels) {
		final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		int level = 0;
		while (level < stackTrace.length && !stackTrace[level].getMethodName().equals("eclipse"))
			level++;
		level += levels + 2;
		if (level >= stackTrace.length)
			level = stackTrace.length - 1;
		final StackTraceElement frame = stackTrace[level];
		return "(" + frame.getFileName() + ":" + frame.getLineNumber() +") " + s;
	}
	
	public static void eclipsePrint(String s) {
		System.out.print(eclipse(s, 0));
	}
	
	public static void eclipsePrintln(String s) {
		System.out.println(eclipse(s, 0));
		System.out.flush();
	}
	
	public static String eclipseParentErrorln(String s) {
		System.out.flush();
		System.err.println(eclipse(s, 2));
		System.err.flush();
		return s;
	}
	
	public static String eclipseErrorln(String s) {
		String mess = eclipse(s, 0);
		System.out.flush();
		System.err.println(mess);
		System.err.flush();
		return s;
	}
	
	public static String eclipseError(String s) {
		String mess = eclipse(s, 0);
		System.out.flush();
		System.err.print(mess);
		System.err.flush();
		return s;
	}
	
	public static void eclipseParentPrintln(String s) {
		System.out.println(eclipse(s, 1));
		System.out.flush();
	}

  public static long lastUsedMemory;

  /**
   * In source string replaces shabl substring with val
   * @param source
   * @param shabl
   * @param val
   * @return
   */
  public static String replaceString(String source,String shabl,String val){
        int i=0;
        String s1 = new String(source);
        StringBuffer sb = new StringBuffer(s1);
        while((i=sb.indexOf(shabl))>=0){
                sb.replace(i,i+shabl.length(),val);
        }
        s1 = sb.toString();
        return s1;
  }

  /**
   * In source string replaces shabl substring with val and providing some info about the number of changes made
   * @param source
   * @param shabl
   * @param val
   * @return
   */
  public static String replaceStringCount(String source,String shabl,String val){
        int i=0;
        String s1 = new String(source);
        //int k = 0;
        StringBuffer sb = new StringBuffer(s1);
        while((i=sb.indexOf(shabl))>=0){
                sb.replace(i,i+shabl.length(),val);
                /*if((int)(k/100)*100==k)
                  System.out.print(k+" ");
                k++;*/
        }
        //System.out.println();
        s1 = sb.toString();
        return s1;
  }
  
  
  public static String replaceStringChar(String source,String shabl,String val){
	  char csource[] = source.toCharArray();
	  char cshabl[] = shabl.toCharArray();
	  //char cval[] = val.toCharArray();
	  StringBuffer sb = new StringBuffer();
	  int pointer = 0;
	  while(pointer<csource.length){
		  boolean found = true;
		  for(int i=0;i<cshabl.length;i++)
			  if(csource[pointer+i]!=cshabl[i]){
				  found = false;
				  break;
			  }
		  if(found){
			  sb.append(val);
			  pointer+=cshabl.length;
		  }else{
			  sb.append(csource[pointer]);
			  pointer++;
		  }
	  }
	  return sb.toString();
  }

  /**
   * Set a string value for XmlObject  
   * @param xBean
   * @param value
   */
  public static void setValue( XmlObject xBean,
                               String    value )
  {
     XmlCursor xCursor = xBean.newCursor(  );
     if ( xCursor.isStartdoc(  ) )
     {
        xCursor.toFirstChild(  );
     }

     xCursor.setTextValue( value );
     xCursor.dispose(  );
  }

  /**
   * Same as SetValue
   * @param xBean
   * @param value
   */
  public static void setLitValue( XmlObject xBean,
                               String    value )
  {
     XmlCursor xCursor = xBean.newCursor(  );
     if ( xCursor.isStartdoc(  ) )
     {
        xCursor.toFirstChild(  );
     }

     xCursor.setTextValue( value );
     xCursor.dispose(  );
  }

  /**
   * Read the text of XmlObject
   * @param xBean
   * @return
   */
  public static String getText( XmlObject xBean)
  {
     XmlCursor xCursor = xBean.newCursor(  );
     if ( xCursor.isStartdoc(  ) )
     {
        xCursor.toFirstChild(  );
     }

     String s = xCursor.getTextValue();
     xCursor.dispose(  );
     return s;
  }
  
  /**
   * read XmlObject value
   */
  public static String getValue( XmlObject xBean )
  {
     String value = null;
     if(xBean!=null){
       XmlCursor xCursor = xBean.newCursor(  );
       value = xCursor.getTextValue(  );
       xCursor.dispose(  );
     }
     return value;
}

/**
 * Load a text file into memory and returns String
 * @param fn
 * @return
 */
public static String loadString(String fn){
  StringBuffer sb = new StringBuffer();
  try{
  LineNumberReader lr = new LineNumberReader(new FileReader(fn));
  String s = null;
  while((s=lr.readLine())!=null){
    sb.append(s+"\n");
  }
  lr.close();
  }catch(Exception e){
    e.printStackTrace();
  }
  return sb.toString();
}

public static Vector<String> loadStringListFromFile(String fn){
Vector<String> list = new Vector<String>(); 
try{
LineNumberReader lr = new LineNumberReader(new FileReader(fn));
String s = null;
while((s=lr.readLine())!=null){
  list.add(s.trim());
}
lr.close();
}catch(Exception e){
  e.printStackTrace();
}
return list;
}

public static Vector<String> loadStringListFromText(String text){
Vector<String> list = new Vector<String>(); 
try{
LineNumberReader lr = new LineNumberReader(new StringReader(text));
String s = null;
while((s=lr.readLine())!=null){
  list.add(s.trim());
}
lr.close();
}catch(Exception e){
  e.printStackTrace();
}
return list;
}


/**
 * Converts InputStream into String
 * @param is
 * @return
 */
public static String loadString(InputStream is){
  StringBuffer sb = new StringBuffer();
  try{
    int b = 0;
    while((b = is.read())>=0){
      sb.append((char)b);
    }
  }catch(Exception e){
    e.printStackTrace();
  }
  return sb.toString();
}

/**
 * Add an attribute to GraphicNode
 * @param n
 * @param label
 * @param name
 * @param value
 * @param typ
 */
public static void addAttribute(GraphicNode n,String label,String name, String value, ObjectType.Enum typ){
  AttDocument.Att at = n.addNewAtt();
  at.setName(name);
  at.setValue(value);
  at.setLabel(label);
  at.setType(typ);
}


public static void addAttributeUniqueNameConcatenatedValues(GraphicNode n,String label,String name, String value, ObjectType.Enum typ){
	  AttDocument.Att at = getFirstAttribute(n,name);
	  String val = "";
	  if(at==null){
	  		at = n.addNewAtt();
	  		at.setName(name);
	  		at.setValue(val);
	  }else
		  val = at.getValue();
	  
	  if(val.equals(""))
		  at.setValue(value);
	  else
		  at.setValue(val+"@@"+value);
	  at.setLabel(label);
	  at.setType(typ);
}

/**
 * Add an attribute to GraphicEdge
 * @param n
 * @param label
 * @param name
 * @param value
 * @param typ
 */
public static void addAttribute(GraphicEdge n,String label,String name, String value, ObjectType.Enum typ){
  AttDocument.Att at = n.addNewAtt();
  at.setName(name);
  at.setValue(value);
  at.setLabel(label);
  at.setType(typ);
}
/**
 * Get first attribute in the list with name 
 * @param n
 * @param name
 * @return
 */
public static AttDocument.Att getFirstAttribute(GraphicNode n,String name){
	AttDocument.Att at = null;
	for(int i=0;i<n.getAttArray().length;i++){
		if(n.getAttArray()[i].getName().equals(name)){
			at = n.getAttArray()[i]; break;
		}
	}
	return at;
}
/**
 * Get first attribute in the list with name 
 * @param n
 * @param name
 * @return
 */
public static AttDocument.Att getFirstAttribute(GraphicEdge n,String name){
	AttDocument.Att at = null;
	for(int i=0;i<n.getAttArray().length;i++){
		if(n.getAttArray()[i].getName().equals(name)){
			at = n.getAttArray()[i]; break;
		}
	}
	return at;
}
/**
 * Set attribute value for GraphicEdge 
 * @param n
 * @param name
 * @return
 */
public static void setAttribute(GraphicEdge n,String label,String name, String value, ObjectType.Enum typ){
	  AttDocument.Att at = getFirstAttribute(n,name);
	  if(at==null)
		  at = n.addNewAtt();
	  at.setName(name);
	  at.setValue(value);
	  at.setLabel(label);
	  at.setType(typ);
	}
/**
 * Set attribute value for GraphicNode 
 * @param n
 * @param name
 * @return
 */
public static void setAttribute(GraphicNode n,String label,String name, String value, ObjectType.Enum typ){
	  AttDocument.Att at = getFirstAttribute(n,name);
	  if(at==null)
		  at = n.addNewAtt();
	  at.setName(name);
	  at.setValue(value);
	  at.setLabel(label);
	  at.setType(typ);
	}
/**
 * Convert string from the form <prefix>#<id> to simply <id> 
 * @param n
 * @param name
 * @return
 */
public static String cutUri(String uri){
  int k = uri.indexOf("#");
  return uri.substring(k+1,uri.length());
}

public static String cutNameSpace(String uri){
	  int k = uri.indexOf("#");
	  return uri.substring(0,k);
	}

/**
 * Substitutes strings ' ','*','-','[',']','__',':' to underscore symbol
 * @param name
 * @return
 */
public static String correctName(String name){
	// cleanup non ascii characters
	name =  name.replaceAll("[^\\p{ASCII}]", "");

	name = Utils.replaceString(name," ","_");
	name = Utils.replaceString(name,":","_");	
	name = Utils.replaceString(name,"/","_");
	name = Utils.replaceString(name,"|","_");
	name = Utils.replaceString(name,"*","_");
	name = Utils.replaceString(name,"-","_");
	name = Utils.replaceString(name,"[","_");
	name = Utils.replaceString(name,"]","_");
	name = Utils.replaceString(name,"(","_");
	name = Utils.replaceString(name,")","_");
	name = Utils.replaceString(name,"__","_");
	name = Utils.replaceString(name,"__","_");
	//name = Utils.replaceString(name,":","_");
	if(name.endsWith("_"))
		name = name.substring(0,name.length()-1);
	if(name.startsWith("_"))
		name = name.substring(1,name.length());

	byte mc[] = name.getBytes();
	StringBuffer sb = new StringBuffer(name);
	for(int i=0;i<mc.length;i++)
		//System.out.println(name.charAt(i)+"\t"+mc[i]);
		if(mc[i]<=0)
			sb.setCharAt(i,'_');
	return sb.toString();
}
/**
 * Returns first in the alphabetic order string from iterator it
 * @param it
 * @return
 */
public static String getVocabularyTerm(Iterator it){
  Vector v = new Vector();
  while(it.hasNext()){
    v.add(it.next());
  }
  Collections.sort(v);
  if(v.size()>0)
    return (String)v.get(0);
  else
    return "";
}
/**
 * Simply prints all statement uris in the entity res
 * @param res
 */
public static void printPropertyURIs(Entity res){
  List lst = res.listStatements();
  Iterator it = lst.iterator();
  while(it.hasNext()){
    Statement st = (Statement)it.next();
    String ss = st.toString();
    StringTokenizer stt = new StringTokenizer(ss,"[], ");
    stt.nextToken();
    //String propname = stt.nextToken();
    //String propuri = stt.nextToken();
    System.out.println(ss);
  }
}
/**
 * Simply prints all statement uris in the utilityClass res
 * @param res
 */
public static void printPropertyURIs(UtilityClass res){
  List lst = res.listStatements();
  Iterator it = lst.iterator();
  while(it.hasNext()){
    Statement st = (Statement)it.next();
    String ss = st.toString();
    StringTokenizer stt = new StringTokenizer(ss,"[], ");
    stt.nextToken();
    //String propname = stt.nextToken();
    //String propuri = stt.nextToken();
    System.out.println(ss);
  }
}
/**
 * Prints how much memory is used
 *
 */
public static void printUsedMemory(){
    long mem = ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
    lastUsedMemory = mem;
    //long mem = Runtime.getRuntime().freeMemory();
    mem = (long)(1e-6f*mem);
    System.out.println("Used memory "+mem+"M");
}
/**
 * Prints a difference in used memory since last call of printUsedMemory,printUsedMemorySinceLastTime,
 * getUsedMemorySinceLastTime,printUsedMemorySinceLastTimeByte functions
 *
 */
public static void printUsedMemorySinceLastTime(){
    long mem = ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
    long used = mem - lastUsedMemory;
    lastUsedMemory = mem;
    //long mem = Runtime.getRuntime().freeMemory();
    used = (long)(1e-3f*used);
    System.out.println("Used since last time "+used+"K");
}
/**
 * Returns a difference in used memory since last call of printUsedMemory,printUsedMemorySinceLastTime,
 * getUsedMemorySinceLastTime,printUsedMemorySinceLastTimeByte functions
 * @return
 */
public static long getUsedMemorySinceLastTime(){
    long mem = ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
    long used = mem - lastUsedMemory;
    lastUsedMemory = mem;
    //long mem = Runtime.getRuntime().freeMemory();
    //used = (long)(1e-3f*used);
    //System.out.println(used);
    return used;
}
public static long getUsedMemory(){
    long mem = Runtime.getRuntime().totalMemory();
    //long used = mem - lastUsedMemory;
    //lastUsedMemory = mem;
    //long mem = Runtime.getRuntime().freeMemory();
    //used = (long)(1e-3f*used);
    //System.out.println(used);
    return mem;
}
public static long getUsedMemoryMb(){
    long mem = Runtime.getRuntime().totalMemory();
    //long used = mem - lastUsedMemory;
    //lastUsedMemory = mem;
    //long mem = Runtime.getRuntime().freeMemory();
    //used = (long)(1e-3f*used);
    //System.out.println(used);
    return (int)((float)mem*1e-6+0.5f);
}
/**
 * Same ad printUsedMemorySinceLastTime but in bytes
 *
 */
public static void printUsedMemorySinceLastTimeByte(){
    long mem = ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
    long used = mem - lastUsedMemory;
    lastUsedMemory = mem;
    //long mem = Runtime.getRuntime().freeMemory();
    //used = (long)(1e-3f*used);
    System.out.println("Used since last time "+used+"Bytes");
}
/**
 * 
 * @param v1
 * @param v2
 * @return The number of common entries 
 */
public static int compareTwoSets(Vector v1, Vector v2){
 int inters = 0;
 for(int i=0;i<v1.size();i++){
   if(v2.indexOf(v1.get(i))>=0)
     inters++;
   //else
   // System.out.println(v1.get(i)+" from list1 not found in list2");
 }
 //for(int i=0;i<v2.size();i++){
 //  if(v1.indexOf(v2.get(i))<0)
 //    System.out.println(v2.get(i)+" from list2 not found in list1");
 //}
 return inters;
}
/**
 * 
 * @param set1
 * @param set2
 * @return Union of two sets
 */
public static Set UnionOfSets(Set set1, Set set2){
    Iterator it = set2.iterator();
    while(it.hasNext()){
      Object o = it.next();
      if(!set1.contains(o))
        set1.add(o);
    }
    return set1;
  }


public static Vector UnionOfVectors(Vector set1, Vector set2){
    Iterator it = set2.iterator();
    while(it.hasNext()){
      Object o = it.next();
      if(!set1.contains(o))
        set1.add(o);
    }
    return set1;
  }
/**
 * After reading XGMML files saved by Cytoscape, this function is used
 * to recover the original Node Ids (Cytoscape will put negative numbers for it).
 * Ids are read from NodeLabels.
 * @param graph
 */
public static void CorrectCytoscapeNodeIds(fr.curie.BiNoM.pathways.analysis.structure.Graph graph){
	for(int i=0;i<graph.Nodes.size();i++){
		fr.curie.BiNoM.pathways.analysis.structure.Node n = (fr.curie.BiNoM.pathways.analysis.structure.Node)graph.Nodes.get(i);
		n.Id = n.NodeLabel;
	}
	graph.recreateNodeEdgeHash();
}

public static void CorrectCytoscapeEdgeIds(fr.curie.BiNoM.pathways.analysis.structure.Graph graph){
	for(int i=0;i<graph.Edges.size();i++){
		fr.curie.BiNoM.pathways.analysis.structure.Edge e = (fr.curie.BiNoM.pathways.analysis.structure.Edge)graph.Edges.get(i);
		e.Id = e.EdgeLabel;
	}
	graph.recreateNodeEdgeHash();
}

/**
 * Sets NotesDocument.Notes content
 * @param n
 * @param value
 */
public static void setNoteHtmlBodyValue(NotesDocument.Notes n, String value){
	HtmlDocument.Html h = n.addNewHtml();
	BodyDocument.Body b = h.addNewBody();
	Utils.setValue(b,value);
    }
/**
 * 
 * @param res
 * @param pname
 * @return URI of the resource referred in res by property pname
 */
public static String getPropertyURI(Entity res, String pname){
String s = null;
List lst = res.listStatements();
Iterator it = lst.iterator();
while(it.hasNext()){
    Statement st = (Statement)it.next();
    String ss = st.toString();
    if(ss.startsWith("["))
    	ss = ss.substring(1,ss.length()-1);
    StringTokenizer stt = new StringTokenizer(ss,", ");
    stt.nextToken();
    String propname = stt.nextToken();
    String propuri = stt.nextToken();
    if(propname.endsWith(pname)) s = propuri;
    //System.out.println(ss);
}
return s;
}

public static String getPropertyURI(Resource res, String pname){
	String s = null;
	//List lst = res.listProperties();
	Iterator it = res.listProperties();
	while(it.hasNext()){
	    Statement st = (Statement)it.next();
	    String ss = st.toString();
	    if(ss.startsWith("["))
	    	ss = ss.substring(1,ss.length()-1);
	    StringTokenizer stt = new StringTokenizer(ss,", ");
	    stt.nextToken();
	    String propname = stt.nextToken();
	    String propuri = stt.nextToken();
	    if(propname.endsWith(pname)) s = propuri;
	    //System.out.println(ss);
	}
	return s;
	}

/**
 * 
 * @param res
 * @param pname
 * @return All URIs of the resources refered in res by property pname
 */
public static Vector getPropertyURIs(Entity res, String pname){
Vector v = new Vector();
List lst = res.listStatements();
Iterator it = lst.iterator();
/*try{
  System.out.println(res.getNAME());
  }catch(Exception e){
  e.printStackTrace();
  }*/
while(it.hasNext()){
    Statement st = (Statement)it.next();
    String ss = st.toString();
    if(ss.startsWith("["))
    	ss = ss.substring(1,ss.length()-1);
    StringTokenizer stt = new StringTokenizer(ss,", ");
    stt.nextToken();
    String propname = stt.nextToken();
    String propuri = stt.nextToken();
    if(propname.endsWith(pname))
	v.add(propuri);
    //System.out.println(ss);
}
return v;
}
/**
 * 
 * @param res
 * @param pname
 * @return All URIs of the resources refered in res by property pname
 */
public static Vector getPropertyURIs(UtilityClass res, String pname){
Vector v = new Vector();
List lst = res.listStatements();
Iterator it = lst.iterator();
while(it.hasNext()){
    Statement st = (Statement)it.next();
    String ss = st.toString();
    if(ss.startsWith("["))
    	ss = ss.substring(1,ss.length()-1);
    StringTokenizer stt = new StringTokenizer(ss,", ");
    stt.nextToken();
    String propname = stt.nextToken();
    String propuri = stt.nextToken();
    if(propname.endsWith(pname)) v.add(propuri);
    //System.out.println(ss);
}
return v;
}
public static int indexOfS(String ar[], String s){
	int k=-1;
	for(int i=0;i<ar.length;i++)
		if(ar[i].equals(s)){
			k = i; break;
		}
	return k;
}

public static String cutFinalAmpersand(String label){
	String res = label;
	if(res.trim().endsWith("@"))
		res = res.substring(0,res.length()-1);
	return res;
}

public static String correctHtml(String id){
	  String res = id;
	  res = Utils.replaceString(res, "\"", "");
	  res = Utils.replaceString(res, "&", "and");
	  res = Utils.replaceString(res, "<", "&lt;");
	  res = Utils.replaceString(res, ">", "&gt;");
	  res = Utils.replaceString(res, "'", "prime");
	  return res;
}

public static float calcMean(float f[]){
    //float r = 0;
    float x = 0;
    //float x2 = 0;
    for(int i=0;i<f.length;i++){
      x+=f[i];
    }
    return x/f.length;
  }

public static float calcStandardDeviation(float f[]){
    float r = 0;
    float x = 0;
    float x2 = 0;
    for(int i=0;i<f.length;i++){
      x+=f[i];
      x2+=f[i]*f[i];
    }
    x/=f.length;
    r = (float)Math.sqrt((x2/f.length-x*x)*(float)f.length/((float)f.length-1));
    return r;
  }

public static int[] SortMass(float cais[]){
	  int res[]=new int[cais.length];
	  for (int i = 0; i < res.length; i++) res[i]=i;

	  int i,j,k,inc,n=cais.length;
	  float v;

	  inc=1;
	  do {
	  	inc *= 3;
	  	inc++;
	  } while (inc <= n);

	  do {
	  	inc /= 3;
	  	for (i=inc+1;i<=n;i++) {
	  		v=cais[res[i-1]];
	  		j=i;
	                  k=res[i-1];
	  		while (cais[res[j-inc-1]]>v) {
	  			//cais[j]=cais[j-inc];
	                          res[j-1]=res[j-inc-1];
	  			j -= inc;
	  			if (j <= inc) break;
	  		}
	  		//cais[j]=v;
	                  res[j-1]=k;
	  	}
	  } while (inc > 0);

	  return res;
	}

public static java.awt.Color convertPaintToColor(java.awt.Paint paint) throws Exception{
    String colorString = paint.toString();
    
    colorString = colorString.substring(15,colorString.length()); 
    colorString = colorString.substring(0,colorString.length()-1);
    
    String rcs = "0";
    String gcs = "0";
    String bcs = "0";
    
    StringTokenizer st = new StringTokenizer(colorString,",");
    while(st.hasMoreTokens()){
    	String s = st.nextToken();
    	if(s.startsWith("r=")) rcs = s.substring(2,s.length());
    	if(s.startsWith("g=")) gcs = s.substring(2,s.length());
    	if(s.startsWith("b=")) bcs = s.substring(2,s.length());
    }
	
	java.awt.Color color = new java.awt.Color(Integer.parseInt(rcs),Integer.parseInt(gcs),Integer.parseInt(bcs)); 
	return color;
}

public static Vector<String> extractAllStringBetween(String text, String from, String to){
	HashSet<String> setoccs = new HashSet<String>(); 
	Vector<String> occs = new Vector<String>();
	char ctext[] = text.toCharArray();
	char cfrom[] = from.toCharArray();
	char cto[] = to.toCharArray();
	int i=0; int textlength = ctext.length-cfrom.length-1;
	while(i<textlength){
		boolean fromfound = true;
		for(int j=0;j<cfrom.length;j++)
			if(ctext[i+j]!=cfrom[j]){
				fromfound = false;
				break;
			}
		if(fromfound){
			i+=cfrom.length-1;
			StringBuffer sb = new StringBuffer();
			boolean tofound = false;
			i++;
			while(!tofound){
				tofound = true;
				for(int j=0;j<cto.length;j++)
					if(ctext[i+j]!=cto[j]){
						tofound = false; break;
					}
				if(!tofound){
					sb.append(ctext[i]); i++;
				}
			}
			setoccs.add(sb.toString());
		}else
			i++;
	}
	for ( Iterator<String> it = setoccs.iterator(); it.hasNext(); ){
		occs.add(it.next());
	}
	Collections.sort(occs);
	return occs;
}

public static void saveStringToFile(String s, String fn){
	try{
		FileWriter fw = new FileWriter(fn);
		fw.write(s);
		fw.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}

public static String extractFolderName(String fileName){
	String folderName = "";
	File f = new File(fileName);
	String nm = f.getName();
	folderName = folderName.substring(0, fileName.length()-nm.length());
	return folderName;
}

/*public static String getGenericComplexName(String complexName){
	String cnm = complexName;
	StringTokenizer lev1 = new StringTokenizer(cnm);
	
	return cnm;
}*/

/**
 * Calculate the factorial of a given number
 * 
 * @author ebonnet
 * 
 * @param n integer
 * @return BigInteger factorial
 */
public static BigInteger factorial(int n) {
    if (n <= 1) {
      return(new BigInteger("1"));
    } else {
      BigInteger bigN = new BigInteger(String.valueOf(n));
      return(bigN.multiply(factorial(n - 1)));
    }
  
}

public static void downloadToFile(String link, String fileName){
    try{
    java.io.FileOutputStream fos = new java.io.FileOutputStream(fileName);
    java.io.BufferedOutputStream bout = new BufferedOutputStream(fos,1024);	    
    URL yahoo = new URL(link);
    URLConnection yc = null;
    DataInputStream in = null;
    yc = yahoo.openConnection();
    in = new DataInputStream(yc.getInputStream());
    int i;
    char c;
    if(in!=null){
    while ((i = in.read()) >= 0) {
        c = (char)i;
        bout.write(c);
        }
    in.close(); }
    bout.flush();
    bout.close();
    }
    catch(Exception e){ System.out.println(e.getMessage()); }
}

public static String downloadURL(String link){
StringBuffer sb = new StringBuffer();
try{
URL yahoo = new URL(link);
URLConnection yc = null;
DataInputStream in = null;
yc = yahoo.openConnection();
in = new DataInputStream(yc.getInputStream());
int i;
char c;
if(in!=null){
while ((i = in.read()) >= 0) {
    c = (char)i;
    sb.append(c);
    }
in.close(); }
}
catch(Exception e){ System.out.println("Problem with connection to "+link); sb.append(e.getMessage()); }

return sb.toString();
}

/*public static void createJPGFile(BufferedImage im, String fileName, float quality){
	  try{
	    File file = new File(fileName);
	    FileOutputStream out = new FileOutputStream(file);
	    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	    JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(im);
	    param.setQuality(quality, true);
	    encoder.setJPEGEncodeParam(param);
	    encoder.encode(im);
	  }catch(Exception e){
	    e.printStackTrace();
	  }
	}
*/

public static class Transparency {
	  public static Image makeColorTransparent(Image im, final Color color) {
	    ImageFilter filter = new RGBImageFilter() {
	      // the color we are looking for... Alpha bits are set to opaque
	      public int markerRGB = color.getRGB() | 0xFF000000;

	      public final int filterRGB(int x, int y, int rgb) {
	        if ( ( rgb | 0xFF000000 ) == markerRGB ) {
	          // Mark the alpha bits as zero - transparent
	          return 0x00FFFFFF & rgb;
	          }
	        else {
	          // nothing to do
	          return rgb;
	          }
	        }
	      }; 
	    ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
	    return Toolkit.getDefaultToolkit().createImage(ip);
	    }
	}

public static BufferedImage getScaledImageSlow(BufferedImage image, int width, int height) throws IOException {
	 Image scaledImage = image.getScaledInstance((int)width, (int)height, Image.SCALE_SMOOTH);
     BufferedImage imageBuff = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_RGB);
     Graphics g = imageBuff.createGraphics();
     g.drawImage(scaledImage, 0, 0, new Color(0,0,0), null);
     g.dispose();
     return imageBuff;
}

public static BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
    int imageWidth  = image.getWidth();
    int imageHeight = image.getHeight();

    double scaleX = (double)width/imageWidth;
    double scaleY = (double)height/imageHeight;
    AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
    AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BICUBIC);

    return bilinearScaleOp.filter(
        image,
        new BufferedImage(width, height, image.getType()));
}

public static String cutFirstLastNonVisibleSymbols(String s){
	StringBuffer res = new StringBuffer(s);
	if(res!=null)if(res.length()>0){
	res = new StringBuffer(cutFirstNonVisibleSymbols(res.toString()));
	res = res.reverse();
	res = new StringBuffer(cutFirstNonVisibleSymbols(res.toString()));	
	res = res.reverse();
	}
	return res.toString();
}

public static String cutFirstNonVisibleSymbols(String s){
	StringBuffer res = new StringBuffer(s);
	int k = 0;
	boolean isNonVisible = true;
	while((res.length()>0)&&isNonVisible){
		char c = res.charAt(k);
		if((c=='\t')||(c=='\n')||(c==' ')||(c=='\r')){
			isNonVisible = true;
			res.deleteCharAt(k);
		}else{
			isNonVisible = false;
		}
			
	}
	return res.toString();
}


public static Vector<String> guessProteinIdentifiers(String name) throws Exception{
	
	System.out.println("Accessing genenames.org.... for "+name);
	
	Vector<String> ids = new Vector<String>();
	//String query1 = "http://www.genenames.org/cgi-bin/quick_search.pl?.cgifields=type&type=contains&num=50&search="+name+"&submit=Submit";
	String query1 = "http://www.genenames.org/cgi-bin/search?search_type=symbol&search="+name+"&submit=Submit";
	String html1 = downloadURL(query1);
	
	if(html1.contains("refused"))
		System.out.println(html1);
	//System.out.println(html1);
	
	LineNumberReader lr = new LineNumberReader(new StringReader(html1));
	String s = null;
	String HUGO = "";
	String HGNC = "";
	boolean finallyFound = false;
	while((s=lr.readLine())!=null){
		s = s.trim();
		String key = "<a href=\"/cgi-bin/gene_symbol_report?hgnc_id=HGNC:";
		if(s.startsWith(key)){
			s = s.substring(key.length(),s.length());
			StringTokenizer st = new StringTokenizer(s,"\"></");
			String _HGNC = st.nextToken();
			s = lr.readLine().trim();
			key = "<span class=\"title\"><strong>";
			s = s.substring(key.length(),s.length());
			st = new StringTokenizer(s,"\"></");
			String _HUGO = st.nextToken();

			boolean foundSynonym = false;
			if(!finallyFound)
			while(!(s=lr.readLine().trim()).equals("</a>")){
				//System.out.println(s);
				if(s.equals("<dt>Synonyms:</dt>")||s.equals("<dt>Previous symbols & names:</dt>")){
					lr.readLine();
					s = lr.readLine().trim();
					s = Utils.replaceString(s, "<dd>", "");
					s = Utils.replaceString(s, "</dd>", "");
					s = Utils.replaceString(s, "<span class=\"highlight\">", "");
					s = Utils.replaceString(s, "</span>", "");
					StringTokenizer st1 = new StringTokenizer(s,"<>\",");
					while(st1.hasMoreTokens()){
						s = st1.nextToken();
						if(s.toLowerCase().equals(name.toLowerCase())){
							foundSynonym = true;
						}
					}
				}
			}
			
			if(!foundSynonym){
				if(!HGNC.equals("")) _HGNC = "";
				if(!HUGO.equals("")) _HUGO = "";
			}else{
				finallyFound = true;
			}
			
			if(!_HGNC.equals("")) HGNC = _HGNC;
			if(!_HUGO.equals("")) HUGO = _HUGO;
		}
	}
	
	String query2 = "http://www.genenames.org/cgi-bin/gene_symbol_report?hgnc_id=HGNC:"+HGNC;
	String html2 = downloadURL(query2);
	
	lr = new LineNumberReader(new StringReader(html2));
	String key1 = "<a class=\"dialog Information";
	while((s=lr.readLine())!=null){
		s = s.trim();
		if(s.contains("External links")) key1 = "--------";
		if(s.startsWith(key1)){
			String s1 = lr.readLine();
			if(s1.contains("icon-info")){
			s = s.substring(key1.length(),s.length());
			StringTokenizer st = new StringTokenizer(s," \">");
			String type = st.nextToken();
			
			while(!(s=lr.readLine()).contains("first last"));
			String value = lr.readLine().trim();
			ids.add(type+":"+value);
			}
		}
		String key3 = "Entrez Gene: <a href=\"http://view.ncbi.nlm.nih.gov/gene/";
		if(s.indexOf(key3)>=0){
			s = s.substring(s.indexOf(key3)+key3.length(), s.length());
			StringTokenizer st = new StringTokenizer(s,"<\"");
			String type = "ENTREZ";
			String value = st.nextToken();
			ids.add(type+":"+value);
		}
		key3 = ">UniProt<";
		if(s.indexOf(key3)>=0){
			String kk = "<dd><a href=\"http://www.uniprot.org/uniprot/";
			s = s.substring(kk.length(), s.length());
			StringTokenizer st = new StringTokenizer(s,"<\"");
			String type = "UNIPROT";
			String value = st.nextToken();
			ids.add(type+":"+value);
		}
		
	}
	
	
	ids.add("HUGO:"+HUGO);
	ids.add("GENECARDS:"+HGNC);	
	ids.add("HGNC:"+HGNC);
	
	//for(int i=0;i<ids.size();i++)
	//	System.out.println("\t"+ids.get(i));
	
	return ids;
}

public static String convertHGNC2HUGOthrowInternet(String hgnc) throws Exception{
	String hugo = null;
	String query1 = "http://www.genenames.org/data/hgnc_data.php?hgnc_id="+hgnc;
	String html1 = downloadURL(query1);
	LineNumberReader lr = new LineNumberReader(new StringReader(html1));
	String s = null;
	while((s=lr.readLine())!=null){
		s = s.trim();
		if(s.contains("Approved Symbol")){
			//System.out.println(s);
			//s = lr.readLine();
			//System.out.println(s);
			s = lr.readLine();
			//System.out.println(s);
			s = Utils.replaceString(s, "<td class=\"symbol_data-data-app_sym\"><strong>", "");
			s = Utils.replaceString(s, "</strong></td>", "");
			hugo = s.trim();
			//System.out.println(hugo);
		}
	}
	return hugo;
}

public static boolean isIntegerNumber(String s){
	boolean isnumber = true;
	for(int i=0;i<s.length();i++){
		char c = s.charAt(i);
		if((c!='0')&&(c!='1')&&(c!='2')&&(c!='3')&&(c!='4')&&(c!='5')&&(c!='6')&&(c!='7')&&(c!='8')&&(c!='9')&&(c!='.')){
			isnumber = false;
			break;
		}
	}
	return isnumber;
}

public static Vector<String> getTagValues(String notes, String tag){
	Vector<String> hugos = new Vector<String>();
	StringTokenizer st = new StringTokenizer(notes," ,;\n");
	while(st.hasMoreTokens()){
		String s = st.nextToken();
		if(s.startsWith(tag+":")){
			StringTokenizer st1 = new StringTokenizer(s,":");
			st1.nextToken();
			try{
			if(st1.hasMoreTokens()){
			String hugo = st1.nextToken();
			if(!hugos.contains(hugo))
				hugos.add(hugo);
			}
			}catch(Exception e){
				System.out.println("ERROR in "+s);
				e.printStackTrace();
			}
		}
	}
	return hugos;
}

public static String replaceByList(String text, HashMap<String, String> map){
	char ctext[] = text.toCharArray();
	char ctextnew[] = new char[ctext.length+(int)(ctext.length*2)];
	// some hashing of ids by first two letters
	HashMap<String, Vector<char[]>> hash = new HashMap<String, Vector<char[]>>();
	int maxidlength = 0;
	
	Vector<String> ids = new Vector<String>();
	for(String s: map.keySet()){
		ids.add(s);
	}
	
	for(String s: ids){
		char cs[] = s.toCharArray();
		String s2 = s.substring(0, 2);
		Vector<char[]> vcs = new Vector<char[]>(); 
		if(hash.get(s2)!=null)
			vcs = hash.get(s2);
		vcs.add(cs);
		hash.put(s2, vcs);
		if(cs.length>maxidlength)
			maxidlength = cs.length;
	}
	
	int i=0; int textlength = ctext.length-maxidlength-1;
	int in=0;
	while(i<textlength){
		char h[] = new char[2];
		h[0] = ctext[i];
		h[1] = ctext[i+1];
		String s2 = new String(h);
		//if(s2.equals("ks")){
		//	System.out.println(i+"\t"+new String(ctext,i,5));
		//}
		Vector<char[]> candidates = hash.get(s2);
		if(candidates==null){ 
			ctextnew[in++] = ctext[i++];
		}else{

			boolean replacementmade = false;
			for(char cid[] :candidates){
				
			boolean idfound = true;
			
			for(int j=0;j<cid.length;j++)
				if(ctext[i+j]!=cid[j]){
					idfound = false;
					break;
				}

			if(idfound){
			boolean goodcontext = true;
			/*if((ctext[i-1]=='\"')&&(ctext[i+cid.length]=='\"'))
				goodcontext = true;
			else
			if((ctext[i-1]=='>')&&(ctext[i+cid.length]=='<'))
				goodcontext = true;
			else
			if((ctext[i-1]=='\"')&&(ctext[i+cid.length]==','))
				goodcontext = true;
			else
			if((ctext[i-1]==',')&&(ctext[i+cid.length]==','))
				goodcontext = true;
			else
			if((ctext[i-1]==',')&&(ctext[i+cid.length]=='\"'))
				goodcontext = true;*/
			char cprefix[] = map.get(new String(cid)).toCharArray();
			if(goodcontext){
				for(int k=0;k<cprefix.length;k++)
					ctextnew[in+k] = cprefix[k];
				in+=cprefix.length;
				i+=cid.length;
				replacementmade = true;
				break;
			}
		}
		}
		if(!replacementmade)
			ctextnew[in++] = ctext[i++];
			
		}
	}
	
	for(int k=textlength;k<ctext.length;k++)
		ctextnew[in++] = ctext[k];
	
	
	String res = new String(ctextnew,0,in);
	return res;
}


public static boolean checkIfInsideRectangle(float x, float y, float w, float h, float rx, float ry, float rw, float rh, float gap){
	boolean inside = false;

	if((x>rx+gap)&&(x+w+gap<rx+rw)&&(y>ry+gap)&&(y+h+gap<ry+rh)) inside = true;
	
	return inside;
}

public static boolean checkIfOutsideRectangle(float x, float y, float w, float h, float rx, float ry, float rw, float rh, float gap){
	boolean outside = false;

	if(x+w<rx-gap) outside = true;
	if(x>rx+rw+gap) outside = true;
	if(y+h<ry-gap) outside = true;
	if(y>ry+rh+gap) outside = true;
	
	return outside;
}

public static String correctIllegalCharacters(String text){
	char textc[] = text.toCharArray();
	Vector<Integer> notgood = new Vector<Integer>();
	for(int i=0;i<textc.length;i++){
		char c = textc[i];
		int ic = (int)c;
		boolean good = false;
		if((c>31)&&(c<127)||(c==9)||(c==10)||(c==11)||(c==12)||(c==13)) good = true;
		switch(ic){
			case 181: textc[i] = 'm'; good = true; break;
			case 194: textc[i] = 'a'; good = true; break;
			//case 195: good = true; break;
			//case 196: good = true; break;
			//case 197: good = true; break;
			//case 206: good = true; break;
			//case 226: good = true; break;
		}
		if(!good){
			textc[i] = '-';
			//boolean found = false;
			//for(int k: notgood)if(k==(int)c) { found = true; break; }
			//if(!found) notgood.add((int)c);
		}
			//System.out.println(c+"\t"+(int)c);
	}
	Collections.sort(notgood);
	//for(int i: notgood) System.out.println(""+i+"\t"+(char)i);
	return new String(textc);
}

public static char[] charHistogram(String text){
	char[] hist = new char[256];
	char ctext[] = text.toCharArray();
	for(char c: ctext)
		hist[c]++;
	return hist;
}

/** 
 * The tables should have first column as index.
 * @param vt1
 * @param prefix1 
 * @param vt2
 * @param prefix2
 * @param folder is used for keeping the results
 * @param correlationThreshold
 * @throws Exception
 */
public static Graph makeTableCorrelationGraph(VDataTable vt1, String prefix1, VDataTable vt2, String prefix2, float correlationThreshold, boolean chooseOnlyMaximalCorrelation) throws Exception{
	vt1.makePrimaryHash(vt1.fieldNames[0]);
	vt2.makePrimaryHash(vt2.fieldNames[0]);
	// make common list of objects
	HashSet<String> names_set = new HashSet<String>();
	Vector<String> names = new Vector<String>();
	for(int i=0;i<vt1.rowCount;i++){
		String name1 = vt1.stringTable[i][0];
		if(vt2.tableHashPrimary.get(name1)!=null){
			if(!names_set.contains(name1))
				names_set.add(name1);
		}
	}
	for(String s: names_set)
		names.add(s);
	Collections.sort(names);
	System.out.println(names.size()+" common objects are found.");
	
	//FileWriter fw  = new FileWriter(folder+prefix1+"_"+prefix2+".txt");
	//fw.write("FIELD1\tFIELD2\tCORRELATION\tCORRELATION_ABS\n");
	
	Graph graph = new Graph();
	
	for(int i=0;i<vt1.colCount;i++)if(vt1.fieldTypes[i]==vt1.NUMERICAL){
		String fni = vt1.fieldNames[i]+"_"+prefix1;
		Node ni = graph.getCreateNode(fni);
		ni.setAttributeValueUnique("FIELD", vt1.fieldNames[i], Attribute.ATTRIBUTE_TYPE_STRING);
		ni.setAttributeValueUnique("SET", prefix1, Attribute.ATTRIBUTE_TYPE_STRING);
		int maxi = -1;
		float maxabscorr = 0f;
		float maxcorr = 0f;
		for(int j=0;j<vt2.colCount;j++)if(vt2.fieldTypes[j]==vt2.NUMERICAL){
			String fnj = vt2.fieldNames[j]+"_"+prefix2;
			Node nj = graph.getCreateNode(fnj);
			nj.setAttributeValueUnique("FIELD", vt2.fieldNames[j], Attribute.ATTRIBUTE_TYPE_STRING);
			nj.setAttributeValueUnique("SET", prefix2, Attribute.ATTRIBUTE_TYPE_STRING);
			float xi[] = new float[names.size()];
			float xj[] = new float[names.size()];
			for(int k=0;k<names.size();k++){
				xi[k] = Float.parseFloat(vt1.stringTable[vt1.tableHashPrimary.get(names.get(k)).get(0)][i]);
				xj[k] = Float.parseFloat(vt2.stringTable[vt2.tableHashPrimary.get(names.get(k)).get(0)][j]);
			}
			float corr = VSimpleFunctions.calcCorrelationCoeff(xi, xj);
			float abscorr = Math.abs(VSimpleFunctions.calcCorrelationCoeff(xi, xj));
			System.out.print(corr+"\t");
			if(!chooseOnlyMaximalCorrelation)
			if(abscorr>=correlationThreshold){
			//	fw.write(fni+"_"+prefix1+"\t"+fnj+"_"+prefix2+"\t"+corr+"\t"+Math.abs(corr)+"\n");
				Edge e = graph.getCreateEdge(fni+"__"+fnj);
				e.Node1 = ni;
				e.Node2 = nj;
				e.setAttributeValueUnique("CORR", ""+corr, Attribute.ATTRIBUTE_TYPE_REAL);
				e.setAttributeValueUnique("ABSCORR", ""+abscorr, Attribute.ATTRIBUTE_TYPE_REAL);
			}
			if(abscorr>maxabscorr){
				maxabscorr = abscorr;
				maxi = j;
			}
		}
		System.out.println();
		if(chooseOnlyMaximalCorrelation){
			if(maxabscorr>=correlationThreshold){
			//	fw.write(fni+"_"+prefix1+"\t"+fnj+"_"+prefix2+"\t"+corr+"\t"+Math.abs(corr)+"\n");
				String fnj = vt2.fieldNames[maxi]+"_"+prefix2;
				Node nj = graph.getCreateNode(fnj);
				Edge e = graph.getCreateEdge(fni+"__"+fnj);
				e.Node1 = ni;
				e.Node2 = nj;
				e.setAttributeValueUnique("CORR", ""+maxcorr, Attribute.ATTRIBUTE_TYPE_REAL);
				e.setAttributeValueUnique("ABSCORR", ""+maxabscorr, Attribute.ATTRIBUTE_TYPE_REAL);
			}
		}
	}
	return graph;
	//fw.close();
}

public static float calcCorrelationCoeff(float m1[], float m2[]){
float res = 0;
int N = m1.length;
float xy = 0f, x2 = 0f, y2 = 0f, x = 0f, y = 0f;
for(int i=0;i<N;i++){
  xy+=m1[i]*m2[i];
  x+=m1[i];
  y+=m2[i];
  x2+=m1[i]*m1[i];
  y2+=m2[i]*m2[i];
}
double disp = Math.sqrt((x2-x*x/N)*(y2-y*y/N));
if(Math.abs(disp)<1e-20)
	  res = 0;
else
    res = (float)((xy-x*y/N)/disp);
return res;
}

public static float calcMedian(float f[]){
    float r = 0;
    int ind[] = Algorithms.SortMass(f);
    if(f.length!=0){
    if(f.length==2*(int)(0.5f*f.length)){
      int mid1 = (int)(0.5f*f.length)-1;
      int mid2 = (int)(0.5f*f.length);
      r = 0.5f*(f[ind[mid1]]+f[ind[mid2]]);
    }else{
      int mid = (int)(0.5f*f.length);
      r = f[ind[mid]];
    }}
    return r;
  }




}