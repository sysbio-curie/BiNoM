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

import cytoscape.CyNetwork;
import cytoscape.Cytoscape;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
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
import fr.curie.BiNoM.cytoscape.utils.*;

import java.io.File;
import java.net.URL;
import java.util.*;

import fr.curie.BiNoM.cytoscape.lib.NetworkUtils;
import fr.curie.BiNoM.cytoscape.netwop.*;
import fr.curie.BiNoM.cytoscape.lib.*;

import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.CyEdge;

public class BioPAXDisplayIndexInfoDialog extends JFrame {

    private BioPAX biopax;
    private JButton okB;
    private JButton showAllEntityNamesB;    
    private static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    private static final double COEF_X = 2, COEF_Y = 2;
    private static final java.awt.Font BOLD_FONT = new java.awt.Font
	("times", java.awt.Font.BOLD, 12);
    private static final java.awt.Font PLAIN_FONT = new java.awt.Font
	("times", java.awt.Font.PLAIN, 12);

    private static final int IPADX = 10;
    private static final int IPADY = 5;
    private static final int LABEL_COL = 1;
    private static final int VALUE_COL = 2;

    public BioPAXDisplayIndexInfoDialog() {
        super("BioPAX Index Info");
    
	JPanel panel;
	JLabel label;
	GridBagConstraints c;

	//gl.addLayoutComponent(panel, c);
	
	panel = new JPanel(new GridBagLayout());
	int y = 0;

	label = new JLabel("BioPAX Index Info");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = LABEL_COL;
	c.ipadx = IPADX;
	c.ipady = 2 * IPADY;
	c.gridy = y;
	c.gridwidth = 2;
	c.anchor = GridBagConstraints.CENTER;
	panel.add(label, c);

	y++;

	String name;
	label = new JLabel("Graph name");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = LABEL_COL;
	c.ipadx = IPADX;
	c.ipady = IPADY;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	if (BioPAXIndexRepository.getInstance().getBioPAXGraphQueryEngine() != null)
	    name = BioPAXIndexRepository.getInstance().getBioPAXGraphQueryEngine().database.name;
	else
	    name = "<no name>";

	label = new JLabel(name);
	label.setFont(PLAIN_FONT);
	c = new GridBagConstraints();
	c.gridx = VALUE_COL;
	c.ipadx = IPADX;
	c.ipady = IPADY;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	y++;

	label = new JLabel("Index file name");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = LABEL_COL;
	c.ipadx = IPADX;
	c.ipady = IPADY;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);
	
	if (BioPAXIndexRepository.getInstance().getBioPAXGraphQueryEngine() != null)
	    name = BioPAXIndexRepository.getInstance().getDatabaseFileName();
	else
	    name ="<not loaded>";
	label = new JLabel(name);
	label.setFont(PLAIN_FONT);
	c = new GridBagConstraints();
	c.gridx = VALUE_COL;
	c.ipadx = IPADX;
	c.ipady = IPADY;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	y++;

	if (BioPAXIndexRepository.getInstance().getBioPAXGraphQueryEngine() != null){	
	    label = new JLabel("Statistics");
	    label.setFont(BOLD_FONT);

	    c = new GridBagConstraints();
	    c.gridx = LABEL_COL;
	    c.gridy = y;
	    c.gridheight = 2;
	    c.ipadx = IPADX;
	    c.ipady = IPADY;
	    c.weighty = 1.0;
	    c.fill = GridBagConstraints.VERTICAL;
	    c.anchor = GridBagConstraints.WEST;

	    JPanel p = new JPanel(new BorderLayout());
	    p.add(label, BorderLayout.WEST);

	    panel.add(p, c);
	
	    String[] columnNames = {"Entity type", "Entity Count"};
		
	    Vector entities = new Vector();
	    Vector numbers = new Vector();
		
	    BioPAXIndexRepository.getInstance().getBioPAXGraphQueryEngine().countEntities(entities, numbers);
	    Object data[][] = new Object[entities.size()][2];
	    for (int i = 0; i < entities.size(); i++) {
		data[i][0] = entities.get(i);
		data[i][1] = numbers.get(i);			
	    }

	    JTable table = new JTable(data, columnNames);

	    c = new GridBagConstraints();
	    c.gridx = VALUE_COL;
	    c.ipadx = IPADX;
	    c.ipady = IPADY;
	    c.gridy = y;
	    c.weightx = 0.8;
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.anchor = GridBagConstraints.WEST;

	    y++;

	    table.getTableHeader().setFont(BOLD_FONT);
	    panel.add(table.getTableHeader(), c);

	    c = new GridBagConstraints();
	    c.gridx = VALUE_COL;
	    c.gridy = y;
	    c.weightx = 0.8;
	    c.weighty = 0.8;
	    c.fill = GridBagConstraints.BOTH;
	    panel.add(table, c);

	    y++;
	}

