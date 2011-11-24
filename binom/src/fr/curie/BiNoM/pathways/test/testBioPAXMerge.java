package fr.curie.BiNoM.pathways.test;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.shared.*;

import java.io.*;
import java.util.*;
import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import edu.rpi.cs.xgmml.*;


public class testBioPAXMerge {

	public static void main(String[] args) {
		try{
			
		    String namespaceString = "http://www.biopax.org/release/biopax-level2.owl#";
		    String importString = "http://www.biopax.org/Downloads/Level2v0.92/biopax-level2.owl";
		    String biopaxString = "http://www.biopax.org/release/biopax-level2.owl#";
		    
		    /*GraphDocument gSMAC = XGMML.loadFromXMGML("c:/datas/binomtest/smac.xgmml");
		    GraphDocument gXIAP = XGMML.loadFromXMGML("c:/datas/binomtest/casp8.xgmml");
		    BioPAX biopax = new BioPAX();
		    biopax.loadBioPAX("c:/datas/binomtest/Apoptosis.owl");
		    
		    BioPAX SMAC = CytoscapeToBioPAXConverter.convert(gSMAC, biopax);
		    BioPAX XIAP = CytoscapeToBioPAXConverter.convert(gXIAP, biopax);
		    
		    BioPAXMerge merge = new BioPAXMerge();
		    merge.mainfile = SMAC.model;
		    merge.referenceFiles.add(XIAP.model);
		    merge.mergeMainWithReferences();
		    
		    BioPAXUtilities.saveModel(merge.mainfile, "c:/datas/binomtest/smac_casp8.owl", BioPAX.biopaxString);
		    
		    System.exit(0);*/

		    String path = "c:/datas/biobase/ver9/";
		    //String path = "";
		    //String prefix = "c:/datas/biobase/biopax/reaction_evidence";
		    String prefix = "reaction_evidence";
		    //String prefix = "c:/datas/biobase/biopax/G1";
		    System.out.println("Loading resources...");
		    //Model main = BioPAXUtilities.loadModel("c:/datas/biobase/biopax/G1.owl",namespaceString,importString);
		    //Model main = BioPAXUtilities.loadModel(path+prefix+".owl",namespaceString,importString);
		    
		    System.out.println("Molecule...");
		    Model molecules1 = BioPAXUtilities.loadModel(path+"molecules1.owl",namespaceString,importString);
		    Model molecules2 = BioPAXUtilities.loadModel(path+"molecules2.owl",namespaceString,importString);
		    Model molecules3 = BioPAXUtilities.loadModel(path+"molecules3.owl",namespaceString,importString);
		    Model molecules4 = BioPAXUtilities.loadModel(path+"molecules4.owl",namespaceString,importString);
		    Model molecules5 = BioPAXUtilities.loadModel(path+"molecules5.owl",namespaceString,importString);
		    Model molecules6 = BioPAXUtilities.loadModel(path+"molecules6.owl",namespaceString,importString);
		    Model molecules7 = BioPAXUtilities.loadModel(path+"molecules7.owl",namespaceString,importString);
		    Model molecules8 = BioPAXUtilities.loadModel(path+"molecules8.owl",namespaceString,importString);
		    Model molecules9 = BioPAXUtilities.loadModel(path+"molecules9.owl",namespaceString,importString);
		    Model molecules10 = BioPAXUtilities.loadModel(path+"molecules10.owl",namespaceString,importString);		    
		    
		    System.out.println("Gene...");
		    Model genes = BioPAXUtilities.loadModel(path+"gene.owl",namespaceString,importString);
		    System.out.println("Reference...");
		    Model publications = BioPAXUtilities.loadModel(path+"reference.owl",namespaceString,importString);
		    System.out.println("Loaded...");
		    Utils.printUsedMemory();

		    BioPAXMerge bm = new BioPAXMerge();
		    //bm.mainfile = main;
		    bm.referenceFiles.add(genes);
		    /*bm.referenceFiles.add(molecules1);
		    bm.referenceFiles.add(molecules2);
		    bm.referenceFiles.add(molecules3);
		    bm.referenceFiles.add(molecules4);
		    bm.referenceFiles.add(molecules5);
		    bm.referenceFiles.add(molecules6);
		    bm.referenceFiles.add(molecules7);
		    bm.referenceFiles.add(molecules8);
		    bm.referenceFiles.add(molecules9);
		    bm.referenceFiles.add(molecules10);*/		    
		    //bm.referenceFiles.add(publications);

		    //String infile = args[0];
		    //String outfile = args[1];
		    String infile = path+prefix+".owl";
		    String outfile = path+prefix+"_full.owl";
		    
		    System.out.println("Loading "+infile+"...");
		    Model main = BioPAXUtilities.loadModel(infile,namespaceString,importString);
		    
		    //BioPAXMerge bm = new BioPAXMerge();
		    bm.mainfile = main;
	    		    
		    for(int i=2;i<args.length;i++){
		    	String fn = args[i];
		    	System.out.println("Loading reference "+fn+"...");
		    	Model mod = BioPAXUtilities.loadModel(fn,namespaceString,importString);;
		    	bm.referenceFiles.add(mod);
		    }
		    		    
		    bm.updateMainfileWithReferences();
		    
		    System.out.println("Saving "+outfile+"...");
		    BioPAXUtilities.saveModel(bm.mainfile,outfile,biopaxString);

			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
