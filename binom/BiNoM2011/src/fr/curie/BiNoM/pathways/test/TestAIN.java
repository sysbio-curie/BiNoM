package fr.curie.BiNoM.pathways.test;

import java.io.File;
import java.io.FileReader;

import javax.swing.JFrame;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.cytoscape.ain.ImportFromAINDialogConstitutiveReactions;
import fr.curie.BiNoM.cytoscape.ain.ImportFromAINDialogFamily;
import fr.curie.BiNoM.cytoscape.ain.ImportFromAINTask;
import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.pathways.SimpleTextInfluenceToBioPAX;
import fr.curie.BiNoM.pathways.analysis.structure.BiographUtils;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class TestAIN {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String fileName = "/bioinfo/users/ebonnet/Binom/ExamplAin_II.txt";
		File file = new File(fileName);

		try {    
			String text = Utils.loadString(fileName);
			SimpleTextInfluenceToBioPAX.deleteInstance();
			text = SimpleTextInfluenceToBioPAX.getInstance().preprocessText(text);
			SimpleTextInfluenceToBioPAX.getInstance().prepareFamilies(text);
			SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamiliesExpand.clear();

			JFrame window = new JFrame();
			ImportFromAINDialogFamily dialog = new ImportFromAINDialogFamily(window,"Defing families",true);
			dialog.setVisible(true);

			if(dialog.result>0) {

				SimpleTextInfluenceToBioPAX.getInstance().userDefinedFamilies = dialog.tempFamilies;

				window = new JFrame();
				ImportFromAINDialogConstitutiveReactions dialogCR = new ImportFromAINDialogConstitutiveReactions(window,"Constitutive reactions",true);
				dialogCR.pop();

				if(dialogCR.result>0) {
					if (file != null) {
						//ImportFromAINTask iain = new ImportFromAINTask(file,text,dialogCR.constitutiveReactions);
						SimpleTextInfluenceToBioPAX.getInstance().makeBioPAX(text, dialogCR.constitutiveReactions);
						
						BioPAXToCytoscapeConverter b2s = new BioPAXToCytoscapeConverter();
						b2s.biopax = SimpleTextInfluenceToBioPAX.getInstance().biopax;
						
						//BioPAX.saveToFile("/bioinfo/users/ebonnet/test.owl",b2s.biopax.biopaxmodel);
						
						BioPAXToCytoscapeConverter.Graph graph = b2s.convert
						(b2s.REACTION_NETWORK_CONVERSION,b2s,
						BioPAX.idName,
						new BioPAXToCytoscapeConverter.Option());

						//XGMML.saveToXGMML(graph.graphDocument, "/bioinfo/users/ebonnet/out.xgmml");
						
					    if (graph != null) {		
					    	Graph graphDoc = XGMML.convertXGMMLToGraph(graph.graphDocument);
					    	Graph grres = BiographUtils.ShowMonoMolecularReactionsAsEdges(graphDoc);
					    	GraphDocument grDoc = XGMML.convertGraphToXGMML(grres);
					    	
					    	XGMML.saveToXGMML(grDoc, "/bioinfo/users/ebonnet/test.xgmml");
					    }
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
