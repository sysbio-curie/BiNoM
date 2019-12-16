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

import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.shared.*;

/**
 * BioPAX Naming Service. Used to construct short, unique and 'meaningfull' names for BioPAX objects
 * @author Andrei Zinovyev
 *
 */
public class BioPAXNamingService {

/**
 * Map from complete uri to the names generated 
 */
private HashMap uri2name = new HashMap();
/**
 * Map from complete uri to the ids generated 
 */
private HashMap uri2id = new HashMap();
/**
 * Map from ids to the uris 
 */
private HashMap id2uri = new HashMap();
/**
 * Map from the names to the uris 
 */
private HashMap name2uri = new HashMap();
/**
 * Names of utilities
 */
public HashMap genericUtilityName = new HashMap();

/**
 * Map from uris to complex object
 */
private HashMap complexList = new HashMap();
/**
 * Map from uris to utilityClass object 
 */
private HashMap sequenceParticipantsList = new HashMap();
private HashMap physicalEntityParticipantsList = new HashMap();


    public BioPAXNamingService() {
    }

    public BioPAXNamingService(BioPAX biopax, boolean verbose) throws Exception {
	generateNames(biopax, verbose);
    }

    public BioPAXNamingService(BioPAX biopax) throws Exception {
	this(biopax, false);
    }

/**
 * Function for generating names
 * @param biopax
 * @param verbose if true then some log is provided
 * @throws Exception
 */
public void generateNames(BioPAX biopax, boolean verbose) throws Exception{

  HashMap uri2name = new HashMap();
  HashMap uri2id = new HashMap();
  HashMap id2uri = new HashMap();
  HashMap name2uri = new HashMap();
  HashMap genericUtilityName = new HashMap();


  List allcompl = biopax_DASH_level2_DOT_owlFactory.getAllcomplex(biopax.model);
  Iterator allcompi = allcompl.iterator();
  while(allcompi.hasNext()){
    complex compl = (complex)allcompi.next();
    complexList.put(compl.uri(),compl);
  }

  List el = biopax_DASH_level2_DOT_owlFactory.getAllcatalysis(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllmodulation(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAlltransportWithBiochemicalReaction(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllbiochemicalReaction(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllcomplexAssembly(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAlltransport(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));

  el = biopax_DASH_level2_DOT_owlFactory.getAllcontrol(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllconversion(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllphysicalInteraction(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllinteraction(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));


  el = biopax_DASH_level2_DOT_owlFactory.getAllpathway(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllcomplex(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAlldna(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));

  el = biopax_DASH_level2_DOT_owlFactory.getAllprotein(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));

  el = biopax_DASH_level2_DOT_owlFactory.getAllrna(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllsmallMolecule(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllphysicalEntity(biopax.model);
  for(int i=0;i<el.size();i++) putEntity((entity)el.get(i));


  el = biopax_DASH_level2_DOT_owlFactory.getAllchemicalStructure(biopax.model);
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllconfidence(biopax.model);
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllexperimentalForm(biopax.model);
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllexternalReferenceUtilityClass(biopax.model);
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllbioSource(biopax.model);
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAlldataSource(biopax.model);
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllopenControlledVocabulary(biopax.model);
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllxref(biopax.model);
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllpublicationXref(biopax.model);
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllrelationshipXref(biopax.model);
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllunificationXref(biopax.model);
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllpathwayStep(biopax.model);
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));

  el = biopax_DASH_level2_DOT_owlFactory.getAllsequenceFeature(biopax.model);
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllsequenceParticipant(biopax.model);
  for(int i=0;i<el.size();i++) {
	  sequenceParticipantsList.put(((utilityClass)el.get(i)).uri(),el.get(i));
	  physicalEntityParticipantsList.put(((utilityClass)el.get(i)).uri(),el.get(i));
  }
  for(int i=0;i<el.size();i++) { 
	  putUtilityClass((utilityClass)el.get(i)); 
  }

  el = biopax_DASH_level2_DOT_owlFactory.getAllphysicalEntityParticipant(biopax.model);
  for(int i=0;i<el.size();i++) {
	  physicalEntityParticipantsList.put(((utilityClass)el.get(i)).uri(),el.get(i));
  }
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));

  el = biopax_DASH_level2_DOT_owlFactory.getAllsequenceInterval(biopax.model);
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllsequenceSite(biopax.model);
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));
  el = biopax_DASH_level2_DOT_owlFactory.getAllsequenceLocation(biopax.model);
  for(int i=0;i<el.size();i++) putUtilityClass((utilityClass)el.get(i));

  if(verbose){
    el = biopax_DASH_level2_DOT_owlFactory.getAllprotein(biopax.model);
    for(int i=0;i<el.size();i++) printEntityName((entity)el.get(i));
    el = biopax_DASH_level2_DOT_owlFactory.getAllcomplex(biopax.model);
    for(int i=0;i<el.size();i++) printEntityName((entity)el.get(i));
    el = biopax_DASH_level2_DOT_owlFactory.getAlldna(biopax.model);
    for(int i=0;i<el.size();i++) printEntityName((entity)el.get(i));
    el = biopax_DASH_level2_DOT_owlFactory.getAllrna(biopax.model);
    for(int i=0;i<el.size();i++) printEntityName((entity)el.get(i));
    el = biopax_DASH_level2_DOT_owlFactory.getAllsmallMolecule(biopax.model);
    for(int i=0;i<el.size();i++) printEntityName((entity)el.get(i));
    el = biopax_DASH_level2_DOT_owlFactory.getAllpathway(biopax.model);
    for(int i=0;i<el.size();i++) printEntityName((entity)el.get(i));


    el = biopax_DASH_level2_DOT_owlFactory.getAllphysicalEntityParticipant(biopax.model);
    for(int i=0;i<el.size();i++) printUtilityClassName((utilityClass)el.get(i));
    el = biopax_DASH_level2_DOT_owlFactory.getAllsequenceParticipant(biopax.model);
    for(int i=0;i<el.size();i++) printUtilityClassName((utilityClass)el.get(i));
    el = biopax_DASH_level2_DOT_owlFactory.getAllsequenceFeature(biopax.model);
    for(int i=0;i<el.size();i++) printUtilityClassName((utilityClass)el.get(i));
    el = biopax_DASH_level2_DOT_owlFactory.getAllpathwayStep(biopax.model);
    for(int i=0;i<el.size();i++) printUtilityClassName((utilityClass)el.get(i));


 }


}

/**
 * Adds an entity for naming
 * @param e
 * @throws Exception
 */
public void putEntity(entity e) throws Exception{
    String id = createEntityId(e);
    String name = createEntityName(e);
    putID(e.uri(),id);
    putName(e.uri(),name);
}

/*public void putEntityVerbose(entity e) throws Exception{
    String id = createEntityId(e);
    String name = createEntityName(e);
    String nm = e.getClass().getName();
    nm = Utils.replaceString(nm,"fr.curie.BiNoM.pathways.biopax.","");
    nm = Utils.replaceString(nm,"Impl","");
    putID(e.uri(),id);
    putName(e.uri(),name);
}*/

/**
 * Adds an utilityClass for naming
 */
public void putUtilityClass(utilityClass e) throws Exception{
    String id = createUtilityId(e);
    String name = createUtilityName(e);
    putID(e.uri(),id);
    putName(e.uri(),name);
    if(!genericUtilityName.containsKey(e.uri()))
      genericUtilityName.put(e.uri(),name);
}
/*public void putUtilityClassVerbose(utilityClass e) throws Exception{
    String id = createUtilityId(e);
    String name = createUtilityName(e);
    String nm = e.getClass().getName();
    if(name.equals("microtubules@plasma_membrane"))
      System.out.println();
    nm = Utils.replaceString(nm,"fr.curie.BiNoM.pathways.biopax.","");
    nm = Utils.replaceString(nm,"Impl","");
    System.out.println(nm+"\t"+name+"\t\t\t"+id);
    putID(e.uri(),id);
    putName(e.uri(),name);
}*/

public void printEntityName(entity e){
  String nm = e.getClass().getName();
  nm = Utils.replaceString(nm,"fr.curie.BiNoM.pathways.biopax.","");
  nm = Utils.replaceString(nm,"Impl","");
  System.out.println(nm+"\t"+getNameByUri(e.uri())+"\t\t\t"+getIdByUri(e.uri()));
}
public void printUtilityClassName(utilityClass e){
  String nm = e.getClass().getName();
  nm = Utils.replaceString(nm,"fr.curie.BiNoM.pathways.biopax.","");
  nm = Utils.replaceString(nm,"Impl","");
  System.out.println(nm+"\t"+getNameByUri(e.uri())+"\t\t\t"+getIdByUri(e.uri()));
}

public void putID(String uri, String id){
  String iid = (String)uri2id.get(uri);
  if(iid==null){
    uri2id.put(uri,id);
    uri2id.put(Utils.cutUri(uri),id);

    String iuri = (String)id2uri.get(id);
    if(iuri==null){
      id2uri.put(id,uri);
    }
    else{
      System.out.println("BADLY CREATED ID: "+id);
      id2uri.put(generateUniqueID(id,id2uri),uri);
    }

  }
  else{
    //System.out.println("SOMETHING STRANGE: URI "+uri+" already specifies id "+id);
  }
}

/**
 * Adds a (uri,name) pair 
 * @param uri
 * @param name
 */
public void putName(String uri, String name){
  String iname = (String)uri2name.get(uri);
  if(iname==null){
    uri2name.put(uri,name);
    uri2name.put(Utils.cutUri(uri),name);

    String iuri = (String)name2uri.get(name);
    if(iuri==null){
      name2uri.put(name,uri);
    }
    else{
      String prevName = (String)uri2name.get(iuri);
      String prevAdd = "["+(String)uri2id.get(iuri)+"]";
      if(!prevName.endsWith(prevAdd))
        prevName += prevAdd;
      name += "["+(String)uri2id.get(uri)+"]";
      name2uri.put(prevName,iuri);
      name2uri.put(name,uri);
      uri2name.put(iuri,prevName);
      uri2name.put(Utils.cutUri(iuri),prevName);
      uri2name.put(uri,name);
      uri2name.put(Utils.cutUri(uri),name);
    }

  }
  else{
    //System.out.println("SOMETHING STRANGE: URI "+uri+" already specifies name "+name);
  }
}
/**
 * Adds # symbols until the id will become unique
 * @param id
 * @param hm
 * @return
 */
public String generateUniqueID(String id, HashMap hm){
  String r = id;
  for(int i=1;i<100000;i++){
    if(!hm.containsKey(id+"#"+i)){
      r = id+"#"+i; break;
    }
  }
  return r;
}

/**
 * Used to get name of the thing with uri
 * @param uri
 * @return
 */
public String getNameByUri(String uri){
  String s = (String)uri2name.get(uri);
  if(s==null) System.out.println("WARNING!!! BIOPAX NAMING SYSTEM FAULT! Cannot get name for "+uri);
  else
  s = Utils.correctName(s);
  return s;
}
/**
 * Used to get id of the thing with uri
 * @param uri
 * @return
 */
public String getIdByUri(String uri){
  String s = (String)uri2id.get(uri);
  if(s==null) System.out.println("WARNING!!! BIOPAX NAMING SYSTEM FAULT! Cannot get id for "+uri);
  return s;
}
public String getUriById(String id){
  String s = (String)id2uri.get(id);
  if(s==null) System.out.println("WARNING!!! BIOPAX NAMING SYSTEM FAULT! Cannot get uri for "+id);
  return s;
}
public String getUriByName(String name){
  String s = (String)name2uri.get(name);
  if(s==null) System.out.println("WARNING!!! BIOPAX NAMING SYSTEM FAULT! Cannot get uri for "+name);
  return s;
}

/**
 * Creates a name for entity pe
 * @param pe
 * @return
 * @throws Exception
 */
  public String createEntityName(entity pe) throws Exception{
    //Utils.printPropertyURIs(pe);
	if(pe instanceof pathway)
		System.out.println();
    String name = pe.getSHORT_DASH_NAME();
    if((name==null)||(name.trim().equals("")))
      name = (String)pe.getNAME();
    //if((name!=null)&&(name.length()>100))
    //  System.out.println("LONG NAME: "+name);
    if((name==null)||((name.length()>100))||(name.equals(""))){
    Iterator sn = pe.getSYNONYMS();
    if(sn.hasNext()){
         Vector names = new Vector();
         String minname = ""; int minlen = 1000;
         while(sn.hasNext()){
           String s = (String)sn.next();
           if(s.length()<minlen){
             minname = s; minlen = s.length();
           }
         }
         name = minname;
    }
    }
    if((name==null)||(name.trim().equals("")))
      name = Utils.cutUri(pe.uri());
    if(pe instanceof dna)
      name = "g"+name;
    if(pe instanceof rna)
      name = "r"+name;

    //if(pe instanceof complex)
    //  name = createNameForComplex((complex)pe);

    return name;
  }

  /**
   * Creates entity id 
   * @param e
   * @return
   * @throws Exception
   */
public String createEntityId(entity e)  throws Exception{
  String id = "";
  id = Utils.cutUri(e.uri());
  return id;
}
/**
 * Creates a name for utilityClass object
 * @param e
 * @return
 * @throws Exception
 */
public String createUtilityName(utilityClass e)  throws Exception{
  //String name = createUtilityId(e);
  String name = null;
  if(e instanceof openControlledVocabulary){
    String s = getVocabularyTerm(((openControlledVocabulary)e).getTERM());
    if(!s.equals("")) name = s;
  }
  if(e instanceof xref){
    xref px = (xref)e;
    if((px.getDB()!=null)&&(px.getID()!=null))
      name = px.getID()+"@"+px.getDB();
    String sa = "";
  }
  if(e instanceof publicationXref){
    publicationXref px = (publicationXref)e;
    String s = "";
    if((px.getDB()!=null)&&(px.getID()!=null))
      s = px.getID()+"@"+px.getDB();
    String sa = "";
    if(px.getAUTHORS()!=null){
      Iterator it = px.getAUTHORS();
      int k = 0;
      while(it.hasNext()){
        String aname = (String)it.next();
        int ic = aname.indexOf(",");
        if(ic>0)
          aname = aname.substring(0,ic);
        sa+=aname+" "; k++;
        if(k>2) break;
      }
    }
    if(px.getSOURCE()!=null)
      if(!getVocabularyTerm(px.getSOURCE()).equals(""))
        sa+="/"+getVocabularyTerm(px.getSOURCE())+" ";
    if(px.getYEAR()!=null)
      if(!px.getYEAR().equals(""))
        sa+="/"+px.getYEAR();
    if(s.equals(""))
      name = sa;
    else if(!sa.equals(""))
      name = s + "/" + sa;
  }
  if(e instanceof sequenceFeature){
    sequenceFeature ft = (sequenceFeature)e;
    if(ft.getSHORT_DASH_NAME()!=null)
      name = ft.getSHORT_DASH_NAME();
    else
      name = ft.getNAME();
    if((name==null)||(name.equals(""))){
      if(ft.getFEATURE_DASH_TYPE()!=null){
        Iterator it = ft.getFEATURE_DASH_TYPE().getTERM();
        if(it!=null)
          name = BioPAXNamingService.getVocabularyTerm(it);
      }
    }
    //if(ft.getFEATURE_DASH_LOCATION())
  }
  if(e instanceof physicalEntityParticipant){
    name = createNameForParticipant((physicalEntityParticipant)e,true);
  }
  if(e instanceof sequenceParticipant){
    name = createNameForParticipant((sequenceParticipant)e,true);
  }

  if((name==null)||(name.equals(""))){
  Iterator it = e.getCOMMENT();
  while(it.hasNext()){
    String comm = (String)it.next();
    if(comm.indexOf("NAME:")>=0){
      int k = comm.indexOf("NAME:");
      name = comm.substring(k+4,comm.length()).trim();
    }
  }
  }

  if((name==null)||(name.equals("")))
    name = createUtilityId(e);
  name = Utils.correctName(name);
  return name;
}
/**
 * Creates an id for utilityClass
 * @param e
 * @return
 * @throws Exception
 */
public static String createUtilityId(utilityClass e) throws Exception{
  String id = "";
  id = Utils.cutUri(e.uri());
  return id;
}
/**
 * Generates a name for complex object
 * @param compl
 * @return
 * @throws Exception
 */
public String createNameForComplex(complex compl) throws Exception{
  String name = "";
  Iterator it = compl.getCOMPONENTS();
  if(it.hasNext()){
  Vector names = new Vector();
  while(it.hasNext()){
    physicalEntityParticipant pep = null;
    com.ibm.adtech.jastor.Thing next = (com.ibm.adtech.jastor.Thing)it.next();
    String uri = next.uri();
    /*String clname = next.getClass().getName();
    if(clname.indexOf("sequenceParticipant")>=0)
        pep = (sequenceParticipant)next;
    else
      pep = (physicalEntityParticipant)next;
    */
    	
    if(sequenceParticipantsList.get(uri)!=null)
      pep = (sequenceParticipant)sequenceParticipantsList.get(uri);
    else
      pep = (physicalEntityParticipant)next;
    /*List lst = next.listStatements();
    for(int i=0;i<lst.size();i++)
      System.out.print(((Statement)lst.get(i)).toString()+"\t");
    System.out.println();*/
    //sequenceParticipant pep = (sequenceParticipant)it.next();
    //System.out.println("Component uri :"+Utils.cutUri(pep.uri())+"\t"+(next).getClass().getName());
    String cname = createNameForParticipant(pep,false);
    int stoich = 1;
    try{
    //if(pep.getSTOICHIOMETRIC_DASH_COEFFICIENT()!=null){
    	//stoich = pep.getSTOICHIOMETRIC_DASH_COEFFICIENT().intValue();
    	Vector v = Utils.getPropertyURIs(pep, "STOICHIOMETRIC-COEFFICIENT");
    	if(v.size()>0){
    		String ss = (String)v.get(0);
    		if(ss.startsWith("\""))
    			ss = ss.substring(1,ss.length());
        	if(ss.endsWith("\""))
        		ss = ss.substring(0,ss.length()-1);
        	if(ss.indexOf("\"^^")>=0)
            ss = ss.substring(0,ss.indexOf("\"^^"));
          if(ss.indexOf(".")>= 0)
            ss = ss.substring(0, ss.indexOf("."));
        	stoich = Integer.parseInt(ss);
    	}
    //}
    }catch(Exception e){
    	Vector v = Utils.getPropertyURIs(pep, "STOICHIOMETRIC-COEFFICIENT");
    	if(v.size()>0)
    		System.out.println("ERROR: in trying to access STOICHIOMETRIC_COEFFICIENT ("+(String)v.get(0)+") for "+compl.uri());
    	else{
    		System.out.println("ERROR: in trying to access STOICHIOMETRIC_COEFFICIENT for "+compl.uri());
    		Iterator its = pep.listStatements().iterator();
    		while(its.hasNext()){
    			System.out.println(((Statement)its.next()).toString());
    		}
    	}
    }
    for(int kk=0;kk<stoich;kk++)
    	names.add(cname);
  }
  Collections.sort(names);
  for(int i=0;i<names.size();i++)
    name+=(String)names.get(i)+":";
  name = name.substring(0,name.length()-1);
  }else{
    name = getNameByUri(compl.uri());
    if(name.length()>0) name = name.substring(0,name.length()-1);
  }
  return name;
}

/**
 * Creates a name for physicalEntityParticipant 
 * @param participant
 * @param addCompartment
 * @return
 * @throws Exception
 */
public String createNameForParticipant(physicalEntityParticipant participant, boolean addCompartment) throws Exception{
  String name = "";
  physicalEntity pe = participant.getPHYSICAL_DASH_ENTITY();
  if(pe==null){
    System.out.println("WARNING!!! Null entity for a participant "+Utils.cutUri(participant.uri()));
    name = Utils.cutUri(participant.uri());
  }
  else{
  //System.out.println(Utils.cutUri(pe.uri()));
  if(complexList.get(pe.uri())!=null){
    //System.out.println(Utils.cutUri(pe.uri()));
    name = createNameForComplex((complex)complexList.get(pe.uri()));
  }else{
    name = getNameByUri(pe.uri());
  // Modifications
  if(participant instanceof sequenceParticipant){
    sequenceParticipant spart = (sequenceParticipant)participant;
    Iterator fts = spart.getSEQUENCE_DASH_FEATURE_DASH_LIST();
    while(fts.hasNext()){
      sequenceFeature ft = (sequenceFeature)fts.next();
      name+="|"+createUtilityName(ft);
    }
  }
  }
  //if(name==null)
  //  System.out.println("WARNING!!! SOMETHING WRONG WITH THE NAMING SYSTEM FOR "+Utils.cutUri(participant.uri()));
  // Compartment
  openControlledVocabulary voc = participant.getCELLULAR_DASH_LOCATION();
  if(voc!=null){
  String compartment = createUtilityName(voc);
  if(!compartment.equals("")){
    if(name.indexOf(compartment)<0){
      if(addCompartment)
         name+="@"+compartment;
    }else{
      name = Utils.replaceString(name,compartment,"");
      if(addCompartment)
         name+="@"+compartment;
    }
  }
  }
  else
    if(addCompartment)
      //name+="@notdef";
    	name+="@";
  }
  return name;
}

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

public String getEntityName(String uri) throws Exception{
	String res = null;
	if(physicalEntityParticipantsList.get(uri)!=null){
		physicalEntityParticipant pep = (physicalEntityParticipant)physicalEntityParticipantsList.get(uri);
		physicalEntity ent = pep.getPHYSICAL_DASH_ENTITY();
		if(ent!=null)
			res = createEntityName(ent);
	}
	return res;
}


}
