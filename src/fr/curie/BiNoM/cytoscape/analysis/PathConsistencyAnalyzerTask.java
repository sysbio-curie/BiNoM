package fr.curie.BiNoM.cytoscape.analysis;

import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import fr.curie.BiNoM.pathways.analysis.structure.*;

public class PathConsistencyAnalyzerTask implements Task {
	
	private DataPathConsistencyAnalyzer analyzer;
    private TaskMonitor taskMonitor;
	
	public PathConsistencyAnalyzerTask(DataPathConsistencyAnalyzer _analyzer){
		analyzer = _analyzer;
	}

	public String getTitle() {
		return "Finding paths from active nodes to targets...";
	}

	public void halt() {
		// TODO Auto-generated method stub

	}

	public void run() {
		analyzer.findPaths();
		taskMonitor.setPercentCompleted(100);
	}

	public void setTaskMonitor(TaskMonitor arg0)
			throws IllegalThreadStateException {
	    taskMonitor = arg0;
	}

}
