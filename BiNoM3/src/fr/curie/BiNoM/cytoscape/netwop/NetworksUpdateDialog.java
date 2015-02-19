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

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.cytoscape.model.CyNetwork;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.io.InputStream;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;

import java.io.File;
import java.net.URL;
import java.util.Set;
import java.util.Vector;

import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;

public class NetworksUpdateDialog extends JDialog {

    private static final double COEF_X = 1.24, COEF_Y = 1.10;
    private static final int MAX_ROWS = 20;
    private JComboBox netwAddCB;
    private JComboBox netwSupCB;
    private JList netwUpdateList;
    private static final String EMPTY_NAME = "                       ";
    private static final java.awt.Font TITLE_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 14);

    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);

    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    public static NetworksUpdateDialog instance;
    private JButton okB, cancelB;
    private JScrollPane scrollPane;

    public static NetworksUpdateDialog getInstance() {
	if (instance == null)
	    instance = new NetworksUpdateDialog();
	return instance;
    }

    public void raise() {
	netwAddCB.removeAllItems();
	netwSupCB.removeAllItems();

	String[] netwNames = NetworkUtils.getNetworkNames(EMPTY_NAME);
	Vector data = new Vector();
	netwAddCB.addItem(EMPTY_NAME);
	netwSupCB.addItem(EMPTY_NAME);
	for (int n = 0; n < netwNames.length; n++) {
	    netwAddCB.addItem(netwNames[n]);
	    netwSupCB.addItem(netwNames[n]);
	    data.add(netwNames[n]);
	}

	netwUpdateList.setListData(data);
	int len = data.size();
	netwUpdateList.setVisibleRowCount(len < MAX_ROWS ? len : MAX_ROWS);

	pack();

	Dimension size = getSize();
	setSize(new Dimension((int)(size.width * COEF_X),
			      (int)(size.height * COEF_Y)));

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
    }

    private NetworksUpdateDialog() {
	JPanel panel = new JPanel(new GridBagLayout());
	GridBagConstraints c;
	int y = 0;

	JLabel title = new JLabel("Updating Networks");

	title.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.anchor = GridBagConstraints.CENTER;
	c.gridwidth = 3;
	c.ipady = 40;
	panel.add(title, c);
	
	JLabel label;

	label = new JLabel("Network to add");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.ipadx = 10;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	label = new JLabel("Network to suppress");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y + 1;
	c.ipadx = 10;
	c.ipady = 30;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	netwAddCB = new JComboBox();
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(netwAddCB, c);

	netwSupCB = new JComboBox();
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y + 1;
	c.anchor = GridBagConstraints.WEST;
	panel.add(netwSupCB, c);

	label = new JLabel("Networks to update");
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y + 2;
	c.ipadx = 10;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	netwUpdateList = new JList();
	netwUpdateList.setBackground(new Color(0xeeeeee));
	scrollPane = new JScrollPane(netwUpdateList);

	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y + 2;
	c.anchor = GridBagConstraints.WEST;
	//c.fill = GridBagConstraints.HORIZONTAL;
	//c.weightx = 1.0;
	//panel.add(netwUpdateList, c);WEST;
	panel.add(scrollPane, c);

	y += 3;

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
		    int idxAdd = netwAddCB.getSelectedIndex();
		    int idxSup = netwSupCB.getSelectedIndex();
		    int idxs[] = netwUpdateList.getSelectedIndices();
		    if (idxAdd != idxSup && idxs.length > 0) {
			CyNetwork netwAdd = (idxAdd == 0 ? null :
					     NetworkUtils.getNetwork(idxAdd - 1));
			CyNetwork netwSup = (idxSup == 0 ? null :
					     NetworkUtils.getNetwork(idxSup - 1));

			if (NetworksUpdateConfirmDialog.getInstance().raise(netwAdd, netwSup, idxs)) {
			    setVisible(false);
			}
			return;
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

	/*
	c = new GridBagConstraints();
	c.ipady = 30;
	c.gridx = 0;
	c.gridy = y;
	c.gridwidth = 3;
	c.anchor = GridBagConstraints.CENTER;

	panel.add(buttonPanel, c);
	*/

	getContentPane().setLayout(new BorderLayout());
	JScrollPane jpane = new JScrollPane(panel);
	getContentPane().add(jpane, BorderLayout.CENTER);
	getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
}
