package fr.curie.BiNoM.pathways.test;

import java.io.File;
import java.io.FileInputStream;

import com.ibm.adtech.jastor.JastorContext;
import com.ibm.adtech.jastor.JastorGenerator;
import com.hp.hpl.jena.rdf.model.*;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.pathways.biopax.Complex;
import fr.curie.BiNoM.pathways.biopax.ModificationFeature;
import fr.curie.BiNoM.pathways.biopax.Pathway;
import fr.curie.BiNoM.pathways.biopax.Protein;
import fr.curie.BiNoM.pathways.biopax.SequenceModificationVocabulary;
import fr.curie.BiNoM.pathways.biopax.biopax_DASH_level3_DOT_owlFactory;
import fr.curie.BiNoM.pathways.converters.BioPAX2Cytoscape;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;

public class TestJastorEB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try{

			//Model model = ModelFactory.createDefaultModel();
			//model.setNsPrefix("bp","http://www.biopax.org/release/biopax-level2.owl#");
			//biopax_DASH_level2_DOT_owlFactory.createpathway("http://bioinfo.curie.fr/celldesigner/celldesigner2biopax#pathway1",model);

			//BioPAX biopax= new BioPAX(BioPAX.biopaxString,"http://bioinfo.curie.fr/celldesigner/celldesigner2biopax#","http://bioinfo.curie.fr/biopaxmodel#");
			BioPAX biopax = new BioPAX();

			/*pathway path = biopax_DASH_level2_DOT_owlFactory.createpathway(biopax.namespaceString+"#pathway1",biopax.biopaxmodel);
			interaction inter = biopax_DASH_level2_DOT_owlFactory.createinteraction(biopax.namespaceString+"#interaction1",biopax.biopaxmodel);
			inter.setNAME("Interaction_name");
			//pathway path = biopax_DASH_level2_DOT_owlFactory.createpathway("http://bioinfo.curie.fr/celldesigner/celldesigner2biopax#pathway1",model);
			//biopax.biopaxmodel.createIndividual(path.uri(),path.resource());
			path.setNAME("pathway_name");
			path.setSHORT_DASH_NAME("short_name");
			path.addCOMMENT("This is a comment to the pathway");
			path.addPATHWAY_DASH_COMPONENTS(inter);
biopax_DASH_level3_DOT_owlFactory.
			protein prot = biopax_DASH_level2_DOT_owlFactory.createprotein(biopax.namespaceString+"protein1",biopax.biopaxmodel);*/

			
			
//			SequenceModificationVocabulary voc1 = biopax_DASH_level3_DOT_owlFactory.createSequenceModificationVocabulary(biopax.namespaceString+"voc1", biopax.biopaxmodel);
//			voc1.addTerm("Phosphorylation");
//			
//			ModificationFeature mod = biopax_DASH_level3_DOT_owlFactory.createModificationFeature(biopax.namespaceString+"mod1", biopax.biopaxmodel);
//			mod.setModificationType(voc1);
			
			Protein prot = biopax_DASH_level3_DOT_owlFactory.createProtein(biopax.namespaceString+"prot1", biopax.biopaxmodel);
			prot.addName("Prot1");
//			prot.addFeature(mod);
	
			
			Complex co = biopax_DASH_level3_DOT_owlFactory.createComplex(biopax.namespaceString+"complex1", biopax.biopaxmodel);
			co.addComponent(prot);
			
			//Pathway path = biopax_DASH_level3_DOT_owlFactory.createPathway(biopax.namespaceString+"pathway1", biopax.biopaxmodel);
			//path.addName("toto");
			
			BioPAX.saveToFile("/Users/eric/test_jastor.owl",biopax.biopaxmodel);
			
//			BioPAXToCytoscapeConverter b2c = new BioPAXToCytoscapeConverter();
//			
//			b2c.biopax = biopax;
//			
//			BioPAXToCytoscapeConverter.Graph graph = b2c.convert
//			(b2c.REACTION_NETWORK_CONVERSION,b2c,
//			biopax.idName,
//			new BioPAXToCytoscapeConverter.Option());
//			
//			System.out.println(graph.graphDocument.toString());
			

		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
