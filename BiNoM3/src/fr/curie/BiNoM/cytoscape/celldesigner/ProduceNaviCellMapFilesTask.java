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

import java.io.*;
import java.util.Properties;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;
import fr.curie.BiNoM.pathways.navicell.*;

public class ProduceNaviCellMapFilesTask implements Task {
    private String wordPressURL = null;
    private String wordPressUser = null;
    private String wordPressPassword = null;
    private String configFileName = null;
    private boolean produceBOversion = true;
    private boolean provideSourceFiles = true;
    private String xrefFile = null;
    private boolean nv2;
    private boolean demo;

	public ProduceNaviCellMapFilesTask(String _configFileName, String _wordPressURL, String _wordPressUser, String _wordPressPassword, boolean _produceBOversion, boolean _provideSourceFiles, String _xrefFile, boolean _nv2, boolean _demo){
    	configFileName = _configFileName;
    	wordPressURL = _wordPressURL;
    	wordPressUser = _wordPressUser;
    	wordPressPassword = _wordPressPassword;
    	produceBOversion = _produceBOversion;
    	provideSourceFiles = _provideSourceFiles;
    	xrefFile = _xrefFile;
	nv2 = _nv2;
	demo = _demo;
    }
    
    public void run(TaskMonitor taskMonitor) {
    	taskMonitor.setTitle(getTitle());
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
    			
			final boolean wordpress_ssl = false; // EV 2013-08-01; set to false, but could be true according to a configuration file or whatever
			final boolean wordpress_xmlrpc_patched = false; // EV 2013-07-19; set to false, but could be true according to a configuration file or whatever
    			String[][] xrefs = null;
    			if(xrefFile!=null){
    			File xref_file = new File(xrefFile);
    			if(!xref_file.exists())
    				xref_file = null;
    			if (xref_file != null) {
    				BufferedReader xref_stream = ProduceClickableMap.open_file(xref_file);
    				xrefs = ProduceClickableMap.load_xrefs(xref_stream, xref_file.toString());
    			} else {
    				xrefs = null;
    			}
    			}
    			
    			
    			try{
    			if(produceBOversion)	
    				ProduceClickableMap.run(base, source_directory, make_tiles, only_tiles, blog_name, null, null, show_default_compartement_name, null, null, null, blog_name, wordpress_ssl, wordpress_xmlrpc_patched, destination, provideSourceFiles, nv2, demo);
    			else
    				ProduceClickableMap.run(base, source_directory, make_tiles, only_tiles, blog_name, null, null, show_default_compartement_name, wordPressURL, wordPressPassword, wordPressUser, blog_name, wordpress_ssl, wordpress_xmlrpc_patched, destination, provideSourceFiles, nv2, demo);
    			}catch(ProduceClickableMap.NaviCellException ne){
        			System.out.println("ERROR: "+ne.getMessage());
            	    taskMonitor.setProgress(1);
            	    taskMonitor.setStatusMessage("ERROR: "+ne.getMessage()+" \n(if this is a NullPointerException,\ntry to launch it for the second time)");
    			}
    			
    			taskMonitor.setProgress(1);
        	    taskMonitor.setStatusMessage("Finished creating NaviCell maps.");
    			
    		}else{
    			System.out.println("ERROR: File "+configFileName+" does not exist.");
        	    taskMonitor.setProgress(1);
        	    taskMonitor.setStatusMessage("ERROR: File "+configFileName+" does not exist.");
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	    taskMonitor.setProgress(1);
    	    taskMonitor.setStatusMessage("Error Creating NaviCell maps:" + e);
    	}
    }

    public String getTitle() {
    	return "BiNoM: Produce NaviCell map files...";
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

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

    
	
	
}
