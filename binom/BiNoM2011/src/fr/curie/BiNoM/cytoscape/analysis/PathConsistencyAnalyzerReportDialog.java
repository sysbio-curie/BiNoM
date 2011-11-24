package fr.curie.BiNoM.cytoscape.analysis;

import javax.swing.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;


/**
 * Report dialog box for optimal cut set results. 
 * 
 * 
 * @author ebonnet
 *
 */
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
    
    private JTextArea textArea = null;
    private String reportText;


    public PathConsistencyAnalyzerReportDialog(String _reportText){
    	reportText = _reportText;
    }

    public void pop() {
    	build();
    	
    	this.setTitle("Optimal cut set report");
    	setLocation((screenSize.width - getSize().width) / 2,
    			(screenSize.height - getSize().height) / 2);
    	setVisible(true);
    }
    
    
    private void build() {
    	
    	getContentPane().removeAll();

    	JPanel panel = new JPanel(new GridBagLayout());
    	textArea = new JTextArea();
    	panel.add(textArea);
    	textArea.setText(reportText);
    	
     	JScrollPane jpane = new JScrollPane(textArea);
		jpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);     	

		getContentPane().add(jpane,BorderLayout.CENTER);    	
		
		JPanel buttonPanel = new JPanel();
		JButton okB = new JButton("Ok");

		okB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		buttonPanel.add(okB);
    	
		JButton copyB = new JButton("Copy report to clipboard");

		copyB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//setVisible(false);
				StringSelection data = new StringSelection(reportText);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(data, data);
			}
		});

		buttonPanel.add(copyB);
		
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		pack();
    	
    	Dimension size = getSize();
    	setSize(new Dimension((int)(800),
			      (int)(600)));
    }
    

	
	
}

