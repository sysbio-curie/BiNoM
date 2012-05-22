package fr.curie.BiNoM.pathways.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MergingMapsDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private JList mergeListProtein;
	private JList mergeListSpecies;
	private JTable tableSpecies;
	private JLabel label1;
	private JButton buttonMerge;
	private JButton buttonDisplay;
	private ArrayList<File> fileList;
	
	private MergingMapsProcessor mmProc;
	
	private int numFiles;
	private int numStep;
	
	
	public MergingMapsDialog(JFrame frame, String mess, boolean modal){
		// call JDialog constructor
		super(frame, mess, modal);
		init();
	}
	
	public void setFileList(ArrayList<File> f) {
		this.fileList = f;
		this.numFiles = fileList.size();		
	}
	
	private void init() {
		
		setSize(600,600);
		setLocation((screenSize.width - getSize().width) / 2,(screenSize.height - getSize().height) / 2);

		this.numStep = 0;
		this.mmProc = new MergingMapsProcessor();
		
		JPanel p = new JPanel(new GridBagLayout());
		int y = 1;
		
		label1 = new JLabel("Merging step x/N");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = y;
		c.gridwidth = 3;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
		p.add(label1, c);
		
		y++;
		
		buttonDisplay = new JButton("Display");
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = y;
		c.gridwidth = 3;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10,10,10,10);
		buttonDisplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displaySpecies();
			}
		});

		p.add(buttonDisplay, c);
		
		y++;
		
		tableSpecies = new JTable();
		tableSpecies.setModel(new DefaultTableModel());
		JScrollPane scrollPane = new JScrollPane(tableSpecies);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = y;
		c.gridwidth = 3;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
		scrollPane.setPreferredSize(new Dimension(580, 180));
		p.add(scrollPane, c);
		
		y++;


		buttonMerge = new JButton("Merge");
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = y;
		c.gridwidth = 3;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.WEST;
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
		buttonMerge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mergeMaps();
			}
		});
		buttonMerge.setEnabled(false);

		p.add(buttonMerge, c);

		
		
		// Ok - Cancel buttons
		JPanel buttonPanel = new JPanel();
//		JButton okB = new JButton("Ok");
//		okB.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// do something !
//			}
//		});
//		buttonPanel.add(okB);

		JButton cancelB = new JButton("Cancel");
		cancelB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		buttonPanel.add(cancelB);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p,BorderLayout.CENTER);
		getContentPane().add(buttonPanel,BorderLayout.SOUTH);
		pack();
	}
	
	private void setTableData(Vector<String> data) {
		int nbRows = data.size();
		String[] colNames = {"1", "2", "3", "4", "5", "6", "7", "8"};
		String[][] tableData = new String[nbRows][4];
		for (int i=0;i<data.size();i++) {
			String[] tk = data.get(i).split("\\t");
			tableData[i] = tk;
		}
		DefaultTableModel tm = (DefaultTableModel) tableSpecies.getModel();
		tm.setDataVector(tableData, colNames);
	}
	
	private void displaySpecies() {
		
		this.numStep++;
		
		if (numStep < numFiles) {
			if (numStep == 1) {
				// first two files
				this.mmProc.setAndLoadFileName1(fileList.get(0).getAbsolutePath());
				this.mmProc.setAndLoadFileName2(fileList.get(1).getAbsolutePath());
				this.mmProc.setMergeLists();
				// set JTable here with data
			}
			else {
				// the rest of the world!
				this.mmProc.setAndLoadFileName2(fileList.get(numStep).getAbsolutePath());
				this.mmProc.setMergeLists();
				// set JTable here with data
			}
			this.buttonDisplay.setEnabled(false);
			this.buttonMerge.setEnabled(true);
		}
		
	}
	
	private void mergeMaps() {
		this.buttonDisplay.setEnabled(true);
		this.buttonMerge.setEnabled(false);
//		for (File f : this.fileList)
//			System.out.println(">>>"+f.getAbsolutePath());
		
//		this.numStep++;
		
//		this.label1.setText(Integer.toString(numStep));
//		if (this.numStep > 10)
//			this.buttonNext.setEnabled(false);
		
//		if (numStep < numFiles) {
//			
//			if (numStep == 1) {
//				// first two files
//				this.mmProc.setAndLoadFileName1(fileList.get(0).getAbsolutePath());
//				this.mmProc.setAndLoadFileName2(fileList.get(1).getAbsolutePath());
//				this.mmProc.mergeTwoMaps();
//			}
//			else {
//				// the rest of the world!
//				this.mmProc.setAndLoadFileName2(fileList.get(numStep).getAbsolutePath());
//				this.mmProc.mergeTwoMaps();
//			}
//			
//			
//		}
		
	}
}
