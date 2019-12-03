package fr.curie.BiNoM.cytoscape.analysis;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PathConsistencyAnalyzerReportDialog extends JDialog {


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
    String reportText;


    public PathConsistencyAnalyzerReportDialog(String _reportText){
    	reportText = _reportText;
    }

    public void pop() {
    	
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
   
    	textArea.setText(reportText);
    	
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

