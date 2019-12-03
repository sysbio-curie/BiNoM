package fr.curie.BiNoM.pathways.test;


import com.hp.hpl.jena.rdf.model.*;
import java.util.*;

import fr.curie.BiNoM.pathways.biopax.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.utils.*;

public class testBioPAXExtractAPO {
	public static void main(String[] args) {
		
		/*
		 * Extract Apoptosis network from reactome and create biopax file
		 */
		
		try{
			//Model model = BioPAXUtilities.loadModel("/Users/eric/work/binom/biopax/H_sapiens_L3.owl", BioPAX.biopaxFileString, BioPAX.importString);
			Model model = BioPAXUtilities.loadModel("c:/datas/reactome/reactome_human.owl", BioPAX.biopaxFileString, BioPAX.importString);
			List pathways = biopax_DASH_level3_DOT_owlFactory.getAllPathway(model);
			System.out.println(pathways.size()+" pathways found");
			for(int i=0;i<pathways.size();i++){
				com.ibm.adtech.jastor.Thing pth = (com.ibm.adtech.jastor.Thing)pathways.get(i);
				Pathway pathway = (Pathway)pth;
				String name = "";
				Iterator<String> its = ((Pathway)pth).getName();
				//System.out.println(((Pathway)pth).toString());
				while(its.hasNext()){
					name+=its.next();
				}
				//System.out.println(pth.uri()+"\t"+pathway.get);
			}
			String uri = "http://www.reactome.org/biopax/48887#Pathway18";
			Vector ids = new Vector();
			ids.add(uri);
			System.out.println("Extract URIs...");
			Model res1 = BioPAXUtilities.extractURIwithAllLinks(model, ids, BioPAX.biopaxString, BioPAX.importString);
			System.out.println("Save model...");
			System.out.println(BioPAX.biopaxString);
			//BioPAXUtilities.saveModel(res1, "/Users/eric/work/binom/biopax/Apoptosis3.owl", BioPAX.biopaxString);
			BioPAXUtilities.saveModel(res1, "c:/datas/reactome/"+uri.substring(37, uri.length()) +".owl", BioPAX.biopaxString);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
