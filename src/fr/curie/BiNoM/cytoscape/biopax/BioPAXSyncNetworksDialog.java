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
package fr.curie.BiNoM.cytoscape.biopax;

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
import fr.curie.BiNoM.cytoscape.biopax.propedit.*;

import java.io.File;
import java.net.URL;
import java.util.*;

import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import fr.curie.BiNoM.cytoscape.netwop.*;
import fr.curie.BiNoM.cytoscape.lib.*;
import fr.curie.BiNoM.biopax.BioPAXSourceDB;

import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.CyEdge;

public class BioPAXSyncNetworksDialog extends JDialog {

    private JList netwList;
    //    private Vector<CyNetwork> netwV;
    private Vector<Object> netwV;
    private JButton okB, cancelB;
    private JScrollPane scrollPane;
    private BioPAX biopax;

    private static final double COEF_X = 1.24, COEF_Y = 1.10;
    private static final int MAX_ROWS = 20;
    private static final String EMPTY_NAME = "                       ";
    private static final java.awt.Font TITLE_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 14);

    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);

    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    public static BioPAXSyncNetworksDialog instance;

    public static BioPAXSyncNetworksDialog getInstance() {
	if (instance == null)
	    instance = new BioPAXSyncNetworksDialog();
	return instance;
    }

    public void raise(BioPAX biopax) {
	this.biopax = biopax;
	netwV = BioPAXSourceDB.getInstance().getAssociatedNetworks(biopax);
	Vector data = new Vector();
	int len = netwV.size();
	int ind[] = new int[len];
	for (int n = 0; n < len; n++) {
	    data.add(((CyNetwork)netwV.get(n)).getTitle());
	    ind[n] = n;
	}

	netwList.setListData(data);
	netwList.setSelectedIndices(ind);

	netwList.setVisibleRowCount(len < MAX_ROWS ? len : MAX_ROWS);

	pack();

	Dimension size = getSize();
	setSize(new Dimension((int)(size.width * COEF_X),
			      (int)(size.height * COEF_Y)));

	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
    }

    private BioPAXSyncNetworksDialog() {
	JPanel panel = new JPanel(new GridBagLayout());
	GridBagConstraints c;
	int y = 0;

	JLabel title = new JLabel("Synchronize networks with BioPAX");

	title.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y++;
	c.anchor = GridBagConstraints.CENTER;
	c.ipady = 40;
	panel.add(title, c);
	
	netwList = new JList();
	netwList.setBackground(new Color(0xeeeeee));
	scrollPane = new JScrollPane(netwList);

	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = y;
	c.anchor = GridBagConstraints.CENTER;
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
		    try {
			System.out.println("should synchronize the following networks:");
			int ind[] = netwList.getSelectedIndices();
			if (ind.length > 0) {
			    BioPAXToCytoscapeConverter b2c = new BioPAXToCytoscapeConverter();
			    b2c.biopax = biopax;
			    BioPAXToCytoscapeConverter.Graph graph = 
				BioPAXToCytoscapeConverter.convert(BioPAXToCytoscapeConverter.FULL_INDEX_CONVERSION, b2c, "", new BioPAXToCytoscapeConverter.Option());
			    edu.rpi.cs.xgmml.GraphicGraph grf = graph.graphDocument.getGraph();
			    System.out.println("NODES COUNT: " + grf.getNodeArray().length);
			    edu.rpi.cs.xgmml.GraphicNode nodes[] = grf.getNodeArray();
			    for (int n = 0; n < nodes.length; n++) {
				//edu.rpi.cs.xgmml.GraphicNode node = nodes[n];
				System.out.println(nodes[n].getId());
			    }

			    CyNetwork wholeNetw = NetworkFactory.createNetwork
				("",
				 graph.graphDocument,
				 BioPAXVisualStyleDefinition.getInstance(),
				 true,
				 null /*taskMonitor*/);
			
			    System.out.println("WHOLENETW: " + wholeNetw.getNodeCount());
			    for (int n = 0; n < ind.length; n++) {
				CyNetwork netw = (CyNetwork)netwV.get(ind[n]);
				CyNetwork netw_old = NetworkUtils.makeBackupNetwork(netw);
				System.out.println(netw.getTitle());
				NetworkOperation op =
				    new SyncNetworkIntersection(netw, wholeNetw);
				CyNetwork nullNetw = NetworkFactory.getNullEmptyNetwork();
				op.eval(nullNetw);
				NetworkUtils.clearAndCopy(netw, nullNetw);
			    }
			    Cytoscape.destroyNetwork(wholeNetw);
			}
		    }
		    catch(Exception ex) {
			ex.printStackTrace();
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

	getContentPane().setLayout(new BorderLayout());
	JScrollPane jpane = new JScrollPane(panel);
	getContentPane().add(jpane, BorderLayout.CENTER);
	getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    static class SyncNetworkIntersection extends NetworkIntersection {

	private HashMap<CyNetwork, HashMap> node_maps = new HashMap();
	private HashMap<CyNetwork, HashMap> edge_maps = new HashMap();

	public SyncNetworkIntersection(CyNetwork left, CyNetwork right) {
	    super(left, right);
	}

	private HashMap buildNodeMap(CyNetwork netw) {
	    java.util.Iterator i = netw.nodesIterator();
	    HashMap map = new HashMap();
	    int l = 0;
	    while(i.hasNext()) {
		CyNode node = (CyNode)i.next();
		String uri_arr[] = BioPAXPropertyManager.getURIs(node);
		System.out.println(node.getIdentifier() + " -> " + uri_arr.length);
		l += uri_arr.length;
		for (int n = 0; n < uri_arr.length; n++) {
		    map.put(uri_arr[n], node);
		}
	    }

	    System.out.println("buildNodMap: " + netw.getNodeCount() + " " + map.size() + " " + l);
	    node_maps.put(netw, map);
	    return map;
	}

	private HashMap buildEdgeMap(CyNetwork netw) {
	    java.util.Iterator i = netw.edgesIterator();
	    HashMap map = new HashMap();
	    while(i.hasNext()) {
		CyEdge edge = (CyEdge)i.next();
		String uri_arr[] = BioPAXPropertyManager.getURIs(edge);
		for (int n = 0; n < uri_arr.length; n++) {
		    map.put(uri_arr[n], edge);
		}
	    }

	    edge_maps.put(netw, map);
	    return map;
	}

	public boolean containsNode(CyNetwork netw, CyNode node) {
	    HashMap map = node_maps.get(netw);
	    if (map == null) {
		map = buildNodeMap(netw);
	    }

	    String uri_arr[] = BioPAXPropertyManager.getURIs(node);
	    for (int n = 0; n < uri_arr.length; n++) {
		if (map.get(uri_arr[n]) != null) {
		    System.out.println("containsNode " + uri_arr[n]);
		    return true;
		}
	    }

	    System.out.println("does not containsNode " + uri_arr[0]);
	    Iterator it = map.entrySet().iterator();
	    while (it.hasNext()) {
		Map.Entry entry = (Map.Entry)it.next();
		System.out.println("-> " + entry.getKey());
	    }
	    return false;
	}
	
	public boolean containsEdge(CyNetwork netw, CyEdge edge) {
	    HashMap map = edge_maps.get(netw);
	    if (map == null) {
		map = buildEdgeMap(netw);
	    }

	    String uri_arr[] = BioPAXPropertyManager.getURIs(edge);
	    for (int n = 0; n < uri_arr.length; n++) {
		if (map.get(uri_arr[n]) != null) {
		    return true;
		}
	    }

	    return false;
	}
    }
}
