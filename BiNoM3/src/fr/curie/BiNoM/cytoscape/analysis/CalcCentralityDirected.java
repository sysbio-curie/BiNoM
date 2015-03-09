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

import Main.Launcher;

import java.awt.event.ActionEvent;
import java.util.*;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.work.TaskIterator;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.cytoscape.utils.ShowTextDialog;
import fr.curie.BiNoM.pathways.analysis.structure.StructureAnalysisUtils;

public class CalcCentralityDirected extends AbstractCyAction {

    private static final String EMPTY_NAME = "                       ";
    public boolean directed = true;

    public CalcCentralityDirected() {
    	super("Inbetweeness directed",
        		Launcher.getAdapter().getCyApplicationManager(),
            "network",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".Analysis.Calc centrality");
    }

    public void actionPerformed(ActionEvent e) {
    	
    	CyAppAdapter adapter = Launcher.getAdapter();
    	
        if(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()!=null){
        	Vector<String> selected = new Vector<String>();
        	if(CyTableUtil.getNodesInState(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true).size() > 1){
    			List<CyNode> nodes = CyTableUtil.getNodesInState(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true);
    			Iterator<CyNode> it = nodes.iterator();
    			while(it.hasNext())
    				selected.add(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork().getRow((CyNode)it.next()).get(CyNetwork.NAME, String.class));
        	}
			StructureAnalysisUtils.Option options = new StructureAnalysisUtils.Option();
			GraphDocument graphDocument = GraphDocumentFactory.getInstance().createGraphDocument(adapter.getCyApplicationManager().getCurrentNetwork());
			
			CalcCentralityTask task = new CalcCentralityTask(graphDocument, selected, directed, options, Launcher.getAdapter().getVisualMappingManager().getCurrentVisualStyle());
			TaskIterator t = new TaskIterator(task);
			Launcher.getAdapter().getTaskManager().execute(t);
			
    		ShowTextDialog dialog = new ShowTextDialog();
    		dialog.pop("Node Inbetweenness", task.getText().toString());
    		
    		/*GraphDocument network = task.getNetwork();
    		cytoscape.data.CyAttributes nodeAttrs = Cytoscape.getNodeAttributes();
    		
    		Set nodes = Cytoscape.getCurrentNetwork().getSelectedNodes();
			Iterator it = nodes.iterator();
			while(it.hasNext()){
				CyNode nd = (CyNode)it.next();
				String id = nd.getIdentifier();
			}*/
    		
        	}
    }
}
