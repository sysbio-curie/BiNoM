package fr.curie.BiNoM.cytoscape.celldesigner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import fr.curie.BiNoM.pathways.utils.MergingMapsProcessor;

public class MergingMapsTask implements Task {

	private TaskMonitor taskMonitor;
	private String configFileName;
	private String outputFileName;
	private MergingMapsOptions options;
	
	public class MergingMapsOptions{
		public boolean mergeImages = false;
		public int zoomLevel = 3;
		public int numberOfTimesToScale = 0;
	}
	
	public MergingMapsTask(String config, String output, MergingMapsOptions _options) {
		configFileName = config;
		outputFileName = output;
		options = _options;
	}
	
	public void halt() {
		// there is no life in the void
	}
	
	public void run() {
		
		try {
			MergingMapsProcessor mm = new MergingMapsProcessor();
			mm.loadConfigFile(configFileName);
			mm.mergeAll(outputFileName);
			
			if(options.mergeImages){
				String outputFileName_prefix = outputFileName;
				if(outputFileName.endsWith(".xml"))
						outputFileName_prefix = outputFileName.substring(0, outputFileName.length()-4);
				mm.mergeMapImages(outputFileName_prefix, options.zoomLevel, options.numberOfTimesToScale, 1);
			}
			
			if(taskMonitor!=null){
				taskMonitor.setPercentCompleted(100);
				taskMonitor.setStatus("Finished merging CellDesigner maps.");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			if(taskMonitor!=null){			
				taskMonitor.setPercentCompleted(100);
				taskMonitor.setStatus("Error Creating NaviCell maps:" + e);
			}
		}
		
	}

    public void setTaskMonitor(TaskMonitor taskMonitor) throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }
    public String getTitle() {
    	return "BiNoM: Merge CellDesigner maps files...";
    }

}
