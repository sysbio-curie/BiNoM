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
package fr.curie.BiNoM.analysis;

import cytoscape.Cytoscape;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.io.InputStream;

import edu.rpi.cs.xgmml.GraphDocument;
import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.pathways.analysis.structure.StructureAnalysisUtils;

import java.io.File;
import java.net.URL;
import java.util.Set;
import java.util.Vector;
import fr.curie.BiNoM.lib.AbstractTask;

import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import fr.curie.BiNoM.analysis.AbstractClusterNetworksTaskFactory;

public class ClusterNetworksDialog extends JDialog {
	
	public JLabel title;

    private static final double COEF_X = 1.24, COEF_Y = 1.10;
    private static final int MAX_ROWS = 20;
    private AbstractClusterNetworksTaskFactory factory;

    private JList moduleList;
    //    private static final String EMPTY_NAME = "                       ";
    private static final java.awt.Font TITLE_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 14);

    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);

    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    public static ClusterNetworksDialog instance;
    private JButton okB, cancelB;
    private JScrollPane scrollPane;
    private JSlider intersectonThresholdS;

    public static ClusterNetworksDialog getInstance(AbstractClusterNetworksTaskFactory factory) {
	if (instance == null) {
	    instance = new ClusterNetworksDialog(factory);
	}
	return instance;
    }

    // will become:
    public void raise(String[] netwNames) {
	Vector data = new Vector();
	for (int n = 0; n < netwNames.length; n++) {
	    System.out.println("raising: " + netwNames[n]);
	    data.add(netwNames[n]);
	}
	moduleList.setListData(data);
	int len = data.size();
	moduleList.setVisibleRowCount(len < MAX_ROWS ? len : MAX_ROWS);

	pack();

	Dimension size = getSize();
	setSize(new Dimension((int)(size.width * COEF_X),
			      (int)(size.height * COEF_Y)));

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);

	System.out.println("setVisible");

	setVisible(true);
    }

    public ClusterNetworksDialog(AbstractClusterNetworksTaskFactory _factory) {
	this.factory = _factory;

	JPanel panel = new JPanel(new GridBagLayout());
	GridBagConstraints c;
	int y = 0;

	title = new JLabel("Clustering networks");

	title.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.anchor = GridBagConstraints.CENTER;
	c.gridwidth = 3;
	c.ipady = 40;
	panel.add(title, c);
	
	JLabel label;

	label = new JLabel("Networks");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y + 2;
	c.ipadx = 10;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	moduleList = new JList();
	moduleList.setBackground(new Color(0xeeeeee));
	scrollPane = new JScrollPane(moduleList);

	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y + 2;
	c.anchor = GridBagConstraints.WEST;
	//c.fill = GridBagConstraints.HORIZONTAL;
	//c.weightx = 1.0;
	//panel.add(netwUpdateList, c);WEST;
	panel.add(scrollPane, c);

	y += 4;

	label = new JLabel("Intersection threshold, %");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.ipadx = 10;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	
	intersectonThresholdS = new JSlider(JSlider.HORIZONTAL,
					    0, 100, ((int)(new StructureAnalysisUtils.Option().intersectionThreshold*100)));
	intersectonThresholdS.setMajorTickSpacing(10);
	intersectonThresholdS.setMinorTickSpacing(1);
	intersectonThresholdS.setPaintTicks(true);
	intersectonThresholdS.setPaintLabels(true);
	
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.gridwidth = 2;
	//c.ipadx = 10;
	c.anchor = GridBagConstraints.WEST;
	panel.add(intersectonThresholdS, c);
	
	
	JPanel pad = new JPanel();
	pad.setPreferredSize(new Dimension(1, 30));
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.gridwidth = 2;
	c.anchor = GridBagConstraints.WEST;
	panel.add(pad, c);

	JPanel buttonPanel = new JPanel();
	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    int idxs[] = moduleList.getSelectedIndices();
		    if (idxs.length == 0) {
			return;
		    }
			
		    StructureAnalysisUtils.Option options = new StructureAnalysisUtils.Option();
		    options.intersectionThreshold = 0.01f*intersectonThresholdS.getValue();
		    /*
		    ClusterNetworksTask task = new ClusterNetworksTask
			(idxs, options,
			 Cytoscape.getCurrentNetworkView().getVisualStyle());
			 fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);		    
		    */

		    AbstractTask task = factory.createTask(idxs, options);
		    task.execute();

		    /*
		      Vector<GraphDocument> modules = new Vector<GraphDocument>();
		      for(int i=0;i<idxs.length;i++){
		      GraphDocument gr = GraphDocumentFactory.getInstance().createGraphDocument(NetworkUtils.getNetwork(idxs[i]));
		      modules.add(gr);
		      }
		      StructureAnalysisUtils.Option options = new StructureAnalysisUtils.Option();
		      options.intersectionThreshold = 0.01f*intersectonThresholdS.getValue();
		      ClusterNetworksTask task = new ClusterNetworksTask
		      (modules,options,
		      Cytoscape.getCurrentNetworkView().getVisualStyle());
		      fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(task);		    
		    */

		    setVisible(false);
		}
	    });

	buttonPanel.add(okB);

	cancelB = new JButton("Cancel");

	cancelB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    setVisible(false);
		}
	    });

	buttonPanel.add(cancelB);

	getContentPane().setLayout(new BorderLayout());
	JScrollPane jpane = new JScrollPane(panel);
	getContentPane().add(jpane, BorderLayout.CENTER);
	getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
}
