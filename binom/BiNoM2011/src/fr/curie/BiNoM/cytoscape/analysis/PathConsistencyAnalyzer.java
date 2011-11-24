package fr.curie.BiNoM.cytoscape.analysis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.cytoscape.lib.*;
import fr.curie.BiNoM.pathways.wrappers.*;
import edu.rpi.cs.xgmml.*;

import cytoscape.Cytoscape;


public class PathConsistencyAnalyzer implements ActionListener{
	
    public void actionPerformed(ActionEvent e){
    	if(Cytoscape.getCurrentNetwork()!=null){
    		
    		PathConsistencyAnalyzerDialog dialog = new PathConsistencyAnalyzerDialog(Cytoscape.getDesktop(),"Path consistency analyzer",true);
    		
    		GraphDocument grDoc = GraphDocumentFactory.getInstance().createGraphDocument(Cytoscape.getCurrentNetwork());
    		
    		/*try{
    		XGMML.saveToXGMML(grDoc, "c:/datas/ewing/pathanalysis/temp1.xgmml");
    		}catch(Exception ee){}*/
    		
    		dialog.analyzer = new DataPathConsistencyAnalyzer();
    		dialog.analyzer.graph = XGMML.convertXGMMLToGraph(grDoc);
    		dialog.fillTheData();
    		dialog.setVisible(true);
    		
    		// path consistency post-processing and analysis
    		
    		// regular path consistency (piquant) analysis
    		if(dialog.result>0){
    			PathConsistencyAnalyzerPathDialog dialogPath = new PathConsistencyAnalyzerPathDialog(Cytoscape.getDesktop(),"Path Browser",false);
        		dialogPath.analyzer = dialog.analyzer;
        		dialogPath.fillTheData();
        		dialogPath.setVisible(true);
    		}
    		
    	}
    }


}
