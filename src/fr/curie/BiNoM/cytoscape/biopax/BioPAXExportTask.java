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
import Main.Launcher;

import java.io.*;

import edu.rpi.cs.xgmml.*;

import java.io.InputStream;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import java.util.Vector;
import java.util.Iterator;
import java.io.File;
import java.net.URL;

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
	private CyNetwork cyNetwork;
	private File file;
	boolean merge;

	public BioPAXExportTask(File file, boolean merge) {
		this.file = file;
		this.merge = merge;
	}


	public String getTitle() {
		return "BiNoM: Export BioPAX " + file.getPath();
	}


	public void run(TaskMonitor taskMonitor) {
		taskMonitor.setTitle(getTitle());
		try {

			
			CyNetwork network = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
			BioPAX biopax = BioPAXSourceDB.getInstance().getBioPAX(network);
			SbmlDocument sbml = CellDesignerSourceDB.getInstance().getCellDesigner(network);

			if (biopax != null) {
				Vector species = new Vector();
				Vector reactions = new Vector();
				//findBioPAXSpeciesAndReactions(species, reactions);

				//CytoscapeToBioPAXConverter.filterIDs(biopax, species, reactions);
				
				// print out the nb of nodes and edges
				GraphDocument graphDocument = GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()); 

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

				taskMonitor.setStatusMessage("File exported (" + species.size() +
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


				taskMonitor.setStatusMessage("File exported (" + species.size() +
						" species, " + reactions.size() +
				" reactions)");
			}

			taskMonitor.setProgress(1);
		}
		catch(Exception e) {
			e.printStackTrace();
			taskMonitor.setProgress(1);
			taskMonitor.setStatusMessage("Error exporting BioPAX file " +
					file.getAbsolutePath() + ": " + e);
		}
	}

	public static void findBioPAXSpeciesAndReactions(Vector species, Vector reactions) {
		CyNetwork netw = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
		CyNetworkView view = Launcher.getAdapter().getCyApplicationManager().getCurrentNetworkView();

		/*
	  if (view.getSelectedNodes().size() == 0) {
	  JOptionPane.showMessageDialog(view.getComponent(),
	  "Please select one or more nodes.");
	  }
		 */

		for (Iterator i = netw.getNodeList().iterator(); i.hasNext(); ) {
			CyNode node = (CyNode) i.next();
			Object o;

			o = netw.getRow(node).get("BIOPAX_SPECIES", String.class);
			if (o != null)
				species.add(o);

			o = netw.getRow(node).get("BIOPAX_REACTION", String.class);
			if (o != null)
				reactions.add(o);
		}

		System.out.println("vectors " + species.size() + " " +
				reactions.size());
	}


	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

}



