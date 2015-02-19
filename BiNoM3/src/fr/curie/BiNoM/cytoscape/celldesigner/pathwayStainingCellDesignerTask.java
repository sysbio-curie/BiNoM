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
import fr.curie.BiNoM.pathways.CellDesignerColorProteins;
import fr.curie.BiNoM.pathways.coloring.CellDesignerPathwayStaining;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class pathwayStainingCellDesignerTask implements Task {
	
    private TaskMonitor taskMonitor;
    /*
     * If  FeatureTableFileName is empty then random or predefined colors will be used
     */
    private String DataTableFileName = null;
    private String CellDesignerFileName = null;
    private String PngFileName = null;
    /*
     *  If ProteinGroupFileName is empty then all individual proteins will be used
     */
    private String ProteinGroupFileName = null;
    
    private stainingOptions options = null;

	public final class stainingOptions{
		public float influenceRadius = 0.01f;
		public boolean normalizeToZValues = true;
		public boolean useModuleDefinitionsFromCellDesignerFile = false;
		public boolean useProteinNameIfHUGOisntFound = true;
		public float thresholdForComputingGradient = 2f;
		public float gridSizeX = 20f;
		public float gridSizeY = 20f;
		public float scaleImage = 1f;
	}
    
    public pathwayStainingCellDesignerTask(String cellDesignerFileName, String pngFileName, String dataTableFileName, String proteinGroupFileName, stainingOptions _options){
    	DataTableFileName = dataTableFileName;
    	if(DataTableFileName!=null)if(DataTableFileName.trim().equals("")) DataTableFileName = null;
    	CellDesignerFileName = cellDesignerFileName;
    	ProteinGroupFileName = proteinGroupFileName;
    	if(ProteinGroupFileName!=null)if(ProteinGroupFileName.trim().equals("")) ProteinGroupFileName = null;
    	PngFileName = pngFileName;
    	if(PngFileName!=null)if(PngFileName.trim().equals("")) PngFileName = null;
    	options = _options;
    }
    
    public void run() {
    	try {
    		File fc = new File(CellDesignerFileName);
    		if(fc.exists()){
    			CellDesignerPathwayStaining cps = new CellDesignerPathwayStaining();
    			cps.infradius = options.influenceRadius;
    			cps.gridStepX = options.gridSizeX;
    			cps.gridStepY = options.gridSizeY;
    			cps.normalizeColumnValues = options.normalizeToZValues;
    			cps.thresholdGradient = options.thresholdForComputingGradient;
    			cps.useModuleDefinitionsFromCellDesignerFile = options.useModuleDefinitionsFromCellDesignerFile;
    			cps.useProteinNameIfHUGOisntFound = options.useProteinNameIfHUGOisntFound;
    			cps.scaleImage = options.scaleImage;
    			
    			cps.run(CellDesignerFileName, PngFileName, DataTableFileName, ProteinGroupFileName);    			
    		}else{
    			System.out.println("ERROR: File "+CellDesignerFileName+" does not exist.");
        	    taskMonitor.setPercentCompleted(99);
        	    taskMonitor.setStatus("ERROR: File "+CellDesignerFileName+" does not exist.");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	    taskMonitor.setPercentCompleted(100);
    	    taskMonitor.setStatus("Error coloring CellDesigner proteins:" + e);
    	}
    }

    public String getTitle() {
    	return "BiNoM: Staining CellDesigner map...";
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
            throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    
	
	
}
