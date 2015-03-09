/*
   BiNoM Cytoscape Plugin
   Copyright (C) 2006-2007 Curie Institute, 26 rue d'Ulm, 75005 Paris - FRANCE

   BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
*/

/*
  BiNoM authors:
	Andrei Zinovyev : http://www.ihes.fr/~zinovyev
	Eric Viara : http://www.sysra.com/viara
	Laurence Calzone :	http://leibniz.biol.vt.edu/people/laurence/laurence.html
*/
package fr.curie.BiNoM.cytoscape.celldesigner;


import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

import org.cytoscape.work.TaskIterator;

import Main.Launcher;
import fr.curie.BiNoM.cytoscape.analysis.ConnectedComponentsTask;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.io.File;

public class modifyCellDesignerNotesDialog extends JFrame {

    private JButton okB, cancelB;
    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);
    
    private JTextField accNumField = null;
    private JButton browseAccNum = null;
    private JTextField indexField = null;
    private JButton browseIndex = null;
    

    private static final double COEF_X = 4, COEF_Y = 1.10;

    private modifyCellDesignerNotesDialog() {
	super("Modify CellDesigner notes");
	final JPanel panel = new JPanel(new GridBagLayout());
	GridBagConstraints c;
	int y = 0;
	int x = 0;

	JLabel title = new JLabel("Select CellDesigner File");
	title.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.gridwidth = 4;
	c.weightx = 0.0;
	c.ipady = 40;	
	c.gridwidth = 4;
	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.NONE;
	panel.add(title, c);
	y++;

	JLabel lab1 = new JLabel("  CellDesigner File  ");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab1,c);

	indexField = new JTextField(30);
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(indexField, c);

	JPanel padPanel = new JPanel();
	c = new GridBagConstraints();
	c.ipadx = 5;
	c.gridx = x++;
	c.gridy = y;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(padPanel, c);

	browseIndex = new JButton("Browse...");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.EAST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(browseIndex,c);

	browseIndex.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			File file = null;
			JFileChooser fileChooser = new JFileChooser();
			
			fileChooser.setFileFilter(new XMLFileFilter());
		
			fileChooser.setDialogTitle("Load CellDesigner File");
		
			JFrame frame = new JFrame();
			int returnVal = fileChooser.showOpenDialog(frame);
		
			if (returnVal == JFileChooser.APPROVE_OPTION) {
			    file = fileChooser.getSelectedFile();
			}
			
			
//		    CyFileFilter indexFilter = new CyFileFilter();
//
//		    indexFilter.addExtension("xml");
//		    indexFilter.setDescription("XML files");
//
//		    File file = FileUtil.getFile
//			("Load CellDesigner File", FileUtil.LOAD, new CyFileFilter[]{indexFilter});
//		    toFront();
		    if (file != null) {
			String fn = file.getAbsolutePath();
			indexField.setText(fn);
		    }

		    /*
		    final JFileChooser fc = new JFileChooser();
		    XGMMLExtensionFilter xgmmlfilter = new XGMMLExtensionFilter();
		    fc.setFileFilter(xgmmlfilter);
		    int returnVal = fc.showOpenDialog(panel);
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String fn = file.getAbsolutePath();
			indexField.setText(fn);
		    }			        
		    */
                }
           });

	
	y++;

	JLabel lab2 = new JLabel("  CellDesigner notes file  ");
	x = 0;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(lab2,c);

	/*lab2 = new JLabel("(optional) ");
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = 5;
	c.anchor = GridBagConstraints.CENTER;
	panel.add(lab2,c);*/
	
	accNumField = new JTextField(30);
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(accNumField,c);

	x++;
	browseAccNum = new JButton("Browse...");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.EAST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(browseAccNum,c);

	padPanel = new JPanel();
	c = new GridBagConstraints();
	c.ipadx = 5;
	c.gridx = x++;
	c.gridy = y;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(padPanel, c);

	browseAccNum.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			
			File file = null;
			JFileChooser fileChooser = new JFileChooser();
					
			fileChooser.setDialogTitle("Load Feature Table File");
		
			JFrame frame = new JFrame();
			int returnVal = fileChooser.showOpenDialog(frame);
		
			if (returnVal == JFileChooser.APPROVE_OPTION) {
			    file = fileChooser.getSelectedFile();
			}
			
			
//		    File file = FileUtil.getFile
//			("Load Feature Table File", FileUtil.LOAD);
//
//		    toFront();
		    if (file != null) {
			String fn = file.getAbsolutePath();
			accNumField.setText(fn);
		    }

		    /*
		    final JFileChooser fc = new JFileChooser();
		    fc.setName(accNumField.getText());
		    int returnVal = fc.showOpenDialog(panel);
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String fn = file.getAbsolutePath();
			accNumField.setText(fn);
		    }			        
		    */
                }
           });
	
	JPanel buttonPanel = new JPanel();

	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            setVisible(false);			
            
            TaskIterator t = new TaskIterator(new modifyCellDesignerNotesTask(indexField.getText(),accNumField.getText()));
    		Launcher.getAdapter().getTaskManager().execute(t);
                }
           });

	cancelB = new JButton("Cancel");

	cancelB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                }
           });

	buttonPanel.add(okB);
	buttonPanel.add(cancelB);

	panel.setPreferredSize(new Dimension(100, 100));
	getContentPane().setLayout(new BorderLayout());
	JScrollPane jpane = new JScrollPane(panel);
	getContentPane().add(jpane, BorderLayout.CENTER);
	getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	pack();
    }

    public static modifyCellDesignerNotesDialog instance;

    public static modifyCellDesignerNotesDialog getInstance() {
	if (instance == null)
	    instance = new modifyCellDesignerNotesDialog();
	return instance;
    }

    public void raise() {

	Dimension size = getSize();
	setSize(new Dimension(550, 200));

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
     }
}

