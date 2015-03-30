package fr.curie.BiNoM.cytoscape.analysis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.cytoscape.work.TaskIterator;

import Main.Launcher;
import fr.curie.BiNoM.cytoscape.biopax.OWLFileFilter;
import fr.curie.BiNoM.cytoscape.brf.TXTFileFilter;
import fr.curie.BiNoM.lib.GraphicUtils;
import fr.curie.BiNoM.pathways.analysis.structure.DataPathConsistencyAnalyzer;
import fr.curie.BiNoM.pathways.analysis.structure.Graph;

public class PathConsistencyAnalyzerBootstrapDialog extends JDialog {
	
    private static final double COEF_X = 1.24, COEF_Y = 1.10;
    private static final int MAX_ROWS = 20;
    private JList sourceList;
    private JList targetList;
    private static final String EMPTY_NAME = "                       ";
    private static final java.awt.Font TITLE_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 14);

    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);

    private static final int SCROLL_WIDTH = 380;
    private static final int SCROLL_HEIGHT = 180;
    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    
    private JPanel panel;
    
    private JTextField numberOfPermutations;
    
    public DataPathConsistencyAnalyzer analyzer;
    
    private JRadioButton permuteActiveNodesRB;
    private JRadioButton permuteAllNodesRB;


    public PathConsistencyAnalyzerBootstrapDialog(JFrame frame, String mess, boolean modal, DataPathConsistencyAnalyzer _analyzer){
    	super(frame, mess, modal);
    	analyzer = _analyzer; 
    	createElements();
    }

    public void fillTheData() {
    	setSize(500, 300);

    	setLocation((screenSize.width - getSize().width) / 2,
		    (screenSize.height - getSize().height) / 2);
    }

    private void createElements() {
    	panel = new JPanel(new GridBagLayout());

        int x = 1;
        int y = 0;
    	
    	
    	JLabel label = new JLabel("   Testing significance by activities permutation");
    	label.setFont(TITLE_FONT);
    	GridBagConstraints c = new GridBagConstraints();
    	c.gridx = x;
    	c.gridy = y;
    	c.anchor = GridBagConstraints.FIRST_LINE_START;
    	panel.add(label, c);
    	
    	y+=3;
    	
	y = GraphicUtils.addHSepPanel(panel, 1, y, 3, 2);    	
    	
        permuteActiveNodesRB = new JRadioButton("Permute activities on active nodes (fast mode)");
        permuteActiveNodesRB.setSelected(true);
        
    	c = new GridBagConstraints();
    	c.gridx = x;
    	c.gridy = y;
    	c.anchor = GridBagConstraints.WEST;
    	panel.add(permuteActiveNodesRB, c);
    	y+=2;
    	
    	permuteAllNodesRB = new JRadioButton("Permute activities on all nodes (slow mode)");
    	
    	permuteAllNodesRB.setEnabled(false);
    	
    	c = new GridBagConstraints();
    	c.gridx = x;
    	c.gridy = y;
    	c.anchor = GridBagConstraints.WEST;
    	panel.add(permuteAllNodesRB, c);
    	y++;
    	
    	ButtonGroup group = new ButtonGroup();
    	group.add(permuteActiveNodesRB);
    	group.add(permuteAllNodesRB);
    	
    	JLabel label1 = new JLabel("Number of permutations                       ");
    	c = new GridBagConstraints();
    	c.gridx = x;
    	c.gridy = y;
    	c.anchor = GridBagConstraints.EAST;
    	panel.add(label1, c);
    	
    	numberOfPermutations = new JTextField();
    	numberOfPermutations.setText("100    ");
    	c = new GridBagConstraints();
    	c.gridx = x;
    	c.gridy = y;
    	c.anchor = GridBagConstraints.EAST;
    	panel.add(numberOfPermutations, c);
    	y++;
    	
    	JButton saveScoresB = new JButton("Save permutation scores in a file...");
    	
    	saveScoresB.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
		    if(analyzer.SA_pathScoresRandomDistribution!=null)if(analyzer.SA_targetScoreRandomDistribution!=null){
    			try{
    				File file = null;
    				JFileChooser fileChooser = new JFileChooser();  				
    				fileChooser.setDialogTitle("Save Permutation scores");
 			
    				JFrame frame = new JFrame();			
    				int userSelection = fileChooser.showSaveDialog(frame);
    				
    				if (userSelection == JFileChooser.APPROVE_OPTION) {
    					file = fileChooser.getSelectedFile();
    					
    					if(!file.getAbsolutePath().endsWith(".txt"))
    						file = new File(file.getAbsolutePath() + ".txt");
    				}
    					    			    
    			    if(file!=null){
    			    	FileWriter fw = new FileWriter(file);
    			    	fw.write("TARGET SCORES:\n");
    			    	for(int kk=0;kk<analyzer.pathwayNodes.size();kk++){
				    fw.write(((fr.curie.BiNoM.pathways.analysis.structure.Node)analyzer.pathwayNodes.get(kk)).Id+"\t");
    			    	}
    			    	fw.write("\n");
    			    	for(int kk=0;kk<analyzer.SA_targetScoreRandomDistribution[0].length;kk++){
				    for(int ll=0;ll<analyzer.SA_targetScoreRandomDistribution.length;ll++){
					fw.write(analyzer.SA_targetScoreRandomDistribution[ll][kk]+"\t");
				    }
				    fw.write("\n");
    			    	}
    			    	fw.write("\n\nPATH SCORES:\n");
    			    	for(int kk=0;kk<analyzer.SA_pathScoresRandomDistribution[0].length;kk++){
				    fw.write(analyzer.SA_pathScoresRandomDistribution[0][kk]+"\n");
    			    	}
    			    	
    			    	fw.close();
    			    }
    			}catch(Exception eee){
			    eee.printStackTrace();
    			}
		    }
    		}
	    });
    	
    	
    	
    	JPanel buttonPanel = new JPanel();
    	
    	JButton okB = new JButton("Run analysis");
    	okB.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			
		    try{
			analyzer.numberOfPermutations = Integer.parseInt(numberOfPermutations.getText().trim());
		    }catch(Exception eee){
			numberOfPermutations.setText("100    ");
			analyzer.numberOfPermutations = 100;
		    }
		    if(permuteActiveNodesRB.isSelected())
			analyzer.testSignificanceMode = analyzer.PERMUTE_NODE_ACTIVITIES;
		    if(permuteAllNodesRB.isSelected())
			analyzer.testSignificanceMode = analyzer.PERMUTE_NODE_ACTIVITIES;
		    
		    TaskIterator t = new TaskIterator(new PathConsistencyAnalyzerBootstrapTask(analyzer));
			Launcher.getAdapter().getTaskManager().execute(t);
    			
    		}
    	    });
    	buttonPanel.add(okB);
    	
    	buttonPanel.add(saveScoresB);
    	
    	JButton cancelB = new JButton("Close");
    	cancelB.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    		    setVisible(false);
    		}
    	    });
    	buttonPanel.add(cancelB);
    	
    	
    	getContentPane().setLayout(new BorderLayout());
    	JScrollPane jpane = new JScrollPane(panel);
    	getContentPane().add(jpane, BorderLayout.CENTER);
    	getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
}
