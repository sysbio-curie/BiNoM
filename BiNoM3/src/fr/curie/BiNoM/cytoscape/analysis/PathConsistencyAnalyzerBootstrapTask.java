package fr.curie.BiNoM.cytoscape.analysis;

import java.text.DecimalFormat;
import java.util.Random;

import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
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

	public void halt() {
		permutationsDone = analyzer.numberOfPermutations+1;
	}

	public void run() {
		taskMonitor.setPercentCompleted(0);
		taskMonitor.setStatus("Initializing...");
		analyzer.initializeSignificanceAnalysis();
		
		Random r = new Random();
		DecimalFormat df = new DecimalFormat("##.##");
		if(analyzer.testSignificanceMode==analyzer.PERMUTE_NODE_ACTIVITIES){
		
			permutationsDone = 0;
			while(permutationsDone<analyzer.numberOfPermutations){
		
				analyzer.significanceAnalysisDoPermutation(permutationsDone);
				
				if(permutationsDone==Math.round(10*(int)(0.1f*permutationsDone))){
					taskMonitor.setPercentCompleted((int)(100f*permutationsDone/analyzer.numberOfPermutations));
					taskMonitor.setStatus(permutationsDone+" permutations done...");
				}
				
				permutationsDone++;
			}
		
			taskMonitor.setPercentCompleted(100);
			taskMonitor.setStatus("Permutation analysis completed.");
		}
	}

	public void setTaskMonitor(TaskMonitor arg0)
			throws IllegalThreadStateException {
		taskMonitor = arg0;
	}

}
