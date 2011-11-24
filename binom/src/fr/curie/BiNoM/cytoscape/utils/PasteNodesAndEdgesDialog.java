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

import cytoscape.Cytoscape;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.CyEdge;
import cytoscape.view.CyNetworkView;

import java.util.*;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;

public class PasteNodesAndEdgesDialog extends JDialog implements ActionListener {

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

    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    private Component nodeCB[];
    private Component edgeCB[];
    private Component edgeNodeCB[];
    private HashMap addNodes, addEdges, addEdgeNodes;
    private JButton selectdefB;

    private JButton okB, cancelB;

    public static PasteNodesAndEdgesDialog instance;

    public static PasteNodesAndEdgesDialog getInstance() {
	if (instance == null)
	    instance = new PasteNodesAndEdgesDialog();
	return instance;
    }

    private HashMap cbNodeMap;
    private HashMap cbEdgeMap;
	
    static class Component {
	JCheckBox cb;
	JComponent component;
	boolean isSelectable;

	Component(JCheckBox cb, JComponent component, boolean isSelectable) {
	    this.cb = cb;
	    this.component = component;
	    this.isSelectable = isSelectable;
	    if (!isSelectable)
		cb.setEnabled(false);
	}

	JComponent getComponent() {return component;}

	boolean isSelected() {
	    if (isSelectable)
		return cb.isSelected();
	    return false;
	}

	void setSelected(boolean select) {
	    if (isSelectable)
		cb.setSelected(select);
	}
    }

    private Component makeCheckBox(boolean adding, String id) {

	/*
	JLabel label = new JLabel(id);
	JCheckBox cb;
	if (adding) {
	    cb = new JCheckBox(addingMessage(adding) + id);
	}
	else {
	    cb = new JCheckBox();
	    label.setForeground(java.awt.Color.GRAY);
	}

	JPanel panel = new JPanel();

	panel.add(cb);
	panel.add(label);

	return new Component(cb, panel, adding);
	*/
	JCheckBox cb = new JCheckBox(addingMessage(adding) + id);
	return new Component(cb, cb, adding);
    }

    private Component makeCheckBox(boolean adding, CyNode cyNode) {
	Component comp = makeCheckBox(adding, cyNode.getIdentifier());

	cbNodeMap.put(comp, cyNode);
	return comp;
    }

    private Component makeCheckBox(boolean adding, CyEdge cyEdge) {
	Component comp = makeCheckBox(adding, cyEdge.getIdentifier());

	cbEdgeMap.put(comp, cyEdge);
	return comp;
    }

