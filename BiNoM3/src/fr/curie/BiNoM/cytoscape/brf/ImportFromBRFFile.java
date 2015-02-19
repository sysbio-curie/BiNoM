package fr.curie.BiNoM.cytoscape.brf;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;

import Main.Launcher;
import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerVisualStyleDefinition;
import fr.curie.BiNoM.cytoscape.lib.NetworkFactory;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.wrappers.XGMML;
import fr.curie.BiNoM.pathways.*;

public class ImportFromBRFFile extends AbstractCyAction {
	
	public ImportFromBRFFile(){
		super("Import network from BiNoM reaction format",
        		Launcher.getAdapter().getCyApplicationManager(),
            "pippo",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu("Plugin.BiNoM 3.BiNoM I/O");
        this.insertSeparatorAfter();
        
	}
	
    public void actionPerformed(ActionEvent e) {
    	
    	File file;
    	JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setFileFilter(new TXTFileFilter());
	
		fileChooser.setDialogTitle("Load BiNoM reaction format file");
	
		JFrame frame = new JFrame();
		int returnVal = fileChooser.showOpenDialog(frame);
	
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    file = fileChooser.getSelectedFile();
	    	if (file != null) {
	    	    try {        
		        
				//String text = Utils.loadString(file.getAbsolutePath());
				
				    BiNoMReactionFormatToCytoscapeConverter brf = new BiNoMReactionFormatToCytoscapeConverter();
				    brf.loadFile(file.getAbsolutePath());
				    Graph graph = brf.convertToGraph();
				    
			
				    CyNetwork cyNetwork = NetworkFactory.createNetwork
				    		(file.getName(),
				    				XGMML.convertGraphToXGMML(graph),
				    				CellDesignerVisualStyleDefinition.getInstance(),
				    				false,
				    				null);
				    
		
			    }
			    catch(Exception ee) {
			    	ee.printStackTrace();
					JOptionPane.showMessageDialog
				    (null, /*Cytoscape.getDesktop(),*/
				     "Cannot open file " + file.getAbsolutePath() + " for reading\n"+ee.getMessage());
			    }
	    	}
		}
    }
}