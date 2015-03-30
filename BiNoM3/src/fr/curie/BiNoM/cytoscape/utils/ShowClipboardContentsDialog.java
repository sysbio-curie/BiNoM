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
package fr.curie.BiNoM.cytoscape.utils;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyEdge;
import Main.Launcher;
import java.util.*;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShowClipboardContentsDialog extends JDialog {
	
	
	
	

    private static final double COEF_X = 1.24, COEF_Y = 1.05;

    private static final java.awt.Font TITLE_FONT = new java.awt.Font("times",
							     java.awt.Font.BOLD,
							     14);
    private static final java.awt.Font BOLD_FONT = new java.awt.Font("times",
							     java.awt.Font.BOLD,
							     12);

    private static final java.awt.Font STD_BOLD_FONT = new java.awt.Font("times",
									 java.awt.Font.BOLD,
									 10);
    private static final java.awt.Font STD_FONT = new java.awt.Font("times",
								    java.awt.Font.PLAIN,
								    10);

    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    private HashMap addNodes, addEdges, addEdgeNodes;

    public static ShowClipboardContentsDialog instance;

    public static ShowClipboardContentsDialog getInstance() {
	if (instance == null)
	    instance = new ShowClipboardContentsDialog();
	return instance;
    }

    public void pop(HashMap addNodes, HashMap addEdges, HashMap addEdgeNodes) {
	this.addNodes = addNodes;
	this.addEdges = addEdges;
	this.addEdgeNodes = addEdgeNodes;

	build();

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
    }

    private void build() {
	getContentPane().removeAll();

	JPanel panel = new JPanel(new GridBagLayout());
	JScrollPane scrollPanel = new JScrollPane(panel);

	GridBagConstraints c;
	int y = 0;

	JLabel title = new JLabel("Nodes and Edges Clipboard");
	title.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.anchor = GridBagConstraints.CENTER;
	c.gridwidth = 4;
	panel.add(title, c);

	JLabel indent = new JLabel("          ");
	title.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.anchor = GridBagConstraints.CENTER;
	c.ipady = 30;
	c.gridwidth = 1;
	panel.add(indent, c);


	CyNetwork network = Launcher.getAdapter().getCyApplicationManager().getCurrentNetwork();

	JLabel info = new JLabel(addNodes.size() + " non connected nodes, " +
				 addEdges.size() + " edges, " +
				 addEdgeNodes.size() + " connected nodes");

	info.setFont(STD_BOLD_FONT);
	    
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.gridwidth = 4;
	panel.add(info, c);

	y++;

	if (addNodes.size() != 0) {
	    JLabel nodeTitle = new JLabel("Non Connected Nodes");
	    nodeTitle.setFont(BOLD_FONT);
	    c = new GridBagConstraints();
	    c.gridx = 0;
	    c.gridy = y++;
	    c.anchor = GridBagConstraints.WEST;
	    c.ipady = 30;
	    c.gridwidth = 4;
	    panel.add(nodeTitle, c);
	}

	int n = 0;
	for (Iterator i = addNodes.entrySet().iterator(); i.hasNext(); n++) {
	    Map.Entry e = (Map.Entry)i.next();
	    CyNode cyNode = (CyNode)e.getKey();
	    JLabel label = new JLabel(Launcher.findNetworkofNode(cyNode.getSUID()).getRow(cyNode).get(CyNetwork.NAME, String.class));
	    label.setFont(STD_FONT);

	    c = new GridBagConstraints();
	    c.gridx = 0;
	    c.gridy = y++;
	    c.anchor = GridBagConstraints.WEST;
	    c.gridwidth = 3;
	    c.ipady = 2;
	    panel.add(label, c);
	}

	if (addEdges.size() != 0) {
	    JLabel edgeTitle = new JLabel("Edges and Connected Nodes");
	    edgeTitle.setFont(BOLD_FONT);
	    c = new GridBagConstraints();
	    c.gridx = 0;
	    c.gridy = y++;
	    c.anchor = GridBagConstraints.WEST;
	    c.ipady = 30;
	    c.gridwidth = 3;
	    panel.add(edgeTitle, c);
	}

	n = 0;
	for (Iterator i = addEdges.entrySet().iterator(); i.hasNext(); n++) {
	    Map.Entry e = (Map.Entry)i.next();
	    CyEdge cyEdge = (CyEdge)e.getKey();
	    JLabel label = new JLabel(Launcher.findNetworkofNode(cyEdge.getSource().getSUID()).getRow(cyEdge).get(CyNetwork.NAME, String.class));
	    label.setFont(STD_FONT);

	    c = new GridBagConstraints();
	    c.gridx = 0;
	    c.gridy = y++;
	    c.gridwidth = 4;
	    c.anchor = GridBagConstraints.WEST;
	    c.ipady = 2;
	    panel.add(label, c);

	    CyNode source = (CyNode)cyEdge.getSource();

	    label = new JLabel("Source ");
	    label.setFont(STD_BOLD_FONT);

	    c = new GridBagConstraints();
	    c.gridx = 1;
	    c.gridy = y;
	    c.gridwidth = 1;
	    c.anchor = GridBagConstraints.WEST;
	    c.ipady = 1;
	    panel.add(label, c);

	    label = new JLabel(Launcher.findNetworkofNode(source.getSUID()).getRow(source).get(CyNetwork.NAME, String.class));
	    label.setFont(STD_FONT);

	    c = new GridBagConstraints();
	    c.gridx = 2;
	    c.gridy = y++;
	    c.gridwidth = 1;
	    c.anchor = GridBagConstraints.WEST;
	    c.ipady = 1;
	    panel.add(label, c);
	    
	    CyNode target = (CyNode)cyEdge.getTarget();

	    label  = new JLabel("Target ");
	    label.setFont(STD_BOLD_FONT);

	    c = new GridBagConstraints();
	    c.gridx = 1;
	    c.gridy = y;
	    c.gridwidth = 1;
	    c.anchor = GridBagConstraints.WEST;
	    c.ipady = 1;
	    panel.add(label, c);

	    label = new JLabel(Launcher.findNetworkofNode(target.getSUID()).getRow(target).get(CyNetwork.NAME, String.class));
	    label.setFont(STD_FONT);

	    c = new GridBagConstraints();
	    c.gridx = 2;
	    c.gridy = y++;
	    c.gridwidth = 1;
	    c.anchor = GridBagConstraints.WEST;
	    c.ipady = 1;
	    panel.add(label, c);
	}

	JPanel buttonPanel = new JPanel();

	JButton okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    setVisible(false);
		}
	    });

	buttonPanel.add(okB);

	JPanel mainPanel = new JPanel(new BorderLayout());

	mainPanel.add(scrollPanel, BorderLayout.CENTER);
	mainPanel.add(buttonPanel, BorderLayout.SOUTH);

	getContentPane().add(mainPanel);
	pack();
	Dimension size = getSize();
	setSize(new Dimension((int)(size.width * COEF_X),
			      (int)(size.height * COEF_Y)));
    }
}
