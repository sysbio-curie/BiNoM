/*
   BiNoM Cytoscape Plugin
   Copyright (C) 2006-2007 Curie Institute, 26 rue d'Ulm, 75005 Paris - FRANCE

   BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
*/

/*
  BiNoM authors:
	Andrei Zinovyev : http://www.ihes.fr/~zinovyev
	Eric Viara : http://www.sysra.com/viara
	Laurence Calzone :	http://leibniz.biol.vt.edu/people/laurence/laurence.html
*/
package fr.curie.BiNoM.cytoscape.celldesigner;

import cytoscape.Cytoscape;
import cytoscape.task.Task;
import cytoscape.task.TaskMonitor;
import java.io.*;
import java.util.Properties;

import javax.swing.JOptionPane;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.test.*;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

import fr.curie.BiNoM.pathways.navicell.*;

public class ProduceNaviCellMapFilesTask implements Task {
    private TaskMonitor taskMonitor;
    private String wordPressURL = null;
    private String wordPressUser = null;
    private String wordPressPassword = null;
    private String configFileName = null;
    private boolean produceBOversion = true;

    public ProduceNaviCellMapFilesTask(String _configFileName, String _wordPressURL, String _wordPressUser, String _wordPressPassword, boolean _produceBOversion){
    	configFileName = _configFileName;
    	wordPressURL = _wordPressURL;
    	wordPressUser = _wordPressUser;
    	wordPressPassword = _wordPressPassword;
    	produceBOversion = _produceBOversion;
    }
    
    public void run() {
    	try {
    		File fc = new File(configFileName);
    		if(fc.exists()){
    			
    			File source_directory = new File(fc.getParent());
    			boolean make_tiles = true;
    			boolean only_tiles = false;
    			Boolean show_default_compartement_name = true;
    			String blog_name = null;
    			String base = null;

    			Properties configuration = load_config(fc);
    			
    			base = configuration.getProperty("base");
    			blog_name = configuration.getProperty("name", base);
    			show_default_compartement_name = "true".equalsIgnoreCase(configuration.getProperty("showDefaultCompartmentName", "false"));    			

    			File destination = new File(source_directory+"/../"+blog_name);
    			if(!destination.exists()) destination.mkdir();
    			
    			try{
    			if(produceBOversion)	
    				ProduceClickableMap.run(base, source_directory, make_tiles, only_tiles, blog_name, null, null, show_default_compartement_name, null, null, null, blog_name, destination, false);
    			else
    				ProduceClickableMap.run(base, source_directory, make_tiles, only_tiles, blog_name, null, null, show_default_compartement_name, wordPressURL, wordPressPassword, wordPressUser, blog_name, destination, false);
    			}catch(ProduceClickableMap.NaviCellException ne){
        			System.out.println("ERROR: "+ne.getMessage());
            	    taskMonitor.setPercentCompleted(99);
            	    taskMonitor.setStatus("ERROR: "+ne.getMessage()+" \n(if this is a NullPointerException,\ntry to launch it for the second time)");
    			}
    			
    			taskMonitor.setPercentCompleted(100);
        	    taskMonitor.setStatus("Finished creating NaviCell maps.");
    			
    		}else{
    			System.out.println("ERROR: File "+configFileName+" does not exist.");
        	    taskMonitor.setPercentCompleted(99);
        	    taskMonitor.setStatus("ERROR: File "+configFileName+" does not exist.");
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	    taskMonitor.setPercentCompleted(100);
    	    taskMonitor.setStatus("Error Creating NaviCell maps:" + e);
    	}
    }

    public String getTitle() {
    	return "BiNoM: Produce NaviCell map files...";
    }

    public void halt() {
    }

    public void setTaskMonitor(TaskMonitor taskMonitor)
            throws IllegalThreadStateException {
        this.taskMonitor = taskMonitor;
    }
    
	private static Properties load_config(File config)
	{
		final Properties configuration = new Properties();
		final FileInputStream config_stream;
		try
		{
			config_stream = new FileInputStream(config);
		}
		catch (FileNotFoundException e1)
		{
			System.err.println(e1.getMessage());
			System.exit(1);
			return configuration;
		}
		try
		{
			configuration.load(config_stream);
		}
		catch (IOException e1)
		{
			System.err.println("failed to load configuration file " + config + ": " + e1.getMessage());
			System.exit(1);
			return configuration;
		}
		return configuration;
	}

    
	
	
}
