package fr.curie.BiNoM.pathways.test;

import java.io.*;
import java.util.*;

import org.csml.csml.version3.ProjectDocument;

import fr.curie.BiNoM.pathways.CSMLToCytoscapeConverter;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.wrappers.CSML;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class testMergeNetworksAndFilter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{

			String fn = "c:/datas/csml/BasalvsNormal_4_Andrei";
			File f = new File(fn);
			File files[] = f.listFiles();
			Vector<Graph> graphs = new Vector<Graph>(); 
			for(int i=0;i<files.length;i++){
				System.out.println((i+1)+":"+files[i].getAbsolutePath());
				ProjectDocument project = CSML.loadCSML(files[i].getAbsolutePath());
				CSMLToCytoscapeConverter ccc = new CSMLToCytoscapeConverter();
				Graph gr = ccc.getGraph(project);
				graphs.add(gr);
			}
			
			Graph gr = BiographUtils.MergeNetworkAndFilter(graphs, 0.67f);
			XGMML.saveToXGMML(gr, "c:/datas/csml/merged.xgmml");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
