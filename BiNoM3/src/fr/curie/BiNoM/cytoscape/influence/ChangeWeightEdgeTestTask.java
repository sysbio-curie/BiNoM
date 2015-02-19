package fr.curie.BiNoM.cytoscape.influence;
/*
BiNoM Cytoscape Plugin under GNU Lesser General Public License 
Copyright (C) 2010-2014 Institut Curie, 26 rue d'Ulm, 75005 Paris - FRANCE   
*/
import java.util.ArrayList;
import java.util.HashSet;

import org.cytoscape.model.CyNetwork;

import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import fr.curie.BiNoM.pathways.utils.*;
/**
 * Test change of weight (=0 or reverse by overriding changeWeight)
 * Act only on edges between nodes where activity is not zero (what edges)
 * 
 * @author Daniel.Rovera@curie.fr
 *
 */
public class ChangeWeightEdgeTestTask implements Task{
	SignEqualityScore a;
	public ChangeWeightEdgeTestTask(SignEqualityScore action){a=action;}
	public String getTitle() {
		return a.title();
	}
	public void halt() {}
	public static void insertionSort(ArrayList<Double> keys,ArrayList<Integer> values){
		for (int i=1;i<keys.size();i++){
			int j=i;
			Double ti=keys.get(i);
			int ts=values.get(i);
			while ((j > 0) && (keys.get(j-1)>ti)){
				keys.set(j,keys.get(j-1));
				values.set(j,values.get(j-1));
				j--;
			}
			keys.set(j,ti);
			values.set(j,ts);
		}
	}
	public void run(){
		HashSet<Integer> activSrc=new HashSet<Integer>();
		HashSet<Integer> activTgt=new HashSet<Integer>();
		for(int j=0;j<a.setNames.keySet().size();j++){
			for(int i=0;i<a.wgs.nodes.size();i++){
				if(a.activIn[j][i]!=0) activSrc.add(i);
				if(a.activAim[j][i]!=0){
					activTgt.add(i);
				}
			}
		}
		double startKappa=a.getKappa();			
		a.txt.append("startKappa=\t\t");a.txt.append(startKappa);a.txt.append("\r\n");
		a.txt.append("Edge\tWeight\tKappa\r\n");
		HashSet<Integer> whatEdges=(new ComputingByBFS(a.wgs)).extractEdges(activSrc,activTgt);
		ArrayList<Integer> foundEdges=new ArrayList<Integer>();
		ArrayList<Double> kappas=new ArrayList<Double>();
		int c=0;
		for(int edge:whatEdges){
			double keptWeigth=a.wgs.weights.get(edge);
			a.changeWeight(edge);
			double kappa=a.getKappa();			
			if(kappa>startKappa){
				foundEdges.add(edge);
				kappas.add(kappa);																
			}
			a.wgs.weights.set(edge,keptWeigth);
			taskMonitor.setPercentCompleted(100*c++/whatEdges.size());			
		}
		insertionSort(kappas,foundEdges);
		for(int i=kappas.size()-1;i>-1;i--){
			
			a.txt.append(a.wgs.edges.get(foundEdges.get(i)).getSource().getNetworkPointer().getRow(a.wgs.edges.get(foundEdges.get(i))).get(CyNetwork.NAME, String.class));
			a.txt.append("\t");
			a.txt.append(a.weightValue(foundEdges.get(i)));
			a.txt.append("\t");
			a.txt.append(kappas.get(i));
			a.txt.append("\r\n");
		}
		taskMonitor.setStatus("...Completed");
	}		
	private TaskMonitor taskMonitor;
	public void setTaskMonitor(TaskMonitor taskMonitor) throws IllegalThreadStateException{
		this.taskMonitor=taskMonitor;
	}
}
