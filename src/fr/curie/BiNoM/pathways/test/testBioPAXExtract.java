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

public class testBioPAXExtract {

	public static void main(String[] args) {
		try{
			
			//String namespaceString = "http://www.biopax.org/release/biopax-level2.owl#";
		    //String importString = "http://www.biopax.org/Downloads/Level2v0.92/biopax-level2.owl";
		    //String biopaxString = "http://www.biopax.org/release/biopax-level2.owl#";			
			
			Model model = BioPAXUtilities.loadModel("c:/datas/binomtest/biopax3/apoptosis3.owl", BioPAX.biopaxFileString, BioPAX.importString);
			System.out.println("Loaded.");
			String uri = "http://www.reactome.org/biopax#SMAC_mediated_dissociation_of_IAP_caspase_complexes_";
			Vector ids = new Vector();
			ids.add(uri);
			Model res = BioPAXUtilities.extractURIwithAllLinks(model, ids, BioPAX.biopaxString, BioPAX.importString);
			BioPAXUtilities.saveModel(res, "c:/datas/binomtest/biopax3/SMAC_IAP_dissociation.owl", BioPAX.biopaxString);

			/*Model model = BioPAXUtilities.loadModel("c:/datas/biopax/ReactomeJan2011/Homo_sapiens2.owl", BioPAX.biopaxFileString, BioPAX.importString);
			List pathways = biopax_DASH_level2_DOT_owlFactory.getAllpathway(model);
			System.out.println(pathways.size()+" pathways found");
			for(int i=0;i<pathways.size();i++){
				com.ibm.adtech.jastor.Thing pth = (com.ibm.adtech.jastor.Thing)pathways.get(i);
				//System.out.println(pth.uri());
			}
			String uri = "Apoptosis";
			Vector ids = new Vector();
			ids.add(uri);
			Model res1 = BioPAXUtilities.extractURIwithAllLinks(model, ids, BioPAX.biopaxString, BioPAX.importString);
			BioPAXUtilities.saveModel(res1, "c:/datas/biopax/ReactomeJan2011/Apoptosis2.owl", BioPAX.biopaxString);
			System.exit(0);
			
			
			BioPAX biopax = new BioPAX();
			biopax.loadBioPAX("c:/datas/biopax/NatureNCI/Ceramide.owl");
			
			GraphDocument grdoc = XGMML.loadFromXMGML("c:/datas/biopax/NatureNCI/temp.xgmml");
			BioPAX res = CytoscapeToBioPAXConverter.convert(grdoc, biopax);
		    res.saveToFile("c:/datas/biopax/NatureNCI/temp.owl", res.biopaxmodel);*/
			
		    /*String namespaceString = "http://www.biopax.org/release/biopax-level2.owl#";
		    String importString = "http://www.biopax.org/Downloads/Level2v0.92/biopax-level2.owl";
		    String biopaxString = "http://www.biopax.org/release/biopax-level2.owl#";
		
		    String prefix = "c:/datas/biobase/testP53Mdm2/";
		    
		    Model source = BioPAXUtilities.loadModel("c:/datas/biobase/biopax/reaction_pathway_full.owl",namespaceString,importString);
		    Graph gr = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML(prefix+"final.xgmml"));
		    //Vector v = new Vector(); v.add("XN000024100");
		    //Model res = BioPAXUtilities.extractFromModel(source, v, namespaceString,importString);
		    Model res = BioPAXUtilities.extractFromModel(source, gr, namespaceString,importString);
		    BioPAXUtilities.saveModel(res, prefix+"final.owl", biopaxString);*/
			
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
