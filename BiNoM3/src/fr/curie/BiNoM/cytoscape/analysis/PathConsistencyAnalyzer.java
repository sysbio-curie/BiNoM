package fr.curie.BiNoM.cytoscape.analysis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import org.cytoscape.application.swing.AbstractCyAction;

import Main.Launcher;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.cytoscape.lib.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import edu.rpi.cs.xgmml.*;


public class PathConsistencyAnalyzer extends AbstractCyAction{
	
	public PathConsistencyAnalyzer(){
		super("Path Influence Quantification analysis...",
        		Launcher.getAdapter().getCyApplicationManager(),
            "network",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu("Plugin.BiNoM 3.Analysis");
	}
	
    public void actionPerformed(ActionEvent e){
    	if(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork()!=null){
    		
    		PathConsistencyAnalyzerDialog dialog = new PathConsistencyAnalyzerDialog(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(),"Path Influence Quantification analyzer",true);
    		
    		GraphDocument graphDocument = GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
    		
    		/*try{
    		XGMML.saveToXGMML(grDoc, "c:/datas/ewing/pathanalysis/temp1.xgmml");
    		}catch(Exception ee){}*/
    		
    		dialog.analyzer = new DataPathConsistencyAnalyzer();
    		dialog.analyzer.graph = XGMML.convertXGMMLToGraph(graphDocument);
    		dialog.fillTheData();
    		dialog.setVisible(true);
    		
    		// path consistency post-processing and analysis
    		
    		// regular path consistency (piquant) analysis
    		if(dialog.result>0){
    			PathConsistencyAnalyzerPathDialog dialogPath = new PathConsistencyAnalyzerPathDialog(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame(),"Path Browser",false);
        		dialogPath.analyzer = dialog.analyzer;
        		dialogPath.fillTheData();
        		dialogPath.setVisible(true);
    		}
    		
    	}
    }


}
