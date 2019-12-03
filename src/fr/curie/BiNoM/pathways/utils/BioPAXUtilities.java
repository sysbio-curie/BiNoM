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

import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;

import fr.curie.BiNoM.pathways.biopax.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
//import com.hp.hpl.jena.util.*;
//import com.hp.hpl.jena.shared.*;
import edu.rpi.cs.xgmml.*;

/**
 * Set of utilities for manipulating com.hp.hpl.jena.rdf.model.Model objects
 * @author Andrei Zinovyev
 *
 */
@SuppressWarnings("unchecked")
public class BioPAXUtilities {


  /**
   * 
   * @param thing
   * @return Number of statements describing the thing
   */	
  public static int numberOfStatements(com.ibm.adtech.jastor.Thing thing){
    return  thing.listStatements().size();
  }
  
  public static int numberOfStatements(Model source){
	    return  source.listStatements().toList().size();
  }
  
  
  /**
   * Extract a part of model which is necessary to define all things with uris
   * @param source
   * @param uris
   * @param namespace
   * @param importString
   * @return
   * @throws Exception
   */
  public static Model extractURIwithAllLinks(Model source, Vector uris, String namespace, String importString) throws Exception{
    //Model res = ModelFactory.createDefaultModel();
    OntModel res = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,null);
    res.setNsPrefix("",namespace);
    Ontology _ontology = res.createOntology("");
    Resource _imprt = res.getResource(importString);
    _ontology.setImport(_imprt);

