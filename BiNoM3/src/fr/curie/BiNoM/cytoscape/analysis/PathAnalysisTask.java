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
package fr.curie.BiNoM.cytoscape.analysis;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.AbstractTask;
import edu.rpi.cs.xgmml.*;

import java.util.Vector;
import java.util.Set;

import fr.curie.BiNoM.pathways.analysis.structure.*;

public class PathAnalysisTask extends AbstractTask{

    private GraphDocument network;
    private VisualStyle vizsty;
    private Vector<String> sources;
    private Vector<String> targets;
    private StructureAnalysisUtils.Option options;
    
    public Set<String> SelectedNodes;


    public PathAnalysisTask(GraphDocument network, Vector<String> sources, Vector<String> targets, StructureAnalysisUtils.Option options, org.cytoscape.view.vizmap.VisualStyle visualStyle) {
	this.network = network;
	this.vizsty = visualStyle;
	this.sources = sources;
	this.targets = targets;
	this.options = options;
    }


    public String getTitle() {
	return "BiNoM: Path analysis";
    }


	public void run(org.cytoscape.work.TaskMonitor taskMonitor) throws Exception {
		taskMonitor.setTitle(getTitle());
	try {
		SelectedNodes = StructureAnalysisUtils.findPaths(network,sources,targets,options);
	    taskMonitor.setProgress(1);;
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setProgress(1);
	    taskMonitor.setStatusMessage("Error in path analysis " + e);
	}
    }
}
