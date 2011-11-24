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
package fr.curie.BiNoM.cytoscape.biopax;

import fr.curie.BiNoM.cytoscape.lib.*;

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import cytoscape.visual.VisualMappingManager;
import cytoscape.visual.VisualStyle;
import java.io.*;
import cytoscape.task.ui.JTaskConfig;

import edu.rpi.cs.xgmml.*;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.CyEdge;
import cytoscape.view.CyNetworkView;
import cytoscape.data.Semantics;
import cytoscape.visual.*;
import java.io.InputStream;

import java.util.Vector;
import java.util.Iterator;

import java.io.File;
import java.net.URL;
import giny.view.NodeView;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;

import fr.curie.BiNoM.biopax.BioPAXSourceDB;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerSourceDB;
import fr.curie.BiNoM.pathways.CytoscapeToBioPAXConverter;
import fr.curie.BiNoM.pathways.CytoscapeToCellDesignerConverter;
import fr.curie.BiNoM.pathways.CellDesignerToBioPAXConverter;
import org.sbml.x2001.ns.celldesigner.*;

import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerExportTask;

public class BioPAXExportTask implements Task {
	private TaskMonitor taskMonitor;
	private CyNetwork cyNetwork;
	private File file;
	private static java.util.HashMap network_bw = new java.util.HashMap();
	boolean merge;

	public BioPAXExportTask(File file, boolean merge) {
		this.file = file;
		this.merge = merge;
	}

	public void halt() {
	}

	public void setTaskMonitor(TaskMonitor taskMonitor)
	throws IllegalThreadStateException {
		this.taskMonitor = taskMonitor;
	}

	public String getTitle() {
		return "BiNoM: Export BioPAX " + file.getPath();
	}

	public CyNetwork getCyNetwork() {
		return cyNetwork;
	}

	public void run() {
		try {
			
			CyNetwork network = Cytoscape.getCurrentNetwork();
			BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(network);
			SbmlDocument sbml = CellDesignerSourceDB.getInstance().getCellDesigner(network);

			if (biopax != null) {

				Vector species = new Vector();
				Vector reactions = new Vector();
				//findBioPAXSpeciesAndReactions(species, reactions);

				//CytoscapeToBioPAXConverter.filterIDs(biopax, species, reactions);
				
				// print out the nb of nodes and edges
				GraphDocument graphDocument = GraphDocumentFactory.getInstance().createGraphDocument
				(Cytoscape.getCurrentNetwork()); 

				BioPAX resbiopax = CytoscapeToBioPAXConverter.convert(graphDocument, biopax);
				
				if(merge){
					System.out.println("Merging...");
					BioPAX original = new BioPAX();
					original.loadBioPAX(file.getAbsolutePath());
					BioPAXMerge merge = new BioPAXMerge();
					merge.mainfile = original.model;
					merge.referenceFiles.add(resbiopax.model);
					merge.mergeMainWithReferences();
					resbiopax.model = merge.mainfile;
				}

				//biopax.saveToFile(file.getAbsolutePath(), biopax.model);
				System.out.println("Saving "+file.getAbsolutePath()+"...");
				resbiopax.saveToFile(file.getAbsolutePath(), resbiopax.model);

				taskMonitor.setStatus("File exported (" + species.size() +
						" species, " + reactions.size() +
				" reactions)");

			} else if (sbml != null) {
				Vector species = new Vector();
				Vector speciesAliases = new Vector();
				Vector reactions = new Vector();
				Vector degraded = new Vector();

				CellDesignerExportTask.findSBMLSpeciesAndReactions
				(sbml, species, speciesAliases,
						reactions, degraded);

				// celldesigner -> biopax
				CytoscapeToCellDesignerConverter.filterIDsCompleteReactions(sbml, species,
						speciesAliases,
						reactions,
						degraded);

				CellDesignerToBioPAXConverter cd2bp = new CellDesignerToBioPAXConverter();

				cd2bp.sbml = sbml;
				cd2bp.biopax = new BioPAX();
				biopax = cd2bp.convert();
				cd2bp.biopax.saveToFile(file.getAbsolutePath(), cd2bp.biopax.biopaxmodel);


				taskMonitor.setStatus("File exported (" + species.size() +
						" species, " + reactions.size() +
				" reactions)");
			}

			taskMonitor.setPercentCompleted(100);
		}
		catch(Exception e) {
			e.printStackTrace();
			taskMonitor.setPercentCompleted(100);
			taskMonitor.setStatus("Error exporting BioPAX file " +
					file.getAbsolutePath() + ": " + e);
		}
	}

	public static void findBioPAXSpeciesAndReactions(Vector species, Vector reactions) {
		CyNetworkView view = Cytoscape.getCurrentNetworkView();
		cytoscape.data.CyAttributes nodeAttrs = Cytoscape.getNodeAttributes();

		/*
	  if (view.getSelectedNodes().size() == 0) {
	  JOptionPane.showMessageDialog(view.getComponent(),
	  "Please select one or more nodes.");
	  }
		 */

		for (Iterator i = view.getNodeViewsIterator(); i.hasNext(); ) {
			NodeView nView = (NodeView)i.next();
			CyNode node = (CyNode)nView.getNode();
			Object o;

			o = nodeAttrs.getStringAttribute(node.getIdentifier(),
					"BIOPAX_SPECIES");
			if (o != null)
				species.add(o);

			o = nodeAttrs.getStringAttribute(node.getIdentifier(),
			"BIOPAX_REACTION");
			if (o != null)
				reactions.add(o);
		}

		System.out.println("vectors " + species.size() + " " +
				reactions.size());
	}

}