    for(int i=0;i<uris.size();i++){
      String uri = (String)uris.get(i);
      copyURIwithAllLinks(namespace,uri,source,res);
    }
    return res;
  }

  /**
   * Extract a part of model which is necessary to define a things with uri 
   * @param namespace
   * @param uri
   * @param modelfrom
   * @param modelto
   * @throws Exception
   */
  public static void copyURIwithAllLinks(String namespace, String uri, Model modelfrom, Model modelto) throws Exception{
     HashMap uris = new HashMap();
     String defNsPrefix = getDefaultNsPrefix(modelfrom);
     if(!defNsPrefix.equals(""))
    	 namespace = defNsPrefix; 
     //com.ibm.adtech.jastor.Thing thing = biopax_DASH_level3_DOT_owlFactory.getThing(namespace+uri,modelfrom);
     com.ibm.adtech.jastor.Thing thing = biopax_DASH_level3_DOT_owlFactory.getThing(uri,modelfrom);
     System.out.println(thing.uri()+"\t"+numberOfStatements(thing));
     //com.ibm.adtech.jastor.Thing thingto = biopax_DASH_level3_DOT_owlFactory.getThing(namespace+uri,modelto);
     //if(thingto==null){
     //System.out.println(thing.uri());
     //modelto.add(thing.listStatements());
     //System.out.println("Resource "+thing.toString());
     copyResourceRecursive(namespace,modelfrom,modelto,thing,uris);
     //}
  }

  private static void copyResourceRecursive(String namespace, Model modelfrom, Model modelto, com.ibm.adtech.jastor.Thing obj, HashMap uris) throws Exception{
	List sl = obj.listStatements();
    modelto.add(sl);
    uris.put(obj.uri(),obj);
    for(int i=0;i<sl.size();i++){
      Statement st = (Statement)sl.get(i);
      RDFNode nod = st.getObject();
      //System.out.print(st.getPredicate().toString()+"\t");
      if(nod.isURIResource()){
        //System.out.println("URIResource: "+nod.toString());
        com.ibm.adtech.jastor.Thing thing = biopax_DASH_level3_DOT_owlFactory.getThing(nod.toString(),modelfrom);
        if(thing!=null)if(uris.get(thing.uri())==null){
           copyResourceRecursive(namespace,modelfrom,modelto,thing,uris);
        }
      }
    }

    }

  /**
   * Save OWL model to the file
   * @param model
   * @param fn
   * @param biopaxString
   * @throws Exception
   */
  public static void saveModel(Model model, String fn, String biopaxString) throws Exception{
    PrintWriter out = new PrintWriter(new FileOutputStream(fn,false));
    RDFWriter writer = model.getWriter("RDF/XML-ABBREV") ;
    writer.setProperty("xmlbase",biopaxString);
    writer.write(model,out,biopaxString);
  }
  
  public static void saveModel(Model model, String fn) throws Exception{
	    PrintWriter out = new PrintWriter(new FileOutputStream(fn,false));
	    RDFWriter writer = model.getWriter("RDF/XML-ABBREV") ;
	    writer.setProperty("xmlbase",BioPAX.biopaxString);
	    writer.write(model,out,BioPAX.biopaxString);
	  }
  
  /**
   * Load the OWL model from file
   * @param fn
   * @param namespaceString
   * @param importString
   * @return
   * @throws Exception
   */
  public static Model loadModel(String fn, String namespaceString, String importString) throws Exception{
    Model res = ModelFactory.createDefaultModel();
    FileInputStream fin = new FileInputStream(fn);
    //System.setErr(new PrintStream(new FileOutputStream(fn+".err")));
    try{
    res.read(fin,"");
    }catch(Exception e){
    	e.printStackTrace();
    	//System.setErr(System.out);
    	throw e;
    }
    System.setErr(System.out);
    /*OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,null);
    model.setNsPrefix("",namespaceString);
    Ontology ontology = model.createOntology("");
    Resource imprt = model.getResource(importString);
    ontology.setImport(imprt);*/
    return res;
  }
  /**
   * Clones the source Model
   * @param source
   * @param namespaceString
   * @param importString
   * @return
   */
  public static Model makeCopy(Model source, String namespaceString, String importString){
    Model res = ModelFactory.createDefaultModel();

    /*OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,null);
    model.setNsPrefix("",namespaceString);
    Ontology ontology = model.createOntology("");
    Resource imprt = model.getResource(importString);
    ontology.setImport(imprt);*/
    res.setNsPrefix("",namespaceString);
    res.add(source.listStatements());
    return res;
  }
  
  /**
   * From all elements in graph BIOPAX_URI attribute values are used to extract a part of the source  
   * @param source
   * @param graph
   * @param namespace
   * @param importString
   * @return
   * @throws Exception
   */
  public static OntModel extractFromModel(Model source, fr.curie.BiNoM.pathways.analysis.structure.Graph graph, String namespace, String importString)  throws Exception{
	  HashMap hm = new HashMap();
	  for(int i=0;i<graph.Nodes.size();i++){
		  Node n = (Node)graph.Nodes.get(i);
		  String typ = n.getFirstAttributeValue("BIOPAX_NODE_TYPE");
		  if(typ==null)
			  typ = "unknown_node_type";
		  Vector v = (Vector)hm.get(typ);
		  if(v==null)
			  v = new Vector();
		  Vector uris = n.getAttributeValues("BIOPAX_URI");
		  for(int j=0;j<uris.size();j++){
			  v.add(Utils.cutUri((String)uris.get(j)));
		  }
		  //System.out.println("Found: "+typ+"\t"+n.getFirstAttributeValue("BIOPAX_URI"));
		  hm.put(typ, v);
	  }
	  for(int i=0;i<graph.Edges.size();i++){
		  Edge n = (Edge)graph.Edges.get(i);
		  String typ = n.getFirstAttributeValue("BIOPAX_EDGE_TYPE");
		  if(typ==null)
			  typ = "unknown_edge_type";
		  Vector v = (Vector)hm.get(typ);
		  if(v==null)
			  v = new Vector();
		  //String uri = n.getFirstAttributeValue("BIOPAX_URI");
		  Vector uris = n.getAttributeValues("BIOPAX_URI");
		  for(int j=0;j<uris.size();j++){
			  String uri = (String)uris.get(j);
			  if(uri!=null){
				  v.add(Utils.cutUri(uri));
				  hm.put(typ, v);
			  }
		  }
	  }
	  Vector ids = new Vector();
	  Iterator it = hm.keySet().iterator();
	  while(it.hasNext()){
		  String typ = (String)it.next();
		  Vector v = (Vector)hm.get(typ);
		  System.out.println(typ+"\t"+v.size());
		  for(int j=0;j<v.size();j++){
			  ids.add(v.get(j));
			  //System.out.print((String)v.get(j)+"\t");
		  }
		  //System.out.println();
	  }
	  OntModel res = extractFromModel(source,ids,namespace,importString);
	  //Model res = null;
	  return res;
  }
  
  public static OntModel extractFromModel(Model source, fr.curie.BiNoM.pathways.analysis.structure.Graph graph)  throws Exception{
	  return extractFromModel(source,graph, BioPAX.namespaceString, BioPAX.importString);
  }
  
  public static OntModel extractFromModel(Model source, Vector ids, String namespace, String importString) throws Exception{
	    OntModel res = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,null);
	     String defNsPrefix = getDefaultNsPrefix(source);
	     if(!defNsPrefix.equals(""))
	    	 namespace = defNsPrefix;
		res.setNsPrefix("",namespace);
		if(!namespace.equals(BioPAX.biopaxString))
			res.setNsPrefix("bp",BioPAX.biopaxString);
		System.out.println("NsPrefix = "+namespace);
	    Ontology _ontology = res.createOntology("");
	    Resource _imprt = res.getResource(importString);
	    _ontology.setImport(_imprt);

	  for(int i=0;i<ids.size();i++){
		  String id = (String)ids.get(i);
		  //System.out.println("Extracting "+id);
		  BioPAXUtilities.copyURIwithAllLinks(namespace, id, source, res);
	  }
	  return res;
  }
  

  public static OntModel extractFromModel(Model source, GraphDocument graph, String namespace, String importString) throws Exception{
	  OntModel res = extractFromModel(source, XGMML.convertXGMMLToGraph(graph), namespace, importString);
	  return res;
  }

  public static OntModel extractFromModel(Model source, GraphDocument graph) throws Exception{
	  OntModel res = extractFromModel(source, XGMML.convertXGMMLToGraph(graph), BioPAX.namespaceString, BioPAX.importString);
	  return res;
  }
  
  public static String getDefaultNsPrefix(Model source){
	  String res = "";
		Map mp = source.getNsPrefixMap();
		Iterator keys = mp.keySet().iterator();
		while(keys.hasNext()){
			String key = (String)keys.next();
			//System.out.println(key+"\t"+(String)mp.get(key));
			if(key.trim().equals(""))
				res = (String)mp.get(key);
		}
		return res;
  }
  
  public static String getResourceType(Resource res){
	  String type = null;
	  type = Utils.getPropertyURI(res, "type");
	  return type;
  }

  public static String getPhysicalEntityType(PhysicalEntity ent){
	  String type = null;
	  //System.out.println(ent.TYPE);
	  List ls = ent.listStatements();
	  Iterator it = ls.iterator();
	  while(it.hasNext())
		  System.out.println(it.next());
	  return type;
  }
  

}