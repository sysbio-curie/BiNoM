package fr.curie.BiNoM.pathways.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MergingMapsDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	JFileChooser fc;
	JTextArea fileName1;
	JTextArea fileName2;
	JList mergeListProtein;
	JList mergeListSpecies;
	JTable tableProtein;
	
	/**
	 * .xml file filter for the JFileChooser box
	 */
	class MyFilter extends javax.swing.filechooser.FileFilter {
	    public boolean accept(File file) {
	        String filename = file.getName();
	        return filename.endsWith(".xml");
	    }
	    public String getDescription() {
	        return "*.xml";
	    }
	}

	public MergingMapsDialog(JFrame frame, String mess, boolean modal){
		// call JDialog constructor
		super(frame, mess, modal);
		init();
	}
	
	private void init() {
		
		setSize(500,300);
		setLocation((screenSize.width - getSize().width) / 2,(screenSize.height - getSize().height) / 2);
		
		// Swing file chooser object
		fc = new JFileChooser();
		//fc.addChoosableFileFilter(new MyFilter());

		JPanel p = new JPanel(new GridBagLayout());
		
		JButton b1 = new JButton("Select file 1");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		p.add(b1,c);
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ret = fc.showDialog(MergingMapsDialog.this, "Select");
				if (ret == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					fileName1.setText(f.getAbsolutePath());
				}
			}
		});

		JButton b2 = new JButton("Select file 2");
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		p.add(b2,c);
		
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ret = fc.showDialog(MergingMapsDialog.this, "Select");
				if (ret == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					fileName2.setText(f.getAbsolutePath());
				}
			}
		});

		// file 1 name display
		fileName1 = new JTextArea(1, 30);
		fileName1.setEditable(false);
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 1;
		p.add(fileName1,c);

		//file 2 name display
		fileName2 = new JTextArea(1, 30);
		fileName2.setEditable(false);
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 2;
		p.add(fileName2,c);
		
		JButton mergeB = new JButton("Get candidate merging list");
		mergeB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// do something !
				MergingMapsProcessor mpc = new MergingMapsProcessor();
				mpc.fileName1 = fileName1.getText();
				mpc.fileName2 = fileName2.getText();
				mpc.setCandidateMergingLists();
				for (int i =0;i<mpc.speciesMap.size();i++)
					System.out.println(mpc.speciesMap.get(i));
				//setMergeListData(mpc.proteinMap);
				setTableProteinData(mpc.speciesMap);
			}
		});
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		p.add(mergeB,c);
		
		/*
		 * candidate merging list
		 */
//		mergeListProtein = new JList();
//		//mergeList.setBackground(new Color(0xeeeeee));
//		JScrollPane scrollPane = new JScrollPane(mergeListProtein);
//		c = new GridBagConstraints();
//		c.gridx = 1;
//		c.gridy = 4;
//		c.gridwidth = 3;
//		c.weightx = 0.5;
//		c.anchor = GridBagConstraints.LINE_START;
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.insets = new Insets(0,0,10,10);
//		scrollPane.setPreferredSize(new Dimension(210, 180));
//		p.add(scrollPane, c);
		
		
		tableProtein = new JTable();
		tableProtein.setModel(new DefaultTableModel());
		JScrollPane scrollPane = new JScrollPane(tableProtein);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 3;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,0,10,10);
		scrollPane.setPreferredSize(new Dimension(210, 180));
		p.add(scrollPane, c);
		
		
		
		// Ok - Cancel buttons
		JPanel buttonPanel = new JPanel();
		JButton okB = new JButton("Ok");
		okB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// do something !
				MergingMapsProcessor mpc = new MergingMapsProcessor();
				mpc.fileName1 = fileName1.getText();
				mpc.fileName2 = fileName2.getText();
				mpc.mergeMaps();
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
		getContentPane().add(p,BorderLayout.CENTER);
		getContentPane().add(buttonPanel,BorderLayout.SOUTH);
		pack();
	}
	
	private void setTableProteinData(Vector<String> data) {
		int nbRows = data.size();
		String[] colNames = {"1", "2", "3", "4", "5", "6", "7", "8"};
		String[][] tableData = new String[nbRows][4];
		for (int i=0;i<data.size();i++) {
			String[] tk = data.get(i).split("\\t");
			tableData[i] = tk;
		}
		DefaultTableModel tm = (DefaultTableModel) tableProtein.getModel();
		tm.setDataVector(tableData, colNames);
		
	}
}
