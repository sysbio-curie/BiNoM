package fr.curie.BiNoM.cytoscape.celldesigner;

import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

import fr.curie.BiNoM.pathways.utils.MergingMapsProcessor;

public class MergingMapsTask implements Task {

	private String configFileName;
	private String outputFileName;
	private MergingMapsOptions options;
	
	public class MergingMapsOptions{
		public boolean mergeImages = false;
		public boolean mergeSpecies = true;
		public int zoomLevel = 3;
		public int numberOfTimesToScale = 0;
	}
	
	public MergingMapsTask(String config, String output, MergingMapsOptions _options) {
		configFileName = config;
		outputFileName = output;
		options = _options;
	}
	
	public void run(TaskMonitor taskMonitor) {
		taskMonitor.setTitle(getTitle());
		try {
			MergingMapsProcessor mm = new MergingMapsProcessor();
			mm.loadConfigFile(configFileName);
			mm.doMergeSpecies = options.mergeSpecies;
			mm.mergeAll(outputFileName);
			
			if(options.mergeImages){
				String outputFileName_prefix = outputFileName;
				if(outputFileName.endsWith(".xml"))
						outputFileName_prefix = outputFileName.substring(0, outputFileName.length()-4);
				mm.mergeMapImages(outputFileName_prefix, options.zoomLevel, options.numberOfTimesToScale, 1);
			}
			
			if(taskMonitor!=null){
				taskMonitor.setProgress(1);
				taskMonitor.setStatusMessage("Finished merging CellDesigner maps.");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			if(taskMonitor!=null){			
				taskMonitor.setProgress(1);
				taskMonitor.setStatusMessage("Error Creating NaviCell maps:" + e);
			}
		}
		
	}

    public String getTitle() {
    	return "BiNoM: Merge CellDesigner maps files...";
    }

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

}
