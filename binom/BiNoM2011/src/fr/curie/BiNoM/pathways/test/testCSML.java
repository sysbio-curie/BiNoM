package fr.curie.BiNoM.pathways.test;

import java.util.*;
import java.io.*;

import org.csml.csml.version3.*;
import org.apache.xmlbeans.*;

import fr.curie.BiNoM.pathways.utils.*;


public class testCSML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
		String fn = "c:/datas/csml/apoptosis";
		ProjectDocument project = loadCSML(fn+".xml");
		
		FileWriter fw = new FileWriter(fn+".sif");

		System.out.println(project.getProject().getModel().getEntitySet().sizeOfEntityArray()+" entities");
		for(int i=0;i<project.getProject().getModel().getEntitySet().sizeOfEntityArray();i++){
			EntityDocument.Entity ent = project.getProject().getModel().getEntitySet().getEntityArray(i);
			System.out.println(ent.getId()+"\t"+ent.getName()+"\t"+ent.getType());
		}
		System.out.println("----------------------------\n"+project.getProject().getModel().getProcessSet().sizeOfProcessArray()+" processes");
		for(int i=0;i<project.getProject().getModel().getProcessSet().sizeOfProcessArray();i++){
			ProcessDocument.Process proc = project.getProject().getModel().getProcessSet().getProcessArray(i);
			System.out.println(proc.getId()+"\t"+proc.getName()+"\t"+proc.getType());
			Vector<String> ins = new Vector<String>();
			Vector<String> outs = new Vector<String>();
			for(int j=0;j<proc.sizeOfConnectorArray();j++){
				ConnectorDocument.Connector cn = proc.getConnectorArray(j);
				//if(cn.)
			}
			//fw.write();
			
		}
		
		
		
		fw.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static org.csml.csml.version3.ProjectDocument loadCSML(String filename){
		org.csml.csml.version3.ProjectDocument project = null;
		try{
		   String text = Utils.loadString(filename);
		   StringReader st = new StringReader(text);
		   project = org.csml.csml.version3.ProjectDocument.Factory.parse(st);
		}catch(Exception e){
			e.printStackTrace();
		}
		return project;
	}

}
