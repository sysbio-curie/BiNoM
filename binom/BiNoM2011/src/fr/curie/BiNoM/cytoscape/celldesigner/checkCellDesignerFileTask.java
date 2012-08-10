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
package fr.curie.BiNoM.cytoscape.celldesigner;

import cytoscape.Cytoscape;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import java.io.*;
import javax.swing.JOptionPane;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.cytoscape.utils.ShowTextDialog;
import fr.curie.BiNoM.pathways.CellDesignerColorProteins;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class checkCellDesignerFileTask implements Task {
    private TaskMonitor taskMonitor;
    private String CellDesignerFileName = null;

    public checkCellDesignerFileTask(String cellDesignerFileName){
    	CellDesignerFileName = cellDesignerFileName;
    }
    
    public void run() {
    	try {
    		File fc = new File(CellDesignerFileName);
    		if(fc.exists()){
    			
    			CheckCellDesignerFile cf = new CheckCellDesignerFile();
    			cf.sbmlDoc = CellDesigner.loadCellDesigner(CellDesignerFileName);
    			cf.checkIfReactionConnectedToIncludedSpecies();
    			
        		ShowTextDialog dialog = new ShowTextDialog();
        		dialog.pop("Checking CellDesigner File", cf.report);
    			
    		}else{
    			System.out.println("ERROR: File "+CellDesignerFileName+" does not exist.");
        	    taskMonitor.setPercentCompleted(99);
        	    taskMonitor.setStatus("ERROR: File "+CellDesignerFileName+" does not exist.");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	    taskMonitor.setPercentCompleted(100);
    	    taskMonitor.setStatus("Error checkin CellDesigner file:" + e);
    	}
    }

    public String getTitle() {
    	return "BiNoM: Check CellDesigner file...";
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
            throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    
	
	
}
