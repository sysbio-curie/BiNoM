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
package fr.curie.BiNoM.cytoscape.netwop;

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.InputStream;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import fr.curie.BiNoM.cytoscape.lib.NetworkFactory;

import java.io.File;
import java.net.URL;
import java.util.Set;
import java.util.Vector;

import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;

public class NetworksUpdateConfirmDialog extends JDialog {

    private static final String OFFSTR = "      ";
    private static final double COEF_X = 1.24, COEF_Y = 1.10;

    private static final java.awt.Font TITLE_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 14);

    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);

    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    public static NetworksUpdateConfirmDialog instance;
    private JPanel mainPanel;
    private JButton okB, cancelB;
    private CyNetwork netwAdd;
    private CyNetwork netwSup;
    private CyNetwork[] networks;

    public static NetworksUpdateConfirmDialog getInstance() {
	if (instance == null)
	    instance = new NetworksUpdateConfirmDialog();
	return instance;
    }

    private JCheckBox cbs[];

    public boolean raise(CyNetwork netwAdd, CyNetwork netwSup, int idxs[]) {

	for (int n = 0; n < idxs.length; n++) {
	    CyNetwork netw = NetworkUtils.getNetwork(idxs[n]);
	    if (netw == netwAdd) {
                JOptionPane.showMessageDialog(mainPanel,
					      "Network " + netw.getTitle() +
					      " cannot be updated as it is " +
					      "used for addition");
		
		return false;
	    }

	    if (netw == netwSup) {
                JOptionPane.showMessageDialog(mainPanel,
					      "Network " + netw.getTitle() +
					      " cannot be updated as it is " +
					      "used for suppression");
		
		return false;
	    }
	}

	this.netwAdd = netwAdd;
	this.netwSup = netwSup;
	networks = getNetworks(idxs);
	mainPanel.removeAll();

	cbs = new JCheckBox[networks.length];

	for (int n = 0; n < networks.length; n++) {
	    CyNetwork netw = networks[n];
	    cbs[n] = new JCheckBox(netw.getTitle());

	    JPanel padPanel = new JPanel();
	    GridBagConstraints c = new GridBagConstraints();
	    c.gridx = 0;
	    c.gridy = 3 * n;
	    c.gridwidth = 2;
	    c.anchor = GridBagConstraints.CENTER;
	    padPanel.setPreferredSize(new Dimension(1, 20));
	    mainPanel.add(padPanel, c);

	    c = new GridBagConstraints();
	    c.gridx = 0;
	    c.gridy = 3 * n + 1;
	    c.anchor = GridBagConstraints.NORTHWEST;
	    mainPanel.add(cbs[n], c);
	    cbs[n].setFont(BOLD_FONT);

	    int add_node_cnt = 0;
	    int sup_node_cnt = 0;
	    int add_edge_cnt = 0;
	    int sup_edge_cnt = 0;

	    NetworkOperation op;
	    if (netwAdd != null && netwAdd != netw) {
		op = new NetworkUnion(netw, netwAdd);
		NetworkOperation.Estimator estim =
		    new NetworkOperation.Estimator();
		op.estimate(netw, estim);
		add_node_cnt += estim.getAddedNodeCount();
		add_edge_cnt += estim.getAddedEdgeCount();
		sup_node_cnt += estim.getSuppressedNodeCount();
		sup_edge_cnt += estim.getSuppressedEdgeCount();
	    }
	    
	    if (netwSup != null && netwSup != netw) {
		op = new NetworkDifference(netw, netwSup);
		NetworkOperation.Estimator estim =
		    new NetworkOperation.Estimator();
		op.estimate(netw, estim);
		add_node_cnt += estim.getAddedNodeCount();
		add_edge_cnt += estim.getAddedEdgeCount();
		sup_node_cnt += estim.getSuppressedNodeCount();
		sup_edge_cnt += estim.getSuppressedEdgeCount();
	    }

	    JPanel subPanel = new JPanel();
	    BoxLayout layout = new BoxLayout(subPanel, BoxLayout.Y_AXIS);
	    subPanel.setLayout(layout);
	    JLabel label;
	    /*
	    label = new JLabel(" ");
	    subPanel.add(label);
	    */

	    if (add_node_cnt == 0 && add_edge_cnt == 0 &&
		sup_node_cnt == 0 && sup_edge_cnt == 0) {
		label = new JLabel(OFFSTR + "No changes needed");
		subPanel.add(label);
		cbs[n].setSelected(false);
		cbs[n].setEnabled(false);
	    }
	    else {
		cbs[n].setSelected(true);
		cbs[n].setEnabled(true);
		if (add_node_cnt != 0) {
		    label = new JLabel(OFFSTR + add_node_cnt + " nodes to add");
		    subPanel.add(label);
		}
		if (add_edge_cnt != 0) {
		    label = new JLabel(OFFSTR + add_edge_cnt + " edges to add");
		    subPanel.add(label);
		}
		if (sup_node_cnt != 0) {
		    label = new JLabel(OFFSTR + sup_node_cnt + " nodes to suppress");
		    subPanel.add(label);
		}
		if (sup_edge_cnt != 0) {
		    label = new JLabel(OFFSTR + sup_edge_cnt + " edges to suppress");
		    subPanel.add(label);
		}
	    }

	    c = new GridBagConstraints();
	    c.gridx = 1;
	    c.gridy = 3 * n + 1;
	    c.ipady = 20;
	    c.anchor = GridBagConstraints.WEST;
	    mainPanel.add(subPanel, c);

	    padPanel = new JPanel();
	    c = new GridBagConstraints();
	    c.gridx = 0;
	    c.gridy = 3 * n + 2;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.anchor = GridBagConstraints.CENTER;
	    padPanel.setBackground(Color.BLACK);
	    padPanel.setPreferredSize(new Dimension(1, 1));
	    mainPanel.add(padPanel, c);
	}

	pack();

	Dimension size = getSize();
	setSize(new Dimension((int)(size.width * COEF_X),
			      (int)(size.height * COEF_Y)));

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
	return true;
    }

    private NetworksUpdateConfirmDialog() {
	JPanel panel = new JPanel(new GridBagLayout());
	GridBagConstraints c;
	int y = 0;

	JLabel title = new JLabel("Updating Networks");

	title.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.anchor = GridBagConstraints.CENTER;
	c.gridwidth = 2;
	c.ipady = 40;
	panel.add(title, c);
	
	mainPanel = new JPanel(new GridBagLayout());

	c = new GridBagConstraints();
	c.ipady = 30;
	c.gridx = 0;
	c.gridy = y++;
	c.gridwidth = 2;
	c.anchor = GridBagConstraints.CENTER;

	panel.add(mainPanel, c);

	JPanel buttonPanel = new JPanel();
	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    for (int n = 0; n < cbs.length; n++) {
			if (!cbs[n].isSelected())
			    networks[n] = null;
		    }

		    NetworksUpdateTask netwUpdTask = new NetworksUpdateTask(networks, netwAdd, netwSup);

		    fr.curie.BiNoM.cytoscape.lib.TaskManager.executeTask(netwUpdTask);
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

	c = new GridBagConstraints();
	c.ipady = 30;
	c.gridx = 0;
	c.gridy = y;
	c.gridwidth = 2;
	c.anchor = GridBagConstraints.CENTER;

	panel.add(buttonPanel, c);

	//getContentPane().add(panel);
	JScrollPane scrollPane = new JScrollPane(panel);
	getContentPane().add(scrollPane);
    }

    private static CyNetwork[] getNetworks(int idxs[]) {
	CyNetwork networks[] = new CyNetwork[idxs.length];
	for (int n = 0; n < idxs.length; n++) {
	    networks[n] = NetworkUtils.getNetwork(idxs[n]);
	}

	return networks;
    }
}
