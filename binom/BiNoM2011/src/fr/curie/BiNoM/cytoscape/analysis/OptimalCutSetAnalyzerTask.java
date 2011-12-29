package fr.curie.BiNoM.cytoscape.analysis;

import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import fr.curie.BiNoM.pathways.analysis.structure.*;

public class OptimalCutSetAnalyzerTask implements Task {
	
	private DataPathConsistencyAnalyzer analyzer;
    private TaskMonitor taskMonitor;
	
	public OptimalCutSetAnalyzerTask(DataPathConsistencyAnalyzer _analyzer){
		analyzer = _analyzer;
	}

	public String getTitle() {
		return "Finding optimal cut sets...";
	}

	public void halt() {
		// TODO Auto-generated method stub

	}

	public void run() {
		analyzer.ocsanaScore();
		analyzer.ocsanaOptimalCutSet();
		taskMonitor.setPercentCompleted(100);
	}

	public void setTaskMonitor(TaskMonitor arg0)
			throws IllegalThreadStateException {
	    taskMonitor = arg0;
	}

}
