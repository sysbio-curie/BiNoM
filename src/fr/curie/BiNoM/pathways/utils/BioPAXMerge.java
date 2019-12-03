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

import java.util.*;
import fr.curie.BiNoM.pathways.wrappers.*;

import fr.curie.BiNoM.pathways.biopax.*;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Utilities for merging BioPAX objects 
 * @author Andrei Zinovyev
  */
public class BioPAXMerge{

/**
 * The base BioPAX com.hp.hpl.jena.rdf.model.Model object
 */
public Model mainfile = null;
/**
 * List of com.hp.hpl.jena.rdf.model.Model objects to merge with the base Model
 */
public Vector referenceFiles = new Vector();

/**
 * Merging of the base file with reference files.
 */
public void mergeMainWithReferences() throws Exception{

	  Model current = BioPAXUtilities.makeCopy(mainfile,BioPAX.namespaceString,BioPAX.importString);
	  HashMap uris = new HashMap();
	  
	  for(int i=0;i<referenceFiles.size();i++){
	    Model mref = (Model)referenceFiles.get(i);
	    System.out.println("Merging reference file "+(i+1)+"...");
	    int k=0;
	    ResIterator itr = mainfile.listSubjects();
	    while(itr.hasNext()){
	    	Resource r = itr.nextResource();
	    	com.ibm.adtech.jastor.Thing thing = biopax_DASH_level3_DOT_owlFactory.getThing(r.getURI(),mainfile);
	        k++;
	        if(k==(int)(0.001f*k)*1000)
	        	System.out.print(k+"\t");
	        uris.put(thing.uri(),thing);
	        //if(BioPAXUtilities.numberOfStatements(thing)==1){
	            com.ibm.adtech.jastor.Thing ref = biopax_DASH_level3_DOT_owlFactory.getThing(r.toString(),mref);
	             if(ref!=null){
	               //System.out.println("FOUND "+ref.uri()+"\t"+BioPAXUtilities.numberOfStatements(ref));
	               if(BioPAXUtilities.numberOfStatements(ref)>1){
	                 BioPAXUtilities.copyURIwithAllLinks(BioPAX.namespaceString,Utils.cutUri(r.toString()),mref,current);
	               }
	             }
	        //}
	    }
	    System.out.println();
		mainfile = current;
	    ResIterator addIt = mref.listSubjects();
	    while(addIt.hasNext()){
	    	Resource r = addIt.nextResource();
	    	com.ibm.adtech.jastor.Thing thing = biopax_DASH_level3_DOT_owlFactory.getThing(r.getURI(),mref);
	        k++;
	        if(k==(int)(0.001f*k)*1000)
	        	System.out.print(k+"\t");
	        if(uris.get(thing.uri())==null){
	               if(BioPAXUtilities.numberOfStatements(thing)>1){
		                 BioPAXUtilities.copyURIwithAllLinks(BioPAX.namespaceString,Utils.cutUri(r.toString()),mref,current);	        	
	        }
	        uris.put(thing.uri(),thing);
	        }
	    }
	  }
}

/**
 * Updating base file with references.
 * Updating means that if an 'empty' thing is found in base then
 * it is filled with information from the references
 * @throws Exception
 */
public void updateMainfileWithReferences() throws Exception{

  Model current = BioPAXUtilities.makeCopy(mainfile,BioPAX.namespaceString,BioPAX.importString);
  for(int i=0;i<referenceFiles.size();i++){
    Model mref = (Model)referenceFiles.get(i);
    System.out.println((new Date()).toString()+": Reference file "+(i+1)+":");
    int k=0;
    ResIterator itr = mainfile.listSubjects();
    Date t = new Date();
    while(itr.hasNext()){
    	Resource r = itr.nextResource();
    	com.ibm.adtech.jastor.Thing thing = biopax_DASH_level3_DOT_owlFactory.getThing(r.getURI(),mainfile);
        k++;
        if(k==(int)(0.001f*k)*1000){
        	System.out.print(""+((new Date()).getTime()-t.getTime())+":"+k+"\t");
        	t = new Date();
        }
        if(BioPAXUtilities.numberOfStatements(thing)==1){
            com.ibm.adtech.jastor.Thing ref = biopax_DASH_level3_DOT_owlFactory.getThing(r.toString(),mref);
             if(ref!=null){
               //System.out.println("FOUND "+ref.uri()+"\t"+BioPAXUtilities.numberOfStatements(ref));
               if(BioPAXUtilities.numberOfStatements(ref)>1){
                 BioPAXUtilities.copyURIwithAllLinks(BioPAX.namespaceString,Utils.cutUri(r.toString()),mref,current);
               }
             }
        }
    }
    /*Iterator itr = mainfile.listStatements();
    int k=0;
    while(itr.hasNext()){
      
      //Resource r = (Resource)itr.next();
      //System.out.println(r.getURI());
      Statement st = (Statement)itr.next();
      RDFNode nod = st.getObject();
      //System.out.print(st.getPredicate().toString()+"\t");
      if(nod.isURIResource()){
        com.ibm.adtech.jastor.Thing thing = biopax_DASH_level2_DOT_owlFactory.getThing(nod.toString(),mainfile);
        //if(BioPAXUtilities.numberOfStatements(thing)>0)
        //  System.out.println(k+":"+" URIResource: "+nod.toString()+"\t"+BioPAXUtilities.numberOfStatements(thing));
        k++;
        if(k==(int)(0.001f*k)*1000)
        	System.out.print(k+"\t");
        //if(k==1000)
        //	break;
        if(BioPAXUtilities.numberOfStatements(thing)==1){
           com.ibm.adtech.jastor.Thing ref = biopax_DASH_level2_DOT_owlFactory.getThing(nod.toString(),mref);
            if(ref!=null){
              //System.out.println("FOUND "+ref.uri()+"\t"+BioPAXUtilities.numberOfStatements(ref));
              if(BioPAXUtilities.numberOfStatements(ref)>1){
                BioPAXUtilities.copyURIwithAllLinks(namespace,Utils.cutUri(nod.toString()),mref,current);
              }
            }
          }
      }      
    }*/
    System.out.println();
  mainfile = current;
  //current = BioPAXUtilities.makeCopy(mainfile,namespace,importString);

  }

}

/**
 * Prints all statements of the base Model
 * @throws Exception
 */
public void printStatements() throws Exception{
  Iterator itr = mainfile.listStatements();
  int k=0;
  while(itr.hasNext()){
    //Resource r = (Resource)itr.next();
    //System.out.println(r.getURI());
    Statement st = (Statement)itr.next();
    RDFNode nod = st.getObject();
    //System.out.print(st.getPredicate().toString()+"\t");
    if(nod.isURIResource()){
      com.ibm.adtech.jastor.Thing thing = biopax_DASH_level3_DOT_owlFactory.getThing(nod.toString(),mainfile);
      if(BioPAXUtilities.numberOfStatements(thing)>0)
        System.out.println((k++)+":"+" URIResource: "+nod.toString()+"\t"+BioPAXUtilities.numberOfStatements(thing));
    }
  }
}

}