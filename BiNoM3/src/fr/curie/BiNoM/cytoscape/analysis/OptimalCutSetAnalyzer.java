package fr.curie.BiNoM.cytoscape.analysis;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.cytoscape.application.swing.AbstractCyAction;

import Main.Launcher;
import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.pathways.analysis.structure.DataPathConsistencyAnalyzer;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

/**
 * Root class  for the optimal intervention set analysis.
 * 
 * Called by the BiNoM menu directly.
 * 
 * @author ebonnet
 *
 */
public class OptimalCutSetAnalyzer extends AbstractCyAction {
	
	public OptimalCutSetAnalyzer(){
		super("OCSANA analysis...",
        		Launcher.getAdapter().getCyApplicationManager(),
            "network",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu("Plugin.BiNoM 3.Analysis");
	}

	   public void actionPerformed(ActionEvent e){
	    	if(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()!=null){
	    		
	    		OptimalCutSetAnalyzerDialog dialog = new OptimalCutSetAnalyzerDialog(null, /*Cytoscape.getDesktop(),*/"Optimal Combinations of Intervention (CI) Strategies for Network Analysis",true);
	    		
	    		GraphDocument graphDocument = GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
	    		
	    		dialog.analyzer = new DataPathConsistencyAnalyzer();
	    		
	    		dialog.analyzer.graph = XGMML.convertXGMMLToGraph(graphDocument);
	    		dialog.fillTheData();
	    		dialog.setVisible(true);
	    		
	    		if (dialog.result>0) {
					OptimalCutSetReportDialog reportForm = new OptimalCutSetReportDialog(dialog.analyzer);
					reportForm.pop();
				}
	    		
	    	}
	    }
}
