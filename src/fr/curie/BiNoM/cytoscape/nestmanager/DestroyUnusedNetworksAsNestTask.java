package fr.curie.BiNoM.cytoscape.nestmanager;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2011 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE  
*/
import java.util.ArrayList;
import cytoscape.Cytoscape;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
/**
 * Destroy networks of the list
 * 
 * @author Daniel.Rovera@curie.fr
 */
public class DestroyUnusedNetworksAsNestTask implements Task{
	ArrayList<String> networkList;
	public DestroyUnusedNetworksAsNestTask(ArrayList<String> networkList){this.networkList=networkList;}	
	public String getTitle(){return DestroyUnusedNetworksAsNest.title;}
	public void halt(){}
	public void run(){
		try{
			for(int i=0;i<networkList.size();i++){
				Cytoscape.destroyNetwork(networkList.get(i));
				taskMonitor.setPercentCompleted(100*(i+1)/networkList.size());
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