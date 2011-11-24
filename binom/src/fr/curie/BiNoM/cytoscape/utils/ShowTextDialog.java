package fr.curie.BiNoM.cytoscape.utils;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.*;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

import cytoscape.*;
import fr.curie.BiNoM.cytoscape.biopax.propedit.*;
import fr.curie.BiNoM.cytoscape.lib.*;
import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.biopax.biopax_DASH_level2_DOT_owlFactory;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.*;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.pathways.biopax.*;
import edu.rpi.cs.xgmml.*;

public class ShowTextDialog extends JDialog {

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
    
    public String text = "";
    public String title = "";
    
    JTextArea textArea = null;

    public void pop(String _title, String _text) {

    	text = _text;
    	title = _title;
    	
    	build();

    	setLocation((screenSize.width - getSize().width) / 2,
                        (screenSize.height - getSize().height) / 2);
    	setVisible(true);
        }

    
    private void build() {
    	getContentPane().removeAll();

    	this.setTitle(title);
    	
    	JPanel panel = new JPanel(new GridBagLayout());
    	
    	JPanel controlPanel = new JPanel(new GridBagLayout());
    	
    	GridBagConstraints c;
    	
    	textArea = new JTextArea();
    	
    	panel.add(textArea);
   
    	textArea.setText(getTextAreaContent());
    	
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
    
    private String getTextAreaContent(){
    	return text;
    }
    private void setTextAreaContent(String _text){
    	text = _text;
    }
    
	
	
}
