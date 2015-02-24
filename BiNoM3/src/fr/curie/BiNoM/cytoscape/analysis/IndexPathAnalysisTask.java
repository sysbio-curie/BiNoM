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

import fr.curie.BiNoM.cytoscape.lib.*;
import fr.curie.BiNoM.cytoscape.biopax.query.*;
import fr.curie.BiNoM.pathways.utils.*;

import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import Main.Launcher;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import edu.rpi.cs.xgmml.*;

import java.util.Collection;
import java.util.Vector;
import java.util.Iterator;
import java.util.Set;

import fr.curie.BiNoM.pathways.analysis.structure.*;

import fr.curie.BiNoM.pathways.wrappers.XGMML;




public class IndexPathAnalysisTask implements Task {

    private TaskMonitor taskMonitor;
    private VisualStyle vizsty;
    private Vector<String> sources;
    private Vector<String> targets;
    private StructureAnalysisUtils.Option options;
    
    private boolean outputCurrentNetwork = false;
    
    public Set<String> SelectedNodes;
    
    private static CyNode getNodeWithName(
            final CyNetwork net, final CyTable table,
            final String colname, final Object value){
        final Collection<CyRow> matchingRows = table.getMatchingRows(colname, value);
        final String primaryKeyColname = table.getPrimaryKey().getName();
        for (final CyRow row : matchingRows)
        {
            final Long nodeId = row.get(primaryKeyColname, Long.class);
            if (nodeId == null)
                continue;
            final CyNode node = net.getNode(nodeId);
            if (node == null)
                continue;
            return node;
        }
        return null;
    }


    public IndexPathAnalysisTask(Vector<String> sources, Vector<String> targets, StructureAnalysisUtils.Option options, VisualStyle vizsty, boolean ocn) {
	this.vizsty = vizsty;
	this.sources = sources;
	this.targets = targets;
	this.options = options;
	this.outputCurrentNetwork = ocn;
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
            throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }

    public String getTitle() {
	return "BiNoM: Index path analysis";
    }

    public CyNetwork getCyNetwork() {
    	return Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
    }

    public void run() {
	try {
		
		BioPAXGraphQueryEngine beng = BioPAXIndexRepository.getInstance().getBioPAXGraphQueryEngine();
		
		System.out.println("beng " + beng);
		if(beng!=null){
			System.out.println("bengDB " + beng.databaseCopyForPathAnalysis);
			
			//funzione da indagare
			if(beng.databaseCopyForPathAnalysis==null)
				beng.prepareDatabaseCopyForIndexPathAnalysis();
			
			System.out.println("1");
			SelectedNodes = StructureAnalysisUtils.findPaths(beng.databaseCopyForPathAnalysis,sources,targets,options);
			System.out.println("2");
			Graph gr = new Graph();
			Iterator sn = SelectedNodes.iterator();
			while(sn.hasNext()){
				Node n = beng.databaseCopyForPathAnalysis.getNode((String)(sn.next()));
				gr.addNode(n);
			}
			System.out.println("3");
			gr.addConnections(beng.databaseCopyForPathAnalysis);
			System.out.println("4");
			if(outputCurrentNetwork){
//				CyNetwork current = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
//				
//				
//				//cytoscape.data.CyAttributes nodeAttrs = Cytoscape.getNodeAttributes();
//				//cytoscape.data.CyAttributes edgeAttrs = Cytoscape.getEdgeAttributes();
//
//				
//				GraphDocument currentGraphDocument = GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
//				Graph currentGraph = XGMML.convertXGMMLToGraph(currentGraphDocument);
//				for(int i=0;i<gr.Nodes.size();i++){
//					Node n = (Node)gr.Nodes.get(i);
//					if(currentGraph.getNode(n.Id)==null){
//						
//				    CyNode nd =  current.addNode();
//				    //Cytoscape.getCyNode(NetworkFactory.toId(n.Id),true);
//				    //current.addNode(nd);
//				    for(int j=0;j<n.Attributes.size();j++){
//				    	Attribute attr = (Attribute)n.Attributes.get(j);
//				    	current.getRow(nd).set(attr.name, attr.value);
//						//nodeAttrs.setAttribute(nd.getIdentifier(), attr.name,attr.value);
//				    }				    
//				    //nd.setIdentifier(nd.getIdentifier());
//					}
//				}
//				for(int i=0;i<gr.Edges.size();i++){
//					Edge e = (Edge)gr.Edges.get(i);
//					if(currentGraph.getEdge(e.Id)==null){
//					CyEdge ed = current.addEdge(getNodeWithName(current, current.getDefaultNodeTable(), "name", NetworkFactory.toId(e.Node1.Id)), 
//							getNodeWithName(current, current.getDefaultNodeTable(), "name",NetworkFactory.toId(e.Node2.Id)), true)	;
////				    CyEdge ed = Cytoscape.getCyEdge(
////				    		Cytoscape.getCyNode(NetworkFactory.toId(e.Node1.Id)),
////						    Cytoscape.getCyNode(NetworkFactory.toId(e.Node2.Id)),
////						    Semantics.INTERACTION,
////						    e.EdgeLabel,
////						    true
////				    );
//					current.getDefaultEdgeTable().getRow(ed.getSUID()).set("name", e.Id);
//				    //ed.setIdentifier(e.Id);
//				    //System.out.println(""+(i+1)+") Creating edge: "+e.EdgeLabel+"\t"+ed.getIdentifier());
//				    //current.addEdge(ed);
//				    for(int j=0;j<e.Attributes.size();j++){
//				    	Attribute attr = (Attribute)e.Attributes.get(j);
//				    	current.getRow(ed).set(attr.name, attr.value);
//						//edgeAttrs.setAttribute(ed.getIdentifier(), attr.name,attr.value);
//				    }
//					}
//				}
//				CyNetworkView networkView = Launcher.getAdapter().getCyNetworkViewFactory().createNetworkView(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
//				Launcher.getAdapter().getCyNetworkViewFactory().createNetworkView(current);
//				//networkView.redrawGraph(true, false);
//				networkView.updateView();
			}else{
				System.out.println("5");
				GraphDocument grDoc = XGMML.convertGraphToXGMML(gr);
				CyNetwork cyNetwork = NetworkFactory.createNetwork
			    (grDoc.getGraph().getName(),
			     grDoc,
			     Launcher.getAdapter().getVisualMappingManager().getCurrentVisualStyle(),
			     true, // applyLayout
			     taskMonitor);
				System.out.println("6");
			}
			
		}else{
			Thread.sleep(100);
			taskMonitor.setStatus("ERROR: Index is not loaded.");
		}
		
		taskMonitor.setPercentCompleted(100);
	}
	catch(Exception e) {
	    e.printStackTrace();
	    taskMonitor.setPercentCompleted(100);
	    taskMonitor.setStatus("Error in index path analysis " + e);
	}
    }
}
