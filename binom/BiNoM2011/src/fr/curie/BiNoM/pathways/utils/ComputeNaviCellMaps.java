package fr.curie.BiNoM.pathways.utils;

import java.util.*;
import java.io.*;
import fr.curie.BiNoM.pathways.navicell.*;

public class ComputeNaviCellMaps {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try{
			
			File currentDir = new File(".");
			File listofdirs[] = currentDir.listFiles();
			
			for(int i=0;i<listofdirs.length;i++){
				if(listofdirs[i].isDirectory()){
					File dir = listofdirs[i];
					System.out.println("DIR: "+dir.getAbsolutePath());
					File listOfFiles[] = dir.listFiles();
					for(int j=0;j<listOfFiles.length;j++)if(listOfFiles[j].getName().equals("config")){
						System.out.println("config file found!");
						// read the config file
						final Properties configuration = ProduceClickableMap.load_config(listOfFiles[j]);
						String project_name = configuration.getProperty("name", "default");
						String newargs[] = new String[args.length+4];
						boolean wordpressfound = false;
						for(int k=0;k<args.length;k++){
							newargs[k] = args[k];
							if(args[k].endsWith("wordpress"))
								wordpressfound = true;
						}
						String suffix = "";
						if(!wordpressfound) suffix = "_light";
						File newProject = new File(currentDir.getAbsolutePath()+"/"+project_name+suffix);
						newProject.mkdir();
						newargs[args.length] = "--config";
						newargs[args.length+1] = listOfFiles[j].getAbsolutePath();
						newargs[args.length+2] = "--destination";
						newargs[args.length+3] = newProject.getAbsolutePath();
						ProduceClickableMap.main(newargs);
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
