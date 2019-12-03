package fr.curie.BiNoM.test;

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
            Model model = BioPAXUtilities.loadModel("/Users/eric/work/binom/biopax/H_sapiens_L3.owl", BioPAX.biopaxFileString, BioPAX.importString);
            List pathways = biopax_DASH_level3_DOT_owlFactory.getAllPathway(model);
            System.out.println(pathways.size()+" pathways found");
            for(int i=0;i<pathways.size();i++){
                com.ibm.adtech.jastor.Thing pth = (com.ibm.adtech.jastor.Thing)pathways.get(i);
                //System.out.println(pth.uri());
            }
            String uri = "Apoptosis";
            Vector ids = new Vector();
            ids.add(uri);
            System.out.println("Extract URIs...");
            Model res1 = BioPAXUtilities.extractURIwithAllLinks(model, ids, BioPAX.biopaxString, BioPAX.importString);
            System.out.println("Save model...");
            System.out.println(BioPAX.biopaxString);
            BioPAXUtilities.saveModel(res1, "/Users/eric/work/binom/biopax/Apoptosis3.owl", BioPAX.biopaxString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

} 