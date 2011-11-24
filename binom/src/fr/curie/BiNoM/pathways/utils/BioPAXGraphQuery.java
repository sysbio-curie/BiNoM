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

import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;

import java.io.*;
import java.util.*;

/**
 * A simple container of the BioPAX standard query 
 */
public class BioPAXGraphQuery{

/**
 * Input for the query
 */
public fr.curie.BiNoM.pathways.analysis.structure.Graph input = null;
/**
 * Output of the query
 */
public fr.curie.BiNoM.pathways.analysis.structure.Graph result = null;
/**
 * Predefined query type
 */
public int queryType = -1;
/**
 * Identifying entities by names and XREFs in the index
 */
public static int SELECT_ENTITIES = 0;
/**
 * For a set of entities in the input adds all complexes in which at list one participates
 */
public static int ADD_COMPLEXES_EXPAND = 1;
/**
 * For a set of entities in the input adds all their complexes 
 */
public static int ADD_COMPLEXES_NOEXPAND = 2;
/**
 * Simply connects all publicationXrefs to the entities
 */
public static int ADD_PUBLICATIONS = 3;
/**
 * For all entities adds all their forms (chemical species)
 */
public static int ADD_SPECIES = 4;
/**
 * For all chemical species adds reactions in which at least
 * one of the species participates as reactants and another one
 * as product  
 */
public static int ADD_CONNECTING_REACTIONS = 5;
/**
 * For all reactions, attaches all connected nodes from the index (species, publications, pathway steps, etc.)
 */
public static int COMPLETE_REACTIONS = 6;
/**
 * For all species adds all reactions in which they participate
 */
public static int ADD_ALL_REACTIONS = 7;
/**
 * Simply lists the publications from the input to the query log
 */
public static int LIST_PUBLICATIONS = 8;
/**
 * Simply lists the proteins from the input to the query log
 */
public static int LIST_PROTEINS = 9;
/**
 * Simply lists the proteins from the input to the query log
 */
public static int LIST_COMPLEXES = 10;
/**
 * Simply lists the reactions from the input to the query log
 */
public static int LIST_REACTIONS = 11;
/**
 * Simply lists the dnas from the input to the query log
 */
public static int LIST_GENES = 12;
/**
 * Simply lists the smallMolecules from the input to the query log
 */
public static int LIST_SMALL_MOLECULES = 13;

/**
 * Utility converting vector of names and ids to a BioPAXGraphQuery object
 */
public static BioPAXGraphQuery convertListOfNamesToQuery(Vector names, Vector IDs){
  BioPAXGraphQuery q = new BioPAXGraphQuery();
  q.input = new fr.curie.BiNoM.pathways.analysis.structure.Graph();
  for(int i=0;i<names.size();i++){
    String name = (String)names.get(i);
    fr.curie.BiNoM.pathways.analysis.structure.Node nd = q.input.getCreateNode(name);
    fr.curie.BiNoM.pathways.analysis.structure.Attribute at = new fr.curie.BiNoM.pathways.analysis.structure.Attribute("BIOPAX_NODE_TYPE","protein");
    nd.Attributes.add(at);
    at = new fr.curie.BiNoM.pathways.analysis.structure.Attribute("BIOPAX_NODE_SYNONYM",name);
    nd.Attributes.add(at);
    if(IDs!=null){
      Vector v = (Vector)IDs.get(i);
      for(int j=0;j<v.size();j++){
        String id = (String)v.get(j);
        if(id!=null){
          fr.curie.BiNoM.pathways.analysis.structure.Attribute at1 = new fr.curie.BiNoM.pathways.analysis.structure.Attribute("BIOPAX_NODE_XREF",id);
          nd.Attributes.add(at1);
        }
      }
    }
  }
  return q;
}

/**
 * Loads BioPAXGraphQuery from a XGMML file 
 */
public static BioPAXGraphQuery parseXGMML(String fn) throws Exception{
  BioPAXGraphQuery q = new BioPAXGraphQuery();
  GraphXGMMLParser gp_input = new GraphXGMMLParser();
  gp_input.parse(fn);
  q.input = gp_input.graph;
  return q;
}

/**
 * deprecated
 * @param fn
 * @throws Exception
 */
public void parseAccessionTable(String fn) throws Exception{
	HashMap keys = new HashMap();
	System.out.println("Number of input nodes : "+input.Nodes.size());
	for(int i=0;i<input.Nodes.size();i++){
		Node n = (Node)input.Nodes.get(i);
		Vector syns = n.getAttributeValues("BIOPAX_NODE_SYNONYM");
		for(int j=0;j<syns.size();j++)
			keys.put(((String)syns.get(j)).toLowerCase(), n);
	}
	Set found = new HashSet();
	HashMap foundids = new HashMap();
	HashMap foundgenes = new HashMap();
	
	LineNumberReader lr = new LineNumberReader(new FileReader(fn));
	String s = null;
	while((s=lr.readLine())!=null){
		StringTokenizer st1 = new StringTokenizer(s,"\t");
		String name = st1.nextToken();
		String name1 = ""; 
		if(!st1.hasMoreTokens())
			continue;
		String id = st1.nextToken();
		StringTokenizer st2 = new StringTokenizer(name,":");
		while(st2.hasMoreTokens())
			name1 = st2.nextToken().toLowerCase();
		if(keys.get(name1)!=null){
			Node n = (Node)keys.get(name1);
			found.add(n);
			foundids.put(id, n);
			if(id.toLowerCase().startsWith("g"))
				foundgenes.put(id, n);
			System.out.println(found.size()+":\t"+n.Id+"\t"+name1+"\t"+id);
		}
	}
	lr.close();
	
	Iterator it = foundids.keySet().iterator();
	while(it.hasNext()){
		String id = (String)it.next();
		Node n = (Node)foundids.get(id);
		Attribute att = new Attribute("BIOPAX_NODE_XREF", id);
		n.Attributes.add(att);
		att = new Attribute("BIOPAX_NODE_XREF", id+"e");
		n.Attributes.add(att);
	}
	
	System.out.println("Found "+foundids.size()+" entities (presum.incl. "+foundgenes.size()+" gene entities)");
	
	System.out.println("NOT FOUND:");
	int k=0;
	for(int i=0;i<input.Nodes.size();i++){
		Node n = (Node)input.Nodes.get(i);
		if(!found.contains(n))
			System.out.println((++k)+"\t"+n.Id);
	}
	
	result = input;
	
}

}