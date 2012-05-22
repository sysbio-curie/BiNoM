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
	private JTable tableProtein;
	private JLabel label1;
	private ArrayList<File> fileList;
	private MergingMapsProcessor mmProc;
	
	
	public MergingMapsDialog(JFrame frame, String mess, boolean modal){
		// call JDialog constructor
		super(frame, mess, modal);
		init();
	}
	
	public void setFileList(ArrayList<File> f) {
		this.fileList = f;
	}
	
	private void init() {
		
		setSize(600,600);
		setLocation((screenSize.width - getSize().width) / 2,(screenSize.height - getSize().height) / 2);
		fileList = new ArrayList<File>();
		mmProc = new MergingMapsProcessor();
		
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
		
		tableProtein = new JTable();
		tableProtein.setModel(new DefaultTableModel());
		JScrollPane scrollPane = new JScrollPane(tableProtein);
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
		
		JButton buttonNext = new JButton("Next >>");
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = y;
		c.gridwidth = 3;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.WEST;
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// do something!
			}
		});

		p.add(buttonNext, c);

		
		
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
