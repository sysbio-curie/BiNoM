package fr.curie.BiNoM.cytoscape.nestmanager;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.util.List;

import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
/**
 * Task of updating edges inside all nests
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class RecreateLostConnectionsInsideNestsTask implements Task{
	RecreateLostConnectionsInsideNests a;
	public RecreateLostConnectionsInsideNestsTask(RecreateLostConnectionsInsideNests action){a=action;}	
	public String getTitle(){return RecreateLostConnectionsInsideNests.title;}
	public void halt(){}
	public void run(){
		try{
			List<CyNode> list=NestUtils.getNodeList(Cytoscape.getCurrentNetwork());
			int i=0,c=list.size();
			for(CyNode nest:list){
				CyNetwork nestedNW=(CyNetwork)nest.getNestedNetwork();
				if(nestedNW==null) continue;else a.updateConnections(nestedNW);
				taskMonitor.setPercentCompleted(100*i++/c);
			}
			taskMonitor.setStatus("...Completed");
		}
		catch(Exception e) {
			e.printStackTrace();
			taskMonitor.setStatus("Error in "+getTitle()+" "+e);
		}	
	}
	private TaskMonitor taskMonitor;
	public void setTaskMonitor(TaskMonitor taskMonitor) throws IllegalThreadStateException{this.taskMonitor=taskMonitor;}
}