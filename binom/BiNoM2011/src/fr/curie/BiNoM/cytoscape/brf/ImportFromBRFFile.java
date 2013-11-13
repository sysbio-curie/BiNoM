package fr.curie.BiNoM.cytoscape.brf;

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;
import cytoscape.util.CyFileFilter;
import cytoscape.util.FileUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.net.URL;

import javax.swing.*;

import fr.curie.BiNoM.cytoscape.celldesigner.CellDesignerVisualStyleDefinition;
import fr.curie.BiNoM.cytoscape.lib.NetworkFactory;
import fr.curie.BiNoM.pathways.CellDesignerToCytoscapeConverter;
import fr.curie.BiNoM.pathways.SimpleTextInfluenceToBioPAX;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.XGMML;
import fr.curie.BiNoM.pathways.*;

public class ImportFromBRFFile implements ActionListener {
	
    public void actionPerformed(ActionEvent e) {
        CyFileFilter bioPaxFilter = new CyFileFilter();

        bioPaxFilter.addExtension("txt");
        bioPaxFilter.setDescription("BiNoM reaction format (BRF) files");

        File file = FileUtil.getFile
	    ("Load BiNoM reaction format file", FileUtil.LOAD, new CyFileFilter[]{bioPaxFilter});

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
			    (Cytoscape.getDesktop(),
			     "Cannot open file " + file.getAbsolutePath() + " for reading\n"+ee.getMessage());
		    }
	    
    }
}