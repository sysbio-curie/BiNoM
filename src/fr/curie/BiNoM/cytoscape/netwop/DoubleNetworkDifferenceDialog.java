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
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.InputStream;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;

import java.io.File;
import java.net.URL;
import java.util.Set;

import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;

public class DoubleNetworkDifferenceDialog extends JDialog implements ActionListener {

    private static final double COEF_X = 1.24, COEF_Y = 1.05;
    private JComboBox netw1CB;
    private JComboBox netw2CB;
    private JTextField diff1TF;
    private JTextField diff2TF;
    static private final String IDENTICAL_STR = "** identical networks **";

    private static final java.awt.Font TITLE_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 14);

    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);

    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    public static DoubleNetworkDifferenceDialog instance;
    private JButton okB, cancelB;

    public static DoubleNetworkDifferenceDialog getInstance() {
	if (instance == null)
	    instance = new DoubleNetworkDifferenceDialog();
	return instance;
    }

    public void raise() {
	netw1CB.removeAllItems();
	netw2CB.removeAllItems();

	String[] netwNames = NetworkUtils.getNetworkNames();
	for (int n = 0; n < netwNames.length; n++) {
	    netw1CB.addItem(netwNames[n]);
	    netw2CB.addItem(netwNames[n]);
	}

	setNames();

	pack();

	Dimension size = getSize();
	setSize(new Dimension((int)(size.width * COEF_X),
			      (int)(size.height * COEF_Y)));

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
    }

    private DoubleNetworkDifferenceDialog() {
	System.out.println("DoubleNetworkDifferenceDialog()");
	JPanel panel = new JPanel(new GridBagLayout());
	GridBagConstraints c;
	int y = 0;

	JLabel title = new JLabel("Choose the two networks");

	title.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.anchor = GridBagConstraints.CENTER;
	c.gridwidth = 3;
	c.ipady = 30;
	panel.add(title, c);
	
	JLabel label;

	label = new JLabel("Network #1");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.ipadx = 10;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	label = new JLabel("Network #2");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y + 1;
	c.ipadx = 10;
	c.ipady = 20;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	netw1CB = new JComboBox();
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(netw1CB, c);

	netw2CB = new JComboBox();
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y + 1;
	c.anchor = GridBagConstraints.WEST;
	panel.add(netw2CB, c);

	y += 2;

	label = new JLabel("N#1 - N#2 Name");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.ipadx = 10;
	c.ipady = 20;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	diff1TF = new JTextField();
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y++;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1;
	panel.add(diff1TF, c);

	label = new JLabel("N#2 - N#1 Name");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.ipadx = 10;
	c.ipady = 20;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	diff2TF = new JTextField();
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y++;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1;
	panel.add(diff2TF, c);

	JPanel buttonPanel = new JPanel();
	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    int idx1 = netw1CB.getSelectedIndex();
		    int idx2 = netw2CB.getSelectedIndex();
		    if (idx1 != idx2) {
			CyNetwork netw1 = NetworkUtils.getNetwork(idx1);
			CyNetwork netw2 = NetworkUtils.getNetwork(idx2);
			NetworkDifference op;
			CyNetwork netw;

			op = new NetworkDifference(netw1, netw2);
			/*
			netw = Cytoscape.createNetwork
			    (netw1.getTitle() + " - " + netw2.getTitle());
			*/
			netw = Cytoscape.createNetwork(diff1TF.getText());
			op.eval(netw);
			
			op = new NetworkDifference(netw2, netw1);
			/*
			netw = Cytoscape.createNetwork
			    (netw2.getTitle() + " - " + netw1.getTitle());
			*/
			netw = Cytoscape.createNetwork(diff2TF.getText());

			op.eval(netw);
		    }
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
	c.gridwidth = 3;
	c.anchor = GridBagConstraints.CENTER;

	panel.add(buttonPanel, c);

	getContentPane().add(panel);

	netw1CB.addActionListener(this);
	netw2CB.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
	setNames();
    }

    private void setNames() {
	int idx1 = netw1CB.getSelectedIndex();
	int idx2 = netw2CB.getSelectedIndex();

	if (idx1 == idx2) {
	    diff1TF.setText(IDENTICAL_STR);
	    diff2TF.setText(IDENTICAL_STR);
	}
	else if (idx1 >= 0 && idx2 >= 0) {
	    CyNetwork netw1 = NetworkUtils.getNetwork(idx1);
	    CyNetwork netw2 = NetworkUtils.getNetwork(idx2);
	    if (netw1 != null && netw2 != null) {
		diff1TF.setText(netw1.getTitle() + " - " + netw2.getTitle());
		diff2TF.setText(netw2.getTitle() + " - " + netw1.getTitle());
	    }
	}
    }
}
