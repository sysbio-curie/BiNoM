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
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class MergingMapsDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private JTable tableSpecies;
	private JLabel label1;
	
	private JButton buttonDisplay;
	private JButton buttonMerge;
	private JButton buttonSave;
	
	private JFileChooser fc;
	
	private ArrayList<File> fileList;
	
	private MergingMapsProcessor mmProc;
	
	private int numFiles;
	private int numStep;
	
	private class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"Alias", "Species", "Compartment", "Name", "Alias", "Species", "Compartment", "Name", "Select"};
        
        private Object[][] data = {{"","","","","","","","",new Boolean(true)}};
        
        public int getColumnCount() {
            return columnNames.length;
        }
 
        public int getRowCount() {
            return data.length;
        }
 
        public String getColumnName(int col) {
            return columnNames[col];
        }
 
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
 
        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
 
        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 8) {
                return false;
            } else {
                return true;
            }
        }
 
        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }

		public void setDataVector(Object[][] tableData, String[] colNames) {
			this.data = tableData;
			this.columnNames = colNames;
		}
    }
 
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
		
		fc = new JFileChooser();
		
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
		//tableSpecies.setModel(new DefaultTableModel());
		tableSpecies.setModel(new MyTableModel());
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

		y++;
		
		buttonSave = new JButton("Save");
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = y;
		c.gridwidth = 3;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.WEST;
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ret = fc.showDialog(MergingMapsDialog.this, "Save the results");
				if (ret == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					saveData(f.getAbsolutePath());
				}
			}
		});
		buttonSave.setEnabled(false);

		p.add(buttonSave, c);
		
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
		
//		int nbRows = data.size();
//		String[] colNames = {"Alias", "Species", "Compartment", "Name", "Alias", "Species", "Compartment", "Name", "Select"};
//		String[][] tableData = new String[nbRows][4];
//		for (int i=0;i<data.size();i++) {
//			String[] tk = data.get(i).split("\\t");
//			tableData[i] = tk;
//		}
//		DefaultTableModel tm = (DefaultTableModel) tableSpecies.getModel();
//		tm.setDataVector(tableData, colNames);
		
		int nbRows = data.size();
		String[] colNames = {"Alias", "Species", "Compartment", "Name", "Alias", "Species", "Compartment", "Name", "Select"};
		Object[][] tableData = new Object[nbRows][colNames.length];
		for (int i=0;i<data.size();i++) {
			String[] row = data.get(i).split("\\t");
			for (int j=0;j<row.length;j++) {
				tableData[i][j] = row[j];
			}
			tableData[i][8] = new Boolean(true);
		}
		//DefaultTableModel tm = (DefaultTableModel) tableSpecies.getModel();
		MyTableModel tm = (MyTableModel) tableSpecies.getModel();
		tm.setDataVector(tableData, colNames);
		tm.fireTableDataChanged();
	}
	
	private void displaySpecies() {
		
		this.numStep++;
		
		if (numStep < numFiles) {
			if (numStep == 1) {
				System.out.println("merging first two files");
				// first two files
				System.out.println("set file names");
				this.mmProc.setAndLoadFileName1(fileList.get(0).getAbsolutePath());
				this.mmProc.setAndLoadFileName2(fileList.get(1).getAbsolutePath());
				System.out.println("set merge lists");
				this.mmProc.setMergeLists();
				this.setTableData(this.mmProc.getSpeciesMap());
			}
			else {
				// the rest of the world!
				this.mmProc.setAndLoadFileName2(fileList.get(numStep).getAbsolutePath());
				this.mmProc.setMergeLists();
				this.setTableData(this.mmProc.getSpeciesMap());
			}
			this.buttonDisplay.setEnabled(false);
			this.buttonMerge.setEnabled(true);
		}
	}
	
	private void mergeMaps() {
		
		this.buttonDisplay.setEnabled(true);
		this.buttonMerge.setEnabled(false);
		this.updateSpeciesMap();
		this.mmProc.mergeTwoMaps();
		
		System.out.println("numSteps: "+numStep);
		System.out.println("numFiles: "+numFiles);
		
		if (this.numStep == numFiles-1) {
			// we have merged all the files, save result and close
			//this.mmProc.saveCd1File("/bioinfo/users/ebonnet/test.xml");
			//this.setVisible(false);
			this.buttonMerge.setEnabled(false);
			this.buttonSave.setEnabled(true);
		}
	}
	
	/**
	 * Save the merged data as a CellDesigner XML file.
	 * @param fileName
	 */
	private void saveData(String fileName) {
		this.mmProc.saveCd1File(fileName);
		this.setVisible(false);
	}
	/**
	 * Replace the speciesMap vector of the processor with the data from the JTable, updated by the user.
	 */
	private void updateSpeciesMap() {
		
		Vector<String> data = new Vector<String>();
		
		// get data from Jtable
		int nbCol = this.tableSpecies.getModel().getColumnCount();
		int nbRow = this.tableSpecies.getModel().getRowCount();
		
		for (int i=0;i<nbRow;i++) {
			boolean b = (Boolean) this.tableSpecies.getModel().getValueAt(i, 8);
			if (b) {
				String str = "";
				for (int j=0;j<nbCol-1;j++) {
					str += (String) this.tableSpecies.getModel().getValueAt(i, j) + "\t";
				}
				str = str.trim();
				data.add(str);
			}
		}
		
		this.mmProc.setSpeciesMap(data);
	}
}
