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

import cytoscape.Cytoscape;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import java.io.*;
import javax.swing.JOptionPane;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class BioPAXLoadIndexTask implements Task {
    private TaskMonitor taskMonitor;
    private String AccNumFileName = null;
    private String IndexFileName = null;

    public BioPAXLoadIndexTask(String indexFileName, String accNumFileName){
    	AccNumFileName = accNumFileName;
    	IndexFileName = indexFileName;
    }
    
    public void run() {
    	try {
    		File f = new File(IndexFileName);
    		if(f.exists()){

    			System.out.println("Loading Index...");
    		    taskMonitor.setStatus("Loading Index...");
    		    BioPAXGraphQueryEngine beng = new BioPAXGraphQueryEngine();
    		    GraphXGMMLParser gp = new GraphXGMMLParser();
    		    gp.parse(IndexFileName);
    		    beng.setDatabase(gp.graph);
    		    BioPAXIndexRepository repository = BioPAXIndexRepository.getInstance();
    		    repository.setBioPAXGraphQueryEngine(beng);
    		    repository.setDatabaseFileName(IndexFileName);
    		    taskMonitor.setPercentCompleted(50);
    		    System.out.println("Loaded.");

    		    if(!AccNumFileName.trim().equals("")){
    		    f = new File(AccNumFileName);
    		    if(f.exists()){
        		    taskMonitor.setStatus("Loading Accession Number table...");
        		    AccessionNumberTable table = new AccessionNumberTable();
        		    table.loadTable(AccNumFileName);
        		    repository.setAccessionNumberTable(table);
        		    repository.setAccNumberFileName(AccNumFileName);
    		    }else{
        			System.out.println("ERROR: File "+IndexFileName+" does not exist.");
            	    taskMonitor.setPercentCompleted(99);
            	    taskMonitor.setStatus("ERROR: File "+IndexFileName+" does not exist.");
    		    }
    		    }

    		    Utils.printUsedMemory();
    		    taskMonitor.setPercentCompleted(100);

    			
    		}else{
    			System.out.println("ERROR: File "+IndexFileName+" does not exist.");
        	    taskMonitor.setPercentCompleted(99);
        	    taskMonitor.setStatus("ERROR: File "+IndexFileName+" does not exist.");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	    taskMonitor.setPercentCompleted(100);
    	    taskMonitor.setStatus("Error loading BioPAX file :" + e);
    	}
    }

    public String getTitle() {
    	return "BiNoM: Loading BioPAX index ";
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
            throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    
	
	
}
