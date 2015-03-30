package fr.curie.BiNoM.cytoscape.analysis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import fr.curie.BiNoM.lib.GraphicUtils;
import fr.curie.BiNoM.pathways.analysis.structure.DataPathConsistencyAnalyzer;

/**
 * Dialog for setting options for the visualization of optimal intervention sets in Cytoscape graphs.
 *  
 * @author ebonnet
 *
 */
public class OptimalCutSetVisuDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel panel;
	private JTextField nbTop;
	private JCheckBox topButton;
	public int nbTopSets = 100;
	public boolean createSetAttributes = true;

	// construct me!
	public OptimalCutSetVisuDialog(JFrame frame, String mess, boolean modal) {
		super(frame, mess, modal);
		createElements();
	}

	private void createElements() {
		
		setSize(500, 300);
		setLocation((screenSize.width - getSize().width) / 2, (screenSize.height - getSize().height) / 2);
		
		panel = new JPanel(new GridBagLayout());

		int x = 1;
		int y = 0;
		
		topButton = new JCheckBox("Create attributes corresponding to optimal intervention sets");
		topButton.setSelected(true);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		panel.add(topButton, c);

		y++;
		
		JLabel nbLabel = new JLabel("Number of top intervention sets to select");
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		panel.add(nbLabel, c);

		nbTop = new JTextField(5);
		nbTop.setText("100");
		nbTop.setEditable(true);
		c = new GridBagConstraints();
		c.gridx = x+1;
		c.gridy = y;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		panel.add(nbTop, c);
		
		JPanel buttonPanel = new JPanel();
		JButton okB = new JButton("Ok");
		okB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nbTopSets = Integer.parseInt(nbTop.getText());
				createSetAttributes = topButton.isSelected();
				setVisible(false);
			}
		});
		buttonPanel.add(okB);

		JButton cancelB = new JButton("Cancel");
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
		
		pack();
	}
}
