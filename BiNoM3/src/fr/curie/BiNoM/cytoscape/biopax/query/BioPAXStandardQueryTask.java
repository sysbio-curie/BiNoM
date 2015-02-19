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
package fr.curie.BiNoM.cytoscape.biopax.query;


import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;

import Main.Launcher;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;

import java.util.*;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.cytoscape.lib.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class BioPAXStandardQueryTask implements Task {
	
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
	
	public class StandardQueryOptions{
	    public boolean selectComplex = false;
	    public boolean complexExpand = false;
	    public boolean complexNoExpand = true;
	    public boolean selectSpecies = false;
	    public boolean selectReactions = false;
	    public boolean reactionConnecting = false;
	    public boolean reactionAll = false;
	    public boolean reactionComplete = false;
	    public boolean selectPublications = false;
	    public StandardQueryOptions(){
	    	
	    }
	}

	private StandardQueryOptions options = null;
    private TaskMonitor taskMonitor;
    private BioPAXGraphQuery query = null; 
    private Vector selectedNodes = null;
    private boolean outputCurrentNetwork = false;
	
	
	public BioPAXStandardQueryTask(StandardQueryOptions opt, BioPAXGraphQuery q, Vector sel, boolean current){
		options = opt;
		query = q;
		selectedNodes = sel;
		outputCurrentNetwork = current;
	}

	public BioPAXStandardQueryTask(){
	}
	
	public String getTitle() {
		return "BioPAX index standard query";
	}

	public void halt() {

	}

	public void run() {
		try{
		BioPAXGraphQueryEngine beng = BioPAXIndexRepository.getInstance().getBioPAXGraphQueryEngine();
		
		BioPAXIndexRepository.getInstance().addToReport("\nNew query: "+(new Date()).toString()+"\n");
		
		Graph copyQueryGraph = query.input;
		query.result = query.input;
		
		System.out.println("Number of edges = "+query.input.Edges.size());
		
		if(selectedNodes!=null){
			Graph graph = new Graph();
			for(int i=0;i<selectedNodes.size();i++){
				String id = (String)selectedNodes.get(i);
				Node n = copyQueryGraph.getNode(id);
				if(n!=null)
					graph.Nodes.add(n);
			}
			graph.addConnections(copyQueryGraph);
			query.input = graph;
		}
		
		System.out.println("Number of edges after selectedNodes = "+query.result.Edges.size());
		
		taskMonitor.setStatus("Add complexes...");
		if(options.selectComplex){
		    if(options.complexExpand)
		    	beng.doQuery(query, query.ADD_COMPLEXES_EXPAND);
		    if(options.complexNoExpand)
		    	beng.doQuery(query, query.ADD_COMPLEXES_NOEXPAND);
		    beng.query.input = beng.query.result;
		}
		
		System.out.println("Number of edges after selectComplexes = "+query.result.Edges.size());
		
	    taskMonitor.setPercentCompleted(20);		
		taskMonitor.setStatus("Add species...");	    
		if(options.selectSpecies){
			beng.doQuery(query, query.ADD_SPECIES);
			beng.query.input = beng.query.result;
		}
		
		System.out.println("Number of edges after add species = "+query.result.Edges.size());
		
	    taskMonitor.setPercentCompleted(40);		
		taskMonitor.setStatus("Add reactions...");	    
		if(options.selectReactions){
			if(options.reactionConnecting)
				beng.doQuery(query, query.ADD_CONNECTING_REACTIONS);
			if(options.reactionAll)
				beng.doQuery(query, query.ADD_ALL_REACTIONS);
			if(options.reactionComplete)
				beng.doQuery(query, query.COMPLETE_REACTIONS);
			beng.query.input = beng.query.result;
		}
		
		System.out.println("Number of edges after selectReactions = "+query.result.Edges.size());
		
	    taskMonitor.setPercentCompleted(60);		
		taskMonitor.setStatus("Add publications...");	    
		if(options.selectPublications){
			beng.doQuery(query, query.ADD_PUBLICATIONS);
			beng.query.input = beng.query.result;
		}
		
		System.out.println("Number of edges after select Publications = "+query.result.Edges.size());
		
		query.result.addNodes(copyQueryGraph);
		query.result.addEdges(copyQueryGraph);
		
		System.out.println("Number of edges after adding edges = "+query.result.Edges.size());
		
		if(outputCurrentNetwork){
			CyNetwork current = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
					
			GraphDocument currentGraphDocument = GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
			Graph currentGraph = XGMML.convertXGMMLToGraph(currentGraphDocument);
			for(int i=0;i<query.result.Nodes.size();i++){
				Node n = (Node)query.result.Nodes.get(i);
				if(currentGraph.getNode(n.Id)==null){
				CyNode nd = current.addNode();
				current.getDefaultNodeTable().getRow(nd.getSUID()).set("name", NetworkFactory.toId(n.Id));
				
			   // CyNode nd = Cytoscape.getCyNode(NetworkFactory.toId(n.Id),true);
			   //current.addNode(nd);
				
			    for(int j=0;j<n.Attributes.size();j++){
			    	Attribute attr = (Attribute)n.Attributes.get(j);
					//nodeAttrs.setAttribute(nd.getIdentifier(), attr.name,attr.value);
			    	if(current.getDefaultNodeTable().getColumn(attr.name) == null)
						current.getDefaultNodeTable().createColumn(attr.name, String.class, false);
			    	
					current.getRow(nd).set(attr.name, attr.value);
			    }
			    //nd.setIdentifier(nd.getIdentifier());
				}
			}
			
			for(int i=0;i<query.result.Edges.size();i++){
				Edge e = (Edge)query.result.Edges.get(i);
				if(currentGraph.getEdge(e.Id)==null){
					CyEdge ed = current.addEdge(getNodeWithName(current, current.getDefaultNodeTable(), "name", NetworkFactory.toId(e.Node1.Id)), 
				    		getNodeWithName(current, current.getDefaultNodeTable(), "name", NetworkFactory.toId(e.Node2.Id)), true);	
					
//			    CyEdge ed = Cytoscape.getCyEdge(
//			    		Cytoscape.getCyNode(NetworkFactory.toId(e.Node1.Id)),
//					    Cytoscape.getCyNode(NetworkFactory.toId(e.Node2.Id)),
//					    Semantics.INTERACTION,
//					    e.EdgeLabel,
//					    true
//			    );
				current.getDefaultEdgeTable().getRow(ed.getSUID()).set("name", e.Id);
			    //ed.setIdentifier(e.Id);
			    //System.out.println(""+(i+1)+") Creating edge: "+e.EdgeLabel+"\t"+ed.getIdentifier());
			    //current.addEdge(ed);
			    for(int j=0;j<e.Attributes.size();j++){
			    	Attribute attr = (Attribute)e.Attributes.get(j);
			    	current.getRow(ed).set(attr.name, attr.value);
					//edgeAttrs.setAttribute(ed.getIdentifier(), attr.name,attr.value);
			    }
				}
			}
			CyNetworkView networkView = Launcher.getAdapter().getCyNetworkViewFactory().createNetworkView(current);
			networkView.updateView();
		}else{
			GraphDocument grDoc = XGMML.convertGraphToXGMML(query.result);
			CyNetwork cyNetwork = NetworkFactory.createNetwork
		    (grDoc.getGraph().getName(),
		     grDoc,
		     Launcher.getAdapter().getVisualMappingManager().getCurrentVisualStyle(),
		     true, // applyLayout
		     taskMonitor);
		}
	    taskMonitor.setPercentCompleted(100);
		}catch(Exception e){
    		e.printStackTrace();
    	    taskMonitor.setPercentCompleted(100);
    	    taskMonitor.setStatus("Error making standard query :" + e);
		}
	}

	public void setTaskMonitor(TaskMonitor arg0)
			throws IllegalThreadStateException {
        this.taskMonitor = arg0;
	}

}
