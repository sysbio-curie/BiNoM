package fr.curie.BiNoM.pathways.test;

import java.io.*;
import java.util.*;

/**
 * <p>Title: Pathway library</p>
 * <p>Description: Various tools for managing and analyzing biological pathways</p>
 * <p>Copyright: Copyright (c) 2005-2006</p>
 * <p>Company: Bioinformatics Service of Instiute of Curie, Paris, France (bioinfo.curie.fr)</p>
 * @author Dr. Andrei Zinovyev http://www.ihes.fr/~zinovyev
 * @version alpha
 */

public class workMatlabOvidiu {

  public static void main(String[] args) {
    try{

      HashMap speciesMap = new HashMap();
      Vector species = new Vector();
      Vector weights = new Vector();

      //LineNumberReader lr1 = new LineNumberReader(new FileReader("c:/datas/nfkb/070706_1/models/species_cd_corresp.txt"));
      //LineNumberReader lr1 = new LineNumberReader(new FileReader("c:/datas/nfkb/070706_1/models/species_hoffman_corresp.txt"));
      //LineNumberReader lr1 = new LineNumberReader(new FileReader("c:/datas/nfkb/hierarchical/species_simplest_corresp.txt"));
      LineNumberReader lr1 = new LineNumberReader(new FileReader("c:/datas/nfkb/070706_1/models/species_lipniacki_corresp.txt"));
      String s = null;
      while((s=lr1.readLine())!=null){
        StringTokenizer st = new StringTokenizer(s,"\tTable()");
        String spname = st.nextToken();
        if(!spname.trim().equals("")){
          String matlabId = st.nextToken();
          System.out.println(spname+"\t"+matlabId);
          speciesMap.put(matlabId,spname);
        }
      }


      //LineNumberReader lr = new LineNumberReader(new FileReader("c:/datas/nfkb/070706_1/models/pca_order.txt"));
      //LineNumberReader lr = new LineNumberReader(new FileReader("c:/datas/nfkb/070706_1/models/pca_order_hoff.txt"));
      //LineNumberReader lr = new LineNumberReader(new FileReader("c:/datas/nfkb/hierarchical/pca_order_simplest.txt"));
      LineNumberReader lr = new LineNumberReader(new FileReader("c:/datas/nfkb/070706_1/models/pca_order_lipniacki.txt"));
      s = null;
      while((s=lr.readLine())!=null){
        StringTokenizer st = new StringTokenizer(s,"\t ");
        String w = st.nextToken();
        String ind = st.nextToken();
        species.add(new Integer((int)Float.parseFloat(ind)));
        weights.add(new Float(Float.parseFloat(w)));
      }

    for(int i=0;i<species.size();i++){
      int sp = ((Integer)species.elementAt(i)).intValue();
      String spname = (String)speciesMap.get(""+sp);
      if(spname==null)
        System.out.println("Id not found : "+sp);
      else
        System.out.println(spname+"\t"+(Float)weights.elementAt(i));
    }

    }catch(Exception e){
      e.printStackTrace();
    }
  }
}