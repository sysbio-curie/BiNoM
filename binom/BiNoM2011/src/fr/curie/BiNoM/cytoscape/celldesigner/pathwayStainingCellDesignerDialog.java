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

public class pathwayStainingCellDesignerDialog extends JFrame {

    private BioPAX biopax;
    private JButton okB, cancelB;
    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);
    
    private JTextField datatableField = null;
    private JButton browseDatatable = null;
    private JTextField celldesignerField = null;
    private JButton browseCellDesigner = null;
    private JTextField pngField = null;
    private JButton browsePng = null;
    private JTextField proteinGroupField = null;
    private JButton browseProteinGroup = null;
    
    private JCheckBox normalizeToZValues = null;
    private JTextField influenceRadius = null;
    private JTextField thresholdForComputingGradient = null;
    private JTextField gridSizeX = null;
    private JTextField gridSizeY = null;
    
    private static final double COEF_X = 4, COEF_Y = 1.10;

    private pathwayStainingCellDesignerDialog() {
	super("Pathway staining dialog");
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
	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab1,c);

	celldesignerField = new JTextField(30);
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(celldesignerField, c);

	JPanel padPanel = new JPanel();
	c = new GridBagConstraints();
	c.ipadx = 5;
	c.gridx = x++;
	c.gridy = y;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(padPanel, c);

	browseCellDesigner = new JButton("Browse...");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.EAST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(browseCellDesigner,c);

	browseCellDesigner.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    CyFileFilter CellDesignerFilter = new CyFileFilter();

		    CellDesignerFilter.addExtension("xml");
		    CellDesignerFilter.setDescription("XML files");

		    File file = FileUtil.getFile
			("Load CellDesigner File", FileUtil.LOAD, new CyFileFilter[]{CellDesignerFilter});
		    toFront();
		    if (file != null) {
			String fn = file.getAbsolutePath();
			celldesignerField.setText(fn);
		    }

		    /*
		    final JFileChooser fc = new JFileChooser();
		    XGMMLExtensionFilter xgmmlfilter = new XGMMLExtensionFilter();
		    fc.setFileFilter(xgmmlfilter);
		    int returnVal = fc.showOpenDialog(panel);
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String fn = file.getAbsolutePath();
			celldesignerField.setText(fn);
		    }			        
		    */
                }
           });


	y++;

	JLabel lab2 = new JLabel("  PNG image file  ");
	x = 0;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(lab2,c);
	
	pngField = new JTextField(30);
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(pngField,c);

	x++;
	browsePng = new JButton("Browse...");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.EAST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(browsePng,c);

	browsePng.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    CyFileFilter CellDesignerFilter = new CyFileFilter();

		    CellDesignerFilter.addExtension("png");
		    CellDesignerFilter.setDescription("PNG files");

		    File file = FileUtil.getFile
			("Load PNG File", FileUtil.LOAD, new CyFileFilter[]{CellDesignerFilter});
		    toFront();
		    if (file != null) {
			String fn = file.getAbsolutePath();
			pngField.setText(fn);
		    }
                }
           });
	
	y++;

	lab2 = new JLabel("(if not empty then staining will be merged with this PNG)");
	x = 1;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(lab2,c);
	
	
	y++;

	lab2 = new JLabel("  Data Table File  ");
	x = 0;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(lab2,c);
	
	datatableField = new JTextField(30);
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(datatableField,c);

	x++;
	browseDatatable = new JButton("Browse...");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.EAST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(browseDatatable,c);

	padPanel = new JPanel();
	c = new GridBagConstraints();
	c.ipadx = 5;
	c.gridx = x++;
	c.gridy = y;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(padPanel, c);

	browseDatatable.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    File file = FileUtil.getFile
			("Load Data Table File", FileUtil.LOAD);

		    toFront();
		    if (file != null) {
			String fn = file.getAbsolutePath();
			datatableField.setText(fn);
		    }
                }
           });
	
	y++;

	lab2 = new JLabel("(if empty then predefined or random colors will be used)");
	x = 1;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(lab2,c);

	y++;

	lab2 = new JLabel("  Gene Set File (gmt)");
	x = 0;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(lab2,c);
	
	proteinGroupField = new JTextField(30);
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	panel.add(proteinGroupField,c);

	x++;
	browseProteinGroup = new JButton("Browse...");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.EAST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(browseProteinGroup,c);

	padPanel = new JPanel();
	c = new GridBagConstraints();
	c.ipadx = 5;
	c.gridx = x++;
	c.gridy = y;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(padPanel, c);

	browseProteinGroup.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    File file = FileUtil.getFile
			("Load Gene Set File", FileUtil.LOAD);

		    toFront();
		    if (file != null) {
			String fn = file.getAbsolutePath();
			proteinGroupField.setText(fn);
		    }
                }
           });	
	
	y++;

	lab2 = new JLabel("(if empty then all individual proteins will be used)");
	x = 1;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(lab2,c);
	
	pathwayStainingCellDesignerTask taskD = new pathwayStainingCellDesignerTask(null,null,null,null,null);
	final pathwayStainingCellDesignerTask.stainingOptions options = taskD.new stainingOptions();
	
	y++;
	normalizeToZValues = new JCheckBox("Normalize Table Columns");
	x = 0;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(normalizeToZValues,c);
	normalizeToZValues.setSelected(options.normalizeToZValues);

	
	y++;

	lab2 = new JLabel("Color spot radius  ");
	x = 0;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.EAST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(lab2,c);
	
	
    influenceRadius = new JTextField(5);
    influenceRadius.setText(""+options.influenceRadius);
	x = 1;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(influenceRadius,c);	
	
	y++;

	lab2 = new JLabel("Threshold for gradients  ");
	x = 0;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.EAST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(lab2,c);
	
	
	thresholdForComputingGradient = new JTextField(5);
	thresholdForComputingGradient.setText(""+options.thresholdForComputingGradient);
	x = 1;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(thresholdForComputingGradient,c);	

	y++;

	lab2 = new JLabel("Grid step X  ");
	x = 0;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.EAST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(lab2,c);
	
	
    gridSizeX = new JTextField(5);
    gridSizeX.setText(""+options.gridSizeX);
	x = 1;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(gridSizeX,c);	
	
	y++;
	
	lab2 = new JLabel("Grid step Y  ");
	x = 0;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x++;
	c.gridy = y;
	c.anchor = GridBagConstraints.EAST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(lab2,c);
	
	
	gridSizeY = new JTextField(5);
	gridSizeY.setText(""+options.gridSizeY);
	x = 1;
	c = new GridBagConstraints();
	c.ipady = 20;	
	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	panel.add(gridSizeY,c);	
	
	
	JPanel buttonPanel = new JPanel();

	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            setVisible(false);	
            try{
            options.influenceRadius = Float.parseFloat(influenceRadius.getText());
            options.normalizeToZValues = normalizeToZValues.isSelected();
            options.thresholdForComputingGradient = Float.parseFloat(thresholdForComputingGradient.getText());
            options.gridSizeX = Float.parseFloat(gridSizeX.getText());
            options.gridSizeY = Float.parseFloat(gridSizeY.getText());
            }catch(Exception ee){
            	ee.printStackTrace();
            }
            pathwayStainingCellDesignerTask task = new pathwayStainingCellDesignerTask(celldesignerField.getText(),pngField.getText(),datatableField.getText(), proteinGroupField.getText(), options);
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

    public static pathwayStainingCellDesignerDialog instance;

    public static pathwayStainingCellDesignerDialog getInstance() {
	if (instance == null)
	    instance = new pathwayStainingCellDesignerDialog();
	return instance;
    }

    public void raise(BioPAX biopax) {
	this.biopax = biopax;

	Dimension size = getSize();
	setSize(new Dimension(650, 640));

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
     }
}

