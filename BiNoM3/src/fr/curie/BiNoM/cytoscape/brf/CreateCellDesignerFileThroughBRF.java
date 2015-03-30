/*
   BiNoM Cytoscape Plugin
   Copyright (C) 2006-2007 Curie Institute, 26 rue d'Ulm, 75005 Paris - FRANCE

   BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
*/

/*
  BiNoM authors:
	Andrei Zinovyev : http://www.ihes.fr/~zinovyev
	Eric Viara : http://www.sysra.com/viara
	Laurence Calzone :	http://leibniz.biol.vt.edu/people/laurence/laurence.html
*/
package fr.curie.BiNoM.cytoscape.brf;


import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;

import Main.Launcher;
import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.cytoscape.celldesigner.XMLFileFilter;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;
import fr.curie.BiNoM.pathways.BiNoMReactionFormatToCytoscapeConverter;
import fr.curie.BiNoM.pathways.CytoscapeToBiNoMReactionFormatConverter;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;


public class CreateCellDesignerFileThroughBRF extends AbstractCyAction {
	
	public CreateCellDesignerFileThroughBRF(){
		super("Create CellDesigner file from the current network...",
        		Launcher.getAdapter().getCyApplicationManager(),
            "pippo",
            Launcher.getAdapter().getCyNetworkViewManager());
        setPreferredMenu(Launcher.appName + ".BiNoM I/O");
	}

    public void actionPerformed(ActionEvent e) {
	CyNetwork network = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();
	
	
	JFileChooser fileChooser = new JFileChooser();

	fileChooser.setFileFilter(new XMLFileFilter());

	fileChooser.setDialogTitle("Save CellDesigner xml File");
	
	File file = null;
	
	int userSelection = fileChooser.showOpenDialog(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame());
	
	if (userSelection == JFileChooser.APPROVE_OPTION) {
		file = fileChooser.getSelectedFile();
		
		if(!file.getAbsolutePath().endsWith(".xml"))
			file = new File(file.getAbsolutePath() + ".xml");
		
		System.out.println("Save as file: " + file.getAbsolutePath());
	}
	else
		file = null;

	    if (file != null) {
	    	
		// Exporting to CellDesigner XML file
	    	try{
	    	    GraphDocument graphDocument = GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
	    	    Graph graph = XGMML.convertXGMMLToGraph(graphDocument);
	    	    CytoscapeToBiNoMReactionFormatConverter brf = new CytoscapeToBiNoMReactionFormatConverter();
	    	    brf.graph = graph;
	    	    String text = brf.convertToBRF();
	    	    System.out.println(text);

	    	    BiNoMReactionFormatToCytoscapeConverter brf2c = new BiNoMReactionFormatToCytoscapeConverter();
	    		brf2c.celldesignergenerator.createNewCellDesignerFile(file.getName(),1000,1500);
	    	    brf2c.statements = Utils.loadStringListFromText(text);
	    	    brf2c.addStatements();
	    	    brf2c.celldesignergenerator.processStatements();
	    	    
	    	    CellDesigner.saveCellDesigner(brf2c.celldesignergenerator.cd, file.getAbsolutePath());
	    		
	    	}catch(Exception ee){
	    		ee.printStackTrace();
	    	}

	    
        }
    }
}
