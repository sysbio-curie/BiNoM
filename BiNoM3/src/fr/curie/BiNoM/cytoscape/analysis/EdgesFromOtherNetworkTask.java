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
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JOptionPane;

import fr.curie.BiNoM.cytoscape.lib.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import Main.Launcher;

import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyEdge;


public class EdgesFromOtherNetworkTask implements Task {

    CyNetwork networks[];
    CyNetwork netwFrom;

    public EdgesFromOtherNetworkTask(CyNetwork networks[], CyNetwork netwFrom) {
	this.networks = networks;
	this.netwFrom = netwFrom;
    }


    public String getTitle() {
	return "BiNoM: Update Connections from other network";
    }


    public void run(TaskMonitor taskMonitor) {
    	if(netwFrom != null && networks != null){
	    	taskMonitor.setTitle(getTitle());
			try {
		
			    /*taskMonitor.setStatus(netw_cnt +
						  " Network" + (netw_cnt != 1 ? "s" : "") +
						  " Updated");*/
				fr.curie.BiNoM.pathways.analysis.structure.Graph gnetworks[] = new fr.curie.BiNoM.pathways.analysis.structure.Graph[networks.length];
				fr.curie.BiNoM.pathways.analysis.structure.Graph gnetwFrom = XGMML.convertXGMMLToGraph(GraphDocumentFactory.getInstance().createGraphDocument(netwFrom));
				
				
				
				for(int i=0;i<networks.length;i++){
				    gnetworks[i] = XGMML.convertXGMMLToGraph(GraphDocumentFactory.getInstance().createGraphDocument(networks[i]));
					gnetworks[i].addConnections(gnetwFrom);
					java.util.Iterator it = netwFrom.getEdgeList().iterator();
					
					Collection<CyNetworkView> networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(networks[i]);
					Iterator<CyNetworkView> networkViewIterator = networkViews.iterator();
					CyNetworkView toView =null;
					while(networkViewIterator.hasNext())
						toView = networkViewIterator.next();
					
					while(it.hasNext()){
						CyEdge edge = (CyEdge)it.next();
						
						if(gnetworks[i].getEdge(netwFrom.getRow(edge).get(CyNetwork.NAME, String.class))!=null){
							if(Launcher.findEdgeWithName(networks[i], netwFrom.getRow(edge).get(CyNetwork.NAME, String.class)) == null){				
							//if(!networks[i].containsEdge(edge)){
								NetworkUtils.addEd(networks[i], netwFrom, edge);
							}				
						}		
						
						
						taskMonitor.setProgress((int)(1f*i/networks.length));			
					}
					
					if(toView != null)
						toView.updateView();
					
					networkViews = Launcher.getAdapter().getCyNetworkViewManager().getNetworkViews(networks[i]);
					networkViewIterator = networkViews.iterator();
					CyNetworkView fromView =null;
					while(networkViewIterator.hasNext())
						fromView = networkViewIterator.next();
					
					VisualStyle vs = Launcher.getAdapter().getVisualMappingManager().getVisualStyle(fromView);	
					vs.apply(toView);
					toView.updateView();	
		
				}
			    taskMonitor.setProgress(1);;
			}
			catch(Exception e) {
			    e.printStackTrace();
			    taskMonitor.setProgress(1);;
			    taskMonitor.setStatusMessage("Error updating networks: " + e);
			}
    	}
    	else
    		JOptionPane.showMessageDialog(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(), 
    				"Select from and to netwok first.");
    }

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}
}
