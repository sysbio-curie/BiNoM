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

package fr.curie.BiNoM.celldesigner.analysis;

import fr.curie.BiNoM.celldesigner.lib.*;
import fr.curie.BiNoM.celldesigner.biopax.*;

import java.io.*;

import edu.rpi.cs.xgmml.*;
import java.io.InputStream;

import java.util.Vector;
import java.util.Iterator;

import java.io.File;
import java.net.URL;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.pathways.analysis.structure.*;

import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerSourceDB;
import fr.curie.BiNoM.pathways.CytoscapeToBioPAXConverter;
import fr.curie.BiNoM.pathways.CytoscapeToCellDesignerConverter;
import fr.curie.BiNoM.pathways.CellDesignerToBioPAXConverter;
import org.sbml.x2001.ns.celldesigner.*;
import edu.rpi.cs.xgmml.*;

import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerExportTask;
import fr.curie.BiNoM.lib.AbstractTask;

import fr.curie.BiNoM.celldesigner.lib.*;

import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.CellDesignerPlugin;

public class ClusterNetworksTask implements AbstractTask {

    private PluginModel model;
    private CellDesignerPlugin plugin;
    private int idxs[];
    private float intersectionThreshold = 0.3f;

    public ClusterNetworksTask(PluginModel model, CellDesignerPlugin plugin,
			       int idxs[], StructureAnalysisUtils.Option options) {
	this.model = model;
	this.plugin = plugin;
	this.idxs = idxs;
	this.intersectionThreshold = options.intersectionThreshold;
    }

    public String getTitle() {
	return "BiNoM: Clustering networks";
    }

    public void execute() {
	try {
	    Vector<GraphDocument> networks = new Vector<GraphDocument>();

	    for(int i = 0; i < idxs.length; i++) {
		//GraphDocument gr = GraphDocumentFactory.getInstance().createGraphDocument(NetworkUtils.getNetwork(idxs[i]));
		//		GraphDocument gr = GraphDocumentFactory.getInstance().createGraphDocument(null);
		GraphDocument gr = GraphDocumentFactory.getInstance().createGraphDocument(model);
		networks.add(gr);
	    }

	    Vector<GraphDocument> v = StructureAnalysisUtils.getClusteredNetworks(networks, intersectionThreshold);
		
	    int size = v.size();
	    for (int n = 0; n < size; n++) {
		GraphDocument grDoc = (GraphDocument)v.get(n);
		NetworkFactory.createNetwork
		    (model,
		     plugin,
		     grDoc);
	    }
	}
	catch(Exception e) {
	    e.printStackTrace();
	}
    }
}
