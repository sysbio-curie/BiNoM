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
package fr.curie.BiNoM.cytoscape.sbml;
import fr.curie.BiNoM.cytoscape.lib.*;
import Main.Launcher;
import edu.rpi.cs.xgmml.*;

import java.util.Vector;
import java.io.File;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import fr.curie.BiNoM.pathways.BioPAXToSBMLConverter;
import fr.curie.BiNoM.pathways.CytoscapeToBioPAXConverter;
import fr.curie.BiNoM.pathways.CytoscapeToCellDesignerConverter;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.cytoscape.biopax.BioPAXExportTask;
import fr.curie.BiNoM.cytoscape.celldesigner.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;


public class SBMLExportTask implements Task {
    private CyNetwork cyNetwork;
    private File file;
    private static java.util.HashMap network_bw = new java.util.HashMap();

    public SBMLExportTask(File file) {
	this.file = file;
    }

    public String getTitle() {
	return "BiNoM: Export SBML " + file.getPath();
    }


    public void run(TaskMonitor taskMonitor) {
    	taskMonitor.setTitle(getTitle());
	try {
            CyNetwork network = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
	    BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(network);

	    Vector species = new Vector();
	    Vector reactions = new Vector();

	    // First situation, we have biopax associated, so we perform BioPAX->SBML conversion
	    if(biopax!=null){
	    
	    BioPAXExportTask.findBioPAXSpeciesAndReactions(species, reactions);

	    CytoscapeToBioPAXConverter.filterIDs(biopax, species, reactions);

	    BioPAXToSBMLConverter b2s = new BioPAXToSBMLConverter();

	    b2s.biopax = biopax;
	    b2s.populateSbml();

	    CellDesigner.saveCellDesigner(b2s.sbmlDoc, file.getAbsolutePath());
	    }
	    
	    
	    org.sbml.x2001.ns.celldesigner.SbmlDocument celldesigner = CellDesignerSourceDB.getInstance().getCellDesigner(network);
	    // Second situation, we have CellDesigner associated
	    if(biopax==null)if(celldesigner!=null){
	    	Vector degraded = new Vector();
	    	Vector speciesAliases = new Vector();
	    	CellDesignerExportTask.findSBMLSpeciesAndReactions(celldesigner, species, speciesAliases, reactions, degraded);
            CytoscapeToCellDesignerConverter.filterIDsCompleteReactions
            (celldesigner, species,
             speciesAliases,
             reactions,
             degraded);
            CellDesigner.saveCellDesigner(celldesigner, file.getAbsolutePath());
	    }
	    
	    
	    // Third situation, nothing is associated
	    if(biopax==null)if(celldesigner==null){
	    	// We first check if some traces of reactions can be found
	    	GraphDocument graphDoc = GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
	    	fr.curie.BiNoM.pathways.analysis.structure.Graph gr = XGMML.convertXGMMLToGraph(graphDoc);
	    	String s = GraphUtils.getListOfReactionsTable(gr, biopax, celldesigner);
	    	SimpleTable st = new SimpleTable();
	    	st.LoadFromSimpleDatFileString(s, true, "\t");
	    	
	    	if(st.rowCount>0){
	    		// Convert as a table of reactions
	    		for(int i=0;i<st.rowCount;i++)
	    			reactions.add(st.stringTable[i][st.fieldNumByName("REACTION")]);
	    		celldesigner = GraphUtils.convertFromListOfReactionsToSBML(reactions);
	    	}else{
	    		// If no traces of annotations were discovered, do some stupid stuff
	    		for(int i=0;i<gr.Edges.size();i++){
	    			Edge e = (Edge)gr.Edges.get(i);
	    			String reactionstring = e.Node1.NodeLabel+"->"+e.Node2.NodeLabel;
	    			reactions.add(reactionstring);
		    		celldesigner = GraphUtils.convertFromListOfReactionsToSBML(reactions);	    			
	    		}
	    	}
    		CellDesigner.saveCellDesigner(celldesigner, file.getAbsolutePath());
	    }
	    

	    // Third situation, 

	    taskMonitor.setStatusMessage("File exported (" + species.size() +
				  " species, " + reactions.size() +
				  " reactions)");
	    taskMonitor.setProgress(1);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setProgress(1);
	    taskMonitor.setStatusMessage("Error importing SBML file " +
				  file.getAbsolutePath() + ": " + e);
	}
    }

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}
}

