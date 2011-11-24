package fr.curie.BiNoM.cytoscape.analysis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cytoscape.Cytoscape;
import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.pathways.analysis.structure.DataPathConsistencyAnalyzer;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

/**
 * Root class  for the optimal cut set analysis.
 * 
 * Called by the BiNoM menu directly.
 * 
 * @author ebonnet
 *
 */
public class OptimalCutSetAnalyzer implements ActionListener {

	   public void actionPerformed(ActionEvent e){
	    	if(Cytoscape.getCurrentNetwork()!=null){
	    		
	    		OptimalCutSetAnalyzerDialog dialog = new OptimalCutSetAnalyzerDialog(Cytoscape.getDesktop(),"Optimal Combinations of Intervention Strategies for Network Analysis",true);
	    		
	    		GraphDocument grDoc = GraphDocumentFactory.getInstance().createGraphDocument(Cytoscape.getCurrentNetwork());
	    		
	    		dialog.analyzer = new DataPathConsistencyAnalyzer();
//	    		try {
//	    		dialog.analyzer.loadGraph("/bioinfo/users/ebonnet/Binom/signal.xgmml");
//	    		}
//	    		catch (Exception ex) {
//	    			ex.printStackTrace();
//	    		}
	    		
	    		dialog.analyzer.graph = XGMML.convertXGMMLToGraph(grDoc);
	    		dialog.fillTheData();
	    		dialog.setVisible(true);
	    		
	    		if (dialog.result>0) {
					String report = dialog.analyzer.optCutSetReport.toString();
					PathConsistencyAnalyzerReportDialog reportForm = new PathConsistencyAnalyzerReportDialog(report);
					reportForm.pop();
				}
	    		
	    	}
	    }
	
}
