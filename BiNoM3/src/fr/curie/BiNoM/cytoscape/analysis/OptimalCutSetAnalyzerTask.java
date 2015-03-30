package fr.curie.BiNoM.cytoscape.analysis;

import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import fr.curie.BiNoM.pathways.analysis.structure.*;

public class OptimalCutSetAnalyzerTask implements Task {
	
	private DataPathConsistencyAnalyzer analyzer;
	
	public OptimalCutSetAnalyzerTask(DataPathConsistencyAnalyzer _analyzer){
		analyzer = _analyzer;
	}

	public String getTitle() {
		return "Finding optimal intervention sets...";
	}


	public void run(TaskMonitor taskMonitor) {
		taskMonitor.setTitle(getTitle());
		analyzer.ocsanaSearch();
		taskMonitor.setProgress(1);;
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}
}
