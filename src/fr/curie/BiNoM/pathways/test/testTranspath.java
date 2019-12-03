package fr.curie.BiNoM.pathways.test;

import com.biobaseInternational.*;
import java.io.*;
import java.util.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.shared.*;

public class testTranspath {
  public static void main(String[] args) {
    try{


      /*LineNumberReader lr = new LineNumberReader(new FileReader("c:/datas/biobase/temp1"));
      String s = null;
      FileWriter ffw = new FileWriter("c:/datas/biobase/temp2");
      Set hs = new HashSet();
      while((s=lr.readLine())!=null){
        StringTokenizer st = new StringTokenizer(s,";");
        while(st.hasMoreTokens())
          hs.add(st.nextToken().trim());
      }
      Iterator itt = hs.iterator();
      while(itt.hasNext()) ffw.write((String)itt.next()+"\n");
      ffw.close();
      System.exit(0);*/

      /*NetworkDocument nd = com.biobaseInternational.NetworkDocument.Factory.parse(new File("c:/datas/biobase/gene.xml"));
      for(int i=0;i<nd.getNetwork().getGeneArray().length;i++){
        String id = nd.getNetwork().getGeneArray(i).getId();
        String name = nd.getNetwork().getGeneArray(i).getName();
        String fullname = nd.getNetwork().getGeneArray(i).getFullname();
        //String info = nd.getNetwork().getGeneArray(i).ge;
        System.out.println(id+"\t"+name+"\t"+fullname);
      }
      Utils.printUsedMemory();*/

      /*NetworkDocument ndp = com.biobaseInternational.NetworkDocument.Factory.parse(new File("c:/datas/biobase/pathway.xml"));
      PathwayDocument.Pathway pa[] = ndp.getNetwork().getPathwayArray();
      Set pt = new HashSet();
      for(int i=0;i<pa.length;i++){
        String type = pa[i].getType();
        pt.add(type);
      }
      Iterator itt = pt.iterator();
      while(itt.hasNext()){
        System.out.print((String)itt.next()+"\n");
      }
      System.exit(0);*/

      HashMap types = new HashMap();
      int count=0;
      NetworkDocument nd = com.biobaseInternational.NetworkDocument.Factory.parse(new File("c:/datas/biobase/molecule.xml"));
      HashMap species = new HashMap();
      MoleculeDocument.Molecule ma[] = nd.getNetwork().getMoleculeArray();
      for(int i=0;i<ma.length;i++){
        //if(i==(int)(0.001f*i)*1000)
        //  System.out.print(i+":\t");
        String id = ma[i].getId();
        String name = ma[i].getName();
        //System.out.println(id+"\t"+name);
        /*String tp = nd.getNetwork().getMoleculeArray(i).getType();
        if(types.get(tp)==null){
          System.out.println(tp);
          types.put(tp,tp);
        }*/
        if(ma[i].getSpecies()!=null){
              StringTokenizer st = new StringTokenizer(ma[i].getSpecies(),";");
              while(st.hasMoreTokens()){
              String cmp = (st.nextToken());
              if(!cmp.trim().equals("")){
              Integer in = (Integer)species.get(cmp);
              if(in==null){
                in = new Integer(1);
              }else{
                in = new Integer(in.intValue()+1);
              }
              species.put(cmp,in);
              }
              }
        }
      }
      Iterator it = species.keySet().iterator();
      while(it.hasNext()){
        String sp = (String)it.next();
        Integer in = (Integer)species.get(sp);
        System.out.print(sp+"\t"+in.intValue()+"\n");
      }

        /*String type = nd.getNetwork().getMoleculeArray(i).getType();
        if((type.indexOf("_mod")>=0)&&(type.indexOf("complex")<0)){
          System.out.print((++count)+"\t"+name+"\t");
          Vector v = Transpath.extractModificationsFromName(name);
          for(int k=0;k<v.size();k++){
            System.out.print((String)v.get(k)+"\t");
            if(types.get(v.get(k))==null){
              types.put(v.get(k),v.get(k));
            }
          }
          System.out.print("\n");
        }
      }
      Utils.printUsedMemory();
      Iterator it = types.keySet().iterator();
      while(it.hasNext()){
        System.out.print((String)types.get(it.next())+"\n");
      }*/

    /*HashMap type = new HashMap();
    HashMap quality = new HashMap();
    HashMap direct = new HashMap();
    HashMap effect = new HashMap();
    NetworkDocument nd = com.biobaseInternational.NetworkDocument.Factory.parse(new File("c:/datas/biobase/reaction.xml"));
    ReactionDocument.Reaction ra[] = nd.getNetwork().getReactionArray();
    int pathway_steps = 0;
    int molecular_evidences = 0;
    int semantics = 0;
    int decompositions = 0;
    HashMap species = new HashMap();
    for(int i=0;i<ra.length;i++){
      if(i==(int)(0.001f*i)*1000)
        System.out.print(i+":\t");
      ReactionDocument.Reaction r = ra[i];
      //if(r.getInhibitor()!=null) if(r.getInhibitor().getItemArray().length>0)
      //  System.out.println("inhibitor not null for "+r.getId());
      if(r.getEnzyme().getItemArray()!=null) if(r.getEnzyme().getItemArray().length>1)
        System.out.println("enzyme length >1 not null for "+r.getId());
      if(type.get(r.getType())==null) type.put(r.getType(),r.getType());
      if(type.get(r.getQuality())==null) quality.put(r.getQuality(),r.getQuality());
      if(type.get(r.getDirect())==null) direct.put(r.getDirect(),r.getDirect());
      if(type.get(r.getEffect())==null) effect.put(r.getEffect(),r.getEffect());
      if(r.getType().equals("molecular evidence")) molecular_evidences++;
      if(r.getType().equals("pathway step")) pathway_steps++;
      if(r.getType().equals("semantic")) semantics++;
      if(r.getType().equals("decomposition")) decompositions++;
      LocationsDocument.Locations la[] = r.getLocationsArray();
      //if(r.getType().equals("pathway step"))
      for(int k=0;k<la.length;k++){
        LocationsDocument.Locations l = la[k];
        for(int j=0;j<l.getCompartmentArray().length;j++){
          Integer in = (Integer)species.get(l.getCompartmentArray(j));
          if(in==null){
            in = new Integer(1);
          }else{
            in = new Integer(in.intValue()+1);
          }
          species.put(l.getCompartmentArray(j),in);
        }
      }
    }
    System.out.println("\nTotal : semantic "+semantics+" pathway step "+pathway_steps+" molecular evidence "+molecular_evidences+" decomposition "+decompositions);
    Iterator it = species.keySet().iterator();
    while(it.hasNext()){
      String sp = (String)it.next();
      Integer in = (Integer)species.get(sp);
      System.out.println(sp+"\t"+in.intValue());
    }*/
    /*System.out.println("Types:"); Iterator it = type.keySet().iterator();
    while(it.hasNext())
      System.out.print((String)type.get(it.next())+"\n");
    System.out.println("Quality:"); it = quality.keySet().iterator();
    while(it.hasNext())
      System.out.print((String)quality.get(it.next())+"\n");
    System.out.println("Direct:"); it = direct.keySet().iterator();
    while(it.hasNext())
      System.out.print((String)direct.get(it.next())+"\n");
    System.out.println("Effect:"); it = effect.keySet().iterator();
    while(it.hasNext())
      System.out.print((String)effect.get(it.next())+"\n");*/
    Utils.printUsedMemory();

    }catch(Exception e){
      e.printStackTrace();
    }
  }
}