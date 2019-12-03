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

import java.io.*;

import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;

public class modifyCellDesignerNotesTask implements Task {
    private String FeatureTableFileName = null;
    private String CellDesignerFileName = null;

    public modifyCellDesignerNotesTask(String cellDesignerFileName, String featureTableFileName){
    	FeatureTableFileName = featureTableFileName;
    	CellDesignerFileName = cellDesignerFileName;
    }
    
    public void run(TaskMonitor taskMonitor) {
    	taskMonitor.setTitle(getTitle());
    	try {
    		File fc = new File(CellDesignerFileName);
    		File ft = new File(FeatureTableFileName);
    		if(fc.exists()&&ft.exists()){
    			
    			ModifyCellDesignerNotes mn = new ModifyCellDesignerNotes();
    			
    			String nameCD = CellDesignerFileName;
    			if(nameCD.toLowerCase().endsWith(".xml")){
    				nameCD = nameCD.substring(0, nameCD.length()-4);
    			}
    			
    			mn.sbmlDoc = CellDesigner.loadCellDesigner(nameCD+".xml");
    			mn.comments = Utils.loadString(FeatureTableFileName);
    			mn.ModifyCellDesignerNotes();
    			CellDesigner.saveCellDesigner(mn.sbmlDoc, nameCD+"_notes.xml");
    			
    		}else{
    			System.out.println("ERROR: File "+CellDesignerFileName+" does not exist.");
        	    taskMonitor.setProgress(1);
        	    taskMonitor.setStatusMessage("ERROR: File "+CellDesignerFileName+" does not exist.");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	    taskMonitor.setProgress(1);
    	    taskMonitor.setStatusMessage("Error modifying CellDesigner notes:" + e);
    	}
    }

    public String getTitle() {
    	return "BiNoM: Modify CellDesigner notes...";
    }

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}
}
