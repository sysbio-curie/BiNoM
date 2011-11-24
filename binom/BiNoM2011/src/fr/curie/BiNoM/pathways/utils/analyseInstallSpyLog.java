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

public class analyseInstallSpyLog {
  public static void main(String[] args) {
    try{
      //LineNumberReader lr = new LineNumberReader(new FileReader("c:\\Program Files\\MJLSoftware\\InstallSpy\\amelieInstallSpyReport.html"));
      LineNumberReader lr = new LineNumberReader(new FileReader("c:\\Program Files\\MJLSoftware\\InstallSpy\\testReport.html"));
      String s = "", sn = "";
      Vector created = new Vector();
      Vector created_times = new Vector();
      Vector deleted = new Vector();
      Vector deleted_times = new Vector();
      boolean ex = false;
      while(!ex){
        sn = lr.readLine();
        if(sn==null) {ex=true; break; }
        if(sn.indexOf("Created")>=0){
          if(s.startsWith("<td width=\"70%\">")){
            s = s.substring(47);
            s = s.substring(0,s.length()-12);
            //System.out.println(s+" Created");
            created.add(s);
            String st = lr.readLine();
            st = st.substring(38);
            st = st.substring(0,st.length()-12);
            created_times.add(st);
          }
        }
        if(sn.indexOf("Deleted")>=0){
          if(s.startsWith("<td width=\"70%\">")){
            s = s.substring(47);
            s = s.substring(0,s.length()-12);
            //System.out.println(s+" Deleted");
            deleted.add(s);
            String st = lr.readLine();
            st = st.substring(38);
            st = st.substring(0,st.length()-12);
            deleted_times.add(st);
          }
        }
        s = sn;
      }
      /*for(int i=0;i<created.size();i++){
        String sc = (String)created.elementAt(i);
        //if(sc.indexOf("pathway")<0)
        if(deleted.indexOf(sc)<0){
          String st = (String)created_times.elementAt(i);
          if(st.startsWith("12:24"))
            System.out.println(sc+" - "+st);
          if(st.startsWith("12:25"))
            System.out.println(sc+" - "+st);
          if(st.startsWith("12:26"))
            System.out.println(sc+" - "+st);
          if(st.startsWith("12:27"))
            System.out.println(sc+" - "+st);
          if(st.startsWith("12:28"))
            System.out.println(sc+" - "+st);
        }
      }*/

      for(int i=0;i<created.size();i++){
        String sc = (String)created.elementAt(i);
        //if(sc.indexOf("pathway")<0)
        //if(deleted.indexOf(sc)<0)
        {
          String st = (String)created_times.elementAt(i);
          //if(st.startsWith("5:03"))
            System.out.println(sc+" - "+st);
          //if(st.startsWith("5:04"))
          //  System.out.println(sc+" - "+st);
          //if(st.startsWith("5:05"))
          //  System.out.println(sc+" - "+st);
          //if(st.startsWith("5:06"))
          //  System.out.println(sc+" - "+st);
        }
      }

      /*for(int i=0;i<deleted.size();i++){
        String sc = (String)deleted.elementAt(i);
           {
          String st = (String)deleted_times.elementAt(i);
          //System.out.println(sc+" - "+st);
          if(st.startsWith("4:20"))
            System.out.println(sc+" - "+st);
          if(st.startsWith("4:21"))
            System.out.println(sc+" - "+st);
          if(st.startsWith("4:22"))
            System.out.println(sc+" - "+st);
        }
      }*/


    }catch(Exception e){
            e.printStackTrace();
    }
  }
}