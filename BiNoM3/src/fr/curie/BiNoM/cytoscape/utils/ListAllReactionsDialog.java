package fr.curie.BiNoM.cytoscape.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.*;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;
import fr.curie.BiNoM.pathways.utils.*;

public class ListAllReactionsDialog extends JDialog {

    private static final double COEF_X = 1.24, COEF_Y = 1.05;

    private static final java.awt.Font TITLE_FONT = new java.awt.Font("times",
							     java.awt.Font.BOLD,
							     14);
    private static final java.awt.Font BOLD_FONT = new java.awt.Font("times",
							     java.awt.Font.BOLD,
							     12);

    private static final java.awt.Font STD_BOLD_FONT = new java.awt.Font("times",
									 java.awt.Font.BOLD,
									 10);
    private static final java.awt.Font STD_FONT = new java.awt.Font("times",
								    java.awt.Font.PLAIN,
								    10);

    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    JTextArea textArea = null;
    
    GraphDocument _graphDoc;
    org.sbml.x2001.ns.celldesigner.SbmlDocument _sbmlobject = null;
    HashMap _sbmlObjectMap = null;
    BioPAX _biopaxobject = null;

    public void pop(GraphDocument grDoc, org.sbml.x2001.ns.celldesigner.SbmlDocument cd, BioPAX bp) {
    	
    	_graphDoc = grDoc;
    	_sbmlobject = cd;
    	if(cd!=null)
    		_sbmlObjectMap = CellDesigner.getEntities(cd);
    	_biopaxobject = bp;

    	build();

    	setLocation((screenSize.width - getSize().width) / 2,
                        (screenSize.height - getSize().height) / 2);
    	setVisible(true);
        }

    private void build() {
    	getContentPane().removeAll();

    	JPanel panel = new JPanel(new GridBagLayout());
    	
    	JPanel controlPanel = new JPanel(new GridBagLayout());

    	GridBagConstraints c;
    	
    	textArea = new JTextArea();
    	
    	panel.add(textArea);
   
    	try{
    	textArea.setText(GraphUtils.getListOfReactionsTable(XGMML.convertXGMMLToGraph(_graphDoc), _biopaxobject, _sbmlobject));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
     	JScrollPane jpane = new JScrollPane(textArea);

		jpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);     	

		//getContentPane().add(controlPanel,BorderLayout.NORTH);
		getContentPane().add(jpane,BorderLayout.CENTER);    	
    	//getContentPane().add(jpane);

    	pack();
    	
    	Dimension size = getSize();
    	setSize(new Dimension((int)(600),
			      (int)(300)));

    }

    
}
