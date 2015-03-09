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
package fr.curie.BiNoM.cytoscape.biopax.query;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.work.TaskIterator;

import Main.Launcher;

import java.awt.BorderLayout;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.wrappers.BioPAX;
import fr.curie.BiNoM.pathways.wrappers.XGMML;
import fr.curie.BiNoM.pathways.utils.*;
import fr.curie.BiNoM.lib.GraphicUtils;

import java.util.*;

import fr.curie.BiNoM.cytoscape.analysis.ConnectedComponentsTask;
import fr.curie.BiNoM.cytoscape.lib.GraphDocumentFactory;


public class BioPAXStandardQueryDialog extends JFrame {

    private BioPAX biopax;
    private JButton okB, cancelB;
    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    
    private JRadioButton outputCurrentNetworkRB = null;    
    private JRadioButton outputNewNetworkRB = null;    
    private JRadioButton allNodesRB = null;    
    private JRadioButton selectedNodesRB = null;
    
    private JCheckBox selectComplex = null;
    private JRadioButton complexExpand = null;
    private JRadioButton complexNoExpand = null;
    	
    private JCheckBox selectSpecies = null;
    
    private JCheckBox selectReactions = null;
    private JRadioButton reactionConnecting = null;
    private JRadioButton reactionAll = null;
    private JRadioButton reactionComplete = null;
    
