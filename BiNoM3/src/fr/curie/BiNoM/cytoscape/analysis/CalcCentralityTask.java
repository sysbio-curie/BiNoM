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
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import edu.rpi.cs.xgmml.*;

import java.util.Vector;
import java.util.Set;

import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.XGMML;
import fr.curie.BiNoM.pathways.utils.*;

public class CalcCentralityTask implements Task{

    private GraphDocument network;
    private VisualStyle vizsty;
    private Vector<String> selected;
    private StructureAnalysisUtils.Option options;
    
    private StringBuffer text = new StringBuffer();
    
    private boolean directed = false;
    
    public Set<String> SelectedNodes;


    public CalcCentralityTask(GraphDocument network, Vector<String> selected, boolean _directed, StructureAnalysisUtils.Option options, VisualStyle vizsty) {
	this.network = network;
	this.vizsty = vizsty;
	this.selected = selected;
	this.options = options;
	directed = _directed;
    }

    public String getTitle() {
	return "BiNoM: Calc Inbetweenness";
    }
    
    public String getText() {
    	return text.toString();
    }
    
    
	public void run(TaskMonitor taskMonitor) {
		taskMonitor.setTitle(getTitle());
	try {
		SubnetworkProperties snp = new SubnetworkProperties();
		snp.network = XGMML.convertXGMMLToGraph(network);
		
		Vector<String> selected_temp = new Vector<String>();
		for(int i=0;i<selected.size();i++)
			selected_temp.add(selected.get(i));
		
		double inbet[] = snp.calcNodeBetweenness(snp.network,selected,directed,true);
		
		System.out.println("DIRECTED = "+directed);
		
		text.append("NODE\tINBETWEENNESS\tSEEDNODE\n");
		//for(int i=0;i<selected.size();i++){
		//	text.append(selected.get(i)+"\t"+inbet[i]+"\n");
		//}
		for(int i=0;i<snp.network.Nodes.size();i++){
			String seed = "FALSE";
			String id = snp.network.Nodes.get(i).Id;
			if(selected_temp.indexOf(id)>=0)
				seed = "TRUE";
			text.append(id+"\t"+inbet[i]+"\t"+seed+"\n");
		}		
		
		network = XGMML.convertGraphToXGMML(snp.network);
		
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setProgress(1);
	    taskMonitor.setStatusMessage("Error in extracting subnetwork " + e);
	}
    }


	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}



}
