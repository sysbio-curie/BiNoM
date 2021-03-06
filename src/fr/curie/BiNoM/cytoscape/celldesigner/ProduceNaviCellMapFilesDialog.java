/*
c   BiNoM Cytoscape Plugin
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
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

import org.cytoscape.work.TaskIterator;

import Main.Launcher;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

import fr.curie.BiNoM.cytoscape.analysis.ConnectedComponentsTask;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;

import java.io.File;

public class ProduceNaviCellMapFilesDialog extends JFrame {

    private BioPAX biopax;
    private JButton okB, cancelB;
    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);
    
    private JTextField wordPressURL = null;
    private JTextField wordPressUser = null;
    private JTextField wordPressPassw = null;
    private JButton browseAccNum = null;
    private JTextField configFile = null;
    private JButton browseIndex = null;
    private JCheckBox produceBrowseOnlyVersion = null;
    
    private JTextField xrefFile = null;
    private JButton browseXref = null;
    private JCheckBox provideSourceFiles = null;

    private static final double COEF_X = 4, COEF_Y = 1.10;

    public ProduceNaviCellMapFilesDialog() {
	super("Color CellDesigner proteins");
	final JPanel panel = new JPanel(new GridBagLayout());
	GridBagConstraints c;
	int y = 0;
	int x = 0;

	JLabel title = new JLabel("NaviCell options:");
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

	JLabel lab1 = new JLabel("  Config File  ");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab1,c);

	configFile = new JTextField(30);
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(configFile, c);

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
					
			fileChooser.setDialogTitle("Load config file");
		
			int returnVal = fileChooser.showSaveDialog(Launcher.getCySwingAppAdapter().getCySwingApplication().getJFrame());
		
			if (returnVal == JFileChooser.APPROVE_OPTION) {
			    file = fileChooser.getSelectedFile();
			}
			
//		    CyFileFilter indexFilter = new CyFileFilter();
//
//		    File file = FileUtil.getFile
//			("Load config file", FileUtil.LOAD, new CyFileFilter[]{indexFilter});
//		    toFront();
		    if (file != null) {
			String fn = file.getAbsolutePath();
			configFile.setText(fn);
		    }
                }
           });

	y++;
	
	x = 0;
	JLabel lab3 = new JLabel("XREFs (link defs, optional)");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab3,c);

	xrefFile = new JTextField(30);
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(xrefFile, c);

	x++;
	browseXref = new JButton("Browse...");
	c = new GridBagConstraints();
	c.gridx = x++; 
	c.gridy = y;
	c.anchor = GridBagConstraints.EAST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(browseXref,c);

	browseXref.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			File file = null;
			JFileChooser fileChooser = new JFileChooser();
					
			fileChooser.setDialogTitle("Load xref file");
		
			JFrame frame = new JFrame();
			int returnVal = fileChooser.showOpenDialog(frame);
		
			if (returnVal == JFileChooser.APPROVE_OPTION) {
			    file = fileChooser.getSelectedFile();
			}
			
//		    CyFileFilter indexFilter = new CyFileFilter();
//
//		    File file = FileUtil.getFile
//			("Load xref file", FileUtil.LOAD, new CyFileFilter[]{indexFilter});
//		    toFront();
		    if (file != null) {
			String fn = file.getAbsolutePath();
			xrefFile.setText(fn);
		    }
                }
           });

	y++;
	
	provideSourceFiles = new JCheckBox();
	provideSourceFiles.setText("Provide NaviCell source files through interface");
	provideSourceFiles.setSelected(true);
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y++;
	c.anchor = GridBagConstraints.WEST;
	panel.add(provideSourceFiles, c);
	
	
	produceBrowseOnlyVersion = new JCheckBox();
	produceBrowseOnlyVersion.setText("Make browse-only version (no WordPress blog)");
	produceBrowseOnlyVersion.setSelected(true);
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y++;
	c.anchor = GridBagConstraints.WEST;
	panel.add(produceBrowseOnlyVersion, c);
	
	
	produceBrowseOnlyVersion.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!produceBrowseOnlyVersion.isSelected()) { wordPressURL.setEnabled(true); wordPressUser.setEnabled(true); wordPressPassw.setEnabled(true); }
			else { wordPressURL.setEnabled(false); wordPressUser.setEnabled(false); wordPressPassw.setEnabled(false); }
		}
	    });

	
	
	y++;

	JLabel lab2 = new JLabel("  WordPress server URL  ");
	x = 0;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(lab2,c);
	
	wordPressURL = new JTextField(30);
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	wordPressURL.setEnabled(false);
	panel.add(wordPressURL,c);

	y++;

	lab2 = new JLabel("  WordPress user name  ");
	x = 0;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(lab2,c);
	
	wordPressUser = new JTextField(30);
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	wordPressUser.setEnabled(false);
	panel.add(wordPressUser,c);

	y++;

	lab2 = new JLabel("  WordPress user password  ");
	x = 0;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(lab2,c);
	
	wordPressPassw = new JTextField(30);
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	wordPressPassw.setEnabled(false);
	panel.add(wordPressPassw,c);

	
	JPanel buttonPanel = new JPanel();

	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            setVisible(false);			
			boolean nv2 = true;
			boolean demo = false; // 2014-12-01: should we control this parameter with a button in the dialog ?+
			TaskIterator t = new TaskIterator(new ProduceNaviCellMapFilesTask(configFile.getText(),wordPressURL.getText(), wordPressUser.getText(), wordPressPassw.getText(), produceBrowseOnlyVersion.isSelected(), provideSourceFiles.isSelected(), xrefFile.getText(), nv2, demo));
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

    public static ProduceNaviCellMapFilesDialog instance;

    public static ProduceNaviCellMapFilesDialog getInstance() {
	if (instance == null)
	    instance = new ProduceNaviCellMapFilesDialog();
	return instance;
    }

    public void raise() {

	Dimension size = getSize();
	setSize(new Dimension(650, 300));

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
     }
}

