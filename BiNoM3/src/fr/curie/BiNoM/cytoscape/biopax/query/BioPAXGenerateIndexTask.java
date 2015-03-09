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


import java.io.*;

import javax.swing.JOptionPane;

import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.utils.BioPAXGraphMappingService;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class BioPAXGenerateIndexTask implements Task {
    private String BioPAXFileName = null;
    private String IndexFileName = null;

    public BioPAXGenerateIndexTask(String bioPAXFileName, String indexFileName){
    	BioPAXFileName = bioPAXFileName;
    	IndexFileName = indexFileName;
    }
    
    public void run(TaskMonitor taskMonitor) {
    	taskMonitor.setTitle(getTitle());
    	try {
    		File f = new File(BioPAXFileName);
    		if(f.exists()){
    			BioPAXGraphMappingService bgms = new BioPAXGraphMappingService();
    		    BioPAX biopax = new BioPAX();
    		    System.out.println("Loading BioPAX...");
    		    taskMonitor.setStatusMessage("Loading BioPAX...");
    		    biopax.loadBioPAX(BioPAXFileName);
    		    taskMonitor.setProgress(0.5);
    		    System.out.println("Loaded.");

    		    taskMonitor.setStatusMessage("Mapping BioPAX to index...");
    		    Graph graph = bgms.mapBioPAXToGraph(biopax);
    		    Utils.printUsedMemory();
    		    taskMonitor.setProgress(0.8);;

    		    taskMonitor.setStatusMessage("Saving index...");
    		    Graph gr = graph; // XGMML.convertXGMMLToGraph(graph);
    		    //XGMML.saveToXGMML(graph,IndexFileName);
    		    gr.saveAsCytoscapeXGMML(IndexFileName);
    		    taskMonitor.setProgress(1);
    			
    		}else{
    			System.out.println("ERROR: File "+BioPAXFileName+" does not exist.");
        	    taskMonitor.setProgress(1);
        	    taskMonitor.setStatusMessage("ERROR: File "+BioPAXFileName+" does not exist.");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	    taskMonitor.setProgress(1);
    	    taskMonitor.setStatusMessage("Error generating BioPAX file :" + e);
    	}
    }

    public String getTitle() {
    	return "BiNoM: Generating BioPAX index ";
    }

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}


	
}