    private JCheckBox selectPublications = null;    
    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);
    private static final java.awt.Font PLAIN_FONT = new java.awt.Font
	("times", java.awt.Font.PLAIN, 12);

    private static final double COEF_X = 1.24, COEF_Y = 1.10;

    private BioPAXStandardQueryDialog() {
        super("BioPAX Standard Query from the Index");

	JLabel label;
	JPanel panel = new JPanel(new GridBagLayout());
	GridBagConstraints c;
	int y = 0;
	int x = 0;

	for (int gx = 0; gx < 5; gx += 2) {
	    GraphicUtils.addVPadPanel(panel, gx, 1, 5);
	}

	x = 1;
	label = new JLabel("BioPAX Standard Query from the Index");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	c.ipady = 10;	
	c.gridwidth = 5;
	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.NONE;
	panel.add(label, c);
	y++;

	y = GraphicUtils.addHSepPanel(panel, 1, y, 3, 2);

	label = new JLabel("Input");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.gridheight = 2;
	c.weightx = 0.0;
	//c.ipady = 10;	
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(label, c);

	x += 2;

	ButtonGroup group = new ButtonGroup();
	allNodesRB = new JRadioButton("All nodes");
	allNodesRB.setFont(PLAIN_FONT);
	group.add(allNodesRB);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.ipady = 10;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(allNodesRB, c);
	y++;

	selectedNodesRB = new JRadioButton("Selected nodes");
	group.add(selectedNodesRB);
	selectedNodesRB.setFont(PLAIN_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	//c.ipady = 10;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(selectedNodesRB, c);
	y++;

	allNodesRB.setSelected(true);

	y = GraphicUtils.addHSepPanel(panel, 1, y, 3, 2);

	x = 1;
	selectComplex = new JCheckBox("Add complexes");
	selectComplex.setSelected(true);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	//c.ipady = 10;	
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(selectComplex, c);
	x += 2;

	group = new ButtonGroup();
	complexNoExpand = new JRadioButton("no expand");
	group.add(complexNoExpand);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	c.ipady = 2;	
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(complexNoExpand, c);
	y++;

	complexExpand = new JRadioButton("expand");
	group.add(complexExpand);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	c.ipady = 2;	
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(complexExpand, c);
	y++;

	complexNoExpand.setSelected(true);

	x = 1;
	selectSpecies = new JCheckBox("Add chemical species");
	selectSpecies.setSelected(true);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	c.ipady = 10;	
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(selectSpecies, c);
	y++;

	x = 1;
	selectReactions = new JCheckBox("Add reactions");
	selectReactions.setSelected(true);
	c = new GridBagConstraints();
	c.gridheight = 3;
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	c.ipady = 10;	
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(selectReactions, c);
    
	x += 2;
	group = new ButtonGroup();
	reactionConnecting = new JRadioButton("connecting reactions");
	group.add(reactionConnecting);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	c.ipady = 2;	
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(reactionConnecting, c);
	y++;

	reactionAll = new JRadioButton("all reactions");
	group.add(reactionAll);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	c.ipady = 2;	
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(reactionAll, c); 
	y++;

	reactionComplete = new JRadioButton("make reactions complete");
	group.add(reactionComplete);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	c.ipady = 2;	
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(reactionComplete, c);
	y++;

	reactionConnecting.setSelected(true);

	x = 1;
	selectPublications = new JCheckBox("Add publications");
	selectPublications.setSelected(true);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	//c.ipady = 10;	
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(selectPublications, c);
	y++;

	y = GraphicUtils.addHSepPanel(panel, 1, y, 3, 2);

	x = 1;
	label = new JLabel("Output");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.gridheight = 2;
	c.weightx = 0.0;
	//c.ipady = 40;	
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(label, c);

	x += 2;

	group = new ButtonGroup();
	outputCurrentNetworkRB = new JRadioButton("Output in the current network");
	group.add(outputCurrentNetworkRB);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	//c.ipady = 10;	
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(outputCurrentNetworkRB, c);
	y++;

	outputNewNetworkRB = new JRadioButton("Output in a new network");
	group.add(outputNewNetworkRB);
	c = new GridBagConstraints();
	c.gridx = x;
	c.gridy = y;
	c.weightx = 0.0;
	c.ipady = 10;	
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.NONE;
	panel.add(outputNewNetworkRB, c);
	y++;

	outputCurrentNetworkRB.setSelected(true);    

	y = GraphicUtils.addHSepPanel(panel, 1, y, 3, 2);

	JPanel buttonPanel = new JPanel();

	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
                    BioPAXStandardQueryTask task = new BioPAXStandardQueryTask();
                    BioPAXStandardQueryTask.StandardQueryOptions options = task.new StandardQueryOptions();
                    options.selectComplex = selectComplex.isSelected();
		    options.complexExpand = complexExpand.isSelected();
		    options.complexNoExpand = complexNoExpand.isSelected();
                    options.selectSpecies = selectSpecies.isSelected();
                    options.selectReactions = selectReactions.isSelected();
		    options.reactionConnecting = reactionConnecting.isSelected();
		    options.reactionAll = reactionAll.isSelected();
		    options.reactionComplete = reactionComplete.isSelected();
                    options.selectPublications = selectPublications.isSelected();
                    Vector selected = null;
                    if(selectedNodesRB.isSelected()){
                    	selected = new Vector();
                    	List<CyNode> nodes = CyTableUtil.getNodesInState(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork(),"selected",true);
                    	//Set nodes = Cytoscape.getCurrentNetwork().getSelectedNodes();
                    	Iterator it = nodes.iterator();
                    	while(it.hasNext()){
			    CyNode nd = (CyNode)it.next();
			    selected.add(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork().getRow(nd).get(CyNetwork.NAME, String.class));
                    	}
                    }
                    BioPAXGraphQuery query = new BioPAXGraphQuery();
                    GraphDocument grDoc = GraphDocumentFactory.getInstance().createGraphDocument(Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork());
                    query.input = XGMML.convertXGMMLToGraph(grDoc);
                    
                    TaskIterator t = new TaskIterator(new BioPAXStandardQueryTask(options,query,selected,outputCurrentNetworkRB.isSelected()));
            		Launcher.getAdapter().getTaskManager().execute(t);
                  
                    
                    setVisible(false);
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

	getContentPane().setLayout(new BorderLayout());

	JScrollPane jpane = new JScrollPane(panel);
	getContentPane().add(jpane, BorderLayout.CENTER);
	getContentPane().add(buttonPanel, BorderLayout.SOUTH);

	selectComplex.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
		    complexExpand.setEnabled(selectComplex.isSelected());
		    complexNoExpand.setEnabled(selectComplex.isSelected());
		}
	    });

	selectReactions.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
		    reactionConnecting.setEnabled(selectReactions.isSelected());
		    reactionAll.setEnabled(selectReactions.isSelected());
		    reactionComplete.setEnabled(selectReactions.isSelected());
		}
	    });

        //pack();
    }

    public static BioPAXStandardQueryDialog instance;

    public static BioPAXStandardQueryDialog getInstance() {
	if (instance == null)
	    instance = new BioPAXStandardQueryDialog();
	return instance;
    }

    public void raise(BioPAX biopax) {
	this.biopax = biopax;

	Dimension size = getSize();
	setSize(new Dimension(400, 500));

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
    }
}
