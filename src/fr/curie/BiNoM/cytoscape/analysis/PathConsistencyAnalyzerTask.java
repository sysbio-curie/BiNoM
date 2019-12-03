package fr.curie.BiNoM.cytoscape.analysis;

import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import fr.curie.BiNoM.pathways.analysis.structure.*;

public class PathConsistencyAnalyzerTask implements Task {
	
	private DataPathConsistencyAnalyzer analyzer;
	
	public PathConsistencyAnalyzerTask(DataPathConsistencyAnalyzer _analyzer){
		analyzer = _analyzer;
	}

	public String getTitle() {
		return "Finding paths from active nodes to targets...";
	}


	public void run(TaskMonitor taskMonitor) {
		taskMonitor.setTitle(getTitle());
		analyzer.findPaths();
		taskMonitor.setProgress(1);;
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}


}
