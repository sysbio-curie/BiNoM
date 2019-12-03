package fr.curie.BiNoM.pathways.wrappers;

import java.util.*;
import java.io.*;

import org.csml.csml.version3.*;
import org.apache.xmlbeans.*;

import fr.curie.BiNoM.pathways.utils.*;


public class CSML {
	
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
