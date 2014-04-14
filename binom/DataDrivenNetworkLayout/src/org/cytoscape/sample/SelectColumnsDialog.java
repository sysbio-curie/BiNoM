package org.cytoscape.sample;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SelectColumnsDialog extends JDialog implements ActionListener {
	
	
	private static final int SCROLL_WIDTH = 210;
	private static final int SCROLL_HEIGHT = 180;
	private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	
	private JPanel panel;
	private JButton okB, cancelB;
private JList sourceList, selList;
	private JScrollPane scrollPane;
	public ArrayList<String> myselList = null;
	
	
	SelectColumnsDialog(JFrame frame, String mess, boolean modal){
		
		// call JDialog constructor
		super(frame, mess, modal);
		createElements();
	}
	
	private void createElements() {
		
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints c;

		JLabel label;

		int y = 1;
		int x = 1;
		label = new JLabel("Source nodes");
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		panel.add(label, c);

		sourceList = new JList();
		sourceList.setBackground(new Color(0xeeeeee));
		scrollPane = new JScrollPane(sourceList);
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y + 1;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,0,10,10);
		scrollPane.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
		panel.add(scrollPane, c);
		JPanel buttonPanel = new JPanel();
		okB = new JButton("OK");
		
		
		okB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Object selList[] = sourceList.getSelectedValues();
				for (int i=0;i<selList.length;i++) {
	            String[] tk = ((String)selList[i]).split("::");
	            myselList.add(tk[0]);
	            
	            
	          }
			}
		});
		
		 

		buttonPanel.add(okB);
		cancelB = new JButton("Cancel");
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
	
	public void setDialogData(ArrayList<String> dataList) {
		
		Vector v = new Vector();
		for (String n : dataList)
			v.add(n);
		
		sourceList.setListData(v);
		
		setSize(400, 300);
		setLocation((screenSize.width - getSize().width) / 2,
				(screenSize.height - getSize().height) / 2);
	}
	
	public void actionPerformed(ActionEvent e) {
	  // nothing to do here
	}
}