	label = new JLabel("Accession number file");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = LABEL_COL;
	c.ipadx = IPADX;
	c.ipady = IPADY;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	if (BioPAXIndexRepository.getInstance().getAccessionNumberTable() != null)
	    name = BioPAXIndexRepository.getInstance().getAccNumberFileName();
	else
	    name = "<not loaded>";

	label = new JLabel(name);
	label.setFont(PLAIN_FONT);
	c = new GridBagConstraints();
	c.gridx = VALUE_COL;
	c.ipadx = IPADX;
	c.ipady = IPADY;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	y++;

	label = new JLabel("Number of records");
	label.setFont(BOLD_FONT);
	c = new GridBagConstraints();
	c.gridx = LABEL_COL;
	c.ipadx = IPADX;
	c.ipady = IPADY;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	if (BioPAXIndexRepository.getInstance().getAccessionNumberTable() != null)
	    name = (new Integer(BioPAXIndexRepository.getInstance().getAccessionNumberTable().idname.size())).toString();
	else
	    name = "<no records>";

	label = new JLabel(name);
	label.setFont(PLAIN_FONT);
	c = new GridBagConstraints();
	c.gridx = VALUE_COL;
	c.ipadx = IPADX;
	c.ipady = IPADY;
	c.gridy = y;
	c.anchor = GridBagConstraints.WEST;
	panel.add(label, c);

	y++;
	JPanel padPanel = new JPanel();
	c = new GridBagConstraints();
	c.gridx = VALUE_COL + 1;
	c.gridy = y;
	c.ipadx = 30;
	c.ipady = 30;
	c.anchor = GridBagConstraints.WEST;
	panel.add(padPanel, c);

	padPanel = new JPanel();
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 0;
	c.ipadx = 30;
	c.ipady = 30;
	c.anchor = GridBagConstraints.WEST;
	panel.add(padPanel, c);

	JPanel buttonPanel = new JPanel();

	okB = new JButton("OK");

	okB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                }
	    });
	
	showAllEntityNamesB = new JButton("Show full list of names...");

	showAllEntityNamesB.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				ListAllNodesDialog dialog = new ListAllNodesDialog();
				dialog.showOnlyEntityNames = true;
				dialog.showAlsoSynonyms = true;
				dialog.pop(BioPAXIndexRepository.getInstance().getBioPAXGraphQueryEngine().database, null, null);
                }
	    });
	

	buttonPanel.add(okB);
	buttonPanel.add(showAllEntityNamesB);

	getContentPane().setLayout(new BorderLayout());

	JScrollPane jpane = new JScrollPane(panel);
	getContentPane().add(jpane, BorderLayout.CENTER);
	getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

    public static BioPAXDisplayIndexInfoDialog instance;

    public static BioPAXDisplayIndexInfoDialog getInstance() {
	if (instance == null)
	    instance = new BioPAXDisplayIndexInfoDialog();
	return instance;
    }

    public void raise(BioPAX biopax) {
	this.biopax = biopax;

	Dimension size = getSize();

	setSize(new Dimension(700, 420));
	
	setLocation((screenSize.width - getSize().width) / 2,
                    (screenSize.height - getSize().height) / 2);
	setVisible(true);
    }
}
