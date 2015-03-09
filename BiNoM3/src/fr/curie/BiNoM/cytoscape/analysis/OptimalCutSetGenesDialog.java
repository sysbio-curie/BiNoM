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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import fr.curie.BiNoM.lib.GraphicUtils;
import fr.curie.BiNoM.pathways.analysis.structure.DataPathConsistencyAnalyzer;

/**
 * Paste box for a list of items to be selected in a JList.
 *  
 * @author ebonnet
 *
 */
public class OptimalCutSetGenesDialog extends JDialog {
	
	private static final double COEF_X = 1.24, COEF_Y = 1.10;
	private static final int MAX_ROWS = 20;
	private JTextArea sourceText;
	private static final java.awt.Font TITLE_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 14);

	private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);

	private static final int SCROLL_WIDTH = 380;
	private static final int SCROLL_HEIGHT = 180;
	private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

	private JPanel panel;
	
	public ArrayList<String> nodeNameArray;

	// constructor
	public OptimalCutSetGenesDialog(JFrame frame, String mess, boolean modal) {
		super(frame, mess, modal);
		this.nodeNameArray = new ArrayList<String>();
		createElements();
	}

	
	private void createElements() {
		
		setSize(500, 300);
		setLocation((screenSize.width - getSize().width) / 2, (screenSize.height - getSize().height) / 2);
		
		panel = new JPanel(new GridBagLayout());

		int x = 1;
		int y = 0;

		JLabel label = new JLabel("Paste a list of items in the text box:");
		label.setFont(TITLE_FONT);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		panel.add(label, c);

		y+=2;

		//y = GraphicUtils.addHSepPanel(panel, 1, y, 3, 2);    	

		sourceText = new JTextArea();
		sourceText.setColumns(40);
		sourceText.setLineWrap(true);
        sourceText.setRows(10);
        sourceText.setWrapStyleWord(true);

		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.CENTER;
		panel.add(sourceText, c);
		y++;

		JPanel buttonPanel = new JPanel();

		JButton okB = new JButton("Ok");
		okB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			     String str = sourceText.getText();
			     String[] tk = str.split("\\s+");
			     //nodeNameArray = tk;
			     for (String s : tk)
			    	 nodeNameArray.add(s);
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
