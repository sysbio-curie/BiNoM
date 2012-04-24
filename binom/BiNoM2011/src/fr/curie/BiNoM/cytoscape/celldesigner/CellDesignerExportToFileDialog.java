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
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
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


public class CellDesignerExportToFileDialog extends JFrame {
	
	public class CellDesignerExportToFileOptions{
	    public boolean changeSpeciesCoordinates = false;
	    public boolean putCytoscapeColorsOnSpecies = false;
	    public boolean makeReactionsGrey = false;
	    public boolean mergeIfFileExists = false;
	    public boolean removeLineBreakPoints = false;
	    public boolean removeComplexNames = false;
	    public boolean removeResiduesNames = false;
	    public boolean insertHypotheticalInfluences_complexMutualInhibition = false;
	    public boolean insertHypotheticalInfluences_inhCatalysisReactant = false;
	    public float scaleFactor = 1f;
	    public int SCALING_POSITION = 1;
	    public int SCALING_SHAPE = 2;
	    public int typeOfScaling = SCALING_SHAPE;
	}
	
    private JCheckBox cbchangeSpeciesCoordinates;
    private JCheckBox cbPutCytoscapeColorsOnSpecies;
    private JCheckBox cbMakeReactionsGrey;
    private JCheckBox cbmergeIfFileExists;
    private JCheckBox cbremoveLineBreakPoints;
    private JCheckBox cbinsertHypotheticalInfluences_complexMutualInhibition;
    private JCheckBox cbinsertHypotheticalInfluences_inhCatalysisReactant;
    
    private JCheckBox cbremoveComplexNames;
    private JCheckBox cbremoveResiduesNames;
	private JTextField tfscaleFactor;
	private JRadioButton rbScalingPosition;
	private JRadioButton rbScalingShape;	
	private ButtonGroup bgtypeOfScaling;


    private JButton okB, cancelB;
    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);
    
    private File cdfile = null;

    private static final double COEF_X = 4, COEF_Y = 1.10;

    private CellDesignerExportToFileDialog() {
	super("Export to CellDesigner file options");
	final JPanel panel = new JPanel(new GridBagLayout());
	GridBagConstraints c;
	int y = 0;
	int x = 0;

	JLabel title = new JLabel("Export to CellDesigner file options");
	title.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y++;
	c.gridwidth = 4;
	c.weightx = 0.0;
	c.ipady = 40;	
	c.gridwidth = 4;
	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.NONE;
	panel.add(title, c);
	y++;
	
	x = 0;
	JLabel lab1 = new JLabel("Merge files if already exists");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab1,c);
	
	cbmergeIfFileExists = new JCheckBox();
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y++;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(cbmergeIfFileExists,c);
	
	x = 0;
	lab1 = new JLabel("Change the species coordinates");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab1,c);

	cbchangeSpeciesCoordinates = new JCheckBox();
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y++;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(cbchangeSpeciesCoordinates,c);
	
	x = 0;
	lab1 = new JLabel("Assign the species colors if modified");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab1,c);
	
	cbPutCytoscapeColorsOnSpecies = new JCheckBox();
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y++;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(cbPutCytoscapeColorsOnSpecies,c);
	

	x = 0;
	lab1 = new JLabel("Make reactions black/grey");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab1,c);
	
	cbMakeReactionsGrey = new JCheckBox();
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y++;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(cbMakeReactionsGrey,c);

	
	x=0;
	lab1 = new JLabel("Remove linebreak points");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab1,c);
	
	cbremoveLineBreakPoints = new JCheckBox();
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y++;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(cbremoveLineBreakPoints,c);
	
	x=0;
	lab1 = new JLabel("Remove complex names");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab1,c);
	
	cbremoveComplexNames = new JCheckBox();
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y++;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(cbremoveComplexNames,c);
	
	x=0;
	lab1 = new JLabel("Remove residue names");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab1,c);
	
	cbremoveResiduesNames = new JCheckBox();
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y++;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(cbremoveResiduesNames,c);

	x=0;
	lab1 = new JLabel("Scale by relative positions");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab1,c);

	rbScalingPosition = new JRadioButton();
	rbScalingPosition.setSelected(true);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y++;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(rbScalingPosition,c);
	
	x=0;
	lab1 = new JLabel("Scale only shapes");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab1,c);
	
	rbScalingShape = new JRadioButton();
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y++;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(rbScalingShape,c);
	
	bgtypeOfScaling =new ButtonGroup();
	bgtypeOfScaling.add(rbScalingPosition);
	bgtypeOfScaling.add(rbScalingShape);

	x=0;
	lab1 = new JLabel("Scaling factors");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab1,c);
	
	tfscaleFactor = new JTextField(5);
	tfscaleFactor.setText("1.0");
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y++;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(tfscaleFactor,c);
	
	x=0;
	lab1 = new JLabel("Add mutual inhibition in complexes");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab1,c);
	
	cbinsertHypotheticalInfluences_complexMutualInhibition = new JCheckBox();
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y++;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(cbinsertHypotheticalInfluences_complexMutualInhibition,c);
	
	x=0;
	lab1 = new JLabel("Add influence of catalyst on reactant");
	c = new GridBagConstraints();
	c.gridx = x++;
	c.gridy = y;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(lab1,c);
	
	cbinsertHypotheticalInfluences_inhCatalysisReactant = new JCheckBox();
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y++;
	c.ipady = 10;	
	c.weightx = 0.0;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(cbinsertHypotheticalInfluences_inhCatalysisReactant,c);
	
	
	JPanel buttonPanel = new JPanel();

	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            setVisible(false);			
            CellDesignerExportToFileOptions options = new CellDesignerExportToFileOptions();
            options.putCytoscapeColorsOnSpecies = cbPutCytoscapeColorsOnSpecies.isSelected();
            options.makeReactionsGrey = cbMakeReactionsGrey.isSelected();
            options.changeSpeciesCoordinates = cbchangeSpeciesCoordinates.isSelected();
            options.mergeIfFileExists = cbmergeIfFileExists.isSelected();
            options.removeComplexNames = cbremoveComplexNames.isSelected();
            options.removeLineBreakPoints = cbremoveLineBreakPoints.isSelected();
            options.removeResiduesNames = cbremoveResiduesNames.isSelected();
            try{
            	options.scaleFactor = Float.parseFloat(tfscaleFactor.getText());
            }catch(Exception ee){
            	options.scaleFactor = 1f;
            }
            if(rbScalingPosition.isSelected())
            	options.typeOfScaling = options.SCALING_POSITION;
            if(rbScalingShape.isSelected())
            	options.typeOfScaling = options.SCALING_SHAPE;
            options.insertHypotheticalInfluences_complexMutualInhibition = cbinsertHypotheticalInfluences_complexMutualInhibition.isSelected();
            options.insertHypotheticalInfluences_inhCatalysisReactant = cbinsertHypotheticalInfluences_inhCatalysisReactant.isSelected();
            
			CellDesignerExportTask task = new CellDesignerExportTask(cdfile,options);
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

    public static CellDesignerExportToFileDialog instance;

    public static CellDesignerExportToFileDialog getInstance() {
	if (instance == null)
	    instance = new CellDesignerExportToFileDialog();
	return instance;
    }

    public void raise(File _cdfile) {
    
    cdfile=_cdfile;

	Dimension size = getSize();
	setSize(new Dimension(300, 550));

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
     }
}

