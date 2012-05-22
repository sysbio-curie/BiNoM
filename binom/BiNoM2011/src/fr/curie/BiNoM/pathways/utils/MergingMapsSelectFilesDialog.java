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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class MergingMapsSelectFilesDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private JFileChooser fc;
	private JTextArea fileName1;
	private JList mergeFileList;

	/**
	 * List of selected files to be merged
	 */
	public ArrayList<File> fileList;
	
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

	/**
	 * Constructor
	 * 
	 * @param frame a JFrame
	 * @param mess message 
	 * @param modal is the window modal?
	 */
	public MergingMapsSelectFilesDialog(JFrame frame, String mess, boolean modal){
		// call JDialog constructor
		super(frame, mess, modal);
		/*
		 * now the real stuff
		 */
		init();
	}
	
	private void init() {
		
		setSize(500,300);
		setLocation((screenSize.width - getSize().width) / 2,(screenSize.height - getSize().height) / 2);
		
		fileList = new ArrayList<File>();
		
		/*
		 *  Create a swing file chooser object
		 */
		fc = new JFileChooser();
		//fc.addChoosableFileFilter(new MyFilter());

		JPanel p = new JPanel(new GridBagLayout());
		
		JLabel label1 = new JLabel("Select the files to be merged.");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10,10,10,10);
		p.add(label1,c);
		
		
		/*
		 * select file button
		 */
		JButton b1 = new JButton("Add file");
		//JButton b2 = new JButton("Remove file");
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10,10,10,10);
		p.add(b1,c);
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ret = fc.showDialog(MergingMapsSelectFilesDialog.this, "Select");
				if (ret == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					addMyFile(f);
					//fileName1.setText(f.getAbsolutePath());
				}
			}
		});

		/*
		 * remove files button
		 */
		JButton b2 = new JButton("Remove file");
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10,10,10,10);
		p.add(b2,c);
		
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeMyFile();
			}
		});

		
		/*
		 * candidate merging list
		 */
		mergeFileList = new JList();
		//mergeList.setBackground(new Color(0xeeeeee));
		JScrollPane scrollPane = new JScrollPane(mergeFileList);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 3;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
		scrollPane.setPreferredSize(new Dimension(210, 180));
		p.add(scrollPane, c);


		
		/*
		 *  Action: Ok - Cancel buttons
		 */
		JPanel buttonPanel = new JPanel();
		JButton okB = new JButton("Ok");
		okB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// do something !
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
	
	
	private void addMyFile(File f) {
		if (!fileList.contains(f)){
			fileList.add(f);
			Vector data = new Vector();
			for (File file : fileList)
				data.add(file.getName());
			mergeFileList.setListData(data);
		}
	}
	
	private void removeMyFile() {
		
		Object obj[] = mergeFileList.getSelectedValues();
		ArrayList<File> rem = new ArrayList<File>();
		for (int i=0;i<obj.length;i++) {
			String name = (String) obj[i];
			for (File f : fileList) {
				if (f.getName().equals(name))
					rem.add(f);
			}
		}
		
		for (File f : rem)
			fileList.remove(f);
		
		Vector data = new Vector();
		for (File f : fileList)
			data.add(f.getName());
		mergeFileList.setListData(data);
	}
	
}
