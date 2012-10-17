package fr.curie.BiNoM.cytoscape.analysis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import fr.curie.BiNoM.pathways.analysis.structure.DataPathConsistencyAnalyzer;
import fr.curie.BiNoM.pathways.analysis.structure.OptimalCombinationAnalyzer;
import fr.curie.BiNoM.pathways.analysis.structure.DataPathConsistencyAnalyzer.optCutSetData;


/**
 * A simple dialog window to show the optimal intervention set search report.
 * 
 * @author ebo
 *
 */
public class OptimalCutSetReportDialog extends JDialog {
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
	 
	    private JFileChooser fc;
	    
	    private DataPathConsistencyAnalyzer dpc;

	    public OptimalCutSetReportDialog(DataPathConsistencyAnalyzer _dpc){
	    	dpc = _dpc;
	    }

	    public void pop() {
	    	build();
	    	
	    	this.setTitle("Optimal intervention set report");
	    	setLocation((screenSize.width - getSize().width) / 2,
	    			(screenSize.height - getSize().height) / 2);
	    	setVisible(true);
	    }
	    
	    
	    private void build() {
	    	
	    	getContentPane().removeAll();

	    	JPanel panel = new JPanel(new GridBagLayout());
	    	textArea = new JTextArea();
	    	panel.add(textArea);
	    	textArea.setText(dpc.optCutSetReport.toString());
	    	
	     	JScrollPane jpane = new JScrollPane(textArea);
			jpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			jpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);     	

			getContentPane().add(jpane,BorderLayout.CENTER);    	
			
			/*
			 * Ok button
			 */
			JPanel buttonPanel = new JPanel();
			JButton okB = new JButton("Ok");

			okB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
			buttonPanel.add(okB);
	    	
			/*
			 * copy report to clipboard button
			 */
			JButton copyB = new JButton("Copy report to clipboard");

			copyB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//setVisible(false);
					StringSelection data = new StringSelection(dpc.optCutSetReport.toString());
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(data, data);
				}
			});
			buttonPanel.add(copyB);

			/*
			 * Save report to file button
			 */
			fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fc.setSelectedFile(new File("Ocsana_report.txt"));
			JButton saveReportB = new JButton("Save report to file");
			saveReportB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					int ret = fc.showSaveDialog(OptimalCutSetReportDialog.this);
					if (ret == fc.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						saveReport(file);
					}
				}
			});
			buttonPanel.add(saveReportB);
			
			
			/*
			 * Save list of intervention sets to file button
			 */
			fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fc.setSelectedFile(new File("hit_set_list.txt"));
			JButton saveHitSetB = new JButton("Save intervention set list to file");
			saveHitSetB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					int ret = fc.showSaveDialog(OptimalCutSetReportDialog.this);
					if (ret == fc.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						saveData(file);
					}
				}
			});
			buttonPanel.add(saveHitSetB);
			
			getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			
			pack();
	    	
	    	Dimension size = getSize();
	    	setSize(new Dimension((int)(800), (int)(600)));
	    }

	    /**
	     * Save optimal intervention set list to a tab-delimited text file.
	     * 
	     * @param f
	     */
	    private void saveData(File f) {
	    	if (f != null)
	    		dpc.saveOptCutSetData(f);
	    }
	    
		/**
		 * save optimal intervention set report to a text file.
		 * 
		 * @param file
		 */
		private void saveReport(File file) {
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(file));
				out.write(dpc.optCutSetReport.toString());
				out.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

	    

}