    public void pop(HashMap addNodes, HashMap addEdges, HashMap addEdgeNodes) {
	this.addNodes = addNodes;
	this.addEdges = addEdges;
	this.addEdgeNodes = addEdgeNodes;
	cbNodeMap = new HashMap();
	cbEdgeMap = new HashMap();

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

	JLabel title = new JLabel("Paste Nodes and Edges");
	title.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.anchor = GridBagConstraints.CENTER;
	c.gridwidth = 4;
	c.ipady = 30;
	panel.add(title, c);

	JLabel info = new JLabel(addNodes.size() + " non connected nodes, " +
				 addEdges.size() + " edges, " +
				 addEdgeNodes.size() + " connected nodes");

	info.setFont(STD_BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.ipady = 30;
	c.gridwidth = 4;
	panel.add(info, c);

	y++;

	JButton selectB = new JButton("Select All");

	selectB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent _e) {
		    selectAll(true);
		}
	    });
				  
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.gridwidth = 1;
	panel.add(selectB, c);

	JButton deselectB = new JButton("Deselect All");

	deselectB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent _e) {
		    selectAll(false);
		}
	    });
				  
	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.gridwidth = 1;
	panel.add(deselectB, c);

	selectdefB = new JButton("Select Default");

	selectdefB.addActionListener(this);
				  
	c = new GridBagConstraints();
	c.gridx = 2;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	c.gridwidth = 1;
	panel.add(selectdefB, c);

	y++;

	CyNetwork network = Cytoscape.getCurrentNetwork();

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

	nodeCB = new Component[addNodes.size()];
	int n = 0;
	for (Iterator i = addNodes.entrySet().iterator(); i.hasNext(); n++) {
	    Map.Entry e = (Map.Entry)i.next();
	    CyNode cyNode = (CyNode)e.getKey();
	    boolean adding = isAdding(e.getValue());
	    nodeCB[n] = makeCheckBox(adding, cyNode);

	    if (adding)
		setSelected(nodeCB[n], true);

	    c = new GridBagConstraints();
	    c.gridx = 0;
	    c.gridy = y++;
	    c.anchor = GridBagConstraints.WEST;
	    c.gridwidth = 3;
	    //c.ipady = 2;
	    panel.add(nodeCB[n].getComponent(), c);
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

	edgeCB = new Component[addEdges.size()];
	edgeNodeCB = new Component[edgeNodeSize(addEdges)];

	n = 0;
	for (Iterator i = addEdges.entrySet().iterator(); i.hasNext(); n++) {
	    Map.Entry e = (Map.Entry)i.next();
	    CyEdge cyEdge = (CyEdge)e.getKey();
	    boolean adding = isAdding(e.getValue());
	    edgeCB[n] = makeCheckBox(adding, cyEdge);

	    if (adding)
		setSelected(edgeCB[n], true);

	    c = new GridBagConstraints();
	    c.gridx = 0;
	    c.gridy = y++;
	    c.gridwidth = 4;
	    c.anchor = GridBagConstraints.WEST;
	    //c.ipady = 2;
	    panel.add(edgeCB[n].getComponent(), c);

	    CyNode source = (CyNode)cyEdge.getSource();
	    boolean sourceAdding = isAdding(addEdgeNodes, source);

	    int m = edgeNodeSourceInd(n);
	    edgeNodeCB[m] = makeCheckBox(sourceAdding, source);

	    if (sourceAdding)
		setSelected(edgeNodeCB[m], true);

	    c = new GridBagConstraints();
	    c.gridx = 2;
	    c.gridy = y++;
	    c.gridwidth = 1;
	    c.anchor = GridBagConstraints.WEST;
	    //c.ipady = 1;
	    panel.add(edgeNodeCB[m].getComponent(), c);

	    CyNode target = (CyNode)cyEdge.getTarget();

	    boolean targetAdding = isAdding(addEdgeNodes, target);

	    m = edgeNodeTargetInd(n);
	    edgeNodeCB[m] = makeCheckBox(targetAdding, target);

	    if (targetAdding)
		setSelected(edgeNodeCB[m], true);

	    c = new GridBagConstraints();
	    c.gridx = 2;
	    c.gridy = y++;
	    c.gridwidth = 2;
	    c.anchor = GridBagConstraints.WEST;
	    //c.ipady = 1;
	    panel.add(edgeNodeCB[m].getComponent(), c);
	}

	System.out.println("y = " + y);
	JPanel buttonPanel = new JPanel();

	okB = new JButton("OK");
	okB.addActionListener(this);

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
	c.gridy = y++;
	c.gridwidth = 3;
	c.anchor = GridBagConstraints.CENTER;

	JPanel mainPanel = new JPanel(new BorderLayout());

	//mainPanel.add(controllPanel, BorderLayout.NORTH);
	mainPanel.add(scrollPanel, BorderLayout.CENTER);
	mainPanel.add(buttonPanel, BorderLayout.SOUTH);

	//panel.add(buttonPanel, c);

	getContentPane().add(mainPanel);
	pack();
	Dimension size = getSize();
	setSize(new Dimension((int)(size.width * COEF_X),
			      (int)(size.height * COEF_Y)));
    }

    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == okB) {
	    NodesAndEdgesClipboard clipboard = NodesAndEdgesClipboard.getInstance();
	    CyNetwork network = Cytoscape.getCurrentNetwork();
	    for (int n = 0; n < nodeCB.length; n++) {
		if (isSelected(nodeCB[n])) {
		    //network.addNode((CyNode)cbNodeMap.get(nodeCB[n]));
		    NetworkUtils.addNodeAndReportPosition((CyNode)cbNodeMap.get(nodeCB[n]), clipboard.getNetwork(), network);
		}
	    }

	    for (int n = 0; n < edgeNodeCB.length; n++) {
		if (isSelected(edgeNodeCB[n])) {
		    //network.addNode((CyNode)cbNodeMap.get(edgeNodeCB[n]));
		    NetworkUtils.addNodeAndReportPosition((CyNode)cbNodeMap.get(edgeNodeCB[n]), clipboard.getNetwork(), network);
		}
	    }

	    for (int n = 0; n < edgeCB.length; n++) {
		if (isSelected(edgeCB[n])) {
		    network.addEdge((CyEdge)cbEdgeMap.get(edgeCB[n]));
		}
	    }

	    CyNetworkView view = Cytoscape.getCurrentNetworkView();
	    view.redrawGraph(true, true);
	    setVisible(false);
	}
	else if (e.getSource() == selectdefB) {
	    int n = 0;
	    for (Iterator i = addNodes.entrySet().iterator(); i.hasNext(); n++) {
		Map.Entry entry = (Map.Entry)i.next();
		CyNode cyNode = (CyNode)entry.getKey();
		setSelected(nodeCB[n], isAdding(entry.getValue()));
	    }
	    
	    n = 0;
	    for (Iterator i = addEdges.entrySet().iterator(); i.hasNext(); n++) {
		Map.Entry entry = (Map.Entry)i.next();
		CyEdge cyEdge = (CyEdge)entry.getKey();
		setSelected(edgeCB[n], isAdding(entry.getValue()));

		CyNode source = (CyNode)cyEdge.getSource();
		int m = edgeNodeSourceInd(n);
		setSelected(edgeNodeCB[m], isAdding(addEdgeNodes, source));

		m = edgeNodeTargetInd(n);
		CyNode target = (CyNode)cyEdge.getTarget();
		setSelected(edgeNodeCB[m], isAdding(addEdgeNodes, target));
		
	    }
	}
    }

    private static boolean isAdding(Object o) {
	return !((Boolean)o).booleanValue();
    }

    private static boolean isAdding(HashMap map, Object o) {
	return isAdding(map.get(o));
    }

    private static String addingMessage(boolean adding) {
	return adding ? "Adding " : "";
    }

    private int edgeNodeSourceInd(int n) {
	return 2 * n;
    }

    private int edgeNodeTargetInd(int n) {
	return 2 * n + 1;
    }

    private int edgeNodeSize(HashMap map) {
	return 2 * map.size();
    }

    private void selectAll(boolean select) {
	for (int n = 0; n < nodeCB.length; n++) {
	    setSelected(nodeCB[n], select);
	}
	
	for (int n = 0; n < edgeCB.length; n++) {
	    setSelected(edgeCB[n], select);
	}
	
	for (int n = 0; n < edgeNodeCB.length; n++) {
	    setSelected(edgeNodeCB[n], select);
	}
    }

    private boolean isSelected(Component comp) {
	return comp.isSelected();
    }

    private void setSelected(Component comp, boolean select) {
	comp.setSelected(select);
    }
}
