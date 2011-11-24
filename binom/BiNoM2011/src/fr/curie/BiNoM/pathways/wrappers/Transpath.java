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

package fr.curie.BiNoM.pathways.wrappers;

import com.biobaseInternational.*;
import java.io.*;
import java.util.*;

import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.utils.*;

/**
 * Experimental converter from Transpath to BioPAX
 * @author Andrei Zinovyev
 *
 */
public class Transpath {

  public NetworkDocument network = null;
  public NetworkDocument annotations = null;

  public HashMap annotationHash = new HashMap();

  public BioPAX biopax = null;

  public HashMap entities = new HashMap();
  public HashMap moleculeHash = new HashMap();
  
  public Vector speciesFilter = null;

  public int typeOfReactionExtraction = 2;
  public static int MOLECULAR_EVIDENCE = 0;
  public static int PATHWAY_STEP = 1;
  public static int SEMANTIC = 2;
  
  public static int moleculeStart = -1;
  public static int moleculeEnd = -1;

  public void loadFromFile(String fn) throws Exception{
    network = com.biobaseInternational.NetworkDocument.Factory.parse(new File(fn));
  }

  public void initializeModel(){
    biopax = new BioPAX();

  }


//  public void populateModel() throws Exception{
//
//    System.out.println("Hashing annotations ...");
//    if(annotations!=null){
//      int len = annotations.getNetwork().getAnnotateArray().length;
//      AnnotateDocument.Annotate ana[] =annotations.getNetwork().getAnnotateArray();
//      for(int i=0;i<len;i++){
//        //if(i==(int)(0.001f*i)*1000)
//        //  System.out.print(i+"\t");
//        AnnotateDocument.Annotate ann = ana[i];
//        annotationHash.put(ann.getId(),ann);
//      }
//    }
//    //System.out.println("");
//
//    Date tm = new Date();
//
//    // Converting genes
//    if(network.getNetwork().getGeneArray()!=null){
//    GeneDocument.Gene ga[] = network.getNetwork().getGeneArray();
//    Date t = new Date();
//    for(int i=0;i<ga.length;i++){
//      if(i==(int)(0.001f*i)*1000){
//    	Date tc = new Date();
//        System.out.print(i+"("+((int)((tc.getTime()-t.getTime())/1000))+")");
//        t = new Date();
//      }
//      String id = ga[i].getId();
//      String name = ga[i].getName();
//      String secid = ga[i].getSecid();
//      /*if(network.getNetwork().getGeneArray(i).getMembers().getItemArray().length>0)
//        System.out.println("WARNING: Gene "+id+" Members are not empty");
//      if(network.getNetwork().getGeneArray(i).getFullname()!=null)
//        System.out.println("WARNING: Gene "+id+" Fullname is not empty");*/
//      if(speciesFilter!=null)
//      if(ga[i].getSpecies()!=null){
//        boolean include = false;
//        StringTokenizer st = new StringTokenizer(ga[i].getSpecies(),";");
//        while(st.hasMoreTokens())
//        if(speciesFilter.indexOf(st.nextToken())>=0)
//          include = true;
//        if(!include)
//          continue;
//      }
//      Dna g = biopax_DASH_level3_DOT_owlFactory.createDna(biopax.namespaceString+id+"e",biopax.biopaxmodel);
//      g.addName(name);
//      if(ga[i].getSpecies()!=null)
//         associateWithSpecies(g,ga[i].getSpecies());
//      // members and groups
//      /*if(network.getNetwork().getGeneArray(i).getGroups().getItemArray().length>0){
//        for(int k=0;k<network.getNetwork().getGeneArray(i).getGroups().getItemArray().length;k++){
//          ItemDocument.Item it = network.getNetwork().getGeneArray(i).getGroups().getItemArray(k);
//          g.addCOMMENT("MEMBEROF "+it.getType()+" "+it.getStringValue());
//        }
//      }*/
//      for(int k=0;k<ga[i].getAccnosArray().length;k++){
//        addAccno(g,ga[i].getAccnosArray(k));
//      }
//      for(int k=0;k<ga[i].getSynonymsArray().length;k++){
//        addSynonym(g,ga[i].getSynonymsArray(k));
//      }
//      for(int k=0;k<ga[i].getReferences().getItemArray().length;k++){
//        addPublication(g,ga[i].getReferences().getItemArray(k));
//      }
//      sequenceParticipant sp = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+id,biopax.biopaxmodel);
//      sp.setPHYSICAL_DASH_ENTITY(g);
//    }
//    System.out.println();
//    }
//
//    // converting molecules
//    // Problem - what to do with compartments? Now just put them in comments
//    
//    //protein Prot = biopax_DASH_level2_DOT_owlFactory.createprotein(biopax.namespaceString+"123"+"e",biopax.biopaxmodel);
//    
//    try{
//    if(network.getNetwork().getMoleculeArray()!=null)if(network.getNetwork().getMoleculeArray().length>0){
//    	
//      MoleculeDocument.Molecule ml[] = network.getNetwork().getMoleculeArray();
//      
//      System.out.print("Creating Molecule hash...");
//      Date t = new Date();
//      for(int i=0;i<ml.length;i++){
//          String id = ml[i].getId();
//          String name = ml[i].getName();
//          String type = ml[i].getType();
//          //if(name.toLowerCase().startsWith("pip"))
//          //	  System.out.println(id+"\t"+name);
//          moleculeHash.put(id, ml[i]);
//      }
//      System.out.println("done.");
//      
//      for(int pass=0;pass<2;pass++){
//    	  
//      if((moleculeStart==-1)||(pass==1)) moleculeStart = 0;
//      if((moleculeEnd==-1)||(pass==1)) moleculeEnd = ml.length;
//      moleculeEnd = Math.min(moleculeEnd,ml.length);
//    	  
//      for(int i=moleculeStart;i<moleculeEnd;i++){
//        if(i==(int)(0.001f*i)*1000){
//        	Date tc = new Date();
//        	System.out.print(i+"("+((int)((tc.getTime()-t.getTime())/1000))+")");
//        	t = new Date();
//        }
//        if(speciesFilter!=null)
//        if(ml[i].getSpecies()!=null){
//          boolean include = false;
//          StringTokenizer st = new StringTokenizer(ml[i].getSpecies().trim(),";");
//          while(st.hasMoreTokens())
//          if(speciesFilter.indexOf(st.nextToken().trim())>=0)
//            include = true;
//          if(!include)
//            continue;
//        }
//
//        //if(entities.get(id)!=null) continue;
//
//        physicalEntity ent = null;
//        protein prot = null;
//        complex compl = null;
//        smallMolecule sm = null;
//        
//        String id = ml[i].getId();
//        String name = ml[i].getName();
//        String type = ml[i].getType();
//
//        if(pass==0){
//        	ent = createEntityObject(id, moleculeHash,entities);
//        }
//
//        if(pass==1){
//          if(type.equals("isogroup")||type.equals("orthobasic")||type.equals("orthogroup")){
//            physicalEntity fam = (physicalEntity)entities.get(id);
//            if(fam!=null)
//            for(int k=0;k<ml[i].getMembers().getItemArray().length;k++){
//                ItemDocument.Item it = ml[i].getMembers().getItemArray(k);
//                String memid = getXLink(it,"href");
//                //physicalEntity mement = (physicalEntity)entities.get(memid);
//                MoleculeDocument.Molecule mol = (MoleculeDocument.Molecule)moleculeHash.get(id);                
//                if(mol!=null)
//                  fam.addCOMMENT("INCLUDES: "+mol.getName()+" ("+memid+")");
//                else
//                  fam.addCOMMENT("INCLUDES: "+memid);
//            }
//          }
//          if(type.equals("basic_mod")||type.equals("isogroup_mod")||type.equals("orthobasic_mod")||type.equals("orthogroup_mod")){
//            // here we make sequenceParticipants for entities (alternative - represent them as entities but we will try to do it properly)
//            if(ml[i].getStateofs().getItemArray().length>0){
//              ItemDocument.Item it = ml[i].getStateofs().getItemArray(0);
//              String compid = getXLink(it,"href");
//              physicalEntity entc = (physicalEntity)entities.get(compid);
//              if(entc!=null){
//                 sequenceParticipant sp = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+id,biopax.biopaxmodel);
//                 sp.setPHYSICAL_DASH_ENTITY(entc);
//                 AddModificationToParticipant(sp,ml[i]);
//                 sp.addCOMMENT("TRANSPATH_TYPE: "+ml[i].getType());
//                 sp.addCOMMENT("TRANSPATH_NAME: "+ml[i].getName());
//                 if((ml[i].getKlass()!=null)&&(!ml[i].getKlass().trim().equals("")))
//                    sp.addCOMMENT("TRANSPATH_KLASS: "+ml[i].getKlass());
//
//                 for(int kk=0;kk<ml[i].getComments().getItemArray().length;kk++){
//                   ItemDocument.Item itcom = ml[i].getComments().getItemArray(kk);
//                   String annid = getXLink(itcom,"href");
//                   if(annotationHash.get(annid)!=null){
//                     //System.out.println("annotation found for "+r.getId());
//                     AnnotateDocument.Annotate ann = (AnnotateDocument.Annotate)annotationHash.get(annid);
//                     sp.addCOMMENT("TRANSPATH_COMMENT: "+ann.getText());
//                     if(ann.getSource()!=null){
//                        for(int k=0;k<ann.getSource().getItemArray().length;k++){
//                          ItemDocument.Item it1 = ann.getSource().getItemArray(k);
//                          String sid = getXLink(it1,"href");
//                          publicationXref pxf = (publicationXref)entities.get(sid);
//                          if(pxf==null){
//                            pxf = biopax_DASH_level2_DOT_owlFactory.createpublicationXref(biopax.namespaceString+sid,biopax.biopaxmodel);
//                          }
//                          sp.getPHYSICAL_DASH_ENTITY().addXREF(pxf);
//                        }
//                     }
//                 }
//                 }
//
//                 for(int k=0;k<ml[i].getSynonymsArray().length;k++){
//                   sp.addCOMMENT("TRANSPATH_SYNONYM: "+ml[i].getSynonymsArray(k));
//                 }
//                 for(int k=0;k<ml[i].getLocationsArray().length;k++){
//                   LocationsDocument.Locations lc = ml[i].getLocationsArray(k);
//                   for(int kk=0;kk<lc.getCompartmentArray().length;kk++){
//                      String comp = lc.getCompartmentArray(kk);
//                      sp.addCOMMENT("TRANSPATH_COMPARTMENT: "+comp);
//                   }
//                 }
//                 entities.put(id,sp);
//              }else{ if(moleculeHash.get(id)==null) System.out.println("\nERROR: Entity is not found for "+id); }
//            }else System.out.println("\nERROR: StateOfs is empty for "+id+"("+ml[i].getName()+")");
//
//          }
//          if(type.equals("complex")||type.equals("orthocomplex")){
//            // here we insert complex components
//            compl = (complex)entities.get(id);
//            if(compl!=null)
//            if(ml[i].getStateofs()!=null)
//              for(int k=0;k<ml[i].getStateofs().getItemArray().length;k++){
//                 ItemDocument.Item it = ml[i].getStateofs().getItemArray(k);
//                 String compid = getXLink(it,"href");
//                 Object obj = entities.get(compid);
//                 if(obj==null){
//                	 createEntityObject(id,moleculeHash,entities);
//                	 obj = entities.get(compid);
//                 }
//                 if((obj!=null)&&(obj.getClass().getName().indexOf("sequenceParticipant")>=0)){
//                   //complex component is sequenceParticipant already
//                   sequenceParticipant spc = (sequenceParticipant)entities.get(compid);
//                   String uri = id+"_c"+(k+1);
//                   sequenceParticipant sp = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+uri,biopax.biopaxmodel);
//                   sp.setPHYSICAL_DASH_ENTITY(spc.getPHYSICAL_DASH_ENTITY());
//                   sp.setCELLULAR_DASH_LOCATION(spc.getCELLULAR_DASH_LOCATION());
//                   Iterator itf = sp.getSEQUENCE_DASH_FEATURE_DASH_LIST();
//                   while(itf.hasNext())
//                     sp.addSEQUENCE_DASH_FEATURE_DASH_LIST((sequenceFeature)itf.next());
//                 }else{
//                 physicalEntity entc = (physicalEntity)entities.get(compid);
//                 if(entc!=null){
//                   String uri = id+"_c"+(k+1);
//                   sequenceParticipant sp = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+uri,biopax.biopaxmodel);
//                   sp.setPHYSICAL_DASH_ENTITY(entc);
//                   compl.addCOMPONENTS(sp);
//                 }
//                 }
//              }
//          }
//          if(type.equals("complex_mod")||type.equals("orthocomplex_mod")){
//            // however, modified complexes are represented as entities
//            compl = (complex)entities.get(id);
//            if(compl!=null)
//            if(ml[i].getStateofs()!=null)
//              for(int k=0;k<ml[i].getStateofs().getItemArray().length;k++){
//            	 // Component of complex
//                 ItemDocument.Item it = ml[i].getStateofs().getItemArray(k);
//                 String compid = getXLink(it,"href");
//                 physicalEntity entc = null; Vector seqFeatures = new Vector();
//                 Object obj = entities.get(compid);
//                 if(obj==null){
//                	 createEntityObject(id,moleculeHash,entities);
//                	 obj = entities.get(compid);
//                 }
//                 if(obj!=null){ // we find entity or participant corresponding to this component 
//                   if(obj.getClass().getName().indexOf("Participant")>=0){ // if it is participant
//                     sequenceParticipant sp = (sequenceParticipant)entities.get(compid); 
//                     entc = (physicalEntity)sp.getPHYSICAL_DASH_ENTITY(); // we extract entity
//                     Iterator itf = sp.getSEQUENCE_DASH_FEATURE_DASH_LIST(); // we memorize all seqFeatures
//                     while(itf.hasNext()){
//                       seqFeatures.add(itf.next());
//                     }
//                   }else{ // if it is entity
//                     entc = (physicalEntity)entities.get(compid);
//                   }
//                 }
//                 if(entc!=null){
//                   String uri = id+"_c"+(k+1);
//                   sequenceParticipant sp = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+uri,biopax.biopaxmodel);
//                   // we should add modification sequence features here but how?
//                   // the problem is that we should identify the component from the name
//                   // here is an attempt
//                   if(moleculeHash.get(compid)!=null)
//                	   AddModificationToComplexComponent(sp,ml[i],(MoleculeDocument.Molecule)moleculeHash.get(compid));
//                   sp.setPHYSICAL_DASH_ENTITY(entc);
//                   for(int kk=0;kk<seqFeatures.size();kk++){
//                     sp.addSEQUENCE_DASH_FEATURE_DASH_LIST((sequenceFeature)seqFeatures.get(kk));
//                   }
//                   compl.addCOMPONENTS(sp);
//                 }
//              }
//          }
//          if(type.equals("family")||type.equals("orthofamily")||type.equals("group (XOR)")){
//            physicalEntity fam = (physicalEntity)entities.get(id);
//            if(fam!=null)
//            for(int k=0;k<ml[i].getMembers().getItemArray().length;k++){
//                ItemDocument.Item it = ml[i].getMembers().getItemArray(k);
//                String memid = getXLink(it,"href");
//                Object obj = entities.get(memid);
//                if(obj==null){
//               	 createEntityObject(id,moleculeHash,entities);
//               	 obj = entities.get(memid);
//                }
//
//                physicalEntity mement = null;
//                if(obj!=null){
//                  if(obj.getClass().getName().indexOf("Participant")>=0){
//                    sequenceParticipant sp = (sequenceParticipant)entities.get(memid);
//                    mement = (physicalEntity)sp.getPHYSICAL_DASH_ENTITY();
//                  }else{
//                    mement = (physicalEntity)entities.get(memid);
//                  }
//                }
//                if(mement!=null)
//                  fam.addCOMMENT("INCLUDES: "+mement.getNAME()+" ("+memid+")");
//                else
//                  fam.addCOMMENT("INCLUDES: "+memid);
//            }
//          }
//        }
//
//    }
//    System.out.println(" done.");
//    }
//    }
//  }catch(Exception e){
//    e.printStackTrace();
//  }
//
//
//    // converting reactions
//    Date t = new Date();
//    if(network.getNetwork().getReactionArray()!=null)if(network.getNetwork().getReactionArray().length>0){
//      ReactionDocument.Reaction ra[] = network.getNetwork().getReactionArray();
//      for(int i=0;i<ra.length;i++){
//         ReactionDocument.Reaction r = ra[i];
//         if(i==(int)(0.001f*i)*1000){
//         	Date tc = new Date();
//         	System.out.print(i+"("+((int)((tc.getTime()-t.getTime())/1000))+")");
//         	t = new Date();
//         }
//         String type = "";
//         // semantic level
//         if(typeOfReactionExtraction==SEMANTIC){
//           type = "semantic";
//         }
//         // evidence level
//         if(typeOfReactionExtraction==MOLECULAR_EVIDENCE){
//           type = "molecular evidence";
//         }
//         // pathway level
//         if(typeOfReactionExtraction==PATHWAY_STEP){
//           type = "pathway step";
//         }
//
//           if(r.getType().equals(type)){
//             // first, make a filter on species
//             // ...
//             biochemicalReaction br = biopax_DASH_level2_DOT_owlFactory.createbiochemicalReaction(biopax.namespaceString+r.getId(),biopax.biopaxmodel);
//             br.setNAME(r.getName().replaceAll("-&gt;","->"));
//             br.addCOMMENT("TRANSPATH_REVERSIBLE: "+r.getReversible());
//             br.addCOMMENT("TRANSPATH_EFFECT: "+r.getEffect());
//             br.addCOMMENT("TRANSPATH_DIRECT: "+r.getDirect());
//             if(r.getQuality()!=null)
//                br.addCOMMENT("TRANSPATH_QUALITY: "+r.getQuality());
//             addReferencesToReaction(r,br);
//             // reactants
//             for(int k=0;k<r.getReactants().getItemArray().length;k++){
//               ItemDocument.Item item = r.getReactants().getItemArray(k);
//               String reactid = getXLink(item,"href");
//               sequenceParticipant sp = (sequenceParticipant)entities.get(reactid);
//               if(sp==null){
//                     sp = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+reactid,biopax.biopaxmodel);
//                     entities.put(reactid,sp);
//               }
//               br.addLEFT(sp);
//             }
//             // products
//             for(int k=0;k<r.getProduces().getItemArray().length;k++){
//               ItemDocument.Item item = r.getProduces().getItemArray(k);
//               String reactid = getXLink(item,"href");
//               sequenceParticipant sp = (sequenceParticipant)entities.get(reactid);
//               if(sp==null){
//                     sp = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+reactid,biopax.biopaxmodel);
//                     entities.put(reactid,sp);
//               }
//               br.addRIGHT(sp);
//             }
//             // modifiers
//             // inhibitors
//             for(int k=0;k<r.getInhibitor().getItemArray().length;k++){
//               ItemDocument.Item item = r.getInhibitor().getItemArray(k);
//               String reactid = getXLink(item,"href");
//               sequenceParticipant sp = (sequenceParticipant)entities.get(reactid);
//               if(sp==null){
//                     sp = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+reactid,biopax.biopaxmodel);
//                     entities.put(reactid,sp);
//               }
//               catalysis cat = biopax_DASH_level2_DOT_owlFactory.createcatalysis(biopax.namespaceString+r.getId()+"_inh"+(k+1),biopax.biopaxmodel);
//               cat.setCONTROL_DASH_TYPE("INHIBITION");
//               cat.setCONTROLLER(sp);
//               cat.setCONTROLLED(br);
//             }
//             // activators
//             for(int k=0;k<r.getEnzyme().getItemArray().length;k++){
//               ItemDocument.Item item = r.getEnzyme().getItemArray(k);
//               String reactid = getXLink(item,"href");
//               sequenceParticipant sp = (sequenceParticipant)entities.get(reactid);
//               if(sp==null){
//                     sp = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+reactid,biopax.biopaxmodel);
//                     entities.put(reactid,sp);
//               }
//               catalysis cat = biopax_DASH_level2_DOT_owlFactory.createcatalysis(biopax.namespaceString+r.getId()+"_act"+(k+1),biopax.biopaxmodel);
//               cat.setCONTROL_DASH_TYPE("ACTIVATION");
//               cat.setCONTROLLER(sp);
//               cat.setCONTROLLED(br);
//             }
//           }
//      }
//    }
//
//    // converting references
//    t = new Date();
//    if(network.getNetwork().getReferenceArray()!=null)if(network.getNetwork().getReferenceArray().length>0){
//      ReferenceDocument.Reference ra[] = network.getNetwork().getReferenceArray();
//      for(int i=0;i<ra.length;i++){
//          if(i==(int)(0.001f*i)*1000){
//           	Date tc = new Date();
//           	System.out.print(i+"("+((int)((tc.getTime()-t.getTime())/1000))+")");
//           	t = new Date();
//        }
//        publicationXref pxr = (publicationXref)entities.get(ra[i].getId());
//        if(pxr==null){
//          pxr = biopax_DASH_level2_DOT_owlFactory.createpublicationXref(biopax.namespaceString+ra[i].getId(),biopax.biopaxmodel);
//          entities.put(ra[i].getId(),pxr);
//        }
//        pxr.addSOURCE(ra[i].getPublication());
//        pxr.setTITLE(ra[i].getTitle());
//        for(int k=0;k<ra[i].getAccnosArray().length;k++){
//          String accno = ra[i].getAccnosArray(k);
//          StringTokenizer st = new StringTokenizer(accno,":");
//          String DB = "";
//          String version = "";
//          String id = "";
//          if(st.hasMoreTokens()) DB = st.nextToken();
//          if(st.hasMoreTokens()) version = st.nextToken();
//          if(st.hasMoreTokens()) id = st.nextToken();
//          String uri = DB+"_"+version+"_"+id;
//          if(id.equals("")) uri = DB+"_"+version;
//          if(!id.equals("")){
//            pxr.setDB(DB);
//            pxr.setDB_DASH_VERSION(version);
//            pxr.setID(id);
//          }else{
//            pxr.setDB(DB);
//            pxr.setID(version);
//          }
//        }
//      }
//    }
//
//    // converting pathways
//    if(network.getNetwork().getPathwayArray()!=null)if(network.getNetwork().getPathwayArray().length>0){
//      PathwayDocument.Pathway pa[] = network.getNetwork().getPathwayArray();
//      for(int pass=0;pass<2;pass++){
//      for(int i=0;i<pa.length;i++){
//        if(i==(int)(0.1f*i)*10)
//          System.out.print(i+"\t");
//        if(pass==0){
//          if(pa[i].getType().equals("evidence chain")||pa[i].getType().equals("chain")){
//            pathwayStep pst = (pathwayStep)entities.get(pa[i].getId());
//            if(pst==null){
//              pst = biopax_DASH_level2_DOT_owlFactory.createpathwayStep(biopax.namespaceString+pa[i].getId(),biopax.biopaxmodel);
//              entities.put(pa[i].getId(),pst);
//            }
//            pst.addCOMMENT("TRANSPATH_CHAIN_NAME: "+pa[i].getName());
//            pst.addCOMMENT("TRANSPATH_CHAIN_DESCRIPTION: "+pa[i].getDescription());
//            for(int k=0;k<pa[i].getReactionsInvolved().getItemArray().length;k++){
//              ItemDocument.Item item = pa[i].getReactionsInvolved().getItemArray(k);
//              String reactid = getXLink(item,"href");
//              interaction inter = (interaction)entities.get(reactid);
//              if(inter==null){
//                biochemicalReaction br = biopax_DASH_level2_DOT_owlFactory.createbiochemicalReaction(biopax.namespaceString+reactid,biopax.biopaxmodel);
//                inter = br;
//                entities.put(reactid,br);
//              }
//              pst.addSTEP_DASH_INTERACTIONS(inter);
//            }
//          }
//        }
//        if(pass==1)
//          if(pa[i].getType().equals("pathway")){
//            pathway pxr = (pathway)entities.get(pa[i].getId());
//            if(pxr==null){
//              pxr = biopax_DASH_level2_DOT_owlFactory.createpathway(biopax.namespaceString+pa[i].getId(),biopax.biopaxmodel);
//              entities.put(pa[i].getId(),pxr);
//            }
//            pxr.setNAME(pa[i].getName());
//            pxr.addCOMMENT("TRANSPATH_CHAIN_DESCRIPTION: "+pa[i].getDescription());
//            for(int k=0;k<pa[i].getReactionsInvolved().getItemArray().length;k++){
//              ItemDocument.Item item = pa[i].getReactionsInvolved().getItemArray(k);
//              String reactid = getXLink(item,"href");
//              interaction inter = (interaction)entities.get(reactid);
//              if(inter==null){
//                biochemicalReaction br = biopax_DASH_level2_DOT_owlFactory.createbiochemicalReaction(biopax.namespaceString+reactid,biopax.biopaxmodel);
//                inter = br;
//                entities.put(reactid,br);
//              }
//              pxr.addPATHWAY_DASH_COMPONENTS(inter);
//            }
//            for(int k=0;k<pa[i].getChains().getItemArray().length;k++){
//              ItemDocument.Item item = pa[i].getChains().getItemArray(k);
//              String pid = getXLink(item,"href");
//              pathwayStep pst = (pathwayStep)entities.get(pid);
//              if(pst==null){
//                System.out.println("ERROR: for pathway "+pa[i].getId()+" pathwayStep "+pid+" not found");
//              }else{
//                pxr.addPATHWAY_DASH_COMPONENTS(pst);
//              }
//            }
//
//          }
//      }
//      }
//    }
//
//    System.out.println("Time spent "+((new Date()).getTime()-tm.getTime()));
//  }
//  
//  public physicalEntity createEntityObject(String id, HashMap molmap, HashMap entities) throws Exception{
//	  
//	  physicalEntity ent = null;
//      protein prot = null;
//      complex compl = null;
//      smallMolecule sm = null;
//	  
//	  MoleculeDocument.Molecule ml = (MoleculeDocument.Molecule)molmap.get(id);
//	  String type = ml.getType();
//	  
//      if(type.equals("basic")||type.equals("isogroup")||type.equals("orthobasic")||type.equals("orthogroup")){
//          prot = biopax_DASH_level2_DOT_owlFactory.createprotein(biopax.namespaceString+id+"e",biopax.biopaxmodel);
//          sequenceParticipant sp = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+id,biopax.biopaxmodel);
//          sp.setPHYSICAL_DASH_ENTITY(prot);
//          ent = prot;
//          associateWithSpecies(prot,ml.getSpecies());
//        }
//        if(type.equals("complex")||type.equals("orthocomplex")){
//          compl = biopax_DASH_level2_DOT_owlFactory.createcomplex(biopax.namespaceString+id+"e",biopax.biopaxmodel);
//          sequenceParticipant sp = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+id,biopax.biopaxmodel);
//          sp.setPHYSICAL_DASH_ENTITY(compl);
//          ent = compl;
//          associateWithSpecies(compl,ml.getSpecies());
//        }
//        if(type.equals("complex_mod")||type.equals("orthocomplex_mod")){
//          // however, modified complexes are represented as entities
//          compl = biopax_DASH_level2_DOT_owlFactory.createcomplex(biopax.namespaceString+id+"e",biopax.biopaxmodel);
//          sequenceParticipant sp = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+id,biopax.biopaxmodel);
//          sp.setPHYSICAL_DASH_ENTITY(compl);
//          ent = compl;
//          associateWithSpecies(compl,ml.getSpecies());
//        }
//        if(type.equals("family")||type.equals("orthofamily")||type.equals("group (XOR)")){
//          ent = biopax_DASH_level2_DOT_owlFactory.createphysicalEntity(biopax.namespaceString+id+"e",biopax.biopaxmodel);
//          sequenceParticipant sp = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+id,biopax.biopaxmodel);
//          sp.setPHYSICAL_DASH_ENTITY(ent);
//          associateWithSpecies(ent,ml.getSpecies());
//        }
//        if(type.equals("other")){
//          sm = biopax_DASH_level2_DOT_owlFactory.createsmallMolecule(biopax.namespaceString+id+"e",biopax.biopaxmodel);
//          sequenceParticipant sp = biopax_DASH_level2_DOT_owlFactory.createsequenceParticipant(biopax.namespaceString+id,biopax.biopaxmodel);
//          sp.setPHYSICAL_DASH_ENTITY(sm);
//          ent = sm;
//          associateWithSpecies(ent,ml.getSpecies());
//        }
//        
//        if(ent!=null){
//
//            ent.addCOMMENT("TRANSPATH_TYPE: "+type);
//            if((ml.getKlass()!=null)&&(!ml.getKlass().trim().equals("")))
//               ent.addCOMMENT("TRANSPATH_KLASS: "+ml.getKlass());
//            for(int kk=0;kk<ml.getComments().getItemArray().length;kk++){
//              ItemDocument.Item it = ml.getComments().getItemArray(kk);
//              String annid = getXLink(it,"href");
//              if(annotationHash.get(annid)!=null){
//                //System.out.println("annotation found for "+r.getId());
//                AnnotateDocument.Annotate ann = (AnnotateDocument.Annotate)annotationHash.get(annid);
//                ent.addCOMMENT("TRANSPATH_COMMENT: "+ann.getText());
//                if(ann.getSource()!=null){
//                   for(int k=0;k<ann.getSource().getItemArray().length;k++){
//                     ItemDocument.Item it1 = ann.getSource().getItemArray(k);
//                     String sid = getXLink(it1,"href");
//                     publicationXref pxf = (publicationXref)entities.get(sid);
//                     if(pxf==null){
//                       pxf = biopax_DASH_level2_DOT_owlFactory.createpublicationXref(biopax.namespaceString+sid,biopax.biopaxmodel);
//                     }
//                     ent.addXREF(pxf);
//                   }
//                }
//            }
//            }
//
//            ent.setNAME(ml.getName());
//            entities.put(id,ent);
//
//            for(int k=0;k<ml.getLocationsArray().length;k++){
//              LocationsDocument.Locations lc = ml.getLocationsArray(k);
//              for(int kk=0;kk<lc.getCompartmentArray().length;kk++){
//                 String comp = lc.getCompartmentArray(kk);
//                 ent.addCOMMENT("TRANSPATH_COMPARTMENT: "+comp);
//              }
//            }
//
//            if(ml.getGenomicAccnosArray()!=null)
//            for(int k=0;k<ml.getGenomicAccnosArray().length;k++){
//              addAccno(ent,ml.getGenomicAccnosArray(k));
//            }
//            if(ml.getSynonymsArray()!=null)
//            for(int k=0;k<ml.getSynonymsArray().length;k++){
//              addSynonym(ent,ml.getSynonymsArray(k));
//            }
//            if(ml.getReferences()!=null)
//            for(int k=0;k<ml.getReferences().getItemArray().length;k++){
//              addPublication(ent,ml.getReferences().getItemArray(k));
//            }
//
//
//
//            }        
//        
//        return ent;
//  }
//
//  public void associateWithSpecies(PhysicalEntity ent, String species) throws Exception{
//    if(species!=null){
//    StringTokenizer st = new StringTokenizer(species,";");
//    while(st.hasMoreTokens()){
//      String sp = st.nextToken().trim();
//      if(sp.trim().length()>2){
//        BioSource bs = (BioSource)entities.get(sp);
//        if(bs==null){
//          bs = biopax_DASH_level3_DOT_owlFactory.createBioSource(biopax.namespaceString+Utils.correctName(sp),biopax.biopaxmodel);
//          bs.addName(sp);
//          entities.put(sp,bs);
//        }
////      if(ent instanceof Dna) ((Dna)ent).addOrganism(bs);
////      if(ent instanceof Protein) ((Protein)ent).setORGANISM(bs);
////      if(ent instanceof Complex) ((Complex)ent).addORGANISM(bs);
//      }
//    }
//    }
//  }
//
//  public void addAccno(entity ent, String accno) throws Exception{
//    StringTokenizer st = new StringTokenizer(accno,":");
//    String DB = "";
//    String version = "";
//    String id = "";
//    if(st.hasMoreTokens()) DB = st.nextToken();
//    if(st.hasMoreTokens()) version = st.nextToken();
//    if(st.hasMoreTokens()) id = st.nextToken();
//    /*String uri = DB+"_"+version+"_"+id;
//    unificationXref ux = (unificationXref)entities.get(uri);
//    if(ux==null){
//      ux = biopax_DASH_level2_DOT_owlFactory.createunificationXref(biopax.namespaceString+uri,biopax.biopaxmodel);
//      entities.put(uri,ux);
//    }
//    ux.setDB(DB);
//    ux.setDB_DASH_VERSION(version);
//    ux.setID(id);*/
//    if(DB.equals("HGNC"))
//      ent.addSYNONYMS(version);
//  }
//
//  public void addAccnoAnnotate(entity ent, String accno) throws Exception{
//    StringTokenizer st = new StringTokenizer(accno,":");
//    String DB = "";
//    String version = "";
//    String id = "";
//    if(st.hasMoreTokens()) DB = st.nextToken();
//    if(st.hasMoreTokens()) version = st.nextToken();
//    if(st.hasMoreTokens()) id = st.nextToken();
//    String uri = DB+"_"+version+"_"+id;
//    if(id.equals("")) uri = DB+"_"+version;
//    unificationXref ux = (unificationXref)entities.get(uri);
//    if(ux==null){
//      ux = biopax_DASH_level2_DOT_owlFactory.createunificationXref(biopax.namespaceString+uri,biopax.biopaxmodel);
//      entities.put(uri,ux);
//    }
//    if(!id.equals("")){
//      ux.setDB(DB);
//      ux.setDB_DASH_VERSION(version);
//      ux.setID(id);
//    }else{
//      ux.setDB(DB);
//      ux.setID(version);
//    }
//    if(DB.equals("HGNC"))
//      ent.addSYNONYMS(version);
//  }
//
//
//  public void addSynonym(entity ent, String syn) throws Exception{
//    StringTokenizer st = new StringTokenizer(syn,";");
//    while(st.hasMoreTokens()){
//      String s = st.nextToken();
//      if(!s.trim().equals(""))
//        ent.addSYNONYMS(s.trim());
//    }
//  }
//
//  public void addPublication(entity ent, ItemDocument.Item it) throws Exception{
//    String id = getXLink(it,"href");
//    if(id!=null){
//      publicationXref px = (publicationXref)entities.get(id);
//      if(px==null){
//        px = biopax_DASH_level2_DOT_owlFactory.createpublicationXref(biopax.namespaceString+id,biopax.biopaxmodel);
//        entities.put(id,px);
//      }
//      ent.addXREF(px);
//    }
//  }
//
//  public String getXLink(ItemDocument.Item it,String attr) throws Exception{
//    String id = null;
//    if(it.getDomNode().getAttributes().getNamedItem("xlink:"+attr)!=null){
//      id = it.getDomNode().getAttributes().getNamedItem("xlink:"+attr).getNodeValue();
//      if(attr.equals("href")){
//        StringTokenizer st = new StringTokenizer(id,"()");
//        st.nextToken(); id = st.nextToken();
//      }
//    }
//    return id;
//  }
//  
//  public void AddModificationToComplexComponent(sequenceParticipant sp, MoleculeDocument.Molecule complex, MoleculeDocument.Molecule component) throws Exception{
//     String complexname = complex.getName().toLowerCase(); 
//	 String cpname = component.getName().toLowerCase();
//	 StringTokenizer st = new StringTokenizer(complex.getName(),":");
//	 String s = null;
//	 while(st.hasMoreTokens()){
//		 s = st.nextToken();
//		 if(s.toLowerCase().startsWith(cpname.toLowerCase())){ // we have found right component
//			 Vector v = extractModificationsFromName(s);
//			    for(int i=0;i<v.size();i++){
//			        String mod = (String)v.get(i);
//			        String uri0 = component.getId()+"_"+Utils.correctName(mod);
//			        String uri = uri0; int k=1;
//			        while(v.indexOf(uri)>=0)
//			          uri = uri0+k;
//			        sequenceFeature sf = biopax_DASH_level2_DOT_owlFactory.createsequenceFeature(biopax.namespaceString+uri,biopax.biopaxmodel);
//			        sf.setNAME(mod);
//			        sp.addSEQUENCE_DASH_FEATURE_DASH_LIST(sf);
//			     }			 
//		 }
//	 }
//  }
//
//  public void AddModificationToParticipant(sequenceParticipant sp, MoleculeDocument.Molecule mol) throws Exception{
//    String name = mol.getName();
//    // First, we try to guess the modifications from the name itself
//    Vector v = extractModificationsFromName(mol.getName());
//    Vector moduris = new Vector();
//    if(v.size()>0){
//    for(int i=0;i<v.size();i++){
//       String mod = (String)v.get(i);
//       String uri0 = mol.getId()+"_"+Utils.correctName(mod);
//       String uri = uri0; int k=1;
//       while(v.indexOf(uri)>=0)
//         uri = uri0+k;
//       moduris.add(uri);
//       sequenceFeature sf = biopax_DASH_level2_DOT_owlFactory.createsequenceFeature(biopax.namespaceString+uri,biopax.biopaxmodel);
//       sf.setNAME(mod);
//       sp.addSEQUENCE_DASH_FEATURE_DASH_LIST(sf);
//    }}else{
//       String uri = mol.getId()+"_"+Utils.correctName(name);
//       sequenceFeature sf = biopax_DASH_level2_DOT_owlFactory.createsequenceFeature(biopax.namespaceString+uri,biopax.biopaxmodel);
//       sp.addSEQUENCE_DASH_FEATURE_DASH_LIST(sf);
//    }
//    // if it does not work we take the name as modification mark
//  }
//
//  public static Vector extractModificationsFromName(String name){
//    Vector res = new Vector();
//    StringTokenizer st = new StringTokenizer(name,"{}");
//    st.nextToken();
//    while(st.hasMoreTokens())
//      res.add(st.nextToken());
//    return res;
//  }
//
//  public void createAccessionTable(String fn) throws Exception{
//
//    FileWriter fw = new FileWriter(fn);
//
//    if(network.getNetwork().getGeneArray()!=null){
//    GeneDocument.Gene ga[] = network.getNetwork().getGeneArray();
//    System.out.println("Processing genes...");
//    for(int i=0;i<ga.length;i++){
//      if(i==(int)(0.001f*i)*1000)
//        System.out.print(i+" ");
//      GeneDocument.Gene g = ga[i];
//      for(int k=0;k<g.getAccnosArray().length;k++){
//        fw.write(g.getAccnosArray(k)+"\t"+g.getId()+"\n");
//      }
//      for(int k=0;k<g.getSynonymsArray().length;k++){
//        String syn = g.getSynonymsArray(k);
//        StringTokenizer st = new StringTokenizer(syn,";");
//        while(st.hasMoreTokens()){
//          String s = st.nextToken();
//          if(s.trim().length()>2)
//            fw.write(s.trim()+"\t"+g.getId()+"\n");
//        }
//      }
//    }
//    }
//
//    if(network.getNetwork().getMoleculeArray()!=null){
//    System.out.println("Processing molecules...");
//    MoleculeDocument.Molecule ma[] = network.getNetwork().getMoleculeArray();
//    HashMap members = new HashMap();
//    HashMap allmolecules = new HashMap();
//    for(int i=0;i<ma.length;i++){
//    	allmolecules.put(ma[i].getId(), ma[i]);
//    }
//    for(int i=0;i<ma.length;i++){
//      if(i==(int)(0.001f*i)*1000)
//        System.out.print(i+" ");
//      MoleculeDocument.Molecule m = ma[i];
//      if(m.getType().indexOf("complex")<0)
//         writeAccNos(m, fw, m.getId());
//      for(int j=0;j<m.getMembers().sizeOfItemArray();j++){
//    	  ItemDocument.Item itm = m.getMembers().getItemArray(j);
//    	  String memid = getXLink(itm,"href");
//    	  //members.put(memid,m);
//    	  MoleculeDocument.Molecule ml = (MoleculeDocument.Molecule)allmolecules.get(memid);
//    	  if(ml.getType().indexOf("complex")<0)
//    	     writeAccNos(ml, fw, m.getId());
//      }
//    }
//    }
//
//    fw.close();
//
//  }
//  
//  private void writeAccNos(MoleculeDocument.Molecule m, FileWriter fw, String id) throws Exception{
//	  
//	  String name = m.getName();
//	  StringTokenizer st1 = new StringTokenizer(name,"(");
//	  String ss = st1.nextToken();
//      if((ss.length()>2)&&(m.getType().indexOf("complex")<0))
//          fw.write(ss+"\t"+id+"\n");
//	  
//      for(int k=0;k<m.getAccnosArray().length;k++){
//          StringTokenizer st = new StringTokenizer(m.getAccnosArray(k),";");
//          while(st.hasMoreTokens()){
//            String s = st.nextToken().trim();
//              fw.write(s+"\t"+id+"\n");
//          }
//        }
//        for(int k=0;k<m.getGenomicAccnosArray().length;k++){
//          StringTokenizer st = new StringTokenizer(m.getGenomicAccnosArray(k),";");
//          while(st.hasMoreTokens()){
//            String s = st.nextToken().trim();
//            if(s.length()>2)//if(s.endsWith("_at"))
//            fw.write(s+"\t"+id+"\n");
//          }
//        }
//        for(int k=0;k<m.getSynonymsArray().length;k++){
//          String syn = m.getSynonymsArray(k);
//          StringTokenizer st = new StringTokenizer(syn,";");
//          while(st.hasMoreTokens()){
//            String s = st.nextToken();
//            if(s.trim().length()>2)
//              fw.write(s.trim()+"\t"+id+"\n");
//          }
//        }	  
//  }
//
//  public void addReferencesToReaction(ReactionDocument.Reaction r, biochemicalReaction br) throws Exception{
//    for(int i=0;i<r.getReferences().getItemArray().length;i++){
//      ItemDocument.Item item = r.getReferences().getItemArray(i);
//      String reactid = getXLink(item,"href");
//      publicationXref xrp = (publicationXref)entities.get(reactid);
//      if(xrp==null){
//        xrp = biopax_DASH_level2_DOT_owlFactory.createpublicationXref(biopax.namespaceString+reactid,biopax.biopaxmodel);
//        entities.put(reactid,xrp);
//      }
//      br.addXREF(xrp);
//    }
//    if(annotations!=null){
//      for(int i=0;i<r.getComments().getItemArray().length;i++){
//        ItemDocument.Item item = r.getComments().getItemArray(i);
//        String annid = getXLink(item,"href");
//        if(annotationHash.get(annid)!=null){
//          //System.out.println("annotation found for "+r.getId());
//          AnnotateDocument.Annotate ann = (AnnotateDocument.Annotate)annotationHash.get(annid);
//          br.addCOMMENT("TRANSPATH_COMMENT: "+ann.getText());
//          if(ann.getSource()!=null){
//             for(int k=0;k<ann.getSource().getItemArray().length;k++){
//               ItemDocument.Item it = ann.getSource().getItemArray(k);
//               String sid = getXLink(it,"href");
//               publicationXref pxf = (publicationXref)entities.get(sid);
//               if(pxf==null){
//                 pxf = biopax_DASH_level2_DOT_owlFactory.createpublicationXref(biopax.namespaceString+sid,biopax.biopaxmodel);
//               }
//               br.addXREF(pxf);
//             }
//          }
//          if(ann.getAccnos()!=null){
//            addAccnoAnnotate(br,ann.getAccnos());
//          }
//        }
//      }
//    }
//  }


}