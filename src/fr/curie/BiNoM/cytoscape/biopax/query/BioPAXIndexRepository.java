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
package fr.curie.BiNoM.cytoscape.biopax.query;

import fr.curie.BiNoM.pathways.utils.*;

public class BioPAXIndexRepository extends BioPAXRepository{
	
  private AccessionNumberTable accNumTable = null;
  private BioPAXGraphQueryEngine queryEngine = null;
  private String databaseFilename = null;
  private String accNumberFilename = null;
  private String report = "";
  
  static private BioPAXIndexRepository instance = null;

  public BioPAXIndexRepository() {
  }
  
  public void setAccessionNumberTable(AccessionNumberTable acctable){
	  accNumTable = acctable;
  }
  public AccessionNumberTable getAccessionNumberTable(){
	  return accNumTable;
  }
  public void setBioPAXGraphQueryEngine(BioPAXGraphQueryEngine beng){
	  queryEngine = beng;
  }
  public BioPAXGraphQueryEngine getBioPAXGraphQueryEngine(){
	  return queryEngine;
  }
  
  public static BioPAXIndexRepository getInstance(){
	  if(instance==null)
		  instance = new BioPAXIndexRepository();
	  return instance;
  }
  
  public void setDatabaseFileName(String dbfn){
	  databaseFilename = dbfn;
  }
  public String getDatabaseFileName(){
	  return databaseFilename;
  }
  public void setAccNumberFileName(String dbfn){
	  accNumberFilename = dbfn;
  }
  public String getAccNumberFileName(){
	  return accNumberFilename;
  }
  
  public void addToReport(String newMess){
	  report+=newMess;
  }
  public void clearReport(){
	  report="";
  }
  public String getReport(){
	  return report;
  }
  public void setReport(String s){
	  report = s;
  }
  
  
}

