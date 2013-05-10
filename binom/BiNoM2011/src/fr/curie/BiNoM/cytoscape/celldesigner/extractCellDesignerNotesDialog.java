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

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.io.InputStream;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.cytoscape.biopax.BioPAXImportTask;
import fr.curie.BiNoM.cytoscape.biopax.propedit.*;

import java.io.File;
import java.net.URL;
import java.util.*;

import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import fr.curie.BiNoM.cytoscape.netwop.*;
import fr.curie.BiNoM.cytoscape.lib.*;

import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.CyEdge;

import cytoscape.util.CyFileFilter;
import cytoscape.util.FileUtil;

public class extractCellDesignerNotesDialog extends JFrame {

    private JButton okB, cancelB;
    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);
    
    //private JTextField accNumField = null;
    //private JButton browseAccNum = null;
    private JTextField indexField = null;
    private JButton browseIndex = null;
    
    private JCheckBox formatAnnotations = null;
    private JCheckBox allAnnotations = null;    
    private JCheckBox guessIdentifiers = null;    
    private JCheckBox removeEmptySections = null;  
    private JCheckBox removeInvalidTags = null;
    private JCheckBox moveNonannotatedTextToReferenceSection = null;
    private JCheckBox useHUGOIdsForModuleIdentification = null;
    private JCheckBox insertMapsTagBeforeModules = null;
    
    private JTextField gmtFileField = null;
    private JButton browseGMTIndex = null;    
    

    private static final double COEF_X = 4, COEF_Y = 1.10;

    public extractCellDesignerNotesDialog() {
	super("Extract CellDesigner notes");
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
	c.gridx = x;
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
		    CyFileFilter indexFilter = new CyFileFilter();

		    indexFilter.addExtension("xml");
		    indexFilter.setDescription("XML files");

		    File file = FileUtil.getFile
			("Load CellDesigner File", FileUtil.LOAD, new CyFileFilter[]{indexFilter});
		    toFront();
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
	
    formatAnnotations = new JCheckBox("Analyze annotations");
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y++;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(formatAnnotations, c);

	formatAnnotations.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
        
			if(formatAnnotations.isSelected()){
				allAnnotations.setEnabled(true);
				guessIdentifiers.setEnabled(true);
				removeEmptySections.setEnabled(true);
				removeInvalidTags.setEnabled(true);
				moveNonannotatedTextToReferenceSection.setEnabled(true);
				gmtFileField.setEnabled(true);
				useHUGOIdsForModuleIdentification.setEnabled(true);
				browseGMTIndex.setEnabled(true);
				insertMapsTagBeforeModules.setEnabled(true);
			}else{
				allAnnotations.setEnabled(false);
				guessIdentifiers.setEnabled(false);
				removeEmptySections.setEnabled(false);
				removeInvalidTags.setEnabled(false);
				moveNonannotatedTextToReferenceSection.setEnabled(false);
				gmtFileField.setEnabled(false);
				useHUGOIdsForModuleIdentification.setEnabled(false);
				browseGMTIndex.setEnabled(false);
				insertMapsTagBeforeModules.setEnabled(false);
			}
		
		}
           });
	
	
    allAnnotations = new JCheckBox("Process empty annotations");    
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y++;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(allAnnotations, c);

    guessIdentifiers = new JCheckBox("Use Internet to fill Identifiers");    
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y++;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(guessIdentifiers, c);

    removeEmptySections = new JCheckBox("Remove empty sections");    
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y++;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(removeEmptySections, c);

	removeInvalidTags = new JCheckBox("Remove empty tags");    
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y++;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(removeInvalidTags, c);

	moveNonannotatedTextToReferenceSection = new JCheckBox("Move non-annotated text to References");    
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y++;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(moveNonannotatedTextToReferenceSection, c);
	
	lab1 = new JLabel(" GMT file for modules:   ");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab1,c);
	
	gmtFileField = new JTextField(30);
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(gmtFileField, c);
	

	browseGMTIndex = new JButton("Browse...");
	c = new GridBagConstraints();
	c.gridx = 2;
	c.gridy = y++;
	c.anchor = GridBagConstraints.EAST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(browseGMTIndex,c);

	browseGMTIndex.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    CyFileFilter gmtFileFieldFilter = new CyFileFilter();

		    gmtFileFieldFilter.addExtension("gmt");
		    gmtFileFieldFilter.setDescription("GMT files");

		    File file = FileUtil.getFile
			("Load GMT File", FileUtil.LOAD, new CyFileFilter[]{gmtFileFieldFilter});
		    toFront();
		    if (file != null) {
			String fn = file.getAbsolutePath();
			gmtFileField.setText(fn);
		    }
                }
           });
	
	useHUGOIdsForModuleIdentification = new JCheckBox("use HUGO for module annotation");    
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y++;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(useHUGOIdsForModuleIdentification, c);

	insertMapsTagBeforeModules = new JCheckBox("insert MAP: tag before MODULE:");    
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y++;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(insertMapsTagBeforeModules, c);
	
	
	extractCellDesignerNotesTask task = new extractCellDesignerNotesTask(null,null);
	final extractCellDesignerNotesTask.extractingNotesOptions options = task.new extractingNotesOptions();
	
	formatAnnotations.setSelected(options.formatAnnotation);
	allAnnotations.setSelected(options.allannotations);
	guessIdentifiers.setSelected(options.guessIdentifiers);
	removeEmptySections.setSelected(options.removeEmptySections);
	removeInvalidTags.setSelected(options.removeInvalidTags);
	moveNonannotatedTextToReferenceSection.setSelected(options.moveNonannotatedTextToReferenceSection);
	useHUGOIdsForModuleIdentification.setSelected(options.useHUGOIdsForModuleIdentification);
	insertMapsTagBeforeModules.setSelected(options.insertMapsTagBeforeModules);
	
	JPanel buttonPanel = new JPanel();	

	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            //setVisible(false);			
            options.formatAnnotation = formatAnnotations.isSelected();
            options.allannotations = allAnnotations.isSelected();
            options.guessIdentifiers = guessIdentifiers.isSelected();
            options.removeEmptySections = removeEmptySections.isSelected();
            options.removeInvalidTags = removeInvalidTags.isSelected();
            options.moveNonannotatedTextToReferenceSection = moveNonannotatedTextToReferenceSection.isSelected();
            options.useHUGOIdsForModuleIdentification = useHUGOIdsForModuleIdentification.isSelected();
            options.insertMapsTagBeforeModules = insertMapsTagBeforeModules.isSelected();
            if(!gmtFileField.getText().trim().equals(""))
            	options.moduleGMTFileName = gmtFileField.getText();
			extractCellDesignerNotesTask task = new extractCellDesignerNotesTask(indexField.getText(),options);
  		    fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);
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

    public static extractCellDesignerNotesDialog instance;

    public static extractCellDesignerNotesDialog getInstance() {
	if (instance == null)
	    instance = new extractCellDesignerNotesDialog();
	return instance;
    }

    public void raise() {

	Dimension size = getSize();
	setSize(new Dimension(550, 400));

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
     }
}

