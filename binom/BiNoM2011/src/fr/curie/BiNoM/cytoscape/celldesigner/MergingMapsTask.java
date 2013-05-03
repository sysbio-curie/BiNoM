package fr.curie.BiNoM.cytoscape.celldesigner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import fr.curie.BiNoM.pathways.utils.MergingMapsProcessor;

public class MergingMapsTask implements Task {

	private TaskMonitor taskMonitor;
	private String configFileName;
	private String outputFileName;
	
	public MergingMapsTask(String config, String output) {
		configFileName = config;
		outputFileName = output;
	}
	
	public void halt() {
		// there is no life in the void
	}
	
	public void run() {
		
		try {
			BufferedReader buf = new BufferedReader(new FileReader(configFileName));	
			String line;
			int ct=0;
			MergingMapsProcessor mm = new MergingMapsProcessor();
			while((line = buf.readLine()) != null) {
				line.trim();
				if (line.length()>0) {
					String[] tk = line.split("\\t|\\s+");
					if (ct==0) {
						int sizeX = Integer.parseInt(tk[1]);
						int sizeY = Integer.parseInt(tk[2]);
						mm.setMapSize(sizeX, sizeY);
					}
					else {
						String fn = tk[0];
						int coordX = Integer.parseInt(tk[1]);
						int coordY = Integer.parseInt(tk[2]);
						mm.addMap(fn, coordX, coordY);
					}
				}
				ct++;
			}
			mm.mergeAll();
			mm.saveMap(outputFileName);
			taskMonitor.setPercentCompleted(100);
			taskMonitor.setStatus("Finished merging CellDesigner maps.");
		}
		catch (Exception e) {
			e.printStackTrace();
    	    taskMonitor.setPercentCompleted(100);
    	    taskMonitor.setStatus("Error Creating NaviCell maps:" + e);
		}
	}

    public void setTaskMonitor(TaskMonitor taskMonitor) throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }
    public String getTitle() {
    	return "BiNoM: Merge CellDesigner maps files...";
    }

}
