package fr.curie.BiNoM.cytoscape.analysis;

import java.text.DecimalFormat;
import java.util.Random;

import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import fr.curie.BiNoM.pathways.analysis.structure.*;

public class PathConsistencyAnalyzerBootstrapTask implements Task {

	private DataPathConsistencyAnalyzer analyzer;
	private TaskMonitor taskMonitor;
	
	private int permutationsDone = 0;
	
	public PathConsistencyAnalyzerBootstrapTask(DataPathConsistencyAnalyzer _analyzer){
		analyzer = _analyzer;
	}
	
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Running significance test...";
	}

	public void run(TaskMonitor taskMonitor) {
		taskMonitor.setTitle(getTitle());
		taskMonitor.setProgress(0);;
		taskMonitor.setStatusMessage("Initializing...");
		analyzer.initializeSignificanceAnalysis();
		
		Random r = new Random();
		DecimalFormat df = new DecimalFormat("##.##");
		if(analyzer.testSignificanceMode==analyzer.PERMUTE_NODE_ACTIVITIES){
		
			permutationsDone = 0;
			while(permutationsDone<analyzer.numberOfPermutations){
		
				analyzer.significanceAnalysisDoPermutation(permutationsDone);
				
				if(permutationsDone==Math.round(10*(int)(0.1f*permutationsDone))){
					taskMonitor.setProgress((int)(1f*permutationsDone/analyzer.numberOfPermutations));
					taskMonitor.setStatusMessage(permutationsDone+" permutations done...");
				}
				
				permutationsDone++;
			}
		
			taskMonitor.setProgress(1);;
			taskMonitor.setStatusMessage("Permutation analysis completed.");
		}
	}

	public void setTaskMonitor(TaskMonitor arg0)
			throws IllegalThreadStateException {
		taskMonitor = arg0;
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

}
