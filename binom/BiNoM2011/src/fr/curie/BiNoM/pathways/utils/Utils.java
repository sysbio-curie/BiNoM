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

import java.io.*;
import java.math.BigInteger;
import java.util.*;

import org.apache.xmlbeans.*;
import org.sbml.x2001.ns.celldesigner.BodyDocument;
import org.sbml.x2001.ns.celldesigner.HtmlDocument;
import org.sbml.x2001.ns.celldesigner.NotesDocument;

import edu.rpi.cs.xgmml.*;
import fr.curie.BiNoM.pathways.biopax.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.shared.*;

/**
 * Set of simple functions
 * @author Andrei Zinovyev
 *
 */
@SuppressWarnings("unchecked")
public class Utils {
	
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
	name = Utils.replaceString(name,"/","_");
	name = Utils.replaceString(name,"|","_");
	name = Utils.replaceString(name,"*","_");
	name = Utils.replaceString(name,"-","_");
	name = Utils.replaceString(name,"[","_");
	name = Utils.replaceString(name,"]","_");
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
   else
     System.out.println(v1.get(i)+" from list1 not found in list2");
 }
 for(int i=0;i<v2.size();i++){
   if(v1.indexOf(v2.get(i))<0)
     System.out.println(v2.get(i)+" from list2 not found in list1");
 }
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
			occs.add(sb.toString());
		}else
			i++;
	}
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
  
}}