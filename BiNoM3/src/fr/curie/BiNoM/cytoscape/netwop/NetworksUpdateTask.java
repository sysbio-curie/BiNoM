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
package fr.curie.BiNoM.cytoscape.netwop;

import fr.curie.BiNoM.cytoscape.lib.*;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import cytoscape.visual.VisualMappingManager;
import cytoscape.visual.VisualStyle;

import java.io.*;

import cytoscape.task.ui.JTaskConfig;
import edu.rpi.cs.xgmml.*;
import cytoscape.data.Semantics;

import java.io.InputStream;
import java.io.File;
import java.net.URL;

import org.cytoscape.model.CyNetwork;


public class NetworksUpdateTask implements Task {
    private TaskMonitor taskMonitor;

    CyNetwork networks[];
    CyNetwork netwAdd;
    CyNetwork netwSup;

    public NetworksUpdateTask(CyNetwork networks[], CyNetwork netwAdd, CyNetwork netwSup) {
	this.networks = networks;
	this.netwAdd = netwAdd;
	this.netwSup = netwSup;
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
            throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    public String getTitle() {
	return "BiNoM: Networks Update";
    }

    public CyNetwork getCyNetwork() {
	return null;
    }

    public void run() {
	try {
	    int netw_cnt = 0;
	    for (int n = 0; n < networks.length; n++) {
		CyNetwork netw = networks[n];
		if (netw == null)
		    continue;

		if (netwAdd == null && netwSup == null)
		    continue;

		CyNetwork netw_old = NetworkUtils.makeBackupNetwork(netw);

		NetworkOperation op;

		if (netwSup != null) {
		    op = new NetworkDifference(netw_old, netwSup);
		    CyNetwork nullNetw = 
		    		
		    		NetworkFactory.getNullEmptyNetwork();
		    System.out.println(netw.getRow(netw).get(CyNetwork.NAME, String.class) + " before " +
				       netw.getNodeCount() +
				       " " + System.currentTimeMillis());
		    op.eval(nullNetw);
		    System.out.println(netw.getRow(netw).get(CyNetwork.NAME, String.class) + " after eval " +
				       netw.getNodeCount() +
				       " " + System.currentTimeMillis());

		    NetworkUtils.clearAndCopy(netw, nullNetw);
		    System.out.println(netw.getRow(netw).get(CyNetwork.NAME, String.class) + " after clear and copy" +
				       netw.getNodeCount());

		    if (netwAdd != null) {
			op = new NetworkUnion(netw, netwAdd);
			op.eval(netw);
		    }
		}
		else if (netwAdd != null) {
		    op = new NetworkUnion(netw, netwAdd);
		    op.eval(netw);
		}
		netw_cnt++;
	    }

	    taskMonitor.setStatus(netw_cnt +
				  " Network" + (netw_cnt != 1 ? "s" : "") +
				  " Updated");
	    taskMonitor.setPercentCompleted(100);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setPercentCompleted(100);
	    taskMonitor.setStatus("Error updating networks: " + e);
	}
    }
}
