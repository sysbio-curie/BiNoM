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

import fr.curie.BiNoM.pathways.analysis.structure.Node;

/**
 * Class for conversion from some accession numbers and names to the id- and namespace used by a database
 */
public class AccessionNumberTable {
	
	/**
	 * Map from id used by a database to the Vector of names in the table
	 * (id - is database identifier, name - some common name or accession number)  
	 */
    public HashMap idname = new HashMap();
	/**
	 * Map from name in the table used to the Vector of database ids/names   
	 */
    public HashMap nameid = new HashMap();

    /**
     * Loads tab delimited two-columns table <common_name_or_accession_number>\t<database_id_or_name>
     */
	public void loadTable(String fn) throws Exception{
	      LineNumberReader ln = new LineNumberReader(new FileReader(fn));
	      String s = null;
	      int kk = 0;
	      System.out.println("Loading accession table");
	      while((s=ln.readLine())!=null){
	          if(kk==(int)(0.0001f*kk)*10000)
	            System.out.print(kk+"\t");
	          kk++;
	          StringTokenizer st = new StringTokenizer(s,"\t");
	          String cod = st.nextToken();
	          if(st.hasMoreTokens()){
	            String id = st.nextToken();
	            String name = "";
	            StringTokenizer st1 = new StringTokenizer(cod,":");
	            while(st1.hasMoreTokens())
	              name = st1.nextToken().toLowerCase();
	            if((name.length()<13)&&(name.length()>1)&&(!name.equals("human"))){
	              //
	              Vector v = (Vector)idname.get(id);
	              if(v==null)
	                v = new Vector();
	              if(v.indexOf(name)<0)
	                v.add(name);
	              idname.put(id,v);
	              //
	              v = (Vector)nameid.get(name);
	              if(v==null)
	                v = new Vector();
	              if(v.indexOf(id)<0)
	                v.add(id);
	              nameid.put(name,v);
	            }
	          }
	        }
	      System.out.println();
	}

	/**
	 * 
	 * @param names list of some names
	 * @return list of all corresponding ids
	 */
	public Vector getIDList(Vector names){
		Vector res = new Vector();
		Set ids = new HashSet(); 
		for(int i=0;i<names.size();i++){
			String name = (String)names.get(i);
			System.out.print(name+"\t");
			Vector vv = (Vector)nameid.get(((String)names.get(i)).toLowerCase());
			if(vv!=null){
			System.out.print(vv.size()+" found\t");
			for(int j=0;j<vv.size();j++){
				ids.add(vv.get(j));
				System.out.print((String)vv.get(j)+"\t");
			}
			System.out.println();
			}
			else
				System.out.println("NOTHING");
		}
		Iterator it = ids.iterator();
		while(it.hasNext())
			res.add(it.next());
		return res;
	}
	
	/**
	 * Prints the whole table in the form
	 * <id>\t<list of all corresponding names> 
	 */
	public void print(){
		System.out.println("Id to name:");
		Iterator it = idname.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			System.out.print(key+":\t");
			Vector v = (Vector)idname.get(key);
			for(int i=0;i<v.size();i++)
				System.out.print((String)v.get(i)+"\t");
			System.out.println();
		}
		System.out.println("Name to id:");
		it = nameid.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			System.out.print(key+":\t");
			Vector v = (Vector)nameid.get(key);
			for(int i=0;i<v.size();i++)
				System.out.print((String)v.get(i)+"\t");
			System.out.println();
		}
	}
	
	/**
	 * To every node of query adds BIOPAX_NODE_SYNONYM attribute accordingly to found ids
	 * connected to the Node.Id
	 */
	public void addSynonyms(fr.curie.BiNoM.pathways.utils.BioPAXGraphQuery query){
	for(int i=0;i<query.input.Nodes.size();i++){
		Node node = (Node)query.input.Nodes.get(i);
		String name = node.Id;
		Vector ids = (Vector)nameid.get(name.toLowerCase());
		if(ids!=null)
		for(int j=0;j<ids.size();j++){
			String id = (String)ids.get(j);
			fr.curie.BiNoM.pathways.analysis.structure.Attribute at1 = new fr.curie.BiNoM.pathways.analysis.structure.Attribute("BIOPAX_NODE_XREF",id);
			node.Attributes.add(at1);
			at1.value = at1.value+"e";
			node.Attributes.add(at1);
			at1 = new fr.curie.BiNoM.pathways.analysis.structure.Attribute("BIOPAX_NODE_SYNONYM",id);
			node.Attributes.add(at1);
		}
	}
    }
	
	
}
