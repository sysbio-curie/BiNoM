package fr.curie.BiNoM.cytoscape.nestmanager;


/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.util.List;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import Main.Launcher;

/**
 * Task of updating edges inside all nests
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class RecreateLostConnectionsInsideNestsTask implements Task{
	RecreateLostConnectionsInsideModules a;
	public RecreateLostConnectionsInsideNestsTask(RecreateLostConnectionsInsideModules action){a=action;}	
	public String getTitle(){return RecreateLostConnectionsInsideModules.title;}
	public void run(TaskMonitor taskMonitor){
		taskMonitor.setTitle(getTitle());
		try{
			List<CyNode> list=Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork().getNodeList();
			int i=0,c=list.size();
			for(CyNode nest:list){
				CyNetwork nestedNW=(CyNetwork)nest.getNetworkPointer();
				if(nestedNW==null) 
					continue;
				else 
					a.updateConnections(nestedNW);
				taskMonitor.setProgress(i++/c);
			}
			taskMonitor.setStatusMessage("...Completed");
		}
		catch(Exception e) {
			e.printStackTrace();
			taskMonitor.setStatusMessage("Error in "+getTitle()+" "+e);
		}	
	}
	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}
}